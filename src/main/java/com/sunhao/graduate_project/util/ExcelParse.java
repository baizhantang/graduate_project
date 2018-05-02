package com.sunhao.graduate_project.util;

import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelParse {
    /**
     * 解析人员名单Excel
     * @param wb
     * @return
     */
    public static List<Map<String, String>> parseExcel(Workbook wb) {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Sheet sheet = wb.getSheetAt(0);
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        //除去表头和第一行
        for (int i = firstRow + 1; i < lastRow + 1; i++) {
            Map<String, String> map = new HashMap();

            Row row = sheet.getRow(i);
            int firstCell = row.getFirstCellNum();
            int lastCell = row.getLastCellNum();


            for (int j = firstCell; j < lastCell; j++) {

                Cell cell2 = sheet.getRow(firstRow).getCell(j);
                //CellType.STRING
                cell2.setCellType(1);
                String key = cell2.getStringCellValue();
                key = DIYReplaceSpace(key);

                Cell cell = row.getCell(j);

                cell.setCellType(1);

                String val = cell.getStringCellValue();
                val = DIYReplaceSpace(val);


                map.put(key, val);


            }
            data.add(map);
        }
        return data;
    }

    public static String DIYReplaceSpace(String inputStr) {
        inputStr = inputStr.replace(" ", "");
        inputStr = inputStr.replace("\u00A0", "");
        return inputStr;
    }
}
