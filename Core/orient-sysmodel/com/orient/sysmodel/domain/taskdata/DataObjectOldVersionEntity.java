package com.orient.sysmodel.domain.taskdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mengbin on 16/7/13.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "CWM_DATAOBJECT_OLDVERSION")
public class DataObjectOldVersionEntity extends DataObjectEntityBase {

    private Integer dataobjectid;
    private int isdeleted;

    public void setDataobjectid(Integer dataobjectid) {
        this.dataobjectid = dataobjectid;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }


    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_DATAOBJECT_OLDVERSION")})
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DATAOBJECTID", nullable = false, length = 40)
    public Integer getDataobjectid() {
        return dataobjectid;
    }

    @Basic
    @Column(name = "DATATYPEID", nullable = true, length = 50)
    public String getDatatypeId() {
        return datatypeId;
    }


    @Basic
    @Column(name = "DATAOBJECTNAME", nullable = false, length = 200)
    public String getDataobjectname() {
        return dataobjectname;
    }


    @Basic
    @Column(name = "ISREF", nullable = true, precision = 0)
    public int getIsref() {
        return isref;
    }


    @Basic
    @Column(name = "DIMENSION", nullable = true, length = 50)
    public String getDimension() {
        return dimension;
    }


    @Basic
    @Column(name = "VALUE", nullable = true, length = 500)
    public String getValue() {
        return value;
    }

    @Basic
    @Column(name = "PARENTDATAOBJECTID", nullable = false, length = 40)
    public Integer getParentdataobjectid() {
        return parentdataobjectid;
    }

    @Basic
    @Column(name = "ORDERNUMBER", nullable = true, precision = 0)
    public int getOrdernumber() {
        return ordernumber;
    }

    @Basic
    @Column(name = "SUBTYPEID", nullable = true, length = 40)
    public String getSubtypeid() {
        return subtypeid;
    }

    @Basic
    @Column(name = "SUBTYPEPARENTID", nullable = true, length = 1000)
    public String getSubtypeparentid() {
        return subtypeparentid;
    }

    @Basic
    @Column(name = "CREATERID", nullable = true, length = 40)
    public String getCreaterid() {
        return createrid;
    }

    @Basic
    @Column(name = "CREATETIME", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreatetime() {
        return createtime;
    }

    @Basic
    @Column(name = "MODIFIERID", nullable = true, length = 40)
    public String getModifierid() {
        return modifierid;
    }


    @Basic
    @Column(name = "MODIFYTIME", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getModifytime() {
        return modifytime;
    }


    @Basic
    @Column(name = "VERSION", nullable = true, length = 10)
    public String getVersion() {
        return version;
    }

    @Basic
    @Column(name = "FILEID", nullable = true, length = 40)
    public String getFileid() {
        return fileid;
    }


    @Basic
    @Column(name = "ISDELETED", nullable = false, precision = 0)
    public int getIsdeleted() {
        return isdeleted;
    }

    @Basic
    @Column(name = "UNIT", nullable = true, length = 50)
    public String getUnit() {
        return unit;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 500)
    public String getDescription() {
        return description;
    }

    @Basic
    @Column(name = "ISGLOBAL", nullable = true, precision = 0)
    public int getIsglobal() {
        return isglobal;
    }

    @Basic
    @Column(name = "CREATE_BY")
    public int getCreateBy() {
        return createBy;
    }

    @Basic
    @Column(name = "NODE_ID", nullable = true, length = 50)
    public String getNodeId() {
        return nodeId;
    }

    @Basic
    @Column(name = "NODE_VERSION", nullable = true, length = 50)
    public Integer getNodeVersion() {
        return nodeVersion;
    }

    @Basic
    @Column(name = "ORIGINAL_OBJ_ID", nullable = true, length = 40)
    public Integer getOriginalObjId() {
        return originalObjId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataObjectOldVersionEntity that = (DataObjectOldVersionEntity) o;

        if (id != that.id) return false;
        if (dataobjectid != that.dataobjectid) return false;
        if (isref != that.isref) return false;
        if (parentdataobjectid != that.parentdataobjectid) return false;
        if (isdeleted != that.isdeleted) return false;
        if (isglobal != that.isglobal) return false;
        if (datatypeId != null ? !datatypeId.equals(that.datatypeId) : that.datatypeId != null) return false;
        if (dataobjectname != null ? !dataobjectname.equals(that.dataobjectname) : that.dataobjectname != null)
            return false;
        if (dimension != null ? !dimension.equals(that.dimension) : that.dimension != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (ordernumber != that.ordernumber) return false;
        if (subtypeid != null ? !subtypeid.equals(that.subtypeid) : that.subtypeid != null) return false;
        if (subtypeparentid != null ? !subtypeparentid.equals(that.subtypeparentid) : that.subtypeparentid != null)
            return false;
        if (createrid != null ? !createrid.equals(that.createrid) : that.createrid != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (modifierid != null ? !modifierid.equals(that.modifierid) : that.modifierid != null) return false;
        if (modifytime != null ? !modifytime.equals(that.modifytime) : that.modifytime != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (fileid != null ? !fileid.equals(that.fileid) : that.fileid != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;
        if (nodeVersion != null ? !nodeVersion.equals(that.nodeVersion) : that.nodeVersion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + isdeleted;
        result = 31 * result + (int) (dataobjectid ^ (dataobjectid >>> 32));
        return result;
    }

}
