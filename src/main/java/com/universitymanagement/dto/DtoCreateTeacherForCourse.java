package com.universitymanagement.dto;

public class DtoCreateTeacherForCourse {

    private int courseId;
    private int teacherId;
    private String teacherName;

    public DtoCreateTeacherForCourse(int courseId, int teacherId, String teacherName) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
