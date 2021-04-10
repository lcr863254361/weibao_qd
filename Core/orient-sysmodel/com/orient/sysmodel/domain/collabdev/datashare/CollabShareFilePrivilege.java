package com.orient.sysmodel.domain.collabdev.datashare;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 数据共享-分享权限
 * @Author GNY
 * @Date 2018/7/27 10:00
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SHARE_FILE_PRIV")
public class CollabShareFilePrivilege {

    private String id;
    private String cbShareFileId;
    private String rights;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SHARE_FILE_PRIV")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CB_SHARE_FILE_ID")
    public String getCbShareFileId() {
        return cbShareFileId;
    }

    public void setCbShareFileId(String cbShareFileId) {
        this.cbShareFileId = cbShareFileId;
    }

    @Basic
    @Column(name = "RIGHTS")
    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

}
