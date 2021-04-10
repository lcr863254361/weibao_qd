package com.orient.sysmodel.domain.flow;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
@Entity
@Table(name = "AUDIT_FLOW_OPINION_SETTING")
public class AuditFlowOpinionSettingEntity {
    private Long id;
    private String taskName;
    private String pdId;
    private String opinion;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_AUDIT_FLOW_OPINION_SETTING")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "PD_ID")
    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    @Basic
    @Column(name = "OPINION")
    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditFlowOpinionSettingEntity that = (AuditFlowOpinionSettingEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (taskName != null ? !taskName.equals(that.taskName) : that.taskName != null) return false;
        if (pdId != null ? !pdId.equals(that.pdId) : that.pdId != null) return false;
        if (opinion != null ? !opinion.equals(that.opinion) : that.opinion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (pdId != null ? pdId.hashCode() : 0);
        result = 31 * result + (opinion != null ? opinion.hashCode() : 0);
        return result;
    }
}
