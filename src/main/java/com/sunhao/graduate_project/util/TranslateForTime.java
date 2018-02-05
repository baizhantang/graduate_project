package com.sunhao.graduate_project.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class TranslateForTime {
    /**
     * 把前端传回的时间数据转换为MySQL的date格式
     */
    private Map<String, String> month = new HashMap<>();

    {
        month.put("January", "01");
        month.put("February", "02");
        month.put("March", "03");
        month.put("April", "04");
        month.put("May", "05");
        month.put("June", "06");
        month.put("July", "07");
        month.put("August", "08");
        month.put("September", "09");
        month.put("October", "10");
        month.put("November", "11");
        month.put("December", "12");
    }
    public Date translate(String inputTime) {
        inputTime.trim();
        int indexOfFirstSpace = inputTime.indexOf(" ");
        int indexOfLastSpace = inputTime.lastIndexOf(" ");

        String replaceStr = inputTime.substring(indexOfFirstSpace+1, indexOfLastSpace);
        inputTime = inputTime.replace(replaceStr, month.get(replaceStr));
        inputTime = inputTime.replace(' ', '-');

        Date result = Date.valueOf(inputTime);
        return result;
    }
}
