package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.Arith;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * ArithDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class ArithDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(ArithDAO.class);

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
    public static final String PID = "pid";
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

    public Arith findById(java.lang.String id) {
        log.debug("getting Arith instance with id: " + id);
        try {
            Arith instance = (Arith) getHibernateTemplate().get("Arith", id);
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
        log.debug("finding Arith instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Arith as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findByCategory(Object category) {
        return findByProperty(CATEGORY, category);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByFileName(Object fileName) {
        return findByProperty(FILE_NAME, fileName);
    }

    public List findByMethodName(Object methodName) {
        return findByProperty(METHOD_NAME, methodName);
    }

    public List findByParaNumber(Object paraNumber) {
        return findByProperty(PARA_NUMBER, paraNumber);
    }

    public List findByParaType(Object paraType) {
        return findByProperty(PARA_TYPE, paraType);
    }

    public List findByRefLib(Object refLib) {
        return findByProperty(REF_LIB, refLib);
    }

    public List findByDataType(Object dataType) {
        return findByProperty(DATA_TYPE, dataType);
    }

    public List findByIsValid(Object isValid) {
        return findByProperty(IS_VALID, isValid);
    }

    public List findByArithType(Object arithType) {
        return findByProperty(ARITH_TYPE, arithType);
    }

    public List findByLeastNumber(Object leastNumber) {
        return findByProperty(LEAST_NUMBER, leastNumber);
    }

    public List findByClassName(Object className) {
        return findByProperty(CLASS_NAME, className);
    }

    public List findByClassPackage(Object classPackage) {
        return findByProperty(CLASS_PACKAGE, classPackage);
    }

    public List findByFileNumber(Object fileNumber) {
        return findByProperty(FILE_NUMBER, fileNumber);
    }

    public List findByArithMethod(Object arithMethod) {
        return findByProperty(ARITH_METHOD, arithMethod);
    }

    public List findByPid(Object pid) {
        return findByProperty(PID, pid);
    }

    public List findByMainJar(Object mainJar) {
        return findByProperty(MAIN_JAR, mainJar);
    }

    public List findBySomeProperty(Object name, Object category, Object paraType, Object leastNumber, Object arithType, Object dataType, Object arithMethod, Object isValid) {
        log.debug("finding Arith instance with property: " + NAME
                + ", value: " + name + "  " + CATEGORY + ", value: " + category
                + "  " + PARA_TYPE + ", value: " + paraType + "  " + LEAST_NUMBER + ", value: " + leastNumber
                + "  " + ARITH_TYPE + ", value: " + arithType + "  " + DATA_TYPE + ", value: " + dataType
                + "  " + ARITH_METHOD + ", value: " + arithMethod + "  " + IS_VALID + ", value: " + isValid);
        try {
            String queryString = "from Arith as model where model."
                    + NAME + "= ? and model." + CATEGORY + "= ? and model." + PARA_TYPE + "= ? and model." + LEAST_NUMBER +
                    "= ? and model." + ARITH_TYPE + "= ? and model." + DATA_TYPE + "= ? and model." + ARITH_METHOD +
                    "= ? and model." + IS_VALID + "= ?";
            return getHibernateTemplate().find(queryString, new Object[]{name, category, paraType, leastNumber, arithType, dataType, arithMethod, isValid});
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

}