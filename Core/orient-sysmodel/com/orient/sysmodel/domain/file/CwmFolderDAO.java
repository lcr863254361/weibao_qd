package com.orient.sysmodel.domain.file;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * CwmFolder entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see CwmFolder
 * @author MyEclipse Persistence Tools
 */

public class CwmFolderDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CwmFolderDAO.class);

	// property constants
	public static final String ID = "id";
	public static final String DELFLAG = "delFlag";
	public static final String ADDFLAG = "addFlag";
	public static final String EDITFLAG = "editFlag";
	public static final String CWMFOLDERID = "cwmFolderId";
	public static final String CWMTABLESID = "cwmTablesId";
	public static final String RECORDID = "recordId";
	public static final String FOLDERNAME = "folderName";
	

	public void save(CwmFolder transientInstance) {
		log.debug("saving CwmFolder instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void delete(CwmFolder persistentInstance) {
		log.debug("deleting CwmFolder instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CwmFolder findById(String id) {
		log.debug("getting CwmFolder instance with id: " + id);
		try {
			CwmFolder instance = (CwmFolder) getHibernateTemplate()
					.get("com.orient.sysmodel.domain.file.CwmFolder", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CwmFolder instance) {
		log.debug("finding CwmFolder instance by example");
		try {
			List results =getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CwmFolder instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CwmFolder as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all CwmFolder instances");
		try {
			String queryString = "from CwmFolder";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CwmFolder merge(CwmFolder detachedInstance) {
		log.debug("merging CwmFolder instance");
		try {
			CwmFolder result = (CwmFolder) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CwmFolder instance) {
		log.debug("attaching dirty CwmFolder instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CwmFolder instance) {
		log.debug("attaching clean CwmFolder instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	 public List getHqlResult(String searchsql) {

		log.debug("getting  CwmFolder searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  CwmFolder searchsql failed", re);
			return null;
		}
	}
}