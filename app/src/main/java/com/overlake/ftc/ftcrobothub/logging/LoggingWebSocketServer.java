package com.overlake.ftc.ftcrobothub.logging;

import android.util.Log;

import com.google.gson.GsonBuilder;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class LoggingWebSocketServer extends WebSocketServer
{
    public LoggingWebSocketServer(int port )
    {
        super( new InetSocketAddress( port ) );
    }

    public LoggingWebSocketServer(InetSocketAddress address ) {
        super( address );
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake ) {
        conn.send(new LoggingConnectionMessage().getJson());
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        conn.send(new LoggingDisconnectMessage().getJson());
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        // Should really handle client requests on the logging such as filtering
        broadcast( new LoggingMessage("Info", message).getJson() );
    }

    @Override
    public void onError( WebSocket conn, Exception exception ) {
        if( conn == null ) {
            Log.e("LoggingWebSocketServer", exception.getMessage() + "");
        } else {
            conn.send(new LoggingErrorMessage(exception).getJson());
        }
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}
