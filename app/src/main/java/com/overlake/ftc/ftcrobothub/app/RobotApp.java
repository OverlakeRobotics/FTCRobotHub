package com.overlake.ftc.ftcrobothub.app;

import android.content.Context;
import android.util.Log;

import com.overlake.ftc.ftcrobothub.webserver.routing.Router;
import com.overlake.ftc.ftcrobothub.websocket.SocketRequestHandler;
import com.overlake.ftc.ftcrobothub.websocket.WebSocket;

import org.apache.http.impl.io.ChunkedInputStream;
import org.apache.http.impl.io.SessionInputBufferImpl;
import org.apache.http.io.SessionInputBuffer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class RobotApp extends App {
    public RobotApp(Context context) {
        super(context);
    }

    @Override
    public void main() {
        WebSocket socket = getWebsocket(7000, new SocketRequestHandler() {
            @Override
            public void handle(Socket socket) {
                Log.i("Client Request", "Reading input stream");
                try {
                    String x = readInputStream(socket.getInputStream());
                    Log.i("Client Request", x);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        socket.start();
    }

    private String readInputStream(InputStream stream) throws IOException {
        SessionInputBufferImpl
        ChunkedInputStream in = new ChunkedInputStream((SessionInputBuffer) stream);
        StringBuilder stringBuilder = new StringBuilder();
        String data = null;
        while ((data = in.readLine()) != null) {
            Log.i("Client Request", "Reading");
            stringBuilder.append(data);
        }
        return stringBuilder.toString();
    }
}
