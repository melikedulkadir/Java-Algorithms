import java.util.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLParser {
    /**
     * TODO: Parse the input XML file and return a dictionary as described in the assignment insturctions
     *
     * @param filename the input XML file
     * @return a dictionary as described in the assignment insturctions
     */
    public static Map<String, Malware> parse(String filename) {
        // TODO: YOUR CODE HERE
        try {
            Map<String, Malware> malwareDB = new HashMap<String, Malware>();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load the input XML document, parse it and return an instance of the
            // Document class.
            Document document = builder.parse(new File(filename));

            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    // Get the value of the title.
                    String title = elem.getElementsByTagName("title")
                            .item(0).getChildNodes().item(0).getNodeValue();

                    // Get the value of the hash.
                    String hash = elem.getElementsByTagName("hash")
                            .item(0).getChildNodes().item(0).getNodeValue();

                    // Get the value of the level.
                    int level = Integer.parseInt(elem.getElementsByTagName("level")
                            .item(0).getChildNodes().item(0).getNodeValue());

                    malwareDB.put(hash, new Malware(title, level, hash));
                }
            }
            return malwareDB;
        }
        catch (Exception exception){
            return null;
        }

    }
}
