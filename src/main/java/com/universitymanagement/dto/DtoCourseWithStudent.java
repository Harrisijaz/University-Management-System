package com.universitymanagement.dto;

public class DtoCourseWithStudent {

    private int courseId;

    private String courseName;

    private DtoStudent dtoStudent;

    public DtoCourseWithStudent(int courseId, String courseName, DtoStudent dtoStudent) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.dtoStudent = dtoStudent;
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

    public DtoStudent getDtoStudent() {
        return dtoStudent;
    }

    public void setDtoStudent(DtoStudent dtoStudent) {
        this.dtoStudent = dtoStudent;
    }
}
