package com.orient.businessmodel.bean.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessColumnEnum;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.sysmodel.operationinterface.IColumnRight;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName BusinessColumn
 * 业务字段
 * @date Apr 17, 2012
 */

public class BusinessColumn implements IBusinessColumn {
    private IColumn col;
    private IColumnRight colRight;
    private IBusinessModel parentModel;
    protected boolean editable = true;
    protected boolean createable = true;
    protected boolean nullable = true;
    //额外过滤条件--为检验唯一性准备
    private String extraCondition = "";

    public BusinessColumn(IColumn col) {
        this.col = col;
    }

    public EnumInter getColType() {
        return BusinessColumnEnum.getBusinessColType(this);
    }

    public IColumn getCol() {
        return col;
    }

    public IBusinessModel getParentModel() {
        return parentModel;
    }

    public void setParentModel(IBusinessModel parentModel) {
        this.parentModel = parentModel;
    }

    public boolean getEditable() {// 能否修改
        return true;
    }

    @Override
    public Restriction getRestriction() {

        return col.getRestriction();
    }

    @Override
    public IRelationColumn getRelationColumnIF() {

        return col.getRelationColumnIF();
    }

    @Override
    public String getIs_Pk() {

        return String.valueOf(col.getIsPK());
    }

    @Override
    public boolean getNullable() {
        //关系列为空标志不同 spf 2014/02/17
        if (getColType() == BusinessColumnEnum.C_Relation) {
            if (null != col && col.getIsNeed().toLowerCase().equals("true")) {

                nullable = false;
            }
        } else {
            if (null != col.getIsNull() && col.getIsNull().toLowerCase().equals("true")) {
                nullable = false;
            }
        }
        return nullable;
    }

    public boolean getCreateable() {// 能否创建
        return true;
    }

    public String getS_column_name() {
        if (getColType() == BusinessColumnEnum.C_Relation) {
            if (col.getRelationColumnIF().getIsFK() == 0) {
                return "";
            } else {
                return col.getRelationColumnIF().getRefTable().getTableName().toUpperCase() + "_ID";
            }
        }
        return col.getColumnName().toUpperCase();
    }

    public String getDisplay_name() {
        return col.getDisplayName();
    }

    public IColumnRight getColRight() {
        return colRight;
    }

    public void setColRight(IColumnRight colRight) {
        this.colRight = colRight;
    }

    @Override
    public String getExtraCondition() {
        return extraCondition;
    }

    @Override
    public void setExtraCondition(String extraCondition) {
        this.extraCondition = extraCondition;
    }

    @Override
    public String getSelector() {
        return col.getSelector();
    }

    @Override
    public String getUnit() {
        return col.getUnit();
    }

    @Override
    public String getId() {
        return col.getId();
    }

    @Override
    public String getEditType() {
        return col.getEditable();
    }
}
