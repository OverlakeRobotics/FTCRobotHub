package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;

import com.overlake.ftc.ftcrobothub.webserver.WebServer;
import com.overlake.ftc.ftcrobothub.webserver.routing.IRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.NotFoundRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.Router;
import com.overlake.ftc.ftcrobothub.webserver.routing.StaticFilesRoute;
import com.overlake.ftc.ftcrobothub.websocket.ClientRequest;
import com.overlake.ftc.ftcrobothub.websocket.SocketRequestHandler;
import com.overlake.ftc.ftcrobothub.websocket.WebSocket;

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

    public void setStaticFiles(IRoute staticFiles) {
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
    }

    public boolean isRunning() {
        return webServer.isListening();
    }

    public WebSocket getWebsocket(int port, SocketRequestHandler socketRequestHandler) {
        return new WebSocket(port, socketRequestHandler);
    }

    public abstract void main();
}
