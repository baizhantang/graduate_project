package com.sunhao.graduate_project.repository;

import com.sunhao.graduate_project.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select task from Task task where task.teacherName=?1 and task.taskStatus is null group by task.taskNumber")
    public List<Task> findTaskByTeacherName(String teacherName);

    @Query("select task from Task task where task.teacherName=?1 and task.taskStatus is not null group by task.taskNumber")
    public List<Task> findHistoryTaskByTeacherName(String teacherName);
}
