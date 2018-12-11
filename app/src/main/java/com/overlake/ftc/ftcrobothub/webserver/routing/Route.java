package com.overlake.ftc.ftcrobothub.webserver.routing;

import com.google.gson.Gson;
import com.overlake.ftc.ftcrobothub.webserver.responses.ServerSideErrorResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;

public abstract class Route implements IRoute
{
    private String baseURI;

    public Route(String baseURI) {
        baseURI = baseURI.startsWith("/") ? baseURI : "/" + baseURI;
        this.baseURI = baseURI.endsWith("/") ? baseURI : baseURI + "/";
    }

    @Override
    public void setRoutes(Map<RoutingPath, RouteHandler> routingTable, IRoute route) {
        try {
            createRoutes(routingTable, route);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    protected void createRoutes(Map<RoutingPath, RouteHandler> routingTable, IRoute route) {
        Class<? extends IRoute> routeClass = route.getClass();
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
        URI = URI.endsWith("/") ? URI : URI + "/";
        URI = URI.startsWith("/") ? URI.substring(1) : URI;
        return baseURI.endsWith("/") ?
            baseURI + URI :
            baseURI + "/" + URI;
    }

    private RouteHandler createHandler(final Method method, final IRoute route) {
        return new RouteHandler() {
            @Override
            public Response handle(IHTTPSession session) {
                return invokeHandler(method, route, session);
            };
        };
    }

    private Response invokeHandler(Method method, IRoute route, IHTTPSession session) {
        try {
            return (Response)method.invoke(route, session);
        } catch (Exception e) {
            return ServerSideErrorResponse.getServerError(session.getUri(), getStackTrace(e));
        }
    }

    private String getStackTrace(Exception e) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(out));
        return new String(out.toByteArray());
    }

    protected <T> T parseBody(IHTTPSession session, T schema) {
        Gson gson = new Gson();
        String json =  getJsonBody(session);
        return (T)(gson.fromJson(json, schema.getClass()));
    }

    private String getJsonBody(IHTTPSession session) {
        try
        {
            InputStream input = session.getInputStream();
            Integer contentLength = Integer.parseInt(session.getHeaders().get("content-length"));
            byte[] buffer = new byte[contentLength];
            while (input.available() < contentLength) { }
            input.read(buffer, 0, contentLength);
            return new String(buffer);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
