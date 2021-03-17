package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane class representing a two-dimensional plane in 3D Cartesian coordinate
 * system
 */
public class Plane implements Geometry {

    Point3D _p0;
    Vector _normal;

    /**
     * plane c-tor receiving 3 Point3D values
     *
     * @param p0 Point3D value
     * @param p1 Point3D value
     * @param p2 Point3D value
     */
    public Plane(Point3D p0, Point3D p1, Point3D p2) {
        _p0 = p0;
        _normal = new Vector(null);
    }

    /**
     * plane c-tor receiving Point3D values and Vector value
     *
     * @param p0     Point3D value
     * @param normal Vector value
     */
    public Plane(Point3D p0, Vector normal) {
        _p0 = p0;
        _normal = normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    /**
     * getP0 method return the relative point of the plane
     *
     * @return Point3D value
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getNormal method return the normal vector of the plane
     *
     * @return Vector value
     */
    public Vector getNormal() {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "p0=" + _p0 +
                ", normal=" + _normal +
                '}';
    }

}