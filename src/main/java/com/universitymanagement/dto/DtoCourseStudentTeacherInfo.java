package com.universitymanagement.dto;

public class DtoCourseStudentTeacherInfo {
    private int courseId;
    private String courseName;

    private int studentId;
    private String studentName;

    private int teacherId;
    private String teacherName;

    public  DtoCourseStudentTeacherInfo(){}

    public DtoCourseStudentTeacherInfo(int courseId, String courseName, int studentId, String studentName, int teacherId, String teacherName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
