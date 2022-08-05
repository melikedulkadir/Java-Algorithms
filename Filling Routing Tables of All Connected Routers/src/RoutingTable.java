import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RoutingTable implements Serializable {

    static final long serialVersionUID = 99L;
    private final Router router;
    private final Network network;
    private final List<RoutingTableEntry> entryList;

    public RoutingTable(Router router) {
        this.router = router;
        this.network = router.getNetwork();
        this.entryList = new ArrayList<>();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Router getRouter() {
        return router;
    }

    public Network getNetwork() {
        return network;
    }

    // Function for find the index in the dijkstra algorithm
    public int findIndex(double[] costValues, boolean[] visited) {
        double min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < costValues.length; i++) {
            if (costValues[i] < min && !visited[i]) {
                min = costValues[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * updateTable() should calculate routing information and then instantiate RoutingTableEntry objects, and finally add
     * them to RoutingTable object’s entryList.
     */
    public void updateTable() {
        // TODO: YOUR CODE HERE
        entryList.clear();
        for (Router router : network.getRouters()) {
            if (router.equals(this.router)) {
                continue;
            }
            RoutingTableEntry routingTableEntry = new RoutingTableEntry(this.router.getIpAddress(), router.getIpAddress(), pathTo(router));
            entryList.add(routingTableEntry);
        }
    }

    /**
     * pathTo(Router destination) should return a Stack<Link> object which contains a stack of Link objects,
     * which represents a valid path from the owner Router to the destination Router.
     *
     * @param destination Destination router
     * @return Stack of links on the path to the destination router
     */


    public Stack<Link> pathTo(Router destination) {
        // TODO: YOUR CODE
        Stack<Link> pathTo = new Stack<>();
        double[][] linkMatrix = new double[network.getRouters().size()][network.getRouters().size()];
        int totalRouter = network.getRouters().size();

        for (int i = 0; i < network.getLinks().size(); i++) {
            linkMatrix[network.getRouters().indexOf(network.getRouterWithIp(network.getLinks().get(i).getIpAddress1()))][network.getRouters().indexOf(network.getRouterWithIp(network.getLinks().get(i).getIpAddress2()))]
                    = network.getLinks().get(i).getCost();
            linkMatrix[network.getRouters().indexOf(network.getRouterWithIp(network.getLinks().get(i).getIpAddress2()))][network.getRouters().indexOf(network.getRouterWithIp(network.getLinks().get(i).getIpAddress1()))]
                    = network.getLinks().get(i).getCost();
        }

        // Array for keeping the distances from source router to other routers
        double[] distance = new double[totalRouter];
        // Array for keeping the shortest path arraylists from source to other routers
        ArrayList<Integer>[] paths = new ArrayList[totalRouter];

        boolean[] visited = new boolean[totalRouter];

        // Initializing the arrays with max values and not visited values(false)
        for (int i = 0; i < totalRouter; i++) {
            distance[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        // Distance from source to itself should be 0.
        distance[network.getRouters().indexOf(router)] = 0;

        paths[network.getRouters().indexOf(router)] = new ArrayList<>();
        paths[network.getRouters().indexOf(router)].add(network.getRouters().indexOf(router));

        for (int i = 0; i < totalRouter; i++) {
            int closestRouter = findIndex(distance, visited);
            if (closestRouter == -1) {
                break;
            }
            visited[closestRouter] = true;
            for (int j = 0; j < totalRouter; j++) {
                if (!visited[j]) {
                    // If there is a link between closest router and j linkMatrix cannot equal 0.
                    if (linkMatrix[closestRouter][j] != 0) {
                        double dist = (distance[closestRouter] + linkMatrix[closestRouter][j]);
                        if (dist < distance[j]) {
                            distance[j] = dist;
                            ArrayList<Integer> pathIndexes = new ArrayList<>(paths[closestRouter]);
                            pathIndexes.add(j);
                            paths[j] = pathIndexes;
                        }
                    }
                }
            }
        }

        //Adding paths to the stack
        if (paths[(network.getRouters().indexOf(destination))] != null) {
            for (int i = paths[(network.getRouters().indexOf(destination))].size() - 1; i > 0; i--) {
                String ipaddress1 = network.getRouters().get(paths[(network.getRouters().indexOf(destination))].get(i)).getIpAddress();
                String ipaddress2 = network.getRouters().get(paths[(network.getRouters().indexOf(destination))].get(i - 1)).getIpAddress();
                pathTo.add(network.getLinkBetweenRouters(ipaddress1, ipaddress2));
            }
        }

        return pathTo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingTable that = (RoutingTable) o;
        return router.equals(that.router) && entryList.equals(that.entryList);
    }

    public List<RoutingTableEntry> getEntryList() {
        return entryList;
    }
}
