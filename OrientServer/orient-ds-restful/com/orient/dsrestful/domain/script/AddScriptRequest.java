package com.orient.dsrestful.domain.script;

import java.io.Serializable;

/**
 * Created by GNY on 2018/3/27
 */
public class AddScriptRequest implements Serializable {

    private String name;
    private String content;
    private String returnType;

    public AddScriptRequest() {
    }

    public AddScriptRequest(String name, String content, String returnType) {
        this.name = name;
        this.content = content;
        this.returnType = returnType;
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

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

}
