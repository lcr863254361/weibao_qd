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
            log.error("????????????????????????,?????????ID????????????");
            throw new ModelExcepion("????????????????????????,?????????ID????????????");
        }
        if (null == modelType) {
            log.error("????????????????????????,???????????????(Table???View)????????????");
            throw new ModelExcepion("????????????????????????,???????????????(Table???View)????????????");
        }
        /*???????????????????????????matrix*/
        IMatrix matrix = getByModelId(schemaId, modelType, modelId);
        if (null == matrix) {
            //??????
            modelType = modelType.equals(BusinessModelEnum.Table) ? BusinessModelEnum.View : BusinessModelEnum.Table;
            matrix = getByModelId(schemaId, modelType, modelId);
            if (null == matrix) {
                log.error("????????????????????????,????????????????????????[ID=" + modelId + "]");
                throw new ModelExcepion("????????????????????????,????????????????????????[ID=" + modelId + "]");
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
            //schemaid?????????????????????schema
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
            //schemaid?????????????????????schema
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
            log.error("????????????????????????,???????????????????????????");
            throw new ModelExcepion("????????????????????????,???????????????????????????");
        }
        if (null == modelType) {
            log.error("????????????????????????,???????????????(Table???View)????????????");
            throw new ModelExcepion("????????????????????????,???????????????(Table???View)????????????");
        }
        if (null == schemaId) {
            log.error("????????????????????????,Schema????????????");
            throw new ModelExcepion("????????????????????????,Schema????????????");
        }
        IMatrix matrix = getBySName(schemaId, modelType, sModelName);
        if (null == matrix) {
            //??????
            modelType = modelType.equals(BusinessModelEnum.Table) ? BusinessModelEnum.View : BusinessModelEnum.Table;
            matrix = getBySName(schemaId, modelType, sModelName);
            if (null == matrix) {
                log.error("????????????????????????,????????????????????????[Name=" + sModelName + "]");
                throw new ModelExcepion("????????????????????????,????????????????????????[Name=" + sModelName + "]");
            }
        }
        return createBusinessModel(matrix, userid);
    }

    /**
     * ??????????????????
     * ????????????ID????????????????????????????????????
     *
     * @param matrix ???????????????
     * @param userid ??????ID
     * @return IBusinessModel`
     * @Method: createBusinessModel
     */
    private IBusinessModel createBusinessModel(IMatrix matrix, String userid) {
        Long start = System.currentTimeMillis();
        BusinessModel bm = new BusinessModel(matrix);
        if (null != userid && !("").equals(userid)) {
            MatrixRight matrixright = new MatrixRight();
            //?????????????????????Matrix???
            IRoleModel roleModel = roleEngine.getRoleModel(false);
            User user = (User) roleModel.getUserById(userid);
            bm.setUserSecrecy(user.getGrade());
            //??????userid??????????????????
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
     * ????????????????????????????????????
     *
     * @param fromRight ??????Matrix??????
     * @param toRight   ??????Matrix??????
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
            /*?????????????????????*/
            if (!StringUtil.isEmpty(fromRight.getFilter())) {
                if (!StringUtil.isEmpty(toRight.getFilter())) {
                    //????????????
                    String filter = "(" + toRight.getFilter() + ") or (" + fromRight.getFilter() + ")";
                    toRight.setFilter(filter);
                } else {
                    toRight.setFilter(fromRight.getFilter());
                }
            }
            if (!StringUtil.isEmpty(fromRight.getUserFilter())) {
                if (!StringUtil.isEmpty(toRight.getUserFilter())) {
                    //????????????
                    String filter = "(" + toRight.getUserFilter() + ") or (" + fromRight.getUserFilter() + ")";
                    toRight.setUserFilter(filter);
                } else {
                    toRight.setUserFilter(fromRight.getUserFilter());
                }
            }
            /** ????????????????????????????????????*/
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
//        //??????Schema??????
//        List<List<TableEdge>> tableEdgeListList = graphTravel.travel(relModel.getMatrix().getMainTable(), mainModel.getMatrix().getMainTable());
//
//        if (null == tableEdgeListList) {
//            log.error("????????????[" + mainModel.getDisplay_name() + "???" + relModel.getDisplay_name() + "???????????????");
//            throw new ModelExcepion("?????????????????????????????????");
//        }
//        //??????????????????????????????????????????????????????
//        List<List<BusinessModelEdge>> bmEdgeListList = new ArrayList<List<BusinessModelEdge>>();
//        //??????????????????
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
        //????????????????????????
        BusinessModelEdge bmEdge = null;
        if (relModel.getId().equals(mainModel.getId())) {//???????????????
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
        if (op == null) {//??????????????????????????????????????????????????????
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
            if (null == bc) { //??????????????????
                fiterList.remove(filter);
                continue;
            }
            if (filter.getOperation() == SqlOperation.IsNull
                    || filter.getOperation() == SqlOperation.IsNotNull) {
                //??????????????????
                filter.setFilterValue("");
                bm.appendCustomerFilter(filter);
                continue;
            }
            if (BusinessModelHelper.isNullString(filter.getFilterValue())) {
                //??????????????????
                fiterList.remove(filter);
                continue;
            }
            if (filter.getOperation() == null) {
                //??????????????????
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
     * ??????????????????
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
     * ??????????????????
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
     * ????????????????????????????????????????????????(??????Map??????)
     */
    public void dataChangeModel(ISqlEngine orientSqlEngine, IBusinessModel bm, Map dataMap, boolean deal_all) {

        List<Map> dataList = new ArrayList<Map>();
        dataList.add(dataMap);
        dataChangeModel(orientSqlEngine, bm, dataList, deal_all);
    }


    /**
     * ????????????????????????????????????????????????(Map????????????)
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
     * ????????????????????????????????????????????????(??????Map??????)
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
        //???????????????????????????
        if (column.getCategory() == IColumn.CATEGORY_COMMON && column.getRestriction() != null) {

            Restriction restriction = column.getRestriction();
            // ????????????????????????1?????????????????????2??????????????????????????????3??????????????????4.??????????????????
            if (restriction.getType() == 1) {
                List<IEnum> enumList = restriction.getAllEnums();//???????????????
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
                //??????id in()????????????????????????1000?????????
                if (idArray.length <= 1000) {
                     /*??????ID????????????*/
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

            //?????????id???????????????????????????in??????????????????
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
                /*??????ID????????????*/
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
                //??????json??????
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
                        //???????????????
                        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
                        List<String> displayValues = new ArrayList<>();
                        users.forEach((userId, user) -> {
                            if (realValues.contains(userId)) {
                                displayValues.add(user.getAllName());
                            }
                        });
                        displayValue = CommonTools.list2String(displayValues);
                    } else if ("0".equals(selectorValue)) {
                        //?????????
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
            //??????????????????
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
            //??????????????????
            List<Map<String, Object>> units = orientSqlEngine.getSysModelService().queryNumberUnit();
            List<Object> dataIds = dataList.stream().map(dataMap -> dataMap.get("ID")).collect(Collectors.toList());
            List<CwmModelDataUnitEntity> modelDataUnitEntities = modelDataUnitService.list(Restrictions.eq("modelId", column.getRefMatrix().getId())
                    , Restrictions.in("dataId", dataIds), Restrictions.eq("sColumnName", columnName));
            if (!modelDataUnitEntities.isEmpty()) {
                for (Map<String, String> dataMap : dataList) {
                    String dataId = dataMap.get("ID");
                    //?????????
                    String value = CommonTools.Obj2String(dataMap.get(columnName));
                    //??????????????????????????????
                    Boolean containsUnitMap = modelDataUnitEntities.stream().filter(modelDataUnitEntitie -> modelDataUnitEntitie.getDataId().equals(dataId)).count() > 0;
                    Boolean isSavedBase = modelDataUnitEntities.stream().filter(modelDataUnitEntitie -> modelDataUnitEntitie.getDataId().equals(dataId) && modelDataUnitEntitie.getUnitId().equals(baseUnitId)).count() > 0;
                    String savedUnit = baseUnitId;
                    if (containsUnitMap && !isSavedBase) {
                        //???????????????????????????????????????????????????,???????????????
                        CwmModelDataUnitEntity savedModelDataUnit = modelDataUnitEntities.stream().filter(modelDataUnitEntitie -> modelDataUnitEntitie.getDataId().equals(dataId)).findFirst().get();
                        savedUnit = savedModelDataUnit.getUnitId();
                        //????????????
                        final String finalSavedUnit = savedUnit;
                        Map<String, Object> selUnitInfo = units.stream().filter(unitMap -> unitMap.get("ID").equals(finalSavedUnit)).findFirst().get();
                        String formulaOut = CommonTools.Obj2String(selUnitInfo.get("FORMULA_OUT"));
                        String origiNalValue = SqlEngineHelper.unitCalculate(value, baseUnitName, formulaOut);
                        dataMap.put(columnName + "_STANDVALUE", value);
                        dataMap.put(columnName, origiNalValue);
                    }
                    //??????????????????
                    dataMap.put(columnName + "_unit", savedUnit);
                }
            }
        }
    }


    /*?????????List?????????Map*/
    private Map<String, String> enumListToMap(List<IEnum> enumList) {

        Map<String, String> enum_Map = new HashMap<>();
        for (IEnum iEnum : enumList) {
            enum_Map.put(iEnum.getValue(), iEnum.getDisplayValue());
        }
        return enum_Map;
    }


    /*?????????List?????????Map*/
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

    /*????????????*/
    private String changeValue(String value, Map change_map) {
        if (!value.equals("")) {
            if (value.indexOf(",") != -1 || value.indexOf(";") != -1) {
                String split = value.indexOf(",") != -1 ? "," : ";";
                /*????????????*/
                String[] values = value.split(split);//?????????
                value = "";
                for (String sub_value : values) {
                    String resultVal = "";
                    if (change_map.containsKey(sub_value)) {
                        resultVal = (String) change_map.get(sub_value);
                    } else {
                        resultVal = "?????????";
                    }
                    value = value + resultVal + split;//???????????????
                }
                if (!value.equals("")) {
                    value = value.substring(0, value.length() - 1);//?????????????????????','?????????
                }
            } else {
                /*?????????????????????*/
                if (change_map.containsKey(value)) {
                    value = (String) change_map.get(value);
                } else {
                    value = "?????????";
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
     * ???id in() ???????????????????????????1000????????????????????????????????????or in
     *
     * @param ids   id??????
     * @param count or in()????????????????????????1000
     * @param field ????????????
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
