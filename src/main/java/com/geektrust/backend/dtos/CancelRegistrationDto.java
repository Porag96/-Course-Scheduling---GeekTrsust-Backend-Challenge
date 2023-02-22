package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.RegistrationStatus;

public class CancelRegistrationDto {
    private final String registrationId;
    private final RegistrationStatus registrationStatus;

    public CancelRegistrationDto(String registrationId, RegistrationStatus registrationStatus )
    {
        this.registrationId = registrationId;
        this.registrationStatus = registrationStatus;
    }

    @Override
    public String toString() {
        return this.registrationId + " " + this.registrationStatus;
    }
}
