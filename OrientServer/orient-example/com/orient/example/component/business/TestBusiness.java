package com.orient.example.component.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.projectCore.ProjectEngine;
import com.orient.collab.business.projectCore.cmd.CommandService;
import com.orient.collab.business.projectCore.cmd.concrete.GetAllSubPlansCmd;
import com.orient.collab.business.projectCore.cmd.concrete.GetChildTaskIdsCascade;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.*;
import com.orient.sysmodel.domain.collab.CollabPrjModelRelationEntity;
import com.orient.sysmodel.domain.template.CollabTemplate;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.service.collab.ICollabPrjModelRelationService;
import com.orient.sysmodel.service.template.ICollabTemplateService;
import com.orient.template.business.TemplateMngBusiness;
import com.orient.utils.CommonTools;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.springmvcsupport.exception.OrientBaseAjaxException;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-20 9:41
 */
@Service
public class TestBusiness extends BaseBusiness {

    @Autowired
    ICollabPrjModelRelationService collabPrjModelRelationService;

    @Autowired
    ICollabTemplateService collabTemplateService;

    @Autowired
    TemplateMngBusiness templateMngBusiness;

    @Autowired
    private ProjectEngine projectEngine;

    @Autowired
    private CommandService commandService;

    /**
     * @param modelId
     * @param dataId
     * @return ?????????????????? ????????? ?????????????????????
     */
    public String createPrjAndBindRelation(Long modelId, Long dataId) {

        String currentUser = UserContextUtil.getUserId();
        //1.?????????????????????????????? ??????
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId.toString(), EnumInter.BusinessModelEnum.Table);
        Map<String, String> dataMap = orientSqlEngine.getBmService().createModelQuery(businessModel).findById(dataId.toString());
        businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataMap, true);
        String pkShowValue = getDisplayData(businessModel, dataMap);
        //2.??????????????????
        Project project = new Project();
        project.setName(pkShowValue + "_????????????");
        project.setPrincipal(currentUser);
        project.setStatus(CollabConstants.STATUS_UNSTARTED);
        List<Directory> firstLevel = orientSqlEngine.getTypeMappingBmService().get(Directory.class, new CustomerFilter("subDirId", EnumInter.SqlOperation.Equal, "-1"));
        if (CommonTools.isEmptyList(firstLevel)) {
            throw new OrientBaseAjaxException("", "???????????????????????????");
        } else {
            project.setParDirId(firstLevel.get(0).getId());
        }
        //
        String prjId = orientSqlEngine.getTypeMappingBmService().insert(project);
        String prjModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Project.class);
        IBusinessModel prjModel = businessModelService.getBusinessModelById(currentUser, prjModelId, null, EnumInter.BusinessModelEnum.Table);
        ProjectTreeNodeStrategy nodeStrategy = ProjectTreeNodeStrategy.fromString(prjModel.getMatrix().getName());
        Map<String, String> prjDataMap = orientSqlEngine.getBmService().createModelQuery(prjModel).findById(prjId);
        nodeStrategy.createDefaultRole(prjModel, prjDataMap);

        //3.bind relation
        CollabPrjModelRelationEntity prjModelRelationEntity = new CollabPrjModelRelationEntity();
        prjModelRelationEntity.setModelId(modelId.toString());
        prjModelRelationEntity.setDataId(dataId.toString());
        prjModelRelationEntity.setPrjId(prjId);
        prjModelRelationEntity.setPrjModelId(prjModelId);
        collabPrjModelRelationService.save(prjModelRelationEntity);
        return null;
    }

    public String createPrjAndBindRelationWithTemplate(Long modelId, Long dataId) {
        String currentUser = UserContextUtil.getUserId();
        //?????????????????????????????? ??????
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId.toString(), EnumInter.BusinessModelEnum.Table);
        Map<String, String> dataMap = orientSqlEngine.getBmService().createModelQuery(businessModel).findById(dataId.toString());
        businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataMap, true);
        String pkShowValue = getDisplayData(businessModel, dataMap);
        String dirId = "";
        List<Directory> firstLevel = orientSqlEngine.getTypeMappingBmService().get(Directory.class, new CustomerFilter("subDirId", EnumInter.SqlOperation.Equal, "-1"));
        if (CommonTools.isEmptyList(firstLevel)) {
            throw new OrientBaseAjaxException("", "???????????????????????????");
        } else {
            dirId = firstLevel.get(0).getId();
        }

        //????????????????????????
        List<CollabTemplate> templateList = collabTemplateService.list(Restrictions.eq(CollabTemplate.NAME, "??????????????????"), Order.desc("id"));
        if (CommonTools.isEmptyList(templateList)) {
            throw new OrientBaseAjaxException("", "?????????????????????????????????");
        } else {
            //???????????????
            CollabTemplate collabTemplate = templateList.get(0);
            //?????????????????????
            String parentModelName = CollabConstants.DIRECTORY;
            AjaxResponseData<CollabTemplateNode> retVal = templateMngBusiness.importBmTemplate(parentModelName, dirId, collabTemplate.getId().toString(), pkShowValue);
            //????????????prjId
            String prjId = retVal.getResults().getNewDataId();
            //????????????
            fixDate(prjId);
            //bind relation
            String prjModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Project.class);
            CollabPrjModelRelationEntity prjModelRelationEntity = new CollabPrjModelRelationEntity();
            prjModelRelationEntity.setModelId(modelId.toString());
            prjModelRelationEntity.setDataId(dataId.toString());
            prjModelRelationEntity.setPrjId(prjId);
            prjModelRelationEntity.setPrjModelId(prjModelId);
            collabPrjModelRelationService.save(prjModelRelationEntity);
            //????????????
            Project project = orientSqlEngine.getTypeMappingBmService().getById(Project.class, prjId);
            this.projectEngine.startProject(project);
        }

        return null;
    }

    /**
     * ????????????
     *
     * @param prjId
     */
    private void fixDate(String prjId) {
        Project project = orientSqlEngine.getTypeMappingBmService().getById(Project.class, prjId);
        try {
            String planedStartDate = project.getPlannedStartDate();
            String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            if (!now.equals(planedStartDate)) {
                int step = Integer.valueOf(CommonTools.calculateBlank(planedStartDate, now));
                updateDate(project, step);
                List<Plan> plans = commandService.execute(new GetAllSubPlansCmd(project, orientSqlEngine));
                if (!CommonTools.isEmptyList(plans)) {
                    plans.forEach(plan -> updateDate(plan, step));
                    List<String> taskIds = commandService.execute(new GetChildTaskIdsCascade(CollabConstants.PLAN, CommonTools.list2String(plans.stream().map(Plan::getId).collect(Collectors.toList())), null));
                    List<Task> tasks = orientSqlEngine.getTypeMappingBmService().get(Task.class, new CustomerFilter("id", EnumInter.SqlOperation.In, CommonTools.list2String(taskIds)));
                    tasks.forEach(task -> updateDate(task, step));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDate(StatefulModel statefulModel, int step) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date planedStartDate = simpleDateFormat.parse(statefulModel.getPlannedStartDate());
            Date planedEndDate = simpleDateFormat.parse(statefulModel.getPlannedEndDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(planedStartDate);
            calendar.add(Calendar.DAY_OF_WEEK, step);
            statefulModel.setPlannedStartDate(simpleDateFormat.format(calendar.getTime()));
            calendar.setTime(planedEndDate);
            calendar.add(Calendar.DAY_OF_WEEK, step);
            statefulModel.setPlannedEndDate(simpleDateFormat.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orientSqlEngine.getTypeMappingBmService().update(statefulModel);
    }
}
