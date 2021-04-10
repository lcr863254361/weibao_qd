package com.aptx.utils.orm;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class BasicHibernateDao<T> extends BaseHibernateDao {
    private final Class<T> entityClass;

    public BasicHibernateDao() {
        Type type = this.getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        this.entityClass = (Class<T>) types[0];
    }

    protected Criteria createCriteria() {
        return createCriteria(entityClass);
    }

    protected DetachedCriteria createDetachedCriteria() {
        return DetachedCriteria.forClass(entityClass);
    }

    /******************* HQL NamedQuery SQL *******************/
    @Deprecated
    public List<T> listBySql(String sql, Object... values) {
        return listBySql(entityClass, sql, values);
    }

    @Deprecated
    public T getBySql(String sql, Object... values) {
        return getBySql(entityClass, sql, values);
    }

    /******************* Create Update Delete *******************/
    public T get(Serializable id) {
        return get(entityClass, id);
    }

    public void deleteById(Serializable id) {
        deleteById(entityClass, id);
    }

    public List<T> listByExample(T example) {
        return listByExample(entityClass, example);
    }

    public PageResult<T> pageByExample(T example, Integer page, Integer limit) {
        return pageByExample(entityClass, example, page, limit);
    }

    public Long countByCriteria(CriteriaCallback callback) {
        return countByCriteria(entityClass, callback);
    }

    public List<T> listByCriteria(CriteriaCallback callback) {
        return listByCriteria(entityClass, callback);
    }

    public PageResult<T> pageByCriteria(CriteriaCallback callback, Integer page, Integer limit) {
        return pageByCriteria(entityClass, callback, page, limit);
    }

    public <M> M getByCriteria(CriteriaCallback callback) {
        return getByCriteria(entityClass, callback);
    }
}
