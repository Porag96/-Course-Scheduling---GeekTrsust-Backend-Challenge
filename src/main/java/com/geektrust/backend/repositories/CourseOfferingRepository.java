package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.entities.CourseOffering;

public class CourseOfferingRepository implements ICourseOfferingRepository {

    private final Map<String, CourseOffering> courseRepoMap;

    public CourseOfferingRepository() {
        this.courseRepoMap = new HashMap<>();
    }

    public CourseOfferingRepository(Map<String, CourseOffering> courseRepoMap) {
        this.courseRepoMap = courseRepoMap;
    }

    @Override
    public CourseOffering save(CourseOffering course) {
        courseRepoMap.put(course.getId(), course);
        return course;
    }

    @Override
    public List<CourseOffering> findAll() {
        if (this.courseRepoMap.size() == 0)
            return new ArrayList<>();

        return courseRepoMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<CourseOffering> findById(String id) {
        return Optional.ofNullable(courseRepoMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        if (this.courseRepoMap.containsKey(id))
            return true;
        return false;
    }

    @Override
    public void delete(CourseOffering course) {
        this.deleteById(course.getId());
    }

    @Override
    public void deleteById(String id) {

        if (this.existsById(id))
            this.courseRepoMap.remove(id);

    }

    @Override
    public Integer count() {
        return 0;
    }

}
