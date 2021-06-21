package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Like a point light, a spot light has a specified location and range over which the light falls off.
 * However, the spot light is constrained to an angle, resulting in a cone-shaped region of illumination.
 * The center of the cone points in the forward direction of the light object.
 * Light also diminishes at the edges of the spot light’s cone.
 * Widening the angle increases the width of the cone and with it increases the size of this fade, known as the ‘penumbra’.
 *
 * Spot lights are generally used for artificial light sources such as flashlights, car headlights and searchlights.
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class SpotLight extends PointLight {

    /**
     * The direction vector of the spot light
     */
    private final Vector _direction;

    /**
     * c-tor initialize all the fields
     *
     * @param intensity The intensity of the light
     * @param position  The position of the light
     * @param direction The direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position); // initialize the fields to the received values
        _direction = direction.normalized(); // normalize the direction vector for the calculations
    }

    //--------------------------------------------------- GETTERS ---------------------------------------------------//

    /**
     * Calculate and return the intensity light on specific point
     *
     * @param p The point on the object (Point3D)
     * @return the intensity (Color)
     */
    @Override
    public Color getIntensity(Point3D p) {
        // calculate the angle between the direction vector to the light vector.
        // use Math.max to validate we take the positive angle
        double max = Math.max(0, _direction.dotProduct(getL(p)));

        // The intensity is the same like in PointLight but scaled by the angle
        // between the direction vector to the light vector
        return super.getIntensity(p).scale(max);
    }

    /**
     * Calculate the distance between the light source and the receiving point
     *
     * @param point the point to calculate the distance to
     * @return the distance between the light source and the receiving point
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

    //--------------------------------------------------- SETTERS ---------------------------------------------------//

    /**
     * Set the specular attenuation factor
     *
     * @param kC The specular attenuation factor
     * @return this (SpotLight)
     */
    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC);

        // return this for chaining
        return this;
    }

    /**
     * Set the light source attenuation factor
     *
     * @param kL The light source attenuation factor
     * @return this (SpotLight)
     */
    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);

        // return this for chaining
        return this;
    }

    /**
     * Attenuation factor
     *
     * @param kQ the attenuation factor
     * @return this (SpotLight)
     */
    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);

        // return this for chaining
        return this;
    }
}
