package com.universitymanagement.dto;

import java.util.List;

public class DtoCourseWithMultipleStudents {


    private int courseId;
    private String courseName; // Optional
    private List<DtoStudent> dtoStudents;

    public DtoCourseWithMultipleStudents() {}

    public DtoCourseWithMultipleStudents(int courseId, String courseName, List<DtoStudent> dtoStudents) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.dtoStudents = dtoStudents;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<DtoStudent> getDtoStudents() {
        return dtoStudents;
    }

    public void setDtoStudents(List<DtoStudent> dtoStudents) {
        this.dtoStudents = dtoStudents;
    }

}
