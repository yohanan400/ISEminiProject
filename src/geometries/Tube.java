package geometries;

import primitives.*;

/**
 * Tube class representing three-dimensional tube in 3D Cartesian coordinate
 * system
 */
public class Tube implements Geometry {

    protected Ray _axisRay;
    protected double _radius;

    /**
     * Tube c-tor receiving ray (Ray) and radius (double)
     *
     * @param axisRay Ray value
     * @param radius  double value
     */
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * getAxisRay return the ray value
     *
     * @return Ray value
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * getRadius return the radius value
     *
     * @return double value
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * getNormal receiving a point, calculate and return the normal of the tube in the current point.
     *
     * @param point Point3D value
     * @return Vector value
     */
    @Override
    public Vector getNormal(Point3D point) {

        double projection = _axisRay.getDir().dotProduct(point.subtract(_axisRay.getP0()));
        if (projection == 0) throw new IllegalArgumentException("the projection cannot be 0");

        Point3D center = _axisRay.getP0().add(_axisRay.getDir().scale(projection));
        Vector v = point.subtract(center);
        return v.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tube tube = (Tube) o;

        if (Double.compare(tube._radius, _radius) != 0) return false;
        return _axisRay != null ? _axisRay.equals(tube._axisRay) : tube._axisRay == null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}
