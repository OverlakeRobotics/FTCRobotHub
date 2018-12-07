package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;

import com.overlake.ftc.ftcrobothub.logging.LoggingTCPServer;
import com.overlake.ftc.ftcrobothub.logging.LoggingWebSocketServer;
import com.overlake.ftc.ftcrobothub.webserver.routing.StaticFilesRoute;

import java.io.IOException;

public class RobotApp extends App {
    private Thread serverThread;

    public RobotApp(Context context) {
        super(context);
    }

    @Override
    public void main()
    {
        initializeLoggingService();
        setStaticFiles(new StaticFilesRoute("public", getActivityContext()));
    }

    private void initializeLoggingService() {
        try
        {
            LoggingTCPServer server = new LoggingTCPServer(7000);
            serverThread = new Thread(server);
            serverThread.start();
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void onClose() {
    }
}
