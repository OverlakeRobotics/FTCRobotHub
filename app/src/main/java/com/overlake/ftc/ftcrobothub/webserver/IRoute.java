package com.overlake.ftc.ftcrobothub.webserver;

import java.util.Map;

public interface IRoute
{
    void setRoutes(Map<RoutingPath, RouteHandler> routingTable, IRoute route);
}
