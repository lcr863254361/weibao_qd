package com.orient.web.modelDesc.operator.builder;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.web.modelDesc.column.ColumnDesc;

/**
 * Created by enjoy on 2016/3/30 0030.
 */
public interface IColumnDescBuilder {
    ColumnDesc buildColumnDesc(IBusinessColumn iBusinessColumn);
}
