package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Implementation of RayTracerBase class
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class RayTracerBasic extends RayTracerBase {

    //--------------------------------------------------- FIELDS ---------------------------------------------------//

    /**
     * The number of continuous rays to calculate the impact on the intersection points
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Stop condition, the minimum impact of rays to continue calculate
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * The starting impact value of ray on intersection point
     */
    private static final double INITIAL_K = 1.0;

    //--------------------------------------------------- METHODS ---------------------------------------------------//

    /**
     * c-tor, initiate the scene field with received scene
     *
     * @param scene The scene (Scene)
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * If the ray intersect the geometry, paint in the final color
     *
     * @param ray The ray to check if he intersect the geometry (Ray)
     * @return The color to paint the pixel (Color)
     */
    @Override
    public Color traceRay(Ray ray) {

        // Search the closest intersection point of the ray in the geometry
        GeoPoint closestPoint = findClosestIntersection(ray);

        // If no intersection paint as a background,
        // else calculate the color to paint and return it
        return closestPoint == null ? _scene._background : calcColor(closestPoint, ray);
    }

    /**
     * Calculate the color of the receiving point
     *
     * @param geopoint The intersection point (and the intersected geometry) (GeoPoint)
     * @param ray      The intersect ray (Ray)
     * @return The final color (Color)
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.getIntensity());
    }

    /**
     * Calculate the color on specific point on the object
     *
     * @param intersection The intersected geometry object with the closest intersection point with the ray
     * @param ray          The intersect ray
     * @param level        The number of intersection levels the ray intersect (the depth of continuous ray to check)
     * @param k            The attenuation factor of the intersected ray
     * @return The color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        // The emission color of the intersected geometry object
        Color color = intersection._geometry.getEmission();

        // Adding the local effects on the point (e.g diffuse and specular)
        color = color.add(calcLocalEffects(intersection, ray, k));

        // If is the last level return the final color, else calculate the global effects (e.g reflected and reflected rays)
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculate the diffuse and the specular on the object
     *
     * @param intersection The intersected geometry object with the closest intersection point with the ray
     * @param ray          The intersect ray
     * @param k            Attenuation factor of the impact of ray on point
     * @return The diffuse and the specular on the object
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        // Preparing the values for calculating
        // v = the direction vector of the ray
        Vector v = ray.getDir();
        // n = the normal vector to the intersected geometry object
        Vector n = intersection._geometry.getNormal(intersection._point);

        // If the ray and the normal are orthogonal, no color to add (equal to BLACK color)
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        // Preparing the values for calculating
        // The material type of the intersected geometry object
        Material material = intersection._geometry.getMaterial();
        // The shininess attenuation of the intersected object
        int nShininess = material.nShininess;
        // The diffuse attenuation of the intersected object
        double kd = material.kD;
        // The specular attenuation of the intersected object
        double ks = material.kS;
        // Initial the final color to BLACK (equal to (0,0,0))
        Color color = Color.BLACK;

        // For all light source in the scene, calculate his impact on the intersected geometry object
        for (LightSource lightSource : _scene._lightSourceList) {
            // Preparing the values for calculating
            // l = the normalized vector from the light source to the intersection point
            Vector l = lightSource.getL(intersection._point);
            // nl = the dot product between n and l to check if nl and nv have the same sign
            // If they have the same sign the ray from the light source visible to the camera
            double nl = alignZero(n.dotProduct(l));
            // if sign(nl) == sing(nv)
            if (nl * nv > 0) {
                // The general transparency attenuation factor
                double ktr = transparency(lightSource, l, n, intersection);
                // If the impact of the lights are to small don't calculate the rest (diffuse and specular etc)
                if (ktr * k > MIN_CALC_COLOR_K) {
                    // The intensity of the light in the intersection point after attenuation
                    Color lightIntensity = lightSource.getIntensity(intersection._point).scale(ktr);
                    // Add to the diffuse and the specular of the light to the final color of the intersection point
                    color = color.add(calcDiffusive(kd, lightIntensity, nl), calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl));
                }
            }
        }
        // Return the final color
        return color;
    }

    /**
     * Calculate the diffuse of the light source on the intersection point
     * in the intersected geometry object
     *
     * @param kD             Diffuse attenuation factor
     * @param nl             The normalized vector from the position point (of the light source) to intersection point
     *                       dot (product) the normal vector to the object
     * @param lightIntensity The light intensity of the light source
     * @return The diffuse (intensity) color on the object (Color)
     */
    private Color calcDiffusive(double kD, Color lightIntensity, double nl) {
        // Calculate the diffuse intensity color
        Color diffuse = lightIntensity.scale(kD * Math.abs(nl));
        return diffuse;
    }

    /**
     * Calculate the specular of the light source on the intersection point
     * in the intersected geometry object
     *
     * @param kS             Specular attenuation factor
     * @param l              The normalized vector from the position point to attached point
     * @param n              The normal vector to the object
     * @param v              The direction vector from the camera
     * @param nShininess     Shininess attenuation factor
     * @param lightIntensity The light intensity
     * @return The specular intensity color on the object (color)
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity, double nl) {
        // Calculate the return vector of the specular from the light source to the camera
        Vector r = l.subtract(n.scale(nl).scale(2));
        // Calculate the specular intensity color
        Color specular = lightIntensity.scale(kS * Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), nShininess));
        return specular;
    }

    /**
     * Calculate partial shadow
     *
     * @param light    The light source
     * @param l        The ray from the light source
     * @param n        The normal to the intersection point
     * @param geoPoint The intersection point
     * @return The shadowing factor (Transparency factor)
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        // Calculate the continuous ray from the intersection point
        Ray lightRay = new Ray(geoPoint._point, l.scale(-1), n);

        // Check if there have any intersection between the object
        // from the intersection point to the light source
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(lightRay);
        // If no intersection the object is translucent (attenuation factor equal to 1)
        if (intersections == null) return 1.0;

        // Calculate the distance from the light source to the intersection point
        double lightDistance = light.getDistance(geoPoint._point);
        // Initiate the transparency to 1 (the object is translucent)
        double ktr = 1.0;

        // For each intersection point with the geometries check if have an intersection
        // between the intersection point to the light source
        for (GeoPoint gp : intersections) {
            // If have any object the ray cross
            if (alignZero(gp._point.distance(geoPoint._point) - lightDistance) <= 0)
                // The intensity of the light is attenuate by the transparency attenuation factor
                ktr *= gp._geometry.getMaterial()._kT;
            // If the intensity of the light ray is too small, the object is opaque
            if (ktr < MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
    }

    /**
     * Find all the intersection between the ray and the geometry object
     * and peak the closest intersection GeoPoint to the ray
     *
     * @param ray The intersect ray (Ray)
     * @return The closest intersection point (GeoPoint)
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        // Find all the intersection GeoPoints
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(ray);
        // If no any intersection, return null
        if (intersections == null) return null;

        // Find the closest intersection GeoPoint to the ray and return him
        return ray.getClosestGeoPoint(intersections);
    }

    /**
     * Calculate all the continues rays effects (A.K refraction and reflection)
     *
     * @param intersection The intersection point
     * @param ray          the intersect ray
     * @param level        the number of the rest layers the ray can go throw (the depth of the recursion)
     * @param k            Attenuation factor
     * @return The effected color (Color)
     */
    private Color calcGlobalEffects(GeoPoint intersection, Ray ray, int level, double k) {
        // Prepare the values for calculation
        // Initiate the result color to BLACK (equal to (0,0,0))
        Color color = Color.BLACK;
        // The normal of the intersected geometry object at the intersection point
        Vector n = intersection._geometry.getNormal(intersection._point);
        // The direction of the light source ray
        Vector v = ray.getDir();
        // The material type of the intersected geometry object
        Material material = intersection._geometry.getMaterial();

        // Update the reflection attenuation factor
        double kkr = k * material._kR;
        // If the impact is not very small, calculate the global effects
        if (kkr > MIN_CALC_COLOR_K)
            // Calculate the reflection
            color = calcGlobalEffect(constructReflectedRay(intersection._point, v, n), level, material._kR, kkr);

        // Update the refraction (transparency) attenuation factor
        double kkt = k * material._kT;
        // If the impact is not very small, calculate the global effects
        if (kkt > MIN_CALC_COLOR_K)
            // Calculate the refracted effect and add it to the result color
            color = color.add(calcGlobalEffect(constructRefractedRay(intersection._point, v, n), level, material._kT, kkt));
        // Return the final color
        return color;
    }

    /**
     * Recursion function to calculate all the chaining (continuous) effects
     *
     * @param ray   The intersect ray
     * @param level The number of the rest layers the ray can go throw (the depth of the recursion)
     * @param kx    Attenuation factor (x for reflection or refraction (transparency))
     * @param kkx   Attenuation factor after Attenuation
     * @return The color of the point (Color)
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        // Find the closest intersection point of the ray and the geometry object
        GeoPoint gp = findClosestIntersection(ray);
        // If no intersections the color is the background, otherwise calculate the color
        return (gp == null ? _scene._background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Calculate the reflected ray
     *
     * @param point The intersect point
     * @param v     The direction of the light ray
     * @param n     The normal vector of the geometry in the intersection point
     * @return The reflected ray (Ray)
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {

        // Calculate the reflection ray
        double nl = alignZero(n.dotProduct(v));
        Vector r = v.subtract(n.scale(nl).scale(2));

        // Create the reflection ray, and return it
        return new Ray(point, r, n);
    }

    /**
     * Calculate the refracted ray
     *
     * @param point The intersection point
     * @param v     The direction of the light ray
     * @param n     The normal vector of the geometry in the intersection point
     * @return The refracted ray (Ray)
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        // Create the refracted ray and return it
        return new Ray(point, v, n);
    }
}