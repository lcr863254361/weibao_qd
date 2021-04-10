/*
 * Title: TbomDirDAO.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:59 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for
 * Schema entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.rcp.design.domain.Schema
 * @author MyEclipse Persistence Tools
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
		RuntimeException.class, IOException.class,Exception.class })
public class TbomDirDAO extends HibernateDaoSupport  {
    
    /** The Constant log. */
    private static final Log log = LogFactory.getLog(TbomDirDAO.class);
	//property constants
	/** The Constant NAME. */
	public static final String NAME = "name";
	
	/** The Constant SCHEMAID. */
	public static final String SCHEMAID = "schemaid";
	
	/** The Constant IS_LOCK. */
	public static final String IS_LOCK = "isLock";
	
	/** The Constant USERNAME. */
	public static final String USERNAME = "userid";
	
	/** The Constant ISDELETE. */
	public static final String ISDELETE="isdelete";
	
	/** The Constant IS_ROOT. */
	public static final String IS_ROOT = "isRoot";



	/* (non-Javadoc)
	 * @see org.springframework.dao.support.DaoSupport#initDao()
	 */
	protected void initDao() {
		//do nothing
	}
    
    /**
	 * Save.
	 * 
	 * @param transientInstance
	 *            the transient instance
	 */
    public void save(TbomDir transientInstance) {
        log.debug("saving TbomDir instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/**
	 * Delete.
	 * 
	 * @param persistentInstance
	 *            the persistent instance
	 */
	public void delete(TbomDir persistentInstance) {
        log.debug("deleting TbomDir instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    /**
	 * Find by id.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the tbom dir
	 */
    public TbomDir findById( java.lang.String id) {
        log.debug("getting TbomDir instance with id: " + id);
        try {
            TbomDir instance = (TbomDir) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.tbom.TbomDir", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    /**
	 * Find by example.
	 * 
	 * @param instance
	 *            the instance
	 * 
	 * @return the list
	 */
    public List findByExample(TbomDir instance) {
        log.debug("finding TbomDir instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /**
	 * Find by property.
	 * 
	 * @param propertyName
	 *            the property name
	 * @param value
	 *            the value
	 * 
	 * @return the list
	 */
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding TbomDir instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from TbomDir as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	/**
	 * Find by name.
	 * 
	 * @param name
	 *            the name
	 * 
	 * @return the list
	 */
	public List findByName(Object name
	) {
		return findByProperty(NAME, name
		);
	}

	/**
	 * Find by name and schemaid.
	 * 
	 * @param name
	 *            the name
	 * @param schemaid
	 *            the schemaid
	 * 
	 * @return the list
	 */
	public List findByNameAndSchemaid(Object name,Object schemaid
	) {
	      log.debug("finding TbomDir instance with property: " + NAME
	              + ", value: " + name+"  "+SCHEMAID+ ", value: " +schemaid+"  "+ISDELETE+ ", value: " +new Long(1));
	        try {
	           String queryString = "from TbomDir as model where model." 
	           						+ NAME + "= ? and model."+SCHEMAID+"= ? and model."+ISDELETE+"= ?";
	  		 return getHibernateTemplate().find(queryString, new Object[]{name,schemaid,new Long(1)});
	        } catch (RuntimeException re) {
	           log.error("find by property name failed", re);
	           throw re;
	        }
	}	
	
	/**
	 * Find by name and isroot and schemaid.
	 * 
	 * @param name
	 *            the name
	 * @param isroot
	 *            the isroot
	 * @param schemaid
	 *            the schemaid
	 * 
	 * @return the list
	 */
	public List findByNameAndIsrootAndSchemaid(Object name,Object isroot,Object schemaid
	) {
	      log.debug("finding Tbom instance with property: " + NAME
	              + ", value: " + name+"  "+IS_ROOT+ ", value: " +isroot+"  "+SCHEMAID+ ", value: " +schemaid);
	        try {
	           String queryString = "from Tbom as model where model." 
	           						+ NAME + "= ? and model."+IS_ROOT+"= ? and model."+SCHEMAID+"= ?";
	  		 return getHibernateTemplate().find(queryString, new Object[]{name,isroot,schemaid});
	        } catch (RuntimeException re) {
	           log.error("find by property name failed", re);
	           throw re;
	        }
	}
	
	/**
	 * Find by schemaid.
	 * 
	 * @param schemaid
	 *            the schemaid
	 * 
	 * @return the list
	 */
	public List findBySchemaid(Object schemaid
	) {
	      log.debug("finding TbomDir instance with property: " + SCHEMAID
	              + ", value: " + schemaid+"  "+ISDELETE+ ", value: " +new Long(1));
	        try {
	           String queryString = "from TbomDir as model where model." 
	           						+ SCHEMAID + "= ? and model."+ISDELETE+"= ?";
	  		 return getHibernateTemplate().find(queryString, new Object[]{schemaid,new Long(1)});
	        } catch (RuntimeException re) {
	           log.error("find by property name failed", re);
	           throw re;
	        }
	  	}
	
	
	/**
	 * Find by is lock.
	 * 
	 * @param isLock
	 *            the is lock
	 * 
	 * @return the list
	 */
	public List findByIsLock(Object isLock
	) {
		return findByProperty(IS_LOCK, isLock
		);
	}
	
	/**
	 * Find by userid.
	 * 
	 * @param userid
	 *            the userid
	 * 
	 * @return the list
	 */
	public List findByUserid(Object userid
	) {
		return findByProperty(USERNAME, userid
		);
	}
	

	/**
	 * Find all.
	 * 
	 * @return the list
	 */
	public List findAll() {
		log.debug("finding all TbomDir instances");
		try {
			String queryString = "from TbomDir where id!=' ' order by order_sign";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Map<String, TbomDir> findAllTboms()
	{
		Map<String, TbomDir> tbomDirs = new HashMap<String, TbomDir>();
		String queryString = "from TbomDir order by order_sign";
		List<TbomDir> tbomList = (List<TbomDir>) this.getHibernateTemplate().find(queryString);
		for(TbomDir tbomDir : tbomList)
		{
			tbomDirs.put(tbomDir.getId(), tbomDir);
		}
		
		return tbomDirs;
				
	}
    /**
	 * Merge.
	 * 
	 * @param detachedInstance
	 *            the detached instance
	 * 
	 * @return the tbom dir
	 */
    public TbomDir merge(TbomDir detachedInstance) {
        log.debug("merging TbomDir instance");
        try {
            TbomDir result = (TbomDir) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /**
	 * Attach dirty.
	 * 
	 * @param instance
	 *            the instance
	 */
    public void attachDirty(TbomDir instance) {
        log.debug("attaching dirty TbomDir instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /**
	 * Attach clean.
	 * 
	 * @param instance
	 *            the instance
	 */
    public void attachClean(TbomDir instance) {
        log.debug("attaching clean TbomDir instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	/**
	 * Gets the from application context.
	 * 
	 * @param ctx
	 *            the ctx
	 * 
	 * @return the from application context
	 */
	public static TbomDirDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (TbomDirDAO) ctx.getBean("TbomDirDAO");
	}
}