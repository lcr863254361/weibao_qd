package com.orient.sysmodel.domain.pvm;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "CWM_TASKCHECKMODEL")
public class TaskCheckModel implements Serializable {

    private Long id;
    private String nodeId;
    private Long checkmodelid;
    private Integer checktablestatus;      //1 编制中, 2 ,可下发  ,4 已下发 ,8 已上传
    private String signroles;
    private String signnames;
    private String uploaduser;
    private Date uploadtime;
    private String html;
    private String name;
    private String remark;
    private String signfileids;

    public static final String TASK_MODEL_ID = "taskmodelid";
    public static final String TASK_DATA_ID = "taskdataid";

    public static int STATUS_EDIT = 1;
    public static int STATUS_REDADY = 2;
    public static int STATUS_DELIVED = 4;
    public static int STATUS_UPLOADED = 8;

    public static final Map<Integer, String> statusMap = new HashMap<Integer, String>() {{
        put(TaskCheckModel.STATUS_EDIT, "编制中");
        put(TaskCheckModel.STATUS_REDADY, "可下发");
        put(TaskCheckModel.STATUS_DELIVED, "已下发");
        put(TaskCheckModel.STATUS_UPLOADED, "已上传");
    }};


    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_TASKCHECKMODEL")})

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NODE_ID")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Basic
    @Column(name = "CHECKMODELID", nullable = true, length = 200)
    public Long getCheckmodelid() {
        return checkmodelid;
    }

    public void setCheckmodelid(Long checkmodelid) {
        this.checkmodelid = checkmodelid;
    }

    @Basic
    @Column(name = "CHECKTABLESTATUS", nullable = false, precision = 0)
    public Integer getChecktablestatus() {
        return checktablestatus;
    }

    public void setChecktablestatus(Integer checktablestatus) {
        this.checktablestatus = checktablestatus;
    }

    @Basic
    @Column(name = "SIGNROLES", nullable = true, length = 4000)
    public String getSignroles() {
        return signroles;
    }

    public void setSignroles(String signroles) {
        this.signroles = signroles;
    }

    @Basic
    @Column(name = "SIGNNAMES", nullable = true, length = 200)
    public String getSignnames() {
        return signnames;
    }

    public void setSignnames(String signnames) {
        this.signnames = signnames;
    }

    @Basic
    @Column(name = "UPLOADUSERS", nullable = true, length = 200)
    public String getUploaduser() {
        return uploaduser;
    }

    public void setUploaduser(String uploaduser) {
        this.uploaduser = uploaduser;
    }

    @Basic
    @Column(name = "UPLOADTIME", nullable = true)
    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }


    @Basic
    @Column(name = "NAME", nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "REMARK", nullable = true)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "SIGNFILEIDS", nullable = true)
    public String getSignfileids() {
        return signfileids;
    }

    public void setSignfileids(String signfileids) {
        this.signfileids = signfileids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskCheckModel that = (TaskCheckModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;
        if (checkmodelid != null ? !checkmodelid.equals(that.checkmodelid) : that.checkmodelid != null) return false;
        if (checktablestatus != null ? !checktablestatus.equals(that.checktablestatus) : that.checktablestatus != null)
            return false;
        if (signroles != null ? !signroles.equals(that.signroles) : that.signroles != null) return false;
        if (signnames != null ? !signnames.equals(that.signnames) : that.signnames != null) return false;
        if (uploaduser != null ? !uploaduser.equals(that.uploaduser) : that.uploaduser != null) return false;
        if (uploadtime != null ? !uploadtime.equals(that.uploadtime) : that.uploadtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nodeId != null ? nodeId.hashCode() : 0);
        result = 31 * result + (checkmodelid != null ? checkmodelid.hashCode() : 0);
        result = 31 * result + (checktablestatus != null ? checktablestatus.hashCode() : 0);
        result = 31 * result + (signroles != null ? signroles.hashCode() : 0);
        result = 31 * result + (signnames != null ? signnames.hashCode() : 0);
        result = 31 * result + (uploaduser != null ? uploaduser.hashCode() : 0);
        result = 31 * result + (uploadtime != null ? uploadtime.hashCode() : 0);
        return result;
    }

    private static final long serialVersionUID = 1L;

    @Lob
    @Column(name = "HTML")
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }


}
