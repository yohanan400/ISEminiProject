package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represent the light source
 */
public interface LightSource {
    /**
     * return the light intensity in specific point
     *
     * @param p Point3D
     * @return the intensity color in the received point (Color)
     */
    public Color getIntensity(Point3D p);

    /**
     * return the normalize direction vector from the light source to the object
     *
     * @param p the position point (Point3D)
     * @return the normalized vector (Vector)
     */
    public Vector getL(Point3D p);

}
