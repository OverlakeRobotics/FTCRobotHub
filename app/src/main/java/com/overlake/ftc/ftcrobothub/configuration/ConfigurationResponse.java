package com.overlake.ftc.ftcrobothub.configuration;

public class ConfigurationResponse
{
    private boolean success;
    private Object data;

    public ConfigurationResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }
}
