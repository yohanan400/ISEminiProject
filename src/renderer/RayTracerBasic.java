package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static primitives.Util.alignZero;

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

    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color ambientLightIntensity = _scene._ambientLight.getIntensity();
        Color EmissionColor = geoPoint._geometry.getEmission();
        return ambientLightIntensity.add(EmissionColor).add(calcLocalEffects(geoPoint, ray));
    }

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
            if (nl * nv > 0) {
                // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection._point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    private Color calcDiffusive(double kD, Vector l, Vector n, Color lightIntensity) {
        Color diffuse = lightIntensity.scale(kD * Math.abs(l.dotProduct(n)));
        return diffuse;
    }

    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)).scale(2));
        Color specular = lightIntensity.scale(kS * Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), nShininess));
        return specular;
    }


}


