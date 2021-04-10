package com.orient.metamodel.metadomain;

/**
 * 视图的显示属性
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public abstract class AbstractViewResultColumn extends BaseMetaBean  {

    /**
     * The id.
     */
    private String id;

    /**
     * 所属视图
     */
    private View cwmViews;

    /**
     * 对应的普通属性
     */
    private Column cwmTabColumns;

    /**
     * 顺序
     */
    private Long order;

    /**
     * 视图id.
     */
    private String viewid;


    public AbstractViewResultColumn() {
    }

    /**
     * full constructor.
     *
     * @param cwmViews      --所属视图
     * @param cwmTabColumns --对应的普通属性
     */
    public AbstractViewResultColumn(View cwmViews, Column cwmTabColumns) {
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

    public String getViewid() {
        return viewid;
    }

    public void setViewid(String viewid) {
        this.viewid = viewid;
    }

}

