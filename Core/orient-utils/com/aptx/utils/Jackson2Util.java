package com.aptx.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;

public abstract class Jackson2Util {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final ObjectMapper DEFAULT_OBJECT_MAPPER = getDefaultObjectMapper();

    public static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.setDateFormat(DATE_FORMAT);
        return objectMapper;
    }

    public static TypeFactory getTypeFactory() {
        return DEFAULT_OBJECT_MAPPER.getTypeFactory();
    }

    public static JavaType constructJavaType(Class<?> mainClass, Class<?>... subClasses) {
        return getTypeFactory().constructParametricType(mainClass, subClasses);
    }

    public static JavaType constructJavaType(Class<?> mainClass, JavaType... subClasses) {
        return getTypeFactory().constructParametricType(mainClass, subClasses);
    }

    public static String toJson(Object object) {
        try {
            return DEFAULT_OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            return null;
        }
    }

    public static void toJson(Object object, Writer writer) {
        try {
            DEFAULT_OBJECT_MAPPER.writeValue(writer, object);
            FileOperateUtil.close(writer);
        }
        catch (IOException e) {
            //Ignore
        }
    }

    public static <T> T convert(Object object, TypeReference<T> typeReference) {
        return DEFAULT_OBJECT_MAPPER.convertValue(object, typeReference);
    }

    public static <T> T convert(Object object, JavaType javaType) {
        return DEFAULT_OBJECT_MAPPER.convertValue(object, javaType);
    }

    public static <T> T convert(Object object, Class<T> clazz) {
        return DEFAULT_OBJECT_MAPPER.convertValue(object, clazz);
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            //TypeReference<T> typeReference = new TypeReference<List<String>>(){};
            return DEFAULT_OBJECT_MAPPER.readValue(json, typeReference);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(String json, JavaType javaType) {
        try {
            //JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, String.class);
            return DEFAULT_OBJECT_MAPPER.readValue(json, javaType);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return DEFAULT_OBJECT_MAPPER.readValue(json, clazz);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(URL url, TypeReference<T> typeReference) {
        try {
            return DEFAULT_OBJECT_MAPPER.readValue(url, typeReference);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(URL url, JavaType javaType) {
        try {
            return DEFAULT_OBJECT_MAPPER.readValue(url, javaType);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(URL url, Class<T> clazz) {
        try {
            return DEFAULT_OBJECT_MAPPER.readValue(url, clazz);
        }
        catch (IOException e) {
            return null;
        }
    }
}
