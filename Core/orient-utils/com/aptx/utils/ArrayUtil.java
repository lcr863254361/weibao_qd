package com.aptx.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class ArrayUtil extends ArrayUtils {
    public static <T> Class<T> getElementClass(T... array) {
        if(array == null) {
            return null;
        }
        else {
            return (Class<T>) array.getClass().getComponentType();
        }
    }

    public static <T> T[] newArray(Class<T> clazz, int length) {
        return (T[]) Array.newInstance(clazz, length);
    }

    public static <T> T first(T... array) {
        if(ArrayUtils.isEmpty(array)) {
            return null;
        }
        return array[0];
    }

    public static <T> T last(T... array) {
        if(ArrayUtils.isEmpty(array)) {
            return null;
        }
        return array[array.length-1];
    }

    public static <T> int lastIndexOfNotNull(T... array) {
        if(ArrayUtils.isEmpty(array)) {
            return -1;
        }
        int index = array.length-1;
        while(index>=0 && array[index]==null) {
            index --;
        }
        return index;
    }

    public static <T> Set<Integer> indexesOf(T[] array, T obj) {
        Set<Integer> retSet = Sets.newTreeSet();
        if(ArrayUtils.isEmpty(array)) {
            return retSet;
        }
        else {
            for(int i=0; i<array.length; i++) {
                if(obj==null && array[i]==null) {
                    retSet.add(i);
                }
                else if(obj!=null && obj.equals(array[i])) {
                    retSet.add(i);
                }
            }
            return retSet;
        }
    }

    public static boolean equals(Object[] array, Object obj) {
        if(array==null || obj==null || !obj.getClass().isArray()) {
            return false;
        }
        else {
            Object[] array2 = (Object[]) obj;
            return Arrays.equals(array, array2);
        }
    }

    public static <T> boolean containsAll(T[] array, T... objs) {
        if(ArrayUtils.isEmpty(objs)) {
            return ArrayUtils.contains(array, null);
        }
        else {
            for(T obj : objs) {
                if(!ArrayUtils.contains(array, obj)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static <T> T[] toValidArray(T... array) {
        if(array == null) {
            return null;
        }
        int lastIndex = lastIndexOfNotNull(array);
        if(lastIndex == -1) {
            return newArray(getElementClass(array), 0);
        }
        else {
            return ArrayUtils.subarray(array, 0, lastIndex + 1);
        }
    }

    public static <T> List<T> toList(T... array) {
        if(array == null) {
            return Lists.newArrayList();
        }
        else {
            return Lists.newArrayList(array);
        }
    }

    public static <T> List<T> toValidList(T... array) {
        return toList(toValidArray(array));
    }

    public static <T> T[] addAll(T[] array, int index, T... objs) {
        if(ArrayUtils.isEmpty(objs)) {
            return ArrayUtils.clone(array);
        }
        T[] retArray = null;
        if(array == null) {
            retArray = newArray(getElementClass(objs), objs.length);
            System.arraycopy(objs, 0, retArray, 0, objs.length);
        }
        else {
            retArray = newArray(getElementClass(array), array.length+objs.length);
            System.arraycopy(array, 0, retArray, 0, index);
            System.arraycopy(objs, 0, retArray, index, objs.length);
            System.arraycopy(array, index, retArray, index+objs.length, array.length-index);
        }
        return retArray;
    }

    public static <T> T[] validAdd(T[] array, T obj) {
        return ArrayUtils.add(toValidArray(array), obj);
    }

    public static <T> T[] validAddAll(T[] array, T... objs) {
        T[] newArr = toValidArray(array);
        return addAll(newArr, newArr.length, objs);
    }

    public static <T> T[] removeFirst(T[] array, T obj) {
        int index = ArrayUtils.indexOf(array, obj);
        if(index < 0) {
            return ArrayUtils.clone(array);
        }
        else {
            return ArrayUtils.remove(array, index);
        }
    }

    public static <T> T[] removeLast(T[] array, T obj) {
        int index = ArrayUtils.lastIndexOf(array, obj);
        if(index < 0) {
            return ArrayUtils.clone(array);
        }
        else {
            return ArrayUtils.remove(array, index);
        }
    }

    public static <T> T[] removeAllElements(T[] array, T... objs) {
        if(ArrayUtils.isEmpty(array) || ArrayUtils.isEmpty(objs)) {
            return ArrayUtils.clone(array);
        }
        Set<Integer> indexes = Sets.newHashSet();
        for(T obj : objs) {
            indexes.addAll(indexesOf(array, obj));
        }
        Integer[] indexesArr = indexes.toArray(new Integer[indexes.size()]);
        return ArrayUtils.removeAll(array, toPrimitive(indexesArr));
    }
}
