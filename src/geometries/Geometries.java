package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    // we choose linkList because we need to go through all the objects iteratively
    private List<Intersectable> _intersectables = new LinkedList<>();


    public Geometries() {
        // nothing to add
    }

    public Geometries(Intersectable... intersectable) {
        add(intersectable);
    }

    /**
     *  add all intersectables in array's param to _intersectables
     * @param intersectable array of objects (geometries)
     */
    public void add(Intersectable... intersectable) {
        _intersectables.addAll(Arrays.asList(intersectable));
    }

    /**
     * Find the intersections of all objects in the list
     * @param ray light ray
     * @return List of intersection points (Point3D)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;

        for (Intersectable item : _intersectables) {
            List<Point3D> intersectionPoints = item.findIntersections(ray);

            if (intersectionPoints != null) {

                if (result == null) {
                    result = new LinkedList<>();
                }

                result.addAll(intersectionPoints);
            }
        }

        return result;
    }



}
