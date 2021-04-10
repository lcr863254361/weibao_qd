package com.orient.metamodel.metadomain;

/**
 * 视图的排序字段
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public class ViewSortColumn extends AbstractViewSortColumn {

    public ViewSortColumn() {
    }

    /**
     * full constructor.
     *
     * @param cwmViews      --视图
     * @param cwmTabColumns --视图的普通属性
     */
    public ViewSortColumn(View cwmViews, Column cwmTabColumns) {
        super(cwmViews, cwmTabColumns);
    }

}
