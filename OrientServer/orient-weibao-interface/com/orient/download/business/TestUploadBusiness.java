package com.orient.download.business;

import com.google.common.base.Joiner;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.download.bean.checkHeadRowCellBean.*;
import com.orient.download.bean.dailyWorkEntity.DailyWorkEntity;
import com.orient.download.bean.deviceInstCheckBean.DeviceInstCheckEntity;
import com.orient.download.bean.deviceInstCheckBean.DeviceInstCheckEventEntity;
import com.orient.download.bean.sparePartsBean.ConsumeBean;
import com.orient.download.bean.uploadCheckInstBean.*;
import com.orient.download.bean.sparePartsBean.DeviceInstBean;
import com.orient.download.bean.sparePartsBean.ReplaceDeviceBean;
import com.orient.download.bean.sparePartsBean.TroubleDeviceBean;
import com.orient.download.bean.uploadCheckInstBean.CellDataBean;
import com.orient.download.bean.uploadCheckInstBean.CheckListTableBean;
import com.orient.download.bean.uploadCheckInstBean.HeadBean;
import com.orient.download.bean.uploadCheckInstBean.RowBean;
import com.orient.download.bean.uploadSignBean.SignModel;
import com.orient.download.bean.uploadWaterRunning.DiveModel;
import com.orient.download.bean.uploadWaterRunning.RecordModel;
import com.orient.download.enums.HttpResponse;
import com.orient.download.enums.HttpResponseStatus;
import com.orient.download.utils.ChangeAmrToMp3;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.*;
import com.orient.utils.image.ImageUtils;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.weibao.business.AccountingFormMgrBusiness;
import com.orient.weibao.business.FormTemplateMgrBusiness;
import com.orient.weibao.business.TaskPrepareMgrBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.enums.CheckCellInstCellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.resource.cci.Record;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-21 16:59
 */
@Service
public class TestUploadBusiness extends BaseBusiness {

    @Autowired
    TestDownloadBusiness testDownloadBusiness;

    @Autowired
    private FileServerConfig fileServerConfig;

    @Autowired
    private FileService fileService;

    @Autowired
    TaskPrepareMgrBusiness taskPrepareMgrBusiness;

    @Autowired
    FormTemplateMgrBusiness formTemplateMgrBusiness;

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    protected UserService UserService;

    @Autowired
    AccountingFormMgrBusiness accountingFormMgrBusiness;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public HttpResponse<String> deviceDataUpdate(DeviceInstBean deviceInstBean) throws Exception {
        HttpResponse retVal = new HttpResponse();
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);

        String deviceInstId = deviceInstBean.getId();
        deviceInstBM.clearAllFilter();
        deviceInstBM.setReserve_filter("AND ID='" + deviceInstId + "'");
        List<Map<String, String>> deviceInstList = testDownloadBusiness.commonGetList(deviceInstBM);
        if (deviceInstList.size() > 0) {
            Map<String, String> deviceInstMap = deviceInstList.get(0);
            String deviceId = deviceInstMap.get("T_SPARE_PARTS_" + schemaId + "_ID");
            deviceInstMap.put("C_STATE_" + deviceInstBM.getId(), deviceInstBean.getState());
            deviceInstMap.put("C_POSITION_" + deviceInstBM.getId(), deviceInstBean.getLocation());
            String version = (String) deviceInstMap.get("C_VERSION_" + deviceInstBM.getId());
            version = String.valueOf(Integer.parseInt(version) + 1);
            deviceInstMap.put("C_VERSION_" + deviceInstBM.getId(), version);
            orientSqlEngine.getBmService().updateModelData(deviceInstBM, deviceInstMap, deviceInstId);
            deviceLifeBM.clearAllFilter();
            deviceLifeBM.setReserve_filter("AND T_SPARE_PARTS_SHILI_" + schemaId + "_ID='" + deviceInstId + "'" +
                    " AND C_END_TIME_" + deviceLifeBM.getId() + " IS NULL");
            String uploadTime = deviceInstBean.getLatestUpdateTime();
//            List<Map<String, Object>> lifeCycleList = orientSqlEngine.getBmService().createModelQuery(deviceLifeBM).orderDesc("to_date(c_start_time_" + deviceLifeBM.getId() + ",'')").list();
            List<Map<String, Object>> lifeCycleList = orientSqlEngine.getBmService().createModelQuery(deviceLifeBM).list();
            if (lifeCycleList.size() > 0) {
                //获取最新的结束日期
                String endTime = CommonTools.Obj2String(lifeCycleList.get(0).get("C_END_TIME_" + deviceLifeBM.getId()));
                if (endTime == null || "".equals(endTime)) {
                    Map lifeCycleMap = lifeCycleList.get(0);
                    String lifeId = CommonTools.Obj2String(lifeCycleMap.get("ID"));
                    lifeCycleMap.put("C_END_TIME_" + deviceLifeBM.getId(), uploadTime);

                    orientSqlEngine.getBmService().updateModelData(deviceLifeBM, lifeCycleMap, lifeId);

                    Map lifeMap = new HashMap<>();
                    lifeMap.put("C_START_TIME_" + deviceLifeBM.getId(), uploadTime);
                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    lifeMap.put("C_UPDATE_TIME_" + deviceLifeBM.getId(), date.format(new Date()));
                    lifeMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), deviceInstBean.getState());
                    lifeMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", deviceInstId);
                    orientSqlEngine.getBmService().insertModelData(deviceLifeBM, lifeMap);
                }
            }
            //修改设备实例了，也要更改设备版本
            deviceBM.setReserve_filter("AND ID='" + deviceId + "'");
            List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
            Map spareMap = spareList.get(0);
            String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + deviceBM.getId()));
            deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
            spareMap.put("C_VERSION_" + deviceBM.getId(), deviceVersion);
            orientSqlEngine.getBmService().updateModelData(deviceBM, spareMap, deviceId);
        }
//        else {
//            retVal.setData("0");
//            retVal.setResult(HttpResponseStatus.FAIL.toString());
//            retVal.setMsg("服务端没有此设备信息！");
//        }
        return retVal;
    }

    public HttpResponse<String> replaceDevice(ReplaceDeviceBean replaceDeviceBean, String recorder, String taskId) {
        HttpResponse retVal = new HttpResponse();
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel replaceDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);

        //更新产品结构中关联的设备实例信息ID
        String oldDeviceId = replaceDeviceBean.getOldDeviceId();
        String replaceDeviceId = replaceDeviceBean.getNewDeviceId();
        deviceInstBM.clearAllFilter();
        deviceInstBM.setReserve_filter("AND ID='" + oldDeviceId + "'");
        //获取到旧设备信息
        List<Map> deviceInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
        deviceInstBM.clearAllFilter();
        deviceInstBM.setReserve_filter("AND ID='" + replaceDeviceId + "'");
        //获取到新设备信息
        List<Map> spareInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
        String productId = "";
        if (deviceInstList.size() > 0 && spareInstList.size() > 0) {
            Map spareInstMap = deviceInstList.get(0);
            //根据旧设备获取到产品结构ID
            productId = (String) spareInstMap.get("C_PRODUCT_ID_" + deviceInstBM.getId());
            if (productId != null && !"".equals(productId)) {
                //旧设备的产品结构id置为空
                spareInstMap.put("C_PRODUCT_ID_" + deviceInstBM.getId(), "");
                String version = (String) spareInstMap.get("C_VERSION_" + deviceInstBM.getId());
                version = String.valueOf(Integer.parseInt(version) + 1);
                spareInstMap.put("C_VERSION_" + deviceInstBM.getId(), version);
                //获取设备ID
                String oldDeviceID = CommonTools.Obj2String(spareInstMap.get("T_SPARE_PARTS_" + schemaId + "_ID"));
                orientSqlEngine.getBmService().updateModelData(deviceInstBM, spareInstMap, oldDeviceId);
                //更新新设备的产品结构id
                Map deviceInstMap = spareInstList.get(0);
                deviceInstMap.put("C_PRODUCT_ID_" + deviceInstBM.getId(), productId);
                String newVersion = (String) deviceInstMap.get("C_VERSION_" + deviceInstBM.getId());
                newVersion = String.valueOf(Integer.parseInt(newVersion) + 1);
                deviceInstMap.put("C_VERSION_" + deviceInstBM.getId(), newVersion);
                //获取新的设备id
                String newDeviceID = CommonTools.Obj2String(deviceInstMap.get("T_SPARE_PARTS_" + schemaId + "_ID"));
                orientSqlEngine.getBmService().updateModelData(deviceInstBM, deviceInstMap, replaceDeviceId);
                //组合两个设备ID
                String deviceIds = oldDeviceID + "," + newDeviceID;
                //修改设备实例了，也要更改设备版本
                deviceBM.setReserve_filter("AND ID IN(" + deviceIds + ")");
                List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
                for (Map spareMap : spareList) {
                    String spareId = CommonTools.Obj2String(spareMap.get("ID"));
                    String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + deviceBM.getId()));
                    deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
                    spareMap.put("C_VERSION_" + deviceBM.getId(), deviceVersion);
                    orientSqlEngine.getBmService().updateModelData(deviceBM, spareMap, spareId);
                }
            }
            //保存替换的设备信息到故障表中
            Map replaceMap = new HashMap<>();
            replaceMap.put("C_NUMBER_ID_" + replaceDeviceBM.getId(), replaceDeviceBean.getId());
            replaceMap.put("C_OLD_DEVICE_ID_" + replaceDeviceBM.getId(), replaceDeviceBean.getOldDeviceId());
            replaceMap.put("C_NEW_DEVICE_ID_" + replaceDeviceBM.getId(), replaceDeviceBean.getNewDeviceId());
            replaceMap.put("C_REPLACE_DESC" + replaceDeviceBM.getId(), replaceDeviceBean.getDesc());
            replaceMap.put("C_TROUBLE_DATE_" + replaceDeviceBM.getId(), replaceDeviceBean.getDate());
            replaceMap.put("C_RECORDER_" + replaceDeviceBM.getId(), recorder);
            replaceMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            orientSqlEngine.getBmService().insertModelData(replaceDeviceBM, replaceMap);
        }
        retVal.setData("1");
        retVal.setResult(HttpResponseStatus.SUCCESS.toString());
        return retVal;
    }

    public void troubleDevice(List<TroubleDeviceBean> troubleDeviceBeanList, String recorder, String taskId) {
        IBusinessModel troubleBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);
//         String troubleId=troubleDeviceBean.getDeviceId();
//        troubleBM.setReserve_filter(" AND C_DEVICE_INST_ID_"+troubleBM.getId()+"='"+troubleId+"'");
//        List<Map> troubleList=orientSqlEngine.getBmService().createModelQuery(troubleBM).list();
//        if (troubleList.size()>0){
//           retVal.setResult(HttpResponseStatus.FAIL.toString());
//            retVal.setMsg("不能重复上传相同故障设备！");
//        }else {

        for (TroubleDeviceBean troubleDeviceBean : troubleDeviceBeanList) {
            Map troubleMap = new HashMap<>();
            troubleMap.put("C_NUMBER_ID_" + troubleBM.getId(), troubleDeviceBean.getId());
            troubleMap.put("C_DESCRIPTION_" + troubleBM.getId(), troubleDeviceBean.getDesc());
            troubleMap.put("C_TROUBLE_DATE_" + troubleBM.getId(), troubleDeviceBean.getDate());
            troubleMap.put("C_RECORDER_" + troubleBM.getId(), recorder);
            troubleMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            String type = troubleDeviceBean.getType();
            //"1"  故障记录；"2"入所检验； "3"单元格故障
            if ("1".equals(type)) {
                troubleMap.put("C_IS_TROUBLE_" + troubleBM.getId(), "true");
                troubleMap.put("C_DEVICE_STATE_" + troubleBM.getId(), "故障");
                troubleMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", troubleDeviceBean.getRefId());
                orientSqlEngine.getBmService().insertModelData(troubleBM, troubleMap);
            } else if ("2".equals(type)) {
                troubleMap.put("C_IS_TROUBLE_" + troubleBM.getId(), "false");
                troubleMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", troubleDeviceBean.getRefId());
                orientSqlEngine.getBmService().insertModelData(troubleBM, troubleMap);
            } else if ("3".equals(type)) {
                troubleBM.clearAllFilter();
                troubleBM.setReserve_filter(" AND C_NUMBER_ID_" + troubleBM.getId() + "='" + troubleDeviceBean.getId() + "' AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map> troubleList = orientSqlEngine.getBmService().createModelQuery(troubleBM).list();
                if (troubleList != null && troubleList.size() > 0) {
                    Map hasTroubleMap = troubleList.get(0);
                    String troubleId = hasTroubleMap.get("ID").toString();
                    hasTroubleMap.put("C_DESCRIPTION_" + troubleBM.getId(), troubleDeviceBean.getDesc());
                    orientSqlEngine.getBmService().updateModelData(troubleBM, hasTroubleMap, troubleId);
                } else {
                    troubleMap.put("C_IS_TROUBLE_" + troubleBM.getId(), "true");
                    troubleMap.put("C_DEVICE_STATE_" + troubleBM.getId(), "单元格故障");
                    troubleMap.put("C_CELL_ID_" + troubleBM.getId(), troubleDeviceBean.getRefId());
                    orientSqlEngine.getBmService().insertModelData(troubleBM, troubleMap);
                }
            }

        }
        //true表示是故障，false表示是入所检验数据
//        if (!isTrouble) {
//            troubleMap.put("C_IS_TROUBLE_" + troubleBM.getId(), "false");
//        } else {
//            troubleMap.put("C_DEVICE_STATE_" + troubleBM.getId(), "故障");
//            troubleMap.put("C_IS_TROUBLE_" + troubleBM.getId(), "true");
//        }
        //"1"  故障记录；"2"入所检验； "3"单元格故障
//        }

//        String troubleDeviceId=troubleDeviceBean.getDeviceId();
//        deviceLifeBM.clearAllFilter();
//        deviceLifeBM.setReserve_filter("AND T_SPARE_PARTS_SHILI_" + schemaId + "_ID ='" + troubleDeviceId + "'" );
//        String troubleDate=troubleDeviceBean.getDate();
//        List<Map<String,Object>> lifeCycleList=orientSqlEngine.getBmService().createModelQuery(deviceLifeBM).orderDesc("to_date(c_start_time_" + deviceLifeBM.getId() + ",'yyyy-mm-dd HH-mi-ss')").list();
//        if (lifeCycleList.size()>0){
//            //获取最新的结束日期
//            String endTime=CommonTools.Obj2String(lifeCycleList.get(0).get("C_END_TIME_"+deviceLifeBM.getId()));
//            if (endTime==null||"".equals(endTime)){
//                Map lifeCycleMap=lifeCycleList.get(0);
//                String lifeId=CommonTools.Obj2String(lifeCycleMap.get("ID"));
//                lifeCycleMap.put("C_END_TIME_" + deviceLifeBM.getId(), troubleDate);
//                lifeCycleMap.put("C_DEVICE_STATE_"+deviceLifeBM.getId(),"故障");
//                orientSqlEngine.getBmService().updateModelData(deviceLifeBM, lifeCycleMap, lifeId);
//
//                Map lifeMap=new HashMap<>();
//                lifeMap.put("C_START_TIME_"+deviceLifeBM.getId(),endTime);
//                lifeMap.put("C_DEVICE_STATE_"+deviceLifeBM.getId(),"故障");
//                lifeMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", troubleDeviceId);
//                lifeMap.put("C_DEVICE_DESC_"+deviceLifeBM.getId(),troubleDeviceBean.getDesc());
//                orientSqlEngine.getBmService().insertModelData(deviceLifeBM, lifeMap);
//            }
//        }
    }

    public List<CheckListTableBean> uploadCheckTableInst(List<CheckListTableBean> checkListTableBeanList, String taskId, String taskName) {
        IBusinessModel checkInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellDataBM = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingReportBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_REPORT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel informLogTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_LOG, schemaId, EnumInter.BusinessModelEnum.Table);
        List<CheckListTableBean> increaseRowTableList = UtilFactory.newArrayList();
        for (CheckListTableBean checkListTableBean : checkListTableBeanList) {
            //判断数据库中是否存在模板实例，以防误删
            String checkInstID = checkListTableBean.getId();
            checkInstBM.setReserve_filter("and ID ='" + checkInstID + "'");
            List<Map<String, String>> checkInstList = orientSqlEngine.getBmService().createModelQuery(checkInstBM).list();
            String isException = checkListTableBean.getIsException();
            if (checkInstList.size() == 0) {
                continue;
            } else {
                taskId = CommonTools.Obj2String(checkInstList.get(0).get("T_DIVING_TASK_" + schemaId + "_ID"));
                String nodeId = CommonTools.Obj2String(checkInstList.get(0).get("C_NODE_ID_" + checkInstBM.getId()));
                String checkTempId = CommonTools.Obj2String(checkInstList.get(0).get("C_CHECK_TEMP_ID_" + checkInstBM.getId()));
                String checkInstFlag = CommonTools.Obj2String(checkInstList.get(0).get("C_AGAIN_FLAG_" + checkInstBM.getId()));
                if ("异常".equals(isException)) {
                    String checkName = CommonTools.Obj2String(checkInstList.get(0).get("C_NAME_" + checkInstBM.getId()));
                    String maxAgainFlagSql = "select max(TO_NUMBER(c_again_flag_" + checkInstBM.getId() + ")) as flag from T_CHECK_TEMP_INST_" + schemaId + " where 1=1 and c_node_id_" + checkInstBM.getId() + "='" + nodeId
                            + "' AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "' AND C_CHECK_TEMP_ID_" + checkInstBM.getId() + "='" + checkTempId + "'";
                    List<Map<String, Object>> checkInstMaxFlagList = jdbcTemplate.queryForList(maxAgainFlagSql);
                    if (checkInstMaxFlagList != null && checkInstMaxFlagList.size() > 0) {
                        checkInstFlag = CommonTools.Obj2String(checkInstMaxFlagList.get(0).get("flag"));
                    }
                    checkInstFlag = "".equals(checkInstFlag) ? "0" : checkInstFlag;
                    int newRecordAgainFlag = Integer.parseInt(checkInstFlag) + 1;
                    if (!"0".equals(checkInstFlag)) {
                        int index = checkName.lastIndexOf("_");
                        checkName = checkName.substring(0, index);
                    }
                    checkName = checkName + "_" + newRecordAgainFlag;
                    String nodeText = CommonTools.Obj2String(checkInstList.get(0).get("C_NODE_TEXT_" + checkInstBM.getId()));
                    String hangciId = CommonTools.Obj2String(checkInstList.get(0).get("T_HANGCI_" + schemaId + "_ID"));
                    String insType = CommonTools.Obj2String(checkInstList.get(0).get("C_INS_TYPE_" + checkInstBM.getId()));
                    String checkTableIsRepeatUpload = CommonTools.Obj2String(checkInstList.get(0).get("C_IS_REPEAT_UPLOAD_" + checkInstBM.getId()));
                    checkTableIsRepeatUpload = "".equals(checkTableIsRepeatUpload) ? "否" : checkTableIsRepeatUpload;
                    Map checkInstMap = checkInstList.get(0);
                    checkInstMap.remove("ID");
                    checkInstMap.remove("C_NAME_" + checkInstBM.getId());
                    checkInstMap.remove("C_AGAIN_FLAG_" + checkInstBM.getId());
                    checkInstMap.remove("C_CHECK_STATE_" + checkInstBM.getId());
                    checkInstMap.remove("C_CHECK_PERSON_" + checkInstBM.getId());
                    checkInstMap.remove("C_CHECK_TIME_" + checkInstBM.getId());
                    checkInstMap.put("C_NAME_" + checkInstBM.getId(), checkName);
                    checkInstMap.put("C_AGAIN_FLAG_" + checkInstBM.getId(), newRecordAgainFlag);
                    checkInstMap.put("C_CHECK_STATE_" + checkInstBM.getId(), "异常");
                    checkInstMap.put("C_CHECK_PERSON_" + checkInstBM.getId(), checkListTableBean.getCheckPerson());
                    checkInstMap.put("C_CHECK_TIME_" + checkInstBM.getId(), checkListTableBean.getCheckDate());
                    checkInstMap.put("C_IS_REPEAT_UPLOAD_" + checkInstBM.getId(), checkTableIsRepeatUpload);
                    checkInstMap.put("C_ATTENTION_" + checkInstBM.getId(), checkListTableBean.getIsAttention());
                    checkInstID = orientSqlEngine.getBmService().insertModelData(checkInstBM, checkInstMap);
                    //复制检查表模板实例
//                    checkInstID = this.insertCheckListTemplate(checkName, checkTempId, taskId, nodeId, nodeText, hangciId, insType, checkTableIsRepeatUpload, checkListTableBean);
                    checkListTableBean = this.copyInsertCheckHeaderCellData(checkListTableBean, checkInstID);
                    //2021.2.2把异常表的拍照、签署、故障单元格Id返回给终端
                    increaseRowTableList.add(checkListTableBean);
                    // 通知日志
//                Map informLogMap = UtilFactory.newHashMap();
//                String checkPersonId = checkListTableBean.getCheckPerson();
//                User user = UserService.findUserById(checkPersonId);
//                informLogMap.put("C_TYPE_" + informLogTableBM.getId(), "check");
//                informLogMap.put("C_TABLE_NAME_" + informLogTableBM.getId(), checkName);
//                informLogMap.put("C_STATE_" + informLogTableBM.getId(), "异常");
//                informLogMap.put("C_TABLE_ID_" + informLogTableBM.getId(), checkInstID);
//                accountingFormMgrBusiness.informMap(informLogTableBM, taskId, taskName, user, informLogMap);
                    continue;
                }
                Map checkInstMap = checkInstList.get(0);
                String tableState = CommonTools.Obj2String(checkInstMap.get("C_CHECK_STATE_" + checkInstBM.getId()));
                if ("已上传".equals(tableState) || "异常".equals(tableState)) {
                    continue;
                }
                List<String> headerIds = UtilFactory.newArrayList();
                List<String> colIndexs = UtilFactory.newArrayList();
                List<HeadBean> headBeanList = checkListTableBean.getHeadBeanArrayList();
                for (HeadBean headBean : headBeanList) {
                    String conIndex = headBean.getColIndex();
                    String headerId = headBean.getDbId();
                    colIndexs.add(conIndex);
                    headerIds.add(headerId);
                }

                cellDataBM.clearCustomFilter();
                cellDataBM.appendCustomerFilter(new CustomerFilter("T_CHECK_TEMP_INST_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, checkInstID));
                List<Map<String, String>> cellValueData = orientSqlEngine.getBmService().createModelQuery(cellDataBM).orderAsc("TO_NUMBER(T_CHECK_CELL_INST_" + schemaId + "_ID)").list();


                String checkTableIsRepeatUpload = CommonTools.Obj2String(checkInstMap.get("C_IS_REPEAT_UPLOAD_" + checkInstBM.getId()));
                checkTableIsRepeatUpload = "".equals(checkTableIsRepeatUpload) ? "否" : checkTableIsRepeatUpload;

                //上传检查表的头尾数据
                List<FrontContentBean> frontContentBeanList = checkListTableBean.getFrontContentBeanList();
                List<EndContentBean> endContentBeanList = checkListTableBean.getEndContentBeanList();
                if (frontContentBeanList != null && frontContentBeanList.size() > 0) {
                    for (FrontContentBean frontContentBean : frontContentBeanList) {
//                    uploadCellInstData(frontContentBean.getContent(), frontContentBean.getId(), cellDataBM, frontContentBean.getCellType(), checkInstID);
                        uploadCellInstData(frontContentBean.getContent(), frontContentBean.getId(), cellDataBM, frontContentBean.getCellType(), checkInstID, cellValueData, frontContentBean.isOperation());
                    }
                }
                boolean isExceptionTable = false;
                boolean isTableComplete = false;
                boolean isHaveEndTime = false;
                if (endContentBeanList != null && endContentBeanList.size() > 0) {
                    for (EndContentBean endContentBean : endContentBeanList) {
                        String endContentName = CommonTools.Obj2String(endContentBean.getName());
                        String content = CommonTools.Obj2String(endContentBean.getContent());
                        if ("异常情况说明".equals(endContentName) && !"".equals(content)) {
                            isExceptionTable = true;
                        } else if ("结束时间".equals(endContentName)) {
                            isHaveEndTime = true;
                            if (!"".equals(content)) {
                                isTableComplete = true;
                            }
                        }
                        uploadCellInstData(endContentBean.getContent(), endContentBean.getId(), cellDataBM, endContentBean.getCellType(), checkInstID, cellValueData, endContentBean.isOperation());
                    }
                }
                if ("是".equals(checkTableIsRepeatUpload)) {
                    //有结束时间并且有内容
                    if (isHaveEndTime && isTableComplete) {
                        checkInstMap.put("C_CHECK_STATE_" + checkInstBM.getId(), "已上传");
                    } else if (isHaveEndTime && (!isTableComplete)) {
                        //有结束时间并且没有内容，设置为未完成
                        checkInstMap.put("C_CHECK_STATE_" + checkInstBM.getId(), "未完成");
                    } else if (!isHaveEndTime) {
                        //其他情况都设置为已上传
                        checkInstMap.put("C_CHECK_STATE_" + checkInstBM.getId(), "已上传");
                    }
                } else {
                    checkInstMap.put("C_CHECK_STATE_" + checkInstBM.getId(), "已上传");
                }
                checkInstMap.put("C_CHECK_TIME_" + checkInstBM.getId(), checkListTableBean.getCheckDate());
                String hasCheckPersonId = CommonTools.Obj2String(checkInstMap.get("C_CHECK_PERSON_" + checkInstBM.getId()));
                if ("".equals(hasCheckPersonId)) {
                    checkInstMap.put("C_CHECK_PERSON_" + checkInstBM.getId(), checkListTableBean.getCheckPerson());
                } else {
                    if (!hasCheckPersonId.contains(checkListTableBean.getCheckPerson())) {
                        hasCheckPersonId += "," + checkListTableBean.getCheckPerson();
                        checkInstMap.put("C_CHECK_PERSON_" + checkInstBM.getId(), hasCheckPersonId);
                    }
                }
                checkInstMap.put("C_ATTENTION_" + checkInstBM.getId(), checkListTableBean.getIsAttention());
                if (isExceptionTable) {
                    checkInstMap.put("C_CHECK_STATE_" + checkInstBM.getId(), "异常");
                }
                orientSqlEngine.getBmService().updateModelData(checkInstBM, checkInstMap, checkInstID);
                List<RowBean> rowBeans = checkListTableBean.getRowBeanArrayList();
                String checkTableType = checkListTableBean.getCheckTableType();
                if ("2".equals(checkTableType)) {
                    handlerCheckType2Table(rowInstBM, cellDataBM, cellInstBM, rowBeans, headerIds, colIndexs, checkInstID, checkListTableBean.getCheckPerson(), cellValueData);
                    //把自增行的单元格Id返回给终端
                    increaseRowTableList.add(checkListTableBean);
                    //通知日志
                    Map informLogMap = UtilFactory.newHashMap();
                    String checkPersonId = checkListTableBean.getCheckPerson();
                    User user = UserService.findUserById(checkPersonId);
                    informLogMap.put("C_TYPE_" + informLogTableBM.getId(), "check");
                    informLogMap.put("C_TABLE_NAME_" + informLogTableBM.getId(), checkListTableBean.getName());
                    informLogMap.put("C_STATE_" + informLogTableBM.getId(), "上传");
                    informLogMap.put("C_TABLE_ID_" + informLogTableBM.getId(), checkInstID);
                    if (isExceptionTable) {
                        informLogMap.put("C_IS_EXCEPTION_" + informLogTableBM.getId(), "异常");
                    }
                    accountingFormMgrBusiness.informMap(informLogTableBM, taskId, taskName, user, informLogMap);
                    orientSqlEngine.getBmService().insertModelData(informLogTableBM, informLogMap);
                    continue;
                }

                divingReportBM.clearAllFilter();
                divingReportBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map> divingReportList = orientSqlEngine.getBmService().createModelQuery(divingReportBM).list();

                rowInstBM.clearAllFilter();
                rowInstBM.setReserve_filter("AND " + PropertyConstant.CHECK_TEMP_INST + "_" + schemaId + "_ID='" + checkListTableBean.getId() + "'");
                List<Map> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).orderAsc("TO_NUMBER(C_ROW_NUMBER_" + rowInstBM.getId() + ")").list();

                Map divingReportMap = UtilFactory.newHashMap();
                String divingReportId = "";
                if (divingReportList != null && divingReportList.size() > 0) {
                    divingReportMap = divingReportList.get(0);
                    divingReportId = CommonTools.Obj2String(divingReportMap.get("ID"));
                }
                String checkName = checkListTableBean.getName();

                for (RowBean rowBean : rowBeans) {
                    String rowId = rowBean.getDbId();
                    if (rowInstList.size() > 0) {
                        for (Map rowMap : rowInstList) {
                            String queryRowId = rowMap.get("ID").toString();
                            //更新行实例
                            if (rowId.equals(queryRowId)) {
                                String productId = CommonTools.Obj2String(rowMap.get("C_PRODUCT_ID_" + rowInstBM.getId()));
                                if (StringUtil.isNotEmpty(productId)) {
                                    deviceInstBM.clearAllFilter();
                                    deviceInstBM.setReserve_filter("AND C_PRODUCT_ID_" + deviceInstBM.getId() + "='" + productId + "'");
                                    List<Map> deviceInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
                                    StringBuilder deviceInstIds = new StringBuilder();
                                    if (deviceInstList.size() > 0) {
                                        for (Map deviceInstMap : deviceInstList) {
                                            String spareInstId = (String) deviceInstMap.get("ID");
                                            deviceInstIds.append(spareInstId);
                                            deviceInstIds.append(",");
                                        }
                                        String deviceInstId = deviceInstIds.toString();
                                        deviceInstId = deviceInstId.substring(0, deviceInstId.length() - 1);
                                        //deviceInstId中只有一个设备实例ID才更新行实例，有多个设备实例Id就更新行实例
                                        if (deviceInstId != null && !"".equals(deviceInstId)) {
                                            if (deviceInstId.indexOf(",") == -1) {
                                                rowMap.put("C_DEVICE_INST_ID_" + rowInstBM.getId(), deviceInstId);
                                                orientSqlEngine.getBmService().updateModelData(rowInstBM, rowMap, rowId);
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }

                    List<CellDataBean> cellDataBeanList = rowBean.getCells();
                    for (CellDataBean cellBean : cellDataBeanList) {
                        String cellId = cellBean.getId();
                        //content save to cell_inst_data table
                        String value = cellBean.getValue();
                        String cellType = cellBean.getType();
                        uploadCellInstData(value, cellId, cellDataBM, cellType, checkInstID, cellValueData, cellBean.isOperation());
                    }
                    // 处理固定的检查表提取某些固定数据
                    handlerFixedCheckTable(checkListTableBean.getName(), cellDataBeanList, divingReportBM, divingReportMap);
                }
                if (checkName.contains("潜水器布放情况记录") || checkName.contains("潜水器开始作业记录") || checkName.contains("潜水器结束作业记录") || checkName.contains("潜水器回收情况记录")) {
                    if (!"".equals(divingReportId) && divingReportId != null) {
                        orientSqlEngine.getBmService().updateModelData(divingReportBM, divingReportMap, divingReportId);
                    } else if (divingReportList.size() == 0) {
                        divingReportMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
                        orientSqlEngine.getBmService().insertModelData(divingReportBM, divingReportMap);
                    }
                }
                //通知日志
                Map informLogMap = UtilFactory.newHashMap();
                String checkPersonId = checkListTableBean.getCheckPerson();
                User user = UserService.findUserById(checkPersonId);
                informLogMap.put("C_TYPE_" + informLogTableBM.getId(), "check");
                informLogMap.put("C_TABLE_NAME_" + informLogTableBM.getId(), checkListTableBean.getName());
                informLogMap.put("C_STATE_" + informLogTableBM.getId(), "上传");
                informLogMap.put("C_TABLE_ID_" + informLogTableBM.getId(), checkInstID);
                if (isExceptionTable) {
                    informLogMap.put("C_IS_EXCEPTION_" + informLogTableBM.getId(), "异常");
                }
                accountingFormMgrBusiness.informMap(informLogTableBM, taskId, taskName, user, informLogMap);
                orientSqlEngine.getBmService().insertModelData(informLogTableBM, informLogMap);
                continue;
            }
        }
        return increaseRowTableList;
    }

    /**
     * 处理固定的检查表提取某些固定数据
     *
     * @param checkName
     * @param cellDataBeanList
     * @param divingReportBM
     * @param divingReportMap
     */
    private void handlerFixedCheckTable(String checkName, List<CellDataBean> cellDataBeanList,
                                        IBusinessModel divingReportBM, Map divingReportMap) {
        if (checkName.contains("潜水器布放情况记录") || checkName.contains("潜水器开始作业记录") || checkName.contains("潜水器结束作业记录") || checkName.contains("潜水器回收情况记录")) {
            //获取所有对象的所有的content集合
            List<String> cellValueList = cellDataBeanList.stream().map(CellDataBean::getValue).collect(Collectors.toList());
            String cellValue = Joiner.on("t").join(cellValueList);
            if (!"".equals(cellValue)) {
                String cellValueArray[] = cellValue.split("t");
                if (cellValueArray.length == 2) {
                    String cellValueContent = cellValueArray[1] == null ? "" : cellValueArray[1];
                    if (cellValueArray[0].contains("下潜日期")) {
                        divingReportMap.put("C_DATE_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("布放时经度") && !"".equals(cellValueContent)) {
//                            double doubleJingdu = Double.valueOf(cellValueArray[1]);
//                            if (cellValueContent.contains(".")) {
//                                int pointRemainLength = cellValueContent.substring(cellValueContent.lastIndexOf(".") + 1, cellValueContent.length()).length();
//                                if (pointRemainLength>4){
//                                    //保留四位小数
//                                    DecimalFormat df = new DecimalFormat("0.0000");
//                                    cellValueContent = df.format(doubleJingdu);
//                                }
//                            }
                        divingReportMap.put("C_JINGDU_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("布放时纬度") && !"".equals(cellValueContent)) {
//                            double doubleWeidu = Double.valueOf(cellValueArray[1]);
//                            if (cellValueContent.contains(".")) {
//                                int pointRemainLength = cellValueContent.substring(cellValueContent.lastIndexOf(".") + 1, cellValueContent.length()).length();
//                                if (pointRemainLength>4){
//                                    //保留四位小数
//                                    DecimalFormat df = new DecimalFormat("0.0000");
//                                    cellValueContent = df.format(doubleWeidu);
//                                }
//                            }
                        divingReportMap.put("C_WEIDU_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("人员进舱时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("personComeinCabinT".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("舱口盖关闭时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("C_HATCH_CLOSE_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("压载铁限位销拆除时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("ballastRemoveTime".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("下达布放指令时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("bufangCommandTime".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("一次布放小艇时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("onceBufangboatTime".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("布放时小艇驾驶人员")) {
                        divingReportMap.put("bufangboatDriver".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("布放时小艇辅助人员")) {
                        divingReportMap.put("bufangboatAssistant".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("脱缆人员")) {
                        divingReportMap.put("tuolanPeople".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("脱缆辅助人员")) {
                        divingReportMap.put("tuolanAssistant".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("潜水器入水时间")) {
                        divingReportMap.put("C_IN_WATER_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("时区")) {
                        divingReportMap.put("timeZone".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("开始注水时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("C_START_FILL_WATER_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("一次回收小艇时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("onceRecoverboatTime".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("布放时最大阵风风速")) {
                        divingReportMap.put("bufangMaxWindSpeed".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("布放时最大平均风速")) {
                        divingReportMap.put("bufangAverageWSpeed".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("布放时浪高")) {
                        divingReportMap.put("bufangLangHeight".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("布放时海况评估")) {
                        divingReportMap.put("bufangSeaStateEstima".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("开始作业时间")) {
                        divingReportMap.put("C_WORK_START_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("作业开始深度")) {
                        divingReportMap.put("C_THROW_END_DEPTH_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("结束作业时间")) {
                        divingReportMap.put("C_WORK_END_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("作业结束深度")) {
                        divingReportMap.put("C_THROW_UP_DEPTH_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("最大深度(米)")) {
                        divingReportMap.put("C_DIVING_DEPTH_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("采样作业情况")) {
                        divingReportMap.put("C_SAMPLE_SITUATION_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("布放类型")) {
                        divingReportMap.put("bufangType".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("上浮到水面时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("C_FLOAT_WATER_TIME_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("二次布放小艇时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("twiceBufangboatTime".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("回收时小艇驾驶人员")) {
                        divingReportMap.put("recoverboatDperson".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("回收时小艇辅助人员")) {
                        divingReportMap.put("recoverboatAssistant".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("挂缆人员")) {
                        divingReportMap.put("gualanPeople".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("挂缆辅助人员")) {
                        divingReportMap.put("gualanAssistant".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("潜水器出水时间")) {
                        divingReportMap.put("C_OUT_WATER_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("回收到甲板时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("recoverDeckTime".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("二次回收小艇时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("twiceRecoverboatTime".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("舱口盖开启时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("C_HATCH_OPEN_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("人员出舱时间") && !"".equals(cellValueContent)) {
                        divingReportMap.put("personOutCabinTime".toUpperCase() + "_" + divingReportBM.getId(), TimeUtil.getTime(TimeUtil.getDateByDateString(cellValueArray[1]), TimeUtil.convertString(cellValueArray[1])));
                    } else if (cellValueArray[0].contains("回收时最大阵风风速(节)")) {
                        divingReportMap.put("recoverMaxWindSpeed".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("回收时最大平均风速(节)")) {
                        divingReportMap.put("recoverMAverageWindS".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("回收时浪高")) {
                        divingReportMap.put("recoverLangHeight".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    } else if (cellValueArray[0].contains("回收时海况评估")) {
                        divingReportMap.put("recoverSeaStateEstim".toUpperCase() + "_" + divingReportBM.getId(), cellValueArray[1]);
                    }
                }
            }
        }
    }

    private void handlerCheckType2Table(IBusinessModel rowInstBM, IBusinessModel cellDataBM, IBusinessModel cellInstBM,
                                        List<RowBean> rowBeans, List<String> headerIds, List<String> colIndexs,
                                        String checkInstID, String uploader, List<Map<String, String>> cellValueData) {
        rowInstBM.clearAllFilter();
        rowInstBM.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + checkInstID + "'");
        List<Map> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).orderAsc("TO_NUMBER(C_ROW_NUMBER_" + rowInstBM.getId() + ")").list();

        String sql = "select max(to_number(C_ROW_NUMBER_" + rowInstBM.getId() + ")) from T_CHECK_ROW_INST_" + schemaId + " where T_CHECK_TEMP_INST_" + schemaId + "_ID = '" + checkInstID + "'";
        int maxRowNumber = (int) metaDaoFactory.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
        });
//        if (maxRowNumber > 0) {
        if (rowInstList != null && rowInstList.size() > 0) {
            for (RowBean rowBean : rowBeans) {
                int orderNumber = Integer.parseInt(rowBean.getOrder());
                String serverUploaderId = rowBean.getServerUploaderId();
                List<CellDataBean> cellDataBeanList = rowBean.getCells();
                boolean isInsertNewRow = true;
                //第一次上传
                for (Map rowInstMap : rowInstList) {
                    String rowInstId = rowInstMap.get("ID").toString();
                    int rowNumber = Integer.parseInt(rowInstMap.get("C_ROW_NUMBER_" + rowInstBM.getId()).toString());
                    String rowPerson = CommonTools.Obj2String(rowInstMap.get("C_UPLOADER_" + rowInstBM.getId()));
                    if (orderNumber == rowNumber) {
//                        if (rowPerson.equals(uploader) || "".equals(rowPerson) || rowPerson.equals(serverUploaderId)) {
                        if (rowPerson.equals(uploader) || "".equals(rowPerson) || rowPerson.equals(serverUploaderId)) {
                            if (!"".equals(rowPerson) && rowPerson.equals(serverUploaderId)) {
                                isInsertNewRow = false;
                            } else {
                                rowInstMap.put("C_UPLOADER_" + rowInstBM.getId(), uploader);
                                isInsertNewRow = false;
                                orientSqlEngine.getBmService().updateModelData(rowInstBM, rowInstMap, rowInstId);
                            }
                            if (cellDataBeanList != null && cellDataBeanList.size() > 0) {
                                for (CellDataBean cellDataBean : cellDataBeanList) {
                                    uploadCellInstData(cellDataBean.getValue(), cellDataBean.getId(), cellDataBM, cellDataBean.getType(), checkInstID, cellValueData, cellDataBean.isOperation());
                                }
                            }
                            break;
                        }
                    }
                }
                if (isInsertNewRow) {   //数据库不存在
                    maxRowNumber++;
//                }
//
//                //数据库存在
//                if (orderNumber <= maxRowNumber) {
//                    if (cellDataBeanList != null && cellDataBeanList.size() > 0) {
//                        for (CellDataBean cellDataBean : cellDataBeanList) {
//                            uploadCellInstData(cellDataBean.getValue(), cellDataBean.getId(), cellDataBM, cellDataBean.getType(), checkInstID);
//                        }
//                    }
//                } else {  //数据库不存在
                    Map<String, String> rowData = UtilFactory.newHashMap();
                    rowData.put("C_ROW_NUMBER_" + rowInstBM.getId(), String.valueOf(maxRowNumber));
                    rowData.put("C_UPLOADER_" + rowInstBM.getId(), uploader);
                    rowData.put(PropertyConstant.CHECK_TEMP_INST + "_" + schemaId + "_ID", checkInstID);
                    String newRowId = orientSqlEngine.getBmService().insertModelData(rowInstBM, rowData);
                    if (cellDataBeanList != null && cellDataBeanList.size() > 0) {
                        for (CellDataBean cellDataBean : cellDataBeanList) {
                            int cellIndex = Integer.parseInt(cellDataBean.getColIndex());
                            String cellType = cellDataBean.getType();
                            String content = cellDataBean.getContent();
                            if (!"0".equals(cellType)) {
                                content = formTemplateMgrBusiness.getDisplayType("#" + cellType);
                            }
                            String value = cellDataBean.getValue();
                            value = "".equals(value) ? "" : value;
                            String headerId = "";
                            for (int i = 0; i < colIndexs.size(); i++) {
                                if (i == cellIndex) {
                                    headerId = headerIds.get(i);
                                }
                            }
                            Map<String, String> cellData = UtilFactory.newHashMap();
                            cellData.put("C_CONTENT_" + cellInstBM.getId(), content);
                            cellData.put("C_CELL_TYPE_" + cellInstBM.getId(), "#" + cellType);
                            cellData.put(PropertyConstant.CHECK_HEADER_INST + "_" + schemaId + "_ID", headerId);
                            cellData.put(PropertyConstant.CHECK_ROW_INST + "_" + schemaId + "_ID", newRowId);
                            //主要是方便获取单元格，不需要多次查询数据库
                            cellData.put(PropertyConstant.CHECK_TEMP_INST + "_" + schemaId + "_ID", checkInstID);
                            String newCellId = orientSqlEngine.getBmService().insertModelData(cellInstBM, cellData);
                            cellDataBean.setId(newCellId);
                            if (!StringUtil.isEmpty(value)) {
                                if ("1".equals(cellType) || "2".equals(cellType) || "4".equals(cellType) || "5".equals(cellType) || "6".equals(cellType) || "10".equals(cellType) || "11".equals(cellType)) {
                                    Map<String, String> cellDataMap = UtilFactory.newHashMap();
                                    cellDataMap.put("C_CONTENT_" + cellDataBM.getId(), value);
                                    cellDataMap.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", checkInstID);
                                    cellDataMap.put("T_CHECK_CELL_INST_" + schemaId + "_ID", newCellId);
                                    orientSqlEngine.getBmService().insertModelData(cellDataBM, cellDataMap);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private CheckListTableBean copyInsertCheckHeaderCellData(CheckListTableBean checkListTableBean, String newTempInstId) {
        String tempTableInstName = PropertyConstant.CHECK_TEMP_INST;
        String headerTableInstName = PropertyConstant.CHECK_HEADER_INST;
        String rowTableInstName = PropertyConstant.CHECK_ROW_INST;
        String cellTableInstName = PropertyConstant.CHECK_CELL_INST;
        String cellDataName = PropertyConstant.CELL_INST_DATA;
        IBusinessModel headerInstBM = businessModelService.getBusinessModelBySName(headerTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(rowTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(cellTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellDataBM = businessModelService.getBusinessModelBySName(cellDataName, schemaId, EnumInter.BusinessModelEnum.Table);
        //提取拍照、签署、故障行返回终端
        LinkedList<RowBean> collectPictureRowBeanList = new LinkedList<>();
        List<HeadBean> headBeanList = checkListTableBean.getHeadBeanArrayList();
        //数据库中插入表头
        List<String> headerIds = UtilFactory.newArrayList();
        List<String> colIndexs = UtilFactory.newArrayList();
        for (HeadBean headBean : headBeanList) {
            Map<String, String> data = UtilFactory.newHashMap();
            data.put("C_NAME_" + headerInstBM.getId(), headBean.getName());
            data.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
            String conIndex = headBean.getColIndex();
            String id = orientSqlEngine.getBmService().insertModelData(headerInstBM, data);
            colIndexs.add(conIndex);
            headerIds.add(id);
        }
        //异常表上传表头表尾内容
        List<FrontContentBean> frontContentBeanList = checkListTableBean.getFrontContentBeanList();
        List<EndContentBean> endContentBeanList = checkListTableBean.getEndContentBeanList();
        LinkedList giveClientFrontContentBeanList = uploadFrontEndTableData(frontContentBeanList, true, newTempInstId, cellDataBM, cellInstBM, tempTableInstName, cellTableInstName);
        LinkedList giveClientEndContentBeanList = uploadFrontEndTableData(endContentBeanList, false, newTempInstId, cellDataBM, cellInstBM, tempTableInstName, cellTableInstName);

        List<RowBean> rowBeanList = checkListTableBean.getRowBeanArrayList();
        for (RowBean rowBean : rowBeanList) {
            Map<String, String> rowData = UtilFactory.newHashMap();
            rowData.put("C_ROW_NUMBER_" + rowInstBM.getId(), rowBean.getOrder());
            rowData.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
            String newRowId = orientSqlEngine.getBmService().insertModelData(rowInstBM, rowData);
            List<CellDataBean> cellDataBeanList = rowBean.getCells();
            //判断单元格类型是否是拍照、签署、故障
            boolean isCollect = false;
            for (CellDataBean cellDataBean : cellDataBeanList) {
                String upCellId = cellDataBean.getId();
                int cellIndex = Integer.parseInt(cellDataBean.getColIndex());
                String cellType = cellDataBean.getType();
                String content = cellDataBean.getContent();
                if (!"0".equals(cellType)) {
                    content = formTemplateMgrBusiness.getDisplayType("#" + cellType);
                }
                String value = cellDataBean.getValue();
                value = "".equals(value) ? "" : value;
                String headerId = "";
                for (int i = 0; i < colIndexs.size(); i++) {
                    if (i == cellIndex) {
                        headerId = headerIds.get(i);
                    }
                }
                Map<String, String> cellData = UtilFactory.newHashMap();
                if ("0".equals(cellType)) {
                    cellData.put("C_CONTENT_" + cellInstBM.getId(), value);
                } else {
                    cellData.put("C_CONTENT_" + cellInstBM.getId(), content);
                }
                cellData.put("C_CELL_TYPE_" + cellInstBM.getId(), "#" + cellType);
                cellData.put(headerTableInstName + "_" + schemaId + "_ID", headerId);
                cellData.put(rowTableInstName + "_" + schemaId + "_ID", newRowId);
                //主要是方便获取单元格，不需要多次查询数据库
                cellData.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
                String newCellId = orientSqlEngine.getBmService().insertModelData(cellInstBM, cellData);
                //拍照项、签署项、故障项
                if ("3".equals(cellType) || "8".equals(cellType) || "9".equals(cellType)) {
                    //此处建立此字段，是为了后期异常表和正常表的图片同时上传时获取异常表的单元格ID
                    // 区分异常表的单元格ID和正常表的单元格ID
//                    cellData.put("C_EXCEPTION_CELLID_" + cellInstBM.getId(), upCellId);
                    cellDataBean.setId(newCellId);
                    isCollect = true;
                }
                if (!StringUtil.isEmpty(value)) {
                    if ("1".equals(cellType) || "2".equals(cellType) || "4".equals(cellType) || "5".equals(cellType) || "6".equals(cellType) || "10".equals(cellType) || "11".equals(cellType)) {
                        Map<String, String> cellDataMap = UtilFactory.newHashMap();
                        cellDataMap.put("C_CONTENT_" + cellDataBM.getId(), value);
                        cellDataMap.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", newTempInstId);
                        cellDataMap.put("T_CHECK_CELL_INST_" + schemaId + "_ID", newCellId);
                        orientSqlEngine.getBmService().insertModelData(cellDataBM, cellDataMap);
                    }
                }
            }
            if (isCollect) {
                collectPictureRowBeanList.add(rowBean);
            }
        }
        if (giveClientFrontContentBeanList != null && giveClientFrontContentBeanList.size() > 0) {
            checkListTableBean.setFrontContentBeanList(giveClientFrontContentBeanList);
        }
        if (giveClientEndContentBeanList != null && giveClientEndContentBeanList.size() > 0) {
            checkListTableBean.setEndContentBeanList(giveClientEndContentBeanList);
        }
        if (collectPictureRowBeanList != null && collectPictureRowBeanList.size() > 0) {
            checkListTableBean.setRowBeanArrayList(collectPictureRowBeanList);
        }
        if ((giveClientFrontContentBeanList != null && giveClientFrontContentBeanList.size() > 0) || (giveClientEndContentBeanList != null && giveClientEndContentBeanList.size() > 0) || (collectPictureRowBeanList != null && collectPictureRowBeanList.size() > 0)) {
            return checkListTableBean;
        } else {
            return null;
        }
    }

    private LinkedList uploadFrontEndTableData(List contentBeanList, boolean isFrontOrEnd, String
            newTempInstId, IBusinessModel cellDataBM,
                                               IBusinessModel cellInstBM, String tempTableInstName, String cellTableInstName) {
        LinkedList giveClientFrontContentBeanList = new LinkedList<>();
        if (contentBeanList.size() > 0) {
            if (isFrontOrEnd) {
                List<FrontContentBean> frontContentBeanList = contentBeanList;
                for (FrontContentBean frontContentBean : frontContentBeanList) {
                    String newCellId = TestUploadBusiness.insertFrontEndMap(cellInstBM, cellDataBM, orientSqlEngine, tempTableInstName,
                            cellTableInstName, schemaId, frontContentBean.getName(), frontContentBean.getCellType(),
                            frontContentBean.getId(), frontContentBean.getContent(), newTempInstId, isFrontOrEnd);
                    if (newCellId != null) {
                        frontContentBean.setId(newCellId);
                        giveClientFrontContentBeanList.add(frontContentBean);
                    }
                }
            } else {
                List<EndContentBean> frontContentBeanList = contentBeanList;
                for (EndContentBean endContentBean : frontContentBeanList) {
                    String newCellId = TestUploadBusiness.insertFrontEndMap(cellInstBM, cellDataBM, orientSqlEngine, tempTableInstName,
                            cellTableInstName, schemaId, endContentBean.getName(), endContentBean.getCellType(),
                            endContentBean.getId(), endContentBean.getContent(), newTempInstId, isFrontOrEnd);
                    if (newCellId != null) {
                        endContentBean.setId(newCellId);
                        giveClientFrontContentBeanList.add(endContentBean);
                    }
                }
            }
        }
        return giveClientFrontContentBeanList;
    }

    private static String insertFrontEndMap(IBusinessModel cellInstBM, IBusinessModel cellDataBM, ISqlEngine
            orientSqlEngine,
                                            String tempTableInstName, String cellTableInstName, String schemaId,
                                            String name, String cellType, String cellId, String content,
                                            String newTempInstId, boolean isFrontOrEnd) {
        Map frontEndContentMap = UtilFactory.newHashMap();
        frontEndContentMap.put("C_CONTENT_" + cellInstBM.getId(), name);
        frontEndContentMap.put("C_CELL_TYPE_" + cellInstBM.getId(), "#" + cellType);
        frontEndContentMap.put("C_IS_HEADER_" + cellInstBM.getId(), isFrontOrEnd);
        frontEndContentMap.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
        String newCellId = orientSqlEngine.getBmService().insertModelData(cellInstBM, frontEndContentMap);
        if ("1".equals(cellType) || "2".equals(cellType) || "4".equals(cellType) || "5".equals(cellType) || "6".equals(cellType) || "10".equals(cellType) || "11".equals(cellType)) {
            Map uploadContentMap = UtilFactory.newHashMap();
            uploadContentMap.put("C_CONTENT_" + cellDataBM.getId(), content);
            uploadContentMap.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
            uploadContentMap.put(cellTableInstName + "_" + schemaId + "_ID", newCellId);
            orientSqlEngine.getBmService().insertModelData(cellDataBM, uploadContentMap);
        }
        //拍照项、签署项、故障项
        if ("3".equals(cellType) || "8".equals(cellType) || "9".equals(cellType)) {
            //此处建立此字段，是为了后期异常表和正常表的图片同时上传时获取异常表的单元格ID
            // 区分异常表的单元格ID和正常表的单元格ID
            frontEndContentMap.put("C_EXCEPTION_CELLID_" + cellInstBM.getId(), cellId);
            return newCellId;
        } else {
            return null;
        }
    }


    private void uploadCellInstData(String value, String cellId, IBusinessModel cellDataBM, String cellType, String
            checkInstID, List<Map<String, String>> cellValueData, boolean isOperateCell) {
        if (!StringUtil.isEmpty(value)) {
            //考虑多次上传数据的情况
//            cellDataBM.clearCustomFilter();
//            cellDataBM.appendCustomerFilter(new CustomerFilter("T_CHECK_CELL_INST_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, cellId));
//            cellDataBM.appendCustomerFilter(new CustomerFilter("T_CHECK_TEMP_INST_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, checkInstID));

//            List<Map<String, String>> cellData = orientSqlEngine.getBmService().createModelQuery(cellDataBM).list();
            if (cellType.equals("1") || cellType.equals("2") || cellType.equals("4") || cellType.equals("5") || cellType.equals("6") || cellType.equals("10") || cellType.equals("11")) {
                boolean isNewAddCellValue = true;
                if (cellValueData != null && cellValueData.size() > 0) {
                    for (Map cellValueMap : cellValueData) {
                        String cellValueId = cellValueMap.get("ID").toString();
                        String refCellId = CommonTools.Obj2String(cellValueMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                        if (cellId.equals(refCellId)) {
                            if ("1".equals(cellType) || "10".equals(cellType)) {
                                if (isOperateCell) {
                                    cellValueMap.put("C_CONTENT_" + cellDataBM.getId(), value);
                                }
                            } else {
                                cellValueMap.put("C_CONTENT_" + cellDataBM.getId(), value);
                            }
                            orientSqlEngine.getBmService().updateModelData(cellDataBM, cellValueMap, cellValueId);
                            isNewAddCellValue = false;
                            break;
                        }
                    }
                }
                if (isNewAddCellValue) {
                    Map<String, String> map = UtilFactory.newHashMap();
                    map.put("C_CONTENT_" + cellDataBM.getId(), value);
                    map.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", checkInstID);
                    map.put("T_CHECK_CELL_INST_" + schemaId + "_ID", cellId);
                    orientSqlEngine.getBmService().insertModelData(cellDataBM, map);
                }
//                Map<String, String> map = UtilFactory.newHashMap();
//                map.put("C_CONTENT_" + cellDataBM.getId(), value);
//                map.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", checkInstID);
//                map.put("T_CHECK_CELL_INST_" + schemaId + "_ID", cellId);
//                if (cellData.size() == 0) {
//                    orientSqlEngine.getBmService().insertModelData(cellDataBM, map);
//                } else {
//                    map = cellData.get(0);
//                    String id = map.get("ID");
//                    map.put("C_CONTENT_" + cellDataBM.getId(), value);
//                    map.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", checkInstID);
//                    map.put("T_CHECK_CELL_INST_" + schemaId + "_ID", cellId);
//                    orientSqlEngine.getBmService().updateModelData(cellDataBM, map, id);
//                }
            }
        }
    }


    public HttpResponse uploadSignInfo(SignModel signModel) {
        HttpResponse httpResponse = new HttpResponse();
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel flowBM = businessModelService.getBusinessModelBySName(PropertyConstant.NODE_DESIGN, schemaId, EnumInter.BusinessModelEnum.Table);
        String type = signModel.getType();
        String targetId = signModel.getTargetId();
        String result = signModel.getResult();
        String reason = signModel.getReason();
        String signDate = signModel.getCreateDate();
        String signerId = signModel.getUserId();
        if (type.equals("table")) {
            checkTempInstBM.clearAllFilter();
            checkTempInstBM.setReserve_filter("AND ID='" + targetId + "'");
            List<Map<String, String>> checkTempInstList = testDownloadBusiness.commonGetList(checkTempInstBM);
            if (checkTempInstList != null && checkTempInstList.size() > 0) {
                for (Map checkMap : checkTempInstList) {
                    checkMap.put("C_REJECT_REASON_" + checkTempInstBM.getId(), reason);
                    checkMap.put("C_SIGN_DATE_" + checkTempInstBM.getId(), signDate);
                    checkMap.put("C_SIGNER_" + checkTempInstBM.getId(), signerId);
                    String checkTempInstState = (String) checkMap.get("C_CHECK_STATE_" + checkTempInstBM.getId());
                    if (result.equals("success")) {
                        if (checkTempInstState.equals("已上传")) {
                            checkMap.put("C_CHECK_STATE_" + checkTempInstBM.getId(), "已签署");
                        }
                    } else {
                        if (checkTempInstState.equals("已上传")) {
                            checkMap.put("C_CHECK_STATE_" + checkTempInstBM.getId(), "未通过");
                        }
                    }
                    orientSqlEngine.getBmService().updateModelData(checkTempInstBM, checkMap, targetId);
                }
                httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
                httpResponse.setData("1");
            } else {
                httpResponse.setResult(HttpResponseStatus.FAIL.toString());
                httpResponse.setMsg("服务端没有此数据");
                httpResponse.setData(new ArrayList<>());
            }
        }
        return httpResponse;
    }


    public HttpResponse uploadCheckInstImage(String cellId, String type, CommonsMultipartFile[]
            images, HttpServletRequest request) {
        HttpResponse httpResponse = new HttpResponse();
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel troubleDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel waterDownBM = businessModelService.getBusinessModelBySName(PropertyConstant.WATER_DOWN_RECORD, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel dailyWorkBM = businessModelService.getBusinessModelBySName(PropertyConstant.DAILY_WORK, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = cellInstBM.getId();
        //1代表表格图片，2是故障设备图片，3是故障设备的录音文件，4是更换设备图片，5是更换设备的录音文件,6代表水下记录图片，7代表下潜的语音
        String troubleId = "";
        List<Map<String, Object>> voiceList = UtilFactory.newArrayList();
        if ("2".equals(type) || "3".equals(type) || "4".equals(type) || "5".equals(type)) {
            troubleDeviceBM.setReserve_filter(" and c_number_id_" + troubleDeviceBM.getId() + "='" + cellId + "'");
            List<Map<String, String>> troubleList = testDownloadBusiness.commonGetList(troubleDeviceBM);
            if (troubleList.size() > 0) {
                troubleId = troubleList.get(0).get("ID");
                modelId = troubleDeviceBM.getId();
                StringBuilder checkImageSql = new StringBuilder();
                checkImageSql.append("select * from CWM_FILE").append(" where TABLEID='").append(modelId).append("' AND DATAID='" + troubleId + "' AND FILETYPE='amr'");
                voiceList = jdbcTemplate.queryForList(checkImageSql.toString());
            }
        }
//
//        String waterDownId = "";
        if ("6".equals(type) || "7".equals(type)) {
            waterDownBM.setReserve_filter(" AND C_PAD_ID_" + waterDownBM.getId() + "='" + cellId + "'");
            List<Map<String, String>> waterDownList = testDownloadBusiness.commonGetList(waterDownBM);
            if (waterDownList.size() > 0) {
//                waterDownId = waterDownList.get(0).get("ID");
                troubleId = waterDownList.get(0).get("ID");
            }
        }

        List<Map<String, Object>> checkImageType1List = UtilFactory.newArrayList();
        //11表示每日工作的图片
        if ("1".equals(type) || "2".equals(type) || "11".equals(type)) {
            if ("2".equals(type)) {
                cellId = troubleId;
                modelId = troubleDeviceBM.getId();
            }
            if ("11".equals(type)) {
                modelId = dailyWorkBM.getId();
            }
            StringBuilder checkImageSql = new StringBuilder();
            checkImageSql.append("select * from CWM_FILE").append(" where TABLEID='").append(modelId).append("' AND DATAID='" + cellId + "'");
            checkImageType1List = jdbcTemplate.queryForList(checkImageSql.toString());
        }
        try {
            String folderName = "";
            for (CommonsMultipartFile img : images) {
                String originalImageName = img.getOriginalFilename();
                String fileName = new Date().getTime() + "_" + img.getOriginalFilename();
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                CwmFile cwmFile = new CwmFile();
                //1代表表格图片，2是故障设备图片，3是故障设备的录音文件，4是更换设备图片，5是更换设备的录音文件,6代表水下记录图片，7代表下潜的语音
                if ("1".equals(type) || "2".equals(type) || "11".equals(type)) {
                    //1代表正常表的拍照
                    if ("1".equals(type) || "11".equals(type)) {
                        folderName = "imagesForChecklist";
                    } else if ("2".equals(type)) {
                        folderName = "imagesForTDevice";
                        cellId = troubleId;
                        modelId = troubleDeviceBM.getId();
                    }
//                    StringBuilder checkImageSql = new StringBuilder();
//                    checkImageSql.append("select * from CWM_FILE").append(" where FILENAME ='").append(img.getOriginalFilename()).append("' AND TABLEID='").append(modelId).append("' AND FILETYPE='")
//                            .append(fileType).append("'").append(" AND DATAID='" + cellId + "'");
//                    List<Map<String, Object>> checkImageList = jdbcTemplate.queryForList(checkImageSql.toString());
                    boolean isNewAddImage = true;
                    if (checkImageType1List.size() > 0) {
//                        continue;
                        Iterator<Map<String, Object>> iterator = checkImageType1List.iterator();
                        while (iterator.hasNext()) {
                            Map checkImageMap = iterator.next();
                            String dbfinalName = CommonTools.Obj2String(checkImageMap.get("FILENAME"));
                            if ("jpg".equals(fileType)) {
                                if (originalImageName.equals(dbfinalName)) {
                                    iterator.remove();
                                    isNewAddImage = false;
                                }
                            }
                        }
                    }
                    if (!isNewAddImage) {
                        continue;
                    }
                    cwmFile.setDataid(cellId);
                    cwmFile.setTableid(modelId);
                }
//                else if ("2".equals(type)) {
//                    folderName = "imagesForTDevice";
//                    cwmFile.setDataid(troubleId);
//                    cwmFile.setTableid(troubleDeviceBM.getId());
//                }
                else if ("3".equals(type)) {
                    folderName = "voiceFileForTDevice";
                    cwmFile.setDataid(troubleId);
                    cwmFile.setTableid(troubleDeviceBM.getId());
                } else if ("4".equals(type)) {
                    folderName = "imagesForRDevice";
                    cwmFile.setDataid(troubleId);
                    cwmFile.setTableid(troubleDeviceBM.getId());
                } else if ("5".equals(type)) {
                    //5替换的设备
                    folderName = "voiceFileForRDevice";
                    cwmFile.setDataid(troubleId);
                    cwmFile.setTableid(troubleDeviceBM.getId());
                } else if ("6".equals(type)) {
                    folderName = "imagesForWaterDown";
//                    cwmFile.setDataid(waterDownId);
                    cwmFile.setDataid(troubleId);
                    cwmFile.setTableid(waterDownBM.getId());
                } else if ("7".equals(type)) {
                    folderName = "voiceFileForDiving";
//                    cwmFile.setDataid(waterDownId);
                    cwmFile.setDataid(troubleId);
                    cwmFile.setTableid(waterDownBM.getId());
                }
//                else if (type.equals("9")) {
//                    folderName = "voiceFileForDiving";
//                    cwmFile.setDataid(waterDownId);
//                    cwmFile.setTableid(waterDownBM.getId());
//                }
                else if ("8".equals(type)) {
                    //8代表异常的表的图片类型（异常表的图片上传走这一个，包括拍照和签署）
                    folderName = "imagesForChecklist";
                    cellInstBM.setReserve_filter("AND C_EXCEPTION_CELLID_" + modelId + "='" + cellId + "'"
//                            +
//                            " AND C_CELL_TYPE_" + cellInstBM.getId() + "='" + "#3" + "'");
                    );
                    List<Map<String, Object>> cellInstList = orientSqlEngine.getBmService().createModelQuery(cellInstBM).list();
                    if (cellInstList.size() > 0) {
                        //终端重做的异常表的拍照单元格Id
                        cellId = (String) cellInstList.get(0).get("ID");
                        StringBuilder checkImageSql = new StringBuilder();
                        checkImageSql.append("select * from CWM_FILE").append(" where FILENAME ='").append(img.getOriginalFilename()).append("' AND TABLEID='").append(modelId).append("' AND FILETYPE='")
                                .append(fileType).append("'").append(" AND DATAID='" + cellId + "'");
                        ;
                        List<Map<String, Object>> checkImageList = jdbcTemplate.queryForList(checkImageSql.toString());
                        if (checkImageList.size() > 0) {
                            continue;
                        }
                    }
                    cwmFile.setDataid(cellId);
                    cwmFile.setTableid(modelId);
                } else if ("10".equals(type)) {
                    //签署
                    folderName = "imagesForChecklist";
                    StringBuilder checkImageSql = new StringBuilder();
//                    checkImageSql.append("select * from CWM_FILE").append(" where FILENAME ='").append(img.getOriginalFilename()).append("' AND TABLEID='").append(modelId).append("' AND FILETYPE='")
//                            .append(fileType).append("'").append(" AND DATAID='" + cellId + "'");
                    checkImageSql.append("select * from CWM_FILE").append(" where TABLEID='").append(modelId).append("' AND DATAID='" + cellId + "'");
                    List<Map<String, Object>> checkImageList = jdbcTemplate.queryForList(checkImageSql.toString());
                    if (checkImageList != null && checkImageList.size() > 0) {
                        for (Map signImageMap : checkImageList) {
                            String fileId = CommonTools.Obj2String(signImageMap.get("FILEID"));
                            String finalImageName = CommonTools.Obj2String(signImageMap.get("FINALNAME"));
                            String imagePath = fileServerConfig.getFtpHome() + File.separator + folderName + File.separator + finalImageName;
                            File imageFile = new File(imagePath);
                            //删除imagesForChecklist文件夹下的原图片
                            if (imageFile.exists()) {
                                imageFile.delete();
                            }
                            //删除缩略图中存在的原图片
                            String suolueImageFolderPath = fileServerConfig.getFtpHome() + File.separator + "imageSuoluetu" + File.separator;
                            imageFile = new File(suolueImageFolderPath + finalImageName);
                            if (imageFile.exists()) {
                                imageFile.delete();
                            }
                            //删除缩略图
                            String finalImageSName = finalImageName.substring(0, finalImageName.lastIndexOf(".")) + "_s." + fileType;
                            String imageSPath = suolueImageFolderPath + finalImageSName;
                            if (new File(imageSPath).exists()) {
                                new File(imageSPath).delete();
                            }
                            //删除数据库中的图片路径数据
                            fileService.deleteFile(fileId);
                        }
                    }
                    cwmFile.setDataid(cellId);
                    cwmFile.setTableid(modelId);
                }else if ("12".equals(type)){
                    //12是每日工作语音
                    folderName = "voiceFileForDailyWork";
                    cwmFile.setDataid(cellId);
                    cwmFile.setTableid(dailyWorkBM.getId());
                }
                String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + folderName;
                if (!new File(imageFolderPath).exists()) {
                    new File(imageFolderPath).mkdirs();
                }
                String imageFile = imageFolderPath + File.separator + fileName;
                img.transferTo(new File(imageFile));
                if ("3".equals(type) || "5".equals(type)||"12".equals(type)) {
                    if (voiceList != null && voiceList.size() > 0) {
                        for (Map voiceMap : voiceList) {
                            String fileId = CommonTools.Obj2String(voiceMap.get("FILEID"));
                            String finalVoiceName = CommonTools.Obj2String(voiceMap.get("FINALNAME"));
                            String voicePath = fileServerConfig.getFtpHome() + File.separator + folderName + File.separator + finalVoiceName;
                            File voiceFile = new File(voicePath);
                            //删除voiceFileForTDevice/voiceFileForRDevice/voiceFileForDiving文件夹下.amr的语音
                            if (voiceFile.exists()) {
                                voiceFile.delete();
                            }
                            //删除.mp3文件
                            String finalImageSName = finalVoiceName.substring(0, finalVoiceName.lastIndexOf(".")) + ".mp3";
                            String imageSPath = fileServerConfig.getFtpHome() + File.separator + folderName + File.separator + finalImageSName;
                            if (new File(imageSPath).exists()) {
                                new File(imageSPath).delete();
                            }
                            //删除数据库中的语音路径数据
                            fileService.deleteFile(fileId);
                        }
                    }
                    //转换.amr格式为.mp3格式
                    String sourcePath = imageFile;
                    sourcePath = FileOperator.toStanderds(sourcePath);
                    String fileNameMp3 = fileName.substring(0, fileName.length() - 4) + ".mp3";
//                    String voiceFolderPath = getPreviewVoicePath() + File.separator + folderName;
//                    if (!new File(voiceFolderPath).exists()) {
//                        new File(voiceFolderPath).mkdirs();
//                    }
//                    String targetMp3Path = voiceFolderPath + File.separator + fileNameMp3;
                    String targetMp3Path = imageFolderPath + File.separator + fileNameMp3;
                    targetMp3Path = FileOperator.toStanderds(targetMp3Path);
                    targetMp3Path = FileOperator.toStanderds(targetMp3Path);
                    ChangeAmrToMp3.changeAmrToMp3(sourcePath, targetMp3Path);
                }
//                cwmFile.setDataid(cellId);
//                cwmFile.setTableid(modelId);
                cwmFile.setFilelocation(File.separator + folderName + File.separator + fileName);
                cwmFile.setUploadStatus(CwmFile.UPLOAD_STATUS_SUCCESS);
                cwmFile.setFileCatalog("common");
                cwmFile.setFilename(img.getOriginalFilename());
                cwmFile.setUploadDate(new Date());
                cwmFile.setFiletype(fileType);
                cwmFile.setFilesize(img.getSize());
                cwmFile.setFinalname(fileName);
                //保存文件到preview
//                String targetPath = CommonTools.getPreviewImagePath() + File.separator + fileName;
//                FileOperator.copyFile(imageFile, targetPath);
                String imageSPath = fileServerConfig.getFtpHome() + File.separator + "imageSuoluetu";
                if (!new File(imageSPath).exists()) {
                    new File(imageSPath).mkdirs();
                }
                imageSPath = imageSPath + File.separator + fileName;
                FileOperator.copyFile(imageFile, imageSPath);
                //图片类型文件处理,设置缩略图
                try {
                    ImageUtils.zoomImageScale(new File(imageSPath), 200);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cwmFile.setConverState("1");
                fileService.createFile(cwmFile);
            }
            if ("1".equals(type) || "2".equals(type)) {
                //终端删除图片，服务端数据库也应该删除
                if (checkImageType1List != null && checkImageType1List.size() > 0) {
                    for (Map removeMap : checkImageType1List) {
                        String fileId = removeMap.get("FILEID").toString();
                        String fileType = CommonTools.Obj2String(removeMap.get("FILETYPE"));
                        String finalImageName = CommonTools.Obj2String(removeMap.get("FINALNAME"));
                        if ("jpg".equals(fileType)) {
                            String imagePath = fileServerConfig.getFtpHome() + File.separator + folderName + File.separator + finalImageName;
                            File imageFile = new File(imagePath);
                            //删除imagesForChecklist文件夹下的原图片
                            if (imageFile.exists()) {
                                imageFile.delete();
                            }
                            //删除缩略图中存在的原图片
                            String suolueImageFolderPath = fileServerConfig.getFtpHome() + File.separator + "imageSuoluetu" + File.separator;
                            imageFile = new File(suolueImageFolderPath + finalImageName);
                            if (imageFile.exists()) {
                                imageFile.delete();
                            }
                            //删除缩略图
                            String finalImageSName = finalImageName.substring(0, finalImageName.lastIndexOf(".")) + "_s." + fileType;
                            String imageSPath = suolueImageFolderPath + finalImageSName;
                            if (new File(imageSPath).exists()) {
                                new File(imageSPath).delete();
                            }
                            //删除数据库中的图片路径数据
                            fileService.deleteFile(fileId);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("上传异常！");
            return httpResponse;
        }
        httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
        httpResponse.setData(new FileResultModel(cellId, type));
        return httpResponse;
    }

    public HttpResponse<String> uploadWaterDownRecord(DiveModel diveModel) {
        HttpResponse httpResponse = new HttpResponse();
        IBusinessModel waterDownRecordBM = businessModelService.getBusinessModelBySName(PropertyConstant.WATER_DOWN_RECORD, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        String taskName = diveModel.getTitle();

        divingTaskBM.setReserve_filter(" AND C_TASK_NAME_" + divingTaskBM.getId() + "='" + taskName + "'");
        List<Map<String, String>> taskList = testDownloadBusiness.commonGetList(divingTaskBM);
        if (taskList.size() > 0) {
            String taskId = taskList.get(0).get("ID");
            List<RecordModel> waterRecordList = diveModel.getRecords();
            if (waterRecordList.size() > 0) {
                for (RecordModel recordModel : waterRecordList) {
                    Map waterMap = UtilFactory.newHashMap();
                    waterMap.put("C_RECORD_TITLE_" + waterDownRecordBM.getId(), recordModel.getContent());
                    waterMap.put("C_RECORD_PERSON_" + waterDownRecordBM.getId(), recordModel.getUserId());
                    waterMap.put("C_RECORD_DATE_" + waterDownRecordBM.getId(), recordModel.getDate());
                    waterMap.put("C_RECORD_DEPTH_" + waterDownRecordBM.getId(), recordModel.getDepth());
                    waterMap.put("C_PAD_ID_" + waterDownRecordBM.getId(), recordModel.getId());
                    waterMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
                    waterMap.put("C_RECORD_NAME_" + waterDownRecordBM.getId(), recordModel.getRecordName());
                    orientSqlEngine.getBmService().insertModelData(waterDownRecordBM, waterMap);
                }
                httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
                httpResponse.setData("1");
            } else {
                httpResponse.setResult(HttpResponseStatus.FAIL.toString());
                httpResponse.setMsg("水下记录表为空");
                httpResponse.setData(new ArrayList<>());
            }
        } else {
            httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("服务端无此任务数据！");
            httpResponse.setData(new ArrayList<>());
        }

        return httpResponse;
    }

    public static String getPreviewVoicePath() {
        return CommonTools.getRootPath() + File.separator + "preview";
    }

    //插入表单模板检查项数据
    public String insertCheckListTemplate(String checkName, String checkTempId, String taskId, String
            nodeId, String nodeText, String hangciId, String insType, String checkTableIsRepeatUpload, CheckListTableBean checkListTableBean) {
        String tableName = PropertyConstant.CHECK_TEMP_INST;
        IBusinessModel checkInstBM = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> data = UtilFactory.newHashMap();
        data.put("C_NAME_" + checkInstBM.getId(), checkName);
        data.put("C_CHECK_TEMP_ID_" + checkInstBM.getId(), checkTempId);
        data.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
        data.put("C_NODE_ID_" + checkInstBM.getId(), nodeId);
        data.put("C_NODE_TEXT_" + checkInstBM.getId(), nodeText);
        data.put("C_CHECK_STATE_" + checkInstBM.getId(), "异常");
        data.put("C_INS_TYPE_" + checkInstBM.getId(), insType);
        data.put("T_HANGCI_" + schemaId + "_ID", hangciId);
        data.put("C_CHECK_TIME_" + checkInstBM.getId(), checkListTableBean.getCheckDate());
        data.put("C_CHECK_PERSON_" + checkInstBM.getId(), checkListTableBean.getCheckPerson());
        //异常字段表示检查表检查的设备是正确的还是错误的，重做表示上传的数据是正确的，可以重复上传，异常即isException是true，表示上传的设备数据之前是有误的
//        data.put("C_EXCEPTION_" + checkInstBM.getId(), "异常");
        data.put("C_ATTENTION_" + checkInstBM.getId(), checkListTableBean.getIsAttention());
        data.put("C_IS_REPEAT_UPLOAD_" + checkInstBM.getId(), checkTableIsRepeatUpload);
        return orientSqlEngine.getBmService().insertModelData(checkInstBM, data);
    }

    //异常表复制表格数据插入到数据库
    public void insertHeaderCellData(CheckListTableBean checkListTableBean, String newTempInstId) {
        String tempTableInstName = PropertyConstant.CHECK_TEMP_INST;
        String headerTableInstName = PropertyConstant.CHECK_HEADER_INST;
        String rowTableInstName = PropertyConstant.CHECK_ROW_INST;
        String cellTableInstName = PropertyConstant.CHECK_CELL_INST;
        String cellDataName = PropertyConstant.CELL_INST_DATA;

        IBusinessModel headerInstBM = businessModelService.getBusinessModelBySName(headerTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(rowTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(cellTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel spareInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellDataBM = businessModelService.getBusinessModelBySName(cellDataName, schemaId, EnumInter.BusinessModelEnum.Table);
        String cellDataModelId = cellDataBM.getId();
        List<HeadBean> headBeanList = checkListTableBean.getHeadBeanArrayList();
        //数据库中插入表头
        List<String> headerIds = UtilFactory.newArrayList();
        List<String> colIndexs = UtilFactory.newArrayList();
        for (HeadBean headBean : headBeanList) {
            Map<String, String> data = UtilFactory.newHashMap();
            data.put("C_NAME_" + headerInstBM.getId(), headBean.getName());
            data.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
            String conIndex = headBean.getColIndex();
            String id = orientSqlEngine.getBmService().insertModelData(headerInstBM, data);
            colIndexs.add(conIndex);
            headerIds.add(id);
        }
        //异常表上传表头表尾内容
        List<FrontContentBean> frontContentBeanList = checkListTableBean.getFrontContentBeanList();
        List<EndContentBean> endContentBeanList = checkListTableBean.getEndContentBeanList();
        if (frontContentBeanList.size() > 0) {
            for (FrontContentBean frontContentBean : frontContentBeanList) {
                String frontCellId = frontContentBean.getId();
                String name = frontContentBean.getName();
                String cellType = frontContentBean.getCellType();
                String content = frontContentBean.getContent();
                Map frontContentMap = UtilFactory.newHashMap();
                frontContentMap.put("C_CONTENT_" + cellInstBM.getId(), name);
                frontContentMap.put("C_CELL_TYPE_" + cellInstBM.getId(), "#" + cellType);
                frontContentMap.put("C_IS_HEADER_" + cellInstBM.getId(), true);
                //拍照项和签署项
                if ("3".equals(cellType) || "8".equals(cellType)) {
                    //此处建立此字段，是为了后期异常表和正常表的图片同时上传时获取异常表的单元格ID
                    // 区分异常表的单元格ID和正常表的单元格ID
                    frontContentMap.put("C_EXCEPTION_CELLID_" + cellInstBM.getId(), frontCellId);
                }
                frontContentMap.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
                String cellId = orientSqlEngine.getBmService().insertModelData(cellInstBM, frontContentMap);
                Map frontUploadContentMap = UtilFactory.newHashMap();
                frontUploadContentMap.put("C_CONTENT_" + cellDataBM.getId(), content);
                frontUploadContentMap.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
                frontUploadContentMap.put(cellTableInstName + "_" + schemaId + "_ID", cellId);
                orientSqlEngine.getBmService().insertModelData(cellDataBM, frontUploadContentMap);
            }
        }
        if (endContentBeanList.size() > 0) {
            for (EndContentBean endContentBean : endContentBeanList) {
                String endCellId = endContentBean.getId();
                String name = endContentBean.getName();
                String cellType = endContentBean.getCellType();
                String content = endContentBean.getContent();
                Map endContentMap = UtilFactory.newHashMap();
                endContentMap.put("C_CONTENT_" + cellInstBM.getId(), name);
                endContentMap.put("C_CELL_TYPE_" + cellInstBM.getId(), "#" + cellType);
                endContentMap.put("C_IS_HEADER_" + cellInstBM.getId(), false);
                endContentMap.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
                //拍照项和签署项
                if ("3".equals(cellType) || "8".equals(cellType)) {
                    //此处建立此字段，是为了后期异常表和正常表的图片同时上传时获取异常表的单元格ID
                    // 区分异常表的单元格ID和正常表的单元格ID
                    endContentMap.put("C_EXCEPTION_CELLID_" + cellInstBM.getId(), endCellId);
                }
                String cellId = orientSqlEngine.getBmService().insertModelData(cellInstBM, endContentMap);
                Map endUploadContentMap = UtilFactory.newHashMap();
                endUploadContentMap.put("C_CONTENT_" + cellDataBM.getId(), content);
                endUploadContentMap.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
                endUploadContentMap.put(cellTableInstName + "_" + schemaId + "_ID", cellId);
                orientSqlEngine.getBmService().insertModelData(cellDataBM, endUploadContentMap);
            }
        }

        rowInstBM.clearAllFilter();
        rowInstBM.setReserve_filter("AND " + tempTableInstName + "_" + schemaId + "_ID='" + checkListTableBean.getId() + "'");
        List<Map> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).list();

        cellInstBM.clearAllFilter();
        List<Map> cellInstList = orientSqlEngine.getBmService().createModelQuery(cellInstBM).list();

        List<RowBean> rowBeanList = checkListTableBean.getRowBeanArrayList();
        for (RowBean rowBean : rowBeanList) {
            Map<String, String> rowData = UtilFactory.newHashMap();
            String upRowId = rowBean.getDbId();
            String rowId = "";
            String productId = "";
            for (Map rowMap : rowInstList) {
                String rowInstId = CommonTools.Obj2String(rowMap.get("ID"));
                if (upRowId.equals(rowInstId)) {
                    productId = CommonTools.Obj2String(rowMap.get("C_PRODUCT_ID_" + rowInstBM.getId()));
                    if (StringUtil.isNotEmpty(productId)) {
                        spareInstBM.setReserve_filter("AND C_PRODUCT_ID_" + spareInstBM.getId() + "='" + productId + "'");
                        List<Map> deviceInstList = orientSqlEngine.getBmService().createModelQuery(spareInstBM).list();
                        StringBuilder deviceInstIds = new StringBuilder();
                        if (deviceInstList.size() > 0) {
                            for (Map deviceInstMap : deviceInstList) {
                                String spareInstId = (String) deviceInstMap.get("ID");
                                deviceInstIds.append(spareInstId);
                                deviceInstIds.append(",");
                            }
                            String deviceInstId = deviceInstIds.toString();
                            deviceInstId = deviceInstId.substring(0, deviceInstId.length() - 1);
                            if (deviceInstId != null && !"".equals(deviceInstId)) {
                                if (deviceInstId.indexOf(",") == -1) {
                                    rowData.put("C_DEVICE_INST_ID_" + rowInstBM.getId(), deviceInstId);
                                }
                            }
                        }
                    }
                    String rowValue = CommonTools.Obj2String(rowMap.get("C_ROW_NUMBER_" + rowInstBM.getId()));
                    rowData.put("C_ROW_NUMBER_" + rowInstBM.getId(), rowValue);
                    rowData.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
                    rowData.put("C_PRODUCT_ID_" + rowInstBM.getId(), productId);
                    rowId = orientSqlEngine.getBmService().insertModelData(rowInstBM, rowData);
                    break;
                }
            }

            List<CellDataBean> cellDataBeanList = rowBean.getCells();
            for (CellDataBean cellDataBean : cellDataBeanList) {
                String upCellId = cellDataBean.getId();
                int cellIndex = Integer.parseInt(cellDataBean.getColIndex());
                String cellType = cellDataBean.getType();
                String content = cellDataBean.getContent();
                String headerId = "";
                for (int i = 0; i < colIndexs.size(); i++) {
                    if (i == cellIndex) {
                        headerId = headerIds.get(i);
                    }
                }
                for (Map cellMap : cellInstList) {
                    String cellInstId = cellMap.get("ID").toString();
                    if (upCellId.equals(cellInstId)) {
                        String value = CommonTools.Obj2String(cellMap.get("C_CONTENT_" + cellInstBM.getId()));
                        value = "".equals(value) ? "" : value;
                        String upCellType = CommonTools.Obj2String(cellMap.get("C_CELL_TYPE_" + cellInstBM.getId()));
                        String checkJoin = CommonTools.Obj2String(cellMap.get("C_CHECK_JOIN_" + cellInstBM.getId()));
                        Map<String, String> cellData = UtilFactory.newHashMap();
                        cellData.put("C_CONTENT_" + cellInstBM.getId(), value);
                        cellData.put("C_CELL_TYPE_" + cellInstBM.getId(), upCellType);
                        cellData.put("C_CHECK_JOIN_" + cellInstBM.getId(), checkJoin);
                        cellData.put("C_PRODUCT_ID_" + cellInstBM.getId(), productId);
                        cellData.put(headerTableInstName + "_" + schemaId + "_ID", headerId);
                        cellData.put(rowTableInstName + "_" + schemaId + "_ID", rowId);
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellData.put(tempTableInstName + "_" + schemaId + "_ID", newTempInstId);
                        //拍照项和签署项
                        if ("#3".equals(upCellType) || "#8".equals(upCellType)) {
                            //此处建立此字段，是为了后期异常表和正常表的图片同时上传时获取异常表的单元格ID
                            // 区分异常表的单元格ID和正常表的单元格ID
                            cellData.put("C_EXCEPTION_CELLID_" + cellInstBM.getId(), upCellId);
                        }
                        String cellId = orientSqlEngine.getBmService().insertModelData(cellInstBM, cellData);
                        if (!StringUtil.isEmpty(content)) {
                            if (cellDataBean.getType().equals("1") || cellDataBean.getType().equals("2") || cellDataBean.getType().equals("4") || cellDataBean.getType().equals("5") || cellDataBean.getType().equals("6") || cellDataBean.getType().equals("10") || cellDataBean.getType().equals("11")) {
                                Map<String, String> map = UtilFactory.newHashMap();
                                map.put("C_CONTENT_" + cellDataModelId, content);
                                map.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", newTempInstId);
                                map.put("T_CHECK_CELL_INST_" + schemaId + "_ID", cellId);
                                orientSqlEngine.getBmService().insertModelData(cellDataBM, map);
                            }
                        }
                    }
                }
            }
        }
    }

    public HttpResponse updateConsumeData(ConsumeBean consumeBean) {
        HttpResponse httpResponse = new HttpResponse();
        String consumeId = consumeBean.getId();
        //耗材消耗个数
        int cosumeNumber = consumeBean.getNumber();
        IBusinessModel consumeBM = businessModelService.getBusinessModelBySName(PropertyConstant.CONSUME_DETAIL, schemaId, EnumInter.BusinessModelEnum.Table);
        consumeBM.clearAllFilter();
        consumeBM.setReserve_filter("AND ID='" + consumeId + "'");
        List<Map<String, Object>> consumeList = orientSqlEngine.getBmService().createModelQuery(consumeBM).list();
        if (consumeList.size() > 0) {
            Map consumeMap = consumeList.get(0);
            //耗材总数
            String number = (String) consumeMap.get("C_NUMBER_" + consumeBM.getId());
            if (number == null || "".equals(number)) {
                number = "0";
            }
            int consumeAllNumer = Integer.parseInt(number);
            //耗材剩余个数
            if (consumeAllNumer >= cosumeNumber) {
                int consumeRemainNumber = consumeAllNumer - cosumeNumber;
                consumeMap.put("C_NUMBER_" + consumeBM.getId(), consumeRemainNumber);
                String version = (String) consumeMap.get("C_VERSION_" + consumeBM.getId());
                version = String.valueOf(Integer.parseInt(version) + 1);
                consumeMap.put("C_VERSION_" + consumeBM.getId(), version);
                orientSqlEngine.getBmService().updateModelData(consumeBM, consumeMap, consumeId);
                httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
                httpResponse.setData("1");
            }
        }
//        else {
            /*httpResponse.setResult(HttpResponseStatus.FAIL.toString());
            httpResponse.setMsg("此耗材在服务端不存在！");
            httpResponse.setData(new ArrayList<>());*/
//        }
        return httpResponse;
    }

    public LinkedList<DeviceInstCheckEntity> uploadDeviceInstCheckEvent(List<DeviceInstCheckEntity> deviceInstCheckEntityList) {
        IBusinessModel structDeviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_DEVICE_INS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstCheckEventBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_INS_EVENT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, String>> structDeviceInstList = testDownloadBusiness.commonGetList(structDeviceInstBM);
        LinkedList<DeviceInstCheckEntity> giveClientDeviceInstCheckList = new LinkedList();
        for (DeviceInstCheckEntity deviceInstCheckEntity : deviceInstCheckEntityList) {
            String deviceInstId = deviceInstCheckEntity.getDeviceInstId();
            String systemId = deviceInstCheckEntity.getSystemId();
            LinkedList<DeviceInstCheckEventEntity> giveClientDeviceInstEventEntityList = new LinkedList<>();
            List<DeviceInstCheckEventEntity> deviceInstCheckEventEntityList = deviceInstCheckEntity.getDeviceInstCheckEventEntities();
            if (deviceInstCheckEventEntityList != null && deviceInstCheckEventEntityList.size() > 0) {
                for (DeviceInstCheckEventEntity deviceInstCheckEventEntity : deviceInstCheckEventEntityList) {
                    String checkEventName = deviceInstCheckEventEntity.getCheckEventName();
                    String checkerId = deviceInstCheckEventEntity.getChecker();
                    StringBuilder userSql = new StringBuilder();
                    String checker = "";
                    if (!"".equals(checkerId) && checkerId != null) {
                        userSql.append("select id,all_name from cwm_sys_user where id ='").append(checkerId).append("'");
                        List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());
                        if (userList != null && userList.size() > 0) {
                            checker = CommonTools.Obj2String(userList.get(0).get("all_name"));
                        }
                    }
                    String checkTime = deviceInstCheckEventEntity.getCheckTime();
                    String isPassed = deviceInstCheckEventEntity.getPassed();
                    Map deviceInstEventMap = UtilFactory.newHashMap();
                    deviceInstEventMap.put("C_NAME_" + deviceInstCheckEventBM.getId(), checkEventName);
                    deviceInstEventMap.put("C_CHECK_PERSON_" + deviceInstCheckEventBM.getId(), checker);
                    deviceInstEventMap.put("C_CHECK_TIME_" + deviceInstCheckEventBM.getId(), checkTime);
                    deviceInstEventMap.put("C_IS_CHECK_" + deviceInstCheckEventBM.getId(), isPassed);
                    deviceInstEventMap.put("T_STRUCT_DEVICE_INS_" + schemaId + "_ID", deviceInstId);
                    String structDeviceId = "";
                    for (Map deviceInstMap : structDeviceInstList) {
                        String hasDeviceInstId = deviceInstMap.get("ID").toString();
                        if (deviceInstId.equals(hasDeviceInstId)) {
                            structDeviceId = CommonTools.Obj2String(deviceInstMap.get("T_STRUCT_DEVICE_" + schemaId + "_ID"));
                            break;
                        }
                    }
                    deviceInstEventMap.put("T_STRUCT_SYSTEM_" + schemaId + "_ID", systemId);
                    deviceInstEventMap.put("T_STRUCT_DEVICE_" + schemaId + "_ID", structDeviceId);
                    String newDeviceInstEventId = orientSqlEngine.getBmService().insertModelData(deviceInstCheckEventBM, deviceInstEventMap);
                    List<CheckListTableBean> checkListTableBeanList = deviceInstCheckEventEntity.getCheckListTableBeanList();
                    LinkedList<CheckListTableBean> giveClientCheckTableList = new LinkedList<>();
                    if (checkListTableBeanList != null && checkListTableBeanList.size() > 0) {
                        for (CheckListTableBean checkListTableBean : checkListTableBeanList) {
                            String checkName = checkListTableBean.getName();
                            String checkPerson = checkListTableBean.getCheckPerson();
                            String checkDate = checkListTableBean.getCheckDate();
                            String checkTableType = checkListTableBean.getCheckTableType();
                            Map checkInstMap = UtilFactory.newHashMap();
                            checkInstMap.put("C_NAME_" + checkInstBM.getId(), checkName);
                            checkInstMap.put("C_CHECK_STATE_" + checkInstBM.getId(), "已上传");
                            checkInstMap.put("C_INS_TYPE_" + checkInstBM.getId(), checkTableType);
                            checkInstMap.put("C_CHECK_TIME_" + checkInstBM.getId(), checkDate);
                            checkInstMap.put("C_CHECK_PERSON_" + checkInstBM.getId(), checker);
                            checkInstMap.put("C_ATTENTION_" + checkInstBM.getId(), checkListTableBean.getIsAttention());
                            checkInstMap.put("T_DEVICE_INS_EVENT_" + systemId + "_ID", newDeviceInstEventId);
                            String newCheckInstId = orientSqlEngine.getBmService().insertModelData(checkInstBM, checkInstMap);
                            //复制检查表模板实例
                            checkListTableBean = this.copyInsertCheckHeaderCellData(checkListTableBean, newCheckInstId);
                            //返回给终端的检查表
                            if (checkListTableBean != null) {
                                giveClientCheckTableList.add(checkListTableBean);
                            }
                        }
                    }
                    if (giveClientCheckTableList != null &&
                            giveClientCheckTableList.size() > 0) {
                        deviceInstCheckEventEntity.setCheckListTableBeanList(giveClientCheckTableList);
                        giveClientDeviceInstEventEntityList.add(deviceInstCheckEventEntity);
                    }
                }
            }
            if (giveClientDeviceInstEventEntityList != null && giveClientDeviceInstEventEntityList.size() > 0) {
                deviceInstCheckEntity.setDeviceInstCheckEventEntities(giveClientDeviceInstEventEntityList);
                giveClientDeviceInstCheckList.add(deviceInstCheckEntity);
            }
        }
        return giveClientDeviceInstCheckList;
    }

    public LinkedList<DailyWorkEntity> uploadDailyWorkData(List<DailyWorkEntity> dailyWorkEntityList) throws ParseException {
        IBusinessModel dailyWorkBM = businessModelService.getBusinessModelBySName(PropertyConstant.DAILY_WORK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structSystemBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_SYSTEM, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structDeviceInsBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_DEVICE_INS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM=businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK,schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structDeviceBM=businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_DEVICE,schemaId, EnumInter.BusinessModelEnum.Table);

        LinkedList<DailyWorkEntity> dailyWorkEntityLinkedList = new LinkedList<>();
//        StringBuilder sql = new StringBuilder();
//        sql.append("select id,all_name from cwm_sys_user");
//        sql.append(" where PERSON_CLASSIFY='").append("1'").append(" and state='").append("1'");
//        List<Map<String,Object>> userList=jdbcTemplate.queryForList(sql.toString());
        for (DailyWorkEntity dailyWorkEntity : dailyWorkEntityList) {
            String padId = CommonTools.Obj2String(dailyWorkEntity.getPadUUID());
            Map dailyWorkMap = UtilFactory.newHashMap();
            if (!"".equals(padId)) {
                dailyWorkBM.clearAllFilter();
                dailyWorkBM.setReserve_filter("AND C_PAD_ID_" + dailyWorkBM.getId() + "='" + padId + "'");
                List<Map<String, Object>> dailyWorkList = orientSqlEngine.getBmService().createModelQuery(dailyWorkBM).list();
                if (dailyWorkList != null && dailyWorkList.size() > 0) {
                    Map hasDailyWorkMap= dailyWorkList.get(0);
                    String dailyWorkId=hasDailyWorkMap.get("ID").toString();
                    orientSqlEngine.getBmService().deleteCascade(dailyWorkBM,dailyWorkId);
                    StringBuilder fileSql = new StringBuilder();
                    fileSql.append("select * from CWM_FILE").append(" where TABLEID='").append(dailyWorkBM.getId()).append("' AND DATAID='" + dailyWorkId + "'");
                    List<Map<String,Object>> deleteFileList = jdbcTemplate.queryForList(fileSql.toString());
                    if (deleteFileList != null && deleteFileList.size() > 0) {
                        for (Map voiceMap : deleteFileList) {
                            String fileId = CommonTools.Obj2String(voiceMap.get("FILEID"));
                            String filelocation = CommonTools.Obj2String(voiceMap.get("FILELOCATION"));
                            String fileType=CommonTools.Obj2String(voiceMap.get("FILETYPE"));
                            String deleteFilePath = fileServerConfig.getFtpHome()  + filelocation;
                            //标准化文件路径
                            deleteFilePath = FileOperator.toStanderds(deleteFilePath);
                            File deleteFile = new File(deleteFilePath);
                            //删除语音及图片
                            if (deleteFile.exists()) {
                                deleteFile.delete();
                            }
                            if ("amr".equals(fileType)){
                                //删除.mp3文件
                                filelocation = filelocation.substring(0, filelocation.lastIndexOf(".")) + ".mp3";
                                String mp3FilePath = fileServerConfig.getFtpHome()  + filelocation;
                                //标准化语音文件路径
                                mp3FilePath = FileOperator.toStanderds(mp3FilePath);
                                if (new File(mp3FilePath).exists()) {
                                    new File(mp3FilePath).delete();
                                }
                            }
                            //删除数据库中的图片语音路径数据
                            fileService.deleteFile(fileId);
                        }
                    }
                }
                dailyWorkMap.put("C_PAD_ID_"+dailyWorkBM.getId(),padId);
            }

            String workDate = dailyWorkEntity.getWorkDate();
            if (!"".equals(workDate) && workDate != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(workDate);
                dailyWorkMap.put("C_WORK_DATE_" + dailyWorkBM.getId(), CommonTools.util2Sql(date));
            }
            dailyWorkMap.put("C_WORK_CONTENT_" + dailyWorkBM.getId(), CommonTools.Obj2String(dailyWorkEntity.getWorkContent()));
            dailyWorkMap.put("C_WORK_TYPE_" + dailyWorkBM.getId(), CommonTools.Obj2String(dailyWorkEntity.getWorkType()));
            String personnel = CommonTools.Obj2String(dailyWorkEntity.getPersonnel());
            if (!"".equals(personnel)) {
                String attendPersonNames = "";
                String attendPersonIds="";
                String personnelArray[] = personnel.split(",");
                List<String> personnelUserNameIdsList = Arrays.asList(personnelArray);
                for (String nameIds : personnelUserNameIdsList) {
                    String userAllName = nameIds.split("-")[0];
                    String userId = nameIds.split("-")[1];
                    attendPersonNames += userAllName + ",";
                    attendPersonIds+=userId+",";
                }
                if (!"".equals(attendPersonNames)) {
                    attendPersonNames = attendPersonNames.substring(0, attendPersonNames.length() - 1);
                    dailyWorkMap.put("C_PERSONNEL_" + dailyWorkBM.getId(), attendPersonNames);
                }
                if (!"".equals(attendPersonIds)){
                    attendPersonIds = attendPersonIds.substring(0, attendPersonIds.length() - 1);
                    dailyWorkMap.put("C_USER_ID_" + dailyWorkBM.getId(), attendPersonIds);
                }
            }
            String structSystem = CommonTools.Obj2String(dailyWorkEntity.getBelongSystem());
            String structDeviceIns = CommonTools.Obj2String(dailyWorkEntity.getBelongDeviceIns());
            String belongTask=CommonTools.Obj2String(dailyWorkEntity.getBelongDivingTask());
            if (!"".equals(structSystem)){
               Map structSystemMap=orientSqlEngine.getBmService().createModelQuery(structSystemBM).findById(structSystem);
               if (structSystemMap!=null&&structSystemMap.size()>0){
                   dailyWorkMap.put("C_STRUCT_SYSTEM_" + dailyWorkBM.getId(), CommonTools.Obj2String(structSystemMap.get("C_NAME_"+structSystemBM.getId())));
               }
            }
            if (!"".equals(structDeviceIns)){
                StringBuffer deviceInsSql=new StringBuffer();
                deviceInsSql.append("select i.C_NUMBER_").append(structDeviceInsBM.getId())
                        .append(",d.C_NAME_").append(structDeviceBM.getId())
                        .append(" from T_STRUCT_DEVICE_INS_"+schemaId)
                        .append(" i left join T_STRUCT_DEVICE_"+schemaId)
                        .append(" d on i.T_STRUCT_DEVICE_"+schemaId+"_ID=d.id where i.id='")
                        .append(structDeviceIns).append("'");
                List<Map<String,Object>> deviceInstList=jdbcTemplate.queryForList(deviceInsSql.toString());
                if (deviceInstList!=null&&deviceInstList.size()>0){
                    String deviceName=CommonTools.Obj2String(deviceInstList.get(0).get("C_NAME_"+structDeviceBM.getId()));
                    String deviceInsNumber=CommonTools.Obj2String(deviceInstList.get(0).get("C_NUMBER_"+structDeviceInsBM.getId()));
                    dailyWorkMap.put("C_STRUCT_DEVICE_INS_" + dailyWorkBM.getId(),deviceName+"-"+deviceInsNumber);
                }
            }
//            if (!"".equals(belongTask)){
//                divingTaskBM.setReserve_filter("AND C_TASK_NAME_"+divingTaskBM.getId()+"='"+belongTask+"'");
//               List<Map<String,Object>>  divingTaskList=orientSqlEngine.getBmService().createModelQuery(divingTaskBM).list();
//                if (divingTaskList!=null&&divingTaskList.size() > 0){
//                    Map divingTaskMap=divingTaskList.get(0);
//                    String taskId=CommonTools.Obj2String(divingTaskMap.get("ID"));
//                    dailyWorkMap.put("T_DIVING_TASK_"+schemaId+"_ID",taskId);
//                }
//            }
            dailyWorkMap.put("C_DIVING_TASK_"+dailyWorkBM.getId(),belongTask);
            dailyWorkMap.put("C_STRUCT_SYSTEM_ID_"+dailyWorkBM.getId(),structSystem);
            dailyWorkMap.put("C_DEVICE_INS_ID_"+dailyWorkBM.getId(),structDeviceIns);
            String dailyWorkId = orientSqlEngine.getBmService().insertModelData(dailyWorkBM, dailyWorkMap);
            dailyWorkEntity.setDailyWorkId(dailyWorkId);
            dailyWorkEntityLinkedList.add(dailyWorkEntity);
        }
        return dailyWorkEntityLinkedList;
    }
}