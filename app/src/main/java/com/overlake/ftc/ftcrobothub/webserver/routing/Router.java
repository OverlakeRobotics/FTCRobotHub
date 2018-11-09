package com.overlake.ftc.ftcrobothub.webserver.routing;

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

    public void addRoute(IRoute route) {
        route.setRoutes(routingTable, route);
    }

    public boolean containsRoute(RoutingPath path) {
        return routingTable.containsKey(path);
    }

    public boolean containsStaticRoute(RoutingPath path) {
        if (StaticFilesRoute.getBaseURI() == null)
            return false;
        else
            return routingTable.containsKey(new RoutingPath(getStaticPath(path), path.method));
    }

    private String getStaticPath(RoutingPath path) {
        return StaticFilesRoute.getBaseURI() + path.uri;
    }
}
