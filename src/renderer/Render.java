package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

/**
 * Render class responsible to render the scene
 */
public class Render {

    ImageWriter _imageWriter = null;
    Scene _scene = null;
    Camera _camera = null;
    RayTracerBase _rayTracerBase = null;

    /**
     * set the image object
     *
     * @param imageWriter the image object (ImageWriter)
     * @return The current render object (Render)
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     * set the scene object
     *
     * @param scene the scene object (Scene)
     * @return The current render object (Render)
     */
    public Render setScene(Scene scene) {
        _scene = scene;
        return this;

    }

    /**
     * set the camera object
     *
     * @param camera the camera object (Camera)
     * @return The current render object (Render)
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;

    }

    /**
     * set the rayTracerBase object
     *
     * @param rayTracerBase the rayTracerBase object (RayTracerBase)
     * @return The current render object (Render)
     */
    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
        _rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * renderImage go through all the pixels and calculate their color and paint them
     */
    public void renderImage() {
        try {

            if (_imageWriter == null) {
                throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
            }

            if (_scene == null) {
                throw new MissingResourceException("Missing scene", "Scene", "String name");
            }

            if (_camera == null) {
                throw new MissingResourceException("Missing camera", "Camera", "Point3D p0/ Vector vUp/ Vector vTo");
            }

            if (_rayTracerBase == null) {
                throw new MissingResourceException("Missing rayTracerBase", "RayTracerBase", "Scene scene");
            }

            for (int i = 0; i < _imageWriter.getNy(); i++) {
                for (int j = 0; j < _imageWriter.getNx(); j++) {
                    Ray ray = _camera.constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), j, i);
                    Color color = _rayTracerBase.traceRay(ray);
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }catch (MissingResourceException e){ // if one of the objects are null then throw UnsupportedOperationException to the user
            throw new UnsupportedOperationException("No implement yet:" + e.getClassName());
        }
    }

    /**
     * print a grid on the image
     * @param interval the interval between all grid lines (int)
     * @param color the color of the lines (Color)
     */
    public void printGrid(int interval, Color color) {

        if (_imageWriter == null) {
            throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
        }

        for (int i = 0; i < _imageWriter.getNx(); i++) {
            for (int j = 0; j < _imageWriter.getNy(); j++) {
                if ((i % interval == 0) || (j % interval == 0))
                    _imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * produce to the image
     */
    public void writeToImage(){
        if (_imageWriter == null) {
            throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
        }

        _imageWriter.writeToImage();
    }
}
