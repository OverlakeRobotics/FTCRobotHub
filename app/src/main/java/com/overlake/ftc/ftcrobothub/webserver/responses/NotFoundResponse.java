package com.overlake.ftc.ftcrobothub.webserver.responses;

import com.jaredrummler.android.device.DeviceName;

import java.io.ByteArrayInputStream;

import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD;

public class NotFoundResponse
{

    public static Response getResponse(String path) {
            return NanoHTTPD.newChunkedResponse(
                    Response.Status.NOT_FOUND,
                    "text/html",
                    new ByteArrayInputStream(getNotFoundResponseHtml(path))
            );
    }

    private static byte[] getNotFoundResponseHtml(String path) {
        String html = "<html>\n" +
                "  <body>\n" +
                "    <h1>Not Found</h1>\n" +
                "    <p>The requested URL " + path + " was not found on this server</p>\n" +
                "    <hr/>\n" +
                "    <p><i>" + DeviceName.getDeviceName() + " on RobotHub Server " + "</i></p>\n" +
                "  </body>\n" +
                "</html>";
        return html.getBytes();
    }
}
