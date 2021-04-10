package com.orient.sysmodel.domain.form;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * @author enjoy
 * @creare 2016-04-08 11:24
 */
@Entity
@Table(name = "MODEL_BTN_INSTANCE")
public class ModelBtnInstanceEntity {
    private Long id;
    private String name;
    private String btnTypeId;
    private String formViewId;
    private String jspath;
    private Long issystem;
    private String code;


    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_MODEL_BTN_INSTANCE")})
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
    @Column(name = "BTN_TYPE_ID", nullable = true, insertable = true, updatable = true, precision = -127)
    public String getBtnTypeId() {
        return btnTypeId;
    }

    public void setBtnTypeId(String btnTypeId) {
        this.btnTypeId = btnTypeId;
    }

    @Basic
    @Column(name = "FORM_VIEW_ID", nullable = true, insertable = true, updatable = true, precision = -127)
    public String getFormViewId() {
        return formViewId;
    }

    public void setFormViewId(String formViewId) {
        this.formViewId = formViewId;
    }

    @Basic
    @Column(name = "JSPATH", nullable = true, insertable = true, updatable = true, length = 100)
    public String getJspath() {
        return jspath;
    }

    public void setJspath(String jspath) {
        this.jspath = jspath;
    }

    @Basic
    @Column(name = "ISSYSTEM", nullable = true, insertable = true, updatable = true, precision = -127)
    public Long getIssystem() {
        return issystem;
    }

    public void setIssystem(Long issystem) {
        this.issystem = issystem;
    }

    @Basic
    @Column(name = "CODE", nullable = true, insertable = true, updatable = true, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelBtnInstanceEntity that = (ModelBtnInstanceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (btnTypeId != null ? !btnTypeId.equals(that.btnTypeId) : that.btnTypeId != null) return false;
        if (formViewId != null ? !formViewId.equals(that.formViewId) : that.formViewId != null) return false;
        if (jspath != null ? !jspath.equals(that.jspath) : that.jspath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (btnTypeId != null ? btnTypeId.hashCode() : 0);
        result = 31 * result + (formViewId != null ? formViewId.hashCode() : 0);
        result = 31 * result + (jspath != null ? jspath.hashCode() : 0);
        return result;
    }
}
