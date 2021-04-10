package com.orient.collabdev.business.version.util;

/**
 *
 */
public final class ProjectVersionUtil {


    static private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    static public void put(Integer nextVersion) {
        threadLocal.set(nextVersion);
    }

    static public Integer get() {
        return threadLocal.get();
    }
}
