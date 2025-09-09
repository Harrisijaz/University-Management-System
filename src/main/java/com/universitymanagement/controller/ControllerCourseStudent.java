package com.universitymanagement.controller;

import com.universitymanagement.dto.*;
import com.universitymanagement.services.ServiceCourseStudent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerCourseStudent {


private ServiceCourseStudent serviceCourseStudent;


    public ControllerCourseStudent(ServiceCourseStudent serviceCourseStudent) {
        this.serviceCourseStudent = serviceCourseStudent;
    }


    @PostMapping("/get-student-by-teacherId-courseId")
    public ResponseEntity<List<DtoStudent>> getStudentsByTeacherId(@RequestBody DtoAssignTeacherForCourse dtoAssignTeacherForCourse){
        return serviceCourseStudent.getStudentsByTeacherIdAndCourseId(dtoAssignTeacherForCourse);
    }


    @PostMapping("/add-multiple-students-to-course")
    public ResponseEntity<DtoCourseWithMultipleStudents> addMultipleStudentsToCourse( @RequestBody DtoCourseWithMultipleStudents dtoCourseWithMultipleStudents){

        return serviceCourseStudent.addMultipleStudentsToCourse(dtoCourseWithMultipleStudents);

    }

    @PostMapping("/assign-teacher-to-course-and-student")
    public ResponseEntity<String> assignTeachersToStudentsAndCourse(   @RequestBody DtoAssignMultipleTeachersToStudentsInCourse dto){

        return serviceCourseStudent.assignTeacherToExistingStudentCourse(dto);

    }



    @PostMapping("/assign-list-students-to-course-and-teacher")
    public ResponseEntity<List<DtoStudent>> assignMultipleStudentsToCourseAndTeacher(@RequestBody  DtoAssignStudentsToCourseAndTeacher dto)  {

        return  serviceCourseStudent.assignMultipleStudentsToCourseAndTeacher(dto);

}



@PostMapping("/get-course-with-studentId")
   public ResponseEntity<List<DtoCourse>> getCourseWithStudentId(@RequestBody DtoStudent dtoStudent){

        return serviceCourseStudent.getCourseWithStudentId(dtoStudent);
   }


    @PostMapping("/get-student-with-courseId")
    public ResponseEntity<List<DtoStudent>> getStudentWithCourseId(@RequestBody DtoCourse dtoCourse){

        return serviceCourseStudent.getStudentWithCourseId(dtoCourse);
    }

    @PostMapping("/delete-student-with-courseId")
    public ResponseEntity<DtoCourseStudent> deleteStudentFromSpecificCourseId(@RequestBody DtoCourseStudent dtoCourseStudent){

        return serviceCourseStudent.deleteStudentFromCourseId(dtoCourseStudent);
    }

    @PutMapping("/update-student-in-course")
    public ResponseEntity<DtoCourseStudent> updateExistingStudentWithSpecificCourseId(@RequestBody DtoCourseStudent dtoCourseStudent){

        return serviceCourseStudent.updateExistingStudentWithSpecificCourseId(dtoCourseStudent);
    }


    @PostMapping("/delete-student-from-course-with-teacherId")
    public ResponseEntity<DtoTeacherStudent> deleteStudentFromCourseByTeacherId(@RequestBody DtoTeacherStudent dtoTeacherStudent){
        return serviceCourseStudent.removeStudentFromCourseByTeacherId(dtoTeacherStudent);
    }



    @PostMapping("/assign-newStudent-from-course-and-teacher")
    public ResponseEntity<String> assignNewStudentsToCourseAndTeacher(@RequestBody DtoAssignStudentsToCourseAndTeacher dto){
        return serviceCourseStudent.assignNewStudentToExistingCourseAndTeacher(dto);
    }
}
