package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class representing a two-dimensional plane in 3D Cartesian coordinate
 * system
 */
public class Plane implements Geometry {

    Point3D _p0;
    Vector _normal;

    /**
     * plane c-tor receiving 3 Point3D values
     * and calculate the normal of the plane
     *
     * @param p0 Point3D value
     * @param p1 Point3D value
     * @param p2 Point3D value
     */
    public Plane(Point3D p0, Point3D p1, Point3D p2) {
        _p0 = p0;
        Vector v1 = new Vector(p1.subtract(p0).getHead());
        Vector v2 = new Vector(p2.subtract(p0).getHead());
        _normal = new Vector(v1.crossProduct(v2).normalize().getHead());
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


    /**
     * find the intersections
     *
     * @param ray light ray
     * @return List of intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        if (_p0.equals(ray.getP0())) {
            return null;
        }

        double numerator = (double) _normal.dotProduct(_p0.subtract(ray.getP0()));
        double denominator = (double) _normal.dotProduct(ray.getDir());

        //if the numerator equal to 0, the starting point is at the plane
        // if the denominator equal to 0, denominator can't be equal to 0
            if (isZero(numerator) || isZero(denominator)) {
            return null;
        }

        double t = alignZero(numerator / denominator);

        // if t<0, the starting point after the plane
        if (t < 0) {
            return null;
        }

        // the intersection point
        Point3D p = new Point3D(ray.getP0().add(ray.getDir().scale(t)).getX(),
                ray.getP0().add(ray.getDir().scale(t)).getY(),
                ray.getP0().add(ray.getDir().scale(t)).getZ());

        return List.of(p);
    }

    @Override
    public String toString() {
        return "Plane{" +
                "p0=" + _p0 +
                ", normal=" + _normal +
                '}';
    }
}
