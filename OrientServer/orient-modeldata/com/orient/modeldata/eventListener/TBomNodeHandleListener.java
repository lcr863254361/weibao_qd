package com.orient.modeldata.eventListener;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.event.GetTbomNodesEvent;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.annotation.NodeHandle;
import com.orient.modeldata.tbomHandle.handle.TbomHandle;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.roleengine.impl.RoleUtilImpl;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TBom动态节点递归定制
 */
@Component
public class TBomNodeHandleListener extends OrientEventListener {
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
        Collection<TbomHandle> handlers = getSortedTbomHandle(param.getFatherNode());

        processNodeHandleAnnotation(param, handlers);
    }

    /**
     * 获取所有@NodeHandle注解
     *
     * @param fatherNode
     * @return
     */
    private List<TbomHandle> getSortedTbomHandle(TBomNode fatherNode) {
        List<TbomHandle> tetVal = new ArrayList<>();
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(TbomHandle.class);
        for (String beanName : beanNames) {
            TbomHandle impl = (TbomHandle) OrientContextLoaderListener.Appwac.getBean(beanName);
            //获取注解
            NodeHandle anno = impl.getClass().getAnnotation(NodeHandle.class);
            String tbomName = anno.tbomName();
            int order = anno.order();
            if (StringUtil.isEmpty(tbomName) || tbomName.equals("*")) {
                tetVal.add(impl);
            } else {
                if (null != fatherNode) {
                    String tbomId = fatherNode.getTbomId();
                    String currentTbomName = roleEngine.getRoleModel(false).getTbomById(tbomId).getName();
                    if (currentTbomName.equals(tbomName)) {
                        tetVal.add(impl);
                    }
                }
            }
        }
        return tetVal;
    }

    /**
     * 处理@NodeHandle注解
     */
    private void processNodeHandleAnnotation(GetTbomNodesEventParam param, Collection<TbomHandle> handlers) {
        //获取父节点前段描述
        TBomNode fatherNode = param.getFatherNode();
        for (TbomHandle handler : handlers) {
            handler.handleTreeNodes(param.getTbomNodes(), fatherNode);
        }

    }

}
