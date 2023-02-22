package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;

public class RegistrationDto {

    private final Registration registration;

    public RegistrationDto(Registration registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {

        String output = "";

        if (registration.getRegistrationStatus() == RegistrationStatus.COURSE_FULL) {
            output = RegistrationStatus.COURSE_FULL.getRegStatus();
        } else
            output = registration.getId() + " " + RegistrationStatus.ACCEPTED.getRegStatus();

        return output.trim();
    }

}
