package com.orient.collabdev.business.version.status;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.structure.tool.IModelAndNodeHelper;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 未开始的数据修改
 *
 * @author panduanduan
 * @create 2018-07-28 10:20 AM
 */
@Component
@MngStatus(status = ManagerStatusEnum.UNSTART)
public class UnstartVersionModifyer extends AbstractVersionModifyer {

    @Override
    public void influentByCreate(IBusinessModel bm, Map<String, String> dataMap, String id) {
        createAndBindNode(bm, dataMap, id, 1);
    }

    @Override
    public void influentByUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, String type) {
        updateNode(bm, dataMap, dataId, type, 1);
    }

    @Override
    public void influentByDelete(IBusinessModel bm, String dataIds, String type) {
        List<CollabNode> collabNodes = modelAndNodeHelper.getOriginNodeByBmData(bm, dataIds);
        //执行真删除
        collabNodes.forEach(collabNodeWithRelation -> collabNodeService.delete(collabNodeWithRelation));
    }

    @Autowired
    IModelAndNodeHelper modelAndNodeHelper;
}
