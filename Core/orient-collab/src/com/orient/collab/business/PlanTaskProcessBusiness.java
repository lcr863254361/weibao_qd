package com.orient.collab.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.projectCore.ProjectEngine;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.business.projectCore.exception.StateIllegalException;
import com.orient.collab.model.StatefulModel;
import com.orient.collab.model.Task;
import com.orient.flow.business.FlowTaskBusiness;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.TASK;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * the plan and task process business
 *
 * @author Seraph
 *         2016-07-26 下午8:37
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class PlanTaskProcessBusiness extends BaseBusiness {

    public CommonResponseData start(String modelName, String dataId) throws StateIllegalException, CollabFlowControlException {
        return callProjectEngineMethod(modelName, dataId, "startCollabFlow");
    }

    public CommonResponseData submitPlan(String dataId) throws StateIllegalException, CollabFlowControlException {
        return callProjectEngineMethod(PLAN, dataId, "submitPlan");
    }

    public CommonResponseData submitTask(String dataId, String transitionName) throws StateIllegalException, CollabFlowControlException {
        return callProjectEngineMethod(TASK, dataId, "submitTask", transitionName);
    }

    public CommonResponseData suspend(String modelName, String dataId) throws StateIllegalException, CollabFlowControlException {
        return callProjectEngineMethod(modelName, dataId, "suspendCollabFlow");
    }

    public CommonResponseData resume(String modelName, String dataId) throws StateIllegalException, CollabFlowControlException {
        return callProjectEngineMethod(modelName, dataId, "resumeCollabFlow");
    }

    public CommonResponseData close(String modelName, String dataId) throws StateIllegalException, CollabFlowControlException {
        return callProjectEngineMethod(modelName, dataId, "closeCollabFlow");
    }

    public CommonResponseData updateCounterSignTaskAssignee(String piId, String assignee, String assigneeIds, String parModelName, String parDataId, String taskName) {
        CommonResponseData retV = this.flowTaskBusiness.setAssignee(piId, taskName, assignee);
        if(!retV.isSuccess()){
            return retV;
        }

        List<Task> tasks = this.orientSqlEngine.getTypeMappingBmService().get(Task.class,
                new CustomerFilter(PLAN.equals(parModelName)? Task.PAR_PLAN_ID : Task.PAR_PLAN_ID, EnumInter.SqlOperation.Equal, parDataId),
                new CustomerFilter(Task.NAME, EnumInter.SqlOperation.Equal, taskName));

        Task task = tasks.get(0);
        task.setPrincipal(assigneeIds);
        this.orientSqlEngine.getTypeMappingBmService().update(task);
        return new CommonResponseData(true, "");
    }

    private CommonResponseData callProjectEngineMethod(String modelName, String dataId, String operationType, Object... extraParamters) throws StateIllegalException, CollabFlowControlException {
        CommonResponseData retV = new CommonResponseData(false, "");
        try {
            Class modelCls = covertModelNameToClass(modelName);
            Class<?>[] parameterTypes = new Class<?>[1 + (extraParamters == null ? 0 : extraParamters.length)];
            parameterTypes[0] = StatefulModel.class;
            if (extraParamters != null) {
                for (int i = 0; i < extraParamters.length; i++) {
                    parameterTypes[i + 1] = extraParamters[i].getClass();
                }
            }
            Method m = ReflectionUtils.findMethod(ProjectEngine.class, operationType, null);

            Object[] parameters = new Object[parameterTypes.length];
            parameters[0] = this.orientSqlEngine.getTypeMappingBmService().getById(modelCls, dataId);
            if (extraParamters != null) {
                for (int i = 0; i < extraParamters.length; i++) {
                    parameters[i + 1] = extraParamters[i];
                }
            }

            retV = (CommonResponseData) ReflectionUtils
                    .invokeMethod(m, projectEngine, parameters);
        } catch (ClassNotFoundException e) {
            throw new CollabFlowControlException("不支持的模型类型");
        } catch (Exception e) {
            //转化为实际异常信息
            Throwable realException = e;
            while(realException instanceof UndeclaredThrowableException){
                realException = ((UndeclaredThrowableException) realException).getUndeclaredThrowable();
            }

            if (realException instanceof StateIllegalException) {
                throw (StateIllegalException) realException;
            } else if (realException instanceof CollabFlowControlException) {
                throw (CollabFlowControlException) realException;
            } else {
                throw e;
            }

        }
        return retV;
    }

    private Class covertModelNameToClass(String modelName) throws ClassNotFoundException {
        //去除CB前缀
        modelName = modelName.substring(3, modelName.length());
        String classFullName = "com.orient.collab.model." + modelName.substring(0, 1).toUpperCase() + modelName.substring(1).toLowerCase();
        Class c = ClassUtils.forName(classFullName, ClassUtils.getDefaultClassLoader());
        return c;
    }

    @Autowired
    private ProjectEngine projectEngine;
    @Autowired
    private FlowTaskBusiness flowTaskBusiness;
}
