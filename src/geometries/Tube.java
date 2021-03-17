package geometries;

import primitives.*;

/**
 * Tube class representing three-dimensional tube in 3D Cartesian coordinate
 * system
 */
public class Tube implements Geometry {

    protected Ray _axisRay;
    protected double _radius;

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
