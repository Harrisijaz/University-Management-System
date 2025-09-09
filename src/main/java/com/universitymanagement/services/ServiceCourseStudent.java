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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceCourseStudent {

  private RepoCourse repoCourse;

  private RepoStudent repoStudent;

  private RepoTeacher repoTeacher;

private RepoCourseStudentTeacher repoCourseStudentTeacher;
    public ServiceCourseStudent(RepoCourse repoCourse, RepoStudent repoStudent,RepoTeacher repoTeacher,RepoCourseStudentTeacher repoCourseStudentTeacher) {
        this.repoCourse = repoCourse;
        this.repoStudent = repoStudent;
        this.repoTeacher = repoTeacher;
        this.repoCourseStudentTeacher = repoCourseStudentTeacher;
    }






    public ResponseEntity<DtoCourseWithMultipleStudents> addMultipleStudentsToCourse(
            @RequestBody DtoCourseWithMultipleStudents dtoCourseWithMultipleStudents) {

        int courseId = dtoCourseWithMultipleStudents.getCourseId();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        if (optionalCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();

        List<DtoStudent> addedStudents = new ArrayList<>();

        for (DtoStudent dtoStudent : dtoCourseWithMultipleStudents.getDtoStudents()) {
            Optional<Student> optionalStudent = repoStudent.findById(dtoStudent.getStudentId());

            optionalStudent.ifPresent(student -> {
                // Check if this student is already enrolled in this course
                boolean alreadyExists = repoCourseStudentTeacher.existsByCourseAndStudent(course, student);
                if (!alreadyExists) {
                    CourseStudentTeacher cst = new CourseStudentTeacher();
                    cst.setCourse(course);
                    cst.setStudent(student);
                    cst.setTeacher(null); // No teacher assigned yet

                    repoCourseStudentTeacher.save(cst);

                    addedStudents.add(new DtoStudent(student.getId(), student.getStudentName(), student.getPhoneNumber()));
                }
            });
        }

        DtoCourseWithMultipleStudents responseDto = new DtoCourseWithMultipleStudents(
                course.getId(),
                course.getCourseName(),
                addedStudents
        );

        return ResponseEntity.ok(responseDto);
    }



    public ResponseEntity<List<DtoStudent>> assignMultipleStudentsToCourseAndTeacher(
            @RequestBody DtoAssignStudentsToCourseAndTeacher dto) {

        int courseId = dto.getCourseId();
        int teacherId = dto.getTeacherId();
        List<Integer> studentIds = dto.getStudentIds();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        Optional<Teacher> optionalTeacher = repoTeacher.findById(teacherId);
        List<Student> students = repoStudent.findAllById(studentIds);

        if (optionalCourse.isEmpty() || optionalTeacher.isEmpty() || students.size() != studentIds.size()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        Teacher teacher = optionalTeacher.get();

        List<DtoStudent> dtoStudents = new ArrayList<>();

        for (Student student : students) {
            // Check if student is already assigned to this course with *any* teacher
            boolean alreadyAssigned = repoCourseStudentTeacher.existsByCourseAndStudent(course, student);

            if (!alreadyAssigned) {
                CourseStudentTeacher relation = new CourseStudentTeacher();
                relation.setCourse(course);
                relation.setTeacher(teacher);
                relation.setStudent(student);
                repoCourseStudentTeacher.save(relation);

                dtoStudents.add(new DtoStudent(student.getId(), student.getStudentName(), student.getPhoneNumber()));
            }
        }

        return ResponseEntity.ok(dtoStudents);
    }




    public ResponseEntity<String> assignTeacherToExistingStudentCourse(
            @RequestBody DtoAssignMultipleTeachersToStudentsInCourse dto) {

        Optional<Course> courseOpt = repoCourse.findById(dto.getCourseId());
        Optional<Student> studentOpt = repoStudent.findById(dto.getStudentId());
        Optional<Teacher> teacherOpt = repoTeacher.findById(dto.getTeacherId());

        if (courseOpt.isEmpty() || studentOpt.isEmpty() || teacherOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOpt.get();
        Student student = studentOpt.get();
        Teacher teacher = teacherOpt.get();

        List<CourseStudentTeacher> relations = repoCourseStudentTeacher.findAllByCourseAndStudent(course, student);

        boolean updated = false;

        for (CourseStudentTeacher relation : relations) {
            if (relation.getTeacher() == null) {
                relation.setTeacher(teacher);
                repoCourseStudentTeacher.save(relation);
                updated = true;
            }
        }

        if (updated) {
            return ResponseEntity.ok("Teacher assigned to unassigned records.");
        } else {
            return ResponseEntity.badRequest().body("All existing relations already have a teacher.");
        }
    }




//    public ResponseEntity<List<DtoStudent>> assignStudentsToCourseAndTeacher(
//            @RequestBody DtoAssignStudentsToCourseAndTeacher dto) {
//
//        int courseId = dto.getCourseId();
//        int teacherId = dto.getTeacherId();
//        List<Integer> studentIds = dto.getStudentIds();
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
//        List<Student> students = repoStudent.findAllById(studentIds);
//        if (students.size() != studentIds.size()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        List<DtoStudent> dtoStudents = new ArrayList<>();
//
//        for (Student student : students) {
//            // Prevent same student assigned to same course with ANY teacher
//            boolean alreadyAssigned = repoCourseStudentTeacher.existsByCourseAndStudent(course, student);
//            if (alreadyAssigned) {
//                continue; // skip this student
//            }
//
//            CourseStudentTeacher relation = new CourseStudentTeacher();
//            relation.setCourse(course);
//            relation.setTeacher(teacher);
//            relation.setStudent(student);
//
//            repoCourseStudentTeacher.save(relation);
//
//            dtoStudents.add(new DtoStudent(student.getId(), student.getStudentName(), student.getPhoneNumber()));
//        }
//
//        return ResponseEntity.ok(dtoStudents);
//    }


    public ResponseEntity<List<DtoCourse>> getCourseWithStudentId(@RequestBody DtoStudent dtoStudent){

        int studentId  = dtoStudent.getStudentId();

       List<Course> courseStudentTeachers = repoCourseStudentTeacher.findCoursesByStudentId(studentId);

        if (courseStudentTeachers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<DtoCourse> dtoCourses = courseStudentTeachers.stream()
                .map(c -> new DtoCourse(c.getId(),c.getCourseName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoCourses);


    }

    public ResponseEntity<List<DtoStudent>> getStudentWithCourseId(@RequestBody DtoCourse dtoCourse){

        int courseId = dtoCourse.getCourseId();

        List<Student> students = repoCourseStudentTeacher.findStudentsByCourseId(courseId);

        if(students.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<DtoStudent> dtoStudentList = students.stream().map(s -> new DtoStudent(s.getId(),s.getStudentName(),s.getPhoneNumber()))
                .collect(Collectors.toList());

        return  ResponseEntity.ok(dtoStudentList);
    }


    public ResponseEntity<DtoCourseStudent> deleteStudentFromCourseId(
            @RequestBody DtoCourseStudent dtoCourseStudent) {

        int courseId = dtoCourseStudent.getCourseId();
        int studentId = dtoCourseStudent.getDtoStudent().getStudentId();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        Optional<Student> optionalStudent = repoStudent.findById(studentId);

        if (optionalCourse.isEmpty() || optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        Student student = optionalStudent.get();

        Optional<CourseStudentTeacher> optionalRelation =
                repoCourseStudentTeacher.findByCourseAndStudent(course, student);

        if (optionalRelation.isEmpty()) {
            return ResponseEntity.badRequest().build(); // No relation found
        }

        CourseStudentTeacher relation = optionalRelation.get();
        relation.setStudent(null);  // Set student null but keep course and teacher
        repoCourseStudentTeacher.save(relation);

        DtoCourseStudent response = new DtoCourseStudent();
        response.setCourseId(courseId);
        response.setDtoStudent(null);  // Optional: or return the original student info

        return ResponseEntity.ok(response);
    }




    public ResponseEntity<DtoTeacherStudent> removeStudentFromCourseByTeacherId(
            @RequestBody DtoTeacherStudent dtoTeacherStudent) {

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
            return ResponseEntity.badRequest().build(); // No matching association
        }

        CourseStudentTeacher relation = optionalRelation.get();
        relation.setStudent(null);  // Unassign the student
        repoCourseStudentTeacher.save(relation); // Save changes

        DtoTeacherStudent response = new DtoTeacherStudent();
        response.setCourseId(courseId);
        response.setTeacherId(teacherId);
        response.setDtoStudent(null);  // Student removed

        return ResponseEntity.ok(response);
    }





    public ResponseEntity<DtoCourseStudent> updateExistingStudentWithSpecificCourseId(@RequestBody DtoCourseStudent dtoCourseStudent) {

        int courseId = dtoCourseStudent.getCourseId();
        DtoStudent dtoStudent = dtoCourseStudent.getDtoStudent();
        int studentId = dtoStudent.getStudentId();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        Optional<Student> optionalStudent = repoStudent.findById(studentId);

        if (optionalCourse.isEmpty() || optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        Student student = optionalStudent.get();


        Optional<CourseStudentTeacher> optionalRelation = repoCourseStudentTeacher
                .findByCourseAndStudent(course, student);

        if (optionalRelation.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Student not enrolled in this course
        }


        student.setStudentName(dtoStudent.getStudentName());
        student.setPhoneNumber(dtoStudent.getPhoneNumber());

        repoStudent.save(student);


        DtoStudent updatedDtoStudent = new DtoStudent(
                student.getId(),
                student.getStudentName(),
                student.getPhoneNumber()
        );

        DtoCourseStudent responseDto = new DtoCourseStudent();
        responseDto.setCourseId(courseId);
        responseDto.setDtoStudent(updatedDtoStudent);

        return ResponseEntity.ok(responseDto);
    }




    public ResponseEntity<List<DtoStudent>> getStudentsByTeacherIdAndCourseId(
            @RequestBody DtoAssignTeacherForCourse dtoAssignTeacherForCourse) {

        int courseId = dtoAssignTeacherForCourse.getCourseId();
        int teacherId = dtoAssignTeacherForCourse.getTeacherId();

        Optional<Course> optionalCourse = repoCourse.findById(courseId);
        Optional<Teacher> optionalTeacher = repoTeacher.findById(teacherId);

        if (optionalTeacher.isEmpty() || optionalCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = optionalCourse.get();
        Teacher teacher = optionalTeacher.get();


        List<CourseStudentTeacher> relations = repoCourseStudentTeacher.findByCourseAndTeacher(course, teacher);

        if (relations.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // No students assigned for this teacher and course
        }


        List<DtoStudent> dtoStudents = relations.stream()
                .map(CourseStudentTeacher::getStudent)
                .distinct()
                .map(student -> new DtoStudent(
                        student.getId(),
                        student.getStudentName(),
                        student.getPhoneNumber()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoStudents);
    }




    public ResponseEntity<String> assignNewStudentToExistingCourseAndTeacher(
            @RequestBody DtoAssignStudentsToCourseAndTeacher dto) {

        int courseId = dto.getCourseId();
        int teacherId = dto.getTeacherId();
        List<Integer> studentIds = dto.getStudentIds();

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
                continue; // Skip invalid student ID
            }

            Student student = optionalStudent.get();

            // Check if the combination already exists
            Optional<CourseStudentTeacher> existingRelation =
                    repoCourseStudentTeacher.findByCourseAndStudentAndTeacher(course, student, teacher);

            if (existingRelation.isEmpty()) {

                Optional<CourseStudentTeacher> emptyStudentSlot =
                        repoCourseStudentTeacher.findByCourseAndTeacherAndStudentIsNull(course, teacher);

                if (emptyStudentSlot.isPresent()) {
                    CourseStudentTeacher reusedRelation = emptyStudentSlot.get();
                    reusedRelation.setStudent(student);
                    repoCourseStudentTeacher.save(reusedRelation);
                } else {
                    CourseStudentTeacher newRelation = new CourseStudentTeacher();
                    newRelation.setCourse(course);
                    newRelation.setTeacher(teacher);
                    newRelation.setStudent(student);
                    repoCourseStudentTeacher.save(newRelation);
                }
            }
        }

        return ResponseEntity.ok("New student assign to course and teacher.");
    }


}
