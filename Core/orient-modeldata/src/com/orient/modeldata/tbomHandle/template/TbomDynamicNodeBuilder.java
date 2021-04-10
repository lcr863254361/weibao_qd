package com.orient.modeldata.tbomHandle.template;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.ITreeNodeFilterModelBean;
import com.orient.businessmodel.bean.IdQueryCondition;
import com.orient.businessmodel.bean.impl.TreeNodeFilterModelBean;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.modeldata.bean.*;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.operationinterface.IDynamicTbom;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 静态Tbom节点构造器
 *
 * @author enjoy
 * @createTime 2016-05-23 14:48
 */
@Component
public class TbomDynamicNodeBuilder extends TbomNodeBuilder {
    @Autowired
    @Qualifier("RoleEngine")
    private IRoleUtil roleEngine;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Autowired
    IModelGridViewService modelGridViewService;


    /**
     * 初始化静态属性信息
     *
     * @param tBomNodeMaterial 构建Tbom材料
     * @param tBomNodes        返回结果
     */
    @Override
    public void initNormalAttr(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes) {
        //获取TBom构造材料
        TBomDynamicNodeMaterial tBomDynamicNodeMaterial = (TBomDynamicNodeMaterial) tBomNodeMaterial;
        tBomNodes.forEach(tBomNode -> {
            //注入基本属性信息
            tBomNode.setStaticDbId(tBomDynamicNodeMaterial.getStaticDbId());
            tBomNode.setTbomId(tBomDynamicNodeMaterial.getTreeDirId());
            tBomNode.setParentNode(tBomDynamicNodeMaterial.getParentNode());
            tBomNode.setLevel(null == tBomNodeMaterial.getParentNode() ? 1 : tBomNodeMaterial.getParentNode().getLevel() + 1);
        });
    }

    /**
     * @param tBomNodeMaterial 构建Tbom材料
     * @param tBomNodes        返回结果
     */
    @Override
    public void initTbomModel(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes) {
        //获取当前登录用户的ID
        String userId = UserContextUtil.getUserId();
        //获取TBom构造材料
        TBomDynamicNodeMaterial tBomDynamicNodeMaterial = (TBomDynamicNodeMaterial) tBomNodeMaterial;
        IDynamicTbom hibernateDynamicTbomNode = tBomDynamicNodeMaterial.getHibernateDynamicTbomNode();
        //父节点前端描述
        TBomNode fatherWebNode = tBomDynamicNodeMaterial.getParentNode();
        //准备模型信息
        IBusinessModel tagetbm;
        boolean isView = StringUtil.isEmpty(hibernateDynamicTbomNode.getView()) ? false : true;
        if (isView) {
            tagetbm = businessModelService.getBusinessModelById(userId, hibernateDynamicTbomNode.getView(), null, EnumInter.BusinessModelEnum.View);
        } else {
            tagetbm = businessModelService.getBusinessModelById(userId, hibernateDynamicTbomNode.getTable(), null, EnumInter.BusinessModelEnum.Table);
        }
        ITreeNodeFilterModelBean nodeFilter = null;
        //如果父节点是动态节点 则需要从父节点继承过滤条件
        if (fatherWebNode.getNodeType().equals(TBomNode.DYNAMIC_NODE)) {
            IDynamicTbom fatherHibernateDynamicTbomNode = tBomDynamicNodeMaterial.getFatherHibernateDynamicNode();
            IBusinessModel parentbm;
            if (fatherWebNode.getModelType().equalsIgnoreCase("0")) {
                parentbm = businessModelService.getBusinessModelById(userId, fatherWebNode.getModelId(), null, EnumInter.BusinessModelEnum.Table);
            } else {
                parentbm = businessModelService.getBusinessModelById(userId, fatherWebNode.getModelId(), null, EnumInter.BusinessModelEnum.View);
            }
            parentbm.getCustFilterList().addAll(fatherWebNode.getOriginalCustomerFilters());
            businessModelService.initModelRelation(tagetbm, parentbm, userId);
            //节点过滤
            if (fatherHibernateDynamicTbomNode.getExp() != null && !fatherHibernateDynamicTbomNode.getExp().isEmpty()) {
                nodeFilter = new TreeNodeFilterModelBean();
                String formatedExp = formatSqlFilterExpression(fatherHibernateDynamicTbomNode.getExp(), roleEngine);
                nodeFilter.setStatic_filter(formatedExp);
            }
            if (nodeFilter != null) {
                tagetbm.setTreeNodeFilterModelBean(nodeFilter);
            }
        } else {
            ITbom fatherHibernateStaticNode = tBomDynamicNodeMaterial.getFatherHibernateStaticNode();
            //过滤条件
            if (fatherHibernateStaticNode.getExpression() != null && !fatherHibernateStaticNode.getExpression().isEmpty()) {
                nodeFilter = new TreeNodeFilterModelBean();
                String formatedExp = formatSqlFilterExpression(fatherHibernateStaticNode.getExpression(), roleEngine);
                nodeFilter.setStatic_filter(formatedExp);
            }
        }
        //注入额外过滤条件
        if (nodeFilter != null) {
            tagetbm.setTreeNodeFilterModelBean(nodeFilter);
        }
        //获取节点展现描述
        String columnIds = hibernateDynamicTbomNode.getColumn();
        String[] colIds = columnIds.split(",");
        List<IBusinessColumn> bcList = new ArrayList<>();
        for (String colId : colIds) {
            bcList.add(tagetbm.getBusinessColumnByID(colId));
        }

        IBusinessModelQuery modelquery = orientSqlEngine.getBmService().createBomNodeQuery(tagetbm, bcList);
        //需要对查询出来的节点进行分组
        modelquery.orderAsc("MAX(TO_NUMBER(ID))");
        List<Map> reslutnodes = modelquery.list();
        //dataChange
        businessModelService.dataChangeModel(orientSqlEngine, tagetbm, reslutnodes, false);
        reslutnodes.forEach(data -> {
            tagetbm.clearCustomFilter();
            List<String> dataValue = new ArrayList<>();
            bcList.forEach(bc -> {
                String value = (String) data.get(bc.getS_column_name());
                CustomerFilter loopfilter = null;
                if (CommonTools.isNullString(value)) {
                    loopfilter = new CustomerFilter(bc.getS_column_name(), EnumInter.SqlOperation.IsNull, "");
                } else {
                    loopfilter = new CustomerFilter(bc.getS_column_name(), EnumInter.SqlOperation.Equal, value);
                }
                dataValue.add(dataChange(value, bc, data));
                tagetbm.appendCustomerFilter(loopfilter);
            });
            //如果不重复的话
            if (tBomNodes.stream().filter(dynamicNode -> dynamicNode.getText().equals(CommonTools.list2String(dataValue))).count() == 0) {
                IBusinessModelQuery dataModelQuery = orientSqlEngine.getBmService().createModelQuery(tagetbm);
                IdQueryCondition idQueryCondition = dataModelQuery.getIdQueryCondition();
                List<Map> subdataIds = dataModelQuery.list();
                TBomDynamicNode dynamicNode = new TBomDynamicNode();
                dynamicNode.setDynamicId(hibernateDynamicTbomNode.getId());
                dynamicNode.setModelId(tagetbm.getId());
                dynamicNode.setModelType(isView ? "1" : "0");
                dynamicNode.setText(CommonTools.list2String(dataValue));
                dynamicNode.getOriginalCustomerFilters().add(new CustomerFilter(idQueryCondition));
                subdataIds.forEach(dataIdMap -> {
                    String dataId = CommonTools.Obj2String(dataIdMap.get("ID"));
                    if (!StringUtil.isEmpty(dataId)) {
                        dynamicNode.getIdList().add(dataId);
                    }
                });
                //初始化关联模型信息
                initRefModels(tagetbm, dynamicNode, hibernateDynamicTbomNode);
                //增加其关联模型描述
                tBomNodes.add(dynamicNode);
            }
        });
    }

    //值转化
    private String dataChange(String value, IBusinessColumn bc, Map<String, String> changedDataMap) {
        String retVal = value;
        List<Map> dataList = new ArrayList<Map>() {{
            add(new HashMap<String, String>() {{
                put(bc.getS_column_name(), value);
            }});
        }};
        businessModelService.dataChangeColumn(orientSqlEngine, bc.getCol(), dataList);
        String dataChangedValue = (String) dataList.get(0).get(bc.getS_column_name());
        Long category = bc.getCol().getCategory();
        Restriction restriction = bc.getCol().getRestriction();
        //如果是外键或枚举类型
        if (category == IColumn.CATEGORY_RELATION ||
                (category == IColumn.CATEGORY_COMMON && restriction != null && restriction.getType() == 2l)) {
            dataChangedValue = changedDataMap.get(bc.getS_column_name() + "_display");
        }
        //如果是json格式
        if (StringUtil.isJson(dataChangedValue)) {
            List<String> showValues = new ArrayList<>();
            JsonUtil.json2List(dataChangedValue).stream().map((Map dataMap) -> dataMap.get("name")).forEach(showData -> {
                showValues.add((String) showData);
            });
            retVal = CommonTools.list2String(showValues);
        }
        //如果是选择器
        else if (bc.getCol().getCategory() == IColumn.CATEGORY_COMMON && !CommonTools.isNullString(bc.getCol().getSelector())) {
            retVal = changedDataMap.get(bc.getS_column_name() + "_display");
        }


        return retVal;
    }

    @Override
    public void initSpecial(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes) {
        String userId = UserContextUtil.getUserId();
        //获取TBom构造材料
        TBomDynamicNodeMaterial tBomDynamicNodeMaterial = (TBomDynamicNodeMaterial) tBomNodeMaterial;
        //获取动态父节点描述
        IDynamicTbom fatherHibernateDynamicNode = tBomDynamicNodeMaterial.getFatherHibernateDynamicNode();
        if (null != fatherHibernateDynamicNode) {
            //如果父节点展现形式为树形
            String parDisplayType = fatherHibernateDynamicNode.getDisplay();
            if ("树形".equals(parDisplayType)) {
                String[] father_sources = fatherHibernateDynamicNode.getSource().split(",");
                //插入模型展现节点
                for (String source_id : father_sources) {
                    /*判断数据源是表还是视图*/
                    String ext_node_type = source_id.charAt(0) == 't' ? EnumInter.BusinessModelEnum.Table.name() : EnumInter.BusinessModelEnum.View.name();
                    //获取模型描述
                    boolean bIsView = EnumInter.BusinessModelEnum.View.name().equals(ext_node_type) ? true : false;
                    IBusinessModel targetBm;
                    if (bIsView == false) {
                        targetBm = businessModelService.getBusinessModelById(userId, fatherHibernateDynamicNode.getTable(), null, EnumInter.BusinessModelEnum.Table);
                    } else {
                        targetBm = businessModelService.getBusinessModelById(userId, fatherHibernateDynamicNode.getView(), null, EnumInter.BusinessModelEnum.View);
                    }
                    //父节点前端描述
                    TBomNode fatherWebNode = tBomDynamicNodeMaterial.getParentNode();
                    IBusinessModel parentbm;
                    if (fatherWebNode.getModelType().equalsIgnoreCase("0")) {
                        parentbm = businessModelService.getBusinessModelById(userId, fatherWebNode.getModelId(), null, EnumInter.BusinessModelEnum.Table);
                    } else {
                        parentbm = businessModelService.getBusinessModelById(userId, fatherWebNode.getModelId(), null, EnumInter.BusinessModelEnum.View);
                    }
                    parentbm.getCustFilterList().addAll(fatherWebNode.getOriginalCustomerFilters());
                    businessModelService.initModelRelation(targetBm, parentbm, userId);
                    IBusinessModelQuery dataModelQuery = orientSqlEngine.getBmService().createModelQuery(targetBm);
                    IdQueryCondition idQueryCondition = dataModelQuery.getIdQueryCondition();
                    List<Map> subdataIds = dataModelQuery.list();
                    //转化为前端描述
                    TBomStaticNode staticNode = new TBomStaticNode();
                    staticNode.setText(targetBm.getDisplay_name());
                    staticNode.setModelType(bIsView ? "1" : "0");
                    staticNode.setModelId(targetBm.getId());
                    staticNode.setLeaf(true);
                    staticNode.getOriginalCustomerFilters().add(new CustomerFilter(idQueryCondition));
                    subdataIds.forEach(dataIdMap -> {
                        String dataId = CommonTools.Obj2String(dataIdMap.get("ID"));
                        if (!StringUtil.isEmpty(dataId)) {
                            staticNode.getIdList().add(dataId);
                        }
                    });
                    //绑定至前段对象
                    TBomModel tBomModel = new TBomModel(targetBm.getId(), targetBm.getDisplay_name(), bIsView ? "1" : "0");
                    CustomerFilter customerFilter = new CustomerFilter("ID", EnumInter.SqlOperation.In, CommonTools.list2String(staticNode.getIdList()));
                    tBomModel.setDefaultFilter(customerFilter);
                    staticNode.gettBomModels().add(tBomModel);
                    tBomNodes.add(0, staticNode);
                }
            }
        }
    }

    /**
     * 初始化关联模型信息
     *
     * @param mainBm
     * @param dynamicNode
     * @param hibernateDynamicTbomNode
     */
    private void initRefModels(IBusinessModel mainBm, TBomDynamicNode dynamicNode, IDynamicTbom hibernateDynamicTbomNode) {
        String userId = UserContextUtil.getUserId();
        String[] dataSources = hibernateDynamicTbomNode.getSource().split(",");
        for (String dataSource : dataSources) {
            //是否绑定模板
            Boolean containsTemplate = dataSource.contains("mtl");
            String sourceId = containsTemplate ? dataSource.substring(1, dataSource.indexOf("-")) : dataSource.substring(1, dataSource.length());
            String templateId = containsTemplate ? dataSource.substring(dataSource.indexOf("-") + 4, dataSource.length()) : "";
            boolean exists = dynamicNode.gettBomModels().stream().filter(loopTbomModel -> Integer.valueOf(loopTbomModel.getModelId()) == Integer.valueOf(sourceId)).count() >= 1l ? true : false;
            if (!exists) {
                /*判断数据源是表还是视图*/
                String ext_node_type = dataSource.charAt(0) == 't' ? EnumInter.BusinessModelEnum.Table.name() : EnumInter.BusinessModelEnum.View.name();
                //获取模型描述
                boolean bIsView = EnumInter.BusinessModelEnum.View.name().equals(ext_node_type) ? true : false;
                IBusinessModel refBm;
                if (bIsView == false) {
                    refBm = businessModelService.getBusinessModelById(userId, sourceId, null, EnumInter.BusinessModelEnum.Table);
                } else {
                    refBm = businessModelService.getBusinessModelById(userId, sourceId, null, EnumInter.BusinessModelEnum.View);
                }
                businessModelService.initModelRelation(refBm, mainBm, userId);
                IBusinessModelQuery modelquery = orientSqlEngine.getBmService().createModelQuery(refBm);
                IdQueryCondition idQueryCondition = modelquery.getIdQueryCondition();

                TBomModel tBomModel = new TBomModel(refBm.getId(), refBm.getDisplay_name(), bIsView ? "1" : "0");
                CustomerFilter customerFilter = new CustomerFilter(idQueryCondition);
                tBomModel.setDefaultFilter(customerFilter);
                tBomModel.setTemplateId(templateId);
                if (!StringUtil.isEmpty(templateId)) {
                    ModelGridViewEntity modelGridViewEntity = modelGridViewService.getById(Long.valueOf(templateId));
                    if (null != modelGridViewEntity) {
                        tBomModel.setTemplateJS(modelGridViewEntity.getExtendclass());
                        tBomModel.setUsePage(modelGridViewEntity.getNeedpage().intValue() == 1);
                        tBomModel.setPageSize(modelGridViewEntity.getPagesize().intValue());
                    }
                }
                dynamicNode.gettBomModels().add(tBomModel);
            }
        }
    }
}

