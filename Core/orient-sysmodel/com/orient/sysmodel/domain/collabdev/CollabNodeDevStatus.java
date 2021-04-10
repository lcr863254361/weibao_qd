package com.orient.sysmodel.domain.collabdev;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 协同-节点开发状态表
 * @Author GNY
 * @Date 2018/7/27 8:51
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SYS_NODE_DEVSTATUS")
public class CollabNodeDevStatus {

    private String id;
    private String nodeId;
    private Long nodeVersion;
    private String techStatus;
    private String approvalStatus;
    private String pid;
    private Long pversion;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_NODE_DEVSTATUS")})
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
    @Column(name = "NODE_VERSION")
    public Long getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(Long nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    @Basic
    @Column(name = "TECH_STATUS")
    public String getTechStatus() {
        return techStatus;
    }

    public void setTechStatus(String techStatus) {
        this.techStatus = techStatus;
    }

    @Basic
    @Column(name = "APPROVAL_STATUS")
    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Basic
    @Column(name = "P_ID")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "P_VERSION")
    public Long getPversion() {
        return pversion;
    }

    public void setPversion(Long pversion) {
        this.pversion = pversion;
    }

}
