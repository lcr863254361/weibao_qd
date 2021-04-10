package com.orient.flow.controller;

import com.orient.flow.business.FlowInfoBusiness;
import com.orient.flow.model.NextFlowNodeInfo;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * the flow information controller
 *
 * @author Seraph
 *         2016-08-04 下午2:18
 */
@Controller
@RequestMapping("/flow/info")
public class FlowInfoController extends BaseController{

    @RequestMapping("/bindDatas")
    @ResponseBody
    public AjaxResponseData<List<FlowDataRelation>> getFlowBindDatas(String piId){
        return new AjaxResponseData(this.flowInfoBusiness.getFlowBindDatas(piId));
    }

    /**
     * 得到后续的任务信息,注意后续可能非任务,而是fork或join,此时会递归此类节点的后续任务节点
     * 但是应注意,fork后应全是task,join后应为task且仅一个,否则可能会在设置后续任务节点执行人时出现问题
     * @param flowTaskId
     * @return
     */
    @RequestMapping("/nextFlowNodes")
    @ResponseBody
    public AjaxResponseData<List<NextFlowNodeInfo>> getNextFlowNodesInfo(String flowTaskId){
        return new AjaxResponseData(this.flowInfoBusiness.getNextFlowNodesInfo(flowTaskId));
    }

    @Autowired
    private FlowInfoBusiness flowInfoBusiness;
}
