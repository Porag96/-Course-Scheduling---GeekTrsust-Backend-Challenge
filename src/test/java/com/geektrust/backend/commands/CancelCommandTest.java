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
import com.geektrust.backend.entities.Employee;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.EmployeeRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.repositories.IEmployeeRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import com.geektrust.backend.repositories.RegistrationRepository;
import com.geektrust.backend.services.RegistrationService;

public class CancelCommandTest {

    private CourseOffering course = new CourseOffering("JAVA",  "JAMES", "05062022", 1, 2);
    private ICourseOfferingRepository courseRepository = new CourseOfferingRepository();
    private IRegistrationRepository registrationRepository = new RegistrationRepository();
    private IEmployeeRepository employeeRepository = new EmployeeRepository();
    private RegistrationService registrationService = new RegistrationService(registrationRepository,
            courseRepository, employeeRepository);
    private CancelCommand cancelCommand = new CancelCommand(registrationService);

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("cancelRegistration should accept cancellation")
    public void cancelRegistrationShouldAcceptCancellation() {

        courseRepository.save(course);

        Employee employee = new Employee("ANDY@GMAIL.COM");
        employeeRepository.save(employee);

        Registration registration = registrationService.registerCourse(employee.getEmail(),
                course.getId());

        List<String> values = List.of("CANCEL", "REG-COURSE-ANDY-JAVA");

        this.cancelCommand.execute(values);

        String expectedOuptut = "REG-COURSE-ANDY-JAVA CANCEL_ACCEPTED";

        Assertions.assertTrue(this.registrationRepository.findById(registration.getId()).isEmpty());

        Assertions.assertEquals(expectedOuptut, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("cancelRegistration should reject cancellation")
    public void cancelRegistrationShouldRejectCancellation() {

        courseRepository.save(course);

        Employee employee = new Employee("ANDY@GMAIL.COM");
        employeeRepository.save(employee);

        Registration registration = registrationService.registerCourse(employee.getEmail(),
                course.getId());

        List<Registration> allotedRegistrations = this.registrationService.allotCourse(course.getId());

        List<String> values = List.of("CANCEL", "REG-COURSE-ANDY-JAVA");

        this.cancelCommand.execute(values);

        String expectedOuptut = "REG-COURSE-ANDY-JAVA CANCEL_REJECTED";

        Assertions.assertFalse(this.registrationRepository.findById(registration.getId()).isEmpty());

        Assertions.assertEquals(expectedOuptut, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
