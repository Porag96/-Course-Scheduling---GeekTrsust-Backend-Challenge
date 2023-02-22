package com.geektrust.backend.services;

import java.util.List;

import com.geektrust.backend.dtos.CancelRegistrationDto;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.InvalidInputException;

public interface IRegistrationService {
    public Registration registerCourse(String email, String courseId) throws InvalidInputException;

    public List<Registration> allotCourse(String courseId);

    public CancelRegistrationDto cancelRegistration(String regId);
}
