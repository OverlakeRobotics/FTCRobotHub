package com.overlake.ftc.ftcrobothub.websocket;

import java.io.InputStream;
import java.net.Socket;

public interface SocketRequestHandler {
    void handle(Socket inputStream);
}
