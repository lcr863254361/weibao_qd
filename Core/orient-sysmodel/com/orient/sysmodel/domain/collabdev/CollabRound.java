package com.orient.sysmodel.domain.collabdev;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 协同-一轮迭代表
 * @Author GNY
 * @Date 2018/7/27 9:08
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SYS_ROUND")
public class CollabRound {

    private Long id;
    private String nodeId;
    private Long startVersion;
    private Long endVersion;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_ROUND")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    @Column(name = "START_VERSION")
    public Long getStartVersion() {
        return startVersion;
    }

    public void setStartVersion(Long startVersion) {
        this.startVersion = startVersion;
    }

    @Basic
    @Column(name = "END_VERSION")
    public Long getEndVersion() {
        return endVersion;
    }

    public void setEndVersion(Long endVersion) {
        this.endVersion = endVersion;
    }

}
