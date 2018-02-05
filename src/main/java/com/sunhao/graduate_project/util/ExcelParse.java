package com.sunhao.graduate_project.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelParse {
    /**
     * 解析人员名单Excel
     * @param fis
     * @return
     */
    public static List<Map<String, String>> parseExcel(InputStream fis) {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        try {
            HSSFWorkbook book = new HSSFWorkbook(fis);
            HSSFSheet sheet = book.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();
            //除去表头和第一行
            for (int i = firstRow + 1; i < lastRow + 1; i++) {
                Map<String, String> map = new HashMap();

                HSSFRow row = sheet.getRow(i);
                int firstCell = row.getFirstCellNum();
                int lastCell = row.getLastCellNum();


                for (int j = firstCell; j < lastCell; j++) {

                    HSSFCell cell2 = sheet.getRow(firstRow).getCell(j);
                    cell2.setCellType(CellType.STRING);
                    String key = cell2.getStringCellValue();

                    HSSFCell cell = row.getCell(j);

                    cell.setCellType(CellType.STRING);

                    String val = cell.getStringCellValue();


                    map.put(key, val);


                }
                data.add(map);
//                System.out.println(map);
            }
//            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
