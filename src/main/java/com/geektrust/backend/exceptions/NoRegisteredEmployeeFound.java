package com.geektrust.backend.exceptions;

public class NoRegisteredEmployeeFound extends RuntimeException {

    public NoRegisteredEmployeeFound() {
        super();
    }

    public NoRegisteredEmployeeFound(String message) {
        super(message);
    }

}
