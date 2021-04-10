package com.orient.sysmodel.domain.his;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
@Entity
@Table(name = "CWM_SYS_HIS_TASK")
public class CwmSysHisTaskEntity {
    private Long id;
    private String taskId;
    private String taskType;
    private String taskAssigner;
    private String taskBeginTime;
    private String taskEndTime;
    //private byte[] taskBindData;
    private String taskName;
    private String savePath;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_SYS_HIS_TASK")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "TASK_TYPE")
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Basic
    @Column(name = "TASK_ASSIGNER")
    public String getTaskAssigner() {
        return taskAssigner;
    }

    public void setTaskAssigner(String taskAssigner) {
        this.taskAssigner = taskAssigner;
    }

    @Basic
    @Column(name = "TASK_BEGIN_TIME")
    public String getTaskBeginTime() {
        return taskBeginTime;
    }

    public void setTaskBeginTime(String taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    @Basic
    @Column(name = "TASK_END_TIME")
    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

//    @Basic
//    @Column(name = "TASK_BIND_DATA")
//    public byte[] getTaskBindData() {
//        return taskBindData;
//    }
//
//    public void setTaskBindData(byte[] taskBindData) {
//        this.taskBindData = taskBindData;
//    }

    @Basic
    @Column(name = "TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name="SAVEPATH")
    public String getSavePath() {return savePath;}

    public void setSavePath(String savePath) {this.savePath = savePath;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmSysHisTaskEntity that = (CwmSysHisTaskEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (taskType != null ? !taskType.equals(that.taskType) : that.taskType != null) return false;
        if (taskAssigner != null ? !taskAssigner.equals(that.taskAssigner) : that.taskAssigner != null) return false;
        if (taskBeginTime != null ? !taskBeginTime.equals(that.taskBeginTime) : that.taskBeginTime != null)
            return false;
        if (taskEndTime != null ? !taskEndTime.equals(that.taskEndTime) : that.taskEndTime != null) return false;
        //if (!Arrays.equals(taskBindData, that.taskBindData)) return false;
        if (taskName != null ? !taskName.equals(that.taskName) : that.taskName != null) return false;
        if (savePath != null ? !savePath.equals(that.savePath) : that.savePath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (taskType != null ? taskType.hashCode() : 0);
        result = 31 * result + (taskAssigner != null ? taskAssigner.hashCode() : 0);
        result = 31 * result + (taskBeginTime != null ? taskBeginTime.hashCode() : 0);
        result = 31 * result + (taskEndTime != null ? taskEndTime.hashCode() : 0);
        //result = 31 * result + (taskBindData != null ? Arrays.hashCode(taskBindData) : 0);
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (savePath != null ? savePath.hashCode() : 0);
        return result;
    }
}
