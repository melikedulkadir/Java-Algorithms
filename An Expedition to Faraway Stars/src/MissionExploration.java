import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionExploration {

    /**
     * Given a Galaxy object, prints the solar systems within that galaxy.
     * Uses exploreSolarSystems() and printSolarSystems() methods of the Galaxy object.
     *
     * @param galaxy a Galaxy object
     */
    public void printSolarSystems(Galaxy galaxy) {
        // TODO: YOUR CODE HERE
        List<SolarSystem> solarSystems = new ArrayList<>(galaxy.exploreSolarSystems());
        galaxy.printSolarSystems(solarSystems);
    }

    /**
     * TODO: Parse the input XML file and return a list of Planet objects.
     *
     * @param filename the input XML file
     * @return a list of Planet objects
     */
    public Galaxy readXML(String filename) {
        List<Planet> planetList = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));

            NodeList nodeList = document.getElementsByTagName("Planet");

            for (int j = 0; j < nodeList.getLength(); j++) {
                List<String> neighbor_planets = new ArrayList<>();
                Node node = nodeList.item(j);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    // Get the value of the ID
                    String id = elem.getElementsByTagName("ID")
                            .item(0).getChildNodes().item(0).getNodeValue();

                    // Get the value of the Technology Level
                    int technologyLevel = Integer.parseInt(elem.getElementsByTagName("TechnologyLevel")
                            .item(0).getChildNodes().item(0).getNodeValue());


                    NodeList neighbors = (elem.getElementsByTagName("PlanetID"));
                    // Adding all neighbor planets to neighbor_planets list
                    for (int k = 0; k < neighbors.getLength(); k++) {
                        neighbor_planets.add(elem.getElementsByTagName("PlanetID")
                                .item(k).getChildNodes().item(0).getNodeValue());
                    }
                    // Creating new Planet
                    Planet planet = new Planet(id, technologyLevel, neighbor_planets);
                    planetList.add(planet);
                }
            }
        } catch (Exception exception) {
            return null;
        }
        return new Galaxy(planetList);
    }
}
