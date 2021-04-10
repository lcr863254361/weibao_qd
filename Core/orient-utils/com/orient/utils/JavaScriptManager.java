package com.orient.utils;

import org.jbpm.api.JbpmException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Js表达式的校验类
 *
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */
public class JavaScriptManager {

    public static Object evaluateScript(String script, Map<String, ?> value) {
        return evaluate(script, value);
    }

    public static Object checkScript(String script, Map<String, ?> value) {
        return check(script, value);
    }

    public static Object check(String script, Map<String, ?> value) {
        // 创建脚本引擎管理器
        ScriptEngineManager sem = new ScriptEngineManager();
        // 创建一个处理JavaScript的脚本引擎
        ScriptEngine engine = sem.getEngineByExtension("js");
        try {
            //遍历传过来Map值 并把它传到脚本引擎中
            Set<String> keySet = value.keySet();
            Iterator<String> keys = keySet.iterator();
            while (keys.hasNext()) {
                engine.put(keys.next(), value.get(keys.next()));
            }
            // 执行javaScript表达式
            return engine.eval(script);
        } catch (ScriptException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * evaluates the script with the given language. If script is null, then this
     * method will return null.
     * 校验js的表达式，
     *
     * @throws JbpmException if language is null.
     */
    public static Object evaluate(String script, Map<String, ?> value) {
        if (script != null && script.length() > 0) {
            StringBuffer sb = new StringBuffer();
            if (script != null && !script.equals("") && script.length() > 0) {
                for (String ex : script.split(";;;;;")) {
                    if (ex != null && ex.length() > 0) {
                        String[] str = ex.split(";,;.");
                        //分支连线的名称
                        String name = str[0];
                        //表达式，如${18}>3
                        //${18}表示日期（日期字段的ID为18）
                        String exp = str[1];
                        //将表达式的字段转换成col+字段ID的形式
                        for (Iterator<String> it = value.keySet().iterator(); it.hasNext(); ) {
                            String id = it.next();
                            exp = exp.replaceAll("\\$\\{" + id + "\\}", "col" + id);
                        }
                        //针对还有这样“${数字}”格式的字符串存在时的处理
                        int x = exp.indexOf("${");
                        int y = exp.indexOf("}", x);
                        if (x > 0 && y > 0 && exp.substring(x + 2, y).matches("^-?\\d+$")) {
                            //此处表示当前表达式中有字段在数据类中已被删除，无法转换成正确的字段信息，不加入到最后的sb字符串中
                            continue;
                        } else {
                            sb.append("if(").append(exp).append(") flag='").append(name).append("';");
                        }
                    }
                }
                script = sb.toString();
            }
        }
        if (script == null) {
            return null;
        }
        Object result = null;
        // 创建脚本引擎管理器
        ScriptEngineManager sem = new ScriptEngineManager();
        // 创建一个处理JavaScript的脚本引擎
        ScriptEngine engine = sem.getEngineByExtension("js");
        try {
            //遍历传过来Map值 并把它传到脚本引擎中
            Set<String> keySet = value.keySet();
            Iterator<String> keys = keySet.iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                //将key转换成col+字段ID的形式
                engine.put("col" + key, value.get(key));
            }
            // 执行javaScript表达式
            result = engine.eval(script);
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @SuppressWarnings({"unchecked"})
    public static void main(String[] args) {
        Map value = new HashMap();
        value.put("中午", 10);
        value.put("下午", 10);
        String script = "if(中午<5) flag='取消'; if(中午>=5) flag =  '驳回';";
        Object result = check(script, value);
        System.out.println((result));
    }

}
