package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represent point light
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    private double kC = 1d, kL = 0d, kQ = 0d;


    /**
     * c-tor initialize all the fields
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * calculate and return the intensity light on specific point
     *
     * @param p the point on the object (Point3D)
     * @return the intensity (Color)
     */
    @Override
    public Color getIntensity(Point3D p) {
        double distance = _position.distance(p);
        double denominator = kC + kL * distance + kQ * distance * distance;
        return getIntensity().scale(1 / denominator);
    }

    /**
     * return the normalize direction vector from the light source to the object
     *
     * @param p the point on the object (Point3D)
     * @return the normalize direction vector from the light source to the object (Vector)
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }

    /**
     * Calculate the distance between the light source and the receiving point
     * @param point the point to calculate the distance to
     * @return the distance between the light source and the receiving point
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
        return this;
    }

    /**
     * Set the specular attenuation factor
     *
     * @param kC The specular attenuation factor
     * @return this (PointLight)
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
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
        return this;

    }

    /**
     * Attenuation factor
     *
     * @param kQ the attenuation factor
     * @return this (PointLight)
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }


}
