package com.orient.devdataobj.controller;

import com.orient.collab.model.GanttPlanDependency;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.devdata.IDevDataMng;
import com.orient.collabdev.business.common.pedigree.PedigreeBusiness;
import com.orient.collabdev.business.designing.PlanRelationBusiness;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.model.PedigreeDTO;
import com.orient.collabdev.model.PedigreeNodeRelation;
import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.devdataobj.event.GetDataObjEvent;
import com.orient.devdataobj.event.param.GetDataObjParam;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.log.annotion.Action;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.ExtGridData;
import com.orient.web.springmvcsupport.DateEditor;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ?????????????????????
 * update by gny 2018/8/24
 *
 * @author mengbin
 * @create 2016-07-22 ??????3:22
 */
@Controller
@RequestMapping("/dataObj")
public class DataObjController extends BaseController {

    @Autowired
    DataObjectBusiness dataObjectBusiness;

    @Autowired
    IRoleUtil roleEngine;

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    PedigreeBusiness pedigreeBusiness;

    @Autowired
    PlanRelationBusiness planRelationBusiness;

    /**
     * ??????????????????????????????
     *
     * @param nodeId      ???????????????ID
     * @param nodeVersion ?????????????????????
     * @param node        ??????????????? :?????????root ??????????????????
     * @param isglobal    0:?????????,1: ?????????
     * @return
     */
    @RequestMapping("getDataObj")
    @ResponseBody
    @Action(ownermodel = "????????????-????????????", detail = "??????????????????")
    public ExtGridData<DataObjectBean> getDataObjects(String nodeId, Integer nodeVersion, String node, Integer isglobal) {
        ExtGridData<DataObjectBean> ret = new ExtGridData<>();
        List<DataObjectBean> dataObjectBeanList = getDataObjectBeanList(nodeId, nodeVersion, node, isglobal);
        ret.setResults(dataObjectBeanList);
        ret.setTotalProperty(dataObjectBeanList.size());
        ret.setSuccess(true);
        return ret;
    }

    private List<DataObjectBean> getDataObjectBeanList(String nodeId, Integer nodeVersion, String node, Integer isglobal) {
        List<DataObjectBean> retVal;
        if (nodeVersion == null) {
            CollabNode collabNode = collabNodeService.getById(nodeId);
            nodeVersion = collabNode.getVersion();
        }
        try {
            if (node.equals("root")) {
                GetDataObjParam eventParam = new GetDataObjParam();
                eventParam.nodeId = nodeId;
                eventParam.nodeVersion = nodeVersion;
                if (isglobal == 0) {
                    eventParam.globalFlag = 2;
                } else {
                    eventParam.globalFlag = 1;
                }
                eventParam.isOnlyRoot = true;
                OrientContextLoaderListener.Appwac.publishEvent(new GetDataObjEvent(this, eventParam));
                retVal = eventParam.retDataObjs;
            } else {
                DataObjectEntity parentDataObj = dataObjectBusiness.getDataObjEntityById(Integer.valueOf(node));
                retVal = dataObjectBusiness.dataChange(dataObjectBusiness.getChildrenDataObj(parentDataObj));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrientBaseAjaxException("", e.toString());
        }
        return retVal;
    }

    @RequestMapping("createDataObj")
    @ResponseBody
    @Action(ownermodel = "????????????-????????????", detail = "??????????????????")
    public AjaxResponseData createDataObj(@RequestBody Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion, Integer isglobal) {
        AjaxResponseData ret = new AjaxResponseData();
        CollabNode collabNode = collabNodeService.getById(nodeId);
        if (nodeVersion == null) {
            nodeVersion = collabNode.getVersion();
        }
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        IDevDataMng devDataMng = getDevDataMng(nodeId, nodeVersion);
        boolean bSuc = devDataMng.createDataObj(data, nodeId, nodeVersion);
        ret.setAlertMsg(true);
        ret.setSuccess(bSuc);
        ret.setMsg(bSuc ? "????????????" : "????????????");
        return ret;
    }

    @RequestMapping("updateDataObj")
    @ResponseBody
    @Action(ownermodel = "????????????-????????????", detail = "??????????????????")
    public AjaxResponseData updateDataObj(@RequestBody Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion, Integer isglobal) {
        AjaxResponseData ret = new AjaxResponseData();
        CollabNode collabNode = collabNodeService.getById(nodeId);
        if (nodeVersion == null) {
            nodeVersion = collabNode.getVersion();
        }
        IDevDataMng devDataMng = getDevDataMng(nodeId, nodeVersion);
        boolean bSuc = devDataMng.updateDataObj(data, nodeId, nodeVersion);
        ret.setAlertMsg(true);
        ret.setSuccess(bSuc);
        ret.setMsg(bSuc ? "????????????" : "????????????");
        return ret;
    }

    @RequestMapping("deleteDataObj")
    @ResponseBody
    @Action(ownermodel = "????????????-????????????", detail = "??????????????????")
    public AjaxResponseData deleteDataObj(@RequestBody Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion, Integer isglobal) {
        AjaxResponseData ret = new AjaxResponseData();
        CollabNode collabNode = collabNodeService.getById(nodeId);
        if (nodeVersion == null) {
            nodeVersion = collabNode.getVersion();
        }
        IDevDataMng devDataMng = getDevDataMng(nodeId, nodeVersion);
        boolean bSuc = devDataMng.deleteDataObj(data, nodeId, nodeVersion);
        ret.setAlertMsg(true);
        ret.setSuccess(bSuc);
        ret.setMsg(bSuc ? "????????????" : "????????????");
        return ret;
    }

    @RequestMapping("swapDataObjOrderNum")
    @ResponseBody
    @Action(ownermodel = "????????????-????????????", detail = "????????????")
    public AjaxResponseData swapDataObjOrderNum(@RequestBody Map<String, List<DataObjectBean>> data) {
        AjaxResponseData ret = new AjaxResponseData();
        List<DataObjectBean> dataObjs = data.get("data");
        List<DataObjectEntity> objs = new ArrayList<>();
        try {
            for (DataObjectBean dataObj : dataObjs) {
                DataObjectEntity modifiedObj = new DataObjectEntity();
                PropertyUtils.copyProperties(modifiedObj, dataObj);
                objs.add(modifiedObj);
            }
            dataObjectBusiness.swapOderNumber(objs.get(0), objs.get(1));
        } catch (Exception e) {
            e.printStackTrace();
            ret.setSuccess(false);
            ret.setMsg("????????????: [" + e.getMessage() + "]");
            return ret;
        }
        ret.setSuccess(true);
        return ret;
    }

    @RequestMapping("convertShareAbleDataObj")
    @ResponseBody
    @Action(ownermodel = "????????????-????????????", detail = "??????????????????????????????")
    public AjaxResponseData convertShareAbleDataObj(@RequestBody Map<String, List<DataObjectBean>> data, String isglobal) {
        AjaxResponseData ret = new AjaxResponseData();
        List<DataObjectBean> dataObjs = data.get("data");
        try {
            for (DataObjectBean dataObj : dataObjs) {
                DataObjectEntity topObj = dataObjectBusiness.getDataObjEntityById(dataObj.getId());
                topObj.setIsglobal(dataObj.getIsglobal());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.setSuccess(false);
            ret.setMsg("????????????: [" + e.getMessage() + "]");
            return ret;
        }
        ret.setSuccess(true);
        return ret;
    }

    /**
     * ?????????????????????detailed comment
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    private IDevDataMng getDevDataMng(String nodeId, Integer nodeVersion) {
        CollabDevNodeDTO projectNode = structureBusiness.getRootNode(nodeId, nodeVersion);
        IDevDataMng retVal = null;
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.collabdev.business.common.devdata.IDevDataMng.class);
        for (String beanName : beanNames) {
            IDevDataMng devDataMng = OrientContextLoaderListener.Appwac.getBean(beanName, IDevDataMng.class);
            Class operateClass = devDataMng.getClass();
            MngStatus classAnnotation = (MngStatus) operateClass.getAnnotation(MngStatus.class);
            ManagerStatusEnum annotationStatus = classAnnotation.status();
            if (annotationStatus == ManagerStatusEnum.fromString(projectNode.getStatus())) {
                retVal = devDataMng;
            }
        }
        return retVal;
    }

    /**
     * ??????????????????????????????
     * 1.??????????????????????????????PLAN_R????????????????????????????????????????????????????????????????????????
     * 2.??????????????????????????????CB_SYS_NODE_RELATION????????????????????????????????????????????????????????????????????????
     */
    @RequestMapping("getRefDataObj")
    @ResponseBody
    @Action(ownermodel = "????????????-????????????", detail = "???????????????????????????????????????")
    public ExtGridData<DataObjectBean> getRefDataObj(String nodeId, Integer nodeVersion, String nodeType, String node, Integer isglobal) {
        ExtGridData<DataObjectBean> ret = new ExtGridData<>();
        List<DataObjectBean> result = new ArrayList<>();
        CollabNode collabNode = collabNodeService.getById(nodeId);
        String bmDataId = collabNode.getBmDataId();
        if (nodeVersion == null) {
            nodeVersion = collabNode.getVersion();
        }
        if (CommonTools.isNullString(nodeType)) {
            nodeType = collabNode.getType();
        }
        switch (nodeType) {
            case CollabDevConstants.NODE_TYPE_TASK:
                //?????????????????????????????????????????????
                //1.?????????????????????????????????????????????????????????????????????
                //2.??????DestDevNodeId???????????????id,??????SrcDevNodeId???????????????????????????
                //3.??????SrcDevNodeId???????????????
                List<String> upTaskNodeIdList = new ArrayList<>();
                CollabDevNodeDTO planNode = structureBusiness.getParentNode(nodeId, nodeVersion);
                PedigreeDTO pedigree = pedigreeBusiness.getPedigree(planNode.getId(), planNode.getVersion(), true, "children");
                List<PedigreeNodeRelation> relations = pedigree.getRelations();
                if (relations.size() > 0) {
                    relations.forEach(relation -> {
                        if (relation.getDestDevNodeId().equals(nodeId)) {
                            upTaskNodeIdList.add(relation.getSrcDevNodeId());
                        }
                    });
                    upTaskNodeIdList.forEach(upTaskNodeId -> {
                        CollabNode taskCollabNode = collabNodeService.getById(upTaskNodeId);
                        List<DataObjectBean> dataObjectBeanList = getDataObjectBeanList(taskCollabNode.getId(), taskCollabNode.getVersion(), node, isglobal);
                        dataObjectBeanList.forEach(dataObjectBean -> dataObjectBean.setUpName(taskCollabNode.getName()));
                        result.addAll(dataObjectBeanList);
                    });
                }
                break;
            case CollabDevConstants.NODE_TYPE_PLAN:
                //?????????????????????????????????????????????
                //1.?????????????????????????????????
                //2.??????PLAN_R_240???????????????????????????????????????????????????
                //3.???????????????????????????FinishPlanId???????????????bmDataId,??????StartPlanId???????????????????????????
                //4.??????StartPlanId???????????????
                CollabDevNodeDTO projectNode = structureBusiness.getParentNode(nodeId, nodeVersion);
                List<GanttPlanDependency> planRelations = planRelationBusiness.getPlanRelations(projectNode.getBmDataId(), projectNode.getId(), projectNode.getVersion());
                List<String> upPlanIdList = new ArrayList<>();
                if (!CommonTools.isEmptyList(planRelations)) {
                    planRelations.forEach(planRelation -> {
                        if (planRelation.getFinishPlanId().equals(bmDataId)) {
                            upPlanIdList.add(planRelation.getStartPlanId());
                        }
                    });
                    upPlanIdList.forEach(upPlanId -> {
                        CollabNode planCollabNode = structureBusiness.collabNodeService.list(Restrictions.eq("bmDataId", upPlanId), Restrictions.eq("type", CollabDevConstants.NODE_TYPE_PLAN)).get(0);
                        List<DataObjectBean> dataObjectBeanList = getDataObjectBeanList(planCollabNode.getId(), planCollabNode.getVersion(), node, isglobal);
                        dataObjectBeanList.forEach(dataObjectBean -> dataObjectBean.setUpName(planCollabNode.getName()));
                        result.addAll(dataObjectBeanList);
                    });
                }
                break;
        }
        ret.setResults(result);
        ret.setTotalProperty(result.size());
        ret.setSuccess(true);
        return ret;
    }

}
