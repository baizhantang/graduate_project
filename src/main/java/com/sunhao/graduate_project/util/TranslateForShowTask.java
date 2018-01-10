package com.sunhao.graduate_project.util;

import com.sunhao.graduate_project.domain.ShowTask;
import com.sunhao.graduate_project.domain.Task;

public class TranslateForShowTask {

    public static ShowTask translate(Task template) {
        ShowTask showTask = new ShowTask();

        showTask.setTaskName(template.getTaskName());
        showTask.setTaskDescribe(template.getTaskDescribe());
        showTask.setTeacherName(template.getTeacherName());
        showTask.setDeadline(template.getDeadline().toLocalDate());
        showTask.setTemplatePath(template.getTemplatePath());
        showTask.setTaskNumber(template.getTaskNumber());
        showTask.setStudentName(template.getStudentName());
        showTask.setStudentNumber(template.getStudentNumber());
        showTask.setDoStatus(template.getTaskStatus());
        showTask.setResultPath(template.getResultPath());
        showTask.setRemark(template.getRemark());

        return showTask;
    }
}
