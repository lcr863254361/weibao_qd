package com.orient.sysmodel.domain.sys;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author enjoy
 * @createTime 2016-06-01 16:25
 */
@Entity
@Table(name = "CWM_PORTAL")
public class CwmPortalEntity {
    private Long id;
    private String title;
    private String url;
    private String jspath;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence",strategy = "sequence",parameters = {@org.hibernate.annotations.Parameter(name="sequence",value="SEQ_CWM_PORTAL")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE", nullable = true, insertable = true, updatable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "URL", nullable = true, insertable = true, updatable = true, length = 2000)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "JSPATH", nullable = true, insertable = true, updatable = true, length = 2000)
    public String getJspath() {
        return jspath;
    }

    public void setJspath(String jspath) {
        this.jspath = jspath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmPortalEntity that = (CwmPortalEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (jspath != null ? !jspath.equals(that.jspath) : that.jspath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (jspath != null ? jspath.hashCode() : 0);
        return result;
    }
}
