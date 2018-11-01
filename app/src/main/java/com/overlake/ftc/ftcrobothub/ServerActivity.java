package com.overlake.ftc.ftcrobothub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

public class ServerActivity extends AppCompatActivity
{
    private AndroidWebServer webServer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        webServer = new AndroidWebServer(8000);
        try
        {
            webServer.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServer.stop();
    }
}
