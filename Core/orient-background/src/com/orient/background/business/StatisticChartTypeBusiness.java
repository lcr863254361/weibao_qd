package com.orient.background.business;

import com.orient.background.bean.StaticticChartTypeWrapper;
import com.orient.sysmodel.domain.statistic.CfChartTypeEntity;
import com.orient.sysmodel.service.statistic.IStatisticChartTypeService;
import com.orient.utils.BeanUtils;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class StatisticChartTypeBusiness extends BaseHibernateBusiness<CfChartTypeEntity> {

    @Autowired
    IStatisticChartTypeService statisticChartTypeService;

    @Override
    public IStatisticChartTypeService getBaseService() {
        return statisticChartTypeService;
    }

    public ExtGridData<StaticticChartTypeWrapper> listSpecial(Integer page, Integer limit, CfChartTypeEntity filter) {
        ExtGridData<CfChartTypeEntity> originalData = super.list(page, limit, filter);
        ExtGridData<StaticticChartTypeWrapper> retVal = new ExtGridData<>();
        BeanUtils.copyProperties(retVal, originalData);
        List<CfChartTypeEntity> dbData = originalData.getResults();
        List<StaticticChartTypeWrapper> wrapperData = new ArrayList<>(dbData.size());
        dbData.forEach(cfChartTypeEntity -> {
            StaticticChartTypeWrapper staticticChartTypeWrapper = new StaticticChartTypeWrapper(cfChartTypeEntity);
            wrapperData.add(staticticChartTypeWrapper);
        });
        retVal.setResults(wrapperData);
        return retVal;
    }

    public void deleteSpecial(Long[] ids) {
        for (Long id : ids) {
            statisticChartTypeService.delete(id);
        }
    }
}
