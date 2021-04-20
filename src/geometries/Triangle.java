package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Triangle class representing a two-dimensional Triangle in 3D Cartesian coordinate
 * system
 */
public class Triangle extends Polygon {

    /**
     * Triangle c-tor receiving a list of vertices (Point3D).
     *
     * @param vertices Point3D[]
     */
    public Triangle(Point3D... vertices) {
        super(vertices);
    }

    /**
     * find the intersections
     *
     * @param ray light ray
     * @return List of intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        //check if the ray intersect the plane
        if (plane.findIntersections(ray) == null) {
            return null;
        }

        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        Vector v = ray.getDir();

        if ((alignZero(v.dotProduct(n1)) > 0 && alignZero(v.dotProduct(n2)) > 0 && alignZero(v.dotProduct(n3)) > 0) ||
                (alignZero(v.dotProduct(n1)) < 0 && alignZero(v.dotProduct(n2)) < 0 && alignZero(v.dotProduct(n3)) < 0)){

            return  plane.findIntersections(ray);
        }
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
