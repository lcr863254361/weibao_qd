package com.orient.collab.business.projectCore.task;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.projectCore.cmd.CommandService;
import com.orient.collab.business.projectCore.cmd.concrete.StartPlansOfProjectCmd;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Project;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.util.List;

import static com.orient.collab.config.CollabConstants.STATUS_PROCESSING;

/**
 * perform a daily plan check task, i.e., check whether a plan's planed start date has arrived.
 *
 * @author Seraph
 *         2016-07-21 上午8:47
 */
public class DailyPlanCheckTask implements Runnable {
    @Override
    public void run() {

        ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
        CommandService commandService = OrientContextLoaderListener.Appwac.getBean(CommandService.class);
        List<Project> projects = sqlEngine.getTypeMappingBmService().get(Project.class, new CustomerFilter("status", EnumInter.SqlOperation.Equal, STATUS_PROCESSING));
        try {
            ConfigurableWebApplicationContext webApplicationContext = (ConfigurableWebApplicationContext) OrientContextLoaderListener.Appwac;
            HibernateTransactionManager transactionManager = (HibernateTransactionManager) webApplicationContext.getBean("transactionManager");
            new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    for (Project project : projects) {
                        try {
                            if(project.getStatus().equals(CollabConstants.STATUS_UNSTARTED)){
                                commandService.execute(new StartPlansOfProjectCmd(project));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }
}
