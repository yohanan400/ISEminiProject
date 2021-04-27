package elements;

import primitives.Color;

public class AmbientLight {

    private Color _iA;
    private double _kA;
    private Color _intensity;

    public AmbientLight(Color iA,double kA ) {
        _iA = new Color(iA);
        _kA = kA;

        _intensity = _iA.scale(kA);
    }

    public Color getIntensity() {
        return _intensity;
    }
}
