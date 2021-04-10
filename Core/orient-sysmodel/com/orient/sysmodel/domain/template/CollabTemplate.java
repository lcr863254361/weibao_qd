package com.orient.sysmodel.domain.template;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-09-21 上午9:51
 */
@Entity
@Table(name = "COLLAB_TEMPLATE")
public class CollabTemplate {

    public static final String EXPORT_USER_ID = "exportUserId";
    public static final String PRIVATE_TEMP = "privateTemp";
    public static final String TYPE = "type";
    public static final String NAME = "name";

    private Long id;
    private String name;
    private String version;
    private String type;
    private Timestamp exportTime;
    private String exportUserId;
    private Boolean privateTemp;
    private String auditUserId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_COLLAB_TEMPLATE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    @Column(name = "VERSION")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "EXPORT_TIME")
    public Timestamp getExportTime() {
        return exportTime;
    }

    public void setExportTime(Timestamp exportTime) {
        this.exportTime = exportTime;
    }

    @Basic
    @Column(name = "EXPORT_USER_ID")
    public String getExportUserId() {
        return exportUserId;
    }

    public void setExportUserId(String exportUserId) {
        this.exportUserId = exportUserId;
    }


    @Basic
    @Column(name = "AUDIT_USER_ID")
    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    @Basic
    @Column(name = "IS_PRIVATE")
    public Boolean getPrivateTemp() {
        return privateTemp;
    }

    public void setPrivateTemp(Boolean privateTemp) {
        this.privateTemp = privateTemp;
    }
}
