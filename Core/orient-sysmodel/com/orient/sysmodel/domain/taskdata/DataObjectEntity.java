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
@Table(name = "CWM_DATAOBJECT")
public class DataObjectEntity extends DataObjectEntityBase {

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_DATAOBJECT")})
    public Integer getId() {
        return id;
    }

    @Basic
    @Column(name = "DATATYPE_ID", nullable = true, length = 50)
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
    @Column(name = "ISGLOBAL")
    public int getIsglobal() {
        return isglobal;
    }

    @Basic
    @Column(name = "CREATE_BY")
    public int getCreateBy() {
        return createBy;
    }

    @Basic
    @Column(name = "NODE_ID")
    public String getNodeId() {
        return nodeId;
    }

    @Basic
    @Column(name = "NODE_VERSION")
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
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
