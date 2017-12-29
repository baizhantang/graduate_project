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

        return showTask;
    }
}
