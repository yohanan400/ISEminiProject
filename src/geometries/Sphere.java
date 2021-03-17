package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere class representing three-dimensional sphere in 3D Cartesian coordinate
 * system
 */
public class Sphere implements Geometry {

    Point3D _center;
    double _radius;


    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
