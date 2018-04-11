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

    /**
     * 添加任务记录
     * @param name
     * @param describe
     * @param deadline
     * @param students
     * @param response
     * @throws IOException
     */
    @PostMapping("/addTask")
    public Object addTask(@RequestParam("name") String name,
                     @RequestParam("describe") String describe,
                     @RequestParam("deadline") String deadline,
                     @RequestParam("question") String question,
                     @RequestParam("teacherUserName") String teacherUserName,
                     @RequestParam("students") String students,
                     HttpServletResponse response) throws IOException {
        return taskService.saveTask(name, describe, deadline, question, teacherUserName, students, response);
    }

    /**
     * 设置为失效，教师操作关闭任务的时候使用
     * @param taskNumber
     * @return
     */
    @PostMapping("/setInvalid")
    public Object setInvalid(@RequestParam("taskNumber") String taskNumber) {
        return taskService.setInvalid(taskNumber);
    }

    /**
     * 完成任务
     * @param taskNumber
     * @param studentNumber
     * @param describe
     * @param answer
     * @return
     */
    @PostMapping("/giveAnswer")
    public Object giveAnswer(@RequestParam("taskNumber") String taskNumber,
                             @RequestParam("studentNumber") String studentNumber,
                             @RequestParam("describe") String describe,
                             @RequestParam("answer") String answer) {
        return taskService.giveAnswer(taskNumber, studentNumber, answer, describe);
    }


    /**
     * 导出文件
     * @param taskNumber
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/exportFile")
    public Object exportFile(@RequestParam("taskNumber") String taskNumber,
                             HttpServletResponse response) throws IOException {
        return taskService.exportFile(taskNumber, response);
    }

}
