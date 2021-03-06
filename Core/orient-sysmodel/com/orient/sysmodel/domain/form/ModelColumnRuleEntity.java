package com.orient.sysmodel.domain.form;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by enjoy on 2016/3/14 0014.
 */
@Entity
@Table(name = "MODEL_COLUMN_RULE")
public class ModelColumnRuleEntity {
    private Long id;
    private String name;
    private String regulation;
    private String desc;
    private String errinfo;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence",strategy = "sequence",parameters = {@Parameter(name="sequence",value="SEQ_MODEL_COLUMN_RULE")})
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
    @Column(name = "REGULATION", nullable = true, insertable = true, updatable = true, length = 400)
    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }

    @Basic
    @Column(name = "DESCR", nullable = true, insertable = true, updatable = true, length = 400)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "ERRINFO", nullable = true, insertable = true, updatable = true, length = 400)
    public String getErrinfo() {
        return errinfo;
    }

    public void setErrinfo(String errinfo) {
        this.errinfo = errinfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelColumnRuleEntity that = (ModelColumnRuleEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (regulation != null ? !regulation.equals(that.regulation) : that.regulation != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (errinfo != null ? !errinfo.equals(that.errinfo) : that.errinfo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (regulation != null ? regulation.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (errinfo != null ? errinfo.hashCode() : 0);
        return result;
    }
}
