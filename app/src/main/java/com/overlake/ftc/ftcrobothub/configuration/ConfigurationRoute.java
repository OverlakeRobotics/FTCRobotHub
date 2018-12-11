package com.overlake.ftc.ftcrobothub.configuration;

import com.overlake.ftc.ftcrobothub.webserver.responses.JsonResponse;
import com.overlake.ftc.ftcrobothub.webserver.routing.AddRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.IRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.Route;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Method;

public class ConfigurationRoute extends Route
{
    public ConfigurationRoute()
    {
        super("api/configuration");
    }

    @AddRoute(uri="", method=Method.GET)
    public Response getConfiguration(IHTTPSession session) {
        Map<String, String> parameters = session.getParms();
        Configuration configuration = ConfigurationBuilder.buildFromFile(parameters.get("configurationName"));
        return new JsonResponse(new ConfigurationResponse(true, configuration));
    }

    @AddRoute(uri="", method=Method.POST)
    public Response createConfiguration(IHTTPSession session) {
        ConfigurationRequest configurationRequest = parseBody(session, new ConfigurationRequest());
        Configuration configuration = new Configuration(configurationRequest.configuration);
        configuration.save(configurationRequest.configurationName);
        return new JsonResponse(new ConfigurationResponse(true, "Saved Configuration: " + configurationRequest.configurationName));
    }
}
