package com.sunhao.graduate_project.service;

import com.sunhao.graduate_project.util.ExcelParse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {
    public String excelParse(@RequestParam("file") MultipartFile excelFile) throws IOException {
        if (null == excelFile) {
            String result = "模板文件为空,请选择文件";
            return result;
        }

        InputStream fis = excelFile.getInputStream();
        List<Map<String, String>> data = ExcelParse.parseExcel(fis);

        System.out.println(data);
        return "success";
    }
}
