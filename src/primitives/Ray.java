package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Ray class representing a ray.
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Ray {

    Point3D _p0;
    Vector _dir;

    /**
     * Ray c-tor receiving a Point3D and a Vector.
     *
     * @param p0  Point3D value
     * @param dir Vector value
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * getP0 method return 'this' p0 Point3D.
     *
     * @return Point3D
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getDir method return the 'this' dir Vector.
     *
     * @return Vector
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * Calculate points on the ray
     *
     * @param t is distance from the ray origin p0 to some point on the ray
     * @return point on the ray
     */
    public Point3D getPoint(double t) {
        return _p0.add(_dir.scale(t));
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

    /**
     * find the closest point to the ray
     *
     * @param point3DList list of points
     * @return the closest point (Point3D value)
     */
    public Point3D findClosestPoint(List<Point3D> point3DList) {
        if (point3DList.isEmpty())
            return null;

        double minDistance = point3DList.get(0).distance(getP0());
        Point3D closest = point3DList.get(0);

        for (Point3D item : point3DList) {
            if (item.distance(getP0()) < minDistance) {
                closest = item;
                minDistance = item.distance(getP0());
            }
        }
        return closest;
    }

    /**
     * find the closest point to the ray
     *
     * @param geoPointList list of geoPoints
     *                     (they all the same geometry but different intersection points, if there have more then one intersection point)
     * @return the geometry with his closest intersection point to the ray (GeoPoint)
     */
    public GeoPoint getClosestGeoPoint(List<GeoPoint> geoPointList) {

        if (geoPointList.isEmpty()) return null;

        double minDistance = geoPointList.get(0)._point.distance(_p0);

        //indexes to find the element with the closest point.
        int indexOfElementWithClosestPoint = 0;
        int iterationIndex = 0;

        for (GeoPoint item : geoPointList) {
            if (item._point.distance(getP0()) < minDistance) {
                minDistance = item._point.distance(getP0());
                indexOfElementWithClosestPoint = iterationIndex;
            }
            iterationIndex++;
        }

        return geoPointList.get(indexOfElementWithClosestPoint);
    }

}
