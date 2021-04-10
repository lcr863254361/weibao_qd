package com.orient.history.core.binddata.handler;

import com.orient.history.core.annotation.HisTaskHandler;
import com.orient.history.core.binddata.model.BindSysData;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.request.SysDataRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@HisTaskHandler(types = {IBindDataHandler.BIND_TYPE_SYSDATA}, order = 20)
@Scope(value = "prototype")
public class BindSysDataHandler extends AbstractBindDataHandler {

    @Autowired
    MetaDAOFactory metaDaoFactory;


    @Override
    public void constructBindData(String taskId, List<TaskBindData> taskBindDatas) {
        super.constructBindData(taskId, taskBindDatas);
        List<SysDataRequest> sysDataRequests = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_SYSDATA);
        if (!CommonTools.isEmptyList(sysDataRequests)) {
            sysDataRequests.forEach(sysDataRequest -> {
                BindSysData bindSysData = new BindSysData();
                bindSysData.setSysTableName(sysDataRequest.getSysTableName());
                if (CommonTools.isEmptyList(sysDataRequest.getSysTableDataList())) {
                    String querySql = new StringBuffer("SELECT * FROM ").append(sysDataRequest.getSysTableName())
                            .append(" WHERE ").append(sysDataRequest.getCustomFilterSql()).toString();
                    List<Map<String, Object>> queryList = metaDaoFactory.getJdbcTemplate().queryForList(querySql);
                    bindSysData.setOriSysDataList(queryList);
                } else {
                    bindSysData.setOriSysDataList(sysDataRequest.getSysTableDataList());
                }
                bindSysData.setExtraData(sysDataRequest.getExtraData());
                taskBindDatas.add(bindSysData);
            });
        }
    }
}
