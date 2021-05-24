package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * implementation of RayTracerBase class
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;


    /**
     * c-tor create RayTracerBasic with scene received
     *
     * @param scene The scene (Scene)
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * if the ray intersect  the geometry, paint in the right color
     *
     * @param ray the ray we check if he intersect with the geometry (Ray)
     * @return the right color to paint the pixel (Color)
     */
    @Override
    public Color traceRay(Ray ray) {

        if (_scene._geometries.findGeoIntersections(ray) == null)
            return _scene._background;

        GeoPoint p = findClosestIntersection(ray);
        return calcColor(p, ray);
    }

    /**
     * calculate the color of the receiving point
     *
     * @param geopoint the intersection point (GeoPoint)
     * @param ray      the ray cross the point (Ray)
     * @return the color (Color)
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.getIntensity());
    }

    /**
     * calculate the color on specific point on the object
     *
     * @param geoPoint The object with the closest intersection point with the object
     * @param ray      The intersect ray
     * @param level    The number of intersection levels the ray intersect
     * @param k        The attenuation factor of the intersected ray
     * @return The color on the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color EmissionColor = geoPoint._geometry.getEmission();
        return 1 == level ? EmissionColor : EmissionColor.add(calcLocalEffects(geoPoint, ray, level, k));
    }

    /**
     * calculate the diffuse and the specular on the object
     *
     * @param intersection The object with the closest intersection point with the object
     * @param ray          The intersect ray
     * @param level        The number of intersection levels the ray intersect
     * @param k            The attenuation factor of the intersected ray
     * @return The diffuse and the specular on the object
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, int level, double k) {
        Vector v = ray.getDir();
        Vector n = intersection._geometry.getNormal(intersection._point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        Material material = intersection._geometry.getMaterial();

        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = Color.BLACK;


        double kkr = k * material._kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(intersection._point, v, n), level, material._kR, kkr);
        double kkt = k * material._kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(calcGlobalEffect(constructRefractedRay(intersection._point, v, n), level, material._kT, kkt));

        for (LightSource lightSource : _scene._lightSourceList) {
            Vector l = lightSource.getL(intersection._point);

            double nl = alignZero(n.dotProduct(l));
            // if sign(nl) == sing(nv)
            if (nl * nv > 0) {
                if (unshaded(lightSource, l, n, intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection._point);
                    color = color.add(calcDiffusive(kd, lightIntensity, nl), calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl));
                }
            }
        }
        return color;
    }

    /**
     * calculate the diffuse on the object
     *
     * @param kD             Diffuse attenuation factor
     * @param nl             The normalized vector from the position point to attached point dot the normal vector to the object
     * @param lightIntensity The light intensity
     * @return The diffuse on the object (Color)
     */
    private Color calcDiffusive(double kD, Color lightIntensity, double nl) {
        Color diffuse = lightIntensity.scale(kD * Math.abs(nl));
        return diffuse;
    }

    /**
     * calculate the specular on the object
     *
     * @param kS             Specular attenuation factor
     * @param l              The normalized vector from the position point to attached point
     * @param n              The normal vector to the object
     * @param v              The direction vector from the camera
     * @param nShininess     Shininess attenuation factor
     * @param lightIntensity The light intensity
     * @return
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity, double nl) {
        Vector r = l.subtract(n.scale(nl).scale(2));
        Color specular = lightIntensity.scale(kS * Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), nShininess));
        return specular;
    }

    /**
     * Check if there have an intersections between the object to the light source
     *
     * @param l        The ray from the light source
     * @param n        The normal to the object
     * @param geoPoint The intersection point between the light ray and the object
     * @return It there not have an intersections with the shadow ray (Boolean)
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {

        if (geoPoint._geometry.getMaterial()._kT != 0)
            return true;

        Ray lightRay = new Ray(geoPoint._point, l.scale(-1), n);

        //check if there have an intersections between the object to the light source
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geoPoint._point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geoPoint._point) - lightDistance) <= 0)
                return false;
        }
        return true;
    }

    /**
     * Find closest intersection GeoPoint
     *
     * @param ray The intersect ray (Ray)
     * @return the closest intersection GeoPoint (GeoPoint)
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(ray);
        if (intersections ==  null) return null;
        return ray.getClosestGeoPoint(intersections);
    }

    /**
     * recursion function to calculate all the chaining effects
     *
     * @param ray   the intersect ray
     * @param level the number of the rest layers the ray can go throw
     * @param kx    Attenuation factor
     * @param kkx   Attenuation factor after Attenuation
     * @return the color on the point (Color)
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {

        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? _scene._background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * calculate the reflected ray
     *
     * @param point the intersect point
     * @param v     the direction of the light ray
     * @param n     the normal vector of the geometry in the intersected point
     * @return the reflected ray (Ray)
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {

        double nl = n.dotProduct(v);
        Vector r = v.subtract(n.scale(nl).scale(2));

        // create the reflection ray
        return new Ray(point, r, n);
    }

    /**
     * calculate the refracted ray
     *
     * @param point the intersect point
     * @param v     the direction of the light ray
     * @param n     the normal vector of the geometry in the intersected point
     * @return the refracted ray (Ray)
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }


}


