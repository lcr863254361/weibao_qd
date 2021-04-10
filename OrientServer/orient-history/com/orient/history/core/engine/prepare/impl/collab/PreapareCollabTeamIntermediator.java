package com.orient.history.core.engine.prepare.impl.collab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.orient.collab.business.TeamBusiness;
import com.orient.collab.model.RoleFunctionTreeNode;
import com.orient.collab.model.RoleUserTreeNode;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.engine.prepare.impl.AbstractPrepareIntermediator;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.service.collab.ICollabRoleService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"com.orient.history.core.util.HisTaskTypeConstants$$COLLAB_TASK"}, order = 40)
@Scope(value = "prototype")
public class PreapareCollabTeamIntermediator extends AbstractPrepareIntermediator {

    @Autowired
    ICollabRoleService collabRoleService;

    @Autowired
    TeamBusiness teamBusiness;

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        String taskId = frontViewRequest.getTaskId();
        Map<String, String> extraMap = frontViewRequest.getExtraData();
        //获取角色ID集合
        String rolelIdsJsonStr = extraMap.get("roleIds");
        if (!StringUtil.isEmpty(rolelIdsJsonStr)) {
            List<Long> collRoleIds = JSONArray.parseArray(rolelIdsJsonStr, Long.class);
            List<CollabRole> collabRoles = collabRoleService.list(Restrictions.in("id", collRoleIds));
            //转化为前端对象
            List<RoleUserTreeNode> roleUserTreeNodes = teamBusiness.converRoleToFrontData(collabRoles);
            //获取功能点信息
            Map<String, List<RoleFunctionTreeNode>> roleFunctionTreeNodes = teamBusiness.converFunctionToFrontData(collabRoles);
            //存至变量中
            Map<String, String> extraData = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_EXTRADATA);
            extraData = CommonTools.isEmptyMap(extraMap) ? new HashMap<>() : extraData;
            extraData.put("roleUserTreeNodes", JSON.toJSONString(roleUserTreeNodes));
            extraData.put("roleFunctionTreeNodes", JSON.toJSONString(roleFunctionTreeNodes));
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_EXTRADATA, extraData);
        }
    }
}
