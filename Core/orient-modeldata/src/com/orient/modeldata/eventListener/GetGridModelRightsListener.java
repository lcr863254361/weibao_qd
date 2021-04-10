package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.modeldata.event.GetGridModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.sysmodel.operationinterface.IMatrixRight;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取模型表格描述时 附加权限信息
 *
 * @author enjoy
 * @creare 2016-04-01 15:06
 */
@Component
public class GetGridModelRightsListener extends OrientEventListener {

    @Qualifier("RoleEngine")
    @Autowired
    protected IRoleUtil roleEngine;

    @Autowired
    IBusinessModelService businessModelService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == GetGridModelDescEvent.class || GetGridModelDescEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        //获取事件参数
        GetModelDescEventParam param = (GetModelDescEventParam) orientEvent.getParams();
        //获取模型ID
        String modelId = param.getModelId();
        String isView = param.getIsView();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        //获取DS模型
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, modelTypeEnum);
        //存至過程變量中
        param.setFlowParams("BM", businessModel);
    }

}
