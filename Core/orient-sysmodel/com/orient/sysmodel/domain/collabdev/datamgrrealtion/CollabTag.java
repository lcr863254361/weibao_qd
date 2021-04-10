package com.orient.sysmodel.domain.collabdev.datamgrrealtion;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 10:39
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_TAG")
public class CollabTag {

    private String id;
    private String name;
    private String cbTagGroupId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_TAG")})
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
    @Column(name = "CB_TAG_GROUP_ID")
    public String getCbTagGroupId() {
        return cbTagGroupId;
    }

    public void setCbTagGroupId(String cbTagGroupId) {
        this.cbTagGroupId = cbTagGroupId;
    }

}
