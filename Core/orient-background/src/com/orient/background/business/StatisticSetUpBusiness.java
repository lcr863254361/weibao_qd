package com.orient.background.business;

import com.orient.background.bean.StatisticSetUpWrapper;
import com.orient.background.statistic.StatisticPreProcessor;
import com.orient.background.statistic.bean.StatisticChartResult;
import com.orient.background.statistic.bean.StatisticResult;
import com.orient.background.statistic.parse.StatisticSqlParser;
import com.orient.background.statistic.parse.StatisticThreadLocalHolder;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.service.statistic.IStatisticSetUpService;
import com.orient.utils.BeanUtils;
import com.orient.utils.JsonUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class StatisticSetUpBusiness extends BaseHibernateBusiness<CfStatiscticEntity> {

    @Autowired
    IStatisticSetUpService statisticSetUpService;

    @Autowired
    StatisticSqlParser statisticSqlParser;

    @Autowired

    @Override
    public IStatisticSetUpService getBaseService() {
        return statisticSetUpService;
    }

    public ExtGridData<StatisticSetUpWrapper> listSpecial(Integer page, Integer limit, CfStatiscticEntity filter) {

        ExtGridData<CfStatiscticEntity> originalData = super.list(page, limit, filter);
        ExtGridData<StatisticSetUpWrapper> retVal = new ExtGridData<>();
        BeanUtils.copyProperties(retVal, originalData);
        List<CfStatiscticEntity> dbData = originalData.getResults();
        List<StatisticSetUpWrapper> wrapperData = new ArrayList<>(dbData.size());
        dbData.forEach(cfStatiscticEntity -> {
            StatisticSetUpWrapper statisticSetUpWrapper = new StatisticSetUpWrapper(cfStatiscticEntity);
            wrapperData.add(statisticSetUpWrapper);
        });
        retVal.setResults(wrapperData);
        return retVal;
    }

    public String doValidateSql(String sql, String params, String preProcessor, HttpServletRequest request) {

        StatisticChartResult statisticResult = null;
        StatisticPreProcessor statisticPreProcessor = prepareStatistic(params, preProcessor);
        try {
            CfStatiscticEntity tmp = new CfStatiscticEntity();
            tmp.setSql(sql);
            statisticResult = statisticPreProcessor.doPreProcess(tmp, request, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return statisticResult.getErrorMsg();
    }

    public StatisticResult doStatisic(Long statisticId, String params, HttpServletRequest request) {

        CfStatiscticEntity statiscticEntity = statisticSetUpService.getById(statisticId);
        //注入统计设置信息
        StatisticSetUpWrapper wrapper = new StatisticSetUpWrapper(statiscticEntity);
        StatisticResult statisticResult = new StatisticResult();
        statisticResult.setStatisticEntity(wrapper);
        statiscticEntity.getCfStatisticChartsById().forEach(cfStatisticChartEntity -> {
            String preProcessor = cfStatisticChartEntity.getPreProcessor();
            StatisticPreProcessor statisticPreProcessor = prepareStatistic(params, preProcessor);
            try {
                StatisticChartResult chartResult = statisticPreProcessor.doPreProcess(statiscticEntity, request, cfStatisticChartEntity);
                statisticResult.getChartResultMap().put(cfStatisticChartEntity.getId(), chartResult);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
        //clear threadLocalData
        StatisticThreadLocalHolder.clearParameters();
        return statisticResult;
    }

    private StatisticPreProcessor prepareStatistic(String params, String preProcessor) {
        Map<String, Object> paramMap = JsonUtil.json2Map(params);
        if (null != paramMap) {
            paramMap.forEach((key, value) -> StatisticThreadLocalHolder.putParamerter(key, value));
        }
        StatisticPreProcessor statisticPreProcessor = (StatisticPreProcessor) OrientContextLoaderListener.Appwac.getBean(preProcessor);
        return statisticPreProcessor;
    }
}
