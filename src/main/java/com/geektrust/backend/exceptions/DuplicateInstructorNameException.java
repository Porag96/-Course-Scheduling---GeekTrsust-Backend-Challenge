package com.geektrust.backend.exceptions;

public class DuplicateInstructorNameException extends RuntimeException {
    
    public DuplicateInstructorNameException(){
        super();
    }

    public DuplicateInstructorNameException(String msg){
        super(msg);
    }
}
