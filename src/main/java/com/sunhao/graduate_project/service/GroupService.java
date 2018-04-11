package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunhao.graduate_project.entity.StudentGroup;
import com.sunhao.graduate_project.repository.GroupRepo;
import com.sunhao.graduate_project.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class GroupService {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private GroupRepo groupRepo;

    @Value("${sitePath}")
    String path;

    /**
     * 处理文件导入的学生组信息
     * @param teacherUserName
     * @param groupName
     * @param students
     * @return
     * @throws IOException
     */
    public void createGroup(String teacherUserName, String groupName, MultipartFile students, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        //解析人员名单
        List<Map<String, String>> personList = excelService.excelParse(students);
        if (personList==null || personList.isEmpty()) {
            myDIYResponse(response, path+"groupManager.html", "暂时无法解析您的文件");
        } else {
            //利用set的属性判断学号是否重复
            Set<String> stuNum = new HashSet<>();
            for (Map<String, String> p :
                    personList) {
                stuNum.add(p.get("学号"));
            }
            if (stuNum.size() != personList.size()) {
                myDIYResponse(response, path+"groupManager.html", "其实学号是不允许重复的");
            } else {
                //生成人员组信息
                StudentGroup studentGroup = new StudentGroup();
                studentGroup.setGroupName(groupName);
                studentGroup.setTeacherUserName(teacherUserName);
                studentGroup.setStudents(JSON.toJSONString(personList).replace(" ", ""));
                studentGroup.setStudentNumber(personList.size());
                studentGroup.setCreateTime(new Timestamp(new Date().getTime()));
                groupRepo.save(studentGroup);

                //返回成功信息
                myDIYResponse(response, path + "groupManager.html");
            }
        }
    }

    /**
     * 根据老师的用户名查找所有相关的学生组
     * @param teacherUserName
     * @return
     */
    public Object findAllGroupByTeacherUserName(String teacherUserName) {
        List<StudentGroup> studentGroups = groupRepo.findAllByTeacherUserName(teacherUserName);
        if (studentGroups == null || studentGroups.isEmpty()) {
            String[] key = {"isSuccess","msg"};
            String[] value = {"false", "没查到相关信息"};
            return JSONUtil.getJSON(key, value);
        }

        List<Map<String, Object>> returnL = new ArrayList<>();
        for (StudentGroup studentGroup : studentGroups) {
            Map<String, Object> translateM = new HashMap<>();
            String time = null;
            try {
                time = studentGroup.getCreateTime().toString();
                translateM.put("createTime", time.substring(0, time.length()-2));
            } catch (java.lang.NullPointerException e) {
                translateM.put("createTime", "无记录");
            }
            translateM.put("id", studentGroup.getId());
            translateM.put("groupName", studentGroup.getGroupName());
            translateM.put("studentNumber", studentGroup.getStudentNumber());
            translateM.put("students", JSON.parse(studentGroup.getStudents()));
            returnL.add(translateM);
        }

        System.out.println(returnL);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("isSuccess", "true");
        returnMap.put("groups", returnL);
        return returnMap;
    }

    /**
     * 处理手工生成的学生组
     * @param teacherUserName
     * @param groupName
     * @param students
     * @return
     */
    public Object createGroupManual(String teacherUserName, String groupName, String students) {
        //处理学生信息
        students = students.replace(" ", "");
        List<Map<String, String>> inputStudents = (List<Map<String, String>>) JSON.parse(students);

        //生成学生组信息
        StudentGroup group = new StudentGroup();
        group.setGroupName(groupName);
        group.setTeacherUserName(teacherUserName);
        group.setStudents(JSON.toJSONString(inputStudents));
        groupRepo.save(group);

        //返回结果
        String[] key = {"isSuccess"};
        String[] value = {"true"};
        return JSONUtil.getJSON(key, value);
    }

    //提示错误信息并且跳转页面
    private void myDIYResponse(HttpServletResponse response, String path, String msg) throws IOException {
        Writer writer = response.getWriter();
        writer.write("<script type='text/javascript'>alert('" + msg + "');window.location.href = '" + path + "';</script>");
    }
    //直接跳转页面
    private void myDIYResponse(HttpServletResponse response, String path) throws IOException {
        Writer writer = response.getWriter();
        writer.write("<script type='text/javascript'>window.location.href = '" + path + "';</script>");
    }

    //清空群组
    public Object cleanGroup(String teacherUserName) {
        groupRepo.deleteAllByTeacherUserName(teacherUserName);

        //返回结果
        String[] key = {"isSuccess"};
        String[] value = {"true"};
        return JSONUtil.getJSON(key, value);
    }
}
