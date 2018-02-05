package com.sunhao.graduate_project.repository;

import com.sunhao.graduate_project.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepo extends JpaRepository<StudentGroup, Integer> {
    List<StudentGroup> findAllByTeacherUserName(String teacherUserName);

    List<StudentGroup> findAllById(int id);
}
