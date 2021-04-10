package com.orient.weibao.utils;

import java.util.regex.Pattern;

public class CommonOperate {
    public static String getValue(String value){
        if (value != null && !"".equals(value)) {
            if (value.contains(".")) {
                value = value.split("\\.")[0];
            }
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isIntNumber = pattern.matcher(value).matches();
            if (!isIntNumber) {
                value = "0";
            }
        }else {
            value="0";
        }
        return value;
    }
}
