package com.orient.sysmodel.domain.etl;
// default package

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for EtlJob entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .EtlJob
  * @author MyEclipse Persistence Tools 
 */

public class EtlJobDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(EtlJobDAO.class);
	//property constants
	public static final String USER_NAME = "userName";
	public static final String STATUS = "status";
	public static final String DDL = "ddl";
	public static final String DML = "dml";
	public static final String LOADSQL = "loadsql";



    
    public void save(EtlJob transientInstance) {
        log.debug("saving EtlJob instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(EtlJob persistentInstance) {
        log.debug("deleting EtlJob instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public EtlJob findById( java.lang.String id) {
        log.debug("getting EtlJob instance with id: " + id);
        try {
            EtlJob instance = (EtlJob) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.etl.EtlJob", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(EtlJob instance) {
        log.debug("finding EtlJob instance by example");
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
      log.debug("finding EtlJob instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from EtlJob as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByUserName(Object userName
	) {
		return findByProperty(USER_NAME, userName
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	
	public List findByDdl(Object ddl
	) {
		return findByProperty(DDL, ddl
		);
	}
	
	public List findByDml(Object dml
	) {
		return findByProperty(DML, dml
		);
	}
	
	public List findByLoadsql(Object loadsql
	) {
		return findByProperty(LOADSQL, loadsql
		);
	}
	

	public List findAll() {
		log.debug("finding all EtlJob instances");
		try {
			String queryString = "from EtlJob";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public EtlJob merge(EtlJob detachedInstance) {
        log.debug("merging EtlJob instance");
        try {
            EtlJob result = (EtlJob) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EtlJob instance) {
        log.debug("attaching dirty EtlJob instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(EtlJob instance) {
        log.debug("attaching clean EtlJob instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}