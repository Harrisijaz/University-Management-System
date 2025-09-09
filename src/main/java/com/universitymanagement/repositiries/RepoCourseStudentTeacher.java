package com.universitymanagement.repositiries;

import com.universitymanagement.entities.Course;
import com.universitymanagement.entities.CourseStudentTeacher;
import com.universitymanagement.entities.Student;
import com.universitymanagement.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoCourseStudentTeacher extends JpaRepository<CourseStudentTeacher,Integer> {

    @Query("SELECT cst.course FROM CourseStudentTeacher cst WHERE cst.student.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") int studentId);


    @Query("SELECT cst.student FROM CourseStudentTeacher cst WHERE cst.course.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") int courseId);


    boolean existsByCourseAndStudent(Course course, Student student);

    Optional<CourseStudentTeacher> findByCourseAndStudent(Course course, Student student);

    List<CourseStudentTeacher> findAllByCourseAndStudent(Course course, Student student);

    List<CourseStudentTeacher> findByCourseAndTeacher(Course course, Teacher teacher);

    List<CourseStudentTeacher> findByTeacherId(int teacherId);

    Optional<CourseStudentTeacher> findByCourseAndStudentAndTeacher(Course course, Student student, Teacher teacher);

    Optional<CourseStudentTeacher> findByCourseAndStudentAndTeacherIsNull(Course course, Student student);

    Optional<CourseStudentTeacher> findByCourseAndTeacherAndStudentIsNull(Course course, Teacher teacher);


}
