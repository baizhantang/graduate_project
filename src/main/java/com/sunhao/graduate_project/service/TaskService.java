package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.sunhao.graduate_project.entity.StudentGroup;
import com.sunhao.graduate_project.entity.Task;
import com.sunhao.graduate_project.repository.GroupRepo;
import com.sunhao.graduate_project.repository.TaskRepo;
import com.sunhao.graduate_project.repository.UserRepo;
import com.sunhao.graduate_project.util.JSONUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static java.lang.Integer.*;

@Service
@Transactional
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Value("${filePath}")
    private String configPath;

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

        Timestamp date = Timestamp.valueOf(deadline);
        Timestamp dateCurrent = new Timestamp(new java.util.Date().getTime());
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
            task.setTeacherName(userRepo.findByUsername(teacherUserName).getName());
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
        if (returnT == null || returnT.isEmpty()) {
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

    public Object exportFile(String taskNumber, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        List<Task> returnT = taskRepo.findAllByTaskNumberAndTaskStatus(taskNumber, "done");
        if (returnT == null || returnT.isEmpty()) {
            String[] key = {"isSuccess", "msg"};
            String[] value = {"false", "无有效数据"};
            return JSONUtil.getJSON(key, value);
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        String question = returnT.get(0).getQuestion();
        List<Map<String, String>> nav = (List<Map<String, String>>) JSON.parse(question);
        HSSFSheet sheet = workbook.createSheet("统计信息");
        HSSFRow row = sheet.createRow(0);
        int i = 0;
        row.createCell(i++).setCellValue("姓名");
        row.createCell(i++).setCellValue("学号");
        for (Map<String, String> n :
                nav) {
            row.createCell(i).setCellValue(n.get("title"));
            i++;
        }

        i = 1;
        int count = 0;
        HSSFRow dataRow = null;
        for (Task t :
                returnT) {
            dataRow = sheet.createRow(i);
            String answer = t.getAnswer();
            List<Map<String, String>> data = (List<Map<String, String>>) JSON.parse(answer);
            if (data.size() > count) {
                count = data.size();
            }
            int j = 0;
            dataRow.createCell(j++).setCellValue(t.getStudentName());
            dataRow.createCell(j++).setCellValue(t.getStudentNumber());
            for (Map<String, String> cellData :
                    data) {
                dataRow.createCell(j).setCellValue(cellData.get("answer"));
                j++;
            }
            i++;
        }

        for (int j = 0; j < count; j++) {
            sheet.autoSizeColumn(j);
        }

        String path = configPath + returnT.get(0).getTaskName() + new Date().getTime() + ".xls";
        String relative = "file/" + returnT.get(0).getTaskName() + new Date().getTime() + ".xls";
        FileOutputStream outputStream = new FileOutputStream(new File(path));
        workbook.write(outputStream);

        String[] key = {"isSuccess", "path"};
        String[] value = {"true", relative};
        System.out.println(JSONUtil.getJSON(key, value));
        return JSONUtil.getJSON(key, value);
    }
}
