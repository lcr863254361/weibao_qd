package com.orient.background.business;

import com.orient.background.bean.StatisticChartInstanceWrapper;
import com.orient.sysmodel.domain.statistic.CfChartInstanceEntity;
import com.orient.sysmodel.service.statistic.IStatisticChartInstanceService;
import com.orient.sysmodel.service.statistic.IStatisticChartTypeService;
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
public class StatisticChartInstanceBusiness extends BaseHibernateBusiness<CfChartInstanceEntity> {

    @Autowired
    IStatisticChartInstanceService statisticChartInstanceService;

    @Autowired
    IStatisticChartTypeService statisticChartTypeService;

    @Override
    public IStatisticChartInstanceService getBaseService() {
        return statisticChartInstanceService;
    }

    public ExtGridData<StatisticChartInstanceWrapper> listSpecial(Integer page, Integer limit, CfChartInstanceEntity filter, Long belongChartTypeId) {

        ExtGridData<CfChartInstanceEntity> originalData = super.list(page, limit, filter, Restrictions.eq("cfChartTypeByCfChartTypeId.id", belongChartTypeId));
        ExtGridData<StatisticChartInstanceWrapper> retVal = new ExtGridData<>();
        BeanUtils.copyProperties(retVal, originalData);
        List<CfChartInstanceEntity> dbData = originalData.getResults();
        List<StatisticChartInstanceWrapper> wrapperData = new ArrayList<>(dbData.size());
        dbData.forEach(cfChartInstanceEntity -> {
            StatisticChartInstanceWrapper staticticChartTypeWrapper = new StatisticChartInstanceWrapper(cfChartInstanceEntity);
            wrapperData.add(staticticChartTypeWrapper);
        });
        retVal.setResults(wrapperData);
        return retVal;
    }

    @Override
    public void update(CfChartInstanceEntity formValue) {
        CfChartInstanceEntity originalData = statisticChartInstanceService.getById(formValue.getId());
        //keep refrences
        BeanUtils.copyNotNullProperties(originalData, formValue);
        super.update(originalData);
    }

    public void saveSpecial(CfChartInstanceEntity formValue, Long belongChartTypeId) {
        formValue.setCfChartTypeByCfChartTypeId(statisticChartTypeService.getById(belongChartTypeId));
        super.save(formValue);
    }
}
