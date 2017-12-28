package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping(value = "/save")
    public Object savePhoto(@RequestParam("file")MultipartFile file) {
        System.out.println(1);

        return photoService.savePhoto(file);
    }
}
