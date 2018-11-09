package com.overlake.ftc.ftcrobothub.webserver.routing;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD.ResponseException;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

public interface RouteHandler {
    Response handle(IHTTPSession session);
}
