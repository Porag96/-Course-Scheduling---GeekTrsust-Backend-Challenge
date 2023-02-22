package com.geektrust.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.geektrust.backend.dtos.CancelRegistrationDto;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Employee;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
import com.geektrust.backend.repositories.CourseOfferingRepository;
import com.geektrust.backend.repositories.EmployeeRepository;
import com.geektrust.backend.repositories.RegistrationRepository;

@DisplayName("Registration Service Test")
@ExtendWith(MockitoExtension.class)

public class RegistrationServiceTest {
        private RegistrationRepository registrationRepository = new RegistrationRepository();
        private CourseOfferingRepository courseRepository = new CourseOfferingRepository();
        private EmployeeRepository employeeRepository = new EmployeeRepository();
        private RegistrationService registrationService = new RegistrationService(registrationRepository,
                        courseRepository, employeeRepository);

        @Test
        @DisplayName("registerToCourseOffering should make resgistration successfull")
        public void registerToCourseOfferingShouldMakeRegistrationSuccessful() {
                Employee employee = new Employee("ANDY@GMAIL.COM");
                this.employeeRepository.save(employee);
                CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                this.courseRepository.save(course);

                String expectedOutput = "Registration ID=REG-COURSE-ANDY-JAVA, employee=ANDY, course=JAVA, registrationStatus=ACCEPTED";

                Registration registration = registrationService.registerCourse("ANDY@GMAIL.COM",
                                "OFFERING-JAVA-JAMES");

                Assertions.assertEquals(expectedOutput, registration.toString());
                Assertions.assertEquals(RegistrationStatus.ACCEPTED, registration.getRegistrationStatus());
        }

        @Test
        @DisplayName("registerTocourse Should use Already Created employee")
        public void registerTocourseShouldUseAlreadyCreatedemployee() {
                Employee employee = new Employee("ANDY@GMAIL.COM");
                this.employeeRepository.save(employee);
                CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                this.courseRepository.save(course);

                Registration registration = registrationService.registerCourse("ANDY@GMAIL.COM",
                                "OFFERING-JAVA-JAMES");

                Assertions.assertEquals(employee, registration.getEmployee());
        }

        @Test
        @DisplayName("register To course should create new employee if not created and save it to employeerEpository")
        public void registerTocourse_ShouldCreateNewemployeeIfNotCreatedAndSaveItToEmployeerEpository() {
                Employee employee = new Employee("ANDY@GMAIL.COM");

                CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                this.courseRepository.save(course);

                Registration registration = registrationService.registerCourse("ANDY@GMAIL.COM",
                                "OFFERING-JAVA-JAMES");

                Assertions.assertEquals(employee, registration.getEmployee());
                Assertions.assertEquals(employee, employeeRepository.findById(employee.getEmail()).get());
        }

        @Test
        @DisplayName("registerTocourse should set status of registration to COURSE_FULL on full capacity and not save it")
        public void registerTocourse_ShouldSetStatusOFREgistrationToCOURSE_FULLOnFullCapacityAndNotSaveIt() {
                CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                this.courseRepository.save(course);

                Registration registration1 = registrationService.registerCourse("ANDY@GMAIL.COM",
                                "OFFERING-JAVA-JAMES");
                Registration registration2 = registrationService.registerCourse("SAM@GMAIL.COM",
                                "OFFERING-JAVA-JAMES");
                Registration registration3 = registrationService.registerCourse("TIM@GMAIL.COM",
                                "OFFERING-JAVA-JAMES");

                Assertions.assertEquals(RegistrationStatus.COURSE_FULL, registration3.getRegistrationStatus());
                Optional<Registration> checkSavedRegistration = this.registrationRepository
                                .findById(registration3.getId());
                Assertions.assertTrue(checkSavedRegistration.isEmpty());
        }

        @Test
        @DisplayName("registerTocourse should not add duplicate registrations")
        public void registerTocourseShouldNotAddDuplicateRegistrations() {
                CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                this.courseRepository.save(course);

                Registration registration1 = registrationService.registerCourse("ANDY@GMAIL.COM",
                                "OFFERING-JAVA-JAMES");
                Registration registration2 = registrationService.registerCourse("ANDY@GMAIL.COM",
                                "OFFERING-JAVA-JAMES");

                Integer expectedTotalRegistration = 1;

                Assertions.assertEquals(expectedTotalRegistration, course.getTotalRegdEmployees());

        }

        @Test
        @DisplayName("Allot should return correcte output")
        public void allotMethodShouldReturnCorrectOutput() {
                CourseOffering course1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                CourseOffering course2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
                CourseOffering course3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);

                this.courseRepository.save(course1);
                this.courseRepository.save(course2);
                this.courseRepository.save(course3);

                List<Registration> registrations = new ArrayList<>();
                registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                                course1.getId()));
                registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                                course3.getId()));
                registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
                                course3.getId()));
                registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
                                course2.getId()));

                List<Registration> expectedOutput = new ArrayList<>();
                expectedOutput.add(registrations.get(1));
                expectedOutput.add(registrations.get(2));

                Assertions.assertEquals(expectedOutput, this.registrationService.allotCourse(course3.getId()));

        }

        @Test
        @DisplayName("Allot should delete course offering if minimum enrollment not done")
        public void allotMethodShouldDeletecourseIfMinimumEnrollmentNotDone() {
                CourseOffering course1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                CourseOffering course2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
                CourseOffering course3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);

                this.courseRepository.save(course1);
                this.courseRepository.save(course2);
                this.courseRepository.save(course3);

                List<Registration> registrations = new ArrayList<>();
                registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                                course1.getId()));

                registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                                course3.getId()));

                registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
                                course2.getId()));

                List<Registration> expectedOutput = new ArrayList<>();
                expectedOutput.add(registrations.get(1));
                expectedOutput.add(registrations.get(2));

                List<Registration> actualRegistrations = this.registrationService.allotCourse(course3.getId());

                Assertions.assertTrue(this.courseRepository.findById(course3.getId()).isEmpty());

        }

        @Test
        @DisplayName("Allot should display list even after course offeirng get deleted")
        public void allotShouldDisplayListEvenAftercourseGetDeleted() {
                CourseOffering course1 = new CourseOffering("JAVA", "JAMES", "15062022", 3, 3);
                this.courseRepository.save(course1);

                List<Registration> registrations = new ArrayList<>();
                registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                                course1.getId()));
                registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
                                course1.getId()));
                List<Registration> actualRegistrations = this.registrationService.allotCourse(course1.getId());
                Assertions.assertTrue(this.courseRepository.findById(course1.getId()).isEmpty());
                Assertions.assertEquals(registrations, actualRegistrations);

        }

        @Test
        @DisplayName("Allot should return empty output on wrong sourse offering id")
        public void allotMethodShouldReturnEmptyOutputOnWrongcourseId() {

                CourseOffering course1 = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                CourseOffering course2 = new CourseOffering("Python", "Thomous", "15062022", 3, 5);
                CourseOffering course3 = new CourseOffering("Jullia", "JAMES", "15062022", 2, 4);

                this.courseRepository.save(course1);
                this.courseRepository.save(course2);
                this.courseRepository.save(course3);

                List<Registration> registrations = new ArrayList<>();
                registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                                course1.getId()));
                registrations.add(this.registrationService.registerCourse("ANDY@GMAIL.COM",
                                course3.getId()));
                registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
                                course3.getId()));
                registrations.add(this.registrationService.registerCourse("SAM@GMAIL.COM",
                                course2.getId()));

                List<Registration> expectedOutput = new ArrayList<>();

                Assertions.assertEquals(expectedOutput, this.registrationService.allotCourse("xyzAbc"));

        }

        @Test
        @DisplayName("cancelRegistration should accept cancellation")
        public void cancelRegistrationShouldAcceptCancellation() {
                CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                this.courseRepository.save(course);

                Employee employee1 = new Employee("ANDY@GMAIL.COM");
                this.employeeRepository.save(employee1);

                Registration registration = this.registrationService.registerCourse(employee1.getEmail(),
                                course.getId());
                CancelRegistrationDto cancelDto = this.registrationService.cancelRegistration(registration.getId());

                String expectedOuptut = "REG-COURSE-ANDY-JAVA CANCEL_ACCEPTED";

                Assertions.assertTrue(this.registrationRepository.findById(registration.getId()).isEmpty());

                Assertions.assertEquals(expectedOuptut, cancelDto.toString());
        }

        @Test
        @DisplayName("cancelRegistration should reject cancellation")
        public void cancelRegistrationShouldRejectCancellation() {
                CourseOffering course = new CourseOffering("JAVA", "JAMES", "15062022", 1, 2);
                this.courseRepository.save(course);

                Employee employee1 = new Employee("ANDY@GMAIL.COM");
                this.employeeRepository.save(employee1);

                Registration registration = this.registrationService.registerCourse(employee1.getEmail(),
                                course.getId());

                List<Registration> allotedRegistrations = this.registrationService.allotCourse(course.getId());

                CancelRegistrationDto cancelDto = this.registrationService.cancelRegistration(registration.getId());

                String expectedOuptut = "REG-COURSE-ANDY-JAVA CANCEL_REJECTED";

                Assertions.assertFalse(this.registrationRepository.findById(registration.getId()).isEmpty());

                Assertions.assertEquals(expectedOuptut, cancelDto.toString());
        }

}
