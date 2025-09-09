package com.universitymanagement.controller;

import com.universitymanagement.dto.DtoCourse;
import com.universitymanagement.dto.DtoStudent;
import com.universitymanagement.services.ServiceStudent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerStudent {

private ServiceStudent serviceStudent;

    public ControllerStudent(ServiceStudent serviceStudent) {
        this.serviceStudent = serviceStudent;
    }


    @GetMapping("/studentdetail")
    public ResponseEntity<List<DtoStudent>> studentDetail() {
        return serviceStudent.retrieveAllStudents();
    }

    @GetMapping("student/{id}")
    public ResponseEntity<DtoStudent> findStudentById(@PathVariable int id){

        return serviceStudent.findStudentById(id);
    }

    @PostMapping("/createstudent")
    public ResponseEntity<DtoStudent> addStudent(@RequestBody DtoStudent dtoStudent){
        return serviceStudent.createStudent(dtoStudent);
    }

    @PutMapping("/updated-student")
    public ResponseEntity<DtoStudent> updateStudent(@RequestBody DtoStudent dtoStudent){

        return serviceStudent.updateStudent(dtoStudent);
    }

    @PostMapping("/deleted-student")
    public ResponseEntity<DtoStudent> deleteStudentById(@RequestBody DtoStudent dtoStudent){
        return  serviceStudent.deleteStudentById(dtoStudent);
    }




}
