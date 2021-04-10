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
 	* A data access object (DAO) providing persistence and search support for EtlScript entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .EtlScript
  * @author MyEclipse Persistence Tools 
 */

public class EtlScriptDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(EtlScriptDAO.class);
	//property constants
	public static final String SCRIPTNAME = "scriptname";
	public static final String FILENAME = "filename";
	public static final String FILETYPE = "filetype";
	public static final String DATAINDEX = "dataindex";
	public static final String LINESPLIT = "linesplit";
	public static final String USERNAME = "username";
	public static final String ERRORSOLVE = "errorsolve";
	public static final String JOBTYPE = "jobtype";
	public static final String FILEPATH = "filepath";
	public static final String FILELENGTH = "filelength";
	public static final String FILELASTMOD = "filelastmod";
	public static final String JOBTIME = "jobtime";
	public static final String SRCCOLUMN = "srccolumn";
	public static final String IMPORT_TYPE = "importType";



    
    public void save(EtlScript transientInstance) {
        log.debug("saving EtlScript instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(EtlScript persistentInstance) {
        log.debug("deleting EtlScript instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public EtlScript findById( java.lang.String id) {
        log.debug("getting EtlScript instance with id: " + id);
        try {
            EtlScript instance = (EtlScript) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.etl.EtlScript", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(EtlScript instance) {
        log.debug("finding EtlScript instance by example");
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
      log.debug("finding EtlScript instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from EtlScript as model where model." 
         						+ propertyName + "= ?";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByScriptname(Object scriptname
	) {
		return findByProperty(SCRIPTNAME, scriptname
		);
	}
	
	public List findByFilename(Object filename
	) {
		return findByProperty(FILENAME, filename
		);
	}
	
	public List findByFiletype(Object filetype
	) {
		return findByProperty(FILETYPE, filetype
		);
	}
	
	public List findByDataindex(Object dataindex
	) {
		return findByProperty(DATAINDEX, dataindex
		);
	}
	
	public List findByLinesplit(Object linesplit
	) {
		return findByProperty(LINESPLIT, linesplit
		);
	}
	
	public List findByUsername(Object username
	) {
		return findByProperty(USERNAME, username
		);
	}
	
	public List findByErrorsolve(Object errorsolve
	) {
		return findByProperty(ERRORSOLVE, errorsolve
		);
	}
	
	public List findByJobtype(Object jobtype
	) {
		return findByProperty(JOBTYPE, jobtype
		);
	}
	
	public List findByFilepath(Object filepath
	) {
		return findByProperty(FILEPATH, filepath
		);
	}
	
	public List findByFilelength(Object filelength
	) {
		return findByProperty(FILELENGTH, filelength
		);
	}
	
	public List findByFilelastmod(Object filelastmod
	) {
		return findByProperty(FILELASTMOD, filelastmod
		);
	}
	
	public List findByJobtime(Object jobtime
	) {
		return findByProperty(JOBTIME, jobtime
		);
	}
	
	public List findBySrccolumn(Object srccolumn
	) {
		return findByProperty(SRCCOLUMN, srccolumn
		);
	}
	
	public List findByImportType(Object importType
	) {
		return findByProperty(IMPORT_TYPE, importType
		);
	}
	

	public List findAll() {
		log.debug("finding all EtlScript instances");
		try {
			String queryString = "from EtlScript";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public EtlScript merge(EtlScript detachedInstance) {
        log.debug("merging EtlScript instance");
        try {
            EtlScript result = (EtlScript) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EtlScript instance) {
        log.debug("attaching dirty EtlScript instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(EtlScript instance) {
        log.debug("attaching clean EtlScript instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List getHqlResult(String searchsql) {

		log.debug("getting  EtlScript searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  EtlScript searchsql failed", re);
			return null;
		}
	}
}