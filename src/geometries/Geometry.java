package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry interface is the base level of all the geometries'
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public abstract class Geometry implements Intersectable {

    protected Color emission = Color.BLACK;
    private Material _material = new Material();

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
    public Color getEmission() {
        return emission;
    }

    /**
     * return the material of the geometry
     * @return the material of the geometry (Material)
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * set the emmission of the object
     * @param emission the emmission of the object
     * @return this
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * set the material of the geometry
     * @param material the material of the geometry (Material)
     * @return the geometry (Geometry)
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }
}
