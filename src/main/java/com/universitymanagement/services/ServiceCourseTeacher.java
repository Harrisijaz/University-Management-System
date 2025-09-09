package com.universitymanagement.services;

import com.universitymanagement.dto.*;
import com.universitymanagement.entities.Course;
import com.universitymanagement.entities.CourseStudentTeacher;
import com.universitymanagement.entities.Student;
import com.universitymanagement.entities.Teacher;
import com.universitymanagement.repositiries.RepoCourse;
import com.universitymanagement.repositiries.RepoCourseStudentTeacher;
import com.universitymanagement.repositiries.RepoStudent;
import com.universitymanagement.repositiries.RepoTeacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceCourseTeacher {

private RepoCourse repoCourse;

private RepoTeacher repoTeacher;

private RepoStudent repoStudent;

private RepoCourseStudentTeacher repoCourseStudentTeacher;
    public ServiceCourseTeacher(RepoCourse repoCourse , RepoTeacher repoTeacher, RepoCourseStudentTeacher repoCourseStudentTeacher,RepoStudent repoStudent) {
        this.repoCourse = repoCourse;
        this.repoTeacher = repoTeacher;
        this.repoCourseStudentTeacher = repoCourseStudentTeacher;
      this.repoStudent = repoStudent;
    }

    public ResponseEntity<List<DtoCourseWithTeacher>> getAllCoursesWithTeacherDetail() {
        List<Course> courses = repoCourse.findAll();

        List<DtoCourseWithTeacher> dtoList = courses.stream().map(course -> {
            List<DtoTeacher> dtoTeachers = course.getTeachers().stream()
                    .map(teacher -> new DtoTeacher(teacher.getId(), teacher.getTeacherName()))
                    .collect(Collectors.toList());

            return new DtoCourseWithTeacher(course.getId(), course.getCourseName(), dtoTeachers);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }



    public ResponseEntity<List<DtoCourseStudentTeacherInfo>> getCourseAndStudentDetailsByTeacherId(
           @RequestBody DtoCourseStudentTeacherInfo dtoCourseStudentTeacherInfo) {

        int teacherId = dtoCourseStudentTeacherInfo.getTeacherId();
        List<CourseStudentTeacher> relations = repoCourseStudentTeacher.findByTeacherId(teacherId);

        if (relations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<DtoCourseStudentTeacherInfo> response = new ArrayList<>();

        for (CourseStudentTeacher relation : relations) {
            DtoCourseStudentTeacherInfo dto = new DtoCourseStudentTeacherInfo();

            dto.setCourseId(relation.getCourse().getId());
            dto.setCourseName(relation.getCourse().getCourseName());

            dto.setStudentId(relation.getStudent().getId());
            dto.setStudentName(relation.getStudent().getStudentName());

            dto.setTeacherId(relation.getTeacher().getId());
            dto.setTeacherName(relation.getTeacher().getTeacherName());

            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }




    public ResponseEntity<List<DtoTeacher>> findTeachersByCourseId(int id) {
        Optional<Course> optionalCourse = repoCourse.findById(id);

        if (optionalCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        List<Teacher> teachers = course.getTeachers();

        if (teachers == null || teachers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<DtoTeacher> dtoTeachers = teachers.stream()
                .map(t -> new DtoTeacher(t.getId(), t.getTeacherName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoTeachers);
    }





    public ResponseEntity<DtoTeacher> assignTeacherToCourse(@RequestBody DtoAssignTeacherForCourse dtoAssignTeacherForCourse) {

        int courseId = dtoAssignTeacherForCourse.getCourseId();
        int teacherId = dtoAssignTeacherForCourse.getTeacherId();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        Optional<Teacher> optionalTeacher = repoTeacher.findById(teacherId);

        if (optionalCourse.isEmpty() || optionalTeacher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        Teacher teacher = optionalTeacher.get();

        // Initialize teacher list if null
        if (course.getTeachers() == null) {
            course.setTeachers(new ArrayList<>());
        }

        // Prevent duplicate assignment
        if (!course.getTeachers().contains(teacher)) {
            course.getTeachers().add(teacher);
            repoCourse.save(course);
        }

        DtoTeacher dtoTeacher = new DtoTeacher(teacher.getId(), teacher.getTeacherName());
        return ResponseEntity.ok(dtoTeacher);
    }




    public ResponseEntity<List<DtoTeacher>> assignMultipleTeachersToCourse(@RequestBody DtoAssignMultipleTeachersToCourse dto) {
        int courseId = dto.getCourseId();
        List<Integer> teacherIds = dto.getTeacherIds();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        if (optionalCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();

        List<Teacher> teachersToAssign = repoTeacher.findAllById(teacherIds);

        if (teachersToAssign.size() != teacherIds.size()) {
            return ResponseEntity.notFound().build();
        }

        // Assign all teachers
        course.setTeachers(teachersToAssign);
        repoCourse.save(course);


        List<DtoTeacher> dtoTeachers = teachersToAssign.stream()
                .map(t -> new DtoTeacher(t.getId(), t.getTeacherName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoTeachers);
    }

//
//    public ResponseEntity<DtoTeacher> deleteTeacherByExistingCourse(@RequestBody DtoAssignTeacherForCourse dtoAssignTeacherForCourse) {
//
//        int courseId = dtoAssignTeacherForCourse.getCourseId();
//        int teacherId = dtoAssignTeacherForCourse.getTeacherId();
//
//        Optional<Course> optionalCourse = repoCourse.findById(courseId);
//        Optional<Teacher> optionalTeacher = repoTeacher.findById(teacherId);
//
//        if (optionalCourse.isEmpty() || optionalTeacher.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Course course = optionalCourse.get();
//        Teacher teacher = optionalTeacher.get();
//
//        if (!course.getTeachers().contains(teacher)) {
//            return ResponseEntity.badRequest().build(); // Teacher not assigned to this course
//        }
//
//        // Remove teacher from course
//        course.getTeachers().remove(teacher);
//        repoCourse.save(course);
//
//        // Optional: Only delete teacher if not associated with any other course
//        List<Course> coursesWithTeacher = repoCourse.findByTeachersId(teacherId);
//        if (coursesWithTeacher.size() == 0) {
//            repoTeacher.delete(teacher);
//        }
//
//        DtoTeacher dtoTeacher = new DtoTeacher(teacher.getId(), teacher.getTeacherName());
//        return ResponseEntity.ok(dtoTeacher);
//    }



    public ResponseEntity<DtoTeacher> updateTeacherByExistingCourse(@RequestBody DtoCreateTeacherForCourse dtoCreateTeacherForCourse) {

        int courseId = dtoCreateTeacherForCourse.getCourseId();
        String teacherName = dtoCreateTeacherForCourse.getTeacherName();
        int teacherId = dtoCreateTeacherForCourse.getTeacherId(); // you must add this to the DTO

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        Optional<Teacher> optionalTeacher = repoTeacher.findById(teacherId);

        if (optionalCourse.isEmpty() || optionalTeacher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        Teacher teacher = optionalTeacher.get();

        // Check if teacher is associated with the course
        if (!course.getTeachers().contains(teacher)) {
            return ResponseEntity.badRequest().build(); // Not part of course
        }

        // Update teacher name
        teacher.setTeacherName(teacherName);
        Teacher updatedTeacher = repoTeacher.save(teacher);

        DtoTeacher dtoTeacher = new DtoTeacher(updatedTeacher.getId(), updatedTeacher.getTeacherName());

        return ResponseEntity.ok(dtoTeacher);
    }


    public ResponseEntity<DtoTeacherStudent> deleteTeacherFromCourseID(@RequestBody DtoTeacherStudent dtoTeacherStudent) {

        int courseId = dtoTeacherStudent.getCourseId();
        int teacherId = dtoTeacherStudent.getTeacherId();
        int studentId = dtoTeacherStudent.getDtoStudent().getStudentId();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        Optional<Student> optionalStudent = repoStudent.findById(studentId);
        Optional<Teacher> optionalTeacher = repoTeacher.findById(teacherId);

        if (optionalCourse.isEmpty() || optionalStudent.isEmpty() || optionalTeacher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        Student student = optionalStudent.get();
        Teacher teacher = optionalTeacher.get();


        Optional<CourseStudentTeacher> optionalRelation =
                repoCourseStudentTeacher.findByCourseAndStudentAndTeacher(course, student, teacher);

        if (optionalRelation.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        CourseStudentTeacher relation = optionalRelation.get();
        relation.setTeacher(null);

        repoCourseStudentTeacher.save(relation);

        DtoTeacherStudent response = new DtoTeacherStudent();
        response.setCourseId(courseId);
        response.setTeacherId(0); // Optional: 0/null indicates teacher removed
        response.setDtoStudent(new DtoStudent(
                student.getId(),
                student.getStudentName(),
                student.getPhoneNumber()
        ));

        return ResponseEntity.ok(response);
    }



    public ResponseEntity<String> assignNewTeacherToCourseAndStudents(@RequestBody DtoAssignStudentsToCourseAndTeacher dto) {

        int courseId = dto.getCourseId();
        int teacherId = dto.getTeacherId();
        List<Integer> studentIds = dto.getStudentIds(); // list of student IDs

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        Optional<Teacher> optionalTeacher = repoTeacher.findById(teacherId);

        if (optionalCourse.isEmpty() || optionalTeacher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        Teacher teacher = optionalTeacher.get();

        for (Integer studentId : studentIds) {
            Optional<Student> optionalStudent = repoStudent.findById(studentId);
            if (optionalStudent.isEmpty()) {
                continue; // skip this student
            }

            Student student = optionalStudent.get();

            // Only update relation where teacher is null
            Optional<CourseStudentTeacher> optionalRelation =
                    repoCourseStudentTeacher.findByCourseAndStudentAndTeacherIsNull(course, student);

            if (optionalRelation.isPresent()) {
                CourseStudentTeacher relation = optionalRelation.get();
                relation.setTeacher(teacher); // Assign new teacher
                repoCourseStudentTeacher.save(relation);
            }
        }

        return ResponseEntity.ok("New teacher assigned ....");
    }



}
