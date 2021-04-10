package com.orient.sysmodel.domain.doc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author enjoyjava
 * @create 2016-11-30 2:28 PM
 */
@Entity
@Table(name = "CWM_DOC_COLUMN_SCOPE")
public class CwmDocColumnScopeEntity {
    private Long id;
    private String columnType;
    private CwmDocHandlerEntity belongDocHandler;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_DOC_COLUMN_SCOPE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "COLUMN_TYPE")
    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BELONG_HANDLER")
    public CwmDocHandlerEntity getBelongDocHandler() {
        return belongDocHandler;
    }

    public void setBelongDocHandler(CwmDocHandlerEntity belongDocHandler) {
        this.belongDocHandler = belongDocHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmDocColumnScopeEntity that = (CwmDocColumnScopeEntity) o;

        if (id != that.id) return false;
        if (columnType != null ? !columnType.equals(that.columnType) : that.columnType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (columnType != null ? columnType.hashCode() : 0);
        return result;
    }
}
