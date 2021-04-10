package com.orient.utils;

import org.apache.commons.beanutils.converters.DateTimeConverter;

import java.util.Date;

/**
 * 对象拷贝日期支持
 *
 * @author enjoy
 * @creare 2016-05-17 10:05
 */
public class UtilDateConvert extends DateTimeConverter {

    public UtilDateConvert() {
    }

    public UtilDateConvert(Object defaultValue) {
        super(defaultValue);
    }

    @SuppressWarnings("rawtypes")
    protected Class getDefaultType() {
        return Date.class;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Object convertToType(Class arg0, Object arg1) throws Exception {
        if (arg1 == null) {
            return null;
        }
        String value = arg1.toString().trim();
        if (value.length() == 0) {
            return null;
        }
        return super.convertToType(arg0, arg1);
    }

}
