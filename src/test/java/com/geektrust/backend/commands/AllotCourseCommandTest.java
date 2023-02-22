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
import com.geektrust.backend.repositories.RegistrationRepository;
import com.geektrust.backend.services.RegistrationService;

@DisplayName("Allot Course Command Test")
public class AllotCourseCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final CourseOfferingRepository courseRepository = new CourseOfferingRepository();
    private final RegistrationRepository registrationRepository = new RegistrationRepository();
    private final RegistrationService registrationService = new RegistrationService(registrationRepository,
            courseRepository, employeeRepository);
    private final ICommand allotCommand = new AllotCourseCommand(registrationService);

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Allot command allot a course")
    public void allot_command_should_allot_a_course() {

        CourseOffering course = new CourseOffering("JAVA", "JAMES", "05062022", 1, 2);
        courseRepository.save(course);

        registrationService.registerCourse("ANDY@GMAIL.COM", course.getId());

        List<String> values = List.of("ALLOT", "OFFERING-JAVA-JAMES");

        String expectedOutput = "REG-COURSE-ANDY-JAVA ANDY@GMAIL.COM OFFERING-JAVA-JAMES JAVA JAMES 05062022 CONFIRMED";

        this.allotCommand.execute(values);

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}
