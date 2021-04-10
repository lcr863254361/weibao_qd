package com.orient.collabdev.business.designing;

import com.alibaba.fastjson.JSONObject;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author GNY
 * @Date 2018/8/1 18:14
 * @Version 1.0
 **/
@Service
public class DesignTaskBusiness extends BaseBusiness {

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    ICollabNodeService collabNodeService;

    public ExtGridData<Map> queryTasks(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort, String parentNodeId) {
        List<CollabNode> taskNodeList = collabNodeService.list(Restrictions.eq("pid", parentNodeId));
        String taskIds = taskNodeList.stream()
                .map(CollabNode::getBmDataId)
                .collect(Collectors.joining(","));
        List<CustomerFilter> filterList = new ArrayList<>();
        CustomerFilter filter = new CustomerFilter("ID", EnumInter.SqlOperation.In, taskIds);
        filterList.add(filter);
        return modelDataBusiness.getModelDataByModelId(orientModelId, isView, page, limit, JSONObject.toJSONString(filterList), true, sort);
    }

}
