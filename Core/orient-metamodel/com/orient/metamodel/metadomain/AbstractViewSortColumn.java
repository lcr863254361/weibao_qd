
package com.orient.metamodel.metadomain;

import java.io.Serializable;

/**
 * 视图排序字段
 *
 * @author mengbin@cssrc.com
 * @date Feb 7, 2012
 */
public abstract class AbstractViewSortColumn extends BaseMetaBean implements Serializable {

    /**
     * The id.
     */
    private String id;

    /**
     * 所属的视图
     */
    private View cwmViews;

    /**
     * 所对应的普通属性
     */
    private Column cwmTabColumns;

    /**
     * 顺序
     */
    private Long order;

    /**
     * 视图的Id,该字段没有用到
     *//*
    private String viewid;*/

    public AbstractViewSortColumn() {
    }

    /**
     *
     * @param cwmViews      --视图
     * @param cwmTabColumns --视图的普通属性
     * @throws
     */
    public AbstractViewSortColumn(View cwmViews, Column cwmTabColumns) {
        this.cwmViews = cwmViews;
        this.cwmTabColumns = cwmTabColumns;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public View getCwmViews() {
        return cwmViews;
    }

    public void setCwmViews(View cwmViews) {
        this.cwmViews = cwmViews;
    }

    public Column getCwmTabColumns() {
        return cwmTabColumns;
    }

    public void setCwmTabColumns(Column cwmTabColumns) {
        this.cwmTabColumns = cwmTabColumns;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

}