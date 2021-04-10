package com.orient.modeldata.eventListener;

import com.orient.web.model.BaseNode;
import com.orient.modeldata.event.GetTbomNodesEvent;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.factory.StragetyFactory;
import com.orient.modeldata.tbomHandle.factory.impl.GetSTNSBySNFactory;
import com.orient.modeldata.tbomHandle.factory.impl.GetTbomRootNodesFactory;
import com.orient.modeldata.tbomHandle.tbomContext.TbomNodeContext;
import com.orient.modeldata.tbomHandle.tbomStrategy.TbomNodeStrategy;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 根据父节点信息获取Tbom静态节点
 *
 * @author enjoy
 * @createTime 2016-05-21 9:52
 */
@Component
public class GetStaticTbomNodesListener extends OrientEventListener {

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
        //获取父节点属性
        String nodeAttr = param.getNodeAttr();
        //获取所属功能点ID
        Long belongFunctionId = param.getBelongFunctionId();
        StragetyFactory stragetyFactory;
        if ("root".equals(node) && null != belongFunctionId && StringUtil.isEmpty(nodeAttr)) {
            //获取根节点信息
            stragetyFactory = new GetTbomRootNodesFactory();
        } else {
            //获取静态子节点
            stragetyFactory = new GetSTNSBySNFactory();
        }
        //获取子节点策略
        TbomNodeStrategy tbomNodeStrategy = stragetyFactory.createTbomNodeStrategy();
        //获取策略容器
        TbomNodeContext tbomNodeContext = new TbomNodeContext(tbomNodeStrategy);
        //获取子节点信息
        List<BaseNode> staticNodes = tbomNodeContext.getSonTbomNodes(param);
        //增加至返回值中
        param.getTbomNodes().addAll(staticNodes);
    }
}

