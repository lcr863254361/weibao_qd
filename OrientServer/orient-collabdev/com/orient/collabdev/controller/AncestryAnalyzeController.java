package com.orient.collabdev.controller;

import com.aptx.utils.GsonUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.internal.Lists;
import com.google.inject.internal.Maps;
import com.orient.collabdev.business.ancestry.core.bean.NodeState;
import com.orient.collabdev.business.ancestry.taskanalyze.AncestryMainBusiness;
import com.orient.collabdev.business.ancestry.taskanalyze.AncestryModelBusiness;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.msg.bean.CommonConsumerMsg;
import com.orient.msg.bussiness.MsgBussiness;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.sysmodel.domain.mq.CwmMsg;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/ancestryAnalyze")
public class AncestryAnalyzeController extends BaseController {
    @Autowired
    AncestryMainBusiness ancestryMainBusiness;

    @Autowired
    AncestryModelBusiness ancestryModelBusiness;

    @Autowired
    MsgBussiness msgBussiness;

    @Autowired
    StructureBusiness structureBusiness;

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @RequestMapping("/taskStateAnalyze")
    @ResponseBody
    public CommonResponseData taskStateAnalyze(String nodeId, Integer version, String depends) {
        depends = Strings.isNullOrEmpty(depends) ? "{}" : depends;
        Map<String, Integer> dependsMap = new Gson().fromJson(depends, new TypeToken<Map<String, Integer>>() {}.getType());

        ancestryMainBusiness.saveVersion(nodeId);
        ancestryMainBusiness.saveStatesAndDepends(nodeId, dependsMap);
        ancestryMainBusiness.taskStateAnalyze(nodeId, version);
        return new CommonResponseData(true, "技术状态分析成功");
    }

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @RequestMapping("/rollbackStateAnalyze")
    @ResponseBody
    public CommonResponseData rollbackStateAnalyze(String nodeId, Integer version, Integer rollbackToVersion, String depends) {
        depends = Strings.isNullOrEmpty(depends) ? "{}" : depends;
        Map<String, Integer> dependsMap = new Gson().fromJson(depends, new TypeToken<Map<String, Integer>>() {}.getType());

        ancestryMainBusiness.saveVersion(nodeId);
        ancestryMainBusiness.saveStatesAndDepends(nodeId, dependsMap);
        ancestryMainBusiness.taskStateAnalyze(nodeId, version);
        ancestryMainBusiness.dataObjectRollback(nodeId, rollbackToVersion);
        return new CommonResponseData(true, "撤回执行成功");
    }

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @RequestMapping("/needlessStateAnalyze")
    @ResponseBody
    public CommonResponseData needlessStateAnalyze(String nodeId, Integer version) {
        CollabNode node = ancestryModelBusiness.getNodeById(nodeId);
        if(version!=null && version>0 && !version.equals(node.getVersion())) {
            return new CommonResponseData(false, "无法对历史任务执行无需更改操作");
        }
        CollabNodeDevStatus devStatus = ancestryModelBusiness.getLatestDevStateByNode(nodeId);
        if(devStatus!=null && !NodeState.TS_WAIT.equals(devStatus.getTechStatus())) {
            return new CommonResponseData(false, "无法对非待更新任务执行无需更改操作");
        }

        ancestryMainBusiness.saveVersion(nodeId);
        Map<String, Integer> dependsMap = ancestryModelBusiness.getUpNodesMaxValidVersions(nodeId);
        ancestryMainBusiness.saveStatesAndDepends(nodeId, dependsMap);
        ancestryMainBusiness.needlessStateAnalyze(nodeId, version);
        return new CommonResponseData(true, "无需更改执行成功");
    }

    /***************************************************************************/
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @RequestMapping("/feedback")
    @ResponseBody
    public CommonResponseData feedback(String nodeId, Integer version, String toNodeMap, String content) {
        CollabNode node = ancestryModelBusiness.getNodeById(nodeId);
        Map<String, Integer> toNodes = GsonUtil.fromJson(toNodeMap, new TypeToken<Map<String, Integer>>(){});
        for(String toNodeId : toNodes.keySet()) {
            Integer toVersion = toNodes.get(toNodeId);
            CollabNode toNode = ancestryModelBusiness.getNodeById(toNodeId);
            Map<String, String> toTask = ancestryModelBusiness.getCollabModelDataByType(toNode.getType(), toNode.getBmDataId());
            String toUserId = toTask.get("PRINCIPAL");

            Map<String, Object> data = Maps.newHashMap();
            data.put("nodeId", nodeId);
            data.put("version", version);
            data.put("bmDataId", node.getBmDataId());
            data.put("type", node.getType());
            data.put("toNodeId", toNodeId);
            data.put("toVersion", toVersion);
            data.put("toBmDataId", toNode.getBmDataId());
            data.put("toType", toNode.getType());

            CommonConsumerMsg msg = new CommonConsumerMsg();
            msg.setTitle("下游反馈消息");
            msg.setContent(content);
            msg.setData(new Gson().toJson(data));
            msg.setTimestamp(System.currentTimeMillis());
            msg.setUserId(Strings.isNullOrEmpty(toUserId) ? -1 : Long.valueOf(toUserId));
            msg.setType(CwmMsg.TYPE_COLLAB_FEEDBACK);
            msg.setSrc(UserContextUtil.getUserId());
            msg.setDest(toUserId);
            msg.setReaded(false);
            msgBussiness.saveCommonMsg(msg);
        }

        return new CommonResponseData(true, "向上游反馈成功");
    }

    @RequestMapping("/getAllHisNodes")
    @ResponseBody
    public ExtGridData<CollabHistoryNode> getAllHisNodes(String nodeId) {
        List<CollabHistoryNode> retList = ancestryModelBusiness.getAllHisNodes(nodeId);
        return new ExtGridData<>(retList, retList.size());
    }

    @RequestMapping("/getUpNodeStates")
    @ResponseBody
    public AjaxResponseData<Map<String, List<CollabNodeDevStatus>>> getUpNodeStates(String nodeId) {
        Map<String, List<CollabNodeDevStatus>> retMap = Maps.newHashMap();
        CollabNode planNode = ancestryModelBusiness.getParentNodeByType(nodeId, CollabDevConstants.NODE_TYPE_PLAN);
        Set<String> upNodeIds = ancestryModelBusiness.getUpNodeIds(nodeId, planNode.getId());
        for (String upNodeId : upNodeIds) {
            CollabNode upNode = ancestryModelBusiness.getNodeById(upNodeId);
            List<CollabNodeDevStatus> stateList = ancestryModelBusiness.getAllDevStateByNode(upNodeId);
            Set<Long> versions = Sets.newHashSet();
            for(CollabNodeDevStatus devStatus : Lists.newArrayList(stateList)) {
                if(versions.contains(devStatus.getNodeVersion())) {
                    stateList.remove(devStatus);
                }
                else {
                    versions.add(devStatus.getNodeVersion());
                }
            }
            retMap.put(upNode.getName(), stateList);
        }

        return new AjaxResponseData<>(retMap);
    }

    @RequestMapping("/getUpNodes")
    @ResponseBody
    public ExtGridData<CollabNode> getUpNodes(String nodeId) {
        List<CollabNode> retList = Lists.newArrayList();
        CollabNode planNode = ancestryModelBusiness.getParentNodeByType(nodeId, CollabDevConstants.NODE_TYPE_PLAN);
        List<CollabNodeRelation> nodeRelations = ancestryModelBusiness.getNodeRelationByPlan(planNode.getId());
        for (CollabNodeRelation nodeRelation : nodeRelations) {
            if (nodeRelation.getDestDevNodeId().equals(nodeId)) {
                CollabNode upNode = ancestryModelBusiness.getNodeById(nodeRelation.getSrcDevNodeId());
                retList.add(upNode);
            }
        }

        return new ExtGridData<>(retList, retList.size());
    }

    //根据下游反馈消息的nodeId获取任务名称
    @RequestMapping("/getTask")
    @ResponseBody
    public String getTask(String nodeId, Integer nodeVersion) {
        CollabDevNodeDTO collabDevNodeDTO = structureBusiness.getNode(nodeId, nodeVersion);
        if (collabDevNodeDTO != null) {
            return collabDevNodeDTO.getName();
        } else {
            throw new OrientBaseAjaxException("", "查询任务名称失败");
        }
    }
}
