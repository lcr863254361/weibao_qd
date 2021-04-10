package com.orient.sysmodel.domain.quantity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-11 9:24
 */
@Entity
@Table(name = "CF_QUANTITY_INSTANCE")
public class CfQuantityInstanceDO {
    private Long id;
    private Long modelId;
    private Long dataId;
    private CwmSysNumberunitDO numberunitDO;
    private CfQuantityDO belongQuantity;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_QUANTITY_INSTANCE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "MODEL_ID")
    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "DATA_ID")
    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUANTITY_ID")
    public CfQuantityDO getBelongQuantity() {
        return belongQuantity;
    }

    public void setBelongQuantity(CfQuantityDO belongQuantity) {
        this.belongQuantity = belongQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfQuantityInstanceDO that = (CfQuantityInstanceDO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (dataId != null ? !dataId.equals(that.dataId) : that.dataId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (dataId != null ? dataId.hashCode() : 0);
        return result;
    }
}
