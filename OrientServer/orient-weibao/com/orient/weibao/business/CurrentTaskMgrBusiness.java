package com.orient.weibao.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.utils.*;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.base.ExtSorter;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.DivingTaskTreeNode;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.enums.CheckNodeType;
import com.orient.weibao.utils.GenerateReportUtil;
import org.drools.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.orient.utils.JsonUtil.getJavaCollection;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-13 10:20
 */
@Service
public class CurrentTaskMgrBusiness extends BaseBusiness {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ModelDataBusiness modelDataBusiness;
    @Autowired
    private FileServerConfig fileServerConfig;
    @Autowired
    MetaDAOFactory metaDAOFactory;
    @Autowired
    GenerateReportUtil generateReportUtil;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public AjaxResponseData<DivingTaskTreeNode> getDivingTaskTreeNodeId() {
        List<DivingTaskTreeNode> list = new ArrayList<>();
        IBusinessModel businessModel = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        businessModel.setReserve_filter("AND C_STATE_" + businessModel.getId() + "='进行中'");
        List<Map> divingTaskList = orientSqlEngine.getBmService().createModelQuery(businessModel).list();
        DivingTaskTreeNode divingTaskTreeNode = new DivingTaskTreeNode();
        if (divingTaskList.size() > 0) {
            for (Map map : divingTaskList) {

                divingTaskTreeNode.setText(CommonTools.Obj2String(map.get("C_TASK_NAME_" + businessModel.getId())));
                divingTaskTreeNode.setId(CommonTools.Obj2String(map.get("ID")));
                divingTaskTreeNode.setDataId(CommonTools.Obj2String(map.get("ID")));
                divingTaskTreeNode.setExpanded(false);
                divingTaskTreeNode.setType(CheckNodeType.TYPE_ROOT);
                divingTaskTreeNode.setIcon("app/images/function/数据建模.png");
                divingTaskTreeNode.setIconCls("icon-function");
                divingTaskTreeNode.setQtip(CommonTools.Obj2String(map.get("C_TASK_NAME_" + businessModel.getId())));
                divingTaskTreeNode.setLeaf(true);
                list.add(divingTaskTreeNode);
            }

        } else {
            divingTaskTreeNode.setId("");
            divingTaskTreeNode.setText("");
        }
        return new AjaxResponseData<>(divingTaskTreeNode);
    }

    public List<String> checkStageNodeStatus(String taskId, String[] cellNodeIdList) {
        List<String> status = UtilFactory.newArrayList();
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = checkTempInstBM.getId();
        List<Map<String, String>> data = UtilFactory.newArrayList();
        if (taskId != null && !"".equals(taskId)) {
            for (String cellNodeId : cellNodeIdList) {
                List<String> statusData = UtilFactory.newArrayList();
                checkTempInstBM.clearCustomFilter();
                checkTempInstBM.appendCustomerFilter(new CustomerFilter("T_DIVING_TASK_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, taskId));
                checkTempInstBM.appendCustomerFilter(new CustomerFilter("C_NODE_ID_" + checkTempInstBM.getId(), EnumInter.SqlOperation.Equal, cellNodeId));
                List<Map> checkTempInstList = orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).list();
                if (checkTempInstList.size() > 0) {
                    for (Map<String, String> map : checkTempInstList) {
                        statusData.add(map.get("C_CHECK_STATE_" + modelId));
                    }
                    if (statusData.contains("未完成")) {
                        status.add("未完成");
                    } else if (statusData.contains("已上传")||statusData.contains("已签署")) {
                        status.add("已通过");
                    } else if (statusData.contains("未通过")) {
                        status.add("未通过");
                    }
                }else{
                    status.add("未完成");
                }
            }
        }
        return status;
    }

    public String getHangciHangduanData(String taskId) {

        String divingTableName = PropertyConstant.DIVING_TASK;
        String hangduanTableName = PropertyConstant.HANGDUAN;
        String hangciTableName = PropertyConstant.HANGCI;

        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(divingTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(hangduanTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangciBM = businessModelService.getBusinessModelBySName(hangciTableName, schemaId, EnumInter.BusinessModelEnum.Table);

        divingTaskBM.setReserve_filter("and id='" + taskId + "'");
        List<Map> divingTaskList = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).list();
        String hangduanId = CommonTools.Obj2String(divingTaskList.get(0).get("T_HANGDUAN_" + schemaId + "_ID"));
        String hangciId = CommonTools.Obj2String(divingTaskList.get(0).get("T_HANGCI_" + schemaId + "_ID"));
        hangduanBM.setReserve_filter("and id='" + hangduanId + "'");
        List<Map> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanBM).list();
        String hangduanName = CommonTools.Obj2String(hangduanList.get(0).get("C_HANGDUAN_NAME_" + hangduanBM.getId()));

        hangciBM.setReserve_filter("and id='" + hangciId + "'");
        List<Map> hangciList = orientSqlEngine.getBmService().createModelQuery(hangciBM).list();
        String hangciName = CommonTools.Obj2String(hangciList.get(0).get("C_HANGCI_NAME_" + hangciBM.getId()));

        return hangciName + " " + hangduanName;
    }

    public ExtGridData<Map<String, Object>> getDeviceInstData(String start,String limit, String productId,String nodeContent,String queryName) throws Exception{
        List instList = new ArrayList<>();
        IBusinessModel deviceInstModel = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceModel = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        int startt = Integer.parseInt(start);
        int limitt = Integer.parseInt(limit);
        String countSql = "select count(*) from " + PropertyConstant.SPARE_PARTS_INST + "_" + schemaId;
        String selectSql = " select * from " + PropertyConstant.SPARE_PARTS_INST + "_" + schemaId+" where T_PRODUCT_STRUCTURE_"+schemaId+"_ID='"+productId+"'";
        String sql;
        List<Map<String, Object>> deviceList;
//        if (query != null) {
////            countSql+=" where c_device_name_"+deviceInstModel.getId()+" ='"+query+"'";
//            selectSql += " where c_device_name_" + deviceInstModel.getId() + " ='" + query + "'";
//        } else
        if (nodeContent != null && !"".equals(nodeContent)) {
            deviceModel.setReserve_filter("and c_device_name_" + deviceModel.getId() + " like '%" + nodeContent + "%'"+
                    " AND T_PRODUCT_STRUCTURE_"+schemaId+"_ID='"+productId+"'");
//            List<Map> deviceList=orientSqlEngine.getBmService().createModelQuery(deviceModel).list();
            sql = "select id from T_SPARE_PARTS_" + schemaId + " where c_device_name_" + deviceModel.getId() + " like '%" + nodeContent + "%' AND T_PRODUCT_STRUCTURE_"+schemaId+"_ID='"+productId+"'";
            deviceList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
            if (deviceList.size() > 0) {
                String id = "";
                for (Map map : deviceList) {
                    id += (String) map.get("ID") + ",";
                }
                id = " (" + id.substring(0, id.length() - 1) + ")";
                selectSql = " select * from T_SPARE_PARTS_SHILI_" + schemaId + " where T_PRODUCT_STRUCTURE_"+schemaId+"_ID='"+productId+"' order by case when c_device_name_" + deviceInstModel.getId() + " in " + id + " then 0 else 1 end ASC ";
//                " union all select from T_SPARE_PARTS_SHILI_"+schemaId+" where c_device_name_"+businessModel.getId()+" not in "+id);
//                businessModel.setReserve_filter(" and c_device_name_"+businessModel.getId()+" not in "+id);
            }
        } else if (queryName != null & !"".equals(queryName)) {
            deviceModel.setReserve_filter("and c_device_name_" + deviceModel.getId() + " = '" + queryName + "'");
//            List<Map> deviceList=orientSqlEngine.getBmService().createModelQuery(deviceModel).list();
            sql = "select id from T_SPARE_PARTS_" + schemaId + " where c_device_name_" + deviceModel.getId() + " like '%" + queryName + "%' AND T_PRODUCT_STRUCTURE_"+schemaId+"_ID='"+productId+"'";
            deviceList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
            if (deviceList.size() > 0) {
                String id = "";
                for (Map map : deviceList) {
                    id += (String) map.get("ID") + ",";
                }
                id = " (" + id.substring(0, id.length() - 1) + ") ";
                selectSql = " select * from T_SPARE_PARTS_SHILI_" + schemaId + " where c_device_name_" + deviceInstModel.getId() + " in " + id;
            }
        }
        List<Map<String, Object>> deviceInst = jdbcTemplate.queryForList(selectSql.toString());
        int totalcount = deviceInst.size();
        int end = startt + limitt;//防止尾页越界
        if (deviceInst.size() < end) {
            end = deviceInst.size();
        }
        for (int i = startt; i < end; i++) {
            Map instMap = new HashMap<>();
            String deviceId = CommonTools.Obj2String(deviceInst.get(i).get("C_DEVICE_NAME_" + deviceInstModel.getId()));
            String deviceSql = "select c_device_name_" + deviceModel.getId() + ",c_device_code_"+deviceModel.getId()+" from T_SPARE_PARTS_" + schemaId + " where id='" + deviceId + "'";
            deviceList = metaDaoFactory.getJdbcTemplate().queryForList(deviceSql.toString());

            String id = CommonTools.Obj2String(deviceInst.get(i).get("id"));
            instMap.put("id", id);
            String deviceName = CommonTools.Obj2String(deviceList.get(0).get("c_device_name_" + deviceModel.getId()));
            instMap.put("deviceName", deviceName);
           String deviceNumber=(String)deviceList.get(0).get("c_device_code_"+deviceModel.getId());
            instMap.put("deviceNumber", deviceNumber);
            String number = CommonTools.Obj2String(deviceInst.get(i).get("c_serial_number_" + deviceInstModel.getId()));
            instMap.put("number", number);
            String state = CommonTools.Obj2String(deviceInst.get(i).get("c_state_" + deviceInstModel.getId()));
            instMap.put("state", state);
            String liezhuangTime = CommonTools.Obj2String(deviceInst.get(i).get("c_liezhuang_time_" + deviceInstModel.getId()));
//            SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd");
//            Date date=sdf.parse(liezhuangTime);
            instMap.put("liezhuangTime", liezhuangTime);
            String position = CommonTools.Obj2String(deviceInst.get(i).get("c_position_" + deviceInstModel.getId()));
            instMap.put("position", position);
            instList.add(instMap);
        }

        return new ExtGridData<>(instList, totalcount);
    }

    public ExtGridData<Map> getCheckInstData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort) {
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
        for (Map checkInstMap : dataList) {
            String checkPersonId = CommonTools.Obj2String(checkInstMap.get("C_CHECK_PERSON_" + businessModel.getId()));
            if (checkPersonId != null && !"".equals(checkPersonId)) {

                StringBuilder sql = new StringBuilder();

                sql.append("select id,all_name from cwm_sys_user where id in(").append(checkPersonId).append(")");

                List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                if (userList.size() > 0) {
                    String allName="";
                    for (Map userMap:userList){
                        allName+=CommonTools.Obj2String(userMap.get("all_name"))+",";
                    }
                    if (!"".equals(allName)){
                        allName=allName.substring(0,allName.length() - 1);
                        checkInstMap.put("C_CHECK_PERSON_" + businessModel.getId(), allName);
                    }
                }
            }
        }
        if (dataChange) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            modelDataBusiness.customDataChange(businessModel, dataList);
        }
        retVal.setResults(dataList);
        retVal.setTotalProperty(count);


        return retVal;
    }

    public ExtGridData<Map> getWaterDownRecordData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort) {
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
        List<Map> dataList = businessModelQuery.orderAsc("to_date(c_record_date_" + orientModelId + ",'yyyy-mm-dd HH24:mi:ss')").list();
        for (Map troubleMap : dataList) {
            String recorderId = CommonTools.Obj2String(troubleMap.get("C_RECORD_PERSON_" + businessModel.getId()));
            if (recorderId != null && !"".equals(recorderId)) {

                StringBuilder sql = new StringBuilder();

                sql.append("select id,all_name from cwm_sys_user where id in(").append(recorderId).append(")");

                List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                if (userList.size() > 0) {
                    String allName = CommonTools.Obj2String(userList.get(0).get("all_name"));
                    troubleMap.put("C_RECORD_PERSON_"+ businessModel.getId(), allName);
                }
            }
        }
        if (dataChange) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            modelDataBusiness.customDataChange(businessModel, dataList);
        }
        retVal.setResults(dataList);
        retVal.setTotalProperty(count);


        return retVal;
    }

    public Map queryDetailContent(String waterDownId){
       String folderName = "voiceFileForDiving";
        IBusinessModel waterDownBM = businessModelService.getBusinessModelBySName(PropertyConstant.WATER_DOWN_RECORD, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId=waterDownBM.getId();
        StringBuilder sql=new StringBuilder();
        sql.append("select c_record_depth_" + modelId + ",c_record_title_"+modelId+" from T_WATER_DOWN_RECORD_" + schemaId + " where id=?");
        Map<String,Object> map=metaDAOFactory.getJdbcTemplate().queryForMap(sql.toString(), waterDownId);
        String depth=CommonTools.Obj2String(map.get("c_record_depth_" + waterDownBM.getId()));
        String detailDescp=CommonTools.Obj2String(map.get("c_record_title_"+waterDownBM.getId()));
        Map detailMap=UtilFactory.newHashMap();
        detailMap.put("depth",depth);
        detailMap.put("detail",detailDescp);
        String voicesql = "select * from cwm_file where DATAID='" + waterDownId + "' and TABLEID='" + modelId + "' and FILETYPE='"+"amr"+"'";
        List<Map<String, Object>> fileList = jdbcTemplate.queryForList(voicesql);
        if (fileList.size()>0){
            String fileName = (String)fileList.get(0).get("FINALNAME");
            String fileNameMp3=fileName.substring(0, fileName.length() - 4)+".mp3";
            String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + folderName;
//            String voiceFolderPath=getPreviewVoicePath() + File.separator+folderName;
            String targetMp3Path=imageFolderPath + File.separator +fileNameMp3;
            targetMp3Path=FileOperator.toStanderds(targetMp3Path);
            Boolean isFileExist= FileOperator.isFileExist(targetMp3Path);
            if (isFileExist){
                String voiceUrl="preview"+ File.separator+folderName+File.separator+fileNameMp3;
                detailMap.put("voiceUrl",voiceUrl);
            }
        }
        return detailMap;
    }

    public static String getPreviewVoicePath() {
        return CommonTools.getRootPath() + File.separator + "preview";
    }

    public String getCheckTableCaseHtml(String instanceId) {
        String retVal = null;
        IBusinessModel instanceModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        instanceModel.setReserve_filter("AND ID = '" + instanceId + "'");
        List<Map> checkTableCaseMapList = orientSqlEngine.getBmService().createModelQuery(instanceModel).list();
        if (checkTableCaseMapList.size() > 0) {
            Map checkTableCaseMap = checkTableCaseMapList.get(0);
            StringBuffer htmlStr = new StringBuffer();
            generateReportUtil.getTableContentHtml(htmlStr, checkTableCaseMap);
            retVal = htmlStr.toString();
        }
        return retVal;
    }

    public String getTroubleIdByCellId(String cellInstId) {
        String tableName = PropertyConstant.TROUBLE_DEVICE_INST;
        IBusinessModel troubleBM = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        troubleBM.setReserve_filter("AND C_CELL_ID_"+troubleBM.getId()+"='"+cellInstId+"'");
        List<Map> troubleList=orientSqlEngine.getBmService().createModelQuery(troubleBM).list();
        String troubleId="";
        if (troubleList!=null&&troubleList.size()>0){
            Map troubleMap=troubleList.get(0);
             troubleId=troubleMap.get("ID").toString();
        }
        return  troubleId;
    }
}
