package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IParamterDao;
import com.orient.sysmodel.domain.sys.Parameter;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IParamterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 参数管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class ParamterService extends BaseService<Parameter> implements IParamterService {

    @Autowired
    IParamterDao paramterDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.paramterDao;
    }
}
