package com.orient.sqlengine.api;

import com.orient.businessmodel.bean.IdQueryCondition;

import java.util.List;
import java.util.Map;

/**
 * 业务模型查询句柄
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 17, 2012
 */
public interface IBusinessModelQuery {
    /**
     * 返回业务模型的记录数
     *
     * @return long
     */
    long count();

    /**
     * 返回业务模型的查询列表
     *
     * @return List
     */
    @SuppressWarnings("unchecked")
    List list();

    List list(Boolean clearCustomFilter);

    /**
     * 返回拼接后的sql语句 及 ？对应的过滤条件集合
     *
     * @return
     */
    IdQueryCondition getIdQueryCondition();

    /**
     * 返回业务模型的查询列表,并包装为beanClass类型,目前仅支持String类型的bean属性
     *
     * @param beanClass
     * @param mapUnderscoreToCamelCase 是否将数据库下划线分割sName属性映射为bean的camelCase
     * @param <T>
     * @return
     */
    <T> List<T> list(Class<T> beanClass, boolean mapUnderscoreToCamelCase);

    /**
     * 设置业务模型正序属性
     *
     * @param colName
     * @return IBusinessModelQuery
     */
    IBusinessModelQuery orderAsc(String colName);

    /**
     * 设置业务模型倒序属性
     *
     * @param colName
     * @return IBusinessModelQuery
     */
    IBusinessModelQuery orderDesc(String colName);

    /**
     * 设置业务模型分页属性
     *
     * @param start
     * @param end
     * @return IBusinessModelQuery
     */
    IBusinessModelQuery page(int start, int end);

    /**
     * 设置业务模型默认排序属性
     *
     * @param isAsc
     * @return IBusinessModelQuery
     */
    IBusinessModelQuery setDefaultIdSort(boolean isAsc);

    Map<String, String> findById(String dataId);

}
