package com.orient.background.doctemplate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-07 15:43
 */
public class DocGridData implements Serializable {

    public enum GridDirectionEnum {
        VERTICAL, ACROSS
    }

    //是否需要列头
    private Boolean needHead = true;
    //列头描述
    private List<DocGridColumn> columns = new ArrayList<>();
    //数据集合
    private List<Map<String, DocHandlerData>> dataList = new ArrayList<>();
    //方向
    private GridDirectionEnum direction = GridDirectionEnum.VERTICAL;

    public List<DocGridColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DocGridColumn> columns) {
        this.columns = columns;
    }

    public List<Map<String, DocHandlerData>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, DocHandlerData>> dataList) {
        this.dataList = dataList;
    }

    public GridDirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(GridDirectionEnum direction) {
        this.direction = direction;
    }

    public Boolean getNeedHead() {
        return needHead;
    }

    public void setNeedHead(Boolean needHead) {
        this.needHead = needHead;
    }
}
