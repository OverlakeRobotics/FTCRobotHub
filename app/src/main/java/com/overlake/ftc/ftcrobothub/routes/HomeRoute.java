package com.overlake.ftc.ftcrobothub.routes;

import com.overlake.ftc.ftcrobothub.webserver.routing.AddRoute;
import com.overlake.ftc.ftcrobothub.webserver.responses.JsonResponse;
import com.overlake.ftc.ftcrobothub.webserver.routing.Route;
import com.overlake.ftc.ftcrobothub.webserver.routing.StaticFilesRoute;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Method;

public class HomeRoute extends Route {
    public HomeRoute() {
        super("");
    }

    @AddRoute(uri="", method=Method.GET)
    public Response get1(IHTTPSession session) {
        return StaticFilesRoute.sendFile("/public/index.html");
    }

    @AddRoute(uri="", method=Method.POST)
    public Response post1(IHTTPSession session) {
        SumDTO sum = new SumDTO();
        NumberInput numbers = parseBody(session, new NumberInput());
        sum.calculate(numbers.getInput());
        return new JsonResponse(sum);
    }


    @AddRoute(uri="add", method=Method.GET)
    public Response get2(IHTTPSession session) {
        Map<String, String> params = session.getParms();
        SumDTO sum = new SumDTO();
        sum.calculate(params.get("numbers"));
        return new JsonResponse(sum);
    }
}

class NumberInput {
    private String input;

    public String getInput() {
        return input;
    }
}
