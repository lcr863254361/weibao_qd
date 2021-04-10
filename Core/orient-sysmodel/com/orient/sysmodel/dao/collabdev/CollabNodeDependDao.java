package com.orient.sysmodel.dao.collabdev;

import com.aptx.utils.orm.BasicHibernateDao;
import com.orient.sysmodel.domain.collabdev.CollabNodeDepend;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2018-09-21 13:56
 */
@Repository
public class CollabNodeDependDao extends BasicHibernateDao<CollabNodeDepend> {
    public List<CollabNodeDepend> getNodeDependByPlan(String pid, Integer version) {
        DetachedCriteria criteria = createDetachedCriteria().add(Restrictions.eq("pId", pid)).add(Restrictions.eq("pVersion", version));
        return listByCriteria(criteria);
    }
}
