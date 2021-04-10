package com.orient.background.custom.statistic.bean;

import com.orient.background.statistic.bean.StatisticChartResult;
import com.orient.background.statistic.chartFormat.StatisticBar;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-04-24 16:21
 */
public class LogRecentOperatStatisticBarResult extends StatisticChartResult<List<String>, List<String>> implements StatisticBar {

    private List<String> xAxis = new ArrayList<>();

    @Override
    public List<String> getXAxis() {
        return xAxis;
    }

    @Override
    public List<String> getLegendData() {
        return new ArrayList<String>() {{
            add("操作数量");
        }};
    }

    @Override
    public List<String> getPostProcessResult() {
        List<String> retVal = super.getPostProcessResult();
        if (null == retVal || retVal.size() == 0) {
            retVal = getPreProcessResult();
        }
        return retVal;
    }
}
