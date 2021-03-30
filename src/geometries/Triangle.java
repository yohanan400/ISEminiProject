package geometries;

import primitives.Point3D;

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

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
