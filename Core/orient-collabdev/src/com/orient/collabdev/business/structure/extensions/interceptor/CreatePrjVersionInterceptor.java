package com.orient.collabdev.business.structure.extensions.interceptor;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.structure.constant.StructOperateType;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructInterceptor;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructMarker;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabPrjVersion;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.sysmodel.service.collabdev.ICollabPrjVersionService;
import com.orient.utils.CommonTools;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 创建项目时保存项目初始版本信息
 *
 * @author panduanduan
 * @create 2018-08-13 5:24 PM
 */
@CollabDevStructMarker(projectStatus = {ManagerStatusEnum.UNSTART}, structOperateType = StructOperateType.CREATE, models = {CollabDevConstants.PROJECT}, order = -10)
public class CreatePrjVersionInterceptor implements CollabDevStructInterceptor {

    @Override
    public boolean preHandle(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            , StructOperateType structOperateType) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            , StructOperateType structOperateType, Object processResult) throws Exception {
        //get ref node
        targetMap.forEach(dataMap -> {
            String prjId = dataMap.get("ID");
            List<CollabNode> collabNodes = collabNodeService.list(Restrictions.eq("bmDataId", prjId), Restrictions.eq("type", CollabDevConstants.NODE_TYPE_PRJ));
            if (!CommonTools.isEmptyList(collabNodes)) {
                String nodeId = collabNodes.get(0).getId();
                CollabPrjVersion collabPrjVersion = new CollabPrjVersion();
                collabPrjVersion.setPrjVersion(1);
                collabPrjVersion.setNodeId(Integer.valueOf(nodeId));
                collabPrjVersionService.save(collabPrjVersion);
            }
        });
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    ICollabPrjVersionService collabPrjVersionService;
}
