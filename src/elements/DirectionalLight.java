package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Directional lights are very useful for creating effects such as sunlight in your scenes.
 * Behaving in many ways like the sun, directional lights can be thought of as distant light sources
 * which exist infinitely far away.
 * A directional light does not have any identifiable source position and so the light object can be placed anywhere in the scene.
 * All objects in the scene are illuminated as if the light is always from the same direction.
 * The distance of the light from the target object is not defined and so the light does not diminish.
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * The direction of the directional light
     */
    private final Vector _direction;

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
