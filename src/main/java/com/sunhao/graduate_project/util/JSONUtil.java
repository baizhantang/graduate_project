package com.sunhao.graduate_project.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class JSONUtil {
    public static JSON getJSON(String[] key, String[] value) {
        if (key.length != value.length) {
            return null;
        }
        Map<String, String> returnMap = new HashMap<>();
        for (int i = 0; i < key.length; i++) {
            returnMap.put(key[i], value[i]);
        }

        JSON json = (JSON) JSON.parse(JSON.toJSONString(returnMap));
        return json;
    }
}
