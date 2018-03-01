package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunhao.graduate_project.entity.ShowTask;
import com.sunhao.graduate_project.entity.Task;
import com.sunhao.graduate_project.repository.TaskRepo;
import com.sunhao.graduate_project.util.JSONUtil;
import com.sunhao.graduate_project.util.TranslateForShowTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchTaskService {

    @Autowired
    private TaskRepo taskRepo;

    public Object getTask(String teacherUserName) {
        List<Task> data = taskRepo.findTaskByTeacherName(teacherUserName);
        Map<String, List<ShowTask>> result = new HashMap<>();

        List<ShowTask> befores = new ArrayList<>();
        List<ShowTask> afters = new ArrayList<>();

        Date dateCurrent = new Date(new java.util.Date().getTime());
        for (Task temp :
                data) { //判断返回的结果数据是否过了截止日期，分开封装返回
            if (temp.getDeadline().after(dateCurrent)) {
                afters.add(TranslateForShowTask.translate(temp)); //把Task（数据库返回）转换为前端友好的ShowTask
            } else {
                befores.add(TranslateForShowTask.translate(temp));
            }
        }

        result.put("pastDue", befores);
        result.put("inProgress", afters);

        String resultS = JSON.toJSONString(result);

        JSONObject resultJ = JSON.parseObject(resultS);
        return resultJ;
    }

    public Object getHistoryTask(String teacherUserName) {
        List<Task> data = taskRepo.findHistoryTaskByTeacherName(teacherUserName);
        Map<String, List<ShowTask>> result = new HashMap<>();
        List<ShowTask> inValid = new ArrayList<>();
        List<ShowTask> past = new ArrayList<>();

        //根据任务状态是完成还是失效分开
        for (Task temp :
                data) {
            if (temp.getTaskStatus().equals("past")) {
                past.add(TranslateForShowTask.translate(temp));
            } else {
                inValid.add(TranslateForShowTask.translate(temp));
            }
        }

        result.put("past", past);
        result.put("inValid", inValid);

        String resultS = JSON.toJSONString(result);

        JSON resultJ = (JSON) JSON.parse(resultS);
        return resultJ;
    }

    public Object getTaskInfo(String taskNumber) {
        List<Task> data = taskRepo.findAllByTaskNumber(taskNumber);
        Map<String, List<ShowTask>> result = new HashMap<>();

        List<ShowTask> done = new ArrayList<>();
        List<ShowTask> toDo = new ArrayList<>();
        List<ShowTask> past = new ArrayList<>();
        List<ShowTask> inValid = new ArrayList<>();


        for (Task temp :
                data) {
            switch (temp.getTaskStatus()) {
                case "toDo": {
                    toDo.add(TranslateForShowTask.translate(temp));
                    break;
                }
                case "done": {
                    done.add(TranslateForShowTask.translate(temp));
                    break;
                }
                case "past": {
                    past.add(TranslateForShowTask.translate(temp));
                    break;
                }
                case "inValid": {
                    inValid.add(TranslateForShowTask.translate(temp));
                    break;
                }
            }
        }

        result.put("toDo", toDo);
        result.put("done", done);
        result.put("past", past);
        result.put("inValid", inValid);

        String resultS = JSON.toJSONString(result);

        JSON resultJ = (JSON) JSON.parse(resultS);
        return resultJ;
    }

    public Object getTaskByStudent(String studentNumber) {
        List<Task> data = taskRepo.findAllByStudentNumber(studentNumber);
        Map<String, List<ShowTask>> result = new HashMap<>();

        List<ShowTask> done = new ArrayList<>();
        List<ShowTask> toDo = new ArrayList<>();
        List<ShowTask> past = new ArrayList<>();
        List<ShowTask> inValid = new ArrayList<>();


        for (Task temp :
                data) {
            switch (temp.getTaskStatus()) {
                case "toDo": {
                    toDo.add(TranslateForShowTask.translate(temp));
                    break;
                }
                case "done": {
                    done.add(TranslateForShowTask.translate(temp));
                    break;
                }
                case "past": {
                    past.add(TranslateForShowTask.translate(temp));
                    break;
                }
                case "inValid": {
                    inValid.add(TranslateForShowTask.translate(temp));
                    break;
                }
            }
        }

        result.put("toDo", toDo);
        result.put("done", done);
        result.put("past", past);
        result.put("inValid", inValid);

        String resultS = JSON.toJSONString(result);

        JSON resultJ = (JSON) JSON.parse(resultS);
        return resultJ;

    }

    public Object getAnswer(String taskNumber, String studentNumber) {
        Task task = taskRepo.findByTaskNumberAndStudentNumber(taskNumber, studentNumber);
        if (task == null) {
            String[] key = {"isSuccess", "msg"};
            String[] value = {"false", "没查到相关记录"};
            return JSONUtil.getJSON(key, value);
        }

        return TranslateForShowTask.translate(task);
    }

    public Object getQuestion(String studentNumber, String taskNumber) {
        Task task = taskRepo.findByTaskNumberAndStudentNumber(taskNumber, studentNumber);
        if (task == null) {
            return null;
        }
        String question = task.getQuestion();
        List<Map<String, String>> returnQ = (List<Map<String, String>>) JSON.parse(question);
        return returnQ;
    }
}
