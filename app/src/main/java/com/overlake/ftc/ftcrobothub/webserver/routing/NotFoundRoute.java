package com.overlake.ftc.ftcrobothub.webserver.routing;

import java.util.Map;

public abstract class NotFoundRoute implements IRoute
{
    public abstract RouteHandler send404Error();

    @Override
    public void setRoutes(Map<RoutingPath, RouteHandler> routingTable, IRoute route)
    {

    }
}
