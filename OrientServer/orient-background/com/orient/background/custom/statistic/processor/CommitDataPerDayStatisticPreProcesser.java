package com.orient.background.custom.statistic.processor;

import com.orient.background.custom.statistic.bean.CommitDataPerDayStatisticResult;
import com.orient.background.statistic.impl.DefaultStatisticPerProcessor;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.utils.StringUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author ZhangSheng
 * @create 2018-09-04 16:22
 */
@Component
public class CommitDataPerDayStatisticPreProcesser extends DefaultStatisticPerProcessor<CommitDataPerDayStatisticResult> {

    @Override
    public CommitDataPerDayStatisticResult doPreProcess(CfStatiscticEntity statisticSet, HttpServletRequest request, CfStatisticChartEntity statisticChartSet) throws IllegalAccessException, InstantiationException {
        CommitDataPerDayStatisticResult statisticResult = super.doPreProcess(statisticSet, request, statisticChartSet);
        //分组
        List<Map<String, Object>> dbData = statisticResult.getData();
        Map<String, Integer> operateGroup = new LinkedHashMap<>();
        dbData.forEach(data -> {
            //String dateTime = CommonTools.Obj2String(data.get("OP_DATE"));
            //因未设计相关业务表，故此处暂采用假数据--ZhangSheng 2018.9.4
            SimpleDateFormat pathFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateTime = pathFormat.format(new Date());
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
