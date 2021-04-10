package com.orient.sysman.scheduler;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sysman.bussiness.SysLogBusiness;
import com.orient.sysmodel.domain.sys.SysLog;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.sys.ISysLogService;
import com.orient.utils.CommonTools;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created by qjs on 2017/1/20.
 */
public class BackUpLogJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        BackUpLogJob self = this;
        ConfigurableWebApplicationContext webApplicationContext = (ConfigurableWebApplicationContext) OrientContextLoaderListener.Appwac;
        HibernateTransactionManager transactionManager = (HibernateTransactionManager) webApplicationContext.getBean("transactionManager");

        new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                SysLogBusiness sysLogBusiness = (SysLogBusiness)webApplicationContext.getBean("sysLogBusiness");
                ISysLogService sysLogService = (ISysLogService)webApplicationContext.getBean("sysLogService");
                //首先备份日志
                String filePath = "";
                int pageNum = 100;
                int count = sysLogService.count();
                if (count > pageNum) {
                    int pageSize = count % pageNum == 0 ? count / pageNum : count / pageNum + 1;
                    String timeStap = CommonTools.FormatDate(new Date(), "yyyyMMddHHmmssS");
                    PageBean pageBean = new PageBean();
                    pageBean.setTotalCount(count);
                    pageBean.setPageCount(pageSize);
                    pageBean.setRows(pageNum);
                    for (int i = 0; i < pageSize; i++) {
                        // 起始页
                        int start = i * pageNum;
                        int end = (i + 1) * pageNum;
                        if (end > count) end = count;
                        pageBean.setPage(i + 1);
                        List<SysLog> logs = sysLogService.listByPage(pageBean);
                        filePath = sysLogBusiness.exportTxt(logs, true, i, pageSize, timeStap);
                    }
                }

                //清空CWM_SYS_LOG表
                String tableName = "CWM_SYS_LOG";
                StringBuffer truncateSqlStr = new StringBuffer();
                truncateSqlStr.append("TRUNCATE TABLE " + tableName);
                MetaDAOFactory metaDaoFactory = OrientContextLoaderListener.Appwac.getBean(MetaDAOFactory.class);
                metaDaoFactory.getJdbcTemplate().execute(truncateSqlStr.toString());
            }
        });

    }
}
