import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Graph {

    private final int verticesNum;
    private final ArrayList<ArrayList<Integer>> adjacency;
    ArrayList<ArrayList<Double>> adjList;
    ArrayList<SolarSystem> solarSystems = new ArrayList<>();

    // Constructor with vertexNumber parameter
    Graph(int v) {
        verticesNum = v;
        adjacency = new ArrayList<>(v);
        adjList = new ArrayList<>(v);
        for (int i = 0; i < v; ++i) {
            adjacency.add(new ArrayList<>());
            adjList.add(new ArrayList<>());
        }
    }

    // Function for adding an edge into the graph with double variable
    void addingEdge(int vertex, double otherVertex) {
        adjList.get(vertex).add(otherVertex);
    }

    // Function for adding an edge into the graph
    void addEdge(int vertex, int otherVertex, boolean isUndirected) {
        adjacency.get(vertex).add(otherVertex);
        if (isUndirected) {
            adjacency.get(otherVertex).add(vertex);
        }
    }

    // Function make DFS for find connected components
    void DFS(int vertex, boolean[] visited, SolarSystem solarSystem, List<Planet> planets) {
        // Mark the current vertex as visited
        visited[vertex] = true;
        solarSystem.addPlanet(planets.get(vertex));

        for (int adjacencyVertex : adjacency.get(vertex)) {
            if (!visited[adjacencyVertex]) {
                DFS(adjacencyVertex, visited, solarSystem, planets);
            }
        }
    }

    // Function that returns a list of connected components Solar Systems
    List<SolarSystem> connectedComponents(List<Planet> planets) {
        // Creating boolean visited array with false values
        boolean[] visited = new boolean[verticesNum];
        for (int vertex = 0; vertex < verticesNum; ++vertex) {
            if (!visited[vertex]) {
                SolarSystem solarSystem = new SolarSystem();
                DFS(vertex, visited, solarSystem, planets);
                solarSystems.add(solarSystem);
            }
        }
        return solarSystems;
    }


    // Recursive function for find topological order
    void topologicalHelper(int v, boolean[] visited, List<Integer> storeValues) {

        // Mark the current vertex visited
        visited[v] = true;
        int adjVertex;
        for (int integer : adjacency.get(v)) {
            adjVertex = integer;
            if (!visited[adjVertex]) {
                topologicalHelper(adjVertex, visited, storeValues);
            }
        }
        storeValues.add(v);
    }

    // Function for find topological order
    public List<Integer> topologicalOrder() {

        List<Integer> topologicalOrder = new ArrayList<>();
        boolean[] visited = new boolean[verticesNum];

        for (int i = 0; i < verticesNum; i++)
            if (!visited[i]) {
                topologicalHelper(i, visited, topologicalOrder);
            }

        Collections.reverse(topologicalOrder);
        return topologicalOrder;
    }
}
