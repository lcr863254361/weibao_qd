package com.orient.sysmodel.domain.file;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * @author enjoy
 * @creare 2016-04-28 14:28
 */
@Entity
@Table(name = "CWM_FILE_GROUP")
public class CwmFileGroupEntity {
    private Long id = null;
    private String groupName = null;
    private String groupType = null;
    private Long isShow = null;

    private List<CwmFileGroupItemEntity> groupItemEntityList;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_FILE_GROUP")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "GROUP_NAME", nullable = true, insertable = true, updatable = true, length = 38)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "GROUP_TYPE", nullable = true, insertable = true, updatable = true, length = 38)
    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    @Basic
    @Column(name = "IS_SHOW", nullable = true, insertable = true, updatable = true, precision = 0)
    public Long getIsShow() {
        return isShow;
    }

    public void setIsShow(Long isShow) {
        this.isShow = isShow;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "belongFileGroup", cascade = CascadeType.REMOVE)
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CwmFileGroupItemEntity> getGroupItemEntityList() {
        return groupItemEntityList;
    }

    public void setGroupItemEntityList(List<CwmFileGroupItemEntity> groupItemEntityList) {
        this.groupItemEntityList = groupItemEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmFileGroupEntity that = (CwmFileGroupEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;
        if (groupType != null ? !groupType.equals(that.groupType) : that.groupType != null) return false;
        if (isShow != null ? !isShow.equals(that.isShow) : that.isShow != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (groupType != null ? groupType.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        return result;
    }
}
