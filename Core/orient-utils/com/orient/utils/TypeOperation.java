package com.orient.utils;

import java.util.List;

/**
 * The type operation class,
 * which can be used to convert from a type to a desired type,
 * or judge whether a type empty etc.
 *
 * @author supengfei1988@foxmail.com
 */
public class TypeOperation {

    /**
     * judge an Object, if null, return "", else return its value of toString()
     *
     * @param obj the object to be transformed
     * @return the converted String
     */
    public static String objectToString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * judge a string, if null, return "", else return the original string
     *
     * @param str
     * @return
     */
    public static String nullToString(String str) {
        if (isNullString(str)) {
            return "";
        } else return str;
    }

    /**
     * judge whether a List is Empty
     *
     * @param list
     */
    public static boolean isEmptyList(List<?> list) {
        return list == null || list.isEmpty();
    }

    //judge a string whether it's null
    private static boolean isNullString(String str) {
        if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("NULL")) {
            return true;
        } else {
            return false;
        }
    }
}
