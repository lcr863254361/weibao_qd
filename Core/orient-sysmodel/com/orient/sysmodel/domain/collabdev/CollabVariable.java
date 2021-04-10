package com.orient.sysmodel.domain.collabdev;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description 协同-运行中变量存储表
 * @Author GNY
 * @Date 2018/7/27 9:12
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SYS_VARIABLE")
public class CollabVariable {

    private String id;
    private String nodeId;
    private Long nodeVersion;
    private Long dbVersion;
    private String key;
    private String converter;
    private String lob;
    private Date dateValue;
    private Double doubleValue;
    private Long longValue;
    private String stringValue;
    private String textValue;
    private Long exeSys;
    private String pid;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_VARIABLE")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Basic
    @Column(name = "NODE_VERSION")
    public Long getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(Long nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    @Basic
    @Column(name = "DBVERSION")
    public Long getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(Long dbVersion) {
        this.dbVersion = dbVersion;
    }

    @Basic
    @Column(name = "KEY")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "CONVERTER")
    public String getConverter() {
        return converter;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    @Basic
    @Column(name = "LOB")
    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    @Basic
    @Column(name = "DATE_VALUE")
    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Basic
    @Column(name = "DOUBLE_VALUE")
    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Basic
    @Column(name = "LONG_VALUE")
    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    @Basic
    @Column(name = "STRING_VALUE")
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Lob
    @Column(name = "TEXT_VALUE")
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Basic
    @Column(name = "EXESYS")
    public Long getExeSys() {
        return exeSys;
    }

    public void setExeSys(Long exeSys) {
        this.exeSys = exeSys;
    }

    @Basic
    @Column(name = "P_ID")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
