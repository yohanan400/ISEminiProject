package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry interface is the base level of all the geometries'
 */
public interface Geometry extends Intersectable {

    /**
     * calculate the normal
     *
     * @param point Point on the surface of the geometry shape
     * @return the normal of the Geometry shape (Vector type)
     */
    public Vector getNormal(Point3D point);
}
