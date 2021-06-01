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
     * Find the intersection point of the ray and the triangle
     *
     * @param ray The light ray
     * @return List of intersection GeoPoint between the ray and triangle
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        // Check if the ray intersect the containing plane of the triangle
        // If not return null (sure not intersect)
        if (plane.findGeoIntersections(ray) == null) {
            return null;
        }

        // Create the triangle
        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        // Calculate the normals
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        Vector v = ray.getDir();

        // If all in the same direction the ray intersect the triangle
        if ((alignZero(v.dotProduct(n1)) > 0 && alignZero(v.dotProduct(n2)) > 0 && alignZero(v.dotProduct(n3)) > 0) ||
                (alignZero(v.dotProduct(n1)) < 0 && alignZero(v.dotProduct(n2)) < 0 && alignZero(v.dotProduct(n3)) < 0)) {

            // find the intersection point and return it as a list of GeoPoint
            return List.of(new GeoPoint(this, plane.findGeoIntersections(ray).get(0)._point));
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
