package com.orient.sysmodel.domain.collabdev.datamgrrealtion;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 11:02
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SUB_DATA_DETAIL")
public class CollabSubDataDetail {

    private Long id;
    private String cbSubDataModelBindId;
    private String dataId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SUB_DATA_DETAIL")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CB_SUB_DATA_MODEL_BIND_ID")
    public String getCbSubDataModelBindId() {
        return cbSubDataModelBindId;
    }

    public void setCbSubDataModelBindId(String cbSubDataModelBindId) {
        this.cbSubDataModelBindId = cbSubDataModelBindId;
    }

    @Basic
    @Column(name = "DATA_ID")
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

}
