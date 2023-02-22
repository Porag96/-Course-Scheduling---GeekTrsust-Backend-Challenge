package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.entities.RegistrationStatus;

public class RegistrationRepository implements IRegistrationRepository {

    private final Map<String, Registration> registrationRepoMap;

    public RegistrationRepository() {
        this.registrationRepoMap = new HashMap<>();
    }

    public RegistrationRepository(Map<String, Registration> registrationRepoMap) {
        this.registrationRepoMap = registrationRepoMap;
    }

    @Override
    public List<Registration> allotCourse(String courseId) {

        List<Registration> regList = findAll();

        regList = regList.stream().filter(reg -> reg.getCourse().getId().equals(courseId))
                .collect(Collectors.toList());

        for (Registration registration : regList) {
            if (registration.getCourse().isMinEmpLimitReached()) {
                registration.setRegistrationStatus(RegistrationStatus.CONFIRMED);
            } else {
                registration.setRegistrationStatus(RegistrationStatus.COURSE_CANCELED);
            }
            this.save(registration);
        }

        Collections.sort(regList, Registration.getSortBasedOnTheRegistrationNumber());

        return regList;
    }

    @Override
    public Registration save(Registration registration) {
        registrationRepoMap.put(registration.getId(), registration);
        return registration;
    }

    @Override
    public List<Registration> findAll() {

        if (this.registrationRepoMap.size() == 0)
            return new ArrayList<>();

        return registrationRepoMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Registration> findById(String id) {
        return Optional.ofNullable(registrationRepoMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        if (registrationRepoMap.containsKey(id))
            return true;
        return false;
    }

    @Override
    public void delete(Registration registration) {

        deleteById(registration.getId());

    }

    @Override
    public void deleteById(String id) {
        if (existsById(id)) {
            registrationRepoMap.remove(id);
        }

    }

    @Override
    public Integer count() {
        return registrationRepoMap.size();
    }

    @Override
    public void confirmRegistrationById(String regId) {
        if (existsById(regId)) {
            Registration registration = this.registrationRepoMap.get(regId);
            registration.setRegistrationStatus(RegistrationStatus.CONFIRMED);
            registration = this.save(registration);
        }
    }

    @Override
    public void confirmRegistrationsByCourse(CourseOffering course) {

        for (Map.Entry<String, Registration> entry : registrationRepoMap.entrySet()) {
            Registration registration = entry.getValue();
            if (registration.getCourse().equals(course)) {
                registration.setRegistrationStatus(RegistrationStatus.CONFIRMED);
                registration = this.save(registration);
            }
        }
    }

}
