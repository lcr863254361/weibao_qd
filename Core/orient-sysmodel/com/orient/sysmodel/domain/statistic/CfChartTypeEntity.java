package com.orient.sysmodel.domain.statistic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-04-04 10:40
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "CF_CHART_TYPE")
public class CfChartTypeEntity {
    private Long id;
    //图形类型名称
    private String name;
    //被哪些图形实例引用
    private Collection<CfChartInstanceEntity> cfChartInstancesById;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_CHART_TYPE")})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfChartTypeEntity that = (CfChartTypeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cfChartTypeByCfChartTypeId", cascade = CascadeType.REMOVE)
    public Collection<CfChartInstanceEntity> getCfChartInstancesById() {
        return cfChartInstancesById;
    }

    public void setCfChartInstancesById(Collection<CfChartInstanceEntity> cfChartInstancesById) {
        this.cfChartInstancesById = cfChartInstancesById;
    }
}
