package com.orient.metamodel.metadomain;

/**
 * 视图的显示字段
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public class ViewResultColumn extends AbstractViewResultColumn {

    public ViewResultColumn() {
    }

    @Override
    public ViewResultColumn clone() throws CloneNotSupportedException {
        return (ViewResultColumn) super.clone();
    }

    /**
     * full constructor
     *
     * @param cwmViews      --所属视图
     * @param cwmTabColumns --对应的普通属性
     */
    public ViewResultColumn(View cwmViews, Column cwmTabColumns) {
        super(cwmViews, cwmTabColumns);
    }

}
