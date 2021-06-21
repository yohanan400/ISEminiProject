package MP2;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import geometries.Plane;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class MP2IIntegrationTest {


    @Test
    public void finalPicture() {

        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1))
                .setViewPlaneDistance(100)
                .setViewPlaneSize(800, 800);

        Scene scene = new Scene("Final picture");
        scene._geometries.add(
//                new Plane(new Point3D(1, 1, 0), new Vector(1, 1, 0)).setEmission(new Color(java.awt.Color.green)), // floor
//                new Plane(new Point3D(-10, 0, 0), new Vector(0, 1, 1)).setEmission(new Color(java.awt.Color.red)), // back wall
//                new Plane(new Point3D(0, 10, 0), new Vector(1,0 , 1)).setEmission(new Color(java.awt.Color.blue)), //
//                new Plane(new Point3D(0, -10, 0), new Vector(1, 0, 1)).setEmission(new Color(java.awt.Color.pink)),
                new Polygon(new Point3D(10,10,0), new Point3D(-10,10,0), new Point3D(-10,-10,0), new Point3D(10,-10,0))
                .setEmission(new Color(java.awt.Color.green))
        );

        scene._lightSourceList.add(new DirectionalLight(new Color(java.awt.Color.white), new Vector(-1,0,0)));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.blue), 0.15));
        ImageWriter imageWriter = new ImageWriter("finalPicture1", 600, 600);


        Render render = new Render()
                .setSuperSampling(true)
                .setCamera(camera)
                .setRayTracerBase(new RayTracerBasic(scene))
                .setImageWriter(imageWriter);

        render.renderImage();
        render.writeToImage();
    }
}
