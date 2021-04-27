package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
        return;
    }

    @Override
    public Color traceRay(Ray ray) {

    if(_scene._geometries.findIntersections(ray)==null)
        return _scene._background;

    Point3D p = ray.findClosestPoint(_scene._geometries.findIntersections(ray));

        return calcColor(p);
    }

    public Color calcColor(Point3D point){
        return _scene._background;
    }
}