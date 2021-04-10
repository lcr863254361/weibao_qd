package com.orient.sysmodel.domain.quantity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-11 10:07
 */
@Entity
@Table(name = "CWM_SYS_NUMBERUNIT")
public class CwmSysNumberunitDO {
    private Long id;
    private String name;
    private String showName;
    private String unit;
    private String isBase;
    private String formulaIn;
    private String formulaOut;
    private Long position;

    private List<CfQuantityDO> quantityDOList;
    private List<CfQuantityInstanceDO> quantityInstanceDOS;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_SYS_NUMBERUNIT")})
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
    @Column(name = "SHOW_NAME")
    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @Basic
    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "IS_BASE")
    public String getIsBase() {
        return isBase;
    }

    public void setIsBase(String isBase) {
        this.isBase = isBase;
    }

    @Basic
    @Column(name = "FORMULA_IN")
    public String getFormulaIn() {
        return formulaIn;
    }

    public void setFormulaIn(String formulaIn) {
        this.formulaIn = formulaIn;
    }

    @Basic
    @Column(name = "FORMULA_OUT")
    public String getFormulaOut() {
        return formulaOut;
    }

    public void setFormulaOut(String formulaOut) {
        this.formulaOut = formulaOut;
    }

    @Basic
    @Column(name = "POSITION")
    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "numberunitDO")
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CfQuantityDO> getQuantityDOList() {
        return quantityDOList;
    }

    public void setQuantityDOList(List<CfQuantityDO> quantityDOList) {
        this.quantityDOList = quantityDOList;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "numberunitDO")
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CfQuantityInstanceDO> getQuantityInstanceDOS() {
        return quantityInstanceDOS;
    }

    public void setQuantityInstanceDOS(List<CfQuantityInstanceDO> quantityInstanceDOS) {
        this.quantityInstanceDOS = quantityInstanceDOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmSysNumberunitDO that = (CwmSysNumberunitDO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (showName != null ? !showName.equals(that.showName) : that.showName != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (isBase != null ? !isBase.equals(that.isBase) : that.isBase != null) return false;
        if (formulaIn != null ? !formulaIn.equals(that.formulaIn) : that.formulaIn != null) return false;
        if (formulaOut != null ? !formulaOut.equals(that.formulaOut) : that.formulaOut != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (showName != null ? showName.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (isBase != null ? isBase.hashCode() : 0);
        result = 31 * result + (formulaIn != null ? formulaIn.hashCode() : 0);
        result = 31 * result + (formulaOut != null ? formulaOut.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
