package com.overlake.ftc.ftcrobothub.webserver;

import com.overlake.ftc.ftcrobothub.routes.HomeRoute;

import java.io.IOException;

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

    public void listen() {
        try {
            this.start();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        RoutingPath path = new RoutingPath(session.getUri(), session.getMethod());
        RouteHandler handler = router.getHandler(path);
        return handler.handle(session);
    }
}
