package com.orient.sysmodel.domain.report;
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
 	* A data access object (DAO) providing persistence and search support for Reports entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Reports
  * @author MyEclipse Persistence Tools 
 */

public class ReportsDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(ReportsDAO.class);
	//property constants
	public static final String SCHEMA_ID = "schema";
	public static final String TABLE_ID = "tableId";
	public static final String VIEWS_ID = "viewsId";
	public static final String COLUMN_ID = "columnId";
	public static final String CONTENT = "content";
	public static final String FILEPATH = "filepath";
	public static final String PID = "pid";
	public static final String TYPE = "type";
	public static final String ORDERS = "orders";
	public static final String CREATE_USER = "createUser";
	public static final String NAME = "name";
	public static final String FILTERJSON = "filterjson";
	public static final String DATA_ENTRY = "dataEntry";



    
    public void save(Reports transientInstance) {
        log.debug("saving Reports instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Reports persistentInstance) {
        log.debug("deleting Reports instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Reports findById( java.lang.String id) {
        log.debug("getting Reports instance with id: " + id);
        try {
            Reports instance = (Reports) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.report.Reports", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Reports instance) {
        log.debug("finding Reports instance by example");
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
      log.debug("finding Reports instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Reports as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findBySchemaId(Object schemaId
	) {
		return findByProperty(SCHEMA_ID, schemaId
		);
	}
	
	public List findByTableId(Object tableId
	) {
		return findByProperty(TABLE_ID, tableId
		);
	}
	
	public List findByViewsId(Object viewsId
	) {
		return findByProperty(VIEWS_ID, viewsId
		);
	}
	
	public List findByColumnId(Object columnId
	) {
		return findByProperty(COLUMN_ID, columnId
		);
	}
	
	public List findByContent(Object content
	) {
		return findByProperty(CONTENT, content
		);
	}
	
	public List findByFilepath(Object filepath
	) {
		return findByProperty(FILEPATH, filepath
		);
	}
	
	public List findByPid(Object pid
	) {
		return findByProperty(PID, pid
		);
	}
	
	public List findByType(Object type
	) {
		return findByProperty(TYPE, type
		);
	}
	
	public List findByOrders(Object orders
	) {
		return findByProperty(ORDERS, orders
		);
	}
	
	public List findByCreateUser(Object createUser
	) {
		return findByProperty(CREATE_USER, createUser
		);
	}
	
	public List findByName(Object name
	) {
		return findByProperty(NAME, name
		);
	}
	
	public List findByFilterjson(Object filterjson
	) {
		return findByProperty(FILTERJSON, filterjson
		);
	}
	
	public List findByDataEntry(Object dataEntry
	) {
		return findByProperty(DATA_ENTRY, dataEntry
		);
	}
	

	public List findAll() {
		log.debug("finding all Reports instances");
		try {
			String queryString = "from Reports";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Reports merge(Reports detachedInstance) {
        log.debug("merging Reports instance");
        try {
            Reports result = (Reports) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Reports instance) {
        log.debug("attaching dirty Reports instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Reports instance) {
        log.debug("attaching clean Reports instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getHqlResult(String searchsql) {

		log.debug("getting  Reports searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  Reports searchsql failed", re);
			return null;
		}
	}
    
}