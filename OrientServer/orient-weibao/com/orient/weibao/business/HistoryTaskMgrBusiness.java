package com.orient.weibao.business;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.Meta;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.sysman.bean.FuncBean;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.ExcelUtil.style.Align;
import com.orient.utils.ExcelUtil.style.BorderStyle;
import com.orient.utils.ExcelUtil.style.Color;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.weibao.bean.HistoryTask.ImportDivingTaskBean;
import com.orient.weibao.bean.HistoryTask.ImportHangciBean;
import com.orient.weibao.bean.HistoryTask.ImportHangduanBean;
import com.orient.weibao.bean.HistoryTask.ImportPersonWeightBean;
import com.orient.weibao.bean.ImportDeviceBean;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.utils.CommonOperate;
import com.orient.weibao.utils.ExcelImport;
import com.orient.weibao.utils.SqlUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class HistoryTaskMgrBusiness extends BaseBusiness {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Resource(name = "UserService")
    UserService userService;
    @Autowired
    ExcelImport excelImport;
    @Autowired
    FileServerConfig fileServerConfig;

    public Map<String, Object> importHangciHangduan(TableEntity excelEntity) {

        IBusinessModel hangciBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGCI, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        Map<String, Object> retVal = new HashMap<>();
        List<ImportHangciBean> hangciBeanArrayList = UtilFactory.newArrayList();
        List<DataEntity> dataEntities = excelEntity.getDataEntityList();
        List<ImportHangduanBean> hangduanBeanList = UtilFactory.newArrayList();
        for (int j = 0; j < dataEntities.size(); j++) {
            ImportHangciBean importHangciBean = new ImportHangciBean();
            ImportHangduanBean importHangduanBean = new ImportHangduanBean();
            //????????????????????????????????????????????????????????????
            List<FieldEntity> fieldEntities = dataEntities.get(j).getFieldEntityList();
            if (fieldEntities != null) {
                for (int i = 0; i < fieldEntities.size(); i++) {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    String name = fieldEntities.get(i).getName();
                    String value = fieldEntities.get(i).getValue();
                    int rowNumer = j + 2;
                    if ("??????".equals(name)) {
                        if (value == null || "".equals(value)) {
                            retVal.put("success", false);
                            retVal.put("msg", "???" + rowNumer + "??????" + i + "??????????????????????????????????????????????????????");
                            return retVal;
                        }
                        importHangciBean.setHangciName(value);
                    } else if ("??????".equals(name)) {
                        if (value == null || "".equals(value)) {
                            retVal.put("success", false);
                            retVal.put("msg", "???" + rowNumer + "??????" + i + "??????????????????????????????????????????????????????");
                            return retVal;
                        }
                        importHangduanBean.setHangduanName(value);
                    } else if ("????????????".equals(name)) {
                        if (value != null && !"".equals(value)) {
                            importHangduanBean.setStartDate(CommonTools.util2Sql(CommonTools.str2Date(value)));
                            importHangciBean.setHangciStartDate(CommonTools.util2Sql(CommonTools.str2Date(value)));
                        }
                    } else if ("???????????????".equals(name)) {
                        if (value != null && !"".equals(value)) {
                            importHangduanBean.setWharfDate(CommonTools.util2Sql(CommonTools.str2Date(value)));
                            importHangciBean.setHangciEndDate(CommonTools.util2Sql(CommonTools.str2Date(value)));
                        }
                    } else if ("????????????".equals(name)) {
                        importHangduanBean.setDivingTimes(CommonOperate.getValue(value));
                    } else if ("????????????".equals(name)) {
                        importHangduanBean.setValidDiving(CommonOperate.getValue(value));
                    } else if ("????????????".equals(name)) {
                        importHangduanBean.setSupportProject(value = value == "null" ? "" : value);
                    } else if ("????????????".equals(name)) {
                        importHangduanBean.setHangciQuality(value);
                    } else if ("??????????????????".equals(name)) {
                        importHangduanBean.setHangciLeaderExpert(value);
                    } else if ("????????????".equals(name)) {
                        importHangduanBean.setHangciLeader(value);
                    } else if ("??????????????????".equals(name)) {
                        importHangduanBean.setHangciChiefExpert(value);
                    } else if ("?????????????????????".equals(name)) {
                        importHangduanBean.setHangciChiefScientist(value);
                    } else if ("???????????????????????????".equals(name)) {
                        importHangduanBean.setHangciChiefSAssistant(value);
                    } else if ("1#(??????????????????)".equals(name)) {
                        importHangduanBean.setFirstDivingDepartor(value);
                    } else if ("2#(??????)".equals(name)) {
                        importHangduanBean.setSecondMaster(value);
                    } else if ("3#(????????????)".equals(name)) {
                        importHangduanBean.setThirdWaterSupport(value);
                    } else if ("??????????????????".equals(name)) {
                        importHangduanBean.setHangciTopRecord(value);
                    } else if ("????????????".equals(name)) {
                        importHangduanBean.setHangciResult(value);
                    } else if ("??????????????????".equals(name)) {
                        importHangduanBean.setHangciReportVideo(value);
                    } else if ("????????????".equals(name)) {
                        importHangduanBean.setHangciFile(value);
                    } else if ("??????????????????".equals(name)) {
                        importHangduanBean.setHangciDivingMap(value);
                    }
                }
                if (!"".equals(importHangciBean.getHangciName()) && !"".equals(importHangduanBean.getHangduanName())) {
                    int count = 0;
                    if (hangciBeanArrayList.size() > 0) {
                        for (ImportHangciBean hangciBean : hangciBeanArrayList) {
                            if (hangciBean.getHangciName().equals(importHangciBean.getHangciName())) {
                                count++;
                                List<ImportHangduanBean> importHangduanBeans = hangciBean.getImportHangduanBeanList();
                                importHangduanBeans.add(importHangduanBean);
                                long tStartDiff = TimeUtil.getTime(hangciBean.getHangciStartDate(), importHangciBean.getHangciStartDate());
                                //?????????importHangciBean???????????????????????????hangciBean???????????????
                                if (tStartDiff < 0) {
                                    hangciBean.setHangciStartDate(importHangciBean.getHangciStartDate());
                                }
                                long tEndDiff = TimeUtil.getTime(hangciBean.getHangciEndDate(), importHangciBean.getHangciEndDate());
                                //?????????importHangciBean???????????????????????????hangciBean???????????????
                                if (tEndDiff > 0) {
                                    hangciBean.setHangciEndDate(importHangciBean.getHangciEndDate());
                                }
                            }
                        }
                    }
                    if (count == 0) {
                        hangduanBeanList = UtilFactory.newArrayList();
                        hangduanBeanList.add(importHangduanBean);
                        importHangciBean.setImportHangduanBeanList(hangduanBeanList);
                        if (!"".equals(importHangciBean.getHangciName())) {
                            hangciBeanArrayList.add(importHangciBean);
                        }
                    }
                }
            }
        }

        if (hangciBeanArrayList.size() > 0) {
//            String hangciSql = "select ID,C_HANGCI_NAME_" + hangciBM.getId() + " as name  from T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID;
            List<Map<String, Object>> hangciList = orientSqlEngine.getBmService().createModelQuery(hangciBM).list();
//            List<String> hangciNameList = generateListString(hangciList);
//            String hangduanSql = "select ID,C_HANGDUAN_NAME_" + hangduanBM.getId() + " as name  from T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID;
            List<Map<String, Object>> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanBM).list();
//            List<String> hangduanNameList = generateListString(hangduanList);

            for (ImportHangciBean importHangciBean : hangciBeanArrayList) {
                String hangciName = importHangciBean.getHangciName();
                List<ImportHangduanBean> importHangduanList = importHangciBean.getImportHangduanBeanList();
                String hasHangciId = "";
                for (Map hangciMap : hangciList) {
                    String hasHangciName = CommonTools.Obj2String(hangciMap.get("C_HANGCI_NAME_" + hangciBM.getId()));
                    if (hangciName.equals(hasHangciName)) {
                        hasHangciId = hangciMap.get("ID").toString();
                        //????????????
                        hangciMap.put("C_PLAN_BEGIN_TIME_" + hangciBM.getId(), importHangciBean.getHangciStartDate());
                        hangciMap.put("C_PLAN_ENDING_TIME_" + hangciBM.getId(), importHangciBean.getHangciEndDate());
                        orientSqlEngine.getBmService().updateModelData(hangciBM, hangciMap, hasHangciId);
                        break;
                    }
                }
                if ("".equals(hasHangciId)) {
                    Map hangciMap = UtilFactory.newHashMap();
                    hangciMap.put("C_HANGCI_NAME_" + hangciBM.getId(), importHangciBean.getHangciName());
                    hangciMap.put("C_PLAN_BEGIN_TIME_" + hangciBM.getId(), importHangciBean.getHangciStartDate());
                    hangciMap.put("C_PLAN_ENDING_TIME_" + hangciBM.getId(), importHangciBean.getHangciEndDate());
                    hasHangciId = orientSqlEngine.getBmService().insertModelData(hangciBM, hangciMap);
                }
                for (ImportHangduanBean importHangduanBean : importHangduanList) {
                    String hangduanName = importHangduanBean.getHangduanName();
                    String hasHangduanId = "";
                    Map hasHangduanMap = UtilFactory.newHashMap();

                    for (Map hangduanMap : hangduanList) {
                        String hasHangduanName = CommonTools.Obj2String(hangduanMap.get("C_HANGDUAN_NAME_" + hangduanBM.getId()));
                        String refHangciId = CommonTools.Obj2String(hangduanMap.get("T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
                        if (hangduanName.equals(hasHangduanName) && hasHangciId.equals(refHangciId)) {
                            hasHangduanId = hangduanMap.get("ID").toString();
                            hasHangduanMap = hangduanMap;
                            break;
                        }
                    }
                    if (!"".equals(hasHangduanId)) {
                        hangduanMap(hangduanBM, importHangduanBean, hasHangduanMap);
                        orientSqlEngine.getBmService().updateModelData(hangduanBM, hasHangduanMap, hasHangduanId);
                        importHangduanBean.setHangduanId(hasHangduanId);
                    } else {
                        Map hangduanMap = UtilFactory.newHashMap();
                        hangduanMap(hangduanBM, importHangduanBean, hangduanMap);
                        //?????????????????????
                        hangduanMap.put("C_IS_START_" + hangduanBM.getId(), "0");
                        hangduanMap.put("C_HANGDUAN_NAME_" + hangduanBM.getId(), hangduanName);
                        hangduanMap.put("T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hasHangciId);
                        String hangduanId = orientSqlEngine.getBmService().insertModelData(hangduanBM, hangduanMap);
                        importHangduanBean.setHangduanId(hangduanId);
                    }
                }
            }
        }
        retVal.put("success", true);
//        retVal.put("msg", "???????????????");
        retVal.put("hangciBeanList", JSON.toJSONString(hangciBeanArrayList));
        return retVal;
    }

    public void hangduanMap(IBusinessModel hangduanBM, ImportHangduanBean importHangduanBean, Map hangduanMap) {
        hangduanMap.put("C_PLAN_START_TIME_" + hangduanBM.getId(), importHangduanBean.getStartDate());
        hangduanMap.put("C_PLAN_END_TIME_" + hangduanBM.getId(), importHangduanBean.getWharfDate());
        int diffrents = TimeUtil.getSecondDiff(importHangduanBean.getWharfDate(), importHangduanBean.getStartDate());
        int gotoSeaDays = diffrents / (24 * 60 * 60) + 1;
        hangduanMap.put("GOTOSEADAYS_" + hangduanBM.getId(), gotoSeaDays);
        hangduanMap.put("DIVINGTIMES_" + hangduanBM.getId(), importHangduanBean.getDivingTimes());
        hangduanMap.put("VALIDDIVING_" + hangduanBM.getId(), importHangduanBean.getValidDiving());
        hangduanMap.put("SUPPORTPROJECT_" + hangduanBM.getId(), importHangduanBean.getSupportProject());
        hangduanMap.put("HANGCIQUALITY_" + hangduanBM.getId(), importHangduanBean.getHangciQuality());
        hangduanMap.put("HANGCILEADEREXPERT_" + hangduanBM.getId(), importHangduanBean.getHangciLeaderExpert());
        hangduanMap.put("HANGCILEADER_" + hangduanBM.getId(), importHangduanBean.getHangciLeader());
        hangduanMap.put("HANGCICHIEFEXPERT_" + hangduanBM.getId(), importHangduanBean.getHangciChiefExpert());
        hangduanMap.put("HANGCICHIEFSCIENTIST_" + hangduanBM.getId(), importHangduanBean.getHangciChiefScientist());
        hangduanMap.put("HANGCICHIEFSASSISTAN_" + hangduanBM.getId(), importHangduanBean.getHangciChiefSAssistant());
        hangduanMap.put("FIRSTDIVINGDEPARTOR_" + hangduanBM.getId(), importHangduanBean.getFirstDivingDepartor());
        hangduanMap.put("SECONDMASTER_" + hangduanBM.getId(), importHangduanBean.getSecondMaster());
        hangduanMap.put("THIRDWATERSUPPORT_" + hangduanBM.getId(), importHangduanBean.getThirdWaterSupport());
        hangduanMap.put("HANGCITOPRECORD_" + hangduanBM.getId(), importHangduanBean.getHangciTopRecord());
        hangduanMap.put("HANGCIRESULT_" + hangduanBM.getId(), importHangduanBean.getHangciResult());
    }

    public List<String> generateListString(List<Map<String, Object>> mapList) {
        List<String> stringList = null;
        if (mapList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map map : mapList) {
                String name = CommonTools.Obj2String(map.get("name"));
                stringBuilder.append(name).append(",");
            }
            String str = stringBuilder.toString();
            String strSplit[] = str.substring(0, str.length() - 1).split(",");
            stringList = new ArrayList<>(Arrays.asList(strSplit));
        }
        return stringList;
    }

    public Map<String, Object> importPersonWeight(TableEntity excelEntity, String hangduanId) {

        IBusinessModel personWeightBM = businessModelService.getBusinessModelBySName(PropertyConstant.PERSON_WEIGHT, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel attendPersonBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        Map hangduanMap = orientSqlEngine.getBmService().createModelQuery(hangduanBM).findById(hangduanId);
        String hasAttendPerson = CommonTools.Obj2String(hangduanMap.get("C_ATTEND_PERSON_" + hangduanBM.getId()));
        String hangduanBeginDate = CommonTools.Obj2String(hangduanMap.get("C_PLAN_START_TIME_" + hangduanBM.getId()));
        String hangduanEndDate = CommonTools.Obj2String(hangduanMap.get("C_PLAN_END_TIME_" + hangduanBM.getId()));
        personWeightBM.setReserve_filter("AND T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + hangduanId + "'");
        List<Map> personWeightList = orientSqlEngine.getBmService().createModelQuery(personWeightBM).list();

        Map<String, Object> retVal = new HashMap<>();
        Map<String, User> allUsers = roleEngine.getRoleModel(false).getUsers();
//        List<User> usersList = userService.findAllUser();
        List<ImportPersonWeightBean> updatePersonWeightList = new ArrayList<>();
        List<ImportPersonWeightBean> newPersonWeightList = new ArrayList<>();

        List<String> invalidUserList = UtilFactory.newArrayList();

        List<DataEntity> dataEntities = excelEntity.getDataEntityList();
        for (int j = 0; j < dataEntities.size(); j++) {
            ImportPersonWeightBean importPersonWeightBean = new ImportPersonWeightBean();
            //????????????????????????????????????????????????????????????
            List<FieldEntity> fieldEntities = dataEntities.get(j).getFieldEntityList();
            if (fieldEntities != null) {
                boolean userExist = false;
                int count = 0;
                for (int i = 0; i < fieldEntities.size(); i++) {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    int rowNumer = j + 2;
                    String name = fieldEntities.get(i).getName();
                    String value = fieldEntities.get(i).getValue();
                    if ("??????".equals(name)) {
                        if (value == null || "".equals(value)) {
//                            retVal.put("success", false);
//                            retVal.put("msg", "???" + rowNumer + "??????" + i + "??????" + name + "????????????????????????????????????");
//                            return retVal;
                            break;
                        }
//                        for (User user : usersList) {
                        for (IUser user : allUsers.values()) {
                            if (value.equals(user.getAllName())) {
                                count++;
                                importPersonWeightBean.setUserId(user.getId());
                                if (personWeightList.size() > 0) {
                                    for (Map personWeight : personWeightList) {
                                        if (personWeight.get("C_ATTEND_ID_" + personWeightBM.getId()).equals(importPersonWeightBean.getUserId())) {
                                            importPersonWeightBean.setPersonWeightKey(personWeight.get("ID").toString());
                                            userExist = true;
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        if (count == 0) {
                            invalidUserList.add(value);
                            break;
                        }
                        importPersonWeightBean.setUserName(value);
                    } else if ("??????".equals(name)) {
                        importPersonWeightBean.setDepartment(value);
                    } else if ("?????????kg???".equals(name)) {
                        importPersonWeightBean.setWeight(value);
                    } else if ("????????????".equals(name)) {
                        int totalDays = 0;
                        if (!"".equals(value) && value != null) {
                            value = value.replaceAll("\r|\n", "");
                            String regex = ",|???";
                            String Dates[] = value.split(regex);
                            List<String> dateList = Arrays.asList(Dates);
                            for (String dateCombine : dateList) {
                                if (dateCombine.contains("???")) {
                                    String dateArrays[] = dateCombine.split("???");
                                    String startDate = dateArrays[0];
                                    String endDate = dateArrays[1];
                                    Date startJoinShipDate = CommonTools.str2Date(startDate);
                                    Date endJoinShipDate = CommonTools.str2Date(endDate);
                                    int diffrents = TimeUtil.getSecondDiff(endJoinShipDate, startJoinShipDate);
                                    totalDays += diffrents / (24 * 60 * 60) + 1;
                                }
                            }
                            importPersonWeightBean.setJoinShipDate(value);
                        } else {
                            if (!"".equals(hangduanBeginDate) && !"".equals(hangduanEndDate)) {
                                Date startJoinShipDate = CommonTools.str2Date(hangduanBeginDate);
                                Date endJoinShipDate = CommonTools.str2Date(hangduanEndDate);
                                int diffrents = TimeUtil.getSecondDiff(endJoinShipDate, startJoinShipDate);
                                totalDays += diffrents / (24 * 60 * 60) + 1;
                                importPersonWeightBean.setJoinShipDate(hangduanBeginDate + "???" + hangduanEndDate);
                            }
                        }
                        importPersonWeightBean.setTotalDays(String.valueOf(totalDays));
                    } else if ("??????".equals(name)) {
                        importPersonWeightBean.setLiveboat(value);
                    } else if ("??????".equals(name)) {
                        importPersonWeightBean.setTechnicalPost(value);
                    } else if ("????????????".equals(name)) {
                        importPersonWeightBean.setHangduanPost(value);
                    } else if ("????????????".equals(name)) {
                        importPersonWeightBean.setPoliticsStatus(value);
                    }
                }
                if (userExist) {
                    updatePersonWeightList.add(importPersonWeightBean);
                } else if (!userExist && count != 0) {
                    newPersonWeightList.add(importPersonWeightBean);
                }
            }
        }

        if (updatePersonWeightList != null && updatePersonWeightList.size() > 0) {
            for (ImportPersonWeightBean importPersonWeightBean : updatePersonWeightList) {
                Map updatePersonWeightMap = UtilFactory.newHashMap();
                personWeightMap(personWeightBM, importPersonWeightBean, updatePersonWeightMap);
                String personWeightKeyId = importPersonWeightBean.getPersonWeightKey();
                orientSqlEngine.getBmService().updateModelData(personWeightBM, updatePersonWeightMap, personWeightKeyId);
            }
        }
        if (newPersonWeightList != null && newPersonWeightList.size() > 0) {
            StringBuffer newAttendPersonStr = new StringBuffer();
            for (ImportPersonWeightBean importPersonWeightBean : newPersonWeightList) {
                String userId = importPersonWeightBean.getUserId();
                newAttendPersonStr.append(userId).append(",");
                Map personWeightMap = UtilFactory.newHashMap();
                personWeightMap(personWeightBM, importPersonWeightBean, personWeightMap);
                personWeightMap.put("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangduanId);
                orientSqlEngine.getBmService().insertModelData(personWeightBM, personWeightMap);
            }

            List<String> newAttendPersonUserIdList = UtilFactory.newArrayList();
            String newAttendPersonUserIds = newAttendPersonStr.toString();
            newAttendPersonUserIds = newAttendPersonUserIds.substring(0, newAttendPersonUserIds.length() - 1);
            if (!"".equals(hasAttendPerson)) {
                hasAttendPerson = hasAttendPerson + "," + newAttendPersonUserIds;
            } else {
                hasAttendPerson = newAttendPersonUserIds;
            }

            hangduanMap.put("C_ATTEND_PERSON_" + hangduanBM.getId(), hasAttendPerson);
            orientSqlEngine.getBmService().updateModelData(hangduanBM, hangduanMap, hangduanId);

            //???????????????????????????????????????????????????
            if (!"".equals(newAttendPersonUserIds) && newAttendPersonUserIds != null) {
                String newAttendPersonUserIdsArrays[] = newAttendPersonUserIds.split(",");
                newAttendPersonUserIdList = Arrays.asList(newAttendPersonUserIdsArrays);
            }
            String taskSql = "select id from t_diving_task_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " where t_hangduan_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID=?";
            List<Map<String, Object>> taskList = jdbcTemplate.queryForList(taskSql, hangduanId);
            if (newAttendPersonUserIdList.size() > 0) {
                for (Map taskMap : taskList) {
                    for (String insertPersonId : newAttendPersonUserIdList) {
                        Map personMap = UtilFactory.newHashMap();
                        personMap.put("C_ATTEND_PERSON_" + attendPersonBM.getId(), insertPersonId);
                        personMap.put("T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", taskMap.get("ID"));
                        personMap.put("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangduanId);
                        orientSqlEngine.getBmService().insertModelData(attendPersonBM, personMap);
                    }
                }
            }
        }
        String invalidUserStr = "";
        if (invalidUserList.size() > 0) {
            invalidUserStr = Joiner.on(",").join(invalidUserList);
        }
        retVal.put("invalidUser", invalidUserStr);
        retVal.put("msg", "???????????????");
        retVal.put("success", true);
        return retVal;
    }

    public void personWeightMap(IBusinessModel personWeightBM, ImportPersonWeightBean importPersonWeightBean, Map personWeightMap) {
        personWeightMap.put("C_ATTEND_ID_" + personWeightBM.getId(), importPersonWeightBean.getUserId());
        personWeightMap.put("C_WEIGHT_" + personWeightBM.getId(), importPersonWeightBean.getWeight());
        personWeightMap.put("C_DEPARTMENT_" + personWeightBM.getId(), importPersonWeightBean.getDepartment());
        personWeightMap.put("C_JOINSHIP_DATE_" + personWeightBM.getId(), importPersonWeightBean.getJoinShipDate());
        personWeightMap.put("C_TOTAL_DAYS_" + personWeightBM.getId(), importPersonWeightBean.getTotalDays());
        personWeightMap.put("C_LIVE_BOAT_" + personWeightBM.getId(), importPersonWeightBean.getLiveboat());
        personWeightMap.put("C_TECHNICALPOST_" + personWeightBM.getId(), importPersonWeightBean.getTechnicalPost());
        personWeightMap.put("C_HANGDUAN_POST_" + personWeightBM.getId(), importPersonWeightBean.getHangduanPost());
        personWeightMap.put("C_POLITICS_STATUS_" + personWeightBM.getId(), importPersonWeightBean.getPoliticsStatus());
    }

    public String exportPersonWeightData(boolean exportAll, String toExportIds, String hangduanId) {
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel personWeightBM = businessModelService.getBusinessModelBySName(PropertyConstant.PERSON_WEIGHT, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        Map hangduanMap = orientSqlEngine.getBmService().createModelQuery(hangduanBM).findById(hangduanId);
        Object[] headers = new Object[]{"??????", "??????", "?????????kg???", "????????????", "??????", "??????", "????????????", "????????????"};
        Excel excel = new Excel();
        excel.row(0).value(headers);
        final int[] i = {1};
        if (exportAll) {
//            String sql = "select pw.*,u.all_name from T_PERSON_WEIGHT_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " pw left join CWM_SYS_USER u on pw.C_ATTEND_ID_" + personWeightBM.getId() + "=u.id where u.state='1' and pw.t_hangduan_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_id='" + hangduanId + "' order by pw.id asc";
            String sql = "select pw.*,u.all_name from T_PERSON_WEIGHT_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " pw left join CWM_SYS_USER u on pw.C_ATTEND_ID_" + personWeightBM.getId() + "=u.id where u.ALL_NAME is not null and u.state='1' and pw.t_hangduan_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_id='" + hangduanId + "' order by pw.id asc";
            List<Map<String, Object>> personWeightList = jdbcTemplate.queryForList(sql);
//            personWeightBM.setReserve_filter("AND T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + hangduanId + "'");
//            List<Map<String, Object>> personWeightList = orientSqlEngine.getBmService().createModelQuery(personWeightBM).orderAsc("TO_NUMBER(C_DEPTH_" + depthDesityBM.getId() + ")").list();
            packageExcel(personWeightList, excel, i, personWeightBM);
        } else {
            if (!"".equals(toExportIds)) {
                String sql = "select pw.*,u.all_name from T_PERSON_WEIGHT_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " pw left join CWM_SYS_USER u on pw.C_ATTEND_ID_" + personWeightBM.getId() + "=u.id where u.state='1' and pw.t_hangduan_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_id='" + hangduanId + "'" + " and pw.id in(" + toExportIds + ") order by pw.id asc";
                List<Map<String, Object>> personWeightList = jdbcTemplate.queryForList(sql);
// personWeightBM.setReserve_filter("AND T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + hangduanId + "'" +
//                        " and id in (" + toExportIds + ")");
//                List<Map<String, Object>> personWeightList = orientSqlEngine.getBmService().createModelQuery(personWeightBM).orderAsc("TO_NUMBER(C_DEPTH_" + depthDesityBM.getId() + ")").list();
                packageExcel(personWeightList, excel, i, personWeightBM);
            }
        }
        for (int j = 0; j < headers.length; j++) {
            excel.column(j).autoWidth();
        }
        String fileName = CommonTools.Obj2String(hangduanMap.get("C_HANGDUAN_NAME_" + hangduanBM.getId()));
        String carryToolFolderPath = FtpFileUtil.EXPORT_ROOT + File.separator + "????????????????????????";
        String folder = FtpFileUtil.getRelativeUploadPath(carryToolFolderPath);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName + "??????.xls");
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    private void packageExcel(List<Map<String, Object>> personWeightList, Excel excel, int i[], IBusinessModel personWeightBM) {
        if (personWeightList != null && personWeightList.size() > 0) {
            for (Map map : personWeightList) {
                excel.cell(i[0], 0).value(map.get("ALL_NAME"));
                excel.cell(i[0], 1).value(map.get("C_DEPARTMENT_" + personWeightBM.getId()));
                excel.cell(i[0], 2).value(map.get("C_WEIGHT_" + personWeightBM.getId()));
                excel.cell(i[0], 3).value(map.get("C_JOINSHIP_DATE_" + personWeightBM.getId()));
                excel.cell(i[0], 4).value(map.get("C_LIVE_BOAT_" + personWeightBM.getId()));
                excel.cell(i[0], 5).value(map.get("C_TECHNICALPOST_" + personWeightBM.getId()));
                excel.cell(i[0], 6).value(map.get("C_HANGDUAN_POST_" + personWeightBM.getId()));
                excel.cell(i[0], 7).value(map.get("C_POLITICS_STATUS_" + personWeightBM.getId()));
                i[0]++;
            }
        }
    }


    public Map<String, Object> importDivingTaskData(TableEntity excelEntity) {
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        divingTaskBM.setReserve_filter("AND C_STATE_" + divingTaskBM.getId() + "='" + "?????????" + "'");
        List<Map> divingTaskList = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).orderAsc("C_TASK_NAME_" + divingTaskBM.getId()).list();

        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        List<Map> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanBM).orderAsc("C_HANGDUAN_NAME_" + hangduanBM.getId()).list();

        IBusinessModel divingStatisticsBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        List<Map> divingStatisticsList = orientSqlEngine.getBmService().createModelQuery(divingStatisticsBM).list();

        Map<String, Object> retVal = new HashMap<>();
        List<ImportDivingTaskBean> importDivingTaskBeanArrayList = UtilFactory.newArrayList();
        List<ImportDivingTaskBean> updateDivingTaskList = UtilFactory.newArrayList();
        List<ImportDivingTaskBean> newDivingTaskList = UtilFactory.newArrayList();
        List<String> recordInvalidDivingTask = UtilFactory.newArrayList();
        List<DataEntity> dataEntities = excelEntity.getDataEntityList();
        Map recordHangduanMap = UtilFactory.newHashMap();
        for (int j = 0; j < dataEntities.size(); j++) {
            ImportDivingTaskBean importDivingTaskBean = new ImportDivingTaskBean();
            //????????????????????????????????????????????????????????????
            List<FieldEntity> fieldEntities = dataEntities.get(j).getFieldEntityList();
            if (fieldEntities != null) {
                boolean isExistDivingTask = false;
                for (int i = 0; i < fieldEntities.size(); i++) {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    String name = fieldEntities.get(i).getName();
                    String value = fieldEntities.get(i).getValue();
                    int rowNumer = j + 2;
                    if ("??????".equals(name)) {
                        if (value == null || "".equals(value)) {
                            retVal.put("success", false);
                            retVal.put("msg", "???" + rowNumer + "??????" + i + "??????" + name + "??????????????????????????????????????????");
                            return retVal;
                        }
                        if (divingTaskList.size() > 0) {
                            for (Map divingTaskMap : divingTaskList) {
                                String hasTaskName = CommonTools.Obj2String(divingTaskMap.get(""));
                                if (value.equals(hasTaskName)) {
                                    importDivingTaskBean.setDivingTaskId(divingTaskMap.get("ID").toString());
                                    isExistDivingTask = true;
                                    break;
                                }
                            }
                        }
                        importDivingTaskBean.setTaskName(value);
                    } else if ("??????".equals(name)) {
                        if (value == null || "".equals(value)) {
                            retVal.put("success", false);
                            retVal.put("msg", "???" + rowNumer + "??????" + i + "??????" + name + "??????????????????????????????????????????");
                            return retVal;
                        }
                        importDivingTaskBean.setHangduanName(value);
                    } else if ("??????".equals(name)) {
                        if (value == null || "".equals(value)) {
                            retVal.put("success", false);
                            retVal.put("msg", "???" + rowNumer + "??????" + i + "??????" + name + "??????????????????????????????????????????");
                            return retVal;
                        }
                        importDivingTaskBean.setHangciName(value);
                    } else if ("????????????".equals(name)) {
                        importDivingTaskBean.setDivingDate(value);
                    } else if ("??????".equals(name)) {
                        importDivingTaskBean.setSeaArea(value);
                    } else if ("????????????".equals(name)) {
                        importDivingTaskBean.setHomeworkType(value);
                    } else if ("????????????(???)".equals(name)) {
                        importDivingTaskBean.setMaxDepth(value);
                    } else if ("??????".equals(name)) {
                        importDivingTaskBean.setZuoxianPerson(value);
                    } else if ("??????".equals(name)) {
                        importDivingTaskBean.setMainDriver(value);
                    } else if ("??????".equals(name)) {
                        importDivingTaskBean.setYouxianPerson(value);
                    } else if ("???????????????????????".equals(name)) {
                        importDivingTaskBean.setJingdu(value);
                    } else if ("???????????????????????".equals(name)) {
                        importDivingTaskBean.setWeidu(value);
                    } else if ("???/?????????".equals(name)) {
                        importDivingTaskBean.setEastWestHalfsphere(value);
                    } else if ("???/?????????".equals(name)) {
                        importDivingTaskBean.setSouthNorthHalfsphere(value);
                    } else if ("??????".equals(name)) {
                        importDivingTaskBean.setTimeZone(value);
                    } else if ("???????????????????????????19??????6??????".equals(name)) {
                        importDivingTaskBean.setBufangType(value);
                    } else if ("????????????".equals(name)) {
                        importDivingTaskBean.setWaterHours(value);
                    } else if ("????????????".equals(name)) {
                        importDivingTaskBean.setHomeWorkHours(value);
                    } else if ("??????????????????".equals(name)) {
                        importDivingTaskBean.setSampleSituation(value);
                    } else if ("??????????????????".equals(name)) {
                        importDivingTaskBean.setSeafloorEnvironmentDesp(value);
                    } else if ("??????????????????????????????".equals(name)) {
                        importDivingTaskBean.setWaterDownPictures(value);
                    } else if ("????????????????????????".equals(name)) {
                        importDivingTaskBean.setBufangCommandTime(value);
                    } else if ("??????????????????".equals(name)) {
                        importDivingTaskBean.setPersonComeinCabinTime(value);
                    } else if ("?????????????????????".equals(name)) {
                        importDivingTaskBean.setHatchCloseTime(value);
                    } else if ("??????????????????????????????".equals(name)) {
                        importDivingTaskBean.setBallastRemoveTime(value);
                    } else if ("?????????????????????".equals(name)) {
                        importDivingTaskBean.setDivingDEntryTime(value);
                    } else if ("??????????????????".equals(name)) {
                        importDivingTaskBean.setStartFillWaterTime(value);
                    } else if ("????????????????????????????????????????????????".equals(name)) {
                        importDivingTaskBean.setStartWorkTime(value);
                    } else if ("???????????????????????????".equals(name)) {
                        importDivingTaskBean.setWorkStartDepth(value);
                    } else if ("??????????????????????????????????????????".equals(name)) {
                        importDivingTaskBean.setEndWorkTime(value);
                    } else if ("???????????????????????????".equals(name)) {
                        importDivingTaskBean.setWorkEndDepth(value);
                    } else if ("?????????????????????".equals(name)) {
                        importDivingTaskBean.setFloatWaterTime(value);
                    } else if ("?????????????????????".equals(name)) {
                        importDivingTaskBean.setDivingDOutWaterTime(value);
                    } else if ("?????????????????????".equals(name)) {
                        importDivingTaskBean.setRecoverDeckTime(value);
                    } else if ("?????????????????????".equals(name)) {
                        importDivingTaskBean.setHatchOpenTime(value);
                    } else if ("??????????????????".equals(name)) {
                        importDivingTaskBean.setPersonComeOutCabinTime(value);
                    } else if ("????????????????????????".equals(name)) {
                        importDivingTaskBean.setOnceBufangSmallboatTime(value);
                    } else if ("????????????????????????".equals(name)) {
                        importDivingTaskBean.setOnceRecoverSmallboatTime(value);
                    } else if ("???????????????????????????".equals(name)) {
                        importDivingTaskBean.setBufangSmallboatDriverPeople(value);
                    } else if ("???????????????????????????".equals(name)) {
                        importDivingTaskBean.setBufangSmallboatAssistant(value);
                    } else if ("????????????".equals(name)) {
                        importDivingTaskBean.setTuolanPeople(value);
                    } else if ("??????????????????".equals(name)) {
                        importDivingTaskBean.setTuolanAssistant(value);
                    } else if ("????????????????????????????????????".equals(name)) {
                        importDivingTaskBean.setBufangMaxWindSpeed(value);
                    } else if ("????????????????????????????????????".equals(name)) {
                        importDivingTaskBean.setBufangAverageWindSpeed(value);
                    } else if ("????????????????????????".equals(name)) {
                        importDivingTaskBean.setBufangLangHeight(value);
                    } else if ("??????????????????????????????".equals(name)) {
                        importDivingTaskBean.setBufangSeaStateEstimate(value);
                    } else if ("????????????????????????".equals(name)) {
                        importDivingTaskBean.setTwiceBufangSmallboatTime(value);
                    } else if ("???????????????????????????".equals(name)) {
                        importDivingTaskBean.setRecoverSmallboatDperson(value);
                    } else if ("???????????????????????????".equals(name)) {
                        importDivingTaskBean.setRecoverSmallboatAssistant(value);
                    } else if ("????????????".equals(name)) {
                        importDivingTaskBean.setGualanPeople(value);
                    } else if ("??????????????????".equals(name)) {
                        importDivingTaskBean.setGualanAssistant(value);
                    } else if ("????????????????????????????????????".equals(name)) {
                        importDivingTaskBean.setRecoverMaxWindSpeed(value);
                    } else if ("????????????????????????????????????".equals(name)) {
                        importDivingTaskBean.setRecoverMaxAverageWindSpeed(value);
                    } else if ("????????????????????????".equals(name)) {
                        importDivingTaskBean.setRecoverLangHeight(value);
                    } else if ("??????????????????????????????".equals(name)) {
                        importDivingTaskBean.setRecoverSeaStateEstimate(value);
                    } else if ("????????????".equals(name)) {
                        importDivingTaskBean.setSpecialVersion(value);
                    }
                }
                if (isExistDivingTask) {
                    updateDivingTaskList.add(importDivingTaskBean);
                } else {
                    //????????????????????????????????????ID
                    int isExistRecord = 0;
                    if (recordHangduanMap != null) {
                        String hangduanName = recordHangduanMap.get("name").toString();
                        String hangduanId = recordHangduanMap.get("id").toString();
                        if (importDivingTaskBean.getHangduanName().equals(hangduanName)) {
                            importDivingTaskBean.setHangduanId(hangduanId);
                            newDivingTaskList.add(importDivingTaskBean);
                            ++isExistRecord;
                        }
                    }
                    if (isExistRecord == 0) {
                        int count = 0;
                        if (hangduanList.size() > 0) {
                            for (Map hangduanMap : hangduanList) {
                                String hasHangduanName = CommonTools.Obj2String(hangduanMap.get("C_HANGDUAN_NAME_" + hangduanBM.getId()));
                                String hasHangduanId = hangduanMap.get("ID").toString();
                                if (importDivingTaskBean.getHangduanName().equals(hasHangduanName)) {
                                    importDivingTaskBean.setHangduanId(hasHangduanId);
                                    newDivingTaskList.add(importDivingTaskBean);
                                    count++;
                                    recordHangduanMap = UtilFactory.newHashMap();
                                    recordHangduanMap.put("name", importDivingTaskBean.getHangduanName());
                                    recordHangduanMap.put("id", importDivingTaskBean.getHangduanId());
                                    break;
                                }
                            }
                        }
                        //???????????????????????????
                        if (count == 0) {
                            recordInvalidDivingTask.add(importDivingTaskBean.getTaskName());
                        }
                    }
                }
            }
        }
        if (updateDivingTaskList != null && updateDivingTaskList.size() > 0) {
            for (ImportDivingTaskBean importDivingTaskBean : updateDivingTaskList) {
                String divingTaskId = importDivingTaskBean.getDivingTaskId();
                Map divingTaskMap = UtilFactory.newHashMap();
                Map divingStatisticsMap = UtilFactory.newHashMap();
                commonDivingStatisticMap(divingTaskBM, divingStatisticsBM, importDivingTaskBean, divingTaskMap, divingStatisticsMap);
                orientSqlEngine.getBmService().updateModelData(divingTaskBM, divingTaskMap, divingTaskId);
                if (divingStatisticsList.size() > 0) {
                    for (Map statisticsMap : divingStatisticsList) {
                        String refDivingTaskId = CommonTools.Obj2String(statisticsMap.get("C_DIVING_TASK_" + divingStatisticsBM.getId()));
                        if (divingTaskId.equals(refDivingTaskId)) {

                        }
                    }
                }
            }
        }
        if (newDivingTaskList != null && newDivingTaskList.size() > 0) {

        }
        return retVal;
    }

    public void commonDivingStatisticMap(IBusinessModel divingTaskBM, IBusinessModel divingStatisticsBM, ImportDivingTaskBean importDivingTaskBean, Map divingTaskMap, Map divingStatisticsMap) {
        divingTaskMap.put("C_SEA_AREA_" + divingTaskBM.getId(), importDivingTaskBean.getSeaArea());
        divingTaskMap.put("C_JINGDU_" + divingTaskBM.getId(), importDivingTaskBean.getJingdu());
        divingTaskMap.put("C_WEIDU_" + divingTaskBM.getId(), importDivingTaskBean.getWeidu());
    }

    public List<Map> divingTaskDataImport(MultipartFile file) throws Exception {
        List retVal = new ArrayList();
        IBusinessModel divingStatisticsBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel sampleSituationBM = businessModelService.getBusinessModelBySName(PropertyConstant.SAMPLE_SITUATION, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        List<Map> sampleSituationList = orientSqlEngine.getBmService().createModelQuery(sampleSituationBM).list();
        divingTaskBM.setReserve_filter("AND C_STATE_" + divingTaskBM.getId() + "='" + "?????????" + "'");
        List<Map> divingTaskList = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).orderAsc("C_TASK_NAME_" + divingTaskBM.getId()).list();
        List<Map> divingStatisticsList = orientSqlEngine.getBmService().createModelQuery(divingStatisticsBM).list();
        List<Map> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanBM).list();
        List<String> invalidTaskList = UtilFactory.newArrayList();
        IBusinessModel[] models = {divingStatisticsBM};
        //??????sheet????????????
        List<List<Map<String, String>>> excelList = excelImport.importFile(file, models);
        for (int i = 0; i < excelList.size(); i++) {
            for (Map<String, String> dataMap : excelList.get(i)) {
                //??????????????????
                String taskName = dataMap.get("C_DIVING_TASK_" + divingStatisticsBM.getId());
                String hangduanName = dataMap.get("C_HANGDUAN_" + divingStatisticsBM.getId());
                String divingDOutWaterTime = CommonTools.Obj2String(dataMap.get("DIVINGDOUTWATERTIME_" + divingStatisticsBM.getId()));
                String divingDEntryTime = CommonTools.Obj2String(dataMap.get("DIVINGDENTRYTIME_" + divingStatisticsBM.getId()));
                String startWorkTime = CommonTools.Obj2String(dataMap.get("STARTWORKTIME_" + divingStatisticsBM.getId()));
                String endWorkTime = CommonTools.Obj2String(dataMap.get("ENDWORKTIME_" + divingStatisticsBM.getId()));
                String sampleSituation = CommonTools.Obj2String(dataMap.get("C_SAMPLE_SITUATION_" + divingStatisticsBM.getId()));
                List<String> newSampleSituationList = UtilFactory.newArrayList();
                if (!"".equals(sampleSituation)) {
                    sampleSituation = sampleSituation.replaceAll("\r|\n", "");
                    String regex = ",|???";
                    String sampleSituationArray[] = sampleSituation.split(regex);
                    newSampleSituationList = new ArrayList<>(Arrays.asList(sampleSituationArray));
                }
                long waterTimeLong = 0;
                if (!"".equals(divingDOutWaterTime) && !"".equals(divingDEntryTime)) {
                    long divingDoutWaterMs = Long.parseLong(divingDOutWaterTime);
                    long divingDEntryWaterMs = Long.parseLong(divingDEntryTime);
                    //?????????
                    if (divingDoutWaterMs <= divingDEntryWaterMs) {
                        waterTimeLong = 24 * 60 * 60 * 1000 - divingDEntryWaterMs + divingDoutWaterMs;
                    } else {
                        waterTimeLong = divingDoutWaterMs - divingDEntryWaterMs;
                    }
                }
                long workTime = 0;
                if (!"".equals(startWorkTime) && !"".equals(endWorkTime)) {
                    long startWorkMs = Long.parseLong(startWorkTime);
                    long endWorkMs = Long.parseLong(endWorkTime);
                    //????????????????????????
                    if (endWorkMs <= startWorkMs) {
                        workTime = 24 * 60 * 60 * 1000 - startWorkMs + endWorkMs;
                    } else {
                        workTime = endWorkMs - startWorkMs;
                    }
                }
                dataMap.put("C_WATER_TIME_LONG_" + divingStatisticsBM.getId(), String.valueOf(waterTimeLong));
                dataMap.put("C_WORK_TIME_LONG_" + divingStatisticsBM.getId(), String.valueOf(workTime));
                String taskId = "";
                String hangduanId = "";
                String hangciId = "";
                String divingStatisticId = "";
                if (divingTaskList.size() > 0) {
                    for (Map divingTaskMap : divingTaskList) {
                        String hasTaskName = CommonTools.Obj2String(divingTaskMap.get("C_TASK_NAME_" + divingTaskBM.getId()));
                        if (taskName.equals(hasTaskName)) {
                            taskId = divingTaskMap.get("ID").toString();
                            hangduanId = CommonTools.Obj2String(divingTaskMap.get("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
                            hangciId = CommonTools.Obj2String(divingTaskMap.get("T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
                            divingTaskMap.put("C_SEA_AREA_" + divingTaskBM.getId(), dataMap.get("C_SEA_AREA_" + divingStatisticsBM.getId()));
                            divingTaskMap.put("C_JINGDU_" + divingTaskBM.getId(), dataMap.get("C_LONGITUDE_" + divingStatisticsBM.getId()));
                            divingTaskMap.put("C_WEIDU_" + divingTaskBM.getId(), dataMap.get("C_LATITUDE_" + divingStatisticsBM.getId()));
                            orientSqlEngine.getBmService().updateModelData(divingTaskBM, divingTaskMap, taskId);
                            dataMap.put("divingTaskId", taskId);
                            break;
                        }
                    }
                }
                if (!"".equals(taskId)) {
                    int count = 0;
                    if (divingStatisticsList.size() > 0) {
                        for (Map statisticsMap : divingStatisticsList) {
                            divingStatisticId = statisticsMap.get("ID").toString();
                            String refTaskId = CommonTools.Obj2String(statisticsMap.get("T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
                            if (taskId.equals(refTaskId)) {
                                orientSqlEngine.getBmService().updateModelData(divingStatisticsBM, dataMap, divingStatisticId);
                                if (sampleSituationList.size() > 0) {
                                    for (Map sampleMap : sampleSituationList) {
                                        String sampleName = CommonTools.Obj2String(sampleMap.get("C_NAME_" + sampleSituationBM.getId()));
                                        String refDivingStatisticId = CommonTools.Obj2String(sampleMap.get("T_DIVING_STATISTICS_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
                                        if (divingStatisticId.equals(refDivingStatisticId)) {
                                            if (newSampleSituationList.size() > 0) {
                                                for (String string : newSampleSituationList) {
                                                    if (sampleName.equals(string)) {
                                                        newSampleSituationList.remove(string);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                count++;
                                break;
                            }
                        }
                    }
                    if (count == 0) {
                        dataMap.put("T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", taskId);
                        dataMap.put("T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangciId);
                        dataMap.put("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangduanId);
                        divingStatisticId = orientSqlEngine.getBmService().insertModelData(divingStatisticsBM, dataMap);
                    }
                } else {
                    if (hangduanList.size() > 0) {
                        for (Map hangduanMap : hangduanList) {
                            String hasHangduanName = CommonTools.Obj2String(hangduanMap.get("C_HANGDUAN_NAME_" + hangduanBM.getId()));
                            if (hangduanName.equals(hasHangduanName)) {
                                hangduanId = hangduanMap.get("ID").toString();
                                hangciId = hangduanMap.get("T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID").toString();
                                break;
                            }
                        }
                    }
                    if (!"".equals(hangduanId)) {
                        Map divingTaskMap = UtilFactory.newHashMap();
                        divingTaskMap.put("C_SEA_AREA_" + divingTaskBM.getId(), dataMap.get("C_SEA_AREA_" + divingStatisticsBM.getId()));
                        divingTaskMap.put("C_JINGDU_" + divingTaskBM.getId(), dataMap.get("C_LONGITUDE_" + divingStatisticsBM.getId()));
                        divingTaskMap.put("C_WEIDU_" + divingTaskBM.getId(), dataMap.get("C_LATITUDE_" + divingStatisticsBM.getId()));
                        divingTaskMap.put("C_TASK_NAME_" + divingTaskBM.getId(), dataMap.get("C_DIVING_TASK_" + divingStatisticsBM.getId()));
                        divingTaskMap.put("C_STATE_" + divingTaskBM.getId(), "?????????");
                        divingTaskMap.put("C_TASK_TYPE_" + divingTaskBM.getId(), "????????????");
                        divingTaskMap.put("T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangciId);
                        divingTaskMap.put("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangduanId);
                        String divingTaskId = orientSqlEngine.getBmService().insertModelData(divingTaskBM, divingTaskMap);
                        dataMap.put("divingTaskId", taskId);
                        dataMap.put("T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", divingTaskId);
                        dataMap.put("T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangciId);
                        dataMap.put("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangduanId);
                        divingStatisticId = orientSqlEngine.getBmService().insertModelData(divingStatisticsBM, dataMap);
                    } else {
                        dataMap.put("divingTaskId", "");
                        invalidTaskList.add(taskName);
                    }
                }
                if (!"".equals(divingStatisticId)) {
                    if (newSampleSituationList.size() > 0) {
                        for (String string : newSampleSituationList) {
                            Map sampleMap = UtilFactory.newHashMap();
                            sampleMap.put("C_NAME_" + sampleSituationBM.getId(), string);
                            sampleMap.put("T_DIVING_STATISTICS_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", divingStatisticId);
                            orientSqlEngine.getBmService().insertModelData(sampleSituationBM, sampleMap);
                        }
                    }
                }
            }
        }
        Map resultMap = UtilFactory.newHashMap();
        resultMap.put("divingTaskDataList", excelList.get(0));
        resultMap.put("invalidTaskList", invalidTaskList);
        retVal.add(resultMap);
//        retVal.add(excelList.get(0));
//        retVal.add(invalidTaskList);
//        retVal.put("divingTaskDataList", excelList.get(0));
//        retVal.put("success", true);
        return retVal;
    }

    public String exportHangciHangduanData(boolean exportAll) {
        IBusinessModel hangciBM = businessModelService.getBusinessModelBySName
                (PropertyConstant.HANGCI, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName
                (PropertyConstant.HANGDUAN, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        Excel excel = new Excel();
        excel.setWorkingSheet(0).sheetName("??????????????????");
        if (exportAll) {
            //select t.*, t.rowid,y.c_hangci_name_3206 from T_HANGDUAN_480 t left join T_HANGCI_480 y on t.t_hangci_480_id=y.id;
            String hangduanSql = "select t.*,y.c_hangci_name_" + hangciBM.getId() + " from T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " t left join T_HANGCI_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " y on t.t_hangci_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_id=y.id";
            List<Map<String, Object>> hangduanList = jdbcTemplate.queryForList(hangduanSql);
            exportDivingStatisticInfoData(excel, hangduanList, hangduanBM, hangciBM);
        }
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT + File.separator + "??????????????????");
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = "??????????????????.xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    public void exportDivingStatisticInfoData(Excel excel, List<Map<String, Object>> list, IBusinessModel hangduanBM, IBusinessModel hangciBM) {
        Object[] headers = new Object[]{"??????", "??????", "????????????", "???????????????", "????????????", "????????????", "????????????", "????????????", "??????????????????",
                "????????????", "??????????????????", "?????????????????????", "???????????????????????????", "1#(??????????????????)", "2#(??????)", "3#(????????????)", "??????????????????", "????????????", "??????????????????",
                "????????????", "??????????????????"};
        excel.row(0).value(headers);
        final int[] i = {1};
        if (list != null && list.size() > 0) {
            for (Map map : list) {
                excel.cell(i[0], 0).value(StringUtil.decodeUnicode(CommonTools.Obj2String(map.get("C_HANGCI_NAME_"+hangciBM.getId())))).warpText(true);
                excel.cell(i[0], 1).value(CommonTools.Obj2String(map.get("C_HANGDUAN_NAME_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 2).value(CommonTools.Obj2String(map.get("C_PLAN_START_TIME_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 3).value(CommonTools.Obj2String(map.get("C_PLAN_END_TIME_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 4).value(CommonTools.Obj2String(map.get("divingTimes_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 5).value(CommonTools.Obj2String(map.get("validDiving_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 6).value(CommonTools.Obj2String(map.get("supportProject_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 7).value(CommonTools.Obj2String(map.get("hangciQuality_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 8).value(CommonTools.Obj2String(map.get("hangciLeaderExpert_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 9).value(CommonTools.Obj2String(map.get("hangciLeader_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 10).value(CommonTools.Obj2String(map.get("hangciChiefExpert_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 11).value(CommonTools.Obj2String(map.get("hangciChiefScientist_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 12).value(CommonTools.Obj2String(map.get("hangciChiefSAssistan_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 13).value(CommonTools.Obj2String(map.get("firstDivingDepartor_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 14).value(CommonTools.Obj2String(map.get("secondMaster_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 15).value(CommonTools.Obj2String(map.get("thirdWaterSupport_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 16).value(CommonTools.Obj2String(map.get("hangciTopRecord_" + hangduanBM.getId()))).warpText(true);
                excel.cell(i[0], 17).value(CommonTools.Obj2String(map.get("hangciResult_" + hangduanBM.getId()))).warpText(true);
                i[0]++;
            }
        }
        for (int j = 0; j < headers.length; j++) {
            excel.column(j).autoWidth();
        }
    }
}
