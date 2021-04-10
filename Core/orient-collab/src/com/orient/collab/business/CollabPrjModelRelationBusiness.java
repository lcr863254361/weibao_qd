package com.orient.collab.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.CollabFlowBindDataVO;
import com.orient.collab.model.ModelDataDescVO;
import com.orient.config.ConfigInfo;
import com.orient.flow.business.ProcessInstanceBusiness;
import com.orient.sysmodel.domain.collab.CollabPrjModelRelationEntity;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.service.collab.ICollabPrjModelRelationService;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class CollabPrjModelRelationBusiness extends BaseHibernateBusiness<CollabPrjModelRelationEntity> {

    @Autowired
    ICollabPrjModelRelationService collabPrjModelRelationService;

    @Autowired
    IModelGridViewService modelGridViewService;

    @Autowired
    ProcessInstanceBusiness processInstanceBusiness;

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Override
    public ICollabPrjModelRelationService getBaseService() {
        return collabPrjModelRelationService;
    }

    /**
     * @param flowTaskId   协同流程任务ID
     * @param taskModelId  协同任务模型ID
     * @param taskId       协同任务数据ID
     * @param templateName 绑定的模板名称
     * @return 根据协同任务信息 获取系统任务所属项目 或所属计划所绑定的主数据源信息
     */
    public CollabFlowBindDataVO getCollabFlowBindData(String flowTaskId, String taskModelId, String taskId, String templateName) {

        //根据模板名称 获取 模板ID
        String templateId = "";
        List<ModelGridViewEntity> modelGridViewEntities = modelGridViewService.list(Restrictions.eq("name", templateName),Restrictions.eq("isvalid",1l));
        if (!CommonTools.isEmptyList(modelGridViewEntities)) {
            templateId = modelGridViewEntities.get(0).getId().toString();
        }
        //获取绑定的模型ID 以及 数据ID
        ModelDataDescVO collabRootModelData = new ModelDataDescVO();
        getCollabRootModelData(taskModelId, taskId, collabRootModelData);
        String bindModelId = "";
        String bindDataId = "";
        if (!collabRootModelData.isEmpy()) {
            List<CollabPrjModelRelationEntity> collabPrjModelRelationEntities = collabPrjModelRelationService.list(Restrictions.eq("prjModelId", collabRootModelData.getModelId()), Restrictions.eq("prjId", collabRootModelData.getDataId()));
            if (!CommonTools.isEmptyList(collabPrjModelRelationEntities)) {
                bindModelId = collabPrjModelRelationEntities.get(0).getModelId();
                bindDataId = collabPrjModelRelationEntities.get(0).getDataId();
            } else {
                throw new OrientBaseAjaxException("", "未找到绑定的模型，请联系管理员！");
            }
        }
        //获取主键显示值信息
        IBusinessModel model = businessModelService.getBusinessModelById(bindModelId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> dataMap = orientSqlEngine.getBmService().createModelQuery(model).findById(bindDataId);
        List<IBusinessColumn> refShowColumns = model.getRefShowColumns();
        List<String> showList = new ArrayList<>();
        refShowColumns.forEach(iBusinessColumn -> showList.add(dataMap.get(iBusinessColumn.getS_column_name())));

        CollabFlowBindDataVO collabFlowBindData = new CollabFlowBindDataVO(bindModelId, bindDataId, templateId);
        String refShowName = CommonTools.list2String(showList, "-");
        collabFlowBindData.setRefShowName(refShowName);

        //TODO 增加定制入口 方便定制业务在extraData中填充定制数据
        return collabFlowBindData;
    }

    public void getCollabRootModelData(String collabModelId, String collabDataId, ModelDataDescVO retVal) {
        String collabSchemaId = ConfigInfo.COLLAB_SCHEMA_ID;
        String[] dicitionary = new String[]{CollabConstants.TASK, CollabConstants.PLAN, CollabConstants.PROJECT};
        IBusinessModel collabModel = businessModelService.getBusinessModelById(collabModelId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> dataMap = orientSqlEngine.getBmService().createModelQuery(collabModel).findById(collabDataId);
        Boolean catched = false;
        for (String fk : dicitionary) {
            String value = dataMap.get(fk + "_" + collabSchemaId + "_ID");
            if (!StringUtil.isEmpty(value)) {
                String parentModelId = businessModelService.getBusinessModelBySName(fk, collabSchemaId, EnumInter.BusinessModelEnum.Table).getId();
                getCollabRootModelData(parentModelId, value, retVal);
                catched = true;
            }
        }
        if (!catched) {
            retVal.setModelId(collabModel.getId());
            retVal.setDataId(dataMap.get("ID"));
        }
    }
}
