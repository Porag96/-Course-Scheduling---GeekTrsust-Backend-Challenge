package com.geektrust.backend.services;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.exceptions.InvalidInputException;

public interface ICourseService {
    public CourseOffering addCourse(String courseTitle, String instructorName, String date, Integer minEmployee,
            Integer maxEmployee)
            throws InvalidInputException;
}
