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
@Table(name = "CF_CHART_INSTANCE")
public class CfChartInstanceEntity {
    private Long id;
    //图形名称
    private String name;
    //图形自定义处理类
    private String handler;
    //所属图形类型
    private CfChartTypeEntity cfChartTypeByCfChartTypeId;
    //被哪些统计配置所引用
    private Collection<CfStatisticChartEntity> cfStatisticChartsById;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_CHART_INSTANCE")})
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
    @Column(name = "HANDLER")
    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfChartInstanceEntity that = (CfChartInstanceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (handler != null ? !handler.equals(that.handler) : that.handler != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "CF_CHART_TYPE_ID", referencedColumnName = "ID")
    public CfChartTypeEntity getCfChartTypeByCfChartTypeId() {
        return cfChartTypeByCfChartTypeId;
    }

    public void setCfChartTypeByCfChartTypeId(CfChartTypeEntity cfChartTypeByCfChartTypeId) {
        this.cfChartTypeByCfChartTypeId = cfChartTypeByCfChartTypeId;
    }

    @OneToMany(mappedBy = "cfChartInstanceByCfChartInstanceId", cascade = CascadeType.ALL)
    public Collection<CfStatisticChartEntity> getCfStatisticChartsById() {
        return cfStatisticChartsById;
    }

    public void setCfStatisticChartsById(Collection<CfStatisticChartEntity> cfStatisticChartsById) {
        this.cfStatisticChartsById = cfStatisticChartsById;
    }
}
