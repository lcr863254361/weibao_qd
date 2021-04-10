package com.orient.pvm.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.pvm.bean.CheckTemplateInfo;
import com.orient.pvm.bean.MulCheckModelTreeNode;
import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.sysmodel.domain.pvm.CwmTaskcheckHtmlEntity;
import com.orient.sysmodel.domain.pvm.CwmTaskmulcheckrelationEntity;
import com.orient.sysmodel.service.pvm.ICheckModelDataTemplateService;
import com.orient.sysmodel.service.pvm.IPVMMulCheckRelationService;
import com.orient.sysmodel.service.pvm.impl.CheckTaskHtmlTemplateService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.style.Align;
import com.orient.utils.ExcelUtil.style.BorderStyle;
import com.orient.utils.ExcelUtil.style.Color;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class PVMMulCheckRelationBusiness extends BaseHibernateBusiness<CwmTaskmulcheckrelationEntity> {

    @Autowired
    IPVMMulCheckRelationService pVMMulCheckRelationService;

    @Autowired
    private CheckTaskHtmlTemplateService checkTaskHtmlTemplateService;

    @Autowired
    private ICheckModelDataTemplateService checkModelDataTemplateService;

    @Autowired
    private CheckModelDataTemplateBusiness checkModelDataTemplateBusiness;

    @Autowired
    FileServerConfig fileServerConfig;

    @Override
    public IPVMMulCheckRelationService getBaseService() {
        return pVMMulCheckRelationService;
    }

    public String canAdd(String checkmodelid, String templateId) {
        //校验是否可添加
        String retVal = "true";
        if (StringUtil.isEmpty(templateId)) {
            retVal = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", checkmodelid),
                    Restrictions.isNull("templateid")
            ).size() > 0 ? "已经存在该模型" : "true";
        } else {
            retVal = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", checkmodelid),
                    Restrictions.eq("templateid", templateId)
            ).size() > 0 ? "已经存在该模型" : "true";
        }

        return retVal;
    }

    public List<MulCheckModelTreeNode> getBindCheckModelNodes(String actionType, String templateId) {
        List<MulCheckModelTreeNode> retVal = new ArrayList<>();
        if ("create".equals(actionType)) {
            //新增记录的时候将relation表中templateId为空的记录查出来即可
            List<CwmTaskmulcheckrelationEntity> queryList = getBindCheckModel(templateId);
            //根据目前的处理逻辑不会出现重复的记录
            queryList.forEach(mulCheckRelation -> {
                MulCheckModelTreeNode treeNode = convertMulCheckRelation2Node(mulCheckRelation);
                retVal.add(treeNode);
            });
        } else if ("update".equals(actionType)) {
            //更新数据的时候需要传入templateId作为条件查询出所有关联的记录
            List<CwmTaskmulcheckrelationEntity> queryList = getBindCheckModel(templateId);
            queryList.forEach(mulCheckRelation -> {
                MulCheckModelTreeNode treeNode = convertMulCheckRelation2Node(mulCheckRelation);
                retVal.add(treeNode);
            });
        }
        return retVal;
    }

    private MulCheckModelTreeNode convertMulCheckRelation2Node(CwmTaskmulcheckrelationEntity entity) {
        MulCheckModelTreeNode node = new MulCheckModelTreeNode();
        String checkName = "";
        if (entity.getCheckmodelid() != null) {//checkModelId存在则不是纯自定义检查表（html)
            checkName = entity.getName();//getCheckModelNameById(entity.getCheckmodelid());
            node.setIconCls("icon-model");
        } else {
            checkName = entity.getName();
            node.setIconCls("icon-html");
        }
        node.setText(checkName);
        node.setLeaf(true);
        node.setExpanded(false);
        BeanUtils.copyProperties(node, entity);

        return node;
    }

    private String getCheckModelNameById(String checkModelId) {
        IBusinessModel model = businessModelService.getBusinessModelById(UserContextUtil.getUserId(), checkModelId, null, EnumInter.BusinessModelEnum.Table);
        return model.getDisplay_name();
    }

    private List<CwmTaskmulcheckrelationEntity> getBindCheckModel(String templateId) {
        List<CwmTaskmulcheckrelationEntity> ret = new ArrayList<>();
        List<Criterion> criterions = new ArrayList<>();
        if (StringUtil.isEmpty(templateId)) {
            criterions.add(Restrictions.isNull("templateid"));
        } else {
            criterions.add(Restrictions.eq("templateid", templateId));
        }
        Order orders[] = new Order[1];
        orders[0] = Order.desc("id");
        ret = pVMMulCheckRelationService.list(criterions.toArray(new Criterion[0]), orders);
        return ret;
    }

    public String getCheckModelName(String checkmodelid) {
        String name = "";
        IBusinessModel model = businessModelService.getBusinessModelById(UserContextUtil.getUserId(), checkmodelid, null, EnumInter.BusinessModelEnum.Table);
        name = model.getDisplay_name();
        return name;
    }

    public void insertTemplateId(String templateId) {
        List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.isNull("templateid"));
        entities.forEach(entity -> {
            entity.setTemplateid(templateId);
            pVMMulCheckRelationService.update(entity);
        });
    }

    public void deleteEmptyTmpIdData() {
        List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.isNull("templateid"));
        if (entities.size() > 0) {
            entities.forEach(entity -> {
                pVMMulCheckRelationService.delete(entity);
            });
        }
    }

    public void deleteRelationByTemplateId(String templateId) {
        List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("templateid", templateId));
        if (entities.size() > 0) {
            entities.forEach(entity -> {
                pVMMulCheckRelationService.delete(entity);
            });
        }

    }

    public ExtGridData<Map> getModelGridData(String orientModelId, String templateId) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        if (StringUtil.isEmpty(templateId)) {//新增时显示的数据
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", orientModelId), Restrictions.isNull("templateid"));
            String modelDataStr = entities.get(0).getModeldata();
            List<Map> modelData = new ArrayList<>();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            retVal.setResults(modelData);
            retVal.setTotalProperty(modelData.size());
        } else {
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", orientModelId), Restrictions.eq("templateid", templateId));
            String modelDataStr = entities.get(0).getModeldata();
            List<Map> modelData = new ArrayList<>();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            retVal.setResults(modelData);
            retVal.setTotalProperty(modelData.size());
        }

        return retVal;
    }

    public void saveModelData(String modelId, String templateId, Map formDataMap) {
        //先根据modelId得到sequence
        String sequenceName = "";
        IBusinessModel model = businessModelService.getBusinessModelById(UserContextUtil.getUserId(), modelId, null, EnumInter.BusinessModelEnum.Table);
        sequenceName = model.getSeq_name();

        //从sequence中得到formDataMap唯一的Id
        StringBuffer querySql = new StringBuffer();
        querySql.append(" SELECT ").append(sequenceName).append(".nextval");
        querySql.append(" FROM ").append(" DUAL ");
        MetaDAOFactory metaDaoFactory = OrientContextLoaderListener.Appwac.getBean(MetaDAOFactory.class);
        List<Map<String, Object>> results = metaDaoFactory.getJdbcTemplate().queryForList(querySql.toString());
        Map<String, Object> result = results.get(0);
        String mapId = result.get("NEXTVAL").toString();
        //存入数据库
        //formDataMap.put("id",mapId);
        formDataMap.put("ID", mapId);
        if (StringUtil.isEmpty(templateId)) {
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.isNull("templateid"));
            String modelDataStr = entities.get(0).getModeldata();
            List<Map> modelData = new ArrayList<>();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            modelData.add(formDataMap);
            entities.get(0).setModeldata(JsonUtil.toJson(modelData));
            pVMMulCheckRelationService.update(entities.get(0));
        } else {
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.eq("templateid", templateId));
            String modelDataStr = entities.get(0).getModeldata();
            List<Map> modelData = new ArrayList<>();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            modelData.add(formDataMap);
            entities.get(0).setModeldata(JsonUtil.toJson(modelData));
            pVMMulCheckRelationService.update(entities.get(0));
        }

    }

    public void deleteModelData(String modelId, Long[] toDelIds, String templateId) {
        List<Long> delIds = new ArrayList<>();
        for (Long delId : toDelIds) {
            delIds.add(delId);
        }
        if (StringUtil.isEmpty(templateId)) {
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.isNull("templateid"));
            String modelDataStr = entities.get(0).getModeldata();
            List<Map> modelData = new ArrayList<>();
            List<Map> modelDataCopy = new ArrayList<>();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            for (int i = 0; i < modelData.size(); i++) {
                modelDataCopy.add(modelData.get(i));
            }
            for (Map data : modelData) {
                if (delIds.contains(Long.valueOf((String) data.get("ID")))) {
                    modelDataCopy.remove(modelData.indexOf(data));
                }
            }
            entities.get(0).setModeldata(JsonUtil.toJson(modelDataCopy));
            pVMMulCheckRelationService.update(entities.get(0));
        } else {
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.eq("templateid", templateId));
            String modelDataStr = entities.get(0).getModeldata();
            List<Map> modelData = new ArrayList<>();
            List<Map> toDeleteData = new ArrayList<>();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            for (Map data : modelData) {
                if (delIds.contains(Long.valueOf((String) data.get("ID")))) {
                    toDeleteData.add(data);
                }
            }
            modelData.removeAll(toDeleteData);
            entities.get(0).setModeldata(JsonUtil.toJson(modelData));
            pVMMulCheckRelationService.update(entities.get(0));
        }
    }

    public void updateModelData(String modelId, String dataId, String templateId, Map formData) {
        if (StringUtil.isEmpty(templateId)) {
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.isNull("templateid"));
            String modelDataStr = entities.get(0).getModeldata();
            List<Map> modelData = new ArrayList<>();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            int index = -1;
            for (int i = 0; i < modelData.size(); i++) {
                if (modelData.get(i).get("ID").equals(dataId)) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                modelData.remove(index);
                modelData.add(index, formData);
            }
            entities.get(0).setModeldata(JsonUtil.toJson(modelData));
            pVMMulCheckRelationService.update(entities.get(0));
        } else {
            List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.eq("templateid", templateId));
            String modelDataStr = entities.get(0).getModeldata();
            List<Map> modelData = new ArrayList<>();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            int index = -1;
            for (int i = 0; i < modelData.size(); i++) {
                if (modelData.get(i).get("ID").equals(dataId)) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                modelData.remove(index);
                modelData.add(index, formData);
            }
            entities.get(0).setModeldata(JsonUtil.toJson(modelData));
            pVMMulCheckRelationService.update(entities.get(0));
        }
    }

    public void updateDataHtml(CwmTaskmulcheckrelationEntity formValue) {
        List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("id", formValue.getId()));
        CwmTaskmulcheckrelationEntity entity = entities.get(0);
        entity.setHtml(formValue.getHtml());
        pVMMulCheckRelationService.update(entity);
    }

    public String canAddFromHtmlTemplate(Long[] htmlTemplateIds, String modelId, String templateId) {
        List<CwmTaskmulcheckrelationEntity> entities = new ArrayList<>();
        String retVal;
        List<String> errorList = new ArrayList<>();
        if (StringUtil.isEmpty(templateId)) {
            entities = pVMMulCheckRelationService.list(Restrictions.isNull("checkmodelid"), Restrictions.isNull("templateid"));
            for (Long htmlId : htmlTemplateIds) {
                CwmTaskcheckHtmlEntity htmlEntity = checkTaskHtmlTemplateService.getById(htmlId);
                if (null != htmlEntity) {
                    Boolean exists = false;
                    for (CwmTaskmulcheckrelationEntity entity : entities) {
                        if (entity.getHtml().equals(htmlEntity.getHtml())) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) {
                        errorList.add("【" + htmlEntity.getName() +
                                "】已经存在，不可重复添加");
                    }
                }
            }
        } else {
            entities = pVMMulCheckRelationService.list(Restrictions.isNull("checkmodelid"), Restrictions.eq("templateid", templateId));
            for (Long htmlId : htmlTemplateIds) {
                CwmTaskcheckHtmlEntity htmlEntity = checkTaskHtmlTemplateService.getById(htmlId);
                if (null != htmlEntity) {
                    Boolean exists = false;
                    for (CwmTaskmulcheckrelationEntity entity : entities) {
                        if (entity.getHtml().equals(htmlEntity.getHtml())) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) {
                        errorList.add("【" + htmlEntity.getName() +
                                "】已经存在，不可重复添加");
                    }
                }
            }
        }
        retVal = CommonTools.list2String(errorList, "</br>");
        return retVal;
    }

    public void createByHtmlTemplate(String modelId, String templateId, Long[] htmlTemplateIds) {
        List<CwmTaskcheckHtmlEntity> htmlEntities = new ArrayList<>();
        for (Long htmlId : htmlTemplateIds) {
            CwmTaskcheckHtmlEntity htmlEntity = checkTaskHtmlTemplateService.getById(htmlId);
            if (null != htmlEntity) {
                htmlEntities.add(htmlEntity);
            }
        }

        htmlEntities.forEach(htmlEntity -> {
            CwmTaskmulcheckrelationEntity entity = new CwmTaskmulcheckrelationEntity();
            entity.setHtml(htmlEntity.getHtml());
            entity.setTemplateid(templateId);
            entity.setName(htmlEntity.getName());
            entity.setCheckmodelid(modelId);
            this.save(entity);
        });

    }

    public void saveRemark(String id, String remark) {
        List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("id", id));
        CwmTaskmulcheckrelationEntity entity = entities.get(0);
        entity.setRemark(remark);
        pVMMulCheckRelationService.update(entity);
    }

    public void saveAssignRoles(String dataId, String signroles) {
        List<CwmTaskmulcheckrelationEntity> entities = pVMMulCheckRelationService.list(Restrictions.eq("id", dataId));
        CwmTaskmulcheckrelationEntity entity = entities.get(0);
        entity.setSignroles(signroles);
        pVMMulCheckRelationService.update(entity);
    }

    public String canAddFromTemplate(Long[] templateIds, String templateId) {
        String retVal = "";
        String userId = UserContextUtil.getUserId();
        List<String> errorList = new ArrayList<>();
        for (Long checkTemplateId : templateIds) {
            CheckModelDataTemplate checkModelDataTemplate = checkModelDataTemplateService.getById(checkTemplateId);
            if (null != checkModelDataTemplate) {
                Long checkModelId = checkModelDataTemplate.getCheckmodelid();
                Boolean exists = false;
                if (StringUtil.isEmpty(templateId)) {
                    exists = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", checkModelId.toString()),
                            Restrictions.isNull("templateid")
                    ).size() > 0;
                } else {
                    exists = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", checkModelId.toString()),
                            Restrictions.eq("templateid", templateId)
                    ).size() > 0;
                }

                if (exists) {
                    errorList.add("【" + businessModelService.getBusinessModelById(userId, checkModelId.toString(), null, EnumInter.BusinessModelEnum.Table).getDisplay_name() +
                            "】已经存在，不可重复添加");
                }
            }
        }
        retVal = CommonTools.list2String(errorList, "</br>");
        return retVal;
    }

    public void createByTemplate(String templateId, List<CheckModelDataTemplate> checkModelDataTemplates) {
        checkModelDataTemplates.forEach(checkModelDataTemplate -> {
            CheckTemplateInfo checkTemplateInfo = checkModelDataTemplateBusiness.getCheckTemplateInfo(checkModelDataTemplate);
            Long checkModelId = checkModelDataTemplate.getCheckmodelid();
            if (null != checkTemplateInfo) {
                List<Map> dataInfo = fillData(checkTemplateInfo, checkModelId);
                CwmTaskmulcheckrelationEntity entity = new CwmTaskmulcheckrelationEntity();
                entity.setModeldata(JsonUtil.toJson(dataInfo));
                entity.setCheckmodelid(checkModelId.toString());
                entity.setName(checkTemplateInfo.getModelDisplayName());
                entity.setRemark(checkTemplateInfo.getRemark());
                entity.setSignroles(checkTemplateInfo.getSignroles());
                entity.setTemplateid(templateId);
                pVMMulCheckRelationService.save(entity);
            }
        });
    }

    private List<Map> fillData(CheckTemplateInfo checkTemplateInfo, Long checkModelId) {
        IBusinessModel checkModel = businessModelService.getBusinessModelById(checkModelId.toString(), EnumInter.BusinessModelEnum.Table);
        List<Map> dataInfo = new ArrayList<>();
        for (Map<String, String> row : checkTemplateInfo.getDatas()) {
            //转化显示名 到 字段真实名
            Map<String, String> realDataMap = transformDisplayMapToSMap(row, checkModel);
            realDataMap.put("ID", getDataId(checkModelId));
            dataInfo.add(realDataMap);
        }
        return dataInfo;
    }

    private String getDataId(Long checkModelId) {
        String sequenceName = "";
        IBusinessModel model = businessModelService.getBusinessModelById(UserContextUtil.getUserId(), checkModelId.toString(), null, EnumInter.BusinessModelEnum.Table);
        sequenceName = model.getSeq_name();

        //从sequence中得到formDataMap唯一的Id
        StringBuffer querySql = new StringBuffer();
        querySql.append(" SELECT ").append(sequenceName).append(".nextval");
        querySql.append(" FROM ").append(" DUAL ");
        MetaDAOFactory metaDaoFactory = OrientContextLoaderListener.Appwac.getBean(MetaDAOFactory.class);
        List<Map<String, Object>> results = metaDaoFactory.getJdbcTemplate().queryForList(querySql.toString());
        Map<String, Object> result = results.get(0);
        String mapId = result.get("NEXTVAL").toString();
        return mapId;
    }

    private Map<String, String> transformDisplayMapToSMap(Map<String, String> row, IBusinessModel model) {
        //删除默认ID属性
        Map<String, String> retVal = new HashMap<>();
        Map<String, String> columnMapping = new HashMap<>();
        model.getAllBcCols().forEach(iBusinessColumn -> {
            columnMapping.put(iBusinessColumn.getDisplay_name(), iBusinessColumn.getS_column_name());
        });
        row.forEach((key, value) -> {
            String scolumnName = columnMapping.get(key);
            if (!StringUtil.isEmpty(scolumnName)) {
                retVal.put(scolumnName, value);
            }
        });
        return retVal;
    }

    public String preapareExportData(String modelId, String templateId, Long[] toExportDataIds) {
        ExtGridData<Map> gridData = new ExtGridData<>();
        if (toExportDataIds.length == 0) { //全部导出
            List<CwmTaskmulcheckrelationEntity> entities = new ArrayList<>();
            if (StringUtil.isEmpty(templateId)) {
                entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.isNull(templateId));
            } else {
                entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.eq("templateid", templateId));
            }
            List<Map> modelData = new ArrayList<>();
            if (!StringUtil.isEmpty(entities.get(0).getModeldata())) {
                modelData = JsonUtil.json2List(entities.get(0).getModeldata());
            }
            gridData.setResults(modelData);
            gridData.setTotalProperty(modelData.size());
        } else {//根据Id导出
            List<CwmTaskmulcheckrelationEntity> entities = new ArrayList<>();
            List<Long> exportIds = Arrays.asList(toExportDataIds);
            if (StringUtil.isEmpty(templateId)) {
                entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.isNull(templateId));
            } else {
                entities = pVMMulCheckRelationService.list(Restrictions.eq("checkmodelid", modelId), Restrictions.eq("templateid", templateId));
            }
            List<Map> exportData = new ArrayList<>();
            List<Map> modelData = new ArrayList<>();
            String modelDataStr = entities.get(0).getModeldata();
            if (!StringUtil.isEmpty(modelDataStr)) {
                modelData = JsonUtil.json2List(modelDataStr);
            }
            for (Map data : modelData) {
                if (exportIds.contains(Long.valueOf((String) data.get("ID")))) {
                    exportData.add(data);
                }
            }
            gridData.setResults(exportData);
            gridData.setTotalProperty(exportData.size());
        }
        EnumInter.BusinessModelEnum modelTypeEnum = EnumInter.BusinessModelEnum.Table;
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, modelTypeEnum);
        List<Map> dataList = gridData.getResults();
        //反轉枚舉、關係屬性、附件
        //invertData(dataList, businessModel);
        Excel excel = new Excel();
        final int[] columnIndex = {0};
        businessModel.getAllBcCols().forEach(column -> {
            excel.column(columnIndex[0]).autoWidth().borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
            excel.cell(0, columnIndex[0])//选择第5行，但忽略第1个单元格，从第2个单元格开始操作
                    .value(column.getDisplay_name());
            columnIndex[0]++;
        });
        final int[] row = {1};
        dataList.forEach(dataMap -> {
            final int[] cell = {0};
            businessModel.getAllBcCols().forEach(column -> {
                String dataValue = CommonTools.Obj2String(dataMap.get(column.getS_column_name()));
                excel.cell(row[0], cell[0])
                        .value(StringUtil.decodeUnicode(dataValue)).warpText(true);
                cell[0]++;
            });
            row[0]++;
        });
        String timeSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = businessModel.getDisplay_name() + ".xls";
        String finalFileName = timeSuffix + "_" + fileName;
        excel.saveExcel(fileServerConfig.getFtpHome() + finalFileName);
        return finalFileName;
    }
}
