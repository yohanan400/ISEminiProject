/**
 *
 */
package lights;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
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
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                .setViewPlaneSize(150, 150).setViewPlaneDistance(1000);

        scene._geometries.add( //
                new Sphere(new Point3D(0, 0, -50), 50) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setNShininess(100).setKt(0.3)),
                new Sphere(new Point3D(0, 0, -50), 25) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(100)));
        scene._lightSourceList.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                .setViewPlaneSize(2500, 2500).setViewPlaneDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene._geometries.add( //
                new Sphere(new Point3D(-950, -900, -1000), 400) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20).setKt(0.5)),
                new Sphere(new Point3D(-950, -900, -1000), 200) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));
        scene._lightSourceList.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                .setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene._geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)), //
                new Sphere(new Point3D(60, 50, -50), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6)));

        scene._lightSourceList.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of five objects lighted by a spot light and point light
     * to show all the effects in one picture
     */
    @Test
    public void reflectionRefractionFiveObjectsTest() {
        Camera camera = new Camera(new Point3D(0, 0, 2000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                .setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

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
                        .setEmission(new Color(java.awt.Color.yellow)) //
                        .setMaterial(new Material().setKd(0.002).setKs(0.2).setNShininess(30).setKt(0.9)),
                new Sphere(new Point3D(-60, 50, 100), 10) //
                        .setEmission(new Color(java.awt.Color.green)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(2).setKt(0.8)));

        scene._lightSourceList.add(new SpotLight(new Color(700, 400, 400), new Point3D(30, 25, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene._lightSourceList.add(new PointLight(new Color(160, 80, 240), new Point3D(-100, -100, 100))//
                .setKl(0.00000000001).setKq(0.0000000001));

        ImageWriter imageWriter = new ImageWriter("reflectionRefractionFiveObjectsTest", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene))
                .setSuperSampling(true)
                .setMultithreading(0);

        render.renderImage();
        render.writeToImage();
    }
}
