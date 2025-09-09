package com.universitymanagement.repositiries;


import com.universitymanagement.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoTeacher extends JpaRepository<Teacher,Integer> {
}
