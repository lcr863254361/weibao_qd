package com.orient.devdataobj.event.listener;

import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.devdataobj.event.GetDataObjEvent;
import com.orient.devdataobj.event.param.GetDataObjParam;
import com.orient.devdatatype.business.DataSubTypeBusiness;
import com.orient.devdatatype.business.DataTypeBusiness;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.domain.taskdata.DataSubTypeEntity;
import com.orient.sysmodel.domain.taskdata.DataTypeEntity;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by mengbin on 16/8/6.
 * Purpose:
 * Detail:转化为前台需要的Bean
 */
@Component
public class ConvertDataObjBeanListener extends OrientEventListener {

    @Autowired
    private DataObjectBusiness dataObjectBusiness;

    @Autowired
    protected IRoleUtil roleEngine;

    @Override
    @OrientEventCheck()
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        //该监听器能够处理对应事件及其子类事件
        return GetDataObjEvent.class == aClass || GetDataObjEvent.class.isAssignableFrom(aClass);

    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (this.isAbord(applicationEvent)) {
            return;
        }
        GetDataObjParam param = (GetDataObjParam) ((OrientEvent) applicationEvent).getParams();
        List<DataObjectEntity> objs = (List<DataObjectEntity>) param.getFlowParams("DataObjEcntitys");
        try {
            List<DataObjectBean> dataObjectBeanList = dataObjectBusiness.dataChange(objs);
            param.retDataObjs.addAll(dataObjectBeanList);
        } catch (Exception e) {
            return;
        }
    }

}
