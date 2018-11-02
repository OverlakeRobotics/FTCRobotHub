package com.overlake.ftc.ftcrobothub.webserver;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;

public abstract class Route {
    private String baseURI;

    public Route(String baseURI) {
        this.baseURI = baseURI;
    }

    public void setRoutes(Map<RoutingPath, RouteHandler> routingTable, Route route) {
        try {
            createRoutes(routingTable, route);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void createRoutes(Map<RoutingPath, RouteHandler> routingTable, Route route) {
        Class<? extends Route> routeClass = route.getClass();
        for (Method method : routeClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AddRoute.class)) {
                AddRoute routeData = method.getAnnotation(AddRoute.class);
                RoutingPath path = new RoutingPath(buildPath(routeData.uri()), routeData.method());
                RouteHandler handler = createHandler(method, route);
                routingTable.put(path, handler);
            }
        }
    }

    private String buildPath(String URI) {
        return baseURI.endsWith("/") ?
            baseURI + URI :
            baseURI + "/" + URI;
    }

    private RouteHandler createHandler(final Method method, final Route route) {
        return new RouteHandler() {
            @Override
            public Response handle(IHTTPSession session) {
                return invokeHandler(method, route, session);
            };
        };
    }

    private Response invokeHandler(Method method, Route route, IHTTPSession session) {
        try {
            return (Response)method.invoke(route, session);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
