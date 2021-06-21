package MP1;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class DepthOfFiledTests {

    private Scene scene = new Scene("Test scene");

    @Test
    public void depthOfFieldTest() {
        Camera camera = new Camera(new Point3D(0, 0, 2000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                .setViewPlaneSize(200, 200)
                .setViewPlaneDistance(800)
                .setDOF(true)
                .setApertureRadiusSize(5)
                .setFocalDistance(new Point3D(0, 0, 2000).distance(new Point3D(20, 10, 1500)));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene._geometries.add( //
                new Plane(new Point3D(0,-20,0), new Vector(0,1,0)).setEmission(new Color(java.awt.Color.green)),
                new Sphere( new Point3D(-10, 3, 1000), 10) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6)),
                new Sphere( new Point3D(20, 10, 1500), 10) //
                        .setEmission(new Color(java.awt.Color.red)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6))
        );

        scene._lightSourceList.add(new SpotLight(new Color(700, 400, 400), new Point3D(30, 25, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene._lightSourceList.add(new PointLight(new Color(160,80,240), new Point3D(-100, -100, 100))//
                .setKl(0.00000000001).setKq(0.0000000001));

        ImageWriter imageWriter = new ImageWriter("depthOfFieldTest10", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene))
                .setMultithreading(0)
                .setDebugPrint()
                .setAdaptiveGrid(true)
                .setSuperSampling(false);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void depthOfField2Test() {
        Camera camera = new Camera(new Point3D(0, 0, 2000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                .setViewPlaneSize(200, 200)
                .setViewPlaneDistance(1000)
                .setDOF(true)
                .setApertureRadiusSize(20)
                .setFocalDistance(new Point3D(0, 0, 2000).distance(new Point3D(60, 80, -20)));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene._geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)), //
                new Sphere(new Point3D(60, 50, -50), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6)),
                new Sphere(new Point3D(-60, -50, 100), 30) //
                        .setEmission(new Color(java.awt.Color.red)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.4)),
                new Sphere(new Point3D(-60, 0, 100), 20) //
                        .setEmission(new Color(java.awt.Color.red)) //
                        .setMaterial(new Material().setKd(0.02).setKs(0.2).setNShininess(30).setKt(0.4)),
                new Sphere(new Point3D(-60, 50, 100), 10) //
                        .setEmission(new Color(java.awt.Color.green)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(2).setKt(0.8))
//                , new Polygon(new Point3D(-80,-50,200), new Point3D(-20,2,200),  new Point3D(-20,20,200), new Point3D(-80,50,200))
//                        .setEmission(new Color(java.awt.Color.yellow))
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6))

        );

        scene._lightSourceList.add(new SpotLight(new Color(700, 400, 400), new Point3D(30, 25, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene._lightSourceList.add(new PointLight(new Color(160, 80, 240), new Point3D(-100, -100, 100))//
                .setKl(0.00000001).setKq(0.0000001));

        ImageWriter imageWriter = new ImageWriter("depthOfFieldTest9", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene))
                .setMultithreading(0)
                .setDebugPrint()
                .setAdaptiveGrid(true)
                .setSuperSampling(false);

        render.renderImage();
        render.writeToImage();


    }


}
