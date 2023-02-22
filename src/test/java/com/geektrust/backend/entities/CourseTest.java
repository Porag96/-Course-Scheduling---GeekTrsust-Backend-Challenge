package com.geektrust.backend.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Course Entity Test")
@ExtendWith(MockitoExtension.class)
public class CourseTest {

    @Test
    @DisplayName("course should be created successfully")
    public void course_should_be_created_successfully() {
        String expectedOutput = "Course offering ID=OFFERING-JAVA-JAMES, title=JAVA, instructorName=JAMES, date=15062022, minEmployees=1, maxEmployees=2";
        CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);

        Assertions.assertEquals(expectedOutput, course.toString());
    }

    @Test
    @DisplayName("creation of course should return correct course id")
    public void creation_of_course_should_return_correct_course_id() {
        String expectedId = "OFFERING-JAVA-JAMES";

        CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);

        Assertions.assertEquals(expectedId, course.getId());
    }

}
