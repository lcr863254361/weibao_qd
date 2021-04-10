package com.orient.component.runenv.data;

import com.orient.component.runenv.data.mapper.ComponentFlowDataMapper;
import com.orient.component.runenv.model.ComponentFlowData;
import com.orient.edm.util.MyBatisUtil;
import com.orient.web.base.BaseDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComponentFlowDataDAO extends BaseDAO {
    private static final Log log = LogFactory.getLog(ComponentFlowDataDAO.class);

    public static final String ID = "id";
    public static final String PROJECTCODE = "projectCode";
    public static final String KEY = "key";
    public static final String VALUE = "value";

    public void save(ComponentFlowData transientInstance) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            session.getMapper(ComponentFlowDataMapper.class).saveData(transientInstance);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void update(ComponentFlowData flowData) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            session.getMapper(ComponentFlowDataMapper.class).update(flowData);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteAll() {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            session.getMapper(ComponentFlowDataMapper.class).deleteAll();
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteData(String projCode, String key) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            session.getMapper(ComponentFlowDataMapper.class).deleteKeyData(projCode, key);
            session.commit();
        } finally {
            session.close();
        }
    }

    public void deleteProjData(String projCode) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            session.getMapper(ComponentFlowDataMapper.class).deleteProjData(projCode);
            session.commit();
        } finally {
            session.close();
        }
    }

    public List<ComponentFlowData> findAll() {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            return session.getMapper(ComponentFlowDataMapper.class).findAll();
        } finally {
            session.close();
        }
    }

    public ComponentFlowData getComponentFlowDataByKey(String key, String projCode) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            return session.getMapper(ComponentFlowDataMapper.class).getComponentFlowDataByKey(projCode, key);
        } finally {
            session.close();
        }
    }

    public List<ComponentFlowData> findByProjCode(String projCode) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            return session.getMapper(ComponentFlowDataMapper.class).findByProjCode(projCode);
        } finally {
            session.close();
        }
    }

}
