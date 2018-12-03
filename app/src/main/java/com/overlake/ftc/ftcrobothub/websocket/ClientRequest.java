package com.overlake.ftc.ftcrobothub.websocket;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class ClientRequest implements Runnable {
    private Socket clientSocket;
    private SocketRequestHandler socketRequestHandler;

    public ClientRequest(Socket clientSocket, SocketRequestHandler socketRequestHanlder) {
        this.clientSocket = clientSocket;
        this.socketRequestHandler = socketRequestHanlder;
    }

    @Override
    public void run() {
        Log.i("ClientRequest", "Received a client socket");
        socketRequestHandler.handle(clientSocket);
        try {
            clientSocket.close();
        } catch (IOException e) {
            Log.i("Client Request", getStackTrace(e));
        }
    }

    private String getStackTrace(Exception e) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < e.getStackTrace().length; i++) {
            builder.append(e.getStackTrace()[i].toString());
        }
        return builder.toString();
    }
}
