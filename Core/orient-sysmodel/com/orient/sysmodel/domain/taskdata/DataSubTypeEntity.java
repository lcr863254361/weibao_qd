package com.orient.sysmodel.domain.taskdata;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mengbin on 16/7/4.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "CWM_DATASUBTYPE")
public class DataSubTypeEntity {
    private String id;
    private String subtypecode; //唯一标示
    private String datatype;    //数据类型id(用于复杂数据类型)  或者是 基本类型的(string,boolean....) 用于枚举数据类型
    private String datasubname;//名称
    private int isref;          //-- 子类型:   1 基础类型 ；2 扩展类型； 4 枚举类型 ；8物理类型 ；16 数组；
    private String datatypeId;  // 父的ID
    private String dimension;
    private String value;
    private int ordernumber;
    private Date createtime;
    private String userid;

    public static  int Status_Publish = 2;
    public static  int Status_Abord = 4;
    private Integer status;
    private int version;
    private int isnewest;
    private String fileid;
    private String unit;


    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_DATASUBTYPE")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SUBTYPECODE")
    public String getSubtypecode() {
        return subtypecode;
    }

    public void setSubtypecode(String subtypecode) {
        this.subtypecode = subtypecode;
    }

    @Basic
    @Column(name = "DATATYPE")
    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    @Basic
    @Column(name = "DATASUBNAME")
    public String getDatasubname() {
        return datasubname;
    }

    public void setDatasubname(String datasubname) {
        this.datasubname = datasubname;
    }

    @Basic
    @Column(name = "ISREF")
    public int getIsref() {
        return isref;
    }

    public void setIsref(int isref) {
        this.isref = isref;
    }

    @Basic
    @Column(name = "DATATYPE_ID")
    public String getDatatypeId() {
        return datatypeId;
    }

    public void setDatatypeId(String datatypeId) {
        this.datatypeId = datatypeId;
    }

    @Basic
    @Column(name = "DIMENSION")
    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    @Basic
    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "ORDERNUMBER")
    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    @Basic
    @Column(name = "CREATETIME")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "USERID")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Basic
    @Column(name = "ISNEWEST")
    public int getIsnewest() {
        return isnewest;
    }

    public void setIsnewest(int isnewest) {
        this.isnewest = isnewest;
    }

    @Basic
    @Column(name = "FILEID")
    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    @Basic
    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSubTypeEntity that = (DataSubTypeEntity) o;

        if (isref != that.isref) return false;
        if (ordernumber != that.ordernumber) return false;
        if (version != that.version) return false;
        if (isnewest != that.isnewest) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (subtypecode != null ? !subtypecode.equals(that.subtypecode) : that.subtypecode != null) return false;
        if (datatype != null ? !datatype.equals(that.datatype) : that.datatype != null) return false;
        if (datasubname != null ? !datasubname.equals(that.datasubname) : that.datasubname != null) return false;
        if (datatypeId != null ? !datatypeId.equals(that.datatypeId) : that.datatypeId != null) return false;
        if (dimension != null ? !dimension.equals(that.dimension) : that.dimension != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (fileid != null ? !fileid.equals(that.fileid) : that.fileid != null) return false;
        return unit != null ? unit.equals(that.unit) : that.unit == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (subtypecode != null ? subtypecode.hashCode() : 0);
        result = 31 * result + (datatype != null ? datatype.hashCode() : 0);
        result = 31 * result + (datasubname != null ? datasubname.hashCode() : 0);
        result = 31 * result + isref;
        result = 31 * result + (datatypeId != null ? datatypeId.hashCode() : 0);
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + ordernumber;
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + isnewest;
        result = 31 * result + (fileid != null ? fileid.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }
}
