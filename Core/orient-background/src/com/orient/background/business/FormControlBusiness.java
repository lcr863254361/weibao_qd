package com.orient.background.business;

import com.orient.utils.StringUtil;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by enjoy on 2016/3/23 0023.
 * Freemarker 模板中使用的service
 */
@Component
public class FormControlBusiness {
    public static final String NO_PERMISSION = "您没有权限";

    /**
     * 获取字段的权限
     *
     * @param fieldName  字段名称
     * @param permission 权限对象
     * @return
     */
    public String getFieldRight(String fieldName,
                                Map<String, Map<String, String>> permission) {
        fieldName = fieldName.toLowerCase();
//        // 可写权限
//        if (permission.get("field").get(fieldName) == null
//                || "w".equals(permission.get("field").get(fieldName))) {
//            return "w";
//        } else if ("b".equals(permission.get("field").get(fieldName))) {
//            return "b";
//        } else if ("r".equals(permission.get("field").get(fieldName))) {
//            return "r";
//        } else {
//            return "no";
//        }
        return "w";
    }

    /**
     * 显示主表字段值，用于模版中使用。
     *
     * @param fieldName  字段名称
     * @param html       字段html
     * @param model      数据对象
     * @param permission 权限对象
     * @return
     */
    public String getField(String fieldName, String html,
                           Map<String, Map<String, Map>> model,
                           Map<String, Map<String, String>> permission) {
        fieldName = fieldName.toLowerCase();
        // 取得值
        Object object = model.get("main").get(fieldName);
        String value = "";
        if (object != null) {
            value = object.toString();
        }
        // 可写权限
        if (permission.get("field") == null
                || permission.get("field").get(fieldName) == null
                || "w".equals(permission.get("field").get(fieldName))) {
            return StringUtil.replaceAll(html, "#value", value);
        } else {
            return NO_PERMISSION;
        }
    }

    /**
     * 将#value替换为字段的值。
     *
     * @param fieldName 字段名称
     * @param model     数据对象
     * @return
     */
    public String getFieldValue(String fieldName,
                                Map<String, Map<String, Map>> model) {
        fieldName = fieldName.toLowerCase();
        // 取得值
        Object object = model.get("main").get(fieldName);
        String value = "";
        if (object != null) {
            value = object.toString();
        }
        return value;
    }

    /**
     * 显示选择器的选择按钮
     *
     * @param fieldName  字段名称
     * @param html       字段html
     * @param model      数据对象
     * @param permission 权限对象
     * @return
     */
    public String getLink(String fieldName, String html,
                          Map<String, Map<String, Map>> model,
                          Map<String, Map<String, String>> permission) {
        Document doc = Jsoup.parse(html);
        Elements elList = doc.select("a");
        if (elList.size() > 0) {              //有超链接的
            for (Element el : elList) {
                String name = el.attr("name");
                if (StringUtil.isNotEmpty(name)) {
                    name = name.replaceAll(" ", "").toLowerCase().substring(0, 2);  //去掉空格 、改变为小写、并截取前两个字符
                    if (name.contains("s:")) {            //并是子表的超链接 直接返回 由页面JS解析 子表字段名称是s:开头的
                        return html;                // 直接返回
                    }
                }
            }
        }
        fieldName = fieldName.toLowerCase();
        // 可写权限
        if (permission.get("field") == null
                || permission.get("field").get(fieldName) == null
                || "w".equals(permission.get("field").get(fieldName))
                || "b".equals(permission.get("field").get(fieldName))) {
            return html;
        } else {
            return NO_PERMISSION;
        }
    }

    /**
     * 返回rdo和checkbox控件
     *
     * @param fieldName
     * @param html
     * @param ctlVal
     * @param model
     * @param permission
     * @return
     */
    public String getRdoChkBox(String fieldName, String html, String ctlVal,
                               Map<String, Map<String, Map>> model,
                               Map<String, Map<String, String>> permission) {
        Object object = model.get("main").get(fieldName.toLowerCase());
        String value = "";
        if (object != null) {
            value = object.toString();
        }
        if (permission.get("field").get(fieldName) == null
                || "w".equals(permission.get("field").get(fieldName))) {
            html = html.replaceAll("(?s)disabled=\\s*\"?\\s*disabled\\s*\"?",
                    "");
            html = getHtml(html, ctlVal, value);
            return html;
        } else if ("b".equals(permission.get("field").get(fieldName))) {
            html = html.replaceAll("(?s)disabled=\\s*\"?\\s*disabled\\s*\"?", "");
            html = getHtml(html, ctlVal, value);
            Document doc = Jsoup.parse(html);
            Elements elList = doc.select("input,select,textarea");
            for (Element e : elList) {
                toRequired(e.outerHtml());
                e.after(toRequired(e.outerHtml()));
                e.remove();
            }
            return doc.getElementsByTag("body").html();
        } else if ("r".equals(permission.get("field").get(fieldName))) {
            html = getHtml(html, ctlVal, value);
            return html;
        } else {
            return NO_PERMISSION;
        }
    }

    /**
     * 转换必填的Html
     *
     * @param html
     * @return
     */
    public static String toRequired(String html) {
        StringBuffer sb = new StringBuffer();
        try {
            Document doc = Jsoup.parse(html);
            Elements elList = doc.select("input,select,textarea");
            for (Iterator<Element> it = elList.iterator(); it.hasNext(); ) {
                Element el = it.next();
                String validate = el.attr("validate");
                String validateValue = "{required:true}";
                if (StringUtil.isNotEmpty(validate)) {
                    JSONObject json = JSONObject.fromObject(validate);
                    json.element("required", "true");
                    validateValue = json.toString().replace("\"", "")
                            .replace("\'", "");
                }
                el.attr("validate", validateValue);
                el.attr("right", "b");
                sb.append(el);
            }
        } catch (Exception e) {
            return html;
        }
        return sb.toString();
    }


    /**
     * 替换html。
     *
     * @param html   html的代码
     * @param ctlVal 控件代表的值。
     * @param value  当前字段的值。
     * @return
     */
    private String getHtml(String html, String ctlVal, String value) {
        // 还没有选择任何的字段
        if (StringUtil.isEmpty(value)) {
            html = html.replaceAll("chk=\"?1\"?", "");
        } else {
            html = html.replace("checked=\"checked\"", "");
            if (value.contains(ctlVal)) {
                html = html.replaceAll("chk=\"?1\"?", "checked=\"checked\"");
            } else {
                html = html.replaceAll("chk=\"?1\"?", "");
            }
        }
        return html;
    }
}
