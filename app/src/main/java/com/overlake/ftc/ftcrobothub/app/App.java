package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;

import com.overlake.ftc.ftcrobothub.webserver.IRoute;
import com.overlake.ftc.ftcrobothub.webserver.PublicFiles;
import com.overlake.ftc.ftcrobothub.webserver.Route;
import com.overlake.ftc.ftcrobothub.webserver.Router;
import com.overlake.ftc.ftcrobothub.webserver.WebServer;

public abstract class App
{
    private WebServer webServer;
    private Router router;
    private PublicFiles publicFiles;
    private Context activityContext;

    public App(Context context) {
        webServer = new WebServer(8000);
        router = webServer.getRouter();
        activityContext = context;
    }

    public Router getRouter() {
        return router;
    }

    public PublicFiles getPublicFiles() {
        return publicFiles;
    }

    public void setPublicFiles(IRoute publicFiles) {
        router.addRoute(publicFiles);
    }

    public Context getActivityContext() {
        return activityContext;
    }

    public void start() {
        webServer.listen();
        main();
    }

    public void stop() {
        webServer.stopListening();
    }

    public boolean isRunning() {
        return webServer.isListening();
    }

    public abstract void main();
}
