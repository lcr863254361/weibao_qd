package com.orient.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtil {

    static String string2Json(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 20);
        sb.append('\"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        sb.append('\"');
        return sb.toString();
    }

    static String number2Json(Number number) {
        return number.toString();
    }

    static String boolean2Json(Boolean bool) {
        return bool.toString();
    }

    static String map2Json(Map<String, Object> map) {
        if (map.isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder(map.size() << 4);
        sb.append('{');
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Object value = map.get(key);
            sb.append('\"');
            sb.append(key);
            sb.append('\"');
            sb.append(':');
            sb.append(toJson(value));
            sb.append(',');
        }
        // 将最后的 ',' 变为 '}':
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }

    static String list2Json(List<Object[]> list) {
        if (list.size() == 0) return "[]";
        StringBuilder sb = new StringBuilder(list.size() << 4);
        sb.append('[');
        for (Object o : list) {
            sb.append(toJson(o));
            sb.append(',');
        }
        // 将最后添加的 ',' 变为 ']':
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    static String array2Json(Object[] array) {
        if (array.length == 0) return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (Object o : array) {
            sb.append(toJson(o));
            sb.append(',');
        }
        // 将最后添加的 ',' 变为 ']':
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    public static String toJson(Object o) {
        if (o == null) return "null";
        if (o instanceof String) return string2Json((String) o);
        if (o instanceof Boolean) return boolean2Json((Boolean) o);
        if (o instanceof Number) return number2Json((Number) o);
        if (o instanceof Map) return map2Json((Map<String, Object>) o);
        if (o instanceof List) return list2Json((List<Object[]>) o);
        if (o instanceof Object[]) return array2Json((Object[]) o);
        throw new RuntimeException("Unsupported type: " + o.getClass().getName());
    }

    /**
     * JSON转成Map对象
     *
     * @param json { "name" : { "first" : "Joe", "last" : "Sixpack" }, "gender" : "MALE", "verified" : false,
     *             "userImage" : "Rm9vYmFyIQ==" }
     * @return Map
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Map<String, Object> json2Map(String json) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSON 转换成List数组
     *
     * @param json [{"ID":"0","COL":{"money":33}},{"ID":"1","COL":{"money":123,"type":"收入"}}]
     * @return List
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static List<Map> json2List(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> getJavaCollection(T clazz, String jsons) {
        List<T> objs = null;
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(jsons);
        if (jsonArray != null) {
            objs = new ArrayList<T>();
            List list = (List) JSONSerializer.toJava(jsonArray);
            for (Object o : list) {
                JSONObject jsonObject = JSONObject.fromObject(o);

                T obj = (T) JSONObject.toBean(jsonObject, clazz.getClass());
                objs.add(obj);
            }
        }
        return objs;
    }

    public static <T> List<T> getJavaCollection(T clazz, String jsons, Map clazzMap) {
        List<T> objs = null;
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(jsons);
        if (jsonArray != null) {
            objs = new ArrayList<T>();
            List list = (List) JSONSerializer.toJava(jsonArray);
            for (Object o : list) {
                JSONObject jsonObject = JSONObject.fromObject(o);

                T obj = (T) JSONObject.toBean(jsonObject, clazz.getClass(), clazzMap);
                objs.add(obj);
            }
        }
        return objs;
    }


    public static <T> T jsonToObj(T clazz, String json) {
        T obj = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            obj = objectMapper.readValue(json, (Class<T>) clazz.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
