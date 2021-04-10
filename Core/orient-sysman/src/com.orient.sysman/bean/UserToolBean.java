package com.orient.sysman.bean;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-26 9:01
 */
public class UserToolBean {

    private Long id;
    private Long userId;
    private Long toolGroupId;
    private Long toolId;
    private String toolPath;
    private String toolIcon;
    private String toolName;
    private String toolVersion;
    private String toolDescription;
    private String toolCode;
    private String toolType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getToolGroupId() {
        return toolGroupId;
    }

    public void setToolGroupId(Long toolGroupId) {
        this.toolGroupId = toolGroupId;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public String getToolPath() {
        return toolPath;
    }

    public void setToolPath(String toolPath) {
        this.toolPath = toolPath;
    }

    public String getToolIcon() {
        return toolIcon;
    }

    public void setToolIcon(String toolIcon) {
        this.toolIcon = toolIcon;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolVersion() {
        return toolVersion;
    }

    public void setToolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
    }

    public String getToolDescription() {
        return toolDescription;
    }

    public void setToolDescription(String toolDescription) {
        this.toolDescription = toolDescription;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
}
