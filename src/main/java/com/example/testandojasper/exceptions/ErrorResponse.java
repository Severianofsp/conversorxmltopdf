package com.example.testandojasper.exceptions;

import java.util.Arrays;

public class ErrorResponse {

    private final String message;
    private final StackTraceElement[] stackTrace;

    public ErrorResponse(String message, StackTraceElement[] stackTrace) {
        this.message = message;
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        return "Error{" +
                "message='" + message + '\'' +
                ", stackTrace=" + Arrays.toString(stackTrace) +
                '}';
    }
}