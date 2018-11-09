package com.overlake.ftc.ftcrobothub.webserver.routing;

import java.util.Map;

public interface IRoute
{
    void setRoutes(Map<RoutingPath, RouteHandler> routingTable, IRoute route);
}
