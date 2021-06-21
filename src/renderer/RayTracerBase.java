package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * An abstract class responsible to ray tracer
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public abstract class RayTracerBase {

    /**
     * The scene object to trace after the ray intersect his geometries objects
     */
    protected Scene _scene;

    /**
     * c-tor, initiate the _scene field with the receiving value
     *
     * @param scene The scene (Scene)
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * Trace the ray and calculate the color of the intersection point
     * of the ray and any object (or the background if no intersections exist)
     *
     * @param ray The ray to trace after (Ray)
     * @return The color of the intersection point (or background if no intersections exist) (Color)
     */
    public abstract Color traceRay(Ray ray);
}
