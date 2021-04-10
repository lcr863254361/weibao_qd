package com.orient.sysmodel.domain.pvm;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "CWM_CHECKMODELDATATEMPLATE")
public class CheckModelDataTemplate {
    private Long id;
    private Long checkmodelid;
    private String templatepath;
    private String groupname;
    private String createuser;
    private Date uploadtime;
    private String name;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_CWM_CHECKMODELDATATEMPLATE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CHECKMODELID", nullable = false, length = 200)
    public Long getCheckmodelid() {
        return checkmodelid;
    }

    public void setCheckmodelid(Long checkmodelid) {
        this.checkmodelid = checkmodelid;
    }

    @Basic
    @Column(name = "TEMPLATEPATH", nullable = false, length = 4000)
    public String getTemplatepath() {
        return templatepath;
    }

    public void setTemplatepath(String templatepath) {
        this.templatepath = templatepath;
    }

    @Basic
    @Column(name = "GROUPNAME", nullable = true, length = 200)
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Basic
    @Column(name = "CREATEUSER", nullable = true, length = 200)
    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Basic
    @Column(name = "UPLOADTIME", nullable = true)
    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    @Basic
    @Column(name = "NAME", nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckModelDataTemplate that = (CheckModelDataTemplate) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (checkmodelid != null ? !checkmodelid.equals(that.checkmodelid) : that.checkmodelid != null) return false;
        if (templatepath != null ? !templatepath.equals(that.templatepath) : that.templatepath != null) return false;
        if (groupname != null ? !groupname.equals(that.groupname) : that.groupname != null) return false;
        if (createuser != null ? !createuser.equals(that.createuser) : that.createuser != null) return false;
        if (uploadtime != null ? !uploadtime.equals(that.uploadtime) : that.uploadtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (checkmodelid != null ? checkmodelid.hashCode() : 0);
        result = 31 * result + (templatepath != null ? templatepath.hashCode() : 0);
        result = 31 * result + (groupname != null ? groupname.hashCode() : 0);
        result = 31 * result + (createuser != null ? createuser.hashCode() : 0);
        result = 31 * result + (uploadtime != null ? uploadtime.hashCode() : 0);
        return result;
    }
}
