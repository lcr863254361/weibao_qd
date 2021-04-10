package com.orient.sysmodel.domain.collabdev;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 抽象中介-节点表
 * @Author GNY
 * @Date 2018/7/26 20:21
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SYS_NODE")
public class CollabNode {

    private String id;
    private String name;
    private Integer nodeOrder;
    private Integer version;
    private String type;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private String status;
    private Integer isRoot;
    private String bmDataId;
    private String pid;

    private Integer lastVersion;

    private List<CollabHistoryNode> historyNodeList = new ArrayList<>();

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_NODE")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "NODEORDER")
    public Integer getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(Integer nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    @Basic
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.lastVersion = this.version;
        this.version = version;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "UPDATE_USER")
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Basic
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "IS_ROOT")
    public Integer getIsRoot() {
        return isRoot;
    }


    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    @Basic
    @Column(name = "BM_DATA_ID")
    public String getBmDataId() {
        return bmDataId;
    }

    public void setBmDataId(String businessModelId) {
        this.bmDataId = businessModelId;
    }

    @Basic
    @Column(name = "PID")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "belongNode")
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) desc")
    public List<CollabHistoryNode> getHistoryNodeList() {
        return historyNodeList;
    }

    public void setHistoryNodeList(List<CollabHistoryNode> historyNodeList) {
        this.historyNodeList = historyNodeList;
    }

    @Transient
    public Integer getLastVersion() {
        return lastVersion;
    }
}
