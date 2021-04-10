package com.orient.sysmodel.service.statistic.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.statistic.IStatisticSetUpDao;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.statistic.IStatisticSetUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class StatisticSetUpService extends BaseService<CfStatiscticEntity> implements IStatisticSetUpService {

    @Autowired
    IStatisticSetUpDao statisticSetUpDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.statisticSetUpDao;
    }
}
