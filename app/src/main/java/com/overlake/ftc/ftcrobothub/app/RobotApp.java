package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;

import com.overlake.ftc.ftcrobothub.configuration.ConfigurationRoute;
import com.overlake.ftc.ftcrobothub.logging.LoggingTCPServer;
import com.overlake.ftc.ftcrobothub.webserver.routing.NotFoundRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.RouteHandler;
import com.overlake.ftc.ftcrobothub.webserver.routing.Router;
import com.overlake.ftc.ftcrobothub.webserver.routing.StaticFilesRoute;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import fi.iki.elonen.NanoHTTPD;

public class RobotApp extends App {
    private LoggingTCPServer server;
    private ExecutorService serverThreadExecutor;

    public RobotApp(Context context) {
        super(context);
    }

    @Override
    public void main()
    {
        initializeLoggingService();
        setStaticFilesBasePath("/public");
        setNotFoundRoute(new NotFoundRoute()
        {
            @Override
            public RouteHandler send404Error()
            {
                return new RouteHandler()
                {
                    @Override
                    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session)
                    {
                        return StaticFilesRoute.sendFile("/public/index.html");
                    }
                };
            }
        });
        Router router = getRouter();
        router.addRoute(new ConfigurationRoute());
    }

    private void initializeLoggingService() {
        try
        {
            server = new LoggingTCPServer(7000);
            serverThreadExecutor = Executors.newSingleThreadExecutor();
            serverThreadExecutor.submit(server);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void onClose() {
        server.stop();
        serverThreadExecutor.shutdownNow();
        try
        {
            serverThreadExecutor.awaitTermination(1, TimeUnit.SECONDS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
