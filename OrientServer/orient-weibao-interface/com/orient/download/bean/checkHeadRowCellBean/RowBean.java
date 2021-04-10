package com.orient.download.bean.checkHeadRowCellBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-10-22 9:03
 */
public class RowBean {
    private String order;
    private List<CellDataBean> cells= UtilFactory.newArrayList();
    private String dbId;
    //索引名称
    private String indexName="";
    //只针对自增行的人员ID
    private String serverUploaderId="";

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<CellDataBean> getCells() {
        return cells;
    }

    public void setCells(List<CellDataBean> cells) {
        this.cells = cells;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public void addCell(CellDataBean cellBean){
        this.cells.add(cellBean);
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getServerUploaderId() {
        return serverUploaderId;
    }

    public void setServerUploaderId(String serverUploaderId) {
        this.serverUploaderId = serverUploaderId;
    }
}
