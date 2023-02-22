package com.geektrust.backend.dtos;

import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;

public class CourseAllotDto {

    private final List<Registration> registrationList;

    public CourseAllotDto(List<Registration> registration) {
        this.registrationList = registration;
    }

    @Override
    public String toString() {
        String op = "";
        for (Registration registration : registrationList) {
            CourseOffering course = registration.getCourse();

            op += registration.getId() + " " + registration.getEmployee().getEmail() + " " + course.getId() + " "
                    + course.getTitle() + " " + course.getInstructorName() + " " + course.getDate() + " "
                    + registration.getRegistrationStatus().toString();
            op += "\n";
        }

        return op.trim();
    }

}
