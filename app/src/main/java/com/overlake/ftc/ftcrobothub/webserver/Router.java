package com.overlake.ftc.ftcrobothub.webserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Router {
    private Map<RoutingPath, RouteHandler> routingTable;

    public Router() {
        this.routingTable = new HashMap<RoutingPath, RouteHandler>();
    }

    public RouteHandler getHandler(RoutingPath route) {
        return routingTable.get(route);
    }

    public void addRoute(IRoute route) {
        route.setRoutes(routingTable, route);
    }
}
