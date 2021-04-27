package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

public class Render {

    ImageWriter _imageWriter;
    Scene _scene;
    Camera _camera;
    RayTracerBase _rayTracerBase;

    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setScene(Scene scene) {
        _scene = scene;
        return this;

    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;

    }

    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
        _rayTracerBase = rayTracerBase;
        return this;
    }

    public void renderImage() {
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
        //throw new UnsupportedOperationException();

        for (int i = 0; i< _imageWriter.getNx(); i++){
            for (int j = 0; j< _imageWriter.getNy(); j++){
                Ray ray = _camera.constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), j,i);
                Color color = _rayTracerBase.traceRay(ray);
                _imageWriter.writePixel(i,j,color);
            }
        }

    }

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
        _imageWriter.writeToImage();
    }

    public void writeToImage(){
        if (_imageWriter == null) {
            throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
        }

        _imageWriter.writeToImage();
    }
}
