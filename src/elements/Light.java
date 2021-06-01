package elements;

import primitives.Color;

/**
 * An abstract class, represent the light
 */
abstract class Light {
    protected Color _intensity; // The intensity of the color of the light

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
