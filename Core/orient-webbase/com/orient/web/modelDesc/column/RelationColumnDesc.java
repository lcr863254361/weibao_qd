package com.orient.web.modelDesc.column;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.metamodel.operationinterface.ITable;

import java.io.Serializable;

/**
 * 关系字段控件描述
 *
 * @author enjoy
 * @creare 2016-03-30 9:38
 */
public class RelationColumnDesc extends ColumnDesc implements Serializable {

    //关联模型显示名称
    private String refModelName;

    //关联模型主键显示值
    private String refModelShowColumn;

    //关联模型ID
    private String refModelId;
    //关联的类型 1对1 1对多 多对1 多对多
    private String refType;

    public String getRefModelId() {
        return refModelId;
    }

    public void setRefModelId(String refModelId) {
        this.refModelId = refModelId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getRefModelName() {
        return refModelName;
    }

    public void setRefModelName(String refModelName) {
        this.refModelName = refModelName;
    }

    public String getRefModelShowColumn() {
        return refModelShowColumn;
    }

    public void setRefModelShowColumn(String refModelShowColumn) {
        this.refModelShowColumn = refModelShowColumn;
    }

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {
        //初始化关系属性信息
        IRelationColumn refColumn = iBusinessColumn.getRelationColumnIF();
        //关联模型显示名称
        ITable refModel = refColumn.getRefTable();
        //关联模型ID
        refModelId = refModel.getId();
        //关联模型名称
        refModelName = refModel.getTableName();
        //关联模型主键显示值
            refModelShowColumn = refModel.getPkColumns().isEmpty() ? "" : ((Column) refModel.getPkColumns().values().iterator().next()).getColumnName();
        //关联类型
        refType = String.valueOf(refColumn.getRelationType());
    }
}
