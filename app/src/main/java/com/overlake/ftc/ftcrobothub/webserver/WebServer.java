package com.overlake.ftc.ftcrobothub.webserver;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class WebServer extends NanoHTTPD
{
    private Router router;
    private boolean isListening;

    public WebServer(int port)
    {
        super(port);
        this.router = new Router();
        isListening = false;
    }

    public Router getRouter() {
        return router;
    }

    public void listen() {
        try {
            this.start();
            isListening = true;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void stopListening() {
        this.stop();
        isListening = false;
    }

    public boolean isListening() {
        return this.isListening;
    }

    @Override
    public Response serve(IHTTPSession session) {
        RoutingPath path = new RoutingPath(session.getUri(), session.getMethod());
        RouteHandler handler = router.getHandler(path);
        return handler.handle(session);
    }
}
