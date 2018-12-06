package com.overlake.ftc.ftcrobothub.logging;

public class LoggingDisconnectMessage extends LoggingMessage {
    public final String message = "Disconnected from FTC logging server.";

    public LoggingDisconnectMessage() {
        super("disconnect");
    }
}
