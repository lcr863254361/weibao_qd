package com.orient.sysmodel.domain.role;
// default package

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for Role entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Role
  * @author MyEclipse Persistence Tools 
 */

public class RoleDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(RoleDAO.class);
	//property constants
	public static final String NAME = "name";
	public static final String MEMO = "memo";
	public static final String TYPE = "type";
	public static final String STATUS = "status";
	public static final String FLG = "flg";



    
    public void save(Role transientInstance) {
        log.debug("saving Role instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Role persistentInstance) {
        log.debug("deleting Role instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Role findById( String id) {
        log.debug("getting Role instance with id: " + id);
        try {
            Role instance = (Role) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.role.Role", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Role instance) {
        log.debug("finding Role instance by example");
        try {
        	List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByExampleLike(Role instance) {
        log.debug("finding Role instance by example like");
        try {
            Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
            Example example = Example.create(instance).enableLike(MatchMode.ANYWHERE).ignoreCase();
            Criteria criteria = session.createCriteria(Role.class).add(example);
            List results = criteria.list();
            log.debug("find by example like successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example like failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Role instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Role as model where model." 
         						+ propertyName + "= ? order by model.id ";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByName(Object name
	) {
		return findByProperty(NAME, name
		);
	}
	
	public List findByMemo(Object memo
	) {
		return findByProperty(MEMO, memo
		);
	}
	
	public List findByType(Object type
	) {
		return findByProperty(TYPE, type
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	
	public List findByFlg(Object flg
	) {
		return findByProperty(FLG, flg
		);
	}
	

	public List findAll() {
		log.debug("finding all Role instances");
		try {
			String queryString = "from Role order by id ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAllForShow() {
		log.debug("finding all Role instances");
		try {
			String queryString = "from Role where id > 0 order by id ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Map<String, Role> findAllRoles()
	{
		Map<String, Role> roles = new LinkedHashMap<String, Role>();
		String queryString = "from Role order by name";
		List<Role> roleList = (List<Role>) getHibernateTemplate().find(queryString);
		for(Role role : roleList)
			roles.put(role.getId(), role);
		
		return roles;
		
	}
	
    public Role merge(Role detachedInstance) {
        log.debug("merging Role instance");
        try {
            Role result = (Role) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Role instance) {
        log.debug("attaching dirty Role instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Role instance) {
        log.debug("attaching clean Role instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getSqlResult(String searchsql) {

		log.debug("getting  Role searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  Role searchsql failed", re);
			return null;
		}
	}
    
}