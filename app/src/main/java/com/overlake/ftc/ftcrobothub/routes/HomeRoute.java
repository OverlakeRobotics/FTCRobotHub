package com.overlake.ftc.ftcrobothub.routes;

import com.overlake.ftc.ftcrobothub.webserver.AddRoute;
import com.overlake.ftc.ftcrobothub.webserver.Route;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Method;

public class HomeRoute extends Route {
    public HomeRoute() {
        super("");
    }

    @AddRoute(uri="", method=Method.GET)
    public Response get1(NanoHTTPD.IHTTPSession session) {
        return NanoHTTPD.newFixedLengthResponse("Hello, world");
    }

    @AddRoute(uri="add", method=Method.GET)
    public Response get2(NanoHTTPD.IHTTPSession session) {
        Map<String, String> params = session.getParms();
        return NanoHTTPD.newFixedLengthResponse(sumParams(session.getParms()));
    }

    private String sumParams(Map<String, String> params) {
        int sum = 0;
        String[] values = params.get("numbers").split(",");
        for (String value : values) {
            sum += Integer.parseInt(value);
        }
        return "The sum of " + params.get("numbers") + " is " + sum;
    }
}
