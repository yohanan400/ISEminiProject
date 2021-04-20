package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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


    /**
     * find the intersections
     *
     * @param ray light ray
     * @return List of intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getP0();
        Point3D O = _center;
        Vector V = ray.getDir();

        // if p0 on the center, calculate with line parametric representation
        // the direction vector normalized
        if (O.equals(p0)) {
            Point3D newPoint = p0.add(ray.getDir().scale(_radius));
            return List.of(new Point3D(newPoint.getX(), newPoint.getY(), newPoint.getZ()));
        }

        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= _radius) {
            return null;
        }

        double th = Math.sqrt(_radius * _radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 > 0 && t2 > 0) {
            Point3D p1 = ray.getPoint(t1);
            Point3D p2 = ray.getPoint(t2);
            return List.of(p1, p2);
        }

        if (t1 > 0) {
            Point3D p1 = ray.getPoint(t1);
            return List.of(p1);
        }

        if (t2 > 0) {
            Point3D p2 = ray.getPoint(t2);
            return List.of(p2);
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sphere sphere = (Sphere) o;

        if (Double.compare(sphere._radius, _radius) != 0) return false;
        return _center != null ? _center.equals(sphere._center) : sphere._center.equals(null);
    }


    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

}
