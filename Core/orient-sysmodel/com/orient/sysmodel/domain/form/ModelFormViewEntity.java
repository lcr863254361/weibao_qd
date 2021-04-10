package com.orient.sysmodel.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by enjoy on 2016/3/14 0014.
 */
@Entity
@Table(name = "MODEL_FORM_VIEW")
public class ModelFormViewEntity {
    private Long id;
    private String name;
    private String desc;
    private Long categoryid;
    private String title;
    private String html;
    private String template;
    private Long versionno;
    private Long isdefault;
    private Long ispublished;
    private String publishedby;
    private Date publishtime;
    private String createby;
    private Date createtime;
    private Long designtype;
    private Long modelid;
    private Long templateid;
    private Long isvalid = 1l;


    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_MODEL_FORM_VIEW")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = true, insertable = true, updatable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "DESCR", nullable = true, insertable = true, updatable = true, length = 100)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "CATEGORYID", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    @Basic
    @Column(name = "TITLE", nullable = true, insertable = true, updatable = true, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "HTML", nullable = true, insertable = true, updatable = true)
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Basic
    @Column(name = "TEMPLATE", nullable = true, insertable = true, updatable = true)
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Basic
    @Column(name = "VERSIONNO", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getVersionno() {
        return versionno;
    }

    public void setVersionno(Long versionno) {
        this.versionno = versionno;
    }

    @Basic
    @Column(name = "ISDEFAULT", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Long isdefault) {
        this.isdefault = isdefault;
    }

    @Basic
    @Column(name = "ISPUBLISHED", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getIspublished() {
        return ispublished;
    }

    public void setIspublished(Long ispublished) {
        this.ispublished = ispublished;
    }

    @Basic
    @Column(name = "PUBLISHEDBY", nullable = true, insertable = true, updatable = true, length = 100)
    public String getPublishedby() {
        return publishedby;
    }

    public void setPublishedby(String publishedby) {
        this.publishedby = publishedby;
    }

    @Basic
    @Column(name = "PUBLISHTIME", nullable = true, insertable = true, updatable = true)
    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    @Basic
    @Column(name = "CREATEBY", nullable = true, insertable = true, updatable = true, length = 100)
    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Basic
    @Column(name = "CREATETIME", nullable = true, insertable = true, updatable = true)
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "DESIGNTYPE", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getDesigntype() {
        return designtype;
    }

    public void setDesigntype(Long designtype) {
        this.designtype = designtype;
    }

    @Basic
    @Column(name = "MODELID", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getModelid() {
        return modelid;
    }

    public void setModelid(Long modelid) {
        this.modelid = modelid;
    }

    @Basic
    @Column(name = "TEMPLATEID", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Long templateid) {
        this.templateid = templateid;
    }

    @Basic
    @Column(name = "ISVALID", nullable = true, insertable = true, updatable = true, length = 100)
    public Long getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Long isvalid) {
        this.isvalid = isvalid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelFormViewEntity that = (ModelFormViewEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (categoryid != null ? !categoryid.equals(that.categoryid) : that.categoryid != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (html != null ? !html.equals(that.html) : that.html != null) return false;
        if (template != null ? !template.equals(that.template) : that.template != null) return false;
        if (versionno != null ? !versionno.equals(that.versionno) : that.versionno != null) return false;
        if (isdefault != null ? !isdefault.equals(that.isdefault) : that.isdefault != null) return false;
        if (ispublished != null ? !ispublished.equals(that.ispublished) : that.ispublished != null) return false;
        if (publishedby != null ? !publishedby.equals(that.publishedby) : that.publishedby != null) return false;
        if (publishtime != null ? !publishtime.equals(that.publishtime) : that.publishtime != null) return false;
        if (createby != null ? !createby.equals(that.createby) : that.createby != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (designtype != null ? !designtype.equals(that.designtype) : that.designtype != null) return false;
        if (modelid != null ? !modelid.equals(that.modelid) : that.modelid != null) return false;
        if (templateid != null ? !templateid.equals(that.templateid) : that.templateid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (categoryid != null ? categoryid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (html != null ? html.hashCode() : 0);
        result = 31 * result + (template != null ? template.hashCode() : 0);
        result = 31 * result + (versionno != null ? versionno.hashCode() : 0);
        result = 31 * result + (isdefault != null ? isdefault.hashCode() : 0);
        result = 31 * result + (ispublished != null ? ispublished.hashCode() : 0);
        result = 31 * result + (publishedby != null ? publishedby.hashCode() : 0);
        result = 31 * result + (publishtime != null ? publishtime.hashCode() : 0);
        result = 31 * result + (createby != null ? createby.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (designtype != null ? designtype.hashCode() : 0);
        result = 31 * result + (modelid != null ? modelid.hashCode() : 0);
        result = 31 * result + (templateid != null ? templateid.hashCode() : 0);
        return result;
    }
}

