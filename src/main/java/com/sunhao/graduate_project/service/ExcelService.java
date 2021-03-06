package com.sunhao.graduate_project.service;

import com.sunhao.graduate_project.util.ExcelParse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Service
public class ExcelService {
    /**
     * 解析人员名单
     */
    public List<Map<String, String>> excelParse(@RequestParam("file") MultipartFile excelFile) throws IOException {
        if (null == excelFile) {
            String result = "模板文件为空,请选择文件";
            return null;
        }

        int indexSuffix = excelFile.getOriginalFilename().indexOf('.');
        Workbook workbook;
        if (!"xls".equals(excelFile.getOriginalFilename().substring(indexSuffix+1))) {
            if (!"xlsx".equals(excelFile.getOriginalFilename().substring(indexSuffix+1))) {
                String result = "文件类型错误";
                return null;
            } else {
                InputStream fis = excelFile.getInputStream();
                workbook = new XSSFWorkbook(fis);
            }
        } else {
            InputStream fis = excelFile.getInputStream();
            workbook = new HSSFWorkbook(fis);
        }

        List<Map<String, String>> data = ExcelParse.parseExcel(workbook); //调用工具类进行解析

        return data;
    }
}
