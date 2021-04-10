package com.orient.sysmodel.domain.workflow;
// default package

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.orient.utils.CommonTools;




/**
 * A data access object (DAO) providing persistence and search support for
 * JbpmVariable entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .JbpmVariable
 * @author MyEclipse Persistence Tools
 */

public class JbpmVariableDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(JbpmVariableDAO.class);
	// property constants
	public static final String PROCESSNAME = "processname";
	public static final String PIID = "piid";
	public static final String TASKNAME = "taskname";
	public static final String TASKID = "taskid";
	public static final String VARIABLETYPE = "variabletype";
	public static final String KEY = "key";
	public static final String VALUE = "value";
	public static final String TASKBELONGED = "taskbelonged";

	protected void initDao() {
		// do nothing
	}

	public void save(JbpmVariable transientInstance) {
		log.debug("saving JbpmVariable instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(JbpmVariable persistentInstance) {
		log.debug("deleting JbpmVariable instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public JbpmVariable findById(java.lang.String id) {
		log.debug("getting JbpmVariable instance with id: " + id);
		try {
			JbpmVariable instance = (JbpmVariable) getHibernateTemplate().get(
					"JbpmVariable", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(JbpmVariable instance) {
		log.debug("finding JbpmVariable instance by example");
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
		log.debug("finding JbpmVariable instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from JbpmVariable as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProcessname(Object processname) {
		return findByProperty(PROCESSNAME, processname);
	}

	public List findByPiid(Object piid) {
		return findByProperty(PIID, piid);
	}

	public List findByTaskname(Object taskname) {
		return findByProperty(TASKNAME, taskname);
	}

	public List findByTaskid(Object taskid) {
		return findByProperty(TASKID, taskid);
	}

	public List findByVariabletype(Object variabletype) {
		return findByProperty(VARIABLETYPE, variabletype);
	}

	public List findByKey(Object key) {
		return findByProperty(KEY, key);
	}

	public List findByValue(Object value) {
		return findByProperty(VALUE, value);
	}

	public List findAll() {
		log.debug("finding all JbpmVariable instances");
		try {
			String queryString = "from JbpmVariable";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public JbpmVariable merge(JbpmVariable detachedInstance) {
		log.debug("merging JbpmVariable instance");
		try {
			JbpmVariable result = (JbpmVariable) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(JbpmVariable instance) {
		log.debug("attaching dirty JbpmVariable instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(JbpmVariable instance) {
		log.debug("attaching clean JbpmVariable instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static JbpmVariableDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (JbpmVariableDAO) ctx.getBean("JbpmVariableDAO");
	}

	@SuppressWarnings("unchecked")
	public JbpmVariable getJbpmRefTaskDataId(String processName, String taskName,String taskId,
			String refTaskType, String piId, String key) {
		try {
			String queryString = "from JbpmVariable as model where model.processname like ? and  model.taskname = ? and model.taskid = ? and model.variabletype = ? and model.key = ? and model.piid = ?";
			List<JbpmVariable> instances = (List<JbpmVariable>) getHibernateTemplate().find(queryString, new Object[]{processName + "%",taskName,taskId,refTaskType,key,piId});
			JbpmVariable variable = null;
			if(null != instances && !instances.isEmpty())
			{
				variable = instances.get(0);
			}
			return variable;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public String findStartUserByPiId(Object piId) {
		
		try {
			String startUser = "";
			String queryString = "from JbpmVariable as model where model."
					+ PIID + "= ? AND model." + VARIABLETYPE + " = ?";
			List  queryList = getHibernateTemplate().find(queryString,piId,JbpmVariableType.STARTPROCESSUSER);
			if(!CommonTools.isEmptyList(queryList)){
				startUser = ((JbpmVariable)queryList.get(0)).getValue();
			}
			return startUser;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}