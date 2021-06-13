package lights;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {
    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
            .setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleInitial() {
        scene._geometries.add( //
                new Sphere( new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)), //
                new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)) //
        );
        scene._lightSourceList.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene._geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setNShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setNShininess(60)), //
                new Sphere( new Point3D(0, 0, -115),30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)) //
        );
        scene._lightSourceList.add( //
                new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade (moving the triangle forward)
     */
    @Test
    public void shadedSphereByTriangleTest1() {
        scene._geometries.add( //
                new Sphere( new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)), //
                new Triangle(new Point3D(-50, -20, 0), new Point3D(-20, -50, 0), new Point3D(-48, -48, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)) //
        );
        scene._lightSourceList.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadedSphereByTriangleTest1", 400, 400)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade (moving the triangle a little bit forward)
     */
    @Test
    public void shadedSphereByTriangleTest2() {
        scene._geometries.add( //
                new Sphere( new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)), //
                new Triangle(new Point3D(-62, -32, 0), new Point3D(-32, -62, 0), new Point3D(-60, -60, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)) //
        );
        scene._lightSourceList.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadedSphereByTriangleTest2", 400, 400)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade (moving the light forward)
     */
    @Test
    public void shadedSphereByTriangleTest3() {
        scene._geometries.add( //
                new Sphere( new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)), //
                new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)) //
        );
        scene._lightSourceList.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-77, -77, 70), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadedSphereByTriangleTest3", 400, 400)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade (moving the light forward)
     */
    @Test
    public void shadedSphereByTriangleTest4() {
        scene._geometries.add( //
                new Sphere( new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)), //
                new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)) //
        );
        scene._lightSourceList.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-90, -90, 125), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadedSphereByTriangleTest4", 400, 400)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }
}
