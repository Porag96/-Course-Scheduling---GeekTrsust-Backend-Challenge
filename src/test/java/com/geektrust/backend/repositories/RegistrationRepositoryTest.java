package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Employee;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
import com.geektrust.backend.services.RegistrationService;

@DisplayName("Registration Repository Test")
public class RegistrationRepositoryTest {

    private Employee employee = new Employee("ANDY@GMAIL.COM");
    private CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);

    private Registration registration = new Registration(employee, course);
    private RegistrationRepository registrationRepository = new RegistrationRepository();

    private CourseOfferingRepository courseRepository = new CourseOfferingRepository();
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private RegistrationService registrationService = new RegistrationService(registrationRepository, courseRepository,
            employeeRepository);

    @Test
    @DisplayName("save method should store the registration obj successfully")
    public void saveRegistration() {

        Registration savedRegistration = registrationRepository.save(registration);

        Assertions.assertEquals(registration, savedRegistration);

    }

    @Test
    @DisplayName("findById method should return registration Given ID")
    public void findById_method_should_return_registration_Given_Id() {
        this.registrationRepository.save(this.registration);

        Assertions.assertEquals(this.registration,
                this.registrationRepository.findById(this.registration.getId()).get());
    }

    @Test
    @DisplayName("findAll method should return all the registrations")
    public void findAllRegistration() {

        // Arrange
        registrationRepository.save(registration);
        int expectedCourseCount = 1;

        // Act
        List<Registration> actualCourses = registrationRepository.findAll();

        // Assert
        Assertions.assertEquals(expectedCourseCount, actualCourses.size());

    }

    @Test
    @DisplayName("findAll method should return Empty List if No Registration Found")
    public void findAllRegistration_ShouldReturnEmptyList() {

        // Arrange
        int expectedCourseCount = 0;

        // Act
        List<Registration> actualCourses = registrationRepository.findAll();

        // Assert
        Assertions.assertEquals(expectedCourseCount, actualCourses.size());

    }

    @Test
    @DisplayName("Allot Method should allot to a course")
    public void allotCourseShouldAllotCourse() {
        CourseOffering course = new CourseOffering("KUBERNETES", "WOODY", "16062022",
                2, 5);

        courseRepository.save(course);

        List<Registration> registrations = new ArrayList<>();
        registrations.add(this.registrationService.registerCourse("WOO@GMAIL.COM",
                course.getId()));

        List<Registration> expectedOutput = new ArrayList<>();
        expectedOutput.add(registrations.get(0));

        Assertions.assertEquals(expectedOutput,
                this.registrationRepository.allotCourse(course.getId()));

    }

    @Test
    @DisplayName("Allot should confirm registration status as confirmed till max number of employee")
    public void allotMethod_ShouldConfirmRegistrationsStatusConfirmed() {
        CourseOffering course1 = new CourseOffering("PYTHON", "HARRY", "15062022", 1,
                2);
        CourseOffering course2 = new CourseOffering("KUBERNETES", "WOODY",
                "15062022", 2, 5);

        courseRepository.save(course1);
        courseRepository.save(course2);

        List<Registration> registrations = new ArrayList<>();
        registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                course1.getId()));
        registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                course2.getId()));
        registrations.add(this.registrationService.registerCourse("JIYA@GMAIL.COM",
                course2.getId()));

        List<Registration> expectedOutput = new ArrayList<>();
        expectedOutput.add(registrations.get(1));
        expectedOutput.add(registrations.get(2));

        List<Registration> actualOutput = this.registrationRepository.allotCourse(course2.getId());
        Assertions.assertEquals(expectedOutput, actualOutput);

        for (Registration reg : actualOutput)
            Assertions.assertEquals(RegistrationStatus.CONFIRMED,
                    reg.getRegistrationStatus());

    }

    @Test
    @DisplayName("Allot should return empty list on wrong course Id")
    public void allotMethod_ShouldReturnEmptyListOnWrongCourse_Id() {
        CourseOffering course1 = new CourseOffering("DOCKER", "JENNY", "15062022", 1,
                2);
        CourseOffering course2 = new CourseOffering("HIBERNATE", "LCWD", "15062022",
                3, 5);

        this.courseRepository.save(course1);
        this.courseRepository.save(course2);

        List<Registration> registrations = new ArrayList<>();
        registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                course1.getId()));
        registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                course2.getId()));

        List<Registration> expectedOutput = new ArrayList<>();

        Assertions.assertEquals(expectedOutput,
                this.registrationRepository.allotCourse("RANDOM"));

    }

    @Test
    @DisplayName("count should return total number of registrations")
    public void count_ShouldReturnTotalNumberOfRegistrations() {
        CourseOffering course = new CourseOffering("REACT", "Six Pack Programmer",
                "15062022", 1, 2);
        Employee employee = new Employee("YASH@GMAIL.COM");

        Registration registration = new Registration(employee, course);
        this.registrationRepository.save(registration);

        Integer expectedRegistration = 1;
        Assertions.assertEquals(expectedRegistration,
                this.registrationRepository.count());
    }

    @Test
    @DisplayName("deleteById should delete registration successfully")
    public void deleteById_ShouldDeleteRegistrationSuccessfully() {

        this.courseRepository.save(course);

        Registration registration = this.registrationService.registerCourse("AVRA@GMAIL.COM", course.getId());

        this.registrationRepository.deleteById(registration.getId());

        Assertions.assertTrue(this.registrationRepository.findById(course.getId()).isEmpty());
    }

    @Test
    @DisplayName("existsById should return true or false")
    public void existsById_ShouldReturnBoolean() {

        Registration registration = new Registration(employee, course);
        registrationRepository.save(registration);

        Assertions.assertTrue(registrationRepository.existsById(registration.getId()));

        registrationRepository.delete(registration);

        Assertions.assertFalse(this.registrationRepository.existsById(registration.getId()));

    }

    @Test
    @DisplayName("delete function should successfuly delete a registration")
    public void deleteShouldDeleteRegistrationEntitySuccessfuly() {
        courseRepository.save(course);

        Registration registration = registrationService.registerCourse("ANDY@GMAIL.COM", course.getId());

        registrationRepository.delete(registration);

        Assertions.assertTrue(this.registrationRepository.findById(course.getId()).isEmpty());
    }

    // @Test
    // @DisplayName("findById method should return correct registration object")
    // public void findByIdMethodShouldReturnCorrectRegistraionObejct() {
    // this.registrationRepository.save(this.registration);

    // Assertions.assertEquals(this.registration,
    // this.registrationRepository.findById(this.registration.getId()).get());
    // }

    // @Test
    // @DisplayName("Save method should save the registration object correctly")
    // public void saveMethodShouldSaveTheRegistrationCorrectly() {
    // this.registrationRepository.save(registration);
    // Registration actualRegistration =
    // this.registrationRepository.findById(registration.getId()).orElseThrow(
    // () -> new NoRegisteredEmployeeFound("Registration with id: " +
    // registration.getId() + " not found!"));

    // Assertions.assertEquals(registration, actualRegistration);
    // }

    // @Test
    // @DisplayName("findAll should return all the registration")
    // public void findAllShouldReturnAllRegistration() {
    // List<Registration> registrations = new ArrayList<>();
    // this.courseRepository.save(this.course);
    // String courseOfferingId = this.course.getId();
    // registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // courseOfferingId));
    // registrations.add(this.registrationService.registerCourse("SANDY@GMAIL.COM",
    // courseOfferingId));

    // Assertions.assertEquals(registrations.stream().collect(Collectors.toSet()),
    // this.registrationRepository.findAll().stream().collect(Collectors.toSet()));

    // }

    // @Test
    // @DisplayName("Allot should return correcte output")
    // public void allotMethodShouldReturnOutput() {
    // Course course1 = new Course("JAVA", "JAMES", "15062022", 1, 2);
    // Course course2 = new Course("Python", "Thomous", "15062022", 3, 5);
    // Course course3 = new Course("Jullia", "JAMES", "15062022", 2, 4);

    // this.courseRepository.save(course1);
    // this.courseRepository.save(course2);
    // this.courseRepository.save(course3);

    // List<Registration> registrations = new ArrayList<>();
    // registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // course1.getId()));
    // registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // course3.getId()));
    // registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
    // course3.getId()));
    // registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
    // course2.getId()));

    // List<Registration> expectedOutput = new ArrayList<>();
    // expectedOutput.add(registrations.get(1));
    // expectedOutput.add(registrations.get(2));

    // Assertions.assertEquals(expectedOutput,
    // this.registrationRepository.allotCourse(course3.getId()));

    // }

    // @Test
    // @DisplayName("Allot should confirm respective registrations given minimum
    // registrations")
    // public void
    // allotMethodShouldConfirmRespectiveRegistrationsGivenMinimumRegistrations() {

    // Course course1 = new Course("JAVA", "JAMES", "15062022", 1, 2);
    // Course course2 = new Course("Python", "Thomous", "15062022", 3, 5);
    // Course course3 = new Course("Jullia", "JAMES", "15062022", 2, 4);

    // this.courseRepository.save(course1);
    // this.courseRepository.save(course2);
    // this.courseRepository.save(course3);

    // List<Registration> registrations = new ArrayList<>();
    // registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // course1.getId()));
    // registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // course3.getId()));
    // registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
    // course3.getId()));
    // registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
    // course2.getId()));

    // List<Registration> expectedOutput = new ArrayList<>();
    // expectedOutput.add(registrations.get(1));
    // expectedOutput.add(registrations.get(2));

    // List<Registration> actualOutput =
    // this.registrationRepository.allotCourse(course3.getId());
    // Assertions.assertEquals(expectedOutput, actualOutput);

    // for (Registration currentRegistration : actualOutput)
    // Assertions.assertEquals(RegistrationStatus.CONFIRMED,
    // currentRegistration.getRegistrationStatus());

    // }

    // @Test
    // @DisplayName("Allot should return empty output on wrong sourse offering id")
    // public void allotMethodShouldReturnEmptyOutputOnWrongCourseOfferingId() {

    // Course course1 = new Course("JAVA", "JAMES", "15062022", 1, 2);
    // Course course2 = new Course("Python", "Thomous", "15062022", 3, 5);
    // Course course3 = new Course("Jullia", "JAMES", "15062022", 2, 4);

    // this.courseRepository.save(course1);
    // this.courseRepository.save(course2);
    // this.courseRepository.save(course3);

    // List<Registration> registrations = new ArrayList<>();
    // registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // course1.getId()));
    // registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // course3.getId()));
    // registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
    // course3.getId()));
    // registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
    // course2.getId()));

    // List<Registration> expectedOutput = new ArrayList<>();

    // Assertions.assertEquals(expectedOutput,
    // this.registrationRepository.allotCourse("random"));

    // }

    // @Test
    // @DisplayName("count function should return correct number of registrations")
    // public void countShouldReturnCorrectNumberOfRegistrations() {

    // Course course = new Course("Jullia", "JAMES", "15062022", 2, 4);
    // Employee employee = new Employee("ANDY@GMAIL.COM");

    // Registration registration = new Registration(employee, course);
    // this.registrationRepository.save(registration);

    // Integer expectedRegistration = 1;
    // Assertions.assertEquals(expectedRegistration,
    // this.registrationRepository.count());
    // }

    // @Test
    // @DisplayName("existsById should return true or false correctly")
    // public void existsByIdShouldReturnTrueOrFalseCorrectly() {
    // try {
    // Course course = new Course("Jullia", "JAMES", "15062022", 2, 4);
    // Employee employee = new Employee("ANDY@GMAIL.COM");

    // Registration registration = new Registration(employee, course);
    // this.registrationRepository.save(registration);

    // Assertions.assertTrue(this.registrationRepository.existsById(registration.getId()));

    // this.registrationRepository.delete(registration);

    // Assertions.assertFalse(this.registrationRepository.existsById(registration.getId()));

    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // }
    // }

    // @Test
    // @DisplayName("delete function should successfuly delete registration entity")
    // public void deleteShouldDeleteRegistrationEntitySuccessfuly() {

    // Course course = new Course("Jullia", "JAMES", "15062022", 2, 4);

    // course = this.courseRepository.save(course);
    // Registration registration =
    // this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // course.getId());

    // this.registrationRepository.delete(registration);

    // Assertions.assertTrue(this.registrationRepository.findById(course.getId()).isEmpty());
    // }

    // @Test
    // @DisplayName("deleteById should delete registration successfully")
    // public void deleteByIdShouldDeleteRegistrationSuccessfully() {

    // Course course = new Course("Jullia", "JAMES", "15062022", 2, 4);

    // course = this.courseRepository.save(course);
    // Registration registration =
    // this.registrationService.registerCourse("ANDY@GMAIL.COM",
    // course.getId());

    // this.registrationRepository.deleteById(registration.getId());

    // Assertions.assertTrue(this.registrationRepository.findById(course.getId()).isEmpty());
    // }

    // @Test
    // @DisplayName("confirmRegistrationsByCourseOffiering should make respective
    // courseOffering registrations confirm")
    // public void
    // confirmRegistrationsByCourseOffieringShouldMakeRespectivecourseOfferingRegistrationsConfirm()
    // {

    // this.course = this.courseRepository.save(this.course);
    // this.registration =
    // this.registrationService.registerCourse(this.employee.getEmail(),
    // this.course.getId());
    // Registration registration =
    // this.registrationService.registerCourse("WOO@GMAIL.COM",
    // this.course.getId());

    // String expectedOutput = "REG-COURSE-ANDY-JAVA ANDY@GMAIL.COM
    // OFFERING-JAVA-JAMES JAVA JAMES 15062022 CONFIRMED\nREG-COURSE-WOO-JAVA
    // WOO@GMAIL.COM OFFERING-JAVA-JAMES JAVA JAMES 15062022 CONFIRMED";

    // this.registrationRepository.confirmRegistrationsByCourse(course);
    // List<Registration> confirmedRegistrations =
    // this.registrationRepository.findAll();
    // Collections.sort(confirmedRegistrations,
    // Registration.getSortBasedOnTheRegistrationNumber());
    // CourseAllotDto allotDto = new CourseAllotDto(confirmedRegistrations);

    // Assertions.assertEquals(expectedOutput, allotDto.toString());
    // Assertions.assertEquals(RegistrationStatus.CONFIRMED,
    // registration.getRegistrationStatus());
    // Assertions.assertEquals(RegistrationStatus.CONFIRMED,
    // this.registration.getRegistrationStatus());
    // }

    // @Test
    // @DisplayName("confirmRegistrationById should make the respective registration
    // confirm")
    // public void
    // confirmRegistrationByIdShouldMakeTheRespectiveRegistrationConfirm() {
    // this.course = this.courseRepository.save(this.course);
    // this.registration =
    // this.registrationService.registerCourse(this.employee.getEmail(),
    // this.course.getId());
    // Registration registration =
    // this.registrationService.registerCourse("WOO@GMAIL.COM",
    // this.course.getId());

    // this.registrationRepository.confirmRegistrationById(registration.getId());

    // Registration checkRegistration =
    // this.registrationRepository.findById(registration.getId())
    // .orElseThrow(() -> new NoRegisteredEmployeeFound());

    // Assertions.assertEquals(RegistrationStatus.CONFIRMED,
    // checkRegistration.getRegistrationStatus());
    // Assertions.assertEquals(RegistrationStatus.CONFIRMED,
    // registration.getRegistrationStatus());
    // }
}
