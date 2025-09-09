package com.universitymanagement.services;

import com.universitymanagement.dto.DtoTeacher;
import com.universitymanagement.entities.Teacher;
import com.universitymanagement.repositiries.RepoTeacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceTeacher {

private RepoTeacher teacherRepo;

    public ServiceTeacher(RepoTeacher teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    public ResponseEntity<List<DtoTeacher>> retrieveAllTeachers() {
        List<Teacher> teachers = teacherRepo.findAll();

        List<DtoTeacher> dtoTeachers = teachers.stream()
                .map(teacher -> new DtoTeacher(teacher.getId(), teacher.getTeacherName()))
                .toList();

        return ResponseEntity.ok(dtoTeachers);
    }

    public ResponseEntity<DtoTeacher> findTeacherById(int id){

        Optional<Teacher> optionalTeacher = teacherRepo.findById(id);

        if (optionalTeacher.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Teacher teacher = optionalTeacher.get();
        DtoTeacher dto = new DtoTeacher(teacher.getId(), teacher.getTeacherName());

        return ResponseEntity.ok(dto);

    }


    public ResponseEntity<DtoTeacher> createteacher(@RequestBody DtoTeacher dtoTeacher){

        String teacherName = dtoTeacher.getTeacherName();

        Teacher teacher = new Teacher();
        teacher.setTeacherName(teacherName);

        Teacher savedteacher = teacherRepo.save(teacher);

        DtoTeacher  response = new DtoTeacher(savedteacher.getId(),savedteacher.getTeacherName());

        return  ResponseEntity.ok(response);

    }

    public ResponseEntity<DtoTeacher> updateTeacher(@RequestBody DtoTeacher dtoTeacher) {

        int teacherId = dtoTeacher.getTeacherId();

        Optional<Teacher> optionalTeacher = teacherRepo.findById(teacherId);

        if (optionalTeacher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Teacher teacher = optionalTeacher.get();
        teacher.setTeacherName(dtoTeacher.getTeacherName());

        Teacher savedTeacher = teacherRepo.save(teacher);

        DtoTeacher responseDto = new DtoTeacher(savedTeacher.getId(), savedTeacher.getTeacherName());

        return ResponseEntity.ok(responseDto);
    }


    public ResponseEntity<DtoTeacher> deleteTeacherById(@RequestBody DtoTeacher dtoTeacher){

        int id = dtoTeacher.getTeacherId();

        Optional<Teacher> optionalTeacher = teacherRepo.findById(id);

        if(optionalTeacher.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Teacher teacher = optionalTeacher.get();

        DtoTeacher responseDto = new DtoTeacher(teacher.getId(), teacher.getTeacherName());

        teacherRepo.delete(teacher);
        return  ResponseEntity.ok(responseDto);


    }



}
