package com.orient.sysmodel.domain.taskdata;

import java.io.Serializable;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 * 2016-10-14 下午5:33
 */
public class DataObjectEntityBase implements Serializable {

    protected Integer id;
    protected String datatypeId;
    protected String dataobjectname;
    protected int isref;
    protected String dimension;
    protected String value;
    protected Integer parentdataobjectid;
    protected Integer ordernumber;
    protected String subtypeid = "";
    protected String subtypeparentid;
    protected String fileid;
    protected String createrid;
    protected Date createtime;
    protected String modifierid;
    protected Date modifytime;
    protected String version;
    protected String unit;
    protected String description;
    protected int isglobal;   //0:为私有的, 1: 为全局的
    protected String nodeId;
    protected Integer nodeVersion;
    protected int createBy;   //1:从共享数据创建 0：从个人数据创建
    protected Integer originalObjId;

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getCreaterid() {
        return createrid;
    }

    public String getModifierid() {
        return modifierid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDatatypeId(String datatypeId) {
        this.datatypeId = datatypeId;
    }

    public void setDataobjectname(String dataobjectname) {
        this.dataobjectname = dataobjectname;
    }

    public void setIsref(int isref) {
        this.isref = isref;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setParentdataobjectid(Integer parentdataobjectid) {
        this.parentdataobjectid = parentdataobjectid;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public void setSubtypeid(String subtypeid) {
        this.subtypeid = subtypeid;
    }

    public void setSubtypeparentid(String subtypeparentid) {
        this.subtypeparentid = subtypeparentid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public void setModifierid(String modifierid) {
        this.modifierid = modifierid;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsglobal(int isglobal) {
        this.isglobal = isglobal;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(Integer nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public Integer getOriginalObjId() {
        return originalObjId;
    }

    public void setOriginalObjId(Integer originalObjId) {
        this.originalObjId = originalObjId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataObjectEntityBase that = (DataObjectEntityBase) o;

        if (id != that.id) return false;
        if (isref != that.isref) return false;
        if (parentdataobjectid != that.parentdataobjectid) return false;
        if (ordernumber != that.ordernumber) return false;
        if (isglobal != that.isglobal) return false;
        if (datatypeId != null ? !datatypeId.equals(that.datatypeId) : that.datatypeId != null) return false;
        if (dataobjectname != null ? !dataobjectname.equals(that.dataobjectname) : that.dataobjectname != null)
            return false;
        if (dimension != null ? !dimension.equals(that.dimension) : that.dimension != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (datatypeId != null ? datatypeId.hashCode() : 0);
        result = 31 * result + (dataobjectname != null ? dataobjectname.hashCode() : 0);
        result = 31 * result + isref;
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (int) (parentdataobjectid ^ (parentdataobjectid >>> 32));
        result = 31 * result + ordernumber;
        result = 31 * result + (subtypeid != null ? subtypeid.hashCode() : 0);
        result = 31 * result + (subtypeparentid != null ? subtypeparentid.hashCode() : 0);
        result = 31 * result + (createrid != null ? createrid.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (modifierid != null ? modifierid.hashCode() : 0);
        result = 31 * result + (modifytime != null ? modifytime.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (fileid != null ? fileid.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + isglobal;
        result = 31 * result + (nodeId != null ? nodeId.hashCode() : 0);
        result = 31 * result + (nodeVersion != null ? nodeVersion.hashCode() : 0);
        return result;
    }

    protected static final long serialVersionUID = 1L;
}
