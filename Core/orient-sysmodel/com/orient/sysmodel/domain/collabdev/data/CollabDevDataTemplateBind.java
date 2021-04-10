package com.orient.sysmodel.domain.collabdev.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 11:43
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_DEVDATA_TEMPLATE_BIND")
public class CollabDevDataTemplateBind {

    private String id;
    private String nodeId;
    private String platform;
    private String displayName;
    private String primaryKey;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_DEVDATA_TEMPLATE_BIND")})
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
    @Column(name = "PLATFORM")
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Basic
    @Column(name = "DISPLAY_NAME")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "PRIMARY_KEY")
    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

}
