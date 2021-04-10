package com.orient.sysmodel.domain.collabdev;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 运维监控-版本变化细节
 * @Author GNY
 * @Date 2018/7/26 19:22
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SYS_VERSION_DETAIL")
public class CollabVersionDetail {

    private String id;
    private String nodeId;
    private String nodeName;
    private String action;
    private String nodeVersion;
    private String nodeType;
    private String nodePath;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_VERSION_DETAIL")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NODE_ID")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Basic
    @Column(name = "NODE_NAME")
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Basic
    @Column(name = "ACTION")
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Basic
    @Column(name = "NODE_VERSION")
    public String getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    @Basic
    @Column(name = "NODE_TYPE")
    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    @Basic
    @Column(name = "NODE_PATH")
    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

}
