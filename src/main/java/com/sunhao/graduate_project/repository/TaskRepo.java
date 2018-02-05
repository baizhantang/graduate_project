package com.sunhao.graduate_project.repository;

import com.sunhao.graduate_project.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {

    @Query("select task from Task task where task.teacherUserName=?1 and task.taskStatus='toDo' group by task.taskNumber")
    List<Task> findTaskByTeacherName(String teacherUserName);

    @Query("select task from Task task where task.teacherUserName=?1 and (task.taskStatus='past' or task.taskStatus='inValid') group by task.taskNumber")
    List<Task> findHistoryTaskByTeacherName(String teacherUserName);

    List<Task> findAllByTaskNumber(String taskNumber);

    List<Task> findAllByStudentNumber(String studentNumber);

    Task findByTaskNumberAndStudentNumber(String taskNumber, String studentNumber);


}
