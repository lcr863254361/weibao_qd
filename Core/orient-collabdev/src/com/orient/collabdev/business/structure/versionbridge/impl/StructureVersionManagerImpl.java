package com.orient.collabdev.business.structure.versionbridge.impl;

import com.google.common.collect.Lists;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.structure.versionbridge.IStructureVersionManager;
import com.orient.collabdev.business.structure.util.CollabNodeExtrator;
import com.orient.collabdev.business.version.status.IVersionModifyer;
import com.orient.collabdev.business.version.type.ICRUDVersionMng;
import com.orient.collabdev.business.version.type.PlanCRUDVersionMng;
import com.orient.collabdev.business.version.type.ProjectCRUDVersionMng;
import com.orient.collabdev.business.version.type.TaskCRUDVersionMng;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import com.orient.utils.Pair;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-28 2:25 PM
 */
@Service
public class StructureVersionManagerImpl implements IStructureVersionManager {

    @Override
    public ICRUDVersionMng getCRUDVersionMng(String modelName, String status) {
        ICRUDVersionMng retVal;
        IVersionModifyer versionModifyer = getVersionModifyerByStatus(status);
        if (null == versionModifyer) {
            throw new OrientBaseAjaxException("", "未知状态");
        }
        if (CollabDevConstants.PROJECT.equalsIgnoreCase(modelName)) {
            retVal = new ProjectCRUDVersionMng(versionModifyer);
        } else if (CollabDevConstants.PLAN.equalsIgnoreCase(modelName)) {
            retVal = new PlanCRUDVersionMng(versionModifyer);
        } else if (CollabDevConstants.TASK.equalsIgnoreCase(modelName)) {
            retVal = new TaskCRUDVersionMng(versionModifyer);
        } else
            throw new OrientBaseAjaxException("", "不支持当前数据模型");
        return retVal;
    }

    private IVersionModifyer getVersionModifyerByStatus(String status) {
        IVersionModifyer retVal = null;
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(IVersionModifyer.class);
        for (String beanName : beanNames) {
            IVersionModifyer versionModifyer = OrientContextLoaderListener.Appwac.getBean(beanName, IVersionModifyer.class);
            Class operateClass = versionModifyer.getClass();
            MngStatus classAnnotation = (MngStatus) operateClass.getAnnotation(MngStatus.class);
            ManagerStatusEnum bindStatus = classAnnotation.status();
            if (bindStatus.toString().equalsIgnoreCase(status)) {
                retVal = versionModifyer;
                break;
            }
        }
        return retVal;
    }

    /**
     * 根据节点信息 获取 绑定的模型数据
     *
     * @param node
     * @return
     */
    @Override
    public Pair<IBusinessModel, Map<String, String>> getNodeRefBmData(CollabNode node) {
        Map<String, List<String>> modelNameAndIds = CollabNodeExtrator.extraModelNameAndIdsFromCollabNode(Lists.newArrayList(node));
        if (!CommonTools.isEmptyMap(modelNameAndIds)) {
            //get first one
            Map.Entry<String, List<String>> onlyOneData = modelNameAndIds.entrySet().iterator().next();
            String modelname = onlyOneData.getKey();
            List<String> ids = onlyOneData.getValue();
            if (!CommonTools.isEmptyList(ids)) {
                IBusinessModel model = businessModelService.getBusinessModelBySName(modelname, CollabDevConstants.COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
                model.appendCustomerFilter(new CustomerFilter("id", EnumInter.SqlOperation.In, CommonTools.list2String(ids)));
                List<Map<String, String>> queryList = orientSqlEngine.getBmService().createModelQuery(model).list(true);
                if (!CommonTools.isEmptyList(queryList)) {
                    return new Pair<>(model, queryList.get(0));
                }
            }
        }
        return null;
    }

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    public IBusinessModelService businessModelService;
}
