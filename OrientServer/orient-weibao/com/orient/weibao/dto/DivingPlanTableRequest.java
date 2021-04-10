package com.orient.weibao.dto;

import com.orient.weibao.mbg.model.CarryTool;
import com.orient.weibao.mbg.model.DivingPlanTable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class DivingPlanTableRequest extends DivingPlanTable {
    private MultipartFile[] imgList;
    private String taskId;
    private CarryTool[] selectedPlanInnerCarryTools;
    private CarryTool[] selectedPlanOuterCarryTools;

    public CarryTool[] getSelectedPlanInnerCarryTools() {
        return selectedPlanInnerCarryTools;
    }

    public void setSelectedPlanInnerCarryTools(CarryTool[] selectedPlanInnerCarryTools) {
        this.selectedPlanInnerCarryTools = selectedPlanInnerCarryTools;
    }

    public CarryTool[] getSelectedPlanOuterCarryTools() {
        return selectedPlanOuterCarryTools;
    }

    public void setSelectedPlanOuterCarryTools(CarryTool[] selectedPlanOuterCarryTools) {
        this.selectedPlanOuterCarryTools = selectedPlanOuterCarryTools;
    }

    public MultipartFile[] getImgList() {
        return imgList;
    }

    public void setImgList(MultipartFile[] imgList) {
        this.imgList = imgList;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
