package com.orient.workflow.bean;

/**
 * @Function Name:  DeployProperty
 * @Description: 流程定义属性
 * @Date Created:  2012-6-6 上午11:43:15
 * @Author: Pan Duan Duan
 * @Last Modified:    ,  Date Modified:
 */
public class DeployProperty implements java.io.Serializable {

    private String objName;

    private String key;

    private String value;

    public DeployProperty() {

    }

    public DeployProperty(String objName, String key, String value) {
        this.objName = objName;
        this.key = key;
        this.value = value;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
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
