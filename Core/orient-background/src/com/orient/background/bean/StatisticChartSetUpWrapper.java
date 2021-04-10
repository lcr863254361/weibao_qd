package com.orient.background.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orient.sysmodel.domain.statistic.CfChartInstanceEntity;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.utils.BeanUtils;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class StatisticChartSetUpWrapper extends CfStatisticChartEntity {

    @JsonIgnore
    @Override
    public CfChartInstanceEntity getCfChartInstanceByCfChartInstanceId() {
        return super.getCfChartInstanceByCfChartInstanceId();
    }

    @JsonIgnore
    @Override
    public CfStatiscticEntity getCfStatiscticByCfStatisticId() {
        return super.getCfStatiscticByCfStatisticId();
    }

    public StatisticChartSetUpWrapper(CfStatisticChartEntity source) {
        if (null != source) {
            BeanUtils.copyProperties(this, source);
        }
    }

    private Long belongStatisSetUpId;

    private Long belongStaticChartInstanceId;

    private String belongStaticChartInstanceName;

    private String belongStatisticChartInstanceHandler;

    public Long getBelongStatisSetUpId() {
        return null != this.getCfStatiscticByCfStatisticId() ? this.getCfStatiscticByCfStatisticId().getId() : belongStatisSetUpId;
    }

    public void setBelongStatisSetUpId(Long belongStatisSetUpId) {
        this.belongStatisSetUpId = belongStatisSetUpId;
    }

    public Long getBelongStaticChartInstanceId() {
        return null != this.getCfChartInstanceByCfChartInstanceId() ? this.getCfChartInstanceByCfChartInstanceId().getId() : belongStaticChartInstanceId;
    }

    public void setBelongStaticChartInstanceId(Long belongStaticChartInstanceId) {
        this.belongStaticChartInstanceId = belongStaticChartInstanceId;
    }

    public String getBelongStaticChartInstanceName() {
        return null != this.getCfChartInstanceByCfChartInstanceId() ? this.getCfChartInstanceByCfChartInstanceId().getName() : belongStaticChartInstanceName;
    }

    public void setBelongStaticChartInstanceName(String belongStaticChartInstanceName) {
        this.belongStaticChartInstanceName = belongStaticChartInstanceName;
    }

    public String getBelongStatisticChartInstanceHandler() {
        return null != this.getCfChartInstanceByCfChartInstanceId() ? this.getCfChartInstanceByCfChartInstanceId().getHandler() : belongStatisticChartInstanceHandler;
    }

    public void setBelongStatisticChartInstanceHandler(String belongStatisticChartInstanceHandler) {
        this.belongStatisticChartInstanceHandler = belongStatisticChartInstanceHandler;
    }
}
