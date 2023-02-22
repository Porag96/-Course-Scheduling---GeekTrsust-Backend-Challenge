package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.AddCourseOfferingCommand;
import com.geektrust.backend.commands.AllotCourseCommand;
import com.geektrust.backend.commands.CancelCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.ICommand;
import com.geektrust.backend.commands.RegisterCommand;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.EmployeeRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.repositories.IEmployeeRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import com.geektrust.backend.repositories.RegistrationRepository;
import com.geektrust.backend.services.Courseservice;
import com.geektrust.backend.services.ICourseService;
import com.geektrust.backend.services.IRegistrationService;
import com.geektrust.backend.services.RegistrationService;

public class ApplicationConfig {

    private final ICourseOfferingRepository courseRepository = new CourseOfferingRepository();
    private final ICourseService courseService = new Courseservice(courseRepository);

    private final IRegistrationRepository registrationRepository = new RegistrationRepository();
    private final IEmployeeRepository employeeRepository = new EmployeeRepository();
    private final IRegistrationService registrationService = new RegistrationService(registrationRepository,
            courseRepository, employeeRepository);

    private final ICommand addCourseOfferingCommand = new AddCourseOfferingCommand(courseService);
    private final ICommand registerCommand = new RegisterCommand(registrationService);
    private final ICommand allotCourseCommand = new AllotCourseCommand(registrationService);
    private final ICommand canceCommand = new CancelCommand(registrationService);

    CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker() {
        commandInvoker.register("ADD-COURSE-OFFERING", addCourseOfferingCommand);
        commandInvoker.register("REGISTER", registerCommand);
        commandInvoker.register("ALLOT", allotCourseCommand);
        commandInvoker.register("CANCEL", canceCommand);

        return commandInvoker;
    }

}
