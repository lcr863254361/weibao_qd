package com.orient.download.bean.currentTaskBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-20 15:24
 */
public class FlowBean {
    private String id="";
   private String name="";
   //拆解维修流程开始时间,日期格式：yyyy-MM-dd
   private String beginDate="";
    //拆解维修流程结束时间,日期格式：yyyy-MM-dd
   private String endDate="";
    private List<CheckTempInstBean> checkTempInstBeanList= UtilFactory.newArrayList();
    private List<PostBean> postBeanList= UtilFactory.newArrayList();
    //下载拆解流程的技术资料文件Ids
    private List<String> fileIds=UtilFactory.newArrayList();

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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<CheckTempInstBean> getCheckTempInstBeanList() {
        return checkTempInstBeanList;
    }

    public void setCheckTempInstBeanList(List<CheckTempInstBean> checkTempInstBeanList) {
        this.checkTempInstBeanList = checkTempInstBeanList;
    }

    public List<PostBean> getPostBeanList() {
        return postBeanList;
    }

    public void setPostBeanList(List<PostBean> postBeanList) {
        this.postBeanList = postBeanList;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }
}
