package lights;

import elements.*;
import geometries.Geometry;
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

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1))  //
            .setViewPlaneSize(150, 150) //
            .setViewPlaneDistance(1000);
    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
            .setViewPlaneSize(200, 200) //
            .setViewPlaneDistance(1000);

    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
    private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 50) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(100));

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1._geometries.add(sphere);
        scene1._lightSourceList.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracerBase(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1._geometries.add(sphere);
        scene1._lightSourceList.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50))//
                .setKl(0.00001).setKq(0.000001));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracerBase(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1._geometries.add(sphere);
        scene1._lightSourceList.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2)) //
                .setKl(0.00001).setKq(0.00000001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracerBase(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2._geometries.add(triangle1.setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.8).setKs(0.2).setNShininess(300)));
        scene2._lightSourceList.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracerBase(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2._geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(300)));
        scene2._lightSourceList.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
                .setKl(0.0005).setKq(0.0005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracerBase(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2._geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(300)));
        scene2._lightSourceList.add(new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1)) //
                .setKl(0.0001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracerBase(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a one sphere lighted by a directional light, PointLight and spot light.
     */
    @Test
    public void sphereIntegrationTest (){
        scene1._geometries.add(sphere);
        DirectionalLight directionalLight = new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1));
        PointLight pointLight = new PointLight(new Color(500, 300, 0), new Point3D(-300, 200, 100))//
                .setKl(0.00001).setKq(0.000001);
        SpotLight spotLight = new SpotLight(new Color(500, 300, 0), new Point3D(250, 200, 100), new Vector(-1, -1, -1)) //
                .setKl(0.0000001).setKq(0.0000001);

        scene1._lightSourceList.add(directionalLight);
        scene1._lightSourceList.add(pointLight);
        scene1._lightSourceList.add(spotLight);

        ImageWriter imageWriter = new ImageWriter("sphereIntegrationTest", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracerBase(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light, PointLight and spot light.
     */
    @Test
    public void twoTrianglesIntegrationTest (){
        scene2._geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(300)));

        DirectionalLight directionalLight = new DirectionalLight(new Color(250, 170, 170), new Vector(5, 1, -10));
        PointLight pointLight = new PointLight(new Color(150, 150, 0), new Point3D(-11, 40, -80))//
                .setKl(0.00000001).setKq(0.00000001);
        SpotLight spotLight = new SpotLight(new Color(250, 150, 0), new Point3D(10, -70, -130), new Vector(-1, -5, -7)) //
                .setKl(0.0000001).setKq(0.0000001);

       scene2._lightSourceList.add(directionalLight);
        scene2._lightSourceList.add(pointLight);
        scene2._lightSourceList.add(spotLight);

        ImageWriter imageWriter = new ImageWriter("twoTrianglesIntegrationTest", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracerBase(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

}

