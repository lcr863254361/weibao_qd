package com.orient.template.model;

import com.orient.sysmodel.domain.template.CollabTemplate;

/**
 * mainly used to displayed in template list
 *
 * @author Seraph
 *         2016-09-21 下午4:25
 */
public class TemplateInfo extends CollabTemplate {

    private String createUserDisplayName;
    private String auditUserDisplayName;
    private String createTimeValue;

    public String getCreateUserDisplayName() {
        return createUserDisplayName;
    }

    public void setCreateUserDisplayName(String createUserDisplayName) {
        this.createUserDisplayName = createUserDisplayName;
    }

    public String getCreateTimeValue() {
        return createTimeValue;
    }

    public void setCreateTimeValue(String createTimeValue) {
        this.createTimeValue = createTimeValue;
    }

    public String getAuditUserDisplayName() {
        return auditUserDisplayName;
    }

    public void setAuditUserDisplayName(String auditUserDisplayName) {
        this.auditUserDisplayName = auditUserDisplayName;
    }
}
