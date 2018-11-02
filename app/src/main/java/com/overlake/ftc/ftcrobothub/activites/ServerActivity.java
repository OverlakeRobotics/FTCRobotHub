package com.overlake.ftc.ftcrobothub.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.overlake.ftc.ftcrobothub.R;
import com.overlake.ftc.ftcrobothub.routes.HomeRoute;
import com.overlake.ftc.ftcrobothub.webserver.Router;
import com.overlake.ftc.ftcrobothub.webserver.WebServer;

import java.io.IOException;

public class ServerActivity extends AppCompatActivity
{
    private WebServer webServer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        webServer = new WebServer(8000);
        Router router = webServer.getRouter();
        router.addRoute(new HomeRoute());


        webServer.listen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServer.stop();
    }
}
