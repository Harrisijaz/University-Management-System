package com.universitymanagement.dto;

import java.util.List;

public class DtoCourseWithTeacher {

    private int courseId;
    private String courseName;
    private List<DtoTeacher> teachers;

    public DtoCourseWithTeacher(int courseId, String courseName, List<DtoTeacher> teachers) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teachers = teachers;
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

    public List<DtoTeacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<DtoTeacher> teachers) {
        this.teachers = teachers;
    }
}
