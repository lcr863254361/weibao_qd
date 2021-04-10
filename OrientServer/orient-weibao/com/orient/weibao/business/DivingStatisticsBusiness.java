package com.orient.weibao.business;

import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.orient.background.bean.OrientTreeColumnDesc;
import com.orient.background.event.SaveModelViewEvent;
import com.orient.background.eventParam.SaveModelViewEventParam;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sysman.bean.SchemaBean;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.style.Align;
import com.orient.utils.ExcelUtil.style.BorderStyle;
import com.orient.utils.ExcelUtil.style.Color;
import com.orient.web.base.*;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.DivingStatisticsBean.DivingStatisticsModel;
import com.orient.weibao.bean.flowPost.Column;
import com.orient.weibao.bean.flowPost.Field;
import com.orient.weibao.bean.flowPost.FlowPostData;
import com.orient.weibao.constants.PropertyConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import static com.orient.utils.JsonUtil.getJavaCollection;

@Service
public class DivingStatisticsBusiness extends BaseBusiness {
    @Autowired
    ModelDataBusiness modelDataBusiness;
    @Autowired
    AccountingFormMgrBusiness accountingFormMgrBusiness;
    @Resource(name = "UserService")
    UserService userService;
    @Autowired
    FileServerConfig fileServerConfig;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MetaDAOFactory metaDAOFactory;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public String exportDivingData(boolean exportAll, String toExportIds) {
        IBusinessModel divingStatisticsBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, schemaId, EnumInter.BusinessModelEnum.Table);
        Excel excel = new Excel();
        Object[] headers = new Object[]{"航次", "潜次", "下潜日期", "下潜类型", "海区", "经度", "纬度", "最大深度(米)", "水中时长", "作业时长", "左舷/主驾/右舷", "左舷单位", "右舷单位", "采样情况", "人员体重和"};
        excel.row(0).value(headers);
        final int[] i = {1};
        if (exportAll) {
            List<Map<String, Object>> divingStatisticsList = orientSqlEngine.getBmService().createModelQuery(divingStatisticsBM).orderAsc("C_DIVING_TASK_" + divingStatisticsBM.getId() + ",C_DIVING_TIME_" + divingStatisticsBM.getId()).list();
            commonExportMethod(excel, i, divingStatisticsList, divingStatisticsBM);
        } else {
            divingStatisticsBM.clearAllFilter();
            if (!toExportIds.equals("")) {
                divingStatisticsBM.setReserve_filter("and id in (" + toExportIds + ")");
                List<Map<String, Object>> divingStatisticsList = orientSqlEngine.getBmService().createModelQuery(divingStatisticsBM).orderAsc("C_DIVING_TASK_" + divingStatisticsBM.getId() + ",C_DIVING_TIME_" + divingStatisticsBM.getId()).list();
                commonExportMethod(excel, i, divingStatisticsList, divingStatisticsBM);
            }
        }
        for (int j = 0; j < 1; j++) {
            excel.column(j).autoWidth();
        }
        String divingFolderPath = FtpFileUtil.EXPORT_ROOT + File.separator + "潜次统计";
        String folder = FtpFileUtil.getRelativeUploadPath(divingFolderPath);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = "潜次统计.xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
//        excel.saveExcel("diving.xls");
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    private void commonExportMethod(Excel excel, final int[] i, List<Map<String, Object>> divingStatisticsList, IBusinessModel divingStatisticsBM) {
        if (divingStatisticsList.size() > 0) {
            for (Map map : divingStatisticsList) {
                excel.cell(i[0], 0).value(map.get("C_HANGCI_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 1).value(map.get("C_DIVING_TASK_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 2).value(map.get("C_DIVING_TIME_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 3).value(map.get("C_WORK_CONTENT_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 4).value(map.get("C_SEA_AREA_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 5).value(map.get("C_LONGITUDE_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 6).value(map.get("C_LATITUDE_" + divingStatisticsBM.getId()));

                excel.cell(i[0], 7).value(map.get("C_DEPTH_" + divingStatisticsBM.getId()));
                String waterHours = CommonTools.Obj2String(map.get("C_WATER_TIME_LONG_" + divingStatisticsBM.getId()));
                if (!"".equals(waterHours)) {
                    if (waterHours.contains(".")) {
                        String splitWaterHours[] = waterHours.split("\\.");
                        waterHours = splitWaterHours[0] + "h" + splitWaterHours[1] + "min";
                    } else {
                        waterHours = waterHours + "h";
                    }
                }
                excel.cell(i[0], 8).value(waterHours);
                excel.cell(i[0], 9).value(map.get("C_WORK_TIME_LONG_" + divingStatisticsBM.getId()));
                String zmrPersons = CommonTools.Obj2String(map.get("C_PERSON_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 10).value(zmrPersons);
                excel.cell(i[0], 11).value(map.get("C_ZX_COMPANY_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 12).value(map.get("C_COMPANY_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 13).value(map.get("C_SAMPLE_SITUATION_" + divingStatisticsBM.getId()));
                excel.cell(i[0], 14).value(map.get("C_PERSON_TOTALWEIGHT_" + divingStatisticsBM.getId()));
                i[0]++;
            }
        }
    }

    public ExtGridData<DivingStatisticsModel> queryDivingStatisticsData(String customerFilter, Integer page, Integer limit) {
        ExtGridData<DivingStatisticsModel> retVal = new ExtGridData<>();
        List<DivingStatisticsModel> results = UtilFactory.newArrayList();
        IBusinessModel divingStatisticBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, schemaId, EnumInter.BusinessModelEnum.Table);

        //        select * from t_hangci_480 t1 right join t_diving_task_480 t2 on t1.id=t2.t_hangci_480_id
//        String sql = "select t1.c_hangci_name_" + hangciBM.getId() + ",t2.id,t2.c_task_name_" + taskBM.getId() + ",t2.c_sea_area_" + taskBM.getId() + " from t_hangci_" + schemaId + " t1 inner join t_diving_task_" + schemaId + " t2 on t1.id=t2.t_hangci_" + schemaId + "_id and t2.c_state_" + taskBM.getId() + "='" + "已结束" + "'";
//        String sql = "select t1.c_hangci_name_" + hangciBM.getId() + ",t2.id,t2.c_task_name_" + taskBM.getId() + ",t2.c_sea_area_" + taskBM.getId() + " from t_hangci_" + schemaId + " t1,t_diving_task_" + schemaId + " t2 where t1.id=t2." + "t_hangci_" + schemaId + "_id";
        if (customerFilter != null) {   //过滤条件不为null,则设置过滤条件
            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, new HashMap());
            customerFilters.forEach(cs -> {
                if (StringUtil.isNotEmpty(cs.getFilterValue())) {
                    String filterValue = cs.getFilterValue();
                    if (cs.getFilterName().equals("C_WATER_TIME_LONG_" + divingStatisticBM.getId())) {
                        if (filterValue.contains("<!=!>")) {
                            String[] time = filterValue.split("<!=!>");
                            divingStatisticBM.setReserve_filter(" AND C_WATER_TIME_LONG_" + divingStatisticBM.getId() + " between " + time[0] + " and " + time[1]);
                        }
                    }
                    if (cs.getFilterName().equals("C_WATER_TIME_LONG_" + divingStatisticBM.getId() + "_START")) {
                        divingStatisticBM.setReserve_filter(" AND C_WATER_TIME_LONG_" + divingStatisticBM.getId() + " >= " + filterValue);
                    }
                    if (cs.getFilterName().equals("C_WATER_TIME_LONG_" + divingStatisticBM.getId() + "_END")) {
                        divingStatisticBM.setReserve_filter(" AND C_WATER_TIME_LONG_" + divingStatisticBM.getId() + " <= " + filterValue);
                    }
                    if (!cs.getFilterName().equals("C_WATER_TIME_LONG_" + divingStatisticBM.getId())
                            && (!cs.getFilterName().equals("C_WATER_TIME_LONG_" + divingStatisticBM.getId() + "_START"))
                            && (!cs.getFilterName().equals("C_WATER_TIME_LONG_" + divingStatisticBM.getId() + "_END"))) {
                        divingStatisticBM.appendCustomerFilter(cs);
                    }
                }
            });
        }
//        long start = System.currentTimeMillis() / 1000;
//        System.out.println("开始：" + start);
        List<Map> divingStatisticList = orientSqlEngine.getBmService().createModelQuery(divingStatisticBM).orderAsc("C_DIVING_TASK_" + divingStatisticBM.getId() + ",C_DIVING_TIME_" + divingStatisticBM.getId()).list();
        getDivingStatisticList(divingStatisticList, results, divingStatisticBM);
//        long end = System.currentTimeMillis() / 1000;
//        System.out.println("结束：" + end);
//        System.out.println("耗时：" + (end - start));
        //分页处理
        retVal.setResults(PageUtil.page(results, page, limit));
        retVal.setTotalProperty(results.size());
        return retVal;
    }

    private void getDivingStatisticList(List<Map> divingStatisticList, List<DivingStatisticsModel> results, IBusinessModel divingStatisticBM) {
        if (divingStatisticList.size() > 0) {
            for (Map statisticMap : divingStatisticList) {
                DivingStatisticsModel divingStatisticsModel = new DivingStatisticsModel();
                divingStatisticsModel.setId(statisticMap.get("ID").toString());
                divingStatisticsModel.setHangciName(CommonTools.Obj2String(statisticMap.get("C_HANGCI_" + divingStatisticBM.getId())));
                divingStatisticsModel.setDivingTime(CommonTools.Obj2String(statisticMap.get("C_DIVING_TIME_" + divingStatisticBM.getId())));
                divingStatisticsModel.setTaskName(CommonTools.Obj2String(statisticMap.get("C_DIVING_TASK_" + divingStatisticBM.getId())));
                divingStatisticsModel.setSeaArea(CommonTools.Obj2String(statisticMap.get("C_SEA_AREA_" + divingStatisticBM.getId())));
                divingStatisticsModel.setDepth(CommonTools.Obj2String(statisticMap.get("C_DEPTH_" + divingStatisticBM.getId())));
                String waterHours = CommonTools.Obj2String(statisticMap.get("C_WATER_TIME_LONG_" + divingStatisticBM.getId()));
                divingStatisticsModel.setWaterHours(waterHours);
                if (!"".equals(waterHours)) {
                    if (waterHours.contains(".")) {
                        String newWaterHours = waterHours.replace(".", "h");
                        newWaterHours = newWaterHours + "min";
                        divingStatisticsModel.setWaterHours(newWaterHours);
                    }
                }
                String workHours = CommonTools.Obj2String(statisticMap.get("C_WORK_TIME_LONG_" + divingStatisticBM.getId()));
                divingStatisticsModel.setHomeWorkHours(workHours);
                if (!"".equals(workHours)) {
                    if (workHours.contains(".")) {
                        String newWorkHours = workHours.replace(".", "h");
                        newWorkHours = newWorkHours + "min";
                        divingStatisticsModel.setHomeWorkHours(newWorkHours);
                    }
                }
                String zmrPersons = CommonTools.Obj2String(statisticMap.get("C_PERSON_" + divingStatisticBM.getId()));
                String zmrPersonsArray[] = zmrPersons.split("/");
                List<String> zmrPersonList = Arrays.asList(zmrPersonsArray);
                if (zmrPersonList != null && zmrPersonList.size() > 0) {
                    for (int i = 0; i < zmrPersonList.size(); i++) {
                        if (i == 0) {
                            divingStatisticsModel.setZuoxianPerson(zmrPersonList.get(i));
                        } else if (i == 1) {
                            divingStatisticsModel.setMainDriverPerson(zmrPersonList.get(i));
                        } else if (i == 2) {
                            divingStatisticsModel.setYouxianPerson(zmrPersonList.get(i));
                        }
                    }
                }
//                divingStatisticsModel.setZmrPersons(zmrPersons);
//                }
                divingStatisticsModel.setCompany(CommonTools.Obj2String(statisticMap.get("C_COMPANY_" + divingStatisticBM.getId())));
                divingStatisticsModel.setHomeWorkContent(CommonTools.Obj2String(statisticMap.get("C_WORK_CONTENT_" + divingStatisticBM.getId())));
                divingStatisticsModel.setPersonTotalWeight(CommonTools.Obj2String(statisticMap.get("C_PERSON_TOTALWEIGHT_" + divingStatisticBM.getId())));
                divingStatisticsModel.setZxCompany(CommonTools.Obj2String(statisticMap.get("C_ZX_COMPANY_" + divingStatisticBM.getId())));
                divingStatisticsModel.setLongitude(CommonTools.Obj2String(statisticMap.get("C_LONGITUDE_" + divingStatisticBM.getId())));
                divingStatisticsModel.setLatitude(CommonTools.Obj2String(statisticMap.get("C_LATITUDE_" + divingStatisticBM.getId())));
                divingStatisticsModel.setSampleSituation(CommonTools.Obj2String(statisticMap.get("C_SAMPLE_SITUATION_" + divingStatisticBM.getId())));
                results.add(divingStatisticsModel);
            }
        }
    }

    private void queryRelTableData(List<Map<String, Object>> dataList, List<DivingStatisticsModel> results, IBusinessModel hangciBM, IBusinessModel taskBM, IBusinessModel divingPlanBM, IBusinessModel divingReportBM) {
        if (dataList.size() > 0) {
            List<Map<String, Object>> divingReportList = orientSqlEngine.getBmService().createModelQuery(divingReportBM).list();
            List<Map> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).list();
            List<User> usersList = userService.findAllUser();
            for (Map map : dataList) {
                DivingStatisticsModel divingStatisticsModel = new DivingStatisticsModel();
                String hangciName = CommonTools.Obj2String(map.get("C_HANGCI_NAME_" + hangciBM.getId()));
                divingStatisticsModel.setHangciName(hangciName);
                String taskName = CommonTools.Obj2String(map.get("C_TASK_NAME_" + taskBM.getId()));
                divingStatisticsModel.setTaskName(taskName);
                String seaArea = CommonTools.Obj2String(map.get("C_SEA_AREA_" + taskBM.getId()));
                divingStatisticsModel.setSeaArea(seaArea);
                String taskId = map.get("ID").toString();
//                 divingReportBM.setReserve_filter("AND T_DIVING_TASK_"+schemaId+"_ID='"+taskId+"'");

                if (divingReportList.size() > 0) {
                    for (Map divingReportMap : divingReportList) {
                        String refTaskId = divingReportMap.get("T_DIVING_TASK_" + schemaId + "_ID").toString();
                        if (taskId.equals(refTaskId)) {
                            //计算水中时长
                            String outWaterTime = CommonTools.Obj2String(divingReportMap.get("C_OUT_WATER_" + divingReportBM.getId()));
                            String inWaterTime = CommonTools.Obj2String(divingReportMap.get("C_IN_WATER_" + divingReportBM.getId()));
                            String waterHours = accountingFormMgrBusiness.differentHours(outWaterTime, inWaterTime);
                            divingStatisticsModel.setWaterHours(waterHours);
                            //计算水下作业时长(作业结束-作业开始)
                            //作业结束时间（抛载上浮压载时间）
                            String workEndTime = CommonTools.Obj2String(divingReportMap.get("C_WORK_END_" + divingReportBM.getId()));
                            String workStartTime = CommonTools.Obj2String(divingReportMap.get("C_WORK_START_" + divingReportBM.getId()));
                            String underWaterHours = accountingFormMgrBusiness.differentHours(workEndTime, workStartTime);
                            divingStatisticsModel.setHomeWorkHours(underWaterHours);
                            break;
                        }
                    }

                }
//                divingPlanBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                if (divingPlanList.size() > 0) {
                    for (Map divingPlanMap : divingPlanList) {
                        String refTaskId = divingPlanMap.get("T_DIVING_TASK_" + schemaId + "_ID").toString();
                        String selectZuoxian = CommonTools.Obj2String(divingPlanMap.get("C_ZUOXIAN_" + divingPlanBM.getId()));
                        String selectMainDriver = CommonTools.Obj2String(divingPlanMap.get("C_MAIN_DRIVER_" + divingPlanBM.getId()));
                        String selectYouxian = CommonTools.Obj2String(divingPlanMap.get("C_YOUXIAN_" + divingPlanBM.getId()));
                        String divingTime = CommonTools.Obj2String(divingPlanMap.get("C_DIVING_DATE_" + divingPlanBM.getId()));
                        if (taskId.equals(refTaskId)) {
                            String zmrPersons = "";
                            if (usersList.size() > 0) {
                                for (User user : usersList) {
                                    if (selectZuoxian.equals(user.getId())) {
                                        zmrPersons += user.getAllName() + "/";
                                    }
                                    if (selectMainDriver.equals(user.getId())) {
                                        zmrPersons += user.getAllName() + "/";
                                    }
                                    if (selectYouxian.equals(user.getId())) {
                                        zmrPersons += user.getAllName() + "/";
                                    }
                                }
                            }
                            if (!"".equals(zmrPersons)) {
                                zmrPersons = zmrPersons.substring(0, zmrPersons.length() - 1);
                            }
                            divingStatisticsModel.setZmrPersons(zmrPersons);
                            divingStatisticsModel.setDivingTime(divingTime);
                            break;
                        }
                    }
                }
                results.add(divingStatisticsModel);
            }
        }
    }

    public ExtGridData<SchemaBean> getStatisticsLineList(String roleId, String assigned, Integer page, Integer limit) {
        IBusinessModel divingStatisticBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel roleStatisticLineBM = businessModelService.getBusinessModelBySName(PropertyConstant.ROLE_STATISTICLINE, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.BusinessModelEnum);
        List<Map> roleStatisticList = orientSqlEngine.getBmService().createModelQuery(roleStatisticLineBM).list();
        List<IBusinessColumn> columnsList = divingStatisticBM.getAllBcCols();
        ExtGridData<SchemaBean> retVal = new ExtGridData<>();
        List<SchemaBean> results = new ArrayList<>();
        if (!StringUtil.isEmpty(roleId)) {
            //获取角色信息
            int count = 0;
            for (int i = 0; i < columnsList.size(); i++) {
                SchemaBean schemaBean = new SchemaBean();
                String columnName = columnsList.get(i).getDisplay_name();
                EnumInter colType = columnsList.get(i).getColType();
                String columnId = columnsList.get(i).getId();
                if (!"C_Relation".equals(String.valueOf(colType))) {
                    //已经分配的列
                    if ("null".equals(assigned) || "true".equals(assigned)) {
                        if (roleStatisticList.size() > 0) {
                            for (Map lineMap : roleStatisticList) {
                                String choosedRoleId = CommonTools.Obj2String(lineMap.get("C_ROLE_ID_" + roleStatisticLineBM.getId()));
                                String choosedColumnId = CommonTools.Obj2String(lineMap.get("C_COLUMN_ID_" + roleStatisticLineBM.getId()));
                                if (roleId.equals(choosedRoleId) && columnId.equals(choosedColumnId)) {
                                    schemaBean.setName(columnName);
                                    schemaBean.setId(columnId);
                                    results.add(schemaBean);
                                    columnsList.remove(i);
                                    i--;
                                    count++;
                                    break;
                                }
                            }
                        }
                        //所有列还未分配
                        else if (assigned == null) {
                            schemaBean.setName(columnName);
                            schemaBean.setId(columnId);
                            results.add(schemaBean);
                        }
                    }
                }
            }
            //剩余未分配的列
            if ("null".equals(assigned) || "".equals(assigned) && count != 0) {
                results = UtilFactory.newArrayList();
                for (IBusinessColumn column : columnsList) {
                    EnumInter colType = column.getColType();
                    if (!"C_Relation".equals(String.valueOf(colType))) {
                        SchemaBean schemaBean = new SchemaBean();
                        String columnName = column.getDisplay_name();
                        schemaBean.setName(columnName);
                        schemaBean.setId(column.getId());
                        results.add(schemaBean);
                    }
                }
            }
        } else {
            //所有未分配的列
            for (IBusinessColumn column : columnsList) {
                EnumInter colType = column.getColType();
                if (!"C_Relation".equals(String.valueOf(colType))) {
                    SchemaBean schemaBean = new SchemaBean();
                    schemaBean.setName(column.getDisplay_name());
                    schemaBean.setId(column.getId());
                    results.add(schemaBean);
                }
            }
        }
        retVal.setTotalProperty(results.size());
        retVal.setResults(PageUtil.page(results, page, limit));
        return retVal;
    }

    public boolean saveAssignLine(String roleId, String[] selectedIds, String direction) {
        IBusinessModel roleStatisticLineBM = businessModelService.getBusinessModelBySName(PropertyConstant.ROLE_STATISTICLINE, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.BusinessModelEnum);
        if ("left".equals(direction)) {
//            String selectIds=CommonTools.array2String(selectedIds);
            if (roleId != null) {
                String roleLineSql = "delete from " + PropertyConstant.ROLE_STATISTICLINE + "_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " WHERE 1=1 AND C_ROLE_ID_" + roleStatisticLineBM.getId() + "=? AND C_COLUMN_ID_" + roleStatisticLineBM.getId() + "=?";
                for (String columnId : selectedIds) {
                    jdbcTemplate.update(roleLineSql, roleId, columnId);
                }
            }
        } else {
//            Map statisticLineMap=UtilFactory.newHashMap();
            for (String columnId : selectedIds) {
//                statisticLineMap.put("C_ROLE_ID_"+roleStatisticLineBM.getId(),roleId);
//                statisticLineMap.put("C_COLUMN_ID_"+roleStatisticLineBM.getId(),columnId);
//                orientSqlEngine.getBmService().insertModelData(roleStatisticLineBM,statisticLineMap);
                String roleLineSql = "insert into " + PropertyConstant.ROLE_STATISTICLINE + "_" + schemaId + " (ID,C_ROLE_ID_" + roleStatisticLineBM.getId() + ", C_COLUMN_ID_" + roleStatisticLineBM.getId() + ") values (" + "SEQ_T_ROLE_STATISTICLINE_" + schemaId + ".nextval," + roleId + "," + columnId + ")";
                metaDAOFactory.getJdbcTemplate().update(roleLineSql);
//                jdbcTemplate.update(roleLineSql, seq_T_ROLE_STATISTICLINE_480, roleId, columnId);
            }
        }
        return true;
    }

    public AjaxResponseData<FlowPostData> getStatisticsLineHead() {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel divingStatisticBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel roleStatisticLineBM = businessModelService.getBusinessModelBySName(PropertyConstant.ROLE_STATISTICLINE,
                schemaId, EnumInter.BusinessModelEnum.Table);

        FlowPostData statisticsLineHeadData = new FlowPostData();

        List<IBusinessColumn> columnsList = divingStatisticBM.getAllBcCols();
        User curUser = UserContextUtil.getCurrentUser();
        String userId = curUser.getId();
        String userRoleSql = "select * from CWM_SYS_ROLE_USER where USER_ID=?";
        List<Map<String, Object>> userRoleList = jdbcTemplate.queryForList(userRoleSql, userId);
        List<String> roleIdsList = UtilFactory.newArrayList();
        List<Field> fieldList = new ArrayList<>();
        List<Column> columnList = new ArrayList<>();
        if (userRoleList != null && userRoleList.size() > 0) {
            for (Map userRoleMap : userRoleList) {
                String roleId = CommonTools.Obj2String(userRoleMap.get("ROLE_ID"));
                roleIdsList.add(roleId);
            }
            if (roleIdsList.size() > 0) {
                String roleIds = Joiner.on(",").join(roleIdsList);
                String roleColumnSql = "select distinct C_COLUMN_ID_" + roleStatisticLineBM.getId() + " from " + PropertyConstant.ROLE_STATISTICLINE + "_" + schemaId + " where C_ROLE_ID_" + roleStatisticLineBM.getId() + " in (" + roleIds + ")";
                List<Map<String, Object>> roleColumnList = jdbcTemplate.queryForList(roleColumnSql);
                if (roleColumnList != null && roleColumnList.size() > 0) {
                    for (Map roleColumnMap : roleColumnList) {
                        String roleColumnId = CommonTools.Obj2String(roleColumnMap.get("C_COLUMN_ID_" + roleStatisticLineBM.getId()));
                        Field field = new Field();
                        field.setName(roleColumnId);
                        fieldList.add(field);
                        if (columnsList != null && columnsList.size() > 0) {
                            for (IBusinessColumn column : columnsList) {
                                String originColumnId = column.getId();
                                if (roleColumnId.equals(originColumnId)) {
                                    Column columnModel = new Column();
                                    columnModel.setFlex(2);
                                    columnModel.setWidth("auto");
                                    columnModel.setHeader(column.getDisplay_name());
                                    columnModel.setDataIndex(column.getId());
                                    columnModel.setColumnActualField(column.getS_column_name());
                                    columnList.add(columnModel);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        statisticsLineHeadData.setFields(fieldList);
        statisticsLineHeadData.setColumns(columnList);
        retVal.setResults(statisticsLineHeadData);
        return retVal;
    }

    public ExtGridData<Map<String, Object>> getRoleStatisticLineData(Integer page, Integer limit, String
            columnJsonString) {
        ExtGridData<Map<String, Object>> retVal = new ExtGridData<>();

        IBusinessModel divingStatisticBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS,
                schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map> divingStatisticList = orientSqlEngine.getBmService().createModelQuery(divingStatisticBM).orderAsc("C_DIVING_TASK_" + divingStatisticBM.getId() + ",C_DIVING_TIME_" + divingStatisticBM.getId()).list();

        List<Map<String, Object>> statisticLineList = new ArrayList<>();
        int totalcount = 0;
        if (divingStatisticList != null && divingStatisticList.size() > 0) {
            for (Map divingStatisticMap : divingStatisticList) {
                Set<String> keySet = divingStatisticMap.keySet();
                Iterator<String> iterator = keySet.iterator();
                Map statisticsMap = UtilFactory.newHashMap();
                boolean flag = false;
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (!"".equals(columnJsonString)) {
                        JSONArray jsonArray = JSONArray.fromObject(columnJsonString);
                        if (jsonArray.size() > 0) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String columnId = jsonObject.getString("dataIndex");
                                String columnActualField = CommonTools.Obj2String(jsonObject.get("columnActualField"));
                                if (key.equals(columnActualField)) {
                                    flag = true;
                                    statisticsMap.put(columnId, divingStatisticMap.get(key));
                                    break;
                                }
                            }
                        }
                    }
                }
                if (flag) {
                    statisticLineList.add(statisticsMap);
                }
            }
            totalcount = statisticLineList.size();
        }
        retVal.setTotalProperty(statisticLineList.size());
        retVal.setResults(PageUtil.page(statisticLineList, page, limit));
        return retVal;
//        return new ExtGridData<>(statisticLineList, totalcount);
    }

    public List<ColumnDesc> getStatisticsModelColumn(String modelID) {
        List<ColumnDesc> retVal = new ArrayList<>();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel roleStatisticLineBM = businessModelService.getBusinessModelBySName(PropertyConstant.ROLE_STATISTICLINE,
                schemaId, EnumInter.BusinessModelEnum.Table);
        User curUser = UserContextUtil.getCurrentUser();
        String userId = curUser.getId();
        String userRoleSql = "select * from CWM_SYS_ROLE_USER where USER_ID=?";
        List<Map<String, Object>> userRoleList = jdbcTemplate.queryForList(userRoleSql, userId);
        List<String> roleIdsList = UtilFactory.newArrayList();
        if (userRoleList != null && userRoleList.size() > 0) {
            for (Map userRoleMap : userRoleList) {
                String roleId = CommonTools.Obj2String(userRoleMap.get("ROLE_ID"));
                roleIdsList.add(roleId);
            }
            if (roleIdsList.size() > 0) {
                String roleIds = Joiner.on(",").join(roleIdsList);
                String roleColumnSql = "select distinct C_COLUMN_ID_" + roleStatisticLineBM.getId() + " from " + PropertyConstant.ROLE_STATISTICLINE + "_" + schemaId + " where C_ROLE_ID_" + roleStatisticLineBM.getId() + " in (" + roleIds + ")";
                List<Map<String, Object>> roleColumnList = jdbcTemplate.queryForList(roleColumnSql);
                if (roleColumnList != null && roleColumnList.size() > 0) {
                    for (Map roleColumnMap : roleColumnList) {
                        String roleColumnId = CommonTools.Obj2String(roleColumnMap.get("C_COLUMN_ID_" + roleStatisticLineBM.getId()));
                        if (null != businessModel) {
                            businessModel.getAllBcCols().forEach(iBusinessColumn -> {
                                ColumnDesc orientExtColumn = new OrientTreeColumnDesc().init(iBusinessColumn);
                                String originColumnId = orientExtColumn.getId();
                                if (roleColumnId.equals(originColumnId)) {
                                    orientExtColumn.setEditAbleType(null);
                                    retVal.add(orientExtColumn);
                                    ;
                                }
                            });
                        }
                    }
                }
            }
        }
        return retVal;
    }

    public void updateStatisticsModelColumn(ModelFormViewEntity formValue) {
        User curUser = UserContextUtil.getCurrentUser();
        IBusinessModel userStatisticsLineBM = businessModelService.getBusinessModelBySName(PropertyConstant.USER_STATISTICLINE, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);

        String userId = curUser.getId();

        SaveModelViewEventParam eventParam = new SaveModelViewEventParam();
        eventParam.setModelFormViewEntity(formValue);
        OrientContextLoaderListener.Appwac.publishEvent(new SaveModelViewEvent(this, eventParam));
    }

    public ExtGridData<Map> getDivingStatisticGridData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort) {
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
            StringBuilder queryFilter = new StringBuilder();
            customerFilters.forEach(cs -> {
                if (StringUtil.isNotEmpty(cs.getFilterValue())) {
                    String filterValue = cs.getFilterValue();
                    if (cs.getFilterName().equals("DIVINGDENTRYTIME_" + businessModel.getId()) ||
                            cs.getFilterName().equals("HATCHOPENTIME_" + businessModel.getId())) {
                        if (filterValue.contains("<!=!>")) {
                            String[] time = filterValue.split("<!=!>");
                            String endDate = "";
                            String startDate = "";
                            if (filterValue.indexOf("<!=!>") == 0) {
                                endDate = time[1];
                            } else if (time.length == 1 && filterValue.indexOf("<!=!>") != 0) {
                                startDate = time[0];
                            } else {
                                startDate = time[0];
                                endDate = time[1];
                            }

                            if (!"".equals(startDate) && !"".equals(endDate)) {
                                queryFilter.append(" AND ").append("TO_CHAR(").append(cs.getFilterName()).append("/(1000 * 60 * 60 * 24) +\n" +
                                        "TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                        " 'YYYY-MM-DD HH24:MI:SS')").append(">=").append("TO_CHAR(").append(startDate).append("/(1000 * 60 * 60 * 24) +\n" +
                                        "TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                        " 'YYYY-MM-DD HH24:MI:SS')");

                                queryFilter.append(" AND ").append("TO_CHAR(").append(cs.getFilterName()).append("/(1000 * 60 * 60 * 24) +\n" +
                                        "TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                        " 'YYYY-MM-DD HH24:MI:SS')").append("<=").append("TO_CHAR(").append(endDate).append("/(1000 * 60 * 60 * 24) +\n" +
                                        "TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                        " 'YYYY-MM-DD HH24:MI:SS')");

                            } else if (!"".equals(startDate)) {
                                queryFilter.append(" AND ").append("TO_CHAR(").append(cs.getFilterName()).append("/(1000 * 60 * 60 * 24) +\n" +
                                        "TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                        " 'YYYY-MM-DD HH24:MI:SS')").append(">=").append("TO_CHAR(").append(startDate).append("/(1000 * 60 * 60 * 24) +\n" +
                                        "TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                        " 'YYYY-MM-DD HH24:MI:SS')");

                            } else if (!"".equals(endDate)) {
                                queryFilter.append(" AND ").append("TO_CHAR(").append(cs.getFilterName()).append("/(1000 * 60 * 60 * 24) +\n" +
                                        "TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                        " 'YYYY-MM-DD HH24:MI:SS')").append("<=").append("TO_CHAR(").append(endDate).append("/(1000 * 60 * 60 * 24) +\n" +
                                        "TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                                        " 'YYYY-MM-DD HH24:MI:SS')");
                            }
                        }
                    } else {
                        businessModel.appendCustomerFilter(cs);
                    }
                }
            });
            businessModel.setReserve_filter(queryFilter.toString());
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
        } else {
            businessModelQuery.orderAsc("TO_NUMBER(ID)");   //如果不指定排序字段，就按照数字类型的id顺序排序
        }
        List<Map> dataList = businessModelQuery.list();
        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    /**
     * 导出下潜统计信息
     *
     * @param exportAll
     * @param toExportIds
     * @return
     */
    public String exportDivingStatisticsData(boolean exportAll, String toExportIds) {
        IBusinessModel divingStatisticsBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        Excel excel = new Excel();
        excel.setWorkingSheet(0).sheetName("潜次统计");
        List<Map<String, Object>> divingStatisticsList = UtilFactory.newArrayList();
        if (exportAll) {
            divingStatisticsList = orientSqlEngine.getBmService().createModelQuery(divingStatisticsBM).orderAsc("TO_NUMBER(ID)").list();
        } else {
            if (!"".equals(toExportIds)) {
                divingStatisticsBM.setReserve_filter("and id in (" + toExportIds + ")");
                divingStatisticsList = orientSqlEngine.getBmService().createModelQuery(divingStatisticsBM).list();
            }
        }
        if (divingStatisticsList != null && divingStatisticsList.size() > 0) {
            exportDivingStatisticInfoData(excel, divingStatisticsList, PropertyConstant.DIVING_STATISTICS);
        }
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT + File.separator + "潜次统计");
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = "潜次统计.xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    public void exportDivingStatisticInfoData(Excel excel, List<Map<String, Object>> list, String tableName) {
        IBusinessModel model = businessModelService.getBusinessModelBySName
                (tableName, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        List<IBusinessColumn> columnsList = model.getAllBcCols();
        final int[] columnIndex = {0};
        for (Map<String, Object> map : list) {
            for (String key : map.keySet()) {
                if ("ID".equals(key)||("T_HANGCI_"+schemaId+"_ID").equals(key)||("T_DIVING_TASK_"+schemaId+"_ID").equals(key)||("T_SAMPLE_SITUATION_"+schemaId+"_ID").equals(key)){
                    continue;
                }
                //转换真实值 为 显示值
                for (IBusinessColumn column : columnsList) {
                    if (key.equals(column.getS_column_name())) {
                        key = column.getDisplay_name();
                        break;
                    }
                }
                excel.column(columnIndex[0]).autoWidth().borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
                excel.cell(0, columnIndex[0])//选择第5行，但忽略第1个单元格，从第2个单元格开始操作
                        .value(key);
                columnIndex[0]++;
            }
            break;

        }
        final int[] row1 = {1};
        list.forEach(dataMap -> {
            final int[] cell = {0};
            for (String key : dataMap.keySet()) {
                if ("ID".equals(key)||("T_HANGCI_"+schemaId+"_ID").equals(key)||("T_DIVING_TASK_"+schemaId+"_ID").equals(key)||("T_SAMPLE_SITUATION_"+schemaId+"_ID").equals(key)){
                   continue;
                }
                String dataValue = CommonTools.Obj2String(dataMap.get(key));
                if (("C_WORK_TIME_LONG_" + model.getId()).equals(key) ||
                        ("C_WATER_TIME_LONG_" + model.getId()).equals(key) ||
                        ("bufangCommandTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("personComeinCabinT".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("hatchCloseTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("divingDEntryTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("startFillWaterTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("startWorkTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("endWorkTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("floatWaterTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("divingDOutWaterTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("recoverDeckTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("hatchOpenTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("personOutCabinTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("onceBufangboatTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("onceRecoverboatTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("twiceBufangboatTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("twiceRecoverboatTime".toUpperCase() + "_" + model.getId()).equals(key) ||
                        ("ballastRemoveTime".toUpperCase() + "_" + model.getId()).equals(key))
                {
                    if (!"".equals(dataValue)){
                        //判断字符串为纯数字类型
                        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                        boolean isIntNumber = pattern.matcher(dataValue).matches();
                        if (isIntNumber) {
                            dataValue=String.valueOf(Long.valueOf(dataValue)-8*60*60*1000);
                            dataValue = TimeUtil.getFormatString(Long.valueOf(dataValue), "HH:mm:ss");
                            dataValue.replaceAll(":","：");
                        }
                    }
                    excel.cell(row1[0], cell[0])
                            .value(dataValue,"HH:mm:ss").warpText(true);
                }else{
                    excel.cell(row1[0], cell[0])
                            .value(StringUtil.decodeUnicode(dataValue)).warpText(true);
                }
                cell[0]++;
            }
            row1[0]++;

        });
    }
}


