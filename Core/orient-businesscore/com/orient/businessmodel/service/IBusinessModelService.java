package com.orient.businessmodel.service;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.sqlengine.api.ISqlEngine;

import java.util.List;
import java.util.Map;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName IBusinessModelService
 * 业务模型服务
 * @date Apr 13, 2012
 */

public interface IBusinessModelService {

    /**
     * 返回业务数据模型
     *
     * @param modelId   String 模型ID
     * @param modelType 模型类型 BusinessModelEnum
     * @return IBusinessModel
     * @Method: getBusinessModelById
     */
    IBusinessModel getBusinessModelById(String modelId, BusinessModelEnum modelType);

    /**
     * 返回业务数据模型
     * 根据用户的权限，组装模型的权限和字段的权限
     *
     * @param userid    用户的ID
     * @param modelId   模型的ID
     * @param schemaId  模型所属Schema的ID
     * @param modelType 模型的类型 BusinessModelEnum
     * @return IBusinessModel
     * @Method: getBusinessModelById
     */
    IBusinessModel getBusinessModelById(String userid, String modelId, String schemaId, BusinessModelEnum modelType);

    /**
     * 根据模型名称返回业务数据模型
     *
     * @param sModelName 模型的名称(不是显示名和物理名称)
     * @param schemaId   模型所属Schema的ID
     * @param modelType  模型的类型 BusinessModelEnum
     * @return IBusinessModel
     * @Method: getBusinessModelBySName
     */
    IBusinessModel getBusinessModelBySName(String sModelName, String schemaId, BusinessModelEnum modelType);

    /**
     * 根据模型名称返回业务数据模型
     * 根据用户的权限，组装模型的权限和字段的权限
     *
     * @param userid     用户ID
     * @param sModelName 模型的名称(不是显示名和物理名称)
     * @param schemaId   模型所属Schema的ID
     * @param modelType  模型的类型 BusinessModelEnum
     * @return IBusinessModel
     * @Method: getBusinessModelBySName
     */
    IBusinessModel getBusinessModelBySName(String userid, String sModelName, String schemaId, BusinessModelEnum modelType);

    /**
     * 设置自定义过滤条件
     *
     * @param bm            模型
     * @param s_column_name 字段在数据库中存储的名称
     * @param filterValue   过滤值
     * @Method: appendCustomFilter
     */
    void appendCustomFilter(IBusinessModel bm, String s_column_name, String filterValue);

    /**
     * 设置自定义过滤条件
     *
     * @param bm            模型
     * @param s_column_name 字段在数据库中存储的名称
     * @param filterValue   过滤值
     * @param operation     操作符
     * @Method: appendCustomFilter
     */
    void appendCustomFilter(IBusinessModel bm, String s_column_name, String filterValue, String operation);


    /**
     * 批量设置模型的过滤条件
     *
     * @param bm
     * @param fiterList 过滤条件列表
     *                  CustomerFilter 过滤对象之间可以设置依赖关系和过滤对象之间的连接方式
     * @Method: appendCustomFilters
     */
    void appendCustomFilters(IBusinessModel bm, List<CustomerFilter> fiterList);

    /**
     * 初始化模型之间的关联关系
     *
     * @param mainModel 主模型
     * @param relModel  关系模型
     * @param userid    用户ID
     * @Method: initModelRelation
     */
    void initModelRelation(IBusinessModel mainModel, IBusinessModel relModel, String userid);

    /**
     * 字段初始化字段信息
     */
    void initColumnData(ISqlEngine orientSqlEngine, IBusinessModel bm, Map dataMap);

    /**
     * 字段初始化真实值
     */
    void initColumnValue(IBusinessModel bm, Map dataMap);

    /**
     * 字段初始化显示值
     */
    void initColumnShow(IBusinessModel bm, Map dataMap);

    /**
     * 模型中的字段的真实值转换成显示值
     */
    void dataChangeModel(ISqlEngine orientSqlEngine, IBusinessModel bm, Map dataMap, boolean deal_all);

    void dataChangeModel(ISqlEngine orientSqlEngine, IBusinessModel bm, List<Map> dataList, boolean deal_all);

    void dataChangeModel(ISqlEngine orientSqlEngine, List<IBusinessColumn> businessColumns, List<Map> dataList);


    /**
     * 根据传入的columns 将Map中的Key转化成IBuisnessColumn,Value也进行转化,主要用于需要数据类型的数据使用,例如分析工具,ODS导出
     *
     * @param orientSqlEngine
     * @param businessColumns
     * @param dataList
     * @return 数据能够按照通道区分
     */
    Map<IBusinessColumn, List<String>> dataValueColumnChange(ISqlEngine orientSqlEngine, List<IBusinessColumn> businessColumns, List<Map> dataList);


    void dataChangeColumn(ISqlEngine orientSqlEngine, IColumn column, List<Map> dataList);


    MetaUtil getMetaEngine();

}