package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Triangle class representing a two-dimensional Triangle in 3D Cartesian coordinate
 * system
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Triangle extends Polygon {

    /**
     * c-tor initiate the vertices with the receiving list of vertices.
     *
     * @param vertices Point3D[]
     */
    public Triangle(Point3D... vertices) {
        super(vertices);
    }

    /**
     * Get the normal of the plane on a specific point
     *
     * @param point Point on the surface of the geometry shape
     * @return The normal on the receiving point
     */
    @Override
    public Vector getNormal(Point3D point) {

        Vector v1 = point.subtract(plane._p0);
        Vector v2 = v1.crossProduct(plane._normal);
        Vector v3 = v1.subtract(v2);

        return v3.crossProduct(v1).normalize();
    }

    /**
     * Find the intersection point of the ray and the triangle
     *
     * @param ray The light ray
     * @return List of intersection GeoPoint between the ray and triangle
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        List<GeoPoint> intersectionsList = plane.findGeoIntersections(ray);

        // Check if the ray intersect the containing plane of the triangle
        // If not return null (sure not intersect)
        if (intersectionsList == null) {
            return null;
        }

        // Create the triangle
        Point3D p0 = ray.getP0();
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        // Calculate the normals
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        Vector v = ray.getDir();

        double vn1 = alignZero(v.dotProduct(n1));
        double vn2 = alignZero(v.dotProduct(n2));
        double vn3 = alignZero(v.dotProduct(n3));

        // If all in the same direction the ray intersect the triangle
        if ((vn1 > 0 && vn2 > 0 && vn3 > 0) || (vn1 < 0 && vn2 < 0 && vn3 < 0)) {

            // find the intersection point and return it as a list of GeoPoint
            return List.of(new GeoPoint(this, intersectionsList.get(0)._point));
        }
        // If the ray not intersect the triangle
        return null;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
