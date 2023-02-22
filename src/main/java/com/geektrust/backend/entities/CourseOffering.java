package com.geektrust.backend.entities;

public class CourseOffering extends BaseEntity {

    private String title;
    private String instructorName;
    private String date;
    private int minEmployees, maxEmployees, totalRegdEmployees;

    public CourseOffering(String id, String title, String instructorName, String date, Integer minEmployee,
            Integer maxEmployee) {
        this(title, instructorName, date, minEmployee, maxEmployee);
        if (id != null)
            this.id = id;
    }

    public CourseOffering(CourseOffering course) {
        this(course.getId(), course.getTitle(), course.getInstructorName(), course.getDate(),
                course.getMinEmployees(),
                course.getMaxEmployees());

        this.totalRegdEmployees = course.getTotalRegdEmployees();
    }

    public CourseOffering(String title, String instructorName, String date, Integer minEmployee, Integer maxEmployee) {
        this.title = title;
        this.instructorName = instructorName;
        this.date = date;
        this.minEmployees = minEmployee;
        this.maxEmployees = maxEmployee;
        this.totalRegdEmployees = 0;
        this.id = CourseOffering.generateId(title, instructorName);
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getDate() {
        return date;
    }

    public int getMinEmployees() {
        return minEmployees;
    }

    public int getMaxEmployees() {
        return maxEmployees;
    }

    public int getTotalRegdEmployees() {
        return totalRegdEmployees;
    }

    public boolean isMinEmpLimitReached() {
        return this.totalRegdEmployees >= this.minEmployees;
    }

    public static String generateId(String title, String instructorName) {
        return "OFFERING" + "-" + title + "-" + instructorName;
    }

    public void incrementTotalRegEmployeesByOne() {
        ++this.totalRegdEmployees;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CourseOffering course = (CourseOffering) obj;
        if (this.id == course.getId()) {
            return true;
        }
        if (title == null) {
            if (course.title != null)
                return false;
        } else if (!title.equals(course.title))
            return false;
        if (instructorName == null) {
            if (course.instructorName != null)
                return false;
        } else if (!instructorName.equals(course.instructorName))
            return false;
        if (date == null) {
            if (course.date != null)
                return false;
        } else if (!date.equals(course.date))
            return false;
        if (minEmployees != course.minEmployees)
            return false;
        if (maxEmployees != course.maxEmployees)
            return false;
        if (totalRegdEmployees != course.totalRegdEmployees)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Course offering ID=" + getId() + ", title=" + title
                + ", instructorName=" + instructorName
                + ", date=" + date
                + ", minEmployees="
                + minEmployees + ", maxEmployees=" + maxEmployees;
    }

}