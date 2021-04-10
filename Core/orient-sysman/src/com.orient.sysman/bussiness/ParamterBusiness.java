package com.orient.sysman.bussiness;

import com.orient.sysmodel.domain.sys.Parameter;
import com.orient.sysmodel.service.sys.IParamterService;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 参数管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class ParamterBusiness extends BaseHibernateBusiness<Parameter> {

    @Autowired
    IParamterService paramterService;

    @Override
    public IParamterService getBaseService() {
        return paramterService;
    }
}
