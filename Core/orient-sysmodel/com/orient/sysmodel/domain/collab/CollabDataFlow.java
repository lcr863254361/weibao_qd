package com.orient.sysmodel.domain.collab;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mengbin on 16/8/22.
 * Purpose:
 * Detail: 任务节点间的数据连线
 */
@Entity
@Table(name = "COLLAB_DATA_FLOW")
public class CollabDataFlow implements Serializable {

    public static final String MODEL_ID = "modelid";
    public static final String SRC_ID = "srcid";
    public static final String DESTNY_ID = "destnyid";

    private Long id;
    private Long modelid;
    private Long srcid;
    private Long destnyid;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_COLLAB_DATA_FLOW")})
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MODELID", nullable = false, length = 50)
    public Long getModelid() {
        return modelid;
    }

    public void setModelid(Long modelid) {
        this.modelid = modelid;
    }

    @Basic
    @Column(name = "SRCID", nullable = false, length = 50)
    public Long getSrcid() {
        return srcid;
    }

    public void setSrcid(Long srcid) {
        this.srcid = srcid;
    }

    @Basic
    @Column(name = "DESTNYID", nullable = false, length = 50)
    public Long getDestnyid() {
        return destnyid;
    }

    public void setDestnyid(Long destnyid) {
        this.destnyid = destnyid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollabDataFlow that = (CollabDataFlow) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modelid != null ? !modelid.equals(that.modelid) : that.modelid != null) return false;
        if (srcid != null ? !srcid.equals(that.srcid) : that.srcid != null) return false;
        if (destnyid != null ? !destnyid.equals(that.destnyid) : that.destnyid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (modelid != null ? modelid.hashCode() : 0);
        result = 31 * result + (srcid != null ? srcid.hashCode() : 0);
        result = 31 * result + (destnyid != null ? destnyid.hashCode() : 0);
        return result;
    }

    private static final long serialVersionUID =  1L;
}
