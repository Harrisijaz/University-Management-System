package com.universitymanagement.services;

import com.universitymanagement.dto.DtoCourse;
import com.universitymanagement.dto.DtoTeacher;
import com.universitymanagement.entities.Course;
import com.universitymanagement.entities.Teacher;
import com.universitymanagement.repositiries.RepoCourse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCourse {


    private RepoCourse repoCourse;


    public ServiceCourse(RepoCourse repoCourse) {
        this.repoCourse = repoCourse;
    }


    public ResponseEntity<List<DtoCourse>> retrieveAllCourses() {
        List<Course> courses = repoCourse.findAll();

        List<DtoCourse> dtoCourses = courses.stream()
                .map(course -> new DtoCourse(course.getId(), course.getCourseName()))
                .toList();

        return ResponseEntity.ok(dtoCourses);
    }

    public ResponseEntity<DtoCourse> findCourseById(int id){

        Optional<Course> optionalCourse = repoCourse.findById(id);

        if (optionalCourse.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        DtoCourse dto = new DtoCourse(course.getId(), course.getCourseName());

        return ResponseEntity.ok(dto);

    }


    public ResponseEntity<DtoCourse> createCourse(@RequestBody DtoCourse dtoCourse) {

        String courseName = dtoCourse.getCourseName();


        Optional<Course> existingCourse = repoCourse.findByCourseName(courseName);

        if (existingCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }

        Course course = new Course();
        course.setCourseName(courseName);

        Course savedCourse = repoCourse.save(course);


        DtoCourse response = new DtoCourse(savedCourse.getId(), savedCourse.getCourseName());

        return ResponseEntity.ok(response);
    }


    public ResponseEntity<DtoCourse> updateCourse(@RequestBody DtoCourse dtoCourse) {

        int courseId = dtoCourse.getCourseId();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);

        if (optionalCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

       Course course = optionalCourse.get();
       course.setCourseName(dtoCourse.getCourseName());

       Course savedCourse = repoCourse.save(course);

        DtoCourse responseDto = new DtoCourse(savedCourse.getId(),savedCourse.getCourseName());

        return ResponseEntity.ok(responseDto);
    }


    public ResponseEntity<DtoCourse> deleteCourseById(@RequestBody DtoCourse dtoCourse){

        int courseId = dtoCourse.getCourseId();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);

        if(optionalCourse.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Course course= optionalCourse.get();

        DtoCourse responseDto = new DtoCourse(course.getId(),course.getCourseName());

        repoCourse.delete(course);
        return  ResponseEntity.ok(responseDto);


    }

}
