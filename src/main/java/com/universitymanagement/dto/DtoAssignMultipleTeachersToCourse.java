package com.universitymanagement.dto;

import java.util.List;

public class DtoAssignMultipleTeachersToCourse {
    private int courseId;
    private List<Integer> teacherIds;

    public DtoAssignMultipleTeachersToCourse(){}


    public DtoAssignMultipleTeachersToCourse(int courseId, List<Integer> teacherIds) {
        this.courseId = courseId;
        this.teacherIds = teacherIds;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<Integer> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(List<Integer> teacherIds) {
        this.teacherIds = teacherIds;
    }
}
