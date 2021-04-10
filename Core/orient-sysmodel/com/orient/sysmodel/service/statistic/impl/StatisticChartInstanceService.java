package com.orient.sysmodel.service.statistic.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.statistic.IStatisticChartInstanceDao;
import com.orient.sysmodel.domain.statistic.CfChartInstanceEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.statistic.IStatisticChartInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class StatisticChartInstanceService extends BaseService<CfChartInstanceEntity> implements IStatisticChartInstanceService {

    @Autowired
    IStatisticChartInstanceDao statisticChartInstanceDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.statisticChartInstanceDao;
    }
}
