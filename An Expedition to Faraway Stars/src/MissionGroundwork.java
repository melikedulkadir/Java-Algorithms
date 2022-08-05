import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionGroundwork {

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     *
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        // TODO: YOUR CODE HERE

        for (Project project : projectList) {
            project.printSchedule(project.getEarliestSchedule());
        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        // TODO: YOUR CODE HERE
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));

            NodeList nodeList = document.getElementsByTagName("Project");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node projectNode = nodeList.item(i);
                List<Task> tasks = new ArrayList<>();

                // Get the value of the Name
                String projectName = ((Element) projectNode).getElementsByTagName("Name")
                        .item(0).getChildNodes().item(0).getNodeValue();

                NodeList taskList = ((Element) projectNode).getElementsByTagName("Task");
                for (int j = 0; j < taskList.getLength(); j++) {
                    List<Integer> dependencies = new ArrayList<>();
                    Node node = taskList.item(j);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;

                        // Get the value of the TaskID.
                        int id = Integer.parseInt(elem.getElementsByTagName("TaskID")
                                .item(0).getChildNodes().item(0).getNodeValue());

                        // Get the value of the Description.
                        String description = elem.getElementsByTagName("Description")
                                .item(0).getChildNodes().item(0).getNodeValue();

                        // Get the value of the Duration.
                        int duration = Integer.parseInt(elem.getElementsByTagName("Duration")
                                .item(0).getChildNodes().item(0).getNodeValue());

                        NodeList dependency = (elem.getElementsByTagName("DependsOnTaskID"));
                        // Adding all dependencies to dependencies list
                        for (int k = 0; k < dependency.getLength(); k++) {
                            dependencies.add(Integer.parseInt(elem.getElementsByTagName("DependsOnTaskID")
                                    .item(k).getChildNodes().item(0).getNodeValue()));
                        }

                        // Creating new Task
                        Task task = new Task(id, description, duration, dependencies);
                        tasks.add(task);
                    }
                }
                // Creating new Project
                Project project = new Project(projectName, tasks);
                projectList.add(project);
            }
        } catch (Exception exception) {
            return null;
        }
        return projectList;
    }


}
