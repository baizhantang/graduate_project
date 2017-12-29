package com.sunhao.graduate_project.controller;

import com.sunhao.graduate_project.service.ExcelService;
import com.sunhao.graduate_project.util.ExcelParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping(value = "/excelParse")
    public List<Map<String, String>> importExcel(@RequestParam("file") MultipartFile excelFile)
            throws  IOException{
        return excelService.excelParse(excelFile);
    }
}
