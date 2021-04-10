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
@Table(name = "CF_STATISTIC_CHART")
public class CfStatisticChartEntity {
    private Long id;
    //自定义前端处理器
    private String customHandler;
    //自定义前处理器
    private String preProcessor;

    private String title;

    //所属统计图形实例
    private CfChartInstanceEntity cfChartInstanceByCfChartInstanceId;
    //所属统计设置
    private CfStatiscticEntity cfStatiscticByCfStatisticId;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CF_STATISTIC_CHART")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CUSTOM_HANDLER")
    public String getCustomHandler() {
        return customHandler;
    }

    public void setCustomHandler(String customHandler) {
        this.customHandler = customHandler;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfStatisticChartEntity that = (CfStatisticChartEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (customHandler != null ? !customHandler.equals(that.customHandler) : that.customHandler != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customHandler != null ? customHandler.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "CF_CHART_INSTANCE_ID", referencedColumnName = "ID")
    public CfChartInstanceEntity getCfChartInstanceByCfChartInstanceId() {
        return cfChartInstanceByCfChartInstanceId;
    }

    public void setCfChartInstanceByCfChartInstanceId(CfChartInstanceEntity cfChartInstanceByCfChartInstanceId) {
        this.cfChartInstanceByCfChartInstanceId = cfChartInstanceByCfChartInstanceId;
    }

    @ManyToOne
    @JoinColumn(name = "CF_STATISTIC_ID", referencedColumnName = "ID")
    public CfStatiscticEntity getCfStatiscticByCfStatisticId() {
        return cfStatiscticByCfStatisticId;
    }

    public void setCfStatiscticByCfStatisticId(CfStatiscticEntity cfStatiscticByCfStatisticId) {
        this.cfStatiscticByCfStatisticId = cfStatiscticByCfStatisticId;
    }

    @Basic
    @Column(name = "PRE_PROCESSOR")
    public String getPreProcessor() {
        return preProcessor;
    }

    public void setPreProcessor(String preProcessor) {
        this.preProcessor = preProcessor;
    }
}
