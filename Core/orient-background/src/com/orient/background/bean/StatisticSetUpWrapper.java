package com.orient.background.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticFilterEntity;
import com.orient.utils.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class StatisticSetUpWrapper extends CfStatiscticEntity {

    @JsonIgnore
    @Override
    public Collection<CfStatisticChartEntity> getCfStatisticChartsById() {
        return super.getCfStatisticChartsById();
    }

    @JsonIgnore
    @Override
    public Collection<CfStatisticFilterEntity> getCfStatisticFiltersById() {
        return super.getCfStatisticFiltersById();
    }

    public StatisticSetUpWrapper(CfStatiscticEntity source) {
        if (null != source) {
            BeanUtils.copyProperties(this, source);
            Collection<CfStatisticChartEntity> cfStatisticChartEntities = getCfStatisticChartsById();
            cfStatisticChartEntities.forEach(cfStatisticChartEntity -> {
                StatisticChartSetUpWrapper statisticChartSetUpWrapper = new StatisticChartSetUpWrapper(cfStatisticChartEntity);
                charts.add(statisticChartSetUpWrapper);
            });
        }
    }

    private List<StatisticChartSetUpWrapper> charts = new ArrayList<>();

    public List<StatisticChartSetUpWrapper> getCharts() {
        return charts;
    }
}
