package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.domain.Task;
import com.sunhao.graduate_project.repository.TaskRepository;
import com.sunhao.graduate_project.service.SearchTaskService;
import com.sunhao.graduate_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
