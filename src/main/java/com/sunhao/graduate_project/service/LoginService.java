package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunhao.graduate_project.domain.User;
import com.sunhao.graduate_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public Object accountVerification(String username, String password) {
        User user = userRepository.findByUsername(username);
        Map<String, String> fail = new HashMap<>();
        Map<String, String> oks = new HashMap<>();

        fail.put("status", "failed");
        oks.put("status", "ok");

        String failed = JSON.toJSONString(fail);
        String ok = JSON.toJSONString(oks);

        JSONObject jsonF = JSON.parseObject(failed);
        JSONObject jsonO = JSON.parseObject(ok);
        
        if (user == null) {
            return jsonF;
        } else if (user.getPassword().equals(password)) {
            return jsonO;
        } else {
            return jsonF;
        }

    }
}
