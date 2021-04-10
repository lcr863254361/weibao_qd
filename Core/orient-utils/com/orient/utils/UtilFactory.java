package com.orient.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UtilFactory {

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K> ArrayList<K> newArrayList() {
        return new ArrayList<>();
    }

    public static <K> HashSet<K> newHashSet() {
        return new HashSet<>();
    }

}
