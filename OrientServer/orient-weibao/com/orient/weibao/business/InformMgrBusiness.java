package com.orient.weibao.business;

import com.google.common.base.Joiner;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.utils.UtilFactory;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.dao.DivingTaskDao;
import com.orient.weibao.dto.DivingTaskNameWithInformLog;
import net.sf.json.JSONArray;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-28 18:22
 */
@Service
public class InformMgrBusiness extends BaseBusiness {

    private String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    @Autowired
    MetaDAOFactory metaDAOFactory;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    FileServerConfig fileServerConfig;

    public AjaxResponseData updateInformContent(String informId, String informContent, String newInformContent) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel informBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hisInformBM = businessModelService.getBusinessModelBySName(PropertyConstant.HIS_INFORM_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        informBM.setReserve_filter("AND ID='" + informId + "'");
        List<Map> informList = orientSqlEngine.getBmService().createModelQuery(informBM).list();
        if (informList.size() > 0) {
            for (Map inforMap : informList) {
                String oldPublishDate = (String) inforMap.get("C_PUBLISH_TIME_" + informBM.getId());
                String departMent = (String) inforMap.get("C_DEPARTMENT_" + informBM.getId());
                Map hisInforMap = UtilFactory.newHashMap();
                hisInforMap.put("C_NAME_" + hisInformBM.getId(), departMent);
                hisInforMap.put("T_INFORM_MGR_" + schemaId + "_ID", informId);
                hisInforMap.put("C_HIS_INFORM_" + hisInformBM.getId(), informContent);
                hisInforMap.put("C_HIS_PUBLISH_TIME_" + hisInformBM.getId(), oldPublishDate);
                orientSqlEngine.getBmService().insertModelData(hisInformBM, hisInforMap);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String newPublishTime = simpleDateFormat.format(new Date());
                inforMap.put("C_INFORM_" + informBM.getId(), newInformContent);
                inforMap.put("C_PUBLISH_TIME_" + informBM.getId(), newPublishTime);
                orientSqlEngine.getBmService().updateModelData(informBM, inforMap, informId);
                retVal.setMsg("修改成功！");
                retVal.setSuccess(true);
            }
        }
        return retVal;
    }

    public void saveInformState(String checkStateName) {
        IBusinessModel informStateBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_STATE, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, Object>> informStateList = orientSqlEngine.getBmService().createModelQuery(informStateBM).list();
        if (informStateList.size() > 0) {
            for (Map informMap : informStateList) {
                String informStateId = (String) informMap.get("ID");
                String name = (String) informMap.get("C_NAME_" + informStateBM.getId());
                if (name.equals(checkStateName)) {
                    informMap.put("C_STATE_" + informStateBM.getId(), "current");
                } else {
                    informMap.put("C_STATE_" + informStateBM.getId(), "history");
                }
                orientSqlEngine.getBmService().updateModelData(informStateBM, informMap, informStateId);
            }
        }
        informStateBM.clearAllFilter();
        informStateBM.setReserve_filter("AND C_NAME_" + informStateBM.getId() + "='" + checkStateName + "'");
        informStateList = orientSqlEngine.getBmService().createModelQuery(informStateBM).list();
        if (informStateList.size() == 0) {
            Map informMap = UtilFactory.newHashMap();
            informMap.put("C_NAME_" + informStateBM.getId(), checkStateName);
            informMap.put("C_STATE_" + informStateBM.getId(), "current");
            orientSqlEngine.getBmService().insertModelData(informStateBM, informMap);
        }
    }

    public AjaxResponseData getInformState() {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel informStateBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_STATE, schemaId, EnumInter.BusinessModelEnum.Table);
        informStateBM.setReserve_filter("AND C_STATE_" + informStateBM.getId() + "='" + "current" + "'");
        List<Map<String, Object>> informStateList = orientSqlEngine.getBmService().createModelQuery(informStateBM).list();
        if (informStateList.size() > 0) {
            Map informMap = informStateList.get(0);
            String stateName = (String) informMap.get("C_NAME_" + informStateBM.getId());
            retVal.setResults(stateName);
            retVal.setSuccess(true);
        } else {
            retVal.setResults("");
            retVal.setSuccess(false);
        }
        return retVal;
    }

    /**
     * 获取正在进行中任务的日志记录
     *
     * @return
     */

    public List<Map<String, Object>> getNoticeInfo() {
        IBusinessModel iTaskBusinessModel = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel infoLogBussinessModel = businessModelService.getBusinessModelBySName("T_INFORM_LOG", schemaId, EnumInter.BusinessModelEnum.Table);
        iTaskBusinessModel.setReserve_filter("and C_STATE_" + iTaskBusinessModel.getId() + "='进行中'");
        List<Map<String, Object>> taskList = orientSqlEngine.getBmService().createModelQuery(iTaskBusinessModel).list();
        List<Map<String, Object>> infoLogList = UtilFactory.newArrayList();
        if (taskList != null && taskList.size() > 0) {
            Map<String, Object> task = taskList.get(0);
            String taskId = (String) task.get("ID");
            infoLogBussinessModel.setReserve_filter(" and C_TASK_ID_" + infoLogBussinessModel.getId() + "=" + taskId);
            infoLogList = orientSqlEngine.getBmService().createModelQuery(infoLogBussinessModel).list();
        }
        return infoLogList;
    }

    @Autowired
    DivingTaskDao divingTaskDao;

    public List<DivingTaskNameWithInformLog> getNoticeCurrentHangduanInfo(boolean isOnlyShowPlan) {
        List<DivingTaskNameWithInformLog> divingTaskNameWithInformLogs = UtilFactory.newArrayList();
        if (isOnlyShowPlan) {
            divingTaskNameWithInformLogs = divingTaskDao.queryCurrentHangduanDivingTaskWithPlanInfomLog();
        } else {
            divingTaskNameWithInformLogs = divingTaskDao.queryCurrentHangduanDivingTaskWithInfomLog();
        }
        return divingTaskNameWithInformLogs;
    }

    public Map getCurrentHangduanFlowPost() {
        Map flowPostMap = UtilFactory.newHashMap();
        IBusinessModel hangduanInformLogBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN_INFORM_LOG, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        divingTaskBM.setReserve_filter("and C_STATE_" + divingTaskBM.getId() + "='进行中'");
        List<Map<String, Object>> taskList = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).list();
        if (taskList != null && taskList.size() > 0) {
            String hangduanId = taskList.get(0).get("T_HANGDUAN_" + schemaId + "_ID").toString();
            hangduanInformLogBM.setReserve_filter("AND C_HD_ID_" + hangduanInformLogBM.getId() + "='" + hangduanId + "'");
            List<Map> hangduanLogList = orientSqlEngine.getBmService().createModelQuery(hangduanInformLogBM).list();
            if (hangduanLogList != null && hangduanLogList.size() > 0) {
                Map hangduanLogMap = hangduanLogList.get(0);
                String publishTime = CommonTools.Obj2String(hangduanLogMap.get("C_PUBLISH_TIME_" + hangduanInformLogBM.getId()));
                String flowPost = CommonTools.Obj2String(hangduanLogMap.get("C_CONTENT_" + hangduanInformLogBM.getId()));
                String hangduanName = CommonTools.Obj2String(hangduanLogMap.get("C_NAME_" + hangduanInformLogBM.getId()));
                String flowPostHeader = CommonTools.Obj2String(hangduanLogMap.get("C_HEADER_" + hangduanInformLogBM.getId()));
                flowPostMap.put("flowPost", flowPost);
                flowPostMap.put("publishTime", publishTime);
                flowPostMap.put("hangduanName", hangduanName);
                flowPostMap.put("flowPostHeader", flowPostHeader);
            }
        }
        return flowPostMap;
    }

    public Map informGetTroubleCellDetail(String troubleId) {
        IBusinessModel troubleDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = troubleDeviceBM.getId();
        StringBuilder sql = new StringBuilder();
        sql.append("select c_description_" + modelId + " from T_TROUBLE_DEVICE_INS_" + schemaId + " where id=?");
        List<Map<String, Object>> descList = metaDAOFactory.getJdbcTemplate().queryForList(sql.toString(), troubleId);
//        String detailContent=CommonTools.Obj2String(map.get("c_replace_desc_" + troubleDeviceBM.getId()));
        String detailContent = "";
        if (descList != null && descList.size() > 0) {
            detailContent = CommonTools.Obj2String(descList.get(0).get("c_description_" + troubleDeviceBM.getId()));
        }
        Map detailMap = UtilFactory.newHashMap();
        detailMap.put("detailContent", detailContent);
        detailMap.put("voiceUrl", null);
        detailMap.put("imageUrls", 1);
        String voicesql = "select * from cwm_file where DATAID='" + troubleId + "' and TABLEID='" + modelId + "'";
        List<Map<String, Object>> fileList = jdbcTemplate.queryForList(voicesql);
        if (fileList != null && fileList.size() > 0) {
            Set<String> imagesUrlsSet = new HashSet<>();
            for (Map fileMap : fileList) {
                String fileId = CommonTools.Obj2String(fileMap.get("FILEID"));
                String fileType = CommonTools.Obj2String(fileMap.get("FILETYPE"));
                String fileName = (String) fileMap.get("FINALNAME");
                if ("amr".equals(fileType)) {
                    String folderName = "voiceFileForTDevice";
                    String fileNameMp3 = fileName.substring(0, fileName.length() - 4) + ".mp3";
                    String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + folderName;
                    String targetMp3Path = imageFolderPath + File.separator + fileNameMp3;
                    targetMp3Path = FileOperator.toStanderds(targetMp3Path);
                    Boolean isFileExist = FileOperator.isFileExist(targetMp3Path);
                    if (isFileExist) {
                        String voiceUrl = "preview" + File.separator + folderName + File.separator + fileNameMp3;
                        detailMap.put("voiceUrl", voiceUrl);
                    }
                } else {
                    String folderName = "imagesForTDevice";
                    String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + folderName;
                    String imageStorePath = imageFolderPath + File.separator + fileName;
                    imageStorePath = FileOperator.toStanderds(imageStorePath);
                    Boolean isFileExist = FileOperator.isFileExist(imageStorePath);
                    if (isFileExist) {
                        String imageUrl = "preview" + File.separator + folderName + File.separator + fileName;
                        imagesUrlsSet.add(imageUrl);
                    }
                }
            }
            if (imagesUrlsSet != null && imagesUrlsSet.size() > 0) {
                //list集合转为json数组
                detailMap.put("imageUrls", JSONArray.fromObject(imagesUrlsSet));
            }
        }
        return detailMap;
    }
}
