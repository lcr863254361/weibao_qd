package com.orient.sqlengine.api;

/**
 * 业务层的引擎类
 *
 * @author zhu longchao
 * @Date Mar 1, 2012		6:02:40 PM
 */
public interface ISqlEngine {

    /**
     * 返回业务引擎中所绑定的业务模型服务
     *
     * @return IModelJdbcService
     */
    IModelJdbcService getBmService();

    /**
     * 返回业务引擎中绑定的系统模型服务
     *
     * @return ISysModelService
     */
    ISysModelService getSysModelService();

    /**
     * get the business model service which support type mapping
     *
     * @return
     */
    ITypeMappingBmService getTypeMappingBmService();

}
