package com.orient.log.aop;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.sys.SysLog;
import com.orient.sysmodel.service.sys.ISysLogService;
import com.orient.sysmodel.service.sys.impl.SysLogService;
import com.orient.web.form.engine.FreemarkEngine;
import com.orient.web.util.TDMParamter;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.io.IOException;

public class LogExecutor implements Runnable {
    private Log logger = LogFactory.getLog(LogExecutor.class);
    private LogHolder logHolder;

    private FreemarkEngine freemarkEngine;
    private ISysLogService sysLogService;

    public void setLogHolders(LogHolder logHolder) {
        this.logHolder = logHolder;
        this.sysLogService = (ISysLogService) OrientContextLoaderListener.Appwac.getBean(SysLogService.class);
        this.freemarkEngine = (FreemarkEngine) OrientContextLoaderListener.Appwac.getBean(FreemarkEngine.class);
    }

    private void doLog() throws TemplateException, IOException {
        SysLog log = logHolder.getSyslog();
        if (logHolder.isNeedParse()) {
            //转化
            String detail = freemarkEngine.parseByStringTemplate(logHolder.getParseDataModel(), log.getOpRemark());
            log.setOpRemark(detail);
        }
        ConfigurableWebApplicationContext webApplicationContext = (ConfigurableWebApplicationContext) OrientContextLoaderListener.Appwac;
        HibernateTransactionManager transactionManager = (HibernateTransactionManager) webApplicationContext.getBean("transactionManager");
        new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                sysLogService.save(log);
            }
        });
    }

    @Override
    public void run() {
        try {
            if(TDMParamter.getIsLogOn()) {//日志开关开启则记录日志信息
                doLog();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

}
