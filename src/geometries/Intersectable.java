package geometries;

import primitives.Point3D;
import primitives.Ray;


import java.util.List;

/**
 * Intersectable interface, to calculate all the intersections on the geometries shapes
 */
public interface Intersectable {

    /**
     * calculate the intersactions on the geometry shapes
     * @param ray light ray
     * @return List of all the intersections between the shape and the ray (List type)
     */
    List<Point3D> findIntersections(Ray ray);
}
