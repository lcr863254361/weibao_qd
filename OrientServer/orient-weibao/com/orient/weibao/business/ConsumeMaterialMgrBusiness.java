package com.orient.weibao.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.weibao.bean.ConsumeMaterialBean;
import com.orient.weibao.bean.PostBean;
import com.orient.weibao.constants.PropertyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-18 9:35
 */
@Service
public class ConsumeMaterialMgrBusiness extends BaseBusiness {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    FileServerConfig fileServerConfig;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public List<Map> queryConsumeTypeList() {

        IBusinessModel consumeTypeModel = businessModelService.getBusinessModelBySName(PropertyConstant.CONSUME_MATERIAL, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = consumeTypeModel.getId();
        String sql = "select * from T_CONSUME_MATERIAL_" + schemaId + " order BY TO_NUMBER(ID)  ASC ";
        List<Map<String, Object>> consumeTypeList = jdbcTemplate.queryForList(sql);
        List consumeList = new ArrayList<>();
        if (consumeTypeList.size() > 0) {
            for (Map map : consumeTypeList) {
                Map consumeMap = new HashMap<>();
                consumeMap.put("id", map.get("ID"));
                consumeMap.put("consumeType", map.get("C_NAME_" + modelId));
                consumeList.add(consumeMap);
            }
        }
        return consumeList;
    }


    public AjaxResponseData addConsumeTypeData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        Map dataMap = (Map) formDataMap.get("fields");
        dataMap.put("C_NAME_" + modelId, dataMap.get("C_NAME_" + modelId));
        SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
        eventParam.setModelId(modelId);
        eventParam.setDataMap(dataMap);
        eventParam.setCreateData(true);
        OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
        retVal.setMsg("保存成功");
        retVal.setSuccess(true);
//        retVal.setResults(eventParam.getDataMap().get("ID"));
//        Map permsMap = new HashMap<>();
//        permsMap.put("ID", eventParam.getDataMap().get("ID"));
//        permsMap.put("refreshConsumeGrid", true);
//        retVal.setResults(permsMap);
        return retVal;
    }

    public AjaxResponseData updateConsumeTypeData(String modelId, String consumeTypeId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        String sql = "update T_CONSUME_MATERIAL_" + schemaId + " set C_NAME_" + modelId + "=?" + " where id=?";
        Map dataMap = (Map) formDataMap.get("fields");
        int result = jdbcTemplate.update(sql, dataMap.get("C_NAME_" + modelId), consumeTypeId);
        retVal.setMsg("修改成功");
        retVal.setSuccess(true);
//        Map permsMap = new HashMap<>();
//        permsMap.put("refreshConsumeGrid", true);
//        permsMap.put("updateConsumeRefresh", true);
//        retVal.setResults(permsMap);
        return retVal;
    }

    public void delConsumeTypeById(String consumeTypeId) {
        String tableName = PropertyConstant.CONSUME_MATERIAL;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().deleteCascade(bm, consumeTypeId);
    }

    public void delConsumeData(String id) {
        IBusinessModel consumeBM = businessModelService.getBusinessModelBySName(PropertyConstant.CONSUME_DETAIL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceModel = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        StringBuilder sql = new StringBuilder();
        sql.append("select MAIN_DATA_ID,SUB_DATA_ID from CWM_RELATION_DATA").append(" where 1=1").append(" AND MAIN_TABLE_NAME=?").append(" AND SUB_TABLE_NAME=?").append(" AND SUB_DATA_ID IN (").append(id).append(")");
        List<Map<String, Object>> consumeIdsLsit = jdbcTemplate.queryForList(sql.toString(), "T_SPARE_PARTS_" + schemaId, "T_CONSUME_DETAIL_" + schemaId);
        if (consumeIdsLsit.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map relMap : consumeIdsLsit) {
                String deviceId = CommonTools.Obj2String(relMap.get("MAIN_DATA_ID"));
                stringBuilder.append(deviceId);
                stringBuilder.append(",");
            }
            String deviceIds = stringBuilder.toString();
            deviceIds = deviceIds.substring(0, deviceIds.length() - 1);
            deviceModel.setReserve_filter("AND ID='" + deviceIds + "'");
            List<Map<String, String>> deviceList = orientSqlEngine.getBmService().createModelQuery(deviceModel).list();
            if (deviceList.size() > 0) {
                for (Map deviceMap : deviceList) {
                    String deviceId = CommonTools.Obj2String(deviceMap.get("ID"));
                    String version = CommonTools.Obj2String(deviceMap.get("C_VERSION_" + deviceModel.getId()));
                    version = String.valueOf(Integer.parseInt(version) + 1);
                    deviceMap.put("C_VERSION_" + deviceModel.getId(), version);
                    orientSqlEngine.getBmService().updateModelData(deviceModel, deviceMap, deviceId);
                }
            }
        }
        orientSqlEngine.getBmService().delete(consumeBM, id);
    }

    public Map<String, Object> importConsumeData(TableEntity excelEntity, String consumeTypeId) {

        IBusinessModel consumeBM = businessModelService.getBusinessModelBySName(PropertyConstant.CONSUME_DETAIL, schemaId, EnumInter.BusinessModelEnum.Table);

        Map<String, Object> retVal = new HashMap<>();
        List<ConsumeMaterialBean> consumeBeanList = UtilFactory.newArrayList();

        List<DataEntity> dataEntities = excelEntity.getDataEntityList();
        for (int j = 0; j < dataEntities.size(); j++) {
            List<FieldEntity> fieldEntities = dataEntities.get(j).getFieldEntityList();
            ConsumeMaterialBean consumeMaterialBean = new ConsumeMaterialBean();
            if (fieldEntities != null) {
                for (int i = 0; i < fieldEntities.size(); i++) {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    String consumeName = fieldEntities.get(i).getName();
                    String consumeValue = fieldEntities.get(i).getValue();
//                String name = fieldEntity.getName();
                    //耗材名称不能为空
                    if ("耗材名称".equals(consumeName)) {
                        if (consumeValue == null || "".equals(consumeValue)) {
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumer + "行第" + i + "列的耗材名称不可为空，请修正后导入！");
                            return retVal;
                        }
                        consumeMaterialBean.setName(consumeValue);
                    } else if ("数量".equals(consumeName)) {
                        if (consumeValue.contains(".")) {
                            consumeValue = consumeValue.split("\\.")[0];
                        }
                        if (consumeValue != null && !"".equals(consumeValue)) {
                            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                            boolean isIntNumber = pattern.matcher(consumeValue).matches();
                            if (!isIntNumber) {
                                int rowNumer = j + 2;
                                retVal.put("success", false);
                                retVal.put("msg", "第" + rowNumer + "行第" + i + "列数量必须为整数，请修正后导入！");
                                return retVal;
                            }
                        } else if (consumeValue == null || "".equals(consumeValue)) {
                            consumeValue = "0";
                        }
                        consumeMaterialBean.setNumber(consumeValue);
                    } else if ("型号".equals(consumeName)) {
                        consumeMaterialBean.setModel(consumeValue);
                    } else if ("存放位置".equals(consumeName)) {
                        consumeMaterialBean.setPosition(consumeValue);
                    } else if ("箱号".equals(consumeName)) {
                        consumeMaterialBean.setBoxNumber(consumeValue);
                    } else if ("入库时间".equals(consumeName)) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date storeDate = null;
                        try {
                            if (consumeValue != null && !"".equals(consumeValue)) {
                                storeDate = simpleDateFormat.parse(consumeValue);
                            } else if (consumeValue == null || "".equals(consumeValue)) {
                                storeDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                            }
                            consumeMaterialBean.setWareHousingDate(storeDate);
                        } catch (ParseException e) {
//                        e.printStackTrace();
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumer + "行第" + i + "列的入库时间格式存在错误！");
                            return retVal;
                        }
                    } else if ("备注".equals(consumeName)) {
                        consumeMaterialBean.setNote(consumeValue);
                    } else {
                        retVal.put("success", false);
                        retVal.put("msg", "导入失败,表头存在错误！");
                        return retVal;
                    }
                }
                if (!"".equals(consumeMaterialBean.getName())) {
                    consumeBeanList.add(consumeMaterialBean);
                }
            }
        }
        if (consumeBeanList.size() > 0) {
            for (ConsumeMaterialBean consumeBean : consumeBeanList) {
                if (consumeBean != null) {
                    String consumeMaterialName = consumeBean.getName();
                    String consumeNumber = consumeBean.getNumber();
                    if ("".equals(consumeNumber)) {
                        consumeNumber = "0";
                    }
                    String consumeModel = consumeBean.getModel();
                    Date wareHouseDate = consumeBean.getWareHousingDate();
                    if (wareHouseDate == null) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            wareHouseDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    consumeBM.clearAllFilter();
                    if (consumeModel != null && !"".equals(consumeModel)) {
                        consumeBM.setReserve_filter("and c_name_" + consumeBM.getId() + " = '" + consumeMaterialName + "'" +
                                " and T_CONSUME_MATERIAL_" + schemaId + "_ID='" + consumeTypeId + "'" +
                                " and c_model_" + consumeBM.getId() + "='" + consumeModel + "'");
                    } else {
                        consumeBM.setReserve_filter("and c_name_" + consumeBM.getId() + " = '" + consumeMaterialName + "'" +
                                " and T_CONSUME_MATERIAL_" + schemaId + "_ID='" + consumeTypeId + "'" +
                                " and c_model_" + consumeBM.getId() + " is null");
                    }
                    List<Map<String, Object>> consumeList = orientSqlEngine.getBmService().createModelQuery(consumeBM).list();
                    if (consumeList.size() > 0) {
                        Map consumeMap = consumeList.get(0);
                        String consumeId = (String) consumeMap.get("ID");
                        //数据库中已经存在的耗材数量
                        int hasNumber = Integer.parseInt(consumeMap.get("C_NUMBER_" + consumeBM.getId()).toString());
                        hasNumber = hasNumber + (Integer.parseInt(consumeNumber));
                        consumeMap.put("C_NUMBER_" + consumeBM.getId(), hasNumber);
                        String version = (String) consumeMap.get("C_VERSION_" + consumeBM.getId());
                        version = String.valueOf(Integer.parseInt(version) + 1);
                        consumeMap.put("C_VERSION_" + consumeBM.getId(), version);
                        consumeMap.put("C_POSITION_" + consumeBM.getId(), consumeBean.getPosition());
                        consumeMap.put("C_BOX_NUMBER_" + consumeBM.getId(), consumeBean.getBoxNumber());
                        consumeMap.put("C_WAREHOUSE_DATE_" + consumeBM.getId(), CommonTools.util2Sql(wareHouseDate));
                        consumeMap.put("C_NOTE_" + consumeBM.getId(), consumeBean.getNote());
                        orientSqlEngine.getBmService().updateModelData(consumeBM, consumeMap, consumeId);
                        continue;
                    } else {
                        Map consumeMap = UtilFactory.newHashMap();
                        consumeMap.put("C_NAME_" + consumeBM.getId(), consumeMaterialName);
                        consumeMap.put("C_MODEL_" + consumeBM.getId(), consumeModel);
                        consumeMap.put("C_NUMBER_" + consumeBM.getId(), consumeNumber);
                        consumeMap.put("T_CONSUME_MATERIAL_" + schemaId + "_ID", consumeTypeId);
                        consumeMap.put("C_POSITION_" + consumeBM.getId(), consumeBean.getPosition());
                        consumeMap.put("C_BOX_NUMBER_" + consumeBM.getId(), consumeBean.getBoxNumber());
                        consumeMap.put("C_WAREHOUSE_DATE_" + consumeBM.getId(), CommonTools.util2Sql(wareHouseDate));
                        consumeMap.put("C_NOTE_" + consumeBM.getId(), consumeBean.getNote());
                        consumeMap.put("C_VERSION_" + consumeBM.getId(), 0);
                        orientSqlEngine.getBmService().insertModelData(consumeBM, consumeMap);
                    }
                }
            }
        }
        retVal.put("success", true);
        retVal.put("msg", "导入成功！");
        return retVal;
    }

    public String exportConsumeData(boolean exportAll, String toExportIds, String consumeTypeId) {
        IBusinessModel consumeTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.CONSUME_MATERIAL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel consumeBM = businessModelService.getBusinessModelBySName(PropertyConstant.CONSUME_DETAIL, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> consumeTypeMap = orientSqlEngine.getBmService().createModelQuery(consumeTypeBM).findById(consumeTypeId);
        consumeBM.setReserve_filter("AND T_CONSUME_MATERIAL_" + schemaId + "_ID='" + consumeTypeId + "'");
        Excel excel = new Excel();
        Object[] headers = new Object[]{"耗材名称", "型号", "数量", "存放位置", "箱号", "入库时间", "备注"};
        excel.row(0).value(headers);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final int[] i = {1};
        if (exportAll) {
            List<Map<String, Object>> consumeList = orientSqlEngine.getBmService().createModelQuery(consumeBM).orderAsc("to_number(ID)").list();
            if (consumeList.size() > 0) {
                for (Map map : consumeList) {
                    excel.cell(i[0], 0).value(map.get("C_NAME_" + consumeBM.getId()));
                    excel.cell(i[0], 1).value(map.get("C_MODEL_" + consumeBM.getId()));
                    excel.cell(i[0], 2).value(map.get("C_NUMBER_" + consumeBM.getId()));
                    excel.cell(i[0], 3).value(map.get("C_POSITION_" + consumeBM.getId()));
                    excel.cell(i[0], 4).value(map.get("C_BOX_NUMBER_" + consumeBM.getId()));
                    excel.cell(i[0], 5).value(map.get("C_WAREHOUSE_DATE_" + consumeBM.getId()));
                    excel.cell(i[0], 6).value(map.get("C_NOTE_" + consumeBM.getId()));
                    i[0]++;
                }
            }
        } else {
            consumeBM.clearAllFilter();
            if (!toExportIds.equals("")) {
                consumeBM.setReserve_filter("and id in (" + toExportIds + ")");
                List<Map<String, Object>> consumeList = orientSqlEngine.getBmService().createModelQuery(consumeBM).orderAsc("to_number(ID)").list();
                if (consumeList.size() > 0) {
                    for (Map map : consumeList) {
                        excel.cell(i[0], 0).value(map.get("C_NAME_" + consumeBM.getId()));
                        excel.cell(i[0], 1).value(map.get("C_MODEL_" + consumeBM.getId()));
                        excel.cell(i[0], 2).value(map.get("C_NUMBER_" + consumeBM.getId()));
                        excel.cell(i[0], 3).value(map.get("C_POSITION_" + consumeBM.getId()));
                        excel.cell(i[0], 4).value(map.get("C_BOX_NUMBER_" + consumeBM.getId()));
                        excel.cell(i[0], 5).value(map.get("C_WAREHOUSE_DATE_" + consumeBM.getId()));
                        excel.cell(i[0], 6).value(map.get("C_NOTE_" + consumeBM.getId()));
                        i[0]++;
                    }
                }
            }
        }
        for (int j = 0; j < 1; j++) {
            excel.column(j).autoWidth();
        }
        String consumeTypeName = consumeTypeMap.get("C_NAME_" + consumeTypeBM.getId());
        String divingFolderPath = FtpFileUtil.EXPORT_ROOT + File.separator + "耗材统计";
        String folder = FtpFileUtil.getRelativeUploadPath(divingFolderPath);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = consumeTypeName+".xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
//        excel.saveExcel("consume.xls");
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }
}
