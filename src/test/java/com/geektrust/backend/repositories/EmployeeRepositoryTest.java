package com.geektrust.backend.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.geektrust.backend.entities.Employee;

@DisplayName("Employee Repository Test")
public class EmployeeRepositoryTest {

    @Test
    @DisplayName("Save method should save the Employee object correctly")
    public void saveMethod_ShouldSaveTheEmployeeCorrectly() {
        Employee employee = new Employee("ANDY@gmail.com");
        EmployeeRepository employeeRepository = new EmployeeRepository();

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertEquals(employee, savedEmployee);
    }

}
