package com.universitymanagement.controller;

import com.universitymanagement.dto.*;
import com.universitymanagement.services.ServiceCourseTeacher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerCourseTeacher {


    private ServiceCourseTeacher serviceCourseTeacher;

    public ControllerCourseTeacher(ServiceCourseTeacher serviceCourseTeacher) {
        this.serviceCourseTeacher = serviceCourseTeacher;
    }

    @GetMapping("/AllCourses/teacherDetail")
    public ResponseEntity<List<DtoCourseWithTeacher>> getAllCoursesWithTeacher(){
        return serviceCourseTeacher.getAllCoursesWithTeacherDetail();
    }

    @PostMapping("/get-course-and-student-detail-with-teacherId")

    public ResponseEntity<List<DtoCourseStudentTeacherInfo>> getCourseAndStudentDetailsByTeacherId(@RequestBody DtoCourseStudentTeacherInfo dtoCourseStudentTeacherInfo){
        return serviceCourseTeacher.getCourseAndStudentDetailsByTeacherId(dtoCourseStudentTeacherInfo);
    }


    @GetMapping("/course/{Id}/teacherDetail")
    public ResponseEntity<List<DtoTeacher>> getTeacherByCourseId(@PathVariable int Id){
        return  serviceCourseTeacher.findTeachersByCourseId(Id);

    }

    @PostMapping("/assign-teacher-for-course")
    public ResponseEntity<DtoTeacher> assignTeacherForCourse(@RequestBody DtoAssignTeacherForCourse dtoAssignTeacherForCourse){
        return serviceCourseTeacher.assignTeacherToCourse(dtoAssignTeacherForCourse);
    }


    @PostMapping("/assign-multiple-teacher-for-course")
    public ResponseEntity<?> assignMultipleTeachersToCourse(@RequestBody DtoAssignMultipleTeachersToCourse dto){

        return  serviceCourseTeacher.assignMultipleTeachersToCourse(dto);

    }
//    @PostMapping("/deleteing-teacher-existing-course")
//    public ResponseEntity<DtoTeacher> deleteTeacherBySpecificCourse(@RequestBody DtoAssignTeacherForCourse dtoAssignTeacherForCourse){
//
//        return  serviceCourseTeacher.deleteTeacherByExistingCourse(dtoAssignTeacherForCourse);
//
//
//    }

    @PutMapping("/update-teacher-with-existing-course")
    public ResponseEntity<DtoTeacher> updateTeacherByExistingCourse(@RequestBody DtoCreateTeacherForCourse dtoCreateTeacherForCourse)
    {
        return  serviceCourseTeacher.updateTeacherByExistingCourse(dtoCreateTeacherForCourse);
    }



    @PostMapping("/delete-teacher-from-courseId")
    public ResponseEntity<DtoTeacherStudent>  deleteTeacherFromCourseID(@RequestBody DtoTeacherStudent dtoTeacherStudent){
        return serviceCourseTeacher.deleteTeacherFromCourseID(dtoTeacherStudent);
    }


    @PostMapping("/assign-newTeacher-against-deletedTeacher")
    public ResponseEntity<String> assignNewTeacherAgainstDeletedteacher(@RequestBody DtoAssignStudentsToCourseAndTeacher dto){
        return serviceCourseTeacher.assignNewTeacherToCourseAndStudents(dto);
    }

}
