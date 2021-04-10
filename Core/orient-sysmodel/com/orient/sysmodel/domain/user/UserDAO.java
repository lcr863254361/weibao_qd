package com.orient.sysmodel.domain.user;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.dao.impl.BaseHibernateDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 	* A data access object (DAO) providing persistence and search support for User entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .User
  * @author MyEclipse Persistence Tools 
 */

public class UserDAO extends BaseHibernateDaoImpl {
    private static final Log log = LogFactory.getLog(UserDAO.class);
	//property constants
	public static final String USER_NAME = "userName";
	public static final String ALL_NAME = "allName";
	public static final String PASSWORD = "password";
	public static final String SEX = "sex";
	public static final String PHONE = "phone";
	public static final String POST = "post";
	public static final String SPECIALTY = "specialty";
	public static final String GRADE = "grade";
	public static final String CREATE_USER = "createUser";
	public static final String UPDATE_USER = "updateUser";
	public static final String NOTES = "notes";
	public static final String STATE = "state";
	public static final String MOBILE = "mobile";
	public static final String FLG = "flg";
	//public static final String DEP_ID = "depId";
	public static final String DEP_ID = "dept";
	public static final String IS_DEL = "isDel";
	public static final String _EMAIL = "EMail";
	public static final String LOCK_STATE = "lockState";
	public static final String LOGIN_FAILURES = "loginFailures";
	public static final String UNIT = "unit";


    /**
     * 
    
     * @Method: save 
    
     * TODO插入用户数据
    
     * @param transientInstance 用户对象数据
    
     * @return void
    
     * @throws
     */
    public void save(User transientInstance) {
        log.debug("saving User instance");
        try {
        	getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(User persistentInstance) {
        log.debug("deleting User instance");
        try {
        	getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
	public void delete(String id) {
        log.debug("deleting User by id");
        try {
        	User user = this.findById(id);
        	this.delete(user);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
	
    public User findById( java.lang.String id) {
        log.debug("getting User instance with id: " + id);
        try {
            User instance = (User) getHibernateTemplate()
                    .get("com.orient.sysmodel.domain.user.User", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(User instance) {
        log.debug("finding User instance by example");
        try {
        	List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

	public List findByExampleLike(LightweightUser instance, Map<String, String> betweens) {
		log.debug("finding User instance by example like");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Example example = Example.create(instance).enableLike(MatchMode.ANYWHERE).ignoreCase();
			Criteria criteria = session.createCriteria(LightweightUser.class).add(example);

			String[] between = betweens.get("birthday").split(",", -1);
			try {
				if("null".equals(between[0])||"null".equals(between[1])) {

				}else {
					criteria.add(Restrictions.between("birthday", format.parse(between[0]), format.parse(between[1])));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			List results = criteria.list();

			log.debug("find by example like successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example like failed", re);
			throw re;
		}
	}
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding User instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from User as model where model." 
         						+ propertyName + "= ? and model.state='1'";
         return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByUserName(Object userName
	) {
		return findByProperty(USER_NAME, userName
		);
	}
	
	public List findByAllName(Object allName
	) {
		return findByProperty(ALL_NAME, allName
		);
	}
	
	public List findByPassword(Object password
	) {
		return findByProperty(PASSWORD, password
		);
	}
	
	public List findBySex(Object sex
	) {
		return findByProperty(SEX, sex
		);
	}
	
	public List findByPhone(Object phone
	) {
		return findByProperty(PHONE, phone
		);
	}
	
	public List findByPost(Object post
	) {
		return findByProperty(POST, post
		);
	}
	
	public List findBySpecialty(Object specialty
	) {
		return findByProperty(SPECIALTY, specialty
		);
	}
	
	public List findByGrade(Object grade
	) {
		return findByProperty(GRADE, grade
		);
	}
	
	public List findByCreateUser(Object createUser
	) {
		return findByProperty(CREATE_USER, createUser
		);
	}
	
	public List findByUpdateUser(Object updateUser
	) {
		return findByProperty(UPDATE_USER, updateUser
		);
	}
	
	public List findByNotes(Object notes
	) {
		return findByProperty(NOTES, notes
		);
	}
	
	public List findByState(Object state
	) {
		return findByProperty(STATE, state
		);
	}
	
	public List findByMobile(Object mobile
	) {
		return findByProperty(MOBILE, mobile
		);
	}
	
	public List findByFlg(Object flg
	) {
		return findByProperty(FLG, flg
		);
	}
	
	public List findByDepId(Object depId
	) {
		return findByProperty(DEP_ID, depId
		);
	}
	
	public List findByIsDel(Object isDel
	) {
		return findByProperty(IS_DEL, isDel
		);
	}
	
	public List findByEMail(Object EMail
	) {
		return findByProperty(_EMAIL, EMail
		);
	}
	
	public List findByLockState(Object lockState
	) {
		return findByProperty(LOCK_STATE, lockState
		);
	}
	
	public List findByLoginFailures(Object loginFailures
	) {
		return findByProperty(LOGIN_FAILURES, loginFailures
		);
	}
	

	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Map<String, User> findAllUsers() {
		Map<String, User> users = new HashMap<String, User>();
		List<User> userList = this.findAll();
		for(User user : userList) {
			if (!"0".equals(user.getState())) {
				users.put(user.getId(), user);
			}
		}
		return users;
	}
	
    public User merge(User detachedInstance) {
        log.debug("merging User instance");
        try {
            User result = (User) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(User instance) {
        log.debug("attaching dirty User instance");
        try {
        	getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(User instance) {
        log.debug("attaching clean User instance");
        try {
        	getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }  
    
    public List getSqlResult(String searchsql) {

		log.debug("getting  User searchsql sentence" + searchsql);

		try {
			SQLQuery query = this.getSession().createSQLQuery(searchsql);
			List result = query.addEntity(User.class).list();
			//List result = query.list();
			return result;
		} catch (RuntimeException re) {
			log.error("getting  User searchsql failed", re);
			return null;
		}
	}
    
    public List getHqlResult(String searchsql) {

		log.debug("getting  User searchsql sentence" + searchsql);

		try {
			List result = this.getHibernateTemplate().find(searchsql);
			return result;
		} catch (RuntimeException re) {
			log.error("getting  User searchsql failed", re);
			return null;
		}
	}

	private HibernateTemplate getHibernateTemplate() {
		return OrientContextLoaderListener.Appwac.getBean(HibernateTemplate.class);
	}
    
}