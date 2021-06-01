package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Ray class representing a ray in the space (3D).
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Ray {

    /**
     * The head point of the ray
     */
    Point3D _p0;

    /**
     * The direction vector of the ray
     */
    Vector _dir;

    /**
     * The distance to move the head of the vector from his current location
     */
    private static final double DELTA = 0.1;

    /**
     * c-tor, initiate the fields with the receiving values.
     *
     * @param p0  The head point of the ray
     * @param dir The direction vector of the ray
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * c-tor calculate new ray with distance of +/- DELTA
     *
     * @param head      The intersection point
     * @param direction The direction vector of the intersect ray
     * @param normal    The normal to the point on the intersected object
     */
    public Ray(Point3D head, Vector direction, Vector normal) {

        // If the normal vector and the direction vector have the same sign, add +DELTA
        // otherwise add -DELTA
        Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA);

        // Adding the DELTA to the head of the ray
        _p0 = head.add(delta);

        // Normalize the direction vector of the ray
        _dir = direction.normalized();
    }

    /**
     * Return the head of the ray
     *
     * @return The head of the ray (Point3D)
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Return the direction Vector
     *
     * @return The direction Vector (Vector)
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * Calculate points on the ray
     *
     * @param t The distance from the head of the ray to some other point on the ray
     * @return The new point on the ray
     */
    public Point3D getPoint(double t) {

        // Adding the product of the ray direction vector and the 't' parameter to ray head
        return _p0.add(_dir.scale(t));
    }

    /**
     * Find the closest point to the head of the ray from the receiving points list as parameter
     *
     * @param point3DList List of points
     * @return The closest point to the head of the ray from the receiving point list(Point3D)
     */
    public Point3D findClosestPoint(List<Point3D> point3DList) {

        // If the list is empty
        if (point3DList.isEmpty())
            return null;

        // The initial distance to compare with the rest of the list
        // is the distance between the head of the ray and the first point in the list
        double minDistance = point3DList.get(0).distance(getP0());

        // The initial closest point is the first point in the list
        Point3D closest = point3DList.get(0);

        // Calculate and compare the distance between the rest of the points in the list
        for (Point3D item : point3DList) {
            // Compare the distance between the minDistance were found until now and the
            // distance of the new point (the next point in the points list)
            if (item.distance(getP0()) < minDistance) {
                // Update the closest point to be the new point
                closest = item;
                //Update the new minimum distance to compare with
                minDistance = item.distance(getP0());
            }
        }
        return closest;
    }

    /**
     * Find the closest point to the ray from the receiving GeoPoints list as parameter
     *
     * @param geoPointList list of geoPoints
     *                     (they all the same geometry but different intersection points,
     *                     if there have more then one intersection point)
     * @return the geometry with his closest intersection point to the ray (GeoPoint)
     */
    public GeoPoint getClosestGeoPoint(List<GeoPoint> geoPointList) {

        // If the list is empty
        if (geoPointList.isEmpty()) return null;

        // The initial distance to compare with the rest of the list
        // is the distance between the head of the ray and the first point in the GeoPoint list
        double minDistance = geoPointList.get(0)._point.distance(getP0());

        //indexes to find the element with the closest point.
        // The initial value is the first element in the list (with index of 0)
        int indexOfElementWithClosestPoint = 0;
        // The index of the current GeoPoint (initial to the first GeoPoint, with index of 0)
        int iterationIndex = 0;

        // Calculate and compare the distance between the rest of the GeoPoints in the list
        for (GeoPoint item : geoPointList) {
            // Compare the distance between the minDistance were found until now and the
            // distance of the new GeoPoint (the next GeoPoint in the GeoPoints list)
            if (item._point.distance(getP0()) < minDistance) {
                //Update the new minimum distance to compare with
                minDistance = item._point.distance(getP0());
                // Update the index of the closest GeoPoint to be the index of the current GeoPoint
                indexOfElementWithClosestPoint = iterationIndex;
            }
            // Update the index to the index of the next GeoPoint in the list
            iterationIndex++;
        }
        // Return the GeoPoint with the index of the closest GeoPoint were found
        return geoPointList.get(indexOfElementWithClosestPoint);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + _p0 +
                ", _dir=" + _dir +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ray ray = (Ray) o;

        if (_p0 != null ? !_p0.equals(ray._p0) : ray._p0 != null) return false;
        return _dir != null ? _dir.equals(ray._dir) : ray._dir == null;
    }
}
