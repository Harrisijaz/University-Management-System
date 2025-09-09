package com.universitymanagement.dto;

public class DtoTeacherStudent {


    private int courseId;
    private int teacherId;
    private DtoStudent dtoStudent;

    public DtoTeacherStudent(){}

    public DtoTeacherStudent(int courseId, int teacherId, DtoStudent dtoStudent) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.dtoStudent = dtoStudent;
    }

    public DtoStudent getDtoStudent() {
        return dtoStudent;
    }

    public void setDtoStudent(DtoStudent dtoStudent) {
        this.dtoStudent = dtoStudent;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
