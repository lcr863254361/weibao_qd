package com.orient.sysmodel.service.collabdev.impl;

import com.google.common.base.Strings;
import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabNodeDao;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.StringUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class CollabNodeService extends BaseService<CollabNode> implements ICollabNodeService {
    //node type
    public static final String NODE_TYPE_DIR = "dir";
    public static final String NODE_TYPE_PRJ = "prj";
    public static final String NODE_TYPE_PLAN = "plan";
    public static final String NODE_TYPE_TASK = "task";

    @Autowired
    ICollabNodeDao collabNodeDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabNodeDao;
    }

    @Override
    public void update(CollabNode node) {
        baseDao.update(node);
    }

    @Override
    public void save(CollabNode node) {
        baseDao.save(node);
    }

    @Override
    public CollabNode getPlanNode(CollabNode node) {
        CollabNode planNode = node;
        while(planNode!=null && !planNode.getType().equalsIgnoreCase(NODE_TYPE_PLAN)) {
            String pid = planNode.getPid();
            if(!Strings.isNullOrEmpty(pid)) {
                planNode = getById(pid);
            }
            else {
                planNode = null;
            }
        }
        if(planNode==null || !planNode.getType().equalsIgnoreCase(NODE_TYPE_PLAN)) {
            return null;
        }
        else {
            return planNode;
        }
    }

}
