package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represent collect of geometries
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Geometries implements Intersectable {

    // We choose linkList because we need to go through all the objects iteratively
    /**
     * List of all the geometries
     */
    private final List<Intersectable> _intersectables = new LinkedList<>();

    /**
     * geometries c-tor
     */
    public Geometries() {
        // nothing to add, we already initialize in the declaration of the filed.
    }

    /**
     * geometries c-tor. Initialize the intersectable geometries list to the received parameters
     *
     * @param intersectable List of intersectables
     */
    public Geometries(Intersectable... intersectable) {
        add(intersectable);
    }

    /**
     * Add all intersectable geometries in array's param to _intersectables
     *
     * @param intersectable Array of objects (geometries)
     */
    public void add(Intersectable... intersectable) {
        _intersectables.addAll(Arrays.asList(intersectable));
    }

    /**
     * Find the intersection points of all objects in the list
     *
     * @param ray light ray
     * @return List of geometries and their intersection points with the ray (GeoPoint)
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null; // Initialize the intersection GeoPoints list

        // For each geometry in the saved list in the _intersection field find the intersection GeoPoints
        for (Intersectable geo : _intersectables) {
            List<GeoPoint> intersectionPoints = geo.findGeoIntersections(ray);

            if (intersectionPoints != null) {

                if (result == null) { // If this is the first time to add an intersection, initialize the list
                    result = new LinkedList<>();
                }

                result.addAll(intersectionPoints); // Add the intersection GeoPoints to the list
            }
        }
        return result; // The list with all the intersection GeoPoints of the geometries in the list
    }
}