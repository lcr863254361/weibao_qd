package com.orient.background.business;

import com.orient.background.bean.StatisticChartSetUpWrapper;
import com.orient.background.bean.StatisticChartSyncBean;
import com.orient.background.bean.StatisticChartSyncData;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.sysmodel.service.statistic.IStatisticChartInstanceService;
import com.orient.sysmodel.service.statistic.IStatisticChartSetUpService;
import com.orient.sysmodel.service.statistic.IStatisticSetUpService;
import com.orient.utils.BeanUtils;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class StatisticChartSetUpBusiness extends BaseHibernateBusiness<CfStatisticChartEntity> {

    @Autowired
    IStatisticChartSetUpService statisticChartSetUpService;

    @Autowired
    IStatisticSetUpService statisticSetUpService;

    @Autowired
    IStatisticChartInstanceService statisticChartInstanceService;

    @Override
    public IStatisticChartSetUpService getBaseService() {
        return statisticChartSetUpService;
    }

    public ExtGridData<StatisticChartSetUpWrapper> listSpecial(Integer page, Integer limit, CfStatisticChartEntity filter, Long belongStatisSetUpId) {

        ExtGridData<CfStatisticChartEntity> originalData = super.list(page, limit, filter, Restrictions.eq("cfStatiscticByCfStatisticId.id", belongStatisSetUpId));
        ExtGridData<StatisticChartSetUpWrapper> retVal = new ExtGridData<>();
        BeanUtils.copyProperties(retVal, originalData);
        List<CfStatisticChartEntity> dbData = originalData.getResults();
        List<StatisticChartSetUpWrapper> wrapperData = new ArrayList<>(dbData.size());
        dbData.forEach(cfStatisticChartEntity -> {
            StatisticChartSetUpWrapper staticticChartTypeWrapper = new StatisticChartSetUpWrapper(cfStatisticChartEntity);
            wrapperData.add(staticticChartTypeWrapper);
        });
        retVal.setResults(wrapperData);
        return retVal;
    }

    @Override
    public void update(CfStatisticChartEntity formValue) {
        CfStatisticChartEntity originalData = statisticChartSetUpService.getById(formValue.getId());
        //keep refrences
        BeanUtils.copyNotNullProperties(originalData, formValue);
        super.update(originalData);
    }

    public void saveSpecial(CfStatisticChartEntity formValue, Long belongStatisSetUpId, Long belongStatisticChartInstanceId) {
        formValue.setCfChartInstanceByCfChartInstanceId(statisticChartInstanceService.getById(belongStatisticChartInstanceId));
        formValue.setCfStatiscticByCfStatisticId(statisticSetUpService.getById(belongStatisSetUpId));
        super.save(formValue);
    }

    /**
     * 批量修改统计设置中的图形设置信息
     *
     * @param statisticChartSyncData
     */
    public void doSyncChartSet(StatisticChartSyncData statisticChartSyncData) {

        statisticChartSyncData.getCreate().forEach(statisticChartSyncBean -> {
            CfStatisticChartEntity cfStatisticChartEntity = new CfStatisticChartEntity();
            syncToDbBean(cfStatisticChartEntity, statisticChartSyncBean);
            statisticChartSetUpService.save(cfStatisticChartEntity);
        });

        statisticChartSyncData.getUpdate().forEach(statisticChartSyncBean -> {
            CfStatisticChartEntity cfStatisticChartEntity = statisticChartSetUpService.getById(statisticChartSyncBean.getId());
            syncToDbBean(cfStatisticChartEntity, statisticChartSyncBean);
            statisticChartSetUpService.update(cfStatisticChartEntity);
        });

        statisticChartSyncData.getRemove().forEach(statisticChartSyncBean -> {
            statisticChartSetUpService.delete(statisticChartSyncBean.getId());
        });
    }

    private void syncToDbBean(CfStatisticChartEntity dbBean, StatisticChartSyncBean statisticChartSyncBean) {
        dbBean.setCfStatiscticByCfStatisticId(statisticSetUpService.getById(statisticChartSyncBean.getBelongStatisSetUpId()));
        dbBean.setCfChartInstanceByCfChartInstanceId(statisticChartInstanceService.getById(statisticChartSyncBean.getBelongStaticChartInstanceId()));
        dbBean.setTitle(statisticChartSyncBean.getTitle());
        dbBean.setCustomHandler(statisticChartSyncBean.getCustomHandler());
        dbBean.setPreProcessor(statisticChartSyncBean.getPreProcessor());
    }
}
