package com.orient.sysmodel.domain.arith;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for Arith entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Arith
  * @author MyEclipse Persistence Tools 
 */

public class ArithDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(ArithDAO.class);
	//property constants
    public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String CATEGORY = "category";
	public static final String DESCRIPTION = "description";
	public static final String FILE_NAME = "fileName";
	public static final String METHOD_NAME = "methodName";
	public static final String PARA_NUMBER = "paraNumber";
	public static final String PARA_TYPE = "paraType";
	public static final String REF_LIB = "refLib";
	public static final String DATA_TYPE = "dataType";
	public static final String IS_VALID = "isValid";
	public static final String ARITH_TYPE = "arithType";
	public static final String LEAST_NUMBER = "leastNumber";
	public static final String CLASS_NAME = "className";
	public static final String CLASS_PACKAGE = "classPackage";
	public static final String FILE_NUMBER = "fileNumber";
	public static final String ARITH_METHOD = "arithMethod";
	public static final String PID = "parentarith";
	public static final String MAIN_JAR = "mainJar";



    
    public void save(Arith transientInstance) {
        log.debug("saving Arith instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Arith persistentInstance) {
        log.debug("deleting Arith instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Arith findById( java.lang.String id) {
        log.debug("getting Arith instance with id: " + id);
        try {
            Arith instance = (Arith) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.arith.Arith", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Arith instance) {
        log.debug("finding Arith instance by example");
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
      log.debug("finding Arith instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Arith as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all Arith instances");
		try {
			String queryString = "from Arith";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Arith merge(Arith detachedInstance) {
        log.debug("merging Arith instance");
        try {
            Arith result = (Arith) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Arith instance) {
        log.debug("attaching dirty Arith instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Arith instance) {
        log.debug("attaching clean Arith instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List findBySomeProperty(Object name,Object category,Object paraType,Object leastNumber,Object arithType,Object dataType,Object arithMethod,Object isValid){
		log.debug("finding Arith instance with property: " + NAME
	              + ", value: " + name+"  "+CATEGORY+ ", value: " +category
	              +"  "+PARA_TYPE+ ", value: " +paraType +"  "+LEAST_NUMBER+ ", value: " +leastNumber
	              +"  "+ARITH_TYPE+ ", value: " +arithType+"  "+DATA_TYPE+ ", value: " +dataType
	              +"  "+ARITH_METHOD+ ", value: " +arithMethod+"  "+IS_VALID+ ", value: " +isValid);
	        try {
	           String queryString = "from Arith as model where model." 
	           						+ NAME + "= ? and model."+CATEGORY+ "= ? and model."+PARA_TYPE+ "= ? and model."+LEAST_NUMBER+
	           					 "= ? and model."+ARITH_TYPE+ "= ? and model."+DATA_TYPE+ "= ? and model."+ARITH_METHOD+
	           					 "= ? and model."+IS_VALID+"= ?";
	  		 return getHibernateTemplate().find(queryString, new Object[]{name,category,paraType,leastNumber,arithType,dataType,arithMethod,isValid});
	        } catch (RuntimeException re) {
	           log.error("find by property name failed", re);
	           throw re;
	        }
	}


}