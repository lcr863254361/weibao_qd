package com.orient.background.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by enjoy on 2016/3/23 0023.
 */
public class ModelViewFormHelper {

    /**
     * 根据前台设计的html 生成表单模板
     *
     * @param html
     * @param modelId
     * @return
     */
    public static String getFreeMarkerTemplate(String html, Long modelId) {
        //获取模型
        //IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId.toString(), EnumInter.BusinessModelEnum.Table);
        Document doc = Jsoup.parseBodyFragment(html);
        //匹配模型字段
        Elements list = doc.select("input[name^=m:],textarea[name^=m:],select[name^=m:],a.link");
        list.forEach(element -> {
            String name = element.attr("name");
            String fieldName = name.replaceAll("^.*:", "").toLowerCase();
            parseMainField(element, fieldName);
        });
        String retVal = doc.body().html();
        //替换特殊字符
        retVal = retVal.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "'");
        retVal = "<#setting number_format=\"#\">\n" + retVal;
        return retVal;
    }

    /**
     * 主表控件赋值
     *
     * @param element
     * @param fieldName
     */
    public static String parseMainField(Element element, String fieldName) {
        String controltype = element.attr("controltype");
        //控件类型
        String type = element.attr("type").toLowerCase();
        if ("attachment".equalsIgnoreCase(controltype)) {
            Element parent = getContainer(element, "div_attachment_container");
            parent.attr("right", "${service.getFieldRight('" + fieldName + "',  permission)}");
            element.val("${service.getFieldValue('" + fieldName + "',model)}");
        }else if("tablenum".equalsIgnoreCase(controltype)){
            Element parent = getContainer(element, "div_tablenum_container");
            parent.attr("right", "${service.getFieldRight('" + fieldName + "',  permission)}");
            element.val("${service.getFieldValue('" + fieldName + "_display',model)}");
        }else if("relation".equalsIgnoreCase(controltype)){
            Element parent = getContainer(element, "div_relation_container");
            parent.attr("right", "${service.getFieldRight('" + fieldName + "',  permission)}");
            element.val("${service.getFieldValue('" + fieldName + "_display',model)}");
        }else if("checktable".equalsIgnoreCase(controltype)){
            element.val("${service.getFieldValue('" + fieldName + "',model)}");
        }else if("dynamicTable".equalsIgnoreCase(controltype)){
            element.val("${service.getFieldValue('" + fieldName + "',model)}");
        }
        //多选或者单选框
        else if ("checkbox".equalsIgnoreCase(type) || "radio".equalsIgnoreCase(type)) {
            String value = element.attr("value");
            element.attr("chk", "1").attr("disabled", "disabled");
            Element elParent = element.parent();
            String parentNodeName = elParent.nodeName();
            if (!parentNodeName.equals("label")) {
                return fieldName + "的html代码必须为<label><input type='checkbox|radio' value='是'/>是</label>的形式";
            }
            //将html赋值给一个变量，在使用service.getRdoChkBox 方法做解析
            //如果外层元素是label，就把<#assign fieldName><label><input type='checkbox' value='' /></label></#assign>当成一个整体进行处理。
            String tmp = parentNodeName.equals("label") ? elParent.toString() : element.toString();
            String str = "<span>&lt;#assign " + fieldName + "Html&gt;" + tmp + " &lt;/#assign&gt;" +
                    "\r\n${service.getRdoChkBox('" + fieldName + "', " + fieldName + "Html,'" + value + "', model, permission)}</span>";
            elParent.before(str);
            elParent.remove();
        } else if (element.nodeName().equalsIgnoreCase("textarea")) {
            //多行文本
            element.append("#value");
            String str = "<span>&lt;#assign " + fieldName + "Html&gt;" + element.toString() + " &lt;/#assign&gt;" +
                    "\r\n${service.getField('" + fieldName + "'," + fieldName + "Html, model, permission)}</span>";

            element.before(str);
            element.remove();
        } else if (element.nodeName().equalsIgnoreCase("input")) {
            //处理文本输入框
            element.attr("value", "#value");
            String ctlType = element.attr("ctlType").toLowerCase();
            if ("selector".equalsIgnoreCase(ctlType)) {
                element.attr("initvalue", "${service.getFieldValue('" + fieldName + "id', model)}");
            }
            String str = "&lt;#assign " + fieldName + "Html&gt;" + element.toString() + " &lt;/#assign&gt;" +
                    "\r\n${service.getField('" + fieldName + "'," + fieldName + "Html, model, permission)}";
            //隐藏的文本框在只读权限下面不返回value
            if ("hidden".equalsIgnoreCase(type)) {
                str = "&lt;#assign " + fieldName + "Html&gt;" + element.toString() + " &lt;/#assign&gt;" +
                        "\r\n${service.getHiddenField('" + fieldName + "'," + fieldName + "Html, model, permission)}";
            }
            element.before(str);
            element.remove();
        } else if (element.nodeName().equalsIgnoreCase("select")) {
            //下拉框
            element.attr("val", "#value");
            String str = "&lt;#assign " + fieldName + "Html&gt;" + element.toString() + " &lt;/#assign&gt;" +
                    "\r\n${service.getField('" + fieldName + "'," + fieldName + "Html, model, permission)}";
            element.before(str);
            element.remove();
        } else if (element.nodeName().equalsIgnoreCase("a")) {
            //处理选择器的a标签
            String str = "&lt;#assign " + fieldName + "Html&gt;" + element.toString() + " &lt;/#assign&gt;" +
                    "\r\n${service.getLink('" + fieldName + "'," + fieldName + "Html, model, permission)}";
            element.before(str);
            element.remove();
        }
        return "";
    }

    /**
     * 根据指定的名称，查找某个节点的父节点。
     *
     * @param node
     * @param containerName
     * @return
     */
    private static Element getContainer(Element node, String containerName) {
        Element parent = node;
        while ((parent = (Element) parent.parent()) != null) {
            String name = parent.attr("name");
            if (containerName.equals(name)) {
                return parent;
            }
        }
        return node;
    }
}
