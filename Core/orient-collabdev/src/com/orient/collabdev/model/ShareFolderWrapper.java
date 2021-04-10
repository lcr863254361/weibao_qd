package com.orient.collabdev.model;

import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFolder;

import java.io.Serializable;

/**
 * 共享文件夹包装类
 * 增加树形节点相关属性
 *
 * @author ZhangSheng
 * @creare 2018-08-22 11:22
 */
public class ShareFolderWrapper extends CollabShareFolder implements Serializable {

    private Boolean leaf = false;

    //树节点默认为不展开
    private Boolean expanded = false;

    private String parentId;

    private String iconCls = "icon-fileGroup";


    public ShareFolderWrapper() {
    }

    public ShareFolderWrapper(CollabShareFolder collabShareFolder) {
        this.setId(collabShareFolder.getId());
        this.setName(collabShareFolder.getName());
        this.setFolderOrder(collabShareFolder.getFolderOrder());
        this.setNodeId(collabShareFolder.getNodeId());
        this.setPid(collabShareFolder.getPid());
        this.setParentId(collabShareFolder.getPid());
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public String getIconCls() {
        return iconCls;
    }
}
