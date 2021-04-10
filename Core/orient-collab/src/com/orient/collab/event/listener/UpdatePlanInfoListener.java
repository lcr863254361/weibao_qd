package com.orient.collab.event.listener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.ProjectTreeNodeEditEvent;
import com.orient.collab.event.ProjectTreeNodeEditEventParam;
import com.orient.collab.model.GanttPlan;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.sysmodel.domain.user.User;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.orient.collab.config.CollabConstants.DISPLAY_ORDER_COL;
import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.TASK;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 * @create 2016-10-26 14:21
 */
@Component
public class UpdatePlanInfoListener extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return ProjectTreeNodeEditEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }

        OrientEvent orientEvent = (OrientEvent) event;
        ProjectTreeNodeEditEventParam params = (ProjectTreeNodeEditEventParam) orientEvent.getParams();

        String modelId = params.getModelId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);

        if(!CollabConstants.PROJECT.equals(businessModel.getMatrix().getName())){
            return;
        }
        Map oriDataMap = params.getOriDataMap();
        Map newDataMap = params.getDataMap();

        String oriPlanedStartDate = CommonTools.Obj2String(oriDataMap.get("PLANNED_START_DATE_" + modelId));
        String oriPlanedEndDate = CommonTools.Obj2String(oriDataMap.get("PLANNED_END_DATE_" + modelId));
        String newPlanedStartDate = CommonTools.Obj2String(newDataMap.get("PLANNED_START_DATE_" + modelId));
        String newPlanedEndDate = CommonTools.Obj2String(newDataMap.get("PLANNED_END_DATE_" + modelId));

        if("".equals(oriPlanedStartDate) && "".equals(oriPlanedEndDate)){
            return;
        }

        if(newPlanedStartDate.equals(oriPlanedStartDate) && newPlanedEndDate.equals(oriPlanedEndDate)){
            return;
        }

        IBusinessModel planModel = businessModelService.getBusinessModelBySName(PLAN, CollabConstants.COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        List<Map> subPlans = UtilFactory.newArrayList();
        iteratorSubPlan(planModel, businessModel, CommonTools.Obj2String(newDataMap.get("ID")), true, subPlans);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date projectPlannedStartDate = dateFormat.parse(newPlanedStartDate);
            Date projectPlannedEndDate = dateFormat.parse(newPlanedEndDate);

            String planStartDateField = "ACTUAL_START_DATE_" + planModel.getId();
            String planEndDateField = "ACTUAL_END_DATE_" + planModel.getId();
            for(Map res : subPlans){
                boolean updated = false;

                String planPlanedStartDate = CommonTools.Obj2String(res.get(planStartDateField));
                if(CommonTools.isNullString(planPlanedStartDate)){
                    continue;
                }

                if(dateFormat.parse(planPlanedStartDate).before(projectPlannedStartDate)){
                    updated = true;
                    res.put(planStartDateField, newPlanedStartDate);
                }

                String planPlanedEndDate = CommonTools.Obj2String(res.get(planEndDateField));
                if(dateFormat.parse(planPlanedEndDate).after(projectPlannedEndDate)){
                    updated = true;
                    res.put(planEndDateField, newPlanedEndDate);
                }

                if(updated){
                    this.orientSqlEngine.getBmService().updateModelData(planModel, res, CommonTools.Obj2String(res.get("ID")));
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void iteratorSubPlan(IBusinessModel planBm, IBusinessModel projectBm, String parentId, boolean parentIsProject, List<Map> allSubPlans) {
        if(parentIsProject){
            planBm.setReserve_filter(" AND " + projectBm.getS_table_name() + "_ID = '" + parentId + "'");
        }else{
            planBm.setReserve_filter(" AND " + planBm.getS_table_name() + "_ID = '" + parentId + "'");
        }

        List<Map> planMaps = this.orientSqlEngine.getBmService().createModelQuery(planBm).list();

        for (Map planMap : planMaps) {
            allSubPlans.add(planMap);
            iteratorSubPlan(planBm, projectBm, CommonTools.Obj2String(planMap.get("ID")), false, allSubPlans);
        }
    }

    @Autowired
    @Qualifier("BusinessModelService")
    private IBusinessModelService businessModelService;
    @Autowired
    private ISqlEngine orientSqlEngine;
}
