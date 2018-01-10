package com.sunhao.graduate_project.service;

import com.sunhao.graduate_project.domain.Task;
import com.sunhao.graduate_project.repository.TaskRepository;
import com.sunhao.graduate_project.util.TranslateForTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private FileService fileService;

    public void saveTask(String name,
                         String describe,
                         String deadline,
                         MultipartFile template,
                         MultipartFile persons,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        List<Map<String, String>> personList;

        PrintWriter out = response.getWriter();

        String taskNumber = UUID.randomUUID().toString();
        personList = excelService.excelParse(persons);
        if (personList == null) {
            out.flush();
            out.println("<script type='text/javascript'>");
            out.println("alert('file error!');");
            out.println("window.location.href = 'http://120.79.141.175/addTask.html';");
            out.println("</script>");
            return;
        }
        String path = fileService.saveFile(template);
        if (path == null) {
            out.flush();
            out.println("<script type='text/javascript'>");
            out.println("alert('file error!');");
            out.println("window.location.href = 'http://120.79.141.175/addTask.html';");
            out.println("</script>");
            return;
        }
        Date date = new TranslateForTime().translate(deadline);
        Date dateCurrent = new Date(new java.util.Date().getTime());
        if (date.before(dateCurrent)) {
            out.flush();
            out.println("<script type='text/javascript'>");
            out.println("alert('time error!');");
            out.println("window.location.href = 'http://120.79.141.175/addTask.html';");
            out.println("</script>");
            return;
        }

        for (Map<String, String> person:
             personList) {
            Task task = new Task();
            task.setTaskNumber(taskNumber);
            task.setTaskName(name);
            task.setTaskDescribe(describe);
            task.setDeadline(date);
            task.setStudentName(person.get("姓名"));
            task.setStudentNumber(person.get("学号"));
            task.setTemplatePath(path);
            task.setTeacherName("孙昊");
            task.setCreateTime(dateCurrent);

            taskRepository.save(task);
        }

        out.flush();
        out.println("<script type='text/javascript'>");
        out.println("alert('success!');");
        out.println("window.location.href = 'http://120.79.141.175/history.html';");
        out.println("</script>");
        return;
    }

    public void saveFile(String taskNumber,
                         String studentNumber,
                         String describe,
                         MultipartFile homework,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        String path = fileService.saveFile(homework);
        if (path == null) {
            out.flush();

            out.println("alert('file error!');");
            out.println("window.location.href = 'http://120.79.141.175/studentIndex.html?studentNumber=" + studentNumber + "';");
            out.println("</script>");
            return;
        }

        Task task = taskRepository.findByTaskNumberAndStudentNumber(taskNumber, studentNumber);

        System.out.println(task);
        task.setRemark(describe);
        task.setResultPath(path);
        task.setTaskStatus("ok");
        taskRepository.save(task);

        out.flush();
        out.println("<script type='text/javascript'>");
        out.println("alert('success!');");
        out.println("window.location.href = 'http://120.79.141.175/studentIndex.html?studentNumber=" + task.getStudentNumber() + "';");
        out.println("</script>");
        return;
    }
}