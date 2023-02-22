package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.CourseOffering;

public class CourseDto {

    private final CourseOffering course;

    public CourseDto(CourseOffering course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return course.getId();
    }

}
