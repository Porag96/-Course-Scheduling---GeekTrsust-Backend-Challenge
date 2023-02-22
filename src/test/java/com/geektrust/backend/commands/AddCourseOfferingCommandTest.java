package com.geektrust.backend.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.services.Courseservice;

@DisplayName("Add Course Test")
public class AddCourseOfferingCommandTest {

    private final PrintStream stdOut = System.out;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private ICourseOfferingRepository courseOfferingRepository = new CourseOfferingRepository();
    private Courseservice courseOfferingService = new Courseservice(courseOfferingRepository);
    private AddCourseOfferingCommand addCourseOfferingCommand = new AddCourseOfferingCommand(courseOfferingService);

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("execute should print course id")
    public void execute_should_return_course_id() {

        String expectedOutput = "OFFERING-JAVA-JAMES";
        addCourseOfferingCommand.execute(List.of("ADD-COURSE-OFFERING", "JAVA", "JAMES", "15062022", "1", "2"));

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    @DisplayName("execute should print INPUT_DATA_ERROR for Invalid Input")
    public void execute_should_print_INPUT_DATA_ERROR_for_invalid_input() {

        String expectedOutput = "INPUT_DATA_ERROR";

        addCourseOfferingCommand.execute(List.of("ADD-COURSE-OFFERING", "PYTHON"));

        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(stdOut);
    }
}
