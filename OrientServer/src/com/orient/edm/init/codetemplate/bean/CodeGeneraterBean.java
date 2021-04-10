package com.orient.edm.init.codetemplate.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-25 15:18
 */
public class CodeGeneraterBean implements Serializable{

    private String name;

    private String packagePath;

    private String hibernateBeanName;

    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getHibernateBeanName() {
        return hibernateBeanName;
    }

    public void setHibernateBeanName(String hibernateBeanName) {
        this.hibernateBeanName = hibernateBeanName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
