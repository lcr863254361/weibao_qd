/*
 * Title: TbomDAO.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:59 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class TbomDAO.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
//		RuntimeException.class, IOException.class,Exception.class })
public class TbomDAO extends HibernateDaoSupport {

	 /** The Constant log. */
 	private static final Log log = LogFactory.getLog(TbomDAO.class);
	 
 	/** The Constant NAME. */
 	public static final String NAME = "name";
	 
 	/** The Constant _DETAIL_TEXT. */
 	public static final String _DETAIL_TEXT = "DetailText";
	 
 	/** The Constant DESCRIPTION. */
 	public static final String DESCRIPTION = "description";
	 
 	/** The Constant BIG_IMAGE. */
 	public static final String BIG_IMAGE = "bigImage";
	 
 	/** The Constant NOR_IMAGE. */
 	public static final String NOR_IMAGE = "norImage";
	 
 	/** The Constant SMA_IMAGE. */
 	public static final String SMA_IMAGE = "smaImage";
	 
 	/** The Constant IS_VALID. */
 	public static final String IS_VALID = "isValid";
	 
 	/** The Constant IS_ROOT. */
 	public static final String IS_ROOT="isRoot";
	 
 	/** The Constant PID. */
 	public static final String PID="parenttbom";
	 
 	/** The Constant SCHEMAID. */
 	public static final String SCHEMAID="schemaid";
	 
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
	 * 
	 * @throws Exception
	 *             the exception
	 */
 	public void save(Tbom transientInstance)throws Exception {
	        log.debug("saving Tbom instance");
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
		 * 
		 * @throws Exception
		 *             the exception
		 */
		public void delete(Tbom persistentInstance)throws Exception {
	        log.debug("deleting Tbom instance");
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
		 * @return the tbom
		 */
    	public Tbom findById( java.lang.String id) {
	        log.debug("getting Tbom instance with id: " + id);
	        try {
	            Tbom instance = (Tbom) getHibernateTemplate()
	                    .get("com.orient.sysmodel.domain.tbom.Tbom", id);
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
    	public List findByExample(Tbom instance) {
	        log.debug("finding Tbom instance by example");
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
	      log.debug("finding Tbom instance with property: " + propertyName
	            + ", value: " + value);
	      try {
	         String queryString = "from Tbom as model where model." 
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
		 * Find by pid and is valid.
		 * 
		 * @param pid
		 *            the pid
		 * @param isValid
		 *            the is valid
		 * 
		 * @return the list
		 */
    	public List findByPIDAndIsValid(Object pid,Object isValid
		) {
		      log.debug("finding Tbom instance with property: " + PID
		              + ", value: " + pid+"  "+IS_VALID+ ", value: " +isValid);
		        try {
		           String queryString = "from Tbom as model where model." 
		           						+ PID + "= ? and model."+IS_VALID+"= ?";
		  		 return getHibernateTemplate().find(queryString, new Object[]{pid,isValid});
		        } catch (RuntimeException re) {
		           log.error("find by property name failed", re);
		           throw re;
		        }
		}	
	    
		/**
		 * Find by version.
		 * 
		 * @param isroot
		 *            the isroot
		 * 
		 * @return the list
		 */
		public List findByVersion(Object isroot
		) {
			return findByProperty(IS_ROOT, isroot
			);
		}
		
	    /**
		 * Find all.
		 * 
		 * @return the list
		 */
    	public List findAll() {
			log.debug("finding all Tbom instances");
			try {
				String queryString = "from Tbom";
			 	return getHibernateTemplate().find(queryString);
			} catch (RuntimeException re) {
				log.error("find all failed", re);
				throw re;
			}
		}
		
	    /**
		 * Merge.
		 * 
		 * @param detachedInstance
		 *            the detached instance
		 * 
		 * @return the tbom
		 * 
		 * @throws Exception
		 *             the exception
		 */
    	public Tbom merge(Tbom detachedInstance)throws Exception {
	        log.debug("merging Tbom instance");
	        try {
	            Tbom result = (Tbom) getHibernateTemplate()
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
    	public void attachDirty(Tbom instance) {
	        log.debug("attaching dirty Tbom instance");
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
    	public void attachClean(Tbom instance) {
	        log.debug("attaching clean Tbom instance");
	        try {
	            getHibernateTemplate().lock(instance, LockMode.NONE);
	            log.debug("attach successful");
	        } catch (RuntimeException re) {
	            log.error("attach failed", re);
	            throw re;
	        }
	    }
    	
    	/**
		 * updateCascade.
		 * 
		 * @param tbomId
		 */
    	public void updateCascade(String tbomId){
        	try {
        		StringBuffer sql=new StringBuffer();
        		sql.append("UPDATE CWM_TBOM SET IS_VALID=0 WHERE ID IN(SELECT ID FROM CWM_TBOM START WITH ID = '").append(tbomId).append("' CONNECT BY PRIOR ID = PID)");
    			this.getSession().createSQLQuery(sql.toString()).executeUpdate();
    		} catch (RuntimeException re) {
    			log.error("updateCascade failed", re);
    		}
        }
    	
    	/**
		 * deleteCascade.
		 * 
		 * @param tbomId
		 */
    	public void deleteCascade(String tbomId){
        	try {
        		StringBuffer sql=new StringBuffer();
        		sql.append("DELETE CWM_TBOM  WHERE ID IN(SELECT ID FROM CWM_TBOM START WITH ID = '").append(tbomId).append("' CONNECT BY PRIOR ID = PID)");
    			this.getSession().createSQLQuery(sql.toString()).executeUpdate();
    		} catch (RuntimeException re) {
    			log.error("deleteCascade failed", re);
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
    	
    	public List findTbomRoots()
    	{
    		String queryString = "from Tbom as model where model."+IS_ROOT+"=1";
    		return getHibernateTemplate().find(queryString);
    	}

		/**
		 * Gets the from application context.
		 * 
		 * @param ctx
		 *            the ctx
		 * 
		 * @return the from application context
		 */
		public static TbomDAO getFromApplicationContext(ApplicationContext ctx) {
	    	return (TbomDAO) ctx.getBean("TbomDAO");
		}
}
