package com.orient.download.bean.currentTaskBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-20 15:17
 */
public class DivingTaskBean {
    private String id="";
    private String taskName="";
    //总指挥
    private String leaderId="";

    private List<FlowBean>  flowBeanList= UtilFactory.newArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<FlowBean> getFlowBeanList() {
        return flowBeanList;
    }

    public void setFlowBeanList(List<FlowBean> flowBeanList) {
        this.flowBeanList = flowBeanList;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
}
