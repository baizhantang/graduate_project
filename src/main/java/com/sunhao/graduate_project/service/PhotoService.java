package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class PhotoService {


    public Object savePhoto(MultipartFile file) {
        if (!file.isEmpty()) {
            String path = null;
            path = "F:\\upload\\" + file.getOriginalFilename();
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(path)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("上传失败," + e.getMessage());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println( "上传失败," + e.getMessage());
                return null;
            }

            Map<String, String> okMap = new HashMap<>();

            okMap.put("path", path);

            String ok = JSON.toJSONString(okMap);

            JSONObject jsonOk = JSON.parseObject(ok);

            return jsonOk;
        } else {
            System.out.println("文件为空");
            return null;
        }
    }
}
