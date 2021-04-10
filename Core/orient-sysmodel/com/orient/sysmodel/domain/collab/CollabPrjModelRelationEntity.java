package com.orient.sysmodel.domain.collab;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-10-19 17:18
 */
@Entity
@Table(name = "COLLAB_PRJ_MODEL_RELATION")
public class CollabPrjModelRelationEntity {
    private Long id;
    private String modelId;
    private String dataId;
    private String prjId;
    private String prjModelId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_COLLAB_PRJ_MODEL_RELATION")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MODEL_ID")
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "DATA_ID")
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Basic
    @Column(name = "PRJ_ID")
    public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    @Basic
    @Column(name = "PRJ_MODEL_ID")
    public String getPrjModelId() {
        return prjModelId;
    }

    public void setPrjModelId(String prjModelId) {
        this.prjModelId = prjModelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollabPrjModelRelationEntity that = (CollabPrjModelRelationEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (dataId != null ? !dataId.equals(that.dataId) : that.dataId != null) return false;
        if (prjId != null ? !prjId.equals(that.prjId) : that.prjId != null) return false;
        if (prjModelId != null ? !prjModelId.equals(that.prjModelId) : that.prjModelId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (dataId != null ? dataId.hashCode() : 0);
        result = 31 * result + (prjId != null ? prjId.hashCode() : 0);
        result = 31 * result + (prjModelId != null ? prjModelId.hashCode() : 0);
        return result;
    }
}
