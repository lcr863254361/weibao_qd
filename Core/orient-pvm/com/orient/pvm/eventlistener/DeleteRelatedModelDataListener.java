package com.orient.pvm.eventlistener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.pvm.event.DeleteCheckModelEvent;
import com.orient.pvm.eventparam.DeleteCheckModelEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.service.pvm.impl.TaskCheckModelService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckRelationService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/10 0010.
 * 删除检查模型所关联的业务模型数据
 */
@Service
public class DeleteRelatedModelDataListener extends OrientEventListener {

    @Autowired
    private TaskCheckModelService taskCheckModelService;


    @Autowired
    private TaskCheckRelationService taskCheckRelationService;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Override
    @OrientEventCheck()
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        //该监听器能够处理对应事件及其子类事件
        return DeleteCheckModelEvent.class == aClass || DeleteCheckModelEvent.class.isAssignableFrom(aClass);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (this.isAbord(applicationEvent)) {
            return;
        }
        String userId = UserContextUtil.getUserId();
        DeleteCheckModelEventParam eventParam = (DeleteCheckModelEventParam) ((OrientEvent) applicationEvent).getParams();
        Long[] toDelCheckModelIds = eventParam.getToDelIds();
        List<TaskCheckModel> toDelCheckModels = taskCheckModelService.getByIds(toDelCheckModelIds);
        List<TaskCheckRelation> toDelRelations = new ArrayList<>();
        toDelCheckModels.forEach(taskCheckModel -> {
            if (null == taskCheckModel.getCheckmodelid())
                return;
            TaskCheckRelation example = new TaskCheckRelation();
            example.setNodeId(taskCheckModel.getNodeId());
            example.setCheckmodelid(taskCheckModel.getCheckmodelid());
            List<TaskCheckRelation> taskCheckRelations = taskCheckRelationService.listBeansByExample(example);
            toDelRelations.addAll(taskCheckRelations);
            //获取模型数据id集合
            if (null != taskCheckModel.getCheckmodelid()) {
                try {
                    IBusinessModel model = businessModelService.getBusinessModelById(userId, taskCheckModel.getCheckmodelid().toString(), null, EnumInter.BusinessModelEnum.Table);
                    taskCheckRelations.stream().map(TaskCheckRelation::getCheckdataid).collect(Collectors.toList()).forEach(dataId -> orientSqlEngine.getBmService().delete(model, dataId.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        eventParam.setFlowParams("toDelRelations", toDelRelations);
    }
}
