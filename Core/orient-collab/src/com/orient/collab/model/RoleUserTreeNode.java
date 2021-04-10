package com.orient.collab.model;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-09 下午8:43
 */
public class RoleUserTreeNode {

    private String id;
    private String roleId;
    private String roleName;
    private String userName;
    private String userId;
    private String deptName;
    private boolean isLeaf;
    private String iconCls = "icon-GROUP"; //默认是用户组图标
    private List<RoleUserTreeNode> children;
    private boolean defaultRole;

    public String getIconCls(){return iconCls;}

    public void setIconCls(String iconCls) {this.iconCls = iconCls;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<RoleUserTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<RoleUserTreeNode> children) {
        this.children = children;
    }

    public boolean isDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(boolean defaultRole) {
        this.defaultRole = defaultRole;
    }
}
