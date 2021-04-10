package com.orient.dsrestful.domain.script;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-26 20:49
 */
public class ScriptBean implements Serializable {

    private long id;
    private String name;//脚本名称
    private String content;//脚本内容
    private boolean enable;//脚本是否有效: 1或空为有效
    private String returnType;
    private boolean used;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

}
