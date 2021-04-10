package com.orient.utils;

import org.reflections.Reflections;


/**
 * Orient Reflection Util
 *
 * @author Seraph
 *         2016-12-02 下午6:50
 */
public class OrientReflectionUtil {

    private static final Reflections reflections = new Reflections("com.orient");

    public static synchronized Reflections getReflections() {
        return reflections;
    }

}
