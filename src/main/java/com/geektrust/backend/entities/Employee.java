package com.geektrust.backend.entities;

import java.util.ArrayList;
import java.util.List;

import com.geektrust.backend.exceptions.InvalidInputException;
import com.geektrust.backend.helper.EmailValidator;

public class Employee extends BaseEntity {

    private String email;
    private String name;
    private List<CourseOffering> empCourses;

    public Employee(String email) {
        if (EmailValidator.isValidEmail(email)) {
            this.email = email;
            this.name = this.email.substring(0, this.email.indexOf('@'));
            empCourses = new ArrayList<>();
        } else {
            throw new InvalidInputException(Messages.INVALID_EMAIL.getMessage());
        }
    }

    public Employee(String id, String email, List<CourseOffering> empCourses) {
        this(email, empCourses);
        this.id = id;
    }

    public Employee(Employee employee) {
        this(employee.getEmail(), employee.getEmployeeCourses());
        this.id = employee.getId();
    }

    public Employee(String email, List<CourseOffering> empCourses) {
        this(email);
        this.empCourses = empCourses;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List<CourseOffering> getEmployeeCourses() {
        return empCourses;
    }

    public void addCourse(CourseOffering course) {
        this.empCourses.add(course);
    }

    @Override
    public String toString() {
        return "email=" + email + ", name=" + name + "";
    }

}