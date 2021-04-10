package com.orient.sysmodel.service.statistic.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.statistic.IStatisticChartSetUpDao;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.statistic.IStatisticChartSetUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class StatisticChartSetUpService extends BaseService<CfStatisticChartEntity> implements IStatisticChartSetUpService {

    @Autowired
    IStatisticChartSetUpDao statisticChartSetUpDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.statisticChartSetUpDao;
    }
}
