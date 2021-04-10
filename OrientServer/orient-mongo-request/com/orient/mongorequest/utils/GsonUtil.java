package com.orient.mongorequest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtil {

    private static Gson mGson;

    private GsonUtil() {

    }

    static {
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    /**
     * 把一个对象转为json字符串
     *
     * @param object 需要被转为json字符串的对象
     * @return
     */
    public static String toJsonString(Object object) {
        String jsonString = null;
        if (mGson != null) {
            jsonString = mGson.toJson(object);
        }
        return jsonString;
    }

    /**
     * 把json字符串解析为一个对象
     *
     * @param jsonString json字符串
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T toBean(String jsonString, Class<T> clz) {
        T t = null;
        if (mGson != null) {
            t = mGson.fromJson(jsonString, clz);
        }
        return t;
    }

    public static <T> T toBean(Reader reader, Class<T> clz) {
        T t = null;
        if (mGson != null) {
            t = mGson.fromJson(reader, clz);
        }
        return t;
    }

    public static <T> T toBean(InputStream inputStream, Class<T> clz) {
        T t = null;
        if (mGson != null) {
            t = mGson.fromJson(new JsonReader(new InputStreamReader(inputStream)), clz);
        }
        return t;
    }

    public static <T> T toBean(File file, Class<T> clz) {
        T t = null;
        if (mGson != null) {
            try {
                t = mGson.fromJson(new JsonReader(new InputStreamReader(new FileInputStream(file))), clz);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public static <T> List<T> toList(String jsonString, Class<T> clz) {
        List<T> list = new ArrayList<>();
        if (mGson != null) {
            JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
            array.forEach(jsonElement -> list.add(mGson.fromJson(jsonElement, clz)));
        }
        return list;
    }

    public static <T> List<T> toList(InputStream inputStream, Class<T> clz) {
        List<T> list = null;
        if (mGson != null) {
            list = mGson.fromJson(new JsonReader(new InputStreamReader(inputStream)), new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    public static <T> List<T> toList(Reader reader, Class<T> clz) {
        List<T> list = null;
        if (mGson != null) {
            list = mGson.fromJson(new JsonReader(reader), new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    public static <T> List<T> toList2(Reader reader, Class<T> clz) {
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(reader).getAsJsonArray();
        for (JsonElement element : array) {
            list.add((mGson.fromJson(element, clz)));
        }
        return list;
    }

    public static <T> List<Map<String, T>> toListMaps(String jsonString) {
        List<Map<String, T>> list = null;
        if (mGson != null) {
            list = mGson.fromJson(jsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    public static <T> Map<String, T> toMap(String jsonString) {
        Map<String, T> map = null;
        if (mGson != null) {
            map = mGson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
