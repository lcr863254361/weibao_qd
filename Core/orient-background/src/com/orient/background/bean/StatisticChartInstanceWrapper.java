package com.orient.background.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orient.sysmodel.domain.statistic.CfChartInstanceEntity;
import com.orient.sysmodel.domain.statistic.CfChartTypeEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.utils.BeanUtils;

import java.util.Collection;

/**
 * 去除关系属性，防止json转化死循环
 *
 * @author Administrator
 * @create 2017-04-04 11:16
 */
public class StatisticChartInstanceWrapper extends CfChartInstanceEntity {

    @JsonIgnore
    @Override
    public CfChartTypeEntity getCfChartTypeByCfChartTypeId() {
        return super.getCfChartTypeByCfChartTypeId();
    }

    @JsonIgnore
    @Override
    public Collection<CfStatisticChartEntity> getCfStatisticChartsById() {
        return super.getCfStatisticChartsById();
    }

    private Long chartTypeId;

    public Long getChartTypeId() {
        return null == getCfChartTypeByCfChartTypeId() ? null : getCfChartTypeByCfChartTypeId().getId();
    }

    public StatisticChartInstanceWrapper(CfChartInstanceEntity source) {
        if (null != source) {
            BeanUtils.copyProperties(this, source);
        }
    }
}
