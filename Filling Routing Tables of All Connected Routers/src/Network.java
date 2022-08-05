import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Network implements Serializable {
    static final long serialVersionUID = 55L;
    private final List<Router> routers = new ArrayList<>();
    private final List<Link> links = new ArrayList<>();

    /**
     * The constructor should read the given file and generate necessary Router and Link objects and initialize link
     * and router arrays.
     * Also, you should implement Link class’s calculateAndSetCost() method in order for the costs to be calculated
     * based on the formula given in the instructions.
     *
     * @param filename Input file to generate the network from
     * @throws FileNotFoundException
     */
    public Network(String filename) throws FileNotFoundException {
        // TODO: YOUR CODE HERE
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String bandwidth = " ";
            Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.\\d{1,3}\\.\\d{1,3}");
            Pattern linkPattern = Pattern.compile("\\d{1,4} Mbps");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Matcher matcher = pattern.matcher(data);
                if (data.startsWith("Router")) {
                    if (matcher.find()) {
                        Router router = new Router(matcher.group(), this);
                        routers.add(router);
                    }
                } else if (data.startsWith("Link")) {
                    List<String> ipAddresses = new ArrayList<>();
                    while (matcher.find()) ipAddresses.add(matcher.group());
                    Matcher bandwidth_matcher = linkPattern.matcher(data);
                    if (bandwidth_matcher.find()) {
                        bandwidth = (bandwidth_matcher.group().split(" ")[0]);
                    }
                    Link link = new Link(ipAddresses.get(0), ipAddresses.get(1), Integer.parseInt(bandwidth));
                    links.add(link);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        updateAllRoutingTables();
    }

    /**
     * IP address of the router should be placed in group 1
     * Subnet of the router should be placed group 2
     *
     * @return regex for matching RouterIP lines
     */
    public static String routerRegularExpression() {
        // TODO: REGEX HERE
        String pattern = "([0-9]{1,3}\\.[0-9]{1,3}\\.(\\d{1,3})\\.\\d{1,3})";
        return pattern;
    }

    /**
     * IP address of the router 1 should be placed in group 1
     * IP address of the router 2 should be placed in group 2
     * Bandwidth of the link should be placed in group 3
     *
     * @return regex for matching Link lines
     */
    public static String linkRegularExpression() {
        // TODO: REGEX HERE
        String pattern = "([0-9]{1,3}\\.[0-9]{1,3}\\.\\d{1,3}\\.\\d{1,3})-([0-9]{1,3}\\.[0-9]{1,3}\\.\\d{1,3}\\.\\d{1,3}).*?(\\d{3,4})[^^0-9].*";
        return pattern;
    }

    public List<Router> getRouters() {
        return routers;
    }

    public List<Link> getLinks() {
        return links;
    }

    public List<RoutingTable> getRoutingTablesOfAllRouters() {
        if (routers != null) {
            List<RoutingTable> routingTableList = new ArrayList<>();
            for (Router router : routers)
                routingTableList.add(router.getRoutingTable());
            return routingTableList;
        }
        return null;
    }

    public Router getRouterWithIp(String ip) {
        if (routers != null) {
            for (Router router : routers) {
                if (router.getIpAddress().equals(ip))
                    return router;
            }
        }
        return null;
    }

    public Link getLinkBetweenRouters(String ipAddr1, String ipAddr2) {
        if (links != null) {
            for (Link link : links) {
                if (link.getIpAddress1().equals(ipAddr1) && link.getIpAddress2().equals(ipAddr2)
                        || link.getIpAddress1().equals(ipAddr2) && link.getIpAddress2().equals(ipAddr1))
                    return link;
            }
        }
        return null;
    }

    public List<Link> getLinksOfRouter(Router router) {
        List<Link> routersLinks = new ArrayList<>();
        if (links != null) {
            for (Link link : links) {
                if (link.getIpAddress1().equals(router.getIpAddress()) ||
                        link.getIpAddress2().equals(router.getIpAddress())) {
                    routersLinks.add(link);
                }
            }
        }
        return routersLinks;
    }

    public void updateAllRoutingTables() {
        for (Router router : getRouters()) {
            router.getRoutingTable().updateTable();
        }
    }

    /**
     * Changes the cost of the link with a new value, and update all routing tables.
     *
     * @param link    Link to update
     * @param newCost New link cost
     */
    public void changeLinkCost(Link link, double newCost) {
        // TODO: YOUR CODE HERE
        // Finding the link, updating the link and updating all routing tables
        for (Link linkItem : links) {
            if (linkItem.equals(link)) {
                linkItem.setCost(newCost);
                updateAllRoutingTables();
                break;
            }
        }
    }

    /**
     * Add a new Link to the Network, and update all routing tables.
     *
     * @param link Link to be added
     */
    public void addLink(Link link) {
        // TODO: YOUR CODE HERE
        links.add(link);
        updateAllRoutingTables();
    }

    /**
     * Remove a Link from the Network, and update all routing tables.
     *
     * @param link Link to be removed
     */
    public void removeLink(Link link) {
        // TODO: YOUR CODE HERE
        // Finding the link and remove it from links list
        for (Link linkItem : links) {
            if (linkItem.equals(link)) {
                links.remove(link);
                updateAllRoutingTables();
                break;
            }
        }
    }

    /**
     * Add a new Router to the Network, and update all routing tables.
     *
     * @param router Router to be added
     */
    public void addRouter(Router router) {
        // TODO: YOUR CODE HERE
        routers.add(router);
        updateAllRoutingTables();
    }

    /**
     * Remove a Router from the Network, and update all routing tables. Beware that removing a router also causes the
     * removal of any links connected to it from the Network. Also beware that a router which was removed should not
     * appear in any routing table entry.
     *
     * @param router Router to be removed
     */
    public void removeRouter(Router router) {
        // TODO: YOUR CODE HERE
        links.removeIf(link -> (link.getIpAddress1().equals(router.getIpAddress())) || (link.getIpAddress2().equals(router.getIpAddress())));
        routers.removeIf(routerItem -> routerItem.equals(router));
        updateAllRoutingTables();
    }

    /**
     * Change the state of the router (down or live), and update all routing tables. Beware that a router which is down
     * should not be reachable and should not appear in any routing table entry’s path. However, this router might appear
     * in other router’s routing-tables as a separate entry with a totalRouteCost=Infinity value because it was not
     * completely removed from the network.
     *
     * @param router Router to update
     * @param isDown New status of the router
     */
    public void changeStateOfRouter(Router router, boolean isDown) {
        // TODO: YOUR CODE HERE
        // Find the router and change its set down value, and if set down is true remove router's links from links list
        for (Router routerItem : routers) {
            if (routerItem.equals(router)) {
                routerItem.setDown(isDown);
                if (isDown) {
                    links.removeIf(link -> link.getIpAddress1().equals(routerItem.getIpAddress()) || link.getIpAddress2().equals(routerItem.getIpAddress()));
                }
                updateAllRoutingTables();
                break;
            }
        }
    }
}
