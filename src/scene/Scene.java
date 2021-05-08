package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.LinkedList;
import java.util.List;

/**
 * combine all the attributes of the scene
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Scene {

    public final String _name;
    public Color _background = Color.BLACK;
    public AmbientLight _ambientLight = new AmbientLight(_background, 0);
    public Geometries _geometries;
    public List<LightSource> _lightSourceList = new LinkedList<LightSource>();

    /**
     * set the name and initialize the geometries list of the scene
     *
     * @param name the name of the scene (String)
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }

    /**
     * Get the values of the scenes fields from .xml file
     *
     * @param fileName the .xml file name
     */
    public void getSceneFieldsFromXML(String fileName) {

        //get the file
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fileName);

            //getting the background color
            NodeList sceneNode = doc.getElementsByTagName("scene");
            Element element = (Element) sceneNode.item(0);
            String[] backgroundParams = element.getAttribute("background-color").split(" ");
            Color backgroundColor = new Color(Double.parseDouble(backgroundParams[0]),
                    Double.parseDouble(backgroundParams[1]),
                    Double.parseDouble(backgroundParams[2]));
            this.setBackground(backgroundColor);


            //getting the ambient light color
            NodeList ambientlightNode = doc.getElementsByTagName("ambient-light");
            Element ambientlightElement = (Element) ambientlightNode.item(0);
            String[] ambientlightParams = ambientlightElement.getAttribute("color").split(" ");
            Color ambientLightColor = new Color(Double.parseDouble(ambientlightParams[0]),
                    Double.parseDouble(ambientlightParams[1]),
                    Double.parseDouble(ambientlightParams[2]));
            AmbientLight ambientLight = new AmbientLight(ambientLightColor, 1);
            this.setAmbientLight(ambientLight);

            // collect the geometries
            //collect sphere
            NodeList spheres = doc.getElementsByTagName("sphere");
            for (int i = 0; i < spheres.getLength(); i++) {
                Element spheresElement = (Element) spheres.item(i);
                String[] spheresCenter = spheresElement.getAttribute("center").split(" ");
                String[] spheresRadius = spheresElement.getAttribute("radius").split(" ");
                Point3D center = new Point3D(
                        Double.parseDouble(spheresCenter[0]),
                        Double.parseDouble(spheresCenter[1]),
                        Double.parseDouble(spheresCenter[2])
                );
                Sphere sphere = new Sphere(center, Double.parseDouble(spheresRadius[0]));
                _geometries.add(sphere);
            }

            //collect triangles
            NodeList triangles = doc.getElementsByTagName("triangle");
            for (int i = 0; i < triangles.getLength(); i++) {
                Element triangleElement = (Element) triangles.item(i);
                String[] p0Arr = triangleElement.getAttribute("p0").split(" ");
                String[] p1Arr = triangleElement.getAttribute("p1").split(" ");
                String[] p2Arr = triangleElement.getAttribute("p2").split(" ");

                Point3D p0 = new Point3D(
                        Double.parseDouble(p0Arr[0]),
                        Double.parseDouble(p0Arr[1]),
                        Double.parseDouble(p0Arr[2])
                );

                Point3D p1 = new Point3D(
                        Double.parseDouble(p1Arr[0]),
                        Double.parseDouble(p1Arr[1]),
                        Double.parseDouble(p1Arr[2])
                );

                Point3D p2 = new Point3D(
                        Double.parseDouble(p2Arr[0]),
                        Double.parseDouble(p2Arr[1]),
                        Double.parseDouble(p2Arr[2])
                );

                Triangle triangle = new Triangle(p0, p1, p2);
                _geometries.add(triangle);

            }

            //collect planes
            //work just with the Plane(Point3D p0, Vector normal) c-tor
            //and not with public Plane(Point3D p0, Point3D p1, Point3D p2)
            NodeList planes = doc.getElementsByTagName("plane");
            for (int i = 0; i < planes.getLength(); i++) {
                Element planeElement = (Element) planes.item(i);
                String[] p0Arr = planeElement.getAttribute("p0").split(" ");
                String[] normalArr = planeElement.getAttribute("normal").split(" ");
                Point3D p0 = new Point3D(
                        Double.parseDouble(p0Arr[0]),
                        Double.parseDouble(p0Arr[1]),
                        Double.parseDouble(p0Arr[2])
                );
                Vector normal = new Vector(
                        Double.parseDouble(normalArr[0]),
                        Double.parseDouble(normalArr[1]),
                        Double.parseDouble(normalArr[2])
                );
                Plane plane = new Plane(p0, normal);
                _geometries.add(plane);
            }

        } catch (Exception e) {
            System.out.println("Something get wrong: ");
            e.printStackTrace();
        }
    }

    /**
     * set the background of the scene
     *
     * @param background the background of the scene (Color)
     * @return The current scene (Scene)
     */
    public Scene setBackground(Color background) {
        _background = background;
        return this;
    }

    /**
     * set the ambient light of the scene
     *
     * @param ambientLight the ambient light (AmbientLight)
     * @return The current scene (Scene)
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }

    /**
     * set the light source list of the scene
     *
     * @param lightSourceList the light source list (List<LightSource>)
     * @return the scene (Scene)
     */
    public Scene setLightSourceList(List<LightSource> lightSourceList) {
        _lightSourceList = lightSourceList;
        return this;
    }
}
