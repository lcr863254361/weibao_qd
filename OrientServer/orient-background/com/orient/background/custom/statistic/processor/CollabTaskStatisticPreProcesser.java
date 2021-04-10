package com.orient.background.custom.statistic.processor;

import com.orient.background.custom.statistic.bean.CollabTaskStatisticBean;
import com.orient.background.custom.statistic.bean.CollabTaskStatisticPieResult;
import com.orient.background.statistic.impl.DefaultStatisticPerProcessor;
import com.orient.background.statistic.parse.StatisticThreadLocalHolder;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.utils.CommonTools;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.orient.collabdev.constant.CollabDevConstants.STATUS_MAPPING;

/**
 * 数据包完成量统计前处理器
 *
 * @author ZhangSheng
 * @create 2018-09-04 09:31 AM
 */
@Component
public class CollabTaskStatisticPreProcesser extends DefaultStatisticPerProcessor<CollabTaskStatisticPieResult> {

    @Override
    public CollabTaskStatisticPieResult doPreProcess(CfStatiscticEntity statisticSet, HttpServletRequest request, CfStatisticChartEntity statisticChartSet) throws IllegalAccessException, InstantiationException {
        if (StatisticThreadLocalHolder.getParamerter("prjId") != null) {
            //查询前处理
            String prjId = StatisticThreadLocalHolder.getParamerter("prjId").toString();
            if (prjId != null && !prjId.isEmpty()) {
                String sql = statisticSet.getSql();
                sql += " start with id = " + prjId + " connect by prior id = pid";
                //sql += " and id in ('" + CommonTools.list2String(prjIds).replaceAll(",", "','") + "')";
                //sql = "select csd.STATUS from CB_SYS_NODE csd where type = 'task' start with id = 46 connect by prior id = pid";
                statisticSet.setSql(sql);
            }
        }

        CollabTaskStatisticPieResult statisticResult = super.doPreProcess(statisticSet, request, statisticChartSet);
        //转化为图形所需数据

        List<Map<String, Object>> originalData = statisticResult.getData();
        //step1 合并
        List<CollabTaskStatisticBean> megredData = mergeStatus(originalData);

        //step2 英文转化为中文
        List<Map<String, Object>> preProcessResult = new ArrayList<>();
        megredData.forEach(data -> {
            Map<String, Object> tmpMap = new HashMap<>();
            tmpMap.put("name", STATUS_MAPPING.get(data.getStatus()));
            tmpMap.put("value", data.getCount());
            preProcessResult.add(tmpMap);
        });
        statisticResult.setPreProcessResult(preProcessResult);

        return statisticResult;
    }

    /**
     * @param originalData 原始数据
     * @return 合并状态
     */
    private List<CollabTaskStatisticBean> mergeStatus(List<Map<String, Object>> originalData) {
        List<CollabTaskStatisticBean> retVal = new ArrayList<>();
        //init data
        STATUS_MAPPING.forEach((key, value) -> {
            CollabTaskStatisticBean collabTaskStatisticBean = new CollabTaskStatisticBean(key, 0);
            retVal.add(collabTaskStatisticBean);
        });

        //get data count
        originalData.forEach(ori -> {
            String status = CommonTools.Obj2String(ori.get("STATUS"));
            CollabTaskStatisticBean collabTaskStatisticBean = retVal.stream().filter(psb -> status.equals(psb.getStatus())).findFirst().get();
            collabTaskStatisticBean.setCount(collabTaskStatisticBean.getCount() + 1);
        });

        //remove zero data
        List<CollabTaskStatisticBean> toRemoveData = retVal.stream().filter(psb -> 0 == psb.getCount().intValue()).collect(Collectors.toList());
        retVal.removeAll(toRemoveData);
        return retVal;
    }
}
