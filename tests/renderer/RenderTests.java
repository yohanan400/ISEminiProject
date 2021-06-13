package renderer;


import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {
    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, -1)) //
            .setViewPlaneDistance(100) //
            .setViewPlaneSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {

        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene._geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down right

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() {
        Scene scene1 = new Scene("XML Test scene");

        scene1.getSceneFieldsFromXML("basicRenderTestTwoColors.xml");
        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene1));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //

        scene._geometries.add(new Sphere(new Point3D(0, 0, -100), 50d), //
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100))// up left
                        .setEmission(new Color(java.awt.Color.GREEN)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
                        .setEmission(new Color(java.awt.Color.RED)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
                        .setEmission(new Color(java.awt.Color.BLUE)));

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera)//
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }
}
