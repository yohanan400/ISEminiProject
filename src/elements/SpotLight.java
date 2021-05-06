package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represent spot light
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /**
     * c-tor initialize all the fields
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light
     * @param kC
     * @param kL
     * @param kQ
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        _direction = direction.normalize();
    }

    /**
     * calculate and return the intensity light on specific point
     *
     * @param p the point on the object (Point3D)
     * @return the intensity (Color)
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0, _direction.dotProduct(getL(p))));
    }

    /**
     * return the normalize direction vector from the light source to the object
     *
     * @param p the point on the object (Point3D)
     * @return the normalize direction vector from the light source to the object (Vector)
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction.getHead().subtract(p).normalize();
    }
}
