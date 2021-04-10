package com.orient.sysmodel.domain.taskdata;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by mengbin on 16/7/4.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "CWM_DATATYPE")
public class DataTypeEntity {

    public DataTypeEntity() {

    }

    /*
    主键
     */
    private String id;

    /*
    类型编码,系统自动编码,以Type开头
     */
    private String datatypecode;
    /*
        数据类型名称
     */
    private String datatypename;
    /**
     * string 字符串；integer 整形；boolean 布尔类型；file 文件类型；date 日期类型，double 双精度浮点数值类型；dataset 物理结构类型
     */
    private String datatype;

    /**
     * 类型描述
     */
    private String description;

    /**
     * 类型的分类 1 基本类型；2 扩展类型；4枚举类型；8 复杂类型
     */

    private short rank;
    public static short Rank_BASE = 1;
    public static short Rank_EXTEND = 2;
    public static short Rank_ENUM = 4;
    public static short Rank_PHYSIC = 8;

    /**
     * 类型的自定义图标
     */
    private String icon;
    /**
     * 版本号，初始为1，修改一次升版本
     */
    private int version;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 创建用户
     */
    private String userid;
    /**
     * 是否最新发布版。1最新版
     */
    private int isnewest;
    /**
     * 值域检查表达式：可以是正则表达式，公式等
     */
    private String checkstr;
    /**
     * 数据状态。0 编制中；1 审批中；2 已发布；3 修改中；4 已废弃
     */
    private int status;

    public static int Status_Publish = 2;
    public static int Status_Abord = 4;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_DATATYPE")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DATATYPECODE")
    public String getDatatypecode() {
        return datatypecode;
    }

    public void setDatatypecode(String datatypecode) {
        this.datatypecode = datatypecode;
    }

    @Basic
    @Column(name = "DATATYPENAME")
    public String getDatatypename() {
        return datatypename;
    }

    public void setDatatypename(String datatypename) {
        this.datatypename = datatypename;
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
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "RANK")
    public short getRank() {
        return rank;
    }

    public void setRank(short rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "ICON")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
    @Column(name = "ISNEWEST")
    public int getIsnewest() {
        return isnewest;
    }

    public void setIsnewest(int isnewest) {
        this.isnewest = isnewest;
    }

    @Basic
    @Column(name = "CHECKSTR")
    public String getCheckstr() {
        return checkstr;
    }

    public void setCheckstr(String checkstr) {
        this.checkstr = checkstr;
    }

    @Basic
    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataTypeEntity that = (DataTypeEntity) o;

        if (rank != that.rank) return false;
        if (version != that.version) return false;
        if (isnewest != that.isnewest) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (datatypecode != null ? !datatypecode.equals(that.datatypecode) : that.datatypecode != null) return false;
        if (datatypename != null ? !datatypename.equals(that.datatypename) : that.datatypename != null) return false;
        if (datatype != null ? !datatype.equals(that.datatype) : that.datatype != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (checkstr != null ? !checkstr.equals(that.checkstr) : that.checkstr != null) return false;
        if (status !=  that.status ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (datatypecode != null ? datatypecode.hashCode() : 0);
        result = 31 * result + (datatypename != null ? datatypename.hashCode() : 0);
        result = 31 * result + (datatype != null ? datatype.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) rank;
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + isnewest;
        result = 31 * result + (checkstr != null ? checkstr.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }
}
