package com.orient.component.runenv.data;

import com.orient.component.runenv.data.mapper.ComponentDashboardMapper;
import com.orient.component.runenv.model.ComponentDashboard;
import com.orient.edm.util.MyBatisUtil;
import com.orient.web.base.BaseDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComponentDashboardDAO extends BaseDAO {
    private static final Log log = LogFactory.getLog(ComponentFlowDataDAO.class);

    public void save(ComponentDashboard transientInstance) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            session.getMapper(ComponentDashboardMapper.class).saveData(transientInstance);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public ComponentDashboard getComponentDashboardByTaskId(String taskId) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            return session.getMapper(ComponentDashboardMapper.class).getComponentDashboardByTaskId(taskId);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();

        }
    }

    public List<ComponentDashboard> getComponentDashboardByProjId(String projId) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            return session.getMapper(ComponentDashboardMapper.class).getComponentDashboardByProjctId(projId);

        } finally {
            session.close();
        }
    }

    public void saveComponentDashboard(ComponentDashboard transientInstance) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        try {
            session.getMapper(ComponentDashboardMapper.class).saveData(transientInstance);
            session.commit();
        } finally {
            session.close();
        }
    }
}
