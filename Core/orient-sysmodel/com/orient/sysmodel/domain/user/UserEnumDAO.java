package com.orient.sysmodel.domain.user;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for UserEnum entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .UserEnum
  * @author MyEclipse Persistence Tools 
 */

public class UserEnumDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(UserEnumDAO.class);
	//property constants
	public static final String ENUM_ID = "enumId";
	public static final String VALUE = "value";
	public static final String DISPLAY_VALUE = "displayValue";
	public static final String IMAGE_URL = "imageUrl";
	public static final String DESCRIPTION = "description";



    
    public void save(UserEnum transientInstance) {
        log.debug("saving UserEnum instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(UserEnum persistentInstance) {
        log.debug("deleting UserEnum instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public UserEnum findById( java.lang.String id) {
        log.debug("getting UserEnum instance with id: " + id);
        try {
            UserEnum instance = (UserEnum) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.user.UserEnum", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(UserEnum instance) {
        log.debug("finding UserEnum instance by example");
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
      log.debug("finding UserEnum instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from UserEnum as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByEnumId(Object enumId
	) {
		return findByProperty(ENUM_ID, enumId
		);
	}
	
	public List findByValue(Object value
	) {
		return findByProperty(VALUE, value
		);
	}
	
	public List findByDisplayValue(Object displayValue
	) {
		return findByProperty(DISPLAY_VALUE, displayValue
		);
	}
	
	public List findByImageUrl(Object imageUrl
	) {
		return findByProperty(IMAGE_URL, imageUrl
		);
	}
	
	public List findByDescription(Object description
	) {
		return findByProperty(DESCRIPTION, description
		);
	}
	

	public List findAll() {
		log.debug("finding all UserEnum instances");
		try {
			String queryString = "from UserEnum";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public UserEnum merge(UserEnum detachedInstance) {
        log.debug("merging UserEnum instance");
        try {
            UserEnum result = (UserEnum) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserEnum instance) {
        log.debug("attaching dirty UserEnum instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(UserEnum instance) {
        log.debug("attaching clean UserEnum instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}