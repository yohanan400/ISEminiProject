package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represent spot light
 */
public class SpotLight extends PointLight {
    private final Vector _direction;
    private double narrowness = 0d;

    /**
     * c-tor initialize all the fields
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * calculate and return the intensity light on specific point
     *
     * @param p the point on the object (Point3D)
     * @return the intensity (Color)
     */
    @Override
    public Color getIntensity(Point3D p) {
        double max = Math.max(0 , _direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(max);
    }

    /**
     * Set the specular attenuation factor
     *
     * @param kC The specular attenuation factor
     * @return this (SpotLight)
     */
    @Override
    public SpotLight setkC(double kC) {
        super.setkC(kC);
        return this;
    }

    /**
     * Set the light source attenuation factor
     *
     * @param kL The light source attenuation factor
     * @return this (SpotLight)
     */
    @Override
    public SpotLight setkL(double kL) {
        super.setkL(kL);
        return this;    }

    /**
     * Attenuation factor
     *
     * @param kQ the attenuation factor
     * @return this (SpotLight)
     */
    @Override
    public SpotLight setkQ(double kQ) {
        super.setkQ(kQ);
        return this;
    }
}
