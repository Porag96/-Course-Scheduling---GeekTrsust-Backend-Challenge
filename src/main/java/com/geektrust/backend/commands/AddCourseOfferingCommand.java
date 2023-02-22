package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Messages;
import com.geektrust.backend.services.ICourseService;

public class AddCourseOfferingCommand implements ICommand {

    private final ICourseService courseService;

    public AddCourseOfferingCommand(ICourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute(List<String> inputStrings) {

        try {

            if (inputStrings.size() < 6) {
                System.out.println(Messages.INPUT_DATA_ERROR);
                return;
            }

            String courseName = inputStrings.get(1), instructorName = inputStrings.get(2), date = inputStrings.get(3);
            Integer minEmployee = Integer.parseInt(inputStrings.get(4)),
                    maxEmployee = Integer.parseInt(inputStrings.get(5));

            CourseOffering course = courseService.addCourse(courseName, instructorName, date, minEmployee, maxEmployee);

            CourseDto courseDto = new CourseDto(course);
            System.out.println(courseDto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
