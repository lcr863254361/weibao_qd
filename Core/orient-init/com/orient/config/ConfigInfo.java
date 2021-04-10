package com.orient.config;

import com.orient.utils.CommonTools;
import com.orient.utils.PathTools;
import com.orient.utils.PropertiesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigInfo {

    public static String COLLAB_SCHEMA_ID;
    public static String TDM_SCHEMA_ID;

    private static String CONTEXTNAME = null;
    public static String DEVICE_SCHEMA_ID;

    //知识管理：全局变量
    public static String KNOWLEDGE_SCHEMA_ID;

    public static String ROOT_DEPARTMENT_ID;

    protected static final String CONFIG_PROPERTIES = "config.properties";

    //数据管理
    public static List<String> VIEW_DIC = new ArrayList<String>() {{
        add("detail");
        add("query");
        add("queryAll");
    }};

    public static Boolean ISDEBUGENABLED = false;

    static {
        String schemaIdConfigName = "collab.schemaId";
        COLLAB_SCHEMA_ID = getPropertyValueConfigured(schemaIdConfigName, CONFIG_PROPERTIES, "");

        String knowledgeSchemaIdConfigName = "knowledge.schemaId";
        KNOWLEDGE_SCHEMA_ID = getPropertyValueConfigured(knowledgeSchemaIdConfigName, CONFIG_PROPERTIES, "");

        String tdmSchemaIdConfigName = "tdm.schemaId";
        TDM_SCHEMA_ID = getPropertyValueConfigured(tdmSchemaIdConfigName, CONFIG_PROPERTIES, "");

        //添加设备schemaId
        String deviceSchemaIdConfigName = "device.schemaId";
        DEVICE_SCHEMA_ID = getPropertyValueConfigured(deviceSchemaIdConfigName, CONFIG_PROPERTIES, "");

        //根部门id
        ROOT_DEPARTMENT_ID = getPropertyValueConfigured("rootDepartmentId", CONFIG_PROPERTIES, "");

        ISDEBUGENABLED = "true".equalsIgnoreCase(getPropertyValueConfigured("debugAbled", CONFIG_PROPERTIES, ""));
    }

    /**
     * 根据配置属性的key获取value
     *
     * @param propertyName
     * @return
     */
    protected static String getPropertyValueConfigured(String propertyName) {
        return getPropertyValueConfigured(propertyName, CONFIG_PROPERTIES, "");
    }

    /**
     * 根据配置属性的key获取value，如果value为空则返回defaultValue
     *
     * @param propertyName
     * @param propertyFileName
     * @param defaultValue
     * @return
     */
    protected static String getPropertyValueConfigured(String propertyName, String propertyFileName, String defaultValue) {
        //the file
        String filePath = PathTools.getRootPath() + File.separator + "WEB-INF" + File.separator + "classes"
                + File.separator + propertyFileName;
        String value = PropertiesUtil.readValue(filePath, propertyName);

        if (CommonTools.isNullString(value)) {
            return defaultValue;
        }
        return value;
    }

    static public String getContextName() {
        if (CONTEXTNAME == null) {
            CONTEXTNAME = PathTools.getRootPath().substring(PathTools.getRootPath().lastIndexOf("/"));
        }
        return CONTEXTNAME;
    }

}
