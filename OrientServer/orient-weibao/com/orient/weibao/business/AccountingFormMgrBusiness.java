package com.orient.weibao.business;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.itextpdf.text.DocumentException;
import com.orient.background.util.ModelViewFormHelper;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.FileServerConfig;
import com.orient.login.service.Base64Utils;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.modeldata.controller.ModelFileController;
import com.orient.mongorequest.model.CommonResponse;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IFunction;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.*;
import com.orient.utils.image.ImageUtils;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.base.ExtSorter;
import com.orient.web.form.model.FileModel;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.accountingFormTableBean.CurrentOutTemplateBean;
import com.orient.weibao.bean.accountingFormTableBean.DivingReportTableBean;
import com.orient.weibao.bean.accountingFormTableBean.ScientistPlanTableBean;
import com.orient.weibao.bean.table.*;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.dao.CarryToolDao;
import com.orient.weibao.dto.CarryToolWithParams;
import com.orient.weibao.mbg.mapper.TotalCarryToolMapper;
import com.orient.weibao.mbg.model.CheckTempInst;
import com.orient.weibao.mbg.model.DivingPlanTable;
import com.orient.weibao.mbg.model.TotalCarryTool;
import com.orient.weibao.utils.SqlUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.orient.utils.CommonTools.Obj2String;
import static com.orient.utils.JsonUtil.getJavaCollection;

@Service
public class AccountingFormMgrBusiness extends BaseBusiness {

    @Resource(name = "UserService")
    UserService userService;

    @Autowired
    ModelFileBusiness modelFileBusiness;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    FileServerConfig fileServerConfig;
    @Autowired
    ModelDataBusiness modelDataBusiness;
    @Autowired
    private CarryToolDao carryToolDao;
    @Autowired
    TotalCarryToolMapper totalCarryToolMapper;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public void submitOutTemplateTable(CurrentOutTemplateBean currentOutTemplateBean, boolean submitType) {
        IBusinessModel balanceCountBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel informLogTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_LOG, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        String peizhongId = currentOutTemplateBean.getPeizhongId();
        String taskId = currentOutTemplateBean.getTaskId();

        User curUser = UserContextUtil.getCurrentUser();
        Map taskMap = orientSqlEngine.getBmService().createModelQuery(divingTaskTableBM).findById(taskId);
        String taskState = CommonTools.Obj2String(taskMap.get("C_STATE_" + divingTaskTableBM.getId()));
        String taskName = CommonTools.Obj2String(taskMap.get("C_TASK_NAME_" + divingTaskTableBM.getId()));

        String fillTableDate = CommonTools.Obj2String(currentOutTemplateBean.getFillTableDate());
        String depth = CommonTools.Obj2String(currentOutTemplateBean.getDepth());
        String desity = CommonTools.Obj2String(currentOutTemplateBean.getDesity());
        String buoyancyLoss = CommonTools.Obj2String(currentOutTemplateBean.getBuoyancyLoss());
        String comeupDesity = CommonTools.Obj2String(currentOutTemplateBean.getComeupDesity());
        String basketWeight = CommonTools.Obj2String(currentOutTemplateBean.getBasketWeight());
        String basketIronWeight = CommonTools.Obj2String(currentOutTemplateBean.getBasketIronWeight());
        String basketPWaterVolume = CommonTools.Obj2String(currentOutTemplateBean.getBasketPWaterVolume());
        String basketIronPWaterVolume = CommonTools.Obj2String(currentOutTemplateBean.getBasketIronPWaterVolume());
        String leadWeight = CommonTools.Obj2String(currentOutTemplateBean.getLeadWeight());
        String comeupWeight = CommonTools.Obj2String(currentOutTemplateBean.getComeupWeight());
        String adjustWeight = CommonTools.Obj2String(currentOutTemplateBean.getAdjustWeight());
        String divingLoad = CommonTools.Obj2String(currentOutTemplateBean.getDivingLoad());
        String mercury = CommonTools.Obj2String(currentOutTemplateBean.getMercury());
        String personStatisticsRowData = currentOutTemplateBean.getPersonStatisticsRowData();
        String toolStatisticsRowData = currentOutTemplateBean.getToolStatisticsRowData();
        String peizhongPeople = currentOutTemplateBean.getPeizhongPeople();
        String checker = currentOutTemplateBean.getChecker();
        String departLeader = currentOutTemplateBean.getDepartLeader();
        String explain = currentOutTemplateBean.getExplain();
        String planFloatHours = currentOutTemplateBean.getPlanFloatHours();
        String workSeaArea = currentOutTemplateBean.getWorkSeaArea();
        String basketIronDensity = currentOutTemplateBean.getBasketIronDensity();

        String cabinWeight = currentOutTemplateBean.getCabinWeight();
        String totalWeight0 = currentOutTemplateBean.getTotalWeight0();
        String totalPWaterVolume0 = currentOutTemplateBean.getTotalPWaterVolume0();
        String leadPWaterVolume = currentOutTemplateBean.getLeadPWaterVolume();
        String comeupPWaterVolume = currentOutTemplateBean.getComeupPWaterVolume();
        String totalWeight1 = currentOutTemplateBean.getTotalWeight1();
        String totalPWaterVolume1 = currentOutTemplateBean.getTotalPWaterVolume1();
        String balanceState = currentOutTemplateBean.getBalanceState();
        String planFloatDepth = currentOutTemplateBean.getFloatDepth();

        Map balanceCountMap = UtilFactory.newHashMap();
        balanceCountMap.put("C_FILL_DATE_" + balanceCountBM.getId(), fillTableDate);
        balanceCountMap.put("C_DEPTH_" + balanceCountBM.getId(), depth);
        balanceCountMap.put("C_DESITY_" + balanceCountBM.getId(), desity);
        balanceCountMap.put("C_FORCE_LOSE_" + balanceCountBM.getId(), buoyancyLoss);
        balanceCountMap.put("C_COMEUP_DESITY_" + balanceCountBM.getId(), comeupDesity);
        balanceCountMap.put("C_BASKET_WEIGHT_" + balanceCountBM.getId(), basketWeight);
        balanceCountMap.put("C_PW_VOLUME_" + balanceCountBM.getId(), basketPWaterVolume);
        //新增采样篮铁砂
        balanceCountMap.put("C_BASKET_IRON_WEIGHT_" + balanceCountBM.getId(), basketIronWeight);
        balanceCountMap.put("C_BASKET_IRON_PW_" + balanceCountBM.getId(), basketIronPWaterVolume);

        balanceCountMap.put("C_TOOL_" + balanceCountBM.getId(), toolStatisticsRowData);
        balanceCountMap.put("C_BALANCE_LEAD_" + balanceCountBM.getId(), leadWeight);
        balanceCountMap.put("C_COME_UP_LOAD_" + balanceCountBM.getId(), comeupWeight);
        balanceCountMap.put("C_ADJUST_LOAD_W_" + balanceCountBM.getId(), adjustWeight);
        balanceCountMap.put("C_DIVING_LOAD_" + balanceCountBM.getId(), divingLoad);
        balanceCountMap.put("C_QUICKSLIVER_LEVEL_" + balanceCountBM.getId(), mercury);
        balanceCountMap.put("C_PERSON_" + balanceCountBM.getId(), personStatisticsRowData);

        balanceCountMap.put("C_BOB_WEIGHT_PERSON_" + balanceCountBM.getId(), peizhongPeople);
        balanceCountMap.put("C_CHECKER_" + balanceCountBM.getId(), checker);
        balanceCountMap.put("C_DEPART_LEADER_" + balanceCountBM.getId(), departLeader);
        balanceCountMap.put("C_EXPLAIN_" + balanceCountBM.getId(), explain);
        balanceCountMap.put("C_PLAN_FLOAT_HOURS_" + balanceCountBM.getId(), planFloatHours);
        balanceCountMap.put("C_WORK_SEA_AREA_" + balanceCountBM.getId(), workSeaArea);
        balanceCountMap.put("C_IRON_DENSITY_" + balanceCountBM.getId(), basketIronDensity);

        balanceCountMap.put("C_CABINWEIGHT_" + balanceCountBM.getId(), cabinWeight);
        balanceCountMap.put("C_TOTALWEIGHT0_" + balanceCountBM.getId(), totalWeight0);
        balanceCountMap.put("C_TOTAL_PSTJ0_" + balanceCountBM.getId(), totalPWaterVolume0);
        balanceCountMap.put("C_LEAD_PSTJ_" + balanceCountBM.getId(), leadPWaterVolume);
        balanceCountMap.put("C_COMEUP_PSTJ_" + balanceCountBM.getId(), comeupPWaterVolume);
        balanceCountMap.put("C_TOTAL_WEIGHT1_" + balanceCountBM.getId(), totalWeight1);
        balanceCountMap.put("C_TOTAL_PSTJ1_" + balanceCountBM.getId(), totalPWaterVolume1);
        balanceCountMap.put("C_BALANCE_STATE_" + balanceCountBM.getId(), balanceState);
        balanceCountMap.put("C_PLAN_FLOAT_DEPTH_" + balanceCountBM.getId(), planFloatDepth);


        balanceCountBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND ID='" + peizhongId + "'");

        List<Map> balanceCountList = orientSqlEngine.getBmService().createModelQuery(balanceCountBM).orderAsc("TO_NUMBER(ID)").list();
        if (balanceCountList.size() > 0) {
            Map balanceMap = balanceCountList.get(0);
            String balanceId = CommonTools.Obj2String(balanceMap.get("ID"));
            if (submitType) {
                balanceCountMap.put("C_SAVE_OR_SUBMIT_" + balanceCountBM.getId(), "submit");
                if (!"已结束".equals(taskState)) {
                    Map informLogMap = UtilFactory.newHashMap();
                    informLogMap.put("C_TYPE_" + informLogTableBM.getId(), "balance");
                    informLogMap.put("C_TABLE_NAME_" + informLogTableBM.getId(), "均衡计算表");
                    informLogMap.put("C_STATE_" + informLogTableBM.getId(), "发布");
                    informLogMap.put("C_TABLE_ID_" + informLogTableBM.getId(), balanceId);
                    informMap(informLogTableBM, taskId, taskName, curUser, informLogMap);
                    orientSqlEngine.getBmService().insertModelData(informLogTableBM, informLogMap);
                }
            } else {
                balanceCountMap.put("C_SAVE_OR_SUBMIT_" + balanceCountBM.getId(), "save");
            }
            orientSqlEngine.getBmService().updateModelData(balanceCountBM, balanceCountMap, balanceId);
        } else {
            balanceCountMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
//            insertPersonOrToolRowData(personStatisticsRowData,balanceCountBM,taskId,true);
//            insertPersonOrToolRowData(toolStatisticsRowData,balanceCountBM,taskId,false);
            Map informLogMap = UtilFactory.newHashMap();
            if (submitType) {
                balanceCountMap.put("C_SAVE_OR_SUBMIT_" + balanceCountBM.getId(), "submit");
                if (!"已结束".equals(taskState)) {
                    informLogMap.put("C_TYPE_" + informLogTableBM.getId(), "balance");
                    informLogMap.put("C_TABLE_NAME_" + informLogTableBM.getId(), "均衡计算表");
                    informLogMap.put("C_STATE_" + informLogTableBM.getId(), "发布");
                    informMap(informLogTableBM, taskId, taskName, curUser, informLogMap);
                }
            } else {
                balanceCountMap.put("C_SAVE_OR_SUBMIT_" + balanceCountBM.getId(), "save");
            }
            String balanceId = orientSqlEngine.getBmService().insertModelData(balanceCountBM, balanceCountMap);
            if (submitType && (!"已结束".equals(taskState))) {
                informLogMap.put("C_TABLE_ID_" + informLogTableBM.getId(), balanceId);
                orientSqlEngine.getBmService().insertModelData(informLogTableBM, informLogMap);
            }
        }
    }

    public ModelAndView getOutTemplateTable(String taskId, String taskName, String peizhongId, boolean isCanEdit, boolean isPhone, boolean isOnlyView) {
        ModelAndView retVal = new ModelAndView();
//        String viewName = "/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/" + (!isPhone ? "currentOutTemplate.jsp" : "currentOutTemplatePhone.jsp");
        String viewName = "/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/currentOutTemplate.jsp";
        IBusinessModel balanceCountBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel carryToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel personWeightBM = businessModelService.getBusinessModelBySName(PropertyConstant.PERSON_WEIGHT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel depthDensityTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTHDESITY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);

        String hangduanId = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).findById(taskId).get("T_HANGDUAN_" + schemaId + "_ID");
        retVal.addObject("isCanEdit", isCanEdit);
        retVal.addObject("taskName", taskName);
        divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND C_TYPE_" + divingDeviceBM.getId() + "='" + "total" + "'");
        List<Map> divingDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceBM).list();
        if (divingDeviceList.size() > 0) {
            retVal.addObject("dDeviceWeight", CommonTools.Obj2String(divingDeviceList.get(0).get("C_AIR_WEIGHT_" + divingDeviceBM.getId())));
            retVal.addObject("dDevicePWaterVolume", CommonTools.Obj2String(divingDeviceList.get(0).get("C_PW_VOLUME_" + divingDeviceBM.getId())));
        }

        balanceCountBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND ID='" + peizhongId + "'");
        List<Map> balanceCountList = orientSqlEngine.getBmService().createModelQuery(balanceCountBM).orderAsc("TO_NUMBER(ID)").list();
        if (balanceCountList.size() > 0) {
            for (Map balanceMap : balanceCountList) {
                String refDivingPlanId = CommonTools.Obj2String(balanceMap.get("T_DIVING__PLAN_TABLE_" + schemaId + "_ID"));
                Map divingPlanMap = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).findById(refDivingPlanId);
                if (divingPlanMap != null) {
                    String divingDepth = CommonTools.Obj2String(divingPlanMap.get("C_PLAN_DIVING_DEPTH_" + divingPlanBM.getId()));
                    String divingDesity = CommonTools.Obj2String(divingPlanMap.get("C_DENSITY_" + divingPlanBM.getId()));
                    retVal.addObject("depth", divingDepth);
//                    retVal.addObject("desity", divingDesity);
                    String planFloatDepth = CommonTools.Obj2String(divingPlanMap.get("C_PLAN_FLOAT_DEPTH_" + divingPlanBM.getId()));
                    retVal.addObject("floatDepth", planFloatDepth);
                    String selectZuoxian = CommonTools.Obj2String(divingPlanMap.get("C_ZUOXIAN_" + divingPlanBM.getId()));
                    String selectMainDriver = CommonTools.Obj2String(divingPlanMap.get("C_MAIN_DRIVER_" + divingPlanBM.getId()));
                    String selectYouxian = CommonTools.Obj2String(divingPlanMap.get("C_YOUXIAN_" + divingPlanBM.getId()));
                    retVal.addObject("leftDriver", selectZuoxian);
                    retVal.addObject("mainDriver", selectMainDriver);
                    retVal.addObject("rightDriver", selectYouxian);

                    retVal.addObject("hangduanId", hangduanId);
//                    Map divingTaskMap=orientSqlEngine.getBmService().createModelQuery(divingTaskBM).findById(taskId);
//                    String hangduanId=CommonTools.Obj2String(divingTaskMap.get("T_HANGDUAN_"+schemaId+"_ID"));
//                    personWeightBM.setReserve_filter("AND T_HANGDUAN_"+schemaId+"_ID='"+hangduanId+"'");
//                    List<Map> personWeightList=orientSqlEngine.getBmService().createModelQuery(personWeightBM).list();
//                    if (personWeightList.size()>0){
//                        for (Map personWeightMap:personWeightList){
//                            if (selectZuoxian.equals(personWeightMap.get("C_ATTEND_ID_"+personWeightBM.getId()))) {
//                                retVal.addObject("leftDriverWeight", personWeightMap.get("C_WEIGHT_"+personWeightBM.getId()));
//                            }
//                            if (selectMainDriver.equals(personWeightMap.get("C_ATTEND_ID_"+personWeightBM.getId()))) {
//                                retVal.addObject("mainDriverWeight", personWeightMap.get("C_WEIGHT_"+personWeightBM.getId()));
//                            }
//                            if (selectYouxian.equals(personWeightMap.get("C_ATTEND_ID_"+personWeightBM.getId()))) {
//                                retVal.addObject("rightDriverWeight",personWeightMap.get("C_WEIGHT_"+personWeightBM.getId()));
//                            }
//                        }
//                    }
                }
                retVal.addObject("fillTableDate", CommonTools.Obj2String(balanceMap.get("C_FILL_DATE_" + balanceCountBM.getId())));
//                retVal.addObject("depth", CommonTools.Obj2String(balanceMap.get("C_DEPTH_" + balanceCountBM.getId())));

                retVal.addObject("desity", CommonTools.Obj2String(balanceMap.get("C_DESITY_" + balanceCountBM.getId())));
                retVal.addObject("buoyancyLoss", CommonTools.Obj2String(balanceMap.get("C_FORCE_LOSE_" + balanceCountBM.getId())));
//                retVal.addObject("basketWeight", CommonTools.Obj2String(balanceMap.get("C_BASKET_WEIGHT_" + balanceCountBM.getId())));
//                retVal.addObject("basketPWaterVolume", CommonTools.Obj2String(balanceMap.get("C_PW_VOLUME_" + balanceCountBM.getId())));
                retVal.addObject("basketIronWeight", CommonTools.Obj2String(balanceMap.get("C_BASKET_IRON_WEIGHT_" + balanceCountBM.getId())));
                retVal.addObject("basketIronPWaterVolume", CommonTools.Obj2String(balanceMap.get("C_BASKET_IRON_PW_" + balanceCountBM.getId())));
                retVal.addObject("leadWeight", CommonTools.Obj2String(balanceMap.get("C_BALANCE_LEAD_" + balanceCountBM.getId())));
                retVal.addObject("comeupWeight", CommonTools.Obj2String(balanceMap.get("C_COME_UP_LOAD_" + balanceCountBM.getId())));
                retVal.addObject("adjustWeight", CommonTools.Obj2String(balanceMap.get("C_ADJUST_LOAD_W_" + balanceCountBM.getId())));
                retVal.addObject("divingLoad", CommonTools.Obj2String(balanceMap.get("C_DIVING_LOAD_" + balanceCountBM.getId())));
                retVal.addObject("mercury", CommonTools.Obj2String(balanceMap.get("C_QUICKSLIVER_LEVEL_" + balanceCountBM.getId())));
                retVal.addObject("personStatisticsRowData", CommonTools.Obj2String(balanceMap.get("C_PERSON_" + balanceCountBM.getId())));
                retVal.addObject("toolStatisticsRowData", CommonTools.Obj2String(balanceMap.get("C_TOOL_" + balanceCountBM.getId())));
                retVal.addObject("taskName", taskName);
                retVal.addObject("peizhongPeople", CommonTools.Obj2String(balanceMap.get("C_BOB_WEIGHT_PERSON_" + balanceCountBM.getId())));
                retVal.addObject("checker", CommonTools.Obj2String(balanceMap.get("C_CHECKER_" + balanceCountBM.getId())));
                retVal.addObject("departLeader", CommonTools.Obj2String(balanceMap.get("C_DEPART_LEADER_" + balanceCountBM.getId())));
                retVal.addObject("explain", CommonTools.Obj2String(balanceMap.get("C_EXPLAIN_" + balanceCountBM.getId())));
                retVal.addObject("comeupDesity", CommonTools.Obj2String(balanceMap.get("C_COMEUP_DESITY_" + balanceCountBM.getId())));
                retVal.addObject("planFloatHours", CommonTools.Obj2String(balanceMap.get("C_PLAN_FLOAT_HOURS_" + balanceCountBM.getId())));
                retVal.addObject("workSeaArea", CommonTools.Obj2String(balanceMap.get("C_WORK_SEA_AREA_" + balanceCountBM.getId())));
                retVal.addObject("basketIronDensity", CommonTools.Obj2String(balanceMap.get("C_IRON_DENSITY_" + balanceCountBM.getId())));
                String isSubmitTable = Obj2String(balanceMap.get("C_SAVE_OR_SUBMIT_" + balanceCountBM.getId()));

                if (isOnlyView||isPhone) {
                    String workSeaAreaId = CommonTools.Obj2String(balanceMap.get("C_WORK_SEA_AREA_" + balanceCountBM.getId()));
                    if (!"".equals(workSeaAreaId)) {
                        Map seaAreaMap = orientSqlEngine.getBmService().createModelQuery(depthDensityTypeBM).findById(workSeaAreaId);
                        String seaAreaName = CommonTools.Obj2String(seaAreaMap.get("C_NAME_" + depthDensityTypeBM.getId()));
                        retVal.addObject("seaAreaName", seaAreaName);
                    }
                    viewName = "/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/view/viewCurrentOutTemplate.jsp";
                }

                retVal.addObject("isSubmitTable", isSubmitTable);
                String divingPlanId = CommonTools.Obj2String(balanceMap.get("T_DIVING__PLAN_TABLE_" + schemaId + "_ID"));
                carryToolBM.clearAllFilter();
                carryToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" + " AND C_TOTAL_STATE_" + carryToolBM.getId() + "='" + "total" + "'" +
                        " AND C_CABIN_OUTORIN_" + carryToolBM.getId() + "='" + "out" + "'" +
                        " AND C_DIVING_PLAN_ID_" + carryToolBM.getId() + "='" + divingPlanId + "'");
                List<Map> carryToolList = orientSqlEngine.getBmService().createModelQuery(carryToolBM).list();
                if (carryToolList.size() > 0) {
                    Map carryToolMap = carryToolList.get(0);
//                    String basketWeight = CommonTools.Obj2String(carryToolMap.get("C_NET_WEIGHT_" + carryToolBM.getId()));
                    String basketWeight = CommonTools.Obj2String(carryToolMap.get("C_AIR_WEIGHT_" + carryToolBM.getId()));
                    String basketPwVolumn = CommonTools.Obj2String(carryToolMap.get("C_PW_VOLUMN_" + carryToolBM.getId()));
                    retVal.addObject("basketWeight", basketWeight);
                    retVal.addObject("basketPWaterVolume", basketPwVolumn);
                }
            }
        }
        retVal.setViewName(viewName);
        return retVal;
    }

    public ModelAndView getDivingTypeData(String hangduanId) {
        ModelAndView retVal = new ModelAndView();
        String viewName = "/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/preOutTemplate.jsp";
        IBusinessModel taskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);

        taskBM.setReserve_filter("AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'");
        List<Map> taskList = orientSqlEngine.getBmService().createModelQuery(taskBM).orderAsc("C_TASK_NAME_" + taskBM.getId()).list();
        if (taskList.size() > 0) {
            List<Map> showTaskList = UtilFactory.newArrayList();
            for (Map taskMap : taskList) {
                Map showTaskMap = UtilFactory.newHashMap();
                String taskName = CommonTools.Obj2String(taskMap.get("C_TASK_NAME_" + taskBM.getId()));
                String showTaskId = CommonTools.Obj2String(taskMap.get("ID"));
                showTaskMap.put("id", showTaskId);
                showTaskMap.put("name", taskName);
                showTaskList.add(showTaskMap);
            }
            //list集合转为json数组
            retVal.addObject("showTaskList", JSONArray.fromObject(showTaskList));
        }
        retVal.setViewName(viewName);
        return retVal;
    }

    public List<Map> getPreRefOutTemplateData(String taskId) throws Exception {
        IBusinessModel balanceCountBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel depthDensityTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTHDESITY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel carryToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);

        divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND C_TYPE_" + divingDeviceBM.getId() + "='" + "total" + "'");
        List<Map> divingDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceBM).list();
        List<Map> preDivingList = UtilFactory.newArrayList();
        Map preDivingMap = UtilFactory.newHashMap();
        if (divingDeviceList.size() > 0) {
            preDivingMap.put("dDeviceWeight", CommonTools.Obj2String(divingDeviceList.get(0).get("C_AIR_WEIGHT_" + divingDeviceBM.getId())));
            preDivingMap.put("dDevicePWaterVolume", CommonTools.Obj2String(divingDeviceList.get(0).get("C_PW_VOLUME_" + divingDeviceBM.getId())));
        }
        balanceCountBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND C_TABLE_STATE_" + balanceCountBM.getId() + "='" + "当前" + "'");
        List<Map> balanceCountList = orientSqlEngine.getBmService().createModelQuery(balanceCountBM).orderAsc("TO_NUMBER(ID)").list();
        if (balanceCountList.size() > 0) {
            for (Map balanceMap : balanceCountList) {
                String divingPlanId = CommonTools.Obj2String(balanceMap.get("T_DIVING__PLAN_TABLE_" + schemaId + "_ID"));
                String seaArea = CommonTools.Obj2String(balanceMap.get("C_WORK_SEA_AREA_" + balanceCountBM.getId()));
                if (!"".equals(seaArea)) {
                    Map seaAreaMap = orientSqlEngine.getBmService().createModelQuery(depthDensityTypeBM).findById(seaArea);
                    if (seaAreaMap != null) {
                        preDivingMap.put("beforeWorkSeaArea", seaAreaMap.get("C_NAME_" + depthDensityTypeBM.getId()));
                    }
                }
                Map divingPlanMap = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).findById(divingPlanId);
                if (divingPlanMap != null) {
                    String divingDepth = CommonTools.Obj2String(divingPlanMap.get("C_PLAN_DIVING_DEPTH_" + divingPlanBM.getId()));
                    String planFloatDepth = CommonTools.Obj2String(divingPlanMap.get("C_PLAN_FLOAT_DEPTH_" + divingPlanBM.getId()));
                    preDivingMap.put("depth", divingDepth);
                    preDivingMap.put("beforeFloatDepth", planFloatDepth);
                    String selectZuoxian = CommonTools.Obj2String(divingPlanMap.get("C_ZUOXIAN_" + divingPlanBM.getId()));
                    String selectMainDriver = CommonTools.Obj2String(divingPlanMap.get("C_MAIN_DRIVER_" + divingPlanBM.getId()));
                    String selectYouxian = CommonTools.Obj2String(divingPlanMap.get("C_YOUXIAN_" + divingPlanBM.getId()));
                    preDivingMap.put("leftDriver", selectZuoxian);
                    preDivingMap.put("mainDriver", selectMainDriver);
                    preDivingMap.put("rightDriver", selectYouxian);
                }


                String planFloatHours = CommonTools.Obj2String(balanceMap.get("C_PLAN_FLOAT_HOURS_" + balanceCountBM.getId()));
                preDivingMap.put("beforeFloatHours", planFloatHours);

                String desity = CommonTools.Obj2String(balanceMap.get("C_DESITY_" + balanceCountBM.getId()));
                preDivingMap.put("desity", desity);
                String divingDepth = CommonTools.Obj2String(preDivingMap.get("depth"));
                if (!"".equals(desity) && !"".equals(divingDepth)) {
                    double buoyancyLoss = 5327.24 * 13 / Double.parseDouble(desity) + 9716 * Double.parseDouble(desity) * 9.80665 * Double.parseDouble(divingDepth) / 2800000000L + 69.5 * Double.parseDouble(divingDepth) / 4500;
                    //四舍五入只保留一位小数
                    buoyancyLoss = new BigDecimal(buoyancyLoss).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                    preDivingMap.put("buoyancyLoss", buoyancyLoss);
                }
                preDivingMap.put("fillTableDate", CommonTools.Obj2String(balanceMap.get("C_FILL_DATE_" + balanceCountBM.getId())));

//                preDivingMap.put("depth", CommonTools.Obj2String(balanceMap.get("C_DEPTH_" + balanceCountBM.getId())));
//                String depthId = CommonTools.Obj2String(balanceMap.get("C_DEPTH_" + balanceCountBM.getId()));
//                if (!"".equals(depthId)) {
//                    Map depthDesityMap = orientSqlEngine.getBmService().createModelQuery(depthDensityBM).findById(depthId);
//                    if (depthDesityMap != null) {
//                        String depth = CommonTools.Obj2String(depthDesityMap.get("C_DEPTH_" + depthDensityBM.getId()));
//                        String density = CommonTools.Obj2String(depthDesityMap.get("C_DESITY_" + depthDensityBM.getId()));
//                        preDivingMap.put("desity", density);
//                        preDivingMap.put("depth", depth);
//                    }
//                }
//                preDivingMap.put("basketWeight", CommonTools.Obj2String(balanceMap.get("C_BASKET_WEIGHT_" + balanceCountBM.getId())));
//                preDivingMap.put("basketPWaterVolume", CommonTools.Obj2String(balanceMap.get("C_PW_VOLUME_" + balanceCountBM.getId())));
                preDivingMap.put("basketIronDensity", CommonTools.Obj2String(balanceMap.get("C_IRON_DENSITY_" + balanceCountBM.getId())));
                preDivingMap.put("basketIronWeight", CommonTools.Obj2String(balanceMap.get("C_BASKET_IRON_WEIGHT_" + balanceCountBM.getId())));
                preDivingMap.put("basketIronPWaterVolume", CommonTools.Obj2String(balanceMap.get("C_BASKET_IRON_PW_" + balanceCountBM.getId())));
                preDivingMap.put("leadWeight", CommonTools.Obj2String(balanceMap.get("C_BALANCE_LEAD_" + balanceCountBM.getId())));
                preDivingMap.put("comeupWeight", CommonTools.Obj2String(balanceMap.get("C_COME_UP_LOAD_" + balanceCountBM.getId())));
                preDivingMap.put("adjustWeight", CommonTools.Obj2String(balanceMap.get("C_ADJUST_LOAD_W_" + balanceCountBM.getId())));
                preDivingMap.put("divingLoad", CommonTools.Obj2String(balanceMap.get("C_DIVING_LOAD_" + balanceCountBM.getId())));
                preDivingMap.put("mercury", CommonTools.Obj2String(balanceMap.get("C_QUICKSLIVER_LEVEL_" + balanceCountBM.getId())));
//                String personString = CommonTools.Obj2String(balanceMap.get("C_PERSON_" + balanceCountBM.getId()));
//                if (personString != "") {
//                    org.json.JSONArray jsonArray = null;
//                    //字符串转为json数组
//                    jsonArray = new org.json.JSONArray(personString);
//                    if (jsonArray.length() > 0) {
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            //获取json对象
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            String userId = jsonObject.getString("name");
//                            User user = userService.findById(userId);
//                            if (user != null) {
//                                String userName = user.getAllName();
//                                //更改键对应的值
//                                jsonObject.put("name", userName);
//                            }
//                        }
//                        personString = jsonArray.toString();
//                    }
//                }
                preDivingMap.put("personStatisticsRowData", CommonTools.Obj2String(balanceMap.get("C_PERSON_" + balanceCountBM.getId())));
                preDivingMap.put("toolStatisticsRowData", CommonTools.Obj2String(balanceMap.get("C_TOOL_" + balanceCountBM.getId())));
                preDivingMap.put("comeupDesity", CommonTools.Obj2String(balanceMap.get("C_COMEUP_DESITY_" + balanceCountBM.getId())));

//                String divingPlanId = CommonTools.Obj2String(balanceMap.get("T_DIVING__PLAN_TABLE_" + schemaId + "_ID"));
                carryToolBM.clearAllFilter();
                carryToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" + " AND C_TOTAL_STATE_" + carryToolBM.getId() + "='" + "total" + "'" +
                        " AND C_CABIN_OUTORIN_" + carryToolBM.getId() + "='" + "out" + "'" +
                        " AND C_DIVING_PLAN_ID_" + carryToolBM.getId() + "='" + divingPlanId + "'");
                List<Map> carryToolList = orientSqlEngine.getBmService().createModelQuery(carryToolBM).list();
                if (carryToolList.size() > 0) {
                    Map carryToolMap = carryToolList.get(0);
//                    String basketWeight = CommonTools.Obj2String(carryToolMap.get("C_NET_WEIGHT_" + carryToolBM.getId()));
                    String basketWeight = CommonTools.Obj2String(carryToolMap.get("C_AIR_WEIGHT_" + carryToolBM.getId()));
                    String basketPwVolumn = CommonTools.Obj2String(carryToolMap.get("C_PW_VOLUMN_" + carryToolBM.getId()));
                    preDivingMap.put("basketWeight", basketWeight);
                    preDivingMap.put("basketPWaterVolume", basketPwVolumn);
                }
                preDivingList.add(preDivingMap);
            }
        }

        return preDivingList;
    }

    public ModelAndView getDivingReportData(String taskId, String taskName) {
        ModelAndView retVal = new ModelAndView();
        String viewName = "/app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/divingReport.jsp";
        IBusinessModel divingPlanTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel carryWorkToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel sparePartsBM = businessModelService.getBusinessModelBySName(PropertyConstant.CABIN_CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingReportBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_REPORT, schemaId, EnumInter.BusinessModelEnum.Table);
        divingPlanTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND C_TABLE_STATE_" + divingPlanTableBM.getId() + "='" + "当前" + "'");
        List<Map> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanTableBM).list();
        if (divingPlanList.size() > 0) {
            Map divingPlanMap = divingPlanList.get(0);
            String divingPlanId = CommonTools.Obj2String(divingPlanMap.get("ID"));
            String selectZuoxian = CommonTools.Obj2String(divingPlanMap.get("C_ZUOXIAN_" + divingPlanTableBM.getId()));
            String selectMainDriver = CommonTools.Obj2String(divingPlanMap.get("C_MAIN_DRIVER_" + divingPlanTableBM.getId()));
            String selectYouxian = CommonTools.Obj2String(divingPlanMap.get("C_YOUXIAN_" + divingPlanTableBM.getId()));
//            List<User> usersList = userService.findAllUser();
//            if (usersList.size() > 0) {
//                for (User user : usersList) {
//                    if (selectZuoxian.equals(user.getId())) {
//                        retVal.addObject("leftDriver", user.getAllName());
//                    }
//                    if (selectMainDriver.equals(user.getId())) {
//                        retVal.addObject("mainDriver", user.getAllName());
//                    }
//                    if (selectYouxian.equals(user.getId())) {
//                        retVal.addObject("rightDriver", user.getAllName());
//                    }
//                }
//            }
            String userIds = "";
            if (!"".equals(selectZuoxian)) {
                userIds += selectZuoxian + ",";
            }
            if (!"".equals(selectMainDriver)) {
                userIds += selectMainDriver + ",";
            }
            if (!"".equals(selectYouxian)) {
                userIds += selectYouxian + ",";
            }
            if (!"".equals(userIds)) {
                userIds = userIds.substring(0, userIds.length() - 1);
                StringBuilder userSql = new StringBuilder();
                userSql.append("select id,all_name from cwm_sys_user where id in(").append(userIds).append(")");
                List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());
                if (userList != null && userList.size() > 0) {
                    for (Map userMap : userList) {
                        String userId = userMap.get("id").toString();
                        String userName = CommonTools.Obj2String(userMap.get("all_name"));
                        if (selectZuoxian.equals(userId)) {
                            retVal.addObject("leftDriver", userName);
                        }
                        if (selectMainDriver.equals(userId)) {
                            retVal.addObject("mainDriver", userName);
                        }
                        if (selectYouxian.equals(userId)) {
                            retVal.addObject("rightDriver", userName);
                        }
                    }
                }
            }
            List<CarryToolWithParams> carryToolWithParamsList = carryToolDao.selectByDivingTaskId(taskId, divingPlanId);
            if (carryToolWithParamsList != null && carryToolWithParamsList.size() > 0) {
                StringBuilder carryToolBuilder = new StringBuilder();
                for (CarryToolWithParams carryToolWithParams : carryToolWithParamsList) {
                    String deviceName = CommonTools.Obj2String(carryToolWithParams.getDeviceName());
                    String carryCount = CommonTools.Obj2String(carryToolWithParams.getcCarryCount3486());
                    carryToolBuilder.append(deviceName).append(carryCount).append("个,");
                }
                String carryTools = carryToolBuilder.toString();
                if (!"".equals(carryTools)) {
                    carryTools = carryTools.substring(0, carryTools.length() - 1);
                    retVal.addObject("homeWorkTools", carryTools);
                }
            }
        }
        divingReportBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
        List<Map> divingReportList = orientSqlEngine.getBmService().createModelQuery(divingReportBM).list();
        if (divingReportList.size() > 0) {
            Map divingReportMap = divingReportList.get(0);
            String divingProgress = CommonTools.Obj2String(divingReportMap.get("C_DIVING_PROGRESS_" + divingReportBM.getId()));
            String summary = CommonTools.Obj2String(divingReportMap.get("C_SUMMARY_" + divingReportBM.getId()));
            String date = CommonTools.Obj2String(divingReportMap.get("C_DATE_" + divingReportBM.getId()));
            String jingdu = CommonTools.Obj2String(divingReportMap.get("C_JINGDU_" + divingReportBM.getId()));
            String weidu = CommonTools.Obj2String(divingReportMap.get("C_WEIDU_" + divingReportBM.getId()));
            String troubleHandle = CommonTools.Obj2String(divingReportMap.get("C_FAULT_HANDLE_" + divingReportBM.getId()));
            String sampleSituation = CommonTools.Obj2String(divingReportMap.get("C_SAMPLE_SITUATION_" + divingReportBM.getId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //计算全程工作时长
            String hatchCloseTime = CommonTools.Obj2String(divingReportMap.get("C_HATCH_CLOSE_" + divingReportBM.getId()));
            String hatchOpenTime = CommonTools.Obj2String(divingReportMap.get("C_HATCH_OPEN_" + divingReportBM.getId()));
            String totalWorkHours = differentHours(hatchOpenTime, hatchCloseTime);
            retVal.addObject("totalWorkHours", totalWorkHours);

            String divingMaxDepth = CommonTools.Obj2String(divingReportMap.get("C_DIVING_DEPTH_" + divingReportBM.getId()));
            //计算水中时长
            String outWaterTime = CommonTools.Obj2String(divingReportMap.get("C_OUT_WATER_" + divingReportBM.getId()));
            String inWaterTime = CommonTools.Obj2String(divingReportMap.get("C_IN_WATER_" + divingReportBM.getId()));
            String waterHours = differentHours(outWaterTime, inWaterTime);
            retVal.addObject("waterHours", waterHours);

            retVal.addObject("divingMaxDepth", divingMaxDepth);

            //计算水下作业时长(作业结束-作业开始)
            //作业结束时间（抛载上浮压载时间）
            String workEndTime = CommonTools.Obj2String(divingReportMap.get("C_WORK_END_" + divingReportBM.getId()));
            String workStartTime = CommonTools.Obj2String(divingReportMap.get("C_WORK_START_" + divingReportBM.getId()));
            String underWaterHours = differentHours(workEndTime, workStartTime);
            retVal.addObject("underWaterHours", underWaterHours);

            //计算平均下潜速度（抛载终止下潜压载时深度÷（作业开始时间-开始注水时间+6））
            String throwEndDepth = CommonTools.Obj2String(divingReportMap.get("C_THROW_END_DEPTH_" + divingReportBM.getId()));
            String startFillWaterTime = CommonTools.Obj2String(divingReportMap.get("C_START_FILL_WATER_" + divingReportBM.getId()));
            String waterDEnvironmentDesp = CommonTools.Obj2String(divingReportMap.get("C_ENVIRONMENT_DESP_" + divingReportBM.getId()));
            try {
                //计算平均下潜速度（抛载终止下潜压载时深度÷（作业开始时间-开始注水时间+6））
                if (workStartTime != "" && startFillWaterTime != "") {
                    Date workStartDate = sdf.parse(workStartTime);
                    Date startFillWaterDate = sdf.parse(startFillWaterTime);
                    long diffeTotalMsCount = workStartDate.getTime() - startFillWaterDate.getTime();
                    long diffeTotalMinutes = (diffeTotalMsCount / (60 * 1000)) + 6;
                    double averageDivingSpeed = Double.valueOf(throwEndDepth) / diffeTotalMinutes;
                    //保留两位小数
                    DecimalFormat df = new DecimalFormat("0.00");
                    String averageDivingSpeeds = df.format(averageDivingSpeed);
                    retVal.addObject("averageDivingSpeed", averageDivingSpeeds);
                }
                //计算平均上浮速度（抛载上浮压载时深度÷（上浮到水面时间-抛载上浮压载时间））
                String throwUpDepth = CommonTools.Obj2String(divingReportMap.get("C_THROW_UP_DEPTH_" + divingReportBM.getId()));
                String floatWaterTime = CommonTools.Obj2String(divingReportMap.get("C_FLOAT_WATER_TIME_" + divingReportBM.getId()));
                if (floatWaterTime != "" && workEndTime != "") {
                    Date floatWaterDate = sdf.parse(floatWaterTime);
                    Date workEndDate = sdf.parse(workEndTime);
                    long diffeTotalMsCount = floatWaterDate.getTime() - workEndDate.getTime();
                    long diffeTotalMinutes = (diffeTotalMsCount / (60 * 1000));
                    double averageFloatSpeed = 0.0;
                    if (diffeTotalMinutes != 0) {
                        averageFloatSpeed = Double.valueOf(throwUpDepth) / diffeTotalMinutes;
                    }
                    retVal.addObject("averageFloatSpeed", averageFloatSpeed);
                }
                retVal.addObject("jingdu", jingdu);

                retVal.addObject("weidu", weidu);
                retVal.addObject("date", date);

                retVal.addObject("sampleSituation", sampleSituation);
                retVal.addObject("divingProgress", divingProgress);
                retVal.addObject("summary", summary);
                retVal.addObject("troubleHandle", troubleHandle);
                retVal.addObject("waterDEnvironmentDesp", waterDEnvironmentDesp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        retVal.addObject("taskName", taskName);
        retVal.setViewName(viewName);
        return retVal;
    }

    //计算时间差
    public Long differentMs(String date_e, String date_s) {
        long differentMs = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!"".equals(date_e) && !"".equals(date_s)) {
            Date dateCloseDate = null;
            try {
                dateCloseDate = sdf.parse(date_e);
                Date dateOpenDate = sdf.parse(date_s);
//                long totalWorkMsCount = dateCloseDate.getTime() - dateOpenDate.getTime();
//                long totalHours = totalWorkMsCount / (60 * 60 * 1000);
                //总共的分钟数减去小时分钟数，即是所剩的分钟
//                long totalMinutes = totalWorkMsCount / (60 * 1000) - totalHours * 60;
//                differentHoursMins = totalHours + "h" + totalMinutes + "min";
                differentMs = dateCloseDate.getTime() - dateOpenDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return differentMs;
    }

    //计算时间差
    public String differentHours(String date_e, String date_s) {
        String differentHoursMins = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date_e != "" && date_s != "") {
            Date dateCloseDate = null;
            try {
                dateCloseDate = sdf.parse(date_e);
                Date dateOpenDate = sdf.parse(date_s);
                long totalWorkMsCount = dateCloseDate.getTime() - dateOpenDate.getTime();
                long totalHours = totalWorkMsCount / (60 * 60 * 1000);
                //总共的分钟数减去小时分钟数，即是所剩的分钟
                long totalMinutes = totalWorkMsCount / (60 * 1000) - totalHours * 60;
                differentHoursMins = totalHours + "h" + totalMinutes + "min";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return differentHoursMins;
    }


    public void submitDivingReportTable(DivingReportTableBean divingReportTableBean) {
        IBusinessModel divingReportBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_REPORT, schemaId, EnumInter.BusinessModelEnum.Table);
        String taskId = divingReportTableBean.getTaskId();
        String divingProgress = CommonTools.Obj2String(divingReportTableBean.getDivingProgress());
        String troubleHandle = CommonTools.Obj2String(divingReportTableBean.getTroubleHandle());
        String waterDEnvironmentDesp = CommonTools.Obj2String(divingReportTableBean.getWaterDEnvironmentDesp());
        String summary = CommonTools.Obj2String(divingReportTableBean.getSummary());

        Map divingReportMap = UtilFactory.newHashMap();
        divingReportMap.put("C_DIVING_PROGRESS_" + divingReportBM.getId(), divingProgress);
        divingReportMap.put("C_SUMMARY_" + divingReportBM.getId(), summary);
        divingReportMap.put("C_FAULT_HANDLE_" + divingReportBM.getId(), troubleHandle);
        divingReportMap.put("C_ENVIRONMENT_DESP_" + divingReportBM.getId(), waterDEnvironmentDesp);
        divingReportBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");

        List<Map> divingReportList = orientSqlEngine.getBmService().createModelQuery(divingReportBM).orderAsc("TO_NUMBER(ID)").list();
        if (divingReportList.size() > 0) {
            for (Map reportMap : divingReportList) {
                String divingReportId = CommonTools.Obj2String(reportMap.get("ID"));
                orientSqlEngine.getBmService().updateModelData(divingReportBM, divingReportMap, divingReportId);
            }
        } else {
            divingReportMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            orientSqlEngine.getBmService().insertModelData(divingReportBM, divingReportMap);
        }
    }

    public AjaxResponseData<List> getDepthDesityData(String deptyDesityTypeId, String peizhongId) {
        deptyDesityTypeId = deptyDesityTypeId == null ? "" : deptyDesityTypeId;
        peizhongId = peizhongId == null ? "" : peizhongId;
        IBusinessModel depthDesityBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTH_DESITY, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel peizhongBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map> depthDesityLists = UtilFactory.newArrayList();
        if (!"-1".equals(deptyDesityTypeId) && !"".equals(deptyDesityTypeId)) {
            depthDesityBM.setReserve_filter("AND T_DEPTH_DESITY_TYPE_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + deptyDesityTypeId + "'");
        } else if ("".equals(deptyDesityTypeId) && !"".equals(peizhongId)) {
            Map<String, String> peizhongMap = orientSqlEngine.getBmService().createModelQuery(peizhongBM).findById(peizhongId);
            String divingPlanId = peizhongMap.get("T_DIVING__PLAN_TABLE_" + schemaId + "_ID");
            Map<String, String> divingPlanMap = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).findById(divingPlanId);
            deptyDesityTypeId = divingPlanMap.get("C_SEA_AREA_" + divingPlanBM.getId());
            if (!"-1".equals(deptyDesityTypeId) && !"".equals(deptyDesityTypeId)) {
                depthDesityBM.setReserve_filter("AND T_DEPTH_DESITY_TYPE_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + deptyDesityTypeId + "'");
            }
        }
        List<Map<String, Object>> depthDesityList = orientSqlEngine.getBmService().createModelQuery(depthDesityBM).orderAsc("TO_NUMBER(C_DEPTH_" + depthDesityBM.getId() + ")").list();
        if (depthDesityList.size() > 0) {
            for (Map depthDesityMap : depthDesityList) {
                Map depthMap = UtilFactory.newHashMap();
                String depth = CommonTools.Obj2String(depthDesityMap.get("C_DEPTH_" + depthDesityBM.getId()));
                String density = CommonTools.Obj2String(depthDesityMap.get("C_DESITY_" + depthDesityBM.getId()));
                String depthDesityKeyId = CommonTools.Obj2String(depthDesityMap.get("ID"));
                depthMap.put("id", depthDesityKeyId);
                depthMap.put("depth", depth);
                depthMap.put("density", density);
                depthDesityLists.add(depthMap);
            }
        }
        return new AjaxResponseData<>(depthDesityLists);
    }

    public AjaxResponseData<String> getDensityBySeaAreaAndDepth(String deptyDesityTypeId, String divingDepth) {
        divingDepth = divingDepth.replaceAll("\n", "");
        IBusinessModel depthDesityBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTH_DESITY, schemaId, EnumInter.BusinessModelEnum.Table);
        depthDesityBM.setReserve_filter("AND T_DEPTH_DESITY_TYPE_" + schemaId + "_ID='" + deptyDesityTypeId + "'" +
                " AND C_DEPTH_" + depthDesityBM.getId() + "='" + divingDepth + "'");
        List<Map<String, Object>> depthDensityList = orientSqlEngine.getBmService().createModelQuery(depthDesityBM).list();
        if (depthDensityList != null && depthDensityList.size() > 0) {
            Map depthDensityMap = depthDensityList.get(0);
            String density = CommonTools.Obj2String(depthDensityMap.get("C_DESITY_" + depthDesityBM.getId()));
            return new AjaxResponseData<>(density);
        }
        return new AjaxResponseData<>(null);
    }

    public AjaxResponseData<List> getDepthDesityTypeData() {
        IBusinessModel depthDesityTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTHDESITY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, Object>> depthDesityTypeList = orientSqlEngine.getBmService().createModelQuery(depthDesityTypeBM).list();
        List<Map> depthDesityTypeLists = UtilFactory.newArrayList();
        if (depthDesityTypeList.size() > 0) {
            for (Map depthDesityTypeMap : depthDesityTypeList) {
                Map depthTypeMap = UtilFactory.newHashMap();
                String depthDesityTypeName = CommonTools.Obj2String(depthDesityTypeMap.get("C_NAME_" + depthDesityTypeBM.getId()));
                String depthDesityTypeKeyId = CommonTools.Obj2String(depthDesityTypeMap.get("ID"));
                depthTypeMap.put("id", depthDesityTypeKeyId);
                depthTypeMap.put("depthDesityTypeName", depthDesityTypeName);
                depthDesityTypeLists.add(depthTypeMap);
            }
        }
        return new AjaxResponseData<>(depthDesityTypeLists);
    }


    public void submitScientistDivingPlan(HttpServletRequest request) throws Exception {
//        CommonResponse retVal = new CommonResponse();
        IBusinessModel divingPlanTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel carryWorkToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel informLogTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_LOG, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel balanceBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        String taskId = request.getParameter("taskId");
        String divingPlanId = request.getParameter("divingPlanId");
        String specialRowData = request.getParameter("specialRowData");
        String cabinInRowData = request.getParameter("cabinInRowData");
        String totalNetWeight = request.getParameter("totalNetWeight");
        String planDivingDepth = request.getParameter("planDivingDepth");
        String planFloatDepth = request.getParameter("planFloatDepth");
        String density = request.getParameter("density");

        Map taskMap = orientSqlEngine.getBmService().createModelQuery(divingTaskTableBM).findById(taskId);
        String taskState = CommonTools.Obj2String(taskMap.get("C_STATE_" + divingTaskTableBM.getId()));
        String taskName = CommonTools.Obj2String(taskMap.get("C_TASK_NAME_" + divingTaskTableBM.getId()));

        divingPlanTableBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                " AND ID='" + divingPlanId + "'");
        List<Map> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanTableBM).list();
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        String fillPerson = request.getParameter("fillPerson");
        String homeSeaArea = request.getParameter("homeSeaArea");
        String attachFiles = request.getParameter("attachFiles");
        String jingdu = request.getParameter("planHomeJingdu");
        String weidu = request.getParameter("planHomeWeidu");
        String mainTask = request.getParameter("mainTask");
        String workProgress = request.getParameter("workProgress");
        String attention = request.getParameter("attention");

//        List<CwmFile> fileModelList = UtilFactory.newArrayList();
        //先判断request中是否包涵multipart类型的数据
//        if (multipartResolver.isMultipart(request)) {
//            //再将request中的数据转化成multipart类型的数据
//            MultipartHttpServletRequest mutiRequest = (MultipartHttpServletRequest) request;
//            Iterator iterator = mutiRequest.getFileNames();
//            while (iterator.hasNext()) {
//                List<MultipartFile> fileList = mutiRequest.getFiles((String) iterator.next());
//                if (fileList.size() > 0) {
//                    for (MultipartFile multipartFile : fileList) {
////                        System.out.println(multipartFile.getName());
////                        System.out.println(multipartFile.getOriginalFilename());
//                        if (!"".equals(multipartFile.getOriginalFilename())) {
//                            CwmFile savedFile = modelFileBusiness.saveUploadFile(multipartFile, divingPlanTableBM.getId(), "", "common", "", "公开");
//                            fileModelList.add(savedFile);
////                        String imagePath = CommonTools.getPreviewImagePath();
////                        String copyPath = imagePath + File.separator + savedFile.getFinalname();
//                            String realFileStoragePath = fileServerConfig.getFtpHome() + savedFile.getFilelocation();
//                            String imageSPath = fileServerConfig.getFtpHome() + File.separator + "imageSuoluetu";
//                            if (!new File(imageSPath).exists()) {
//                                new File(imageSPath).mkdirs();
//                            }
//                            imageSPath = imageSPath + File.separator + savedFile.getFinalname();
//                            ;
////                        FileOperator.copyFile(imageFile, imageSPath);
//                            FileOperator.copyFile(realFileStoragePath, imageSPath);
//                            //生成缩略图
//                            ImageUtils.zoomImageScale(new File(imageSPath), 200);
//                        }
//                    }
//                }
//            }
//        }
        String fileJson = "";
//        if (fileModelList.size() > 0) {
//            List<Map> uploadFileList = UtilFactory.newArrayList();
//            for (CwmFile fileModel : fileModelList) {
//                Map uploadFileMap = UtilFactory.newHashMap();
//                String fileId = fileModel.getFileid();
//                String finalname = fileModel.getFinalname();
//                String fileName = fileModel.getFilename();
//                String fileType = fileModel.getFiletype();
//                uploadFileMap.put("id", fileId);
//                uploadFileMap.put("finalname", finalname);
////                uploadFileMap.put("fileName", fileName);
//                uploadFileMap.put("fileType", fileType);
//                uploadFileList.add(uploadFileMap);
//            }
//            //List集合转为json数组
//            JSONArray json = JSONArray.fromObject(uploadFileList);
//            fileJson = json.toString();
//        }
        if (!"".equals(attachFiles) && attachFiles != null) {
            List<Map> uploadFileList = UtilFactory.newArrayList();
            org.json.JSONArray jsonArray = new org.json.JSONArray(attachFiles);
            for (int i = 0; i < jsonArray.length(); i++) {
                Map uploadFileMap = UtilFactory.newHashMap();
                org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fileId = jsonObject.getString("fileId");
                String filename = jsonObject.getString("filename");
                String finalname = jsonObject.getString("finalName");
                String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
                uploadFileMap.put("id", fileId);
                uploadFileMap.put("finalname", finalname);
//                uploadFileMap.put("fileName", fileName);
                uploadFileMap.put("fileType", fileType);
                uploadFileList.add(uploadFileMap);
            }
            //List集合转为json数组
            JSONArray json = JSONArray.fromObject(uploadFileList);
            fileJson = json.toString();
        }
        Map divingPlanMap = UtilFactory.newHashMap();
        divingPlanMap = divingPlanList.get(0);
        String saveOrSubmit = CommonTools.Obj2String(divingPlanMap.get("C_SAVE_OR_SUBMIT_" + divingPlanTableBM.getId()));
        List<Map<String, List>> recordList = UtilFactory.newArrayList();
        Map recordMap = UtilFactory.newHashMap();
        if ("submit".equals(saveOrSubmit)) {
            List<Map> planList = UtilFactory.newArrayList();
            String hasSeaArea = CommonTools.Obj2String(divingPlanMap.get("C_SEA_AREA_" + divingPlanTableBM.getId()));
            String hasJingdu = CommonTools.Obj2String(divingPlanMap.get("C_JINGDU_" + divingPlanTableBM.getId()));
            String hasWeidu = CommonTools.Obj2String(divingPlanMap.get("C_WEIDU_" + divingPlanTableBM.getId()));
            String hasMainTask = CommonTools.Obj2String(divingPlanMap.get("C_MAIN_TASK_" + divingPlanTableBM.getId()));
            String hasWorkProgress = CommonTools.Obj2String(divingPlanMap.get("C_WORK_PROGRESS_" + divingPlanTableBM.getId()));
            String hasAttention = CommonTools.Obj2String(divingPlanMap.get("C_ATTENTION_" + divingPlanTableBM.getId()));
            String hasPlanDepth = CommonTools.Obj2String(divingPlanMap.get("C_PLAN_DIVING_DEPTH_" + divingPlanTableBM.getId()));
            String hasDensity = CommonTools.Obj2String(divingPlanMap.get("C_DENSITY_" + divingPlanTableBM.getId()));
            String hasFloatDepth = CommonTools.Obj2String(divingPlanMap.get("C_PLAN_FLOAT_DEPTH_" + divingPlanTableBM.getId()));
            if (!hasSeaArea.equals(homeSeaArea)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "seaArea");
                planList.add(planMap);
            }
            if (!hasJingdu.equals(jingdu)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "longtitude");
                planList.add(planMap);
            }
            if (!hasWeidu.equals(weidu)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "latitude");
                planList.add(planMap);
            }
            if (!hasMainTask.equals(mainTask)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "mainTask");
                planList.add(planMap);
            }
            if (!hasWorkProgress.equals(workProgress)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "workProgress");
                planList.add(planMap);
            }
            if (!hasAttention.equals(attention)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "attention");
                planList.add(planMap);
            }
            if (!hasPlanDepth.equals(planDivingDepth)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "selectDivingDepth");
                planList.add(planMap);
            }
            if (!hasFloatDepth.equals(planFloatDepth)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "selectPlanFloatDepth");
                planList.add(planMap);
            }
            if (!hasDensity.equals(density)) {
                Map planMap = UtilFactory.newHashMap();
                planMap.put("name", "density");
                planList.add(planMap);
            }
            recordMap.put("plan", planList);
            System.out.println(recordMap);
        }
        divingPlanMap.put("C_FILL_PERSON_" + divingPlanTableBM.getId(), fillPerson);
        divingPlanMap.put("C_SEA_AREA_" + divingPlanTableBM.getId(), homeSeaArea);
        divingPlanMap.put("C_JINGDU_" + divingPlanTableBM.getId(), jingdu);
        divingPlanMap.put("C_WEIDU_" + divingPlanTableBM.getId(), weidu);
        divingPlanMap.put("C_MAIN_TASK_" + divingPlanTableBM.getId(), mainTask);
        divingPlanMap.put("C_WORK_PROGRESS_" + divingPlanTableBM.getId(), workProgress);
        divingPlanMap.put("C_ATTENTION_" + divingPlanTableBM.getId(), attention);
        if (!"".equals(fileJson)) {
            divingPlanMap.put("C_HOME_MAP_" + divingPlanTableBM.getId(), fileJson);
        }
        divingPlanMap.put("C_PLAN_DIVING_DEPTH_" + divingPlanTableBM.getId(), planDivingDepth);
        divingPlanMap.put("C_PLAN_FLOAT_DEPTH_" + divingPlanTableBM.getId(), planFloatDepth);
        divingPlanMap.put("C_DENSITY_" + divingPlanTableBM.getId(), density);

        specialRowData = checkInsertNewDevice(specialRowData, true);
        cabinInRowData = checkInsertNewDevice(cabinInRowData, false);
        carryWorkToolBM.clearAllFilter();
        carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                        " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'"
//                    + " AND C_TABLE_FLAG_" + carryWorkToolBM.getId() + "='" + tableFlag + "'"
        );
        List<Map<String, Object>> carryToolList = orientSqlEngine.getBmService().createModelQuery(carryWorkToolBM).list();
        List<Map> carryList = UtilFactory.newArrayList();
        updateCarryTool(specialRowData, carryWorkToolBM, taskId, true, carryToolList, divingPlanId, carryList, saveOrSubmit);
        if ("submit".equals(saveOrSubmit)) {
            recordMap.put("cabinOut", carryList);
        }
        carryList = UtilFactory.newArrayList();
        updateCarryTool(cabinInRowData, carryWorkToolBM, taskId, false, carryToolList, divingPlanId, carryList, saveOrSubmit);
        if ("submit".equals(saveOrSubmit)) {
            recordMap.put("cabinIn", carryList);
        }
        if (recordMap.size() > 0) {
            Gson gson = new Gson();
            divingPlanMap.put("C_RECORD_RED_" + divingPlanTableBM.getId(), gson.toJson(recordMap));
        }
        orientSqlEngine.getBmService().updateModelData(divingPlanTableBM, divingPlanMap, divingPlanId);

        updateTotalNetWeight(totalNetWeight, carryWorkToolBM, taskId, carryToolList, divingPlanId);

//        balanceBM.setReserve_filter("AND T_DIVING__PLAN_TABLE_"+schemaId+"_ID='"+divingPlanId+"'"+"" +
//                " AND T_DIVING_TASK_"+schemaId+"_ID='"+taskId+"'");
//        List<Map> balanceList=orientSqlEngine.getBmService().createModelQuery(balanceBM).list();
//        if (balanceList!=null&&balanceList.size()>0){
//            Map balanceMap=balanceList.get(0);
//            balanceMap.put("")
//        }

//        retVal.setSuccess(true);
//        return retVal;
    }


    public void saveScientistPlanTable(HttpServletRequest request) throws Exception {
        Map<String, String> parmMap = new HashMap<String, String>();
        Enumeration<String> a = request.getParameterNames();
        String parm = null;
        String val = "";
        while (a.hasMoreElements()) {
            //参数名
            parm = a.nextElement();
            //值
            val = request.getParameter(parm);
            parmMap.put(parm, val);
        }
        String cabinOutRowData = CommonTools.Obj2String(parmMap.get("cabinOutRowData"));
        String cabinRowData = CommonTools.Obj2String(parmMap.get("cabinInRowData"));
        if (!"".equals(cabinOutRowData)) {
            String cabinOutJsonRow = checkInsertNewDevice(cabinOutRowData, true);
        }
        if (!"".equals(cabinRowData)) {
            String cabinJsonRow = checkInsertNewDevice(cabinRowData, false);
        }
        DivingPlanTable divingPlanTable = JSON.parseObject(JSON.toJSONString(parmMap), DivingPlanTable.class);
    }


    public String checkInsertNewDevice(String rowData, boolean isCabinOutOrIn) {
        IBusinessModel totalCarryToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CABIN_CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        try {
            org.json.JSONArray jsonArray = new org.json.JSONArray(rowData);
            if (jsonArray.length() > 0) {
                String isBelongOutOrIn = "";
                if (isCabinOutOrIn) {
                    isBelongOutOrIn = "cabinOut";
                } else {
                    isBelongOutOrIn = "cabinIn";
                }
                String sql = "select max(to_number(C_SERIAL_NUMBER_" + totalCarryToolBM.getId() + ")) from T_CABIN_CARRY_TOOL_" + schemaId + " where C_CABIN_INOROUT_" + totalCarryToolBM.getId() + "= '" + isBelongOutOrIn + "'";
                int maxNumber = (int) metaDaoFactory.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
                    @Override
                    public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                        preparedStatement.execute();
                        ResultSet rs = preparedStatement.getResultSet();
                        rs.next();
                        return rs.getInt(1);
                    }
                });
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String deviceId = jsonObject.getString("deviceId");
                    String carryCount = jsonObject.getString("carryCount");
                    Double airWeight = Double.parseDouble(jsonObject.getString("airWeight") == "" ? "0" : jsonObject.getString("airWeight"));

                    String connectWay = jsonObject.getString("connectWay");
                    if ("".equals(deviceId)) {
                        maxNumber = ++maxNumber;
                        String newDeviceName = jsonObject.getString("newDeviceName");


                        Map totalCarryToolMap = UtilFactory.newHashMap();
                        totalCarryToolMap.put("C_NAME_" + totalCarryToolBM.getId(), newDeviceName);
                        totalCarryToolMap.put("C_SERIAL_NUMBER_" + totalCarryToolBM.getId(), maxNumber);
                        totalCarryToolMap.put("C_CABIN_INOROUT_" + totalCarryToolBM.getId(), isBelongOutOrIn);
                        totalCarryToolMap.put("C_AIR_WEIGHT_" + totalCarryToolBM.getId(), airWeight);
                        totalCarryToolMap.put("C_CONNECT_WAY_" + totalCarryToolBM.getId(), connectWay);

                        //舱外
                        if (isCabinOutOrIn) {
                            String pWaterVolume = jsonObject.getString("pWaterVolume");
                            String freshWeight = jsonObject.getString("freshWeight");
                            String length = jsonObject.getString("length");
                            String width = jsonObject.getString("width");
                            totalCarryToolMap.put("C_DEWATER_VOLUME_" + totalCarryToolBM.getId(), Double.parseDouble(pWaterVolume == "" ? "0" : pWaterVolume));
                            totalCarryToolMap.put("C_FRESH_WATER_WEIGHT_" + totalCarryToolBM.getId(), Double.parseDouble(freshWeight == "" ? "0" : freshWeight));
                            if (!"".equals(length)) {
                                totalCarryToolMap.put("C_LENGTH_" + totalCarryToolBM.getId(), Float.parseFloat(length));
                            }
                            if (!"".equals(width)) {
                                totalCarryToolMap.put("C_WIDTH_" + totalCarryToolBM.getId(), Float.parseFloat(width));
                            }
                        }
                        String newDeviceId = orientSqlEngine.getBmService().insertModelData(totalCarryToolBM, totalCarryToolMap);
                        jsonObject.put("deviceId", newDeviceId);
                        jsonObject.remove("newDeviceName");
                    }
                }
                //JSONArray转String
                rowData = jsonArray.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rowData;
    }

    public ModelAndView getScientistDivingPlanData(HttpServletRequest request, String taskId, String divingPlanId, String attachJson) throws Exception {
        ModelAndView retVal = new ModelAndView();
        String viewName = "/app/javascript/orientjs/extjs/ScientistTaskPrepareMgr/scientistDivingPlanTable.jsp";
        OutputStream os = null;
//        response.setContentType("text/html;charset=utf-8");
        List<Map> homeMapPathList = UtilFactory.newArrayList();
        List<Map> showCarryToolList = UtilFactory.newArrayList();
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel carryWorkToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        retVal.addObject("modelId", divingPlanBM.getId());
        divingPlanBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'"
                + " AND ID='" + divingPlanId + "'"
        );
        List<Map> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).list();
        List<ScientistPlanTableBean> scientistPlanTableBeanList = UtilFactory.newArrayList();
        if (divingPlanList.size() > 0) {
            for (Map divingPlanMap : divingPlanList) {
                ScientistPlanTableBean scientistPlanTableBean = new ScientistPlanTableBean();
                scientistPlanTableBean.setFillPerson(CommonTools.Obj2String(divingPlanMap.get("C_FILL_PERSON_" + divingPlanBM.getId())));
                scientistPlanTableBean.setHomeSeaArea(CommonTools.Obj2String(divingPlanMap.get("C_SEA_AREA_" + divingPlanBM.getId())));
                scientistPlanTableBean.setPlanHomeJingdu(CommonTools.Obj2String(divingPlanMap.get("C_JINGDU_" + divingPlanBM.getId())));
                scientistPlanTableBean.setPlanHomeWeidu(CommonTools.Obj2String(divingPlanMap.get("C_WEIDU_" + divingPlanBM.getId())));
                scientistPlanTableBean.setMainTask(CommonTools.Obj2String(divingPlanMap.get("C_MAIN_TASK_" + divingPlanBM.getId())));
                scientistPlanTableBean.setWorkProgress(CommonTools.Obj2String(divingPlanMap.get("C_WORK_PROGRESS_" + divingPlanBM.getId())));
                scientistPlanTableBean.setAttention(CommonTools.Obj2String(divingPlanMap.get("C_ATTENTION_" + divingPlanBM.getId())));
                scientistPlanTableBean.setPlanDivingDepth(CommonTools.Obj2String(divingPlanMap.get("C_PLAN_DIVING_DEPTH_" + divingPlanBM.getId())));
                scientistPlanTableBean.setPlanFloatDepth(CommonTools.Obj2String(divingPlanMap.get("C_PLAN_FLOAT_DEPTH_" + divingPlanBM.getId())));
                String density = CommonTools.Obj2String(divingPlanMap.get("C_DENSITY_" + divingPlanBM.getId()));
                scientistPlanTableBean.setDensity(density);
                divingPlanBM.clearAllFilter();
                divingPlanBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'"
                        + " AND C_TABLE_STATE_" + divingPlanBM.getId() + "='当前'");
                List<Map<Object, Object>> curDivingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).list();
                scientistPlanTableBean.setIsSubmitTable(CommonTools.Obj2String(curDivingPlanList.get(0).get("C_SAVE_OR_SUBMIT_" + divingPlanBM.getId())));
                String tableFlag = CommonTools.Obj2String(divingPlanMap.get("C_TABLE_FLAG_" + divingPlanBM.getId()));
                scientistPlanTableBean.setTableFlag(CommonTools.Obj2String(divingPlanMap.get("C_TABLE_FLAG_" + divingPlanBM.getId())));
                scientistPlanTableBean.setTableState(CommonTools.Obj2String(divingPlanMap.get("C_TABLE_STATE_" + divingPlanBM.getId())));
                String basketPicFileId = CommonTools.Obj2String(divingPlanMap.get("C_PREVIEW_ID_" + divingPlanBM.getId()));
                request.setAttribute("fileId", basketPicFileId);
                if (!"".equals(basketPicFileId)) {
                    Map basketPicMap = UtilFactory.newHashMap();
                    StringBuilder basketPicSql = new StringBuilder();
                    basketPicSql.append("select * from CWM_FILE").append(" where TABLEID='").append(divingPlanBM.getId()).append("' AND FILEID='").append(basketPicFileId).append("'");
                    List<Map<String, Object>> basketPicList = jdbcTemplate.queryForList(basketPicSql.toString());
                    if (basketPicList != null && basketPicList.size() > 0) {
                        Map basketMap = basketPicList.get(0);
                        File file = new File(fileServerConfig.getFtpHome() + Obj2String(basketMap.get("FILELOCATION")));
                        if (file.exists()) {
                            String finalName = Obj2String(basketMap.get("FINALNAME"));
                            String fileSuolueImageName = finalName.substring(0, finalName.lastIndexOf(".")) + "_s." + basketMap.get("FILETYPE");
                            File fileSuoluetuFile = new File(fileServerConfig.getFtpHome() + "\\imageSuoluetu" + "\\" + fileSuolueImageName);
                            if (fileSuoluetuFile.exists()) {
                                //缩略图
                                basketPicMap.put("sltfileName", fileSuolueImageName);
                            }
                            //此处不是缩略图
                            basketPicMap.put("finalName", finalName);
                            basketPicMap.put("fileName", Obj2String(basketMap.get("FILENAME")));
                            basketPicMap.put("fileId", Obj2String(basketMap.get("FILEID")));
                            //map对象转为json对象
                            retVal.addObject("basketPicMap", com.alibaba.fastjson.JSONObject.toJSON(basketPicMap));
                        }
                    }
                }
                String homeMapFiles = CommonTools.Obj2String(divingPlanMap.get("C_HOME_MAP_" + divingPlanBM.getId()));
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
                            homeMapSql.append("select * from CWM_FILE").append(" where TABLEID='").append(divingPlanBM.getId()).append("' AND FILEID='").append(fileId).append("' AND FINALNAME='").append(finalname).append("' AND FILETYPE='").append(fileType).append("'");
                            List<Map<String, Object>> homeMapList = jdbcTemplate.queryForList(homeMapSql.toString());
                            if (homeMapList.size() > 0) {
                                for (Map homeMap : homeMapList) {
                                    Map fileMap = UtilFactory.newHashMap();
                                    File file = new File(fileServerConfig.getFtpHome() + CommonTools.Obj2String(homeMap.get("FILELOCATION")));
                                    if (file.exists()) {
                                        String filePath = CommonTools.Obj2String(homeMap.get("FILELOCATION"));
//                                    String encodeImage64 = Base64Utils.encodeFile(filePath);
                                        String fileRealName = StringUtils.substringBeforeLast(Obj2String(homeMap.get("FINALNAME")), ".") + "." + StringUtils.substringAfterLast(Obj2String(homeMap.get("FILELOCATION")), ".");
                                        fileMap.put("fileName", CommonTools.Obj2String(homeMap.get("FILENAME")));
                                        fileMap.put("filePath", filePath);
                                        fileMap.put("fileId", CommonTools.Obj2String(homeMap.get("FILEID")));
                                        //设置缩略图名称
//                                        String sFilePath = StringUtils.substringBeforeLast(CommonTools.Obj2String(homeMap.get("FINALNAME")), ".") + "_s." + StringUtils.substringAfterLast(CommonTools.Obj2String(homeMap.get("FILELOCATION")), ".");
//                                        fileMap.put("sltFilePath", sFilePath);
                                        fileMap.put("tableFlag", tableFlag);
                                        homeMapPathList.add(fileMap);
                                    }
                                }
                            }
                        }
                    }
                }
                scientistPlanTableBeanList.add(scientistPlanTableBean);
                carryWorkToolBM.clearAllFilter();
                carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                        " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'" +
                        " AND C_TOTAL_STATE_" + carryWorkToolBM.getId() + " is null");
                List<Map> carryToolList = orientSqlEngine.getBmService().createModelQuery(carryWorkToolBM).orderAsc("C_ROW_NUMBER_" + carryWorkToolBM.getId()).list();
                if (carryToolList.size() > 0) {
                    for (Map carryMap : carryToolList) {
                        String deviceId = CommonTools.Obj2String(carryMap.get("C_DEVICE_ID_" + carryWorkToolBM.getId()));
                        String rowNumber = CommonTools.Obj2String(carryMap.get("C_ROW_NUMBER_" + carryWorkToolBM.getId()));
                        String carryCount = CommonTools.Obj2String(carryMap.get("C_CARRY_COUNT_" + carryWorkToolBM.getId()));
                        String isCabinOutOrIn = Obj2String(carryMap.get("C_CABIN_OUTORIN_" + carryWorkToolBM.getId()));
                        String connectWay = CommonTools.Obj2String(carryMap.get("C_CONNECT_WAY_" + carryWorkToolBM.getId()));
                        String airWeight = CommonTools.Obj2String(carryMap.get("C_AIR_WEIGHT_" + carryWorkToolBM.getId()));
                        String pWaterVolume = CommonTools.Obj2String(carryMap.get("C_PW_VOLUMN_" + carryWorkToolBM.getId()));
                        String freshWaterWeight = CommonTools.Obj2String(carryMap.get("C_FRESH_WATER_" + carryWorkToolBM.getId()));
                        Map showCarryMap = UtilFactory.newHashMap();
                        showCarryMap.put("deviceId", deviceId);
                        showCarryMap.put("carryCount", carryCount);
                        showCarryMap.put("rowNumber", rowNumber);
                        showCarryMap.put("isCabinOutOrIn", isCabinOutOrIn);
                        showCarryMap.put("connectWay", connectWay);
                        showCarryMap.put("tableFlag", tableFlag);
                        showCarryMap.put("airWeight", airWeight);
                        showCarryMap.put("pWaterVolume", pWaterVolume);
                        showCarryMap.put("freshWaterWeight", freshWaterWeight);
                        showCarryToolList.add(showCarryMap);
                    }
                    //list集合转为json数组
                    JSONArray showCarryToolMapJson = JSONArray.fromObject(showCarryToolList);
                    retVal.addObject("showCarryToolList", showCarryToolMapJson.toString());
                }
            }
            if (homeMapPathList != null) {
                //list集合转为json数组
                JSONArray homeMapJson = JSONArray.fromObject(homeMapPathList);
                retVal.addObject("homeMapJson", homeMapJson.toString());
            }
            //list集合转为json数组
            JSONArray scientistMapJson = JSONArray.fromObject(scientistPlanTableBeanList);
//            System.out.println(scientistMapJson);
//            String scientistMapJsonToString=scientistMapJson.toString().replace("\r\n","\\n");
            retVal.addObject("scientistPlanTableBeanList", scientistMapJson.toString());
            retVal.addObject("attachJson", attachJson);
        }

        retVal.setViewName(viewName);
        return retVal;
    }

    public List<Map> querDivingTaskList() {
        List taskList = new ArrayList<>();
        IBusinessModel divingTaskModel = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanModel = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        hangduanModel.setReserve_filter("AND C_IS_START_" + hangduanModel.getId() + "='" + "1" + "'");
        List<Map> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanModel).list();
        StringBuilder hangduanIdBuilder = new StringBuilder();
        if (hangduanList.size() > 0) {
            for (Map hangduanMap : hangduanList) {
                String hangduanId = hangduanMap.get("ID").toString();
                hangduanIdBuilder.append(hangduanId).append(",");
            }
            String hangduanIds = hangduanIdBuilder.toString();
            hangduanIds = hangduanIds.substring(0, hangduanIds.length() - 1);
            String sql = "select * from T_DIVING_TASK_" + schemaId + " where T_HANGDUAN_" + schemaId + "_ID IN (" + hangduanIds + ") order by C_TASK_NAME_" + divingTaskModel.getId() + " asc";
            List<Map<String, Object>> divingTaskList = jdbcTemplate.queryForList(sql);

            if (divingTaskList.size() > 0) {
                for (Map taskMap : divingTaskList) {
                    Map divingTaskMap = new HashMap<>();
                    divingTaskMap.put("id", taskMap.get("ID"));
                    divingTaskMap.put("taskName", taskMap.get("C_TASK_NAME_" + divingTaskModel.getId()));
                    divingTaskMap.put("hangduanId", taskMap.get("T_HANGDUAN_" + schemaId + "_ID"));
                    taskList.add(divingTaskMap);
                }
            }
        }
        return taskList;
    }

    public void informMap(IBusinessModel informLogTableBM, String taskId, String taskName, User curUser, Map informLogMap) {
        informLogMap.put("C_TASK_ID_" + informLogTableBM.getId(), taskId);
        informLogMap.put("C_TASK_NAME_" + informLogTableBM.getId(), taskName);
        if (curUser != null) {
            informLogMap.put("C_UPLOAD_PERSON_" + informLogTableBM.getId(), curUser.getAllName());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newSubmitTime = simpleDateFormat.format(new Date());
        informLogMap.put("C_UPLOAD_TIME_" + informLogTableBM.getId(), newSubmitTime);
    }

    public void totalCarryToolNetWeight(String totalNetWeight, IBusinessModel carryWorkToolBM, String taskId, String divingPlanId) {
        org.json.JSONArray jsonArray = null;
        try {
            jsonArray = new org.json.JSONArray(totalNetWeight);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String isTotalCabinOutOrIn = jsonObject.getString("isTotalCabinOutOrIn");
                    String allNetWeight = jsonObject.getString("allNetWeight");
                    Map carryToolMap = UtilFactory.newHashMap();
                    carryToolMap.put("C_NET_WEIGHT_" + carryWorkToolBM.getId(), allNetWeight);
                    carryToolMap.put("C_TOTAL_STATE_" + carryWorkToolBM.getId(), "total");
                    if ("in".equals(isTotalCabinOutOrIn)) {
                        carryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "in");
                    } else if ("out".equals(isTotalCabinOutOrIn)) {
                        String pWaterAllVolume = jsonObject.getString("pWaterAllVolume");
                        String airAllWeight = jsonObject.getString("airAllWeight");
                        carryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "out");
                        carryToolMap.put("C_PW_VOLUMN_" + carryWorkToolBM.getId(), pWaterAllVolume);
                        carryToolMap.put("C_AIR_WEIGHT_" + carryWorkToolBM.getId(), airAllWeight);
                    }
                    carryToolMap.put("C_DIVING_PLAN_ID_" + carryWorkToolBM.getId(), divingPlanId);
                    carryToolMap.put("T_DIVING__PLAN_TABLE_" + schemaId + "_ID", divingPlanId);
                    carryToolMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
                    orientSqlEngine.getBmService().insertModelData(carryWorkToolBM, carryToolMap);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateTotalNetWeight(String totalNetWeight, IBusinessModel carryWorkToolBM, String taskId, List<Map<String, Object>> carryToolList, String divingPlanId) {
        org.json.JSONArray jsonArray = null;
        try {
            jsonArray = new org.json.JSONArray(totalNetWeight);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String isTotalCabinOutOrIn = jsonObject.getString("isTotalCabinOutOrIn");
                    String allNetWeight = jsonObject.getString("allNetWeight");
                    int record = 0;
                    if (carryToolList.size() > 0) {
                        for (Map carryToolMap : carryToolList) {
                            String carryToolId = Obj2String(carryToolMap.get("ID"));
                            String totalState = CommonTools.Obj2String(carryToolMap.get("C_TOTAL_STATE_" + carryWorkToolBM.getId()));
                            String cabinOutOrIn = CommonTools.Obj2String(carryToolMap.get("C_CABIN_OUTORIN_" + carryWorkToolBM.getId()));
                            if (isTotalCabinOutOrIn.equals(cabinOutOrIn) && "total".equals(totalState)) {
                                if ("in".equals(cabinOutOrIn)) {
                                    String sql = "update T_CARRY_TOOL_" + schemaId + " set C_NET_WEIGHT_" + carryWorkToolBM.getId() + "=?,C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "=?,T_DIVING__PLAN_TABLE_" + schemaId + "_ID=?" + " where id=?";
                                    jdbcTemplate.update(sql, allNetWeight, divingPlanId, divingPlanId, carryToolId);
                                    record++;
                                } else {
                                    String pWaterAllVolume = jsonObject.getString("pWaterAllVolume");
                                    String airAllWeight = jsonObject.getString("airAllWeight");
                                    String sql = "update T_CARRY_TOOL_" + schemaId + " set C_AIR_WEIGHT_" + carryWorkToolBM.getId() + "=?,C_PW_VOLUMN_" + carryWorkToolBM.getId() + "=?,C_NET_WEIGHT_" + carryWorkToolBM.getId() + "=?,C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "=?,T_DIVING__PLAN_TABLE_" + schemaId + "_ID=?" + " where id=?";
                                    jdbcTemplate.update(sql, airAllWeight, pWaterAllVolume, allNetWeight, divingPlanId, divingPlanId, carryToolId);
                                    record++;
                                }
                                break;
                            }
                        }
                    }
                    if (record == 0) {
                        Map insertCarryToolMap = UtilFactory.newHashMap();
                        insertCarryToolMap.put("C_NET_WEIGHT_" + carryWorkToolBM.getId(), allNetWeight);
                        insertCarryToolMap.put("C_TOTAL_STATE_" + carryWorkToolBM.getId(), "total");
                        if ("in".equals(isTotalCabinOutOrIn)) {
                            insertCarryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "in");
                        } else if ("out".equals(isTotalCabinOutOrIn)) {
                            String pWaterAllVolume = jsonObject.getString("pWaterAllVolume");
                            String airAllWeight = jsonObject.getString("airAllWeight");
                            insertCarryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "out");
                            insertCarryToolMap.put("C_PW_VOLUMN_" + carryWorkToolBM.getId(), pWaterAllVolume);
                            insertCarryToolMap.put("C_AIR_WEIGHT_" + carryWorkToolBM.getId(), airAllWeight);
                        }
//                        insertCarryToolMap.put("C_TYPE_" + carryWorkToolBM.getId(), scientist);
//                        insertCarryToolMap.put("C_TABLE_FLAG_" + carryWorkToolBM.getId(), tableFlag);
                        insertCarryToolMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
                        insertCarryToolMap.put("C_DIVING_PLAN_ID_" + carryWorkToolBM.getId(), divingPlanId);
                        insertCarryToolMap.put("T_DIVING__PLAN_TABLE_" + schemaId + "_ID", divingPlanId);
                        orientSqlEngine.getBmService().insertModelData(carryWorkToolBM, insertCarryToolMap);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void carryToolMap(String rowData, IBusinessModel carryWorkToolBM, String taskId, boolean isCabinOutOrIn, String divingPlanId) {
        org.json.JSONArray jsonArray = null;
        try {
            jsonArray = new org.json.JSONArray(rowData);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String deviceId = jsonObject.getString("deviceId");
                    String rowNumber = jsonObject.getString("rowNumber");
                    String carryCount = jsonObject.getString("carryCount");
                    String pWaterVolume = "";
                    String freshWeight = "";
                    Map carryToolMap = UtilFactory.newHashMap();
                    //舱外
                    if (isCabinOutOrIn) {
                        pWaterVolume = jsonObject.getString("pWaterVolume");
                        freshWeight = jsonObject.getString("freshWeight");
                        carryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "out");
                    } else {
                        //舱内
                        carryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "in");
                    }
                    String airWeight = jsonObject.getString("airWeight");
                    String connectWay = jsonObject.getString("connectWay");
                    carryToolMap.put("C_DEVICE_ID_" + carryWorkToolBM.getId(), deviceId);
                    carryToolMap.put("C_ROW_NUMBER_" + carryWorkToolBM.getId(), rowNumber);
                    carryToolMap.put("C_CARRY_COUNT_" + carryWorkToolBM.getId(), carryCount);
                    carryToolMap.put("C_AIR_WEIGHT_" + carryWorkToolBM.getId(), airWeight);
                    carryToolMap.put("C_PW_VOLUMN_" + carryWorkToolBM.getId(), pWaterVolume);
                    carryToolMap.put("C_FRESH_WATER_" + carryWorkToolBM.getId(), freshWeight);
                    carryToolMap.put("C_CONNECT_WAY_" + carryWorkToolBM.getId(), connectWay);
                    carryToolMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
//                    carryToolMap.put("C_TYPE_" + carryWorkToolBM.getId(), scientist);
//                    carryToolMap.put("C_TABLE_FLAG_" + carryWorkToolBM.getId(), tableFlag);
                    carryToolMap.put("C_DIVING_PLAN_ID_" + carryWorkToolBM.getId(), divingPlanId);
                    orientSqlEngine.getBmService().insertModelData(carryWorkToolBM, carryToolMap);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateCarryTool(String rowData, IBusinessModel carryWorkToolBM, String taskId, boolean isCabinOutOrIn, List<Map<String, Object>> carryToolList, String divingPlanId, List<Map> carrytList, String saveOrSubmit) {
        org.json.JSONArray jsonArray = null;
        try {
            jsonArray = new org.json.JSONArray(rowData);
            StringBuilder deviceIds = new StringBuilder();
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String deviceId = jsonObject.getString("deviceId");
                    String rowNumber = jsonObject.getString("rowNumber");
                    String carryCount = jsonObject.getString("carryCount");
                    String airWeight = jsonObject.getString("airWeight");
                    String pWaterVolume = "";
                    String freshWeight = "";
                    //舱外
                    if (isCabinOutOrIn) {
                        pWaterVolume = jsonObject.getString("pWaterVolume");
                        freshWeight = jsonObject.getString("freshWeight");
                    }
                    String connectWay = jsonObject.getString("connectWay");
                    deviceIds.append(deviceId).append(",");
                    Map insertCarryToolMap = UtilFactory.newHashMap();
                    int isEqual = 0;
                    if (carryToolList.size() > 0) {
                        for (Map carryToolMap : carryToolList) {
                            String carryToolId = Obj2String(carryToolMap.get("ID"));
                            String carryDeviceId = CommonTools.Obj2String(carryToolMap.get("C_DEVICE_ID_" + carryWorkToolBM.getId()));
                            String hasAirWeight = CommonTools.Obj2String(carryToolMap.get("C_AIR_WEIGHT_" + carryWorkToolBM.getId()));
                            String hasPWvolumn = CommonTools.Obj2String(carryToolMap.get("C_PW_VOLUMN_" + carryWorkToolBM.getId()));
                            String hasFreshWater = CommonTools.Obj2String(carryToolMap.get("C_FRESH_WATER_" + carryWorkToolBM.getId()));
                            String hasConnectWay = CommonTools.Obj2String(carryToolMap.get("C_CONNECT_WAY_" + carryWorkToolBM.getId()));
                            String hasCarryCount = CommonTools.Obj2String(carryToolMap.get("C_CARRY_COUNT_" + carryWorkToolBM.getId()));
                            if (deviceId.equals(carryDeviceId)) {
                                carryToolMap.put("C_ROW_NUMBER_" + carryWorkToolBM.getId(), rowNumber);
                                carryToolMap.put("C_CARRY_COUNT_" + carryWorkToolBM.getId(), carryCount);
                                carryToolMap.put("C_AIR_WEIGHT_" + carryWorkToolBM.getId(), airWeight);
                                carryToolMap.put("C_PW_VOLUMN_" + carryWorkToolBM.getId(), pWaterVolume);
                                carryToolMap.put("C_FRESH_WATER_" + carryWorkToolBM.getId(), freshWeight);
                                carryToolMap.put("C_CONNECT_WAY_" + carryWorkToolBM.getId(), connectWay);
                                if (isCabinOutOrIn) {
                                    carryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "out");
                                    if ("submit".equals(saveOrSubmit)) {
                                        if (!hasAirWeight.equals(airWeight) || !hasCarryCount.equals(carryCount) || !hasPWvolumn.equals(pWaterVolume) || !hasFreshWater.equals(freshWeight) || !hasConnectWay.equals(connectWay)) {
                                            Map carryoutMap = UtilFactory.newHashMap();
                                            carryoutMap.put("deviceId", deviceId);
                                            carrytList.add(carryoutMap);
                                        }
                                    }
                                } else {
                                    carryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "in");
                                    if ("submit".equals(saveOrSubmit)) {
                                        if (!hasAirWeight.equals(airWeight) || !hasCarryCount.equals(carryCount) || !hasPWvolumn.equals(pWaterVolume) || !hasFreshWater.equals(freshWeight) || !hasConnectWay.equals(connectWay)) {
                                            Map carrrInMap = UtilFactory.newHashMap();
                                            carrrInMap.put("deviceId", deviceId);
                                            carrytList.add(carrrInMap);
                                        }
                                    }
                                }
                                String sql = "update T_CARRY_TOOL_" + schemaId + " set C_ROW_NUMBER_" + carryWorkToolBM.getId() + "=?,C_CARRY_COUNT_" + carryWorkToolBM.getId() + "=?,C_AIR_WEIGHT_" + carryWorkToolBM.getId() + "=?,C_PW_VOLUMN_" + carryWorkToolBM.getId() + "=?,C_FRESH_WATER_" + carryWorkToolBM.getId() + "=?,C_CONNECT_WAY_" + carryWorkToolBM.getId() + "=?,C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "=?,C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "=? where id=?";
                                if (isCabinOutOrIn) {
                                    //舱外
                                    jdbcTemplate.update(sql, rowNumber, carryCount, airWeight, pWaterVolume, freshWeight, connectWay, "out", divingPlanId, carryToolId);
                                } else {
                                    jdbcTemplate.update(sql, rowNumber, carryCount, airWeight, pWaterVolume, freshWeight, connectWay, "in", divingPlanId, carryToolId);
                                }

//                             orientSqlEngine.getBmService().updateModelData(divingPlanTableBM, carryToolMap, carryToolId);
                                isEqual++;
                                break;
                            }
                        }
                    }
                    if (isEqual == 0) {
                        insertCarryToolMap.put("C_DEVICE_ID_" + carryWorkToolBM.getId(), deviceId);
                        insertCarryToolMap.put("C_ROW_NUMBER_" + carryWorkToolBM.getId(), rowNumber);
                        insertCarryToolMap.put("C_CARRY_COUNT_" + carryWorkToolBM.getId(), carryCount);
                        insertCarryToolMap.put("C_AIR_WEIGHT_" + carryWorkToolBM.getId(), airWeight);
                        insertCarryToolMap.put("C_PW_VOLUMN_" + carryWorkToolBM.getId(), pWaterVolume);
                        insertCarryToolMap.put("C_FRESH_WATER_" + carryWorkToolBM.getId(), freshWeight);
                        insertCarryToolMap.put("C_CONNECT_WAY_" + carryWorkToolBM.getId(), connectWay);
                        //舱外
                        if (isCabinOutOrIn) {
                            insertCarryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "out");
                            if ("submit".equals(saveOrSubmit)) {
                                Map carryoutMap = UtilFactory.newHashMap();
                                carryoutMap.put("deviceId", deviceId);
                                carrytList.add(carryoutMap);
                            }
                        } else {
                            //舱内
                            insertCarryToolMap.put("C_CABIN_OUTORIN_" + carryWorkToolBM.getId(), "in");
                            if ("submit".equals(saveOrSubmit)) {
                                Map carrrInMap = UtilFactory.newHashMap();
                                carrrInMap.put("deviceId", deviceId);
                                carrytList.add(carrrInMap);
                            }
                        }
                        insertCarryToolMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
//                        insertCarryToolMap.put("C_TYPE_" + carryWorkToolBM.getId(), scientist);
//                        insertCarryToolMap.put("C_TABLE_FLAG_" + carryWorkToolBM.getId(), tableFlag);
                        insertCarryToolMap.put("C_DIVING_PLAN_ID_" + carryWorkToolBM.getId(), divingPlanId);
                        insertCarryToolMap.put("T_DIVING__PLAN_TABLE_" + schemaId + "_ID", divingPlanId);
                        orientSqlEngine.getBmService().insertModelData(carryWorkToolBM, insertCarryToolMap);
                    }
                }
                //查找数据库中需要删除的设备
                String hasDeviceIds = deviceIds.toString();
                hasDeviceIds = hasDeviceIds.substring(0, hasDeviceIds.length() - 1);
                if (isCabinOutOrIn) {
                    //舱外
                    carryWorkToolBM.clearAllFilter();
//                    if ("scientist".equals(scientist)) {
                    carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                            " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'" +
//                                " AND C_TABLE_FLAG_" + carryWorkToolBM.getId() + "='" + tableFlag + "'" +
                            " AND C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "!='" + "in" + "'" + " AND C_TOTAL_STATE_" + carryWorkToolBM.getId() + " is null" +
                            " AND C_DEVICE_ID_" + carryWorkToolBM.getId() + " not in (" + hasDeviceIds + ")");
//                    } else {
                    //总表
//                        carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
//                                " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'" +
//                                " AND C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "!='" + "in" + "'" + " AND C_TOTAL_STATE_" + carryWorkToolBM.getId() + " is null" +
//                                " AND C_DEVICE_ID_" + carryWorkToolBM.getId() + " not in (" + hasDeviceIds + ")");
//                    }
                } else {
//                    if ("scientist".equals(scientist)) {
//                        //科学家舱内
//                        carryWorkToolBM.clearAllFilter();
//                        carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
//                                " AND C_TYPE_" + carryWorkToolBM.getId() + "='" + "scientist" + "'" +
//                                " AND C_TABLE_FLAG_" + carryWorkToolBM.getId() + "='" + tableFlag + "'" +
//                                " AND C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "='" + "in" + "'" + " AND C_TOTAL_STATE_" + carryWorkToolBM.getId() + " is null" +
//                                " AND C_DEVICE_ID_" + carryWorkToolBM.getId() + " not in (" + hasDeviceIds + ")");
//                    } else {
                    //总表舱内
                    carryWorkToolBM.clearAllFilter();
                    carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                            " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'" +
                            " AND C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "='" + "in" + "'" + " AND C_TOTAL_STATE_" + carryWorkToolBM.getId() + " is null" +
                            " AND C_DEVICE_ID_" + carryWorkToolBM.getId() + " not in (" + hasDeviceIds + ")");
//                    }
                }
            } else {
                //舱外
                if (isCabinOutOrIn) {
//                    if ("scientist".equals(scientist)) {
//                        carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
//                                " AND C_TYPE_" + carryWorkToolBM.getId() + "='" + "scientist" + "'" +
//                                " AND C_TABLE_FLAG_" + carryWorkToolBM.getId() + "='" + tableFlag + "'" +
//                                " AND C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "!='" + "in" + "'");
//                    } else {
                    carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                            " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'" +
                            " AND C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "!='" + "in" + "'");
//                    }
                } else {
//                    if ("scientist".equals(scientist)) {
//                        //舱内
//                        carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
//                                " AND C_TYPE_" + carryWorkToolBM.getId() + "='" + "scientist" + "'" +
//                                " AND C_TABLE_FLAG_" + carryWorkToolBM.getId() + "='" + tableFlag + "'" +
//                                " AND C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "='" + "in" + "'");
//                    } else {
                    //舱内
                    carryWorkToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                            " AND C_DIVING_PLAN_ID_" + carryWorkToolBM.getId() + "='" + divingPlanId + "'" +
                            " AND C_CABIN_OUTORIN_" + carryWorkToolBM.getId() + "='" + "in" + "'");
//                    }
                }
            }
            carryToolList = orientSqlEngine.getBmService().createModelQuery(carryWorkToolBM).list();
            if (carryToolList.size() > 0) {
                for (Map carryMap : carryToolList) {
                    String deleteCarryToolId = Obj2String(carryMap.get("ID"));
                    orientSqlEngine.getBmService().delete(carryWorkToolBM, deleteCarryToolId);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public AjaxResponseData agreeScientistPlanTable(String taskId, String divingPlanId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel peizhongBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel informLogTableBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_LOG, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel depthBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTH_DESITY, schemaId, EnumInter.BusinessModelEnum.Table);

        User curUser = UserContextUtil.getCurrentUser();
        Map divingTaskMap = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).findById(taskId);
        String taskState = CommonTools.Obj2String(divingTaskMap.get("C_STATE_" + divingTaskBM.getId()));
        String taskName = CommonTools.Obj2String(divingTaskMap.get("C_TASK_NAME_" + divingTaskBM.getId()));

        peizhongBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
        List<Map<String, Object>> peizhongList = orientSqlEngine.getBmService().createModelQuery(peizhongBM).list();

        divingPlanBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
        List<Map<String, Object>> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).list();
        Map preCurrentPlanMap = UtilFactory.newHashMap();
        Map currentPlanMap = UtilFactory.newHashMap();
        if (divingPlanList.size() > 0) {
            for (Map divingPlanMap : divingPlanList) {
                String planId = divingPlanMap.get("ID").toString();
                String tableState = CommonTools.Obj2String(divingPlanMap.get("C_TABLE_STATE_" + divingPlanBM.getId()));
                if (planId.equals(divingPlanId)) {
                    //科学家数据提交到总表
                    divingPlanMap.put("C_TABLE_STATE_" + divingPlanBM.getId(), "当前");
                    currentPlanMap = divingPlanMap;
                    if (!"已结束".equals(taskState)) {
                        Map informLogMap = UtilFactory.newHashMap();
                        informLogMap.put("C_STATE_" + informLogTableBM.getId(), "提交");
                        informLogMap.put("C_TYPE_" + informLogTableBM.getId(), "scientistPlan");
                        informLogMap.put("C_TABLE_NAME_" + informLogTableBM.getId(), "科学家下潜计划表");
                        informMap(informLogTableBM, taskId, taskName, curUser, informLogMap);
                        informLogMap.put("C_TABLE_ID_" + informLogTableBM.getId(), planId);
                        orientSqlEngine.getBmService().insertModelData(informLogTableBM, informLogMap);
                    }
                    divingTaskMap.put("C_SEA_AREA_" + divingTaskBM.getId(), Obj2String(divingPlanMap.get("C_SEA_AREA_" + divingPlanBM.getId())));
                    divingTaskMap.put("C_JINGDU_" + divingTaskBM.getId(), Obj2String(divingPlanMap.get("C_JINGDU_" + divingPlanBM.getId())));
                    divingTaskMap.put("C_WEIDU_" + divingTaskBM.getId(), Obj2String(divingPlanMap.get("C_WEIDU_" + divingPlanBM.getId())));
                    String depthId = CommonTools.Obj2String(divingPlanMap.get("C_PLAN_DIVING_DEPTH_" + divingPlanBM.getId()));
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
                } else {
                    //修改表格状态，防止出现多条当前科学家下潜计划数据
                    divingPlanMap.put("C_TABLE_STATE_" + divingPlanBM.getId(), "备用");
                    if ("当前".equals(tableState)) {
                        preCurrentPlanMap = divingPlanMap;
                    }
                }
                orientSqlEngine.getBmService().updateModelData(divingPlanBM, divingPlanMap, planId);
            }
            //合并上一条当前计划数据和当前计划数据
            if (!preCurrentPlanMap.isEmpty() && !currentPlanMap.isEmpty()) {
                preCurrentPlanMap.remove("id");
                preCurrentPlanMap.remove("C_TABLE_STATE_" + divingPlanBM.getId());
//                preCurrentPlanMap.remove("C_SAVE_OR_SUBMIT_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_HOME_MAP_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_FILL_PERSON_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_SEA_AREA_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_JINGDU_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_WEIDU_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_PLAN_DIVING_DEPTH_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_PLAN_FLOAT_DEPTH_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_ATTENTION_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("T_DIVING_TASK_" + schemaId + "_ID");
                preCurrentPlanMap.remove("C_DENSITY_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_MAIN_TASK_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_WORK_PROGRESS_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_SERIAL_NUMBER_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_RECORD_RED_" + divingPlanBM.getId());
                preCurrentPlanMap.remove("C_PREVIEW_ID_" + divingPlanBM.getId());
                currentPlanMap.remove("C_SAVE_OR_SUBMIT_" + divingPlanBM.getId());
                Map combineMap = UtilFactory.newHashMap();
                combineMap.putAll(currentPlanMap);
                combineMap.putAll(preCurrentPlanMap);
                String currentPlanId = combineMap.get("ID").toString();
                orientSqlEngine.getBmService().updateModelData(divingPlanBM, combineMap, currentPlanId);
            }

            if (peizhongList.size() > 0) {
                for (Map peizhongMap : peizhongList) {
                    String peizhongId = peizhongMap.get("ID").toString();
                    String refDivingPlanId = CommonTools.Obj2String(peizhongMap.get("T_DIVING__PLAN_TABLE_" + schemaId + "_ID"));
                    if (divingPlanId.equals(refDivingPlanId)) {
                        peizhongMap.put("C_TABLE_STATE_" + peizhongBM.getId(), "当前");
                    } else {
                        //修改表格状态，防止出现多条当前配重数据
                        peizhongMap.put("C_TABLE_STATE_" + peizhongBM.getId(), "备用");
                    }
                    orientSqlEngine.getBmService().updateModelData(peizhongBM, peizhongMap, peizhongId);
                }
            }
            retVal.setSuccess(true);
            retVal.setMsg("提交成功!");
        } else {
            retVal.setSuccess(false);
            retVal.setMsg("表格还未保存!");
        }
        return retVal;
    }

    public String easyAddDivingPlan(String taskId, String modelId) {
        String msg = "快速新增成功";
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        //配重
        IBusinessModel balanceBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        divingPlanBM.setReserve_filter("AND T_DIVING_TASK_"+PropertyConstant.WEI_BAO_SCHEMA_ID+"_ID='"+taskId+"'");
        long count = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).count();
        Map divingPlanMap = UtilFactory.newHashMap();
        Map peizhongMap = UtilFactory.newHashMap();
        divingPlanMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
        peizhongMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
        if (count == 0) {
            divingPlanMap.put("C_SERIAL_NUMBER_" + divingPlanBM.getId(), "1");
            divingPlanMap.put("C_TABLE_STATE_" + divingPlanBM.getId(), "当前");
            peizhongMap.put("C_SERIAL_NUMBER_" + balanceBM.getId(), "1");
            peizhongMap.put("C_TABLE_STATE_" + balanceBM.getId(), "当前");
        } else {
            String sql = "select max(to_number(C_SERIAL_NUMBER_" + modelId + ")) from T_DIVING__PLAN_TABLE_" + schemaId + " where T_DIVING_TASK_" + schemaId + "_ID = '" + taskId + "'";
            int maxNumber = (int) metaDaoFactory.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
                @Override
                public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                    preparedStatement.execute();
                    ResultSet rs = preparedStatement.getResultSet();
                    rs.next();
                    return rs.getInt(1);
                }
            });
            maxNumber = ++maxNumber;
            divingPlanMap.put("C_SERIAL_NUMBER_" + divingPlanBM.getId(), maxNumber);
            divingPlanMap.put("C_TABLE_STATE_" + divingPlanBM.getId(), "备用");
            peizhongMap.put("C_SERIAL_NUMBER_" + balanceBM.getId(), maxNumber);
            peizhongMap.put("C_TABLE_STATE_" + balanceBM.getId(), "备用");
        }
        String divingPlanId = orientSqlEngine.getBmService().insertModelData(divingPlanBM, divingPlanMap);
        peizhongMap.put("T_DIVING__PLAN_TABLE_" + schemaId + "_ID", divingPlanId);
        orientSqlEngine.getBmService().insertModelData(balanceBM, peizhongMap);
        return msg;
    }

    public String easyCopyDivingPlan(String taskId, String divingPlanId) {
        String msg = "快速复制成功";
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        //配重
        IBusinessModel balanceBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel carryToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);

        String sql = "select max(to_number(C_SERIAL_NUMBER_" + divingPlanBM.getId() + ")) from T_DIVING__PLAN_TABLE_" + schemaId + " where T_DIVING_TASK_" + schemaId + "_ID = '" + taskId + "'";
        int maxNumber = (int) metaDaoFactory.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
        });
        maxNumber = ++maxNumber;
        divingPlanBM.setReserve_filter(" AND ID='" + divingPlanId + "' AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
        List<Map<String, Object>> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).list();
        if (divingPlanList != null && divingPlanList.size() > 0) {
            Map divingPlanMap = divingPlanList.get(0);
            divingPlanMap.remove("ID");
            divingPlanMap.remove("C_SAVE_OR_SUBMIT_" + divingPlanBM.getId());
            divingPlanMap.remove("C_RECORD_RED_" + divingPlanBM.getId());
            divingPlanMap.put("C_SERIAL_NUMBER_" + divingPlanBM.getId(), maxNumber);
            divingPlanMap.put("C_TABLE_STATE_" + divingPlanBM.getId(), "备用");
            String newDivingPlanId = orientSqlEngine.getBmService().insertModelData(divingPlanBM, divingPlanMap);

            Map peizhongMap = UtilFactory.newHashMap();
            peizhongMap.put("C_SERIAL_NUMBER_" + balanceBM.getId(), maxNumber);
            peizhongMap.put("C_TABLE_STATE_" + balanceBM.getId(), "备用");
            peizhongMap.put("T_DIVING__PLAN_TABLE_" + schemaId + "_ID", newDivingPlanId);
            peizhongMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            peizhongMap.put("C_PLAN_FLOAT_DEPTH_" + balanceBM.getId(), CommonTools.Obj2String(divingPlanMap.get("C_PLAN_FLOAT_DEPTH_" + divingPlanBM.getId())));
            orientSqlEngine.getBmService().insertModelData(balanceBM, peizhongMap);

            carryToolBM.setReserve_filter("AND C_DIVING_PLAN_ID_" + carryToolBM.getId() + "='" + divingPlanId + "' AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
            List<Map<String, Object>> carryToolList = orientSqlEngine.getBmService().createModelQuery(carryToolBM).list();
            if (carryToolList != null && carryToolList.size() > 0) {
                for (Map carryToolMap : carryToolList) {
                    carryToolMap.remove("ID");
                    carryToolMap.put("C_DIVING_PLAN_ID_" + carryToolBM.getId(), newDivingPlanId);
                    carryToolMap.put("T_DIVING__PLAN_TABLE_" + schemaId + "_ID", newDivingPlanId);
                    orientSqlEngine.getBmService().insertModelData(carryToolBM, carryToolMap);
                }
            }
        }
        return msg;
    }

    public ExtGridData<Map> getScientistPlanData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort, String taskName) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        if (!org.apache.commons.lang.StringUtils.isEmpty(customerFilter)) {
            Map clazzMap = new HashMap();
            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, clazzMap);
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
        }
        List<Map> dataList = businessModelQuery.list();
        for (Map divingPlanMap : dataList) {
            String serialNumber = CommonTools.Obj2String(divingPlanMap.get("C_SERIAL_NUMBER_" + businessModel.getId()));
            divingPlanMap.put("C_SERIAL_NUMBER_" + orientModelId, taskName + "_" + serialNumber);
        }
        if (dataChange) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            modelDataBusiness.customDataChange(businessModel, dataList);
        }
        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    public StringBuilder generateBalanceHtml(String hangduanId, HttpServletRequest request) throws JSONException {
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel balanceBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel seaAreaBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTHDESITY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel planCarryToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CARRY_TOOL, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);

        List<Map> seaAreaList = orientSqlEngine.getBmService().createModelQuery(seaAreaBM).list();

        divingTaskBM.setReserve_filter("AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'");

        List<Map> divingTaskList = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).orderAsc("C_TASK_NAME_" + divingTaskBM.getId()).list();

        StringBuilder html = new StringBuilder();

        if (divingTaskList != null && divingTaskList.size() > 0) {
            StringBuffer stringBufferTaskId = new StringBuffer();
            StringBuffer stringBufferTaskName = new StringBuffer();
            for (Map divingTaskMap : divingTaskList) {
                String divingTaskId = divingTaskMap.get("ID").toString();
                String taskName = divingTaskMap.get("C_TASK_NAME_" + divingTaskBM.getId()).toString();
                String taskState = CommonTools.Obj2String(divingTaskMap.get("C_STATE_" + divingTaskBM.getId()));
                if ("已结束".equals(taskState)) {
                    stringBufferTaskId.append(divingTaskId).append(",");
                    stringBufferTaskName.append(taskName).append(",");
                }
            }
            String taskIds = stringBufferTaskId.toString().substring(0, stringBufferTaskId.length() - 1);
            String taskNames = stringBufferTaskName.toString().substring(0, stringBufferTaskName.length() - 1);
            balanceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID IN(" + taskIds + ") AND C_TABLE_STATE_"+balanceBM.getId()+"='当前'");
            List<Map<String, String>> balanceList = SqlUtil.getStringList(orientSqlEngine.getBmService().createModelQuery(balanceBM).orderAsc("TO_NUMBER(T_DIVING_TASK_"+schemaId+"_ID)").list());
            if (balanceList != null && balanceList.size() > 0) {
                for (Map<String, String> balanceMap : balanceList) {
                    String divingPlanId = balanceMap.get("T_DIVING__PLAN_TABLE_" + schemaId + "_ID");
                    String refTaskId = balanceMap.get("T_DIVING_TASK_" + schemaId + "_ID").toString();
                    String taskIdArray[] = taskIds.split(",");
                    String taskName = "";
                    List<String> taskIdsList = Arrays.asList(taskIdArray);
                    for (int i = 0; i < taskIdsList.size(); i++) {
                        if (refTaskId.equals(taskIdsList.get(i))) {
                            taskName = Arrays.asList(taskNames.split(",")).get(i);
                            break;
                        }
                    }
                    String fillTableDate = CommonTools.Obj2String(balanceMap.get("C_FILL_DATE_" + balanceBM.getId()));
                    String seaAreaId = CommonTools.Obj2String(balanceMap.get("C_WORK_SEA_AREA_" + balanceBM.getId()));
                    String seaAreaName="";
                    if (!"".equals(seaAreaId)&&seaAreaList.size()>0){
                        for (Map seaAreaMap:seaAreaList){
                            String keyId=seaAreaMap.get("ID").toString();
                            if (seaAreaId.equals(keyId)){
                                seaAreaName=CommonTools.Obj2String(seaAreaMap.get("C_NAME_"+seaAreaBM.getId()));
                                break;
                            }
                        }
                    }
                    String depth = CommonTools.Obj2String(balanceMap.get("C_DEPTH_" + balanceBM.getId()));
                    String desity = CommonTools.Obj2String(balanceMap.get("C_DESITY_" + balanceBM.getId()));
                    String buoyancyLoss = CommonTools.Obj2String(balanceMap.get("C_FORCE_LOSE_" + balanceBM.getId()));
                    String floatDepth = CommonTools.Obj2String(balanceMap.get("C_PLAN_FLOAT_DEPTH_" + balanceBM.getId()));
                    String planFloatHours = CommonTools.Obj2String(balanceMap.get("C_PLAN_FLOAT_HOURS_" + balanceBM.getId()));
                    String basketIronDensity = CommonTools.Obj2String(balanceMap.get("C_IRON_DENSITY_" + balanceBM.getId()));
                    String comeupDesity = CommonTools.Obj2String(balanceMap.get("C_COMEUP_DESITY_" + balanceBM.getId()));
                    String person = CommonTools.Obj2String(balanceMap.get("C_PERSON_" + balanceBM.getId()));
                    String tool = CommonTools.Obj2String(balanceMap.get("C_TOOL_" + balanceBM.getId()));
                    String selectPerson0 = "", selectPerson1 = "", selectPerson2 = "";
                    String weight0 = "", weight1 = "", weight2 = "";
                    if (!"".equals(person) && person != null) {
                        org.json.JSONArray jsonArray = new org.json.JSONArray(person);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            String weight = jsonObject.getString("weight");
                            if (i == 0) {
                                selectPerson0 = name;
                                weight0 = weight;
                            } else if (i == 1) {
                                selectPerson1 = name;
                                weight1 = weight;
                            } else if (i == 2) {
                                selectPerson2 = name;
                                weight2 = weight;
                            }
                        }
                    }
                    String toolWeight0 = "", toolWeight1 = "";
                    if (!"".equals(tool) && tool != null) {
                        org.json.JSONArray jsonArray = new org.json.JSONArray(tool);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String weight = jsonObject.getString("weight");
                            if (i == 0) {
                                toolWeight0 = weight;
                            } else if (i == 1) {
                                toolWeight1 = weight;
                            }
                        }
                    }

                    String basketIronWeight = CommonTools.Obj2String(balanceMap.get("C_BASKET_IRON_WEIGHT_" + balanceBM.getId()));
                    String basketIronPWaterVolume = CommonTools.Obj2String(balanceMap.get("C_BASKET_IRON_PW_" + balanceBM.getId()));
                    String leadWeight = CommonTools.Obj2String(balanceMap.get("C_BALANCE_LEAD_" + balanceBM.getId()));
                    String comeupWeight = CommonTools.Obj2String(balanceMap.get("C_COME_UP_LOAD_" + balanceBM.getId()));
                    String adjustWeight = CommonTools.Obj2String(balanceMap.get("C_ADJUST_LOAD_W_" + balanceBM.getId()));
                    String divingLoad = CommonTools.Obj2String(balanceMap.get("C_DIVING_LOAD_" + balanceBM.getId()));
                    String mercury = CommonTools.Obj2String(balanceMap.get("C_QUICKSLIVER_LEVEL_" + balanceBM.getId()));
                    String peizhongPeople = CommonTools.Obj2String(balanceMap.get("C_BOB_WEIGHT_PERSON_" + balanceBM.getId()));
                    String checker = CommonTools.Obj2String(balanceMap.get("C_CHECKER_" + balanceBM.getId()));
                    String departLeader = CommonTools.Obj2String(balanceMap.get("C_DEPART_LEADER_" + balanceBM.getId()));
                    String explain = CommonTools.Obj2String(balanceMap.get("C_EXPLAIN_" + balanceBM.getId()));

                    String cabinWeight = CommonTools.Obj2String(balanceMap.get("C_CABINWEIGHT_" + balanceBM.getId()));
                    String totalWeight0 = CommonTools.Obj2String(balanceMap.get("C_TOTALWEIGHT0_" + balanceBM.getId()));
                    String totalPWaterVolume0 = CommonTools.Obj2String(balanceMap.get("C_TOTAL_PSTJ0_" + balanceBM.getId()));
                    String leadPWaterVolume = CommonTools.Obj2String(balanceMap.get("C_LEAD_PSTJ_" + balanceBM.getId()));
                    String comeupPWaterVolume = CommonTools.Obj2String(balanceMap.get("C_COMEUP_PSTJ_" + balanceBM.getId()));
                    String totalWeight1 = CommonTools.Obj2String(balanceMap.get("C_TOTAL_WEIGHT1_" + balanceBM.getId()));
                    String totalPWaterVolume1 = CommonTools.Obj2String(balanceMap.get("C_TOTAL_PSTJ1_" + balanceBM.getId()));
                    String balanceState = CommonTools.Obj2String(balanceMap.get("C_BALANCE_STATE_" + balanceBM.getId()));

                    String basketWeight = "", basketPWaterVolume = "";
                    planCarryToolBM.clearAllFilter();
                    planCarryToolBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + refTaskId + "'" + " AND C_TOTAL_STATE_" + planCarryToolBM.getId() + "='" + "total" + "'" +
                            " AND C_CABIN_OUTORIN_" + planCarryToolBM.getId() + "='" + "out" + "'" +
                            " AND C_DIVING_PLAN_ID_" + planCarryToolBM.getId() + "='" + divingPlanId + "'");
                    List<Map> carryToolList = orientSqlEngine.getBmService().createModelQuery(planCarryToolBM).list();
                    if (carryToolList.size() > 0) {
                        Map carryToolMap = carryToolList.get(0);
                        basketWeight = CommonTools.Obj2String(carryToolMap.get("C_AIR_WEIGHT_" + planCarryToolBM.getId()));
                        basketPWaterVolume = CommonTools.Obj2String(carryToolMap.get("C_PW_VOLUMN_" + planCarryToolBM.getId()));
                    }
                    divingDeviceBM.clearAllFilter();
                    divingDeviceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + refTaskId + "'" +
                            " AND C_TYPE_" + divingDeviceBM.getId() + "='" + "total" + "'");
                    List<Map> divingDeviceList = orientSqlEngine.getBmService().createModelQuery(divingDeviceBM).list();
                    String dDeviceWeight = "";
                    String dDevicePWaterVolume = "";
                    if (divingDeviceList.size() > 0) {
                        dDeviceWeight = CommonTools.Obj2String(divingDeviceList.get(0).get("C_AIR_WEIGHT_" + divingDeviceBM.getId()));
                        dDevicePWaterVolume = CommonTools.Obj2String(divingDeviceList.get(0).get("C_PW_VOLUME_" + divingDeviceBM.getId()));
                    }
                    html.append("<html><head>").append("</head>")
                            .append("<body>").append("<table width=\"100%\" height=\"90%\" border=\"1\" align=\"center\" cellspacing=0 cellpadding=0>\n" +
                            "<tr>\n" +
                            "<td align=\"center\" valign=\"middle\" colspan=\"4\">" + taskName + "深海勇士均衡计算表</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">潜次</td>\n" +
                            "            <td>" + taskName + "</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">填表日期</td>\n" +
                            "            <td>" + fillTableDate + "</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">作业海区</td>\n" +
                            "            <td id=\"seaAreaName\" name=\"seaAreaName\">" + seaAreaName + "</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">下潜深度(M)</td>\n" +
                            "            <td id=\"divingDepth\" name=\"divingDepth\">" + depth + "\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">密度</td>\n" +
                            "            <td name=\"desity\" id=\"desity\" name=\"desity\">" + desity + "</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">浮力损失</td>\n" +
                            "            <td name=\"buoyancyLoss\" type=\"text\" id=\"buoyancyLoss\">" + buoyancyLoss + "</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">\n" +
                            "                上浮深度(M)\n" +
                            "            </td>\n" +
                            "            <td id=\"floatDepth\" name=\"floatDepth\">" + floatDepth + "</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">计划上浮时长(min)</td>\n" +
                            "            <td name=\"planFloatHours\" id=\"planFloatHours\">" + planFloatHours + "\n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr height=\"20\">\n" +
                            "\n" +
                            "            <td align=\"center\" valign=\"middle\">采样篮铁砂密度</td>\n" +
                            "            <td name=\"basketIronDensity\" id=\"basketIronDensity\">" + basketIronDensity + "</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">\n" +
                            "                上浮压载密度\n" +
                            "            </td>\n" +
                            "            <td colspan=\"2\" name=\"comeupDesity\" id=\"comeupDesity\">\n" +
                            comeupDesity + "</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\" colspan=\"2\">状态</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">重量(kg)</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">排水体积(L)</td>\n" +
                            "        </tr>\n" +
                            "        <tr height=\"20px\" id=\"personStatistics1\">\n" +
                            "            <td rowspan=\"3\" align=\"center\" valign=\"middle\">\n" +
                            "                人员\n" +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\" style=\"width: auto;\" id=\"selectPerson0\" name=\"selectPerson0\">\n" + selectPerson0 +
                            "            </td>\n" +
                            "           <td name=\"weight0\" id=\"weight0\">\n" + weight0 +
                            "           </td>\n" +
                            "            <td align=\"center\" valign=\"middle\">\n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr height=\"20px\" id=\"personStatistics2\">\n" +
                            "            <td align=\"center\" valign=\"middle\" style=\"width: auto;\" id=\"selectPerson1\" name=\"selectPerson1\">\n" + selectPerson1 +
                            "            </td>\n" +
                            "            <td name=\"weight1\" id=\"weight1\">\n" + weight1 +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\">\n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr height=\"20px\" id=\"personStatistics3\">\n" +
                            "            <td align=\"center\" valign=\"middle\" style=\"width: auto;\" id=\"selectPerson2\" name=\"selectPerson2\">\n" + selectPerson2 +
                            "            </td>\n" +
                            "            <td name=\"weight2\" id=\"weight2\">\n" + weight2 +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\">\n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr height=\"20px\" id=\"toolStatistics1\">\n" +
                            "            <td rowspan=\"2\" align=\"center\" valign=\"middle\">\n" +
                            "                工具\n" +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\">潜水器</td>\n" +
                            "            <td name=\"toolWeight0\" id=\"toolWeight0\">\n" + toolWeight0 +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\">\n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr height=\"20px\" id=\"toolStatistics2\">\n" +
                            "            <td align=\"center\" valign=\"middle\">科学家</td>\n" +
                            "            <td name=\"toolWeight1\" id=\"toolWeight1\">\n" + toolWeight1 +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\">\n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td colspan=\"2\" align=\"center\" valign=\"middle\">舱内总重</td>\n" +
                            "            <td name=\"cabinWeight\" id=\"cabinWeight\">\n" + cabinWeight +
                            "            </td>\n" +
                            "            <td id=\"canbinPWaterVolume\" name=\"canbinPWaterVolume \">\n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td colspan=\"2\" align=\"center\" valign=\"middle\">采样篮工具</td>\n" +
                            "            <td name=\"basketWeight\" id=\"basketWeight\">" + basketWeight +
                            "            </td>\n" +
                            "            <td name=\"basketPWaterVolume\" id=\"basketPWaterVolume\">" + basketPWaterVolume +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td colspan=\"2\" align=\"center\" valign=\"middle\">采样篮铁砂</td>\n" +
                            "            <td name=\"basketIronWeight\" id=\"basketIronWeight\">\n" + basketIronWeight +
                            "                \n" +
                            "            </td>\n" +
                            "            <td name=\"basketIronPWaterVolume\" id=\"basketIronPWaterVolume\">\n" + basketIronPWaterVolume +
                            "                \n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td colspan=\"2\" align=\"center\" valign=\"middle\">潜水器总重</td>\n" +
                            "            <td id=\"dDeviceWeight\" name=\"dDeviceWeight\"\n" +
                            "               >" + dDeviceWeight + "\n" +
                            "            </td>\n" +
                            "            <td id=\"dDevicePWaterVolume\" name=\"dDevicePWaterVolume\"\n" +
                            "               >" + dDevicePWaterVolume + "\n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\" colspan=\"2\">合计</td>\n" +
                            "            <td id=\"totalWeight0\" name=\"totalWeight0\">" + totalWeight0 + "</td>\n" +
                            "            <td id=\"totalPWaterVolume0\" name=\"totalPWaterVolume0\">" + totalPWaterVolume0 + "</td>\n" +
                            "        </tr>\n" +
                            "        <!--<tr height=\"20\"></tr>-->\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\" colspan=\"2\">配重</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">重量(kg)</td>\n" +
                            "            <td align=\"center\" valign=\"middle\">排水体积(L)</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\" colspan=\"2\">\n" +
                            "                配重铅块\n" +
                            "            </td>\n" +
                            "            <td name=\"leadWeight\" type=\"number\" id=\"leadWeight\">\n" + leadWeight +
                            "               \n" +
                            "            </td>\n" +
                            "            <td name=\"leadPWaterVolume\" id=\"leadPWaterVolume\">" + leadPWaterVolume + "</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\" colspan=\"2\">\n" +
                            "                上浮压载\n" +
                            "            </td>\n" +
                            "            <td name=\"comeupWeight\" id=\"comeupWeight\">\n" + comeupWeight +
                            "               \n" +
                            "            </td>\n" +
                            "            <td id=\"comeupPWaterVolume\" name=\"comeupPWaterVolume\">" + comeupPWaterVolume + "</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\" colspan=\"2\">\n" +
                            "                可调压载水\n" +
                            "            </td>\n" +
                            "            <td name=\"adjustWeight\" id=\"adjustWeight\">\n" + adjustWeight +
                            "                \n" +
                            "            </td>\n" +
                            "            <td></td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\" colspan=\"2\">合计</td>\n" +
                            "            <td id=\"totalWeight1\" name=\"totalWeight1\">" + totalWeight1 + "</td>\n" +
                            "            <td id=\"totalPWaterVolume1\" name=\"totalPWaterVolume1\">" + totalPWaterVolume1 + "</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">\n" +
                            "                均衡状态(浮力-重量)\n" +
                            "            </td>\n" +
                            "            <td colspan=\"3\" id=\"balanceState\" name=\"balanceState\">" + balanceState + "</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td colspan=\"4\" align=\"center\" valign=\"middle\">" + taskName + "配重</td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">配重铅块</td>\n" +
                            "            <td name=\"peizhongQk\" id=\"peizhongQk\">\n" + leadWeight +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\" rowspan=\"2\">配重人</td>\n" +
                            "            <td rowspan=\"2\" name=\"peizhongPeople\" id=\"peizhongPeople\">\n" + peizhongPeople +
                            "                \n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">下潜压载</td>\n" +
                            "            <td name=\"divingLoad\" id=\"divingLoad\">\n" + divingLoad +
                            "                \n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">上浮压载</td>\n" +
                            "            <td name=\"comeupLoad\" id=\"comeupLoad\">\n" + comeupWeight +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\" rowspan=\"2\">校核人</td>\n" +
                            "            <td rowspan=\"2\" name=\"checker\" id=\"checker\">\n" + checker +
                            "                \n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">可调压载水舱液位</td>\n" +
                            "            <td name=\"adjustLoad\" id=\"adjustLoad\">\n" + adjustWeight +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">艏部水银液位</td>\n" +
                            "            <td name=\"mercury\" id=\"mercury\">\n" + mercury +
                            "             \n" +
                            "            </td>\n" +
                            "            <td align=\"center\" valign=\"middle\" rowspan=\"2\">部门长</td>\n" +
                            "            <td rowspan=\"2\" name=\"departLeader\" id=\"departLeader\">\n" + departLeader +
                            "                \n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">采样篮工具重量</td>\n" +
                            "            <td name=\"currentBasketWeight\" id=\"currentBasketWeight\">\n" + basketWeight +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td align=\"center\" valign=\"middle\">说明</td>\n" +
                            "            <td colspan=\"3\" name=\"explain\" id=\"explain\">\n" + explain +
                            "                \n" +
                            "            </td>\n" +
                            "        </tr>\n" +
                            "    </table>\n").append("</body>\n" +
                            "</html>\n").toString();

                }
            }
        }
        return html;
    }
}
