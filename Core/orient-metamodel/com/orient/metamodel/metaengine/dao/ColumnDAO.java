/*
 * Title: ColumnDAO.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.metamodel.metaengine.dao;

import com.orient.metamodel.metadomain.Column;
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
 * ColumnDAO
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 180, rollbackFor = {
        RuntimeException.class, IOException.class, Exception.class})
public class ColumnDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(ColumnDAO.class);

    /**
     * The Constant NAME.
     */
    public static final String NAME = "name";

    /**
     * The Constant DISPLAY_NAME.
     */
    public static final String DISPLAY_NAME = "displayName";

    /**
     * The Constant CATEGORY.
     */
    public static final String CATEGORY = "category";

    /**
     * The Constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";

    /**
     * The Constant COLUMN_NAME.
     */
    public static final String COLUMN_NAME = "columnName";

    /**
     * The Constant IS_WHO_SEARCH.
     */
    public static final String IS_WHO_SEARCH = "isWhoSearch";

    /**
     * The Constant IS_FOR_SEARCH.
     */
    public static final String IS_FOR_SEARCH = "isForSearch";

    /**
     * The Constant IS_INDEX.
     */
    public static final String IS_INDEX = "isIndex";

    /**
     * The Constant OPERATE_SIGN.
     */
    public static final String OPERATE_SIGN = "operateSign";

    /**
     * The Constant PURPOSE.
     */
    public static final String PURPOSE = "purpose";

    /**
     * The Constant CASESENSITIVE.
     */
    public static final String CASESENSITIVE = "casesensitive";

    /**
     * The Constant DEFALUT_VALUE.
     */
    public static final String DEFALUT_VALUE = "defalutValue";

    /**
     * The Constant IS_NULLABLE.
     */
    public static final String IS_NULLABLE = "isNullable";

    /**
     * The Constant IS_ONLY.
     */
    public static final String IS_ONLY = "isOnly";

    /**
     * The Constant IS_PK.
     */
    public static final String IS_PK = "isPK";

    /**
     * The Constant IS_AUTO_INCREMENT.
     */
    public static final String IS_AUTO_INCREMENT = "isAutoIncrement";

    /**
     * The Constant RESTRICTION_ID.
     */
    public static final String RESTRICTION_ID = "restrictionID";

    /**
     * The Constant TYPE.
     */
    public static final String TYPE = "type";

    /**
     * The Constant SEQUENCE_NAME.
     */
    public static final String SEQUENCE_NAME = "sequenceName";

    /**
     * The Constant MAX_LENGTH.
     */
    public static final String MAX_LENGTH = "maxLength";

    /**
     * The Constant MIN_LENGTH.
     */
    public static final String MIN_LENGTH = "minLength";

    /**
     * The Constant IS_SHOW.
     */
    public static final String IS_SHOW = "isShow";

    /**
     * The Constant IS_WARP.
     */
    public static final String IS_WARP = "isWarp";

    /**
     * The Constant PROPERTY_PARAGRAPH.
     */
    public static final String PROPERTY_PARAGRAPH = "propertyParagraph";

    /**
     * The Constant PROPERY_CATEGORY.
     */
    public static final String PROPERY_CATEGORY = "properyCategory";

    /**
     * The Constant LINAGE.
     */
    public static final String LINAGE = "linage";

    /**
     * The Constant IS_VALID.
     */
    public static final String IS_VALID = "isValid";

    /**
     * The Constant IS_MUTI_UK.
     */
    public static final String IS_MUTI_UK = "isMutiUK";

    /**
     * The Constant IS_USED_PAI_XU.
     */
    public static final String IS_USED_PAI_XU = "isUsedPaiXu";

    /**
     * The Constant VIEW_ID.
     */
    public static final String VIEW_ID = "view";

    protected void initDao() {
        //do nothing
    }

    public List findColumnsByIsvalidAndCategory(Object isvalid, Object category) throws Exception {
        try {
            String queryString = "from Column as model where model."
                    + IS_VALID + "= ? and model." + CATEGORY + "!= ?";
            return getHibernateTemplate().find(queryString, new Object[]{isvalid, category});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findColumnsByIsvalidAndId(Object isvalid, Object id) throws Exception {
        try {
            String queryString = "from Column as model where model."
                    + IS_VALID + "= ? and model.id in (?)";
            return getHibernateTemplate().find(queryString, new Object[]{isvalid, id});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findColumnsByIsvalidAndSequenceName(Object isvalid, Object sequenceName) throws Exception {
        try {
            String queryString = "from Column as model where model."
                    + IS_VALID + "= ? and model." + SEQUENCE_NAME + "= ?";
            return getHibernateTemplate().find(queryString, new Object[]{isvalid, sequenceName});
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Save.
     *
     * @param transientInstance the transient instance
     * @throws Exception the exception
     */
    public void save(Column transientInstance) throws Exception {
        log.debug("saving Column instance");
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
     */
    public void delete(Column persistentInstance) {
        log.debug("deleting Column instance");
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
     * @return the column
     */
    public Column findById(java.lang.String id) {
        log.debug("getting Column instance with id: " + id);
        try {
            Column instance = (Column) getHibernateTemplate()
                    .get("com.orient.metamodel.metadomain.Column", id);
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
    public List findByExample(Column instance) {
        log.debug("finding Column instance by example");
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
        log.debug("finding Column instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Column as model where model."
                    + propertyName + "= ?";
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
     * Find by category.
     *
     * @param category the category
     * @return the list
     */
    public List findByCategory(Object category) {
        return findByProperty(CATEGORY, category);
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
     * Find by column name.
     *
     * @param columnName the column name
     * @return the list
     */
    public List findByColumnName(Object columnName) {
        return findByProperty(COLUMN_NAME, columnName);
    }

    /**
     * Find by is who search.
     *
     * @param isWhoSearch the is who search
     * @return the list
     */
    public List findByIsWhoSearch(Object isWhoSearch) {
        return findByProperty(IS_WHO_SEARCH, isWhoSearch);
    }

    /**
     * Find by is for search.
     *
     * @param isForSearch the is for search
     * @return the list
     */
    public List findByIsForSearch(Object isForSearch) {
        return findByProperty(IS_FOR_SEARCH, isForSearch);
    }

    /**
     * Find by is index.
     *
     * @param isIndex the is index
     * @return the list
     */
    public List findByIsIndex(Object isIndex) {
        return findByProperty(IS_INDEX, isIndex);
    }

    /**
     * Find by operate sign.
     *
     * @param operateSign the operate sign
     * @return the list
     */
    public List findByOperateSign(Object operateSign) {
        return findByProperty(OPERATE_SIGN, operateSign);
    }

    /**
     * Find by purpose.
     *
     * @param purpose the purpose
     * @return the list
     */
    public List findByPurpose(Object purpose) {
        return findByProperty(PURPOSE, purpose);
    }

    /**
     * Find by casesensitive.
     *
     * @param casesensitive the casesensitive
     * @return the list
     */
    public List findByCasesensitive(Object casesensitive) {
        return findByProperty(CASESENSITIVE, casesensitive);
    }

    /**
     * Find by defalut value.
     *
     * @param defalutValue the defalut value
     * @return the list
     */
    public List findByDefalutValue(Object defalutValue) {
        return findByProperty(DEFALUT_VALUE, defalutValue);
    }

    /**
     * Find by is nullable.
     *
     * @param isNullable the is nullable
     * @return the list
     */
    public List findByIsNullable(Object isNullable) {
        return findByProperty(IS_NULLABLE, isNullable);
    }

    /**
     * Find by is only.
     *
     * @param isOnly the is only
     * @return the list
     */
    public List findByIsOnly(Object isOnly) {
        return findByProperty(IS_ONLY, isOnly);
    }

    /**
     * Find by is pk.
     *
     * @param isPK the is pk
     * @return the list
     */
    public List findByIsPK(Object isPK) {
        return findByProperty(IS_PK, isPK);
    }

    /**
     * Find by is auto increment.
     *
     * @param isAutoIncrement the is auto increment
     * @return the list
     */
    public List findByIsAutoIncrement(Object isAutoIncrement) {
        return findByProperty(IS_AUTO_INCREMENT, isAutoIncrement);
    }

    /**
     * Find by restriction id.
     *
     * @param restrictionID the restriction id
     * @return the list
     */
    public List findByRestrictionID(Object restrictionID) {
        return findByProperty(RESTRICTION_ID, restrictionID);
    }

    /**
     * Find by type.
     *
     * @param type the type
     * @return the list
     */
    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    /**
     * Find by sequence name.
     *
     * @param sequenceName the sequence name
     * @return the list
     */
    public List findBySequenceName(Object sequenceName) {
        return findByProperty(SEQUENCE_NAME, sequenceName);
    }

    /**
     * Find by max length.
     *
     * @param maxLength the max length
     * @return the list
     */
    public List findByMaxLength(Object maxLength) {
        return findByProperty(MAX_LENGTH, maxLength);
    }

    /**
     * Find by min length.
     *
     * @param minLength the min length
     * @return the list
     */
    public List findByMinLength(Object minLength) {
        return findByProperty(MIN_LENGTH, minLength);
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
     * Find by is warp.
     *
     * @param isWarp the is warp
     * @return the list
     */
    public List findByIsWarp(Object isWarp) {
        return findByProperty(IS_WARP, isWarp);
    }

    /**
     * Find by property paragraph.
     *
     * @param propertyParagraph the property paragraph
     * @return the list
     */
    public List findByPropertyParagraph(Object propertyParagraph) {
        return findByProperty(PROPERTY_PARAGRAPH, propertyParagraph);
    }

    /**
     * Find by propery category.
     *
     * @param properyCategory the propery category
     * @return the list
     */
    public List findByProperyCategory(Object properyCategory) {
        return findByProperty(PROPERY_CATEGORY, properyCategory);
    }

    /**
     * Find by linage.
     *
     * @param linage the linage
     * @return the list
     */
    public List findByLinage(Object linage) {
        return findByProperty(LINAGE, linage);
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
     * Find by is muti uk.
     *
     * @param isMutiUK the is muti uk
     * @return the list
     */
    public List findByIsMutiUK(Object isMutiUK) {
        return findByProperty(IS_MUTI_UK, isMutiUK);
    }

    /**
     * Find by is used pai xu.
     *
     * @param isUsedPaiXu the is used pai xu
     * @return the list
     */
    public List findByIsUsedPaiXu(Object isUsedPaiXu) {
        return findByProperty(IS_USED_PAI_XU, isUsedPaiXu);
    }

    /**
     * Find by view id.
     *
     * @param viewId the view id
     * @return the list
     */
    public List findByViewId(Object viewId) {
        return findByProperty(VIEW_ID, viewId);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    public List findAll() {
        log.debug("finding all Column instances");
        try {
            String queryString = "from Column";
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
     * @return the column
     * @throws Exception the exception
     */
    public Column merge(Column detachedInstance) throws Exception {
        log.debug("merging Column instance");
        try {
            Column result = (Column) getHibernateTemplate()
                    .merge(detachedInstance);
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
    public void attachDirty(Column instance) {
        log.debug("attaching dirty Column instance");
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
    public void attachClean(Column instance) {
        log.debug("attaching clean Column instance");
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
    public static ColumnDAO getFromApplicationContext(ApplicationContext ctx) {
        return (ColumnDAO) ctx.getBean("ColumnDAO");
    }

    public List getSqlResultByTableId(String tableid) {
        try {
            StringBuilder searchsql = new StringBuilder();
            searchsql.append("select CTC.id,CTC.type,CTC.display_name,category, "
                    + "CTC.S_COLUMN_NAME from cwm_tab_columns CTC,cwm_table_column TC where TC.table_id='");
            searchsql.append(tableid);
            searchsql.append("' and TC.type=3 AND TC.COLUMN_ID=CTC.ID "
                    //wubing 修改 tbom 普通表中动态子节点 出现 关联属性字段
                    //+ "and ctc.category=1 order by TC.order_sign asc");
                    + "order by TC.order_sign asc");
            SQLQuery query = this.getSession().createSQLQuery(searchsql.toString());
            List result = query.list();
            return result;
        } catch (RuntimeException re) {
            log.error("getting  Column searchsql failed", re);
            return null;
        }
    }

    public List getSqlResultByViewId(String viewid) {
        try {
            StringBuilder searchsql = new StringBuilder();
            searchsql.append("SELECT RETURN_COLUMN_ID,CTC.DISPLAY_NAME,CTC.TYPE,"
                    + "CTC.CATEGORY,CTC.S_COLUMN_NAME FROM CWM_VIEW_RETURN_COLUMN CVRC,CWM_TAB_COLUMNS CTC "
                    + "WHERE VIEW_ID='" + viewid
                    + "' AND RETURN_COLUMN_ID=CTC.ID ORDER BY CVRC.ORDER_SIGN ASC");
            SQLQuery query = this.getSession().createSQLQuery(searchsql.toString());
            List result = query.list();
            return result;
        } catch (RuntimeException re) {
            log.error("getting  Column searchsql failed", re);
            return null;
        }
    }

}