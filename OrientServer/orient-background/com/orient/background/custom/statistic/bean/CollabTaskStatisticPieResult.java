package com.orient.background.custom.statistic.bean;

import com.orient.background.statistic.bean.StatisticChartResult;
import com.orient.background.statistic.chartFormat.StatisticPie;
import com.orient.utils.CommonTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.orient.collab.config.CollabConstants.STATUS_MAPPING;

/**
 * 数据包完成量统计结果描述
 *
 * @author zhangsheng
 * @create 2018-09-03 16:56 AM
 */
public class CollabTaskStatisticPieResult extends StatisticChartResult<List<Map<String, Object>>, List<Map<String, Object>>> implements StatisticPie {

    private List<String> legendData = new ArrayList<>();

    public List<String> getLegendData() {
        if (CommonTools.isEmptyList(legendData)) {
            STATUS_MAPPING.forEach((key, value) -> legendData.add(value));
        }
        return legendData;
    }

    @Override
    public List<Map<String, Object>> getPostProcessResult() {
        List<Map<String, Object>> retVal = super.getPostProcessResult();
        if (null == retVal || retVal.size() == 0) {
            retVal = getPreProcessResult();
        }
        return retVal;
    }
}
