package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.domain.role.PartOperations;
import com.orient.sysmodel.domain.role.PartOperationsDAO;
import com.orient.sysmodel.service.sys.IModelRightsService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-04 18:49
 */
@Service
public class ModelRightsService implements IModelRightsService {

    @Autowired
    PartOperationsDAO PartOperationsDAO;

    @Override
    public PartOperations getModelRights(String modelId, String roleId) {
        List<PartOperations> queryList = PartOperationsDAO.findByCriterion(Restrictions.eq("tableId", modelId), Restrictions.eq("role.id", roleId));
        if (queryList.size() > 0) {
            return queryList.get(0);
        }
        return null;
    }

    @Override
    public void saveModelRights(PartOperations partOperations) {
        PartOperationsDAO.save(partOperations);
    }

    @Override
    public void updateModelRights(PartOperations partOperations) {
        PartOperationsDAO.attachDirty(partOperations);
    }

    @Override
    public PartOperations findById(Long id) {
        return PartOperationsDAO.findById(id);
    }
}
