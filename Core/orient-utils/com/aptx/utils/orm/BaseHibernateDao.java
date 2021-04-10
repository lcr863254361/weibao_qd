package com.aptx.utils.orm;

import com.aptx.utils.CollectionUtil;
import com.aptx.utils.bean.BaseBean;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class BaseHibernateDao extends BaseBean {
    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Query createQuery(String hql) {
        return currentSession().createQuery(hql);
    }

    @Deprecated
    public SQLQuery createSQLQuery(String sql) {
        return currentSession().createSQLQuery(sql);
    }

    protected <M> Criteria createCriteria(Class<M> clazz) {
        return currentSession().createCriteria(clazz);
    }

    /******************* Create Update Delete *******************/
    public Serializable save(Object entity) {
        return currentSession().save(entity);
    }

    public void update(Object entity) {
        currentSession().update(entity);
    }

    public void saveOrUpdate(Object entity) {
        currentSession().saveOrUpdate(entity);
    }

    public void persist(Object entity) {
        currentSession().persist(entity);
    }

    public <M> M merge(M entity) {
        return (M) currentSession().merge(entity);
    }

    public void delete(Object entity) {
        currentSession().delete(entity);
    }

    public <M> void deleteAll(Collection<M> entities) {
        if(CollectionUtil.isNullOrEmpty(entities)) {
            return;
        }
        for(M entity : entities) {
            delete(entity);
        }
    }

    public <M> void deleteById(Class<M> clazz, Serializable id) {
        M entity = get(clazz, id);
        delete(entity);
    }

    public void flush() {
        currentSession().flush();
    }

    public void refresh(Object entity) {
        currentSession().refresh(entity);
    }

    /******************* HQL NamedQuery SQL *******************/
    public int executeHql(String hql, Object... values) {
        Query query = createQuery(hql);
        setParameters(query, values);
        return query.executeUpdate();
    }

    public <M> List<M> listByHql(String hql, Object... values) {
        return listByHql(hql, null, null, values);
    }

    public <M> List<M> listByHql(String hql, Integer page, Integer limit, Object... values) {
        Query query = createQuery(hql);
        setParameters(query, values);
        setPageQuery(query, page, limit);
        return query.list();
    }

    public Long countByHql(String hql, Object... values) {
        String countHql = "select count(*) from ( " + hql + ")";
        Query query = createQuery(countHql);
        setParameters(query, values);
        return (Long) query.uniqueResult();
    }

    public <M> PageResult<M> pageByHql(String hql, Integer page, Integer limit, Object... values) {
        List<M> results = listByHql(hql, page, limit, values);
        Long total = countByHql(hql, values);
        return new PageResult<>(results, total);
    }

    public <M> M getByHql(String hql, Object... values) {
        Query query = createQuery(hql);
        setParameters(query, values);
        return (M) query.uniqueResult();
    }

    public <M> List<M> listByNamedQuery(String name, Integer page, Integer limit, Object... values) {
        Query query = currentSession().getNamedQuery(name);
        setParameters(query, values);
        setPageQuery(query, page, limit);
        return query.list();
    }

    @Deprecated
    public int executeSql(String sql, Object... values) {
        Query query = createSQLQuery(sql);
        setParameters(query, values);
        return query.executeUpdate();
    }

    @Deprecated
    public <M> List<M> listBySql(Class<M> clazz, String sql, Object... values) {
        SQLQuery query = createSQLQuery(sql);
        if(clazz.getAnnotation(Entity.class) != null) {
            query.addEntity(clazz);
        }
        else {
            query.setResultTransformer(Transformers.aliasToBean(clazz));
        }
        setParameters(query, values);
        return query.list();
    }

    @Deprecated
    public <M> M getBySql(Class<M> clazz, String sql, Object... values) {
        SQLQuery query = createSQLQuery(sql);
        if(clazz.getAnnotation(Entity.class) != null) {
            query.addEntity(clazz);
        }
        else {
            query.setResultTransformer(Transformers.aliasToBean(clazz));
        }
        setParameters(query, values);
        return (M) query.uniqueResult();
    }

    /******************* Criteria *******************/
    public <M> M get(Class<M> clazz, Serializable id) {
        return (M) currentSession().get(clazz, id);
    }

    public <M> M load(Class<M> clazz, Serializable id) {
        return (M) currentSession().load(clazz, id);
    }

    public <M> List<M> listByExample(Class<M> clazz, M example) {
        Criteria criteria = createCriteria(clazz);
        criteria.add(Example.create(example).enableLike(MatchMode.ANYWHERE).ignoreCase().excludeZeroes());
        return criteria.list();
    }

    public <M> PageResult<M> pageByExample(Class<M> clazz, M example, Integer page, Integer limit) {
        Criteria criteria = createCriteria(clazz);
        criteria.add(Example.create(example).enableLike(MatchMode.ANYWHERE).ignoreCase().excludeZeroes());
        return pageByCriteria(criteria, page, limit);
    }

    public Long countByCriteria(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public <M> List<M> listByCriteria(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
        return criteria.list();
    }

    public <M> PageResult<M> pageByCriteria(DetachedCriteria detachedCriteria, Integer page, Integer limit) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
        return pageByCriteria(criteria, page, limit);
    }

    public <M> M getByCriteria(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
        return (M) criteria.uniqueResult();
    }

    //**************************************
    public <M> Long countByCriteria(Class<M> clazz, CriteriaCallback callback) {
        Criteria criteria = createCriteria(clazz);
        callback.setCriteria(criteria);
        callback.buildCriteria();
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public <M> List<M> listByCriteria(Class<M> clazz, CriteriaCallback callback) {
        Criteria criteria = createCriteria(clazz);
        callback.setCriteria(criteria);
        callback.buildCriteria();
        return criteria.list();
    }

    public <M> PageResult<M> pageByCriteria(Class<M> clazz, CriteriaCallback callback, Integer page, Integer limit) {
        Criteria criteria = createCriteria(clazz);
        callback.setCriteria(criteria);
        callback.buildCriteria();
        return pageByCriteria(criteria, page, limit);
    }

    public <M, N> N getByCriteria(Class<M> clazz, CriteriaCallback callback) {
        Criteria criteria = createCriteria(clazz);
        callback.setCriteria(criteria);
        callback.buildCriteria();
        return (N) criteria.uniqueResult();
    }

    /******************* Commons *******************/
    public <M> PageResult<M> pageByCriteria(Criteria criteria, Integer page, Integer limit) {
        Long total = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        if(page!=null && limit!=null) {
            criteria.setFirstResult((page-1) * limit);
            criteria.setMaxResults(limit);
        }
        List<M> results = criteria.list();
        return new PageResult<>(results, total);
    }

    public void setParameters(Query query, Object... values) {
        if(values != null) {
            for(int i=0; i<values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
    }

    public void setPageQuery(Query query, Integer page, Integer limit) {
        if(page!=null && limit!=null) {
            query.setFirstResult((page-1) * limit);
            query.setMaxResults(limit);
        }
    }
}
