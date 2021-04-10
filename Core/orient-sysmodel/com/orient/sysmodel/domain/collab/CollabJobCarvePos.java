package com.orient.sysmodel.domain.collab;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mengbin on 16/8/22.
 * Purpose:
 * Detail:
 */
@Entity
@Table(name = "COLLAB_JOB_CARVEPOS")
public class CollabJobCarvePos implements Serializable {

    public static final String MODEL_ID = "modelid";
    public static final String DATA_ID = "dataid";

    private Long id;
    private Long modelid;
    private Long dataid;
    private Float xpos;
    private Float ypos;
    private Float width;
    private Float heigth;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_COLLAB_JOB_CARVEPOS")})
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
    @Column(name = "DATAID", nullable = false, length = 50)
    public Long getDataid() {
        return dataid;
    }

    public void setDataid(Long dataid) {
        this.dataid = dataid;
    }

    @Basic
    @Column(name = "XPOS", nullable = false, length = 50)
    public Float getXpos() {
        return xpos;
    }

    public void setXpos(Float xpos) {
        this.xpos = xpos;
    }

    @Basic
    @Column(name = "YPOS", nullable = false, length = 50)
    public Float getYpos() {
        return ypos;
    }

    public void setYpos(Float ypos) {
        this.ypos = ypos;
    }

    @Basic
    @Column(name = "WIDTH", nullable = false, length = 50)
    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    @Basic
    @Column(name = "HEIGTH", nullable = false, length = 50)
    public Float getHeigth() {
        return heigth;
    }

    public void setHeigth(Float heigth) {
        this.heigth = heigth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollabJobCarvePos that = (CollabJobCarvePos) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modelid != null ? !modelid.equals(that.modelid) : that.modelid != null) return false;
        if (dataid != null ? !dataid.equals(that.dataid) : that.dataid != null) return false;
        if (xpos != null ? !xpos.equals(that.xpos) : that.xpos != null) return false;
        if (ypos != null ? !ypos.equals(that.ypos) : that.ypos != null) return false;
        if (width != null ? !width.equals(that.width) : that.width != null) return false;
        if (heigth != null ? !heigth.equals(that.heigth) : that.heigth != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (modelid != null ? modelid.hashCode() : 0);
        result = 31 * result + (dataid != null ? dataid.hashCode() : 0);
        result = 31 * result + (xpos != null ? xpos.hashCode() : 0);
        result = 31 * result + (ypos != null ? ypos.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (heigth != null ? heigth.hashCode() : 0);
        return result;
    }

    private static final long serialVersionUID =  1L;
}
