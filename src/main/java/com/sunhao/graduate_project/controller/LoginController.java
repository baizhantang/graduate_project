package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/login")
    public Object accountIsOk(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        return loginService.accountVerification(username, password);
    }

}
