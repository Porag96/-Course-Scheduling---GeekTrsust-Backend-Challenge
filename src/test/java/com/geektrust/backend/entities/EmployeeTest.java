package com.geektrust.backend.entities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.geektrust.backend.exceptions.InvalidInputException;

@DisplayName("Registration Entity Test")
@ExtendWith(MockitoExtension.class)
public class EmployeeTest {

    private Employee employee = new Employee("ANDY@GMAIL.COM");

    @Test
    @DisplayName("Employee should be created successfully")
    public void employee_should_be_created_successfully() {

        // Arrange
        String expextedOutput = "email=ANDY@GMAIL.COM, name=ANDY";

        // Act and Assert
        Assertions.assertEquals(expextedOutput, employee.toString());
    }

    @Test
    @DisplayName("Employee should throw InvalidInputException if input email is not correct")
    public void emplyee_ShouldThrowInvalidInputException() {

        // Arrange
        String email = "PORAG";

        // Act and Assert
        Assertions.assertThrows(InvalidInputException.class, () -> new Employee(email));
    }

    @Test
    @DisplayName("getEmployeeCourse should return correct output")
    public void getEmployeeCourse_ShouldReturnCorrectOutput() {
        try {

            List<CourseOffering> courses = new ArrayList<>();
            courses.add(new CourseOffering("JAVA", "JAMES", "15062022", 1, 2));
            courses.add(new CourseOffering("Python", "JAMES", "15062022", 1, 2));
            courses.add(new CourseOffering("AWS", "JAMES", "15062022", 1, 2));

            for (CourseOffering course : courses)
                this.employee.addCourse(course);

            Assertions.assertEquals(courses, employee.getEmployeeCourses());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("valid Employee Email should create Employee object on valid email")
    public void validEmployeeEmail_ShouldCreateEmployeeObjectOnValidEmail() {
        Employee employee = new Employee("ANDY@GMAIL.COM");
        Assertions.assertNotNull(employee);
    }

}
