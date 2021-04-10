package com.orient.sysmodel.domain.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by DuanDuanPan on 2016/6/30 0030.
 */
@Entity
@Table(name = "CWM_SYS_TOOLS")
public class CwmSysToolsEntity {
    private Long id;
    private String toolIcon;
    private String toolName;
    private String toolVersion;
    private String toolDescription;
    private String toolCode;
    private String toolType;
    private Long groupId;
    private CwmSysToolsGroupEntity belongGroup;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_SYS_TOOLS")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TOOL_ICON")
    public String getToolIcon() {
        return toolIcon;
    }

    public void setToolIcon(String toolIcon) {
        this.toolIcon = toolIcon;
    }

    @Basic
    @Column(name = "TOOL_NAME")
    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    @Basic
    @Column(name = "TOOL_VERSION")
    public String getToolVersion() {
        return toolVersion;
    }

    public void setToolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
    }

    @Basic
    @Column(name = "TOOL_DESCRIPTION")
    public String getToolDescription() {
        return toolDescription;
    }

    public void setToolDescription(String toolDescription) {
        this.toolDescription = toolDescription;
    }

    @Basic
    @Column(name = "TOOL_CODE")
    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    @Basic
    @Column(name = "TOOL_TYPE")
    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    public CwmSysToolsGroupEntity getBelongGroup() {
        return belongGroup;
    }

    public void setBelongGroup(CwmSysToolsGroupEntity belongGroup) {
        this.belongGroup = belongGroup;
        this.groupId = null == belongGroup ? null : belongGroup.getId();
    }

    @Transient
    public Long getGroupId() {
        return null == belongGroup ? groupId : belongGroup.getId();
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmSysToolsEntity that = (CwmSysToolsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (toolIcon != null ? !toolIcon.equals(that.toolIcon) : that.toolIcon != null) return false;
        if (toolName != null ? !toolName.equals(that.toolName) : that.toolName != null) return false;
        if (toolVersion != null ? !toolVersion.equals(that.toolVersion) : that.toolVersion != null) return false;
        if (toolDescription != null ? !toolDescription.equals(that.toolDescription) : that.toolDescription != null)
            return false;
        if (toolCode != null ? !toolCode.equals(that.toolCode) : that.toolCode != null) return false;
        if (toolType != null ? !toolType.equals(that.toolType) : that.toolType != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (toolIcon != null ? toolIcon.hashCode() : 0);
        result = 31 * result + (toolName != null ? toolName.hashCode() : 0);
        result = 31 * result + (toolVersion != null ? toolVersion.hashCode() : 0);
        result = 31 * result + (toolDescription != null ? toolDescription.hashCode() : 0);
        result = 31 * result + (toolCode != null ? toolCode.hashCode() : 0);
        result = 31 * result + (toolType != null ? toolType.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
