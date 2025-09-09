package com.universitymanagement.repositiries;

import com.universitymanagement.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoCourse extends JpaRepository<Course,Integer> {


    Optional<Course> findByCourseName(String courseName);

    List<Course> findByTeachersId(int teacherId);


}
