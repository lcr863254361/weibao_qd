package com.orient.sysmodel.domain.quantity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-11 9:24
 */
@Entity
@Table(name = "CF_QUANTITY")
public class CfQuantityDO {
    private Long id;
    private String name;
    private String datatype;
    private CwmSysNumberunitDO numberunitDO;
    private List<CfQuantityInstanceDO> instances;
    private List<CfQuantityTemplateRelationDO> templateRelations;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_QUANTITY")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    @Column(name = "DATATYPE")
    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIT_ID")
    public CwmSysNumberunitDO getNumberunitDO() {
        return numberunitDO;
    }

    public void setNumberunitDO(CwmSysNumberunitDO numberunitDO) {
        this.numberunitDO = numberunitDO;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "belongQuantity", cascade = CascadeType.REMOVE)
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CfQuantityInstanceDO> getInstances() {
        return instances;
    }

    public void setInstances(List<CfQuantityInstanceDO> instances) {
        this.instances = instances;
    }
    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "belongQuantity", cascade = CascadeType.REMOVE)
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CfQuantityTemplateRelationDO> getTemplateRelations() {
        return templateRelations;
    }

    public void setTemplateRelations(List<CfQuantityTemplateRelationDO> templateRelations) {
        this.templateRelations = templateRelations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfQuantityDO that = (CfQuantityDO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (datatype != null ? !datatype.equals(that.datatype) : that.datatype != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (datatype != null ? datatype.hashCode() : 0);
        return result;
    }
}
