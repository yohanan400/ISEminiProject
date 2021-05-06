package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represent directional light
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector _direction;

    /**
     * c-tor initialize the intensity filed
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction;
    }

    /**
     * get the intensity light on specific point
     * @param p the point on the object (Point3D)
     * @return the intensity (Color)
     */
    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity();
    }

    /**
     * return the normalize direction vector from the light source to the object
     * @param p the point on the object (Point3D)
     * @return the normalize direction vector from the light source to the object (Vector)
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
