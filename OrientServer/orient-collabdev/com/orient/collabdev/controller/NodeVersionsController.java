package com.orient.collabdev.controller;

import com.orient.collabdev.model.NodeVersionVO;
import com.orient.collabdev.model.WheelIteration;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabRound;
import com.orient.sysmodel.service.collabdev.ICollabHistoryNodeService;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.sysmodel.service.collabdev.ICollabRoundService;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 节点版本相关
 * @Author GNY
 * @Date 2018/8/27 9:56
 * @Version 1.0
 **/
@Controller
@RequestMapping("/nodeVersions")
public class NodeVersionsController extends BaseController {

    /**
     * 获取一个节点的所有版本信息,包括当前版本和历史版本
     *
     * @param nodeId
     * @return
     */
    @RequestMapping("")
    @ResponseBody
    public AjaxResponseData<List<NodeVersionVO>> list(String nodeId) {
        AjaxResponseData<List<NodeVersionVO>> retVal = new AjaxResponseData<>();
        if (CommonTools.isNullString(nodeId)) {
            throw new OrientBaseAjaxException("", "ajax请求参数缺少节点id，请检查前台代码");
        }
        List<NodeVersionVO> result = new ArrayList<>();
        CollabNode currentNode = collabNodeService.getById(nodeId);
        deal(result, currentNode);
        retVal.setResults(result);
        return retVal;
    }

    private void deal(List<NodeVersionVO> result, CollabNode currentNode) {
        NodeVersionVO versionVO = new NodeVersionVO();
        versionVO.setNodeId(currentNode.getId());
        versionVO.setHistory(false);
        versionVO.setUpdateTime(currentNode.getUpdateTime());
        versionVO.setUpdateUser(currentNode.getUpdateUser());
        versionVO.setVersion(currentNode.getVersion());
        result.add(versionVO);
        List<CollabHistoryNode> historyNodeList = currentNode.getHistoryNodeList();
        if (!CommonTools.isEmptyList(historyNodeList)) {
            convertHistoryNodeToNodeVersionVO(result, historyNodeList);
        }
    }

    /**
     * 跳转到工作包迭代轮次版本轮播的Slider.jsp
     */
    @RequestMapping("listPlanWheelIteration")
    public ModelAndView listPlanVersion(String nodeId) {
        ModelAndView modelAndView = new ModelAndView();
        AjaxResponseData<List<WheelIteration>> results = listWheelIterations(nodeId);
        WheelIteration wheelIteration = results.getResults().get(0);
        List<NodeVersionVO> result = new ArrayList<>();
        CollabNode currentNode = collabNodeService.getById(nodeId);
        getPlanVersionList(nodeId, wheelIteration.getStartVersion(), wheelIteration.getEndVersion(), result, currentNode);
        modelAndView.setViewName("/app/javascript/orientjs/extjs/CollabDev/Processing/ViewBoard/Plan/Slider.jsp");
        modelAndView.addObject("list", result);
        modelAndView.addObject("nodeId", nodeId);
        modelAndView.addObject("wheelNumber", wheelIteration.getWheelNumber());
        return modelAndView;
    }

    /**
     * 跳转到工作包迭代轮次版本轮播的Slider.jsp
     */
    @RequestMapping("getDefaultPlanVersions")
    @ResponseBody
    public AjaxResponseData<List<NodeVersionVO>>  getDefaultPlanVersions(String nodeId) {
        AjaxResponseData<List<NodeVersionVO>> retVal =new AjaxResponseData<>();
        WheelIteration wheelIteration = listWheelIterations(nodeId).getResults().get(0);
        List<NodeVersionVO> result = new ArrayList<>();
        CollabNode currentNode = collabNodeService.getById(nodeId);
        getPlanVersionList(nodeId, wheelIteration.getStartVersion(), wheelIteration.getEndVersion(), result, currentNode);
        retVal.setResults(result);
        return retVal;
    }

    /**
     * 获取一个迭代轮次下所有工作包的版本集合
     *
     * @param nodeId
     * @param startVersion
     * @param endVersion
     * @return
     */
    @RequestMapping("listPlanVersionsByWheelNumber")
    @ResponseBody
    public AjaxResponseData<List<NodeVersionVO>> listPlanVersionByWheelNumber(String nodeId, Long startVersion, Long endVersion) {
        AjaxResponseData<List<NodeVersionVO>> retVal = new AjaxResponseData<>();
        List<NodeVersionVO> result = new ArrayList<>();
        CollabNode currentNode = collabNodeService.getById(nodeId);
        getPlanVersionList(nodeId, startVersion, endVersion, result, currentNode);
        retVal.setResults(result);
        return retVal;
    }

    private void getPlanVersionList(String nodeId, Long startVersion, Long endVersion, List<NodeVersionVO> result, CollabNode currentNode) {
        if (startVersion != null && endVersion != null) { //非最新迭代轮：可能是多轮迭代中的一轮，也可能是刚好迭代完的最新一轮
            List<CollabHistoryNode> historyNodeList = currentNode.getHistoryNodeList()
                    .stream()
                    .filter(node -> node.getVersion() <= endVersion && node.getVersion() >= startVersion)
                    .sorted(Comparator.comparingInt(CollabHistoryNode::getVersion))
                    .collect(Collectors.toList());
            convertHistoryNodeToNodeVersionVO(result, historyNodeList);
            if (endVersion.equals(Long.valueOf(currentNode.getVersion()))) {//如果是刚好迭代完的最新一轮，需要把当前最新节点版本也加入
                NodeVersionVO versionVO = new NodeVersionVO();
                versionVO.setNodeId(currentNode.getId());
                versionVO.setHistory(false);
                versionVO.setUpdateTime(currentNode.getUpdateTime());
                versionVO.setUpdateUser(currentNode.getUpdateUser());
                versionVO.setVersion(currentNode.getVersion());
                result.add(versionVO);
            }
        } else {
            List<CollabRound> collabRoundList = collabRoundService.list(Restrictions.eq("nodeId", nodeId));
            if (!CommonTools.isEmptyList(collabRoundList)) {
                Long lastSecondWheelEndVersion = collabRoundList.get(collabRoundList.size() - 1).getEndVersion();
                dealCurrentWheelIteration(result, currentNode, lastSecondWheelEndVersion);
            } else {
                List<CollabHistoryNode> historyNodeList = currentNode.getHistoryNodeList()
                        .stream()
                        .sorted(Comparator.comparingInt(CollabHistoryNode::getVersion))
                        .collect(Collectors.toList());
                if (!CommonTools.isEmptyList(historyNodeList)) {
                    convertHistoryNodeToNodeVersionVO(result, historyNodeList);
                }
                NodeVersionVO versionVO = new NodeVersionVO();
                versionVO.setNodeId(currentNode.getId());
                versionVO.setHistory(false);
                versionVO.setUpdateTime(currentNode.getUpdateTime());
                versionVO.setUpdateUser(currentNode.getUpdateUser());
                versionVO.setVersion(currentNode.getVersion());
                result.add(versionVO);
            }
        }
    }

    /**
     * 获取工作包迭代轮次
     *
     * @param nodeId
     * @return
     */
    @RequestMapping("wheelsIterations")
    @ResponseBody
    public AjaxResponseData<List<WheelIteration>> listWheelIterations(String nodeId) {
        AjaxResponseData<List<WheelIteration>> retVal = new AjaxResponseData<>();
        List<WheelIteration> results = new ArrayList<>();
        List<CollabRound> collabRoundList = collabRoundService.list(Restrictions.eq("nodeId", nodeId), Order.asc("id"));
        if (!CommonTools.isEmptyList(collabRoundList)) {
            for (int i = collabRoundList.size() - 1; i >= 0; i--) {
                if (i == collabRoundList.size() - 1) {
                    CollabNode currentNode = collabNodeService.getById(nodeId);
                    //如果结束最后一轮的结束版本不等于当前节点版本，说明有一轮新的迭代正在进行中，但是CB_SYS_ROUND表中没有最新迭代轮的记录
                    //手动new一个迭代轮对象为最新迭代轮，这轮迭代的数据的开始版本和结束版本都是null
                    if (!Long.valueOf(currentNode.getVersion()).equals(collabRoundList.get(i).getEndVersion())) {
                        WheelIteration lastWheelIteration = new WheelIteration();
                        lastWheelIteration.setWheelNumber(i + 2);
                        results.add(lastWheelIteration);
                    }
                }
                CollabRound collabRound = collabRoundList.get(i);
                WheelIteration wheelIteration = new WheelIteration();
                wheelIteration.setWheelNumber(i + 1);
                wheelIteration.setStartVersion(collabRound.getStartVersion());
                wheelIteration.setEndVersion(collabRound.getEndVersion());
                results.add(wheelIteration);
            }
        } else { //第一轮迭代进行中，所以CB_SYS_ROUND表中没有记录，手动new一个迭代轮对象为第一轮
            WheelIteration wheelIteration = new WheelIteration();
            wheelIteration.setWheelNumber(1);
            results.add(wheelIteration);
        }
        retVal.setResults(results);
        retVal.setSuccess(true);
        return retVal;
    }

    private void dealCurrentWheelIteration(List<NodeVersionVO> result, CollabNode currentNode, Long lastSecondWheelEndVersion) {
        List<CollabHistoryNode> historyNodeList = currentNode.getHistoryNodeList()
                .stream()
                .filter(node -> node.getVersion() < currentNode.getVersion() && node.getVersion() > lastSecondWheelEndVersion)
                .sorted(Comparator.comparingInt(CollabHistoryNode::getVersion))
                .collect(Collectors.toList());
        if (historyNodeList.size() > 0) {
            convertHistoryNodeToNodeVersionVO(result, historyNodeList);
        }
        NodeVersionVO versionVO = new NodeVersionVO();
        versionVO.setNodeId(currentNode.getId());
        versionVO.setHistory(false);
        versionVO.setUpdateTime(currentNode.getUpdateTime());
        versionVO.setUpdateUser(currentNode.getUpdateUser());
        versionVO.setVersion(currentNode.getVersion());
        result.add(versionVO);
    }

    private void convertHistoryNodeToNodeVersionVO(List<NodeVersionVO> result, List<CollabHistoryNode> historyNodeList) {
        historyNodeList.forEach(collabHistoryNode -> {
            NodeVersionVO historyVersionVO = new NodeVersionVO();
            historyVersionVO.setNodeId(collabHistoryNode.getId());
            historyVersionVO.setHistory(true);
            historyVersionVO.setUpdateTime(collabHistoryNode.getUpdateTime());
            historyVersionVO.setUpdateUser(collabHistoryNode.getUpdateUser());
            historyVersionVO.setVersion(collabHistoryNode.getVersion());
            result.add(historyVersionVO);
        });
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    ICollabHistoryNodeService collabHistoryNodeService;

    @Autowired
    ICollabRoundService collabRoundService;

}
