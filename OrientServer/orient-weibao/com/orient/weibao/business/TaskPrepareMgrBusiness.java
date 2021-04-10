package com.orient.weibao.business;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.jpdl.model.Property;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.mongorequest.model.CommonResponse;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IFunction;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.role.RoleService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.image.ImageUtils;
import com.orient.web.base.*;
import com.orient.web.form.service.impl.FormService;
import com.orient.web.springmvcsupport.exception.DSException;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.Point;
import com.orient.weibao.bean.flowPost.Column;
import com.orient.weibao.bean.flowPost.Field;
import com.orient.weibao.bean.flowPost.FlowPostData;
import com.orient.weibao.constants.PropertyConstant;
import com.sun.faces.util.Util;
import jdk.nashorn.internal.runtime.Undefined;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.orient.utils.JsonUtil.getJavaCollection;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-12-22 11:09
 */
@Service
public class TaskPrepareMgrBusiness extends BaseBusiness {

    @Autowired
    MetaDAOFactory metaDAOFactory;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ModelDataBusiness modelDataBusiness;
    @Autowired
    RoleService roleService;
    @Autowired
    protected UserService UserService;
    @Autowired
    AccountingFormMgrBusiness accountingFormMgrBusiness;
    @Resource(name = "UserService")
    UserService userService;
    @Autowired
    FormService formService;
    @Autowired
    FileServerConfig fileServerConfig;
    @Autowired
    FileService fileService;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public List<Point> getPoints(String schemaId, String modelId) {
        List<Point> pointList = new ArrayList<>();
        IBusinessModel ibm = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map> ibmList = orientSqlEngine.getBmService().createModelQuery(ibm).list();
        if (!ibmList.isEmpty()) {
            for (Map map : ibmList) {
                Point point = new Point((String) map.get("ID"), (String) map.get("C_TASK_NAME_" + modelId));
                String jin = (String) map.get("C_JINGDU_" + modelId);
                String wei = (String) map.get("C_WEIDU_" + modelId);
                point.setValue("[" + jin + ", " + wei + ", 0]");
                point.setFlowId("T_HANGDUAN_" + schemaId + "_ID");
                pointList.add(point);
            }
        }
        return pointList;
    }

    public void saveMxGraphXml(String xmlStr, String taskId, String flowTempTypeId) {
        IBusinessModel bm = getBusinessModelByName(PropertyConstant.NODE_DESIGN);
        String modelId = bm.getId();
        String versionColName = "C_VERSON_" + modelId;
        String userColName = "C_EDITOR_" + modelId;
        String editTimeColName = "C_EDIT_TIME_" + modelId;
        String colName = PropertyConstant.DIVING_TASK + "_" + schemaId + "_ID";
        String id = taskId;
        if (StringUtil.isNotEmpty(flowTempTypeId)) {
//            colName = PropertyConstant.HANGCI + "_" + schemaId + "_ID";
            colName = PropertyConstant.FLOW_TEMP_TYPE + "_" + schemaId + "_ID";
            id = flowTempTypeId;
        }

        bm.setReserve_filter("AND " + colName + " = '" + id + "'");
        List<Map<String, String>> retVal = orientSqlEngine.getBmService().createModelQuery(bm).list();

        String version = "1";
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (retVal.size() != 0) {
            Map designMap = retVal.get(0);
            String designKeyId = designMap.get("ID").toString();
            version = CommonTools.Obj2String(designMap.get(versionColName));
            version = String.valueOf(Integer.parseInt(version) + 1);

            designMap.put(versionColName, version);
            designMap.put(userColName, UserContextUtil.getUserId());
            designMap.put(editTimeColName, date.format(new Date()));
            designMap.put("C_XML_" + modelId, xmlStr);
            orientSqlEngine.getBmService().updateModelData(bm, designMap, designKeyId);
        } else {
            Map<String, String> data = UtilFactory.newHashMap();
            data.put(versionColName, version);
            data.put(userColName, UserContextUtil.getUserId());
            data.put(editTimeColName, date.format(new Date()));
            data.put(PropertyConstant.DIVING_TASK + "_" + schemaId + "_ID", taskId);
            data.put(PropertyConstant.FLOW_TEMP_TYPE + "_" + schemaId + "_ID", flowTempTypeId);
            data.put("C_XML_" + modelId, xmlStr);
            String nodeDesignId = orientSqlEngine.getBmService().insertModelData(bm, data);
        }

//        Map<String, String> data = UtilFactory.newHashMap();
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        data.put(versionColName, version);
//        data.put(userColName, UserContextUtil.getUserId());
//        data.put(editTimeColName, date.format(new Date()));
//        data.put(PropertyConstant.DIVING_TASK + "_" + schemaId + "_ID", taskId);
//        data.put(PropertyConstant.FLOW_TEMP_TYPE + "_" + schemaId + "_ID", flowTempTypeId);
//        data.put("C_XML_" + modelId, xmlStr);
//        String nodeDesignId = orientSqlEngine.getBmService().insertModelData(bm, data);
//        return nodeDesignId;
    }

    public IBusinessModel getBusinessModelByName(String modelName) {
        return businessModelService.getBusinessModelBySName(modelName, schemaId, EnumInter.BusinessModelEnum.Table);
    }

    public String getXmlStr(String taskId, String flowTempTypeId) {
        String tableName = PropertyConstant.NODE_DESIGN;

        String colName = PropertyConstant.DIVING_TASK + "_" + schemaId + "_ID";
        String id = taskId;
        if (StringUtil.isNotEmpty(flowTempTypeId)) {
            colName = PropertyConstant.FLOW_TEMP_TYPE + "_" + schemaId + "_ID";
            id = flowTempTypeId;
        }

        IBusinessModel bm = getBusinessModelByName(tableName);
        String modelId = bm.getId();
        bm.setReserve_filter("and " + colName + "='" + id + "'");
        List<Map<String, String>> data = orientSqlEngine.getBmService().createModelQuery(bm).list();

        String xmlStr = "";
        if (data.size() != 0) {
            //获取最新的xml
            xmlStr = data.get(0).get("C_XML_" + modelId);
        }
        return xmlStr;
    }

    public void deleteCellMxgraph(String nodeId, String flowTempTypeId, String taskId) {
        String tableName = PropertyConstant.CHECK_TEMP_INST;
        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel tempInstBM = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);

        if (!CommonTools.isNullString(flowTempTypeId)) {
            refPostBM.setReserve_filter("AND T_FLOW_TEMP_TYPE_" + schemaId + "_ID='" + flowTempTypeId + "'" +
                    " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'");
            tempInstBM.setReserve_filter("AND T_FLOW_TEMP_TYPE_" + schemaId + "_ID='" + flowTempTypeId + "'" +
                    " AND C_NODE_ID_" + tempInstBM.getId() + "='" + nodeId + "'");
        } else {
            refPostBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'");
            tempInstBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_NODE_ID_" + tempInstBM.getId() + "='" + nodeId + "'");
        }
        List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
        if (refPostList.size() != 0) {
            for (Map refMap : refPostList) {
                String refPostId = (String) refMap.get("ID");
                orientSqlEngine.getBmService().delete(refPostBM, refPostId);
            }
        }
        List<Map> tempInstList = orientSqlEngine.getBmService().createModelQuery(tempInstBM).list();
        if (tempInstList.size() != 0) {
            for (Map tempInstMap : tempInstList) {
                String tempInstId = (String) tempInstMap.get("ID");
                orientSqlEngine.getBmService().deleteCascade(tempInstBM, tempInstId);
            }
        }
    }

    public List<Map> queryCheckTempInstList() {
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        IBusinessModel checkTypeModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = checkTypeModel.getId();
        String sql = "select * from T_CHECK_TEMP_INST_" + schemaId;
        List<Map<String, Object>> checkTypeList = jdbcTemplate.queryForList(sql);
        List checkList = new ArrayList<>();
        if (checkTypeList.size() > 0) {
            for (Map map : checkTypeList) {
                Map checkMap = new HashMap<>();
                checkMap.put("id", map.get("ID"));
                checkMap.put("checkName", map.get("C_NAME_" + modelId));
                checkList.add(checkMap);
            }
        }
        return checkList;
    }

    //绑定检查表模板
    public CommonResponseData bindCheckTableTemp(String checkTempId, String taskId, String nodeId, String nodeText, String flowTempTypeId, String flowId, String deviceCycleId) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel formCheckModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel formCheckModelInst = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headerModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        if (CommonTools.isNullString(taskId)) {
            taskId = "";
        }
        if (CommonTools.isNullString(flowId)) {
            flowId = "";
        }
        if (CommonTools.isNullString(flowTempTypeId)) {
            flowTempTypeId = "";
        }
        if (CommonTools.isNullString(deviceCycleId)) {
            deviceCycleId = "";
        }

        formCheckModel.setReserve_filter("AND ID IN (" + checkTempId + ")");
        List<Map> formCheckList = orientSqlEngine.getBmService().createModelQuery(formCheckModel).list();

        //判断数据库中任务准备是否已经绑定了检查表模板数据
        if (formCheckList.size() > 0) {
            List<String> oldCheckInstIdsList = UtilFactory.newArrayList();
            List<String> newCheckInstIdsList = UtilFactory.newArrayList();
            Map oldCheckInstTypeMap = UtilFactory.newHashMap();
            for (Map map : formCheckList) {
                String tempId = CommonTools.Obj2String(map.get("ID"));
                String instype = CommonTools.Obj2String(map.get("C_INS_TYPE_" + formCheckModel.getId()));

                if (StringUtil.isNotEmpty(taskId)) {
                    formCheckModelInst.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID = '" + taskId + "'" +
                            " AND C_CHECK_TEMP_ID_" + formCheckModelInst.getId() + " = '" + tempId + "'" +
                            " AND C_NODE_ID_" + formCheckModelInst.getId() + " = '" + nodeId + "'" +
                            " AND C_AGAIN_FLAG_" + formCheckModelInst.getId() + " is null"
                    );
                }
                //判断数据库中任务模板是否已经生成了模板数据
                else if (taskId.isEmpty() && flowId.isEmpty() && deviceCycleId.isEmpty()) {
                    formCheckModelInst.clearCustomFilter();
                    formCheckModelInst.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID IS " + null + "" +
                            " AND C_CHECK_TEMP_ID_" + formCheckModelInst.getId() + " = '" + tempId + "'" +
                            " AND C_NODE_ID_" + formCheckModelInst.getId() + " = '" + nodeId + "'" +
                            " AND T_FLOW_TEMP_TYPE_" + schemaId + "_ID = '" + flowTempTypeId + "'");
                } else if (StringUtil.isNotEmpty(flowId)) {
                    formCheckModelInst.clearCustomFilter();
                    formCheckModelInst.setReserve_filter(" AND T_DESTROY_FLOW_" + schemaId + "_ID ='" + flowId + "'" +
                            " AND C_CHECK_TEMP_ID_" + formCheckModelInst.getId() + " = '" + tempId + "'");
                } else if (StringUtil.isNotEmpty(deviceCycleId)) {
                    formCheckModelInst.clearCustomFilter();
                    formCheckModelInst.setReserve_filter(" AND T_CYCLE_DEVICE_" + schemaId + "_ID ='" + deviceCycleId + "'" +
                            " AND C_CHECK_TEMP_ID_" + formCheckModelInst.getId() + " = '" + tempId + "'");
                }

                List<Map> hasBindMapList = orientSqlEngine.getBmService().createModelQuery(formCheckModelInst).list();
                if (hasBindMapList.size() == 0) {
                    String checkName = CommonTools.Obj2String(map.get("c_name_" + formCheckModel.getId()));
                    String checkType = CommonTools.Obj2String(map.get("C_TEMP_TYPE_" + formCheckModel.getId()));
                    String isRepeatUpload = CommonTools.Obj2String(map.get("C_IS_REPEAT_UPLOAD_" + formCheckModel.getId()));
                    String newTempId = this.insertCheckListTemplate(checkName, checkType, isRepeatUpload, tempId, taskId, nodeId, nodeText, flowTempTypeId, flowId, deviceCycleId);
                    //插入表头、行、单元格实例
//                    StringBuilder headerSql = new StringBuilder();
//                    headerSql.append("select * from T_CHECK_HEADER_" + schemaId).append(" where 1=1").append(" and T_CHECK_TEMP_" + schemaId + "_ID =?").append("order by ID ASC");
//                    List<Map<String, Object>> headerList = metaDAOFactory.getJdbcTemplate().queryForList(headerSql.toString(), tempId);
//                    this.importCheckList(headerList, newTempId, tempId);
                    oldCheckInstIdsList.add(tempId);
                    newCheckInstIdsList.add(newTempId);
                    oldCheckInstTypeMap.put(tempId, instype);
                } else {
                    continue;
                }
            }
            if (oldCheckInstIdsList.size() > 0 && newCheckInstIdsList.size() > 0) {
                commonCopyCheckTable(oldCheckInstIdsList, newCheckInstIdsList, headerModel, rowModel, cellModel, formCheckModel, true, oldCheckInstTypeMap);
            }
        }
        retVal.setSuccess(true);
        retVal.setMsg("保存成功！");
        return retVal;
    }

    //插入表单模板检查项数据
    public String insertCheckListTemplate(String checkName, String checkType, String isRepeatUpload, String checkTempId, String taskId, String nodeId, String nodeText, String flowTempTypeId, String flowId, String deviceCycleId) {
        String tableName = PropertyConstant.CHECK_TEMP_INST;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> data = UtilFactory.newHashMap();
        data.put("C_NAME_" + bm.getId(), checkName);
        data.put("C_CHECK_TEMP_ID_" + bm.getId(), checkTempId);
        data.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
        data.put("C_NODE_ID_" + bm.getId(), nodeId);
        data.put("C_NODE_TEXT_" + bm.getId(), nodeText);
        data.put("C_CHECK_STATE_" + bm.getId(), "未完成");
        data.put("C_INS_TYPE_" + bm.getId(), checkType);
        data.put("C_IS_REPEAT_UPLOAD_" + bm.getId(), isRepeatUpload);
        data.put("T_FLOW_TEMP_TYPE_" + schemaId + "_ID", flowTempTypeId);
        data.put("T_DESTROY_FLOW_" + schemaId + "_ID", flowId);
        data.put("T_CYCLE_DEVICE_" + schemaId + "_ID", deviceCycleId);
        return orientSqlEngine.getBmService().insertModelData(bm, data);
    }

    //插入表头、行、单元格数据
    public void importCheckList(List<Map<String, Object>> headers, String newTempId, String checkTableTempId) {

        //Map<String, Object> retVal = UtilFactory.newHashMap();
        String tempTableName = PropertyConstant.CHECK_TEMP;
        String headerTableName = PropertyConstant.CHECK_HEADER;
        String rowTableName = PropertyConstant.CHECK_ROW;
        String cellTableName = PropertyConstant.CHECK_CELL;

        String tempTableInstName = PropertyConstant.CHECK_TEMP_INST;
        String headerTableInstName = PropertyConstant.CHECK_HEADER_INST;
        String rowTableInstName = PropertyConstant.CHECK_ROW_INST;
        String cellTableInstName = PropertyConstant.CHECK_CELL_INST;

        String productTableName = PropertyConstant.PRODUCT_STRUCTURE;

        IBusinessModel headerBM = businessModelService.getBusinessModelBySName(headerTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(rowTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellBM = businessModelService.getBusinessModelBySName(cellTableName, schemaId, EnumInter.BusinessModelEnum.Table);

        IBusinessModel headerInstBM = businessModelService.getBusinessModelBySName(headerTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(rowTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(cellTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel tempInstBM = businessModelService.getBusinessModelBySName(tempTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);

        IBusinessModel productBM = businessModelService.getBusinessModelBySName(productTableName, schemaId, EnumInter.BusinessModelEnum.Table);

        //数据库中插入表头
        List<String> headerIds = UtilFactory.newArrayList();
        for (Map map : headers) {
            Map<String, String> data = UtilFactory.newHashMap();
            data.put("C_NAME_" + headerInstBM.getId(), CommonTools.Obj2String(map.get("C_NAME_" + headerBM.getId())));
            data.put(tempTableInstName + "_" + schemaId + "_ID", newTempId);
            String id = orientSqlEngine.getBmService().insertModelData(headerInstBM, data);
            headerIds.add(id);
        }

        //数据库中插入行
//        StringBuilder rowSql=new StringBuilder();
//        rowSql.append("select * from T_FORM_TEMPLET_ROW_"+schemaId).append(" where 1=1").append(" and " + tempTableName + "_" + schemaId + "_ID" + "=?").append("order by C_ROW_NUMBER_" + rowBM.getId() + " ASC");
//        List<Map<String,Object>> rowList=metaDAOFactory.getJdbcTemplate().queryForList(rowSql.toString(), checkTableTempId);
        rowBM.setReserve_filter("and " + tempTableName + "_" + schemaId + "_ID" + "='" + checkTableTempId + "'");
        List<Map<String, Object>> rowList = orientSqlEngine.getBmService().createModelQuery(rowBM).orderAsc("TO_NUMBER(C_ROW_NUMBER_" + rowBM.getId() + ")").list();
        for (Map map : rowList) {
            String rowValue = CommonTools.Obj2String(map.get("C_ROW_NUMBER_" + rowBM.getId()));
            String productId = CommonTools.Obj2String(map.get("C_PRODUCT_ID_" + rowBM.getId()));
            String originRowId = CommonTools.Obj2String(map.get("ID"));
            Map<String, String> rowData = UtilFactory.newHashMap();
            rowData.put("C_ROW_NUMBER_" + rowInstBM.getId(), rowValue);
            rowData.put(tempTableInstName + "_" + schemaId + "_ID", newTempId);
            rowData.put("C_PRODUCT_ID_" + rowInstBM.getId(), productId);
            String rowId = orientSqlEngine.getBmService().insertModelData(rowInstBM, rowData);

            StringBuilder cellSql = new StringBuilder();
            cellSql.append("select * from T_CHECK_CELL_" + schemaId).append(" where 1=1").append(" and " + tempTableName + "_" + schemaId + "_ID" + "=?");
            cellSql.append(" and " + rowTableName + "_" + schemaId + "_ID" + "=?").append("order by ID ASC");
            List<Map<String, Object>> cellList = metaDAOFactory.getJdbcTemplate().queryForList(cellSql.toString(), checkTableTempId, originRowId);
            //数据库中插入单元格数据
            for (int i = 0; i < cellList.size(); i++) {
                String headerId = headerIds.get(i);
                String value = CommonTools.Obj2String(cellList.get(i).get("C_CONTENT_" + cellBM.getId()));
                value = "".equals(value) ? "" : value;
                String cellType = CommonTools.Obj2String(cellList.get(i).get("C_CELL_TYPE_" + cellBM.getId()));
                String checkJoin = CommonTools.Obj2String(cellList.get(i).get("C_CHECK_JOIN_" + cellBM.getId()));
                Map<String, String> cellData = UtilFactory.newHashMap();
                cellData.put("C_CONTENT_" + cellInstBM.getId(), value);
                cellData.put("C_CELL_TYPE_" + cellInstBM.getId(), cellType);
                cellData.put("C_CHECK_JOIN_" + cellInstBM.getId(), checkJoin);
                cellData.put("C_PRODUCT_ID_" + cellInstBM.getId(), productId);
                cellData.put(headerTableInstName + "_" + schemaId + "_ID", headerId);
                cellData.put(rowTableInstName + "_" + schemaId + "_ID", rowId);
                //主要是方便获取单元格，不需要多次查询数据库
                cellData.put(tempTableInstName + "_" + schemaId + "_ID", newTempId);
                orientSqlEngine.getBmService().insertModelData(cellInstBM, cellData);
            }
        }
        //插入表头表尾
        cellBM.clearAllFilter();
        cellBM.setReserve_filter("AND T_CHECK_TEMP_" + schemaId + "_ID='" + checkTableTempId + "'" + " AND C_IS_HEADER_" + cellBM.getId() + " IS NOT NULL");
        List<Map> cellHeadEndList = orientSqlEngine.getBmService().createModelQuery(cellBM).list();
        if (cellHeadEndList.size() > 0) {
            for (Map cellMap : cellHeadEndList) {
                String value = CommonTools.Obj2String(cellMap.get("C_CONTENT_" + cellBM.getId()));
                value = "".equals(value) ? "" : value;
                String cellType = CommonTools.Obj2String(cellMap.get("C_CELL_TYPE_" + cellBM.getId()));
                String isHeader = CommonTools.Obj2String(cellMap.get("C_IS_HEADER_" + cellBM.getId()));
                Map<String, String> cellData = UtilFactory.newHashMap();
                cellData.put("C_CONTENT_" + cellInstBM.getId(), value);
                cellData.put("C_CELL_TYPE_" + cellInstBM.getId(), cellType);
                cellData.put("C_IS_HEADER_" + cellInstBM.getId(), isHeader);
                //主要是方便获取单元格，不需要多次查询数据库
                cellData.put(tempTableInstName + "_" + schemaId + "_ID", newTempId);
                orientSqlEngine.getBmService().insertModelData(cellInstBM, cellData);
            }
        }
    }

    public void deleteCheckTable(String id) {
        String tableName = PropertyConstant.CHECK_TEMP_INST;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().deleteCascade(bm, id);
    }

//    public CommonResponseData bindPostTableTemp(String postId, String taskId, String nodeId, String nodeText, String flowTempTypeId, String flowId) {
//        CommonResponseData retVal = new CommonResponseData();
//        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
//        if (CommonTools.isNullString(flowId)) {
//            flowId = "";
//        }
//        if (CommonTools.isNullString(taskId)) {
//            taskId = "";
//        }
//        String postIds[] = {};
//        if (postId.contains(",")) {
//            postIds = postId.split(",");
//            for (String id : postIds) {
//                if (StringUtil.isNotEmpty(taskId)) {
//                    refPostBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID = '" + taskId + "'" +
//                            " AND C_POST_ID_" + refPostBM.getId() + "='" + id + "'" +
//                            " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'" +
//                            " AND C_NODE_TEXT_" + refPostBM.getId() + "='" + nodeText + "'");
//                    List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
//                    if (refPostList.size() == 0) {
//                        Map map = UtilFactory.newHashMap();
//                        map.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
//                        map.put("C_POST_ID_" + refPostBM.getId(), id);
//                        map.put("C_NODE_ID_" + refPostBM.getId(), nodeId);
//                        map.put("C_NODE_TEXT_" + refPostBM.getId(), nodeText);
//                        orientSqlEngine.getBmService().insertModelData(refPostBM, map);
//                    } else {
//                        continue;
//                    }
//                } else if (taskId.isEmpty() && flowId.isEmpty()) {
//                    refPostBM.clearAllFilter();
//                    refPostBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID IS " + null + "" +
//                            " AND C_POST_ID_" + refPostBM.getId() + "='" + id + "'" +
//                            " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'" +
//                            " AND C_NODE_TEXT_" + refPostBM.getId() + "='" + nodeText + "'");
//                } else if (StringUtil.isNotEmpty(flowId)) {
//                    refPostBM.clearCustomFilter();
//                    refPostBM.setReserve_filter(" AND T_DESTROY_FLOW_" + schemaId + "_ID ='" + flowId + "'" +
//                            " AND C_POST_ID_" + refPostBM.getId() + "='" + id + "'");
//                }
//                List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
//                if (refPostList.size() == 0) {
//                    Map map = UtilFactory.newHashMap();
//                    map.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
//                    map.put("C_POST_ID_" + refPostBM.getId(), id);
//                    map.put("C_NODE_ID_" + refPostBM.getId(), nodeId);
//                    map.put("C_NODE_TEXT_" + refPostBM.getId(), nodeText);
//                    map.put("T_FLOW_TEMP_TYPE_" + schemaId + "_ID", flowTempTypeId);
//                    map.put("T_DESTROY_FLOW_" + schemaId + "_ID", flowId);
//                    orientSqlEngine.getBmService().insertModelData(refPostBM, map);
//                }
//            }
//        } else {
//            if (StringUtil.isNotEmpty(taskId)) {
//                refPostBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID = '" + taskId + "'" +
//                        " AND C_POST_ID_" + refPostBM.getId() + "='" + postId + "'" +
//                        " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'" +
//                        " AND C_NODE_TEXT_" + refPostBM.getId() + "='" + nodeText + "'");
//                List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
//                if (refPostList.size() == 0) {
//                    Map map = UtilFactory.newHashMap();
//                    map.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
//                    map.put("C_POST_ID_" + refPostBM.getId(), postId);
//                    map.put("C_NODE_ID_" + refPostBM.getId(), nodeId);
//                    map.put("C_NODE_TEXT_" + refPostBM.getId(), nodeText);
//                    orientSqlEngine.getBmService().insertModelData(refPostBM, map);
//                } else {
//                    retVal.setSuccess(false);
//                    retVal.setMsg("保存失败，不能重复添加！");
//                    return retVal;
//                }
//            } else if (taskId.isEmpty() && flowId.isEmpty()) {
//                refPostBM.clearAllFilter();
//                refPostBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID IS " + null + "" +
//                        " AND C_POST_ID_" + refPostBM.getId() + "='" + postId + "'" +
//                        " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'" +
//                        " AND C_NODE_TEXT_" + refPostBM.getId() + "='" + nodeText + "'");
//            } else if (StringUtil.isNotEmpty(flowId)) {
//                refPostBM.clearCustomFilter();
//                refPostBM.setReserve_filter(" AND T_DESTROY_FLOW_" + schemaId + "_ID ='" + flowId + "'" +
//                        " AND C_POST_ID_" + refPostBM.getId() + "='" + postId + "'");
//            }
//            List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
//            if (refPostList.size() == 0) {
//                Map map = UtilFactory.newHashMap();
//                map.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
//                map.put("C_POST_ID_" + refPostBM.getId(), postId);
//                map.put("C_NODE_ID_" + refPostBM.getId(), nodeId);
//                map.put("C_NODE_TEXT_" + refPostBM.getId(), nodeText);
//                map.put("T_FLOW_TEMP_TYPE_" + schemaId + "_ID", flowTempTypeId);
//                map.put("T_DESTROY_FLOW_" + schemaId + "_ID", flowId);
//                orientSqlEngine.getBmService().insertModelData(refPostBM, map);
//            } else {
//                retVal.setSuccess(false);
//                retVal.setMsg("保存失败，不能重复添加！");
//                return retVal;
//            }
//
//        }
//        retVal.setSuccess(true);
//        retVal.setMsg("保存成功！");
//        return retVal;
//    }

    public CommonResponseData bindPostTableTemp(String postId, String taskId, String nodeId, String nodeText, String flowTempTypeId, String flowId) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        if (CommonTools.isNullString(flowId)) {
            flowId = "";
        }
        if (CommonTools.isNullString(taskId)) {
            taskId = "";
        }
        if (CommonTools.isNullString(flowTempTypeId)) {
            flowTempTypeId = "";
        }
        postBM.setReserve_filter("AND ID IN (" + postId + ")");
        List<Map<String, Object>> postList = orientSqlEngine.getBmService().createModelQuery(postBM).list();
        if (!"".equals(taskId)) {
            refPostBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID = '" + taskId + "'" +
                    " AND C_POST_ID_" + refPostBM.getId() + " in(" + postId + ")" +
                    " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'");
        } else if (taskId.isEmpty() && flowId.isEmpty()) {
            refPostBM.clearAllFilter();
            refPostBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID IS " + null + "" +
                    " AND C_POST_ID_" + refPostBM.getId() + " in(" + postId + ")" +
                    " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'" + "" +
                    " AND T_FLOW_TEMP_TYPE_" + schemaId + "_ID='" + flowTempTypeId + "'");
        } else if (StringUtil.isNotEmpty(flowId)) {
            refPostBM.clearCustomFilter();
            refPostBM.setReserve_filter(" AND T_DESTROY_FLOW_" + schemaId + "_ID ='" + flowId + "'" +
                    " AND C_POST_ID_" + refPostBM.getId() + " in(" + postId + ")");
        }
        List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
        String postIdSplit[] = postId.split(",");
        List<String> postIdList = new ArrayList<>(Arrays.asList(postIdSplit));
        if (refPostList.size() > 0) {
            for (Map refPost : refPostList) {
                String refPostId = CommonTools.Obj2String(refPost.get("C_POST_ID_" + refPostBM.getId()));
                if (postIdList.contains(refPostId)) {
                    postIdList.remove(refPostId);
                }
            }
        }
        for (String newPostId : postIdList) {
            for (Map postMap : postList) {
                String postMgrId = CommonTools.Obj2String(postMap.get("ID"));
                String postType = CommonTools.Obj2String(postMap.get("C_POST_TYPE_" + postBM.getId()));
                if (newPostId.equals(postMgrId)) {
                    Map map = UtilFactory.newHashMap();
                    map.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
                    map.put("C_POST_ID_" + refPostBM.getId(), newPostId);
                    map.put("C_NODE_ID_" + refPostBM.getId(), nodeId);
                    map.put("C_NODE_TEXT_" + refPostBM.getId(), nodeText);
                    map.put("C_POST_TYPE_" + refPostBM.getId(), postType);
                    map.put("T_FLOW_TEMP_TYPE_" + schemaId + "_ID", flowTempTypeId);
                    map.put("T_DESTROY_FLOW_" + schemaId + "_ID", flowId);
                    orientSqlEngine.getBmService().insertModelData(refPostBM, map);
                    break;
                }
            }
        }
        retVal.setSuccess(true);
        retVal.setMsg("保存成功！");
        return retVal;
    }

    public CommonResponseData AddAttendPostData(String modelId, String formData, String nodeId, String nodeText, String taskId, String hangciId) {

        CommonResponseData retVal = new CommonResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        Map dataMap = (Map) formDataMap.get("fields");
        String postName = CommonTools.Obj2String(dataMap.get("C_POST_ID_" + modelId));

        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        if (StringUtil.isNotEmpty(taskId)) {
            refPostBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID = '" + taskId + "'" +
                    " AND C_POST_ID_" + refPostBM.getId() + "='" + postName + "'" +
                    " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'" +
                    " AND C_NODE_TEXT_" + refPostBM.getId() + "='" + nodeText + "'");
        } else {
            refPostBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID = '" + "" + "'" +
                    " AND C_POST_ID_" + refPostBM.getId() + "='" + postName + "'" +
                    " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'" +
                    " AND C_NODE_TEXT_" + refPostBM.getId() + "='" + nodeText + "'");
        }

        List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
        if (refPostList.size() == 0) {
//            Map map = UtilFactory.newHashMap();
            dataMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            dataMap.put("C_NODE_ID_" + refPostBM.getId(), nodeId);
            dataMap.put("C_NODE_TEXT_" + refPostBM.getId(), nodeText);
            dataMap.put("T_HANGCI_" + schemaId + "_ID", hangciId);
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
//            retVal.setResults(eventParam.getDataMap().get("ID"));
//            return retVal;
            retVal.setSuccess(true);
            retVal.setMsg("新增成功！");
        } else {
            retVal.setSuccess(false);
            retVal.setMsg("新增失败,不能重复新增！");
        }
        return retVal;
    }

    public void deleteAttendPostData(String params) throws Exception {
        IBusinessModel attendPersonBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        org.json.JSONArray jsonArray = null;
        //字符串转为json数组
        jsonArray = new org.json.JSONArray(params);
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                //获取json对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String attendPostKeyId = jsonObject.getString("attendPostKeyId");
                String postId = jsonObject.getString("postId");
                String taskId = jsonObject.getString("taskId");
                attendPersonBM.setReserve_filter("AND C_ATTEND_POST_" + attendPersonBM.getId() + " like '%" + postId + "%'" +
                        " AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map<String, Object>> attendPersonsList = orientSqlEngine.getBmService().createModelQuery(attendPersonBM).list();
                if (attendPersonsList.size() > 0) {
                    for (Map personMap : attendPersonsList) {
                        String attendPersonKeyId = CommonTools.Obj2String(personMap.get("ID"));
                        List<String> attendPostList = UtilFactory.newArrayList();
                        String attendPostIds = CommonTools.Obj2String(personMap.get("C_ATTEND_POST_" + attendPersonBM.getId()));
                        if (attendPostIds != "") {
                            String attendPersonId[] = attendPostIds.split(",");
                            attendPostList = new ArrayList<>(Arrays.asList(attendPersonId));
                            if (attendPostList.contains(postId)) {
                                //移除相同的元素
                                attendPostList.removeIf(s -> s.equals(postId));
                                String remainAttendPostIds = Joiner.on(",").join(attendPostList);
                                personMap.remove("C_ATTEND_POST_" + attendPersonBM.getId());
                                personMap.put("C_ATTEND_POST_" + attendPersonBM.getId(), remainAttendPostIds);
                                orientSqlEngine.getBmService().updateModelData(attendPersonBM, personMap, attendPersonKeyId);
                            }
                        }
                    }
                }
                orientSqlEngine.getBmService().delete(refPostBM, attendPostKeyId);
            }
        }
    }

    public void deleteTempAttendPostData(String id) {
        IBusinessModel businessModel = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().delete(businessModel, id);
    }


    /**
     * 获取模型数据
     *
     * @param orientModelId 模型 | 视图ID
     * @return
     */
    public ExtGridData<Map> queryAttendPostData(String orientModelId, String isView, Integer page, Integer
            pagesize, String customerFilter, Boolean dataChange, String sort, String taskId) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        IBusinessModel attendPersonModel = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        if (!StringUtils.isEmpty(customerFilter)) {
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

        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);

        List<Map> dataList = businessModelQuery.list();
        for (Map map : dataList) {
            String postId = CommonTools.Obj2String(map.get("C_POST_ID_" + orientModelId));
            if (StringUtil.isNotEmpty(postId)) {
                StringBuilder sql = new StringBuilder();
                sql.append("select id,c_post_name_" + postBM.getId() + " from T_POST_MGR_" + schemaId + " where id in(").append(postId).append(")");
                List<Map<String, Object>> list = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                map.put("C_POST_ID_" + orientModelId, list.get(0).get("c_post_name_" + postBM.getId()));
                map.put("C_POST_ID_" + orientModelId + "_DISPLAY", postId);
                attendPersonModel.clearAllFilter();
                attendPersonModel.setReserve_filter("AND C_ATTEND_POST_" + attendPersonModel.getId() + " like '%" + postId + "%'" +
                        " AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map<String, Object>> attendPersonsList = orientSqlEngine.getBmService().createModelQuery(attendPersonModel).list();
                if (attendPersonsList.size() > 0) {
                    String attendPersonIds = "";
                    for (Map attendMap : attendPersonsList) {
                        String attendPostId = (String) attendMap.get("C_ATTEND_POST_" + attendPersonModel.getId());
                        if (attendPostId.contains(",")) {
                            String attendPostIds[] = attendPostId.split(",");
                            for (String str : attendPostIds) {
                                if (str.equals(postId)) {
                                    String attendPerson = (String) attendMap.get("C_ATTEND_PERSON_" + attendPersonModel.getId());
                                    attendPersonIds += attendPerson + ",";
                                }
                            }
                        } else {
                            if (attendPostId.equals(postId)) {
                                String attendPerson = (String) attendMap.get("C_ATTEND_PERSON_" + attendPersonModel.getId());
                                attendPersonIds += attendPerson + ",";
                            }
                        }

                    }
                    if (attendPersonIds != null && !"".equals(attendPersonIds)) {
                        attendPersonIds = attendPersonIds.substring(0, attendPersonIds.length() - 1);
                        StringBuilder userSql = new StringBuilder();

                        userSql.append("select id,all_name from cwm_sys_user where id in(").append(attendPersonIds).append(")");

                        List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());
                        String allNames = "";
                        String postPersonIds = "";
                        if (userList.size() > 0) {
                            for (Map userMap : userList) {
                                String allName = CommonTools.Obj2String(userMap.get("all_name"));
                                allNames += allName + ",";
                                String postPersonId = (String) userMap.get("ID");
                                postPersonIds += postPersonId + ",";
                            }
                            allNames = allNames.substring(0, allNames.length() - 1);
                            postPersonIds = postPersonIds.substring(0, postPersonIds.length() - 1);
                            map.put("C_POST_PERSONNEL_" + orientModelId, allNames);
                            map.put("C_POST_PERSONNEL_" + orientModelId + "_ID", postPersonIds);
                        }
                    }
                } else {
                    map.put("C_POST_PERSONNEL_" + orientModelId, "");
                    map.put("C_POST_PERSONNEL_" + orientModelId + "_ID", "");
                }
                attendPersonModel.clearAllFilter();
                attendPersonModel.setReserve_filter("AND C_SIGN_POST_" + attendPersonModel.getId() + " like '%" + postId + "%'" +
                        " AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map<String, Object>> signPersonsList = orientSqlEngine.getBmService().createModelQuery(attendPersonModel).list();
                if (signPersonsList.size() > 0) {
                    String signPersonIds = "";
                    for (Map attendMap : signPersonsList) {
                        String signPostId = (String) attendMap.get("C_SIGN_POST_" + attendPersonModel.getId());
                        if (signPostId.contains(",")) {
                            String signPostIds[] = signPostId.split(",");
                            for (String str : signPostIds) {
                                if (str.equals(postId)) {
                                    String signPerson = (String) attendMap.get("C_ATTEND_PERSON_" + attendPersonModel.getId());
                                    signPersonIds += signPerson + ",";
                                }
                            }
                        } else {
                            if (signPostId.equals(postId)) {
                                String signPerson = (String) attendMap.get("C_ATTEND_PERSON_" + attendPersonModel.getId());
                                signPersonIds += signPerson + ",";
                            }
                        }
                    }
                    if (signPersonIds != null && !"".equals(signPersonIds)) {
                        signPersonIds = signPersonIds.substring(0, signPersonIds.length() - 1);
                        StringBuilder userSql = new StringBuilder();

                        userSql.append("select id,all_name from cwm_sys_user where id in(").append(signPersonIds).append(")");

                        List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());
                        String allNames = "";
                        String signPersons = "";
                        if (userList.size() > 0) {
                            for (Map userMap : userList) {
                                String allName = CommonTools.Obj2String(userMap.get("all_name"));
                                allNames += allName + ",";
                                String signPerson = CommonTools.Obj2String(userMap.get("ID"));
                                signPersons += signPerson + ",";
                            }
                            allNames = allNames.substring(0, allNames.length() - 1);
                            signPersons = signPersons.substring(0, signPersons.length() - 1);
                            map.put("C_SIGN_PERSON_" + orientModelId, allNames);
                            map.put("C_SIGN_PERSON_" + orientModelId + "_ID", signPersons);
                        }
                    }
                } else {
                    map.put("C_SIGN_PERSON_" + orientModelId, "");
                    map.put("C_SIGN_PERSON_" + orientModelId + "_ID", "");
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

    public boolean isSuperAdmin() {
        String userId = UserContextUtil.getUserId();
        List<IRole> roleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        for (IRole role : roleList) {
            List<ModelBtnTypeEntity> btnTypes = roleService.getBtnTypesByRoleId(role.getId(), true);
            for (ModelBtnTypeEntity modelBtnTypeEntity : btnTypes) {
                if (modelBtnTypeEntity.getName().equals("超级管理员")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteHangciData(String id) {
        IBusinessModel businessModel = businessModelService.getBusinessModelBySName(PropertyConstant.HANGCI, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().deleteCascade(businessModel, id);
    }

    public void deleteHangduanData(String id) {
        IBusinessModel businessModel = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().deleteCascade(businessModel, id);
    }

    public void deleteDivingTaskData(String id) {
        IBusinessModel businessModel = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().deleteCascade(businessModel, id);
    }

    public CommonResponseData divingTaskBegin(String taskId) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel taskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        String taskModelId = taskBM.getId();
        List<Map<String, Object>> divingTaskList = orientSqlEngine.getBmService().createModelQuery(taskBM).list();
        if (divingTaskList.size() > 0) {
            for (int i = 0; i < divingTaskList.size(); i++) {
                String taskID = CommonTools.Obj2String(divingTaskList.get(i).get("ID"));
                if (taskId.equals(taskID)) {
                    divingTaskList.remove(i);
                } else {
                    continue;
                }
            }
            for (Map taskMap : divingTaskList) {
                String taskState = CommonTools.Obj2String(taskMap.get("c_state_" + taskBM.getId()));
                if ("进行中".equals(taskState)) {
                    retVal.setSuccess(false);
                    retVal.setMsg("有其它任务还未结束!");
                    return retVal;
                } else {
                    continue;
                }
            }
            taskBM.clearCustomFilter();
            taskBM.setReserve_filter(" AND ID='" + taskId + "'");
            Map<String, String> divingTaskMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(taskBM).list().get(0);
            String taskState = CommonTools.Obj2String(divingTaskMap.get("c_state_" + taskBM.getId()));
            if (taskState.equals("未开始")) {
                divingTaskMap.put("c_state_" + taskModelId, "进行中");
                orientSqlEngine.getBmService().updateModelData(taskBM, divingTaskMap, taskId);
                retVal.setSuccess(true);
            } else if (taskState.equals("已结束")) {
                retVal.setSuccess(false);
                retVal.setMsg("当前任务已结束！");
            } else if (taskState.equals("进行中")) {
                retVal.setSuccess(false);
                retVal.setMsg("任务已经启动！");
            }
        }

//        taskBM.setReserve_filter(" AND ID='" + taskId + "'");
//
//        Map<String, String> taskMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(taskBM).list().get(0);
////        if (taskState!=null&&!"".equals(taskState)){
//        String hangduanId=taskMap.get("T_HANGDUAN_" + schemaId + "_ID");
//        taskBM.clearCustomFilter();
//          taskBM.setReserve_filter(" AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'");
//        List<Map<String,Object>> taskList=orientSqlEngine.getBmService().createModelQuery(taskBM).list();
//        for (Map map:taskList){
//            String taskState=CommonTools.Obj2String(map.get("c_state_" + taskModelId));
//            String taskID=CommonTools.Obj2String(map.get("ID"));
//            if (!taskID.equals(taskId)){
//                if("启动".equals(taskState)){
//                    retVal.setSuccess(false);
//                    return retVal;
//                }else{
//                    continue;
//                }
//            }else {
//               continue;
//            }
//        }
////        checkInstBM.appendCustomerFilter(new CustomerFilter("T_DIVING_TASK_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, taskId));
////        checkInstBM.appendCustomerFilter(new CustomerFilter("C_CHECK_STATE_" + checkInstBM.getId(), EnumInter.SqlOperation.Equal, "未完成"));
////        List<Map<String, String>> checkInstList = orientSqlEngine.getBmService().createModelQuery(checkInstBM).list();
////        for (Map<String, String> map : checkInstList) {
////            map.put("C_CHECK_STATE_" + checkInstBM.getId(), "已下发");
////            String id = map.get("ID");
////            orientSqlEngine.getBmService().updateModelData(checkInstBM, map, id);
////        }
//        taskBM.clearCustomFilter();
//        taskBM.setReserve_filter(" AND ID='" + taskId + "'");
//        Map<String, String> divingTaskMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(taskBM).list().get(0);
//        divingTaskMap.put("c_state_" + taskModelId, "启动");
//        orientSqlEngine.getBmService().updateModelData(taskBM, divingTaskMap, taskId);
//        retVal.setSuccess(true);
//        return retVal;
        return retVal;
    }

    public CommonResponseData divingTaskEnd(String taskId) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel taskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingPlanBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING__PLAN_TABLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingReportBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_REPORT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingStaticsBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangciBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGCI, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel balanceBM = businessModelService.getBusinessModelBySName(PropertyConstant.BALANCE_COUNT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);

        String taskModelId = taskBM.getId();
        taskBM.setReserve_filter(" AND ID='" + taskId + "'");

        Map<String, String> taskMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(taskBM).list().get(0);
//        if (taskState!=null&&!"".equals(taskState)){
        String taskState = taskMap.get("c_state_" + taskModelId);
        String hangduanId = taskMap.get("T_HANGDUAN_" + schemaId + "_ID");
        if (taskState.equals("进行中")) {
            taskMap.put("c_state_" + taskModelId, "已结束");
            orientSqlEngine.getBmService().updateModelData(taskBM, taskMap, taskId);
            String hangciId = taskMap.get("T_HANGCI_" + schemaId + "_ID");
            Map<String, String> hangciMap = orientSqlEngine.getBmService().createModelQuery(hangciBM).findById(hangciId);
            String hangciName = hangciMap.get("C_HANGCI_NAME_" + hangciBM.getId());
            Map<String, String> hangduanMap = orientSqlEngine.getBmService().createModelQuery(hangduanBM).findById(hangduanId);
            String hangduanName = hangduanMap.get("C_HANGDUAN_NAME_" + hangduanBM.getId());
            Map divingStatisticMap = UtilFactory.newHashMap();
            divingStatisticMap.put("C_HANGCI_" + divingStaticsBM.getId(), hangciName);
            divingStatisticMap.put("C_HANGDUAN_" + divingStaticsBM.getId(), hangduanName);
            String seaArea = taskMap.get("C_SEA_AREA_" + taskModelId);
            String taskName = taskMap.get("C_TASK_NAME_" + taskModelId);
            divingStatisticMap.put("C_SEA_AREA_" + divingStaticsBM.getId(), seaArea);
            divingStatisticMap.put("C_DIVING_TASK_" + divingStaticsBM.getId(), taskName);
            divingStatisticMap.put("T_HANGCI_" + schemaId + "_ID", hangciId);
            divingStatisticMap.put("T_DIVING_TASK_" + schemaId + "_ID", taskId);
            divingReportBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
            List<Map<String, Object>> divingReportList = orientSqlEngine.getBmService().createModelQuery(divingReportBM).list();
            if (divingReportList.size() > 0) {
                for (Map divingReportMap : divingReportList) {
                    //计算水中时长
                    String outWaterTime = CommonTools.Obj2String(divingReportMap.get("C_OUT_WATER_" + divingReportBM.getId()));
                    String inWaterTime = CommonTools.Obj2String(divingReportMap.get("C_IN_WATER_" + divingReportBM.getId()));
                    long waterHours = accountingFormMgrBusiness.differentMs(outWaterTime, inWaterTime);
//                    if (!"".equals(waterHours) && waterHours != null) {
//                        String newWaterHours = waterHours.replace("h", ".");
//                        int index = newWaterHours.indexOf("m");
//                        newWaterHours = newWaterHours.substring(0, index);
//                        divingStatisticMap.put("C_WATER_TIME_LONG_" + divingStaticsBM.getId(), newWaterHours);
//                    }
                    divingStatisticMap.put("C_WATER_TIME_LONG_" + divingStaticsBM.getId(), waterHours);

                    //计算水下作业时长(作业结束-作业开始)
                    //作业结束时间（抛载上浮压载时间）
                    String workEndTime = CommonTools.Obj2String(divingReportMap.get("C_WORK_END_" + divingReportBM.getId()));
                    String workStartTime = CommonTools.Obj2String(divingReportMap.get("C_WORK_START_" + divingReportBM.getId()));
                    long underWaterHours = accountingFormMgrBusiness.differentMs(workEndTime, workStartTime);
                    divingStatisticMap.put("C_WORK_TIME_LONG_" + divingStaticsBM.getId(), underWaterHours);
//                    if (!"".equals(underWaterHours) && underWaterHours != null) {
//                        String newUnderWaterHours = underWaterHours.replace("h", ".");
//                        int index = newUnderWaterHours.indexOf("m");
//                        newUnderWaterHours = newUnderWaterHours.substring(0, index);
//                        divingStatisticMap.put("C_WORK_TIME_LONG_" + divingStaticsBM.getId(), newUnderWaterHours);
//                    }
                    String divingMaxDepth = CommonTools.Obj2String(divingReportMap.get("C_DIVING_DEPTH_" + divingReportBM.getId()));
                    String divingTime = CommonTools.Obj2String(divingReportMap.get("C_DATE_" + divingReportBM.getId()));
                    divingStatisticMap.put("C_DIVING_TIME_" + divingStaticsBM.getId(), divingTime);
                    divingStatisticMap.put("C_DEPTH_" + divingStaticsBM.getId(), divingMaxDepth);
                    //经度
                    String longitude = CommonTools.Obj2String(divingReportMap.get("C_JINGDU_" + divingReportBM.getId()));
                    if (longitude.contains("W") || longitude.contains("E") || longitude.contains("N") || longitude.contains("S")) {
                        String unit = longitude.substring(longitude.lastIndexOf("°") + 1, longitude.length());
                        divingStatisticMap.put(getFieldName("eastWestHalfsphere", divingStaticsBM), unit);
                        longitude = longitude.substring(0, longitude.lastIndexOf("°"));
                    }
                    divingStatisticMap.put("C_LONGITUDE_" + divingStaticsBM.getId(), longitude);
                    //纬度
                    String latitude = CommonTools.Obj2String(divingReportMap.get("C_WEIDU_" + divingReportBM.getId()));
                    if (latitude.contains("W") || latitude.contains("E") || latitude.contains("N") || latitude.contains("S")) {
                        String unit = latitude.substring(latitude.lastIndexOf("°") + 1, latitude.length());
                        divingStatisticMap.put(getFieldName("southNorthHalfsphere", divingStaticsBM), unit);
                        latitude = latitude.substring(0, latitude.lastIndexOf("°"));
                    }
                    divingStatisticMap.put("C_LATITUDE_" + divingStaticsBM.getId(), latitude);
                    String sampleSituation = CommonTools.Obj2String(divingReportMap.get("C_SAMPLE_SITUATION_" + divingReportBM.getId()));
                    divingStatisticMap.put("C_SAMPLE_SITUATION_" + divingStaticsBM.getId(), sampleSituation);

                    divingStatisticMap.put(getFieldName("personComeinCabinT", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("personComeinCabinT", divingReportBM))));
                    divingStatisticMap.put(getFieldName("ballastRemoveTime", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("ballastRemoveTime", divingReportBM))));
                    divingStatisticMap.put(getFieldName("bufangCommandTime", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("bufangCommandTime", divingReportBM))));
                    divingStatisticMap.put(getFieldName("onceBufangboatTime", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("onceBufangboatTime", divingReportBM))));
                    divingStatisticMap.put(getFieldName("bufangboatDriver", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("bufangboatDriver", divingReportBM))));
                    divingStatisticMap.put(getFieldName("bufangboatAssistant", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("bufangboatAssistant", divingReportBM))));
                    divingStatisticMap.put(getFieldName("tuolanPeople", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("tuolanPeople", divingReportBM))));
                    divingStatisticMap.put(getFieldName("tuolanAssistant", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("tuolanAssistant", divingReportBM))));
                    divingStatisticMap.put(getFieldName("timeZone", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("timeZone", divingReportBM))));
                    divingStatisticMap.put(getFieldName("onceRecoverboatTime", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("onceRecoverboatTime", divingReportBM))));
                    divingStatisticMap.put(getFieldName("bufangMaxWindSpeed", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("bufangMaxWindSpeed", divingReportBM))));
                    divingStatisticMap.put(getFieldName("bufangAverageWSpeed", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("bufangAverageWSpeed", divingReportBM))));
                    divingStatisticMap.put(getFieldName("bufangLangHeight", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("bufangLangHeight", divingReportBM))));
                    divingStatisticMap.put(getFieldName("bufangSeaStateEstima", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("bufangSeaStateEstima", divingReportBM))));
                    divingStatisticMap.put(getFieldName("bufangType", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("bufangType", divingReportBM))));
                    divingStatisticMap.put(getFieldName("twiceBufangboatTime", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("twiceBufangboatTime", divingReportBM))));
                    divingStatisticMap.put(getFieldName("recoverboatDperson", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("recoverboatDperson", divingReportBM))));
                    divingStatisticMap.put(getFieldName("recoverboatAssistant", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("recoverboatAssistant", divingReportBM))));
                    divingStatisticMap.put(getFieldName("gualanPeople", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("gualanPeople", divingReportBM))));
                    divingStatisticMap.put(getFieldName("gualanAssistant", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("gualanAssistant", divingReportBM))));
                    divingStatisticMap.put(getFieldName("recoverDeckTime", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("recoverDeckTime", divingReportBM))));
                    divingStatisticMap.put(getFieldName("twiceRecoverboatTime", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("twiceRecoverboatTime", divingReportBM))));
                    divingStatisticMap.put(getFieldName("personOutCabinTime", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("personOutCabinTime", divingReportBM))));
                    divingStatisticMap.put(getFieldName("recoverMaxWindSpeed", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("recoverMaxWindSpeed", divingReportBM))));
                    divingStatisticMap.put(getFieldName("recoverMAverageWindS", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("recoverMAverageWindS", divingReportBM))));
                    divingStatisticMap.put(getFieldName("recoverLangHeight", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("recoverLangHeight", divingReportBM))));
                    divingStatisticMap.put(getFieldName("recoverSeaStateEstim", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get(getFieldName("recoverSeaStateEstim", divingReportBM))));

                    String hatchCloseTime = CommonTools.Obj2String(divingReportMap.get("C_HATCH_CLOSE_" + divingReportBM.getId()));
                    if (!"".equals(hatchCloseTime)) {
                        divingStatisticMap.put(getFieldName("hatchCloseTime", divingStaticsBM), TimeUtil.getTime(TimeUtil.getDateByDateString(hatchCloseTime), TimeUtil.convertString(hatchCloseTime)));
                    }
                    String startFillWaterT = CommonTools.Obj2String(divingReportMap.get("C_START_FILL_WATER_" + divingReportBM.getId()));
                    if (!"".equals(startFillWaterT)) {
                        divingStatisticMap.put(getFieldName("startFillWaterTime", divingStaticsBM), TimeUtil.getTime(TimeUtil.getDateByDateString(startFillWaterT), TimeUtil.convertString(startFillWaterT)));
                    }
                    if (!"".equals(workStartTime)) {
                        divingStatisticMap.put(getFieldName("startWorkTime", divingStaticsBM), TimeUtil.getTime(TimeUtil.getDateByDateString(workStartTime), TimeUtil.convertString(workStartTime)));
                    }
                    if (!"".equals(workEndTime)) {
                        divingStatisticMap.put(getFieldName("endWorkTime", divingStaticsBM), TimeUtil.getTime(TimeUtil.getDateByDateString(workEndTime), TimeUtil.convertString(workEndTime)));
                    }
                    //作业结束深度(抛载上浮压载时深度(M))
                    divingStatisticMap.put(getFieldName("workEndDepth", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get("C_THROW_UP_DEPTH_" + divingReportBM.getId())));
                    //作业开始深度（抛载终止下潜压载时深度(M)）
                    divingStatisticMap.put(getFieldName("workStartDepth", divingStaticsBM), CommonTools.Obj2String(divingReportMap.get("C_THROW_END_DEPTH_" + divingReportBM.getId())));
                    //上浮到水面时间
                    String floatWaterTime = CommonTools.Obj2String(divingReportMap.get("C_FLOAT_WATER_TIME_" + divingReportBM.getId()));
                    if (!"".equals(floatWaterTime)) {
                        divingStatisticMap.put(getFieldName("floatWaterTime", divingStaticsBM), TimeUtil.getTime(TimeUtil.getDateByDateString(floatWaterTime), TimeUtil.convertString(floatWaterTime)));
                    }
                    //潜水器入水时间
                    if (!"".equals(inWaterTime)) {
                        divingStatisticMap.put(getFieldName("divingDEntryTime", divingStaticsBM), TimeUtil.getTime(TimeUtil.getDateByDateString(inWaterTime), TimeUtil.convertString(inWaterTime)));
                    }
                    //潜水器出水时间
                    if (!"".equals(outWaterTime)) {
                        divingStatisticMap.put(getFieldName("divingDOutWaterTime", divingStaticsBM), TimeUtil.getTime(TimeUtil.getDateByDateString(outWaterTime), TimeUtil.convertString(outWaterTime)));
                    }
                    String hatchOpenTime = CommonTools.Obj2String(divingReportMap.get("C_HATCH_OPEN_" + divingReportBM.getId()));
                    if (!"".equals(hatchOpenTime)) {
                        divingStatisticMap.put(getFieldName("hatchOpenTime", divingStaticsBM), TimeUtil.getTime(TimeUtil.getDateByDateString(hatchOpenTime), TimeUtil.convertString(hatchOpenTime)));
                    }
                }
            }
            divingPlanBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_TABLE_STATE_" + divingPlanBM.getId() + "='" + "当前" + "'");
            List<Map<String, Object>> divingPlanList = orientSqlEngine.getBmService().createModelQuery(divingPlanBM).list();
            if (divingPlanList.size() > 0) {
                Map divingPlanMap = divingPlanList.get(0);
                String selectZuoxian = CommonTools.Obj2String(divingPlanMap.get("C_ZUOXIAN_" + divingPlanBM.getId()));
                String selectMainDriver = CommonTools.Obj2String(divingPlanMap.get("C_MAIN_DRIVER_" + divingPlanBM.getId()));
                String selectYouxian = CommonTools.Obj2String(divingPlanMap.get("C_YOUXIAN_" + divingPlanBM.getId()));
//                String divingTime = CommonTools.Obj2String(divingPlanMap.get("C_DIVING_DATE_" + divingPlanBM.getId()));
                String divingType = CommonTools.Obj2String(divingPlanMap.get("C_DIVING_TYPE_" + divingPlanBM.getId()));
//                if (!"".equals(selectYouxian)) {
//                    User user = userService.findUserById(selectYouxian);
//                    if (user != null) {
//                        String unit = user.getUnit();
//                        divingStatisticMap.put("C_COMPANY_" + divingStaticsBM.getId(), unit);
//                    }
//                }
                List<String> personIdsList = UtilFactory.newArrayList();
                if (!"".equals(selectZuoxian)) {
                    personIdsList.add(selectZuoxian);
                }
                if (!"".equals(selectMainDriver)) {
                    personIdsList.add(selectMainDriver);
                }
                if (!"".equals(selectYouxian)) {
                    personIdsList.add(selectYouxian);
                }
                String userNames = "";
                if (personIdsList.size() > 0) {
                    String personIds = Joiner.on(",").join(personIdsList);
                    String userSql = "select id,all_name,unit from cwm_sys_user where id in (" + personIds + ")";
                    List<Map<String, Object>> userList = jdbcTemplate.queryForList(userSql);
                    for (String personId : personIdsList) {
                        if (userList.size() > 0) {
                            for (Map userMap : userList) {
                                String userId = userMap.get("ID").toString();
                                String userName = CommonTools.Obj2String(userMap.get("all_name"));
                                String company = CommonTools.Obj2String(userMap.get("unit"));
                                if (personId.equals(userId)) {
                                    userNames += userName + "/";
                                }
                                if (selectZuoxian.equals(personId) && (personId.equals(userId))) {
                                    divingStatisticMap.put("C_ZUOXIAN_" + divingStaticsBM.getId(), userName);
                                    divingStatisticMap.put("C_ZX_COMPANY_" + divingStaticsBM.getId(), company);
                                }
                                if (selectYouxian.equals(personId) && (personId.equals(userId))) {
                                    divingStatisticMap.put("C_YOUXIAN_" + divingStaticsBM.getId(), userName);
                                    divingStatisticMap.put("C_COMPANY_" + divingStaticsBM.getId(), company);
                                }
                                if (selectMainDriver.equals(personId) && (personId.equals(userId))) {
                                    divingStatisticMap.put("C_MAIN_DRIVER_" + divingStaticsBM.getId(), userName);
                                }
                            }
                        }
                    }
                }
                if (!"".equals(userNames)) {
                    userNames = userNames.substring(0, userNames.length() - 1);
                }

//                divingStatisticMap.put("C_ZUOXIAN_" + divingStaticsBM.getId(), selectZuoxian);
//                divingStatisticMap.put("C_MAIN_DRIVER_" + divingStaticsBM.getId(), selectMainDriver);
//                divingStatisticMap.put("C_YOUXIAN_" + divingStaticsBM.getId(), selectYouxian);
//                divingStatisticMap.put("C_DIVING_TIME_"+divingStaticsBM.getId(),divingTime);
//                String zmrPersons = "";
//                if (!"".equals(selectZuoxian)) {
//                    zmrPersons += selectZuoxian + ",";
//                }
//                if (!"".equals(selectMainDriver)) {
//                    zmrPersons += selectMainDriver + ",";
//                }
//                if (!"".equals(selectYouxian)) {
//                    zmrPersons += selectYouxian + ",";
//                }
//                if (!"".equals(zmrPersons)) {
//                    zmrPersons = zmrPersons.substring(0, zmrPersons.length() - 1);
//                }
                divingStatisticMap.put("C_PERSON_" + divingStaticsBM.getId(), userNames);

                divingStatisticMap.put("C_WORK_CONTENT_" + divingStaticsBM.getId(), divingType);
            }
            balanceBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_TABLE_STATE_" + balanceBM.getId() + "='" + "当前" + "'");
            List<Map<String, Object>> balanceList = orientSqlEngine.getBmService().createModelQuery(balanceBM).list();
            if (balanceList.size() > 0) {
                Map balanceMap = balanceList.get(0);
                String person = CommonTools.Obj2String(balanceMap.get("C_PERSON_" + balanceBM.getId()));
                if (!"".equals(person)) {
                    try {
                        org.json.JSONArray jsonArray = new org.json.JSONArray(person);
                        if (jsonArray.length() > 0) {
                            double personAllWeight = 0.0;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //获取json对象
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String weight = CommonTools.Obj2String(jsonObject.getString("weight"));
                                if (!"".equals(weight)) {
                                    double personWeight = Double.parseDouble(weight);
                                    personAllWeight += personWeight;
                                }
                            }
                            divingStatisticMap.put("C_PERSON_TOTALWEIGHT_" + divingStaticsBM.getId(), personAllWeight);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            orientSqlEngine.getBmService().insertModelData(divingStaticsBM, divingStatisticMap);
            retVal.setSuccess(true);
            retVal.setMsg("成功结束下潜任务！");
        } else if (taskState.equals("未开始")) {
            retVal.setSuccess(false);
            retVal.setMsg("当前任务还未启动！");
        } else {
            retVal.setMsg("当前任务已经结束！");
            retVal.setSuccess(false);
        }
        return retVal;
    }

    private String getFieldName(String filedName, IBusinessModel businessModel) {
        return filedName.toUpperCase() + "_" + businessModel.getId();
    }

    public ExtGridData<Map> getAttendPersonData(String orientModelId, String isView, Integer page, Integer
            pagesize, String customerFilter, Boolean dataChange, String sort, String taskId) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        IBusinessModel attendModel = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel postModel = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel taskModel = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanModel = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        if (taskId != null & !"".equals(taskId)) {
            taskModel.setReserve_filter("and id='" + taskId + "'");
            List<Map> taskList = orientSqlEngine.getBmService().createModelQuery(taskModel).list();
            String hangduanId = CommonTools.Obj2String(taskList.get(0).get("T_HANGDUAN_" + schemaId + "_ID"));
            hangduanModel.setReserve_filter("and id='" + hangduanId + "'");
            List<Map> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanModel).list();
            String attendPersonIds = CommonTools.Obj2String(hangduanList.get(0).get("C_ATTEND_PERSON_" + hangduanModel.getId()));
            if (attendPersonIds.contains(",")) {
                String attendPerson[] = attendPersonIds.split(",");
                attendModel.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map<String, Object>> attendPersonnelList = orientSqlEngine.getBmService().createModelQuery(attendModel).list();
                if (attendPerson.length >= attendPersonnelList.size()) {
                    for (String attendId : attendPerson) {
                        Map attendMap = new HashMap<>();
                        attendModel.clearAllFilter();
                        attendModel.setReserve_filter("and C_ATTEND_PERSON_" + orientModelId + "='" + attendId + "'" +
                                " AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                        List<Map> attendList = orientSqlEngine.getBmService().createModelQuery(attendModel).list();
                        if (attendList.size() > 0) {
                            continue;
                        }
                        attendMap.put("C_ATTEND_PERSON_" + orientModelId, attendId);
                        attendMap.put(PropertyConstant.DIVING_TASK + "_" + schemaId + "_ID", taskId);
                        orientSqlEngine.getBmService().insertModelData(attendModel, attendMap);
                    }
                } else {
                    if (attendPersonnelList.size() > 0) {
                        for (Map attendSzieMap : attendPersonnelList) {
                            String attendKeyId = (String) attendSzieMap.get("ID");
                            String attendPersonId = (String) attendSzieMap.get("C_ATTEND_PERSON_" + orientModelId);
                            if (attendPersonIds.indexOf(attendPersonId) == -1) {
                                orientSqlEngine.getBmService().delete(attendModel, attendKeyId);
                            }
                        }
                        for (String attendId : attendPerson) {
                            Map attendMap = new HashMap<>();
                            attendModel.clearAllFilter();
                            attendModel.setReserve_filter("and C_ATTEND_PERSON_" + orientModelId + "='" + attendId + "'" +
                                    " AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                            List<Map> attendList = orientSqlEngine.getBmService().createModelQuery(attendModel).list();
                            if (attendList.size() > 0) {
                                continue;
                            }
                            attendMap.put("C_ATTEND_PERSON_" + orientModelId, attendId);
                            attendMap.put(PropertyConstant.DIVING_TASK + "_" + schemaId + "_ID", taskId);
                            orientSqlEngine.getBmService().insertModelData(attendModel, attendMap);
                        }

                    }
                }
            } else if (attendPersonIds.indexOf(",") == -1 && (attendPersonIds != null && !"".equals(attendPersonIds))) {
                businessModel.setReserve_filter("and C_ATTEND_PERSON_" + orientModelId + "='" + attendPersonIds + "'" +
                        " AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map> attendList = orientSqlEngine.getBmService().createModelQuery(attendModel).list();
                if (attendList.size() == 0) {
                    Map attendMap = new HashMap<>();
                    attendMap.put("C_ATTEND_PERSON_" + orientModelId, attendPersonIds);
                    attendMap.put(PropertyConstant.DIVING_TASK + "_" + schemaId + "_ID", taskId);
                    orientSqlEngine.getBmService().insertModelData(attendModel, attendMap);
                }
            } else if (attendPersonIds == null || "".equals(attendPersonIds)) {
                attendModel.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map> attendList = orientSqlEngine.getBmService().createModelQuery(attendModel).list();
                for (Map map : attendList) {
                    String attendId = (String) map.get("ID");
                    orientSqlEngine.getBmService().delete(attendModel, attendId);
                }
            }
        }
        if (!StringUtils.isEmpty(customerFilter)) {
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
        if (dataList.size() > 0) {
            String userIds = "";
            String postIds = "";
            String signPostIds = "";
            for (Map map : dataList) {
                String Id = CommonTools.Obj2String(map.get("C_ATTEND_PERSON_" + orientModelId));
                userIds += Id + ",";
                String postId = CommonTools.Obj2String(map.get("C_ATTEND_POST_" + orientModelId));
                if (StringUtil.isNotEmpty(postId)) {
                    postIds += postId + ",";
                } else {
                    continue;
                }
            }
            for (Map map : dataList) {
                String Id = CommonTools.Obj2String(map.get("C_ATTEND_PERSON_" + orientModelId));
                userIds += Id + ",";
                String postId = CommonTools.Obj2String(map.get("C_ATTEND_POST_" + orientModelId));
                String signPostId = CommonTools.Obj2String(map.get("C_SIGN_POST_" + orientModelId));
                if (StringUtil.isNotEmpty(postId)) {
                    postIds += postId + ",";
                }
                if (StringUtil.isNotEmpty(signPostId)) {
                    signPostIds += signPostId + ",";
                }
            }
            userIds = userIds.substring(0, userIds.length() - 1);
            List<Map<String, Object>> userList = UtilFactory.newArrayList();
            if (userIds != null && !"".equals(userIds)) {
                List<Map<String, Object>> postList = UtilFactory.newArrayList();
                List<Map<String, Object>> signList = UtilFactory.newArrayList();
                if (postIds != null && !"".equals(postIds)) {
                    postIds = postIds.substring(0, postIds.length() - 1);
                    StringBuilder postSql = new StringBuilder();
                    postSql.append("select id,c_post_name_" + postModel.getId() + " from t_post_mgr_" + schemaId + " where id in(").append(postIds).append(")");
                    postList = metaDaoFactory.getJdbcTemplate().queryForList(postSql.toString());
                }
                if (signPostIds != null && !"".equals(signPostIds)) {
                    signPostIds = signPostIds.substring(0, signPostIds.length() - 1);
                    StringBuilder postSql = new StringBuilder();
                    postSql.append("select id,c_post_name_" + postModel.getId() + " from t_post_mgr_" + schemaId + " where id in(").append(signPostIds).append(")");
                    signList = metaDaoFactory.getJdbcTemplate().queryForList(postSql.toString());
                }
                StringBuilder sql = new StringBuilder();
                sql.append("select id,all_name from cwm_sys_user where id in(").append(userIds).append(")");
                userList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                String postSessionName = "C_ATTEND_POST_" + orientModelId;
                String signSessionName = "C_SIGN_POST_" + orientModelId;
                if (userList.size() > 0) {
                    for (Map userMap : userList) {
                        for (Map map : dataList) {
                            String attendpersonId = CommonTools.Obj2String(map.get(("C_ATTEND_PERSON_" + orientModelId)));
                            if (attendpersonId.equals(userMap.get("ID"))) {
                                map.put("C_ATTEND_PERSON_" + orientModelId, CommonTools.Obj2String(userMap.get("all_name")));
                            } else {
                                continue;
                            }
                            String postName = getNames(postSessionName, map, postList, postModel);
                            map.put(postSessionName + "_DISPLAY", postName);
                            String signName = getNames(signSessionName, map, signList, postModel);
                            map.put(signSessionName + "_DISPLAY", signName);
                        }

                    }
                }
            }
        }

        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    //获取参与岗位名称及签署岗位名称
    public String getNames(String postSessionName, Map
            map, List<Map<String, Object>> postList, IBusinessModel postModel) {
        String postName = "";
        if (postList.size() > 0) {
            for (Map post : postList) {
                String attendpostIds = CommonTools.Obj2String(map.get((postSessionName)));
                if (attendpostIds.contains(",")) {
                    String postId[] = attendpostIds.split(",");
                    for (String str : postId) {
                        if (str.equals(post.get("ID"))) {
                            String selectPostName = CommonTools.Obj2String(post.get("c_post_name_" + postModel.getId()));
                            postName += selectPostName + ",";
                        }
                    }
                } else if (attendpostIds.indexOf(",") == -1) {
                    if (attendpostIds.equals(post.get("ID"))) {
                        String selectPostName = CommonTools.Obj2String(post.get("c_post_name_" + postModel.getId()));
                        postName += selectPostName + ",";
                    }
                }
            }
            if (StringUtil.isNotEmpty(postName)) {
                postName = postName.substring(0, postName.length() - 1);
            }
        }
        return postName;
    }


    public ExtGridData<Map<String, Object>> queryAttendPersonData(String start, String limit, String
            queryName, String postIds) {
        List instList = new ArrayList<>();
        IBusinessModel postModel = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        int startt = Integer.parseInt(start);
        int limitt = Integer.parseInt(limit);

        String selectSql = " select id , c_post_name_" + postModel.getId() + ",c_post_type_" + postModel.getId() + " from " + PropertyConstant.POST_MGR + "_" + schemaId + " order by c_post_type_" + postModel.getId() + ",c_post_name_" + postModel.getId() + " asc";
        if (queryName != null & !"".equals(queryName)) {
            if (queryName.contains("\'")) {
                queryName = queryName.replaceAll("\'", "\''");
            }
//            List<Map> deviceList=orientSqlEngine.getBmService().createModelQuery(deviceModel).list();
            selectSql = "select id,c_post_name_" + postModel.getId() + ",c_post_type_" + postModel.getId() + " from T_POST_MGR_" + schemaId + " where c_post_name_" + postModel.getId() + " like '%" + queryName + "%'";
        }
        List<Map<String, Object>> deviceInst = jdbcTemplate.queryForList(selectSql.toString());
        int totalcount = deviceInst.size();
        int end = startt + limitt;//防止尾页越界
        if (deviceInst.size() < end) {
            end = deviceInst.size();
        }
        for (int i = startt; i < end; i++) {
            Map instMap = new HashMap<>();
            String postId = CommonTools.Obj2String(deviceInst.get(i).get("ID"));
            if (postIds.contains(",")) {
                String postIDs[] = postIds.split(",");
                for (String str : postIDs) {
                    if (str.equals(postId)) {
                        instMap.put("checked", true);
                    }
                }
            } else {
                if (postIds.equals(postId)) {
                    instMap.put("checked", true);
                }
            }
            instMap.put("id", postId);
            String deviceName = CommonTools.Obj2String(deviceInst.get(i).get("c_post_name_" + postModel.getId()));
            String postType = CommonTools.Obj2String(deviceInst.get(i).get("c_post_type_" + postModel.getId()));
            instMap.put("postName", deviceName);
            instMap.put("postType", postType);
            instList.add(instMap);
        }

        return new ExtGridData<>(instList, totalcount);
    }


    public ExtGridData<Map<String, Object>> querySelectPersonData(String start, String limit, String
            userName, String selectPersonIds, String taskId) {
        List instList = new ArrayList<>();
        IBusinessModel postModel = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel attendModel = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        int startt = Integer.parseInt(start);
        int limitt = Integer.parseInt(limit);

        attendModel.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
        List<Map> attendList = orientSqlEngine.getBmService().createModelQuery(attendModel).list();
        String attendPersonIds = "";
        int totalcount = 0;
        if (attendList.size() > 0) {
            String deptSql = "select id,name from CWM_SYS_DEPARTMENT";
            List<Map<String, Object>> deptList = jdbcTemplate.queryForList(deptSql.toString());
            for (Map attendMap : attendList) {
                String attendPersonId = (String) attendMap.get("C_ATTEND_PERSON_" + attendModel.getId());
                attendPersonIds += attendPersonId + ",";
            }
            attendPersonIds = attendPersonIds.substring(0, attendPersonIds.length() - 1);
            String selectSql = " select id , all_name,dep_id from cwm_sys_user where id in (" + attendPersonIds + ") order by dep_id asc";
            if (userName != null & !"".equals(userName)) {
                if (userName.contains("\'")) {
                    userName = userName.replaceAll("\'", "\''");
                }
//            List<Map> deviceList=orientSqlEngine.getBmService().createModelQuery(deviceModel).list();
                selectSql = "select u.id,u.all_name,u.dep_id from cwm_sys_user u where u.all_name like '%" + userName + "%' and u.state='1' order by u.dep_id asc";
            }
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(selectSql.toString());
            totalcount = userList.size();
            int end = startt + limitt;//防止尾页越界
            if (userList.size() < end) {
                end = userList.size();
            }
            for (int i = startt; i < end; i++) {
                Map instMap = new HashMap<>();
                String userId = CommonTools.Obj2String(userList.get(i).get("ID"));
                if (selectPersonIds.contains(",")) {
                    String postIDs[] = selectPersonIds.split(",");
                    for (String str : postIDs) {
                        if (str.equals(userId)) {
                            instMap.put("checked", true);
                        }
                    }
                } else {
                    if (selectPersonIds.equals(userId)) {
                        instMap.put("checked", true);
                    }
                }
                String personName = CommonTools.Obj2String(userList.get(i).get("all_name"));
                if (deptList != null && deptList.size() > 0) {
                    for (Map deptMap : deptList) {
                        String deptId = CommonTools.Obj2String(deptMap.get("id"));
                        String deptName = CommonTools.Obj2String(deptMap.get("name"));
                        if (deptId.equals(userList.get(i).get("dep_id"))) {
                            instMap.put("deptName", deptName);
                            break;
                        }
                    }
                }
                instMap.put("id", userId);
                instMap.put("personName", personName);
                instList.add(instMap);
            }
        }
        return new ExtGridData<>(instList, totalcount);
    }

    public CommonResponseData addPostData(String selectId, String taskId, String attendKeyId, String
            signPostLogo) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel attendBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        Boolean signLogo = Boolean.valueOf(signPostLogo);
        String hasPostId = "";

        String selectIds = (selectId == null) ? "" : selectId;

        attendBM.setReserve_filter(" AND T_DIVING_TASK_" + schemaId + "_ID = '" + taskId + "'" +
                " AND ID ='" + attendKeyId + "'");
        List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(attendBM).list();
        if (refPostList.size() > 0) {
            for (Map map : refPostList) {
                String id = CommonTools.Obj2String(map.get("ID"));
                if (signLogo) {
                    hasPostId = CommonTools.Obj2String(map.get("C_SIGN_POST_" + attendBM.getId()));
                } else {
                    hasPostId = CommonTools.Obj2String(map.get("C_ATTEND_POST_" + attendBM.getId()));
                }
                if (StringUtil.isNotEmpty(selectIds) && selectIds.equals(hasPostId)) {
                    continue;
                } else if (StringUtil.isEmpty(selectIds) && selectIds.equals(hasPostId)) {
                    if (signLogo) {
                        map.put("C_SIGN_POST_" + attendBM.getId(), selectIds);
                    } else {
                        map.put("C_ATTEND_POST_" + attendBM.getId(), selectIds);
                    }
                } else if (StringUtil.isNotEmpty(selectIds) && !selectIds.equals(hasPostId)) {
                    if (signLogo) {
                        map.put("C_SIGN_POST_" + attendBM.getId(), selectIds);
                    } else {
                        map.put("C_ATTEND_POST_" + attendBM.getId(), selectIds);
                    }

                } else if (StringUtil.isEmpty(selectIds) && !selectIds.equals(hasPostId)) {
                    if (signLogo) {
                        map.put("C_SIGN_POST_" + attendBM.getId(), selectIds);
                    } else {
                        map.put("C_ATTEND_POST_" + attendBM.getId(), selectIds);
                    }
                }

                orientSqlEngine.getBmService().updateModelData(attendBM, map, id);
                retVal.setSuccess(true);
                retVal.setMsg("保存成功！");
            }
        }
        return retVal;
    }

    public CommonResponseData selectPersonData(String selectPersonId, String taskId, String postId, String
            beforeSelectPersonIds, String signPersonLogo, String flowId) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel attendBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        Boolean signLogo = Boolean.valueOf(signPersonLogo);
        String selectPersonIds = (selectPersonId == null) ? "" : selectPersonId;
        String beforeSelectPersonId = (beforeSelectPersonIds == null || beforeSelectPersonIds.equals("undefined")) ? "" : beforeSelectPersonIds;
        List<String> deletePersonList = new ArrayList<>();
        if (selectPersonIds != null && !"".equals(selectPersonIds)) {
            String newAttendPersonIds = "";
            String splitSelectPersonIds[] = selectPersonIds.split(",");
            List<String> splitSelectList = new ArrayList<>(Arrays.asList(splitSelectPersonIds));
            if (beforeSelectPersonId != null && !"".equals(beforeSelectPersonId)) {
                String[] hasAttendPerson = beforeSelectPersonId.split(",");
                List<String> hasAttendPersonList = new ArrayList<>(Arrays.asList(hasAttendPerson));
                for (String attend : splitSelectList) {
                    if (hasAttendPersonList.contains(attend)) {
                        continue;
                    } else {
                        hasAttendPersonList.add(attend);
                    }
                }
                int delete = 0;
                for (int i = 0; i < hasAttendPersonList.size(); i++) {
                    for (String attend : splitSelectList) {
                        if (hasAttendPersonList.get(i).equals(attend)) {
                            delete++;
                            break;
                        }
                    }
                    if (i >= delete) {
                        deletePersonList.add(hasAttendPersonList.get(i));
                        hasAttendPersonList.remove(i);
                        i--;
                    }
                }
                newAttendPersonIds = Joiner.on(",").join(hasAttendPersonList);
            } else {
                newAttendPersonIds = selectPersonIds;
            }
            attendBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_ATTEND_PERSON_" + attendBM.getId() + " IN (" + newAttendPersonIds + ")");
            List<Map> selectPersonList = orientSqlEngine.getBmService().createModelQuery(attendBM).list();
            if (selectPersonList.size() > 0) {
                for (Map selectMap : selectPersonList) {
                    String attendId = (String) selectMap.get("ID");
                    String attendPostIds = CommonTools.Obj2String(selectMap.get("C_ATTEND_POST_" + attendBM.getId()));
                    String signPostIds = (String) selectMap.get("C_SIGN_POST_" + attendBM.getId());
                    if (signLogo) {
                        if (signPostIds != null && !"".equals(signPostIds) && signPostIds.indexOf(postId) != -1) {
                            continue;
                        } else if (signPostIds != null && !"".equals(signPostIds) && signPostIds.indexOf(postId) == -1) {
                            signPostIds = signPostIds + "," + postId;
                            selectMap.put("C_SIGN_POST_" + attendBM.getId(), signPostIds);
                            orientSqlEngine.getBmService().updateModelData(attendBM, selectMap, attendId);
                        } else if (signPostIds == null || "".equals(signPostIds)) {
                            selectMap.put("C_SIGN_POST_" + attendBM.getId(), postId);
                            orientSqlEngine.getBmService().updateModelData(attendBM, selectMap, attendId);
                        }
                    } else {
                        List<String> attendPostIdList = new ArrayList<>(Arrays.asList(postId));
                        if (!"".equals(attendPostIds)) {
                            String attendPostIdsSplit[] = attendPostIds.split(",");
                            attendPostIdList = new ArrayList<>(Arrays.asList(attendPostIdsSplit));
                            if (attendPostIdList.contains(postId)) {
                                continue;
                            } else {
                                attendPostIdList.add(postId);
                            }
                        }
                        String newPostId = Joiner.on(",").join(attendPostIdList);
                        selectMap.put("C_ATTEND_POST_" + attendBM.getId(), newPostId);
                        orientSqlEngine.getBmService().updateModelData(attendBM, selectMap, attendId);
                    }
                }
            }
        } else {
            String[] beforeSelectIds = beforeSelectPersonIds.split(",");
            deletePersonList = new ArrayList<>(Arrays.asList(beforeSelectIds));
        }
        if (deletePersonList.size() > 0) {
            String deletePersonIds = Joiner.on(",").join(deletePersonList);
            attendBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                    " AND C_ATTEND_PERSON_" + attendBM.getId() + " IN (" + deletePersonIds + ")");
            List<Map> selectPersonList = orientSqlEngine.getBmService().createModelQuery(attendBM).list();
            if (selectPersonList.size() > 0) {

                for (Map selectMap : selectPersonList) {
                    String attendId = (String) selectMap.get("ID");
                    String attendPostId = "";
                    if (signLogo) {
                        attendPostId = (String) selectMap.get("C_SIGN_POST_" + attendBM.getId());
                    } else {
                        attendPostId = (String) selectMap.get("C_ATTEND_POST_" + attendBM.getId());
                    }
                    if (attendPostId.contains(",")) {
                        String attendPostIds[] = attendPostId.split(",");
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < attendPostIds.length; i++) {
                            if (attendPostIds[i].equals(postId)) {
                                continue;
                            }
                            list.add(attendPostIds[i]);
                            attendPostId = Joiner.on(",").join(list);  //list集合转为字符串
                        }
                    } else {
                        if (attendPostId.equals(postId)) {
                            attendPostId = "";
                        }
                    }
                    if (signLogo) {
                        selectMap.put("C_SIGN_POST_" + attendBM.getId(), attendPostId);
                        orientSqlEngine.getBmService().updateModelData(attendBM, selectMap, attendId);
                    } else {
                        selectMap.put("C_ATTEND_POST_" + attendBM.getId(), attendPostId);
                        orientSqlEngine.getBmService().updateModelData(attendBM, selectMap, attendId);
                    }

                }
            }
        }
        retVal.setSuccess(true);
        retVal.setMsg("保存成功！");
        return retVal;
    }

    //插入表头、行、单元格数据
    public void copyCheckInstList(List<Map<String, Object>> headers, String newTempId, String
            checkTableTempId) {

        //Map<String, Object> retVal = UtilFactory.newHashMap();

        String tempTableInstName = PropertyConstant.CHECK_TEMP_INST;
        String headerTableInstName = PropertyConstant.CHECK_HEADER_INST;
        String rowTableInstName = PropertyConstant.CHECK_ROW_INST;
        String cellTableInstName = PropertyConstant.CHECK_CELL_INST;

        String productTableName = PropertyConstant.PRODUCT_STRUCTURE;

        IBusinessModel headerInstBM = businessModelService.getBusinessModelBySName(headerTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(rowTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(cellTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel tempInstBM = businessModelService.getBusinessModelBySName(tempTableInstName, schemaId, EnumInter.BusinessModelEnum.Table);

        IBusinessModel productBM = businessModelService.getBusinessModelBySName(productTableName, schemaId, EnumInter.BusinessModelEnum.Table);

        //数据库中插入表头
        List<String> headerIds = UtilFactory.newArrayList();
        for (Map map : headers) {
            Map<String, String> data = UtilFactory.newHashMap();
            data.put("C_NAME_" + headerInstBM.getId(), CommonTools.Obj2String(map.get("C_NAME_" + headerInstBM.getId())));
            data.put(tempTableInstName + "_" + schemaId + "_ID", newTempId);
            String id = orientSqlEngine.getBmService().insertModelData(headerInstBM, data);
            headerIds.add(id);
        }
        //数据库中插入行
//        StringBuilder rowSql=new StringBuilder();
//        rowSql.append("select * from T_FORM_TEMPLET_ROW_"+schemaId).append(" where 1=1").append(" and " + tempTableName + "_" + schemaId + "_ID" + "=?").append("order by C_ROW_NUMBER_" + rowBM.getId() + " ASC");
//        List<Map<String,Object>> rowList=metaDAOFactory.getJdbcTemplate().queryForList(rowSql.toString(), checkTableTempId);
        rowInstBM.setReserve_filter("and " + tempTableInstName + "_" + schemaId + "_ID" + "='" + checkTableTempId + "'");
        List<Map<String, Object>> rowList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).orderAsc("TO_NUMBER(C_ROW_NUMBER_" + rowInstBM.getId() + ")").list();
        for (Map map : rowList) {
            String rowValue = CommonTools.Obj2String(map.get("C_ROW_NUMBER_" + rowInstBM.getId()));
            String productId = CommonTools.Obj2String(map.get("C_PRODUCT_ID_" + rowInstBM.getId()));
            String originRowId = CommonTools.Obj2String(map.get("ID"));
            Map<String, String> rowData = UtilFactory.newHashMap();
            rowData.put("C_ROW_NUMBER_" + rowInstBM.getId(), rowValue);
            rowData.put(tempTableInstName + "_" + schemaId + "_ID", newTempId);
            rowData.put("C_PRODUCT_ID_" + rowInstBM.getId(), productId);
            String rowId = orientSqlEngine.getBmService().insertModelData(rowInstBM, rowData);

            StringBuilder cellSql = new StringBuilder();
            cellSql.append("select * from T_CHECK_CELL_INST_" + schemaId).append(" where 1=1").append(" and " + tempTableInstName + "_" + schemaId + "_ID" + "=?");
            cellSql.append(" and " + rowTableInstName + "_" + schemaId + "_ID" + "=?").append("order by ID ASC");
            List<Map<String, Object>> cellList = metaDAOFactory.getJdbcTemplate().queryForList(cellSql.toString(), checkTableTempId, originRowId);
            //数据库中插入单元格数据
            for (int i = 0; i < cellList.size(); i++) {
                String headerId = headerIds.get(i);
                String value = CommonTools.Obj2String(cellList.get(i).get("C_CONTENT_" + cellInstBM.getId()));
                value = "".equals(value) ? "" : value;
                String cellType = CommonTools.Obj2String(cellList.get(i).get("C_CELL_TYPE_" + cellInstBM.getId()));
                String checkJoin = CommonTools.Obj2String(cellList.get(i).get("C_CHECK_JOIN_" + cellInstBM.getId()));
                Map<String, String> cellData = UtilFactory.newHashMap();
                cellData.put("C_CONTENT_" + cellInstBM.getId(), value);
                cellData.put("C_CELL_TYPE_" + cellInstBM.getId(), cellType);
                cellData.put("C_CHECK_JOIN_" + cellInstBM.getId(), checkJoin);
                cellData.put("C_PRODUCT_ID_" + cellInstBM.getId(), productId);
                cellData.put(headerTableInstName + "_" + schemaId + "_ID", headerId);
                cellData.put(rowTableInstName + "_" + schemaId + "_ID", rowId);
                //主要是方便获取单元格，不需要多次查询数据库
                cellData.put(tempTableInstName + "_" + schemaId + "_ID", newTempId);
                orientSqlEngine.getBmService().insertModelData(cellInstBM, cellData);
            }
        }
        //插入任务模板管理中实例表头表尾
        cellInstBM.clearAllFilter();
        cellInstBM.setReserve_filter("AND " + tempTableInstName + "_" + schemaId + "_ID='" + checkTableTempId + "'" + " AND C_IS_HEADER_" + cellInstBM.getId() + " IS NOT NULL");
        List<Map> cellHeadEndList = orientSqlEngine.getBmService().createModelQuery(cellInstBM).list();
        if (cellHeadEndList.size() > 0) {
            for (Map cellMap : cellHeadEndList) {
                String value = CommonTools.Obj2String(cellMap.get("C_CONTENT_" + cellInstBM.getId()));
                value = "".equals(value) ? "" : value;
                String cellType = CommonTools.Obj2String(cellMap.get("C_CELL_TYPE_" + cellInstBM.getId()));
                String isHeader = CommonTools.Obj2String(cellMap.get("C_IS_HEADER_" + cellInstBM.getId()));
                Map<String, String> cellData = UtilFactory.newHashMap();
                cellData.put("C_CONTENT_" + cellInstBM.getId(), value);
                cellData.put("C_CELL_TYPE_" + cellInstBM.getId(), cellType);
                cellData.put("C_IS_HEADER_" + cellInstBM.getId(), isHeader);
                //主要是方便获取单元格，不需要多次查询数据库
                cellData.put(tempTableInstName + "_" + schemaId + "_ID", newTempId);
                orientSqlEngine.getBmService().insertModelData(cellInstBM, cellData);
            }
        }
    }

    public ExtComboboxResponseData<ExtComboboxData> getEnumFlowTempType() {
        ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();

        IBusinessModel flowTempTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.FLOW_TEMP_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map> flowTempTypeList = orientSqlEngine.getBmService().createModelQuery(flowTempTypeBM).list();
        if (flowTempTypeList.size() > 0) {
            for (Map flowMap : flowTempTypeList) {
                String flowTempId = (String) flowMap.get("ID");
                String flowTempName = (String) flowMap.get("C_NAME_" + flowTempTypeBM.getId());
                ExtComboboxData cb = new ExtComboboxData();
                cb.setId(flowTempId);
                cb.setValue(flowTempName);
                retVal.getResults().add(cb);
            }
        } else {
            ExtComboboxData cb = new ExtComboboxData();
            cb.setId("");
            cb.setValue("");
            retVal.getResults().add(cb);
        }
        retVal.setTotalProperty(retVal.getResults().size());
        return retVal;
    }

    public ExtComboboxResponseData<ExtComboboxData> getEnumTasKType() {
        ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();
        ExtComboboxData cb = new ExtComboboxData();
        String taskTypeId = "维护任务";
        String taskTypeName = "维护任务";
        cb.setId(taskTypeId);
        cb.setValue(taskTypeName);
        retVal.getResults().add(cb);
        taskTypeId = "潜次任务";
        taskTypeName = "潜次任务";
        cb = new ExtComboboxData();
        cb.setId(taskTypeId);
        cb.setValue(taskTypeName);
        retVal.getResults().add(cb);
        retVal.setTotalProperty(retVal.getResults().size());
        return retVal;
    }

    public AjaxResponseData getHangciTime(String hangciId) throws Exception {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel hangciBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGCI, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> hangciMap = orientSqlEngine.getBmService().createModelQuery(hangciBM).findById(hangciId);
        List<String> hangciTimeList = UtilFactory.newArrayList();
        if (hangciMap != null) {
            String planStartTime = hangciMap.get("C_PLAN_BEGIN_TIME_" + hangciBM.getId());
            String planEndTime = CommonTools.Obj2String(hangciMap.get("C_PLAN_ENDING_TIME_" + hangciBM.getId()));
            hangciTimeList.add(planStartTime);
            hangciTimeList.add(planEndTime);
            retVal.setResults(hangciTimeList);
        }
        return retVal;
    }

    public AjaxResponseData getHangduanTime(String hangduanId) throws Exception {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, String> hangduanMap = orientSqlEngine.getBmService().createModelQuery(hangduanBM).findById(hangduanId);
        List<String> hangduanTimeList = UtilFactory.newArrayList();
        if (hangduanMap != null) {
            String planStartTime = hangduanMap.get("C_PLAN_START_TIME_" + hangduanBM.getId());
            String planEndTime = CommonTools.Obj2String(hangduanMap.get("C_PLAN_END_TIME_" + hangduanBM.getId()));
            hangduanTimeList.add(planStartTime);
            hangduanTimeList.add(planEndTime);
            retVal.setResults(hangduanTimeList);
        }
        return retVal;
    }

    public ExtGridData<Map> queryPersonWeight(String orientModelId, String isView, Integer page, Integer
            pagesize, String customerFilter,
                                              Boolean dataChange, String sort, String hangduanId) {
        ExtGridData<Map> retVal = new ExtGridData<>();

        IBusinessModel hangduanModel = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel personWeightModel = businessModelService.getBusinessModelBySName(PropertyConstant.PERSON_WEIGHT, schemaId, EnumInter.BusinessModelEnum.Table);
        Map hangduanMap = orientSqlEngine.getBmService().createModelQuery(hangduanModel).findById(hangduanId);
        String attendPersonIds = "";
        if (hangduanMap != null) {
            attendPersonIds = CommonTools.Obj2String(hangduanMap.get("C_ATTEND_PERSON_" + hangduanModel.getId()));
        }
        List<String> attendPersonList = UtilFactory.newArrayList();
        if (attendPersonIds != "") {
            String attendPersonId[] = attendPersonIds.split(",");
            attendPersonList = new ArrayList<>(Arrays.asList(attendPersonId));
        }
        personWeightModel.setReserve_filter("AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'");
        List<Map> personWeightList = orientSqlEngine.getBmService().createModelQuery(personWeightModel).list();
        if (personWeightList.size() > 0) {
            for (Map personWeightMap : personWeightList) {
                String personWeightKeyId = CommonTools.Obj2String(personWeightMap.get("ID"));
                String personId = CommonTools.Obj2String(personWeightMap.get("C_ATTEND_ID_" + personWeightModel.getId()));
                if (attendPersonList.size() > 0) {
                    if (attendPersonList.contains(personId)) {
                        //移除相同的元素，剩下的就是需要新插入到人员重力表中
                        attendPersonList.removeIf(s -> s.equals(personId));
                    }
                    //不包含，说明是需要删除
                    else {
                        orientSqlEngine.getBmService().deleteCascade(personWeightModel, personWeightKeyId);
                    }
                } else {
                    orientSqlEngine.getBmService().deleteCascade(personWeightModel, personWeightKeyId);
                }
            }
        }
        //attendPersonList剩下的元素插入到personWeight中；personWeight的list集合为0，attendPersonList的所有元素插入
        if (attendPersonList.size() > 0) {
            for (String attendPerson : attendPersonList) {
                Map personWeightMap = UtilFactory.newHashMap();
                personWeightMap.put("C_ATTEND_ID_" + personWeightModel.getId(), attendPerson);
                personWeightMap.put("T_HANGDUAN_" + schemaId + "_ID", hangduanId);
                orientSqlEngine.getBmService().insertModelData(personWeightModel, personWeightMap);
            }
        }
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        if (!org.apache.commons.lang.StringUtils.isEmpty(customerFilter)) {
            Map clazzMap = new HashMap();
            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, clazzMap);
            businessModel.clearAllFilter();
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
        List<Map> dataList = businessModelQuery.orderAsc("TO_NUMBER(ID)").list();
        if (dataList.size() > 0) {
            String postIds = "";
            StringBuilder attendPerson = new StringBuilder();
            for (Map map : dataList) {
                String attendId = CommonTools.Obj2String(map.get("C_ATTEND_ID_" + orientModelId));
                if (!"".equals(attendId)) {
                    attendPerson.append(attendId).append(",");
                }
            }
            String userIds = attendPerson.toString();
            userIds = userIds.substring(0, userIds.length() - 1);

            StringBuilder userSql = new StringBuilder();
            userSql.append("select id,all_name from cwm_sys_user where id in(").append(userIds).append(")");
            List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());

            for (Map dataMap : dataList) {
                String attendPersonId = CommonTools.Obj2String(dataMap.get("C_ATTEND_ID_" + orientModelId));
                String keyId = CommonTools.Obj2String(dataMap.get("ID"));
                for (Map userMap : userList) {
                    String userKeyId = CommonTools.Obj2String(userMap.get("id"));
                    String userName = CommonTools.Obj2String(userMap.get("all_name"));
                    if (attendPersonId.equals(userKeyId)) {
                        dataMap.put("C_ATTEND_ID_" + orientModelId + "_DISPLAY", userKeyId);
                        dataMap.put("C_ATTEND_ID_" + orientModelId, userName);
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

    public AjaxResponseData<FlowPostData> getFlowPostHead(String hangduanId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR,
                schemaId, EnumInter.BusinessModelEnum.Table);
        FlowPostData flowPostData = new FlowPostData();
        List<Field> fieldList = initFields(postBM, false);
        List<Column> columnList = initColumn(postBM, false);
//        List<Map> content = initContent(postBM, divingTaskBM, hangciId);
        flowPostData.setFields(fieldList);
        flowPostData.setColumns(columnList);
//        flowPostData.setContent(content);
        retVal.setResults(flowPostData);
        return retVal;
    }

    private List<Field> initFields(IBusinessModel bm, boolean isAttendTimesTab) {
        List<Field> fieldList = new ArrayList<>();
        bm.setReserve_filter(" AND C_POST_TYPE_" + bm.getId() + " LIKE '%" + "流动" + "%'");
        List<Map> mapList = this.orientSqlEngine.getBmService().createModelQuery(bm).orderAsc("C_POST_NAME_" + bm.getId()).list();
        if (isAttendTimesTab) {
            fieldList.add(new Field("userName"));
        } else {
            fieldList.add(new Field("divingName"));
        }
        if (mapList != null && mapList.size() > 0) {
            for (int i = 0; i < mapList.size(); i++) {
                Field field = new Field();
                field.setName((String) mapList.get(i).get("ID"));
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    private List<Column> initColumn(IBusinessModel bm, boolean isAttendTimesTab) {
        List<Column> columnList = new ArrayList<>();
        Column divingTaskCol = new Column();
//        divingTaskCol.setFlex(2);
        divingTaskCol.setWidth("auto");
        if (isAttendTimesTab) {
            divingTaskCol.setDataIndex("userName");
            divingTaskCol.setHeader("姓名");
        } else {
            divingTaskCol.setDataIndex("divingName");
            divingTaskCol.setHeader("潜次名称");
        }
        divingTaskCol.setMeType("固定");
        columnList.add(divingTaskCol);
        bm.setReserve_filter(" AND C_POST_TYPE_" + bm.getId() + " LIKE '%" + "流动" + "%'");
        List<Map> mapList = this.orientSqlEngine.getBmService().createModelQuery(bm).orderAsc("C_POST_NAME_" + bm.getId()).list();
        if (mapList != null && mapList.size() > 0) {
            for (int i = 0; i < mapList.size(); i++) {
                Column column = new Column();
//                column.setFlex(2);
                column.setWidth("auto");
                column.setHeader((String) mapList.get(i).get("C_POST_NAME_" + bm.getId()));
                column.setDataIndex((String) mapList.get(i).get("ID"));
                column.setMeType((String) mapList.get(i).get("C_POST_TYPE_" + bm.getId()));
                if (column.getMeType().equalsIgnoreCase("流动")) {

//                    column.setXtype("actioncolumn");
                }
                columnList.add(column);
            }
        }
        return columnList;
    }

    public AjaxResponseData<FlowPostData> getAttendTimesFlowPostHead(String hangduanId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR,
                schemaId, EnumInter.BusinessModelEnum.Table);
        FlowPostData flowPostData = new FlowPostData();
        List<Field> fieldList = initFields(postBM, true);
        List<Column> columnList = initColumn(postBM, true);
//        List<Map> content = initContent(postBM, divingTaskBM, hangciId);
        flowPostData.setFields(fieldList);
        flowPostData.setColumns(columnList);
//        flowPostData.setContent(content);
        retVal.setResults(flowPostData);
        return retVal;
    }

    public ExtGridData<Map<String, Object>> getDivingTaskFlowPost(String start, String limit, String hangduanId) {
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR,
                schemaId, EnumInter.BusinessModelEnum.Table);
        List divingTaskList = new ArrayList<>();
        int totalcount = 0;
        int limitt = Integer.parseInt(limit);
        int startt = Integer.parseInt(start);

        divingTaskBM.setReserve_filter(" AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'");
        List<Map> divingTaskMapList = this.orientSqlEngine.getBmService().createModelQuery(divingTaskBM).orderAsc("C_TASK_NAME_" + divingTaskBM.getId()).list();
        totalcount = divingTaskMapList.size();
        int end = startt + limitt;//防止尾页越界
        if (divingTaskMapList.size() < end) {
            end = divingTaskMapList.size();
        }
        List<Column> columnList = initColumn(postBM, false);
        for (int i = startt; i < end; i++) {
            Map instMap = new HashMap<>();
            String taskName = CommonTools.Obj2String(divingTaskMapList.get(i).get("C_TASK_NAME_" + divingTaskBM.getId()));
            String taskId = CommonTools.Obj2String(divingTaskMapList.get(i).get("ID"));
            String taskState = CommonTools.Obj2String(divingTaskMapList.get(i).get("C_STATE_" + divingTaskBM.getId()));
            List<Map> attendPersonList = UtilFactory.newArrayList();
            if (columnList.size() > 0) {
                int count = 0;
                for (Column column : columnList) {
                    if (count < columnList.size()) {
                        String postId = column.getDataIndex();
                        String type = column.getMeType();
                        if (type.equalsIgnoreCase("流动")) {
                            Map attendPersonMap = getChoosedAttendPerson(taskId, postId);
                            if (attendPersonMap != null) {
                                attendPersonMap.put("postId", postId);
                                attendPersonMap.put("attendPersonIds", attendPersonMap.get("attendPersonIds"));
                                attendPersonMap.put("attendPersonNames", attendPersonMap.get("attendPersonNames"));
                                attendPersonList.add(attendPersonMap);
                                count++;
                            }
                        }
                    }
                }
            }
            instMap.put("taskId", taskId);
            instMap.put("divingName", taskName);
            instMap.put("taskState", taskState);
            instMap.put("attendPersonList", attendPersonList);
            divingTaskList.add(instMap);
        }
        return new ExtGridData<>(divingTaskList, totalcount);
    }


    public ExtGridData<Map<String, Object>> getFlowPostAttendTimes(String start, String limit, String hangduanId) {
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel attendPersonBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON,
                schemaId, EnumInter.BusinessModelEnum.Table);

        List divingTaskList = new ArrayList<>();
        int totalcount = 0;
        int limitt = Integer.parseInt(limit);
        int startt = Integer.parseInt(start);

        Map<String, String> hangduanMap = this.orientSqlEngine.getBmService().createModelQuery(hangduanBM).findById(hangduanId);
        String attendPersonIds = hangduanMap.get("C_ATTEND_PERSON_" + hangduanBM.getId());
        List<Map<String, Object>> userList = UtilFactory.newArrayList();
        if (!"".equals(attendPersonIds)) {
//           String attendPersonId[] = attendPersonIds.split(",");
//           attendPersonIdsList = new ArrayList<>(Arrays.asList(attendPersonId));

            StringBuilder userSql = new StringBuilder();
            //只需要统计潜航员的参与次数
            userSql.append("select id,all_name from cwm_sys_user where id in(").append(attendPersonIds).append(")").append(" and person_classify=1");
            userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());
        }
        totalcount = userList.size();
        int end = startt + limitt;//防止尾页越界
        if (userList.size() < end) {
            end = userList.size();
        }
        List<Field> fieldList = initFields(postBM, true);
        List<Map<String, Object>> attendTimeList = UtilFactory.newArrayList();
        if (fieldList.size() > 0) {
            for (Field field : fieldList) {
                String userName = field.getName();
                if (userName.equalsIgnoreCase("userName")) {
                    continue;
                } else {
                    String flowPostId = userName;
                    attendPersonBM.clearAllFilter();
                    attendPersonBM.setReserve_filter("AND C_ATTEND_POST_" + attendPersonBM.getId() + " like '%" + flowPostId + "%'" + " AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'");
                    List<Map<String, Object>> attendPersonsList = orientSqlEngine.getBmService().createModelQuery(attendPersonBM).orderAsc("C_ATTEND_PERSON_" + attendPersonBM.getId()).list();
                    if (attendPersonsList.size() > 0) {
                        for (Map personMap : attendPersonsList) {
                            int count = 0;
                            String userId = CommonTools.Obj2String(personMap.get("C_ATTEND_PERSON_" + attendPersonBM.getId()));
                            List<String> attendPostList = UtilFactory.newArrayList();
                            String attendPostIds = CommonTools.Obj2String(personMap.get("C_ATTEND_POST_" + attendPersonBM.getId()));
                            if (attendPostIds != "") {
                                String attendPersonId[] = attendPostIds.split(",");
                                attendPostList = new ArrayList<>(Arrays.asList(attendPersonId));
                                if (attendPostList.contains(flowPostId)) {
                                    int j = 0;
                                    if (attendTimeList.size() > 0) {
                                        for (int i = 0; i < attendTimeList.size(); i++) {
                                            Map attendTimeMap = attendTimeList.get(i);
                                            String hasUserId = attendTimeMap.get("userId").toString();
                                            String hasPostId = attendTimeMap.get("postId").toString();
                                            int hasCount = Integer.parseInt(attendTimeMap.get("count").toString());
                                            //attendTimeList集合中存在相同的userId及相同的岗位，则hasCout++自增
                                            if (userId.equals(hasUserId) && flowPostId.equals(hasPostId)) {
                                                hasCount++;
                                                attendTimeMap.put("count", hasCount);
                                                j++;
                                            }
                                        }
                                    }
                                    //相同的姓名，对应不同的岗位插入到attendTimeList中
                                    if (j == 0) {
                                        count++;
                                        Map attendTimeMap = UtilFactory.newHashMap();
                                        attendTimeMap.put("userId", userId);
                                        attendTimeMap.put("postId", flowPostId);
                                        attendTimeMap.put("count", count);
                                        attendTimeList.add(attendTimeMap);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = startt; i < end; i++) {
            Map instMap = new HashMap<>();
            String allName = CommonTools.Obj2String(userList.get(i).get("all_name"));
            String userId = CommonTools.Obj2String(userList.get(i).get("ID"));
            if (attendTimeList.size() > 0) {
                for (Map attendMap : attendTimeList) {
                    String attendPersonId = attendMap.get("userId").toString();
                    String postId = attendMap.get("postId").toString();
                    String count = attendMap.get("count").toString();
                    if (userId.equals(attendPersonId)) {
                        instMap.put(postId, count);
                    }
                }
            }
            instMap.put("userId", userId);
            instMap.put("userName", allName);
            divingTaskList.add(instMap);
        }
        return new ExtGridData<>(divingTaskList, totalcount);
    }

    private Map getChoosedAttendPerson(String taskId, String postId) {
        Map attendPersonMap = UtilFactory.newHashMap();
        IBusinessModel attendPersonBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON,
                schemaId, EnumInter.BusinessModelEnum.Table);
        attendPersonBM.setReserve_filter("AND C_ATTEND_POST_" + attendPersonBM.getId() + " like '%" + postId + "%'" +
                " AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
        List<Map<String, Object>> attendPersonsList = orientSqlEngine.getBmService().createModelQuery(attendPersonBM).list();
        if (attendPersonsList.size() > 0) {
            StringBuilder attendPersonBuilder = new StringBuilder();
            for (Map personMap : attendPersonsList) {
                String personId = CommonTools.Obj2String(personMap.get("C_ATTEND_PERSON_" + attendPersonBM.getId()));
                List<String> attendPostList = UtilFactory.newArrayList();
                String attendPostIds = CommonTools.Obj2String(personMap.get("C_ATTEND_POST_" + attendPersonBM.getId()));
                if (attendPostIds != "") {
                    String attendPersonId[] = attendPostIds.split(",");
                    attendPostList = new ArrayList<>(Arrays.asList(attendPersonId));
                    for (String attendPostId : attendPostList) {
                        if (postId.equals(attendPostId)) {
                            attendPersonBuilder.append(personId).append(",");
                            break;
                        }
                    }
                }
            }
            String attendPersons = attendPersonBuilder.toString();

            if (attendPersons != null && !"".equals(attendPersons)) {
                attendPersons = attendPersons.substring(0, attendPersons.length() - 1);
                StringBuilder userSql = new StringBuilder();
                userSql.append("select id,all_name from cwm_sys_user where id in(").append(attendPersons).append(")");
                List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());
                StringBuilder allNames = new StringBuilder();
                StringBuilder postPersonIds = new StringBuilder();
                if (userList.size() > 0) {
                    for (Map userMap : userList) {
                        String allName = CommonTools.Obj2String(userMap.get("all_name"));
                        allNames.append(allName).append(",");
                        String postPersonId = (String) userMap.get("ID");
                        postPersonIds.append(postPersonId).append(",");
                    }
                    String userNames = allNames.toString();
                    userNames = userNames.substring(0, userNames.length() - 1);
                    String personIds = postPersonIds.toString();
                    personIds = personIds.substring(0, personIds.length() - 1);
                    attendPersonMap.put("attendPersonNames", userNames);
                    attendPersonMap.put("attendPersonIds", personIds);
                }
            }
        }
        return attendPersonMap;
    }

    public AjaxResponseData againTable(String taskId, String nodeId, String nodeText) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel checkInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel headerInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);

        checkInstBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "' AND C_NODE_ID_" + checkInstBM.getId() + "='" + nodeId + "'");
        List<Map> checkInstList = orientSqlEngine.getBmService().createModelQuery(checkInstBM).orderAsc("TO_NUMBER(ID)").list();
        if (checkInstList.size() > 0) {
            Set<Map> recordMaxAgrinFlagSet = UtilFactory.newHashSet();
            for (Map checkMap : checkInstList) {
                String againFlag = CommonTools.Obj2String(checkMap.get("C_AGAIN_FLAG_" + checkInstBM.getId()));
                String checkTempId = CommonTools.Obj2String(checkMap.get("C_CHECK_TEMP_ID_" + checkInstBM.getId()));
                againFlag = againFlag == "" ? "0" : againFlag;
//                int againFlatInt=Integer.parseInt(againFlag);
                int count = 0;
                if (recordMaxAgrinFlagSet != null && recordMaxAgrinFlagSet.size() > 0) {
                    for (Map recordMap : recordMaxAgrinFlagSet) {
                        String recordCheckTempId = CommonTools.Obj2String(recordMap.get("C_CHECK_TEMP_ID_" + checkInstBM.getId()));
                        String recordAgainFlag = CommonTools.Obj2String(recordMap.get("C_AGAIN_FLAG_" + checkInstBM.getId()));
                        recordAgainFlag = "".equals(recordAgainFlag) ? "0" : recordAgainFlag;
                        int recordAgainFlagInt = Integer.parseInt(recordAgainFlag);
                        if (checkTempId.equals(recordCheckTempId)) {
                            count++;
                            if (Integer.parseInt(againFlag) > recordAgainFlagInt) {
                                recordMaxAgrinFlagSet.remove(recordMap);
                                recordMaxAgrinFlagSet.add(checkMap);
                                break;
                            }
                        }
                    }
                }
                if (count == 0) {
                    recordMaxAgrinFlagSet.add(checkMap);
                }
            }
            if (recordMaxAgrinFlagSet != null && recordMaxAgrinFlagSet.size() > 0) {
                List<String> oldCheckInstIdsList = UtilFactory.newArrayList();
                List<String> newCheckInstIdsList = UtilFactory.newArrayList();
                Map checkInstTypeMap = UtilFactory.newHashMap();
                for (Map checkMap : recordMaxAgrinFlagSet) {
                    String oldCheckInstId = checkMap.get("ID").toString();
                    String oldCheckInstType = CommonTools.Obj2String(checkMap.get("C_INS_TYPE_" + checkInstBM.getId()));
                    checkInstTypeMap.put(oldCheckInstId, oldCheckInstType);
                    oldCheckInstIdsList.add(oldCheckInstId);
                    String recordAgainFlag = CommonTools.Obj2String(checkMap.get("C_AGAIN_FLAG_" + checkInstBM.getId()));
                    String checkName = CommonTools.Obj2String(checkMap.get("C_NAME_" + checkInstBM.getId()));
                    recordAgainFlag = recordAgainFlag == "" ? "0" : recordAgainFlag;
                    checkMap.remove("ID");
                    checkMap.remove("C_CHECK_PERSON_" + checkInstBM.getId());
                    checkMap.remove("C_CHECK_TIME_" + checkInstBM.getId());
                    checkMap.remove("C_ATTENTION_" + checkInstBM.getId());
                    int newRecordAgrinFlag = Integer.parseInt(recordAgainFlag) + 1;
                    checkMap.put("C_AGAIN_FLAG_" + checkInstBM.getId(), newRecordAgrinFlag);
                    checkMap.put("C_CHECK_STATE_" + checkInstBM.getId(), "未完成");
//                    long timeTamp=System.currentTimeMillis();
                    if (!"0".equals(recordAgainFlag)) {
                        int index = checkName.lastIndexOf("_");
                        checkName = checkName.substring(0, index);
                    }
                    checkMap.put("C_NAME_" + checkInstBM.getId(), checkName + "_" + newRecordAgrinFlag);
                    String newCheckInstId = orientSqlEngine.getBmService().insertModelData(checkInstBM, checkMap);
                    newCheckInstIdsList.add(newCheckInstId);
                    //插入表头、行、单元格实例
//                    StringBuilder headerSql = new StringBuilder();
//                    headerSql.append("select * from T_CHECK_HEADER_INST_" + schemaId).append(" where 1=1").append(" and T_CHECK_TEMP_INST_" + schemaId + "_ID =?").append("order by ID ASC");
//                    List<Map<String, Object>> headerList = metaDAOFactory.getJdbcTemplate().queryForList(headerSql.toString(), oldCheckInstId);
//                    copyCheckInstList(headerList, newCheckInstId, oldCheckInstId);
//                    retVal.setSuccess(true);
//                    retVal.setMsg("重做成功！");
                }
                commonCopyCheckTable(oldCheckInstIdsList, newCheckInstIdsList, headerInstBM, rowInstBM, cellInstBM, checkInstBM, false, checkInstTypeMap);
                retVal.setSuccess(true);
                retVal.setMsg("重做成功！");
            }
        } else {
            retVal.setSuccess(false);
            retVal.setMsg("该流程下还未有表格，暂不支持重做！");
        }
        return retVal;
    }

    //检查表实例再复制一份成为相同的检查表
    public void commonCopyCheckTable(List oldCheckInstIdsList, List newCheckInstIdsList, IBusinessModel headerInstBM, IBusinessModel rowInstBM, IBusinessModel cellInstBM, IBusinessModel checkInstBM, boolean bindWay, Map checkInstTypeMap) {
        String headTableName = headerInstBM.getS_table_name();
        String rowTableName = rowInstBM.getS_table_name();
        String cellTableName = cellInstBM.getS_table_name();
        String checkInstTableName = checkInstBM.getS_table_name();
        String headModelId = headerInstBM.getId();
        String rowModelId = rowInstBM.getId();
        String cellModelId = cellInstBM.getId();

        String oldCheckInstIds = Joiner.on(",").join(oldCheckInstIdsList);

        StringBuilder headerSql = new StringBuilder();
        headerSql.append("select * from " + headTableName).append(" where 1=1").append(" and " + checkInstTableName + "_ID IN (").append(oldCheckInstIds).append(") order by ID ASC");
        List<Map<String, Object>> headerList = metaDAOFactory.getJdbcTemplate().queryForList(headerSql.toString());

        rowInstBM.setReserve_filter("AND " + checkInstTableName + "_ID" + " IN (" + oldCheckInstIds + ")");
        List<Map<String, Object>> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).orderAsc("TO_NUMBER(" + checkInstTableName + "_ID),TO_NUMBER(C_ROW_NUMBER_" + rowInstBM.getId() + ")").list();

        cellInstBM.setReserve_filter("AND " + checkInstTableName + "_ID IN(" + oldCheckInstIds + ")");
        List<Map<String, Object>> cellInstList = orientSqlEngine.getBmService().createModelQuery(cellInstBM).orderAsc("TO_NUMBER(" + checkInstTableName + "_ID),TO_NUMBER(ID)").list();


        if (bindWay) {
            headerInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
            cellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
            rowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        }

        Map<String, List> newHeaderMap = UtilFactory.newHashMap();
        List<Map> newHeaderList = UtilFactory.newArrayList();
        Map recordNewHeaderMap = UtilFactory.newHashMap();
        String recordCheckInstId = "";
        int headCount = 0;
        for (Map headerMap : headerList) {
            String oldHeaderId = headerMap.get("ID").toString();
            String refCheckInstId = CommonTools.Obj2String(headerMap.get(checkInstTableName + "_ID"));
            Map<String, String> insertHeadMap = UtilFactory.newHashMap();

            if (!recordCheckInstId.equals(refCheckInstId) && !"".equals(recordCheckInstId)) {
                newHeaderList.add(recordNewHeaderMap);
                newHeaderMap.put(recordCheckInstId, newHeaderList);
                newHeaderList = UtilFactory.newArrayList();
                recordNewHeaderMap = UtilFactory.newHashMap();
                headCount++;
            }
            for (int i = 0; i < oldCheckInstIdsList.size(); i++) {
                if (refCheckInstId.equals(oldCheckInstIdsList.get(i))) {
                    recordCheckInstId = refCheckInstId;
//                    headerMap.remove("ID");
                    insertHeadMap.put("C_NAME_" + headerInstBM.getId(), CommonTools.Obj2String(headerMap.get("C_NAME_" + headModelId)));
                    insertHeadMap.put(PropertyConstant.CHECK_TEMP_INST + "_" + schemaId + "_ID", newCheckInstIdsList.get(i).toString());
                    String newHeaderId = orientSqlEngine.getBmService().insertModelData(headerInstBM, insertHeadMap);
                    recordNewHeaderMap.put(oldHeaderId, newHeaderId);
                    break;
                }
            }
        }
        if (headCount == 0) {
            newHeaderList.add(recordNewHeaderMap);
            newHeaderMap.put(recordCheckInstId, newHeaderList);
        } else if (newHeaderMap.size() < oldCheckInstIdsList.size()) {
            newHeaderList.add(recordNewHeaderMap);
            newHeaderMap.put(recordCheckInstId, newHeaderList);
        }
        Map newRowMap = UtilFactory.newHashMap();
        List<Map> newRowList = UtilFactory.newArrayList();
        Map recordNewRowMap = UtilFactory.newHashMap();
        String oldCheckInstId = "";

        int rowCount = 0;
        if (rowInstList.size() > 0) {
            Map checkType2Map = UtilFactory.newHashMap();
            for (Map rowInstMap : rowInstList) {
                String oldRowInstId = rowInstMap.get("ID").toString();
                String refCheckInstId = CommonTools.Obj2String(rowInstMap.get(checkInstTableName + "_ID"));
                String rowValue = CommonTools.Obj2String(rowInstMap.get("C_ROW_NUMBER_" + rowModelId));
                String productId = CommonTools.Obj2String(rowInstMap.get("C_PRODUCT_ID_" + rowModelId));

                //2类型的检查表只复制检查表的第一行
                if ("2".equals(checkInstTypeMap.get(refCheckInstId))) {
                    if (Integer.parseInt(rowValue) > 1) {
                        continue;
                    } else if ("1".equals(rowValue)) {
                        checkType2Map.put(refCheckInstId, oldRowInstId);
                    }
//                    if (oldCheckInstIdList != null && oldCheckInstIdList.size() > 0) {
//                        if (!oldCheckInstIdList.contains(refCheckInstId)) {
//                            oldCheckInstIdList.add(refCheckInstId);
//                            oldRowInstIdList.add(oldRowInstId);
//                        }
//                        if (oldRowInstIdList != null && oldRowInstIdList.size() > 0) {
//                            if (!oldRowInstIdList.contains(oldRowInstId)) {
//                                    continue;
//                            }
//                        }
//                    } else {
//                        oldRowInstIdList.add(oldRowInstId);
//                        oldCheckInstIdList.add(refCheckInstId);
//                    }
                }

                Map<String, String> insertRowMap = UtilFactory.newHashMap();

//                rowInstMap.remove("C_DEVICE_INST_ID_" + rowInstBM.getId());
                if (!oldCheckInstId.equals(refCheckInstId) && !"".equals(oldCheckInstId)) {
                    newRowList.add(recordNewRowMap);
                    newRowMap.put(oldCheckInstId, newRowList);
                    newRowList = UtilFactory.newArrayList();
                    recordNewRowMap = UtilFactory.newHashMap();
                    rowCount++;
                }
                for (int i = 0; i < oldCheckInstIdsList.size(); i++) {
                    if (refCheckInstId.equals(oldCheckInstIdsList.get(i))) {
                        oldCheckInstId = refCheckInstId;
//                        rowInstMap.remove("ID");
                        insertRowMap.put("C_ROW_NUMBER_" + rowInstBM.getId(), rowValue);
                        insertRowMap.put("C_PRODUCT_ID_" + rowInstBM.getId(), productId);
                        insertRowMap.put(PropertyConstant.CHECK_TEMP_INST + "_" + schemaId + "_ID", newCheckInstIdsList.get(i).toString());
                        String newRowInstId = orientSqlEngine.getBmService().insertModelData(rowInstBM, insertRowMap);
                        recordNewRowMap.put(oldRowInstId, newRowInstId);
                        break;
                    }
                }
            }
            if (rowCount == 0) {
                newRowList.add(recordNewRowMap);
                newRowMap.put(oldCheckInstId, newRowList);
            } else if (newRowMap.size() < oldCheckInstIdsList.size()) {
                newRowList.add(recordNewRowMap);
                newRowMap.put(oldCheckInstId, newRowList);
            }
            if (cellInstList.size() > 0) {
                for (Map cellInstMap : cellInstList) {
                    String isHeaderType = CommonTools.Obj2String(cellInstMap.get("C_IS_HEADER_" + cellModelId));
                    String refCheckInstId = cellInstMap.get(checkInstTableName + "_ID").toString();
                    String refHeadInstId = CommonTools.Obj2String(cellInstMap.get(headTableName + "_ID"));
                    String refRowInstId = CommonTools.Obj2String(cellInstMap.get(rowTableName + "_ID"));
                    //2类型的检查表只复制检查表的第一行单元格数据及表头和表尾内容
                    if ("2".equals(checkInstTypeMap.get(refCheckInstId))) {
                        if (!"".equals(refRowInstId) && !refRowInstId.equals(checkType2Map.get(refCheckInstId))) {
                            continue;
                        }
                    }
//                    cellInstMap.remove("ID");
//                    cellInstMap.remove("C_EXCEPTION_CELLID_" + cellInstBM.getId());

                    String value = CommonTools.Obj2String(cellInstMap.get("C_CONTENT_" + cellModelId));
                    value = "".equals(value) ? "" : value;
                    String cellType = CommonTools.Obj2String(cellInstMap.get("C_CELL_TYPE_" + cellModelId));
                    String checkJoin = CommonTools.Obj2String(cellInstMap.get("C_CHECK_JOIN_" + cellModelId));
                    String productId = CommonTools.Obj2String(cellInstMap.get("C_PRODUCT_ID_" + cellModelId));
                    Map<String, String> insertCellMap = UtilFactory.newHashMap();
                    insertCellMap.put("C_CONTENT_" + cellInstBM.getId(), value);
                    insertCellMap.put("C_CELL_TYPE_" + cellInstBM.getId(), cellType);
                    insertCellMap.put("C_CHECK_JOIN_" + cellInstBM.getId(), checkJoin);
                    insertCellMap.put("C_PRODUCT_ID_" + cellInstBM.getId(), productId);
                    insertCellMap.put("C_IS_HEADER_" + cellInstBM.getId(), isHeaderType);
                    //获取表头
                    List<Map> newHeaderIdList = (List) newHeaderMap.get(refCheckInstId);
                    for (Map headerMap : newHeaderIdList) {
                        Set<String> keySet = headerMap.keySet();
                        Iterator<String> iterator = keySet.iterator();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            if (key.equals(refHeadInstId)) {
                                insertCellMap.put(PropertyConstant.CHECK_HEADER_INST + "_" + schemaId + "_ID", headerMap.get(key).toString());
                                break;
                            }
                        }
                    }
                    //获取行list
                    List<Map> newRowIdList = (List) newRowMap.get(refCheckInstId);
                    for (Map rowMap : newRowIdList) {
                        Set<String> keySet = rowMap.keySet();
                        Iterator<String> iterator = keySet.iterator();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            if (key.equals(refRowInstId)) {
                                insertCellMap.put(PropertyConstant.CHECK_ROW_INST + "_" + schemaId + "_ID", rowMap.get(key).toString());
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < oldCheckInstIdsList.size(); i++) {
                        if (refCheckInstId.equals(oldCheckInstIdsList.get(i))) {
                            insertCellMap.put(PropertyConstant.CHECK_TEMP_INST + "_" + schemaId + "_ID", newCheckInstIdsList.get(i).toString());
                            orientSqlEngine.getBmService().insertModelData(cellInstBM, insertCellMap);
                            break;
                        }
                    }
                }
            }
        }

    }

    public AjaxResponseData easyDivingTask(String taskId, String hangduanId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel attendPersonBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel nodeDesignBM = businessModelService.getBusinessModelBySName(PropertyConstant.NODE_DESIGN, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel nodePostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headerInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);

        divingTaskBM.setReserve_filter("AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'" +
                " AND ID='" + taskId + "'");
        List<Map> divingTaskList = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).list();
        Map divingTaskMap = divingTaskList.get(0);
        String hangciId = CommonTools.Obj2String(divingTaskMap.get("T_HANGCI_" + schemaId + "_ID"));
        divingTaskMap.remove("ID");
        divingTaskMap.remove("C_PLAN_START_TIME_" + divingTaskBM.getId());
        divingTaskMap.remove("C_END_TIME_" + divingTaskBM.getId());
        divingTaskMap.remove("C_PLAN_DIVING_DEPTH_" + divingTaskBM.getId());
        divingTaskMap.remove("C_JINGDU_" + divingTaskBM.getId());
        divingTaskMap.remove("C_WEIDU_" + divingTaskBM.getId());
        divingTaskMap.remove("C_SEA_AREA_" + divingTaskBM.getId());
        divingTaskMap.remove("C_WATER_HOURS_" + divingTaskBM.getId());
        divingTaskMap.remove("C_POSITION_TIME_" + divingTaskBM.getId());
        divingTaskMap.remove("C_PLAN_THROW_TIME_" + divingTaskBM.getId());
        divingTaskMap.remove("C_FLOAT_TO_WTIME_" + divingTaskBM.getId());
        divingTaskMap.remove("C_TASK_NAME_" + divingTaskBM.getId());
        SimpleDateFormat taskNameFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        divingTaskMap.put("C_TASK_NAME_" + divingTaskBM.getId(), System.currentTimeMillis());
        //任务名称命名为唯一时间后缀
        divingTaskMap.put("C_TASK_NAME_" + divingTaskBM.getId(), taskNameFormat.format(new Date()));
        divingTaskMap.put("C_STATE_" + divingTaskBM.getId(), "未开始");
        String newTaskId = orientSqlEngine.getBmService().insertModelData(divingTaskBM, divingTaskMap);
//        hangduanBM.setReserve_filter("and id='" + hangduanId + "'");
//        List<Map> hangduanList = orientSqlEngine.getBmService().createModelQuery(hangduanBM).list();
//        //提前插入参与人员
//        String attendPersonIds = CommonTools.Obj2String(hangduanList.get(0).get("C_ATTEND_PERSON_" + hangduanBM.getId()));
//        List<Map> newAttendPersonList = UtilFactory.newArrayList();
//        if (attendPersonIds != "") {
//            String attendPersonId[] = attendPersonIds.split(",");
//            List<String> attendPersonList = new ArrayList<>(Arrays.asList(attendPersonId));
//            for (String everyPersonId : attendPersonList) {
//                Map insertPersonMap = UtilFactory.newHashMap();
//                insertPersonMap.put("C_ATTEND_PERSON_" + attendPersonBM.getId(), everyPersonId);
//                insertPersonMap.put("T_DIVING_TASK_" + schemaId + "_ID", newTaskId);
//                insertPersonMap.put("T_HANGDUAN_" + schemaId + "_ID", hangduanId);
//                insertPersonMap.put("C_ATTEND_POST_"+attendPersonBM.getId(),"");
//                String insertId = orientSqlEngine.getBmService().insertModelData(attendPersonBM, insertPersonMap);
//                insertPersonMap.put("ID", insertId);
//                newAttendPersonList.add(insertPersonMap);
//            }
//        }
        nodeDesignBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
        List<Map> nodeDesignList = orientSqlEngine.getBmService().createModelQuery(nodeDesignBM).orderAsc("TO_NUMBER(ID)").list();
        if (nodeDesignList != null && nodeDesignList.size() > 0) {
            Map nodeDesignMap = nodeDesignList.get(0);
            nodeDesignMap.remove("ID");
            nodeDesignMap.remove("T_DIVING_TASK_" + schemaId + "_ID");
            nodeDesignMap.remove("C_VERSON_" + nodeDesignBM.getId());
            nodeDesignMap.put("C_VERSON_" + nodeDesignBM.getId(), "0");
            nodeDesignMap.put("C_EDIT_TIME_" + nodeDesignBM.getId(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            nodeDesignMap.put("T_DIVING_TASK_" + schemaId + "_ID", newTaskId);
            String newNodeDesignId = orientSqlEngine.getBmService().insertModelData(nodeDesignBM, nodeDesignMap);
            //复制岗位模板
            nodePostBM.appendCustomerFilter(new CustomerFilter("T_DIVING_TASK_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, taskId));
            List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(nodePostBM).list();
            if (refPostList.size() > 0) {
                for (Map nodePostMap : refPostList) {
                    nodePostMap.remove("ID");
                    nodePostMap.remove("T_DIVING_TASK_" + schemaId + "_ID");
                    nodePostMap.put("T_DIVING_TASK_" + schemaId + "_ID", newTaskId);
                    String nodePostId = CommonTools.Obj2String(nodePostMap.get("C_POST_ID_" + nodePostBM.getId()));
                    String refPostType = CommonTools.Obj2String(nodePostMap.get("C_POST_TYPE_" + nodePostBM.getId()));
                    String newNodePostId = orientSqlEngine.getBmService().insertModelData(nodePostBM, nodePostMap);
//                    if ("固化".equals(refPostType)) {
//                        attendPersonBM.clearAllFilter();
//                        attendPersonBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
//                                " AND C_ATTEND_POST_" + attendPersonBM.getId() + " like '%" + nodePostId + "%'");
//                        List<Map> attendPersonList = orientSqlEngine.getBmService().createModelQuery(attendPersonBM).list();
//                        if (attendPersonList != null && attendPersonList.size() > 0) {
//                            for (Map attendPersonMap : attendPersonList) {
//                                String attendPersonId = CommonTools.Obj2String(attendPersonMap.get("C_ATTEND_PERSON_" + attendPersonBM.getId()));
//                                String attendPostIds = CommonTools.Obj2String(attendPersonMap.get("C_ATTEND_POST_" + attendPersonBM.getId()));
//                                if (!"".equals(attendPostIds)) {
//                                    String attendPostId[] = attendPostIds.split(",");
//                                    List<String> attendPostList = new ArrayList<>(Arrays.asList(attendPostId));
//                                    if (attendPostList.contains(nodePostId)) {
//                                        if (newAttendPersonList != null && newAttendPersonList.size() > 0) {
//                                            for (Map newAttendPersonMap : newAttendPersonList) {
//                                                String newAttendId = CommonTools.Obj2String(newAttendPersonMap.get("ID"));
//                                                String newAttendPeronId = CommonTools.Obj2String(newAttendPersonMap.get("C_ATTEND_PERSON_" + attendPersonBM.getId()));
//                                                String newAttendPostId =CommonTools.Obj2String(newAttendPersonMap.get("C_ATTEND_POST_"+attendPersonBM.getId()));
//                                                if (attendPersonId.equals(newAttendPeronId)) {
//                                                    Set<String> newAttendPostList = new HashSet<>();
//                                                    if ("".equals(newAttendPostId)) {
//                                                        newAttendPostId += nodePostId + ",";
//                                                        newAttendPostId = newAttendPostId.substring(0, newAttendPostId.length() - 1);
//                                                    } else {
//                                                        String newAttendPostIds[] = newAttendPostId.split(",");
//                                                        newAttendPostList = new HashSet<>(Arrays.asList(newAttendPostIds));
//                                                        newAttendPostList.add(nodePostId);
//                                                    }
//                                                    if (newAttendPostList.size() > 0) {
//                                                        newAttendPostId = Joiner.on(",").join(newAttendPostList);
//                                                    }
//                                                    newAttendPersonMap.put("C_ATTEND_POST_" + attendPersonBM.getId(), newAttendPostId);
//                                                    orientSqlEngine.getBmService().updateModelData(attendPersonBM, newAttendPersonMap, newAttendId);
//                                                    break;
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
                }
            }
            //复制参与人员
            attendPersonBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
            List<Map> attendPersonList = orientSqlEngine.getBmService().createModelQuery(attendPersonBM).list();
            if (attendPersonList != null && attendPersonList.size() > 0) {
                for (Map personMap : attendPersonList) {
                    personMap.remove("ID");
                    personMap.remove("T_DIVING_TASK_" + schemaId + "_ID");
                    personMap.put("T_DIVING_TASK_" + schemaId + "_ID", newTaskId);
                    orientSqlEngine.getBmService().insertModelData(attendPersonBM, personMap);
                }
            }
            //复制检查表模板实例
            checkTempInstBM.appendCustomerFilter(new CustomerFilter("T_DIVING_TASK_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, taskId));
            List<Map> checkInstList = orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).list();
            if (checkInstList.size() > 0) {
                List<String> oldCheckInstIdsList = UtilFactory.newArrayList();
                List<String> newCheckInstIdsList = UtilFactory.newArrayList();
                Map checkInstTypeMap = UtilFactory.newHashMap();
                for (Map<String, String> checkMap : checkInstList) {
                    String oldCheckInstId = (String) checkMap.get("ID");
                    String insType = checkMap.get("C_INS_TYPE_" + checkTempInstBM.getId());
                    Map checkInstMap = UtilFactory.newHashMap();
                    checkInstMap.put("C_NODE_ID_" + checkTempInstBM.getId(), checkMap.get("C_NODE_ID_" + checkTempInstBM.getId()));
                    checkInstMap.put("C_NODE_TEXT_" + checkTempInstBM.getId(), checkMap.get("C_NODE_TEXT_" + checkTempInstBM.getId()));
                    checkInstMap.put("C_NAME_" + checkTempInstBM.getId(), checkMap.get("C_NAME_" + checkTempInstBM.getId()));
                    checkInstMap.put("C_INS_TYPE_" + checkTempInstBM.getId(), checkMap.get("C_INS_TYPE_" + checkTempInstBM.getId()));
                    String isRepeatUpload = CommonTools.Obj2String(checkMap.get("C_IS_REPEAT_UPLOAD_" + checkTempInstBM.getId()));
                    isRepeatUpload = "".equals(isRepeatUpload) ? "否" : isRepeatUpload;
                    checkInstMap.put("C_IS_REPEAT_UPLOAD_" + checkTempInstBM.getId(), isRepeatUpload);
                    String checkTempId = checkMap.get("C_CHECK_TEMP_ID_" + checkTempInstBM.getId());
                    checkInstMap.put("C_CHECK_TEMP_ID_" + checkTempInstBM.getId(), checkTempId);
                    checkInstMap.put("C_CHECK_STATE_" + checkTempInstBM.getId(), "未完成");
                    checkInstMap.put("T_DIVING_TASK_" + schemaId + "_ID", newTaskId);
                    checkInstMap.put("T_HANGCI_" + schemaId + "_ID", hangciId);
                    String newCheckInstId = orientSqlEngine.getBmService().insertModelData(checkTempInstBM, checkInstMap);
                    oldCheckInstIdsList.add(oldCheckInstId);
                    newCheckInstIdsList.add(newCheckInstId);
                    checkInstTypeMap.put(oldCheckInstId, insType);
                }
                commonCopyCheckTable(oldCheckInstIdsList, newCheckInstIdsList, headerInstBM, rowInstBM, cellInstBM, checkTempInstBM, false, checkInstTypeMap);
            }
            retVal.setSuccess(true);
            retVal.setMsg("复制成功！");
        }
        return retVal;
    }

    public CommonResponse publishFlowPost(String hangduanId) {
        CommonResponse retVal = new CommonResponse();
        retVal.setSuccess(false);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanInformLogBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN_INFORM_LOG,
                schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN,
                schemaId, EnumInter.BusinessModelEnum.Table);
        divingTaskBM.setReserve_filter(" AND T_HANGDUAN_" + schemaId + "_ID='" + hangduanId + "'");
        List<Map> divingTaskMapList = this.orientSqlEngine.getBmService().createModelQuery(divingTaskBM).orderAsc("C_TASK_NAME_" + divingTaskBM.getId()).list();
        if (divingTaskMapList != null && divingTaskMapList.size() > 0) {
            List<Map> publishFlowPostList = UtilFactory.newArrayList();
            List<Column> columnList = initColumn(postBM, false);
            for (int i = 0; i < divingTaskMapList.size(); i++) {
                String taskName = CommonTools.Obj2String(divingTaskMapList.get(i).get("C_TASK_NAME_" + divingTaskBM.getId()));
                String taskId = CommonTools.Obj2String(divingTaskMapList.get(i).get("ID"));
                if (columnList.size() > 0) {
                    int count = 0;
                    Map publishFlowPostMap = UtilFactory.newHashMap();
                    publishFlowPostMap.put("0", taskName);
                    for (Column column : columnList) {
                        if (count < columnList.size()) {
                            String postId = column.getDataIndex();
                            String postName = column.getHeader();
                            String type = column.getMeType();
                            if (type.equalsIgnoreCase("流动")) {
                                Map attendPersonMap = getChoosedAttendPerson(taskId, postId);
//                                publishFlowPostMap.put("postName", postName);
                                if (attendPersonMap != null && attendPersonMap.size() > 0) {
                                    publishFlowPostMap.put(postId, attendPersonMap.get("attendPersonNames"));
                                } else {
                                    publishFlowPostMap.put(postId, "无");
                                }
                                count++;
                            }
                        }
                    }
                    publishFlowPostList.add(publishFlowPostMap);
                }
            }
            if (publishFlowPostList != null && publishFlowPostList.size() > 0) {
                Map hangduanMap = orientSqlEngine.getBmService().createModelQuery(hangduanBM).findById(hangduanId);
                //list集合转为jsonArray
                net.sf.json.JSONArray flowPostJsonArray = net.sf.json.JSONArray.fromObject(publishFlowPostList);
                hangduanInformLogBM.setReserve_filter("AND C_HD_ID_" + hangduanInformLogBM.getId() + "='" + hangduanId + "'");
                List<Map> hangduanInformLogList = orientSqlEngine.getBmService().createModelQuery(hangduanInformLogBM).list();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String publishTime = simpleDateFormat.format(new Date());
                List<Map> flowPostHeaderList = UtilFactory.newArrayList();
                if (columnList != null && columnList.size() > 0) {
                    for (Column column : columnList) {
                        Map flowPostHeaderMap = UtilFactory.newHashMap();
                        String postId = "";
                        if ("潜次名称".equals(column.getHeader())) {
                            postId = "0";
                        } else {
                            postId = column.getDataIndex();
                        }
                        flowPostHeaderMap.put("column_name", postId);
                        flowPostHeaderMap.put("column_comment", column.getHeader());
                        flowPostHeaderList.add(flowPostHeaderMap);
                    }
                }
                if (hangduanInformLogList != null && hangduanInformLogList.size() > 0) {
                    Map hangduanInformMap = hangduanInformLogList.get(0);
                    String hdInformId = hangduanInformMap.get("ID").toString();
                    hangduanInformMap.put("C_CONTENT_" + hangduanInformLogBM.getId(), flowPostJsonArray.toString());
                    hangduanInformMap.put("C_PUBLISH_TIME_" + hangduanInformLogBM.getId(), publishTime);
                    hangduanInformMap.put("C_NAME_" + hangduanInformLogBM.getId(), hangduanMap.get("C_HANGDUAN_NAME_" + hangduanBM.getId()));
                    net.sf.json.JSONArray flowPostHeaderArray = net.sf.json.JSONArray.fromObject(flowPostHeaderList);
                    hangduanInformMap.put("C_HEADER_" + hangduanInformLogBM.getId(), flowPostHeaderArray.toString());
                    orientSqlEngine.getBmService().updateModelData(hangduanInformLogBM, hangduanInformMap, hdInformId);
                } else {
                    Map hangduanInformMap = UtilFactory.newHashMap();
                    hangduanInformMap.put("C_CONTENT_" + hangduanInformLogBM.getId(), flowPostJsonArray.toString());
                    hangduanInformMap.put("C_HD_ID_" + hangduanInformLogBM.getId(), hangduanId);
                    hangduanInformMap.put("C_NAME_" + hangduanInformLogBM.getId(), hangduanMap.get("C_HANGDUAN_NAME_" + hangduanBM.getId()));
                    hangduanInformMap.put("C_PUBLISH_TIME_" + hangduanInformLogBM.getId(), publishTime);
                    net.sf.json.JSONArray flowPostHeaderArray = net.sf.json.JSONArray.fromObject(flowPostHeaderList);
                    hangduanInformMap.put("C_HEADER_" + hangduanInformLogBM.getId(), flowPostHeaderArray.toString());
                    orientSqlEngine.getBmService().insertModelData(hangduanInformLogBM, hangduanInformMap);
                }
            }
            retVal.setSuccess(true);
            retVal.setMsg("发布成功！");
        }
        return retVal;
    }

    public ExtGridData<Map> getDivingTaskData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort) {
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
            customerFilters.forEach(cs -> businessModel.appendCustomerFilter(cs));
        }
        long count = orientSqlEngine.getBmService().createModelQuery(businessModel).count();
        IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
        if (null != page && null != pagesize) {
            int start = (page - 1) * pagesize;
            int end = page * pagesize > count ? (int) count : (page * pagesize);
            businessModelQuery.page(start, end);
        }
        List<Map> dataList = businessModelQuery.orderAsc("C_TASK_NAME_" + orientModelId).list();
        if (dataChange) {
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
            modelDataBusiness.customDataChange(businessModel, dataList);
        }
        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    public String insertCheckInstList(String fileName, String divingTaskId) {
        String tableName = PropertyConstant.CHECK_TEMP_INST;
        String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            fileName = fileName.substring(0, index);
        }
        String modelId = bm.getId();
        bm.setReserve_filter("and C_NAME_" + modelId + "='" + fileName + "'" +
                " and T_DIVING_TASK_" + schemaId + "_ID='" + divingTaskId + "'");
        List<Map<String, String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if (list.size() == 0) {
            Map<String, String> data = UtilFactory.newHashMap();
            data.put("C_NAME_" + bm.getId(), fileName);
            if (StringUtil.isNotEmpty(divingTaskId)) {
                data.put("T_DIVING_TASK_" + schemaId + "_ID", divingTaskId);
            }
            return orientSqlEngine.getBmService().insertModelData(bm, data);
        } else {
            return null;
        }
    }

    public Map<String, Object> importCheckInstHeadCellList(TableEntity excelEntity, List<String> headers, String checkTableInstId, List<String> fileImgUrlList) throws Exception {
        Map<String, Object> retValue = null;
        insertTableHeaderAndTabletTail(excelEntity, checkTableInstId, fileImgUrlList);
        retValue = insertTableDetail(excelEntity, headers, checkTableInstId, fileImgUrlList);
        return retValue;
    }

    private void insertTableHeaderAndTabletTail(TableEntity entity, String checkTableInstId, List<String> fileImgUrlList) throws Exception {
        List<TableEntity> tableEntityList = entity.getSubTableEntityList();
        if (tableEntityList != null) {
            IBusinessModel cellBusinessModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            IBusinessModel cellValueBM = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA, schemaId, EnumInter.BusinessModelEnum.Table);
            Map<String, String> dataMap = new HashMap();

            for (TableEntity tableEntity : tableEntityList) {
                if (tableEntity != null) {
                    List<DataEntity> dataEntities = tableEntity.getDataEntityList();
                    for (DataEntity dataEntity : dataEntities) {
                        dataMap.clear();
                        dataMap.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", checkTableInstId);
                        List<FieldEntity> fieldEntityList = dataEntity.getFieldEntityList();
                        String cellCategory = fieldEntityList.get(1).getValue();
                        if ("表头".equals(cellCategory)) {
                            dataMap.put("C_IS_HEADER_" + cellBusinessModel.getId(), "true");
                        } else if ("表尾".equals(cellCategory)) {
                            dataMap.put("C_IS_HEADER_" + cellBusinessModel.getId(), "false");
                        } else {
                            continue;
                        }
                        String cellFieldname = fieldEntityList.get(2).getValue();
                        dataMap.put("C_CONTENT_" + cellBusinessModel.getId(), cellFieldname);
                        String cellData = fieldEntityList.get(3).getValue();
                        String cellType = "";
                        if (StringUtil.isNotEmpty(cellData)) {
                            if (cellData.indexOf("#") == 0 && cellData.contains("#")) {
                                //签署
                                cellType = "#8";
                            } else {
                                //填写
                                cellType = "#2";
                            }
                            dataMap.put("C_CELL_TYPE_" + cellBusinessModel.getId(), cellType);
                            String cellInstId = orientSqlEngine.getBmService().insertModelData(cellBusinessModel, dataMap);
                            if (!cellData.contains("#")) {
                                Map cellValueMap = UtilFactory.newHashMap();
                                cellValueMap.put("C_CONTENT_" + cellValueBM.getId(), cellData);
                                cellValueMap.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", checkTableInstId);
                                cellValueMap.put("T_CHECK_CELL_INST_" + schemaId + "_ID", cellInstId);
                                orientSqlEngine.getBmService().insertModelData(cellValueBM, cellValueMap);
                            } else if (cellData.indexOf("#") == 0 && cellData.contains("#")) {
                                //签署
                                if (fieldEntityList != null && fileImgUrlList.size() > 0) {
                                    for (String path : fileImgUrlList) {
                                        String urlDecodePath = "";
                                        if (path.contains("\\") || path.contains("/")) {
                                            urlDecodePath = URLDecoder.decode(path, "UTF-8");
                                            System.out.println(urlDecodePath);
                                            String fileName = FileOperator.getFileName(urlDecodePath);
                                            String cellDataImageName = cellData.substring(1, cellData.length());
                                            String fileSufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                                            if ("jpg".equals(fileSufix)) {
                                                if (cellDataImageName.equals(fileName)) {
                                                    File rawFile = new File(urlDecodePath);
                                                    FileInputStream fileInputStream = new FileInputStream(rawFile);
                                                    MultipartFile multipartFile = new MockMultipartFile(rawFile.getName(), rawFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
                                                    saveCheckTableResultPicFile(multipartFile, cellBusinessModel.getId(), cellInstId, "common", "", "公开");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public CwmFile saveCheckTableResultPicFile(MultipartFile file, String modelId, String dataId, String fileCatalog, String desc, String secrecy) {
        CwmFile cwmFile = new CwmFile();
        String fileName = file.getOriginalFilename();
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        //保存文件
        String SCIENTIS_PREVIEW = "checkTableResult_pic";
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.UPLOAD_ROOT);
        folder += "/" + SCIENTIS_PREVIEW + "/";
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String realFileStoragePath = fileServerConfig.getFtpHome() + folder + fileName;
        try {
            FileOperator.createFile(realFileStoragePath, file.getBytes());
            cwmFile.setDataid(dataId);
            cwmFile.setTableid(modelId);
            cwmFile.setFilename(fileName);
            cwmFile.setFinalname(fileName);
            cwmFile.setFilelocation(folder + finalFileName);
            cwmFile.setFilesize(file.getSize());
            cwmFile.setUploadStatus(CwmFile.UPLOAD_STATUS_SUCCESS);
            cwmFile.setUploadUserId(UserContextUtil.getUserId());
            cwmFile.setUploadDate(new Date());
            cwmFile.setFileCatalog(fileCatalog);
            cwmFile.setFiledescription(CommonTools.null2String(desc));
            cwmFile.setFilesecrecy(secrecy);
            fileService.createFile(cwmFile);
            String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + "checkTableResult_pic";
            if (!new File(imageFolderPath).exists()) {
                new File(imageFolderPath).mkdirs();
            }
            String imageFile = imageFolderPath + File.separator + fileName;
            file.transferTo(new File(imageFile));
            //保存文件到preview
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cwmFile;
    }

    private Map<String, Object> insertTableDetail(TableEntity excelEntity, List<String> headers, String checkTableInstId, List<String> fileImgUrlList) throws Exception {
        Map<String, Object> retVal = UtilFactory.newHashMap();
        String tempTableName = PropertyConstant.CHECK_TEMP_INST;
        String headerTableName = PropertyConstant.CHECK_HEADER_INST;
        String rowTableName = PropertyConstant.CHECK_ROW_INST;
        String cellTableName = PropertyConstant.CHECK_CELL_INST;
        String cellValueName = PropertyConstant.CELL_INST_DATA;

        IBusinessModel headerBM = businessModelService.getBusinessModelBySName(headerTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(rowTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellBM = businessModelService.getBusinessModelBySName(cellTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellValueBM = businessModelService.getBusinessModelBySName(cellValueName, schemaId, EnumInter.BusinessModelEnum.Table);

        //数据库中插入表头
        List<String> headerIds = UtilFactory.newArrayList();
        for (String header : headers) {
            if (!PropertyConstant.EXCEL_STRUCT_HEADER.equals(header)) {
                Map<String, String> data = UtilFactory.newHashMap();
                data.put("C_NAME_" + headerBM.getId(), header);
                data.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", checkTableInstId);
                String headerId = orientSqlEngine.getBmService().insertModelData(headerBM, data);
                headerIds.add(headerId);
            }
        }

        //数据库中插入行
        List<DataEntity> dataEntities = excelEntity.getDataEntityList();

        for (DataEntity dataEntity : dataEntities) {
            List<FieldEntity> fieldEntities = dataEntity.getFieldEntityList();
            String rowValue = dataEntity.getPkVal();
            if (rowValue == null) {
                continue;
            }
            int fieldEntitiesSize = fieldEntities.size();
            Map<String, String> rowMap = UtilFactory.newHashMap();
            rowMap.put("C_ROW_NUMBER_" + rowBM.getId(), rowValue);
            rowMap.put(tempTableName + "_" + schemaId + "_ID", checkTableInstId);
            String rowId = orientSqlEngine.getBmService().insertModelData(rowBM, rowMap);
            //数据库中插入单元格数据
            int headerSize = headers.size();
            for (int i = 0; i < fieldEntitiesSize; i++) {
                if (i == fieldEntitiesSize - 1 && fieldEntitiesSize - 1 < headerSize) {
                    fieldEntitiesSize = fieldEntitiesSize + (headerSize - (fieldEntitiesSize - 1));
                }
                String value = "";
                String name = "";
                if (fieldEntities.size() < fieldEntitiesSize && i == fieldEntitiesSize - 1) {
                    value = "---";
                } else {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    value = fieldEntity.getValue();
                    name = fieldEntity.getName();
                }

                String headerId = headerIds.get(i - 1);

                Map<String, String> cellMap = UtilFactory.newHashMap();
                String cellType = "";
                if (StringUtil.isNotEmpty(value)) {
                    if (name.equals(headers.get(i - 1)) && name.contains("#")) {
                        if (value.indexOf("#") == 0 && value.contains("#")) {
                            //签署
                            cellType = "#8";
                            cellMap.put("C_CONTENT_" + cellBM.getId(), "签署");
                            //主要是方便获取单元格，不需要多次查询数据库
                            cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                            cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                            cellMap.put("C_CELL_TYPE_" + cellBM.getId(), cellType);
                            //主要是方便获取单元格，不需要多次查询数据库
                            cellMap.put(tempTableName + "_" + schemaId + "_ID", checkTableInstId);
                            String cellInstId = orientSqlEngine.getBmService().insertModelData(cellBM, cellMap);
                            if (fileImgUrlList != null && fileImgUrlList.size() > 0) {
                                for (String path : fileImgUrlList) {
                                    String urlDecodePath = "";
                                    if (path.contains("\\") || path.contains("/")) {
                                        urlDecodePath = URLDecoder.decode(path, "UTF-8");
                                        System.out.println(urlDecodePath);
                                        String fileName = FileOperator.getFileName(urlDecodePath);
                                        String cellDataImageName = value.substring(1, value.length());
                                        String fileSufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                                        if ("jpg".equals(fileSufix)) {
                                            if (cellDataImageName.equals(fileName)) {
                                                File rawFile = new File(urlDecodePath);
                                                FileInputStream fileInputStream = new FileInputStream(rawFile);
                                                MultipartFile multipartFile = new MockMultipartFile(rawFile.getName(), rawFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
                                                saveCheckTableResultPicFile(multipartFile, cellBM.getId(), cellInstId, "common", "", "公开");
                                            }
                                        }
                                    }
                                }
                            }
                        }else{
                            //填写
                            cellType = "#2";
                            cellMap.put("C_CONTENT_" + cellBM.getId(), "填写");
                            //主要是方便获取单元格，不需要多次查询数据库
                            cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                            cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                            cellMap.put("C_CELL_TYPE_" + cellBM.getId(), cellType);
                            //主要是方便获取单元格，不需要多次查询数据库
                            cellMap.put(tempTableName + "_" + schemaId + "_ID", checkTableInstId);
                            String cellInstId = orientSqlEngine.getBmService().insertModelData(cellBM, cellMap);
                            Map cellValueMap = UtilFactory.newHashMap();
                            cellValueMap.put("C_CONTENT_" + cellValueBM.getId(), value);
                            cellValueMap.put("T_CHECK_TEMP_INST_" + schemaId + "_ID", checkTableInstId);
                            cellValueMap.put("T_CHECK_CELL_INST_" + schemaId + "_ID", cellInstId);
                            orientSqlEngine.getBmService().insertModelData(cellValueBM, cellValueMap);
                        }
                    } else {
                        //填写
                        cellType = "#0";
                        cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                        cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                        cellMap.put("C_CELL_TYPE_" + cellBM.getId(), cellType);
                        //主要是方便获取单元格，不需要多次查询数据库
                        cellMap.put(tempTableName + "_" + schemaId + "_ID", checkTableInstId);
                        String cellInstId = orientSqlEngine.getBmService().insertModelData(cellBM, cellMap);
                    }
                } else {
                    value = "---";
                    cellMap.put("C_CONTENT_" + cellBM.getId(), value);
                    cellMap.put(headerTableName + "_" + schemaId + "_ID", headerId);
                    cellMap.put(rowTableName + "_" + schemaId + "_ID", rowId);
                    cellMap.put("C_CELL_TYPE_" + cellBM.getId(), "#0");
                    //主要是方便获取单元格，不需要多次查询数据库
                    cellMap.put(tempTableName + "_" + schemaId + "_ID", checkTableInstId);
                    orientSqlEngine.getBmService().insertModelData(cellBM, cellMap);
                    continue;
                }
            }
        }
        retVal.put("success", true);
        retVal.put("msg", "导入成功！");
        return retVal;
    }

    public void checkTablebindFlowData(String flowId, String flowName, String checkTableInstIds, String taskId) {
        IBusinessModel checkTableInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        checkTableInstBM.setReserve_filter("AND ID IN (" + checkTableInstIds + ")" +
                " AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
        List<Map<String, Object>> checkTableInstList = orientSqlEngine.getBmService().createModelQuery(checkTableInstBM).list();
        if (checkTableInstList != null && checkTableInstList.size() > 0) {
            for (Map checkInstMap : checkTableInstList) {
                String checkTableInstId = checkInstMap.get("ID").toString();
                checkInstMap.put("C_NODE_ID_" + checkTableInstBM.getId(), flowId);
                checkInstMap.put("C_NODE_TEXT_" + checkTableInstBM.getId(), flowName);
                orientSqlEngine.getBmService().updateModelData(checkTableInstBM, checkInstMap, checkTableInstId);
            }
        }
    }
}
