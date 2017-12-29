package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/save")
    public Object savePhoto(@RequestParam("file")MultipartFile file) {

        return fileService.saveFile(file);
    }
}
