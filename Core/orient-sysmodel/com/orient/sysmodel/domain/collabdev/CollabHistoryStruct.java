package com.orient.sysmodel.domain.collabdev;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 抽象中介-历史结构表
 * @Author GNY
 * @Date 2018/7/26 20:41
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SYS_HIS_STRUCT")
public class CollabHistoryStruct {

    private String id;
    private String rootId;
    private Integer rootVersion;
    private String structor;
    private Long isSynced;
    private String remark;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SYS_HIS_STRUCT")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ROOT_ID")
    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    @Basic
    @Column(name = "ROOT_VERSION")
    public Integer getRootVersion() {
        return rootVersion;
    }

    public void setRootVersion(Integer rootVersion) {
        this.rootVersion = rootVersion;
    }

    @Basic
    @Column(name = "STRUCTOR")
    public String getStructor() {
        return structor;
    }

    public void setStructor(String structor) {
        this.structor = structor;
    }

    @Basic
    @Column(name = "IS_SYNCED")
    public Long getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(Long isSynced) {
        this.isSynced = isSynced;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
