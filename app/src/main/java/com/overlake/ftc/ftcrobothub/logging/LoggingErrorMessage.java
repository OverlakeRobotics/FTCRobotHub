package com.overlake.ftc.ftcrobothub.logging;

public class LoggingErrorMessage extends LoggingMessage {
    public Exception exception;

    public LoggingErrorMessage(Exception exception) {
        super("Error", "An Exception was thrown");
        this.exception = exception;
    }
}
