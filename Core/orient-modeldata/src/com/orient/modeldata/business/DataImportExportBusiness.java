package com.orient.modeldata.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.analyze.AnalyzeContext;
import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.bean.ImportDataBean;
import com.orient.modeldata.persistent.PersistentContext;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.ExcelUtil.style.Align;
import com.orient.utils.ExcelUtil.style.BorderStyle;
import com.orient.utils.ExcelUtil.style.Color;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 模型数据业务处理
 *
 * @author enjoy
 * @creare 2016-04-01 13:27
 */
@Service
public class DataImportExportBusiness extends BaseBusiness {

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    FileServerConfig fileServerConfig;

    /**
     * 为预览做的解析
     */
    public Map<String, Object> doPreviewAnalyze(Configuration config) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            config.setAnalyzeType("preview");
            config.setRelationMap(null);

            AnalyzeContext analyzeContext = new AnalyzeContext(config);
            List<String> headers = analyzeContext.doHeadAnalyze();
            List<Map<String, Object>> content = analyzeContext.doContentAnalyze();

            retMap.put("headers", headers);
            retMap.put("content", content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return retMap;
    }

    /**
     * 为保存入库做的解析
     */
    public String doDBInputAnalyze(Configuration config) {
        try {
            config.setAnalyzeType("dbInput");
            AnalyzeContext analyzeContext = new AnalyzeContext(config);

            List<String> colNames = new ArrayList<>(config.getRelationMap().keySet());
            List<Map<String, Object>> content = analyzeContext.doContentAnalyze();

            PersistentContext persistentContext = new PersistentContext(config.getTableName(), colNames, content);
            String info = persistentContext.doValidate();
            if(info != null) {
                return info;
            }
            int cnt = persistentContext.doPersistent();
            if(cnt <= 0) {
                return "数据导入失败";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "数据导入失败";
        }

        return null;
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

        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = businessModel.getDisplay_name() + ".xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        Excel excel = new Excel();
        final int[] columnIndex = {0};
        businessModel.getAllBcCols().forEach(column -> {
            excel.column(columnIndex[0]).autoWidth().borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
            excel.cell(0, columnIndex[0])//选择第5行，但忽略第1个单元格，从第2个单元格开始操作
                    .value(column.getDisplay_name());
            columnIndex[0]++;
        });
        String realFileStoragePath = fileServerConfig.getFtpHome() + folder + finalFileName;
        excel.saveExcel(realFileStoragePath);
        return realFileStoragePath;
    }

    /**
     * @param orientModelId  模型 | 视图ID
     * @param isView         是否是视图
     * @param customerFilter 过滤条件
     * @return 准备导出数据
     */
    public String preapareExportData(String orientModelId, String isView, String customerFilter) {
        ExtGridData<Map> gridData = modelDataBusiness.getModelDataByModelId(orientModelId, isView, null, null, customerFilter, false, null);
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
                excel.cell(row[0], cell[0])
                        .value(dataMap.get(column.getS_column_name())).warpText(true);
                cell[0]++;
            });
            row[0]++;
        });
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = businessModel.getDisplay_name() + ".xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return folder + finalFileName;
    }

    /**
     * 将数据转化为真实显示值
     *
     * @param dataList      待转化数据集合
     * @param businessModel 模型描述
     */
    private void invertData(List<Map> dataList, IBusinessModel businessModel) {

    }

}

