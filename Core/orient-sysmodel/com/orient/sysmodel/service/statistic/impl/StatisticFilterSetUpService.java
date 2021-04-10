package com.orient.sysmodel.service.statistic.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.statistic.IStatisticFilterSetUpDao;
import com.orient.sysmodel.domain.statistic.CfStatisticFilterEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.statistic.IStatisticFilterSetUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class StatisticFilterSetUpService extends BaseService<CfStatisticFilterEntity> implements IStatisticFilterSetUpService {

    @Autowired
    IStatisticFilterSetUpDao statisticFilterSetUpDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.statisticFilterSetUpDao;
    }
}
