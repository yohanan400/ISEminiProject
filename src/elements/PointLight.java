package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * A point light is located at a point in space and sends light out in all directions equally.
 * The direction of light hitting a surface is the line from the point of contact back to the center of the light object.
 * The intensity diminishes with distance from the light, reaching zero at a specified range.
 * Light intensity is inversely proportional to the square of the distance from the source.
 * This is known as ‘inverse square law’ and is similar to how light behaves in the real world.
 *
 * Point lights are useful for simulating lamps and other local sources of light in a scene.
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class PointLight extends Light implements LightSource {

    //--------------------------------------------------- FIELDS ---------------------------------------------------//

    /**
     * The position point of the light source in space
     */
    protected Point3D _position;

    /**
     * The specular attenuation factor, required to ensure that the denominator in getIntensity >𝟏
     */
    private double kC = 1d;

    /**
     * The light source attenuation factor
     */
    private double kL = 0d;

    /**
     * The attenuation factor of the energy coming to the point
     */
    private double kQ = 0d;

    //--------------------------------------------------- C-TOR ---------------------------------------------------//

    /**
     * c-tor initialize all the fields
     *
     * @param intensity The intensity of the light
     * @param position  The position of the light
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity); // Initialize the intensity to the received intensity
        _position = position;
    }

    //--------------------------------------------------- GETTERS ---------------------------------------------------//

    /**
     * Calculate and return the intensity light on specific point
     *
     * @param p The point on the object (Point3D)
     * @return The intensity (Color)
     */
    @Override
    public Color getIntensity(Point3D p) {
        // The intensity of the color of the light
        // (the distribution of the light in the surface area)
        // is proportional to squared distance

        //Calculate the denominator of the proportion
        double distance = _position.distance(p);
        double denominator = kC + kL * distance + kQ * distance * distance;

        // return the final intensity
        return getIntensity().scale(1 / denominator);
    }

    /**
     * Return the normalize direction vector from the light source to the object
     *
     * @param p The point on the object (Point3D)
     * @return The normalize direction vector from the light source to the object (Vector)
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }

    /**
     * Calculate the distance between the light source and the receiving point
     *
     * @param point The point to calculate the distance to
     * @return The distance between the light source and the receiving point
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

    /**
     * Set the position point (the source light position)
     *
     * @param position The position point
     * @return this (PointLight)
     */
    public PointLight setPosition(Point3D position) {
        _position = position;

        // return this for chaining
        return this;
    }

    //--------------------------------------------------- SETTERS ---------------------------------------------------//

    /**
     * Set the specular attenuation factor
     *
     * @param kC The specular attenuation factor
     * @return this (PointLight)
     */
    public PointLight setKc(double kC) {
        this.kC = kC;

        // return this for chaining
        return this;
    }

    /**
     * Set the light source attenuation factor
     *
     * @param kL The light source attenuation factor
     * @return this (PointLight)
     */
    public PointLight setKl(double kL) {
        this.kL = kL;

        // return this for chaining
        return this;

    }

    /**
     * Set the energy attenuation factor
     *
     * @param kQ The attenuation factor
     * @return this (PointLight)
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;

        // return this for chaining
        return this;
    }
}
