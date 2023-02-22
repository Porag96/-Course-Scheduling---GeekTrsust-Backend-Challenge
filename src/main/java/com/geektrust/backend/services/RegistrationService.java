package com.geektrust.backend.services;

import java.util.List;
import java.util.Optional;

import com.geektrust.backend.dtos.CancelRegistrationDto;
import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Employee;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;
import com.geektrust.backend.exceptions.CourseNotFoundException;
import com.geektrust.backend.exceptions.InvalidInputException;
import com.geektrust.backend.exceptions.NoRegisteredEmployeeFound;
import com.geektrust.backend.repositories.ICourseOfferingRepository;
import com.geektrust.backend.repositories.IEmployeeRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;

public class RegistrationService implements IRegistrationService {

    private IRegistrationRepository registrationRepository;
    private ICourseOfferingRepository courseRepository;
    private IEmployeeRepository employeeRepository;

    public RegistrationService(IRegistrationRepository registrationRepository, ICourseOfferingRepository courseRepository,
            IEmployeeRepository employeeRepository) {
        this.registrationRepository = registrationRepository;
        this.courseRepository = courseRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Registration registerCourse(String email, String courseId) throws InvalidInputException {

        CourseOffering course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course " + courseId + " " + "Not Found."));

        Employee employee = null;

        Optional<Employee> desiredEmployee = employeeRepository.findById(email);

        if (desiredEmployee.isPresent())
            employee = desiredEmployee.get();
        else
            employee = new Employee(email);

        String regId = Registration.generateId(employee, course);

        Optional<Registration> desiredRegById = registrationRepository.findById(regId);

        if (desiredRegById.isPresent())
            return desiredRegById.get();

        Registration registration = null;

        if (isMaxEmpLimitReached(course)) {
            registration = new Registration(employee, course);
            registration.setRegistrationStatus(RegistrationStatus.COURSE_FULL);
            return registration;
        }

        course.incrementTotalRegEmployeesByOne();
        course = courseRepository.save(course);

        employee.addCourse(course);
        employee = this.employeeRepository.save(employee);

        registration = new Registration(employee, course);
        registrationRepository.save(registration);

        return registration;
    }

    @Override
    public List<Registration> allotCourse(String courseId) {

        Optional<CourseOffering> cOptional = courseRepository.findById(courseId);

        if (cOptional.isPresent()) {
            CourseOffering course = cOptional.get();
            if (course.getTotalRegdEmployees() < course.getMinEmployees()) {
                courseRepository.delete(course);
            }

        }
        return registrationRepository.allotCourse(courseId);
    }

    @Override
    public CancelRegistrationDto cancelRegistration(String regId) {

        Registration registration = registrationRepository.findById(regId)
                .orElseThrow(() -> new NoRegisteredEmployeeFound());

        if (registration.getRegistrationStatus() == RegistrationStatus.CONFIRMED) {
            return new CancelRegistrationDto(regId, RegistrationStatus.CANCEL_REJECTED);
        }

        registrationRepository.delete(registration);

        return new CancelRegistrationDto(regId, RegistrationStatus.CANCEL_ACCEPTED);
    }

    private boolean isMaxEmpLimitReached(CourseOffering course) {
        if (course.getTotalRegdEmployees() == course.getMaxEmployees()) {
            return true;
        }

        return false;
    }

}
