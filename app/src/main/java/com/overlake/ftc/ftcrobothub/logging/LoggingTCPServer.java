package com.overlake.ftc.ftcrobothub.logging;

import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

public class LoggingTCPServer implements Runnable
{
    private final int LoggingTCPServerPort = 7001;
    private int loggingWebSocketServerPort;
    private ServerSocket loggingSocket;
    private LoggingWebSocketServer loggingWebSocketServer;

    public LoggingTCPServer(int loggingWebSocketServerPort) throws IOException
    {
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
            while (true) {
                Socket loggingClientSocket = loggingSocket.accept();
                broadCastLoggingLine(loggingClientSocket);
                loggingClientSocket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void broadCastLoggingLine(Socket clientSocket) throws IOException
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String line;
        while((line = input.readLine()) != null) {
            loggingWebSocketServer.broadcast(line);
        }
    }
}
