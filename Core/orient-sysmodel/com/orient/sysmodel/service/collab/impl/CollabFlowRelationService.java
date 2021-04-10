package com.orient.sysmodel.service.collab.impl;

import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sysmodel.domain.collab.CollabFlowRelation;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collab.ICollabFlowRelationService;
import com.orient.utils.UtilFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CollabFlowRelationService
 *
 * @author Seraph
 *         2016-08-16 上午10:17
 */
@Service
public class CollabFlowRelationService extends BaseService<CollabFlowRelation> implements ICollabFlowRelationService{

    @Override
    public List<CollabFlowRelation> getSubCollabFlowDataRelationsCascade(String parentPiId) {
        StringBuffer querySql = new StringBuffer();
        querySql.append("SELECT ID, PARENT_PI_ID, SUB_PI_ID, TASK_ID ");
        querySql.append("FROM  COLLAB_FLOW_RELATION ");
        querySql.append("START WITH PARENT_PI_ID = ? ");
        querySql.append("CONNECT BY PRIOR SUB_PI_ID = PARENT_PI_ID");
        List<Map<String,Object>> queryList = metaDaoFactory.getJdbcTemplate().queryForList(querySql.toString(),new Object[]{parentPiId});

        List<CollabFlowRelation> retV = UtilFactory.newArrayList();
        for(Map<String,Object> queryV : queryList){
            CollabFlowRelation collabFlowRelation = new CollabFlowRelation((Long) queryV.get("ID"), String.valueOf(queryV.get("PARENT_PI_ID")),
                    String.valueOf(queryV.get("SUB_PI_ID")), String.valueOf(queryV.get("TASK_ID")));
            retV.add(collabFlowRelation);
        }

        return retV;
    }

    @Autowired
    protected MetaDAOFactory metaDaoFactory;
}
