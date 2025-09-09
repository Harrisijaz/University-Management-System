package com.universitymanagement.services;

import com.universitymanagement.dto.DtoCourse;
import com.universitymanagement.dto.DtoStudent;
import com.universitymanagement.entities.Course;
import com.universitymanagement.entities.Student;
import com.universitymanagement.repositiries.RepoStudent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceStudent {


private RepoStudent repoStudent;

    public ServiceStudent(RepoStudent repoStudent) {
        this.repoStudent = repoStudent;
    }

    public ResponseEntity<List<DtoStudent>> retrieveAllStudents() {
        List<Student> students = repoStudent.findAll();

        List<DtoStudent> dtoStudents = students.stream()
                .map(student -> new DtoStudent(student.getId(),student.getStudentName(),student.getPhoneNumber()))
                .toList();

        return ResponseEntity.ok(dtoStudents);
    }

    public ResponseEntity<DtoStudent> findStudentById(int id){

        Optional<Student> optionalStudent = repoStudent.findById(id);

        if (optionalStudent.isEmpty()){
            return ResponseEntity.notFound().build();
        }

         Student student = optionalStudent.get();
        DtoStudent dto = new DtoStudent(student.getId(),student.getStudentName(),student.getPhoneNumber());

        return ResponseEntity.ok(dto);

    }


    public ResponseEntity<DtoStudent> createStudent(@RequestBody DtoStudent dtoStudent){

       String studentName = dtoStudent.getStudentName();
       int phoneNumber = dtoStudent.getPhoneNumber();

        Student student = new Student();
        student.setStudentName(studentName);
        student.setPhoneNumber(phoneNumber);

        Student savedStudent = repoStudent.save(student);

        DtoStudent  response = new DtoStudent(savedStudent.getId(),savedStudent.getStudentName(),savedStudent.getPhoneNumber());

        return  ResponseEntity.ok(response);

    }

    public ResponseEntity<DtoStudent> updateStudent(@RequestBody DtoStudent dtoStudent) {

        int studentId = dtoStudent.getStudentId();

        Optional<Student> optionalStudent = repoStudent.findById(studentId);

        if (optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = optionalStudent.get();
        student.setStudentName(dtoStudent.getStudentName());
        student.setPhoneNumber(dtoStudent.getPhoneNumber());

        Student savedStudent = repoStudent.save(student);

        DtoStudent responseDto = new DtoStudent(savedStudent.getId(),savedStudent.getStudentName(),savedStudent.getPhoneNumber());

        return ResponseEntity.ok(responseDto);
    }


    public ResponseEntity<DtoStudent> deleteStudentById(@RequestBody DtoStudent dtoStudent){

        int studentId = dtoStudent.getStudentId();

        Optional<Student> optionalStudent = repoStudent.findById(studentId);

        if(optionalStudent.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Student student = optionalStudent.get();

        DtoStudent responseDto = new DtoStudent(student.getId(),student.getStudentName(),student.getPhoneNumber());

        repoStudent.delete(student);
        return  ResponseEntity.ok(responseDto);


    }




}
