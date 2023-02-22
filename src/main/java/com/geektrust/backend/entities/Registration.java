package com.geektrust.backend.entities;

import java.util.Comparator;

public class Registration extends BaseEntity {

    private final Employee employee;
    private final CourseOffering course;
    private RegistrationStatus registrationStatus;

    public Registration(Employee employee, CourseOffering course) {
        this.employee = employee;
        this.course = course;
        this.id = Registration.generateId(employee, course);
        this.registrationStatus = RegistrationStatus.ACCEPTED;
    }

    public Registration(String id, Employee employee, CourseOffering course) {
        this(employee, course);
        if (id != null)
            this.id = id;
    }

    public Registration(Registration registration) {
        this(registration.getId(), registration.getEmployee(), registration.getCourse());
        this.registrationStatus = registration.getRegistrationStatus();
    }

    public Employee getEmployee() {
        return employee;
    }

    public CourseOffering getCourse() {
        return course;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public static String generateId(Employee employee, CourseOffering course) {
        return Messages.REG_COURSE.getMessage() + "-" + employee.getName() + "-" + course.getTitle();
    }

    public static SortBasedOnTheRegistrationNumber getSortBasedOnTheRegistrationNumber() {
        return new SortBasedOnTheRegistrationNumber();
    }

    @Override
    public String toString() {
        return "Registration ID=" + this.id + ", employee="
                + employee.getName() + ", course="
                + course.getTitle()
                + ", registrationStatus=" + registrationStatus
                + "";
    }

}

class SortBasedOnTheRegistrationNumber implements Comparator<Registration> {
    @Override
    public int compare(Registration registration1, Registration registration2) {
        return registration1.getId().compareTo(registration2.getId());
    }
}
