package com.orient.download.bean.uploadCheckInstBean;

import com.orient.download.bean.checkHeadRowCellBean.EndContentBean;
import com.orient.download.bean.checkHeadRowCellBean.FrontContentBean;
import com.orient.utils.UtilFactory;

import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *上传的检查表实体
 * @author User
 * @create 2018-10-20 11:18
 */
public class CheckListTableBean {
    private String id;
    private String name;
    private String state;
    private String checkDate;
    private String checkPerson;
    private String isAttention;
    private String isException;
    //1,3普通表；2只有表头生成的表格
    private String checkTableType;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getIsException() {
        return isException;
    }

    public void setIsException(String isException) {
        this.isException = isException;
    }

    public String getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(String isAttention) {
        this.isAttention = isAttention;
    }

    public String getCheckTableType() {
        return checkTableType;
    }

    public void setCheckTableType(String checkTableType) {
        this.checkTableType = checkTableType;
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
}
