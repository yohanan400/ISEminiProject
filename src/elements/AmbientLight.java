package elements;

import primitives.Color;

/**
 * represent the ambient light and his impact on specific pixel/ point
 */
public class AmbientLight {

    private Color _iA;
    private double _kA;
    private Color _intensity;

    /**
     *  c-tor calculate and return the intensity
     * @param iA the light intensity on specific pixel/ point (Color)
     * @param kA the light intensity of the ambient light (double)
     */
    public AmbientLight(Color iA,double kA ) {
        _iA = new Color(iA);
        _kA = kA;

        _intensity = _iA.scale(kA);
    }

    /**
     * return the intensity of the ambient light
     * @return the intensity (Color)
     */
    public Color getIntensity() {
        return _intensity;
    }
}
