package com.aptx.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

public abstract class NumberUtil {
    public static final Pattern PATTERN_NUMBER = Pattern.compile("[0-9\\.]+");
    public static final Pattern PATTERN_LONG = Pattern.compile("[0-9]+");
    public static final Pattern PATTERN_DOUBLE = Pattern.compile("[0-9]+\\.[0-9]+");

    public static String[] octalsToRadixes(Object[] objs, int radix) {
        if(objs==null || objs.length==0 || radix<=1) {
            return new String[0];
        }
        else {
            String[] retArr = new String[objs.length];
            for (int i =0; i<objs.length; i++) {
                retArr[i] = NumberUtil.octalToRadix(objs[i], radix);
            }
            return retArr;
        }
    }

    public static String octalToRadix(Object obj, int radix) {
        if(obj==null || radix<=1) {
            return null;
        }
        else {
            Long num = toNumber(obj, Long.class);
            return Long.toString(num, radix);
        }
    }

    public static boolean isNumber(Object obj) {
        if (obj == null) {
            return false;
        }
        else if(obj instanceof String) {
            return PATTERN_NUMBER.matcher((String) obj).matches();
        }
        else if(obj instanceof Byte || obj instanceof Short || obj instanceof Integer || obj instanceof Long
                || obj instanceof Float || obj instanceof Double || obj instanceof BigInteger || obj instanceof BigDecimal) {
            return true;
        }
        else {
            return false;
        }
    }

    public static <T> T toNumber(Object obj, Class<T> clazz) {
        if (obj == null) {
            return null;
        }
        else if(clazz == Byte.class) {
            return (T) Byte.valueOf(obj.toString());
        }
        else if(clazz == Short.class) {
            return (T) Short.valueOf(obj.toString());
        }
        else if(clazz == Integer.class) {
            return (T) Integer.valueOf(obj.toString());
        }
        else if(clazz == Long.class) {
            return (T) Long.valueOf(obj.toString());
        }
        else if(clazz == Float.class) {
            return (T) Float.valueOf(obj.toString());
        }
        else if(clazz == Double.class) {
            return (T) Double.valueOf(obj.toString());
        }
        else if(clazz == BigInteger.class) {
            return (T) new BigInteger(obj.toString());
        }
        else if(clazz == BigDecimal.class) {
            return (T) new BigDecimal(obj.toString());
        }
        else if(clazz == String.class) {
            return (T) obj.toString();
        }
        else {
            throw new RuntimeException("Unsupported type: "+clazz.getName());
        }
    }
}
