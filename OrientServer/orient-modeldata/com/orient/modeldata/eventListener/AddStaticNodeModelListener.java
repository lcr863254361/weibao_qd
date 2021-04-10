package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.bean.TBomModel;
import com.orient.modeldata.bean.TBomStaticNode;
import com.orient.modeldata.event.GetTbomNodesEvent;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.annotation.StaticNodeModel;
import com.orient.modeldata.tbomHandle.nodeModel.AddStaticNodeModel;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.roleengine.impl.RoleUtilImpl;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.CommonTools;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.model.BaseNode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 静态TBom节点定制
 */
@Component
public class AddStaticNodeModelListener extends OrientEventListener {
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
        List<BaseNode> tbomNodes = param.getTbomNodes();
        Collection<AddStaticNodeModel> handlers = getSortedStaticNodeModel();
        if(tbomNodes!=null && tbomNodes.size()>0) {
            for(BaseNode tbomNode : tbomNodes) {
                if(tbomNode instanceof TBomStaticNode) {
                    TBomStaticNode staticNode = (TBomStaticNode) tbomNode;
                    processStaticNodeModelAnnotation(staticNode, handlers);

                }
            }
        }
    }

    /**
     * 获取所有@StaticNodeModel注解
     * @return
     */
    private Collection<AddStaticNodeModel> getSortedStaticNodeModel() {
        Map<Integer, AddStaticNodeModel> sortedMap = new TreeMap<>();
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(AddStaticNodeModel.class);
        for (String beanName : beanNames) {
            AddStaticNodeModel impl = (AddStaticNodeModel)OrientContextLoaderListener.Appwac.getBean(beanName);
            //获取注解
            StaticNodeModel anno = impl.getClass().getAnnotation(StaticNodeModel.class);
            int order = anno.order();
            sortedMap.put(order, impl);
        }
        return sortedMap.values();
    }

    /**
     * 处理@StaticNodeModel注解
     */
    private void processStaticNodeModelAnnotation(TBomStaticNode staticNode, Collection<AddStaticNodeModel> handlers) {
        for(AddStaticNodeModel handler : handlers) {
            StaticNodeModel anno = handler.getClass().getAnnotation(StaticNodeModel.class);
            String tbomName = anno.tbomName();
            if(tbomName!=null && tbomName.equals(staticNode.getText())) {
                //获取静态节点新增模型
                IBusinessModel bm = handler.getBusinessModel();
                if(bm == null) {
                    continue;
                }
                //绑定至前段对象
                TBomModel tBomModel = new TBomModel(bm.getId(), bm.getDisplay_name(), EnumInter.BusinessModelEnum.Table.toString());
                //设置新增动态模型过滤条件
                CustomerFilter defaultFilter = handler.getCustomerFilter(bm);
                tBomModel.setDefaultFilter(defaultFilter);
                String template = handler.getTemplateName();
                if(!CommonTools.isNullString(template)) {
                    ModelGridViewEntity modelGridViewEntity =
                            modelGridViewService.get(Restrictions.eq("modelid", bm.getId()), Restrictions.eq("name", template));
                    if(modelGridViewEntity != null) {
                        tBomModel.setTemplateId(String.valueOf(modelGridViewEntity.getId()));
                        tBomModel.setTemplateJS(modelGridViewEntity.getExtendclass());
                        tBomModel.setUsePage(modelGridViewEntity.getNeedpage().intValue() == 1);
                        tBomModel.setPageSize(modelGridViewEntity.getPagesize().intValue());
                    }
                }
                List<TBomModel> tBomModels = new ArrayList<>();
                tBomModels.add(tBomModel);
                tBomModels.addAll(staticNode.gettBomModels());
                staticNode.settBomModels(tBomModels);
                //对生成后的静态节点的所有模型进行处理
                handler.customAllModels(staticNode.gettBomModels());
            }
        }

    }

}
