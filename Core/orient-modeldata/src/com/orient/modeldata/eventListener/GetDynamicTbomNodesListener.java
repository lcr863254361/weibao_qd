package com.orient.modeldata.eventListener;

import com.orient.web.model.BaseNode;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.event.GetTbomNodesEvent;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.factory.StragetyFactory;
import com.orient.modeldata.tbomHandle.factory.impl.GetDTNSByDNFactory;
import com.orient.modeldata.tbomHandle.factory.impl.GetDTNSBySNFactory;
import com.orient.modeldata.tbomHandle.tbomContext.TbomNodeContext;
import com.orient.modeldata.tbomHandle.tbomStrategy.TbomNodeStrategy;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 根据父节点信息获取Tbom动态子节点信息
 *
 * @author enjoy
 * @createTime 2016-05-21 9:52
 */
@Component
public class GetDynamicTbomNodesListener extends OrientEventListener {

    @Autowired
    protected ISqlEngine orientSqlEngine;

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
        //获取父节点ID
        String node = param.getNode();
        //获取父节点前段描述
        TBomNode fatherNode = param.getFatherNode();
        if (!"root".equals(node) && null != fatherNode) {
            //获取根节点信息
            boolean bStatic = fatherNode.getNodeType().equalsIgnoreCase(TBomNode.STATIC_NODE) ? true : false;
            StragetyFactory stragetyFactory = bStatic ? new GetDTNSBySNFactory() : new GetDTNSByDNFactory();
            //获取子节点策略
            TbomNodeStrategy tbomNodeStrategy = stragetyFactory.createTbomNodeStrategy();
            //获取策略容器
            TbomNodeContext tbomNodeContext = new TbomNodeContext(tbomNodeStrategy);
            //获取子节点信息
            List<BaseNode> dynamicNodes = tbomNodeContext.getSonTbomNodes(param);
            //增加至返回值中
            param.getTbomNodes().addAll(dynamicNodes);
        }
    }
}

