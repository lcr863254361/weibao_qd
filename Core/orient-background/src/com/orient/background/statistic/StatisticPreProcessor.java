package com.orient.background.statistic;

import com.orient.background.statistic.bean.StatisticChartResult;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public interface StatisticPreProcessor<M extends StatisticChartResult> {

    M doPreProcess(CfStatiscticEntity statisticSet, HttpServletRequest request, CfStatisticChartEntity statisticChartSet) throws IllegalAccessException, InstantiationException;
}
