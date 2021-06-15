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
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Plane extends Geometry {

    /**
     * Point on the plane
     */
    Point3D _p0;

    /**
     * The normal of the plane
     */
    Vector _normal;

    /**
     * c-tor receiving 3 points on the plane
     * and calculate the normal of the plane
     *
     * @param p0 1st point on the plane (Point3D)
     * @param p1 2nd point on the plane (Point3D)
     * @param p2 3rd point on the plane (Point3D)
     */
    public Plane(Point3D p0, Point3D p1, Point3D p2) {
        _p0 = p0;

        // To calculate the normal we take 2 vectors (from the three received points)
        // and cross Product them. Eventually we normalize the normal.

        // Calculate the 2 vectors
        Vector v1 = new Vector(p1.subtract(p0).getHead());
        Vector v2 = new Vector(p2.subtract(p0).getHead());

        // Calculate the normal and normalize him
        _normal = new Vector(v1.crossProduct(v2).normalize().getHead());
    }

    /**
     * c-tor initialize the fields with the receiving values
     *
     * @param p0     Point on the plane
     * @param normal The normal of the plane
     */
    public Plane(Point3D p0, Vector normal) {
        _p0 = p0;
        _normal = normal;
    }

    /**
     * Get the normal of the plane on a specific point
     *
     * @param point Point on the surface of the geometry shape
     * @return The normal on the receiving point
     */
    @Override
    public Vector getNormal(Point3D point) {

        Vector v1 = point.subtract(_p0);
        Vector v2 = v1.crossProduct(_normal);
        Vector v3 = v1.subtract(v2);

        return v3.crossProduct(v1).normalize();
    }

    /**
     * Return the relative point of the plane
     *
     * @return Point3D value
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Return the normal vector of the plane
     *
     * @return Vector value
     */
    public Vector getNormal() {
        return _normal;
    }

    /**
     * Find the intersection point of the ray and the plane
     *
     * @param ray The light ray
     * @return List of intersection GeoPoint between the plane and the ray
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        // If the ray start at the plane
        if (_p0.equals(ray.getP0())) {
            return null;
        }

        // If the ration between the ray points (in general) and the plane points (in general)
        // is bigger then 1, it is a intersection point.
        // Ray points: 𝑃=𝑃0+𝑡∙𝑣
        // Plane points: N∙(𝑄0−𝑃)
        // 𝑡 = ( 𝑁∙(𝑄0−𝑃0) ) / 𝑁∙𝑣
        double numerator = (double) _normal.dotProduct(_p0.subtract(ray.getP0()));
        double denominator = (double) _normal.dotProduct(ray.getDir());

        // If the numerator equal to 0, the starting point is at the plane
        // If the denominator equal to 0, denominator can't be equal to 0
        if (isZero(numerator) || isZero(denominator)) {
            return null;
        }

        double t = alignZero(numerator / denominator);

        // if t<0, the starting point after the plane
        if (t < 0) {
            return null;
        }

        Point3D point = ray.getP0().add(ray.getDir().scale(t));

        // The intersection point
        Point3D p = new Point3D(point.getX(),
                point.getY(),
                point .getZ());

        return List.of(new GeoPoint(this, p)); // List of intersection GeoPoint
    }

    @Override
    public String toString() {
        return "Plane{" +
                "p0=" + _p0 +
                ", normal=" + _normal +
                '}';
    }
}
