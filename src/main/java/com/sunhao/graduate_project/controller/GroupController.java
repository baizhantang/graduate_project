package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * 创建群组
     * @param teacherUserName
     * @param groupName
     * @param students
     * @return
     * @throws IOException
     */
    @PostMapping("/createGroup")
    public void createGroup(@RequestParam("teacherUserName") String teacherUserName,
                              @RequestParam("groupName") String groupName,
                              @RequestParam("students")MultipartFile students,
                              HttpServletResponse response) throws IOException {
        groupService.createGroup(teacherUserName, groupName, students, response);
    }

    /**
     * 查询所有的群组
     * @param teacherUserName
     * @return
     */
    @PostMapping("/findAllGroup")
    public Object findAllGroupByTeacherUserName(@RequestParam("teacherUserName") String teacherUserName) {
        return groupService.findAllGroupByTeacherUserName(teacherUserName);
    }


    /**
     * 手动创建群组
     * @param teacherUserName
     * @param groupName
     * @param students
     * @return
     */
    @PostMapping("/createGroupManual")
    public Object createGroupManual(@RequestParam("teacherUserName") String teacherUserName,
                                    @RequestParam("groupName") String groupName,
                                    @RequestParam("students") String students) {
        return groupService.createGroupManual(teacherUserName, groupName, students);
    }

    @PostMapping("/cleanGroup")
    public Object cleanGroup(@RequestParam("teacherUserName") String teacherUserName) {
        System.out.println(1);
        return groupService.cleanGroup(teacherUserName);
    }
}
