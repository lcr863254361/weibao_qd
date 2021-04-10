/**
 * @Project: OrientEDM
 * @Title: BusinessModelService.java
 * @Package com.orient.businessmodel.service
 * TODO
 * @author zhulc@cssrc.com.cn
 * @date Apr 6, 2012 3:35:07 PM
 * @Copyright: 2012 www.cssrc.com.cn. All rights reserved.
 * @version V1.0
 */


package com.orient.businessmodel.service.impl;

import com.orient.businessmodel.Util.BusinessModelCacheHelper;
import com.orient.businessmodel.Util.BusinessModelHelper;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.Util.EnumInter.SqlOperation;
import com.orient.businessmodel.Util.ModelExcepion;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.IdQueryCondition;
import com.orient.businessmodel.bean.impl.BusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.*;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.sysmodel.domain.modeldata.CwmModelDataUnitEntity;
import com.orient.sysmodel.domain.role.MatrixRight;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IMatrixRight;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.modeldata.IModelDataUnitService;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.orient.utils.JsonUtil.getJavaCollection;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName BusinessModelService
 * TODO
 * @date Apr 6, 2012
 */

public class BusinessModelServiceImpl implements IBusinessModelService {

    private MetaDAOFactory metadaofactory;
    private MetaUtil metaEngine;
    private IRoleUtil roleEngine;
    private BusinessModelCacheHelper businessModelCacheHelper;
    private IModelDataUnitService modelDataUnitService;
    private static final Logger log = Logger.getLogger(BusinessModelServiceImpl.class);

    public BusinessModelCacheHelper getBusinessModelCacheHelper() {
        return businessModelCacheHelper;
    }

    public void setBusinessModelCacheHelper(BusinessModelCacheHelper businessModelCacheHelper) {
        this.businessModelCacheHelper = businessModelCacheHelper;
    }

    public IModelDataUnitService getModelDataUnitService() {
        return modelDataUnitService;
    }

    public void setModelDataUnitService(IModelDataUnitService modelDataUnitService) {
        this.modelDataUnitService = modelDataUnitService;
    }

    /**
     * TODO
     *
     * @param modelId
     * @param modelType
     * @return
     * @Method: getBusinessModelById
     * @see com.orient.businessmodel.service.IBusinessModelService#getBusinessModelById(java.lang.String, com.orient.businessmodel.Util.EnumInter.BusinessModelEnum)
     */

    public IBusinessModel getBusinessModelById(String modelId, BusinessModelEnum modelType) {
        return getBusinessModelById(null, modelId, null, modelType);
    }

    /**
     * TODO
     *
     * @param modelId
     * @param schemaId
     * @param modelType
     * @return IBusinessModel
     * @Method: getBusinessModelById
     */
    public IBusinessModel getBusinessModelById(String modelId, String schemaId, BusinessModelEnum modelType) {
        return getBusinessModelById(null, modelId, schemaId, modelType);
    }

    /**
     * TODO
     *
     * @param userid
     * @param modelId
     * @param schemaId
     * @param modelType
     * @return
     * @Method: getBusinessModelById
     * @see com.orient.businessmodel.service.IBusinessModelService#getBusinessModelById(java.lang.String, java.lang.String, java.lang.String, com.orient.businessmodel.Util.EnumInter.BusinessModelEnum)
     */

    public IBusinessModel getBusinessModelById(String userid, String modelId,
                                               String schemaId, BusinessModelEnum modelType) {

        if (null == modelId || ("").equals(modelId)) {
            log.error("业务模型获取失败,模型的ID不可为空");
            throw new ModelExcepion("业务模型获取失败,模型的ID不可为空");
        }
        if (null == modelType) {
            log.error("业务模型获取失败,模型的类型(Table或View)不可为空");
            throw new ModelExcepion("业务模型获取失败,模型的类型(Table或View)不可为空");
        }
        /*从元数据模型中获取matrix*/
        IMatrix matrix = getByModelId(schemaId, modelType, modelId);
        if (null == matrix) {
            //容错
            modelType = modelType.equals(BusinessModelEnum.Table) ? BusinessModelEnum.View : BusinessModelEnum.Table;
            matrix = getByModelId(schemaId, modelType, modelId);
            if (null == matrix) {
                log.error("业务模型获取失败,找不到指定的模型[ID=" + modelId + "]");
                throw new ModelExcepion("业务模型获取失败,找不到指定的模型[ID=" + modelId + "]");
            }
        }
        return createBusinessModel(matrix, userid);
    }

    private IMatrix getByModelId(String schemaId, BusinessModelEnum modelType, String modelId) {
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemamap = metaModel.getSchemas();
        List<ISchema> schemaList = new ArrayList<ISchema>();
        if (null != schemaId && !("").equals(schemaId)) {
            ISchema schema = metaModel.getISchemaById(schemaId);
            schemaList.add(schema);
        } else {
            //schemaid为空，遍历所有schema
            for (Map.Entry<String, Schema> entry : schemamap.entrySet()) {
                schemaList.add(entry.getValue());
            }
        }
        IMatrix matrix = null;
        for (ISchema schema : schemaList) {
            switch (modelType) {
                case Table:
                    matrix = schema.getTableById(modelId);
                    break;
                case View:
                    matrix = schema.getViewById(modelId);
                    break;
            }
            if (null != matrix) {
                break;
            }
        }
        return matrix;
    }

    private IMatrix getBySName(String schemaId, BusinessModelEnum modelType, String sModelName) {
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemamap = metaModel.getSchemas();
        List<ISchema> schemaList = new ArrayList<ISchema>();
        if (null != schemaId && !("").equals(schemaId)) {
            ISchema schema = metaModel.getISchemaById(schemaId);
            schemaList.add(schema);
        } else {
            //schemaid为空，遍历所有schema
            for (Map.Entry<String, Schema> entry : schemamap.entrySet()) {
                schemaList.add(entry.getValue());
            }
        }
        IMatrix matrix = null;
        for (ISchema schema : schemaList) {
            switch (modelType) {
                case Table:
                    matrix = schema.getTableByName(sModelName);
                    break;
                case View:
                    matrix = schema.getViewByName(sModelName);
                    break;
            }
            if (null != matrix) {
                break;
            }
        }
        return matrix;
    }

    /**
     * TODO
     *
     * @param sModelName
     * @param schemaId
     * @param modelType
     * @return
     * @Method: getBusinessModelBySName
     * @see com.orient.businessmodel.service.IBusinessModelService#getBusinessModelBySName(java.lang.String, java.lang.String, com.orient.businessmodel.Util.EnumInter.BusinessModelEnum)
     */

    public IBusinessModel getBusinessModelBySName(String sModelName, String schemaId, BusinessModelEnum modelType) {
        return getBusinessModelBySName("", sModelName, schemaId, modelType);
    }

    /**
     * TODO
     *
     * @param userid
     * @param sModelName
     * @param schemaId
     * @param modelType
     * @return
     * @Method: getBusinessModelBySName
     * @see com.orient.businessmodel.service.IBusinessModelService#getBusinessModelBySName(java.lang.String, java.lang.String, java.lang.String, com.orient.businessmodel.Util.EnumInter.BusinessModelEnum)
     */

    public IBusinessModel getBusinessModelBySName(String userid, String sModelName, String schemaId, BusinessModelEnum modelType) {
        if (null == sModelName || ("").equals(sModelName)) {
            log.error("业务模型获取失败,模型的名称不可为空");
            throw new ModelExcepion("业务模型获取失败,模型的名称不可为空");
        }
        if (null == modelType) {
            log.error("业务模型获取失败,模型的类型(Table或View)不可为空");
            throw new ModelExcepion("业务模型获取失败,模型的类型(Table或View)不可为空");
        }
        if (null == schemaId) {
            log.error("业务模型获取失败,Schema不可为空");
            throw new ModelExcepion("业务模型获取失败,Schema不可为空");
        }
        IMatrix matrix = getBySName(schemaId, modelType, sModelName);
        if (null == matrix) {
            //容错
            modelType = modelType.equals(BusinessModelEnum.Table) ? BusinessModelEnum.View : BusinessModelEnum.Table;
            matrix = getBySName(schemaId, modelType, sModelName);
            if (null == matrix) {
                log.error("业务模型获取失败,找不到指定的模型[Name=" + sModelName + "]");
                throw new ModelExcepion("业务模型获取失败,找不到指定的模型[Name=" + sModelName + "]");
            }
        }
        return createBusinessModel(matrix, userid);
    }

    /**
     * 创建业务模型
     * 根据用户ID，获取角色权限，赋给模型
     *
     * @param matrix 元数据模型
     * @param userid 用户ID
     * @return IBusinessModel`
     * @Method: createBusinessModel
     */
    private IBusinessModel createBusinessModel(IMatrix matrix, String userid) {
        Long start = System.currentTimeMillis();
        BusinessModel bm = new BusinessModel(matrix);
        if (null != userid && !("").equals(userid)) {
            MatrixRight matrixright = new MatrixRight();
            //添加密级字段到Matrix中
            IRoleModel roleModel = roleEngine.getRoleModel(false);
            User user = (User) roleModel.getUserById(userid);
            bm.setUserSecrecy(user.getGrade());
            //通过userid获取多个角色
            List<IRole> roles = roleModel.getRolesOfUser(userid);
            for (IRole role : roles) {
                IMatrixRight right = role.getRightsOfMatrix(matrix);
                mergeRight(right, matrixright);
            }
            bm.setMatrixRight(matrixright);
        }
        bm.initColumns();
        if (IMatrix.TYPE_VIEW == bm.getMatrix().getMatrixType()) {
            IBusinessModel mainModel = createBusinessModel(matrix.getMainTable(), userid);
            bm.setMainModel(mainModel);
        }
        Long end = System.currentTimeMillis();
        return bm;
    }

    /**
     * 将角色定义的权限进行合并
     *
     * @param fromRight 角色Matrix权限
     * @param toRight   目标Matrix权限
     * @Method: mergeRight
     */
    private void mergeRight(IMatrixRight fromRight, MatrixRight toRight) {
        Method[] methods = IMatrixRight.class.getDeclaredMethods();
        try {
            for (Method method : methods) {
                if (method.getReturnType() == List.class) {
                    String methodName = method.getName();
                    List tpRights = (List) method.invoke(toRight);
                    List fromRights = (List) method.invoke(fromRight);
                    List newRights = unionToList(fromRights, tpRights);
                    MatrixRight.class.getSuperclass().getDeclaredMethod("set" + method.getName().substring(3), List.class).invoke(toRight, newRights);
                }
            }
            /*合并过滤表达式*/
            if (!StringUtil.isEmpty(fromRight.getFilter())) {
                if (!StringUtil.isEmpty(toRight.getFilter())) {
                    //或的关系
                    String filter = "(" + toRight.getFilter() + ") or (" + fromRight.getFilter() + ")";
                    toRight.setFilter(filter);
                } else {
                    toRight.setFilter(fromRight.getFilter());
                }
            }
            if (!StringUtil.isEmpty(fromRight.getUserFilter())) {
                if (!StringUtil.isEmpty(toRight.getUserFilter())) {
                    //或的关系
                    String filter = "(" + toRight.getUserFilter() + ") or (" + fromRight.getUserFilter() + ")";
                    toRight.setUserFilter(filter);
                } else {
                    toRight.setUserFilter(fromRight.getUserFilter());
                }
            }
            /** 合并是否由表访问权限设置*/
            toRight.setModelRightSet(fromRight.getModelRightSet() || toRight.getModelRightSet());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private <T> List<T> unionToList(List<T> fromList, List<T> toList) {
        toList.addAll(fromList);
        List<T> newColIds = toList.stream().distinct().collect(Collectors.toList());
        return newColIds;
    }

    /**
     * TODO
     *
     * @param mainModel
     * @param relModel
     * @param userid
     * @Method: initModelRelation
     * @see com.orient.businessmodel.service.IBusinessModelService#initModelRelation(com.orient.businessmodel.bean.IBusinessModel, com.orient.businessmodel.bean.IBusinessModel, java.lang.String)
     */
    public void initModelRelation(IBusinessModel mainModel
            , IBusinessModel relModel, String userid) {
//        IMetaModel metaModel = metaEngine.getMeta(false);
//        SchemaGraph graph = metaModel.getSchemaGraphMap().get(mainModel.getSchema().getId());
//        ISchemaGraphTravel graphTravel = new SchemaGraphTravel(graph);
//        //遍历Schema谱系
//        List<List<TableEdge>> tableEdgeListList = graphTravel.travel(relModel.getMatrix().getMainTable(), mainModel.getMatrix().getMainTable());
//
//        if (null == tableEdgeListList) {
//            log.error("业务模型[" + mainModel.getDisplay_name() + "与" + relModel.getDisplay_name() + "之间无关系");
//            throw new ModelExcepion("业务模型关系初始化失败");
//        }
//        //把元数据模型的边转成业务模型的连接边
//        List<List<BusinessModelEdge>> bmEdgeListList = new ArrayList<List<BusinessModelEdge>>();
//        //保留最短路径
//        List<TableEdge> tableEdgeList = tableEdgeListList.stream().min((list1, list2) -> list1.size() - list2.size()).get();
//        List<BusinessModelEdge> bmEdgeList = new ArrayList<BusinessModelEdge>();
//        for (int i = 0; i < tableEdgeList.size(); i++) {
//            IBusinessModel start = null;
//            IBusinessModel end = null;
//            TableEdge tableEdge = tableEdgeList.get(i);
//            if (i == 0) {
//                start = relModel;
//            } else {
//                start = getBusinessModelById(userid,
//                        tableEdge.getStart().getId(),
//                        tableEdge.getStart().getSchema().getId(),
//                        BusinessModelEnum.Table);
//            }
//            if (i == tableEdgeList.size() - 1) {
//                end = mainModel;
//            } else {
//                end = getBusinessModelById(userid,
//                        tableEdge.getEnd().getId(),
//                        tableEdge.getEnd().getSchema().getId(),
//                        BusinessModelEnum.Table);
//            }
//            BusinessModelEdge bmEdge = new BusinessModelEdge(start, end, tableEdge.getEdgeType());
//            bmEdge.setManyToMany(tableEdge.isManyToMany());
//            bmEdgeList.add(bmEdge);
//        }
//        bmEdgeListList.add(bmEdgeList);
//        mainModel.setPedigree(bmEdgeListList);
        //获取直接关联关系
        BusinessModelEdge bmEdge = null;
        if (relModel.getId().equals(mainModel.getId())) {//自身到自身
            bmEdge = new BusinessModelEdge(relModel, mainModel, 2);
            bmEdge.setManyToMany(false);
        } else {
            Integer edgeType = 0;
            final Boolean[] manyToMany = {false};
            relModel.getAllBcCols().forEach(iBusinessColumn -> {
                IRelationColumn relationColumn = iBusinessColumn.getRelationColumnIF();
                if (null != relationColumn && relationColumn.getRefTable().getId().equals(mainModel.getId())) {
                    manyToMany[0] = relationColumn.getRelationType() == 4;
                }
            });
            bmEdge = new BusinessModelEdge(relModel, mainModel, edgeType);
            bmEdge.setManyToMany(manyToMany[0]);
        }
        final BusinessModelEdge finalBmEdge = bmEdge;
        List<BusinessModelEdge> bmEdgeList = new ArrayList<BusinessModelEdge>() {{
            add(finalBmEdge);
        }};
        List<List<BusinessModelEdge>> bmEdgeListList = new ArrayList<List<BusinessModelEdge>>() {{
            add(bmEdgeList);
        }};
        mainModel.setPedigree(bmEdgeListList);
    }


    /**
     * TODO
     *
     * @param bm
     * @param s_column_name
     * @param filterValue
     * @Method: appendCustomFilter
     * @see com.orient.businessmodel.service.IBusinessModelService#appendCustomFilter(com.orient.businessmodel.bean.IBusinessModel, java.lang.String, java.lang.String)
     */
    public void appendCustomFilter(IBusinessModel bm, String s_column_name,
                                   String filterValue) {
        this.appendCustomFilter(bm, s_column_name, filterValue, "");
    }

    /**
     * TODO
     *
     * @param bm
     * @param s_column_name
     * @param filterValue
     * @param operation
     * @Method: appendCustomFilter
     * @see com.orient.businessmodel.service.IBusinessModelService#appendCustomFilter(com.orient.businessmodel.bean.IBusinessModel, java.lang.String, java.lang.String, java.lang.String)
     */
    public void appendCustomFilter(IBusinessModel bm, String s_column_name,
                                   String filterValue, String operation) {
        IBusinessColumn bc = bm.getBusinessColumnBySName(s_column_name);
        if (null == bc) {
            return;
        }
        SqlOperation op = SqlOperation.getSqlOperation(operation);
        if (op == SqlOperation.IsNull || op == SqlOperation.IsNotNull) {
            CustomerFilter filter = new CustomerFilter(s_column_name, op, "");
            bm.appendCustomerFilter(filter);
            return;
        }
        if (BusinessModelHelper.isNullString(filterValue)) {
            return;
        }
        if (op == null) {//输入操作错误时，返回该字段默认操作符
            op = BusinessModelHelper.getDefaultOperation(bc);
        }
        CustomerFilter filter = new CustomerFilter(s_column_name, op, filterValue);
        bm.appendCustomerFilter(filter);

    }

    /**
     * TODO
     *
     * @param bm
     * @param fiterList
     * @Method: appendCustomFilters
     * @see com.orient.businessmodel.service.IBusinessModelService#appendCustomFilters(com.orient.businessmodel.bean.IBusinessModel, java.util.List)
     */
    public void appendCustomFilters(IBusinessModel bm,
                                    List<CustomerFilter> fiterList) {
        for (CustomerFilter filter : fiterList) {
            IBusinessColumn bc = bm.getBusinessColumnBySName(filter.getFilterName());
            if (null == bc) { //无效过滤条件
                fiterList.remove(filter);
                continue;
            }
            if (filter.getOperation() == SqlOperation.IsNull
                    || filter.getOperation() == SqlOperation.IsNotNull) {
                //特殊过滤条件
                filter.setFilterValue("");
                bm.appendCustomerFilter(filter);
                continue;
            }
            if (BusinessModelHelper.isNullString(filter.getFilterValue())) {
                //过滤值为空时
                fiterList.remove(filter);
                continue;
            }
            if (filter.getOperation() == null) {
                //默认的操作符
                filter.setOperation(
                        BusinessModelHelper.getDefaultOperation(bc));
            }
            bm.appendCustomerFilter(filter);
        }
    }


    public void initColumnData(ISqlEngine orientSqlEngine, IBusinessModel bm, Map dataMap) {

        initColumnValue(bm, dataMap);
        dataChangeModel(orientSqlEngine, bm, dataMap, true);
        initColumnShow(bm, dataMap);
    }


    /**
     * 初始化真实值
     */
    public void initColumnValue(IBusinessModel bm, Map dataMap) {

        List<IBusinessColumn> businessColumns = bm.getAllBcCols();
        for (IBusinessColumn businessColumn : businessColumns) {

            Column column = (Column) businessColumn.getCol();
            String columnName = column.getColumnName();
            if (column.getCategory() == IColumn.CATEGORY_RELATION) {

                IRelationColumn refColumn = column.getRelationColumnIF();
                ITable refTable = refColumn.getRefTable();
                columnName = refTable.getTableName() + "_ID";
            }
            if (dataMap.containsKey(columnName)) {
                column.setDataValue(CommonTools.Obj2String(dataMap.get(columnName)));
            }
        }
    }

    /**
     * 初始化显示值
     */
    public void initColumnShow(IBusinessModel bm, Map dataMap) {

        List<IBusinessColumn> businessColumns = bm.getAllBcCols();
        for (IBusinessColumn businessColumn : businessColumns) {

            Column column = (Column) businessColumn.getCol();
            String columnName = column.getColumnName();
            if (column.getCategory() == IColumn.CATEGORY_RELATION) {

                IRelationColumn refColumn = column.getRelationColumnIF();
                ITable refTable = refColumn.getRefTable();
                columnName = refTable.getTableName() + "_ID";
            }
            if (dataMap.containsKey(columnName)) {
                column.setDisplayValue(CommonTools.Obj2String(dataMap.get(columnName)));
            }
        }
    }


    /**
     * 根据表将数据的真实值转换成显示值(单个Map转换)
     */
    public void dataChangeModel(ISqlEngine orientSqlEngine, IBusinessModel bm, Map dataMap, boolean deal_all) {

        List<Map> dataList = new ArrayList<Map>();
        dataList.add(dataMap);
        dataChangeModel(orientSqlEngine, bm, dataList, deal_all);
    }


    /**
     * 根据表将数据的真实值转换成显示值(Map集合转换)
     */
    public void dataChangeModel(ISqlEngine orientSqlEngine, IBusinessModel bm, List<Map> dataList, boolean deal_all) {

        if (dataList.size() > 0) {
            List<IBusinessColumn> businessColumns = new ArrayList<IBusinessColumn>();
            if (deal_all) {
                businessColumns = bm.getAllBcCols();
            } else {
                businessColumns = bm.getAllBcCols();
            }
            dataChangeModel(orientSqlEngine, businessColumns, dataList);
        }
    }


    /**
     * 根据列将数据的真实值转换成显示值(单个Map转换)
     */
    public void dataChangeModel(ISqlEngine orientSqlEngine, List<IBusinessColumn> businessColumns, List<Map> dataList) {

        for (IBusinessColumn businessColumn : businessColumns) {
            IColumn column = businessColumn.getCol();
            dataChangeColumn(orientSqlEngine, column, dataList);
        }
    }

    public Map<IBusinessColumn, List<String>> dataValueColumnChange(ISqlEngine orientSqlEngine, List<IBusinessColumn> businessColumns, List<Map> dataList) {

        Map<IBusinessColumn, List<String>> retValues = new HashMap<>();
        Map<String, IBusinessColumn> columnMap = new HashMap<>();
        for (IBusinessColumn col : businessColumns) {
            String colName = col.getCol().getColumnName();
            columnMap.put(colName, col);
        }

        for (IBusinessColumn businessColumn : businessColumns) {
            IColumn column = businessColumn.getCol();
            dataChangeColumn(orientSqlEngine, column, dataList);
        }
        int rowIndex = 0;
        for (Map dataValue : dataList) {

            Map<IBusinessColumn, String> row = new HashMap<>();
            for (Object key : dataValue.keySet()) {
                String value = CommonTools.Obj2String(dataValue.get(key));
                IBusinessColumn businessColumn = columnMap.get(key);
                if (businessColumn == null) continue;
                if (rowIndex == 0) {

                    List<String> values = new ArrayList<>();

                    values.add(value);
                    retValues.put(businessColumn, values);
                } else {
                    retValues.get(businessColumn).add(value);

                }
//                retValues.get(businessColumn);
//                row.put(businessColumn, value);

            }
            rowIndex++;
        }
        return retValues;
    }


    public void dataChangeColumn(ISqlEngine orientSqlEngine, IColumn column, List<Map> dataList) {


        String columnName = column.getColumnName();
        //普通属性且存在枚举
        if (column.getCategory() == IColumn.CATEGORY_COMMON && column.getRestriction() != null) {

            Restriction restriction = column.getRestriction();
            // 数据约束的类型，1表示枚举约束，2表示数据表枚举约束，3表示范围约束4.动态范围约束
            if (restriction.getType() == 1) {
                List<IEnum> enumList = restriction.getAllEnums();//获取枚举值
                Map<String, String> enumMap = enumListToMap(enumList);
                for (Map<String, String> dataMap : dataList) {
                    String value = CommonTools.Obj2String(dataMap.get(columnName));
                    if (!value.equals("")) {
                        dataMap.put(columnName, changeValue(value, enumMap));
                    }

                }
            } else if (restriction.getType() == 2) {
                ITable enumTable = restriction.getTableEnum().getTable();
                String displayColumnDBName = restriction.getTableEnum().getColumn().getColumnName();
                String ids = "";
                for (Map<String, String> dataMap : dataList) {
                    String value = CommonTools.Obj2String(dataMap.get(columnName));
                    if (!value.equals("")) {
                        String[] values = (value + ",").split(",");
                        for (String subValue : values) {
                            ids += "," + subValue;
                        }
                    }
                }
                ids = ids.equals("") ? "" : ids.substring(1);
                String[] idArray = ids.split(",");
                //解决id in()里的参数个数超过1000的问题
                if (idArray.length <= 1000) {
                     /*加入ID过滤条件*/
                    IBusinessModel refModel = getBusinessModelById(enumTable.getId(), BusinessModelEnum.getBusinessModelType(String.valueOf(enumTable.getMatrixType())));
                    CustomerFilter filter = new CustomerFilter("ID", SqlOperation.In, ids);
                    refModel.appendCustomerFilter(filter);
                    IBusinessModelQuery modelQuery = orientSqlEngine.getBmService().createModelQuery(refModel);
                    List<Map<String, String>> refList = modelQuery.list();
                    for (Map<String, String> dataMap : dataList) {
                        List<Map<String, String>> jsonDatas = new ArrayList<>();
                        String value = CommonTools.Obj2String(dataMap.get(columnName));
                        if (!value.equals("")) {
                            String varIds[] = value.split(",");
                            for (String id : varIds) {
                                Map<String, String> enumMap = listToMap(displayColumnDBName, refList);
                                String showValue = changeValue(id, enumMap);
                                Map<String, String> jsonData = new HashMap<>();
                                jsonData.put("id", id);
                                jsonData.put("name", showValue);
                                jsonDatas.add(jsonData);
                            }
                        }
                        dataMap.put(columnName + "_display", JsonUtil.toJson(jsonDatas));
                    }
                }else {
                    IBusinessModel refModel = getBusinessModelById(enumTable.getId(), BusinessModelEnum.getBusinessModelType(String.valueOf(enumTable.getMatrixType())));
                    List<Map<String, Object>> refList = metadaofactory.getJdbcTemplate().queryForList("SELECT * FROM " + refModel.getS_table_name() + " WHERE " + getOracleSQLIn(Arrays.asList(idArray), 1000, "ID"));
                    for (Map<String, String> dataMap : dataList) {
                        List<Map<String, String>> jsonDatas = new ArrayList<>();
                        String value = CommonTools.Obj2String(dataMap.get(columnName));
                        if (!value.equals("")) {
                            String varIds[] = value.split(",");
                            for (String id : varIds) {
                                String showValue = "";
                                for (IBusinessColumn keyColumn : refModel.getRefShowColumns()) {
                                    Map<String, String> enumMap = listToMap(refList, keyColumn.getCol().getColumnName());
                                    showValue += "-" + changeValue(id, enumMap);
                                }
                                showValue = showValue.substring(1);
                                Map<String, String> jsonData = new HashMap<>();
                                jsonData.put("id", id);
                                jsonData.put("name", showValue);
                                jsonDatas.add(jsonData);
                            }
                            dataMap.put(columnName + "_display", JsonUtil.toJson(jsonDatas));
                        }
                    }
                }

            }
        } else if (column.getCategory() == IColumn.CATEGORY_RELATION) {

            IRelationColumn refColumn = column.getRelationColumnIF();
            ITable refTable = refColumn.getRefTable();
            columnName = (refTable.getTableName() + "_ID").toUpperCase();

            //只能把id值单取出来，然后用in语句进行过滤
            String ids = "";
            for (Map<String, String> dataMap : dataList) {
                String value = CommonTools.Obj2String(dataMap.get(columnName));
                if (!value.equals("")) {
                    String[] values = (value + ",").split(",");
                    for (String subValue : values) {
                        ids += "," + subValue;
                    }
                }
            }
            ids = ids.equals("") ? "" : ids.substring(1);
            String[] idArray = ids.split(",");
            if (idArray.length <= 1000) {
                /*加入ID过滤条件*/
                IBusinessModel refModel = getBusinessModelById(refTable.getId(), BusinessModelEnum.getBusinessModelType(String.valueOf(refTable.getMatrixType())));
                CustomerFilter filter = new CustomerFilter("ID", SqlOperation.In, ids);
                refModel.appendCustomerFilter(filter);
                IBusinessModelQuery modelQuery = orientSqlEngine.getBmService().createModelQuery(refModel);
                List<Map<String, String>> refList = modelQuery.list();
                for (Map<String, String> dataMap : dataList) {
                    List<Map<String, String>> jsonDatas = new ArrayList<>();
                    String value = CommonTools.Obj2String(dataMap.get(columnName));
                    if (!value.equals("")) {
                        String varIds[] = value.split(",");
                        for (String id : varIds) {
                            String showValue = "";
                            for (IBusinessColumn keyColumn : refModel.getRefShowColumns()) {

                                Map<String, String> enumMap = listToMap(keyColumn.getCol().getColumnName(), refList);
                                showValue += "-" + changeValue(id, enumMap);
                            }
                            showValue = showValue.substring(1);
                            Map<String, String> jsonData = new HashMap<>();
                            jsonData.put("id", id);
                            jsonData.put("name", showValue);
                            jsonDatas.add(jsonData);
                        }
                        dataMap.put(columnName + "_display", JsonUtil.toJson(jsonDatas));
                    }
                }
                //存为json格式
            } else {
                IBusinessModel refModel = getBusinessModelById(refTable.getId(), BusinessModelEnum.getBusinessModelType(String.valueOf(refTable.getMatrixType())));
                List<Map<String, Object>> refList = metadaofactory.getJdbcTemplate().queryForList("SELECT * FROM " + refModel.getS_table_name() + " WHERE " + getOracleSQLIn(Arrays.asList(idArray), 1000, "ID"));
                for (Map<String, String> dataMap : dataList) {
                    List<Map<String, String>> jsonDatas = new ArrayList<>();
                    String value = CommonTools.Obj2String(dataMap.get(columnName));
                    if (!value.equals("")) {
                        String varIds[] = value.split(",");
                        for (String id : varIds) {
                            String showValue = "";
                            for (IBusinessColumn keyColumn : refModel.getRefShowColumns()) {
                                Map<String, String> enumMap = listToMap(refList, keyColumn.getCol().getColumnName());
                                showValue += "-" + changeValue(id, enumMap);
                            }
                            showValue = showValue.substring(1);
                            Map<String, String> jsonData = new HashMap<>();
                            jsonData.put("id", id);
                            jsonData.put("name", showValue);
                            jsonDatas.add(jsonData);
                        }
                        dataMap.put(columnName + "_display", JsonUtil.toJson(jsonDatas));
                    }
                }
            }

        } else if (column.getCategory() == IColumn.CATEGORY_COMMON && !CommonTools.isNullString(column.getSelector())) {
            String selectorJson = column.getSelector();
            JSONObject selector = JSONObject.fromObject(selectorJson);
            String selectorValue = selector.getString("selectorType");
            for (Map<String, String> dataMap : dataList) {
                String value = CommonTools.Obj2String(dataMap.get(columnName));
                if (!"".equals(value)) {
                    List<String> realValues = CommonTools.arrayToList(value.split(","));
                    String displayValue = "";
                    if ("1".equals(selectorValue)) {
                        //用户选择器
                        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
                        List<String> displayValues = new ArrayList<>();
                        users.forEach((userId, user) -> {
                            if (realValues.contains(userId)) {
                                displayValues.add(user.getAllName());
                            }
                        });
                        displayValue = CommonTools.list2String(displayValues);
                    } else if ("0".equals(selectorValue)) {
                        //单部门
                        Map<Integer, Department> depts = roleEngine.getRoleModel(false).getDepartments();
                        List<String> displayValues = new ArrayList<>();
                        depts.forEach((depId, department) -> {
                            if (realValues.contains(depId.toString())) {
                                displayValues.add(department.getName());
                            }
                        });
                        displayValue = CommonTools.list2String(displayValues);
                    }
                    dataMap.put(columnName + "_display", displayValue);
                }
            }
        } else if (column.getCategory() == IColumn.CATEGORY_COMMON && !CommonTools.isNullString(column.getUnit())) {
            String unitJson = column.getUnit();
            JSONObject unit = JSONObject.fromObject(unitJson);
            String unitName = unit.getString("unitName");
            List<Map<String, Object>> unitList = orientSqlEngine.getSysModelService().queryNumberUnitByName(unitName);
            //找出基准单位
            Map<String, Object> baseUnit = null;
            for (Map<String, Object> map : unitList) {
                if ("1".equals(CommonTools.Obj2String(map.get("IS_BASE")))) {
                    baseUnit = map;
                    break;
                }
            }
            String baseUnitId = CommonTools.Obj2String(baseUnit.get("ID"));
            String baseUnitName = CommonTools.Obj2String(baseUnit.get("UNIT"));
            JSONArray selUnitIds = unit.getJSONArray("selectorIds");
            //减少查询次数
            List<Map<String, Object>> units = orientSqlEngine.getSysModelService().queryNumberUnit();
            List<Object> dataIds = dataList.stream().map(dataMap -> dataMap.get("ID")).collect(Collectors.toList());
            List<CwmModelDataUnitEntity> modelDataUnitEntities = modelDataUnitService.list(Restrictions.eq("modelId", column.getRefMatrix().getId())
                    , Restrictions.in("dataId", dataIds), Restrictions.eq("sColumnName", columnName));
            if (!modelDataUnitEntities.isEmpty()) {
                for (Map<String, String> dataMap : dataList) {
                    String dataId = dataMap.get("ID");
                    //基准值
                    String value = CommonTools.Obj2String(dataMap.get(columnName));
                    //转化为当时所选单位值
                    Boolean containsUnitMap = modelDataUnitEntities.stream().filter(modelDataUnitEntitie -> modelDataUnitEntitie.getDataId().equals(dataId)).count() > 0;
                    Boolean isSavedBase = modelDataUnitEntities.stream().filter(modelDataUnitEntitie -> modelDataUnitEntitie.getDataId().equals(dataId) && modelDataUnitEntitie.getUnitId().equals(baseUnitId)).count() > 0;
                    String savedUnit = baseUnitId;
                    if (containsUnitMap && !isSavedBase) {
                        //如果新增或者编辑的时候不是标准单位,则需要转化
                        CwmModelDataUnitEntity savedModelDataUnit = modelDataUnitEntities.stream().filter(modelDataUnitEntitie -> modelDataUnitEntitie.getDataId().equals(dataId)).findFirst().get();
                        savedUnit = savedModelDataUnit.getUnitId();
                        //转化数值
                        final String finalSavedUnit = savedUnit;
                        Map<String, Object> selUnitInfo = units.stream().filter(unitMap -> unitMap.get("ID").equals(finalSavedUnit)).findFirst().get();
                        String formulaOut = CommonTools.Obj2String(selUnitInfo.get("FORMULA_OUT"));
                        String origiNalValue = SqlEngineHelper.unitCalculate(value, baseUnitName, formulaOut);
                        dataMap.put(columnName + "_STANDVALUE", value);
                        dataMap.put(columnName, origiNalValue);
                    }
                    //保存单位信息
                    dataMap.put(columnName + "_unit", savedUnit);
                }
            }
        }
    }


    /*将枚举List转换成Map*/
    private Map<String, String> enumListToMap(List<IEnum> enumList) {

        Map<String, String> enum_Map = new HashMap<>();
        for (IEnum iEnum : enumList) {
            enum_Map.put(iEnum.getValue(), iEnum.getDisplayValue());
        }
        return enum_Map;
    }


    /*将枚举List转换成Map*/
    private Map<String, String> listToMap(String key, List<Map<String, String>> dataList) {

        Map<String, String> enum_Map = new HashMap<>();
        for (Map<String, String> map : dataList) {
            String value = CommonTools.Obj2String(map.get("ID"));
            String display_value = CommonTools.Obj2String(map.get(key));
            enum_Map.put(value, display_value);
        }
        return enum_Map;
    }

    private Map<String, String> listToMap(List<Map<String, Object>> dataList, String key) {

        Map<String, String> enum_Map = new HashMap<>();
        for (Map<String, Object> map : dataList) {
            String value = CommonTools.Obj2String(map.get("ID"));
            String display_value = CommonTools.Obj2String(map.get(key));
            enum_Map.put(value, display_value);
        }
        return enum_Map;
    }

    /*数据替换*/
    private String changeValue(String value, Map change_map) {
        if (!value.equals("")) {
            if (value.indexOf(",") != -1 || value.indexOf(";") != -1) {
                String split = value.indexOf(",") != -1 ? "," : ";";
                /*多个值时*/
                String[] values = value.split(split);//拆分串
                value = "";
                for (String sub_value : values) {
                    String resultVal = "";
                    if (change_map.containsKey(sub_value)) {
                        resultVal = (String) change_map.get(sub_value);
                    } else {
                        resultVal = "无效值";
                    }
                    value = value + resultVal + split;//拼接枚举值
                }
                if (!value.equals("")) {
                    value = value.substring(0, value.length() - 1);//如果最后一位是','则去掉
                }
            } else {
                /*单个值或空值时*/
                if (change_map.containsKey(value)) {
                    value = (String) change_map.get(value);
                } else {
                    value = "无效值";
                }
            }
        }
        return value;
    }

    public MetaUtil getMetaEngine() {
        return metaEngine;
    }

    public void setMetaEngine(MetaUtil metaEngine) {
        this.metaEngine = metaEngine;
    }

    public IRoleUtil getRoleEngine() {
        return roleEngine;
    }

    public void setRoleEngine(IRoleUtil roleEngine) {
        this.roleEngine = roleEngine;
    }

    public MetaDAOFactory getMetadaofactory() {
        return metadaofactory;
    }

    public void setMetadaofactory(MetaDAOFactory metadaofactory) {
        this.metadaofactory = metadaofactory;
    }

    /**
     * 当id in() 里面的参数个数超过1000时，需要把多余的吃啊分为or in
     *
     * @param ids   id集合
     * @param count or in()里的个数，一般是1000
     * @param field 查询字段
     * @return
     */
    private String getOracleSQLIn(List<String> ids, int count, String field) {
        count = Math.max(count, 1000);
        int len = ids.size();
        int size = len % count;
        if (size == 0) {
            size = len / count;
        } else {
            size = (len / count) + 1;
        }
        StringBuffer sql = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int fromIndex = i * count;
            int toIndex = Math.min(fromIndex + count, len);
            String productId = StringUtils.defaultIfEmpty(StringUtils.join(ids.subList(fromIndex, toIndex), "','"), "");
            if (i != 0) {
                sql.append(" or ");
            }
            sql.append(field).append(" in ('").append(productId).append("')");
        }
        return StringUtils.defaultIfEmpty(sql.toString(), field + " in ('')");
    }

}
