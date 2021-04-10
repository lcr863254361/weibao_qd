/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.sysmodel.dao.impl;

import com.orient.sysmodel.dao.IBaseDao;
import org.hibernate.*;

/**
 * 数据库基础操作类的父类
 * 实现IBaseDao接口
 * @author ZENGCHAO
 *
 */
public abstract class BaseHibernateDao implements IBaseDao {
	public abstract Session getSession();
	public abstract Query getQuery(String hql);
	public abstract SQLQuery getSQLQuery(String sql);
	public abstract Criteria getCriteria(Class<?> clazz);
	public abstract SessionFactory getSessionFactory();
}
