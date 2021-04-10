package com.orient.background.business;

import com.orient.background.bean.CfQuantityInstanceVO;
import com.orient.background.bean.ModelPathVO;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.metamodel.dijkstra.DijikstraSchemaTravel;
import com.orient.metamodel.dijkstra.DijkstraSchema;
import com.orient.metamodel.dijkstra.IDijikstraSchemaTravel;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.metamodel.pedigree.TableEdge;
import com.orient.sysmodel.domain.quantity.CfQuantityDO;
import com.orient.sysmodel.domain.quantity.CfQuantityInstanceDO;
import com.orient.sysmodel.domain.quantity.CfQuantityTemplateDO;
import com.orient.sysmodel.domain.quantity.CwmSysNumberunitDO;
import com.orient.sysmodel.service.quantity.IQuantityInstanceService;
import com.orient.sysmodel.service.quantity.IQuantityService;
import com.orient.sysmodel.service.quantity.IQuantityTemplateService;
import com.orient.sysmodel.service.quantity.IUnitService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class QuantityInstanceBusiness extends BaseHibernateBusiness<CfQuantityInstanceDO> {

    @Autowired
    IQuantityInstanceService quantityInstanceService;

    @Autowired
    IQuantityTemplateService quantityTemplateService;

    @Autowired
    IQuantityService quantityService;

    @Autowired
    IUnitService unitService;

    @Override
    public IQuantityInstanceService getBaseService() {
        return quantityInstanceService;
    }


    public void createByTemplate(Long modelId, Long dataId, Long[] templateIds) {
        List<CfQuantityTemplateDO> quantityTemplateDOS = quantityTemplateService.getByIds(templateIds);
        List<CfQuantityInstanceDO> exists = quantityInstanceService.list(Restrictions.eq("modelId", modelId),
                Restrictions.eq("dataId", dataId));
        List<Long> quantityIds = exists.stream().map(quantityInstanceDO -> quantityInstanceDO.getBelongQuantity().getId()).collect(Collectors.toList());
        quantityTemplateDOS.forEach(template -> template.getTemplateRelations().forEach(templateRelation -> {
            CfQuantityDO quantityDO = templateRelation.getBelongQuantity();
            if (!quantityIds.contains(quantityDO.getId())) {
                quantityIds.add(quantityDO.getId());
                CfQuantityInstanceDO quantityInstanceDO = new CfQuantityInstanceDO();
                quantityInstanceDO.setModelId(modelId);
                quantityInstanceDO.setDataId(dataId);
                quantityInstanceDO.setBelongQuantity(quantityDO);
                quantityInstanceDO.setNumberunitDO(quantityDO.getNumberunitDO());
                quantityInstanceService.save(quantityInstanceDO);
            }
        }));
    }

    public ExtGridData<CfQuantityInstanceVO> listSpecial(Integer page, Integer limit, CfQuantityInstanceDO filter) {
        ExtGridData<CfQuantityInstanceVO> retVal = new ExtGridData<>();
        ExtGridData<CfQuantityInstanceDO> originalData = super.list(page, limit, filter);
        BeanUtils.copyProperties(retVal, originalData);
        List<CfQuantityInstanceDO> results = originalData.getResults();
        List<CfQuantityInstanceVO> vos = new ArrayList<>();
        results.forEach(quantityInstanceDO -> {
            CfQuantityInstanceVO quantityInstanceVO = new CfQuantityInstanceVO(quantityInstanceDO);
            vos.add(quantityInstanceVO);
        });
        retVal.setResults(vos);
        return retVal;
    }

    public void createByManual(Long modelId, Long dataId, Long[] quantityIds) {
        List<CfQuantityInstanceDO> exists = quantityInstanceService.list(Restrictions.eq("modelId", modelId),
                Restrictions.eq("dataId", dataId));
        List<Long> existIds = exists.stream().map(quantityInstanceDO -> quantityInstanceDO.getBelongQuantity().getId()).collect(Collectors.toList());
        List<CfQuantityDO> quantityDOS = quantityService.getByIds(quantityIds);
        quantityDOS.forEach(quantityDO -> {
            if (!existIds.contains(quantityDO.getId())) {
                CfQuantityInstanceDO quantityInstanceDO = new CfQuantityInstanceDO();
                quantityInstanceDO.setModelId(modelId);
                quantityInstanceDO.setDataId(dataId);
                quantityInstanceDO.setBelongQuantity(quantityDO);
                quantityInstanceDO.setNumberunitDO(quantityDO.getNumberunitDO());
                quantityInstanceService.save(quantityInstanceDO);
            }
        });

    }

    public List<ModelPathVO> getModelPath(String startModelId, String endModelId) {
        List<ModelPathVO> retVal = new ArrayList<>();
        IBusinessModel relModel = businessModelService.getBusinessModelById(startModelId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel mainModel = businessModelService.getBusinessModelById(endModelId, EnumInter.BusinessModelEnum.Table);
        if (startModelId.equals(endModelId)) {
            ModelPathVO modelPathVO = new ModelPathVO();
            modelPathVO.setModelId(startModelId);
            modelPathVO.setModelName(relModel.getDisplay_name());
            retVal.add(modelPathVO);
        } else {
            IMetaModel metaModel = metaEngine.getMeta(false);
            DijkstraSchema graph = metaModel.getDijkstraGraphMap().get(mainModel.getSchema().getId());
            IDijikstraSchemaTravel graphTravel = new DijikstraSchemaTravel(graph);
            //遍历Schema谱系
            List<TableEdge> minPath = graphTravel.getMinStep(relModel.getMatrix().getMainTable(), mainModel.getMatrix().getMainTable());

            if (!CommonTools.isEmptyList(minPath)) {
                final ModelPathVO[] lastModelPath = {null};
                final ModelPathVO[] firstPoint = {null};
                minPath.forEach(tableEdge -> {
                    ITable startPoint = tableEdge.getStart();
                    if (null == lastModelPath[0]) {
                        //初始化AB
                        firstPoint[0] = converITableToModelPath(startPoint, tableEdge);
                        ITable endPoint = tableEdge.getEnd();
                        ModelPathVO endModelPath = converITableToModelPath(endPoint, tableEdge);
                        firstPoint[0].setNextModel(endModelPath);
                        lastModelPath[0] = endModelPath;
                    } else {
                        //初始化BC中的C CD中的D ...
                        ITable endPoint = tableEdge.getEnd();
                        ModelPathVO endModelPath = converITableToModelPath(endPoint, tableEdge);
                        lastModelPath[0].setEdgeType(tableEdge.getEdgeType());
                        lastModelPath[0].setNextModel(endModelPath);
                        lastModelPath[0] = endModelPath;
                    }
                });
                retVal.add(firstPoint[0]);
            } else {
                throw new OrientBaseAjaxException("", "未找到相关路径");
            }
        }
        return retVal;
    }

    /**
     * @param point
     * @param tableEdge
     * @return
     */
    private ModelPathVO converITableToModelPath(ITable point, TableEdge tableEdge) {
        ModelPathVO retVal = new ModelPathVO();
        retVal.setModelId(point.getId());
        retVal.setModelName(point.getDisplayName());
        retVal.setEdgeType(tableEdge.getEdgeType());
        retVal.setManyToMany(tableEdge.isManyToMany());
        return retVal;
    }


    public void updateSpecial(CfQuantityInstanceVO formValue) {
        CfQuantityInstanceDO quantityInstanceDO = quantityInstanceService.getById(formValue.getId());
        Long unitId = formValue.getUnitId();
        if (!unitId.equals(quantityInstanceDO.getNumberunitDO().getId())) {
            CwmSysNumberunitDO cwmSysNumberunitDO = unitService.getById(unitId);
            quantityInstanceDO.setNumberunitDO(cwmSysNumberunitDO);
            super.update(quantityInstanceDO);
        }
    }

    /**
     * @param modelPathVO
     * @param dataId
     * @return 根据父子层级 依次初始化每层节点
     */
    public List<Map<String, String>> extractModelPath(ModelPathVO modelPathVO, Long dataId) {
        List<Map<String, String>> retVal = new ArrayList<>();
        //init first node
        if (null != modelPathVO) {
            initFirstNode(modelPathVO, dataId, retVal);
            extraData(modelPathVO.getNextModel(), modelPathVO, dataId, retVal);
        }
        return retVal;
    }

    /**
     * 初始化叶子节点
     *
     * @param modelPathVO 叶子节点描述
     * @param dataId      叶子节点数据ID
     * @param retVal      递归存储
     */
    private void initFirstNode(ModelPathVO modelPathVO, Long dataId, List<Map<String, String>> retVal) {
        IBusinessModel model = businessModelService.getBusinessModelById(modelPathVO.getModelId(), EnumInter.BusinessModelEnum.Table);
        Map<String, String> dataMap = orientSqlEngine.getBmService().createModelQuery(model).findById(dataId.toString());
        businessModelService.dataChangeModel(orientSqlEngine, model, dataMap, true);
        Map<String, String> tmpMap = converToModelData(dataMap, model);
        retVal.add(tmpMap);
    }

    /**
     * 递归初始化节点
     *
     * @param modelPathVO  待初始化节点
     * @param sonModelPath
     * @param parentDataId
     * @param retVal
     */
    private void extraData(ModelPathVO modelPathVO, ModelPathVO sonModelPath, Long parentDataId, List<Map<String, String>> retVal) {
        //TODO 完善反向遍历
        //TODO 完善多对多
        if (null == modelPathVO) {
            return;
        }
        if (modelPathVO.getEdgeType().intValue() == 0) {
            throw new OrientBaseAjaxException("", "暂不支持一对多方向遍历");
        }
        if (modelPathVO.isManyToMany()) {
            throw new OrientBaseAjaxException("", "暂不支持多对多遍历");
        }
        IBusinessModel model = businessModelService.getBusinessModelById(modelPathVO.getModelId(), EnumInter.BusinessModelEnum.Table);
        if (null != sonModelPath) {
            IBusinessModel parentModel = businessModelService.getBusinessModelById(sonModelPath.getModelId(), EnumInter.BusinessModelEnum.Table);
            Map<String, String> sonDataMap = orientSqlEngine.getBmService().createModelQuery(parentModel).findById(parentDataId.toString());
            CustomerFilter filter = new CustomerFilter("ID", EnumInter.SqlOperation.Equal, sonDataMap.get(model.getS_table_name() + "_ID"));
            model.appendCustomerFilter(filter);
        }
        List<Map<String, String>> dataList = orientSqlEngine.getBmService().createModelQuery(model).list();
        if (!CommonTools.isEmptyList(dataList)) {
            Map<String, String> dataMap = dataList.get(0);
            businessModelService.dataChangeModel(orientSqlEngine, model, dataMap, true);
            Map<String, String> tmpMap = converToModelData(dataMap, model);
            retVal.add(tmpMap);
            extraData(modelPathVO.getNextModel(), modelPathVO, Long.valueOf(dataMap.get("ID")), retVal);
        }
    }

    /**
     * 业务数据转化为采集节点数据
     *
     * @param dataMap 业务数据
     * @param model   业务模型
     * @return
     */
    private Map<String, String> converToModelData(Map<String, String> dataMap, IBusinessModel model) {
        String displayData = getDisplayData(model, dataMap);
        String dataId = dataMap.get("ID");
        Map<String, String> tmpMap = new HashMap<>();
        tmpMap.put("modelId", model.getId());
        tmpMap.put("dataId", dataId);
        tmpMap.put("displayName", displayData);
        return tmpMap;
    }
}
