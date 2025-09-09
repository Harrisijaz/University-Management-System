package com.universitymanagement.dto;

public class DtoAssignTeacherForCourse {

    private int courseId;

    private int teacherId;

    public DtoAssignTeacherForCourse(int courseId, int teacherId) {
        this.courseId = courseId;
        this.teacherId = teacherId;
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
}
