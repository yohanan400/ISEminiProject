package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry interface is the base level of all the geometries'
 */
public abstract class Geometry implements Intersectable {

    protected Color emmission = Color.BLACK;

    /**
     * calculate the normal
     *
     * @param point Point on the surface of the geometry shape
     * @return the normal of the Geometry shape (Vector type)
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * return the emmission of the object
     * @return Color value
     */
    public Color getEmmission() {
        return emmission;
    }

    /**
     * set the emmission of the object
     * @param emmission the emmission of the object
     * @return this
     */
    public Geometry setEmmission(Color emmission) {
        this.emmission = emmission;
        return this;
    }
}
