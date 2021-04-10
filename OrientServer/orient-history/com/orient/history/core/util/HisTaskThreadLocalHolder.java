package com.orient.history.core.util;

import EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public final class HisTaskThreadLocalHolder {

    static private Map<String, ThreadLocal> concurrentHashMap = new ConcurrentHashMap();

    static public <T> void put(String key, T object) {
        ThreadLocal<T> tl = new ThreadLocal<>();
        ThreadLocal<T> existedTl = concurrentHashMap.putIfAbsent(key, tl);
        if (existedTl != null) {
            existedTl.set(object);
        } else {
            tl.set(object);
        }
    }

    static public <T> T get(String key) {
        ThreadLocal<T> tl = concurrentHashMap.get(key);
        if (tl == null) {
            return null;
        } else {
            return tl.get();
        }
    }

    static public void removeKey(String key) {
        concurrentHashMap.remove(key);
    }
}
