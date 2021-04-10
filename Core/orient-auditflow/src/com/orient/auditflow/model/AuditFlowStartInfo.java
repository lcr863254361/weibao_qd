package com.orient.auditflow.model;

import com.orient.workflow.bean.AssignUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * information necessary to start an audit flow
 *
 * @author Seraph
 *         2016-08-03 下午1:43
 */
public class AuditFlowStartInfo {

    /**
     * 待启动pdName,将转换为最新版本PdId
     */
    private String pdName;

    private String pdId;

    /**
     * 审批流程类型
     */
    private String auditType;

    /**
     * 绑定的模型数据,key是模型名称,value是数据id
     */
    private List<BindDataInfo> bindDatas;

    /**
     * 第一个任务的执行人
     */
    private AssignUser firstTaskUserAssign;

    private Map<String,AssignUser> taskUserAssigns = new HashMap<>();

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public Map<String, AssignUser> getTaskUserAssigns() {
        return taskUserAssigns;
    }

    public void setTaskUserAssigns(Map<String, AssignUser> taskUserAssigns) {
        this.taskUserAssigns = taskUserAssigns;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public AssignUser getFirstTaskUserAssign() {
        return firstTaskUserAssign;
    }

    public void setFirstTaskUserAssign(AssignUser firstTaskUserAssign) {
        this.firstTaskUserAssign = firstTaskUserAssign;
    }

    public List<BindDataInfo> getBindDatas() {
        return bindDatas;
    }

    public void setBindDatas(List<BindDataInfo> bindDatas) {
        this.bindDatas = bindDatas;
    }

    public static class BindDataInfo {

        private String model;
        private String dataId;
        private String processType;
        //额外的属性
        private Map<String, String> extraParams = new HashMap<>();

        public Map<String, String> getExtraParams() {
            return extraParams;
        }

        public void setExtraParams(Map<String, String> extraParams) {
            this.extraParams = extraParams;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public String getProcessType() {
            return processType;
        }

        public void setProcessType(String processType) {
            this.processType = processType;
        }
    }
}
