package com.orient.modeldata.bean;

import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.web.model.BaseNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tbom节点描述
 *
 * @author enjoy
 * @creare 2016-05-17 9:44
 */
public class TBomNode extends BaseNode implements Serializable {

    //节点类型
    public static String STATIC_NODE = "static_node";

    public static String DYNAMIC_NODE = "Dynamic_node";

    String tbomId;

    String modelType;

    int level = 1;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    String modelId;

    List<String> idList = new ArrayList<>();

    //add by DuanDuanPan keep original customer filters
    List<CustomerFilter> originalCustomerFilters = new ArrayList<>();

    String nodeType;

    List<TBomNode> childNodes = new ArrayList<>();

    String url;

    TBomNode parentNode;

    String staticDbId;

    public TBomNode() {
    }

    //中间数据展现区域 模型描述
    private List<TBomModel> tBomModels = new ArrayList<>();

    public String getStaticDbId() {
        return staticDbId;
    }

    public void setStaticDbId(String staticDbId) {
        this.staticDbId = staticDbId;
    }

    public TBomNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TBomNode parentNode) {
        this.parentNode = parentNode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TBomNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<TBomNode> childNodes) {
        this.childNodes = childNodes;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getTbomId() {
        return tbomId;
    }

    public void setTbomId(String tbomId) {
        this.tbomId = tbomId;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public List<TBomModel> gettBomModels() {
        return tBomModels;
    }

    public void settBomModels(List<TBomModel> tBomModels) {
        this.tBomModels = tBomModels;
    }

    public List<CustomerFilter> getOriginalCustomerFilters() {
        return originalCustomerFilters;
    }

    public void setOriginalCustomerFilters(List<CustomerFilter> originalCustomerFilters) {
        this.originalCustomerFilters = originalCustomerFilters;
    }
}

