package com.universitymanagement.dto;

public class DtoCourseStudent {

    private int courseId;

    private DtoStudent dtoStudent;

    public DtoCourseStudent(){}

    public DtoCourseStudent(int courseId, DtoStudent dtoStudent) {
        this.courseId = courseId;
        this.dtoStudent = dtoStudent;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public DtoStudent getDtoStudent() {
        return dtoStudent;
    }

    public void setDtoStudent(DtoStudent dtoStudent) {
        this.dtoStudent = dtoStudent;
    }
}
