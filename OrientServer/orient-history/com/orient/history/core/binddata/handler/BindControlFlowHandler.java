package com.orient.history.core.binddata.handler;

import com.orient.flow.business.FlowDiagramContentBusiness;
import com.orient.flow.model.FlowTaskNodeModel;
import com.orient.history.core.annotation.HisTaskHandler;
import com.orient.history.core.binddata.model.BindControlFlowData;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.workflow.bean.FlowInfo;
import com.orient.workflow.service.ProcessInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@HisTaskHandler(types = {IBindDataHandler.BIND_TYPE_CONTROLFLOWDATA}, order = 30)
@Scope(value = "prototype")
public class BindControlFlowHandler extends AbstractBindDataHandler {

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Autowired
    ProcessInformationService processInformationService;

    @Autowired
    FlowDiagramContentBusiness flowDiagramContentBusiness;

    @Override
    public void constructBindData(String taskId, List<TaskBindData> taskBindDatas) {
        super.constructBindData(taskId, taskBindDatas);
        //获取流程绑定信息
        String piId = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_CONTROLFLOWDATA);
        List<FlowInfo> flowInfos = processInformationService.getMainAndSubPIs(piId);
        BindControlFlowData bindControlFlowData = constructBindControlFlowDataByPiId(flowInfos.get(0).getId());
        flowInfos.forEach(flowInfo -> {
            //忽略第一个 第一个为主流程
            if (!flowInfos.get(0).getId().equals(flowInfo.getId())) {
                BindControlFlowData sonBindControlFlowData = constructBindControlFlowDataByPiId(piId);
                bindControlFlowData.getSonControlFlowData().add(sonBindControlFlowData);
            }
        });
        taskBindDatas.add(bindControlFlowData);
    }

    private BindControlFlowData constructBindControlFlowDataByPiId(String piId) {
        BindControlFlowData retVal = new BindControlFlowData();
        InputStream inputStream = flowDiagramContentBusiness.getFlowJPDLContentAsStreamByPiId(piId);
        List<FlowTaskNodeModel> flowTaskNodeModels = flowDiagramContentBusiness.getLatestFlowDiagMonitorModelByPiId(piId);
        retVal.setPiId(piId);
        retVal.setJpdlDesc(CommonTools.inputstreamToStr(inputStream));
        retVal.setFlowTaskNodeModelList(flowTaskNodeModels);
        return retVal;
    }
}
