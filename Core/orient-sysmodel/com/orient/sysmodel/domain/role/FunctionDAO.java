package com.orient.sysmodel.domain.role;

// default package

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Function entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .Function
 * @author MyEclipse Persistence Tools
 */

public class FunctionDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(FunctionDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String URL = "url";
	public static final String NOTES = "notes";
	public static final String ADD_FLG = "addFlg";
	public static final String DEL_FLG = "delFlg";
	public static final String EDIT_FLG = "editFlg";
	public static final String LOGTYPE = "logtype";
	public static final String LOGSHOW = "logshow";
	public static final String IS_SHOW = "isShow";
	public static final String TBOM_FLG = "tbomFlg";
	public static final String PARENTID = "parentid";
	public static final String JS = "js";
	public static final String ICON = "icon";

	public void save(Function transientInstance) {
		log.debug("saving Function instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Function persistentInstance) {
		log.debug("deleting Function instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Function findById(java.lang.Long id) {
		log.debug("getting Function instance with id: " + id);
		try {
			Function instance = (Function) getHibernateTemplate().get(
					"com.orient.sysmodel.domain.role.Function", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Function instance) {
		log.debug("finding Function instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Function instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Function as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List findByAddFlg(Object addFlg) {
		return findByProperty(ADD_FLG, addFlg);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findByEditFlg(Object editFlg) {
		return findByProperty(EDIT_FLG, editFlg);
	}

	public List findByLogtype(Object logtype) {
		return findByProperty(LOGTYPE, logtype);
	}

	public List findByLogshow(Object logshow) {
		return findByProperty(LOGSHOW, logshow);
	}

	public List findByIsShow(Object isShow) {

		String queryString = "from Function where " + IS_SHOW + " = " + isShow
				+ " ORDER BY position ASC";
		return getHibernateTemplate().find(queryString);
	}

	public List findByTbomFlg(Object tbomFlg) {
		return findByProperty(TBOM_FLG, tbomFlg);
	}

	public List findByParentId(Object parentId) {

		String queryString = "from Function where " + PARENTID + " = "
				+ parentId + " ORDER BY position ASC";
		return getHibernateTemplate().find(queryString);
	}

	public List findByJs(Object js) {
		return findByProperty(JS, js);
	}

	public List findByIcon(Object icon) {
		return findByProperty(ICON, icon);
	}

	public List findAll() {
		log.debug("finding all Function instances");
		try {
			String queryString = "from Function ORDER BY position ASC";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Map<String, Function> findAllFuns() {
		Map<String, Function> functions = new LinkedHashMap<String, Function>();
		List<Function> functionList = this.findAll();
		for(Function function : functionList)
			functions.put(function.getFunctionid().toString(), function);
		
		return functions;
	}

	public Function merge(Function detachedInstance) {
		log.debug("merging Function instance");
		try {
			Function result = (Function) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Function instance) {
		log.debug("attaching dirty Function instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Function instance) {
		log.debug("attaching clean Function instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List getSqlResult(String searchsql) {

		log.debug("getting  Function searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  Function searchsql failed", re);
			return null;
		}
	}
}