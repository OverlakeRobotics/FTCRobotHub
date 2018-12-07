package com.overlake.ftc.ftcrobothub.logging;

import com.google.gson.Gson;

public class LoggingMessage {
    public final String tag = "LoggingService";
    public String level;
    public String message;

    public LoggingMessage(String level, String message) {
        this.level = level;
        this.message = message;
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
