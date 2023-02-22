package com.geektrust.backend.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Registration Entity Test")
@ExtendWith(MockitoExtension.class)
public class RegistrationTest {

    private Employee employee = new Employee("ANDY@GMAIL.COM");
    private CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
    private Registration registration = new Registration(employee, course);

    @Test
    @DisplayName("registration should be created successfully")
    public void registration_should_be_created_successfully() {
        String expectedOutput = "Registration ID=REG-COURSE-ANDY-JAVA, employee=ANDY, course=JAVA, registrationStatus=ACCEPTED";

        Assertions.assertEquals(expectedOutput, registration.toString());
    }

    @Test
    @DisplayName("creation of registration should return correct registration id")
    public void creation_of_registration_should_return_correct_registration_id() {

        String expectedId = "REG-COURSE-ANDY-JAVA";

        Assertions.assertEquals(expectedId, registration.getId());
    }

    @Test
    @DisplayName("creation of registration should return status accepted")
    public void creation_of_registration_should_return_status_accepted() {

        RegistrationStatus expectedId = RegistrationStatus.CONFIRMED;

        this.registration.setRegistrationStatus(RegistrationStatus.CONFIRMED);

        Assertions.assertEquals(expectedId, registration.getRegistrationStatus());
    }

}
