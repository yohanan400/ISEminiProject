package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represent directional light
 */
public class DirectionalLight extends Light implements LightSource {

    private final Vector _direction; // The direction of the directional light

    /**
     * c-tor initialize the intensity filed
     *
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity); // Initialize the intensity to the received value
        _direction = direction;
    }

    /**
     * Get the intensity light on specific point
     *
     * @param p the point on the object (Point3D)
     * @return the intensity (Color)
     */
    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity();
    }

    /**
     * Return the normalize direction vector from the light source to the object
     *
     * @param p the point on the object (Point3D)
     * @return the normalize direction vector from the light source to the object (Vector)
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction.normalize();
    }

    /**
     * Calculate the distance between the light source and the receiving point
     *
     * @param point the point to calculate the distance to
     * @return the distance between the light source and the receiving point
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
