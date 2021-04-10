package com.orient.component.runenv.model;

public class ComponentParam {

    /**
     * 从委托发起的主数据的dataId
     */
    public static String MAIN_DATA_ID = "mainDataId";

    /**
     * 从委托发起的主数据的模型名称
     */
    public static String MAIN_MODEL_NAME = "mainModelName";

    private String key;
    private String value;

    public ComponentParam() {

    }

    public ComponentParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
