package com.overlake.ftc.ftcrobothub.webserver.responses;

import com.jaredrummler.android.device.DeviceName;

import java.io.ByteArrayInputStream;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;

public class ServerSideErrorResponse
{
    public static Response getServerError(String path, String stackTrace) {
        return NanoHTTPD.newChunkedResponse(
            Response.Status.INTERNAL_ERROR,
            "text/html",
            new ByteArrayInputStream(
                (
                    "<html>\n" +
                    "<body>\n" +
                    "<h1>Internal Server Error</h1>\n" +
                    "<p>Internal error occurred on " + path + " on this server </p>\n" +
                    "</p>" + stackTrace + "</p>\n" +
                    "<p></p>\n" +
                    "<hr/>\n" +
                    "<p><i>" + DeviceName.getDeviceName() + " on RobotHub Server</i></p>\n" +
                    "</body>\n" +
                    "</html>"
                ).getBytes()
            )
        );
    }
}
