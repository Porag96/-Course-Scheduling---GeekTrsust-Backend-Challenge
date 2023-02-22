package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.dtos.CancelRegistrationDto;
import com.geektrust.backend.entities.Messages;
import com.geektrust.backend.services.IRegistrationService;

public class CancelCommand implements ICommand {

    private final IRegistrationService registrationService;

    public CancelCommand(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void execute(List<String> inputStrings) {

        try {

            if (inputStrings.size() < 2) {
                System.out.println(Messages.INPUT_DATA_ERROR);
                return;
            }

            String regId = inputStrings.get(1);

            CancelRegistrationDto cancelRegistrationDto = registrationService.cancelRegistration(regId);
            System.out.println(cancelRegistrationDto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
