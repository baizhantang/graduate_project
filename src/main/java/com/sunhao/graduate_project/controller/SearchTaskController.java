package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.SearchTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchTaskController {

    @Autowired
    private SearchTaskService searchTaskService;

    /**
     * 查询正在进行中的记录,按照是否过期分开
     * @param teacherUserName
     * @return
     */
    @PostMapping(value = "/getProcessingTask")
    public Object getTask(@RequestParam("teacherUserName") String teacherUserName) {
        return searchTaskService.getTask(teacherUserName);
    }

    /**
     * 查询已经完成或者关闭的任务
     * @param teacherUserName
     * @return
     */
    @PostMapping("/getHistoryTask")
    public Object getHistoryTask(@RequestParam("teacherUserName") String teacherUserName) {
        return searchTaskService.getHistoryTask(teacherUserName);
    }


    /**
     * 获取某个任务的详情
     * @param taskNumber
     * @return
     */
    @PostMapping(value = "/getTaskInfo")
    public Object getTaskInfo(@RequestParam("taskNumber") String taskNumber) {
        return searchTaskService.getTaskInfo(taskNumber);
    }

    /**
     * 通过学号查询相关任务
     * @param studentNumber
     * @return
     */
    @PostMapping(value = "/getTaskByStudent")
    public Object getTaskByStudent(@RequestParam("studentNumber") String studentNumber) {
        return searchTaskService.getTaskByStudent(studentNumber);
    }


    /**
     * 获取某个学生的答案
     * @param taskNumber
     * @param studentNumber
     * @return
     */
    @PostMapping("/getAnswer")
    public Object getAnswer(@RequestParam("taskNumber") String taskNumber,
                            @RequestParam("studentNumber") String studentNumber) {
        return searchTaskService.getAnswer(taskNumber, studentNumber);
    }
}
