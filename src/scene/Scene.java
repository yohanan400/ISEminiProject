package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * combine all the attributes of the scene
 */
public class Scene {

    public final String _name;
    public Color _background = Color.BLACK;
    public AmbientLight _ambientLight = new AmbientLight(_background, 0);
    public Geometries _geometries;

    /**
     * set the name and initialize the geometries list of the scene
     * @param name the name of the scene (String)
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }

    /**
     * set the background of the scene
     * @param background the background of the scene (Color)
     * @return The current scene (Scene)
     */
    public Scene setBackground(Color background) {
        _background = background;
        return this;
    }

    /**
     * set the ambient light of the scene
     * @param ambientLight the ambient light (AmbientLight)
     * @return The current scene (Scene)
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }
}
