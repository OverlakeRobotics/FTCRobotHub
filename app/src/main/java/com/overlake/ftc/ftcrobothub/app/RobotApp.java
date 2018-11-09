package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;

import com.overlake.ftc.ftcrobothub.routes.HomeRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.StaticFilesRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.Router;

public class RobotApp extends App
{
    public RobotApp(Context context) {
        super(context);
    }

    @Override
    public void main()
    {
        Router router = getRouter();
        router.addRoute(new HomeRoute());
        setStaticFiles(new StaticFilesRoute("/public", getActivityContext()));
    }
}
