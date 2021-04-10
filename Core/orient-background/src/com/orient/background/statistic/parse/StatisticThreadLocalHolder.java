package com.orient.background.statistic.parse;

import java.util.HashMap;
import java.util.Map;


/**
 * 用于 统计信息 存储额外的统计输入所需信息
 *
 * @author enjoyjavapan
 */
public class StatisticThreadLocalHolder {
    public static final short RESULT_SUCCESS = 1;
    public static final short RESULT_FAIL = 0;


    /**
     * 参数
     */
    private static ThreadLocal<Map<String, Object>> paramertersLocal = new ThreadLocal<Map<String, Object>>();


    public static Map<String, Object> getParamerters() {
        if (paramertersLocal.get() == null) {
            paramertersLocal.set(new HashMap<String, Object>());
        }
        return paramertersLocal.get();
    }

    public static Object getParamerter(String key) {
        if (paramertersLocal.get() == null) {
            paramertersLocal.set(new HashMap<String, Object>());
        }
        return paramertersLocal.get().get(key);
    }

    public static void putParamerter(String key, Object value) {
        if (paramertersLocal.get() == null) {
            paramertersLocal.set(new HashMap<String, Object>());
        }
        paramertersLocal.get().put(key, value);
    }

    public static void clearParameters() {
        paramertersLocal.remove();
    }
}
