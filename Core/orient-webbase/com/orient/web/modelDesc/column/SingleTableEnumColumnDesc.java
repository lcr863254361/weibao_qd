package com.orient.web.modelDesc.column;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.metadomain.TableEnum;

import java.io.Serializable;

/**
 * 单选下拉框表单元素描述
 *
 * @author enjoy
 * @creare 2016-03-30 10:12
 */
public class SingleTableEnumColumnDesc extends EnumColumnDesc implements Serializable {


    private String bindModelId;

    private String displayColumnDBName;

    @Override
    public void specialInit(IBusinessColumn iBusinessColumn) {
        Restriction restriction = iBusinessColumn.getRestriction();
        TableEnum tableEnum = restriction.getTableEnum();
        if (null != tableEnum) {
            bindModelId = tableEnum.getTable().getId();
            displayColumnDBName = tableEnum.getColumn().getColumnName();
        }
    }

    public String getBindModelId() {
        return bindModelId;
    }

    public void setBindModelId(String bindModelId) {
        this.bindModelId = bindModelId;
    }

    public String getDisplayColumnDBName() {
        return displayColumnDBName;
    }

    public void setDisplayColumnDBName(String displayColumnDBName) {
        this.displayColumnDBName = displayColumnDBName;
    }
}
