package com.orient.sysmodel.domain.sys;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for SeqGenerator entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .SeqGenerator
  * @author MyEclipse Persistence Tools 
 */

public class SeqGeneratorDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(SeqGeneratorDAO.class);
	//property constants
	public static final String NAME = "name";
	public static final String CONTENT = "content";
	public static final String ENABLE = "enable";
	public static final String RETURN_TYPE = "returnType";
	public static final String CHANGED = "changed";



    
    public void save(SeqGenerator transientInstance) {
        log.debug("saving SeqGenerator instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(SeqGenerator persistentInstance) {
        log.debug("deleting SeqGenerator instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public SeqGenerator findById( java.lang.Long id) {
        log.debug("getting SeqGenerator instance with id: " + id);
        try {
            SeqGenerator instance = (SeqGenerator) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.sys.SeqGenerator", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(SeqGenerator instance) {
        log.debug("finding SeqGenerator instance by example");
        try {
        	List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding SeqGenerator instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from SeqGenerator as model where model." 
         						+ propertyName + "= ?";
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
	
	public List findByContent(Object content
	) {
		return findByProperty(CONTENT, content
		);
	}
	
	public List findByEnable(Object enable
	) {
		return findByProperty(ENABLE, enable
		);
	}
	
	public List findByReturnType(Object returnType
	) {
		return findByProperty(RETURN_TYPE, returnType
		);
	}
	
	public List findByChanged(Object changed
	) {
		return findByProperty(CHANGED, changed
		);
	}
	

	public List findAll() {
		log.debug("finding all SeqGenerator instances");
		try {
			String queryString = "from SeqGenerator";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public SeqGenerator merge(SeqGenerator detachedInstance) {
        log.debug("merging SeqGenerator instance");
        try {
            SeqGenerator result = (SeqGenerator) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SeqGenerator instance) {
        log.debug("attaching dirty SeqGenerator instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(SeqGenerator instance) {
        log.debug("attaching clean SeqGenerator instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getHqlResult(String searchsql) {
		log.debug("getting  SeqGenerator searchsql sentence" + searchsql);
		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  SeqGenerator searchsql failed", re);
			return null;
		}
	}
    
    public List getSqlResult(String searchsql) {
		log.debug("getting  SeqGenerator searchsql sentence" + searchsql);
		try {
			SQLQuery query = this.getSession().createSQLQuery(searchsql);
			List result = query.list();
			return result;
		} catch (RuntimeException re) {
			log.error("getting  SeqGenerator searchsql failed", re);
			return null;
		}
	}
    
}