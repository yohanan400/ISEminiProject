package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.MissingResourceException;

/**
 * Render class responsible to render the scene
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Render {

    /**
     * The object responsible to make the jpeg after the render of the scene
     */
    ImageWriter _imageWriter = null;

    /**
     * The camera of the scene
     */
    Camera _camera = null;

    /**
     * The ray tracer object to trace after the rays from the camera to the scene objects
     */
    RayTracerBase _rayTracerBase = null;

    /**
     * Set the image object
     *
     * @param imageWriter The image object (ImageWriter)
     * @return this (Render)
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;

        // Return this for chaining
        return this;
    }

    /**
     * Set the camera object
     *
     * @param camera the camera object (Camera)
     * @return this (Render)
     */
    public Render setCamera(Camera camera) {
        _camera = camera;

        // return this for chaining
        return this;

    }

    /**
     * Set the rayTracerBase object
     *
     * @param rayTracerBase The rayTracerBase object (RayTracerBase)
     * @return this (Render)
     */
    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
        _rayTracerBase = rayTracerBase;

        // return this for chaining
        return this;
    }

    /**
     * Go through all the pixels and calculate their color and paint them
     */
    public void renderImage() {
        try {

            // Check if have a image writer object
            if (_imageWriter == null) {
                throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
            }

            // Check if have a camera object
            if (_camera == null) {
                throw new MissingResourceException("Missing camera", "Camera", "Point3D p0/ Vector vUp/ Vector vTo");
            }

            // Check if have ray tracer (base) object
            if (_rayTracerBase == null) {
                throw new MissingResourceException("Missing rayTracerBase", "RayTracerBase", "Scene scene");
            }

            // Go through the view plane to all pixels and paint them by calling to writePixel function
            for (int i = 0; i < _imageWriter.getNy(); i++) {
                for (int j = 0; j < _imageWriter.getNx(); j++) {
                    // Create the ray from the camera to the objects
                    Color color = Color.BLACK;
                    for (int k = 1; k<=100; k++) {
                        Ray ray = _camera.constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), j, i);
                        // Set the color of the current pixel
                        color = color.add(_rayTracerBase.traceRay(ray));
                    }
                    // Paint the pixel
                    _imageWriter.writePixel(j, i, color.reduce(100));
                }
            }
        } catch (MissingResourceException e) {
            // If one of the objects are null then throw UnsupportedOperationException to the user
            throw new UnsupportedOperationException("No implement yet:" + e.getClassName());
        }
    }

    /**
     * Print a grid on the image
     *
     * @param interval The interval between all grid lines (int)
     * @param color    The color of the lines (Color)
     */
    public void printGrid(int interval, Color color) {

        // Check if have a image writer object
        if (_imageWriter == null) {
            throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
        }

        // Create the grid
        for (int i = 0; i < _imageWriter.getNx(); i++) {
            for (int j = 0; j < _imageWriter.getNy(); j++) {
                if ((i % interval == 0) || (j % interval == 0))
                    // Paint the current pixel
                    _imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * Produce to the image
     */
    public void writeToImage() {

        // Check if have a image writer object
        if (_imageWriter == null) {
            throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
        }
        // Write to image
        _imageWriter.writeToImage();
    }
}
