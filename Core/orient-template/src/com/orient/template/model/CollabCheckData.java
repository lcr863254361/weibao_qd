package com.orient.template.model;

import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.utils.UtilFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * represent a check data node
 *
 * @author Seraph
 *         2016-10-18 上午9:36
 */
public class CollabCheckData implements Serializable {

    private List<TaskCheckModel> taskCheckModels = UtilFactory.newArrayList();
    private List<TaskCheckRelation> taskCheckRelations = UtilFactory.newArrayList();
    private Map<String, List<Map<String, String>>> taskCheckDatas = new HashMap<>();

    transient private String taskModelId;
    transient private String taskDataId;

    private static final long serialVersionUID = 1L;

    public CollabCheckData(String taskModelId, String taskDataId) {
        this.taskModelId = taskModelId;
        this.taskDataId = taskDataId;
    }

    public List<TaskCheckModel> getTaskCheckModels() {
        return taskCheckModels;
    }

    public void setTaskCheckModels(List<TaskCheckModel> taskCheckModels) {
        this.taskCheckModels = taskCheckModels;
    }

    public List<TaskCheckRelation> getTaskCheckRelations() {
        return taskCheckRelations;
    }

    public void setTaskCheckRelations(List<TaskCheckRelation> taskCheckRelations) {
        this.taskCheckRelations = taskCheckRelations;
    }

    public String getTaskModelId() {
        return taskModelId;
    }

    public String getTaskDataId() {
        return taskDataId;
    }

    public void setTaskDataId(String taskDataId) {
        this.taskDataId = taskDataId;
    }

    public Map<String, List<Map<String, String>>> getTaskCheckDatas() {
        return taskCheckDatas;
    }

    public void setTaskCheckDatas(Map<String, List<Map<String, String>>> taskCheckDatas) {
        this.taskCheckDatas = taskCheckDatas;
    }

}
