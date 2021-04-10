package com.orient.pvm.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.config.CollabConstants;
import com.orient.pvm.bean.CheckModelTreeNode;
import com.orient.sysmodel.domain.pvm.*;
import com.orient.sysmodel.service.IBaseService;
import com.orient.sysmodel.service.pvm.ICheckModelDataTemplateService;
import com.orient.sysmodel.service.pvm.IPVMMulCheckRelationService;
import com.orient.sysmodel.service.pvm.impl.CheckTaskHtmlTemplateService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckModelService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckRelationService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail: 处理协同Task相关的的检查表格
 */
@Component
public class CheckModelBusiness extends BaseHibernateBusiness<TaskCheckModel> {


    @Autowired
    private TaskCheckModelService taskCheckModelService;

    @Override
    public IBaseService getBaseService() {
        return taskCheckModelService;
    }

    @Autowired
    private ICheckModelDataTemplateService checkModelDataTemplateService;

    @Autowired
    private TaskCheckRelationService taskCheckRelationService;

    @Autowired
    private CheckTaskHtmlTemplateService checkTaskHtmlTemplateService;

    @Autowired
    PVMMulCheckRelationBusiness pVMMulCheckRelationBusiness;

    @Autowired
    IPVMMulCheckRelationService pVMMulCheckRelationService;

    /**
     * 获取已经绑定的检查表格模型
     *
     * @param nodeId 节点id
     * @param flag   表示绑定模型的状态   1 编制中, 2 ,可下发  ,4 已下发 ,8 已上传    按位累加例如 编制中和可下发的 flag设置成 3
     * @return
     */
    public List<TaskCheckModel> getBindCheckModel(String nodeId, int flag) {

        List<TaskCheckModel> ret = new ArrayList<>();
        Criterion oldCriterion = null;
        if (CommonTools.isByteCodeInFlag(TaskCheckModel.STATUS_EDIT, flag)) {
            oldCriterion = Restrictions.eq("checktablestatus", TaskCheckModel.STATUS_EDIT);
        }
        if (CommonTools.isByteCodeInFlag(TaskCheckModel.STATUS_REDADY, flag)) {
            if (oldCriterion == null) {
                oldCriterion = Restrictions.eq("checktablestatus", TaskCheckModel.STATUS_REDADY);
            } else {
                oldCriterion = Restrictions.or(oldCriterion, Restrictions.eq("checktablestatus", TaskCheckModel.STATUS_REDADY));
            }

        }
        if (CommonTools.isByteCodeInFlag(TaskCheckModel.STATUS_DELIVED, flag)) {
            if (oldCriterion == null) {
                oldCriterion = Restrictions.eq("checktablestatus", TaskCheckModel.STATUS_DELIVED);
            } else {
                oldCriterion = Restrictions.or(oldCriterion, Restrictions.eq("checktablestatus", TaskCheckModel.STATUS_DELIVED));
            }

        }
        if (CommonTools.isByteCodeInFlag(TaskCheckModel.STATUS_UPLOADED, flag)) {
            if (oldCriterion == null) {
                oldCriterion = Restrictions.eq("checktablestatus", TaskCheckModel.STATUS_UPLOADED);
            } else {
                oldCriterion = Restrictions.or(oldCriterion, Restrictions.eq("checktablestatus", TaskCheckModel.STATUS_UPLOADED));
            }

        }
        List<Criterion> criterions = new ArrayList<>();
        criterions.add(oldCriterion);
        criterions.add(Restrictions.eq("nodeId", StringUtil.isEmpty(nodeId) ? null : nodeId));
        Order orders[] = new Order[1];
        orders[0] = Order.desc("id");
        ret = taskCheckModelService.list(criterions.toArray(new Criterion[0]), orders);
        return ret;
    }


    /**
     * 绑定节点与检查模型,并且能够根据模型已经绑定的Excel模版设置值
     *
     * @param nodeId
     * @param checkModelId
     * @return
     */
    public TaskCheckModel bindCheckModel(String nodeId, String checkModelId, int status, String html, String htmlTemplateName) {

        TaskCheckModel taskCheckModel = new TaskCheckModel();
        String name = "";
        if (!StringUtil.isEmpty(checkModelId)) {
            taskCheckModel.setCheckmodelid(StringUtil.isEmpty(checkModelId) ? null : Long.valueOf(checkModelId));
            IBusinessModel model = businessModelService.getBusinessModelById(UserContextUtil.getUserId(), checkModelId, null, EnumInter.BusinessModelEnum.Table);
            name = model.getDisplay_name();
        } else {
            name = htmlTemplateName;
        }
        taskCheckModel.setName(name);
        taskCheckModel.setChecktablestatus(status);
        taskCheckModel.setNodeId(nodeId);
        //  taskCheckModel.setUploaduser(UserContextUtil.getUserAllName());
        //  taskCheckModel.setUploadtime(new Date());
        taskCheckModel.setHtml(html);
        taskCheckModelService.save(taskCheckModel);
        return taskCheckModel;

    }


    /**
     * 接触模型的绑定
     *
     * @param taskModeId
     * @param taskId
     * @param checkModelId
     * @return
     */
    public boolean detachCheckMode(String taskModeId, String taskId, String checkModelId) {
        TaskCheckModel taskCheckModel = new TaskCheckModel();
        taskCheckModel.setCheckmodelid(Long.valueOf(checkModelId));
      /*  taskCheckModel.setTaskmodelid(Long.valueOf(taskModeId));
        taskCheckModel.setTaskdataid(Long.valueOf(taskId));*/
        List<TaskCheckModel> bindmodels = taskCheckModelService.listBeansByExample(taskCheckModel);

        for (TaskCheckModel bindModel : bindmodels) {
            taskCheckModelService.delete(bindModel);
        }
        return true;

    }


    public List<CheckModelTreeNode> getBindCheckModelNodes(String node, String nodeId) {
        List<CheckModelTreeNode> retVal = new ArrayList<>();
        if ("-1".equals(node)) {
            //获取所有状态的检查表信息
            List<TaskCheckModel> queryList = getBindCheckModel(nodeId, 15);
            List<TaskCheckModel> distinctList = getDistinctCheckModel(queryList);
            distinctList.forEach(taskCheckModel -> {
                CheckModelTreeNode checkModelTreeNode = convertTaskCheckModelToTreeNode(taskCheckModel);
                retVal.add(checkModelTreeNode);
            });
        } else if (node.startsWith("all_")) {
//            //加载所有状态的数据
//            String taskCheckModelId = node.substring(4, node.length());
//            TaskCheckModel taskCheckModel = taskCheckModelService.getById(Long.valueOf(taskCheckModelId));
//            retVal.addAll(constructAllStatusNode(taskCheckModel));
        }
        return retVal;
    }

    private List<CheckModelTreeNode> constructAllStatusNode(TaskCheckModel taskCheckModel) {
        String checkModelId = taskCheckModel.getCheckmodelid().toString();
        List<CheckModelTreeNode> checkModelTreeNodes = new ArrayList<>();
        TaskCheckModel.statusMap.forEach((statusInteger, status) -> {
            CheckModelTreeNode statusNode = constructStatusNode(checkModelId, status, statusInteger);
            statusNode.setRealId(taskCheckModel.getId());
            checkModelTreeNodes.add(statusNode);
        });
        return checkModelTreeNodes;
    }

    private CheckModelTreeNode constructStatusNode(String checkModelId, String name, Integer status) {
        CheckModelTreeNode statusNode = new CheckModelTreeNode();
        statusNode.setText(name);
        statusNode.setId("status_" + status + "_" + checkModelId);
        statusNode.setLeaf(true);
        statusNode.setChecktablestatus(status);
        statusNode.setCheckmodelid(Long.valueOf(checkModelId));
        return statusNode;
    }

    private List<TaskCheckModel> getDistinctCheckModel(List<TaskCheckModel> queryList) {
        List<TaskCheckModel> distinctCheckModels = new ArrayList<>();
        Set<Long> existsModelIds = new HashSet<>();
        queryList.forEach(taskCheckModel -> {
            Long checkModelId = taskCheckModel.getCheckmodelid();
            if (null == checkModelId) {
                distinctCheckModels.add(taskCheckModel);
            } else if (!existsModelIds.contains(checkModelId)) {
                distinctCheckModels.add(taskCheckModel);
                existsModelIds.add(checkModelId);
            }
        });
        return distinctCheckModels;
    }

    public CheckModelTreeNode convertTaskCheckModelToTreeNode(TaskCheckModel taskCheckModel) {
        CheckModelTreeNode checkModelTreeNode = new CheckModelTreeNode();
        checkModelTreeNode.setText(taskCheckModel.getName() + "(" + taskCheckModel.statusMap.get(taskCheckModel.getChecktablestatus()) + ")");
        checkModelTreeNode.setIconCls(taskCheckModel.getCheckmodelid() != null ? "icon-model" : "icon-html");
        checkModelTreeNode.setLeaf(true);
        checkModelTreeNode.setExpanded(false);
        BeanUtils.copyProperties(checkModelTreeNode, taskCheckModel);
        checkModelTreeNode.setId(taskCheckModel.getId().toString());
        checkModelTreeNode.setRealId(taskCheckModel.getId());
        checkModelTreeNode.setChecktablestatus(taskCheckModel.getChecktablestatus());
        checkModelTreeNode.setRemark(taskCheckModel.getRemark());
        return checkModelTreeNode;
    }

    public String canAdd(String checkmodelid, String nodeId) {
        //校验是否可添加
        String retVal = "true";
        retVal = taskCheckModelService.list(Restrictions.eq("checkmodelid", Long.valueOf(checkmodelid)),
                Restrictions.eq("nodeId", nodeId)).size() > 0 ? "已经存在该模型" : "true";
        return retVal;
    }

    public Map<String, Object> getBusinessModelDesc(Long taskCheckModelId, Integer status) {
        Map<String, Object> dataMap = new HashMap<>();
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(taskCheckModelId);
        if (null != taskCheckModel) {
            TaskCheckRelation example = new TaskCheckRelation();
            example.setNodeId(taskCheckModel.getNodeId());
            example.setCheckmodelid(taskCheckModel.getCheckmodelid());
            List<Long> dataIds = taskCheckRelationService.listBeansByExample(example).stream().map(TaskCheckRelation::getCheckdataid).collect(Collectors.toList());
            dataMap.put("modelId", taskCheckModel.getCheckmodelid());
            dataMap.put("dataIds", dataIds);
        }
        return dataMap;
    }

    public String createRelationDatas(Long taskCheckModelId, String toBindDataIds) {
        String retVal = "";
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(taskCheckModelId);
        Long checkModelId = taskCheckModel.getCheckmodelid();
        String[] toBindArr = toBindDataIds.split(",");
        for (String dataId : toBindArr) {
            TaskCheckRelation tmp = new TaskCheckRelation();
            tmp.setNodeId(taskCheckModel.getNodeId());
            tmp.setCheckmodelid(checkModelId);
            tmp.setCheckdataid(Long.valueOf(dataId));
            taskCheckRelationService.save(tmp);
        }
        return retVal;
    }


    public boolean createRelationDatas(TaskCheckModel taskCheckModel, String toBindDataIds) {

        Long checkModelId = taskCheckModel.getCheckmodelid();
      /*  Long taskModelId = taskCheckModel.getTaskmodelid();
        Long taskDataId = taskCheckModel.getTaskdataid();*/
        String[] toBindArr = toBindDataIds.split(",");
        for (String dataId : toBindArr) {
            TaskCheckRelation tmp = new TaskCheckRelation();
          /*  tmp.setTaskmodelid(taskModelId);
            tmp.setTaskdataid(taskDataId);*/
            tmp.setCheckmodelid(checkModelId);
            tmp.setCheckdataid(Long.valueOf(dataId));

            taskCheckRelationService.save(tmp);
        }
        return true;
    }

    public String updateCheckModelStatus(Long taskCheckModelId, Integer toSaveStatus) {
        String retVal = "";
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(taskCheckModelId);
        taskCheckModel.setChecktablestatus(toSaveStatus);
        return retVal;
    }

    public String releaseRelations(String modelId, Long[] toDelIds) {
        String retVal = "";
        Criterion checkModelCriterion = Restrictions.eq("checkmodelid", Long.valueOf(modelId));
        Criterion checkdataCriterion = Restrictions.in("checkdataid", toDelIds);
        List<TaskCheckRelation> queryList = taskCheckRelationService.list(checkModelCriterion, checkdataCriterion);
        queryList.forEach(taskCheckRelation -> taskCheckRelationService.delete(taskCheckRelation));
        return retVal;
    }

    /**
     * @param templateIds
     * @return 校验是否可以通过模板新增
     */
    public String canAddFromTemplate(Long[] templateIds, String nodeId) {
        String retVal = "";
        String userId = UserContextUtil.getUserId();
        List<String> errorList = new ArrayList<>();
        for (Long templateId : templateIds) {
            CheckModelDataTemplate checkModelDataTemplate = checkModelDataTemplateService.getById(templateId);
            if (null != checkModelDataTemplate) {
                Long checkModelId = checkModelDataTemplate.getCheckmodelid();
                Boolean exists = taskCheckModelService.list(Restrictions.eq("checkmodelid", Long.valueOf(checkModelId)),
                        Restrictions.eq("nodeId", nodeId)).size() > 0;
                if (exists) {
                    errorList.add("【" + businessModelService.getBusinessModelById(userId, checkModelId.toString(), null, EnumInter.BusinessModelEnum.Table).getDisplay_name() +
                            "】已经存在，不可重复添加");
                }
            }
        }
        retVal = CommonTools.list2String(errorList, "</br>");
        return retVal;
    }

    public String canAddFromHtmlTemplate(Long[] htmlTemplates, String nodeId) {
        String retVal;
        List<String> errorList = new ArrayList<>();
        for (Long templateId : htmlTemplates) {
            CwmTaskcheckHtmlEntity htmlEntity = checkTaskHtmlTemplateService.getById(templateId);
            if (null != htmlEntity) {
                Boolean exists = taskCheckModelService.list(Restrictions.eq("name", htmlEntity.getName()),
                        Restrictions.eq("nodeId", nodeId)).size() > 0;
                if (exists) {
                    errorList.add("【" + htmlEntity.getName() +
                            "】已经存在，不可重复添加");
                }
            }
        }
        retVal = CommonTools.list2String(errorList, "</br>");
        return retVal;
    }

    public List<CwmTaskcheckHtmlEntity> getHtmlTemplatesByIds(Long[] ids) {
        return checkTaskHtmlTemplateService.getByIds(ids);
    }


    public List<Long> getCheckModelIdsByTemplateIds(Long[] templateIds) {

        List<Long> retVal = new ArrayList<>();
        for (Long templateId : templateIds) {
            CheckModelDataTemplate checkModelDataTemplate = checkModelDataTemplateService.getById(templateId);
            if (null != checkModelDataTemplate) {
                Long checkModelId = checkModelDataTemplate.getCheckmodelid();
                retVal.add(checkModelId);
            }
        }
        return retVal;
    }

    public void saveAssignRoles(Long taskCheckModelId, String signroles) {
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(taskCheckModelId);
        taskCheckModel.setSignroles(signroles);
        taskCheckModelService.save(taskCheckModel);
    }

    /**
     * @param sourceDataId    原始数据ID
     * @param sourceModelName 原始模型名称
     * @param destiId         待拷贝数据ID
     * @param destiModelName  待拷贝模型名称
     *                        <p>
     *                        拷贝检查数据
     */
    public void copyCheckData(String sourceDataId, String sourceModelName, String destiId, String destiModelName) {
        String collabSchemaId = CollabConstants.COLLAB_SCHEMA_ID;
        IBusinessModel sourceModel = businessModelService.getBusinessModelBySName(sourceModelName, collabSchemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel destiModel = businessModelService.getBusinessModelBySName(destiModelName, collabSchemaId, EnumInter.BusinessModelEnum.Table);
        String sourceModelId = sourceModel.getId();
        String destiModelId = destiModel.getId();
        List<TaskCheckModel> taskCheckModels = taskCheckModelService.list(Restrictions.eq(TaskCheckModel.TASK_MODEL_ID, Long.valueOf(sourceModelId)),
                Restrictions.eq(TaskCheckModel.TASK_DATA_ID, Long.valueOf(sourceDataId)));
        List<TaskCheckRelation> taskCheckRelations = taskCheckRelationService.list(Restrictions.eq("taskmodelid", Long.valueOf(sourceModelId)),
                Restrictions.eq("taskdataid", Long.valueOf(sourceDataId)));
        //拷贝模型绑定
        taskCheckModels.forEach(taskCheckModel -> {
            TaskCheckModel newTaskCheckModel = new TaskCheckModel();
            BeanUtils.copyProperties(newTaskCheckModel, taskCheckModel);
            newTaskCheckModel.setId(null);
          /*  newTaskCheckModel.setTaskmodelid(Long.valueOf(destiModelId));
            newTaskCheckModel.setTaskdataid(Long.valueOf(destiId));*/
            taskCheckModelService.save(newTaskCheckModel);
        });
        //拷贝数据绑定
        taskCheckRelations.forEach(taskCheckRelation -> {
            TaskCheckRelation newTaskCheckRelation = new TaskCheckRelation();
            BeanUtils.copyProperties(newTaskCheckRelation, taskCheckRelation);
            newTaskCheckRelation.setId(null);
            /*newTaskCheckRelation.setTaskmodelid(Long.valueOf(destiModelId));
            newTaskCheckRelation.setTaskdataid(Long.valueOf(destiId));*/
            taskCheckRelationService.save(newTaskCheckRelation);
        });
    }

    public void saveRemark(Long id, String remark) {
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(id);
        taskCheckModel.setRemark(remark);
        taskCheckModelService.update(taskCheckModel);
    }

    public List<CheckModelDataTemplate> getByIds(Long[] templateIds) {
        List<CheckModelDataTemplate> checkModelDataTemplates = checkModelDataTemplateService.getByIds(templateIds);
        return checkModelDataTemplates;
    }

    public String canAddFromMulTemplate(Long[] mulTemplateIds, String nodeId) {
        List<String> str = new ArrayList<>();
        String retStr = "";
        for (Long templateId : mulTemplateIds) {//对每一个综合模板进行的操作
            //获得所有html模板id,进行校验能否添加html模板
            List<Long> htmlTmpIds = new ArrayList<>();
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.isNull("checkmodelid"), Restrictions.eq("templateid", templateId.toString()));
            entities.forEach(entity -> {
                List<CwmTaskcheckHtmlEntity> htmlEntities = checkTaskHtmlTemplateService.list();
                for (CwmTaskcheckHtmlEntity htmlEntity : htmlEntities) {
                    if (entity.getHtml().equals(htmlEntity.getHtml())) {
                        htmlTmpIds.add(htmlEntity.getId());
                    }
                }
            });
            Long[] htmlTemplateIds = htmlTmpIds.toArray(new Long[htmlTmpIds.size()]);
            str.add(canAddFromHtmlTemplate(htmlTemplateIds, nodeId));

            //校验能否添加模型
            String retVal = "";
            String userId = UserContextUtil.getUserId();
            List<String> errorList = new ArrayList<>();
            entities = pVMMulCheckRelationService.list(Restrictions.isNotNull("checkmodelid"), Restrictions.eq("templateid", templateId.toString()));
            entities.forEach(entity -> {
                Boolean exists = taskCheckModelService.list(Restrictions.eq("checkmodelid", Long.valueOf(entity.getCheckmodelid())),
                        Restrictions.eq("nodeId", Long.valueOf(nodeId))).size() > 0;
                if (exists) {
                    errorList.add("【" + businessModelService.getBusinessModelById(userId, entity.getCheckmodelid(), null, EnumInter.BusinessModelEnum.Table).getDisplay_name() +
                            "】已经存在，不可重复添加");
                }

            });
            retVal = CommonTools.list2String(errorList, "</br>");
            str.add(retVal);
        }

        retStr = CommonTools.list2String(str);
        return retStr;
    }

//    public void createByMulTemplate(String taskmodelid, String taskdataid, Long[] mulTemplateIds) {
//        for(Long templateId:mulTemplateIds) {//对于综合模板中的每一个模板进行保存
//            //保存html数据
//
//            //保存模型数据
//        }
//
//    }
}
