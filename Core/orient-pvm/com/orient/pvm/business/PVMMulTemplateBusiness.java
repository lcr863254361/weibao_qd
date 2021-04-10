package com.orient.pvm.business;
import com.orient.sysmodel.service.pvm.IPVMMulTemplateService;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orient.sysmodel.domain.pvm.CwmTaskmultiplecheckmodelEntity;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class PVMMulTemplateBusiness extends BaseHibernateBusiness<CwmTaskmultiplecheckmodelEntity> {

    @Autowired
    IPVMMulTemplateService pVMMulTemplateService;

    @Override
    public IPVMMulTemplateService getBaseService() {
        return pVMMulTemplateService;
    }


}
