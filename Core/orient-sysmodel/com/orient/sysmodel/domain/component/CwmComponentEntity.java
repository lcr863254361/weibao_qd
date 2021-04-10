package com.orient.sysmodel.domain.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2016/8/28 0028.
 */
@Entity
@Table(name = "CWM_COMPONENT")
public class CwmComponentEntity {

    private Long id;
    private String componentname;
    private String classname;
    private String remark;

    private List<CwmComponentModelEntity> cwmComponentModelEntityList;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_COMPONENT")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "COMPONENTNAME")
    public String getComponentname() {
        return componentname;
    }

    public void setComponentname(String componentname) {
        this.componentname = componentname;
    }

    @Basic
    @Column(name = "CLASSNAME")
    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "belongComponent", cascade = {CascadeType.REMOVE})
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) desc")
    public List<CwmComponentModelEntity> getCwmComponentModelEntityList() {
        return cwmComponentModelEntityList;
    }

    public void setCwmComponentModelEntityList(List<CwmComponentModelEntity> cwmComponentModelEntityList) {
        this.cwmComponentModelEntityList = cwmComponentModelEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmComponentEntity that = (CwmComponentEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (componentname != null ? !componentname.equals(that.componentname) : that.componentname != null)
            return false;
        if (classname != null ? !classname.equals(that.classname) : that.classname != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (componentname != null ? componentname.hashCode() : 0);
        result = 31 * result + (classname != null ? classname.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}

