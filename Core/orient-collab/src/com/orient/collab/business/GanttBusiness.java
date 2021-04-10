package com.orient.collab.business;

import com.orient.auditflow.business.AuditFlowInfoBusiness;
import com.orient.auditflow.config.AuditFlowStatus;
import com.orient.auditflow.config.AuditFlowType;
import com.orient.auditflow.model.AuditFlowInfo;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.event.*;
import com.orient.collab.model.*;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.*;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;

/**
 * the gantt business
 *
 * @author Seraph
 *         2016-07-18 上午9:54
 */
@Service
public class GanttBusiness extends BaseBusiness {

    /**
     * 得到一个plan的前驱Plan列表
     *
     * @param curPlanId 待查询plan id
     * @return
     */
    public List<Plan> getPredecessorPlans(String curPlanId) {

        List<GanttPlanDependency> dependencies = this.orientSqlEngine.getTypeMappingBmService().get(GanttPlanDependency.class,
                new CustomerFilter(GanttPlanDependency.FINISH_PLAN_ID, EnumInter.SqlOperation.Equal, curPlanId));

        List<Plan> plans = UtilFactory.newArrayList();

        for (GanttPlanDependency dependency : dependencies) {
            Plan plan = this.orientSqlEngine.getTypeMappingBmService().getById(Plan.class, dependency.getStartPlanId());
            if (plan != null) {
                plans.add(plan);
            }
        }

        return plans;
    }

    /**
     * 得到一个plan的后继Plan列表
     *
     * @param curPlanId 待查询plan id
     * @return
     */
    public List<Plan> getSuccessorPlans(String curPlanId) {

        List<GanttPlanDependency> dependencies = this.orientSqlEngine.getTypeMappingBmService().get(GanttPlanDependency.class,
                new CustomerFilter(GanttPlanDependency.START_PLAN_ID, EnumInter.SqlOperation.Equal, curPlanId));

        List<Plan> plans = UtilFactory.newArrayList();

        for (GanttPlanDependency dependency : dependencies) {
            Plan plan = this.orientSqlEngine.getTypeMappingBmService().getById(Plan.class, dependency.getFinishPlanId());
            if (plan != null) {
                plans.add(plan);
            }
        }

        return plans;
    }

    public List<GanttPlan> getSubPlansCascade(String parModelName, String parDataId) {

        IBusinessModel planBm = this.businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        IBusinessModel parBm = null;
        if (PLAN.equals(parModelName)) {
            parBm = planBm;
        } else {
            parBm = this.businessModelService.getBusinessModelBySName(parModelName, COLLAB_SCHEMA_ID, Table);
        }
        planBm.setReserve_filter(" AND " + parBm.getS_table_name() + "_ID = '" + parDataId + "'");

        List planAsMaps = this.orientSqlEngine.getBmService().createModelQuery(planBm).orderAsc("TO_NUMBER(" + DISPLAY_ORDER_COL + planBm.getId() + ")").list();
        List<GanttPlan> plans = BusinessDataConverter.convertMapListToBeanList(planBm, planAsMaps, GanttPlan.class, true);

        for (GanttPlan plan : plans) {
            iteratorPlanSubTree(planBm, plan);
            plan.setIconCls("icon-" + plan.getType());
        }
        return plans;
    }

    public List<GanttPlanDependency> getGanttPlanDependencies(String rootModelName, String rootDataId, boolean baseLine) {
        if (!PROJECT.equals(rootModelName)) {
            return UtilFactory.newArrayList();
        }

        IBusinessModel rootBm = this.businessModelService.getBusinessModelBySName(rootModelName, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planDependencyBm = this.businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        IBusinessModel baseLineBm = this.businessModelService.getBusinessModelBySName(BASE_LINE, COLLAB_SCHEMA_ID, Table);

        StringBuilder reserveFilter = new StringBuilder(" AND ")
                .append(rootBm.getS_table_name()).append("_ID = '").append(rootDataId).append("' ")
                .append(" AND ").append(baseLineBm.getS_table_name()).append("_ID");
        if (!baseLine) {
            reserveFilter.append(" is null ");
        } else {
            reserveFilter.append(" is not null ");
        }
        planDependencyBm.setReserve_filter(reserveFilter.toString());

        List planDependenciesAsMaps = this.orientSqlEngine.getBmService().createModelQuery(planDependencyBm).list();
        List<GanttPlanDependency> planDependencies = BusinessDataConverter
                .convertMapListToBeanList(planDependencyBm, planDependenciesAsMaps, GanttPlanDependency.class, true);
        return planDependencies;
    }

    private void iteratorPlanSubTree(IBusinessModel planBm, GanttPlan parentPlan) {
        planBm.setReserve_filter(" AND " + planBm.getS_table_name() + "_ID = '" + parentPlan.getId() + "'");
        if (!CommonTools.isNullString(parentPlan.getPrincipal())) {
            User user = this.UserService.findById(parentPlan.getPrincipal());
            parentPlan.setResourceName(user.getAllName());
        }

        List planAsMaps = this.orientSqlEngine.getBmService().createModelQuery(planBm).orderAsc(DISPLAY_ORDER_COL + planBm.getId()).list();
        if (planAsMaps.size() == 0) {
            parentPlan.setLeaf(true);
            return;
        }

        List<GanttPlan> plans = BusinessDataConverter.convertMapListToBeanList(planBm, planAsMaps, GanttPlan.class, true);
        parentPlan.setChildren(plans);
        for (GanttPlan plan : plans) {
            iteratorPlanSubTree(planBm, plan);
            plan.setIconCls("icon-" + plan.getType());
        }
    }

    /**
     * 创建/更新plan
     * 创建Plan完成后,已经添加了默认角色
     *
     * @param rootModelName
     * @param rootDataId
     * @param toCreatePlans
     * @return
     */
    public List<GanttPlan> createOrUpdatePlans(String rootModelName, String rootDataId, List<GanttPlan> toCreatePlans) {
        Collection<User> users = roleEngine.getRoleModel(false).getUsers().values();
        IBusinessModel planBm = this.businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);

        Project project = this.orientSqlEngine.getTypeMappingBmService().getById(Project.class, rootDataId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<GanttPlan> retV = UtilFactory.newArrayList();
        for (GanttPlan plan : toCreatePlans) {
            if (plan.getParentId() == null || "-1".equals(plan.getParentId())) {
                plan.setParProjectId(rootDataId);
            } else {
                plan.setParPlanId(plan.getParentId());
            }

            if (plan.isNewCreate()) {
                plan.setStatus(STATUS_UNSTARTED);
                this.generateDisplayOrderInfo(plan);
                plan.setType(PLAN_TYPE_NORMAL);

                String resourceName = plan.getResourceName();
                if (!StringUtil.isEmpty(plan.getResourceName())) {
                    Predicate<User> userAllNameFilter = user -> resourceName.equals(user.getAllName());
                    if (users.stream().filter(userAllNameFilter).count() > 0) {
                        User user = users.stream().filter(userAllNameFilter).findFirst().get();
                        plan.setPrincipal(user.getId());
                    }
                }

                if (!CommonTools.isNullString(plan.getParPlanId())) {
                    Plan parPlan = this.orientSqlEngine.getTypeMappingBmService().getById(Plan.class, plan.getParPlanId());
                    parPlan.setType(PLAN_TYPE_GROUP);
                    this.orientSqlEngine.getTypeMappingBmService().update(parPlan);
                }
            } else {
                if (!CommonTools.isNullString(plan.getActualStartDate())
                        && !CommonTools.isNullString(plan.getActualEndDate())) {
                    if (plan.getActualStartDate().equals(plan.getActualEndDate())) {
                        plan.setType(PLAN_TYPE_MILESTONE);
                    }
                } else if (plan.isLeaf()) {
                    plan.setType(PLAN_TYPE_NORMAL);
                }
            }

            if (!CommonTools.isNullString(plan.getActualStartDate())
                    && !CommonTools.isNullString(plan.getActualEndDate())) {
                try {
                    if (dateFormat.parse(plan.getActualStartDate()).before(dateFormat.parse(project.getPlannedStartDate()))) {
                        plan.setActualStartDate(project.getPlannedStartDate());
                    }

                    if (dateFormat.parse(plan.getActualEndDate()).after(dateFormat.parse(project.getPlannedEndDate()))) {
                        plan.setActualEndDate(project.getPlannedEndDate());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Map<String, String> dataMap = BusinessDataConverter.convertBeanToRealColMap(planBm, plan, true, true);

            if (CommonTools.isNullString(plan.getId())) {
                ProjectTreeNodeCreatedEventParam eventParam = new ProjectTreeNodeCreatedEventParam();
                eventParam.setModelId(planBm.getId());
                eventParam.setDataMap(dataMap);
                eventParam.setCreateData(true);
                OrientContextLoaderListener.Appwac.publishEvent(new ProjectTreeNodeCreatedEvent(this, eventParam));
                String newId = CommonTools.Obj2String(eventParam.getDataMap().get("ID"));
                plan.setId(newId);
            } else {
                this.orientSqlEngine.getBmService().updateModelData(planBm, dataMap, plan.getId());
            }

            plan.setNewCreate(false);
            plan.setIconCls("icon-" + plan.getType());
            retV.add(plan);
        }
        return retV;
    }

    public CommonResponseData deletePlans(List<GanttPlan> plans) {

        CommonResponseData retV = new CommonResponseData(false, "");

        ProjectTreeNodeStrategy nodeStrategy = ProjectTreeNodeStrategy.fromString(PLAN);
        for (GanttPlan plan : plans) {
            TreeDeleteResult deleteResult = nodeStrategy.deleteNode(plan.getId());
            ProjectTreeNodeDeletedEventParam deletedEventParam = new ProjectTreeNodeDeletedEventParam(PLAN, plan.getId(), deleteResult);
            OrientContextLoaderListener.Appwac.publishEvent(new ProjectTreeNodeDeletedEvent(this, deletedEventParam));
        }

        retV.setSuccess(true);
        return retV;
    }

    public List<GanttPlanDependency> createOrUpdateDependencies(String rootModelName, String rootDataId, List<GanttPlanDependency> dependencies) {
        IBusinessModel dependencyBm = this.businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        for (GanttPlanDependency dependency : dependencies) {
            //dependency.setBlngProjectId(rootDataId);
            if (CommonTools.isNullString(dependency.getId())) {
                String newId = this.orientSqlEngine.getBmService().insertModelData(dependencyBm, dependency, true);
                dependency.setId(newId);
            } else {
                this.orientSqlEngine.getBmService().updateModelData(dependencyBm, dependency, dependency.getId(), true);
            }
        }
        return dependencies;
    }

    public CommonResponseData deleteDependencies(List<GanttPlanDependency> dependencies) {
        IBusinessModel dependencyBm = this.businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);

        CommonResponseData retV = new CommonResponseData(false, "");
        for (GanttPlanDependency dependency : dependencies) {
            this.orientSqlEngine.getBmService().delete(dependencyBm, dependency.getId());
        }

        retV.setSuccess(true);
        return retV;
    }

    public GanttAssignmentData getGanttAssignmentData(String rootModelName, String rootDataId) {
        GanttAssignmentData assignmentData = new GanttAssignmentData();

        List<GanttResource> resources = UtilFactory.newArrayList();
        List<CollabRole> roles = this.roleService.list(Restrictions.eq(MODEL_NAME, rootModelName), Restrictions.eq(NODE_ID, rootDataId));
        for (CollabRole role : roles) {
            for (CollabRole.User user : role.getUsers()) {
                GanttResource resource = new GanttResource();
                resource.setId(user.getId());
                resource.setName(user.getAllName());
                resources.add(resource);
            }
        }


        assignmentData.setResources(resources);
        return assignmentData;
    }

    public GanttResourceAssign saveResourceAssignment(GanttResourceAssign resourceAssign) {
        IBusinessModel planBm = this.businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);

        Map<String, String> oriData = this.orientSqlEngine.getBmService().createModelQuery(planBm).findById(resourceAssign.getPlanId());
        String oriUserId = CommonTools.Obj2String(oriData.get("PRINCIPAL_" + planBm.getId()));

        Map<String, String> updateMap = UtilFactory.newHashMap();
        updateMap.put("PRINCIPAL_" + planBm.getId(), resourceAssign.getResourceId());
        oriData.put("PRINCIPAL_" + planBm.getId(), resourceAssign.getResourceId());
        this.orientSqlEngine.getBmService().updateModelData(planBm, updateMap, resourceAssign.getPlanId());

        ProjectTreeNodeEditEventParam eventParam = new ProjectTreeNodeEditEventParam();
        eventParam.setModelId(planBm.getId());
        eventParam.setDataMap(oriData);
        eventParam.setOriginalUserId(oriUserId);

        OrientContextLoaderListener.Appwac.publishEvent(new ProjectTreeNodeEditEvent(this, eventParam));
        return resourceAssign;
    }

    public CommonResponseData setBaseLine(String rootModelName, String rootDataId) {
        return checkCanSetBaseLine(rootModelName, rootDataId);
    }


    public GanttControlStatus getGanttControlStatus(String rootModelName, String rootDataId) {
        AuditFlowInfo wbsBaselineSetAuditInfo = auditFlowInfoBusiness.getAuditFlowInfo(rootModelName, rootDataId, AuditFlowType.WbsBaseLineAudit.toString());
        AuditFlowInfo wbsBaselineEditAuditInfo = auditFlowInfoBusiness.getAuditFlowInfo(rootModelName, rootDataId, AuditFlowType.WbsBaseLineEditAudit.toString());

        Project project = this.orientSqlEngine.getTypeMappingBmService().getById(Project.class, rootDataId);

        GanttControlStatus retV = new GanttControlStatus();
        retV.setBaselineSetAuditFlowInfo(wbsBaselineSetAuditInfo);
        retV.setBaselineEditAuditFlowInfo(wbsBaselineEditAuditInfo);

        if (wbsBaselineSetAuditInfo.getStatus() == AuditFlowStatus.NotStarted || wbsBaselineEditAuditInfo.getStatus() == AuditFlowStatus.NotStarted) {
            retV.setBaselineSetAuditIsLastest(true);
        } else {
            retV.setBaselineSetAuditIsLastest(wbsBaselineSetAuditInfo.getStartTime().after(wbsBaselineEditAuditInfo.getStartTime()));
        }

        if (!project.getStatus().equals(CollabConstants.STATUS_UNSTARTED)) {
            retV.setCanSetBaseline(false);
            retV.setCanEditBaseline(false);
            retV.setCanStartProject(false);
        } else {
            if (retV.isBaselineSetAuditIsLastest()) {
                retV.setCanSetBaseline(wbsBaselineSetAuditInfo.getStatus() == AuditFlowStatus.NotStarted
                        || wbsBaselineSetAuditInfo.getStatus() == AuditFlowStatus.EndError);
                retV.setCanEditBaseline(wbsBaselineSetAuditInfo.getStatus() == AuditFlowStatus.End);

                retV.setCanStartProject(wbsBaselineSetAuditInfo.getStatus() == AuditFlowStatus.End);
            } else {
                retV.setCanSetBaseline(wbsBaselineEditAuditInfo.getStatus() == AuditFlowStatus.End);
                retV.setCanEditBaseline(wbsBaselineEditAuditInfo.getStatus() == AuditFlowStatus.EndError);

                retV.setCanStartProject(wbsBaselineEditAuditInfo.getStatus() == AuditFlowStatus.End);
            }
        }

        if (!project.getStatus().equals(CollabConstants.STATUS_PROCESSING)) {
            retV.setCanSubmitProject(false);
        }

        retV.setProjectStatus(project.getStatus());

        return retV;
    }


    private CommonResponseData checkCanSetBaseLine(String rootModelName, String rootDataId) {
        CommonResponseData retV = new CommonResponseData(false, "");
        GanttControlStatus ganttControlStatus = this.getGanttControlStatus(rootModelName, rootDataId);
        if (ganttControlStatus.isBaselineSetAuditIsLastest()) {
            switch (ganttControlStatus.getBaselineSetAuditFlowInfo().getStatus()) {
                case NotStarted:
                    retV.setSuccess(true);
                    break;
                case Active:
                    retV.setMsg("基线审批中!");
                    break;
                case EndError:
                    retV.setSuccess(true);
                    retV.setMsg("基线审批不通过,请修改后再次审批!");
                    break;
                case End:
                    retV.setMsg("审批已通过");
                    break;
            }
        } else {
            return new CommonResponseData(true, "");
        }

        return retV;
    }

    private void generateDisplayOrderInfo(GanttPlan plan) {
        if (CommonTools.isNullString(plan.getPreSib()) && CommonTools.isNullString(plan.getNextSib())) {
            plan.setDisplayOrder("5");
            return;
        }

        if (!CommonTools.isNullString(plan.getPreSib()) && !CommonTools.isNullString(plan.getNextSib())) {
            Plan preSibling = this.orientSqlEngine.getTypeMappingBmService().getById(Plan.class, plan.getPreSib());
            Plan nextSibling = this.orientSqlEngine.getTypeMappingBmService().getById(Plan.class, plan.getNextSib());
            plan.setDisplayOrder(nextSibling.getDisplayOrder());
            nextSibling.setDisplayOrder(String.valueOf((Integer.valueOf(preSibling.getDisplayOrder()) + Integer.valueOf(nextSibling.getDisplayOrder())) / 2));
            this.orientSqlEngine.getTypeMappingBmService().update(nextSibling);
            return;
        }

        if (!CommonTools.isNullString(plan.getNextSib())) {
            Plan nextSibling = this.orientSqlEngine.getTypeMappingBmService().getById(Plan.class, plan.getNextSib());
            plan.setDisplayOrder(String.valueOf(Integer.valueOf(nextSibling.getDisplayOrder()) - 1));
            return;
        }

        Plan preSibling = this.orientSqlEngine.getTypeMappingBmService().getById(Plan.class, plan.getPreSib());
        plan.setDisplayOrder(String.valueOf(Integer.valueOf(preSibling.getDisplayOrder()) + 1));
        return;

    }

    public Map<String, String> getModelIdByName(String[] modelNames) {
        Map<String, String> retVal = new HashMap<>();
        for (String modelName : modelNames) {
            IBusinessModel businessModel = this.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
            retVal.put(modelName, businessModel.getId());
        }
        return retVal;
    }

    @Autowired
    private AuditFlowInfoBusiness auditFlowInfoBusiness;
    @Autowired
    private CollabRoleService roleService;
    @Autowired
    private UserService UserService;


}
