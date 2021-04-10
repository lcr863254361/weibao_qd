/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.sysmodel.service;




import com.orient.sysmodel.dao.IBaseDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/21 0021.
 */
public interface IBaseService<M> {
	IBaseDao getBaseDao();

	BaseService.DBType getDBType();

	M getById(Serializable id);

	List<M> getByIds(Serializable[] ids);

	M getByName(String name);

	M getByProperties(String propName, Object propValue);

	M get(Criterion... criterions);

	M getByHql(String hql);

	Object getField(String field);

	List<M> listByPage(PageBean pageBean);

	List<M> list();

	List<M> list(Criterion... criterions);

	List<M> list(Order... orders);

	List<M> list(Criterion criterion, Order order);

	List<M> list(Criterion[] criterions, Order... orders);

	List<M> list(String hql);

	List<M> list(String hql, Integer currentPage, Integer pageSize);

	List<M> listForEntity(String hql);

	List<M> listForEntity(String hql, Object... params);

	void save(M m);

	void update(M m);

	void merge(M m);

	void delete(Serializable id);

	void delete(Serializable[] ids);

	void delete(M m);

	int count();

	int count(Criterion... criterions);

	int executeHQL(String hql);

	int executeSQL(String sql);

	Object queryHQL(String hql);

	int queryForInt(String sql);

	long queryForLong(String sql);

	List<Map<String, Object>> queryForList(String sql);

	Integer count(M m,List<Criterion> criterions);

	List<M> listByPage(M filter, Integer page, Integer limit, Criterion... criterions);

	List<M> listBeansByExample(M filter);

}
