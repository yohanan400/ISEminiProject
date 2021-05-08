package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * an abstract class responsible on the Ray tracer
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public abstract class RayTracerBase {

    protected Scene _scene;

    /**
     * c-tor receive scene and set him in the variable
     *
     * @param scene scene (Scene)
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * method with trace the ray
     *
     * @param ray (Ray)
     * @return right color (Color)
     */
    public abstract Color traceRay(Ray ray);

}
