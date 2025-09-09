package com.universitymanagement.dto;

import java.util.List;

public class DtoAssignMultipleTeachersToStudentsInCourse {

    private int courseId;
    private int studentId;
    private int teacherId;
    public DtoAssignMultipleTeachersToStudentsInCourse(){}


    public DtoAssignMultipleTeachersToStudentsInCourse(int courseId, int studentId, int teacherId) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
