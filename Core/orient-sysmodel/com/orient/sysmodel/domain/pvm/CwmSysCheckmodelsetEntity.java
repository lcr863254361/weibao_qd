package com.orient.sysmodel.domain.pvm;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/11/7.
 */
@Entity
@Table(name = "CWM_SYS_CHECKMODELSET")
public class CwmSysCheckmodelsetEntity {
    private String id;
    private BigDecimal checkType;//0任意格式 1字符串 2勾选 3字符串加勾选
    private BigDecimal isRequired;//0是必填项 其余不是必填项
    private BigDecimal isBindPhoto;//0绑定照片 其余不绑定
    private String modelId;
    private String columnId;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_SYS_CHECKMODELSET")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CHECK_TYPE")
    public BigDecimal getCheckType() {
        return checkType;
    }

    public void setCheckType(BigDecimal checkType) {
        this.checkType = checkType;
    }

    @Basic
    @Column(name = "IS_REQUIRED")
    public BigDecimal getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(BigDecimal isRequired) {
        this.isRequired = isRequired;
    }

    @Basic
    @Column(name = "IS_BIND_PHOTO")
    public BigDecimal getIsBindPhoto() {return isBindPhoto;}

    public void setIsBindPhoto(BigDecimal isBindPhoto) {this.isBindPhoto = isBindPhoto;}

    @Basic
    @Column(name = "MODEL_ID")
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "COLUMN_ID")
    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmSysCheckmodelsetEntity that = (CwmSysCheckmodelsetEntity) o;

        if (checkType != null ? !checkType.equals(that.checkType) : that.checkType != null) return false;
        if (isRequired != null ? !isRequired.equals(that.isRequired) : that.isRequired != null) return false;
        if (columnId != null ? !columnId.equals(that.columnId) : that.columnId != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (checkType != null ? checkType.hashCode() : 0);
        result = 31 * result + (isRequired != null ? isRequired.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (columnId != null ? columnId.hashCode() : 0);
        return result;
    }
}
