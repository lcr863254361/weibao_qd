package com.orient.sysmodel.domain.flow;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-08-07 15:51
 */
@Entity
@Table(name = "FLOW_COMMISSION")
public class FlowCommissionEntity {
    private Long id;
    private String pdid;
    private String mainUserName;
    private String slaveUserName;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@Parameter(name = "sequence", value = "SEQ_FLOW_COMMISSION")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PDID")
    public String getPdid() {
        return pdid;
    }

    public void setPdid(String pdid) {
        this.pdid = pdid;
    }

    @Basic
    @Column(name = "MAIN_USER_NAME")
    public String getMainUserName() {
        return mainUserName;
    }

    public void setMainUserName(String mainUserName) {
        this.mainUserName = mainUserName;
    }

    @Basic
    @Column(name = "SLAVE_USER_NAME")
    public String getSlaveUserName() {
        return slaveUserName;
    }

    public void setSlaveUserName(String slaveUserName) {
        this.slaveUserName = slaveUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowCommissionEntity that = (FlowCommissionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (pdid != null ? !pdid.equals(that.pdid) : that.pdid != null) return false;
        if (mainUserName != null ? !mainUserName.equals(that.mainUserName) : that.mainUserName != null) return false;
        if (slaveUserName != null ? !slaveUserName.equals(that.slaveUserName) : that.slaveUserName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pdid != null ? pdid.hashCode() : 0);
        result = 31 * result + (mainUserName != null ? mainUserName.hashCode() : 0);
        result = 31 * result + (slaveUserName != null ? slaveUserName.hashCode() : 0);
        return result;
    }
}
