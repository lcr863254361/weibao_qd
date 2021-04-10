package com.orient.sqlengine.util;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BusinessModelUtils for CRUD operations, each method product or comsume Map/List<Map> with non-digitalized keys (without model id or schama id)
 * if they have. So it's needless for you to append model id or schema id when you deal with the key of map. Each method can also deal with column
 * ends with "_ID" on accuracy comparing for all columns in business model. However, in query sql segement, you must use place holder "#M#"(model id)
 * and "#S#"(schema id) for it's difficult to analyse the programma of the sql segement.
 *
 * For example: Table T_A_111 with columns of: ID, C_A_2222, T_B_ID_2222, T_C_111_ID
 *
 * 1.Create a record with the map of: {"C_A":"a", "T_B_ID":"1", "T_C_ID":"1"}
 * 2.Update a record with the map of: {"ID":"1", "C_A":"a", "T_B_ID":"1", "T_C_ID":"2"}
 * 3.Query records with sql segement: AND T_B_ID_#M#=1, return list: [{"ID":"1", "C_A":"a", "T_B_ID":"1", "T_C_ID":"2"}, ...]
 *
 * @author Administrator
 * @create 2018-09-27 15:41
 */
@Service
public class BusinessModelUtils {
    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    public IBusinessModelService businessModelService;

    /**
     * Get business model by schemaId and tableId
     * @param schemaId
     * @param tableName
     * @return
     */
    public IBusinessModel getBusinessModel(String schemaId, String tableName) {
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        return bm;
    }

    /**
     * Get business model by modelId
     * @param modelId
     * @return
     */
    public IBusinessModel getBusinessModelById(String modelId) {
        IBusinessModel bm = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        return bm;
    }

    /**
     * Get business model id by schemaId and tableId
     * @param schemaId
     * @param tableName
     * @return
     */
    public String getBusinessModelId(String schemaId, String tableName) {
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        return bm.getId();
    }

    /*************************************** CRUD Method ***************************************/
    /******************** CRUD Methods with BusinessModel ********************/
    /**
     * Create a record with non-digitalized map
     * @param bm business model
     * @param dataMap map with keys whithout model or schema ids
     * @return id
     */
    public String createRecord(IBusinessModel bm, Map<String, String> dataMap) {
        String modelId = bm.getId();
        Map<String, String> realMap = appendModelIdAndSchemaId(dataMap, modelId);
        String id = orientSqlEngine.getBmService().insertModelData(bm, realMap);
        realMap.put("ID", id);
        realMap = wipeModelIdAndSchemaId(realMap);
        dataMap.clear();
        dataMap.putAll(realMap);
        return id;
    }

    /**
     * Update a record with non-digitalized map
     * @param bm business model
     * @param dataMap map with keys whithout model or schema ids
     * @return id
     */
    public boolean updateRecord(IBusinessModel bm, Map<String, String> dataMap) {
        String id = dataMap.get("ID");
        String modelId = bm.getId();
        Map<String, String> realMap = appendModelIdAndSchemaId(dataMap, modelId);
        boolean success = orientSqlEngine.getBmService().updateModelData(bm, realMap, id);
        realMap = wipeModelIdAndSchemaId(realMap);
        dataMap.clear();
        dataMap.putAll(realMap);
        return success;
    }

    /**
     * Query records with sql segement
     * @param bm business model
     * @param filter sql segement with place holders of "#M#" for model id and "#S#" for schema id
     * @return query records
     */
    public List<Map<String, String>> queryRecords(IBusinessModel bm, String filter) {
        return queryRecords(bm, filter, null, null);
    }

    /**
     * Paged query records with sql segement
     * @param bm business model
     * @param filter sql segement with place holders of "#M#" for model id and "#S#" for schema id
     * @param page current page
     * @param limit max records in page
     * @return query records
     */
    public List<Map<String, String>> queryRecords(IBusinessModel bm, String filter, Integer page, Integer limit) {
        String sid = bm.getSchema().getId();
        filter = filter.replaceAll("#M#", bm.getId()).replaceAll("#S#", sid);
        bm.setReserve_filter(filter);
        if (page != null && limit != null && page != 0 && limit != 0) {
            int start = (page - 1) * limit;
            int end = start + limit;
            List<Map<String, String>> retVal = orientSqlEngine.getBmService().createModelQuery(bm).page(start, end).list();
            return wipeModelIdAndSchemaId(retVal);
        } else {
            List<Map<String, String>> retVal = orientSqlEngine.getBmService().createModelQuery(bm).list();
            return wipeModelIdAndSchemaId(retVal);
        }
    }

    /**
     *
     * Paged query records with sql segement
     * @param bm business model
     * @param filter sql segement with place holders of "#M#" for model id and "#S#" for schema id
     * @param asc ascend column with place holder, null permitted
     * @param desc descend column with place holder, null permitted
     * @param page current page
     * @param limit max records in page
     * @return query records
     */
    public List<Map<String, String>> queryRecords(IBusinessModel bm, String filter, String asc, String desc, Integer page, Integer limit) {
        String sid = bm.getSchema().getId();
        filter = filter.replaceAll("#M#", bm.getId()).replaceAll("#S#", sid);
        bm.setReserve_filter(filter);
        IBusinessModelQuery modelQuery = orientSqlEngine.getBmService().createModelQuery(bm);
        if (asc != null && !"".equals(asc)) {
            asc = asc.replace("#M#", bm.getId()).replace("#S#", sid);
            modelQuery.orderAsc(asc);
        }
        if (desc != null && !"".equals(desc)) {
            desc = desc.replace("#M#", bm.getId()).replace("#S#", sid);
            modelQuery.orderDesc(desc);
        }
        if (page != null && limit != null && page != 0 && limit != 0) {
            int start = (page - 1) * limit;
            int end = start + limit;
            modelQuery.page(start, end);
        }
        List<Map<String, String>> retVal = modelQuery.list();
        return wipeModelIdAndSchemaId(retVal);
    }

    /**
     * Count of the query records
     * @param bm business model
     * @param filter sql segement with place holders of "#M#" for model id and "#S#" for schema id
     * @return records count
     */
    public long queryCounts(IBusinessModel bm, String filter) {
        String sid = bm.getSchema().getId();
        filter = filter.replaceAll("#M#", bm.getId()).replaceAll("#S#", sid);
        bm.setReserve_filter(filter);
        return orientSqlEngine.getBmService().createModelQuery(bm).count();
    }

    /**
     * Delete records by ids with "," splited
     * @param bm business model
     * @param ids ids of the records, "," splited
     * @param cascade cascade ?
     */
    public void deleteByIds(IBusinessModel bm, String ids, Boolean cascade) {
        if(cascade!=null && cascade==true) {
            orientSqlEngine.getBmService().deleteCascade(bm, ids);
        }
        else {
            orientSqlEngine.getBmService().delete(bm, ids);
        }
    }

    /**
     * Delete records by ids with "," splited
     * @param bm business model
     * @param filter sql segement with place holders of "#M#" for model id and "#S#" for schema id
     * @param cascade cascade ?
     */
    public void deleteRecords(IBusinessModel bm, String filter, Boolean cascade) {
        List<Map<String, String>> recs = queryRecords(bm, filter);
        if(recs==null || recs.size()==0) {
            return;
        }
        List<String> ids = new ArrayList<>();
        for(Map<String, String> rec : recs) {
            ids.add(rec.get("ID"));
        }
        if(cascade!=null && cascade==true) {
            orientSqlEngine.getBmService().deleteCascade(bm, CommonTools.list2String(ids));
        }
        else {
            orientSqlEngine.getBmService().delete(bm, CommonTools.list2String(ids));
        }
    }

    /******************** CRUD Methods with modelId ********************/
    public String createRecord(String modelId, Map<String, String> dataMap) {
        IBusinessModel bm = getBusinessModelById(modelId);
        return createRecord(bm, dataMap);
    }

    public boolean updateRecord(String modelId, Map<String, String> dataMap) {
        IBusinessModel bm = getBusinessModelById(modelId);
        return updateRecord(bm, dataMap);
    }

    public List<Map<String, String>> queryRecords(String modelId, String filter) {
        return queryRecords(modelId, filter, null, null);
    }

    public List<Map<String, String>> queryRecords(String modelId, String filter, Integer page, Integer limit) {
        IBusinessModel bm = getBusinessModelById(modelId);
        return queryRecords(bm, filter, page, limit);
    }

    public List<Map<String, String>> queryRecords(String modelId, String filter, String asc, String desc, Integer page, Integer limit) {
        IBusinessModel bm = getBusinessModelById(modelId);
        return queryRecords(bm, filter, asc, desc, page, limit);
    }

    public long queryCounts(String modelId, String filter) {
        IBusinessModel bm = getBusinessModelById(modelId);
        return queryCounts(bm, filter);
    }

    public void deleteByIds(String modelId, String ids, Boolean cascade) {
        IBusinessModel bm = getBusinessModelById(modelId);
        deleteByIds(bm, ids, cascade);
    }

    public void deleteRecords(String modelId, String filter, Boolean cascade) {
        IBusinessModel bm = getBusinessModelById(modelId);
        deleteRecords(bm, filter, cascade);
    }

    /******************** CRUD Methods with SchemaId & tableName ********************/
    public String createRecord(String schemaId, String tableName, Map<String, String> dataMap) {
        IBusinessModel bm = getBusinessModel(schemaId, tableName);
        return createRecord(bm, dataMap);
    }

    public boolean updateRecord(String schemaId, String tableName, Map<String, String> dataMap) {
        IBusinessModel bm = getBusinessModel(schemaId, tableName);
        return updateRecord(bm, dataMap);
    }

    public List<Map<String, String>> queryRecords(String schemaId, String tableName, String filter) {
        return queryRecords(schemaId, tableName, filter, null, null);
    }

    public List<Map<String, String>> queryRecords(String schemaId, String tableName, String filter, Integer page, Integer limit) {
        IBusinessModel bm = getBusinessModel(schemaId, tableName);
        return queryRecords(bm, filter, page, limit);
    }

    public List<Map<String, String>> queryRecords(String schemaId, String tableName, String filter, String asc, String desc, Integer page, Integer limit) {
        IBusinessModel bm = getBusinessModel(schemaId, tableName);
        return queryRecords(bm, filter, asc, desc, page, limit);
    }

    public long queryCounts(String schemaId, String tableName, String filter) {
        IBusinessModel bm = getBusinessModel(schemaId, tableName);
        return queryCounts(bm, filter);
    }

    public void deleteByIds(String schemaId, String tableName, String ids, Boolean cascade) {
        IBusinessModel bm = getBusinessModel(schemaId, tableName);
        deleteByIds(bm, ids, cascade);
    }

    public void deleteRecords(String schemaId, String tableName, String filter, Boolean cascade) {
        IBusinessModel bm = getBusinessModel(schemaId, tableName);
        deleteRecords(bm, filter, cascade);
    }

    /*************************************** Wipe/Append ModelId & SchemaId ***************************************/
    public static List<Map<String, String>> wipeModelIdAndSchemaId(List<Map<String, String>> dataList) {
        List<Map<String, String>> retList = new ArrayList<>();
        if (dataList == null) {
            return retList;
        }
        for (Map<String, String> map : dataList) {
            retList.add(wipeModelIdAndSchemaId(map));
        }
        return retList;
    }

    public static Map<String, String> wipeModelIdAndSchemaId(Map<String, String> dataMap) {
        Map<String, String> retMap = new HashMap<>();
        if (dataMap == null) {
            return retMap;
        }
        for (String key : dataMap.keySet()) {
            Pattern p = Pattern.compile("_\\d++");
            Matcher m = p.matcher(key);
            if (m.find()) {
                String num = key.substring(m.start(), m.end());
                String newKey = key.replace(num, "");
                String value = dataMap.get(key);
                retMap.put(newKey, value);
            } else {
                retMap.put(key, dataMap.get(key));
            }
        }
        return retMap;
    }

    public List<Map<String, String>> appendModelIdAndSchemaId(List<Map<String, String>> dataList, String modelId) {
        List<Map<String, String>> retList = new ArrayList<>();
        if (dataList == null) {
            return retList;
        }
        for (Map<String, String> map : dataList) {
            retList.add(appendModelIdAndSchemaId(map, modelId));
        }
        return retList;
    }

    public Map<String, String> appendModelIdAndSchemaId(Map<String, String> dataMap, String modelId) {
        IBusinessModel bm = getBusinessModelById(modelId);
        String sid = bm.getSchema().getId();
        Map<String, String> retMap = new HashMap<>();
        if (dataMap == null) {
            return retMap;
        }
        for (String key : dataMap.keySet()) {
            if ("ID".equals(key)) {
                retMap.put(key, dataMap.get(key));
            }
            else if(!key.endsWith("_ID")) {
                retMap.put(key + "_" + modelId, dataMap.get(key));
            }
            else {
                Set<String> probResults = Sets.newHashSet();
                probResults.add(key + "_" + modelId);
                probResults.add(key.substring(0, key.length()-3) + "_" + sid + "_ID");

                String matchedColumnName = null;
                for (IBusinessColumn bc : bm.getAllBcCols()) {
                    String columnName = bc.getS_column_name();
                    if(probResults.contains(columnName)) {
                        matchedColumnName = columnName;
                        break;
                    }
                }

                if(!Strings.isNullOrEmpty(matchedColumnName)) {
                    retMap.put(matchedColumnName, dataMap.get(key));
                }
                else {
                    retMap.put(key.substring(0, key.length()-3) + "_" + sid + "_ID", dataMap.get(key));
                }
            }
        }
        return retMap;
    }
}
