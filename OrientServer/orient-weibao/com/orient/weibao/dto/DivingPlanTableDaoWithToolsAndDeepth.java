package com.orient.weibao.dto;

import com.orient.weibao.mbg.model.DepthDesity;
import com.orient.weibao.mbg.model.DivingPlanTable;

import java.util.List;

public class DivingPlanTableDaoWithToolsAndDeepth extends DivingPlanTable {

    private List<CarryToolWithParams> carryTools;
    private DepthDesity depthDesity;

    public List<CarryToolWithParams> getCarryTools() {
        return carryTools;
    }

    public DepthDesity getDepthDesity() {
        return depthDesity;
    }

    public void setCarryTools(List<CarryToolWithParams> carryTools) {
        this.carryTools = carryTools;
    }

    public void setDepthDesity(DepthDesity depthDesity) {
        this.depthDesity = depthDesity;
    }
}
