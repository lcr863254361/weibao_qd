package com.orient.background.bean;

import com.orient.sysmodel.domain.quantity.CfQuantityInstanceDO;
import com.orient.utils.BeanUtils;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2017-07-13 2:19 PM
 */
public class CfQuantityInstanceVO extends CfQuantityInstanceDO {

    private Long unitId;

    private String unitName;

    private String quantityName;

    private String quantityDataType;

    private Long quantityId;

    public CfQuantityInstanceVO() {
    }

    public CfQuantityInstanceVO(CfQuantityInstanceDO quantityInstanceDO) {
        BeanUtils.copyProperties(this, quantityInstanceDO);
        setUnitId(quantityInstanceDO.getNumberunitDO().getId());
        setUnitName(quantityInstanceDO.getNumberunitDO().getUnit());
        setQuantityName(quantityInstanceDO.getBelongQuantity().getName());
        setQuantityDataType(quantityInstanceDO.getBelongQuantity().getDatatype());
        setQuantityId(quantityInstanceDO.getBelongQuantity().getId());
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getQuantityName() {
        return quantityName;
    }

    public void setQuantityName(String quantityName) {
        this.quantityName = quantityName;
    }

    public String getQuantityDataType() {
        return quantityDataType;
    }

    public void setQuantityDataType(String quantityDataType) {
        this.quantityDataType = quantityDataType;
    }

    public Long getQuantityId() {
        return quantityId;
    }

    public void setQuantityId(Long quantityId) {
        this.quantityId = quantityId;
    }
}
