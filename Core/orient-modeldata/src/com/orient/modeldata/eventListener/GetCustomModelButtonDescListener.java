package com.orient.modeldata.eventListener;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.background.bean.ModelBtnInstanceWrapper;
import com.orient.background.business.ModelBtnInstanceBusiness;
import com.orient.modeldata.event.GetModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.modeldata.util.ButtonBuilder;
import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.operationinterface.IMatrixRight;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.modelDesc.model.OrientModelDesc;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取默认按钮描述
 */
@Component
public class GetCustomModelButtonDescListener extends OrientEventListener {

    @Autowired
    ModelBtnInstanceBusiness modelBtnInstanceBusiness;

    @Autowired
    IModelGridViewService modelGridViewService;

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
        //根据用户权限 过滤用户操作按钮
        if (!StringUtil.isEmpty(templateId)) {
            //获取模型权限
            IBusinessModel businessModel = (IBusinessModel) param.getFlowParams("BM");
            IMatrixRight matrixRight = businessModel.getMatrixRight();
            Long modelGridId = Long.valueOf(templateId);
            //获取自定义表格描述
            ModelGridViewEntity modelGridViewEntity = modelGridViewService.getById(modelGridId);
            List<Long> btnIds = StringUtil.isEmpty(modelGridViewEntity.getBtns()) ? new ArrayList<>() : changeIdsToLongList(modelGridViewEntity.getBtns());
            //获取系统默认按钮实例
            if(!CommonTools.isEmptyList(btnIds)){
                List<ModelBtnInstanceWrapper> modelBtnInstanceEntityList = modelBtnInstanceBusiness.listByCriterion(Restrictions.in("id", btnIds));
                List<ModelBtnInstanceEntity> btns = ButtonBuilder.filterButtonByRight(modelBtnInstanceEntityList, matrixRight);
                //排序
                btns.sort((ModelBtnInstanceEntity e1, ModelBtnInstanceEntity e2) ->
                                btnIds.indexOf(e1.getId()) - btnIds.indexOf(e2.getId())
                );
                //根据权限过滤
                orientModelDesc.setBtns(btns);
            }
        }
    }

    private List<Long> changeIdsToLongList(String btns) {
        List<Long> retVal = new ArrayList<>();
        for (String btnIdStr : btns.split(",")) {
            retVal.add(Long.valueOf(btnIdStr));
        }
        return retVal;
    }
}
