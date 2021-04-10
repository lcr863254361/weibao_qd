package com.orient.sysmodel.domain.collab;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * CollabFlowRelation
 *
 * @author Seraph
 *         2016-08-16 上午10:06
 */
@Entity
@Table(name = "COLLAB_FLOW_RELATION")
public class CollabFlowRelation {

    public CollabFlowRelation(){

    }

    public CollabFlowRelation(String parentPiId, String subPiId, String taskId){
        this.parentPiId = parentPiId;
        this.subPiId = subPiId;
        this.taskId = taskId;
    }

    public CollabFlowRelation(Long id, String parentPiId, String subPiId, String taskId){
        this.id = id;
        this.parentPiId = parentPiId;
        this.subPiId = subPiId;
        this.taskId = taskId;
    }

    private Long id;
    private String parentPiId;
    private String subPiId;
    private String taskId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_COLLAB_FLOW_RELATION")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PARENT_PI_ID")
    public String getParentPiId() {
        return parentPiId;
    }

    public void setParentPiId(String parentPiId) {
        this.parentPiId = parentPiId;
    }

    @Basic
    @Column(name = "SUB_PI_ID")
    public String getSubPiId() {
        return subPiId;
    }

    public void setSubPiId(String subPiId) {
        this.subPiId = subPiId;
    }

    @Basic
    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
