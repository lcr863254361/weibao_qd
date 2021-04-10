package com.orient.sysman.bussiness;

import com.orient.businessmodel.Util.BusinessModelCacheHelper;
import com.orient.sysman.bean.PartOperationsWrapper;
import com.orient.sysmodel.domain.role.PartOperations;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.operationinterface.IPartOperations;
import com.orient.sysmodel.service.sys.IModelRightsService;
import com.orient.utils.BeanUtils;
import com.orient.web.base.BaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-04 18:47
 */
@Component
public class ModelRightsBusiness extends BaseBusiness {

    @Autowired
    IModelRightsService modelRightsService;

    @Autowired
    BusinessModelCacheHelper businessModelCacheHelper;


    public PartOperationsWrapper getModelRights(String modelId, String roleId) {
        PartOperations partOperations = modelRightsService.getModelRights(modelId, roleId);
        if (null != partOperations) {
            partOperations.setRole(null);
            PartOperationsWrapper partOperationsWrapper = new PartOperationsWrapper();
            BeanUtils.copyProperties(partOperationsWrapper, partOperations);
            partOperationsWrapper.setRoleInfo(roleId);
            return partOperationsWrapper;
        }
        return null;

    }

    public void save(PartOperationsWrapper formValue) {
        PartOperations toSaveData = new PartOperations();
        BeanUtils.copyProperties(toSaveData, formValue);
        Role role = roleEngine.getRoleModel(false).getRoleById(formValue.getRoleInfo());
        toSaveData.setRole(role);
        modelRightsService.saveModelRights(toSaveData);
        formValue.setId(toSaveData.getId());
        //更新角色缓存
        role.getPartOperations().add(toSaveData);
        //清除模型相关缓存
        clearECache(toSaveData.getTableId());
    }

    public void update(PartOperationsWrapper formValue) {
        PartOperations toSaveData = modelRightsService.findById(formValue.getId());
        //更新制定字段
        toSaveData.setColumnId(formValue.getColumnId());
        toSaveData.setAddColumnIds(formValue.getAddColumnIds());
        toSaveData.setModifyColumnIds(formValue.getModifyColumnIds());
        toSaveData.setDetailColumnIds(formValue.getDetailColumnIds());
        toSaveData.setExportColumnIds(formValue.getExportColumnIds());
        toSaveData.setOperationsId(formValue.getOperationsId());
        toSaveData.setFilter(formValue.getFilter());
        toSaveData.setUserFilter(formValue.getUserFilter());
        modelRightsService.updateModelRights(toSaveData);
        //更新角色缓存
        Role role = roleEngine.getRoleModel(false).getRoleById(toSaveData.getRole().getId());
        List<IPartOperations> partOperationses = role.getPartOperationsOfTable(toSaveData.getTableId());
        partOperationses.forEach(iPartOperations -> {
            if(iPartOperations.getTableId().equals(toSaveData.getTableId())){
                role.getPartOperations().remove(iPartOperations);
            }
        });
        role.getPartOperations().add(toSaveData);
        clearECache(toSaveData.getTableId());
    }

    private void clearECache(String modelId){
        businessModelCacheHelper.businessModelCache.getKeys().forEach(key->{
            if(((String)key).startsWith(modelId)){
                businessModelCacheHelper.businessModelCache.remove(key);
            }
        });
    }
}
