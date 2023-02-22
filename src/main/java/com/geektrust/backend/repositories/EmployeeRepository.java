package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.geektrust.backend.entities.Employee;

public class EmployeeRepository implements IEmployeeRepository {

    private final Map<String, Employee> employeeRepoMap;

    public EmployeeRepository() {
        this.employeeRepoMap = new HashMap<>();
    }

    public EmployeeRepository(Map<String, Employee> employeeRepoMap) {
        this.employeeRepoMap = employeeRepoMap;
    }

    @Override
    public Employee save(Employee employee) {
        employeeRepoMap.put(employee.getEmail(), employee);
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public Optional<Employee> findById(String id) {
        return Optional.ofNullable(employeeRepoMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public void delete(Employee entity) {
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public Integer count() {
        return 0;
    }

}
