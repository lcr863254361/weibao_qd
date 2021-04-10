package com.orient.pvm.bean;


import com.orient.web.model.BaseNode;

/**
 * Created by qjs on 2016/12/21.
 */
public class MulCheckModelTreeNode extends BaseNode {
    private String checkmodelid;
    private String templateid;
    private String signroles;
    private String signnames;
    private String html;
    private String remark;
    private String name;

    public String getCheckmodelid() {
        return checkmodelid;
    }

    public void setCheckmodelid(String checkmodelid) {
        this.checkmodelid = checkmodelid;
    }

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }

    public String getSignroles() {
        return signroles;
    }

    public void setSignroles(String signroles) {
        this.signroles = signroles;
    }

    public String getSignnames() {
        return signnames;
    }

    public void setSignnames(String signnames) {
        this.signnames = signnames;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
