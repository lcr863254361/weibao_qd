package com.orient.workflow.tools;

import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.orient.workflow.WorkFlowConstants;
import com.orient.workflow.bean.DeployProperty;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.model.Activity;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowCommonTools {
    /**
     * @Function Name:  getValueByObjNameAndKey
     * @Description: @param dps 集合
     * @Description: @param objName 属性名称
     * @Description: @param key 属性key
     * @Description: @return 属性值
     * @Date Created:  2012-6-6 下午01:51:55
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    public static String getValueByObjNameAndKey(List<DeployProperty> dps, String objName, String key) {
        String value = "";
        //空转串
        objName = CommonTools.null2String(objName);
        key = CommonTools.null2String(key);
        for (DeployProperty dp : dps) {
            if (objName.equals(dp.getObjName()) && key.equals(dp.getKey())) {
                value = dp.getValue();
                break;
            }
        }
        return value;
    }

    /**
     * @param pd
     * @return 根據流程定義獲取其下面所有的任務信息集合
     */
    public static List<Activity> getTaskActivities(ProcessDefinition pd) {
        List<Activity> theStartTransActivities = UtilFactory.newArrayList();
        ProcessDefinitionImpl prcDefImpl = (ProcessDefinitionImpl) pd;
        prcDefImpl.getActivities().forEach(activity -> {
            if (activity.getType().equals(WorkFlowConstants.ACTIVITY_TYPE_TASK) || activity.getType().equals(WorkFlowConstants.ACTIVITY_TYPE_CUSTOM)) {
                theStartTransActivities.add(activity);
            }
        });
        return theStartTransActivities;
    }

    public static ExecutionImpl getMainLineExecution(ExecutionImpl execution) {
        ExecutionImpl mainExecution = execution.getProcessInstance();
        while (mainExecution.getProcessInstance().getSuperProcessExecution() != null) {
            mainExecution = mainExecution.getSuperProcessExecution()
                    .getProcessInstance();
        }
        return mainExecution;
    }

    public static Map<String, String> splitNameAndVersion(String pdId) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("flowName", pdId.substring(0, pdId.lastIndexOf("-")));
        dataMap.put("flowVersion", pdId.substring(pdId.lastIndexOf("-") + 1, pdId.length()));
        return dataMap;
    }
}
