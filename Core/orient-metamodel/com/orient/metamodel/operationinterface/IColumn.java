package com.orient.metamodel.operationinterface;

import com.orient.metamodel.metadomain.Restriction;

import java.util.Set;

/**
 * 对字段的操作
 *
 * @author mengbin@cssrc.com.cn
 * @date Mar 16, 2012
 */
public interface IColumn extends Comparable {

    /**
     * @Fields CATEGORY_COMMON :普通属性
     */
    long CATEGORY_COMMON = 1;

    /**
     * @Fields CATEGORY_RELATION : 关系属性
     */
    long CATEGORY_RELATION = 2;

    /**
     * @Fields CATEGORY_ARITH : 统计属性
     */
    long CATEGORY_ARITH = 3;

    /**
     * @Fields ISVAILD_INVALID : 无效
     */
    long ISVAILD_INVALID = 0;

    /**
     * @Fields ISVAILD_VALID : 有效
     */
    long ISVAILD_VALID = 1;

    /**
     * @Fields ISVAILD_MODIFY : 即将修改
     */
    long ISVAILD_MODIFY = 2;

    /**
     * @Fields ISVAILD_DELETE : 已经删除
     */
    long ISVAILD_DELETE = 3;

    String TYPE_STRING = "String";
    String TYPE_BOOLEAN = "Boolean";
    String TYPE_BYTE = "Byte";
    String TYPE_DATE = "Date";
    String TYPE_DATETIME = "DateTime";
    String TYPE_DECIMAL = "Decimal";
    String TYPE_DOUBEL = "Double";
    String TYPE_FLOAT = "Float";
    String TYPE_INT = "Integer";
    String TYPE_BIGINT = "BigInteger";
    String TYPE_ODS = "ODS";
    String TYPE_CHECK = "Check";
    String TYPE_HADOOP = "Hadoop";
    String TYPE_TEXT = "Text";
    String TYPE_NAMEVALUE = "NameValue";
    String TYPE_SUBTABLE = "SubTable";
    String TYPE_FILE = "File";

    /**
     * @Fields SHOW : 显示
     */
    String SHOW = "True";

    /**
     * @Fields HIDE : 隐藏
     */
    String HIDE = "False";

    /**
     * 获取Column在CWM_TAB_COLUMN中的ID
     *
     * @return String
     */
    String getId();

    /**
     * 获取Column在CWM_TAB_COLUMN中的 S_COLUMN_NAME
     *
     * @return String
     */
    String getColumnName();

    /**
     * 获取Column在CWM_TAB_COLUMN中的 S_NAME
     *
     * @return String
     */
    String getName();

    /**
     * 获取Column在CWM_TAB_COLUMN中的 CATEGORY
     *
     * @return Long
     */
    Long getCategory();

    /**
     * 获取该字段的用途
     *
     * @return String
     */
    String getPurpose();

    /**
     * 是否有效
     *
     * @return Long
     */
    Long getIsValid();

    /**
     * * 该属性是否显示
     *
     * @return String
     */
    String getIsShow();

    /**
     * 获得关系属性的特性
     *
     * @return IRelationColumn
     */
    IRelationColumn getRelationColumnIF();

    /**
     * 获取该字段的约束
     *
     * @return IRestriction
     */
    IRestriction getRefRestriction();

    /**
     * 获取数据类型
     *
     * @return String
     */
    String getType();

    /**
     * 获取Column的所有者
     *
     * @return IMatrix
     */
    IMatrix getRefMatrix();

    /**
     * * 获取显示名称
     *
     * @return String
     */
    String getDisplayName();

    /**
     * 是否能够编辑
     *
     * @return True 可编辑 False 不可编辑
     */
    String getEditable();

    /**
     * 是否自增
     *
     * @return True 自增 False 不自增
     */
    String getIsAutoIncrement();

    /**
     * 返回字段对应的Sequence
     * 不存在则返回Null
     *
     * @return String
     */
    String getSequenceName();

    /**
     * 是否可以为空
     *
     * @return True 可为空 False 不可为空
     */
    String getIsNull();

    /**
     * @Function Name:  getIsPK
     * @Description: @return 是否为主键显示值 1：是 0：否
     * @Date Created:  2013-3-19 下午02:06:37
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    Long getIsPK();

    /**
     * @Function Name:  getRestriction
     * @Description: @return 得到字段的约束
     * @Date Created:  2013-3-19 下午04:12:43
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    Restriction getRestriction();

    /**
     * @Function Name:  getIsWrap
     * @Description: @return 是否多行显示
     * @Date Created:  2013-3-19 下午04:12:53
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    String getIsWrap();

    /**
     * 是否唯一
     */
    String getIsOnly();

    /**
     * 真实值
     */
    String getDataValue();

    /**
     * 显示值
     */
    String getDisplayValue();

    Long getMinLength();

    Long getMaxLength();

    Long getNumLength();

    Long getNumprecision();

    String getDefaultValue();

    Set getColumnSet();

    /**
     * 是否需要
     */
    String getIsNeed();

    String getSelector();

    String getUnit();

}

