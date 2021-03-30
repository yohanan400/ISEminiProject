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


    /**
     * sphere c-tor receiving center (Point3D) and radius (double).
     *
     * @param center Point3D value
     * @param radius double value
     */
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * getCenter returns the center point value.
     *
     * @return Point3D value
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getRadius returns the radius value.
     *
     * @return double value
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * getNormal receiving point on the sphere and return the normal.
     *
     * @param point Point3D value
     * @return Vector value
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector v = new Vector(point.subtract(_center).getHead());
        return v.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sphere sphere = (Sphere) o;

        if (Double.compare(sphere._radius, _radius) != 0) return false;
        return _center != null ? _center.equals(sphere._center) : sphere._center == null;
    }


    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }
}
