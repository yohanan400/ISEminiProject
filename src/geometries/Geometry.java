package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry interface is the base level of all the geometries'
 */
public interface Geometry {

    public Vector getNormal(Point3D point);
}
