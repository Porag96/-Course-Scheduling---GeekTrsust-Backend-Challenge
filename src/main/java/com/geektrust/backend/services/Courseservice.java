package com.geektrust.backend.services;

import java.util.Optional;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Messages;
import com.geektrust.backend.exceptions.InvalidInputException;
import com.geektrust.backend.repositories.ICourseOfferingRepository;

public class Courseservice implements ICourseService {

    private final ICourseOfferingRepository courseRepository;

    public Courseservice(ICourseOfferingRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseOffering addCourse(String courseTitle, String instructorName, String date, Integer minEmployee,
            Integer maxEmployee)
            throws InvalidInputException {

        if (minEmployee == 0 || maxEmployee == 0)
            throw new InvalidInputException(Messages.INPUT_DATA_ERROR.getMessage());

        if (minEmployee > maxEmployee)
            throw new InvalidInputException(Messages.INPUT_DATA_ERROR.getMessage());

        if (courseTitle == "" || instructorName == "" || date == "")
            throw new InvalidInputException(Messages.INPUT_DATA_ERROR.getMessage());

        String courseId = CourseOffering.generateId(courseTitle, instructorName);

        Optional<CourseOffering> desiredCourse = courseRepository.findById(courseId);

        if (desiredCourse.isPresent())
            return desiredCourse.get();

        CourseOffering course = courseRepository
                .save(new CourseOffering(courseTitle, instructorName, date, minEmployee, maxEmployee));

        return course;
    }

}
