package com.universitymanagement.repositiries;

import com.universitymanagement.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoStudent extends JpaRepository<Student,Integer> {
//    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id= :courseId")
//    List<Student> findStudentsByCourseId(@Param("courseId") int courseId);

}
