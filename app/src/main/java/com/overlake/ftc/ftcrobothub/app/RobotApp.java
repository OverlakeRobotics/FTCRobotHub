package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;
import com.overlake.ftc.ftcrobothub.activities.ChatRoomServer;

public class RobotApp extends App {
    public static StringBuilder streamData = new StringBuilder();

    public RobotApp(Context context) {
        super(context);
    }

    @Override
    public void main()
    {
        try
        {
            ChatRoomServer server = new ChatRoomServer(7000);
            server.start();
            while ( true ) {
                server.broadcast("hello, world");
            }
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }
}
