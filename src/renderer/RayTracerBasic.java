package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

/**
 * implementation of RayTracerBase class
 */
public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * if the ray intersect the geometry, paint in the right color
     * @param ray the ray we check if he intersect with the geometry (Ray)
     * @return the right color to paint the pixel (Color)
     */
    @Override
    public Color traceRay(Ray ray) {

    if(_scene._geometries.findIntersections(ray)==null)
        return _scene._background;

    Point3D p = ray.findClosestPoint(_scene._geometries.findIntersections(ray));

        return calcColor(p);
    }

    /**
     * calculate the right color to paint the object
     * @param point the point to calculate his color (Point3D)
     * @return the right color (Color)
     */
    public Color calcColor(Point3D point){
        return _scene._ambientLight.getIntensity();
    }
}
