package com.orient.sysmodel.domain.collabdev;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 协同-节点关系表
 * @Author GNY
 * @Date 2018/7/27 9:01
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SYS_NODE_RELATION_HIS")
public class CollabNodeHisRelation {

    private String id;
    private String srcDevNodeId;
    private String destDevNodeId;
    private String pid;
    private Integer pversion;
    private String rid;
    private Integer rversion;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_NODE_RELATION_HIS")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SRC_DEVNODE_ID")
    public String getSrcDevNodeId() {
        return srcDevNodeId;
    }

    public void setSrcDevNodeId(String srcDevNodeId) {
        this.srcDevNodeId = srcDevNodeId;
    }

    @Basic
    @Column(name = "DEST_DEVNODE_ID")
    public String getDestDevNodeId() {
        return destDevNodeId;
    }

    public void setDestDevNodeId(String destDevNodeId) {
        this.destDevNodeId = destDevNodeId;
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
    public Integer getPversion() {
        return pversion;
    }

    public void setPversion(Integer pversion) {
        this.pversion = pversion;
    }

    @Basic
    @Column(name = "R_ID")
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
    @Basic
    @Column(name = "R_VERSION")
    public Integer getRversion() {
        return rversion;
    }

    public void setRversion(Integer rversion) {
        this.rversion = rversion;
    }

}
