package com.geektrust.backend.entities;

public enum Messages {

    CONFIRMED("CONFIRMED"), COURSE_FULL("COURSE_FULL"),
    COURSE_CANCELED("COURSE_CANCELED"), CANCEL_ACCEPTED("CANCEL_ACCEPTED"),
    CANCEL_REJECTED("CANCEL_REJECTED"), INPUT_DATA_ERROR("INPUT_DATA_ERROR"),
    INVALID_EMAIL("INVALID_EMAIL"), REG_COURSE("REG-COURSE"), COURSE_OFFERING("OFFERING");

    private String message;

    private Messages(String messages) {
        this.message = messages;
    }

    public String getMessage() {
        return this.message;
    }

}
