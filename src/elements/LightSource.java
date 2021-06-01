package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * An interface, represent the light source
 */
public interface LightSource {
    /**
     * Return the light intensity in specific point
     *
     * @param p Point3D
     * @return The intensity color in the received point (Color)
     */
    public Color getIntensity(Point3D p);

    /**
     * Return the normalize direction vector from the light source to the object
     *
     * @param p The position point (Point3D)
     * @return The normalized vector (Vector)
     */
    public Vector getL(Point3D p);

    /**
     * Calculate the distance between the light source and the receiving point
     *
     * @param point The point to calculate the distance to
     * @return The distance between the light source and the receiving point
     */
    double getDistance(Point3D point);

}
