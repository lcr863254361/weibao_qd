package com.orient.sqlengine.api;

import com.orient.businessmodel.annotation.BindModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.businessmodel.service.impl.QueryOrder;

import java.util.List;

/**
 * the business model service which has added a supplement of type mapping
 *
 * @author Seraph
 *         2016-07-21 下午4:30
 */
public interface ITypeMappingBmService {

    /**
     * insert into the bm annotated by {@link BindModel}
     * @param bean
     * @param <T>
     * @return
     */
    <T> String insert(T bean);

    /**
     * update the bm annotated by {@link BindModel}
     * @param bean
     * @param <T>
     * @return
     */
    <T> boolean update(T bean);

    /**
     * get from the bm annotated by {@link BindModel}
     * @param beanClass
     * @param filters the filterName of filter is the bean's property
     * @param <T>
     * @return
     */
    <T> List<T> get(Class<T> beanClass, CustomerFilter... filters);

    /**
     * get from the bm annotated by {@link BindModel} with order column
     * @param beanClass
     * @param order
     * @param filters the filterName of filter is the bean's property
     * @param <T>
     * @return
     */
    <T> List<T> get(Class<T> beanClass, QueryOrder order, CustomerFilter... filters);

    /**
     * get from the bm annotated by {@link BindModel} via id
     * @param beanClass
     * @param <T>
     * @return
     */
    <T> T getById(Class<T> beanClass, String dataId);

    /**
     * delete by ids
     * @param beanClass
     * @param dataIds
     * @param cascade
     * @param <T>
     * @return
     */
    <T> void delete(Class<T> beanClass, String dataIds, boolean cascade);

    <T> String getModelId(Class<T> beanClass);

    /**
     * get class of model binding, searched by {@link BindModel}
     * @param modelName
     * @param schemaMark
     * @param schemaMarkIsName
     * @return the binded class, or null if no binding is defined
     */
    Class getModelBindClass(String modelName, String schemaMark, boolean schemaMarkIsName);

}
