package renderer;


import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import primitives.*;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {
    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, -1)) //
            .setDistance(100) //
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
                .setScene(scene) //
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
        // This code is for the specific xml file from the moodle!
        String docName = "basicRenderTestTwoColors.xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(docName);


            //getting the background color
            NodeList sceneNode = doc.getElementsByTagName("scene");
            Element element =(Element) sceneNode.item(0);
            String[] backgroundParams =  element.getAttribute("background-color").split(" ");
            Color backgroundColor = new Color(Double.parseDouble(backgroundParams[0]) ,
                    Double.parseDouble(backgroundParams[1]) ,
                    Double.parseDouble(backgroundParams[2]));
            scene1.setBackground(backgroundColor);


            //getting the ambient light color
           NodeList ambientlightNode = doc.getElementsByTagName("ambient-light");
           Element ambientlightElement = (Element) ambientlightNode.item(0);
            String[] ambientlightParams = ambientlightElement.getAttribute("color").split(" ");
           Color ambientLightColor = new Color(Double.parseDouble(ambientlightParams[0]) ,
                   Double.parseDouble(ambientlightParams[1]) ,
                   Double.parseDouble(ambientlightParams[2]));
            AmbientLight ambientLight = new AmbientLight(ambientLightColor, 1);
            scene1.setAmbientLight(ambientLight);

            // get the geometries
            // sphere
            NodeList spheres = doc.getElementsByTagName("sphere");
            Element spheresElement = (Element) spheres.item(0);
            String[] spheresCenter = spheresElement.getAttribute("center").split(" ");
            String[] spheresRadius = spheresElement.getAttribute("radius").split(" ");
            Point3D center = new Point3D(
                    Double.parseDouble(spheresCenter[0]) ,
                    Double.parseDouble(spheresCenter[1]) ,
                    Double.parseDouble(spheresCenter[2])
            );
            Sphere sphere = new Sphere(center, Double.parseDouble(spheresRadius[0]));
            scene1._geometries.add(sphere);

            //triangles
            NodeList triangles = doc.getElementsByTagName("triangle");
            for (int i = 0; i< triangles.getLength(); i++) {
                Element triangleElement = (Element) triangles.item(i);
                String[] p0Arr = triangleElement.getAttribute("p0").split(" ");
                String[] p1Arr = triangleElement.getAttribute("p1").split(" ");
                String[] p2Arr = triangleElement.getAttribute("p2").split(" ");

                Point3D p0 = new Point3D(
                        Double.parseDouble(p0Arr[0]) ,
                        Double.parseDouble(p0Arr[1]) ,
                        Double.parseDouble(p0Arr[2])
                );

                Point3D p1 = new Point3D(
                        Double.parseDouble(p1Arr[0]) ,
                        Double.parseDouble(p1Arr[1]) ,
                        Double.parseDouble(p1Arr[2])
                );

                Point3D p2 = new Point3D(
                        Double.parseDouble(p2Arr[0]) ,
                        Double.parseDouble(p2Arr[1]) ,
                        Double.parseDouble(p2Arr[2])
                );

                Triangle triangle = new Triangle(p0, p1, p2);
                scene1._geometries.add(triangle);

            }

        }catch (Exception e) {
            System.out.println("busted");
            e.printStackTrace();
        }


        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setScene(scene1) //
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

        scene._geometries.add(new Sphere(50, new Point3D(0, 0, -100)), //
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
                .setScene(scene) //
                .setCamera(camera)//
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }

}
