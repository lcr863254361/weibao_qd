package com.orient.collab.business.projectCore;

import com.orient.collab.business.projectCore.cmd.CommandService;
import com.orient.collab.business.projectCore.cmd.concrete.*;
import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptorMng;
import com.orient.collab.business.projectCore.extensions.util.ThreadParamaterHolder;
import com.orient.collab.business.projectCore.task.DailyPlanCheckTask;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collab.model.StatefulModel;
import com.orient.collab.model.Task;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.web.base.CommonResponseData;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.orient.collab.business.projectCore.constant.ProjectCoreConstant.*;
import static com.orient.collab.config.CollabConstants.*;


/**
 * an implementation of {@link ProjectEngine}
 *
 * @author Seraph
 *         2016-07-20 下午4:21
 */
@Component
public class ProjectEngineImpl implements ProjectEngine, ApplicationListener<ContextRefreshedEvent>, FactoryBean<ProjectEngine> {

    private ScheduledExecutorService planDateTriggerExecutor = Executors.newScheduledThreadPool(1);//triggered every day at 00:00 AM

    private static final int HOUR_TO_CHECK_PLAN = 6;

    @Override
    public CommonResponseData startProject(Project project) throws CollabFlowControlException {
        try {
            CommonResponseData retV = new CommonResponseData(false, "");

            this.commandService.execute(new StartPlansOfProjectCmd(project));
            this.commandService.execute(new ChangeStateCmd(project, STATUS_PROCESSING, this.orientSqlEngine));
            this.commandService.execute(new ChangeActualStartDateCmd(project, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), this.orientSqlEngine));
            retV.setSuccess(true);
            return retV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CollabFlowControlException(e.getMessage());
        }
    }

    @Override
    public CommonResponseData submitProject(Project project) throws CollabFlowControlException {
        try {
            CommonResponseData retV = this.commandService.execute(new CheckCanProjectSubmitCmd(project));
            if (!retV.isSuccess()) {
                return retV;
            }

            //TODO check audit flow status, start an audit flow if none exists
            //boolean auditFlowStarted = this.commandService.execute(new StartAuditFlowOfProjectSubmitCmd(project));
            this.commandService.execute(new ChangeStateCmd(project, STATUS_FINISHED, this.orientSqlEngine));
            this.commandService.execute(new ChangeActualEndDateCmd(project, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), this.orientSqlEngine));
            this.orientSqlEngine.getTypeMappingBmService().update(project);
            retV.setSuccess(true);
            retV.setMsg("项目提交成功");
            return retV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CollabFlowControlException(e.getMessage());
        }
    }

    //TODO
    //启动流程 error:应选择一条执行路线;
    @Override
    public CommonResponseData startCollabFlow(StatefulModel sm) throws CollabFlowControlException {

        try {
            CommonResponseData retV = new CommonResponseData(true, "");

            if (null != ThreadParamaterHolder.get(ProcessDefinition.class)) {
                ProcessInstance pi = this.commandService.execute(new StartCollabFlowCmd(sm,
                        ThreadParamaterHolder.get(ProcessDefinition.class).getId()));
                retV.setMsg(pi.getId());
            }
            return retV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CollabFlowControlException(e.getMessage());
        }
    }

    /**
     * ignore flow
     * 当前节点和递归所有状态为"PROCESSING"的子节点,修改其状态为"SUSPENDED"
     *
     * @param sm
     * @return
     */
    @Override
    public CommonResponseData suspendCollabFlow(StatefulModel sm) throws CollabFlowControlException {
        try {
            CommonResponseData retV = new CommonResponseData(false, "");
            boolean updateSuccess = this.commandService.execute(new ChangeStateCascadeCmd(sm, STATUS_PROCESSING, STATUS_SUSPENDED, true));
            retV.setSuccess(updateSuccess);
            if (!updateSuccess) {
                retV.setMsg("暂停失败,更新状态失败");
            }
            return retV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CollabFlowControlException(e.getMessage());
        }
    }

    /**
     * ignore flow
     * 1)父节点为暂停中不可恢复
     * 2)递归设置本节点和所有状态为"SUSPENDED"的子节点状态为"PROCESSING"
     *
     * @param sm
     * @return
     */
    @Override
    public CommonResponseData resumeCollabFlow(StatefulModel sm) throws CollabFlowControlException {
        CommonResponseData retV = new CommonResponseData(false, "");
        try {
            boolean updateSuccess = this.commandService.execute(new ChangeStateCascadeCmd(sm, STATUS_SUSPENDED, STATUS_PROCESSING, true));
            retV.setSuccess(updateSuccess);
            if (!updateSuccess) {
                retV.setMsg("恢复失败,更新状态失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CollabFlowControlException(e.getMessage());
        }
        return retV;
    }

    @Override
    public CommonResponseData closeCollabFlow(StatefulModel sm) throws CollabFlowControlException {
        CommonResponseData retV = new CommonResponseData(true, "");
        try {
            boolean deleteSuccess = this.commandService.execute(new CloseCollabFlowCmd(sm, ThreadParamaterHolder.get(ProcessInstance.class)));
            if (!deleteSuccess) {
                retV.setMsg("关闭流程失败");
            }
            this.commandService.execute(new ChangeStateCascadeCmd(sm, STATUS_SUSPENDED, STATUS_PROCESSING, true));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CollabFlowControlException(e.getMessage());
        }
        return retV;
    }

    @Override
    public CommonResponseData submitPlan(Plan plan) throws CollabFlowControlException {
        CommonResponseData retV = new CommonResponseData(false, "");
        try {
            this.commandService.execute(new SubmitPlanCmd(plan));
            Deque<Map<String, String>> parentInfo = ProjectTreeNodeStrategy.PlanNode.getParentRouteInfo(null, plan);
            Project project = this.orientSqlEngine.getTypeMappingBmService().getById(Project.class, parentInfo.pop().get("ID"));
            this.commandService.execute(new StartPlansOfProjectCmd(project));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CollabFlowControlException(e.getMessage());
        }

        retV.setSuccess(true);
        return retV;
    }

    @Override
    public CommonResponseData submitTask(Task task, String transition) throws CollabFlowControlException {
        CommonResponseData retV = new CommonResponseData(false, "");
        try {
            this.commandService.execute(new SubmitCollabTaskCmd(task, transition));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CollabFlowControlException(e.getMessage());
        }

        retV.setSuccess(true);
        return retV;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            return;
        }

        long timeToDelay;
        ZonedDateTime zdt = ZonedDateTime.now();
        if (zdt.getHour() < HOUR_TO_CHECK_PLAN) {
            timeToDelay = (HOUR_TO_CHECK_PLAN - zdt.getHour()) * 60 - zdt.getMinute();
        } else {
            //start imediately and push next to tomorrow
            Executors.newFixedThreadPool(1).execute(new DailyPlanCheckTask());
            timeToDelay = (24 - zdt.getHour() + HOUR_TO_CHECK_PLAN) * 60 - zdt.getMinute();
        }

        planDateTriggerExecutor.scheduleAtFixedRate(new DailyPlanCheckTask(), timeToDelay, 24 * 60, TimeUnit.MINUTES);
    }

    @Override
    public ProjectEngine getObject() throws Exception {
        return (ProjectEngine) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{ProjectEngine.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                StatefulModel sm = (StatefulModel) args[0];
                String modelName;
                if (sm instanceof Project) {
                    modelName = PROJECT;
                } else if (sm instanceof Plan) {
                    modelName = PLAN;
                } else if (sm instanceof Task) {
                    modelName = TASK;
                } else {
                    throw new IllegalArgumentException("wrong model");
                }

                ProcessType processType;
                if (method.getName().startsWith(PROCESS_TYPE_START)) {
                    processType = ProcessType.START;
                } else if (method.getName().startsWith(PROCESS_TYPE_SUBMIT)) {
                    processType = ProcessType.SUBMIT;
                } else if (method.getName().startsWith(PROCESS_TYPE_SUSPEND)) {
                    processType = ProcessType.SUSPEND;
                } else if (method.getName().startsWith(PROCESS_TYPE_RESUME)) {
                    processType = ProcessType.RESUME;
                } else if (method.getName().startsWith(PROCESS_TYPE_CLOSE)) {
                    processType = ProcessType.CLOSE;
                } else {
                    return method.invoke(ProjectEngineImpl.this, args);
                }

                List<CollabProcessInterceptor> interceptorList = collabProcessInterceptorMng.getInterceptors(modelName, processType);

                for (CollabProcessInterceptor interceptor : interceptorList) {
                    boolean proceed = interceptor.preHandle(sm, modelName, processType);
                    if (!proceed) {
                        break;
                    }
                }

                Object retV = method.invoke(ProjectEngineImpl.this, args);

                for (CollabProcessInterceptor interceptor : interceptorList) {
                    interceptor.afterCompletion(sm, modelName, processType, retV);
                }

                return retV;
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return ProjectEngine.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Autowired
    private ISqlEngine orientSqlEngine;
    @Autowired
    private CommandService commandService;
    @Autowired
    private CollabProcessInterceptorMng collabProcessInterceptorMng;
}
