package com.orient.sysmodel.domain.collabdev.datashare;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 10:06
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SHARE_FILE_PRIV_DG")
public class CollabShareFilePrivilegeDetailGroup {

    private String id;
    private String cbShareFilePrivilegeId;
    private String connection;
    private String pGroupId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SHARE_FILE_PRIV_DG")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CB_SHARE_FILE_PRIVILEGE_ID")
    public String getCbShareFilePrivilegeId() {
        return cbShareFilePrivilegeId;
    }

    public void setCbShareFilePrivilegeId(String cbShareFilePrivilegeId) {
        this.cbShareFilePrivilegeId = cbShareFilePrivilegeId;
    }

    @Basic
    @Column(name = "CONNECTION")
    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    @Basic
    @Column(name = "P_GROUP_ID")
    public String getpGroupId() {
        return pGroupId;
    }

    public void setpGroupId(String pGroupId) {
        this.pGroupId = pGroupId;
    }

}
