package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunhao.graduate_project.entity.StudentGroup;
import com.sunhao.graduate_project.repository.GroupRepo;
import com.sunhao.graduate_project.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupService {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private GroupRepo groupRepo;

    /**
     * 处理文件导入的学生组信息
     * @param teacherUserName
     * @param groupName
     * @param students
     * @return
     * @throws IOException
     */
    public Object createGroup(String teacherUserName, String groupName, MultipartFile students) throws IOException {
        //解析人员名单
        List<Map<String, String>> personList = excelService.excelParse(students);
        if (personList.isEmpty()) {
            String[] key = {"isSuccess", "msg"};
            String[] value = {"false", "人员名单出错"};
            return JSONUtil.getJSON(key, value);
        }

        //生成人员组信息
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupName(groupName);
        studentGroup.setTeacherUserName(teacherUserName);
        studentGroup.setStudents(JSON.toJSONString(personList).replace(" ", ""));
        groupRepo.save(studentGroup);

        //返回成功信息
        String[] key = {"isSuccess"};
        String[] value = {"true"};
        return JSONUtil.getJSON(key, value);
    }

    /**
     * 根据老师的用户名查找所有相关的学生组
     * @param teacherUserName
     * @return
     */
    public Object findAllGroupByTeacherUserName(String teacherUserName) {
        List<StudentGroup> studentGroups = groupRepo.findAllByTeacherUserName(teacherUserName);
        if (studentGroups.isEmpty()) {
            String[] key = {"isSuccess","msg"};
            String[] value = {"false", "没查到相关信息"};
            return JSONUtil.getJSON(key, value);
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("isSuccess", "true");
        returnMap.put("returnData", studentGroups);
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
}
