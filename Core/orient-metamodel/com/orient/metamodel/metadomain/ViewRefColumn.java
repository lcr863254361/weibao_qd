package com.orient.metamodel.metadomain;

/**
 * 数据视图的关联数据类信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public class ViewRefColumn extends AbstractViewRefColumn  {

    public ViewRefColumn() {
    }

    /**
     * @param cwmViews  --视图
     * @param cwmTables --主数据类
     */
    public ViewRefColumn(View cwmViews, Table cwmTables) {
        super(cwmViews, cwmTables);
    }

}
