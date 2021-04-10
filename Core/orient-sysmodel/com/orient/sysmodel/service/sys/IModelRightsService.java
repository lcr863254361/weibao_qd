package com.orient.sysmodel.service.sys;

import com.orient.sysmodel.domain.role.PartOperations;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-04 18:49
 */
public interface IModelRightsService {
    PartOperations getModelRights(String modelId, String roleId);

    void saveModelRights(PartOperations partOperations);

    void updateModelRights(PartOperations partOperations);

    PartOperations findById(Long id);
}
