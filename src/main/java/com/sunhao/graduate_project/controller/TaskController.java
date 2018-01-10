package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/addTask")
    public void addTask(@RequestParam("name") String name,
                     @RequestParam("describe") String describe,
                     @RequestParam("deadline") String deadline,
                     @RequestParam("template") MultipartFile template,
                     @RequestParam("person") MultipartFile person,
                     HttpServletResponse response) throws IOException {
        taskService.saveTask(name, describe, deadline, template, person, response);
    }

    @PostMapping(value = "/uploadFile")
    public void uploadFile(@RequestParam("taskNumber") String taskNumber,
                           @RequestParam("studentNumber") String studentNumber,
                           @RequestParam("describe") String describe,
                           @RequestParam("homework") MultipartFile homework,
                           HttpServletResponse response) throws IOException {
        taskService.saveFile(taskNumber, studentNumber, describe, homework, response);
    }
}
