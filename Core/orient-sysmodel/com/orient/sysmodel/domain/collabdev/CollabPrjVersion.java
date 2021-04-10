package com.orient.sysmodel.domain.collabdev;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-14 4:11 PM
 */
@Entity
@Table(name = "CB_SYS_PRJ_VERSION")
public class CollabPrjVersion {
    private Integer id;
    private Integer prjVersion;
    private Integer nodeId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_PRJ_VERSION")})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PRJ_VERSION")
    public Integer getPrjVersion() {
        return prjVersion;
    }

    public void setPrjVersion(Integer prjVersion) {
        this.prjVersion = prjVersion;
    }

    @Basic
    @Column(name = "NODE_ID")
    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollabPrjVersion that = (CollabPrjVersion) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(prjVersion, that.prjVersion);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, prjVersion);
    }
}
