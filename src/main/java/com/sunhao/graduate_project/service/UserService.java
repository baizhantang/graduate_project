package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunhao.graduate_project.entity.User;
import com.sunhao.graduate_project.repository.UserRepo;
import com.sunhao.graduate_project.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

//    登录
    public Object login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            String[] key = {"isSuccess"};
            String[] value = {"true"};
            return JSONUtil.getJSON(key, value);
        } else {
            String[] key = {"isSuccess"};
            String[] value = {"false"};
            return JSONUtil.getJSON(key, value);
        }
    }

//    注册
    public Object registUser(String username, String password, String name) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setName(name);
            userRepo.save(newUser);

            String[] key = {"isSuccess"};
            String[] value = {"true"};
            return JSONUtil.getJSON(key, value);
        } else {
            String[] key = {"isSuccess"};
            String[] value = {"false"};
            return JSONUtil.getJSON(key, value);
        }
    }
}
