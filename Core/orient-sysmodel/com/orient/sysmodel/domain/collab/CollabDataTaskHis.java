package com.orient.sysmodel.domain.collab;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mengbin on 16/8/23.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "COLLAB_DATATASK_HIS")
public class CollabDataTaskHis {
    private String id;
    private Long taskmodelid;
    private Long dataid;
    private String userid;
    private String taskstate;
    private Date createtime;
    private Date taketime;
    private Date finishtime;
    private String message;


    @Id
    @Column(name = "ID", nullable = false, length = 20)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TASKMODELID", nullable = false, length = 50)
    public Long getTaskmodelid() {
        return taskmodelid;
    }

    public void setTaskmodelid(Long taskmodelid) {
        this.taskmodelid = taskmodelid;
    }

    @Basic
    @Column(name = "DATAID", nullable = false, length = 50)
    public Long getDataid() {
        return dataid;
    }

    public void setDataid(Long dataid) {
        this.dataid = dataid;
    }

    @Basic
    @Column(name = "USERID", nullable = false, length = 50)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "TASKSTATE", nullable = false, length = 50)
    public String getTaskstate() {
        return taskstate;
    }

    public void setTaskstate(String taskstate) {
        this.taskstate = taskstate;
    }

    @Basic
    @Column(name = "CREATETIME", nullable = true)
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "TAKETIME", nullable = true)
    public Date getTaketime() {
        return taketime;
    }

    public void setTaketime(Date taketime) {
        this.taketime = taketime;
    }

    @Basic
    @Column(name = "FINISHTIME", nullable = true)
    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }


    @Basic
    @Column(name = "MESSAGE", nullable = true, length = 500)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollabDataTaskHis that = (CollabDataTaskHis) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (taskmodelid != null ? !taskmodelid.equals(that.taskmodelid) : that.taskmodelid != null) return false;
        if (dataid != null ? !dataid.equals(that.dataid) : that.dataid != null) return false;
        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (taskstate != null ? !taskstate.equals(that.taskstate) : that.taskstate != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (taketime != null ? !taketime.equals(that.taketime) : that.taketime != null) return false;
        if (finishtime != null ? !finishtime.equals(that.finishtime) : that.finishtime != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (taskmodelid != null ? taskmodelid.hashCode() : 0);
        result = 31 * result + (dataid != null ? dataid.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (taskstate != null ? taskstate.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (taketime != null ? taketime.hashCode() : 0);
        result = 31 * result + (finishtime != null ? finishtime.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
