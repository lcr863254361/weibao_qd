package com.orient.collab.business.aspect;

import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.RoleFunctionTreeNode;
import com.orient.sysmodel.domain.collab.CollabFunction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-14 14:51
 */
@Component
public class ControlFunction {

    List<String> planBreakFunctionDic = new ArrayList<String>() {{
        add("控制流");
        add("任务处理");
        add("数据流");
        add("工作包分解");
    }};

    List<String> pvmDataFunctionDic = new ArrayList<String>() {{
        add("离线数据");
    }};

    Boolean canPlanBreak = CollabConstants.COLLAB_ENABLE_PLAN_BREAK;

    Boolean canPVMData = CollabConstants.COLLAB_ENABLE_PVMDATA;

    public void controlModelFunctions(List<CollabFunction> functions) {

        List<CollabFunction> toRemoveFunctions = new ArrayList<>();
        if (!canPlanBreak) {
            functions.forEach(collabFunction -> {
                if (planBreakFunctionDic.contains(collabFunction.getName())) {
                    toRemoveFunctions.add(collabFunction);
                }
            });
        }
        if (!canPVMData) {
            functions.forEach(collabFunction -> {
                if (pvmDataFunctionDic.contains(collabFunction.getName())) {
                    toRemoveFunctions.add(collabFunction);
                }
            });
        }
        functions.removeAll(toRemoveFunctions);
    }

    public void controlRoleFunctions(List<RoleFunctionTreeNode> functions) {
        List<RoleFunctionTreeNode> toRemoveFunctions = new ArrayList<>();
        if (!canPlanBreak) {
            functions.forEach(roleFunctionTreeNode -> {
                if (planBreakFunctionDic.contains(roleFunctionTreeNode.getText())) {
                    toRemoveFunctions.add(roleFunctionTreeNode);
                }
            });
        }
        if (!canPVMData) {
            functions.forEach(roleFunctionTreeNode -> {
                if (pvmDataFunctionDic.contains(roleFunctionTreeNode.getText())) {
                    toRemoveFunctions.add(roleFunctionTreeNode);
                }
            });
        }
        functions.removeAll(toRemoveFunctions);
    }
}
