package com.orient.weibao.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelServiceImpl;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.DeleteModelDataEvent;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.DeleteModelDataEventParam;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.mongorequest.model.CommonResponse;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.web.base.*;
import com.orient.weibao.bean.HistoryTask.ImportHangciBean;
import com.orient.weibao.bean.flowPost.FlowPostData;
import com.orient.weibao.business.FormTemplateMgrBusiness;
import com.orient.weibao.business.TaskPrepareMgrBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.utils.DeCompress;
import com.orient.weibao.utils.WeibaoPropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-12-22 11:09
 */
@Controller
@RequestMapping("/taskPrepareController")
public class TaskPrepareMgrController extends BaseController {

    @Autowired
    TaskPrepareMgrBusiness taskPrepareMgrBusiness;
    @Autowired
    BusinessModelServiceImpl businessModelService;
    @Autowired
    ISqlEngine orientSqlEngine;
    @Autowired
    MetaDAOFactory metaDAOFactory;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private FileServerConfig fileServerConfig;
    @Autowired
    FormTemplateMgrController formTemplateMgrController;

    /**
     * ?????????????????????
     *
     * @param: request
     * @param: taskId
     * @param: platformId
     * @param: modelId
     * @return:
     */
    @RequestMapping("saveMxGraphXml")
    @ResponseBody
    public AjaxResponseData<String> saveMxGraphXml(HttpServletRequest request, String taskId, String flowTempTypeId) throws Exception {
        AjaxResponseData retVal = new AjaxResponseData();
        String xmlStr = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replace("&#xa;", "\n");
        xmlStr = "<mxGraphModel>" + xmlStr + "</mxGraphModel>";
        taskPrepareMgrBusiness.saveMxGraphXml(xmlStr, taskId, flowTempTypeId);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ??????????????????
     *
     * @param: taskId
     * @return:
     */
    @RequestMapping("getXmlStr")
    @ResponseBody
    public AjaxResponseData<String> getXmlStr(String taskId, String flowTempTypeId) {
        AjaxResponseData retVal = new AjaxResponseData();
        String xmlStr = taskPrepareMgrBusiness.getXmlStr(taskId, flowTempTypeId);
        retVal.setSuccess(true);
        retVal.setResults(xmlStr);
        return retVal;
    }

    /**
     * ???????????????Mxgraph??????????????????????????????????????????????????????????????????
     *
     * @param nodeId
     * @param flowTempTypeId
     * @param taskId
     * @return
     */
    @RequestMapping("deleteCellMxgraph")
    @ResponseBody
    public AjaxResponseData<String> deleteCellMxgraph(String nodeId, String flowTempTypeId, String taskId) {
        AjaxResponseData retVal = new AjaxResponseData();
        taskPrepareMgrBusiness.deleteCellMxgraph(nodeId, flowTempTypeId, taskId);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ?????????????????????????????????
     *
     * @param:
     * @return:
     */
    @RequestMapping("queryCheckTempInstList")
    @ResponseBody
    public JSONObject queryCheckTempInstList() {
        List<Map> checkTypeList = taskPrepareMgrBusiness.queryCheckTempInstList();
        JSONArray jsonArray = JSONArray.fromObject(checkTypeList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("results", jsonArray);
        return jsonObject;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param: checkId
     * @param: taskId
     * @param: flowTempTypeId  ??????????????????ID
     * @param: flowId  ????????????ID
     * @return:
     */
    @RequestMapping("bindCheckTableTemp")
    @ResponseBody
    public CommonResponseData bindCheckTableTemp(String checkId, String taskId, String nodeId, String nodeText, String flowTempTypeId, String flowId, String deviceCycleId) {
        return taskPrepareMgrBusiness.bindCheckTableTemp(checkId, taskId, nodeId, nodeText, flowTempTypeId, flowId, deviceCycleId);
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param: id
     * @return:
     */
    @RequestMapping("deleteCheckTable")
    @ResponseBody
    public AjaxResponseData<String> deleteCheckTable(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        taskPrepareMgrBusiness.deleteCheckTable(id);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ??????????????????
     *
     * @param: postId
     * @param: taskId
     * @param: nodeId
     * @param: nodeText
     * @return:
     */
    @RequestMapping("bindPostTableTemp")
    @ResponseBody
    public CommonResponseData bindPostTableTemp(String postId, String taskId, String nodeId, String nodeText, String flowTempTypeId, String flowId) {
        return taskPrepareMgrBusiness.bindPostTableTemp(postId, taskId, nodeId, nodeText, flowTempTypeId, flowId);
    }

    /**
     * ??????????????????
     *
     * @param: id
     * @return:
     */
    @RequestMapping("deleteAttendPostData")
    @ResponseBody
    public AjaxResponseData deleteAttendPostData(String params) throws Exception {
        AjaxResponseData retVal = new AjaxResponseData();
        taskPrepareMgrBusiness.deleteAttendPostData(params);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ??????????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping("deleteTempAttendPostData")
    @ResponseBody
    public AjaxResponseData deleteTempAttendPostData(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        taskPrepareMgrBusiness.deleteTempAttendPostData(id);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @param taskId
     * @return
     */
    @RequestMapping("queryAttendPostData")
    @ResponseBody
    public ExtGridData<Map> queryAttendPostData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort, String taskId) {
        ExtGridData<Map> retVal = taskPrepareMgrBusiness.queryAttendPostData(orientModelId, isView, page, limit, customerFilter, true, sort, taskId);
        return retVal;
    }

    @RequestMapping("isSuperAdmin")
    @ResponseBody
    public AjaxResponseData<Object> isSuperAdmin() {
        AjaxResponseData<Object> responseData = new AjaxResponseData<>();
        boolean isSuperAdmin = taskPrepareMgrBusiness.isSuperAdmin();
        responseData.setResults(isSuperAdmin);
        return responseData;
    }

    /**
     * ??????????????????
     *
     * @param modelId
     * @param formData
     * @param flowId
     * @return
     */
    @RequestMapping("saveHangciData")
    @ResponseBody
    public AjaxResponseData<String> saveHangciData(String modelId, String formData, String flowId) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("??????????????????");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("????????????");
//            Map permsMap = new HashMap<>();
//            permsMap.put("ID", eventParam.getDataMap().get("ID"));
//            permsMap.put("refreshTree", true);
//            retVal.setResults(permsMap);
            return retVal;
        }
    }

    /**
     * ??????????????????
     *
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("updateHangciData")
    @ResponseBody
    public AjaxResponseData updateHangciData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("??????????????????");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("????????????");
//            Map permsMap = new HashMap<>();
//            permsMap.put("refreshTree", true);
//            retVal.setResults(permsMap);
        }
        return retVal;
    }

    /**
     * ????????????
     *
     * @param modelId
     * @param formData
     * @param flowId
     * @return
     */
    @RequestMapping("saveHangduanData")
    @ResponseBody
    public AjaxResponseData<String> saveHangduanData(String modelId, String formData, String flowId) {
        IBusinessModel attendPersonBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("??????????????????");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
            dataMap.put("T_HANGCI_" + schemaId + "_ID", flowId);
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("????????????");
//            Map permsMap = new HashMap<>();
//            permsMap.put("ID", eventParam.getDataMap().get("ID"));
//            permsMap.put("refreshTree", true);
//            retVal.setResults(permsMap);
            return retVal;
        }
    }

    /**
     * ??????????????????
     *
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("updateHangduanData")
    @ResponseBody
    public AjaxResponseData updateHangduanData(String modelId, String formData) {
        IBusinessModel attendPersonBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("??????????????????");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String hangduanId = CommonTools.Obj2String(dataMap.get("ID"));
            String hangduanAttendPersons = CommonTools.Obj2String(dataMap.get("C_ATTEND_PERSON_" + modelId));
            attendPersonBM.setReserve_filter("AND T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + hangduanId + "'");
            List<Map> attendPersonList = orientSqlEngine.getBmService().createModelQuery(attendPersonBM).list();
            List<String> duanAttendPersonList = UtilFactory.newArrayList();
            if (!"".equals(hangduanAttendPersons)) {
                String attendPersonId[] = hangduanAttendPersons.split(",");
                duanAttendPersonList = new ArrayList<>(Arrays.asList(attendPersonId));
            }

            if (attendPersonList.size() > 0) {
                Set<String> taskIdList = new HashSet<>();
                for (Map personMap : attendPersonList) {
                    String attendPersonKeyId = CommonTools.Obj2String(personMap.get("ID"));
                    String attendPersonId = CommonTools.Obj2String(personMap.get("C_ATTEND_PERSON_" + attendPersonBM.getId()));
                    String taskId = CommonTools.Obj2String(personMap.get("T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID"));
                    if (!taskIdList.contains(taskId)) {
                        String rawAttendPersonId[] = hangduanAttendPersons.split(",");
                        List<String> rawduanAttendPersonList = new ArrayList<>(Arrays.asList(rawAttendPersonId));
                        duanAttendPersonList = rawduanAttendPersonList;
                    }
                    if (duanAttendPersonList.contains(attendPersonId)) {
                        //?????????????????????
                        duanAttendPersonList.removeIf(s -> s.equals(attendPersonId));
//                        String remainAttendPesonIds = Joiner.on(",").join(duanAttendPersonList);
                    } else {
                        orientSqlEngine.getBmService().deleteCascade(attendPersonBM, attendPersonKeyId);
                    }
                    taskIdList.add(taskId);
                }
                if (duanAttendPersonList.size() > 0) {
                    for (String taskId : taskIdList) {
                        for (String insertPersonId : duanAttendPersonList) {
                            Map personMap = UtilFactory.newHashMap();
                            personMap.put("C_ATTEND_PERSON_" + attendPersonBM.getId(), insertPersonId);
                            personMap.put("T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", taskId);
                            personMap.put("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangduanId);
                            orientSqlEngine.getBmService().insertModelData(attendPersonBM, personMap);
                        }
                    }
                }
            } else {
                String taskSql = "select id from t_diving_task_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " where t_hangduan_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID=?";
                List<Map<String, Object>> taskList = jdbcTemplate.queryForList(taskSql, hangduanId);
                if (duanAttendPersonList.size() > 0) {
                    for (Map taskMap : taskList) {
                        for (String insertPersonId : duanAttendPersonList) {
                            Map personMap = UtilFactory.newHashMap();
                            personMap.put("C_ATTEND_PERSON_" + attendPersonBM.getId(), insertPersonId);
                            personMap.put("T_DIVING_TASK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", taskMap.get("ID"));
                            personMap.put("T_HANGDUAN_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", hangduanId);
                            orientSqlEngine.getBmService().insertModelData(attendPersonBM, personMap);
                        }
                    }
                }
            }
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("????????????");
//            Map permsMap = new HashMap<>();
//            permsMap.put("refreshTree", true);
//            retVal.setResults(permsMap);
        }
        return retVal;
    }

    /**
     * ????????????????????????
     *
     * @param: modelId
     * @param: formData
     * @param: flowId ??????ID
     * @return:
     */
    @RequestMapping("saveDivingTaskData")
    @ResponseBody
    public AjaxResponseData<String> saveDivingTaskData(String modelId, String formData, String flowId) {
        AjaxResponseData retVal = new AjaxResponseData();
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel nodeDesignBM = businessModelService.getBusinessModelBySName(PropertyConstant.NODE_DESIGN, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel attendPersonBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headerInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);

        String nodeDesignTableId = nodeDesignBM.getId();
        String refPostTableId = refPostBM.getId();
        String checkTempInstTableId = checkTempInstBM.getId();

        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("??????????????????");
            retVal.setSuccess(false);
            return retVal;
        } else {
            hangduanBM.setReserve_filter("and id='" + flowId + "'");
            List<Map> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanBM).list();
            if (hangduanList.size() != 0) {
                String hangciId = CommonTools.Obj2String(hangduanList.get(0).get("T_HANGCI_" + schemaId + "_ID"));
                String nodeDesignTempId = "";
                Map formDataMap = JsonUtil.json2Map(formData);
                Map dataMap = (Map) formDataMap.get("fields");
                //??????????????????
                dataMap.put("T_HANGDUAN_" + schemaId + "_ID", flowId);
                dataMap.put("T_HANGCI_" + schemaId + "_ID", hangciId);
                dataMap.put("C_STATE_" + modelId, "?????????");
                String newTaskId = orientSqlEngine.getBmService().insertModelData(divingTaskBM, dataMap);

                //????????????????????????
                String attendPersonIds = CommonTools.Obj2String(hangduanList.get(0).get("C_ATTEND_PERSON_" + hangduanBM.getId()));
                if (attendPersonIds != "") {
                    String attendPersonId[] = attendPersonIds.split(",");
                    List<String> attendPersonList = new ArrayList<>(Arrays.asList(attendPersonId));
                    for (String everyPersonId : attendPersonList) {
                        Map insertPersonMap = UtilFactory.newHashMap();
                        insertPersonMap.put("C_ATTEND_PERSON_" + attendPersonBM.getId(), everyPersonId);
                        insertPersonMap.put("T_DIVING_" + schemaId + "_ID", newTaskId);
                        insertPersonMap.put("T_HANGDUAN_" + schemaId + "_ID", flowId);
                        String insertSql = "insert into T_ATTEND_PERSON_" + schemaId + "( ID,C_ATTEND_PERSON_" + attendPersonBM.getId() + ",T_DIVING_TASK_" + schemaId + "_ID," + "T_HANGDUAN_" + schemaId + "_ID)" + " values (" + "SEQ_T_ATTEND_PERSON_" + schemaId + ".nextval," + everyPersonId + "," + newTaskId + "," + flowId + ")";
                        metaDAOFactory.getJdbcTemplate().update(insertSql);
//                                orientSqlEngine.getBmService().insertModelData(attendPersonBM, insertPersonMap);
                    }
                }

                String flowTempType = CommonTools.Obj2String(dataMap.get("C_FLOW_TEMP_TYPE_" + modelId));
                nodeDesignBM.appendCustomerFilter(new CustomerFilter("T_FLOW_TEMP_TYPE_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, flowTempType));
                List<Map> nodeDesignTemp = orientSqlEngine.getBmService().createModelQuery(nodeDesignBM).list();
                //????????????????????????
                if (nodeDesignTemp.size() != 0) {
                    nodeDesignTempId = CommonTools.Obj2String(nodeDesignTemp.get(0).get("ID"));
                    //???????????????????????????????????????????????????????????????
                    Map<String, String> nodeDesignMap = UtilFactory.newHashMap();
                    nodeDesignMap.put("C_VERSON_" + nodeDesignTableId, "0");
                    nodeDesignMap.put("C_EDIT_TIME_" + nodeDesignTableId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    nodeDesignMap.put("T_DIVING_TASK_" + schemaId + "_ID", newTaskId);
                    nodeDesignMap.put("C_XML_" + nodeDesignTableId, CommonTools.Obj2String(nodeDesignTemp.get(0).get("C_XML_" + nodeDesignTableId)));
                    nodeDesignMap.put("C_FLOW_TEMP_TYPE_" + nodeDesignTableId, flowTempType);
                    String nodeDesignId = orientSqlEngine.getBmService().insertModelData(nodeDesignBM, nodeDesignMap);
                    //??????????????????
                    refPostBM.appendCustomerFilter(new CustomerFilter("T_FLOW_TEMP_TYPE_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, flowTempType));
                    List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
                    if (refPostList.size() > 0) {
                        for (Map<String, String> refMap : refPostList) {
                            Map postMap = UtilFactory.newHashMap();
                            postMap.put("C_POST_ID_" + refPostTableId, refMap.get("C_POST_ID_" + refPostTableId));
                            postMap.put("C_NODE_ID_" + refPostTableId, refMap.get("C_NODE_ID_" + refPostTableId));
                            postMap.put("C_NODE_TEXT_" + refPostTableId, refMap.get("C_NODE_TEXT_" + refPostTableId));
                            postMap.put("C_POST_TYPE_" + refPostTableId, refMap.get("C_POST_TYPE_" + refPostTableId));
                            postMap.put("T_DIVING_TASK_" + schemaId + "_ID", newTaskId);
                            orientSqlEngine.getBmService().insertModelData(refPostBM, postMap);
                        }
                    }
                    //???????????????????????????
                    checkTempInstBM.appendCustomerFilter(new CustomerFilter("T_FLOW_TEMP_TYPE_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, flowTempType));
                    List<Map> checkInstList = orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).list();
                    if (checkInstList.size() > 0) {
                        List<String> oldCheckInstIdsList = UtilFactory.newArrayList();
                        List<String> newCheckInstIdsList = UtilFactory.newArrayList();
                        Map oldCheckInstTypeMap = UtilFactory.newHashMap();
                        for (Map<String, String> checkMap : checkInstList) {
                            String oldCheckInstId = (String) checkMap.get("ID");
                            String oldCheckInstType = CommonTools.Obj2String(checkMap.get("C_INS_TYPE_" + checkTempInstBM.getId()));
                            Map checkInstMap = UtilFactory.newHashMap();
                            checkInstMap.put("C_NODE_ID_" + checkTempInstTableId, checkMap.get("C_NODE_ID_" + checkTempInstTableId));
                            checkInstMap.put("C_NODE_TEXT_" + checkTempInstTableId, checkMap.get("C_NODE_TEXT_" + checkTempInstTableId));
                            checkInstMap.put("C_NAME_" + checkTempInstTableId, checkMap.get("C_NAME_" + checkTempInstTableId));
                            checkInstMap.put("C_INS_TYPE_" + checkTempInstTableId, checkMap.get("C_INS_TYPE_" + checkTempInstTableId));
                            String checkTempId = checkMap.get("C_CHECK_TEMP_ID_" + checkTempInstTableId);
                            checkInstMap.put("C_CHECK_TEMP_ID_" + checkTempInstTableId, checkTempId);
                            String isRepeatUpload = checkMap.get("C_IS_REPEAT_UPLOAD_" + checkTempInstTableId);
                            isRepeatUpload = "".equals(isRepeatUpload) ? "???" : isRepeatUpload;
                            checkInstMap.put("C_IS_REPEAT_UPLOAD_" + checkTempInstTableId, isRepeatUpload);
                            checkInstMap.put("C_CHECK_STATE_" + checkTempInstTableId, "?????????");
                            checkInstMap.put("T_DIVING_TASK_" + schemaId + "_ID", newTaskId);
                            checkInstMap.put("T_HANGCI_" + schemaId + "_ID", hangciId);
                            String newCheckInstId = orientSqlEngine.getBmService().insertModelData(checkTempInstBM, checkInstMap);
                            //???????????????ID
//                            String checkInstId = (String) checkMap.get("ID");
//                            //????????????????????????????????????
//                            StringBuilder headerSql = new StringBuilder();
//                            headerSql.append("select * from T_CHECK_HEADER_INST_" + schemaId).append(" where 1=1").append(" and T_CHECK_TEMP_INST_" + schemaId + "_ID =?").append("order by ID ASC");
//                            List<Map<String, Object>> headerList = metaDAOFactory.getJdbcTemplate().queryForList(headerSql.toString(), checkInstId);
//                            taskPrepareMgrBusiness.copyCheckInstList(headerList, checkTempInstId, checkInstId);
                            oldCheckInstIdsList.add(oldCheckInstId);
                            newCheckInstIdsList.add(newCheckInstId);
                            oldCheckInstTypeMap.put(oldCheckInstId, oldCheckInstType);
                        }
                        taskPrepareMgrBusiness.commonCopyCheckTable(oldCheckInstIdsList, newCheckInstIdsList, headerInstBM, rowInstBM, cellInstBM, checkTempInstBM, false, oldCheckInstTypeMap);
                    }
                }
            }
            retVal.setSuccess(true);
            retVal.setMsg("????????????");
        }
        return retVal;
    }

    /**
     * ????????????????????????
     *
     * @param: modelId
     * @param: formData
     * @return:
     */
    @RequestMapping("updateDivingTaskData")
    @ResponseBody
    public AjaxResponseData updateDivingTaskData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("??????????????????");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("????????????");
//            Map permsMap = new HashMap<>();
//            permsMap.put("refreshTree", true);
//            retVal.setResults(permsMap);
        }
        return retVal;
    }

    /**
     * ??????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping("deleteHangciData")
    @ResponseBody
    public AjaxResponseData deleteHangciData(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        taskPrepareMgrBusiness.deleteHangciData(id);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ??????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping("deleteHangduanData")
    @ResponseBody
    public AjaxResponseData deleteHangduanData(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        taskPrepareMgrBusiness.deleteHangduanData(id);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping("deleteDivingTaskData")
    @ResponseBody
    public AjaxResponseData deleteDivingTaskData(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        taskPrepareMgrBusiness.deleteDivingTaskData(id);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ??????????????????
     *
     * @param taskId
     * @return
     */
    @RequestMapping("divingTaskBegin")
    @ResponseBody
    public CommonResponseData divingTaskBegin(String taskId) {
        CommonResponseData retVal = new CommonResponseData();
        retVal = taskPrepareMgrBusiness.divingTaskBegin(taskId);
        return retVal;
    }

    @RequestMapping("divingTaskEnd")
    @ResponseBody
    public CommonResponseData divingTaskEnd(String taskId) {
        CommonResponseData retVal = new CommonResponseData();
        retVal = taskPrepareMgrBusiness.divingTaskEnd(taskId);
        return retVal;
    }

    @RequestMapping("AddAttendPostData")
    @ResponseBody
    public CommonResponseData AddAttendPostData(String modelId, String formData, String nodeId, String
            nodeText, String taskId, String platformId) {
        return taskPrepareMgrBusiness.AddAttendPostData(modelId, formData, nodeId, nodeText, taskId, platformId);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @param taskId
     * @return
     */
    @RequestMapping("getAttendPersonData")
    @ResponseBody
    public ExtGridData<Map> getAttendPersonData(String orientModelId, String isView, Integer page, Integer
            limit, String customerFilter, String sort, String taskId) {

        ExtGridData<Map> retVal = taskPrepareMgrBusiness.getAttendPersonData(orientModelId, isView, page, limit, customerFilter, true, sort, taskId);
        return retVal;
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param start
     * @param limit
     * @param queryName
     * @param postIds   ?????????????????????ID
     * @return
     */
    @RequestMapping("queryAttendPersonData")
    @ResponseBody
    public ExtGridData queryAttendPersonData(String start, String limit, String queryName, String postIds) {
        ExtGridData str = taskPrepareMgrBusiness.queryAttendPersonData(start, limit, queryName, postIds);
        return str;
    }

    /**
     * ????????????
     *
     * @param start
     * @param limit
     * @param userName
     * @param selectPersonIds
     * @return
     */
    @RequestMapping("querySelectPersonData")
    @ResponseBody
    public ExtGridData querySelectPersonData(String start, String limit, String userName, String
            selectPersonIds, String taskId) {
        ExtGridData str = taskPrepareMgrBusiness.querySelectPersonData(start, limit, userName, selectPersonIds, taskId);
        return str;
    }

    /**
     * ?????????????????????????????????
     *
     * @param selectId
     * @param taskId
     * @param attendKeyId
     * @return
     */
    @RequestMapping("addPostData")
    @ResponseBody
    public CommonResponseData addPostData(String selectId, String taskId, String attendKeyId, String signPostLogo) {
        return taskPrepareMgrBusiness.addPostData(selectId, taskId, attendKeyId, signPostLogo);
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param selectPersonId
     * @param taskId
     * @param postId
     * @param beforeSelectPersonIds ?????????????????????
     * @param signPersonLogo
     * @param
     * @return
     */
    @RequestMapping("selectPersonData")
    @ResponseBody
    public CommonResponseData selectPersonData(String selectPersonId, String taskId, String postId, String
            beforeSelectPersonIds, String signPersonLogo, String flowId) {
        return taskPrepareMgrBusiness.selectPersonData(selectPersonId, taskId, postId, beforeSelectPersonIds, signPersonLogo, flowId);
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @RequestMapping("getEnumFlowTempType")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getEnumFlowTempType() {
        ExtComboboxResponseData<ExtComboboxData> retValue = taskPrepareMgrBusiness.getEnumFlowTempType();
        return retValue;
    }

    @RequestMapping("getEnumTasKType")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getEnumTasKType() {
        ExtComboboxResponseData<ExtComboboxData> retValue = taskPrepareMgrBusiness.getEnumTasKType();
        return retValue;
    }

    /**
     * ??????????????????
     *
     * @param hangciId
     * @return
     * @throws Exception
     */
    @RequestMapping("getHangciTime")
    @ResponseBody
    public AjaxResponseData getHangciTime(String hangciId) throws Exception {
        return taskPrepareMgrBusiness.getHangciTime(hangciId);
    }

    /**
     * ??????????????????
     *
     * @param hangduanId
     * @return
     * @throws Exception
     */
    @RequestMapping("getHangduanTime")
    @ResponseBody
    public AjaxResponseData getHangduanTime(String hangduanId) throws Exception {
        return taskPrepareMgrBusiness.getHangduanTime(hangduanId);
    }

    /**
     * ??????????????????
     *
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @param hangduanId
     * @return
     */
    @RequestMapping("queryPersonWeight")
    @ResponseBody
    public ExtGridData<Map> queryPersonWeight(String orientModelId, String isView, Integer page, Integer
            limit, String customerFilter, String sort, String hangduanId) {
        ExtGridData<Map> retVal = taskPrepareMgrBusiness.queryPersonWeight(orientModelId, isView, page, limit, customerFilter, true, sort, hangduanId);
        return retVal;
    }

    /**
     * ??????????????????
     *
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("updatePersonWeightData")
    @ResponseBody
    public AjaxResponseData updatePersonWeightData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("??????????????????");
            retVal.setSuccess(false);
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String personId = CommonTools.Obj2String(dataMap.get("C_ATTEND_ID_" + modelId + "_DISPLAY"));
            dataMap.put("C_ATTEND_ID_" + modelId, personId);
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("????????????");
        }
        return retVal;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param
     * @return
     */
    @RequestMapping("getFlowPostHead")
    @ResponseBody
    public AjaxResponseData<FlowPostData> getFlowPostHead(String hangduanId) {
        return taskPrepareMgrBusiness.getFlowPostHead(hangduanId);
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param hangduanId
     * @return
     */
    @RequestMapping("getAttendTimesFlowPostHead")
    @ResponseBody
    public AjaxResponseData<FlowPostData> getAttendTimesFlowPostHead(String hangduanId) {
        return taskPrepareMgrBusiness.getAttendTimesFlowPostHead(hangduanId);
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param start
     * @param limit
     * @param hangduanId
     * @return
     */
    @RequestMapping("getDivingTaskFlowPost")
    @ResponseBody
    public ExtGridData getDivingTaskFlowPost(String start, String limit, String hangduanId) {
        ExtGridData str = taskPrepareMgrBusiness.getDivingTaskFlowPost(start, limit, hangduanId);
        return str;
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param start
     * @param limit
     * @param hangduanId
     * @return
     */
    @RequestMapping("getFlowPostAttendTimes")
    @ResponseBody
    public ExtGridData getFlowPostAttendTimes(String start, String limit, String hangduanId) {
        ExtGridData str = taskPrepareMgrBusiness.getFlowPostAttendTimes(start, limit, hangduanId);
        return str;
    }

//    @RequestMapping("getChoosedAttendPerson")
//    @ResponseBody
//    private Map getChoosedAttendPerson(String taskId,String postId){
//        return taskPrepareMgrBusiness.getChoosedAttendPerson(taskId,postId);
//    }

    /**
     * ????????????
     *
     * @param taskId
     * @param nodeId
     * @param nodeText
     * @return
     */
    @RequestMapping("againTable")
    @ResponseBody
    public AjaxResponseData<String> againTable(String taskId, String nodeId, String nodeText) {
        AjaxResponseData retVal = new AjaxResponseData();
        retVal = taskPrepareMgrBusiness.againTable(taskId, nodeId, nodeText);
        return retVal;
    }

    /**
     * ????????????????????????
     *
     * @param taskId
     * @param hangduanId
     * @return
     */
    @RequestMapping("easyDivingTask")
    @ResponseBody
    public AjaxResponseData easyDivingTask(String taskId, String hangduanId) {
        return taskPrepareMgrBusiness.easyDivingTask(taskId, hangduanId);
    }

    /**
     * ??????????????????????????????
     *
     * @param hangduanId
     * @return
     */
    @RequestMapping("publishFlowPost")
    @ResponseBody
    public CommonResponse publishFlowPost(String hangduanId) {
        return taskPrepareMgrBusiness.publishFlowPost(hangduanId);
    }

    /**
     * ????????????????????????
     *
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @return
     */
    @RequestMapping("getDivingTaskData")
    @ResponseBody
    public ExtGridData<Map> getDivingTaskData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort) {
        ExtGridData<Map> retVal = taskPrepareMgrBusiness.getDivingTaskData(orientModelId, isView, page, limit, customerFilter, true, sort);
        return retVal;
    }

    @RequestMapping("importCheckTableResultData")
    @ResponseBody
    public Map<String, Object> importCheckTableResultData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        String fileName = "";
        Map<String, Object> retVal = null;
        String encoding = WeibaoPropertyUtil.getPropertyValueConfigured("zip.encoding", "config.properties", "C:");
        String filePath = fileServerConfig.getFtpHome() + File.separator + FtpFileUtil.IMPORT_ROOT + "/???????????????????????????/";
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                String fileSufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                File dst = new File(fileName);
                if ("zip".equals(fileSufix)) {
                    File desZip = new File(filePath + fileName);
                    if (!desZip.exists()) {
                        desZip.mkdirs();
                    }
                    file.transferTo(desZip);
                    String zipUrl = filePath + fileName;
                    //??????zip?????????????????????
                    String deCompressUrl = filePath + fileName.substring(0, fileName.length() - 4);
                    if (!new File(deCompressUrl).exists()) {
                        new File(deCompressUrl).mkdirs();
                    }
                    DeCompress.DecompressUtil(zipUrl, deCompressUrl, encoding);
                    FileOperator fileOperator = new FileOperator();
                    //??????Excel??????????????????
                    List<String> fileList = fileOperator.getChildFilePath(deCompressUrl);
                    List<String> fileImgUrlList = UtilFactory.newArrayList();
                    //???????????????????????????????????????????????????
                    FileOperator.getChildFilePath(deCompressUrl, fileImgUrlList);
                    for (String path : fileList) {
                        System.out.println(path);
                        String sufix = path.substring(path.lastIndexOf(".") + 1);
                        if ("xls".equals(sufix) || "xlsx".equals(sufix)) {
                            retVal = handleCheckTableResultExcel(request, path, fileImgUrlList);
                        }
                    }
                    desZip.delete();
                } else {
                    retVal = new HashMap<>();
                    retVal.put("msg", "???????????????.xls,.xlsx,.zip????????????");
                }
            }
        }
        DeCompress.deleteDirectory(filePath);
        return retVal;
    }

    public Map<String, Object> handleCheckTableResultExcel(HttpServletRequest request, String fileName, List<String> fileImgUrlList) throws Exception {

        ExcelReader excelReader = new ExcelReader();
        List<String> headers = null;
        File excelFile = new File(fileName);
        InputStream input = new FileInputStream(excelFile);
        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");
        TableEntity excelEntity = excelReader.readFile(input, after2007);
        if (excelReader.getNumOfSheets() > 1) {
            headers = formTemplateMgrController.getHeaders(excelEntity);
        } else {
            headers = Arrays.asList(excelReader.getExcelReaderConfig().getColumns());
        }

        String divingTaskId = request.getParameter("divingTaskId");
        System.out.println("filename:" + fileName);
        if (fileName.contains("\\") || fileName.contains("/")) {
            fileName = FileOperator.getFileName(fileName);
        }
        //???????????????????????????
        String checkTableInstId = taskPrepareMgrBusiness.insertCheckInstList(fileName, divingTaskId);
        excelFile.delete();
        if (checkTableInstId == null) {
            return null;
        }
        //??????????????????????????????
        Map<String, Object> retVal = taskPrepareMgrBusiness.importCheckInstHeadCellList(excelEntity, headers, checkTableInstId, fileImgUrlList);
        return retVal;
    }

    /**
     * ?????????????????????
     *
     * @param flowId
     * @param flowName
     * @param checkTableInstIds
     * @param taskId
     * @return
     */
    @RequestMapping("checkTablebindFlowData")
    @ResponseBody
    public AjaxResponseData checkTablebindFlowData(String flowId, String flowName, String checkTableInstIds, String taskId) {
        AjaxResponseData retVal = new AjaxResponseData();
        taskPrepareMgrBusiness.checkTablebindFlowData(flowId, flowName, checkTableInstIds, taskId);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * ???????????????????????????
     * @param request
     * @param taskId
     * @return
     * @throws Exception
     */
    @RequestMapping("easybindFlowData")
    @ResponseBody
    public Map<String, Object> easybindFlowData(HttpServletRequest request, String taskId) throws Exception {
        Map<String, Object> retVal = null;
        String fileName = "";
        String filePath = fileServerConfig.getFtpHome() + File.separator + FtpFileUtil.IMPORT_ROOT + "/?????????????????????/";
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                String fileSufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                File dst = new File(fileName);
                if ("xls".equals(fileSufix) || "xlsx".equals(fileSufix)) {
                    file.transferTo(dst);
                    retVal = bindHandleExcel(request, fileName);
                } else {
                    retVal = new HashMap<>();
                    retVal.put("msg", "???????????????.xls,.xlsx????????????");
                }
            }
        }
        DeCompress.deleteDirectory(filePath);
        return retVal;
    }

    public Map<String, Object> bindHandleExcel(HttpServletRequest request, String fileName) throws Exception {
        ExcelReader excelReader = new ExcelReader();
        File excelFile = new File(fileName);
        InputStream input = new FileInputStream(excelFile);
        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");
        TableEntity excelEntity = excelReader.readFile(input, after2007);
        String taskId = request.getParameter("taskId");
        excelFile.delete();
        Map<String, Object> retVal = taskPrepareMgrBusiness.checkTableEasyBindFlow(excelEntity, taskId);
        return retVal;
    }
}
