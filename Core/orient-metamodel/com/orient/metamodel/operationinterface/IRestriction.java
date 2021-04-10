package com.orient.metamodel.operationinterface;

import com.orient.metamodel.metadomain.TableEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mengbin@cssrc.com.cn
 * @date Mar 17, 2012
 */
public interface IRestriction {

    int TYPE_ENUM = 1;
    int TYPE_TABLEENUM = 2;
    int TYPE_RANGEENUM = 3;
    int TYPE_DYNAMICRANGEENUM = 4;

    /**
     * @return String
     */
    String getId();

    /**
     * 获取约束类型
     *
     * @return int
     */
    int getRestionType();

    /**
     * 是否多选
     *
     * @return boolean
     */
    boolean isMutiSelected();

    /**
     * 获取所有的枚举值变量
     *
     * @return List<IEnum>
     */
    List<IEnum> getAllEnums();

    /**
     * 根据枚举的数据库值获取显示值（数据库值）
     *
     * @return String
     */
    List<String> getDisplayEnumByDBValue(List<String> ids);

    /**
     * 返回表枚举约束
     * 如果不存在返回Null
     *
     * @return TableEnum
     */
    TableEnum getTableEnum();

    /**
     * 返回范围约束的最大值
     * 不存在返回Null
     *
     * @return BigDecimal
     */
    BigDecimal getMaxLength();

    /**
     * 返回范围约束的最小值
     * 不存在返回Null
     *
     * @return BigDecimal
     */
    BigDecimal getMinLength();

}

