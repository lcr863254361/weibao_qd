package com.orient.sysmodel.domain.form;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by enjoy on 2016/3/14 0014.
 */
@XStreamAlias("modelGrid")
@Entity
@Table(name = "MODEL_GRID_VIEW")
public class ModelGridViewEntity implements Serializable{
    private Long id;
    private String name;
    private String alias;
    private Long style;
    private Long needpage;
    private Long pagesize;
    private String displayfield;
    private String addfield;
    private String editfield;
    private String btns;
    private String detailfield;
    private String modelid;
    private Long templateid;
    private Long versionno;
    private Long isdefault;
    private String queryfield;
    private String extendclass;
    private Long isvalid=1l;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_MODEL_GRID_VIEW")})
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
    @Column(name = "ALIAS", nullable = true, insertable = true, updatable = true, length = 100)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "STYLE", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getStyle() {
        return style;
    }

    public void setStyle(Long style) {
        this.style = style;
    }

    @Basic
    @Column(name = "NEEDPAGE", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getNeedpage() {
        return needpage;
    }

    public void setNeedpage(Long needpage) {
        this.needpage = needpage;
    }

    @Basic
    @Column(name = "PAGESIZE", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getPagesize() {
        return pagesize;
    }

    public void setPagesize(Long pagesize) {
        this.pagesize = pagesize;
    }

    @Basic
    @Column(name = "DISPLAYFIELD", nullable = true, insertable = true, updatable = true)
    public String getDisplayfield() {
        return displayfield;
    }

    public void setDisplayfield(String displayfield) {
        this.displayfield = displayfield;
    }

    @Basic
    @Column(name = "ADDFIELD", nullable = true, insertable = true, updatable = true)
    public String getAddfield() {
        return addfield;
    }

    public void setAddfield(String conditionfield) {
        this.addfield = conditionfield;
    }

    @Basic
    @Column(name = "EDITFIELD", nullable = true, insertable = true, updatable = true)
    public String getEditfield() {
        return editfield;
    }

    public void setEditfield(String sortfield) {
        this.editfield = sortfield;
    }

    @Basic
    @Column(name = "QUERYFIELD", nullable = true, insertable = true, updatable = true)
    public String getQueryfield() {
        return queryfield;
    }

    public void setQueryfield(String queryfield) {
        this.queryfield = queryfield;
    }

    @Basic
    @Column(name = "BTNS", nullable = true, insertable = true, updatable = true)
    public String getBtns() {
        return btns;
    }

    public void setBtns(String btns) {
        this.btns = btns;
    }

    @Basic
    @Column(name = "DETAILFIELD", nullable = true, insertable = true, updatable = true)
    public String getDetailfield() {
        return detailfield;
    }

    public void setDetailfield(String filterfield) {
        this.detailfield = filterfield;
    }

    @Basic
    @Column(name = "MODELID", nullable = true, insertable = true, updatable = true)
    public String getModelid() {
        return modelid;
    }

    public void setModelid(String modelid) {
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
    @Column(name = "EXTENDCLASS", nullable = true, insertable = true, updatable = true, length = 100)
    public String getExtendclass() {
        return extendclass;
    }

    public void setExtendclass(String extendclass) {
        this.extendclass = extendclass;
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

        ModelGridViewEntity that = (ModelGridViewEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (style != null ? !style.equals(that.style) : that.style != null) return false;
        if (needpage != null ? !needpage.equals(that.needpage) : that.needpage != null) return false;
        if (pagesize != null ? !pagesize.equals(that.pagesize) : that.pagesize != null) return false;
        if (displayfield != null ? !displayfield.equals(that.displayfield) : that.displayfield != null) return false;
        if (addfield != null ? !addfield.equals(that.addfield) : that.addfield != null)
            return false;
        if (editfield != null ? !editfield.equals(that.editfield) : that.editfield != null) return false;
        if (btns != null ? !btns.equals(that.btns) : that.btns != null) return false;
        if (detailfield != null ? !detailfield.equals(that.detailfield) : that.detailfield != null) return false;
        if (modelid != null ? !modelid.equals(that.modelid) : that.modelid != null) return false;
        if (templateid != null ? !templateid.equals(that.templateid) : that.templateid != null) return false;
        if (versionno != null ? !versionno.equals(that.versionno) : that.versionno != null) return false;
        if (isdefault != null ? !isdefault.equals(that.isdefault) : that.isdefault != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (needpage != null ? needpage.hashCode() : 0);
        result = 31 * result + (pagesize != null ? pagesize.hashCode() : 0);
        result = 31 * result + (displayfield != null ? displayfield.hashCode() : 0);
        result = 31 * result + (addfield != null ? addfield.hashCode() : 0);
        result = 31 * result + (editfield != null ? editfield.hashCode() : 0);
        result = 31 * result + (btns != null ? btns.hashCode() : 0);
        result = 31 * result + (detailfield != null ? detailfield.hashCode() : 0);
        result = 31 * result + (modelid != null ? modelid.hashCode() : 0);
        result = 31 * result + (templateid != null ? templateid.hashCode() : 0);
        result = 31 * result + (versionno != null ? versionno.hashCode() : 0);
        result = 31 * result + (isdefault != null ? isdefault.hashCode() : 0);
        return result;
    }
}

