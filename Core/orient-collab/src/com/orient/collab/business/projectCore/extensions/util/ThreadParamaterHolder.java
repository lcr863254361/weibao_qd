package com.orient.collab.business.projectCore.extensions.util;

import EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap;

import java.util.Map;

/**
 * ThreadParamaterHolder
 *
 * @author Seraph
 *         2016-08-15 上午9:49
 */
public final class ThreadParamaterHolder {

    static public <T> void put(Class<T> cls, T object){
        ThreadLocal<T> tl = new ThreadLocal<>();
        ThreadLocal<T> existedTl = typeMap.putIfAbsent(cls, tl);
        if(existedTl != null){
            existedTl.set(object);
        }else{
            tl.set(object);
        }
    }

    static public <T> T get(Class<T> cls){
        ThreadLocal<T> tl = typeMap.get(cls);
        if(tl == null){
            return null;
        }else{
            return tl.get();
        }
    }

    static public <T> void put(String marker, T object){
        ThreadLocal<T> tl = new ThreadLocal<>();
        ThreadLocal<T> existedTl = markerMap.putIfAbsent(marker, tl);
        if(existedTl != null){
            existedTl.set(object);
        }else{
            tl.set(object);
        }
    }

    static public <T> T get(String marker){
        ThreadLocal<T> tl = markerMap.get(marker);
        if(tl == null){
            return null;
        }else{
            return tl.get();
        }
    }

    static private Map<Class<?>, ThreadLocal> typeMap = new ConcurrentHashMap();
    static private Map<String, ThreadLocal> markerMap = new ConcurrentHashMap();

}
