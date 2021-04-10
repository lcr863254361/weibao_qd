package com.orient.sysmodel.domain.pvm;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/20.
 */
@Entity
@Table(name = "CWM_TASKMULTIPLECHECKMODEL")
public class CwmTaskmultiplecheckmodelEntity implements Serializable {
    private String id;
    private String name;
    private String remark;

    private static final long serialVersionUID =  1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_TASKMULTIPLECHECKMODEL")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String mark) {
        this.remark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmTaskmultiplecheckmodelEntity that = (CwmTaskmultiplecheckmodelEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
