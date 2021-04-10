package com.orient.collabdev.model;

import com.orient.collabdev.constant.NodeTypeEnum;
import com.orient.collabdev.constant.PurposeHelper;
import com.orient.utils.BeanUtils;

import java.io.Serializable;

/**
 * node view object
 *
 * @author panduanduan
 * @create 2018-07-27 9:04 AM
 */
public class CollabDevNodeVO implements Serializable {

    private String id;

    private String name;

    private String type;

    private Integer version;

    private Integer nodeOrder;

    private String status;

    private Integer isRoot;

    private Boolean isHistory = false;

    private String bmDataId;

    //tree
    private String iconCls;

    private boolean leaf = false;

    private Boolean expanded = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(Integer nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getHistory() {
        return isHistory;
    }

    public void setHistory(Boolean history) {
        isHistory = history;
    }

    public String getBmDataId() {
        return bmDataId;
    }

    public void setBmDataId(String bmDataId) {
        this.bmDataId = bmDataId;
    }

    public static CollabDevNodeVO buildFromDTO(CollabDevNodeDTO collabDevNodeDTO, PurposeHelper purpose) {
        CollabDevNodeVO retVal = new CollabDevNodeVO();
        BeanUtils.copyProperties(retVal, collabDevNodeDTO);
        //configure tree attributes
        String nodeType = retVal.getType();
        NodeTypeEnum nodeTypeEnum = NodeTypeEnum.fromString(nodeType);
        if (null != nodeType) {
            nodeTypeEnum.setIconCls(retVal);
            nodeTypeEnum.setIsLeaf(retVal, purpose);
            nodeTypeEnum.setExpand(retVal, purpose);
        }
        return retVal;
    }
}
