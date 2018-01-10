package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunhao.graduate_project.domain.ShowTask;
import com.sunhao.graduate_project.domain.Task;
import com.sunhao.graduate_project.repository.TaskRepository;
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
    private TaskRepository taskRepository;

    public Object getTask(String teacherName) {
        List<Task> data = taskRepository.findTaskByTeacherName(teacherName);
        Map<String, List<ShowTask>> result = new HashMap<>();

        List<ShowTask> befores = new ArrayList<>();
        List<ShowTask> afters = new ArrayList<>();

        Date dateCurrent = new Date(new java.util.Date().getTime());
        for (Task temp :
                data) {
            if (temp.getDeadline().after(dateCurrent)) {
                afters.add(TranslateForShowTask.translate(temp));
            } else {
                befores.add(TranslateForShowTask.translate(temp));
            }
        }

        result.put("fail", befores);
        result.put("processing", afters);

        String resultS = JSON.toJSONString(result);

        JSONObject resultJ = JSON.parseObject(resultS);
        return resultJ;
    }

    public Object getHistoryTask(String teacherName) {
        List<Task> data = taskRepository.findHistoryTaskByTeacherName(teacherName);
        Map<String, List<ShowTask>> result = new HashMap<>();
        List<ShowTask> translate = new ArrayList<>();

        for (Task temp :
                data) {
            translate.add(TranslateForShowTask.translate(temp));
        }

        result.put("his", translate);

        String resultS = JSON.toJSONString(result);

        JSONObject resultJ = JSON.parseObject(resultS);
        return resultJ;
    }

    public Object getTaskInfo(String taskNumber) {
        List<Task> data = taskRepository.findByTaskNumber(taskNumber);
        Map<String, List<ShowTask>> result = new HashMap<>();

        List<ShowTask> success = new ArrayList<>();
        List<ShowTask> nodata = new ArrayList<>();

        for (Task temp :
                data) {
            if (temp.getTaskStatus() != null) {
                success.add(TranslateForShowTask.translate(temp));
            } else {
                nodata.add(TranslateForShowTask.translate(temp));
            }
        }

        result.put("successed", success);
        result.put("nodata", nodata);

        String resultS = JSON.toJSONString(result);

        JSONObject resultJ = JSON.parseObject(resultS);
        return resultJ;

    }

    public Object getTaskByStudent(String studentNumber) {
        List<Task> data = taskRepository.findByStudentNumber(studentNumber);
        System.out.println(data);
        Map<String, List<ShowTask>> result = new HashMap<>();

        List<ShowTask> success = new ArrayList<>();
        List<ShowTask> nodata = new ArrayList<>();

        for (Task temp :
                data) {
            if (temp.getTaskStatus() != null) {
                success.add(TranslateForShowTask.translate(temp));
            } else {
                nodata.add(TranslateForShowTask.translate(temp));
            }
        }

        result.put("successed", success);
        result.put("nodata", nodata);

        System.out.println(nodata);

        String resultS = JSON.toJSONString(result);

        JSONObject resultJ = JSON.parseObject(resultS);
        return resultJ;

    }

}
