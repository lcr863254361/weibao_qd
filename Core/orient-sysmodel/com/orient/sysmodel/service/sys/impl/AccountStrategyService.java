package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IAccountStrategyDao;
import com.orient.sysmodel.domain.sys.CwmSysAccountStrategyEntity;
import com.orient.sysmodel.domain.user.PasswordHistory;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IAccountStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 账户策略管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class AccountStrategyService extends BaseService<CwmSysAccountStrategyEntity> implements IAccountStrategyService {

    @Autowired
    IAccountStrategyDao accountStrategyDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.accountStrategyDao;
    }

}
