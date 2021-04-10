package com.orient.history.core.binddata.handler.audit;

import com.orient.history.core.annotation.HisTaskHandler;
import com.orient.history.core.binddata.handler.AbstractBindDataHandler;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.binddata.model.BindOpinionData;
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
@HisTaskHandler(types = {IBindDataHandler.BIND_TYPE_OPINIONDATA}, order = 10)
@Scope(value = "prototype")
public class BindOpinionHandler extends AbstractBindDataHandler {

    @Override
    public void constructBindData(String taskId, List<TaskBindData> taskBindDatas) {
        super.constructBindData(taskId, taskBindDatas);
        Map<String, String> opinions = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_OPINIONDATA);
        if (!CommonTools.isEmptyMap(opinions)) {
            BindOpinionData bindOpinionData = new BindOpinionData();
            bindOpinionData.setOpinions(opinions);
            taskBindDatas.add(bindOpinionData);
        }
    }
}
