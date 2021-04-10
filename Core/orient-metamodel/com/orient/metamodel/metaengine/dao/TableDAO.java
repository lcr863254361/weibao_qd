package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * TableDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class TableDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(TableDAO.class);

    /**
     * The Constant NAME.
     */
    public static final String NAME = "name";

    /**
     * The Constant DISPLAY_NAME.
     */
    public static final String DISPLAY_NAME = "displayName";

    /**
     * The Constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "tableName";

    /**
     * The Constant PID.
     */
    public static final String PID = "pid";

    /**
     * The Constant PAIXU_FX.
     */
    public static final String PAIXU_FX = "paiXu";

    /**
     * The Constant SCHEMA_ID.
     */
    public static final String SCHEMA_ID = "schema";

    /**
     * The Constant IS_CONNECT_TABLE.
     */
    public static final String IS_CONNECT_TABLE = "isConnectTable";

    /**
     * The Constant IS_SHOW.
     */
    public static final String IS_SHOW = "isShow";

    /**
     * The Constant _DETAIL_TEXT.
     */
    public static final String DETAIL_TEXT = "DetailText";

    /**
     * The Constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";

    /**
     * The Constant BIG_IMAGE.
     */
    public static final String BIG_IMAGE = "bigImage";

    /**
     * The Constant NOR_IMAGE.
     */
    public static final String NOR_IMAGE = "norImage";

    /**
     * The Constant SMA_IMAGE.
     */
    public static final String SMA_IMAGE = "smaImage";

    /**
     * The Constant CATEGORY.
     */
    public static final String CATEGORY = "category";

    /**
     * The Constant IS_VALID.
     */
    public static final String IS_VALID = "isValid";

    /**
     * The Constant MAP_TABLE.
     */
    public static final String MAP_TABLE = "mapTable";


    public List queryList(String tableId) {
        try {
            String searchSql = "SELECT ID,S_NAME,DISPLAY_NAME,S_TABLE_NAME FROM CWM_TABLES START WITH ID in ('" + tableId + "') CONNECT BY PRIOR ID = PID AND IS_VALID=1";
            SQLQuery query = getSession().createSQLQuery(searchSql);
            List result = query.list();
            return result;
        } catch (RuntimeException re) {
            log.error("getting  User searchsql failed", re);
            return null;
        }
    }

    public int getCount(String cName, String tname) {
        int count = 0;
        try {
            String searchSql = "SELECT COUNT(" + cName + ") FROM " + tname;
            SQLQuery query = getSession().createSQLQuery(searchSql);
            List result = query.list();
            if (result.size() != 0) {
                count = Integer.parseInt(result.get(0).toString());
            }
            return count;
        } catch (RuntimeException re) {
            return count;
        }
    }

    /**
     * 获取文件列表
     *
     * @param schemaId
     * @param isValid
     * @return
     */
    public List findBySchemaidAndIsValidAndMapTable(Object schemaId, Object isValid) {
        try {
            String queryString = "from Table as model where model." + IS_VALID + "= ? and model." + SCHEMA_ID + "= ?  and model." + MAP_TABLE + " is null";
            return getHibernateTemplate().find(queryString, new Object[]{isValid, schemaId});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySchemaidAndSname(Object schemaId, Object sname) {
        try {
            String queryString = "from Table as model where model." + NAME + "= ? and model." + SCHEMA_ID + "= ?";
            return getHibernateTemplate().find(queryString, new Object[]{sname, schemaId});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    protected void initDao() {
        //do nothing
    }

    /**
     * Save
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(Table transientInstance) throws Exception {
        log.debug("saving Table instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    /**
     * Delete
     *
     * @param persistentInstance the persistent instance
     * @throws Exception the exception
     */
    public void delete(Table persistentInstance) throws Exception {
        log.debug("deleting Table instance");
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
     * @return the table
     */
    public Table findById(java.lang.String id) {
        log.debug("getting Table instance with id: " + id);
        try {
            return (Table) getHibernateTemplate().get("com.orient.metamodel.metadomain.Table", id);
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
    public List findByExample(Table instance) {
        log.debug("finding Table instance by example");
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
        log.debug("finding Table instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Table as model where model." + propertyName + "= ?";
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
     * Find by display name.
     *
     * @param displayName the display name
     * @return the list
     */
    public List findByDisplayName(Object displayName) {
        return findByProperty(DISPLAY_NAME, displayName);
    }

    /**
     * Find by schema id.
     *
     * @param schemaId the schema id
     * @return the list
     */
    public List findBySchemaId(Object schemaId) {
        return findByProperty(SCHEMA_ID, schemaId);
    }

    /**
     * Find by table name.
     *
     * @param tableName the table name
     * @return the list
     */
    public List findByTableName(Object tableName) {
        return findByProperty(TABLE_NAME, tableName);
    }

//	public List findByPid(Object pid
//	) {
//		return findByProperty(PID, pid
//		);
//	}

//	public List findByPaixuFX(Object paixuFX
//	) {
//		return findByProperty(PAIXU_FX, paixuFX
//		);
//	}

    /**
     * Find by is connect table.
     *
     * @param isConnectTable the is connect table
     * @return the list
     */
    public List findByIsConnectTable(Object isConnectTable) {
        return findByProperty(IS_CONNECT_TABLE, isConnectTable);
    }

    /**
     * Find by is show.
     *
     * @param isShow the is show
     * @return the list
     */
    public List findByIsShow(Object isShow) {
        return findByProperty(IS_SHOW, isShow);
    }

    /**
     * Find by detail text.
     *
     * @param detailText the detail text
     * @return the list
     */
    public List findByDetailText(Object detailText) {
        return findByProperty(DETAIL_TEXT, detailText);
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
     * Find by big image.
     *
     * @param bigImage the big image
     * @return the list
     */
    public List findByBigImage(Object bigImage) {
        return findByProperty(BIG_IMAGE, bigImage);
    }

    /**
     * Find by nor image.
     *
     * @param norImage the nor image
     * @return the list
     */
    public List findByNorImage(Object norImage) {
        return findByProperty(NOR_IMAGE, norImage);
    }

    /**
     * Find by sma image.
     *
     * @param smaImage the sma image
     * @return the list
     */
    public List findBySmaImage(Object smaImage) {
        return findByProperty(SMA_IMAGE, smaImage);
    }

    /**
     * Find by category.
     *
     * @param category the category
     * @return the list
     */
    public List findByCategory(Object category) {
        return findByProperty(CATEGORY, category);
    }

    /**
     * Find by is valid.
     *
     * @param isValid the is valid
     * @return the list
     */
    public List findByIsValid(Object isValid) {
        return findByProperty(IS_VALID, isValid);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all Table instances");
        try {
            String queryString = "from Table";
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
     * @return the table
     * @throws Exception the exception
     */
    public Table merge(Table detachedInstance) throws Exception {
        log.debug("merging Table instance");
        try {
            Table result = getHibernateTemplate().merge(detachedInstance);
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
    public void attachDirty(Table instance) {
        log.debug("attaching dirty Table instance");
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
    public void attachClean(Table instance) {
        log.debug("attaching clean Table instance");
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
    public static TableDAO getFromApplicationContext(ApplicationContext ctx) {
        return (TableDAO) ctx.getBean("TableDAO");
    }

    public List getSqlResultByTableName(String tableName) {
        try {
            String searchSql = "SELECT * FROM USER_TABLES WHERE TABLE_NAME='" + tableName.toUpperCase() + "'";
            SQLQuery query = this.getSession().createSQLQuery(searchSql);
            List result = query.list();
            return result;
        } catch (RuntimeException re) {
            return null;
        }
    }

    public int getCountByTableName(String tablename) {
        int count = 0;
        try {
            String searchSql = "SELECT COUNT(*) FROM " + tablename;
            SQLQuery query = this.getSession().createSQLQuery(searchSql);
            List result = query.list();
            if (result.size() != 0) {
                count = Integer.parseInt(result.get(0).toString());
            }
            return count;
        } catch (RuntimeException re) {
            return count;
        }
    }

}