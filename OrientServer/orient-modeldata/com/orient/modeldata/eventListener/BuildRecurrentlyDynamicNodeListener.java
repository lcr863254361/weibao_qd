package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.ITreeNodeFilterModelBean;
import com.orient.businessmodel.bean.impl.TreeNodeFilterModelBean;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.bean.TBomDynamicNode;
import com.orient.modeldata.bean.TBomModel;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.event.GetTbomNodesEvent;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.annotation.RecurrentlyDynamicNode;
import com.orient.modeldata.tbomHandle.nodeModel.BuildRecurrentlyDynamicNode;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.operationinterface.IDynamicTbom;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.roleengine.impl.RoleUtilImpl;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TBom动态节点递归定制
 */
@Component
public class BuildRecurrentlyDynamicNodeListener extends OrientEventListener {
    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected RoleUtilImpl roleEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Autowired
    IModelGridViewService modelGridViewService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return isOrientEvent(eventType) && (eventType == GetTbomNodesEvent.class || GetTbomNodesEvent.class.isAssignableFrom(eventType));
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        //获取事件参数
        GetTbomNodesEventParam param = (GetTbomNodesEventParam) orientEvent.getParams();
        Collection<BuildRecurrentlyDynamicNode> handlers = getSortedBuildRecurrentlyDynamicNode();
        //获取父节点前段描述
        TBomNode fatherNode = param.getFatherNode();
        if (fatherNode != null && fatherNode.getModelId() != null) {
            processDynamicNodeRecurrentlyAnnotation(param, handlers);
        }
    }

    /**
     * 获取所有@DynamicNodeRecurrently注解
     *
     * @return
     */
    private Collection<BuildRecurrentlyDynamicNode> getSortedBuildRecurrentlyDynamicNode() {
        Map<Integer, BuildRecurrentlyDynamicNode> sortedMap = new TreeMap<>();
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(BuildRecurrentlyDynamicNode.class);
        for (String beanName : beanNames) {
            BuildRecurrentlyDynamicNode impl = (BuildRecurrentlyDynamicNode) OrientContextLoaderListener.Appwac.getBean(beanName);
            //获取注解
            RecurrentlyDynamicNode anno = impl.getClass().getAnnotation(RecurrentlyDynamicNode.class);
            int order = anno.order();
            sortedMap.put(order, impl);
        }
        return sortedMap.values();
    }

    /**
     * 处理@DynamicNodeRecurrently注解
     */
    private void processDynamicNodeRecurrentlyAnnotation(GetTbomNodesEventParam param, Collection<BuildRecurrentlyDynamicNode> handlers) {
        //获取父节点前段描述
        TBomNode fatherNode = param.getFatherNode();
        IBusinessModel bm = businessModelService.getBusinessModelById(fatherNode.getModelId(), EnumInter.BusinessModelEnum.getBusinessModelType(fatherNode.getModelType()));
        for (BuildRecurrentlyDynamicNode handler : handlers) {
            RecurrentlyDynamicNode anno = handler.getClass().getAnnotation(RecurrentlyDynamicNode.class);
            String modelName = anno.modelName();
            if (modelName.equals(bm.getName())) {
                //获取其静态节点ID
                String staticDbId = fatherNode.getStaticDbId();
                //获取所述tbomId
                String treeId = fatherNode.getTbomId();
                //获取Tbom根节点节点后端描述
                ITbom hibernateRootNode = roleEngine.getRoleModel(false).getTbomById(treeId);
                //获取最近的静态节点
                ITbom parentHibernateTbomNode = hibernateRootNode.getchildNodebyNodeId(staticDbId);
                //获取该静态节点下 动态节点描述
                SortedSet<IDynamicTbom> alldynamicNodes = parentHibernateTbomNode.getDynamicTbomSet();
                if (TBomNode.STATIC_NODE.equals(fatherNode.getNodeType())) {
                    //由于数据库平行保存动态节点 故只需要动态加载第一个即可
                    //只加载第一个
                    if (!alldynamicNodes.isEmpty()) {
                        List<TBomNode> tBomNodes = buildTBomNode(bm.getId(), fatherNode, alldynamicNodes.first(), handler);
                        //注入基本属性信息
                        tBomNodes.forEach(tBomNode -> {
                            tBomNode.setStaticDbId(staticDbId);
                            tBomNode.setTbomId(treeId);
                            tBomNode.setParentNode(fatherNode);
                        });
                        param.setTbomNodes(new ArrayList<>());
                        param.getTbomNodes().addAll(tBomNodes);
                    }
                } else if (TBomNode.DYNAMIC_NODE.equals(fatherNode.getNodeType())) {
                    //如果父节点为动态节点
                    TBomDynamicNode dynamicParentNode = (TBomDynamicNode) fatherNode;
                    //获取组件描述
                    IDynamicTbom muduleHibernateDynamicNode = alldynamicNodes.stream().filter(dynamicTbom -> dynamicTbom.getId().equals(dynamicParentNode.getDynamicId())).findFirst().get();

                    List<TBomNode> tBomNodes = buildTBomNode(bm.getId(), fatherNode, muduleHibernateDynamicNode, handler);

                    //注入基本属性信息
                    tBomNodes.forEach(tBomNode -> {
                        tBomNode.setStaticDbId(staticDbId);
                        tBomNode.setTbomId(treeId);
                        tBomNode.setParentNode(fatherNode);
                    });

                    param.getTbomNodes().addAll(tBomNodes);
                }
            }
        }
    }

    private List<TBomNode> buildTBomNode(String modelId, TBomNode fatherNode, IDynamicTbom muduleHibernateDynamicNode, BuildRecurrentlyDynamicNode handler) {
        List<TBomNode> retList = new ArrayList<>();
        IBusinessModel bm = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        //获取当前登录用户的ID
        String userId = UserContextUtil.getUserId();
        String schemaId = bm.getSchema().getId();

        //父节点过滤条件
        if (fatherNode.getNodeType().equals(TBomNode.DYNAMIC_NODE)) {
            List<String> ids = fatherNode.getIdList();
            List<CustomerFilter> filters = handler.getDynamicFatherNodeFilters(bm, fatherNode);
            for (CustomerFilter filter : filters) {
                bm.appendCustomerFilter(filter);
            }
            //节点过滤
            ITreeNodeFilterModelBean nodeFilter = null;
            if (muduleHibernateDynamicNode.getExp() != null && !muduleHibernateDynamicNode.getExp().isEmpty()) {
                nodeFilter = new TreeNodeFilterModelBean();
                nodeFilter.setStatic_filter(muduleHibernateDynamicNode.getExp());
                bm.setTreeNodeFilterModelBean(nodeFilter);
            }
        } else {
            List<CustomerFilter> filters = handler.getStaticFatherNodeFilters(bm, fatherNode);
            for (CustomerFilter filter : filters) {
                bm.appendCustomerFilter(filter);
            }
        }

        IBusinessModelQuery modelquery = orientSqlEngine.getBmService().createModelQuery(bm);
        List<Map<String, Object>> resultNodes = modelquery.list();
        for (Map<String, Object> resultNode : resultNodes) {
            String id = CommonTools.Obj2String(resultNode.get("ID"));
            String text = handler.getNodeText(bm, resultNode);
            TBomDynamicNode dynamicNode = new TBomDynamicNode();
            dynamicNode.setDynamicId(muduleHibernateDynamicNode.getId());
            dynamicNode.setModelId(modelId);
            dynamicNode.setModelType("0");
            dynamicNode.setText(text);
            dynamicNode.getIdList().add(id);
            //初始化关联模型信息
            initRefModels(modelId, dynamicNode, muduleHibernateDynamicNode, handler);
            retList.add(dynamicNode);
        }

        return retList;
    }

    private void initRefModels(String modelId, TBomDynamicNode dynamicNode, IDynamicTbom hibernateDynamicTbomNode, BuildRecurrentlyDynamicNode handler) {
        IBusinessModel mainBm = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        String userId = UserContextUtil.getUserId();
        String[] dataSources = hibernateDynamicTbomNode.getSource().split(",");
        if (!CommonTools.isEmptyList(dynamicNode.getOriginalCustomerFilters())) {
            mainBm.getCustFilterList().addAll(dynamicNode.getOriginalCustomerFilters());
        }
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
                IBusinessModelQuery dataModelQuery = orientSqlEngine.getBmService().createModelQuery(refBm);
                List<Map<String, Object>> results = dataModelQuery.list();
                TBomModel tBomModel = new TBomModel(refBm.getId(), refBm.getDisplay_name(), bIsView ? "1" : "0");
                //设置动态模型过滤条件
                CustomerFilter customerFilter = handler.getCustomerFilter(refBm, dynamicNode, results);
                tBomModel.setDefaultFilter(customerFilter);
                tBomModel.setTemplateId(templateId);
                if (!StringUtil.isEmpty(templateId)) {
                    ModelGridViewEntity modelGridViewEntity = modelGridViewService.getById(Long.valueOf(templateId));
                    if (null != modelGridViewEntity) {
                        tBomModel.setTemplateJS(modelGridViewEntity.getExtendclass());
                    }
                }
                dynamicNode.gettBomModels().add(tBomModel);
            }
        }
        //对生成后的静态节点的所有模型进行处理
        handler.customAllModels(dynamicNode.gettBomModels());
    }

}
