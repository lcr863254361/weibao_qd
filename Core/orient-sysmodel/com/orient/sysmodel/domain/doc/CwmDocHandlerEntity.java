package com.orient.sysmodel.domain.doc;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author enjoyjava
 * @create 2016-11-30 2:28 PM
 */
@Entity
@Table(name = "CWM_DOC_HANDLER")
public class CwmDocHandlerEntity {
    private Long id;
    private String beanName;
    private String showName;
    private List<CwmDocColumnScopeEntity> docColumnScopes;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_DOC_HANDLER")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BEAN_NAME")
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Basic
    @Column(name = "SHOW_NAME")
    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "belongDocHandler", cascade = CascadeType.REMOVE)
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CwmDocColumnScopeEntity> getDocColumnScopes() {
        return docColumnScopes;
    }

    public void setDocColumnScopes(List<CwmDocColumnScopeEntity> docColumnScopes) {
        this.docColumnScopes = docColumnScopes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmDocHandlerEntity that = (CwmDocHandlerEntity) o;

        if (id != that.id) return false;
        if (beanName != null ? !beanName.equals(that.beanName) : that.beanName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (beanName != null ? beanName.hashCode() : 0);
        return result;
    }
}
