package com.orient.modeldata.controller;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.bean.SchemaNodeVO;
import com.orient.modeldata.bean.SchemaWrapper;
import com.orient.modeldata.business.ModelTreeBusiness;
import com.orient.modeldata.event.GetTbomNodesEvent;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.model.BaseNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * TBom控制层
 *
 * @author enjoy
 * @creare 2016-05-17 8:36
 */
@Controller
@RequestMapping("/modelTree")
public class ModelTreeController {


    @Autowired
    ModelTreeBusiness modelTreeBusiness;

    /**
     * 获取所绑定功能点的当前用户所拥有的schema集合
     *
     * @param belongFunctionId 功能点ID
     * @return
     */
    @RequestMapping("getSchemaCombobox")
    @ResponseBody
    public ExtComboboxResponseData<SchemaWrapper> getSchemaCombobox(Long belongFunctionId) {
        List<SchemaWrapper> userSchemaList = modelTreeBusiness.getUserSchemaList(belongFunctionId);
        ExtComboboxResponseData retVal = new ExtComboboxResponseData();
        retVal.setResults(userSchemaList);
        retVal.setTotalProperty(userSchemaList.size());
        return retVal;
    }


    /**
     * 获取tbom信息
     *
     * @param schemaId         所属schemaId
     * @param belongFunctionId 所属功能点ID
     * @param nodeAttr         父节点属性
     * @return
     */
    @RequestMapping("getNodesByPId")
    @ResponseBody
    public List<BaseNode> getNodesByPId(String schemaId, String node, Long belongFunctionId, String nodeAttr) {

        GetTbomNodesEventParam getTbomNodesEventParam = new GetTbomNodesEventParam(schemaId, belongFunctionId, node, nodeAttr);
        GetTbomNodesEvent getTbomNodesEvent = new GetTbomNodesEvent(this, getTbomNodesEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getTbomNodesEvent);
        return getTbomNodesEventParam.getTbomNodes();
    }

    /**
     * 获取tbom信息
     *
     * @param schemaId         所属schemaId
     * @return
     */
    @RequestMapping("getNodesBySchemaId")
    @ResponseBody
    public List<SchemaNodeVO> getNodesBySchemaId(String schemaId, String node) {

        return modelTreeBusiness.getNodesBySchemaId(schemaId,node);
    }


}

