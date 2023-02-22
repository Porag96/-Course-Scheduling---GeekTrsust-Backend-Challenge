package com.geektrust.backend.entities;

public enum RegistrationStatus {
    ACCEPTED("ACCEPTED"), CONFIRMED("CONFIRMED"), CANCEL_ACCEPTED("CANCEL_ACCEPTED"),
    CANCEL_REJECTED("CANCEL_REJECTED"), COURSE_FULL("COURSE_FULL_ERROR"), COURSE_CANCELED("COURSE_CANCELED");

    private String regStatus;

    private RegistrationStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    public String getRegStatus() {
        return regStatus;
    }

}
