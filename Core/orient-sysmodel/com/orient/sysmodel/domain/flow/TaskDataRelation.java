package com.orient.sysmodel.domain.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-06-30 下午4:51
 */
@Entity
@Table(name = "TASK_DATA_RELATION")
public class TaskDataRelation {

    public static final String PI_ID = "piId";
    public static final String TASK_NAME = "taskName";
    public static final String TABLE_NAME = "tableName";
    public static final String CREATE_TIME = "createTime";
    public static final String DATA_ID = "dataId";

    private long id;
    private String type;
    private String tableName;
    private String dataId;
    private String piId;
    private String taskName;
    private Date createTime;
    private String strategy;
    private String flowTaskId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_FLOW_DATA_RELATION")})
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TYPE", nullable = true, length = 30)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "TASK_NAME", nullable = true, length = 50)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "PI_ID", nullable = true, length = 50)
    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    @Basic
    @Column(name = "FLOW_TASK_ID", nullable = true, length = 50)
    public String getFlowTaskId() {
        return flowTaskId;
    }

    public void setFlowTaskId(String flowTaskId) {
        this.flowTaskId = flowTaskId;
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

}
