package com.orient.collabdev.controller;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.designing.DesigningStructureBusiness;
import com.orient.collabdev.constant.PurposeHelper;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.model.CollabDevNodeVO;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.DeleteModelDataEvent;
import com.orient.modeldata.event.GetGridModelDescEvent;
import com.orient.modeldata.eventParam.DeleteModelDataEventParam;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-27 9:03 AM
 */
@Controller
@RequestMapping("/collabNavigation")
public class NavigationController extends BaseController {

    @Autowired
    DesigningStructureBusiness designingStructureBusiness;

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @RequestMapping("structure")
    @ResponseBody
    public AjaxResponseData<List<CollabDevNodeVO>> getDesigningTreeStruct(String node, String purpose) {
        List<CollabDevNodeDTO> collabDevNodeDTOS = designingStructureBusiness.getStructureByParent(node);
        List<CollabDevNodeVO> vos = new ArrayList<>();
        collabDevNodeDTOS.forEach(collabDevNodeDTO -> vos.add(CollabDevNodeVO.buildFromDTO(collabDevNodeDTO, PurposeHelper.fromString(purpose))));
        //排序
        vos.sort(Comparator.comparingInt(CollabDevNodeVO::getNodeOrder));
        AjaxResponseData<List<CollabDevNodeVO>> responseData = new AjaxResponseData<>(vos);
        return responseData;
    }

    @RequestMapping("specialStructure")
    @ResponseBody
    public AjaxResponseData<List<CollabDevNodeVO>> getDesigningTreeStructFromSpecialNode(String node, String purpose, String startNodeId, Integer startNodeVersion) {
        List<CollabDevNodeDTO> collabDevNodeDTOS = designingStructureBusiness.getStructureFromSpecialNode(node, startNodeId, startNodeVersion);
        List<CollabDevNodeVO> vos = new ArrayList<>();
        PurposeHelper purposeHelper = PurposeHelper.fromString(purpose);
        collabDevNodeDTOS.forEach(collabDevNodeDTO -> vos.add(CollabDevNodeVO.buildFromDTO(collabDevNodeDTO, purposeHelper)));
        //remove special nodes
        purposeHelper.handleNode(vos);
        //排序
        vos.sort(Comparator.comparingInt(CollabDevNodeVO::getNodeOrder));
        AjaxResponseData<List<CollabDevNodeVO>> responseData = new AjaxResponseData<>(vos);
        return responseData;
    }

    /**
     * 获取研发模型描述
     *
     * @param modelName
     * @return
     */
    @RequestMapping("getGridModelDesc")
    @ResponseBody
    public AjaxResponseData<GetModelDescEventParam> getGridModelDesc(String modelName, String templateId, String isView) {
        IBusinessModel collabDevBM = designingStructureBusiness.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
        GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(collabDevBM.getId(), templateId, isView);
        GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getModelDescEvent);
        return new AjaxResponseData<>(getModelDescEventParam);
    }

    /**
     * get collab develop model description and model data
     *
     * @param modelName
     * @param dataId
     * @return
     */
    @RequestMapping("getGridModelDescAndData")
    @ResponseBody
    public AjaxResponseData<Map<String, Object>> getGridModelDescAndData(String modelName, String dataId, String templateId, String isView) {
        IBusinessModel collabDevBM = designingStructureBusiness.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
        String modelId = collabDevBM.getId();
        Map<String, Object> retVal = new HashMap<>();
        //获取模型描述
        GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(modelId, templateId, isView);
        GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getModelDescEvent);
        //获取模型数据
        Map dataMap = modelDataBusiness.getModelDataByModelIdAndDataId(modelId, dataId);
        retVal.put("orientModelDesc", getModelDescEventParam.getOrientModelDesc());
        retVal.put("modelData", dataMap);
        return new AjaxResponseData<>(retVal);
    }

    @RequestMapping("deleteNode")
    @ResponseBody
    public CommonResponseData delete(String modelName, Long[] toDelIds, String isCascade) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel collabDevBM = designingStructureBusiness.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
        String modelId = collabDevBM.getId();
        DeleteModelDataEventParam deleteModelDataEventParam = new DeleteModelDataEventParam(modelId, toDelIds, isCascade);
        OrientContextLoaderListener.Appwac.publishEvent(new DeleteModelDataEvent(ModelDataController.class, deleteModelDataEventParam));
        retVal.setMsg("删除成功");
        return retVal;
    }
}
