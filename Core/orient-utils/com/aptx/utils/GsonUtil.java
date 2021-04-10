package com.aptx.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public abstract class GsonUtil {
    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final Type TYPE_STRING_MAP = new TypeToken<Map<String, String>>(){}.getType();
    public static final Type TYPE_STRING_LIST_MAP = new TypeToken<List<Map<String, String>>>(){}.getType();

    public static final Gson DEFAULT_GSON = getDefaultGson();

    public static Gson getDefaultGson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.TRANSIENT)
                .setDateFormat(DATE_FORMAT_STRING).create();
        return gson;
    }

    public static String toJson(Object object) {
        return DEFAULT_GSON.toJson(object);
    }

    public static <T> T fromJson(String json, TypeToken typeToken) {
        return DEFAULT_GSON.fromJson(json, typeToken.getType());
    }

    public static <T> T fromJson(String json, Type type) {
        return DEFAULT_GSON.fromJson(json, type);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return DEFAULT_GSON.fromJson(json, clazz);
    }

    public static <T> T fromJson(Reader reader, TypeToken typeToken) {
        return DEFAULT_GSON.fromJson(reader, typeToken.getType());
    }

    public static <T> T fromJson(Reader reader, Type type) {
        return DEFAULT_GSON.fromJson(reader, type);
    }

    public static <T> T fromJson(Reader reader, Class<T> clazz) {
        return DEFAULT_GSON.fromJson(reader, clazz);
    }

    public static Map<String, String> fromStringMapJson(String json) {
        return DEFAULT_GSON.fromJson(json, TYPE_STRING_MAP);
    }

    public static List<Map<String, String>> fromStringMapListJson(String json) {
        return DEFAULT_GSON.fromJson(json, TYPE_STRING_LIST_MAP);
    }

    public static <T> T getCopy(T src) {
        String json = DEFAULT_GSON.toJson(src);
        T target = DEFAULT_GSON.fromJson(json, (Class<T>) src.getClass());
        return target;
    }
}
