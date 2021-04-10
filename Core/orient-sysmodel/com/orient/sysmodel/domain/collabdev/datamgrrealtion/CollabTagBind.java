package com.orient.sysmodel.domain.collabdev.datamgrrealtion;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 10:30
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_TAG_BIND")
public class CollabTagBind {

    private String id;
    private String name;
    private String cbTagId;
    private String tableType;
    private String tablePrimaryKey;
    private String dataPrimaryKey;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_TAG_BIND")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "CB_TAG_ID")
    public String getCbTagId() {
        return cbTagId;
    }

    public void setCbTagId(String cbTagId) {
        this.cbTagId = cbTagId;
    }

    @Basic
    @Column(name = "TABLE_TYPE")
    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    @Basic
    @Column(name = "TABLE_PRIMARY_KEY")
    public String getTablePrimaryKey() {
        return tablePrimaryKey;
    }

    public void setTablePrimaryKey(String tablePrimaryKey) {
        this.tablePrimaryKey = tablePrimaryKey;
    }

    @Basic
    @Column(name = "DATA_PRIMARY_KEY")
    public String getDataPrimaryKey() {
        return dataPrimaryKey;
    }

    public void setDataPrimaryKey(String dataPrimaryKey) {
        this.dataPrimaryKey = dataPrimaryKey;
    }

}
