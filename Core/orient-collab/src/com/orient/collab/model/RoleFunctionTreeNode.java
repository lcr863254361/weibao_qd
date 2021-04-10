package com.orient.collab.model;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * the role function tree node
 *
 * @author Seraph
 * 2016-07-09 下午4:19
 */
public class RoleFunctionTreeNode {

    private String id;
    private String text;
    private String pid;
    private boolean checked;
    private boolean expanded;
    private boolean leaf;
    private String iconCls;
    private String stage;
    private List<RoleFunctionTreeNode> results = UtilFactory.newArrayList();

    public RoleFunctionTreeNode() {
    }

    public RoleFunctionTreeNode(String id, String text, boolean expanded, boolean leaf, String iconCls) {
        this.id = id;
        this.text = text;
        this.expanded = expanded;
        this.leaf = leaf;
        this.iconCls = iconCls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<RoleFunctionTreeNode> getResults() {
        return results;
    }

    public void setResults(List<RoleFunctionTreeNode> results) {
        this.results = results;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getIconCls() {
        return iconCls;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
