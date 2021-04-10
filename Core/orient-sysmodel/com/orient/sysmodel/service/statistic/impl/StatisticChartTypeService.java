package com.orient.sysmodel.service.statistic.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.statistic.IStatisticChartTypeDao;
import com.orient.sysmodel.domain.statistic.CfChartTypeEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.statistic.IStatisticChartTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class StatisticChartTypeService extends BaseService<CfChartTypeEntity> implements IStatisticChartTypeService {

    @Autowired
    IStatisticChartTypeDao statisticChartTypeDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.statisticChartTypeDao;
    }
}
