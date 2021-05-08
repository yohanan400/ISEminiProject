package elements;

import primitives.Color;

/**
 * represent the ambient light and his impact on specific pixel/ point
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class AmbientLight extends Light {

    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * c-tor calculate and return the intensity
     *
     * @param iA the light intensity on specific pixel/ point (Color)
     * @param kA the light intensity of the ambient light (double)
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }

}
