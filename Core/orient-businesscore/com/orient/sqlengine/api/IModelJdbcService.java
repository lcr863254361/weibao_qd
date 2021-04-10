package com.orient.sqlengine.api;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.operationinterface.IEnum;
import com.orient.metamodel.operationinterface.IRestriction;

import java.util.List;
import java.util.Map;

/**
 * 业务模型的增删改查，校验业务
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 17, 2012
 */
public interface IModelJdbcService {

    /**
     * 返回业务模型的查询句柄
     *
     * @param bm 待查询的业务模型
     * @return IBusinessModelQuery 查询句柄
     */
    IBusinessModelQuery createModelQuery(IBusinessModel bm);

    /**
     * @param bm
     * @param bcList 子节点的组成字段链表 （唯一区别一个子节点）
     * @return 返回业务模型下子节点的查询句柄
     */
    IBusinessModelQuery createBomNodeQuery(IBusinessModel bm, List<IBusinessColumn> bcList);

    /**
     * 把一个map结构的数据记录插入到业务模型中
     *
     * @param bm      插入的业务模型
     * @param dataMap 待插入数据
     *                Key:插入字段的数据库存储名称
     *                value:该字段的字符串值
     * @return String 插入成功后产生的主键值
     */
    String insertModelData(IBusinessModel bm, Map<String, String> dataMap);

    /**
     * 将一个javabean的数据插入到业务模型中
     *
     * @param bm
     * @param bean                将使用此bean的属性与业务模型sname做比较
     * @param useCamelCaseMapping 是否将属性的camelCase映射为数据库sName为下划线分割的类型
     * @return 插入成功后产生的主键值
     */
    String insertModelData(IBusinessModel bm, Object bean, boolean useCamelCaseMapping);

    /**
     * 更新业务模型的记录
     *
     * @param bm      待更新的业务模型
     * @param dataMap 更新的数据记录map结构
     *                Key:插入字段的数据库存储名称
     *                value:该字段的字符串值
     * @param dataId  更新记录的主键
     * @return boolean 更新成功或失败
     */
    Boolean updateModelData(IBusinessModel bm, Map<String, String> dataMap, String dataId);

    /**
     * @param bm                  待更新的业务模型
     * @param bean                将使用此bean的属性与业务模型sname做比较
     * @param dataId              更新记录的主键
     * @param useCamelCaseMapping 是否将属性的camelCase映射为数据库sName为下划线分割的类型
     * @return 更新成功或失败
     */
    Boolean updateModelData(IBusinessModel bm, Object bean, String dataId, boolean useCamelCaseMapping);

    /**
     * 校验字段值是否唯一
     *
     * @param bc       字段对象
     * @param colValue 字段对应的值
     * @return boolean true唯一 false已存在
     */
    boolean checkColValueOnly(IBusinessColumn bc, String colValue);

    /**
     * 校验待删除记录是否可以删除，或级联删除
     *
     * @param bm         待删除的模型
     * @param delDataIds 待删除的记录主键字符串（可以是多个主键，需要以逗号分割）
     * @return int
     * 0：可以删除 1:级联删除(自有关系) 2:不可删除(紧耦合)
     */
    int checkDelAble(IBusinessModel bm, String delDataIds);

    /**
     * 校验记录是否满足唯一性约束
     *
     * @param bm        业务模型
     * @param modelData 校验数据的Map结构
     *                  Key:插入字段的数据库存储名称
     *                  value:该字段的字符串值
     * @return boolean true表名满足唯一性约束，false 则不满足
     */
    boolean checkMultiUk(IBusinessModel bm, Map<String, String> modelData);

    /**
     * 校验数据是否满足定义在模型上的业务表达式
     * 如果有内容表明数据校验不通过
     * 没内容则校验成功
     *
     * @param bm        业务模型
     * @param modelData 业务数据
     * @return String 校验结果
     */
    String checkModelExpression(IBusinessModel bm, Map<String, String> modelData);

    /**
     * 返回该记录对应的主键显示值
     * 如果涉及多个字段，把多个字段值用逗号拼接起来
     * 如果没有定义主键显示字段，则返回ID
     *
     * @param bm   业务模型
     * @param pkId 业务模型中的记录
     * @return String 主键显示值
     */
    String queryModelUkShowValue(IBusinessModel bm, String pkId);

    /**
     * 删除当前模型中的dataIds中的记录
     *
     * @param bm      业务模型
     * @param dataIds 待删除记录的主键字符串（可以是多个主键，需要以逗号分割）
     */
    void delete(IBusinessModel bm, String dataIds);

    /**
     * 清空当前模型中的所有记录
     *
     * @param bm 业务模型
     */
    void clear(IBusinessModel bm);

    /**
     * 级联删除当前模型及其关联模型中的所有相关记录
     *
     * @param bm      业务模型
     * @param dataIds 待删除记录的主键字符串（可以是多个主键，需要以逗号分割）
     *                也可以是查询主键的sql语句
     */
    void deleteCascade(IBusinessModel bm, String dataIds);

    /**
     * 约束为动态范围约束时，返回范围约束Map
     * 否则返回null
     *
     * @return Map<String,String>
     * key:mindata value:***
     * key:maxdata value:***
     */
    Map<String, String> queryResDynamicRange(IRestriction res);

    /**
     * 约束为静态范围约束时，返回范围约束Map
     * 否则返回null
     */
    Map<String, String> queryResStaticRange(IRestriction res);

    /**
     * 约束为表枚举约束时，返回枚举约束
     * 否则返回null
     */
    List<IEnum> queryResTableEnum(IRestriction res);

}
