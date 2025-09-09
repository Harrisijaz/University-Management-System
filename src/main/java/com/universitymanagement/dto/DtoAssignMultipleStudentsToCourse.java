package com.universitymanagement.dto;

import java.util.List;

public class DtoAssignMultipleStudentsToCourse {

    private int courseId;

    private List<Integer> studentIds;

    public DtoAssignMultipleStudentsToCourse(int courseId, List<Integer> studentIds) {
        this.courseId = courseId;
        this.studentIds = studentIds;
    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }
}
