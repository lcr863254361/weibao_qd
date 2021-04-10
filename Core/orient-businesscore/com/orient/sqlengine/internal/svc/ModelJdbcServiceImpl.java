package com.orient.sqlengine.internal.svc;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.operationinterface.IEnum;
import com.orient.metamodel.operationinterface.IRestriction;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.IModelJdbcService;
import com.orient.sqlengine.cmd.api.EDMCommandService;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.sqlengine.internal.business.cmd.*;
import com.orient.sqlengine.internal.query.BusinessModelQueryImpl;
import com.orient.sqlengine.internal.query.BussinessModelGroupQueryImpl;
import com.orient.sqlengine.internal.sys.cmd.RestrictionCmd;
import com.orient.sqlengine.util.BusinessDataConverter;

import java.util.List;
import java.util.Map;

/**
 * 业务模型服务的实现
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 17, 2012
 */
public class ModelJdbcServiceImpl implements IModelJdbcService {
    EDMCommandService commandService;

    /**
     * TODO
     *
     * @param bm
     * @return
     * @Method: createModelQuery
     * @see com.orient.sqlengine.api.IModelJdbcService#createModelQuery(com.orient.businessmodel.bean.IBusinessModel)
     */
    @Override
    public IBusinessModelQuery createModelQuery(IBusinessModel bm) {
        BusinessModelQueryImpl modelquery = new BusinessModelQueryImpl(bm);
        modelquery.setCommandService(commandService);
        return modelquery;
    }


    /**
     * @param bm
     * @param bcList
     * @return
     * @see com.orient.sqlengine.api.IModelJdbcService#createBomNodeQuery(com.orient.businessmodel.bean.IBusinessModel, java.util.List)
     */
    @Override
    public IBusinessModelQuery createBomNodeQuery(IBusinessModel bm,
                                                  List<IBusinessColumn> bcList) {

        BussinessModelGroupQueryImpl modelquery = new BussinessModelGroupQueryImpl(bm, bcList);
        modelquery.setCommandService(commandService);
        return modelquery;
    }


    /**
     * TODO
     *
     * @param bc
     * @param colValue
     * @return
     * @Method: checkColValueOnly
     * @see com.orient.sqlengine.api.IModelJdbcService#checkColValueOnly(com.orient.businessmodel.bean.IBusinessColumn, java.lang.String)
     */
    public boolean checkColValueOnly(IBusinessColumn bc, String colValue) {
        CheckColumnOnlyCmd cmd = new CheckColumnOnlyCmd(bc, colValue);
        return (Boolean) commandService.execute(cmd);
    }


    /**
     * TODO
     *
     * @param bm
     * @param modelData
     * @return
     * @Method: checkModelExpression
     * @see com.orient.sqlengine.api.IModelJdbcService#checkModelExpression(com.orient.businessmodel.bean.IBusinessModel, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public String checkModelExpression(IBusinessModel bm, Map<String, String> modelData) {
        CheckModelExpressionCmd expressionCmd = new CheckModelExpressionCmd(bm, modelData);
        expressionCmd.setCommandService(commandService);
        return (String) commandService.execute(expressionCmd);
    }

    /**
     * TODO
     *
     * @param bm
     * @param delDataIds
     * @return
     * @Method: checkDelAble
     * @see com.orient.sqlengine.api.IModelJdbcService#checkDelAble(com.orient.businessmodel.bean.IBusinessModel, java.lang.String)
     */
    public int checkDelAble(IBusinessModel bm, String delDataIds) {
        return (Integer) commandService.execute(new CheckDelAbleCmd(bm, delDataIds));
    }

    /**
     * TODO
     *
     * @param bm
     * @param modelData
     * @return
     * @Method: checkMultiUk
     * @see com.orient.sqlengine.api.IModelJdbcService#checkMultiUk(com.orient.businessmodel.bean.IBusinessModel, java.util.Map)
     */
    public boolean checkMultiUk(IBusinessModel bm, Map<String, String> modelData) {
        return (Boolean) commandService.execute(new CheckMultiUkCmd(bm, modelData));
    }

    /**
     * TODO
     *
     * @param bm
     * @param pkId
     * @return
     * @Method: queryModelUkShowValue
     * @see com.orient.sqlengine.api.IModelJdbcService#queryModelUkShowValue(com.orient.businessmodel.bean.IBusinessModel, java.lang.String)
     */
    public String queryModelUkShowValue(IBusinessModel bm, String pkId) {
        return (String) commandService.execute(new QueryUkShowValueCmd(bm, pkId));
    }

    /**
     * TODO
     *
     * @param res
     * @return
     * @Method: queryResDynamicRange
     * @see com.orient.sqlengine.api.IModelJdbcService#queryResDynamicRange(com.orient.metamodel.operationinterface.IRestriction)
     */
    public Map<String, String> queryResDynamicRange(IRestriction res) {
        RestrictionCmd resCmd = new RestrictionCmd(res);
        resCmd.setCommandService(commandService);
        return resCmd.dynamicRange();
    }


    public Map<String, String> queryResStaticRange(IRestriction res) {
        RestrictionCmd resCmd = new RestrictionCmd(res);
        resCmd.setCommandService(commandService);
        return resCmd.staticRange();
    }


    public List<IEnum> queryResTableEnum(IRestriction res) {

        RestrictionCmd resCmd = new RestrictionCmd(res);
        resCmd.setCommandService(commandService);
        return resCmd.tableEnum();
    }

    /**
     * TODO
     *
     * @param bm
     * @param dataMap
     * @return
     * @Method: insertModelData
     * @see com.orient.sqlengine.api.IModelJdbcService#insertModelData(com.orient.businessmodel.bean.IBusinessModel, java.util.Map)
     */
    public String insertModelData(IBusinessModel bm, Map<String, String> dataMap) {
        AddBusinessModelDataCmd cmd = new AddBusinessModelDataCmd(bm, dataMap);
        return SqlEngineHelper.Obj2String(commandService.execute(cmd));

    }

    @Override
    public String insertModelData(IBusinessModel bm, Object bean, boolean useCamelCaseMapping) {
        Map<String, String> dataMap = BusinessDataConverter.convertBeanToRealColMap(bm, bean, useCamelCaseMapping);
        AddBusinessModelDataCmd cmd = new AddBusinessModelDataCmd(bm, dataMap);
        return SqlEngineHelper.Obj2String(commandService.execute(cmd));
    }

    /**
     * TODO
     *
     * @param bm
     * @param dataMap
     * @param dataId
     * @return
     * @Method: updateModelData
     * @see com.orient.sqlengine.api.IModelJdbcService#updateModelData(com.orient.businessmodel.bean.IBusinessModel, java.util.Map, java.lang.String)
     */
    public Boolean updateModelData(IBusinessModel bm, Map<String, String> dataMap, String dataId) {
        UpdateBusinessModelDataCmd cmd = new UpdateBusinessModelDataCmd(bm, dataMap, dataId);
        return (Boolean) commandService.execute(cmd);

    }

    @Override
    public Boolean updateModelData(IBusinessModel bm, Object bean, String dataId, boolean useCamelCaseMapping) {
        Map<String, String> dataMap = BusinessDataConverter.convertBeanToRealColMap(bm, bean, useCamelCaseMapping);
        return this.updateModelData(bm, dataMap, dataId);
    }

    /**
     * TODO
     *
     * @param bm
     * @param dataIds
     * @Method: delete
     * @see com.orient.sqlengine.api.IModelJdbcService#delete(com.orient.businessmodel.bean.IBusinessModel, java.lang.String)
     */
    public void delete(IBusinessModel bm, String dataIds) {
        DelBusinessModelDataCmd delCmd = new DelBusinessModelDataCmd(bm, dataIds, false);
        commandService.execute(delCmd);
    }

    /**
     * TODO
     *
     * @param bm
     * @param dataIds
     * @Method: deleteCascade
     * @see com.orient.sqlengine.api.IModelJdbcService#deleteCascade(com.orient.businessmodel.bean.IBusinessModel, java.lang.String)
     */
    public void deleteCascade(IBusinessModel bm, String dataIds) {
        DelBusinessModelDataCmd delCmd = new DelBusinessModelDataCmd(bm, dataIds, true);
        commandService.execute(delCmd);
    }

    public EDMCommandService getCommandService() {
        return commandService;
    }

    public void setCommandService(EDMCommandService commandService) {
        this.commandService = commandService;
    }


    @Override
    public void clear(IBusinessModel bm) {
        ClearBusinessModelDataCmd clearCmd = new ClearBusinessModelDataCmd(bm);
        commandService.execute(clearCmd);

    }


}
