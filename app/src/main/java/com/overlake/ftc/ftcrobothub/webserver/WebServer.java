package com.overlake.ftc.ftcrobothub.webserver;

import com.overlake.ftc.ftcrobothub.webserver.responses.NotFoundResponse;
import com.overlake.ftc.ftcrobothub.webserver.routing.NotFoundRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.RouteHandler;
import com.overlake.ftc.ftcrobothub.webserver.routing.Router;
import com.overlake.ftc.ftcrobothub.webserver.routing.RoutingPath;
import com.overlake.ftc.ftcrobothub.webserver.routing.StaticFilesRoute;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class WebServer extends NanoHTTPD
{
    private Router router;
    private boolean isListening;
    private NotFoundRoute notFoundRoute;

    public WebServer(int port)
    {
        super(port);
        this.router = new Router();
        isListening = false;
        notFoundRoute = getDefault404Route();
    }

    private NotFoundRoute getDefault404Route() {
        return new NotFoundRoute()
        {
            @Override
            public RouteHandler send404Error()
            {
                return new RouteHandler()
                {
                    @Override
                    public Response handle(IHTTPSession session)
                    {
                        return NotFoundResponse.getResponse(session.getUri());
                    }
                };
            }
        };
    }

    public Router getRouter() {
        return router;
    }

    public void setNotFoundRoute(NotFoundRoute route) {
        this.notFoundRoute = route;
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
        RouteHandler handler = getHandler(path);
        return handler.handle(session);
    }

    private RouteHandler getHandler(RoutingPath path) {
        if (router.containsRoute(path))
            return router.getHandler(path);
        else if (router.containsStaticRoute(path))
            return router.getHandler(new RoutingPath(getPublicPath(path), path.method));
        else
            return notFoundRoute.send404Error();
    }

    private String getPublicPath(RoutingPath path) {
        return StaticFilesRoute.getBaseURI() + path.uri;
    }
}
