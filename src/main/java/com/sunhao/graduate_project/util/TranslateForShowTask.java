package com.sunhao.graduate_project.util;

import com.sunhao.graduate_project.entity.ShowTask;
import com.sunhao.graduate_project.entity.Task;

public class TranslateForShowTask {

    /**
     * 将Task转换为ShowTask
     * @param template
     * @return
     */
    public static ShowTask translate(Task template) {
        ShowTask showTask = new ShowTask();

        showTask.setTaskName(template.getTaskName());
        showTask.setTaskDescribe(template.getTaskDescribe());
        showTask.setTeacherName(template.getTeacherName());
        showTask.setDeadline(template.getDeadline().toLocalDate());
        showTask.setTaskNumber(template.getTaskNumber());
        showTask.setStudentName(template.getStudentName());
        showTask.setStudentNumber(template.getStudentNumber());
        showTask.setTaskStatus(template.getTaskStatus());
        showTask.setRemark(template.getRemark());
        showTask.setQuestion(template.getQuestion());
        showTask.setAnswer(template.getAnswer());
        showTask.setTeacherUserName(template.getTeacherUserName());

        return showTask;
    }
}
