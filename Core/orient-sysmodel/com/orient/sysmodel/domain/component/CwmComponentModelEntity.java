package com.orient.sysmodel.domain.component;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
@Entity
@Table(name = "CWM_COMPONENT_MODEL")
public class CwmComponentModelEntity {

    private Long id;
    private String nodeId;
    private CwmComponentEntity belongComponent;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_COMPONENT_MODEL")})
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPONENT_ID")
    public CwmComponentEntity getBelongComponent() {
        return belongComponent;
    }

    public void setBelongComponent(CwmComponentEntity belongComponent) {
        this.belongComponent = belongComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmComponentModelEntity that = (CwmComponentModelEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nodeId != null ? nodeId.hashCode() : 0);
        return result;
    }
}
