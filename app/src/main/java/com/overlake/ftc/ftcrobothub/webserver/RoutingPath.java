package com.overlake.ftc.ftcrobothub.webserver;

import fi.iki.elonen.NanoHTTPD.Method;

public class RoutingPath {
    public String uri;
    public Method method;

    public RoutingPath(String uri, Method method) {
        this.uri = uri;
        this.method = method;
    }

    @Override
    public int hashCode() {
        return uri.hashCode() + method.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RoutingPath) {
            RoutingPath other = (RoutingPath)obj;
            return other.method == this.method &&
                    other.uri.equals(this.uri);

        }
        return false;
    }
}
