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

    private static final double DELTA = 0.1;

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

        GeoPoint p = ray.getClosestGeoPoint(_scene._geometries.findGeoIntersections(ray));

        return calcColor(p, ray);
    }

    /**
     * calculate the color on specific point on the object
     *
     * @param geoPoint The object with the closest intersection point with the object
     * @param ray      The intersect ray
     * @return The color on the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color ambientLightIntensity = _scene._ambientLight.getIntensity();
        Color EmissionColor = geoPoint._geometry.getEmission();
        return ambientLightIntensity.add(EmissionColor).add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * calculate the diffuse and the specular on the object
     *
     * @param intersection The object with the closest intersection point with the object
     * @param ray          The intersect ray
     * @return The diffuse and the specular on the object
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection._geometry.getNormal(intersection._point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        Material material = intersection._geometry.getMaterial();

        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = Color.BLACK;

        for (LightSource lightSource : _scene._lightSourceList) {
            Vector l = lightSource.getL(intersection._point);

            double nl = alignZero(n.dotProduct(l));
            // if sign(nl) == sing(nv)
            if (nl * nv > 0) {
                if (unshaded(lightSource ,l,n, intersection)) {
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
     * @param l The ray from the light source
     * @param n The normal to the object
     * @param geoPoint The intersection point between the light ray and the object
     * @return It there not have an intersections with the shadow ray (Boolean)
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint){

        // the light ray from the intersection point to the light
        Vector lightDirection = l.scale(-1);

        //calculate the delta size to move the starting point of the shadow ray
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);

        // update the starting point
        Point3D point = geoPoint._point.add(delta);

        // create the shadow ray
        Ray lightRay= new Ray(point, lightDirection);

        //check if there have an intersections between the object to the light source
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance= light.getDistance(geoPoint._point);
        for (GeoPoint gp: intersections) {
            if (alignZero(gp._point.distance(geoPoint._point) - lightDistance) <= 0)
                return false;
        }
        return true;

    }
}


