package com.orient.sysmodel.domain.statistic;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-04-04 10:40
 */
@Entity
@Table(name = "CF_STATISTIC_FILTER")
public class CfStatisticFilterEntity {
    private Long id;
    //过滤字段id
    private Long columnId;
    //所属统计设置
    private CfStatiscticEntity cfStatiscticByCfStatisticId;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_STATISTIC_FILTER")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "COLUMN_ID")
    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfStatisticFilterEntity that = (CfStatisticFilterEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (columnId != null ? !columnId.equals(that.columnId) : that.columnId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (columnId != null ? columnId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "CF_STATISTIC_ID", referencedColumnName = "ID")
    public CfStatiscticEntity getCfStatiscticByCfStatisticId() {
        return cfStatiscticByCfStatisticId;
    }

    public void setCfStatiscticByCfStatisticId(CfStatiscticEntity cfStatiscticByCfStatisticId) {
        this.cfStatiscticByCfStatisticId = cfStatiscticByCfStatisticId;
    }
}
