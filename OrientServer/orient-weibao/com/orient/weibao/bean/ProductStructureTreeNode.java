package com.orient.weibao.bean;

import com.orient.utils.UtilFactory;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-01-12 16:11
 */
public class ProductStructureTreeNode {

    private String id;
    private String dataId;
    private String text;
    private boolean expanded=false;
    private boolean leaf=false;
    private String iconCls;
    private String icon;
    private String order;
    private String type;
    private String qtip;
    private int level;
    private String pId;
    private String modelName;
    private String modelId;
    private List<ProductStructureTreeNode> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQtip() {
        return qtip;
    }

    public void setQtip(String qtip) {
        this.qtip = qtip;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<ProductStructureTreeNode> getResults() {
        return results;
    }

    public void setResults(List<ProductStructureTreeNode> results) {
        this.results = results;
    }
}

