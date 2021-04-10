package com.orient.collab.business.projectCore.extensions.interceptors;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.CollabFlowInfoBusiness;
import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessMarker;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.CollabFlowInfo;
import com.orient.collab.model.Plan;
import com.orient.collab.model.StatefulModel;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.orient.collab.config.CollabConstants.*;

/**
 * SubmitTaskOrPlanInterceptor
 *
 * @author Seraph
 *         2016-08-18 上午9:11
 */
@CollabProcessMarker(order = 1, processType = {ProcessType.SUBMIT}, models = {PLAN, TASK})
public class SubmitTaskOrPlanInterceptor implements CollabProcessInterceptor {

    @Autowired
    private CollabFlowInfoBusiness collabFlowInfoBusiness;

    @Autowired
    ISqlEngine orientSqlEngine;

    @Override
    public boolean preHandle(StatefulModel statefulModel, String modelName, ProcessType processType) throws Exception {

        //所绑定流程是否已经已经完成
        CollabFlowInfo collabFlowInfo = this.collabFlowInfoBusiness.getCurrentFlowInfoByModelNameAndDataId(modelName, statefulModel.getId());
        if (!collabFlowInfo.isEmpty()) {
            //不存在流程实例
            if (StringUtil.isEmpty(collabFlowInfo.getPiId()) && !StringUtil.isEmpty(collabFlowInfo.getPdId())) {
                throw new CollabFlowControlException("有子任务未完成，无法提交");
            } else if (!STATUS_FINISHED.equals(collabFlowInfo.getFlowStatus())) {
                throw new CollabFlowControlException("有子任务未完成或未忽略,无法提交");
            }
        }
        //check son plans has finished all
        if (CollabConstants.PLAN.equals(modelName)) {
            List<Plan> sonPlans = orientSqlEngine.getTypeMappingBmService().get(Plan.class, new CustomerFilter(Plan.PAR_PLAN_ID, EnumInter.SqlOperation.Equal, statefulModel.getId()));
            if (!CommonTools.isEmptyList(sonPlans)) {
                Set<String> status = new HashSet<>();
                sonPlans.forEach(sonPlan -> status.add(sonPlan.getStatus()));
                if (status.size() > 1 || !STATUS_FINISHED.equals(status.iterator().next())) {
                    throw new CollabFlowControlException("子计划未完成,无法提交");
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(StatefulModel statefulModel, String modelName, ProcessType processType, Object processResult) throws Exception {

    }

    @Autowired
    private ISqlEngine sqlEngine;
}
