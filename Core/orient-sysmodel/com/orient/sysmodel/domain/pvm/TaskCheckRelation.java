package com.orient.sysmodel.domain.pvm;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "CWM_TASKCHECKRELATION")
public class TaskCheckRelation implements Serializable {

    private Long id;
    private Long checkmodelid;
    private Long checkdataid;
    private Long taskmodelid;
    private Long taskdataid;
    private String nodeId;

    public static final String TASK_MODEL_ID = "taskmodelid";
    public static final String TASK_DATA_ID = "taskdataid";

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_TASKCHECKRELATION")})

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CHECKMODELID", nullable = false, length = 200)
    public Long getCheckmodelid() {
        return checkmodelid;
    }

    public void setCheckmodelid(Long checkmodelid) {
        this.checkmodelid = checkmodelid;
    }

    @Basic
    @Column(name = "CHECKDATAID", nullable = false, length = 200)
    public Long getCheckdataid() {
        return checkdataid;
    }

    public void setCheckdataid(Long checkdataid) {
        this.checkdataid = checkdataid;
    }

    @Basic
    @Column(name = "TASKMODELID", length = 200)
    public Long getTaskmodelid() {
        return taskmodelid;
    }

    public void setTaskmodelid(Long taskmodelid) {
        this.taskmodelid = taskmodelid;
    }

    @Basic
    @Column(name = "TASKDATAID", length = 200)
    public Long getTaskdataid() {
        return taskdataid;
    }

    public void setTaskdataid(Long taskdataid) {
        this.taskdataid = taskdataid;
    }

    @Basic
    @Column(name = "NODE_ID", nullable = false, length = 38)
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskCheckRelation that = (TaskCheckRelation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (checkmodelid != null ? !checkmodelid.equals(that.checkmodelid) : that.checkmodelid != null) return false;
        if (checkdataid != null ? !checkdataid.equals(that.checkdataid) : that.checkdataid != null) return false;
        if (taskmodelid != null ? !taskmodelid.equals(that.taskmodelid) : that.taskmodelid != null) return false;
        if (taskdataid != null ? !taskdataid.equals(that.taskdataid) : that.taskdataid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (checkmodelid != null ? checkmodelid.hashCode() : 0);
        result = 31 * result + (checkdataid != null ? checkdataid.hashCode() : 0);
        result = 31 * result + (taskmodelid != null ? taskmodelid.hashCode() : 0);
        result = 31 * result + (taskdataid != null ? taskdataid.hashCode() : 0);
        return result;
    }

}
