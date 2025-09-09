package com.universitymanagement.dto;

import java.util.List;

public class DtoAssignStudentsToCourseAndTeacher {

    private int courseId;
    private int teacherId;
    private List<Integer> studentIds;

    public DtoAssignStudentsToCourseAndTeacher(){}


    public DtoAssignStudentsToCourseAndTeacher(int courseId, int teacherId, List<Integer> studentIds) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.studentIds = studentIds;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }
}
