package com.orient.sysmodel.domain.user;
// default package


import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for UserColumn entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .UserColumn
  * @author MyEclipse Persistence Tools 
 */

public class UserColumnDAO extends HibernateDaoSupport  {
    private static final Log log = LogFactory.getLog(UserColumnDAO.class);
	//property constants
	public static final String DISPLAY_NAME = "displayName";
	public static final String _SCOLUMN_NAME = "SColumnName";
	public static final String IS_FOR_SEARCH = "isForSearch";
	public static final String IS_NULLABLE = "isNullable";
	public static final String IS_ONLY = "isOnly";
	public static final String IS_PK = "isPk";
	public static final String ENMU_ID = "enmuId";
	public static final String COL_TYPE = "colType";
	public static final String SEQUENCE_NAME = "sequenceName";
	public static final String IS_AUTOINCREMENT = "isAutoincrement";
	public static final String IS_WRAP = "isWrap";
	public static final String CHECK_TYPE = "checkType";
	public static final String IS_MULTI_SELECTED = "isMultiSelected";
	public static final String DEFAULT_VALUE = "defaultValue";
	public static final String DISPLAY_SHOW = "displayShow";
	public static final String EDIT_SHOW = "editShow";
	public static final String INPUT_TYPE = "inputType";
	public static final String IS_READONLY = "isReadonly";
	public static final String REF_TABLE = "refTable";
	public static final String REF_TABLE_COLUMN_ID = "refTableColumnId";
	public static final String REF_TABLE_COLUMN_SHOWNAME = "refTableColumnShowname";
	public static final String POP_WINDOW_FUNCTION = "popWindowFunction";
	public static final String IS_FOR_INFOSEARCH = "isForInfosearch";
	public static final String IS_DISPALYINFO_SHOW = "isDispalyinfoShow";
	public static final String IS_VIEWINFO_SHOW = "isViewinfoShow";



    
    public void save(UserColumn transientInstance) {
        log.debug("saving UserColumn instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(UserColumn persistentInstance) {
        log.debug("deleting UserColumn instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public UserColumn findById( java.lang.String id) {
        log.debug("getting UserColumn instance with id: " + id);
        try {
            UserColumn instance = (UserColumn) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.user.UserColumn", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(UserColumn instance) {
        log.debug("finding UserColumn instance by example");
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
      log.debug("finding UserColumn instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from UserColumn as model where model." 
         						+ propertyName + "= ? order by model.shot ";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByDisplayName(Object displayName
	) {
		return findByProperty(DISPLAY_NAME, displayName
		);
	}
	
	public List findBySColumnName(Object SColumnName
	) {
		return findByProperty(_SCOLUMN_NAME, SColumnName
		);
	}
	
	public List findByIsForSearch(Object isForSearch
	) {
		return findByProperty(IS_FOR_SEARCH, isForSearch
		);
	}
	
	public List findByIsNullable(Object isNullable
	) {
		return findByProperty(IS_NULLABLE, isNullable
		);
	}
	
	public List findByIsOnly(Object isOnly
	) {
		return findByProperty(IS_ONLY, isOnly
		);
	}
	
	public List findByIsPk(Object isPk
	) {
		return findByProperty(IS_PK, isPk
		);
	}
	
	public List findByEnmuId(Object enmuId
	) {
		return findByProperty(ENMU_ID, enmuId
		);
	}
	
	public List findByColType(Object colType
	) {
		return findByProperty(COL_TYPE, colType
		);
	}
	
	public List findBySequenceName(Object sequenceName
	) {
		return findByProperty(SEQUENCE_NAME, sequenceName
		);
	}
	
	public List findByIsAutoincrement(Object isAutoincrement
	) {
		return findByProperty(IS_AUTOINCREMENT, isAutoincrement
		);
	}
	
	public List findByIsWrap(Object isWrap
	) {
		return findByProperty(IS_WRAP, isWrap
		);
	}
	
	public List findByCheckType(Object checkType
	) {
		return findByProperty(CHECK_TYPE, checkType
		);
	}
	
	public List findByIsMultiSelected(Object isMultiSelected
	) {
		return findByProperty(IS_MULTI_SELECTED, isMultiSelected
		);
	}
	
	public List findByDefaultValue(Object defaultValue
	) {
		return findByProperty(DEFAULT_VALUE, defaultValue
		);
	}
	
	public List findByDisplayShow(Object displayShow
	) {
		return findByProperty(DISPLAY_SHOW, displayShow
		);
	}
	
	public List findByEditShow(Object editShow
	) {
		return findByProperty(EDIT_SHOW, editShow
		);
	}
	
	public List findByInputType(Object inputType
	) {
		return findByProperty(INPUT_TYPE, inputType
		);
	}
	
	public List findByIsReadonly(Object isReadonly
	) {
		return findByProperty(IS_READONLY, isReadonly
		);
	}
	
	public List findByRefTable(Object refTable
	) {
		return findByProperty(REF_TABLE, refTable
		);
	}
	
	public List findByRefTableColumnId(Object refTableColumnId
	) {
		return findByProperty(REF_TABLE_COLUMN_ID, refTableColumnId
		);
	}
	
	public List findByRefTableColumnShowname(Object refTableColumnShowname
	) {
		return findByProperty(REF_TABLE_COLUMN_SHOWNAME, refTableColumnShowname
		);
	}
	
	public List findByPopWindowFunction(Object popWindowFunction
	) {
		return findByProperty(POP_WINDOW_FUNCTION, popWindowFunction
		);
	}
	
	public List findByIsForInfosearch(Object isForInfosearch
	) {
		return findByProperty(IS_FOR_INFOSEARCH, isForInfosearch
		);
	}
	
	public List findByIsDispalyinfoShow(Object isDispalyinfoShow
	) {
		return findByProperty(IS_DISPALYINFO_SHOW, isDispalyinfoShow
		);
	}
	
	public List findByIsViewinfoShow(Object isViewinfoShow
	) {
		return findByProperty(IS_VIEWINFO_SHOW, isViewinfoShow
		);
	}
	

	public List findAll() {
		log.debug("finding all UserColumn instances");
		try {
			String queryString = "from UserColumn order by shot ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public UserColumn merge(UserColumn detachedInstance) {
        log.debug("merging UserColumn instance");
        try {
            UserColumn result = (UserColumn) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserColumn instance) {
        log.debug("attaching dirty UserColumn instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(UserColumn instance) {
        log.debug("attaching clean UserColumn instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}