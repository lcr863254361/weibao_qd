package com.orient.background.bean;

import com.orient.sysmodel.domain.quantity.CfQuantityDO;
import com.orient.utils.BeanUtils;

/**
 * Quantity view object
 *
 * @author Administrator
 * @create 2017-07-11 10:01
 */
public class CfQuantityVO extends CfQuantityDO {

    private Long unitId;

    private String unitName;

    public CfQuantityVO() {

    }

    public CfQuantityVO(CfQuantityDO cfQuantityDO) {
        BeanUtils.copyProperties(this, cfQuantityDO);
        setUnitId(cfQuantityDO.getNumberunitDO().getId());
        setUnitName(cfQuantityDO.getNumberunitDO().getUnit());
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
}
