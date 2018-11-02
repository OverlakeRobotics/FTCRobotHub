package com.overlake.ftc.ftcrobothub.routes;

import com.overlake.ftc.ftcrobothub.webserver.AddRoute;
import com.overlake.ftc.ftcrobothub.webserver.Route;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Method;

public class HomeRoute extends Route {
    public HomeRoute() {
        super("");
    }

    @AddRoute(uri="1", method=Method.GET)
    public Response get1(NanoHTTPD.IHTTPSession session) {
        return NanoHTTPD.newFixedLengthResponse("Hello, world");
    }

    @AddRoute(uri="2", method=Method.GET)
    public Response get2(NanoHTTPD.IHTTPSession session) {
        return NanoHTTPD.newFixedLengthResponse(this.calculate());
    }

    private String calculate() {
        return "" + (1 + 1);
    }
}
