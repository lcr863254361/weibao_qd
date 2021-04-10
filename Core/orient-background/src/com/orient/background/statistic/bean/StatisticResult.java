package com.orient.background.statistic.bean;

import com.orient.background.bean.StatisticSetUpWrapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class StatisticResult implements Serializable {

    private StatisticSetUpWrapper statisticEntity;

    private Map<Long, StatisticChartResult> chartResultMap = new HashMap<>();


    public StatisticSetUpWrapper getStatisticEntity() {
        return statisticEntity;
    }

    public void setStatisticEntity(StatisticSetUpWrapper statisticEntity) {
        this.statisticEntity = statisticEntity;
    }

    public Map<Long, StatisticChartResult> getChartResultMap() {
        return chartResultMap;
    }

    public void setChartResultMap(Map<Long, StatisticChartResult> chartResultMap) {
        this.chartResultMap = chartResultMap;
    }
}
