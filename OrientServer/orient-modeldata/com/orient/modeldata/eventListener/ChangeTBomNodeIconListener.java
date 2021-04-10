package com.orient.modeldata.eventListener;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.event.GetTbomNodesEvent;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.annotation.NodeIcon;
import com.orient.modeldata.tbomHandle.icon.TbomIcon;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.roleengine.impl.RoleUtilImpl;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.model.BaseNode;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qjs on 2017/2/11.
 * TBom节点图标监听器
 */
@Component
public class ChangeTBomNodeIconListener extends OrientEventListener {
    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected RoleUtilImpl roleEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

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
        //获取父节点前段描述
        TBomNode fatherNode = param.getFatherNode();
        //if(fatherNode != null) {

            List<BaseNode> nodes = param.getTbomNodes();
            List<TBomNode> tBomNodes = new ArrayList<>();
            if(nodes.size() > 0) {//避免重复处理
                nodes.forEach(node->{
                    TBomNode tNode = (TBomNode)node;
                    tBomNodes.add(tNode);
                });
                processIconAnnotation(tBomNodes);
            }

        //}
    }

    /**
     * 处理@NodeIcon注解
     */
    public void processIconAnnotation(List<TBomNode> nodes) {
        Map<Integer,TbomIcon> tbomMap = new LinkedHashMap<>();
        //获取所有实现TbomIcon接口的实现类
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.modeldata.tbomHandle.icon.TbomIcon.class);
        for (String beanName : beanNames) {
            TbomIcon tbomIcon = (TbomIcon)OrientContextLoaderListener.Appwac.getBean(beanName);
            //获取注解
            NodeIcon nodeIcon = tbomIcon.getClass().getAnnotation(NodeIcon.class);
            int order = nodeIcon.order();
            tbomMap.put(order,tbomIcon);
        }
        //排序
        List<Integer> sortedKeys = tbomMap.keySet().stream().sorted((i1, i2) -> {
            return i1 - i2;
        }).collect(Collectors.toList());
        sortedKeys.forEach(order -> {
            String userId = UserContextUtil.getUserId();
            List<IRole> roleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
            TbomIcon tbomIcon = tbomMap.get(order);
            String tbomName = tbomIcon.getClass().getAnnotation(NodeIcon.class).tbomName();
            String tbomDirId = nodes.get(0).getTbomId();
            boolean match = false;
            roleList.forEach(role -> {
                List<ITbom> tboms = role.getAllTboms();
                tboms.forEach(tbom -> {
                    if(tbom.getId().equals(tbomDirId)) {
                        String name = tbom.getName();
                        if(name.equals(tbomName)) {
                            tbomIcon.setIconCls(nodes);
                        }
                    }
                });
            });
        });
    }

}
