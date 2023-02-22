package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.dtos.CourseAllotDto;
import com.geektrust.backend.entities.Messages;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.services.IRegistrationService;

public class AllotCourseCommand implements ICommand {

    private final IRegistrationService registrationService;

    public AllotCourseCommand(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void execute(List<String> inputStrings) {

        try {

            if (inputStrings.size() < 2) {
                System.out.println(Messages.INPUT_DATA_ERROR);
                return;
            }

            String courseOfferingId = inputStrings.get(1);

            List<Registration> registrations = registrationService.allotCourse(courseOfferingId);
            CourseAllotDto courseAllotDto = new CourseAllotDto(registrations);
            System.out.println(courseAllotDto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
