package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Sphere class representing three-dimensional sphere in 3D Cartesian coordinate
 * system
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Sphere extends Geometry {

    /**
     * The center of the sphere
     */
    Point3D _center;

    /**
     * The radius of the sphere
     */
    double _radius;


    /**
     * c-tor initialize the fields with the receiving values
     *
     * @param center The center of the sphere (Point3D)
     * @param radius The radius of the sphere (double)
     */
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * Return the center point value
     *
     * @return The center of the sphere (Point3D)
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * Returns the radius value
     *
     * @return The radius of the sphere (double)
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * Return the normal to the sphere in the receiving point
     *
     * @param point Point on the sphere (Point3D)
     * @return The normal to the sphere in the receiving point (Vector)
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector v = new Vector(point.subtract(_center).getHead());
        return v.normalize(); // return the normalize normal vector
    }

    /**
     * Find the first intersection point of the ray and the sphere
     *
     * @param ray The light ray
     * @return List of intersection GeoPoints between the received ray and the sphere
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        Point3D p0 = ray.getP0(); // The head of the ray
        Vector V = ray.getDir(); // The direction of the ray
        Point3D O = _center; // The center of the sphere

        // If p0 is on the center, calculate with line parametric representation
        // (the direction vector already normalized)
        if (O.equals(p0)) {
            return List.of(new GeoPoint(this, p0.add(V.scale(_radius))));
        }
        // Calculate the intersection point by Pythagoras statement.
        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);

        // The distance between the center point of the sphere to the ray (by creating 90 degrees)
        double d = Math.sqrt(U.lengthSquared() - tm * tm);

        // If the distance d is bigger then the radius, the ray go next to the sphere (outside)
        // which mean 0 intersection point
        if (d >= _radius) {
            return null;
        }

        // Calculate the intersection points (from both sides)
        double th = Math.sqrt(_radius * _radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        // If there have 2 intersection points (the ray cross the sphere at two points)
        if (t1 > 0 && t2 > 0) {
            GeoPoint gp1 = new GeoPoint(this, ray.getPoint(t1));
            GeoPoint gp2 = new GeoPoint(this, ray.getPoint(t2));
            return List.of(gp1, gp2); // List of the two intersection points
        }

        // If there have only one intersection point (the closest side to the ray)
        if (t1 > 0) {
            GeoPoint gp1 = new GeoPoint(this, ray.getPoint(t1));
            return List.of(gp1); // List of the intersection point
        }

        // If there have only one intersection point (the farther side to the ray)
        if (t2 > 0) {
            GeoPoint gp2 = new GeoPoint(this, ray.getPoint(t2));
            return List.of(gp2); // List of the intersection point
        }
        return null; // If there have not any intersection point
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
