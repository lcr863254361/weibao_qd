package com.orient.metamodel.operationinterface;

/**
 * @author mengbin
 * @Date Mar 19, 2012		10:42:10 AM
 */
public interface IEnum {

    /**
     * 获得显示值
     *
     * @return String
     */
    String getDisplayValue();

    /**
     * 获取真实值
     *
     * @return String
     */
    String getValue();

    /**
     * 是否打开
     *
     * @return boolean
     */
    boolean isOpen();

    String getId();

    Long getOrder();

}

