import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Galaxy {

    private final List<Planet> planets;
    private List<SolarSystem> solarSystems;

    public Galaxy(List<Planet> planets) {
        this.planets = planets;
    }

    /**
     * Using the galaxy's list of Planet objects, explores all the solar systems in the galaxy.
     * Saves the result to the solarSystems instance variable and returns a shallow copy of it.
     *
     * @return List of SolarSystem objects.
     */
    public List<SolarSystem> exploreSolarSystems() {
        solarSystems = new ArrayList<>();
        // TODO: YOUR CODE HERE
        Graph graph = new Graph(planets.size());
        for (Planet planet : planets) {
            for (String neighborId : planet.getNeighbors()) {
                for (Planet neighbor_planet : planets) {
                    if (neighborId.equals(neighbor_planet.getId())) {
                        graph.addEdge(planets.indexOf(planet), planets.indexOf(neighbor_planet), true);
                    }
                }
            }
        }
        solarSystems.addAll(graph.connectedComponents(planets));
        return new ArrayList<>(solarSystems);
    }

    public List<SolarSystem> getSolarSystems() {
        return solarSystems;
    }

    // FOR TESTING
    public List<Planet> getPlanets() {
        return planets;
    }

    // FOR TESTING
    public int getSolarSystemIndexByPlanetID(String planetId) {
        for (int i = 0; i < solarSystems.size(); i++) {
            SolarSystem solarSystem = solarSystems.get(i);
            if (solarSystem.hasPlanet(planetId)) {
                return i;
            }
        }
        return -1;
    }

    public void printSolarSystems(List<SolarSystem> solarSystems) {
        System.out.printf("%d solar systems have been discovered.%n", solarSystems.size());
        for (int i = 0; i < solarSystems.size(); i++) {
            SolarSystem solarSystem = solarSystems.get(i);
            List<Planet> planets = new ArrayList<>(solarSystem.getPlanets());
            Collections.sort(planets);
            System.out.printf("Planets in Solar System %d: %s", i + 1, planets);
            System.out.println();
        }
    }
}
