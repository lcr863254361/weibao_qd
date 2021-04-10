package com.orient.sysmodel.domain.etl;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for EtlTranslator entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .EtlTranslator
  * @author MyEclipse Persistence Tools 
 */

public class EtlTranslatorDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(EtlTranslatorDAO.class);
	//property constants
	public static final String TABLENAME = "tablename";
	public static final String TABLEID = "tableid";
	public static final String SCRIPTID = "script";
	public static final String TABLECOLUMN = "tablecolumn";
	public static final String TABLESYSNAME = "tablesysname";
	public static final String TRANSLATOR = "translator";



    
    public void save(EtlTranslator transientInstance) {
        log.debug("saving EtlTranslator instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(EtlTranslator persistentInstance) {
        log.debug("deleting EtlTranslator instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public EtlTranslator findById( java.lang.String id) {
        log.debug("getting EtlTranslator instance with id: " + id);
        try {
            EtlTranslator instance = (EtlTranslator) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.etl.EtlTranslator", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(EtlTranslator instance) {
        log.debug("finding EtlTranslator instance by example");
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
      log.debug("finding EtlTranslator instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from EtlTranslator as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByTablename(Object tablename
	) {
		return findByProperty(TABLENAME, tablename
		);
	}
	
	public List findByTableid(Object tableid
	) {
		return findByProperty(TABLEID, tableid
		);
	}
	
	public List findByScriptid(Object scriptid
	) {
		return findByProperty(SCRIPTID, scriptid
		);
	}
	
	public List findByTablecolumn(Object tablecolumn
	) {
		return findByProperty(TABLECOLUMN, tablecolumn
		);
	}
	
	public List findByTablesysname(Object tablesysname
	) {
		return findByProperty(TABLESYSNAME, tablesysname
		);
	}
	
	public List findByTranslator(Object translator
	) {
		return findByProperty(TRANSLATOR, translator
		);
	}
	

	public List findAll() {
		log.debug("finding all EtlTranslator instances");
		try {
			String queryString = "from EtlTranslator";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public EtlTranslator merge(EtlTranslator detachedInstance) {
        log.debug("merging EtlTranslator instance");
        try {
            EtlTranslator result = (EtlTranslator) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EtlTranslator instance) {
        log.debug("attaching dirty EtlTranslator instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(EtlTranslator instance) {
        log.debug("attaching clean EtlTranslator instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getHqlResult(String searchsql) {

		log.debug("getting  EtlTranslator searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  EtlTranslator searchsql failed", re);
			return null;
		}
	}
}