package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/createGroup")
    public Object createGroup(@RequestParam("teacherUserName") String teacherUserName,
                              @RequestParam("groupName") String groupName,
                              @RequestParam("students")MultipartFile students) throws IOException {
        return groupService.createGroup(teacherUserName, groupName, students);
    }

    @PostMapping("/findAllGroup")
    public Object findAllGroupByTeacherUserName(@RequestParam("teacherUserName") String teacherUserName) {
        return groupService.findAllGroupByTeacherUserName(teacherUserName);
    }

    @PostMapping("/createGroupManual")
    public Object createGroupManual(@RequestParam("teacherUserName") String teacherUserName,
                                    @RequestParam("groupName") String groupName,
                                    @RequestParam("students") String students) {
        return groupService.createGroupManual(teacherUserName, groupName, students);
    }
}
