package com.sunhao.graduate_project.util;

import java.util.UUID;

public class GenerateUUID {

    /**
     * 产生随机序列
     * @param fileName
     * @return
     */
    public static String getUUID(String fileName) {
        int suffixIndex = fileName.indexOf('.');
        String suffix = fileName.substring(suffixIndex);

        String uuid = UUID.randomUUID().toString().replace("-", "");

        return uuid + suffix;
    }
}
