package com.orient.collabdev.business.structure;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collabdev.business.structure.constant.StructOperateType;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructInterceptor;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructOperateInterceptorMng;
import com.orient.collabdev.business.structure.util.CollabNodeExtrator;
import com.orient.collabdev.business.structure.versionbridge.IStructureVersionManager;
import com.orient.collabdev.business.version.type.ICRUDVersionMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-13 4:18 PM
 */
@Component
public class StructCoreImpl extends AbstractStructCore implements FactoryBean<StructCore> {

    @Autowired
    IStructureVersionManager structureVersionManager;

    @Autowired
    CollabDevStructOperateInterceptorMng collabDevStructOperateInterceptorMng;


    @Override
    public void add(IBusinessModel bm, Map<String, String> dataMap, String id) {
        //get crudmng
        ICRUDVersionMng crudVersionMng = structureVersionManager.getCRUDVersionMng(bm.getName(), getRootStatus(bm, id));
        crudVersionMng.doCreate(bm, dataMap, id);
    }

    @Override
    public void delete(IBusinessModel bm, String dataIds) {
        List<CollabNodeWithRelation> collabNodeWithRelationList = modelAndNodeHelper.getNodeByBmData(bm, dataIds);
        //remove son nodes
        List<CollabNodeWithRelation> children = new ArrayList<>();
        collabNodeWithRelationList.forEach(collabNodeWithRelation -> children.addAll(collabNodeWithRelation.getChildren()));
        Map<String, List<String>> toDelModelData = CollabNodeExtrator.extraModelNameAndIds(children);
        toDelModelData.forEach((modelName, dataIdList) -> {
            IBusinessModel businessModel = businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
            orientSqlEngine.getBmService().delete(businessModel, CommonTools.list2String(dataIdList));
        });
        ICRUDVersionMng crudVersionMng = structureVersionManager.getCRUDVersionMng(bm.getName(), getRootStatus(bm, dataIds.split(",")[0]));
        crudVersionMng.doDelete(bm, dataIds);
    }


    @Override
    public void update(IBusinessModel bm, Map<String, String> dataMap, String dataId) {
        Map<String, String> originalMap = orientSqlEngine.getBmService().createModelQuery(bm).findById(dataId);
        //only name changed event can trigger node update
        MapDifference<String, String> difference = Maps.difference(originalMap, dataMap);
        Map<String, MapDifference.ValueDifference<String>> valueDifferenceMap = difference.entriesDiffering();
        String modelId = bm.getId();
//        if (valueDifferenceMap.containsKey("NAME_" + modelId) || valueDifferenceMap.containsKey("STATUS_" + modelId)) {
        ICRUDVersionMng crudVersionMng = structureVersionManager.getCRUDVersionMng(bm.getName(), getRootStatus(bm, dataId));
        crudVersionMng.doUpdate(bm, dataMap, dataId);
//        }
    }


    @Override
    public StructCore getObject() {
        return (StructCore) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{StructCore.class}, (proxy, method, args) -> {

            IBusinessModel bm = (IBusinessModel) args[0];
            String modelName = bm.getName();
            StructOperateType structOperateType = StructOperateType.fromString(method.getName());
            if (null == structOperateType) {
                return method.invoke(StructCoreImpl.this, args);
            }

            ManagerStatusEnum projectStatus;
            String exampleDataId = null;
            List<Map<String, String>> sourceData = new ArrayList<>();
            List<Map<String, String>> targetData = new ArrayList<>();
            if (method.getName().equalsIgnoreCase("add")) {
                targetData.add((Map<String, String>) args[1]);
            } else if (method.getName().equalsIgnoreCase("update")) {
                exampleDataId = (String) args[2];
                sourceData.add(orientSqlEngine.getBmService().createModelQuery(bm).findById(exampleDataId));
                targetData.add((Map<String, String>) args[1]);
            } else if (method.getName().equalsIgnoreCase("delete")) {
                String ids = (String) args[1];
                if (!StringUtil.isEmpty(ids)) {
                    exampleDataId = ids.split(",")[0];
                    bm.appendCustomerFilter(new CustomerFilter("id", EnumInter.SqlOperation.In, ids));
                    sourceData.addAll(orientSqlEngine.getBmService().createModelQuery(bm).list(true));
                }
            }
            //get root node
            CollabNode rootNode = getRootNode(bm, exampleDataId);
            projectStatus = null == rootNode ? ManagerStatusEnum.UNSTART : ManagerStatusEnum.fromString(rootNode.getStatus());

            List<CollabDevStructInterceptor> interceptorList = collabDevStructOperateInterceptorMng.getInterceptors(modelName, structOperateType, projectStatus);
            for (CollabDevStructInterceptor interceptor : interceptorList) {
                boolean proceed = interceptor.preHandle(bm, sourceData, targetData, rootNode, structOperateType);
                if (!proceed) {
                    break;
                }
            }
            Object retV = method.invoke(StructCoreImpl.this, args);

            for (CollabDevStructInterceptor interceptor : interceptorList) {
                interceptor.afterCompletion(bm, sourceData, targetData, rootNode, structOperateType, retV);
            }
            return retV;
        });
    }

    private String getRootStatus(IBusinessModel bm, String dataId) {
        CollabNode rootNode = getRootNode(bm, dataId);
        String prjStatus = null == rootNode ? ManagerStatusEnum.UNSTART.toString() : rootNode.getStatus();
        return prjStatus;
    }

    @Override
    public Class<?> getObjectType() {
        return StructCore.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
