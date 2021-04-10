/*
 * Title: SchemaDAO.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.Schema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * SchemaDAO
 */
//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
//		RuntimeException.class, IOException.class,Exception.class })
public class SchemaDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(SchemaDAO.class);
    //property constants
    /**
     * The Constant NAME.
     */
    public static final String NAME = "name";

    /**
     * The Constant VERSION.
     */
    public static final String VERSION = "version";

    /**
     * The Constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";

    /**
     * The Constant IS_LOCK.
     */
    public static final String IS_LOCK = "isLock";

    /**
     * The Constant USERID.
     */
    public static final String USER_ID = "userid";

    /**
     * The Constant IS_DELETE.
     */
    public static final String IS_DELETE = "isdelete";
    /**
     * The Constant TYPE.
     */
    public static final String TYPE = "type";


    /* (non-Javadoc)
     * @see org.springframework.dao.support.DaoSupport#initDao()
     */
    protected void initDao() {
        //do nothing
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(Schema transientInstance) throws Exception {
        log.debug("saving Schema instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    /**
     * Delete.
     *
     * @param persistentInstance the persistent instance
     * @throws Exception the exception
     */
    public void delete(Schema persistentInstance) throws Exception {
        log.debug("deleting Schema instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the schema
     */
    public Schema findById(String id) {
        log.debug("getting Schema instance with id: " + id);
        try {
            Schema instance = getHibernateTemplate().get(Schema.class, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     * hibernate3之后，get方法首先在查找session缓存，然后查找二级缓存，再查询数据库；
     * 这里发现缓存和数据库不一样，所以需要把对象变为游离状态，然后再调用get方法就直接去查询数据库了
     *
     * @param schemaId
     * @return
     */
    public Schema findByIdInDB(String schemaId) {
        log.debug("getting Schema instance with id: " + schemaId);
        try {
            //使得对象处于脱管的游离状态
            getSession().clear();
            Schema instance = getHibernateTemplate().get(Schema.class, schemaId);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     * Find by example.
     *
     * @param instance the instance
     * @return the list
     */
    public List findByExample(Schema instance) {
        log.debug("finding Schema instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    /**
     * Find by property.
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list
     */
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Schema instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Schema as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the list
     */
    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    /**
     * Find by name and version.
     *
     * @param name    the name
     * @param version the version
     * @return the list
     * @throws Exception the exception
     */
    public List findByNameAndVersion(Object name, Object version) throws Exception {
        log.debug("finding Schema instance with property: " + NAME
                + ", value: " + name + "  " + VERSION + ", value: " + version + " " + IS_DELETE + ", value: " + new Long(1));
        try {
            String queryString = "from Schema as model where model."
                    + NAME + "= ? and model." + VERSION + "= ? and model." + IS_DELETE + "= ?";
            return getHibernateTemplate().find(queryString, new Object[]{name, version, new Long(1)});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByIslockAndType(Object islock, Object type) throws Exception {
        try {
            String queryString = "from Schema as model where model."
                    + IS_LOCK + "= ? and model." + TYPE + "!= ?";
            return getHibernateTemplate().find(queryString, new Object[]{islock, type});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByIslockAndIsDelete(Object islock, Object isdelete) throws Exception {
        try {
            String queryString = "from Schema as model where model."
                    + IS_LOCK + "= ? and model." + IS_DELETE + "= ?";
            return getHibernateTemplate().find(queryString, new Object[]{islock, isdelete});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByIsDelete(Object isdelete) throws Exception {
        try {
            String queryString = "from Schema as model where model." + IS_DELETE + "= ?";
            return getHibernateTemplate().find(queryString, new Object[]{isdelete});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public int getCountByIslockAndTypeAndSchema(String id) {
        int count = 0;
        try {
            String searchSql = "select count(*) count from cwm_schema where is_lock=1 and type!='1' and id not in('" + id + "')";
            SQLQuery query = this.getSession().createSQLQuery(searchSql);
            List result = query.list();
            if (result.size() != 0) {
                Map map = (Map) result.get(0);
                count = Integer.parseInt(map.get("count").toString());
            }
            return count;
        } catch (RuntimeException re) {
            log.error("getting  Schema searchsql failed", re);
            return count;
        }
    }

    public int getCountByIslockAndSchema(String id) {
        int count = 0;
        try {
            String searchSql = "select count(*) count from cwm_schema where is_lock=1 and id not in('" + id + "')";
            SQLQuery query = this.getSession().createSQLQuery(searchSql);
            List result = query.list();
            if (result.size() != 0) {
                Map map = (Map) result.get(0);
                count = Integer.parseInt(map.get("count").toString());
            }
            return count;
        } catch (RuntimeException re) {
            log.error("getting  Schema searchsql failed", re);
            return count;
        }
    }

    public int getCountByMapTableAndIsvalid(String tableName, String schemaId) {
        int count = 0;
        try {
            String searchSql = "select count(*) count from cwm_tables where map_table  in (select id from cwm_tables where s_name='" + tableName + "' schema_id in('" + schemaId + "')) and is_valid=1";
            SQLQuery query = this.getSession().createSQLQuery(searchSql);
            List result = query.list();
            if (result.size() != 0) {
                Map map = (Map) result.get(0);
                count = Integer.parseInt(map.get("count").toString());
            }
            return count;
        } catch (RuntimeException re) {
            log.error("getting  Schema searchsql failed", re);
            return count;
        }
    }

    public int getCountByMapColumnAndIsvalid(String columnName, String tableName, String schemaId) {
        int count = 0;
        try {
            String searchSql = "select count(*) count from cwm_tables where map_column in(select id from cwm_tab_columns where s_name='" + columnName + "' and table_id in (select id from cwm_tables where s_name='" + tableName + "' and schema_id in('" + schemaId + "'))) and is_valid =1";
            SQLQuery query = this.getSession().createSQLQuery(searchSql);
            List result = query.list();
            if (result.size() != 0) {
                Map map = (Map) result.get(0);
                count = Integer.parseInt(map.get("count").toString());
            }
            return count;
        } catch (RuntimeException re) {
            log.error("getting  Schema searchsql failed", re);
            return count;
        }
    }

    /**
     * Find by version.
     *
     * @param version the version
     * @return the list
     */
    public List findByVersion(Object version) {
        return findByProperty(VERSION, version);
    }

    /**
     * Find by description.
     *
     * @param description the description
     * @return the list
     */
    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find by is lock.
     *
     * @param isLock the is lock
     * @return the list
     */
    public List findByIsLock(Object isLock) {
        return findByProperty(IS_LOCK, isLock);
    }

    /**
     * Find by userid.
     *
     * @param userid the userid
     * @return the list
     */
    public List findByUserid(Object userid) {
        return findByProperty(USER_ID, userid);
    }


    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all Schema instances");
        try {
            String queryString = "from Schema";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

    }

    /**
     * Merge.
     *
     * @param detachedInstance the detached instance
     * @return the schema
     * @throws Exception the exception
     */
    public Schema merge(Schema detachedInstance) throws Exception {
        log.debug("merging Schema instance");
        try {
            Schema result = getHibernateTemplate().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /**
     * Attach dirty.
     *
     * @param instance the instance
     */
    public void attachDirty(Schema instance) {
        log.debug("attaching dirty Schema instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * Attach clean.
     *
     * @param instance the instance
     */
    public void attachClean(Schema instance) {
        log.debug("attaching clean Schema instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * Gets the from application context.
     *
     * @param ctx the ctx
     * @return the from application context
     */
    public static SchemaDAO getFromApplicationContext(ApplicationContext ctx) {
        return (SchemaDAO) ctx.getBean("SchemaDAO");
    }

    public List findShareSchema() throws Exception {
        try {
            String queryString = "from Schema as model where model." + TYPE
                    + "= ? and model." + IS_DELETE + "= ? and model." + IS_LOCK
                    + "!= ?";
            return getHibernateTemplate().find(queryString, new Object[]{"1", new Long(1), new Long(1)});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
}