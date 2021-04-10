package com.orient.weibao.controller;

import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelServiceImpl;
import com.orient.edm.asyncbean.EDM_UserContainer_Async;
import com.orient.edm.init.FileServerConfig;
import com.orient.log.annotion.Action;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.modeldata.fileHandle.OrientModelFileHandle;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.user.User;
import com.orient.utils.*;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.utils.image.ImageUtils;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.form.model.FileModel;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.accountingFormTableBean.CurrentOutTemplateBean;
import com.orient.weibao.bean.accountingFormTableBean.DivingDeviceTableBean;
import com.orient.weibao.bean.accountingFormTableBean.DivingPlanTableBean;
import com.orient.weibao.bean.accountingFormTableBean.DivingReportTableBean;
import com.orient.weibao.business.AccountingFormMgrBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.dao.CarryToolDao;
import com.orient.weibao.dao.DivingPlanTableDao;
import com.orient.weibao.dao.InformLogDao;
import com.orient.weibao.dto.CarryToolWithParams;
import com.orient.weibao.mbg.mapper.DivingPlanTableMapper;
import com.orient.weibao.mbg.mapper.InformLogMapper;
import com.orient.weibao.mbg.model.CheckTempInst;
import com.orient.weibao.mbg.model.DivingPlanTable;
import com.orient.weibao.mbg.model.InformLog;
import com.orient.weibao.scheduler.BackUpData;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.orient.utils.CommonTools.Obj2String;

@Controller
@RequestMapping("/accountingForm")
public class AccountingFormMgrController {

    @Autowired
    BusinessModelServiceImpl businessModelService;
    @Autowired
    ISqlEngine orientSqlEngine;
    @Autowired
    MetaDAOFactory metaDAOFactory;
    @Autowired
    AccountingFormMgrBusiness accountingFormMgrBusiness;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    FileServerConfig fileServerConfig;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    @RequestMapping("getPersons")
    @ResponseBody
    public AjaxResponseData<List<Map>> getPersons(String hangduanId, String taskId) {
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map> personsList = UtilFactory.newArrayList();
        hangduanBM.setReserve_filter("AND ID ='" + hangduanId + "'");
        List<Map<String, Object>> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanBM).list();
        if (hangduanList.size() > 0) {
            Map hangduanMap = hangduanList.get(0);
            String attendPersons = Obj2String(hangduanMap.get("C_ATTEND_PERSON_" + hangduanBM.getId()));
            if (!"".equals(attendPersons)) {
                StringBuilder userSql = new StringBuilder();
                userSql.append("select id,all_name from cwm_sys_user where id in(").append(attendPersons).append(")").append("order by all_name asc");
                List<Map<String, Object>> userList = metaDAOFactory.getJdbcTemplate().queryForList(userSql.toString());
                if (userList.size() > 0) {
                    for (Map userMap : userList) {
                        Map personMap = UtilFactory.newHashMap();
                        String allName = Obj2String(userMap.get("all_name"));
                        String userId = Obj2String(userMap.get("ID"));
                        personMap.put("id", userId);
                        personMap.put("name", allName);
                        personsList.add(personMap);
                    }
                }
            }
        }
        return new AjaxResponseData<>(personsList);
    }

    @RequestMapping("getCarryToolList")
    @ResponseBody
    public AjaxResponseData<List> getCarryToolList() {
        IBusinessModel carryToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CABIN_CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map> cabinCarryToolList = orientSqlEngine.getBmService().createModelQuery(carryToolBM).list();
        List<Map> carryToolList = UtilFactory.newArrayList();
        if (cabinCarryToolList.size() > 0) {
            for (Map hasCarryMap : cabinCarryToolList) {
                Map carryMap = UtilFactory.newHashMap();
                String spareName = Obj2String(hasCarryMap.get("C_NAME_" + carryToolBM.getId()));
                String spareId = Obj2String(hasCarryMap.get("ID"));
                String airWeight = Obj2String(hasCarryMap.get("C_AIR_WEIGHT_" + carryToolBM.getId()));
                String deWaterVolume = Obj2String(hasCarryMap.get("C_DEWATER_VOLUME_" + carryToolBM.getId()));
                String freshWaterWeight = Obj2String(hasCarryMap.get("C_FRESH_WATER_WEIGHT_" + carryToolBM.getId()));
                String isCabinOutOrIn = CommonTools.Obj2String(hasCarryMap.get("C_CABIN_INOROUT_" + carryToolBM.getId()));
                String connectWay = CommonTools.Obj2String(hasCarryMap.get("C_CONNECT_WAY_" + carryToolBM.getId()));
                String length = CommonTools.Obj2String(hasCarryMap.get("C_LENGTH_" + carryToolBM.getId()));
                String width = CommonTools.Obj2String(hasCarryMap.get("C_WIDTH_" + carryToolBM.getId()));
                carryMap.put("id", spareId);
                carryMap.put("name", spareName);
                carryMap.put("airWeight", airWeight);
                carryMap.put("deWaterVolume", deWaterVolume);
                carryMap.put("freshWaterWeight", freshWaterWeight);
                carryMap.put("isCabinOutOrIn", isCabinOutOrIn);
                carryMap.put("connectWay", connectWay);
                carryMap.put("length", length);
                carryMap.put("width", width);
//                carryMap.put("planCarryCount", planCarryCount);
                carryToolList.add(carryMap);
            }
        }
        return new AjaxResponseData<>(carryToolList);
    }

    @Action(ownermodel = "任务管理", detail = "提交下潜计划表")
    @RequestMapping("submitDivingPlanTable")
    @ResponseBody
    public void submitDivingPlanTable(DivingPlanTableBean divingPlanTableBean, boolean submitType) {
        IBusinessModel divingPlanTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel carryWorkToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel informLogTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_LOG, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel balanceTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);

        User curUser = UserContextUtil.getCurrentUser();
        String taskId = divingPlanTableBean.getTaskId();
        Map taskMap = orientSqlEngine.getBmService().createModelQuery(divingTaskTableBM).findById(taskId);
        String taskState = CommonTools.Obj2String(taskMap.get("C_STATE_" + divingTaskTableBM.getId()));
        String taskName = CommonTools.Obj2String(taskMap.get("C_TASK_NAME_" + divingTaskTableBM.getId()));
        divingPlanTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" + "" +
                " AND C_TABLE_STATE_" + divingPlanTableBM.getId() + "='" + "当前" + "'");

        List<Map> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanTableBM).list();
        String specialRowData = divingPlanTableBean.getSpecialRowData();
        String cabinInRowData = divingPlanTableBean.getCabinInRowData();
        String totalNetWeight = divingPlanTableBean.getTotalNetWeight();
        if (divingPlanList.size() == 0) {
            Map divingPlanMap = divingPlanMap(divingPlanTableBM, divingPlanTableBean, divingPlanList.size(), taskId);
            Map informLogMap = UtilFactory.newHashMap();
            if (submitType) {
                divingPlanMap.put("C_SAVE_OR_SUBMIT_" + divingPlanTableBM.getId(), "submit");
                if (!"已结束".equals(taskState)) {
                    informLogMap.put("C_TYPE_" + informLogTableBM.getId(), "plan");
                    informLogMap.put("C_TABLE_NAME_" + informLogTableBM.getId(), "下潜计划表");
                    informLogMap.put("C_STATE_" + informLogTableBM.getId(), "新增");
                    accountingFormMgrBusiness.informMap(informLogTableBM, taskId, taskName, curUser, informLogMap);
                }
            } else {
                divingPlanMap.put("C_SAVE_OR_SUBMIT_" + divingPlanTableBM.getId(), "save");
            }
            String divingPlanId = orientSqlEngine.getBmService().insertModelData(divingPlanTableBM, divingPlanMap);
            //新增配重
            Map peizhongMap = UtilFactory.newHashMap();
            peizhongMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            peizhongMap.put("C_SERIAL_NUMBER_" + balanceTableBM.getId(), "1");
            peizhongMap.put("C_TABLE_STATE_" + balanceTableBM.getId(), "当前");
            peizhongMap.put("T_DIVING__PLAN_TABLE_" + schemaId + "_ID", divingPlanId);
            orientSqlEngine.getBmService().insertModelData(balanceTableBM, peizhongMap);

            if (submitType && (!"已结束".equals(taskState))) {
                informLogMap.put("C_TABLE_ID_" + informLogTableBM.getId(), divingPlanId);
                orientSqlEngine.getBmService().insertModelData(informLogTableBM, informLogMap);
            }
//            JSONArray jsonArray = null;
//            try {
//                jsonArray = new JSONArray(specialRowData);
//                if (jsonArray.length() > 0) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String deviceId = jsonObject.getString("deviceId");
//                        String rowNumber = jsonObject.getString("rowNumber");
//                        String carryCount = jsonObject.getString("carryCount");
//                        String airWeight = jsonObject.getString("airWeight");
//                        String pWaterVolume = jsonObject.getString("pWaterVolume");
//                        String connectWay=jsonObject.getString("connectWay");
//                        String freshWeight = jsonObject.getString("freshWeight");
//                        Map carryToolMap = UtilFactory.newHashMap();
//                        carryToolMap.put("C_DEVICE_ID_" + carryWorkToolBM.getId(), deviceId);
//                        carryToolMap.put("C_ROW_NUMBER_" + carryWorkToolBM.getId(), rowNumber);
//                        carryToolMap.put("C_CARRY_COUNT_" + carryWorkToolBM.getId(), carryCount);
//                        carryToolMap.put("C_AIR_WEIGHT_" + carryWorkToolBM.getId(), airWeight);
//                        carryToolMap.put("C_PW_VOLUMN_" + carryWorkToolBM.getId(), pWaterVolume);
//                        carryToolMap.put("C_FRESH_WATER_" + carryWorkToolBM.getId(), freshWeight);
//                        carryToolMap.put("C_CONNECT_WAY_" + carryWorkToolBM.getId(), connectWay);
//                        carryToolMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
//                        orientSqlEngine.getBmService().insertModelData(carryWorkToolBM, carryToolMap);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//            accountingFormMgrBusiness.carryToolMap(specialRowData, carryWorkToolBM, taskId, true, divingPlanId);
//            accountingFormMgrBusiness.carryToolMap(cabinInRowData, carryWorkToolBM, taskId, false, divingPlanId);
//            accountingFormMgrBusiness.totalCarryToolNetWeight(totalNetWeight, carryWorkToolBM, taskId, divingPlanId);

            specialRowData = accountingFormMgrBusiness.checkInsertNewDevice(specialRowData, true);
            cabinInRowData = accountingFormMgrBusiness.checkInsertNewDevice(cabinInRowData, false);
            carryWorkToolBM.clearAllFilter();
            carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                            " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'"
//                    + " AND C_TABLE_FLAG_" + carryWorkToolBM.getId() + "='" + tableFlag + "'"
            );
            List<Map<String, Object>> carryToolList = orientSqlEngine.getBmService().createModelQuery(carryWorkToolBM).list();
            List<Map> carryList = UtilFactory.newArrayList();
            accountingFormMgrBusiness.updateCarryTool(specialRowData, carryWorkToolBM, taskId, true, carryToolList, divingPlanId, carryList, "");
            carryList = UtilFactory.newArrayList();
            accountingFormMgrBusiness.updateCarryTool(cabinInRowData, carryWorkToolBM, taskId, false, carryToolList, divingPlanId, carryList, "");
            orientSqlEngine.getBmService().updateModelData(divingPlanTableBM, divingPlanMap, divingPlanId);
            accountingFormMgrBusiness.updateTotalNetWeight(totalNetWeight, carryWorkToolBM, taskId, carryToolList, divingPlanId);

        } else {
            String divingPlanId = Obj2String(divingPlanList.get(0).get("ID"));
            Map divingPlanMap = divingPlanMap(divingPlanTableBM, divingPlanTableBean, divingPlanList.size(), taskId);
            if (submitType) {
                divingPlanMap.put("C_SAVE_OR_SUBMIT_" + divingPlanTableBM.getId(), "submit");
                if (!"已结束".equals(taskState)) {
                    Map informLogMap = UtilFactory.newHashMap();
                    informLogMap.put("C_STATE_" + informLogTableBM.getId(), "发布");
                    informLogMap.put("C_TYPE_" + informLogTableBM.getId(), "plan");
                    informLogMap.put("C_TABLE_NAME_" + informLogTableBM.getId(), "下潜计划表");
                    accountingFormMgrBusiness.informMap(informLogTableBM, taskId, taskName, curUser, informLogMap);
                    informLogMap.put("C_TABLE_ID_" + informLogTableBM.getId(), divingPlanId);
                    orientSqlEngine.getBmService().insertModelData(informLogTableBM, informLogMap);
                }
            } else {
                divingPlanMap.put("C_SAVE_OR_SUBMIT_" + divingPlanTableBM.getId(), "save");
                //在点击发布按钮后，任何人只要点击保存按钮就修改标红为空
                divingPlanMap.put("C_RECORD_RED_" + divingPlanTableBM.getId(), "");
            }
            divingPlanMap.put("C_SERIAL_NUMBER_" + divingPlanTableBM.getId(), CommonTools.Obj2String(divingPlanList.get(0).get("C_SERIAL_NUMBER_" + divingPlanTableBM.getId())));
            orientSqlEngine.getBmService().updateModelData(divingPlanTableBM, divingPlanMap, divingPlanId);
            specialRowData = accountingFormMgrBusiness.checkInsertNewDevice(specialRowData, true);
            cabinInRowData = accountingFormMgrBusiness.checkInsertNewDevice(cabinInRowData, false);
            carryWorkToolBM.clearAllFilter();
            carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'");
            List<Map<String, Object>> carryToolList = orientSqlEngine.getBmService().createModelQuery(carryWorkToolBM).list();
            accountingFormMgrBusiness.updateCarryTool(specialRowData, carryWorkToolBM, taskId, true, carryToolList, divingPlanId, null, "");
            accountingFormMgrBusiness.updateCarryTool(cabinInRowData, carryWorkToolBM, taskId, false, carryToolList, divingPlanId, null, "");
            accountingFormMgrBusiness.updateTotalNetWeight(totalNetWeight, carryWorkToolBM, taskId, carryToolList, divingPlanId);
        }
    }

    private Map divingPlanMap(IBusinessModel divingPlanTableBM, DivingPlanTableBean divingPlanTableBean, int divingPlanListSize, String taskId) {
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel depthBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTH_DESITY, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel depthDesityType=businessModelService.getBusinessModelBySName(PropertyConstant.DEPTHDESITY_TYPE,schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> divingTaskMap = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).findById(taskId);
        Map divingPlanMap = UtilFactory.newHashMap();
        divingPlanMap.put("C_NUMBER_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getNumberContent()));
        divingPlanMap.put("C_POSITION_TIME_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getPositionTime()));
        divingPlanMap.put("C_PLAN_THROW_TIME_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getPalnThrowTime()));
        divingPlanMap.put("C_FLOAT_TO_WTIME_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getPlanFloatToWTime()));
        divingPlanMap.put("C_DIVING_TYPE_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getDivingType()));
        divingPlanMap.put("C_SEA_AREA_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getSeaArea()));
        divingPlanMap.put("C_JINGDU_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getJingdu()));
        divingPlanMap.put("C_WEIDU_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getWeidu()));
        divingPlanMap.put("C_DIVING_DATE_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getDivingDate()));
        divingPlanMap.put("C_DENSITY_" + divingPlanTableBM.getId(), CommonTools.Obj2String(divingPlanTableBean.getDensity()));
        divingPlanMap.put("C_PLAN_DIVING_DEPTH_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getPlanDivingDepth()));
        divingPlanMap.put("C_PLAN_FLOAT_DEPTH_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getPlanFloatDepth()));
        String zuoxianUserId = Obj2String(divingPlanTableBean.getSelectZuoxian());
        zuoxianUserId = ("-1".equals(zuoxianUserId) ? "" : zuoxianUserId);
        divingPlanMap.put("C_ZUOXIAN_" + divingPlanTableBM.getId(), zuoxianUserId);
        String mainDriverUserId = Obj2String(divingPlanTableBean.getSelectMainDriver());
        mainDriverUserId = ("-1".equals(mainDriverUserId) ? "" : mainDriverUserId);
        divingPlanMap.put("C_MAIN_DRIVER_" + divingPlanTableBM.getId(), mainDriverUserId);
        String youxianUserId = Obj2String(divingPlanTableBean.getSelectYouxian());
        youxianUserId = ("-1".equals(youxianUserId) ? "" : youxianUserId);
        divingPlanMap.put("C_YOUXIAN_" + divingPlanTableBM.getId(), youxianUserId);
        divingPlanMap.put("C_MAIN_TASK_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getMainTask()));
        divingPlanMap.put("C_WORK_PROGRESS_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getWorkProgress()));
        divingPlanMap.put("C_ATTENTION_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getAttention()));
        divingPlanMap.put("C_WORK_POINT_" + divingPlanTableBM.getId(), Obj2String(divingPlanTableBean.getHomeWorkPoint()));
        if (divingPlanListSize == 0) {
            divingPlanMap.put("C_TABLE_STATE_" + divingPlanTableBM.getId(), "当前");
            divingPlanMap.put("C_SERIAL_NUMBER_" + divingPlanTableBM.getId(), "1");
            divingPlanMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            //总表
//            divingPlanMap.put("C_TYPE_"+divingPlanTableBM.getId(),"all");
        }
        String seaAreaId=CommonTools.Obj2String(divingPlanTableBean.getSeaArea());
        if (!"-1".equals(seaAreaId)&&!"".equals(seaAreaId)){
            Map seaAreaMap=orientSqlEngine.getBmService().createModelQuery(depthDesityType).findById(seaAreaId);
            if (seaAreaMap != null){
                divingTaskMap.put("C_SEA_AREA_" + divingTaskBM.getId(), Obj2String(seaAreaMap.get("C_NAME_"+depthDesityType.getId())));
            }
        }
//        divingTaskMap.put("C_SEA_AREA_" + divingTaskBM.getId(), Obj2String(divingPlanTableBean.getSeaArea()));
        divingTaskMap.put("C_JINGDU_" + divingTaskBM.getId(), Obj2String(divingPlanTableBean.getJingdu()));
        divingTaskMap.put("C_WEIDU_" + divingTaskBM.getId(), Obj2String(divingPlanTableBean.getWeidu()));
        divingTaskMap.put("C_WATER_HOURS_" + divingTaskBM.getId(), Obj2String(divingPlanTableBean.getPlanWaterTime()));
        divingTaskMap.put("C_POSITION_TIME_" + divingTaskBM.getId(), Obj2String(divingPlanTableBean.getPositionTime()));
        divingTaskMap.put("C_PLAN_THROW_TIME_" + divingTaskBM.getId(), Obj2String(divingPlanTableBean.getPalnThrowTime()));
        divingTaskMap.put("C_FLOAT_TO_WTIME_" + divingTaskBM.getId(), Obj2String(divingPlanTableBean.getPlanFloatToWTime()));
        String depthId = CommonTools.Obj2String(divingPlanTableBean.getPlanDivingDepth());
        if (!"-1".equals(depthId) && !"".equals(depthId)) {
            Map depthMap = orientSqlEngine.getBmService().createModelQuery(depthBM).findById(depthId);
            if (depthMap != null) {
                String depth = CommonTools.Obj2String(depthMap.get("C_DEPTH_" + depthBM.getId()));
                divingTaskMap.put("C_PLAN_DIVING_DEPTH_" + divingTaskBM.getId(), depth);
            }
        } else {
            divingTaskMap.put("C_PLAN_DIVING_DEPTH_" + divingTaskBM.getId(), "");
        }
        orientSqlEngine.getBmService().updateModelData(divingTaskBM, divingTaskMap, taskId);
        return divingPlanMap;
    }

    /**
     * @param taskId
     * @param hangduanId
     * @param anyUpdate
     * @param isPhone  是否是手机显示界面
     * @return
     * @throws Exception
     */
    @Autowired
    DivingPlanTableDao divingPlanTableDao;

    @RequestMapping("getDivingPlanTableData")
    public ModelAndView getDivingPlanTableData(String taskId, String hangduanId, boolean anyUpdate, boolean isCanEdit, boolean isPhone, boolean isScientist) throws Exception {
        ModelAndView retVal = new ModelAndView();
        String viewName = "/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/" + (!isPhone ? "divingPlanTable.jsp" : "divingPlanTablePhone.jsp");
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel depthDensityTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTHDESITY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        hangduanId = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).findById(taskId).get("T_HANGDUAN_" + schemaId + "_ID");
        if (isPhone) {
            //手机直接查的话，需要再查一个航段ID
//            String hangDuanIdByDivingTaskId = divingPlanTableDao.getHangDuanIdByDivingTaskId(taskId);
            retVal.addObject("hangduanId", hangduanId);
        }
        if (isScientist)
            retVal.addObject("isScientist", true);
        //所有人的权限，可以编辑下潜计划表；isCanEdit从预览界面转为编辑保存发布界面
        if (anyUpdate) {
            viewName = "/app/javascript/orientjs/extjs/AnyUpdateDivingPlanMgr/anyDivingPlanTable.jsp";
        }
        IBusinessModel divingPlanTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel carryWorkToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
//        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel outTemplateBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);

//        divingTaskBM.setReserve_filter("AND ID='" + taskId + "'");
//        List<Map> divingTaskList = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).list();
//        Map divingTaskMap = divingTaskList.get(0);
//        String seaArea = CommonTools.Obj2String(divingTaskMap.get("C_SEA_AREA_" + divingTaskBM.getId()));
//        String jingdu = CommonTools.Obj2String(divingTaskMap.get("C_JINGDU_" + divingTaskBM.getId()));
//        String weidu = CommonTools.Obj2String(divingTaskMap.get("C_WEIDU_" + divingTaskBM.getId()));
//        String planDivingDepth = CommonTools.Obj2String(divingTaskMap.get("C_PLAN_DIVING_DEPTH_" + divingTaskBM.getId()));
//        retVal.addObject("seaArea", seaArea);
//        retVal.addObject("longtitude", jingdu);
//        retVal.addObject("latitude", weidu);
//        retVal.addObject("planDivingDepth", planDivingDepth);

        divingPlanTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND C_TABLE_STATE_" + divingPlanTableBM.getId() + "='" + "当前" + "'");
        List<Map> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanTableBM).list();
        if (divingPlanList.size() > 0) {
            Map divingPlanMap = divingPlanList.get(0);
            String divingPlanId = divingPlanMap.get("ID").toString();
            String numberContent = Obj2String(divingPlanMap.get("C_NUMBER_" + divingPlanTableBM.getId()));
            String positionTime = Obj2String(divingPlanMap.get("C_POSITION_TIME_" + divingPlanTableBM.getId()));
            String palnThrowTime = Obj2String(divingPlanMap.get("C_PLAN_THROW_TIME_" + divingPlanTableBM.getId()));
            String planFloatToWTime = Obj2String(divingPlanMap.get("C_FLOAT_TO_WTIME_" + divingPlanTableBM.getId()));

            if (planFloatToWTime != null && planFloatToWTime != "") {
                if (positionTime != null && positionTime != "") {
//                    System.out.println( Integer.parseInt(positionTime.substring(0, 2)));
//                    System.out.println( Integer.parseInt(positionTime.substring(2, 4)));
                    int positionTimeShiftMinutes = Integer.parseInt(positionTime.substring(0, 2)) * 60 + Integer.parseInt(positionTime.substring(2, 4));
                    int floatToWTimeShiftMinutes = Integer.parseInt(planFloatToWTime.substring(0, 2)) * 60 + Integer.parseInt(planFloatToWTime.substring(2, 4));
                    int differentMinuteCount = 0;
                    //浮至水面时间小于各就各位时间，说明浮至水面时间已经是第二天
                    if (floatToWTimeShiftMinutes <= positionTimeShiftMinutes) {
                        differentMinuteCount = 24 * 60 - positionTimeShiftMinutes + floatToWTimeShiftMinutes;
                    } else if (floatToWTimeShiftMinutes > positionTimeShiftMinutes) {
                        //浮至水面时间是当天
                        differentMinuteCount = floatToWTimeShiftMinutes - positionTimeShiftMinutes;
                    }
                    differentMinuteCount=differentMinuteCount-10;
                    //计算水下呆的小时数
                    String hours = String.valueOf(differentMinuteCount / 60);
                    if (Integer.parseInt(hours) < 10) {
                        hours = "0" + String.valueOf(hours);
                    }
                    //小时后剩下的分钟数
                    int remainMinutes = differentMinuteCount % 60;
                    String allMinutes = String.valueOf(remainMinutes);
                    if (remainMinutes < 10) {
                        allMinutes = "0" + allMinutes;
                    }
                    if (remainMinutes != 0) {
                        //合并
                        String allHoursMinutes = "" + hours + allMinutes;
                        retVal.addObject("planWaterTime", allHoursMinutes);
                    } else {
                        //合并
                        String allHoursMinutes = "" + hours;
                        retVal.addObject("planWaterTime", allHoursMinutes);
                    }
                }
            }
            List<Map> homeMapPathList = UtilFactory.newArrayList();
            String divingType = Obj2String(divingPlanMap.get("C_DIVING_TYPE_" + divingPlanTableBM.getId()));
            String divingDate = Obj2String(divingPlanMap.get("C_DIVING_DATE_" + divingPlanTableBM.getId()));
            String density = CommonTools.Obj2String(divingPlanMap.get("C_DENSITY_" + divingPlanTableBM.getId()));
            String selectZuoxian = Obj2String(divingPlanMap.get("C_ZUOXIAN_" + divingPlanTableBM.getId()));
            String selectMainDriver = Obj2String(divingPlanMap.get("C_MAIN_DRIVER_" + divingPlanTableBM.getId()));
            String selectYouxian = Obj2String(divingPlanMap.get("C_YOUXIAN_" + divingPlanTableBM.getId()));
            String mainTask = Obj2String(divingPlanMap.get("C_MAIN_TASK_" + divingPlanTableBM.getId()));
            String workProgress = Obj2String(divingPlanMap.get("C_WORK_PROGRESS_" + divingPlanTableBM.getId()));
            String attention = Obj2String(divingPlanMap.get("C_ATTENTION_" + divingPlanTableBM.getId()));
            String seaArea = Obj2String(divingPlanMap.get("C_SEA_AREA_" + divingPlanTableBM.getId()));
            String jingdu = Obj2String(divingPlanMap.get("C_JINGDU_" + divingPlanTableBM.getId()));
            String weidu = Obj2String(divingPlanMap.get("C_WEIDU_" + divingPlanTableBM.getId()));
            String planDivingDepth = Obj2String(divingPlanMap.get("C_PLAN_DIVING_DEPTH_" + divingPlanTableBM.getId()));
            String planFloatDepth = Obj2String(divingPlanMap.get("C_PLAN_FLOAT_DEPTH_" + divingPlanTableBM.getId()));
            String homeMapFiles = Obj2String(divingPlanMap.get("C_HOME_MAP_" + divingPlanTableBM.getId()));
            String isSubmitTable = Obj2String(divingPlanMap.get("C_SAVE_OR_SUBMIT_" + divingPlanTableBM.getId()));
            if ("submit".equals(isSubmitTable) && !anyUpdate && !isPhone) {
                if (!"".equals(seaArea)) {
                    Map seaAreaMap = orientSqlEngine.getBmService().createModelQuery(depthDensityTypeBM).findById(seaArea);
                    seaArea= CommonTools.Obj2String(seaAreaMap.get("C_NAME_" + depthDensityTypeBM.getId()));
                }
                viewName = "/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/view/viewDivingPlanTable.jsp";
            }
            String homeWorkPoint = Obj2String(divingPlanMap.get("C_WORK_POINT_" + divingPlanTableBM.getId()));
            String fillPerson = Obj2String(divingPlanMap.get("C_FILL_PERSON_" + divingPlanTableBM.getId()));
            String recordRed = CommonTools.Obj2String(divingPlanMap.get("C_RECORD_RED_" + divingPlanTableBM.getId()));
            String basketPicFileId = CommonTools.Obj2String(divingPlanMap.get("C_PREVIEW_ID_" + divingPlanTableBM.getId()));
            org.json.JSONArray jsonArray = null;
            if (StringUtil.isNotEmpty(homeMapFiles)) {
                jsonArray = new org.json.JSONArray(homeMapFiles);
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String fileId = jsonObject.getString("id");
                        String finalname = jsonObject.getString("finalname");
                        String fileType = jsonObject.getString("fileType");
                        StringBuilder homeMapSql = new StringBuilder();
                        homeMapSql.append("select * from CWM_FILE").append(" where TABLEID='").append(divingPlanTableBM.getId()).append("' AND FILEID='").append(fileId).append("' AND FINALNAME='").append(finalname).append("' AND FILETYPE='").append(fileType).append("'");
                        List<Map<String, Object>> homeMapList = jdbcTemplate.queryForList(homeMapSql.toString());
                        if (homeMapList.size() > 0) {
                            for (Map homeMap : homeMapList) {
                                Map fileMap = UtilFactory.newHashMap();
                                File file = new File(fileServerConfig.getFtpHome() + Obj2String(homeMap.get("FILELOCATION")));
                                if (file.exists()) {
                                    String filePath = Obj2String(homeMap.get("FILELOCATION"));
//                                    String encodeImage64 = Base64Utils.encodeFile(filePath);
//                                    homeMapPathList.add(encodeImage64);
//                                    String fileRealName = StringUtils.substringBeforeLast(Obj2String(homeMap.get("FINALNAME")), ".") + "." + StringUtils.substringAfterLast(Obj2String(homeMap.get("FILELOCATION")), ".");
                                    fileMap.put("fileName", Obj2String(homeMap.get("FILENAME")));
                                    fileMap.put("filePath", filePath);
                                    fileMap.put("fileId", Obj2String(homeMap.get("FILEID")));
                                    //设置缩略图名称
//                                    String sFilePath = StringUtils.substringBeforeLast(Obj2String(homeMap.get("FINALNAME")), ".") + "_s." + StringUtils.substringAfterLast(Obj2String(homeMap.get("FILELOCATION")), ".");
//                                    fileMap.put("sltFilePath", sFilePath);

                                    homeMapPathList.add(fileMap);
                                }
                            }
                            //list集合转为json数组
                            net.sf.json.JSONArray homeMapJson = net.sf.json.JSONArray.fromObject(homeMapPathList);
                            //json数组转为字符串
                            retVal.addObject("homeMapPathList", homeMapJson.toString());
                        }
                    }
                }
            }
            if (!"".equals(basketPicFileId)) {
                Map basketPicMap = UtilFactory.newHashMap();
                StringBuilder basketPicSql = new StringBuilder();
                basketPicSql.append("select * from CWM_FILE").append(" where TABLEID='").append(divingPlanTableBM.getId()).append("' AND FILEID='").append(basketPicFileId).append("'");
                List<Map<String, Object>> basketPicList = jdbcTemplate.queryForList(basketPicSql.toString());
                if (basketPicList != null && basketPicList.size() > 0) {
                    Map basketMap = basketPicList.get(0);
                    File file = new File(fileServerConfig.getFtpHome() + Obj2String(basketMap.get("FILELOCATION")));
                    if (file.exists()) {
                        String fileRealName = StringUtils.substringBeforeLast(Obj2String(basketMap.get("FINALNAME")), ".") + "." + StringUtils.substringAfterLast(Obj2String(basketMap.get("FILELOCATION")), ".");
                        basketPicMap.put("fileName", Obj2String(basketMap.get("FILENAME")));
                        basketPicMap.put("filePath", fileRealName);
                        basketPicMap.put("fileId", Obj2String(basketMap.get("FILEID")));
                        //设置缩略图名称
                        String sFilePath = StringUtils.substringBeforeLast(Obj2String(basketMap.get("FINALNAME")), ".") + "_s." + StringUtils.substringAfterLast(Obj2String(basketMap.get("FILELOCATION")), ".");
                        basketPicMap.put("sltFilePath", sFilePath);
                        //map对象转为json对象
                        retVal.addObject("basketPicMap", com.alibaba.fastjson.JSONObject.toJSON(basketPicMap));
                    }
                }
            }
            retVal.addObject("numberContent", numberContent);
            retVal.addObject("positionTime", positionTime);
            retVal.addObject("palnThrowTime", palnThrowTime);
            retVal.addObject("planFloatToWTime", planFloatToWTime);
            retVal.addObject("divingType", divingType);
            retVal.addObject("seaArea", seaArea);
            retVal.addObject("longtitude", jingdu);
            retVal.addObject("latitude", weidu);
            retVal.addObject("planDivingDepth", planDivingDepth);
            retVal.addObject("planFloatDepth", planFloatDepth);
            retVal.addObject("divingDate", divingDate);
            retVal.addObject("density", density);
            retVal.addObject("selectZuoxian", selectZuoxian);
            retVal.addObject("selectMainDriver", selectMainDriver);
            retVal.addObject("selectYouxian", selectYouxian);
            retVal.addObject("mainTask", mainTask);
            retVal.addObject("workProgress", workProgress);
            retVal.addObject("attention", attention);
            retVal.addObject("isSubmitTable", isSubmitTable);
            retVal.addObject("homeWorkPoint", homeWorkPoint);
            retVal.addObject("recordRed", recordRed);
            retVal.addObject("hangduanId", hangduanId);
            retVal.addObject("isCanEdit", !isCanEdit);
            retVal.addObject("fillPerson", fillPerson);
            retVal.addObject("basketPicFileId", basketPicFileId);
            List<Map> showCarryToolList = UtilFactory.newArrayList();
            List<Map> cabinInCarryToolList = UtilFactory.newArrayList();

            carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" + " AND C_TOTAL_STATE_" + carryWorkToolBM.getId() + " is null" +
                    " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'");
            List<Map> carryToolList = orientSqlEngine.getBmService().createModelQuery(carryWorkToolBM).orderAsc("TO_NUMBER(C_ROW_NUMBER_" + carryWorkToolBM.getId() + ")").list();

            if (carryToolList.size() > 0) {
                for (Map carryMap : carryToolList) {
                    String deviceId = Obj2String(carryMap.get("C_DEVICE_ID_" + carryWorkToolBM.getId()));
                    String rowNumber = Obj2String(carryMap.get("C_ROW_NUMBER_" + carryWorkToolBM.getId()));
                    String carryCount = Obj2String(carryMap.get("C_CARRY_COUNT_" + carryWorkToolBM.getId()));
                    String isCabinOutOrIn = Obj2String(carryMap.get("C_CABIN_OUTORIN_" + carryWorkToolBM.getId()));
                    String airWeight = CommonTools.Obj2String(carryMap.get("C_AIR_WEIGHT_" + carryWorkToolBM.getId()));
                    String deWaterVolume = CommonTools.Obj2String(carryMap.get("C_PW_VOLUMN_" + carryWorkToolBM.getId()));
                    String freshWaterWeight = CommonTools.Obj2String(carryMap.get("C_FRESH_WATER_" + carryWorkToolBM.getId()));
                    String connectWay = CommonTools.Obj2String(carryMap.get("C_CONNECT_WAY_" + carryWorkToolBM.getId()));
                    Map carryToolMap = UtilFactory.newHashMap();
                    carryToolMap.put("deviceId", deviceId);
                    carryToolMap.put("carryCount", carryCount);
                    carryToolMap.put("rowNumber", rowNumber);
                    carryToolMap.put("isCabinOutOrIn", isCabinOutOrIn);
                    carryToolMap.put("airWeight", airWeight);
                    carryToolMap.put("deWaterVolume", deWaterVolume);
                    carryToolMap.put("freshWaterWeight", freshWaterWeight);
                    carryToolMap.put("connectWay", connectWay);

//                    if ("in".equals(isCabinOutOrIn)){
//                        cabinInCarryToolList.add(carryToolMap);
//                    }else{
//                        showCarryToolList.add(carryToolMap);
//                    }
                    showCarryToolList.add(carryToolMap);
                }
                //list集合转为json数组
                net.sf.json.JSONArray showCarryToolMapJson = net.sf.json.JSONArray.fromObject(showCarryToolList);
                retVal.addObject("showCarryToolList", showCarryToolMapJson.toString());
            }
            outTemplateBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" + " AND C_TABLE_STATE_" + outTemplateBM.getId() + "='" + "当前" + "'");
            List<Map> outTemplateList = orientSqlEngine.getBmService().createModelQuery(outTemplateBM).list();
            if (outTemplateList.size() > 0) {
                Map outTemplateMap = outTemplateList.get(0);
                retVal.addObject("divingLoad", Obj2String(outTemplateMap.get("C_DIVING_LOAD_" + outTemplateBM.getId())));
                retVal.addObject("comeupLoad", Obj2String(outTemplateMap.get("C_COME_UP_LOAD_" + outTemplateBM.getId())));
                retVal.addObject("adjustLoad", Obj2String(outTemplateMap.get("C_ADJUST_LOAD_W_" + outTemplateBM.getId())));
                retVal.addObject("peizhongQk", Obj2String(outTemplateMap.get("C_BALANCE_LEAD_" + outTemplateBM.getId())));
//                retVal.addObject("basketWeight", Obj2String(outTemplateMap.get("C_BASKET_WEIGHT_" + outTemplateBM.getId())));
                retVal.addObject("mercury", Obj2String(outTemplateMap.get("C_QUICKSLIVER_LEVEL_" + outTemplateBM.getId())));
                retVal.addObject("planFloatTime", Obj2String(outTemplateMap.get("C_PLAN_FLOAT_HOURS_" + outTemplateBM.getId())));
            }
        }
        retVal.setViewName(viewName);
        return retVal;
    }

    @Action(ownermodel = "任务管理", detail = "提交潜水器重量表")
    @RequestMapping("submitDivingDeviceTable")
    @ResponseBody
    public void submitDivingDeviceTable(DivingDeviceTableBean divingDeviceTableBean) {
        IBusinessModel divingDeviceTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);
        String taskId = divingDeviceTableBean.getTaskId();
        divingDeviceTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND C_TYPE_" + divingDeviceTableBM.getId() + "='" + "remain" + "'");
        List<Map> divingDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceTableBM).list();
        String divingDevice = Obj2String(divingDeviceTableBean.getDivingDevice());
        String divingAirWeight = Obj2String(divingDeviceTableBean.getDivingAirWeight());
        String divingPWaterVolume = Obj2String(divingDeviceTableBean.getDivingPWaterVolume());
        String deTableRowData = divingDeviceTableBean.getDeTableRowData();
        String inTableRowData = divingDeviceTableBean.getInTableRowData();
        Map divingDeviceMap = UtilFactory.newHashMap();
        divingDeviceMap.put("C_DEVICE_" + divingDeviceTableBM.getId(), divingDevice);
        divingDeviceMap.put("C_AIR_WEIGHT_" + divingDeviceTableBM.getId(), divingAirWeight);
        divingDeviceMap.put("C_PW_VOLUME_" + divingDeviceTableBM.getId(), divingPWaterVolume);
        if (divingDeviceList.size() > 0) {
            String keyId = Obj2String(divingDeviceList.get(0).get("ID"));
            orientSqlEngine.getBmService().updateModelData(divingDeviceTableBM, divingDeviceMap, keyId);
            deOrIninsertOracle(divingDeviceTableBM, deTableRowData, true, taskId, divingDeviceList.size());
            deOrIninsertOracle(divingDeviceTableBM, inTableRowData, false, taskId, divingDeviceList.size());
        } else {
//            Map divingDeviceMap=UtilFactory.newHashMap();
//            divingDeviceMap.put("C_DEVICE_"+divingDeviceTableBM.getId(),divingDevice);
//            divingDeviceMap.put("C_AIR_WEIGHT_"+divingDeviceTableBM.getId(),divingAirWeight);
//            divingDeviceMap.put("C_PW_VOLUME_"+divingDeviceTableBM.getId(),divingPWaterVolume);
            divingDeviceMap.put("C_TYPE_" + divingDeviceTableBM.getId(), "remain");
            divingDeviceMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            orientSqlEngine.getBmService().insertModelData(divingDeviceTableBM, divingDeviceMap);
            deOrIninsertOracle(divingDeviceTableBM, deTableRowData, true, taskId, divingDeviceList.size());
            deOrIninsertOracle(divingDeviceTableBM, inTableRowData, false, taskId, divingDeviceList.size());
        }
    }

    public void deOrIninsertOracle(IBusinessModel divingDeviceTableBM, String tableRowData, boolean isDeOrIn, String taskId, int divingDeviceSize) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(tableRowData);
            if (jsonArray.length() > 0) {
                StringBuilder divingDeviceBuilder = new StringBuilder();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String deviceName = jsonObject.getString("deviceName");
                    divingDeviceBuilder.append(deviceName).append(",");
                }
                String deleteDevice = divingDeviceBuilder.toString();
                if (deleteDevice != null && !"".equals(deleteDevice)) {
                    deleteDevice = deleteDevice.substring(0, deleteDevice.length() - 1);
                    divingDeviceTableBM.clearAllFilter();
                    if (isDeOrIn) {
                        divingDeviceTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                                " AND C_TYPE_" + divingDeviceTableBM.getId() + "='" + "decrease" + "'" +
                                " AND C_DEVICE_" + divingDeviceTableBM.getId() + " not in('" + deleteDevice + "')");
                    } else {
                        divingDeviceTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                                " AND C_TYPE_" + divingDeviceTableBM.getId() + "='" + "increase" + "'" +
                                " AND C_DEVICE_" + divingDeviceTableBM.getId() + " not in('" + deleteDevice + "')");
                    }
                    List<Map> deleteDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceTableBM).list();
                    if (deleteDeviceList.size() > 0) {
                        for (Map deleteMap : deleteDeviceList) {
                            String deleteKeyId = Obj2String(deleteMap.get("ID"));
                            orientSqlEngine.getBmService().deleteCascade(divingDeviceTableBM, deleteKeyId);
                        }
                    }
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String deviceName = jsonObject.getString("deviceName");
                    String airWeight = jsonObject.getString("airWeight");
                    String pwVolume = jsonObject.getString("pwVolume");
                    String rowNumber = jsonObject.getString("rowNumber");
                    Map tableRowMap = UtilFactory.newHashMap();
                    tableRowMap.put("C_ROWINDEX_" + divingDeviceTableBM.getId(), rowNumber);
                    if (divingDeviceSize > 0) {
                        divingDeviceTableBM.clearAllFilter();
                        if (isDeOrIn) {
                            divingDeviceTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                                    " AND C_TYPE_" + divingDeviceTableBM.getId() + "='" + "decrease" + "'" +
                                    " AND C_DEVICE_" + divingDeviceTableBM.getId() + "='" + deviceName + "'");
                        } else {
                            divingDeviceTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                                    " AND C_TYPE_" + divingDeviceTableBM.getId() + "='" + "increase" + "'" +
                                    " AND C_DEVICE_" + divingDeviceTableBM.getId() + "='" + deviceName + "'");
                        }
                        List<Map> rowDataList = orientSqlEngine.getBmService().createModelQuery(divingDeviceTableBM).list();
                        if (rowDataList.size() > 0) {
                            String rowId = Obj2String(rowDataList.get(0).get("ID"));
                            orientSqlEngine.getBmService().updateModelData(divingDeviceTableBM, tableRowMap, rowId);
                            continue;
                        }
                    }
                    tableRowMap.put("C_DEVICE_" + divingDeviceTableBM.getId(), deviceName);
                    tableRowMap.put("C_AIR_WEIGHT_" + divingDeviceTableBM.getId(), airWeight);
                    tableRowMap.put("C_PW_VOLUME_" + divingDeviceTableBM.getId(), pwVolume);
                    tableRowMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
                    if (isDeOrIn) {
                        tableRowMap.put("C_TYPE_" + divingDeviceTableBM.getId(), "decrease");
                    } else {
                        tableRowMap.put("C_TYPE_" + divingDeviceTableBM.getId(), "increase");
                    }
                    orientSqlEngine.getBmService().insertModelData(divingDeviceTableBM, tableRowMap);
                }
            } else {
                divingDeviceTableBM.clearAllFilter();
                if (isDeOrIn) {
                    divingDeviceTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                            " AND C_TYPE_" + divingDeviceTableBM.getId() + "='" + "decrease" + "'");
                } else {
                    divingDeviceTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                            " AND C_TYPE_" + divingDeviceTableBM.getId() + "='" + "increase" + "'");
                }
                List<Map> deleteDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceTableBM).list();
                if (deleteDeviceList.size() > 0) {
                    for (Map deleteMap : deleteDeviceList) {
                        String deleteKeyId = Obj2String(deleteMap.get("ID"));
                        orientSqlEngine.getBmService().deleteCascade(divingDeviceTableBM, deleteKeyId);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("getDivingDeviceData")
    @ResponseBody
    public ModelAndView getDivingDeviceData(String taskId, String hangduanId) {
        ModelAndView retVal = new ModelAndView();
        String viewName = "/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/divingDeviceWeight.jsp";
        IBusinessModel taskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);

        taskBM.setReserve_filter("AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'");
        List<Map> taskList = orientSqlEngine.getBmService().createModelQuery(taskBM).orderAsc("TO_NUMBER(ID)").list();
        if (taskList.size() > 0) {
            List<Map> showTaskList = UtilFactory.newArrayList();
            for (Map taskMap : taskList) {
                Map showTaskMap = UtilFactory.newHashMap();
                String taskName = Obj2String(taskMap.get("C_TASK_NAME_" + taskBM.getId()));
                String showTaskId = Obj2String(taskMap.get("ID"));
                showTaskMap.put("id", showTaskId);
                showTaskMap.put("name", taskName);
                showTaskList.add(showTaskMap);
            }
            retVal.addObject("showTaskList", showTaskList);
            divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_TYPE_" + divingDeviceBM.getId() + "='" + "remain" + "'");
            List<Map> divingDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceBM).list();
            if (divingDeviceList.size() > 0) {
                String chooseDivingTaskId = Obj2String(divingDeviceList.get(0).get("C_DEVICE_" + divingDeviceBM.getId()));
                String divingAirWeight = Obj2String(divingDeviceList.get(0).get("C_AIR_WEIGHT_" + divingDeviceBM.getId()));
                String divingPWaterVolume = Obj2String(divingDeviceList.get(0).get("C_PW_VOLUME_" + divingDeviceBM.getId()));
                retVal.addObject("chooseDivingTaskId", chooseDivingTaskId);
                retVal.addObject("divingAirWeight", divingAirWeight);
                retVal.addObject("divingPWaterVolume", divingPWaterVolume);

                divingDeviceBM.clearAllFilter();
                divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                        " AND C_TYPE_" + divingDeviceBM.getId() + "='" + "decrease" + "'");
                List<Map> decreaseDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceBM).orderAsc("TO_NUMBER(C_ROWINDEX_" + divingDeviceBM.getId() + ")").list();
                List<Map> showDeDeviceRowList = UtilFactory.newArrayList();
                Double totalWeight = Double.parseDouble(divingAirWeight);
                Double totalPwVolume = Double.parseDouble(divingPWaterVolume);
                if (decreaseDeviceList.size() > 0) {
                    for (Map rowMap : decreaseDeviceList) {
                        Map showRowData = UtilFactory.newHashMap();
                        String deviceName = Obj2String(rowMap.get("C_DEVICE_" + divingDeviceBM.getId()));
                        String airWeight = Obj2String(rowMap.get("C_AIR_WEIGHT_" + divingDeviceBM.getId()));
                        String pwVolume = Obj2String(rowMap.get("C_PW_VOLUME_" + divingDeviceBM.getId()));
                        if (!"".equals(airWeight)) {
                            totalWeight -= Double.parseDouble(airWeight);
                        }
                        if (!"".equals(pwVolume)) {
                            totalPwVolume -= Double.parseDouble(pwVolume);
                        }
                        String rowNumber = Obj2String(rowMap.get("C_ROWINDEX_" + divingDeviceBM.getId()));
                        showRowData.put("deviceName", deviceName);
                        showRowData.put("airWeight", airWeight);
                        showRowData.put("pwVolume", pwVolume);
                        showRowData.put("rowNumber", rowNumber);
                        showDeDeviceRowList.add(showRowData);
                    }
                    retVal.addObject("decreaseRowData", showDeDeviceRowList);
                }
                divingDeviceBM.clearAllFilter();
                divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                        " AND C_TYPE_" + divingDeviceBM.getId() + "='" + "increase" + "'");
                List<Map> increaseDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceBM).orderAsc("TO_NUMBER(C_ROWINDEX_" + divingDeviceBM.getId() + ")").list();
                List<Map> showInDeviceRowList = UtilFactory.newArrayList();
                if (increaseDeviceList.size() > 0) {
                    for (Map rowMap : increaseDeviceList) {
                        Map showRowData = UtilFactory.newHashMap();
                        String deviceName = Obj2String(rowMap.get("C_DEVICE_" + divingDeviceBM.getId()));
                        String airWeight = Obj2String(rowMap.get("C_AIR_WEIGHT_" + divingDeviceBM.getId()));
                        String pwVolume = Obj2String(rowMap.get("C_PW_VOLUME_" + divingDeviceBM.getId()));
                        totalWeight += Double.parseDouble(airWeight);
                        totalPwVolume += Double.parseDouble(pwVolume);
                        String rowNumber = Obj2String(rowMap.get("C_ROWINDEX_" + divingDeviceBM.getId()));
                        showRowData.put("deviceName", deviceName);
                        showRowData.put("airWeight", airWeight);
                        showRowData.put("pwVolume", pwVolume);
                        showRowData.put("rowNumber", rowNumber);
                        showInDeviceRowList.add(showRowData);
                    }
                    retVal.addObject("increaseRowData", showInDeviceRowList);
                }
                retVal.addObject("totalAirWeight", totalWeight);
                retVal.addObject("totalPWaterVolume", totalPwVolume);
                divingDeviceBM.clearAllFilter();
                divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                        " AND C_TYPE_" + divingDeviceBM.getId() + "='" + "total" + "'");
                List<Map> totalList = orientSqlEngine.getBmService().createModelQuery(divingDeviceBM).list();
                Map newTotalMap = UtilFactory.newHashMap();
                newTotalMap.put("C_AIR_WEIGHT_" + divingDeviceBM.getId(), totalWeight);
                newTotalMap.put("C_PW_VOLUME_" + divingDeviceBM.getId(), totalPwVolume);
                if (totalList.size() > 0) {
                    Map totalMap = totalList.get(0);
                    String totalId = Obj2String(totalMap.get("ID"));
                    orientSqlEngine.getBmService().updateModelData(divingDeviceBM, newTotalMap, totalId);
                } else {
                    newTotalMap.put("C_TYPE_" + divingDeviceBM.getId(), "total");
                    newTotalMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
                    orientSqlEngine.getBmService().insertModelData(divingDeviceBM, newTotalMap);
                }
            }
        }
        retVal.setViewName(viewName);
        return retVal;
    }

    @RequestMapping("getRefDivingDeviceData")
    @ResponseBody
    public List<Map> getRefDivingDeviceData(String taskId, String deviceId) {
        IBusinessModel divingDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);
        //相对潜次不存在
        if ("0".equals(deviceId)||taskId.equals(deviceId)) {
            divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_DEVICE_" + divingDeviceBM.getId() + "='" + deviceId + "'" + " AND C_TYPE_" + divingDeviceBM.getId() + "='remain'");
        }
        //相对潜次数据存在，查找上一次潜次的潜水器总重量
        else if (!taskId.equals(deviceId)) {
            divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + deviceId + "'" + " AND C_TYPE_" + divingDeviceBM.getId() + "='total'");
        }
        List<Map> divingDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceBM).list();
        List<Map> showRefDivingList = UtilFactory.newArrayList();
        if (divingDeviceList.size() > 0) {
            String divingAirWeight = Obj2String(divingDeviceList.get(0).get("C_AIR_WEIGHT_" + divingDeviceBM.getId()));
            String divingPWaterVolume = Obj2String(divingDeviceList.get(0).get("C_PW_VOLUME_" + divingDeviceBM.getId()));
            Map showDivingDeviceMap = UtilFactory.newHashMap();
            showDivingDeviceMap.put("divingAirWeight", divingAirWeight);
            showDivingDeviceMap.put("divingPWaterVolume", divingPWaterVolume);
            showRefDivingList.add(showDivingDeviceMap);
        }
        return showRefDivingList;
    }

    @Action(ownermodel = "任务管理", detail = "提交配重输出模板")
    @RequestMapping("submitOutTemplateTable")
    @ResponseBody
    public void submitOutTemplateTable(CurrentOutTemplateBean currentOutTemplateBean, boolean submitType) {
        accountingFormMgrBusiness.submitOutTemplateTable(currentOutTemplateBean, submitType);
    }

    @RequestMapping("getOutTemplateTable")
    @ResponseBody
    public ModelAndView getOutTemplateTable(String taskId, String taskName, String peizhongId, boolean isCanEdit, boolean isPhone, boolean isOnlyView) {
        return accountingFormMgrBusiness.getOutTemplateTable(taskId, taskName, peizhongId, isCanEdit, isPhone, isOnlyView);
    }

    /**
     * \
     * 获取上一次下潜任务下拉框数据
     *
     * @param hangduanId
     * @return
     */
    @RequestMapping("getDivingTypeData")
    @ResponseBody
    public ModelAndView getDivingTypeData(String hangduanId) {
        return accountingFormMgrBusiness.getDivingTypeData(hangduanId);
    }

    /**
     * 二级联动 获取上一次潜次关联的数据
     *
     * @param taskId
     * @return
     */
    @RequestMapping("getPreRefOutTemplateData")
    @ResponseBody
    public List<Map> getPreRefOutTemplateData(String taskId) throws Exception {
        return accountingFormMgrBusiness.getPreRefOutTemplateData(taskId);
    }

    /**
     * 获取潜次报告数据
     *
     * @param taskId
     * @return
     */
    @RequestMapping("getDivingReportData")
    @ResponseBody
    public ModelAndView getDivingReportData(String taskId, String taskName) {
        return accountingFormMgrBusiness.getDivingReportData(taskId, taskName);
    }

    /**
     * 提交下潜报告表格数据
     *
     * @param divingReportTableBean
     */
    @Action(ownermodel = "任务管理", detail = "提交潜次报告")
    @RequestMapping("submitDivingReportTable")
    @ResponseBody
    public void submitDivingReportTable(DivingReportTableBean divingReportTableBean) {
        accountingFormMgrBusiness.submitDivingReportTable(divingReportTableBean);
    }

    @RequestMapping("getDepthDesitySelectData")
    @ResponseBody
    public AjaxResponseData<List> getDepthDesityData(String deptyDesityTypeId, String peizhongId) {
        return accountingFormMgrBusiness.getDepthDesityData(deptyDesityTypeId, peizhongId);
    }

    @RequestMapping("getDensityBySeaAreaAndDepth")
    @ResponseBody
    public AjaxResponseData<String> getDensityBySeaAreaAndDepth(String deptyDesityTypeId, String divingDepth) {
        return accountingFormMgrBusiness.getDensityBySeaAreaAndDepth(deptyDesityTypeId, divingDepth);
    }

    /**
     * 获取深度密度类型数据（海区）
     *
     * @return
     */
    @RequestMapping("getDepthDesityTypeData")
    @ResponseBody
    public AjaxResponseData<List> getDepthDesityTypeData() {
        return accountingFormMgrBusiness.getDepthDesityTypeData();
    }

    @RequestMapping("submitScientistDivingPlan")
    @ResponseBody
    public void submitScientistDivingPlan(HttpServletRequest request) throws Exception {
        accountingFormMgrBusiness.submitScientistDivingPlan(request);
    }

    @Autowired
    ModelFileBusiness modelFileBusiness;
    @Autowired
    InformLogDao informLogDao;

    @RequestMapping("saveScientistPicturePreviewPic")
    @ResponseBody
    @Transactional
    public AjaxResponseData saveScientistPicturePreviewPic(HttpServletRequest request, String divingPlanId, String taskId, HttpSession session) throws Exception {
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        String userName = UserContextUtil.getUserAllName();
        InformLog informLog = new InformLog();
        informLog.setcTableName3566("作业工具布局图");
        if (userName != null)
            informLog.setcUploadPerson3566(userName);
        informLog.setcState3566("发布");
        informLog.setcType3566("preview");
        informLog.setcTaskId3566(taskId);
        //把下潜计划主键放在tableId上用
        informLog.setcTableId3566(divingPlanId);
        informLog.setcUploadTime3566(DateFormatUtil.formaDatetTime(new Date()));


        AjaxResponseData result = new AjaxResponseData<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //保存图片文件
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest mutiRequest = (MultipartHttpServletRequest) request;
            Iterator iterator = mutiRequest.getFileNames();
            while (iterator.hasNext()) {
                List<MultipartFile> fileList = mutiRequest.getFiles((String) iterator.next());
                if (fileList.size() > 0) {
                    for (MultipartFile multipartFile : fileList) {
                        if (!"".equals(multipartFile.getOriginalFilename())) {
                            CwmFile savedFile = modelFileBusiness.saveScientistPicturePreviewPicFile(multipartFile, divingPlanBM.getId(), "", "common", "", "公开");
                            String realFileStoragePath = fileServerConfig.getFtpHome() + savedFile.getFilelocation();
                            String imageSPath = fileServerConfig.getFtpHome() + File.separator + "imageSuoluetu";
                            if (!new File(imageSPath).exists()) {
                                new File(imageSPath).mkdirs();
                            }
                            imageSPath = imageSPath + File.separator + savedFile.getFinalname();
                            FileOperator.copyFile(realFileStoragePath, imageSPath);
                            //生成缩略图
                            ImageUtils.zoomImageScale(new File(imageSPath), 200);
                            //保存到数据库
                            DivingPlanTable divingPlanTable = new DivingPlanTable();
                            divingPlanTable.setId(divingPlanId);
                            divingPlanTable.setcPreviewId3487(savedFile.getFileid());
                            int i = divingPlanTableMapper.updateByPrimaryKeySelective(divingPlanTable);
                            int insert = informLogDao.insertSelective(informLog);
                            if (i > 0 && insert > 0) {
                                result.setSuccess(true);
                                return result;
                            }
                        }
                    }
                }
            }
        }

        result.setSuccess(false);
        return result;


    }


    @RequestMapping(value = "/getScientistDivingPlanData", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView getScientistDivingPlanData(HttpServletRequest request, String taskId, String divingPlanId, String attachJson) throws Exception {
        return accountingFormMgrBusiness.getScientistDivingPlanData(request, taskId, divingPlanId, attachJson);
    }

    /**
     * 获取均衡表中人员体重
     *
     * @param hangduanId
     * @return
     */
    @RequestMapping("getPersonWeight")
    @ResponseBody
    public AjaxResponseData<List<Map>> getPersonWeight(String hangduanId) {
        List<Map> getPersonsList = UtilFactory.newArrayList();
        IBusinessModel personWeightBM = businessModelService.getBusinessModelBySName(PropertyConstant.PERSON_WEIGHT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> hangduanMap = orientSqlEngine.getBmService().createModelQuery(hangduanBM).findById(hangduanId);
        personWeightBM.setReserve_filter("AND T_HANGDUAN_" + schemaId + "_ID ='" + hangduanId + "'");
        List<Map<String, Object>> personWeightList = orientSqlEngine.getBmService().createModelQuery(personWeightBM).list();
        if (hangduanMap != null) {
            String attendPersonIds = hangduanMap.get("C_ATTEND_PERSON_" + hangduanBM.getId());
            if (!"".equals(attendPersonIds)) {
                StringBuilder userSql = new StringBuilder();
                userSql.append("select id,all_name from cwm_sys_user where id in(").append(attendPersonIds).append(")").append("order by all_name asc");
                List<Map<String, Object>> userList = metaDAOFactory.getJdbcTemplate().queryForList(userSql.toString());
                if (userList.size() > 0) {
                    for (Map userMap : userList) {
                        String allName = Obj2String(userMap.get("all_name"));
                        String userId = Obj2String(userMap.get("ID"));
                        if (personWeightList.size() > 0) {
                            for (Map personMap : personWeightList) {
                                String attendPersonId = Obj2String(personMap.get("C_ATTEND_ID_" + personWeightBM.getId()));
                                String personWeight = Obj2String(personMap.get("C_WEIGHT_" + personWeightBM.getId()));
                                if (userId.equals(attendPersonId)) {
                                    Map queryMap = UtilFactory.newHashMap();
                                    queryMap.put("id", attendPersonId);
                                    queryMap.put("name", allName);
                                    queryMap.put("weight", personWeight);
                                    getPersonsList.add(queryMap);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return new AjaxResponseData<>(getPersonsList);
    }

    /**
     * 查找航段启动是1，下面的任务名称
     *
     * @return
     */
    @RequestMapping("querDivingTaskList")
    @ResponseBody
    public net.sf.json.JSONObject querDivingTaskList() {
        List<Map> taskList = accountingFormMgrBusiness.querDivingTaskList();
        net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(taskList);
        net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
        jsonObject.element("results", jsonArray);
        return jsonObject;
    }

    /**
     * 提交科学家下潜计划表
     *
     * @param taskId
     * @param divingPlanId
     * @return
     */
    @RequestMapping("agreeScientistPlanTable")
    @ResponseBody
    public AjaxResponseData agreeScientistPlanTable(String taskId, String divingPlanId) {
        return accountingFormMgrBusiness.agreeScientistPlanTable(taskId, divingPlanId);
    }

    /**
     * 快速新增科学家下潜计划
     *
     * @param taskId
     * @param modelId
     * @return
     */
    @RequestMapping("easyAddDivingPlan")
    @ResponseBody
    public AjaxResponseData easyAddDivingPlan(String taskId, String modelId) {
        AjaxResponseData retVal = new AjaxResponseData();
        String msg = accountingFormMgrBusiness.easyAddDivingPlan(taskId, modelId);
        retVal.setSuccess(true);
        retVal.setMsg(msg);
        return retVal;
    }

    /**
     * 快速复制科学家下潜计划表
     *
     * @param taskId
     * @param divingPlanId
     * @return
     */
    @RequestMapping("easyCopyDivingPlan")
    @ResponseBody
    public AjaxResponseData easyCopyDivingPlan(String taskId, String divingPlanId) {
        AjaxResponseData retVal = new AjaxResponseData();
        String msg = accountingFormMgrBusiness.easyCopyDivingPlan(taskId, divingPlanId);
        retVal.setSuccess(true);
        retVal.setMsg(msg);
        return retVal;
    }

    @RequestMapping("getScientistPlanData")
    @ResponseBody
    public ExtGridData<Map> getScientistPlanData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort, String taskName) {

        ExtGridData<Map> retVal = accountingFormMgrBusiness.getScientistPlanData(orientModelId, isView, page, limit, customerFilter, true, sort, taskName);
        return retVal;
    }

    @RequestMapping("deleteScientistPlanData")
    @ResponseBody
    public AjaxResponseData deleteScientistPlanData(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel businessModel = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        if (!"".equals(id)) {
            orientSqlEngine.getBmService().deleteCascade(businessModel, id);
            retVal.setSuccess(true);
            retVal.setMsg("删除成功！");
        } else {
            retVal.setSuccess(false);
            retVal.setMsg("至少选择一条数据！");
        }
        return retVal;
    }

    /**
     * 绘图jsp
     *
     * @return
     */
    @RequestMapping("getScientistPicturePreview")
    public ModelAndView getScientistPicturePreview() {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "/app/javascript/orientjs/extjs/ScientisPicturePreivew/scientistPicturePreview.jsp";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    /**
     * 绘图预览jsp
     *
     * @return
     */
    @Autowired
    DivingPlanTableMapper divingPlanTableMapper;

    @RequestMapping("getScientistPicturePreviewFile")
    public ModelAndView getScientistPicturePreviewFile(HttpServletRequest request, String divingPlanId) {
        DivingPlanTable divingPlanTable = divingPlanTableMapper.selectByPrimaryKey(divingPlanId);
        ModelAndView modelAndView = new ModelAndView();
        if (divingPlanTable != null) {
            request.setAttribute("fileId", divingPlanTable.getcPreviewId3487());
        }
        String viewName = "/app/javascript/orientjs/extjs/ScientisPicturePreivew/scientistPicturePreviewFile.jsp";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }


    @Autowired
    private CarryToolDao carryToolDao;

    @RequestMapping("getPlanCarryTools")
    @ResponseBody
    public AjaxResponseData getPlanCarryTools(@RequestParam String taskId,
                                              @RequestParam String divingPlanId) {
        List<CarryToolWithParams> carryToolWithParams = carryToolDao.selectByDivingTaskId(taskId, divingPlanId);
        return new AjaxResponseData(carryToolWithParams);
    }

    @Autowired
    BackUpData backUpData;

    @RequestMapping("testScheduler")
    @ResponseBody
    public void testScheduler() {
        backUpData.upToDataCenter();
    }

    @Autowired
    OrientModelFileHandle orientModelFileHandle;

    @RequestMapping("scientistUploadAttach")
    @ResponseBody
    public AjaxResponseData<FileModel> scientistUploadAttach(MultipartFile file, String modelId, String dataId, String fileCatalog, String desc, String secrecy) {
        AjaxResponseData<FileModel> responseData = new AjaxResponseData();
        FileModel fileModel = new FileModel();
        CwmFile savedFile = modelFileBusiness.saveUploadFile(file, modelId, dataId, fileCatalog, desc, secrecy);
        //文件处理
        orientModelFileHandle.doHandleModelFile(modelId, dataId, savedFile);
        try {
            BeanUtils.copyProperties(fileModel, savedFile);
            fileModel.setFilePath(savedFile.getFilelocation());
            responseData.setResults(fileModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    @RequestMapping("generateBalanceHtml")
    @ResponseBody
    public void generateBalanceHtml(String hangduanId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String html = accountingFormMgrBusiness.generateBalanceHtml(hangduanId, request).toString();
        PrintWriter printWriter = null;
        if (!"".equals(html)) {
            try {
                printWriter = response.getWriter();
                printWriter.println(html);
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                printWriter.close();
            }
        }
    }
}

