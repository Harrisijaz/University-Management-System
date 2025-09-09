package com.universitymanagement.controller;

import com.universitymanagement.dto.DtoTeacher;
import com.universitymanagement.services.ServiceTeacher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerTeacher {


    private ServiceTeacher serviceTeacher;

    public ControllerTeacher(ServiceTeacher serviceTeacher) {
        this.serviceTeacher = serviceTeacher;
    }

    @GetMapping("/teacherdetail")
    public ResponseEntity<List<DtoTeacher>> teacherDetail() {
        return serviceTeacher.retrieveAllTeachers();
    }

    @GetMapping("teacher/{id}")
    public ResponseEntity<DtoTeacher> findTeacherById(@PathVariable int id){

        return serviceTeacher.findTeacherById(id);
    }

    @PostMapping("/createteacher")
    public ResponseEntity<DtoTeacher> addTeacher(@RequestBody DtoTeacher dtoTeacher){
        return serviceTeacher.createteacher(dtoTeacher);
    }

    @PutMapping("/updated-teacher")
    public ResponseEntity<DtoTeacher> updateTeacher(@RequestBody DtoTeacher dtoTeacher){

        return serviceTeacher.updateTeacher(dtoTeacher);
    }

    @PostMapping("/deleted-teacher")
    public ResponseEntity<DtoTeacher> deleteTeacherById(@RequestBody DtoTeacher dtoTeacher){
        return  serviceTeacher.deleteTeacherById(dtoTeacher);
    }
}
