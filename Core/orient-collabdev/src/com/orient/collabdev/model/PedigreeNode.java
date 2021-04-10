package com.orient.collabdev.model;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 3:04 PM
 */
public class PedigreeNode extends CollabDevNodeDTO {

    private String techStatus;

    private String approvalStatus;

    public String getTechStatus() {
        return techStatus;
    }

    public void setTechStatus(String techStatus) {
        this.techStatus = techStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
