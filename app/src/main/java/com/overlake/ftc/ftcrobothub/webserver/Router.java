package com.overlake.ftc.ftcrobothub.webserver;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<RoutingPath, RouteHandler> routingTable;

    public Router() {
        this.routingTable = new HashMap<RoutingPath, RouteHandler>();
    }

    public RouteHandler getHandler(RoutingPath route) {
        return routingTable.get(route);
    }

    public void addRoute(Route route) {
        route.setRoutes(routingTable, route);
    }
}
