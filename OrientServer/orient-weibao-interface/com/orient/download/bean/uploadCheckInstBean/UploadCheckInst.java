package com.orient.download.bean.uploadCheckInstBean;

import com.orient.download.bean.uploadCheckInstBean.CheckListTableBean;
import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-21 19:51
 */
public class UploadCheckInst {

    private String taskId="";
    private String taskName="";

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    private List<CheckListTableBean> checkListTableBeanList= UtilFactory.newArrayList();

    public List<CheckListTableBean> getCheckListTableBeanList() {
        return checkListTableBeanList;
    }

    public void setCheckListTableBeanList(List<CheckListTableBean> checkListTableBeanList) {
        this.checkListTableBeanList = checkListTableBeanList;
    }

    public int getListSize(){
        return this.checkListTableBeanList.size();
    }
}
