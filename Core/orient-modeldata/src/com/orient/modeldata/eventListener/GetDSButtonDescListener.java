package com.orient.modeldata.eventListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.background.bean.ModelBtnInstanceWrapper;
import com.orient.background.business.ModelBtnInstanceBusiness;
import com.orient.config.ConfigInfo;
import com.orient.modeldata.event.GetModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.modeldata.util.ButtonBuilder;
import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.sysmodel.operationinterface.IMatrixRight;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.modelDesc.model.OrientModelDesc;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取默认按钮描述
 */
@Component
public class GetDSButtonDescListener extends OrientEventListener {

    @Autowired
    ModelBtnInstanceBusiness modelBtnInstanceBusiness;


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == GetModelDescEvent.class || GetModelDescEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        //获取事件参数
        GetModelDescEventParam param = (GetModelDescEventParam) orientEvent.getParams();
        //获取模板ID
        String templateId = param.getTemplateId();
        //获取模型描述
        OrientModelDesc orientModelDesc = param.getOrientModelDesc();
        IBusinessModel businessModel = (IBusinessModel) param.getFlowParams("BM");
        IMatrixRight matrixRight = businessModel.getMatrixRight();
        //根据用户权限 过滤用户操作按钮
        if (StringUtil.isEmpty(templateId)) {
            //获取系统默认按钮实例
            List<ModelBtnInstanceWrapper> modelBtnInstanceEntityList = modelBtnInstanceBusiness.listByCriterion(Restrictions.eq("issystem", 1l));
            List<ModelBtnInstanceEntity> btns = ButtonBuilder.filterButtonByRight(modelBtnInstanceEntityList, matrixRight);
            //视图只保留详细、查询、查询全部功能按钮 暂不支持视图的新增、修改、删除以及流程等功能
            if (businessModel.getModelType().equals(EnumInter.BusinessModelEnum.View)) {
                List<ModelBtnInstanceEntity> toRemoveBtns = btns.stream().filter(modelBtnInstanceEntity -> !ConfigInfo.VIEW_DIC.contains(modelBtnInstanceEntity.getCode())).collect(Collectors.toList());
                btns.removeAll(toRemoveBtns);
            }
            //根据权限过滤
            orientModelDesc.setBtns(btns);
        }
    }
}
