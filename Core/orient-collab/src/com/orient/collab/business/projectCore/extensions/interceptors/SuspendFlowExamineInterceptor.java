package com.orient.collab.business.projectCore.extensions.interceptors;

import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessMarker;
import com.orient.collab.business.projectCore.extensions.util.ThreadParamaterHolder;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.StatefulModel;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.flow.business.ProcessInstanceBusiness;
import com.orient.sysmodel.service.flow.impl.FlowDataRelationService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static com.orient.collab.config.CollabConstants.COLLAB_PD_NAME_SPERATOR;

/**
 * when suspend flow, examine the pi
 *
 * @author Seraph
 *         2016-08-15 下午2:43
 */
@CollabProcessMarker(order = 1, processType={ProcessType.SUSPEND}, models={CollabConstants.PLAN, CollabConstants.TASK})
public class SuspendFlowExamineInterceptor implements CollabProcessInterceptor {

    @Override
    public boolean preHandle(StatefulModel statefulModel, String modelName, ProcessType processType) throws Exception {
        String pdKey = modelName + COLLAB_PD_NAME_SPERATOR + statefulModel.getId();
        List<ProcessDefinition> processDefinitionList = processDefinitionBusiness.getAllPrcDefsWithPdKeyOrNameDescByVersion(pdKey, true);

        if(processDefinitionList.size() == 0){
            throw new CollabFlowControlException("未部署流程定义");
        }

        ProcessDefinition processDefinition = processDefinitionList.get(0);
        ProcessInstance pi = processInstanceBusiness.getLatestActiveProcessInstanceByPdId(processDefinition.getId());
        if(pi == null){
            throw new CollabFlowControlException("流程尚未启动");
        }

        ThreadParamaterHolder.put(ProcessInstance.class, pi);
        return true;
    }

    @Override
    public void afterCompletion(StatefulModel statefulModel, String modelName, ProcessType processType, Object processResult) throws Exception {

    }

    @Autowired
    @Qualifier("processInstanceBusiness")
    private ProcessInstanceBusiness processInstanceBusiness;
    @Autowired
    @Qualifier("processDefinitionBusiness")
    private ProcessDefinitionBusiness processDefinitionBusiness;
    @Autowired
    private FlowDataRelationService flowDataRelationService;
}
