package elements;

import primitives.Color;

/**
 * An abstract class, represent the light
 */
abstract class Light {

    /**
     * The intensity of the color of the light
     */
    protected Color _intensity;

    /**
     * c-tor initialize the intensity filed
     *
     * @param intensity (Color)
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * Return the intensity of the light source
     *
     * @return The intensity of the light source (Color)
     */
    public Color getIntensity() {
        return _intensity;
    }
}
