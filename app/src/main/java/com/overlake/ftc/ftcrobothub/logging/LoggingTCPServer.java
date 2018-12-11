package com.overlake.ftc.ftcrobothub.logging;

import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoggingTCPServer implements Runnable
{
    private volatile boolean isRunning;
    private final int LoggingTCPServerPort = 7001;
    private int loggingWebSocketServerPort;
    private ServerSocket loggingSocket;
    private LoggingWebSocketServer loggingWebSocketServer;

    public LoggingTCPServer(int loggingWebSocketServerPort) throws IOException
    {
        isRunning = true;
        this.loggingWebSocketServerPort = loggingWebSocketServerPort;
        loggingSocket = new ServerSocket(LoggingTCPServerPort);
    }

    @Override
    public void run()
    {

        try
        {
            loggingWebSocketServer = new LoggingWebSocketServer(loggingWebSocketServerPort);
            loggingWebSocketServer.start();
            while (isRunning) {
                Socket loggingClientSocket = loggingSocket.accept();
                while (!loggingClientSocket.isClosed()) {
                    broadCastLoggingLine(loggingClientSocket);
                }
                loggingClientSocket.close();
            }
        }
        catch (IOException e)
        {
            Log.i("LoggingTCPServer", e.getMessage());
        }
    }

    private void broadCastLoggingLine(Socket clientSocket)
    {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while((line = input.readLine()) != null) {
                loggingWebSocketServer.broadcast(line);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void stop() {
        isRunning = false;
        try
        {
            loggingSocket.close();
            loggingWebSocketServer.stop();
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
        catch (IOException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
