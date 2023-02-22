package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.dtos.RegistrationDto;
import com.geektrust.backend.entities.Messages;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.services.IRegistrationService;

public class RegisterCommand implements ICommand {

    private final IRegistrationService registrationService;

    public RegisterCommand(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void execute(List<String> inputStrings) {

        try {

            if (inputStrings.size() < 3) {
                System.out.println(Messages.INPUT_DATA_ERROR);
                return;
            }

            String email = inputStrings.get(1);
            String courseOfferingId = inputStrings.get(2);

            Registration registration = registrationService.registerCourse(email, courseOfferingId);

            RegistrationDto registrationDto = new RegistrationDto(registration);

            System.out.println(registrationDto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
