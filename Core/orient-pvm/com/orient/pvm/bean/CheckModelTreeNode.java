package com.orient.pvm.bean;


import com.orient.web.model.BaseNode;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class CheckModelTreeNode extends BaseNode {

    private Long realId;
    private Long taskmodelid;
    private Long taskdataid;
    private Long checkmodelid;
    //默认加载所有
    private Integer checktablestatus = 15;      //1 编制中, 2 ,可下发  ,4 已下发 ,8 已上传

    private String signroles;
    private String signnames;
    private String html;
    private String name;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRealId() {
        return realId;
    }

    public void setRealId(Long realId) {
        this.realId = realId;
    }

    public Long getTaskmodelid() {
        return taskmodelid;
    }

    public void setTaskmodelid(Long taskmodelid) {
        this.taskmodelid = taskmodelid;
    }

    public Long getTaskdataid() {
        return taskdataid;
    }

    public void setTaskdataid(Long taskdataid) {
        this.taskdataid = taskdataid;
    }

    public Long getCheckmodelid() {
        return checkmodelid;
    }

    public void setCheckmodelid(Long checkmodelid) {
        this.checkmodelid = checkmodelid;
    }

    public Integer getChecktablestatus() {
        return checktablestatus;
    }

    public void setChecktablestatus(Integer checktablestatus) {
        this.checktablestatus = checktablestatus;
    }
}

