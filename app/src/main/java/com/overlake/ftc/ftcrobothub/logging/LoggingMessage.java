package com.overlake.ftc.ftcrobothub.logging;

import com.google.gson.Gson;

public abstract class LoggingMessage {
    private String tag;
    private final String message = "Connected to FTC logging server.";

    public LoggingMessage(String tag) {
        this.tag = tag;
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
