package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Tube class representing three-dimensional tube (infinite cylinder) in 3D Cartesian coordinate
 * system
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Tube extends Geometry {

    /**
     * The centered ray of the tube
     */
    protected Ray _axisRay;

    /**
     * The radius of the tube
     */
    protected double _radius;

    /**
     * c-tor initiate the fields with the receiving values
     *
     * @param axisRay The centered ray of the tube
     * @param radius  The radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * Return the centered ray of the tube
     *
     * @return The centered ray of the tube (Ray)
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * Return the radius value of the tube
     *
     * @return The radius value of the tube (double)
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * Receiving a point, calculate and return the normal of the tube in the current point.
     *
     * @param point A point on the tube
     * @return The normal of the tube in the receiving point (Vector)
     */
    @Override
    public Vector getNormal(Point3D point) {

        Vector centeredVectorDirection = _axisRay.getDir();
        Point3D p0 = _axisRay.getP0();

        // If the projection equals to zero we cant calculate a normal.
        double projection = centeredVectorDirection.dotProduct(point.subtract(p0));
        if (projection == 0) throw new IllegalArgumentException("the projection cannot be 0");

        // Calculate the point on the centered ray of the tube to calculate the normal with it.
        Point3D center = p0.add(centeredVectorDirection.scale(projection));

        // Calculate the normal
        Vector v = point.subtract(center);

        // Return the normalized normal
        return v.normalize();
    }

    /**
     * Return list of intersection GeoPoint
     *
     * @param ray The light ray
     * @return List of intersection GeoPoint
     */
    // NOT IMPLEMENTED
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
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
