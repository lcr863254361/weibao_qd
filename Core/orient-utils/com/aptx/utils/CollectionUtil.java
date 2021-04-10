package com.aptx.utils;

import com.google.common.base.Function;
import com.google.common.collect.*;

import java.util.*;

public abstract class CollectionUtil {
    @SuppressWarnings("unchecked")
    public static <M> M toType(Object object) {
        return (M) object;
    }

    public static <M> M toType(Object object, Class<? extends M> clazz) {
        if(object == null) {
            return null;
        }
        else {
            return clazz.cast(object);
        }
    }

    public static <M> M first(Collection<M> collection) {
        if(isNullOrEmpty(collection)) {
            return null;
        }
        return Lists.newArrayList(collection).get(0);
    }

    public static <M> M last(Collection<M> collection) {
        if(isNullOrEmpty(collection)) {
            return null;
        }
        return Lists.newArrayList(collection).get(collection.size()-1);
    }

    public static <M> M firstKey(Map<M, ?> map) {
        if(isNullOrEmpty(map)) {
            return null;
        }
        return Lists.newArrayList(map.keySet()).get(0);
    }

    public static <N> N firstValue(Map<?, N> map) {
        if(isNullOrEmpty(map)) {
            return null;
        }
        return Lists.newArrayList(map.values()).get(0);
    }

    public static <M> M lastKey(Map<M, ?> map) {
        if(isNullOrEmpty(map)) {
            return null;
        }
        return Lists.newArrayList(map.keySet()).get(map.size()-1);
    }

    public static <N> N lastValue(Map<?, N> map) {
        if(isNullOrEmpty(map)) {
            return null;
        }
        return Lists.newArrayList(map.values()).get(map.size()-1);
    }

    /*************************** Ordering ***************************/
    public static Ordering getNaturalOrdering(boolean isNullsFirst) {
        Ordering ordering = Ordering.natural();
        if(isNullsFirst) {
            ordering.nullsFirst();
        }
        else {
            ordering.nullsLast();
        }
        return ordering;
    }

    public static <M> Ordering<M> getNaturalOrdering(boolean isNullsFirst, Function<M, ? extends Comparable> function) {
        Ordering<M> ordering = getNaturalOrdering(isNullsFirst).onResultOf(function);
        return ordering;
    }

    public static <M> List<M> topologicalSort(Multimap<M, M> map) {
        List<M> retList = Lists.newLinkedList();

        Multimap<M, M> tempMap = ArrayListMultimap.create();
        Set<M> tempNodes = Sets.newLinkedHashSet();
        for(Map.Entry<M, M> entry : map.entries()) {
            M key = entry.getKey();
            M val = entry.getValue();
            if(key==null && val!=null) {
                tempNodes.add(val);
            }
            else if(key!=null && val==null) {
                tempNodes.add(key);
            }
            else if(key!=null && val!=null) {
                tempMap.put(key, val);
                tempNodes.add(key);
                tempNodes.add(val);
            }
        }

        while(!tempMap.isEmpty()) {
            Set<M> keys = Sets.newHashSet(tempMap.keySet());
            Collection<M> values = tempMap.values();
            keys.removeAll(values);
            if(!keys.isEmpty()) {
                retList.addAll(keys);
                for(M key : keys) {
                    tempMap.removeAll(key);
                    tempNodes.remove(key);
                }
            }
            else {
                throw new RuntimeException("AOV网络中存在有向环："+tempMap);
            }
        }

        if(!tempNodes.isEmpty()) {
            for(M singleNode: tempNodes) {
                if(!retList.contains(singleNode)) {
                    retList.add(singleNode);
                }
            }
        }
        return retList;
    }

    public static <M> List<M> getRootNodes(Multimap<M, M> map) {
        List<M> retList = Lists.newLinkedList();

        Multimap<M, M> tempMap = ArrayListMultimap.create();
        Set<M> tempNodes = Sets.newLinkedHashSet();
        for(Map.Entry<M, M> entry : map.entries()) {
            M key = entry.getKey();
            M val = entry.getValue();
            if(key==null && val!=null) {
                tempNodes.add(val);
            }
            else if(key!=null && val==null) {
                tempNodes.add(key);
            }
            else if(key!=null && val!=null) {
                tempMap.put(key, val);
                tempNodes.add(key);
                tempNodes.add(val);
            }
        }

        if(!tempMap.isEmpty()) {
            Set<M> keys = Sets.newHashSet(tempMap.keySet());
            Collection<M> values = tempMap.values();
            keys.removeAll(values);
            if(!keys.isEmpty()) {
                retList.addAll(keys);
            }

            keys = Sets.newHashSet(tempMap.keySet());
            keys.addAll(values);
            tempNodes.removeAll(keys);
        }
        retList.addAll(tempNodes);

        return retList;
    }

    /*************************** Deal Null & Empty ***************************/
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection==null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map==null || map.isEmpty();
    }

    public static String[] nullToEmpty(String[] arr) {
        String[] retArr = new String[arr!=null ? arr.length : 0];
        if(arr == null) {
            return retArr;
        }
        for (int i=0; i<arr.length; i++) {
            String str = arr[i];
            if(str != null) {
                retArr[i] = str;
            }
            else {
                retArr[i] = "";
            }

        }
        return retArr;
    }

    public static List<String> nullToEmpty(Collection<String> collection) {
        List<String> retList = Lists.newLinkedList();
        if(collection == null) {
            return retList;
        }
        for(String str : collection) {
            if(str != null) {
                retList.add(str);
            }
            else {
                retList.add("");
            }
        }
        return retList;
    }

    public static <T> Map<T, String> nullToEmpty(Map<T, String> map) {
        Map<T, String> retMap = Maps.newLinkedHashMap();
        if(map == null) {
            return retMap;
        }
        for(Map.Entry<T, String> entry: map.entrySet()) {
            T key = entry.getKey();
            String val = entry.getValue();
            if(val != null) {
                retMap.put(key, val);
            }
            else {
                retMap.put(key, "");
            }
        }
        return retMap;
    }

    /*************************** Split ***************************/
    public static <M> List<M> subList(List<M> list, int from, int to) {
        if(isNullOrEmpty(list)) {
            return new LinkedList<>();
        }
        int start = Math.min(from, list.size()-1);
        int end = Math.min(to, list.size()-1);
        return list.subList(start, end);
    }

    public static <M> List<Collection<M>> split(Collection<M> collection, int splitSize) {
        List<Collection<M>> retList = new LinkedList<>();
        if(isNullOrEmpty(collection)) {
            return retList;
        }
        Collection<M> elemCollection = null;
        int index = 0;
        for(M value : collection) {
            if(index%splitSize == 0) {
                elemCollection = new LinkedList<>();
                retList.add(elemCollection);
            }
            elemCollection.add(value);
            index++;
        }
        return retList;
    }
}
