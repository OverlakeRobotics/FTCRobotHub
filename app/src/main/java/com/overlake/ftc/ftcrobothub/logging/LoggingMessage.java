package com.overlake.ftc.ftcrobothub.logging;

import com.google.gson.Gson;

import java.util.Date;

public class LoggingMessage {
    public final String tag = "LoggingService";
    public String level;
    public String message;
    public long date;

    public LoggingMessage(String level, String message) {
        this.level = level;
        this.message = message;
        this.date = new Date().getTime();
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
