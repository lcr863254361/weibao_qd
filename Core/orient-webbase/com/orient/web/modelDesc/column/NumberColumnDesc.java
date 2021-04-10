package com.orient.web.modelDesc.column;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.metadomain.TableEnum;
import com.orient.utils.CommonTools;
import com.orient.utils.Commons;
import com.orient.utils.StringUtil;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数值类型表单元素描述
 *
 * @author enjoy
 * @creare 2016-03-30 9:40
 */
public class NumberColumnDesc extends ColumnDesc implements Serializable {

    //默认长度
    private int numberLength = 100;

    //默认精度
    private int numberPrecision = 0;

    //单位信息
    private String unit;

    private BigDecimal minValue;

    private BigDecimal maxValue;

    private String minColumnId;

    private String maxColumnId;

    private String minTableId;

    private String maxTableId;

    public String getMinColumnId() {
        return minColumnId;
    }

    public void setMinColumnId(String minColumnId) {
        this.minColumnId = minColumnId;
    }

    public String getMaxColumnId() {
        return maxColumnId;
    }

    public void setMaxColumnId(String maxColumnId) {
        this.maxColumnId = maxColumnId;
    }

    public String getMinTableId() {
        return minTableId;
    }

    public void setMinTableId(String minTableId) {
        this.minTableId = minTableId;
    }

    public String getMaxTableId() {
        return maxTableId;
    }

    public void setMaxTableId(String maxTableId) {
        this.maxTableId = maxTableId;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getNumberLength() {
        return numberLength;
    }

    public void setNumberLength(int numberLength) {
        this.numberLength = numberLength;
    }

    public int getNumberPrecision() {
        return numberPrecision;
    }

    public void setNumberPrecision(int numberPrecision) {
        this.numberPrecision = numberPrecision;
    }


    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {
        unit = iBusinessColumn.getCol().getUnit();
        Restriction restriction = iBusinessColumn.getRestriction();
        if (null != restriction) {
            //静态约束
            minValue = restriction.getMinLength();
            maxValue = restriction.getMaxLength();
            //动态约束
            if (null != restriction.getTableEnum()) {
                TableEnum tableEnum = restriction.getTableEnum();
                maxTableId = tableEnum.getMaxTable().getId();
                maxColumnId = tableEnum.getMaxColumn().getId();
                minTableId = tableEnum.getMinTable().getId();
                minColumnId = tableEnum.getMinColumn().getId();
            }
        }
        Long numprecision = iBusinessColumn.getCol().getNumprecision();
        if(numprecision != null){
            numberPrecision = numprecision.intValue();
        }
    }

}
