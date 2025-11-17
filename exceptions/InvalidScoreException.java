package com.student.management.exceptions;

public class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }

    public InvalidScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}