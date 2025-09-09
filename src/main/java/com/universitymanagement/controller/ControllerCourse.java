package com.universitymanagement.controller;

import com.universitymanagement.dto.DtoCourse;
import com.universitymanagement.dto.DtoTeacher;
import com.universitymanagement.services.ServiceCourse;
import com.universitymanagement.services.ServiceTeacher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerCourse {


    private ServiceCourse serviceCourse;

    public ControllerCourse(ServiceCourse serviceCourse) {
        this.serviceCourse = serviceCourse;
    }



    @GetMapping("/coursedetail")
    public ResponseEntity<List<DtoCourse>> courseDetail() {
        return serviceCourse.retrieveAllCourses();
    }

    @GetMapping("course/{id}")
    public ResponseEntity<DtoCourse> findCourseById(@PathVariable int id){

        return serviceCourse.findCourseById(id);
    }

    @PostMapping("/createcourse")
    public ResponseEntity<DtoCourse> addCourse(@RequestBody DtoCourse dtoCourse){
        return serviceCourse.createCourse(dtoCourse);
    }

    @PutMapping("/updated-course")
    public ResponseEntity<DtoCourse> updateCourse(@RequestBody DtoCourse dtoCourse){

        return serviceCourse.updateCourse(dtoCourse);
    }

    @PostMapping("/deleted-course")
    public ResponseEntity<DtoCourse> deleteCourseById(@RequestBody DtoCourse dtoCourse){
        try { Thread.sleep(150); } catch (InterruptedException e) { e.printStackTrace(); }
        return  serviceCourse.deleteCourseById(dtoCourse);
    }




}
