package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

import java.util.List;

public class Scene {

    public final String _name;
    public Color _background = Color.BLACK;
    public AmbientLight _ambientLight = new AmbientLight(_background, 0);
    public Geometries _geometries;

    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }

    public Scene setBackground(Color background) {
        _background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }
}
