package com.orient.devdataobj.event.listener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.event.ProjectTreeNodeCreatedEvent;
import com.orient.collab.event.ProjectTreeNodeCreatedEventParam;
import com.orient.devdataobj.event.DataObjModifiedEvent;
import com.orient.devdataobj.event.param.DataObjModifiedParam;
import com.orient.devdataobj.event.param.GetDataObjParam;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.util.UserContextUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mengbin on 16/8/23.
 * Purpose: 将修改的DataObj分组,根据对应的modelId,DataId进行分组
 * Detail:
 */
@Service
public class GroupModifiedDataObjListener extends OrientEventListener {
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
        Map<String, List<DataObjectEntity>> groupMap = new HashMap<>();
        DataObjModifiedParam param = (DataObjModifiedParam) ((OrientEvent) event).getParams();
        for (DataObjectEntity modifiedDataObj : param.modifiedDataObjList) {
            String nodeId = modifiedDataObj.getNodeId();
           /* String modelId = modifiedDataObj.getModelid();
            String dataId = modifiedDataObj.getDataid();
            String key = modelId+":"+dataId;*/
            String key = nodeId;
            List<DataObjectEntity> dataObjList = groupMap.get(key);
            if (dataObjList == null) {
                dataObjList = new ArrayList<>();
            }
            dataObjList.add(modifiedDataObj);
            groupMap.put(key, dataObjList);
        }
        param.setFlowParams("groupedDataObjs", groupMap);

    }
}
