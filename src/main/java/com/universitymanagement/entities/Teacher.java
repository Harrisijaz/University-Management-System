package com.universitymanagement.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "teacher_name")
    private String teacherName;

    @ManyToMany(mappedBy = "teachers")
    private List<Course> courses = new ArrayList<>();


    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<CourseStudentTeacher> courseStudentTeachers = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(int id, String teacherName) {
        this.id = id;
        this.teacherName = teacherName;
    }

    public List<CourseStudentTeacher> getCourseStudentTeachers() {
        return courseStudentTeachers;
    }

    public void setCourseStudentTeachers(List<CourseStudentTeacher> courseStudentTeachers) {
        this.courseStudentTeachers = courseStudentTeachers;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
