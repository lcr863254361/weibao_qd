package com.orient.sysmodel.domain.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * FlowDataRelation
 *
 * @author Seraph
 *         2016-06-30 下午4:23
 */
@Entity
@Table(name = "FLOW_DATA_RELATION")
public class FlowDataRelation implements Serializable {

    public static final String PI_ID = "piId";
    public static final String MAIN_TYPE = "mainType";
    public static final String TABLE_NAME = "tableName";
    public static final String DATA_ID = "dataId";
    public static final String CREATE_TIME = "createTime";

    private Long id;
    private String mainType;
    private String subType;
    private String tableName;
    private String dataId;
    private String piId;
    private Date createTime;
    private String strategy;
    private String extramParams;

    public FlowDataRelation() {

    }

    public FlowDataRelation(String subType, String tableName, String dataId, String extramParams) {
        this.subType = subType;
        this.tableName = tableName;
        this.dataId = dataId;
        this.extramParams = extramParams;
    }

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_FLOW_DATA_RELATION")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MAIN_TYPE", nullable = true, length = 30)
    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    @Basic
    @Column(name = "SUB_TYPE", nullable = true, length = 30)
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    @Basic
    @Column(name = "TABLE_NAME", nullable = true, length = 30)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Basic
    @Column(name = "DATA_ID", nullable = true, length = 20)
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Basic
    @Column(name = "PI_ID", nullable = true, length = 50)
    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Basic
    @Column(name = "CREATE_TIME", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "STRATEGY", nullable = true, length = 200)
    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @Basic
    @Column(name = "EXTRAM_PARAMS", nullable = true, length = 400)
    public String getExtramParams() {
        return extramParams;
    }

    public void setExtramParams(String extramParams) {
        this.extramParams = extramParams;
    }
}
