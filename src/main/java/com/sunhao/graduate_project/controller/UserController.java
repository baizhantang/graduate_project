package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/login")
    public Object accountIsOk(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    /**
     * 注册
     * @param username
     * @param password
     * @param name
     * @return
     */
    @PostMapping(value = "/regist")
    public Object registUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name) {
        return userService.registUser(username, password, name);
    }

}
