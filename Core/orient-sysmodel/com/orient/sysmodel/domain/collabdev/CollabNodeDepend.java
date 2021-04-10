package com.orient.sysmodel.domain.collabdev;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by karry on 18-8-31.
 */
@Entity
@Table(name = "CB_SYS_NODE_DEPEND")
public class CollabNodeDepend {
    private String id;
    private String nodeId;
    private Integer nodeVersion;
    private String dependId;
    private Integer dependVersion;
    private String pId;
    private Integer pVersion;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_NODE_DEPEND")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NODE_ID", nullable = true, insertable = true, updatable = true, length = 38)
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Basic
    @Column(name = "NODE_VERSION", nullable = true, insertable = true, updatable = true, precision = -127)
    public Integer getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(Integer nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    @Basic
    @Column(name = "DEPEND_ID", nullable = true, insertable = true, updatable = true, length = 38)
    public String getDependId() {
        return dependId;
    }

    public void setDependId(String dependId) {
        this.dependId = dependId;
    }

    @Basic
    @Column(name = "DEPEND_VERSION", nullable = true, insertable = true, updatable = true, precision = -127)
    public Integer getDependVersion() {
        return dependVersion;
    }

    public void setDependVersion(Integer dependVersion) {
        this.dependVersion = dependVersion;
    }

    @Basic
    @Column(name = "P_ID", nullable = true, insertable = true, updatable = true, length = 38)
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Basic
    @Column(name = "P_VERSION", nullable = true, insertable = true, updatable = true, precision = -127)
    public Integer getpVersion() {
        return pVersion;
    }

    public void setpVersion(Integer pVersion) {
        this.pVersion = pVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollabNodeDepend that = (CollabNodeDepend) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;
        if (nodeVersion != null ? !nodeVersion.equals(that.nodeVersion) : that.nodeVersion != null) return false;
        if (dependId != null ? !dependId.equals(that.dependId) : that.dependId != null) return false;
        if (dependVersion != null ? !dependVersion.equals(that.dependVersion) : that.dependVersion != null)
            return false;
        if (pId != null ? !pId.equals(that.pId) : that.pId != null) return false;
        if (pVersion != null ? !pVersion.equals(that.pVersion) : that.pVersion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nodeId != null ? nodeId.hashCode() : 0);
        result = 31 * result + (nodeVersion != null ? nodeVersion.hashCode() : 0);
        result = 31 * result + (dependId != null ? dependId.hashCode() : 0);
        result = 31 * result + (dependVersion != null ? dependVersion.hashCode() : 0);
        result = 31 * result + (pId != null ? pId.hashCode() : 0);
        result = 31 * result + (pVersion != null ? pVersion.hashCode() : 0);
        return result;
    }
}
