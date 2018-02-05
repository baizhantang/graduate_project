package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.sunhao.graduate_project.entity.StudentGroup;
import com.sunhao.graduate_project.entity.Task;
import com.sunhao.graduate_project.repository.GroupRepo;
import com.sunhao.graduate_project.repository.TaskRepo;
import com.sunhao.graduate_project.util.JSONUtil;
import com.sunhao.graduate_project.util.TranslateForTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

import static java.lang.Integer.*;

@Service
@Transactional
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private GroupRepo groupRepo;

    /**
     * 发布任务
     * @param name
     * @param describe
     * @param deadline
     * @param question
     * @param studentsID
     * @param response
     * @throws IOException
     */
    public Object saveTask(String name,
                         String describe,
                         String deadline,
                         String question,
                         String teacherName,
                         String teacherUserName,
                         String studentsID,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        String taskNumber = UUID.randomUUID().toString().replace("-", "");
        StudentGroup returnStudents = groupRepo.findOne(parseInt(studentsID));
        if (returnStudents == null) {
            String[] key = {"isSuccess", "msg"};
            String[] value = {"false", "学生信息没有查到"};
            return JSONUtil.getJSON(key, value);
        }
        List<Map<String, String>> personList = (List<Map<String, String>>) JSON.parse(returnStudents.getStudents());

        //利用set的属性判断学号是否重复
        Set<String> stuNum = new HashSet<>();
        for (Map<String, String> p :
                personList) {
            stuNum.add(p.get("学号"));
        }
        if (stuNum.size() != personList.size()) {
            String[] key = {"isSuccess", "msg"};
            String[] value = {"false", "学号出错"};
            return JSONUtil.getJSON(key, value);
        }

        Date date = new TranslateForTime().translate(deadline);
        Date dateCurrent = new Date(new java.util.Date().getTime());
        if (date.before(dateCurrent)) {
            String[] key = {"isSuccess","msg"};
            String[] value = {"false","时间错误"};
            return JSONUtil.getJSON(key, value);
        }

        for (Map<String, String> person:
             personList) {
            Task task = new Task();
            task.setTaskNumber(taskNumber);
            task.setTaskName(name);
            task.setTaskDescribe(describe);
            task.setDeadline(date);
            task.setTeacherUserName(teacherUserName);
            task.setStudentName(person.get("姓名"));
            task.setStudentNumber(person.get("学号"));
            task.setQuestion(question.replace(" ", ""));
            task.setTeacherName(teacherName);
            task.setCreateTime(dateCurrent);
            task.setTaskStatus("toDo");

            taskRepo.save(task);
        }

        String[] key = {"isSuccess"};
        String[] value = {"true"};
        return JSONUtil.getJSON(key, value);
    }

    public Object setInvalid(String taskNumber) {
        List<Task> returnT = taskRepo.findAllByTaskNumber(taskNumber);
        if (returnT.isEmpty()) {
            String[] key = {"isSuccess", "msg"};
            String[] value = {"false", "没有相关记录"};
            return JSONUtil.getJSON(key, value);
        }
        for (Task t :
                returnT) {
            t.setTaskStatus("inValid");
        }
        String[] key = {"isSuccess"};
        String[] value = {"true"};
        return JSONUtil.getJSON(key, value);
    }

    public Object giveAnswer(String taskNumber, String studentNumber, String answer, String describe) {
        answer = answer.replace(" ", "");
        Task task = taskRepo.findByTaskNumberAndStudentNumber(taskNumber, studentNumber);
        if (task == null) {
            String[] key = {"isSuccess", "msg"};
            String[] value = {"false", "没查到相关任务记录"};
            return JSONUtil.getJSON(key, value);
        }

        switch (task.getTaskStatus()) {
            case "toDo":
            case "done": {
                task.setRemark(describe);
                task.setAnswer(answer);
                task.setTaskStatus("done");

                taskRepo.save(task);
                String[] key = {"isSuccess"};
                String[] value = {"true"};
                return JSONUtil.getJSON(key, value);
            }
            case "past": {
                String[] key = {"isSuccess", "msg"};
                String[] value = {"false", "任务已经完成"};
                return JSONUtil.getJSON(key, value);
            }
            case "inValid": {
                String[] key = {"isSuccess", "msg"};
                String[] value = {"false", "任务已经关闭"};
                return JSONUtil.getJSON(key, value);
            }
            default:
                String[] key = {"isSuccess"};
                String[] value = {"false"};
                return JSONUtil.getJSON(key, value);
        }
    }
}
