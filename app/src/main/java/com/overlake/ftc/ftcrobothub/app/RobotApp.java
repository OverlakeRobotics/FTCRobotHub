package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.overlake.ftc.ftcrobothub.logging.LoggingServer;

import java.io.IOException;

import static android.content.Context.WIFI_SERVICE;

public class RobotApp extends App {
    private LoggingServer server;

    public RobotApp(Context context) {
        super(context);
    }

    @Override
    public void main()
    {
        try
        {
            server = new LoggingServer(7000);
            server.start();
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void onClose() {
        try {
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
