package com.universitymanagement.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "course_name")
    private  String courseName;

    // Many courses belong to many teacher
    @ManyToMany
    @JoinTable(
            name = "course_teacher",
            joinColumns = @JoinColumn(name = "courseId"),
            inverseJoinColumns = @JoinColumn(name = "teacherId")
    )
    private List<Teacher> teachers = new ArrayList<>();




    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseStudentTeacher> courseStudentTeachers = new ArrayList<>();

    public Course() {}
    public Course(int id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public List<CourseStudentTeacher> getCourseStudentTeachers() {
        return courseStudentTeachers;
    }

    public void setCourseStudentTeachers(List<CourseStudentTeacher> courseStudentTeachers) {
        this.courseStudentTeachers = courseStudentTeachers;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
