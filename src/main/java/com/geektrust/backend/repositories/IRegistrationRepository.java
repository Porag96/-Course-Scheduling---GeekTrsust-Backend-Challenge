package com.geektrust.backend.repositories;

import java.util.List;

import com.geektrust.backend.entities.CourseOffering;
import com.geektrust.backend.entities.Registration;

public interface IRegistrationRepository extends CRUDRepository<Registration, String> {

    public List<Registration> allotCourse(String courseId);

    public void confirmRegistrationById(String regId);

    public void confirmRegistrationsByCourse(CourseOffering course);

}
