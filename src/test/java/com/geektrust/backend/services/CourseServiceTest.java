package com.geektrust.backend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.exceptions.InvalidInputException;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.ICourseOfferingRepository;

@DisplayName("Course Service Test")
@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

        private CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        private ICourseOfferingRepository courseRepository = new CourseOfferingRepository();
        private Courseservice courseservice = new Courseservice(courseRepository);

        @Test
        @DisplayName("addCourse should craete a course succesfuly")
        public void addCourse_ShouldCreateACourseSuccessfuly() {

                CourseOffering savedCourse = courseservice.addCourse("JAVA", "JAMES",
                                "15062022", 1, 2);

                Assertions.assertEquals(this.course, savedCourse);
        }

        @Test
        @DisplayName("add Course should throw Input Data Error Exception if min employees is zero")
        public void add_Course_should_throw_Input_Data_Error_Exception_if_min_employees_is_zero() {
                Assertions.assertThrows(InvalidInputException.class,
                                () -> courseservice.addCourse("JAVA", "JAMES", "15062022", 0,
                                                2));
        }

        @Test
        @DisplayName("add Course should throw Input Data Error Exception if max employees is zero")
        public void add_Course_should_throw_Input_Data_Error_Exception_if_max_employees_is_zero() {
                Assertions.assertThrows(InvalidInputException.class,
                                () -> courseservice.addCourse("JAVA", "JAMES", "15062022", 1,
                                                0));
        }

        @Test
        @DisplayName("add Course should throw Input Data Error Exception if min employees is greather than max employees")
        public void add_Course_should_throw_Input_Data_Error_Exception_if_min_employees_is_greather_than_max_employees() {
                Assertions.assertThrows(InvalidInputException.class,
                                () -> courseservice.addCourse("JAVA", "JAMES", "15062022", 5,
                                                3));
        }

        @Test
        @DisplayName("addCourseOffering should not add duplicate course offering")
        public void addCourseOfferingShouldNotAddDuplicateCourseOffering() {
                this.courseRepository.save(this.course);
                CourseOffering duplicateCourse = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);

                duplicateCourse = this.courseRepository.save(duplicateCourse);

                Integer expectedNumberOfCourse = 1;

                Assertions.assertEquals(expectedNumberOfCourse, this.courseRepository.findAll().size());
        }

}
