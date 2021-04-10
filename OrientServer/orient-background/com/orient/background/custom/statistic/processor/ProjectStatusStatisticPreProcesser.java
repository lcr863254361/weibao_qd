package com.orient.background.custom.statistic.processor;

import com.orient.background.custom.statistic.bean.ProjectStatusStatisticBean;
import com.orient.background.custom.statistic.bean.ProjectStatusStatisticPieResult;
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

import static com.orient.collab.config.CollabConstants.STATUS_MAPPING;

/**
 * 项目状态统计前处理器
 *
 * @author panduanduan
 * @create 2017-04-10 10:08 AM
 */
@Component
public class ProjectStatusStatisticPreProcesser extends DefaultStatisticPerProcessor<ProjectStatusStatisticPieResult> {

    @Override
    public ProjectStatusStatisticPieResult doPreProcess(CfStatiscticEntity statisticSet, HttpServletRequest request, CfStatisticChartEntity statisticChartSet) throws IllegalAccessException, InstantiationException {
        //查询前处理
        List<String> prjIds = (List<String>) StatisticThreadLocalHolder.getParamerter("prjIds");
        if (!CommonTools.isEmptyList(prjIds)) {
            String sql = statisticSet.getSql();
            sql += " and id in ('" + CommonTools.list2String(prjIds).replaceAll(",", "','") + "')";
            statisticSet.setSql(sql);
        }
        ProjectStatusStatisticPieResult statisticResult = super.doPreProcess(statisticSet, request, statisticChartSet);
        //转化为图形所需数据

        List<Map<String, Object>> originalData = statisticResult.getData();
        //step1 合并
        List<ProjectStatusStatisticBean> megredData = mergeStatus(originalData);
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
    private List<ProjectStatusStatisticBean> mergeStatus(List<Map<String, Object>> originalData) {
        List<ProjectStatusStatisticBean> retVal = new ArrayList<>();
        //init data
        STATUS_MAPPING.forEach((key, value) -> {
            ProjectStatusStatisticBean projectStatusStatisticBean = new ProjectStatusStatisticBean(key, 0);
            retVal.add(projectStatusStatisticBean);
        });
        originalData.forEach(ori -> {
            String status = CommonTools.Obj2String(ori.get("STATUS"));
            ProjectStatusStatisticBean projectStatusStatisticBean = retVal.stream().filter(psb -> status.equals(psb.getStatus())).findFirst().get();
            projectStatusStatisticBean.setCount(projectStatusStatisticBean.getCount() + 1);
        });
        //remove zero data
        List<ProjectStatusStatisticBean> toRemoveData = retVal.stream().filter(psb -> 0 == psb.getCount().intValue()).collect(Collectors.toList());
        retVal.removeAll(toRemoveData);
        return retVal;
    }
}
