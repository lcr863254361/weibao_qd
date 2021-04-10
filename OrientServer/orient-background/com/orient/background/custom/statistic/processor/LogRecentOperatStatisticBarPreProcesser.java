package com.orient.background.custom.statistic.processor;

import com.orient.background.custom.statistic.bean.LogRecentOperatStatisticBarResult;
import com.orient.background.statistic.impl.DefaultStatisticPerProcessor;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-04-24 16:31
 */
@Component
public class LogRecentOperatStatisticBarPreProcesser extends DefaultStatisticPerProcessor<LogRecentOperatStatisticBarResult> {

    @Override
    public LogRecentOperatStatisticBarResult doPreProcess(CfStatiscticEntity statisticSet, HttpServletRequest request, CfStatisticChartEntity statisticChartSet) throws IllegalAccessException, InstantiationException {
        LogRecentOperatStatisticBarResult statisticResult = super.doPreProcess(statisticSet, request, statisticChartSet);
        //分组
        List<Map<String, Object>> dbData = statisticResult.getData();
        Map<String, Integer> operateGroup = new LinkedHashMap<>();
        dbData.forEach(data -> {
            String dateTime = CommonTools.Obj2String(data.get("OP_DATE"));
            if (!StringUtil.isEmpty(dateTime)) {
                String date = dateTime.substring(0,10);
                if (operateGroup.get(date) == null) {
                    operateGroup.put(date, 0);
                }
                operateGroup.put(date, operateGroup.get(date) + 1);
            }
        });
        List<String> preProcessResult = new ArrayList<>();
        operateGroup.forEach((date, count) -> {
            statisticResult.getXAxis().add(date);
            preProcessResult.add(count.toString());
        });
        statisticResult.setPreProcessResult(preProcessResult);
        return statisticResult;
    }
}
