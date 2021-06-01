package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Represent spot light
 */
public class SpotLight extends PointLight {
    private final Vector _direction; // The direction vector of the spot light

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
