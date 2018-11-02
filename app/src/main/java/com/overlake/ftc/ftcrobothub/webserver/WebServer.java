package com.overlake.ftc.ftcrobothub.webserver;

import com.overlake.ftc.ftcrobothub.routes.HomeRoute;

import fi.iki.elonen.NanoHTTPD;

public class WebServer extends NanoHTTPD
{
    private Router router;

    public WebServer(int port)
    {
        super(port);
        this.router = new Router();
    }

    public Router getRouter() {
        return router;
    }

    @Override
    public Response serve(IHTTPSession session) {
        RoutingPath path = new RoutingPath(session.getUri(), session.getMethod());
        RouteHandler handler = router.getHandler(path);
        Response res = handler.handle(session);
        return res;
    }
}
