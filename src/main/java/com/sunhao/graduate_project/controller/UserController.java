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

    @PostMapping(value = "/login")
    public Object accountIsOk(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        return userService.accountVerification(username, password);
    }

}
