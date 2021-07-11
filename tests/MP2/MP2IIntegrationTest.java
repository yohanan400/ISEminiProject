package MP2;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
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
                .setViewPlaneDistance(1000)
                .setViewPlaneSize(800, 800)
                .setDOF(true)
                .setApertureRadiusSize(10)
                .setFocalDistance(new Point3D(0, 0, 1000).distance(new Point3D(0,1,100)));

        Scene scene = new Scene("Final picture");
        scene._geometries.add(

                new Polygon(new Point3D(200,200,1), new Point3D(-200,200,1),
                        new Point3D(-200,-200,1), new Point3D(200,-200,1))
                .setEmission(new Color(java.awt.Color.green))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(50)),

                new Polygon(new Point3D(200,-200,1), new Point3D(-200,-200,1),
                        new Point3D(-250,-250,250), new Point3D(250,-250,250))
                        .setEmission(new Color(java.awt.Color.blue))
                        .setMaterial(new Material().setKd(0.1).setKs(0.1).setNShininess(80)),

                new Polygon(new Point3D(240, 250,200), new Point3D(200,200,0),
                        new Point3D(200,-200,0), new Point3D(250,-250,250))
                        .setEmission(new Color(java.awt.Color.pink))
                        .setMaterial(new Material().setKd(0.9).setKs(0.1).setNShininess(10)),

                new Polygon(new Point3D(-240,250,200), new Point3D(-200,200,0),
                         new Point3D(-200,-200,0),new Point3D(-250,-250,250))
                        .setEmission(new Color(java.awt.Color.white)),

                new Polygon(new Point3D(240,250,200), new Point3D(200,200,1),
                        new Point3D(-200,200,1),new Point3D(-240,250,200))
                        .setEmission(new Color(java.awt.Color.gray)),

                new Sphere(new Point3D(-250,1,250), 20),
                new Sphere(new Point3D(-200,1,220), 20),
                new Sphere(new Point3D(-150,1,190), 20),
                new Sphere(new Point3D(-100,1,160), 20),
                new Sphere(new Point3D(-50,1,130), 20),
                new Sphere(new Point3D(0,1,100), 20),
                new Sphere(new Point3D(50,1,70), 20),
                new Sphere(new Point3D(100,1,40), 20),
                new Sphere(new Point3D(150,1,20), 20),
                new Sphere(new Point3D(200,1,10), 20),
                new Sphere(new Point3D(250,1,1), 20)

        );

        scene._lightSourceList.add(new DirectionalLight(new Color(java.awt.Color.white), new Vector(-1,0,0)));
      //  scene._lightSourceList.add(new PointLight(new Color(150,240,5), new Point3D(0,1,150)).setKc(0.001).setKl(0.0001));


        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.blue), 0.15));
        ImageWriter imageWriter = new ImageWriter("finalPicture7", 600, 600);


        Render render = new Render()
//                .setSuperSampling(true)
                .setAdaptiveGrid(true)
                .setCamera(camera)
                .setRayTracerBase(new RayTracerBasic(scene))
                .setImageWriter(imageWriter)
                .setMultithreading(0);

        render.renderImage();
        render.writeToImage();
    }
}
