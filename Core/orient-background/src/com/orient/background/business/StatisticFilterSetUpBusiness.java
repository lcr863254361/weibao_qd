package com.orient.background.business;

import com.orient.sysmodel.domain.statistic.CfStatisticFilterEntity;
import com.orient.sysmodel.service.statistic.IStatisticFilterSetUpService;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class StatisticFilterSetUpBusiness extends BaseHibernateBusiness<CfStatisticFilterEntity> {

    @Autowired
    IStatisticFilterSetUpService statisticFilterSetUpService;

    @Override
    public IStatisticFilterSetUpService getBaseService() {
        return statisticFilterSetUpService;
    }
}
