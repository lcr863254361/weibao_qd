package com.orient.sysmodel.domain.collabdev.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 交付物绑定表
 * @Author GNY
 * @Date 2018/7/27 10:26
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_RESULT_BIND")
public class CollabResultBind {

    private String id;
    private String nodeId;
    private Integer hasDevData;  //1.绑定，0.未绑定
    private Integer hasModelData;
    private Integer hasPVMData;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_RESULT_BIND")})
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
    @Column(name = "HAS_DEVDATA")
    public Integer getHasDevData() {
        return hasDevData;
    }

    public void setHasDevData(Integer hasDevData) {
        this.hasDevData = hasDevData;
    }

    @Basic
    @Column(name = "HAS_MODELDATA")
    public Integer getHasModelData() {
        return hasModelData;
    }

    public void setHasModelData(Integer hasModelData) {
        this.hasModelData = hasModelData;
    }

    @Basic
    @Column(name = "HAS_PVMDATA")
    public Integer getHasPVMData() {
        return hasPVMData;
    }

    public void setHasPVMData(Integer hasPVMData) {
        this.hasPVMData = hasPVMData;
    }

}
