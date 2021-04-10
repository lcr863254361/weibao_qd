package com.orient.sysmodel.domain.tools;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-21 15:02
 */
@Entity
@Table(name = "CWM_USER_TOOL")
public class CwmUserTool {
    private Long id;
    private Long userId;
    private Long toolId;
    private String toolPath;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_USER_TOOL")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "TOOL_ID")
    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    @Basic
    @Column(name = "TOOL_PATH")
    public String getToolPath() {
        return toolPath;
    }

    public void setToolPath(String toolPath) {
        this.toolPath = toolPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmUserTool that = (CwmUserTool) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (toolId != null ? !toolId.equals(that.toolId) : that.toolId != null) return false;
        if (toolPath != null ? !toolPath.equals(that.toolPath) : that.toolPath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (toolId != null ? toolId.hashCode() : 0);
        result = 31 * result + (toolPath != null ? toolPath.hashCode() : 0);
        return result;
    }
}
