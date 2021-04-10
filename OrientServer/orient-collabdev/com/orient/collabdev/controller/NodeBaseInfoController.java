package com.orient.collabdev.controller;

import com.orient.collabdev.model.BaseNodeInfo;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import com.orient.sysmodel.service.collabdev.ICollabNodeDevStatusService;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseController;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static com.orient.collabdev.constant.CollabDevConstants.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/10/23 9:34
 * @Version 1.0
 **/
@Controller
@RequestMapping("/nodeBaseInfo")
public class NodeBaseInfoController extends BaseController {

    @RequestMapping("")
    @ResponseBody
    public ExtGridData<BaseNodeInfo> get(String node, String type) {
        ExtGridData<BaseNodeInfo> retVal = new ExtGridData<>();
        if (node.equals("-1")) {
            List<BaseNodeInfo> projects = new ArrayList<>();
            List<CollabNode> collabNodes = collabNodeService.list(Restrictions.eq("type", NODE_TYPE_PRJ));
            for (CollabNode collabNode : collabNodes) {
                BaseNodeInfo baseNodeInfo = new BaseNodeInfo();
                baseNodeInfo.setProjectName(collabNode.getName());
                baseNodeInfo.setVersion(collabNode.getVersion());
                baseNodeInfo.setName(collabNode.getName());
                baseNodeInfo.setStatus(STATUS_MAPPING.get(collabNode.getStatus()));
                baseNodeInfo.setType(collabNode.getType());
                baseNodeInfo.setLeaf(false);
                baseNodeInfo.setId(collabNode.getId());
                baseNodeInfo.setIconCls("icon-collabDev-project");
                projects.add(baseNodeInfo);
            }
            retVal.setResults(projects);
        } else {
            switch (type) {
                case NODE_TYPE_PRJ:
                    List<BaseNodeInfo> plans = new ArrayList<>();
                    List<CollabNode> planNodeList = collabNodeService.list(new Criterion[]{Restrictions.eq("pid", node), Restrictions.eq("type", NODE_TYPE_PLAN)}, Order.asc("nodeOrder"));
                    if (!CommonTools.isEmptyList(planNodeList)) {
                        for (CollabNode collabNode : planNodeList) {
                            BaseNodeInfo baseNodeInfo = new BaseNodeInfo();
                            baseNodeInfo.setVersion(collabNode.getVersion());
                            baseNodeInfo.setName(collabNode.getName());
                            baseNodeInfo.setStatus(STATUS_MAPPING.get(collabNode.getStatus()));
                            baseNodeInfo.setType(collabNode.getType());
                            baseNodeInfo.setLeaf(false);
                            baseNodeInfo.setId(collabNode.getId());
                            baseNodeInfo.setIconCls("icon-collabDev-plan");
                            plans.add(baseNodeInfo);
                        }
                    }
                    retVal.setResults(plans);
                    break;
                case NODE_TYPE_PLAN:
                    List<BaseNodeInfo> tasks = new ArrayList<>();
                    List<CollabNode> taskNodeList = collabNodeService.list(new Criterion[]{Restrictions.eq("pid", node), Restrictions.or(Restrictions.eq("type", NODE_TYPE_PLAN), Restrictions.eq("type", NODE_TYPE_TASK))}, Order.asc("nodeOrder"));
                    if (!CommonTools.isEmptyList(taskNodeList)) {
                        for (CollabNode collabNode : taskNodeList) {
                            BaseNodeInfo baseNodeInfo = new BaseNodeInfo();
                            if (collabNode.getType().equals(NODE_TYPE_PLAN)) {
                                baseNodeInfo.setVersion(collabNode.getVersion());
                                baseNodeInfo.setName(collabNode.getName());
                                baseNodeInfo.setStatus(STATUS_MAPPING.get(collabNode.getStatus()));
                                baseNodeInfo.setType(collabNode.getType());
                                baseNodeInfo.setLeaf(false);
                                baseNodeInfo.setId(collabNode.getId());
                                baseNodeInfo.setIconCls("icon-collabDev-plan");
                            } else {
                                baseNodeInfo.setVersion(collabNode.getVersion());
                                baseNodeInfo.setName(collabNode.getName());
                                baseNodeInfo.setStatus(STATUS_MAPPING.get(collabNode.getStatus()));
                                baseNodeInfo.setType(collabNode.getType());
                                baseNodeInfo.setLeaf(true);
                                baseNodeInfo.setId(collabNode.getId());
                                baseNodeInfo.setIconCls("icon-collabDev-task");
                                List<CollabNodeDevStatus> collabNodeDevStatuses = collabNodeDevStatusService.list(Restrictions.eq("nodeId", collabNode.getId()), Restrictions.eq("nodeVersion", Long.valueOf(collabNode.getVersion())));
                                if (!CommonTools.isEmptyList(collabNodeDevStatuses)) {
                                    baseNodeInfo.setTechStatus(collabNodeDevStatuses.get(0).getTechStatus());
                                }
                            }
                            tasks.add(baseNodeInfo);
                        }
                    }
                    retVal.setResults(tasks);
                    break;
                default:
                    break;
            }
        }
        return retVal;
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    ICollabNodeDevStatusService collabNodeDevStatusService;

}
