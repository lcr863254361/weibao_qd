package com.orient.history.core.binddata.handler.audit;

import com.orient.history.core.annotation.HisTaskHandler;
import com.orient.history.core.binddata.handler.AbstractBindDataHandler;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.binddata.model.BindFreemarkerData;
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
@HisTaskHandler(types = {IBindDataHandler.BIND_TYPE_FREEMARKER})
@Scope(value = "prototype")
public class BindFreemarkerHandler extends AbstractBindDataHandler {

    @Override
    public void constructBindData(String taskId, List<TaskBindData> taskBindDatas) {
        super.constructBindData(taskId, taskBindDatas);
        //保存模板信息
        Map<String, List<String>> freemarkerDetail = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_FREEMARKER);
        if (!CommonTools.isEmptyMap(freemarkerDetail)) {
            freemarkerDetail.forEach((key, values) -> values.forEach(value -> {
                BindFreemarkerData bindFreemarkerData = new BindFreemarkerData();
                bindFreemarkerData.getExtraData().put("key", key);
                bindFreemarkerData.setHtml(value);
                taskBindDatas.add(bindFreemarkerData);
            }));
        }
    }
}
