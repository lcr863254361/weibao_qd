package com.orient.pvm.eventlistener;

import com.orient.pvm.event.DeleteCheckModelEvent;
import com.orient.pvm.eventparam.DeleteCheckModelEventParam;
import com.orient.sysmodel.service.pvm.impl.TaskCheckModelService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/8/10 0010.
 * 删除检查模型所关联的业务模型数据
 */
@Service
public class DeleteCheckModelListener extends OrientEventListener {

    @Autowired
    private TaskCheckModelService taskCheckModelService;

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
        taskCheckModelService.delete(eventParam.getToDelIds());
    }
}
