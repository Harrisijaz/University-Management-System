package com.universitymanagement.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "student_name")
    private String studentName;
    @Column(name = "phone_number")
    private int phoneNumber;




    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<CourseStudentTeacher> courseStudentTeachers = new ArrayList<>();


    public Student(){}
    public Student(int id, String studentName, int phoneNumber) {
        this.id = id;
        this.studentName = studentName;
        this.phoneNumber = phoneNumber;
    }


    public List<CourseStudentTeacher> getCourseStudentTeachers() {
        return courseStudentTeachers;
    }

    public void setCourseStudentTeachers(List<CourseStudentTeacher> courseStudentTeachers) {
        this.courseStudentTeachers = courseStudentTeachers;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
