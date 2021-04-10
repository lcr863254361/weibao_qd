package com.orient.sysmodel.domain.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-29 14:16
 */
@Entity
@Table(name = "CWM_BACK")
public class CwmBackEntity {
    private Long id;
    private String remark;
    private String userId;
    private Date backDate;
    private String filePath;

    private String schemaId;
    private String tableId;
    private String backModel;
    private String backData;
    private String autoBack;
    private Date autoBackDate;
    private String autoBackZq;
    private Long type;


    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_BACK")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SCHEMA_ID", nullable = true, length = 38)
    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    @Basic
    @Column(name = "USER_ID", nullable = true, length = 20)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Basic
    @Column(name = "BACK_DATE", nullable = true)
    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    @Basic
    @Column(name = "FILE_PATH", nullable = true, length = 200)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "BACK_MODEL", nullable = true, length = 1)
    public String getBackModel() {
        return backModel;
    }

    public void setBackModel(String backModel) {
        this.backModel = backModel;
    }

    @Basic
    @Column(name = "BACK_DATA", nullable = true, length = 1)
    public String getBackData() {
        return backData;
    }

    public void setBackData(String backData) {
        this.backData = backData;
    }

    @Basic
    @Column(name = "AUTO_BACK", nullable = true, length = 1)
    public String getAutoBack() {
        return autoBack;
    }

    public void setAutoBack(String autoBack) {
        this.autoBack = autoBack;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Basic
    @Column(name = "AUTO_BACK_DATE", nullable = true)
    public Date getAutoBackDate() {
        return autoBackDate;
    }

    public void setAutoBackDate(Date autoBackDate) {
        this.autoBackDate = autoBackDate;
    }

    @Basic
    @Column(name = "AUTO_BACK_ZQ", nullable = true, length = 3)
    public String getAutoBackZq() {
        return autoBackZq;
    }

    public void setAutoBackZq(String autoBackZq) {
        this.autoBackZq = autoBackZq;
    }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "TYPE", nullable = true, precision = 0)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Basic
    @Column(name = "TABLE_ID", nullable = true, length = 38)
    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmBackEntity that = (CwmBackEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (schemaId != null ? !schemaId.equals(that.schemaId) : that.schemaId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (backDate != null ? !backDate.equals(that.backDate) : that.backDate != null) return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (backModel != null ? !backModel.equals(that.backModel) : that.backModel != null) return false;
        if (backData != null ? !backData.equals(that.backData) : that.backData != null) return false;
        if (autoBack != null ? !autoBack.equals(that.autoBack) : that.autoBack != null) return false;
        if (autoBackDate != null ? !autoBackDate.equals(that.autoBackDate) : that.autoBackDate != null) return false;
        if (autoBackZq != null ? !autoBackZq.equals(that.autoBackZq) : that.autoBackZq != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (tableId != null ? !tableId.equals(that.tableId) : that.tableId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (schemaId != null ? schemaId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (backDate != null ? backDate.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (backModel != null ? backModel.hashCode() : 0);
        result = 31 * result + (backData != null ? backData.hashCode() : 0);
        result = 31 * result + (autoBack != null ? autoBack.hashCode() : 0);
        result = 31 * result + (autoBackDate != null ? autoBackDate.hashCode() : 0);
        result = 31 * result + (autoBackZq != null ? autoBackZq.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (tableId != null ? tableId.hashCode() : 0);
        return result;
    }
}
