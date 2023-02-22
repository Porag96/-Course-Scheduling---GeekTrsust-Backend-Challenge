package com.geektrust.backend.exceptions;

public class DuplicateCourseNameException extends RuntimeException {

    public DuplicateCourseNameException() {
        super();
    }

    public DuplicateCourseNameException(String msg) {
        super(msg);
    }
    
}
