package com.orient.sysmodel.domain.statistic;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-04-04 10:40
 */
@Entity
@Table(name = "CF_STATISCTIC")
public class CfStatiscticEntity {
    private Long id;
    //统计配置名称
    private String name;
    //统计sql
    private String sql;
    //绑定了哪些图形实例
    private Collection<CfStatisticChartEntity> cfStatisticChartsById;
    //过滤条件集合
    private Collection<CfStatisticFilterEntity> cfStatisticFiltersById;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_STATISCTIC")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "SQL")
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfStatiscticEntity that = (CfStatiscticEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cfStatiscticByCfStatisticId",cascade = CascadeType.REMOVE)
    public Collection<CfStatisticChartEntity> getCfStatisticChartsById() {
        return cfStatisticChartsById;
    }

    public void setCfStatisticChartsById(Collection<CfStatisticChartEntity> cfStatisticChartsById) {
        this.cfStatisticChartsById = cfStatisticChartsById;
    }

    @OneToMany(mappedBy = "cfStatiscticByCfStatisticId",cascade = CascadeType.REMOVE)
    public Collection<CfStatisticFilterEntity> getCfStatisticFiltersById() {
        return cfStatisticFiltersById;
    }

    public void setCfStatisticFiltersById(Collection<CfStatisticFilterEntity> cfStatisticFiltersById) {
        this.cfStatisticFiltersById = cfStatisticFiltersById;
    }
}
