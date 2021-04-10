package com.orient.pvm.eventlistener;

import com.orient.pvm.event.DeleteCheckModelEvent;
import com.orient.pvm.eventparam.DeleteCheckModelEventParam;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.service.pvm.impl.TaskCheckRelationService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10 0010.
 * 删除检查模型所关联的业务模型数据
 */
@Service
public class DeleteRelationDataListener extends OrientEventListener {

    @Autowired
    private TaskCheckRelationService taskCheckRelationService;

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
        DeleteCheckModelEventParam eventParam = (DeleteCheckModelEventParam) ((OrientEvent) applicationEvent).getParams();
        List<TaskCheckRelation> toDelRelations = (List<TaskCheckRelation>) eventParam.getFlowParams("toDelRelations");
        if (null != toDelRelations) {
            toDelRelations.stream().forEach(taskCheckRelation -> taskCheckRelationService.delete(taskCheckRelation));
        }
    }
}
