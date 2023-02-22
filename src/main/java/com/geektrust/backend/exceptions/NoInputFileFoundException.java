package com.geektrust.backend.exceptions;

public class NoInputFileFoundException extends RuntimeException {

    public NoInputFileFoundException() {
    }

    public NoInputFileFoundException(String message) {
        super(message);
    }

}
