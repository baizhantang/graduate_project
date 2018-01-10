package com.sunhao.graduate_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunhao.graduate_project.util.GenerateUUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {


    public String saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            String path = null;
            String usePath = GenerateUUID.getUUID(file.getOriginalFilename());
            path = "C:\\Users\\Administrator\\Desktop\\apache-tomcat-7.0.82\\webapps\\ROOT\\file\\"  + usePath; //服务器配置
//            path = "C:\\Users\\syhc\\Documents\\HBuilderProject\\test\\file\\" + usePath; //家配置
//            path = "F:\\nutStore\\HBuilderProject\\test\\file\\" + usePath;  //办公室配置
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

//            Map<String, String> okMap = new HashMap<>();
//
//            okMap.put("path", path);
//
//            String ok = JSON.toJSONString(okMap);
//
//            JSONObject jsonOk = JSON.parseObject(ok);

            return usePath;
        } else {
            System.out.println("文件为空");
            return null;
        }
    }
}
