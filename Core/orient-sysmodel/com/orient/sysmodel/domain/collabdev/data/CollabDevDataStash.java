package com.orient.sysmodel.domain.collabdev.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 11:40
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_DEVDATA_STASH")
public class CollabDevDataStash {

    private String id;
    private String nodeId;
    private Long nodeVersion;
    private String dtoId;
    private String dtoVersion;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_DEVDATA_STASH")})
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
    @Column(name = "DTO_ID")
    public String getDtoId() {
        return dtoId;
    }

    public void setDtoId(String dtoId) {
        this.dtoId = dtoId;
    }

    @Basic
    @Column(name = "DTO_VERSION")
    public String getDtoVersion() {
        return dtoVersion;
    }

    public void setDtoVersion(String dtoVersion) {
        this.dtoVersion = dtoVersion;
    }
}
