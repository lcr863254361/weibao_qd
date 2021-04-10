package com.orient.workflow.cmd;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 * 同步主流程所绑定的数据信息
 */
public class SyncMainProcessBindCommand implements Command<Object> {

    private ExecutionImpl subProcessInstance;

    public SyncMainProcessBindCommand(ExecutionImpl subProcessInstance) {
        this.subProcessInstance = subProcessInstance;
    }

    @Override
    public Object execute(Environment environment) throws Exception {
        IFlowDataRelationService flowDataRelationService = (IFlowDataRelationService) OrientContextLoaderListener.Appwac.getBean("flowDataRelationService");
        ExecutionImpl mainExecution = this.subProcessInstance;
        while (mainExecution.getSuperProcessExecution() != null) {
            mainExecution = mainExecution.getSuperProcessExecution();
        }
        List<FlowDataRelation> flowDataRelationList = flowDataRelationService.list(Restrictions.eq("piId", mainExecution.getId()));
        if (!CommonTools.isEmptyList(flowDataRelationList)) {
            flowDataRelationList.forEach(flowDataRelation -> {
                FlowDataRelation subDataBind = new FlowDataRelation();
                BeanUtils.copyProperties(subDataBind, flowDataRelation);
                subDataBind.setId(null);
                subDataBind.setPiId(this.subProcessInstance.getId());
                flowDataRelationService.save(subDataBind);
            });
        }
        return null;
    }
}
