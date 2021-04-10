package com.orient.collabdev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orient.utils.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 10:55 AM
 */
public class PedigreeNodeVO implements Serializable {

    private String id;

    private String name;

    private String version;

    private String updateUser;

    private Date updateTime;

    private String techStatus;

    private String approvalStatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

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

    public static PedigreeNodeVO converDTOtoVO(PedigreeNode collabNode) {
        PedigreeNodeVO pedigreeNodeVO = new PedigreeNodeVO();
        BeanUtils.copyProperties(pedigreeNodeVO, collabNode);
        return pedigreeNodeVO;
    }
}
