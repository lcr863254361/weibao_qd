package com.orient.sysman.scheduler;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysman.bussiness.DataBackBusiness;
import com.orient.sysman.event.BackUpEvent;
import com.orient.sysman.eventtParam.BackUpEventParam;
import com.orient.sysmodel.domain.sys.CwmBackEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-30 15:16
 */
public class BackUpJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        BackUpJob self= this;
        ConfigurableWebApplicationContext webApplicationContext = (ConfigurableWebApplicationContext) OrientContextLoaderListener.Appwac;
        HibernateTransactionManager transactionManager = (HibernateTransactionManager) webApplicationContext.getBean("transactionManager");
        new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, webApplicationContext.getServletContext());
                CwmBackEntity formValue = new CwmBackEntity();
                formValue.setRemark("定时备份");
                BackUpEventParam eventParam = new BackUpEventParam(formValue);
                OrientContextLoaderListener.Appwac.publishEvent(new BackUpEvent(self, eventParam));
                DataBackBusiness dataBackBusiness = (DataBackBusiness) webApplicationContext.getBean("dataBackBusiness");
                dataBackBusiness.save(formValue);
            }
        });
    }
}
