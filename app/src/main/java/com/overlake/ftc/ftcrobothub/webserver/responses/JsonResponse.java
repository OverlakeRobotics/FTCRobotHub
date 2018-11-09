package com.overlake.ftc.ftcrobothub.webserver.responses;

import android.util.JsonWriter;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import fi.iki.elonen.NanoHTTPD.Response;

public class JsonResponse extends Response
{
    public JsonResponse(Object data)
    {
        super(
            Status.OK,
            "application/json",
            new ByteArrayInputStream(
                (new Gson()).toJson(data).getBytes()
            ),
            (new Gson()).toJson(data).getBytes().length
        );
    }
}
