package com.orient.weibao.utils;

import com.orient.utils.CommonTools;
import com.orient.utils.PathTools;
import com.orient.utils.PropertiesUtil;

import java.io.File;

public class WeibaoPropertyUtil {

    public static String getPropertyValueConfigured(String propertyName, String propertyFileName, String defaultValue) {
        //the file
        String filePath = PathTools.getRootPath() + File.separator + "WEB-INF" + File.separator + "classes"
                + File.separator + propertyFileName;
        String value = PropertiesUtil.readValue(filePath, propertyName);

        if (CommonTools.isNullString(value)) {
            return defaultValue;
        }
        return value;
    }

}
