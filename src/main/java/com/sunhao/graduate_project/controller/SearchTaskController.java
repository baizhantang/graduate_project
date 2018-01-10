package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.domain.Task;
import com.sunhao.graduate_project.repository.TaskRepository;
import com.sunhao.graduate_project.service.SearchTaskService;
import com.sunhao.graduate_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class SearchTaskController {

    @Autowired
    private SearchTaskService searchTaskService;

    @PostMapping(value = "/getProcessingTask")
    public Object getTask(@RequestParam("teacherName") String teacherName) {
        return searchTaskService.getTask(teacherName);
    }

    @PostMapping(value = "/getHistoryTask")
    public Object getHistoryTask(@RequestParam("teacherName") String teacherName) {
        return searchTaskService.getHistoryTask(teacherName);
    }

    @PostMapping(value = "/getTaskInfo")
    public Object getTaskInfo(@RequestParam("taskNumber") String taskNumber) {
        return searchTaskService.getTaskInfo(taskNumber);
    }

    @PostMapping(value = "/getTaskByStudent")
    public Object getTaskByStudent(@RequestParam("studentNumber") String studentNumber) {
        return searchTaskService.getTaskByStudent(studentNumber);
    }
}
