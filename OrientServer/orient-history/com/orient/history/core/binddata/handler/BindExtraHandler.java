package com.orient.history.core.binddata.handler;

import com.orient.history.core.annotation.HisTaskHandler;
import com.orient.history.core.binddata.model.BindExtraData;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.utils.CommonTools;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@HisTaskHandler(types = {IBindDataHandler.BIND_TYPE_EXTRADATA})
@Scope(value = "prototype")
public class BindExtraHandler extends AbstractBindDataHandler {

    @Override
    public void constructBindData(String taskId, List<TaskBindData> taskBindDatas) {
        super.constructBindData(taskId, taskBindDatas);
        Map<String, String> extraData = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_EXTRADATA);
        BindExtraData bindExtraData = new BindExtraData("");
        if (!CommonTools.isEmptyMap(extraData)) {
            bindExtraData.setExtraMap(extraData);
            taskBindDatas.add(bindExtraData);
        }
    }
}
