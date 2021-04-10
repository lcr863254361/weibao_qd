/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.sysmodel.service;

import com.orient.sysmodel.dao.IBaseDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository("baseService")
public class BaseService<M> implements IBaseService<M> {

    public static enum DBType {
        Oracle, Mysql, SqlServer, Sybase, DB2
    }

    @Autowired
    protected IBaseDao baseDao;
    private final Class<M> entityClass;

    /**
     * 获取BaseDao
     *
     * @return
     */
    @Override
    public IBaseDao getBaseDao() {
        return this.baseDao;
    }

    @Override
    public BaseService.DBType getDBType() {
        return BaseService.DBType.Oracle;
    }

    /**
     * 获取<M>的Class对象
     */
    @SuppressWarnings("unchecked")
    public BaseService() {
        Class<?> c = this.getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<M>) p[0];
        } else {
            this.entityClass = null;
        }
    }

    /**
     * 根据Id获取<M>
     *
     * @param id
     * @return
     */
    @Override
    public M getById(Serializable id) {
        return baseDao.get(entityClass, id);
    }

    /**
     * 根据多个ID获取多个<M>
     *
     * @param ids
     * @return
     */
    @Override
    public List<M> getByIds(Serializable[] ids) {
        return null != ids && ids.length > 0 ? baseDao.list(entityClass, Restrictions.in("id", ids)) : new ArrayList<>();
    }

    /**
     * 根据Name获取<M>
     *
     * @param name
     * @return
     */
    @Override
    public M getByName(String name) {
        return baseDao.getBy(entityClass, Restrictions.eq("name", name));
    }

    /**
     * 根据属性名获取<M>
     *
     * @param propName
     * @param propValue
     * @return
     */
    @Override
    public M getByProperties(String propName, Object propValue) {
        return baseDao.getBy(entityClass, Restrictions.eq(propName, propValue));
    }

    /**
     * 根据多个的查询条件查询单个对象
     *
     * @param criterions
     * @return
     */
    @Override
    public M get(Criterion... criterions) {
        return baseDao.getBy(entityClass, criterions);
    }

    @Override
    public M getByHql(String hql) {
        return baseDao.getByHQL(entityClass, hql);
    }

    /**
     * 获取单个字段的值
     *
     * @param field
     * @return
     */
    @Override
    public Object getField(String field) {
        Object value = null;
        return value;
    }

    /**
     * 分页获取<M>
     *
     * @param pageBean
     * @return
     */
    @Override
    public List<M> listByPage(PageBean pageBean) {
        M example = (M) pageBean.getExampleFilter();
        if (null == example && null != this.entityClass) {
            try {
                example = entityClass.newInstance();
                pageBean.setExampleFilter(example);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        pageBean.setTotalCount(baseDao.count(entityClass, example, pageBean.getCriterions()));
        return baseDao.list(entityClass, pageBean);
    }

    /**
     * 获取全部数据
     *
     * @return
     */
    @Override
    public List<M> list() {
        return baseDao.list(entityClass);
    }

    /**
     * 根据查询条件获取集合
     *
     * @param criterions
     * @return
     */
    @Override
    public List<M> list(Criterion... criterions) {
        return baseDao.list(entityClass, criterions);
    }

    /**
     * 按照排序规则获取集合
     *
     * @param
     * @return
     */
    @Override
    public List<M> list(Order... orders) {
        return baseDao.list(entityClass, orders);
    }

    /**
     * 获取集合
     *
     * @param criterion
     * @param order
     * @return
     */
    @Override
    public List<M> list(Criterion criterion, Order order) {
        return baseDao.list(entityClass, criterion, order);
    }

    /**
     * @param criterions
     * @param orders
     * @return
     */
    @Override
    public List<M> list(Criterion[] criterions, Order... orders) {
        return baseDao.list(entityClass, criterions, orders);
    }

    /**
     * 根据HQL查询
     *
     * @param hql
     * @return
     */
    @Override
    public List<M> list(String hql) {
        return baseDao.list(entityClass, hql);
    }

    /**
     * 根据HQL查询
     *
     * @param hql
     * @return
     */
    @Override
    public List<M> list(String hql, Integer currentPage, Integer pageSize) {
        return baseDao.list(entityClass, hql, currentPage, pageSize);
    }

    /**
     * 自定实体查询
     *
     * @param hql
     * @return
     */
    @Override
    public List<M> listForEntity(String hql) {
        return this.baseDao.listForEntity(this.entityClass, hql);
    }

    /**
     * 自定实体查询
     *
     * @param hql
     * @return
     */
    @Override
    public List<M> listForEntity(String hql, Object... params) {
        return this.baseDao.listForEntity(this.entityClass, hql, params);
    }

    /**
     * 保存<M>
     *
     * @param m
     */
    @Override
    public void save(M m) {
        baseDao.save(m);
    }

    /**
     * 更新<M>
     *
     * @param m
     */
    @Override
    public void update(M m) {
        baseDao.update(m);
    }

    /**
     * 更新<M>
     *
     * @param m
     */
    @Override
    public void merge(M m) {
        baseDao.merge(m);
    }

    /**
     * 根据ID删除<M>
     *
     * @param id
     */
    @Override
    public void delete(Serializable id) {
        baseDao.delete(entityClass, id);
    }

    /**
     * 根据多个id删除
     *
     * @param ids
     */
    @Override
    public void delete(Serializable[] ids) {
        StringBuffer strIds = new StringBuffer();
        for (Serializable id : ids)
            strIds.append("," + id);
        String hql = "delete " + entityClass.getSimpleName() + " where id in(" + strIds.substring(1) + ")";
        baseDao.execteBulk(hql, null);
    }

    /**
     * 删除<M>
     *
     * @param m
     */
    @Override
    public void delete(M m) {
        baseDao.delete(m);
    }

    /**
     * 获取所有数量
     */
    @Override
    public int count() {
        return baseDao.count(entityClass);
    }

    /**
     * 获取指定条件的集合数量
     *
     * @param criterions
     */
    @Override
    public int count(Criterion... criterions) {
        return baseDao.count(entityClass, criterions);
    }

    /**
     * 执行HQL语句
     *
     * @param hql
     * @return
     */
    @Override
    public int executeHQL(String hql) {
        return baseDao.getQuery(hql).executeUpdate();
    }

    /**
     * 执行SQL语句
     *
     * @param sql
     * @return
     */
    @Override
    public int executeSQL(String sql) {
        return baseDao.getSQLQuery(sql).executeUpdate();
    }

    /**
     * 使用HQL语句查询单个值
     *
     * @param hql
     * @return
     */
    @Override
    public Object queryHQL(String hql) {
        return baseDao.getQuery(hql).uniqueResult();
    }

    /**
     * 使用SQL查询单个int值
     *
     * @param sql
     * @return
     */
    @Override
    public int queryForInt(String sql) {
        return baseDao.queryForInt(sql);
    }

    /**
     * 使用sql查询单个Long值
     *
     * @param sql
     * @return
     */
    @Override
    public long queryForLong(String sql) {
        return baseDao.queryForLong(sql);
    }

    /**
     * 使用SQL查询多列值
     *
     * @param sql
     * @return
     */
    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        return baseDao.queryForList(sql);
    }

    @Override
    public Integer count(M m, List<Criterion> criterions) {
        return baseDao.count(entityClass, m, criterions);
    }

    @Override
    public List<M> listByPage(M filter, Integer page, Integer limit, Criterion... criterions) {
        return baseDao.listByPage(entityClass, filter, page, limit, criterions);
    }

    @Override
    public List<M> listBeansByExample(M filter) {
        return baseDao.listBeansByExample(filter);
    }
}