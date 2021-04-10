package com.orient.sysmodel.domain.collabdev.datamgrrealtion;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 数据管理关联-项目主数据绑定表
 * @Author GNY
 * @Date 2018/7/27 10:22
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_MAIN_DATA")
public class CollabMainData {

    private Long id;
    private String modelId;
    private String dataId;
    private String projectId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_MAIN_DATA")})
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
    @Column(name = "DATAID")
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Basic
    @Column(name = "PROJECTID")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
