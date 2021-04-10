package com.orient.auditflow.config;

import static com.orient.workflow.WorkFlowConstants.*;

/**
 * AuditFlowStatus
 *
 * @author Seraph
 *         2016-08-24 上午10:12
 */
public enum AuditFlowStatus {

    NotStarted(STATE_NOT_STARTED),
    Active(STATE_ACTIVE),
    End(STATE_END),
    EndCancel(STATE_END_CANCEL),
    EndError(STATE_END_ERROR);

    AuditFlowStatus(String name){
        this.name = name;
    }

    private final String name;
    @Override
    public String toString(){
        return this.name;
    }
}
