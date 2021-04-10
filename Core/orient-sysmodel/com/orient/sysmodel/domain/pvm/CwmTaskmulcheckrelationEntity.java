package com.orient.sysmodel.domain.pvm;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/22.
 */
@Entity
@Table(name = "CWM_TASKMULCHECKRELATION")
public class CwmTaskmulcheckrelationEntity {
    private String id;
    private String templateid;
    private String checkmodelid;
    private String modeldata;
    private String html;
    private String signroles;
    private String signnames;
    private String uploadusers;
    private Timestamp uploadtime;
    private String remark;
    private String name;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_TASKMULCHECKRELATION")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TEMPLATEID")
    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }

    @Basic
    @Column(name = "CHECKMODELID")
    public String getCheckmodelid() {
        return checkmodelid;
    }

    public void setCheckmodelid(String checkmodelid) {
        this.checkmodelid = checkmodelid;
    }

    @Basic
    @Column(name = "MODELDATA")
    public String getModeldata() {
        return modeldata;
    }

    public void setModeldata(String modeldata) {
        this.modeldata = modeldata;
    }

    @Basic
    @Column(name = "HTML")
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Basic
    @Column(name = "SIGNROLES")
    public String getSignroles() {
        return signroles;
    }

    public void setSignroles(String signroles) {
        this.signroles = signroles;
    }

    @Basic
    @Column(name = "SIGNNAMES")
    public String getSignnames() {
        return signnames;
    }

    public void setSignnames(String signnames) {
        this.signnames = signnames;
    }

    @Basic
    @Column(name = "UPLOADUSERS")
    public String getUploadusers() {
        return uploadusers;
    }

    public void setUploadusers(String uploadusers) {
        this.uploadusers = uploadusers;
    }

    @Basic
    @Column(name = "UPLOADTIME")
    public Timestamp getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Timestamp uploadtime) {
        this.uploadtime = uploadtime;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmTaskmulcheckrelationEntity that = (CwmTaskmulcheckrelationEntity) o;

        if (checkmodelid != null ? !checkmodelid.equals(that.checkmodelid) : that.checkmodelid != null) return false;
        if (html != null ? !html.equals(that.html) : that.html != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modeldata != null ? !modeldata.equals(that.modeldata) : that.modeldata != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (signnames != null ? !signnames.equals(that.signnames) : that.signnames != null) return false;
        if (signroles != null ? !signroles.equals(that.signroles) : that.signroles != null) return false;
        if (templateid != null ? !templateid.equals(that.templateid) : that.templateid != null) return false;
        if (uploadtime != null ? !uploadtime.equals(that.uploadtime) : that.uploadtime != null) return false;
        if (uploadusers != null ? !uploadusers.equals(that.uploadusers) : that.uploadusers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (templateid != null ? templateid.hashCode() : 0);
        result = 31 * result + (checkmodelid != null ? checkmodelid.hashCode() : 0);
        result = 31 * result + (modeldata != null ? modeldata.hashCode() : 0);
        result = 31 * result + (html != null ? html.hashCode() : 0);
        result = 31 * result + (signroles != null ? signroles.hashCode() : 0);
        result = 31 * result + (signnames != null ? signnames.hashCode() : 0);
        result = 31 * result + (uploadusers != null ? uploadusers.hashCode() : 0);
        result = 31 * result + (uploadtime != null ? uploadtime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
