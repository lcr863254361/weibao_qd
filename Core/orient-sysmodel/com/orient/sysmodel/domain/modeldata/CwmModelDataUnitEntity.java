package com.orient.sysmodel.domain.modeldata;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Entity
@Table(name = "CWM_MODEL_DATA_UNIT")
public class CwmModelDataUnitEntity {
    private String modelId;
    private Long id;
    private String dataId;
    private String sColumnName;
    private String columnValue;
    private String unitId;

    @Basic
    @Column(name = "MODEL_ID")
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence",strategy = "sequence",parameters = {@Parameter(name="sequence",value="SEQ_CWM_MODEL_DATA_UNIT")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @Column(name = "S_COLUMN_NAME")
    public String getsColumnName() {
        return sColumnName;
    }

    public void setsColumnName(String sColumnName) {
        this.sColumnName = sColumnName;
    }

    @Basic
    @Column(name = "UNIT_ID")
    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "COLUMN_VALUE")
    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmModelDataUnitEntity that = (CwmModelDataUnitEntity) o;

        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dataId != null ? !dataId.equals(that.dataId) : that.dataId != null) return false;
        if (sColumnName != null ? !sColumnName.equals(that.sColumnName) : that.sColumnName != null) return false;
        if (unitId != null ? !unitId.equals(that.unitId) : that.unitId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = modelId != null ? modelId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (dataId != null ? dataId.hashCode() : 0);
        result = 31 * result + (sColumnName != null ? sColumnName.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        return result;
    }
}
