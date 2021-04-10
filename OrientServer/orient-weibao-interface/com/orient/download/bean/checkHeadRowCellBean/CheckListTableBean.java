package com.orient.download.bean.checkHeadRowCellBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *下载表格的实体类
 * @author User
 * @create 2018-10-20 11:18
 */
public class CheckListTableBean {
    private String id;
    private String name;
    //状态用于区分填写表还是签署表
    private String state;
    private List<HeadBean> headBeanArrayList= UtilFactory.newArrayList();
    private List<RowBean> rowBeanArrayList= UtilFactory.newArrayList();
    //最前面内容
    private List<FrontContentBean> frontContentBeanList=UtilFactory.newArrayList();
    //结尾内容
    private List<EndContentBean> endContentBeanList=UtilFactory.newArrayList();
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HeadBean> getHeadBeanArrayList() {
        return headBeanArrayList;
    }

    public void setHeadBeanArrayList(List<HeadBean> headBeanArrayList) {
        this.headBeanArrayList = headBeanArrayList;
    }

    public List<RowBean> getRowBeanArrayList() {
        return rowBeanArrayList;
    }

    public void setRowBeanArrayList(List<RowBean> rowBeanArrayList) {
        this.rowBeanArrayList = rowBeanArrayList;
    }

    public void addHeader(HeadBean headBean){
        this.headBeanArrayList.add(headBean);
    }
    public void addRow(RowBean rowBean){
        this.rowBeanArrayList.add(rowBean);
    }

    public List<FrontContentBean> getFrontContentBeanList() {
        return frontContentBeanList;
    }

    public void setFrontContentBeanList(List<FrontContentBean> frontContentBeanList) {
        this.frontContentBeanList = frontContentBeanList;
    }

    public List<EndContentBean> getEndContentBeanList() {
        return endContentBeanList;
    }

    public void setEndContentBeanList(List<EndContentBean> endContentBeanList) {
        this.endContentBeanList = endContentBeanList;
    }

    public void addFrontContent(FrontContentBean frontContentBean){
        this.frontContentBeanList.add(frontContentBean);
    }
    public void addEndContent(EndContentBean endContentBean){
        this.endContentBeanList.add(endContentBean);
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
