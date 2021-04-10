package com.aptx.utils;

import org.apache.commons.beanutils.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Map;

public abstract class ReflectUtil {
    public static Object getProperty(Object bean, String name) {
        try {
            return PropertyUtils.getProperty(bean, name);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String[] getArrayProperty(Object bean, String name) {
        try {
            return BeanUtils.getArrayProperty(bean, name);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void setProperty(Object bean, String name, Object value) {
        try {
            PropertyUtils.setProperty(bean, name, value);
        }
        catch (Exception e) {
            // no operation
        }
    }

    public static Object cloneBean(Object bean) {
        try {
            return BeanUtils.cloneBean(bean);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void copyProperty(Object bean, String name, Object value) {
        try {
            BeanUtils.copyProperty(bean, name, value);
        }
        catch (Exception e) {
            // no operation
        }
    }

    public static void copyProperties(Object from, Object to) {
        try {
            BeanUtils.copyProperties(to, from);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> describe(Object bean) {
        try {
            return BeanUtils.describe(bean);
        }
        catch (Exception e) {
            return null;
        }
    }

    /****************************** Collections ******************************/
    public static void changeValueInCollection(Collection collection, String name, Object value) {
        BeanPropertyValueChangeClosure closure = new BeanPropertyValueChangeClosure(name, value);
        CollectionUtils.forAllDo(collection, closure);
    }

    public static void filterValueInCollection(Collection collection, String name, Object value) {
        BeanPropertyValueEqualsPredicate predicate = new BeanPropertyValueEqualsPredicate(name, value);
        CollectionUtils.filter(collection, predicate);
    }

    public static Collection collectValuesInCollection(Collection collection, String name) {
        BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer(name);
        return CollectionUtils.collect(collection, transformer);
    }
}
