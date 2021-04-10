package com.orient.sysmodel.domain.sys;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-04 9:44
 */
@Entity
@Table(name = "CWM_SYS_ACCOUNT_STRATEGY")
public class CwmSysAccountStrategyEntity {
    private Long id;
    private String strategyName;
    private String strategyNote;
    private String strategyValue1;
    private String strategyValue2;
    private String isUse;
    private String type;
    private String strategyValue;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_SYS_ACCOUNT_STRATEGY")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "STRATEGY_NAME", nullable = true, length = 100)
    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    @Basic
    @Column(name = "STRATEGY_NOTE", nullable = true, length = 1000)
    public String getStrategyNote() {
        return strategyNote;
    }

    public void setStrategyNote(String strategyNote) {
        this.strategyNote = strategyNote;
    }

    @Basic
    @Column(name = "STRATEGY_VALUE1", nullable = true, length = 100)
    public String getStrategyValue1() {
        return strategyValue1;
    }

    public void setStrategyValue1(String strategyValue1) {
        this.strategyValue1 = strategyValue1;
    }

    @Basic
    @Column(name = "STRATEGY_VALUE2", nullable = true, length = 100)
    public String getStrategyValue2() {
        return strategyValue2;
    }

    public void setStrategyValue2(String strategyValue2) {
        this.strategyValue2 = strategyValue2;
    }

    @Basic
    @Column(name = "IS_USE", nullable = true, length = 1)
    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    @Basic
    @Column(name = "TYPE", nullable = true, length = 1)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "STRATEGY_VALUE", nullable = true, length = 100)
    public String getStrategyValue() {
        return strategyValue;
    }

    public void setStrategyValue(String strategyValue) {
        this.strategyValue = strategyValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmSysAccountStrategyEntity that = (CwmSysAccountStrategyEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (strategyName != null ? !strategyName.equals(that.strategyName) : that.strategyName != null) return false;
        if (strategyNote != null ? !strategyNote.equals(that.strategyNote) : that.strategyNote != null) return false;
        if (strategyValue1 != null ? !strategyValue1.equals(that.strategyValue1) : that.strategyValue1 != null)
            return false;
        if (strategyValue2 != null ? !strategyValue2.equals(that.strategyValue2) : that.strategyValue2 != null)
            return false;
        if (isUse != null ? !isUse.equals(that.isUse) : that.isUse != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (strategyValue != null ? !strategyValue.equals(that.strategyValue) : that.strategyValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (strategyName != null ? strategyName.hashCode() : 0);
        result = 31 * result + (strategyNote != null ? strategyNote.hashCode() : 0);
        result = 31 * result + (strategyValue1 != null ? strategyValue1.hashCode() : 0);
        result = 31 * result + (strategyValue2 != null ? strategyValue2.hashCode() : 0);
        result = 31 * result + (isUse != null ? isUse.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (strategyValue != null ? strategyValue.hashCode() : 0);
        return result;
    }
}
