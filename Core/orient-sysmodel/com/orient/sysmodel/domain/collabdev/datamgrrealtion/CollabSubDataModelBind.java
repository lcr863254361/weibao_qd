package com.orient.sysmodel.domain.collabdev.datamgrrealtion;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 10:55
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SUB_DATA_MODEL_BIND")
public class CollabSubDataModelBind {

    private Long id;
    private String  cbSubDataTypeId;
    private String modelId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SUB_DATA_MODEL_BIND")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CB_SUB_DATA_TYPE_ID")
    public String getCbSubDataTypeId() {
        return cbSubDataTypeId;
    }

    public void setCbSubDataTypeId(String cbSubDataTypeId) {
        this.cbSubDataTypeId = cbSubDataTypeId;
    }

    @Basic
    @Column(name = "MODEL_ID")
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
