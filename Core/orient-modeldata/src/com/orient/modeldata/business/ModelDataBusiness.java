package com.orient.modeldata.business;

import com.orient.background.bean.OrientTreeColumnDesc;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.config.ConfigInfo;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metadomain.RelationColumns;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.metaengine.dao.RelationColumnsDAO;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.modeldata.bean.ImportDataBean;
import com.orient.modeldata.bean.ModelNode;
import com.orient.modeldata.dataanalyze.analyzeContext.AnalyzeContext;
import com.orient.modeldata.validateHandler.bean.ValidateError;
import com.orient.modeldata.validateHandler.builder.concretebuilder.DefaultModelDataValidatorBuilder;
import com.orient.modeldata.validateHandler.builder.director.DefaultModelDataValidatorDirector;
import com.orient.modeldata.validateHandler.builder.director.IModelDataValidatorDirector;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.ExcelUtil.style.Align;
import com.orient.utils.ExcelUtil.style.BorderStyle;
import com.orient.utils.ExcelUtil.style.Color;
import com.orient.utils.FileOperator;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.base.ExtSorter;
import com.orient.web.model.BaseNode;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.util.UserContextUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.orient.utils.JsonUtil.getJavaCollection;

/**
 * 模型数据业务处理
 *
 * @author enjoy
 * @creare 2016-04-01 13:27
 */
@Service
public class ModelDataBusiness extends BaseBusiness {


    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    RelationColumnsDAO relationColumnsDAO;

    @Autowired
    @Qualifier("defaultModelDataValidatorBuilder")
    DefaultModelDataValidatorBuilder defaultModelDataValidatorBuilder;

    /**
     * 获取模型数据
     *
     * @param orientModelId 模型 | 视图ID
     * @return
     */
    public ExtGridData<Map> getModelDataByModelId(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort) {
        List<CustomerFilter> customerFilters = new ArrayList<>();
        if (!StringUtils.isEmpty(customerFilter)) {
            Map clazzMap = new HashMap();
            customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, clazzMap);
        }
        return getModelDataByModelId(orientModelId, isView, page, pagesize, customerFilters, dataChange, sort);
    }

    public ExtGridData<Map> getModelDataByModelId(String orientModelId, String isView, Integer page, Integer pagesize, List<CustomerFilter> customerFilters, Boolean dataChange, String sort) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        if (!CommonTools.isEmptyList(customerFilters)) {
            customerFilters.forEach(cs -> businessModel.appendCustomerFilter(cs));
        }
        long count = orientSqlEngine.getBmService().createModelQuery(businessModel).count();
        IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
        if (null != page && null != pagesize) {
            int start = (page - 1) * pagesize;
            int end = page * pagesize > count ? (int) count : (page * pagesize);
            businessModelQuery.page(start, end);
        }
        if (!StringUtil.isEmpty(sort)) {
            List<ExtSorter> sorters = JsonUtil.getJavaCollection(new ExtSorter(), sort);
            sorters.forEach(loopSort -> {
                if ("ASC".equals(loopSort.getDirection())) {
                    businessModelQuery.orderAsc(loopSort.getProperty());
                } else if ("DESC".equals(loopSort.getDirection())) {
                    businessModelQuery.orderDesc(loopSort.getProperty());
                }
            });
        }else {
            businessModelQuery.orderAsc("TO_NUMBER(ID)");   //如果不指定排序字段，就按照数字类型的id顺序排序
        }
        List<Map> dataList = businessModelQuery.list();
        if (dataChange) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            customDataChange(businessModel, dataList);
        }

        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    public void customDataChange(IBusinessModel businessModel, List<Map> dataList) {
        if (dataList.size() > 0) {
            List<IBusinessColumn> businessColumns = businessModel.getAllBcCols();
            for (IBusinessColumn businessColumn : businessColumns) {
                IColumn column = businessColumn.getCol();
                String columnName = column.getColumnName();
                if (column.getCategory() == IColumn.CATEGORY_COMMON && !CommonTools.isNullString(column.getSelector())) {
                    String selectorJson = column.getSelector();
                    JSONObject selector = JSONObject.fromObject(selectorJson);
                    String selectorValue = selector.getString("selectorType");
                    for (Map<String, String> dataMap : dataList) {
                        String value = CommonTools.Obj2String(dataMap.get(columnName));
                        if (!"".equals(value)) {
                            if ("4".equals(selectorValue)) {
                                //设备选择器
                                List<String> displayValues = new ArrayList<>();
                                IBusinessModel bm = businessModelService.getBusinessModelBySName("T_DEVICE", ConfigInfo.DEVICE_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
                                bm.setReserve_filter(" AND ID IN (" + value + ")");
                                List<Map<String, String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
                                for (Map<String, String> map : list) {
                                    displayValues.add(map.get("C_NAME_" + bm.getId()));
                                }
                                String displayValue = CommonTools.list2String(displayValues);
                                if (displayValue == null || "".equals(displayValue)) {
                                    displayValue = "无效值";
                                }
                                dataMap.put(columnName + "_display", displayValue);
                            } else if ("5".equals(selectorValue)) {
                                //tableName和schemaID选择器
                                List<String> displayValues = new ArrayList<>();
                                String tableName = selector.getString("tableName");
                                String schemaId = selector.getString("schemaId");
                                try {
                                    Field field = ConfigInfo.class.getField(schemaId);
                                    if (field != null) {
                                        schemaId = (String) field.get(null);
                                    } else {
                                        schemaId = null;
                                    }
                                    if (schemaId != null) {
                                        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
                                        if (bm != null) {
                                            IBusinessColumn showCol = bm.getRefShowColumns().get(0);
                                            bm.setReserve_filter(" AND ID IN (" + value + ")");
                                            List<Map<String, String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
                                            for (Map<String, String> map : list) {
                                                String display = map.get(showCol.getS_column_name());
                                                if (display != null) {
                                                    displayValues.add(display);
                                                } else {
                                                    displayValues.add("无效值");
                                                }
                                            }
                                            String displayValue = CommonTools.list2String(displayValues);
                                            dataMap.put(columnName + "_display", displayValue);
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取数值型的数据列表,并且转化成以列的方式存储
     *
     * @param orientModelId
     * @param isView
     * @param page
     * @param pagesize
     * @param customerFilter
     * @return
     */
    public Map<IBusinessColumn, List<String>> getNumricModelDataByModelId(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter) {
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        if (!StringUtils.isEmpty(customerFilter)) {
            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter);
            customerFilters.forEach(cs -> businessModel.appendCustomerFilter(cs));
        }
        long count = orientSqlEngine.getBmService().createModelQuery(businessModel).count();
        IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
        if (null != page && null != pagesize) {
            int start = (page - 1) * pagesize;
            int end = page * pagesize > count ? (int) count : page * pagesize;
            businessModelQuery.page(start, end);
        }
        List<Map> dataList = businessModelQuery.list();
        List<IBusinessColumn> numColumns = new ArrayList<>();
        for (IBusinessColumn column : businessModel.getAllBcCols()) {


            if (column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Double ||
                    column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_BigInteger ||
                    column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Float ||
                    column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Integer ||
                    column.getColType() == EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Decimal) {
                numColumns.add(column);

            }

        }
        return businessModelService.dataValueColumnChange(orientSqlEngine, numColumns, dataList);

    }

    /**
     * 刪除模型數據
     *
     * @param modelId  模型 | 视图ID
     * @param toDelIds 待删除的数据ID集合
     */
    public void delete(String modelId, Long[] toDelIds, String isCascade) {
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        if (isCascade != null && isCascade.equals("true")) {
            orientSqlEngine.getBmService().deleteCascade(businessModel, CommonTools.array2String(toDelIds));
        } else {
            orientSqlEngine.getBmService().delete(businessModel, CommonTools.array2String(toDelIds));
        }

    }

    /**
     * @param modelId 模型 | 视图ID
     * @param dataId  数据ID
     * @return 根據模型ID 以及 數據ID 獲取數據描述u
     */
    public Map getModelDataByModelIdAndDataId(String modelId, String dataId) {
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
       if (!dataId.isEmpty()){
           CustomerFilter customerFilter = new CustomerFilter("ID", EnumInter.SqlOperation.Equal, dataId);
           businessModel.appendCustomerFilter(customerFilter);
       }
        IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
        List<Map> dataList = businessModelQuery.list();
        if (dataList.size() > 0) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            return dataList.get(0);
        } else
            return new HashMap<>();
    }

    /**
     * @param node     父节点ID
     * @param schemaId 所属schmeaId
     * @return 获取模型树形展现数据
     */
    public List<ModelNode> getModelNodes(String node, String schemaId) {
        List<ModelNode> retVal = new ArrayList<>();
        if ("root".equals(node)) {
            retVal.add(new ModelNode("0", "数据类", "", false, "数据类"));
            retVal.add(new ModelNode("1", "视图", "", false, "视图"));
        } else if (!StringUtil.isEmpty(schemaId) && !StringUtil.isEmpty(node)) {
            ISchema iSchema = metaEngine.getMeta(false).getISchemaById(schemaId);
            if ("1".equals(node)) {
                //视图
                iSchema.getAllViews().forEach(iView -> {
                    retVal.add(new ModelNode(iView.getId(), iView.getDisplayName(), "", true, iView.getName()));
                });
            } else if ("0".equals(node)) {
                //数据类
                iSchema.getAllTables().forEach(iTable -> {
                    retVal.add(new ModelNode(iTable.getId(), iTable.getDisplayName(), "", true, iTable.getTableName()));
                });
            }
        }
        return retVal;
    }

    /**
     * @param node 父节点ID
     * @return 获取所有schema下的所有模型的信息 采用树形方式展现
     */
    public List<ModelNode> getModelNodes(String node, Boolean containsView, String excludeSchemaIds, String[] excludedSchemaNames) {
        List<ModelNode> retVal = new ArrayList<>();
        List<String> exIdList = new ArrayList<>();
        if (!StringUtil.isEmpty(excludeSchemaIds)) {
            String[] excludeIds = excludeSchemaIds.split(",");
            exIdList = Arrays.asList(excludeIds);
        }
        if ("-1".equals(node)) {
            //加载schema信息
            if (null != excludedSchemaNames && excludedSchemaNames.length > 0) {
                for (String excludedSchemaName : excludedSchemaNames) {
                    List<String> excludeSchemaIdsByName = metaEngine.getMeta(false).getSchemas().values().stream().filter(schema -> excludedSchemaName.equals(schema.getName())).map(ISchema::getId).collect(Collectors.toList());
                    exIdList.addAll(excludeSchemaIdsByName);
                }
            }
            final List<String> finalExIdList = exIdList;
            metaEngine.getMeta(false).getSchemas().forEach((schemaId, schema) -> {
//                if (!finalExIdList.contains(schema.getId())) {
//                    retVal.add(new ModelNode("schema-" + schema.getId(), schema.getName(), "icon-schema", false, true, schema.getName()));
//                }
                retVal.add(new ModelNode("schema-" + schema.getId(), schema.getName(), "icon-schema", false, true, schema.getName()));
            });
        } else if (node.startsWith("schema")) {
            //加载通用分组节点
            String schemaId = node.substring(node.indexOf("-") + 1);
            retVal.add(new ModelNode("model-" + schemaId, "数据表", "icon-modelGroup", false, true, "数据表"));
            if (containsView) {
                retVal.add(new ModelNode("view-" + schemaId, "视图", "icon-viewGroup", false, true, "视图"));
            }
        } else if (node.startsWith("model")) {
            //加载某schema下的表信息
            String schemaId = node.substring(node.indexOf("-") + 1);
            ISchema iSchema = metaEngine.getMeta(false).getISchemaById(schemaId);
            //表
            iSchema.getAllTables().forEach(iTable -> {
                retVal.add(new ModelNode(iTable.getId(), iTable.getDisplayName(), "icon-model", true, iTable.getTableName()));
            });
        } else if (node.startsWith("view")) {
            //加载某schema下的视图信息
            String schemaId = node.substring(node.indexOf("-") + 1);
            ISchema iSchema = metaEngine.getMeta(false).getISchemaById(schemaId);
            iSchema.getAllViews().forEach(iView -> {
                retVal.add(new ModelNode(iView.getId(), iView.getDisplayName(), "icon-view", true, iView.getName()));
            });
        }
        return retVal;
    }

    /**
     * @param orientModelId 模型 | 视图ID
     * @return 根據模型ID 獲取 模型字段描述集合
     */
    public ExtComboboxResponseData<ColumnDesc> getModelColumCombobox(String orientModelId) {
        ExtComboboxResponseData<ColumnDesc> retVal = new ExtComboboxResponseData<>();
        if (!StringUtils.isEmpty(orientModelId)) {
            List<ColumnDesc> columnDescs = new ArrayList<>();
            IBusinessModel businessModel = businessModelService.getBusinessModelById(orientModelId, EnumInter.BusinessModelEnum.Table);
            if (null != businessModel) {
                businessModel.getAllBcCols().forEach(iBusinessColumn -> {
                    ColumnDesc orientExtColumn = new OrientTreeColumnDesc().init(iBusinessColumn);
                    columnDescs.add(orientExtColumn);
                });
            }
            retVal.setTotalProperty(columnDescs.size());
            retVal.setResults(columnDescs);
        }
        return retVal;
    }

    /**
     * @param file 待保存的文件
     * @return 上传附件 并解析数据
     */
    public TableEntity uploadAndanalyze(MultipartFile file) {
        String timeSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = file.getOriginalFilename();
        String finalFileName = timeSuffix + "_" + fileName;
        //保存文件
        String realFileStoragePath = fileServerConfig.getFtpHome() + File.separator + finalFileName;
        TableEntity dataSet = new TableEntity();
        try {
            FileOperator.createFile(realFileStoragePath, file.getBytes());
            //解析数据
            AnalyzeContext analyzeContext = new AnalyzeContext(new File(realFileStoragePath));
            dataSet = analyzeContext.doAnalyzeFile();
            dataSet.setSourceFileName(realFileStoragePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析文件
        return dataSet;
    }

    /**
     * 保存導入的數據
     *
     * @param importDataBean 前端数据预处理描述
     */
    public void saveImportData(ImportDataBean importDataBean) {
        AnalyzeContext analyzeContext = new AnalyzeContext(new File(importDataBean.getSourceFileName()));
        //解析文件
        TableEntity dataSet = analyzeContext.doAnalyzeFile();
        //获取数据
        List<Map<String, String>> dataList = extraDataSet(dataSet);
        //同步数据
        List<Map<String, String>> syncedData = syncData(dataList, importDataBean);
        //映射数据
        List<Map<String, String>> toSaveData = doMappingData(syncedData, importDataBean);
        //保存至数据库
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, importDataBean.getModelId(), null, EnumInter.BusinessModelEnum.BusinessModelEnum.Table);
        toSaveData.forEach(dataMap -> {
            orientSqlEngine.getBmService().insertModelData(businessModel, dataMap);
        });
    }

    /**
     * @param dataList       待导入数据集合
     * @param importDataBean 前端数据预处理描述
     * @return 根据选择的映射关系 重新组织数据
     */
    private List<Map<String, String>> doMappingData(List<Map<String, String>> dataList, ImportDataBean importDataBean) {
        List mappingValues = null;
        List<Map<String, String>> toSaveDataList = new ArrayList<>();
        mappingValues = JsonUtil.json2List(importDataBean.getMappingValue());
        List finalMappingValues = mappingValues;
        dataList.forEach(dataMap -> {
            Map<String, String> newDataMap = new HashMap<>();
            finalMappingValues.forEach(mapData -> {
                LinkedHashMap<String, String> mappingMap = (LinkedHashMap<String, String>) mapData;
                dataMap.forEach((key, value) -> {
                    if (mappingMap.get("headName").equals(key)) {
                        newDataMap.put((String) mappingMap.get("dbName"), value);
                    }
                });
            });
            toSaveDataList.add(newDataMap);
        });
        return toSaveDataList;
    }

    /**
     * @param dataList       待导入数据集合
     * @param importDataBean 前端数据预处理描述
     * @return 合并前端数据预处理以及解析文件的原始数据
     */
    private List<Map<String, String>> syncData(List<Map<String, String>> dataList, ImportDataBean importDataBean) {
        List<Map<String, String>> syncedData = new ArrayList<>();
        List<String> toRemoveIds = null != importDataBean.getDeletedData() ? CommonTools.arrayToList(importDataBean.getDeletedData()) : new ArrayList<>();
        dataList.forEach(dataMap -> {
            String id = dataMap.get("ID");
            if (!toRemoveIds.contains(id)) {
                //修改的
                List updatedData = getListFromJson(importDataBean.getUpdatedData());
                //更新數據
                final Boolean[] catched = {false};
                updatedData.forEach(data -> {
                    LinkedHashMap<String, String> updateDataMap = (LinkedHashMap<String, String>) data;
                    if (null != updateDataMap) {
                        String toUpdateDataId = (String) updateDataMap.get("ID");
                        if (id.equals(toUpdateDataId)) {
                            catched[0] = true;
                            Map<String, String> newDataMap = new HashMap<>();
                            newDataMap.putAll(dataMap);
                            updateDataMap.forEach((key, value) -> {
                                newDataMap.put(key, (String) value);
                            });
                            syncedData.add(newDataMap);
                        }
                    }
                });
                if (!catched[0]) {
                    //未變化數據
                    syncedData.add(dataMap);
                }
            }
        });
        //新增数据
        List createdData = getListFromJson(importDataBean.getCreatedData());
        createdData.forEach(data -> {
            LinkedHashMap<String, String> createDataMap = (LinkedHashMap<String, String>) data;
            if (null != createDataMap) {
                Map<String, String> newDataMap = new HashMap<>();
                createDataMap.forEach((key, value) -> {
                    newDataMap.put(key, (String) value);
                });
                syncedData.add(newDataMap);
            }
        });
        return syncedData;
    }

    /**
     * @param json
     * @return 将json字符串解析成list对象
     */
    private List getListFromJson(String json) {
        List retVal = new ArrayList();
        retVal = !StringUtil.isEmpty(json) ? JsonUtil.json2List(json) : new ArrayList();
        return retVal;
    }

    /**
     * @param dataSet 原始Excel解析出来的数据描述
     * @return 抽取excel解析出来的数据
     */
    private List<Map<String, String>> extraDataSet(TableEntity dataSet) {
        List<Map<String, String>> dataList = new ArrayList<>();
        dataSet.getDataEntityList().forEach(dataEntity -> {
            Map<String, String> dataMap = new HashMap<>();
            dataEntity.getFieldEntityList().forEach(fieldEntity -> {
                dataMap.put(fieldEntity.getName(), fieldEntity.getValue());
            });
            dataList.add(dataMap);
        });
        return dataList;
    }

    /**
     * @param modelId 模型ID
     * @return 准备导入模板文件
     */
    public String prepareModelTemplateFile(String modelId) {
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.BusinessModelEnum.Table);
        String timeSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = businessModel.getDisplay_name() + ".xls";
        String finalFileName = timeSuffix + "_" + fileName;
        Excel excel = new Excel();
        final int[] columnIndex = {0};
        businessModel.getAllBcCols().forEach(column -> {
            excel.column(columnIndex[0]).autoWidth().borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
            excel.cell(0, columnIndex[0])//选择第5行，但忽略第1个单元格，从第2个单元格开始操作
                    .value(column.getDisplay_name());
            columnIndex[0]++;
        });
        excel.saveExcel(fileServerConfig.getFtpHome() + finalFileName);
        return fileServerConfig.getFtpHome() + finalFileName;
    }

    /**
     * @param orientModelId  模型 | 视图ID
     * @param isView         是否是视图
     * @param customerFilter 过滤条件
     * @return 准备导出数据
     */
    public String preapareExportData(String orientModelId, String isView, String customerFilter) {
        ExtGridData<Map> gridData = getModelDataByModelId(orientModelId, isView, null, null, customerFilter, false, null);
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        List<Map> dataList = gridData.getResults();
        //反轉枚舉、關係屬性、附件
        invertData(dataList, businessModel);
        Excel excel = new Excel();
        final int[] columnIndex = {0};
        businessModel.getAllBcCols().forEach(column -> {
            excel.column(columnIndex[0]).autoWidth().borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
            excel.cell(0, columnIndex[0])//选择第5行，但忽略第1个单元格，从第2个单元格开始操作
                    .value(column.getDisplay_name());
            columnIndex[0]++;
        });
        final int[] row = {1};
        dataList.forEach(dataMap -> {
            final int[] cell = {0};
            businessModel.getAllBcCols().forEach(column -> {
                String dataValue = CommonTools.Obj2String(dataMap.get(column.getS_column_name()));
                excel.cell(row[0], cell[0])
                        .value(StringUtil.decodeUnicode(dataValue)).warpText(true);
                cell[0]++;
            });
            row[0]++;
        });
        String timeSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = businessModel.getDisplay_name() + ".xls";
        String finalFileName = timeSuffix + "_" + fileName;
        excel.saveExcel(fileServerConfig.getFtpHome() + finalFileName);
        return finalFileName;
    }

    /**
     * 将数据转化为真实显示值
     *
     * @param dataList      待转化数据集合
     * @param businessModel 模型描述
     */
    private void invertData(List<Map> dataList, IBusinessModel businessModel) {

    }


    /**
     * @param modelId  模型ID
     * @param columnId 字段ID
     * @return 字段动态范围约束
     */
    public Map<String, String> getDynamicRangeData(String modelId, String columnId) {
        Map<String, String> retVal = new HashMap<>();
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
        IBusinessColumn businessColumn = businessModel.getBusinessColumnByID(columnId);
        if (null != businessColumn) {
            Restriction restriction = businessColumn.getRestriction();
            retVal = orientSqlEngine.getBmService().queryResDynamicRange(restriction);
        }
        return retVal;
    }

    /**
     * @param modelId
     * @param columnId
     * @param columnValue
     * @return 校验唯一性
     */
    public Boolean validateUnique(String modelId, String columnId, String columnValue) {
        Boolean retVal = true;
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
        IBusinessColumn businessColumn = businessModel.getBusinessColumnByID(columnId);
        if (null != businessColumn) {
            retVal = orientSqlEngine.getBmService().checkColValueOnly(businessColumn, columnValue);
        }
        return retVal;
    }

    public Boolean validateMultiUnique(Map dataMap) {
        Boolean retVal = true;
        String modelId = (String) dataMap.get("modelId");
        if (!StringUtil.isEmpty(modelId)) {
            String userId = UserContextUtil.getUserId();
            IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
            retVal = orientSqlEngine.getBmService().checkMultiUk(businessModel, dataMap);
        }
        return retVal;
    }

    /**
     * @param modelId
     * @param isMain
     * @return 获取模型的关联模型信息
     */
    public List<BaseNode> getRefModel(String modelId, Boolean isMain) {

        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
        List<BaseNode> retVal = new ArrayList<>();
        List<RelationColumns> relationColumnses = relationColumnsDAO.findByProperty("refTable.id", modelId);
        relationColumnses.forEach(relationColumn -> {
            if (isMain) {
                BaseNode baseNode = new BaseNode();
                baseNode.setText(relationColumn.getTable().getDisplayName());
                baseNode.setId(relationColumn.getTable().getId());
                baseNode.setLeaf(true);
                baseNode.setIconCls("icon-model");
                retVal.add(baseNode);
            }
        });
        return retVal;
    }

    public String getModelDisplayName(String modelId, String isView, String userId) {
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, modelTypeEnum);
        return businessModel.getDisplay_name();
    }

    public List<ValidateError> doValidate(Map dataMap, String modelId) {
        IModelDataValidatorDirector defaultDirector = new DefaultModelDataValidatorDirector(modelId, dataMap);
        List<ValidateError> retVal = defaultDirector.buildModelValidator(defaultModelDataValidatorBuilder);
        return retVal;
    }

    public String getModelSNameByModelId(String modelId) {
        String userId = UserContextUtil.getUserId();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
        return businessModel.getS_table_name();
    }
}

