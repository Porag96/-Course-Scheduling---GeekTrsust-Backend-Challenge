package com.geektrust.backend.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.EmployeeRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.repositories.IEmployeeRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import com.geektrust.backend.repositories.RegistrationRepository;
import com.geektrust.backend.services.RegistrationService;

public class RegisterCommandTest {

    private CourseOffering course = new CourseOffering("JAVA", "JAMES", "05062022", 1, 2);
    private ICourseOfferingRepository courseRepository = new CourseOfferingRepository();
    private IRegistrationRepository registrationRepository = new RegistrationRepository();
    private IEmployeeRepository employeeRepository = new EmployeeRepository();
    private RegistrationService registrationService = new RegistrationService(registrationRepository,
            courseRepository, employeeRepository);
    private RegisterCommand registerCommand = new RegisterCommand(registrationService);

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Register Command should return registration id and accepted status")
    public void register_command_should_return_registration_id_and_ACCEPTED_Status() {

        courseRepository.save(course);

        List<String> values = List.of("REGISTER", "ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

        registerCommand.execute(values);

        String expectedOutput = "REG-COURSE-ANDY-JAVA ACCEPTED";

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @Test
    @DisplayName("Register Command should print COURSE_FULL status if Maximum employee exceeded")
    public void register_Command_should_print_COURSE_FULL_status_if_Maximum_employee_exceeded() {

        courseRepository.save(course);

        List<String> values = List.of("REGISTER", "ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES");

        registerCommand.execute(values);

        values = List.of("REGISTER", "WOO@GMAIL.COM", "OFFERING-JAVA-JAMES");

        this.registerCommand.execute(values);

        values = List.of("REGISTER", "ALICE@GMAIL.COM", "OFFERING-JAVA-JAMES");

        registerCommand.execute(values);

        String expectedOutput = "REG-COURSE-ANDY-JAVA ACCEPTED\nREG-COURSE-WOO-JAVA ACCEPTED\nCOURSE_FULL_ERROR";

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @Test
    @DisplayName("RegisterCommand should print INPUT_DATA_ERROR on wrong input")
    public void registerCommandShoulfPrintINPUT_DATA_ERROROnWrongInput() {
        courseRepository.save(course);

        List<String> values = List.of("REGISTER", "ANDY@GMAIL.COM");

        this.registerCommand.execute(values);

        Assertions.assertEquals("INPUT_DATA_ERROR", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
