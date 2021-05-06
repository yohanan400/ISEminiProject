package renderer;

import elements.AmbientLight;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * implementation of RayTracerBase class
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * if the ray intersect  the geometry, paint in the right color
     * @param ray the ray we check if he intersect with the geometry (Ray)
     * @return the right color to paint the pixel (Color)
     */
    @Override
    public Color traceRay(Ray ray) {

    if(_scene._geometries.findGeoIntersections(ray)==null)
        return _scene._background;

    GeoPoint p = ray.getClosestGeoPoint(_scene._geometries.findGeoIntersections(ray));

        return calcColor(p, ray);
    }

    /**
     * calculate the right color to paint the object
     * @param geoPoint the geoPoint to calculate his intersection point color (geoPoint)
     * @param ray the ray from the viewer (camera)
     * @return the color in the intersection point (Color)
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray){
        Color ambientLightIntensity = _scene._ambientLight.getIntensity();
        Color EmissionColor = geoPoint._geometry.getEmission();

        Vector l = _scene._lightSourceList.get(0).getL(geoPoint._point);
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        Vector r =l.subtract(n.scale(l.dotProduct(n)).scale(2));

        // factors (kD - diffuse factor, kS - specular factor and nSH - shininess factor)
        double kD = geoPoint._geometry.getMaterial().kD;
        double kS = geoPoint._geometry.getMaterial().kS;
        int nSH = geoPoint._geometry.getMaterial().nShininess;

        // calculate the diffuse and specular
        double diffuse = kD * l.dotProduct(n);
        double specular = kS * Math.max(0, Math.pow(ray.getDir().scale(-1).dotProduct(r), nSH));


        return ambientLightIntensity.add(EmissionColor).add(diffuse).add(specular);
    }
}
