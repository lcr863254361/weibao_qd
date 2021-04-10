package com.orient.example.component.collabextend;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessMarker;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collab.model.StatefulModel;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabPrjModelRelationEntity;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.service.collab.ICollabPrjModelRelationService;
import com.orient.utils.CommonTools;
import com.orient.web.springmvcsupport.exception.OrientBaseAjaxException;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-20 15:13
 */
@CollabProcessMarker(models = CollabConstants.PLAN, processType = ProcessType.SUBMIT, order = 0)
public class TestPlanInterceptor implements CollabProcessInterceptor {

    @Override
    public boolean preHandle(StatefulModel statefulModel, String modelName, ProcessType processType) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(StatefulModel statefulModel, String modelName, ProcessType processType, Object processResult) throws Exception {
        if (statefulModel.getName().equals("试验策划")) {
            String planId = statefulModel.getId();
            Plan plan = orientSqlEngine.getTypeMappingBmService().getById(Plan.class, planId);
            String projectId = plan.getParProjectId();
            //get all plans
            List<Plan> plans = orientSqlEngine.getTypeMappingBmService().get(Plan.class, new CustomerFilter("parProjectId", EnumInter.SqlOperation.Equal, projectId));
            Plan nextPlan = plans.stream().filter(tmp -> "试验准备".equals(tmp.getName())).findAny().get();
            //
            List<CollabPrjModelRelationEntity> collabPrjModelRelationEntities = collabPrjModelRelationService.list(Restrictions.eq("prjModelId", orientSqlEngine.getTypeMappingBmService().getModelId(Project.class)), Restrictions.eq("prjId", projectId));
            if (!CommonTools.isEmptyList(collabPrjModelRelationEntities)) {
                String mainModelId = collabPrjModelRelationEntities.get(0).getModelId();
                String mainDataId = collabPrjModelRelationEntities.get(0).getDataId();
                //获取试验大纲信息
                String currentUserId = UserContextUtil.getUserId();
                IBusinessModel mainModel = businessModelService.getBusinessModelById(mainModelId, EnumInter.BusinessModelEnum.Table);
                IBusinessModel refModel = businessModelService.getBusinessModelBySName(currentUserId, "T_DG", mainModel.getSchema().getId(), EnumInter.BusinessModelEnum.Table);
                mainModel.appendCustomerFilter(new CustomerFilter("ID", EnumInter.SqlOperation.Equal, mainDataId));
                businessModelService.initModelRelation(refModel, mainModel, currentUserId);
                List<Map<String, String>> testPlans = orientSqlEngine.getBmService().createModelQuery(refModel).list();
                if (!CommonTools.isEmptyList(testPlans)) {
                    //取第一个
                    Map<String, String> testPlan = testPlans.get(0);
                    businessModelService.dataChangeModel(orientSqlEngine, refModel, testPlan, true);
                    String fileDesc = testPlan.get("C_FJ_" + refModel.getId());
                    DataObjectEntity dataObjectEntity = new DataObjectEntity();
                    dataObjectEntity.setDataobjectname("试验大纲");
                    dataObjectEntity.setValue(fileDesc);
                    //文件类型
                    dataObjectEntity.setDatatypeId("5");
                    dataObjectEntity.setIsref(1);
                    dataObjectEntity.setIsglobal(1);
                    dataObjectEntity.setModelid(orientSqlEngine.getTypeMappingBmService().getModelId(Plan.class));
                    dataObjectEntity.setDataid(nextPlan.getId());
                    dataObjectBusiness.createTopDataObj(dataObjectEntity);
                } else {
                    throw new OrientBaseAjaxException("", "请至少上传一份试验大纲");
                }
            }
        }
    }

    @Autowired
    ISqlEngine orientSqlEngine;

    @Autowired
    DataObjectBusiness dataObjectBusiness;

    @Autowired
    ICollabPrjModelRelationService collabPrjModelRelationService;

    @Autowired
    public IBusinessModelService businessModelService;

}
