import java.util.*;

public class SubspaceCommunicationNetwork {

    private List<SolarSystem> solarSystems;

    /**
     * Perform initializations regarding your implementation if necessary
     *
     * @param solarSystems a list of SolarSystem objects
     */
    public SubspaceCommunicationNetwork(List<SolarSystem> solarSystems) {
        // TODO: YOUR CODE HERE
        this.solarSystems = solarSystems;
    }

    // Function for finding min edge in the graph and returns index
    public int findMin(Graph graph, boolean[] marked, int i, Double current_min, int index) {
        boolean isBefore = false;
        if (graph.adjList.get(i).size() != 0) {
            for (Double cost : graph.adjList.get(i)) {
                if (!Double.isNaN(cost)) {
                    if (cost < current_min) {
                        current_min = cost;
                        index = (graph.adjList.get(i).indexOf(current_min)) + i + 1;
                    }
                }
            }
            for (int j = 1; j < i; j++) {
                if (!Double.isNaN(graph.adjList.get(j).get(i - j - 1)) && graph.adjList.get(j).get(i - j - 1) < current_min) {
                    current_min = graph.adjList.get(j).get(i - j - 1);
                    index = j;
                    isBefore = true;
                }
            }
            if (current_min.equals(Constants.SUBSPACE_COMMUNICATION_CONSTANT)) {
                return index;
            }

            // If min edge's vertices marked true it creates a cycles. So we should pass this edge.
            if (marked[i] && marked[(index)]) {
                if (isBefore) graph.adjList.get(index).set(i - index - 1, Double.NaN);
                else graph.adjList.get(i).set(graph.adjList.get(i).indexOf(current_min), Double.NaN);
                return findMin(graph, marked, i, Constants.SUBSPACE_COMMUNICATION_CONSTANT, index);
            } else return index;
        }
        return index;
    }

    /**
     * Using the solar systems of the network, generates a list of HyperChannel objects that constitute the minimum cost communication network.
     *
     * @return A list HyperChannel objects that constitute the minimum cost communication network.
     */
    public List<HyperChannel> getMinimumCostCommunicationNetwork() {

        // TODO: YOUR CODE HERE
        List<HyperChannel> minimumCostCommunicationNetwork = new ArrayList<>();
        List<Planet> maxTechLevelPlanets = new ArrayList<>();

        // Adding all planets which has max technology level into maxTechLevelPlanets list
        for (SolarSystem solarSystem : solarSystems) {
            solarSystem.getPlanets().sort(Comparator.comparing(Planet::getTechnologyLevel).reversed());
            maxTechLevelPlanets.add(solarSystem.getPlanets().get(0));
        }

        Graph graph = new Graph(maxTechLevelPlanets.size());

        int start = 1;
        double cost = 0.0;
        double average = 0.0;

        // Creating new edges with index of planets and cost values
        for (Planet maxTechLevelPlanet : maxTechLevelPlanets) {
            for (int i = start; i < maxTechLevelPlanets.size(); i++) {
                average = (maxTechLevelPlanet.getTechnologyLevel() + (maxTechLevelPlanets.get(i).getTechnologyLevel())) / (double) 2;
                cost = Constants.SUBSPACE_COMMUNICATION_CONSTANT / average;
                graph.addingEdge(maxTechLevelPlanets.indexOf(maxTechLevelPlanet), cost);
            }
            start++;
        }

        List<Integer> addedVertices = new ArrayList<>();
        addedVertices.add(0);

        boolean[] marked = new boolean[maxTechLevelPlanets.size()];
        int last = 0;
        double current_min = 0.0;
        int current_index = 0;

        // Find all max edges with Prims lazy algorithm
        while (addedVertices.size() < graph.adjList.size()) {
            double min = Constants.SUBSPACE_COMMUNICATION_CONSTANT;
            for (int i : addedVertices) {
                current_index = findMin(graph, marked, i, Constants.SUBSPACE_COMMUNICATION_CONSTANT, current_index);
                if (current_index < i && current_index != 0)
                    current_min = graph.adjList.get(current_index).get(i - current_index - 1);
                else if (current_index > i && current_index != 0)
                    current_min = graph.adjList.get(i).get(current_index - i - 1);
                else continue;
                if (current_min < min) {
                    min = current_min;
                    last = i;
                }
            }

            // Create new hyperChannel with max edge vertices and add to minimumCostCommunicationNetwork list
            HyperChannel hyperChannel = new HyperChannel(maxTechLevelPlanets.get(current_index), maxTechLevelPlanets.get(last), min);
            minimumCostCommunicationNetwork.add(hyperChannel);

            // Mark visited vertex true
            marked[last] = true;
            marked[current_index] = true;

            // Make added edge null
            if (current_index < last && current_index != 0) {
                graph.adjList.get(current_index).set(last - current_index - 1, Double.NaN);
                current_index = last;
            } else if (current_index > last) graph.adjList.get(last).set(current_index - last - 1, Double.NaN);

            addedVertices.add(current_index);
        }

        return minimumCostCommunicationNetwork;
    }

    public void printMinimumCostCommunicationNetwork(List<HyperChannel> network) {
        Double sum = 0.0;
        for (HyperChannel channel : network) {
            Planet[] planets = {channel.getFrom(), channel.getTo()};
            Arrays.sort(planets);
            System.out.printf(Locale.ENGLISH, "Hyperchannel between %s - %s with cost %f", planets[0], planets[1], channel.getWeight());
            System.out.println();
            sum += channel.getWeight();
        }
        System.out.printf(Locale.ENGLISH, "The total cost of the subspace communication network is %f.", sum);
        System.out.println();
    }
}
