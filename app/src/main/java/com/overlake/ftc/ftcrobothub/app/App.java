package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.overlake.ftc.ftcrobothub.webserver.WebServer;
import com.overlake.ftc.ftcrobothub.webserver.routing.IRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.NotFoundRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.Router;
import com.overlake.ftc.ftcrobothub.webserver.routing.StaticFilesRoute;

import static android.content.Context.WIFI_SERVICE;

public abstract class App
{
    private WebServer webServer;
    private Router router;
    private StaticFilesRoute staticFiles;
    private Context activityContext;

    public App(Context context) {
        webServer = new WebServer(8000);
        router = webServer.getRouter();
        activityContext = context;
    }

    public Router getRouter() {
        return router;
    }

    public StaticFilesRoute getStaticFiles() {
        return staticFiles;
    }

    public void setNotFoundRoute(NotFoundRoute route) { webServer.setNotFoundRoute(route); }

    public void setStaticFiles(StaticFilesRoute staticFiles) {
        this.staticFiles = staticFiles;
        router.addRoute(staticFiles);
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
        onClose();
    }

    public boolean isRunning() {
        return webServer.isListening();
    }

    public abstract void main();

    public abstract void onClose();
}
