package elements;

import primitives.Color;

/**
 * represent the light
 */
abstract class Light {
    private Color _intensity;

    /**
     * c-tor initialize the intensity filed
     * @param intensity (Color)
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * return the intensity of the light source
     * @return the intensity of the light source (Color)
     */
    public Color getIntensity() {
        return _intensity;
    }
}
