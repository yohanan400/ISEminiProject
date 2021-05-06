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
     * @param p
     * @return
     */
    public Vector getL(Point3D p);

}
