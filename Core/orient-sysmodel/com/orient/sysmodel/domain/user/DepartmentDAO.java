package com.orient.sysmodel.domain.user;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for Department entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Department
  * @author MyEclipse Persistence Tools 
 */

public class DepartmentDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(DepartmentDAO.class);
	//property constants
	public static final String PID = "pid";
    //public static final String PID = "parentDept";
	public static final String NAME = "name";
	public static final String FUNCTION = "function";
	public static final String NOTES = "notes";
	public static final String ADD_FLG = "addFlg";
	public static final String DEL_FLG = "delFlg";
	public static final String EDIT_FLG = "editFlg";



    
    public void save(Department transientInstance) {
        log.debug("saving Department instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Department persistentInstance) {
        log.debug("deleting Department instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Department findById( java.lang.String id) {
        log.debug("getting Department instance with id: " + id);
        try {
            Department instance = (Department) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.user.Department", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Department instance) {
        log.debug("finding Department instance by example");
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
      log.debug("finding Department instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Department as model where model." 
         						+ propertyName + "= ?";
        return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPid(Object pid
	) {
		return findByProperty(PID, pid
		);
	}
	
	public List findByName(Object name
	) {
		return findByProperty(NAME, name
		);
	}
	
	public List findByFunction(Object function
	) {
		return findByProperty(FUNCTION, function
		);
	}
	
	public List findByNotes(Object notes
	) {
		return findByProperty(NOTES, notes
		);
	}
	
	public List findByAddFlg(Object addFlg
	) {
		return findByProperty(ADD_FLG, addFlg
		);
	}
	
	public List findByDelFlg(Object delFlg
	) {
		return findByProperty(DEL_FLG, delFlg
		);
	}
	
	public List findByEditFlg(Object editFlg
	) {
		return findByProperty(EDIT_FLG, editFlg
		);
	}
	

	public List findAll() {
		log.debug("finding all Department instances");
		try {
			String queryString = "from Department"+" order by to_number(id) ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Map<Integer, Department> findAllDeparts()
	{
		Map<Integer, Department> departs = new TreeMap<Integer, Department>();
		List<Department> departList = this.findAll();
		for(Department depart : departList)
			departs.put(Integer.valueOf(depart.getId()), depart);	
		
		return departs;
		
	}
	
    public Department merge(Department detachedInstance) {
        log.debug("merging Department instance");
        try {
            Department result = (Department) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Department instance) {
        log.debug("attaching dirty Department instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Department instance) {
        log.debug("attaching clean Department instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}