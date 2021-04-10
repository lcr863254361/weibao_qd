package com.orient.collab.event.listener;

import com.orient.collab.business.DataTaskBusiness;
import com.orient.collab.business.DataFlowBusiness;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Task;
import com.orient.devdataobj.event.DataObjModifiedEvent;
import com.orient.devdataobj.event.param.DataObjModifiedParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mengbin on 16/8/23.
 * Purpose:
 * Detail: 根据修改的数据对象,形成数据任务的通知
 */
@Service
public class DataTaskCreate extends OrientEventListener {


    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected DataFlowBusiness dataFlowBusiness;

    @Autowired
    protected DataTaskBusiness dataTaskBusiness;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return DataObjModifiedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        String planModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Plan.class);

        DataObjModifiedParam param = (DataObjModifiedParam)((OrientEvent)event).getParams();
        Map<String ,List<DataObjectEntity>> groupMap = (Map<String ,List<DataObjectEntity>>)param.getFlowParams("groupedDataObjs");
        for(String key :groupMap.keySet()){
            String[] ids = key.split(":");
            String modelId = ids[0];
            String dataId = ids[1];
            if(modelId.equals(planModelId)){
                continue;
            }
            else {
              List<Task>  tasks =  dataFlowBusiness.getNextTasks(dataId);
                for (Task task:tasks){
                    dataTaskBusiness.createDataTask(task);
                }

            }


        }





    }
}
