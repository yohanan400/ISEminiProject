package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represent point light
 */
public class PointLight extends Light implements LightSource {

    private Point3D _position;
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

    public void setPosition(Point3D position) {
        _position = position;
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;

    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
