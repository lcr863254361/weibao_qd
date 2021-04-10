package com.orient.sysmodel.domain.form;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by enjoy on 2016/3/14 0014.
 */
@Entity
@Table(name = "MODEL_QUERY")
public class ModelQueryEntity {
    private Long id;
    private String name;
    private String code;
    private BigDecimal modelid;
    private String modelname;
    private BigDecimal needpage;
    private String conditionfield;
    private String resultfield;
    private String sortfield;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence",strategy = "sequence",parameters = {@Parameter(name="sequence",value="SEQ_MODEL_QUERY")})
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
    @Column(name = "CODE", nullable = true, insertable = true, updatable = true, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "MODELID", nullable = true, insertable = true, updatable = true, precision = -127)
    public BigDecimal getModelid() {
        return modelid;
    }

    public void setModelid(BigDecimal modelid) {
        this.modelid = modelid;
    }

    @Basic
    @Column(name = "MODELNAME", nullable = true, insertable = true, updatable = true, length = 100)
    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    @Basic
    @Column(name = "NEEDPAGE", nullable = true, insertable = true, updatable = true, precision = -127)
    public BigDecimal getNeedpage() {
        return needpage;
    }

    public void setNeedpage(BigDecimal needpage) {
        this.needpage = needpage;
    }

    @Basic
    @Column(name = "CONDITIONFIELD", nullable = true, insertable = true, updatable = true)
    public String getConditionfield() {
        return conditionfield;
    }

    public void setConditionfield(String conditionfield) {
        this.conditionfield = conditionfield;
    }

    @Basic
    @Column(name = "RESULTFIELD", nullable = true, insertable = true, updatable = true)
    public String getResultfield() {
        return resultfield;
    }

    public void setResultfield(String resultfield) {
        this.resultfield = resultfield;
    }

    @Basic
    @Column(name = "SORTFIELD", nullable = true, insertable = true, updatable = true)
    public String getSortfield() {
        return sortfield;
    }

    public void setSortfield(String sortfield) {
        this.sortfield = sortfield;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelQueryEntity that = (ModelQueryEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (modelid != null ? !modelid.equals(that.modelid) : that.modelid != null) return false;
        if (modelname != null ? !modelname.equals(that.modelname) : that.modelname != null) return false;
        if (needpage != null ? !needpage.equals(that.needpage) : that.needpage != null) return false;
        if (conditionfield != null ? !conditionfield.equals(that.conditionfield) : that.conditionfield != null)
            return false;
        if (resultfield != null ? !resultfield.equals(that.resultfield) : that.resultfield != null) return false;
        if (sortfield != null ? !sortfield.equals(that.sortfield) : that.sortfield != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (modelid != null ? modelid.hashCode() : 0);
        result = 31 * result + (modelname != null ? modelname.hashCode() : 0);
        result = 31 * result + (needpage != null ? needpage.hashCode() : 0);
        result = 31 * result + (conditionfield != null ? conditionfield.hashCode() : 0);
        result = 31 * result + (resultfield != null ? resultfield.hashCode() : 0);
        result = 31 * result + (sortfield != null ? sortfield.hashCode() : 0);
        return result;
    }
}
