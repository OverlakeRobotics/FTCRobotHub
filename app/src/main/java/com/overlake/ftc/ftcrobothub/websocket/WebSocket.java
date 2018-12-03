package com.overlake.ftc.ftcrobothub.websocket;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocket {
    private Thread serverThread;
    private SocketRequestHandler socketRequestHandler;
    private int port;

    public WebSocket(int port, SocketRequestHandler socketRequestHandler) {
        this.port = port;
        this.socketRequestHandler = socketRequestHandler;
    }

    public void start() {
        final ExecutorService clientPool = Executors.newFixedThreadPool(5);
        this.serverThread = new Thread(getServerTask(clientPool));
        this.serverThread.start();
    }

    private Runnable getServerTask(final ExecutorService clientPool) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket server = new ServerSocket(port);
                    while (true) {
                        Log.i("WebSocket", "Waiting for client connections....");
                        Socket clientSocket = server.accept();
                        clientPool.submit(new ClientRequest(clientSocket, socketRequestHandler));
                    }
                } catch (IOException e) {
                    Log.i("WebSocket","Unable to process client request");
                    Log.i("WebSocket", getStackTrace(e));
                } catch (Exception e) {
                    Log.i("WebSocket","Error creating client request instance");
                    Log.i("WebSocket", getStackTrace(e));
                }
            }
        };
    }

    private String getStackTrace(Exception e) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < e.getStackTrace().length; i++) {
            builder.append(e.getStackTrace()[i].toString());
        }
        return builder.toString();
    }
}
