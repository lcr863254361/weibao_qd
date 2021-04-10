package com.orient.sysmodel.domain.file;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for CwmFile entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .CwmFile
  * @author MyEclipse Persistence Tools 
 */

public class CwmFileGroupDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(CwmFileGroupDAO.class);
	//property constants
    public static final String GROUPNAME = "groupName";
	public static final String GROUPTYPE = "groupType";
	public static final String ISSHOW = "isShow";

    
    public void save(CwmFileGroup transientInstance) {
        log.debug("saving CwmFileGroup instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(CwmFileGroup persistentInstance) {
        log.debug("deleting CwmFileGroup instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public CwmFileGroup findById( java.lang.String id) {
        log.debug("getting CwmFileGroup instance with id: " + id);
        try {
        	CwmFileGroup instance = (CwmFileGroup) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.file.CwmFileGroup", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(CwmFileGroup instance) {
        log.debug("finding CwmFileGroup instance by example");
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
      log.debug("finding CwmFileGroup instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from CwmFileGroup as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByGroupType(Object grouptype
	) {
		return findByProperty(GROUPTYPE, grouptype
		);
	}	
	
	public List findByGroupName(Object groupname
	) {
		return findByProperty(GROUPNAME, groupname
		);
	}
	
	public List findByIsShow(Object isShow
	) {
		return findByProperty(ISSHOW, isShow
		);
	}

	public List findAll() {
		log.debug("finding all CwmFileGroup instances");
		try {
			String queryString = "from CwmFileGroup";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public CwmFileGroup merge(CwmFileGroup detachedInstance) {
        log.debug("merging CwmFileGroup instance");
        try {
        	CwmFileGroup result = (CwmFileGroup) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public List<String> getFileTypes()
    {
    	String sql = "select name from cwm_codetoname where typeid='FILE' order by to_number(id)";
    	SQLQuery query = getSessionFactory().openSession().createSQLQuery(sql);
    	return query.list();
    }
}