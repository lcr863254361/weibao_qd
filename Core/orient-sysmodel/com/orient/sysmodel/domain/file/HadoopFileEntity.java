/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.sysmodel.domain.file;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by mengbin on 16/5/9.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "CWM_HADOOP_FILE")
public class HadoopFileEntity {
    private Long id;
    private String cwmFileId;
    private Integer state;
    private String fdspath;
    private String mrjobname;


    public static  Integer UnStart =0, Uploaded = 1, Impoerted = 2;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_HADOOP_FILE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CWM_FILE_ID")
    public String getCwmFileId() {
        return cwmFileId;
    }

    public void setCwmFileId(String cwmFileId) {
        this.cwmFileId = cwmFileId;
    }

    @Basic
    @Column(name = "STATE")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "FDSPATH")
    public String getFdspath() {
        return fdspath;
    }

    public void setFdspath(String fdspath) {
        this.fdspath = fdspath;
    }

    @Basic
    @Column(name = "MRJOBNAME")
    public String getMrjobname() {
        return mrjobname;
    }

    public void setMrjobname(String mrjobname) {
        this.mrjobname = mrjobname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HadoopFileEntity that = (HadoopFileEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cwmFileId != null ? !cwmFileId.equals(that.cwmFileId) : that.cwmFileId != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (fdspath != null ? !fdspath.equals(that.fdspath) : that.fdspath != null) return false;
        if (mrjobname != null ? !mrjobname.equals(that.mrjobname) : that.mrjobname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cwmFileId != null ? cwmFileId.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (fdspath != null ? fdspath.hashCode() : 0);
        result = 31 * result + (mrjobname != null ? mrjobname.hashCode() : 0);
        return result;
    }
}
