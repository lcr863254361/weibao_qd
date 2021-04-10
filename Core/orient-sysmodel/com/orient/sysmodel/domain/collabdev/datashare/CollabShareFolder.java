package com.orient.sysmodel.domain.collabdev.datashare;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 数据共享-共享文件夹
 * @Author GNY
 * @Date 2018/7/27 9:49
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SHARE_FOLDER")
public class CollabShareFolder {

    private String id;
    private String name;
    private Long folderOrder;
    private String nodeId;
    private String pid;
    private List<CollabShareFile> shareFileList = new ArrayList<>();


    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SHARE_FOLDER")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    @Column(name = "FOLDER_ORDER")
    public Long getFolderOrder() {
        return folderOrder;
    }

    public void setFolderOrder(Long folderOrder) {
        this.folderOrder = folderOrder;
    }

    @Basic
    @Column(name = "NODE_ID")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Basic
    @Column(name = "PID")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "belongFolder", cascade = CascadeType.REMOVE)
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CollabShareFile> getShareFileList() {
        return shareFileList;
    }

    public void setShareFileList(List<CollabShareFile> shareFileList) {
        this.shareFileList = shareFileList;
    }
}
