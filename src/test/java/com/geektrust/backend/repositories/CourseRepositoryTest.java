package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.exceptions.CourseNotFoundException;

@DisplayName("Course Repository Test")
@ExtendWith(MockitoExtension.class)
public class CourseRepositoryTest {

    private final CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
    private final CourseOfferingRepository courseRepository = new CourseOfferingRepository();

    @Test
    @DisplayName("save method should store the course obj successfully")
    public void saveCourse() {

        CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOfferingRepository courseRepository = new CourseOfferingRepository();
        course = courseRepository.save(course);

        CourseOffering actualCourse = courseRepository.findById(course.getId())
                .orElseThrow(() -> new CourseNotFoundException());

        Assertions.assertEquals(course, actualCourse);

    }

    @Test
    @DisplayName("findAll method should return all the course")
    public void findAllCourse() {

        List<CourseOffering> courses = new ArrayList<>();

        courses.add(this.course);
        courses.add(new CourseOffering("Python", "JAMES", "15062022", 1, 2));

        for (CourseOffering course : courses)
            this.courseRepository.save(course);

        Assertions.assertEquals(courses.stream().collect(Collectors.toSet()),
                this.courseRepository.findAll().stream().collect(Collectors.toSet()));

    }

    @Test
    @DisplayName("findAll method should return Empty List if No Course Found")
    public void findAllCourse_ShouldReturnEmptyList() {

        // Arrange
        int expectedCourseCount = 0;

        // Act
        List<CourseOffering> actualCourses = courseRepository.findAll();

        // Assert
        Assertions.assertEquals(expectedCourseCount, actualCourses.size());

    }

    @Test
    @DisplayName("findById method should return Course Given Id")
    public void findById_ShouldReturnCourse_GivenContestId() {

        this.courseRepository.save(course);

        Assertions.assertEquals(course, courseRepository.findById(this.course.getId()).get());
    }

    @Test
    @DisplayName("existsById should return correct ouptut")
    public void existsByIdShouldReturnCorrectOutput() {
        CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOfferingRepository courseRepository = new CourseOfferingRepository();
        course = courseRepository.save(course);

        Assertions.assertTrue(courseRepository.existsById(course.getId()));
    }

    @Test
    @DisplayName("DeletebyId should delete course offering succesfully")
    public void DeletebyIdShouldDeleteCourseOfferingSuccessfuly() {
        CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOfferingRepository courseRepository = new CourseOfferingRepository();
        course = courseRepository.save(course);

        courseRepository.deleteById(course.getId());

        Assertions.assertTrue(courseRepository.findById(course.getId()).isEmpty());

    }

    @Test
    @DisplayName("Delete should delete course offering succesfully")
    public void deleteSHouldDeleteCourseOfferingSuccessfuly() {
        CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
        CourseOfferingRepository CourseRepository = new CourseOfferingRepository();
        course = CourseRepository.save(course);

        CourseRepository.delete(course);

        Assertions.assertTrue(CourseRepository.findById(course.getId()).isEmpty());

    }

}
