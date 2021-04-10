package com.orient.sysmodel.domain.sys;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author enjoy
 * @createTime 2016-06-01 16:25
 */
@Entity
@Table(name = "CWM_SYS_USER_PORTAL")
public class CwmSysUserPortalEntity {
    private Long id;
    private Long userId;
    private Long portalId;
    private Long order;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_SYS_USER_PORTAL")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_ID", nullable = false, insertable = true, updatable = true, precision = -127)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "PORTAL_ID", nullable = false, insertable = true, updatable = true, precision = -127)
    public Long getPortalId() {
        return portalId;
    }

    public void setPortalId(Long portalId) {
        this.portalId = portalId;
    }

    @Basic
    @Column(name = "PORTAL_ORDER", nullable = false, insertable = true, updatable = true, precision = -127)
    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmSysUserPortalEntity that = (CwmSysUserPortalEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (portalId != null ? !portalId.equals(that.portalId) : that.portalId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (portalId != null ? portalId.hashCode() : 0);
        return result;
    }
}
