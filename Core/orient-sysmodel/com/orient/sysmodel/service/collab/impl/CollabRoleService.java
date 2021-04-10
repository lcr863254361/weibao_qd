package com.orient.sysmodel.service.collab.impl;

import com.orient.sysmodel.dao.collab.ICollabRoleDao;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collab.ICollabRoleService;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * collab role service
 *
 * @author Seraph
 * 2016-07-08 下午2:35
 */
@Service
public class CollabRoleService extends BaseService<CollabRole> implements ICollabRoleService {

    /**
     * 对未分配角色的用户进行过滤
     *
     * @param assignedUserIds 已经分配用角色的用户ids
     * @param userName        查询框里输入的名称，数据库查询时应该和userName和allName这两列的值匹配
     * @param departmentId    查询框里传入的部门id
     * @return
     */
    @Override
    public List<CollabRole.User> getUnassignedUsers(String assignedUserIds, String userName, String departmentId) {

        List<Criterion> criterias = UtilFactory.newArrayList();
        if (assignedUserIds.length() > 0) {
            criterias.add(Restrictions.not(Restrictions.in("id", assignedUserIds.split(","))));
        }

        if (!CommonTools.isNullString(userName)) {
            //criterias.add(Restrictions.like("userName", userName));

            //liuchao++:修改“未分配用户查询名称时查不到记录bug”
            criterias.add(Restrictions.or(Restrictions.like("userName", "%" + userName + "%"), Restrictions.like("allName", "%" + userName + "%")));
        }

        if (!CommonTools.isNullString(departmentId)) {
            criterias.add(Restrictions.eq("depId", departmentId));
        }
        //未分配用户中，如果state为0，代表该用户已经被假删除，不应该出现在未分配用户列表里
        criterias.add(Restrictions.eq("state", "1"));
        criterias.add(Restrictions.gt("id", "0"));
        return this.collabRoleDao.list(CollabRole.User.class, criterias.toArray(new Criterion[criterias.size()]));
    }

    @Override
    public List<CollabRole> getCollabRoleByUserId(String userId) {

        return this.getBaseDao().getSession().createCriteria(CollabRole.class).createCriteria("users").add(Restrictions.eq("id", userId)).list();
    }


    @Autowired
    private ICollabRoleDao collabRoleDao;

}
