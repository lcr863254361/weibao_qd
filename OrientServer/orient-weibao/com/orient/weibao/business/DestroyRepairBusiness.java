package com.orient.weibao.business;

import com.google.common.base.Joiner;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.event.ProjectTreeNodeCreatedEvent;
import com.orient.collab.event.ProjectTreeNodeCreatedEventParam;
import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.collab.model.GanttPlan;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.*;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.ImportDestroyBean;
import com.orient.weibao.bean.ImportDeviceBean;
import com.orient.weibao.bean.ProductStructureTreeNode;
import com.orient.weibao.bean.ganttBean.DestroyFlowBean;
import com.orient.weibao.bean.ganttBean.DestroyTypeBean;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.enums.FlowNodeType;
import com.orient.weibao.enums.ProductStructureNodeType;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessModelEnum;
import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.*;
import static com.orient.collab.config.CollabConstants.PLAN_TYPE_MILESTONE;
import static com.orient.collab.config.CollabConstants.PLAN_TYPE_NORMAL;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;
import static com.orient.utils.JsonUtil.getJavaCollection;

@Service
public class DestroyRepairBusiness extends BaseBusiness {

    @Resource(name = "UserService")
    UserService userService;
    @Autowired
    MetaDAOFactory metaDAOFactory;
    @Autowired
    TaskPrepareMgrBusiness taskPrepareMgrBusiness;
    @Autowired
    private FileService fileService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public AjaxResponseData<List<ProductStructureTreeNode>> getDestroyNextLayerNodes(String id, String type, String level) {    //传过来-1和root
        List<ProductStructureTreeNode> retVal = new ArrayList<>();
        IBusinessModel destroyTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel destroyFlowBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_FLOW, schemaId, EnumInter.BusinessModelEnum.Table);
        switch (type) {
            //如果type类型是根节点，就遍历根节点下的主流程，在前台点击根节点会触发以下事件
            case FlowNodeType.TYPE_ROOT:       //orientSqlEngine     sql引擎    根据模型查找到表
                List<Map> mainFlowTempMapList = orientSqlEngine.getBmService().createModelQuery(destroyTypeBM).orderAsc("TO_NUMBER(ID)").list();
                if (mainFlowTempMapList.size() > 0) {
                    mainFlowTempMapList.forEach(destroyTypeMap -> {
                        ProductStructureTreeNode destroyTypeNode = new ProductStructureTreeNode();
                        //获得T_FLOW_TM_460表中的C_NAME_2713字段所对应的某一行的value值，放到mainFlowNode
                        destroyTypeNode.setText(CommonTools.Obj2String(destroyTypeMap.get("C_NAME_" + destroyTypeBM.getId())));    //getId得到2713
                        String destroyTypeId = CommonTools.Obj2String(destroyTypeMap.get("ID"));     //mainFlowId是获得T_FLOW_TM_460中的ID
                        destroyTypeNode.setId("subFlow_" + destroyTypeId);
                        destroyTypeNode.setDataId(destroyTypeId);
                        destroyTypeNode.setExpanded(true);
                        destroyTypeNode.setType(FlowNodeType.TYPE_DESTROY_TYPE);
                        destroyTypeNode.setIcon("app/images/function/数据建模.png");
                        destroyTypeNode.setIconCls("icon-function");
                        destroyTypeNode.setModelName(PropertyConstant.DESTROY_TYPE);
                        destroyTypeNode.setModelId(destroyTypeBM.getId());
                        int levell = Integer.parseInt(level);
                        destroyTypeNode.setLevel(++levell);
                        destroyTypeNode.setQtip(CommonTools.Obj2String(destroyTypeMap.get("C_NAME_" + destroyTypeBM.getId())));
                        //给子流程加过滤条件
//                        destroyFlowBM.setReserve_filter("AND T_DESTROY_TYPE_" + schemaId + "_ID = '" + destroyTypeId + "'");
//                        //判断主流程下的两个子流程是否是叶子节点，即判断主流程下再有没有孩子
//                        if (orientSqlEngine.getBmService().createModelQuery(destroyFlowBM).list().size() == 0) {
                        destroyTypeNode.setLeaf(true);
                        destroyTypeNode.setExpanded(false);
//                        }
                        retVal.add(destroyTypeNode);
                    });
                }
                break;
//            case FlowNodeType.TYPE_DESTROY_TYPE:     //在前台点击专业按钮会触发以下事件,显示岗位
//                destroyFlowBM.setReserve_filter("AND T_DESTROY_TYPE_" + schemaId + "_ID = '" + id + "'");
//                System.out.println("----------------------------------------");
//                List<Map> destroyFlowMapList = orientSqlEngine.getBmService().createModelQuery(destroyFlowBM).orderAsc("ID").list();
//                if (destroyFlowMapList.size() > 0) {
//                    destroyFlowMapList.forEach(destroyFlowMap -> {
//                        ProductStructureTreeNode destroyFlowNode = new ProductStructureTreeNode();
//                        destroyFlowNode.setText(CommonTools.Obj2String(destroyFlowMap.get("C_NAME_" + destroyFlowBM.getId())));
//                        String destroyFlowId = CommonTools.Obj2String(destroyFlowMap.get("ID"));
//                        destroyFlowNode.setId("subFlow_" + destroyFlowId);
//                        destroyFlowNode.setDataId(destroyFlowId);
//                        destroyFlowNode.setExpanded(false);
//                        destroyFlowNode.setType(FlowNodeType.TYPE_DESTROY_FLOW);
//                        destroyFlowNode.setIcon("app/images/function/数据建模.png");
//                        destroyFlowNode.setIconCls("icon-function");
//                        int levell = Integer.parseInt(level);
//                        destroyFlowNode.setLevel(++levell);
//                        destroyFlowNode.setQtip(CommonTools.Obj2String(destroyFlowMap.get("C_NAME_" + destroyFlowBM.getId())));
//                        destroyFlowNode.setLeaf(true);
//                        destroyFlowNode.setModelName(PropertyConstant.DESTROY_FLOW);
//                        destroyFlowNode.setModelId(destroyFlowBM.getId());
//                        retVal.add(destroyFlowNode);
//                    });
//                }
//                break;
            default:
                break;
        }
        return new AjaxResponseData<>(retVal);
    }

    public List<DestroyTypeBean> getDestroyTypeList() {
        IBusinessModel destroyTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        List<DestroyTypeBean> destroyTypeBeanList = UtilFactory.newArrayList();
        List<Map<String, Object>> destroyTypeList = orientSqlEngine.getBmService().createModelQuery(destroyTypeBM).list();
        if (destroyTypeList.size() > 0) {
            for (Map destroyTypeMap : destroyTypeList) {
                DestroyTypeBean destroyTypeBean = new DestroyTypeBean();
                String destroyTypeId = CommonTools.Obj2String(destroyTypeMap.get("ID"));
                String destroyName = CommonTools.Obj2String(destroyTypeMap.get("C_NAME_" + destroyTypeBM.getId()));
                destroyTypeBean.setId(destroyTypeId);
                destroyTypeBean.setName(destroyName);
                destroyTypeBean.setModelName(PropertyConstant.DESTROY_TYPE);
                destroyTypeBeanList.add(destroyTypeBean);
            }
        }
        return destroyTypeBeanList;
    }

    public List<DestroyFlowBean> getGanttDestroyFlow(String parModelName, String parDataId) throws Exception {
        List<DestroyFlowBean> taskBeanList = UtilFactory.newArrayList();
        IBusinessModel planBm = this.businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_FLOW, schemaId, Table);
        IBusinessModel checkTempInstBM = this.businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, Table);
        IBusinessModel parBm = null;
        if (PropertyConstant.DESTROY_FLOW.equals(parModelName)) {
            parBm = planBm;
        } else {
            parBm = this.businessModelService.getBusinessModelBySName(parModelName, schemaId, Table);
        }
        planBm.setReserve_filter(" AND " + parBm.getS_table_name() + "_ID = '" + parDataId + "'");

        List<Map<String, Object>> flowAsMaps = this.orientSqlEngine.getBmService().createModelQuery(planBm).orderAsc("C_FLOW_START_TIME_"+planBm.getId()).list();
//        List<TaskBean> tasks = BusinessDataConverter.convertMapListToBeanList(planBm, planAsMaps,TaskBean.class, true);
        if (flowAsMaps.size() > 0) {
            for (Map flowMap : flowAsMaps) {
                String flowId = (String) flowMap.get("ID");
                DestroyFlowBean destroyFlowBean = new DestroyFlowBean();
                destroyFlowBean.setId(CommonTools.Obj2String(flowMap.get("ID")));
//                  taskBean.setName(CommonTools.Obj2String(taskMap.get("C_TASK_NAME_"+planBm.getId())));
                destroyFlowBean.setName(CommonTools.Obj2String(flowMap.get("C_NAME_" + planBm.getId())));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String taskStartDate = CommonTools.Obj2String(flowMap.get("C_FLOW_START_TIME_" + planBm.getId()));
                String taskEndDate = CommonTools.Obj2String(flowMap.get("C_FLOW_END_TIME_" + planBm.getId()));
                if (!"".equals(taskStartDate)) {
                    destroyFlowBean.setStartDate(dateFormat.format(dateFormat.parse(taskStartDate)));
                }
                if (!"".equals(taskEndDate)) {
                    destroyFlowBean.setEndDate(dateFormat.format(dateFormat.parse(taskEndDate)));
                }
                destroyFlowBean.setParentId(null);
                String dutorId = CommonTools.Obj2String(flowMap.get("C_DUTOR_" + planBm.getId()));
                User dutor = userService.findById(dutorId);
                if (dutor != null) {
                    destroyFlowBean.setTaskDutor(dutor.getAllName());
                }
                destroyFlowBean.setLeaf(true);
                checkTempInstBM.clearAllFilter();
                checkTempInstBM.setReserve_filter("AND T_DESTROY_FLOW_" + schemaId + "_ID='" + flowId + "'");
                List<Map<String, Object>> checkInstList = orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).list();
                int uploadCount = 0;
                float uploadPercent = 0;
                if (checkInstList.size() > 0) {
                    for (Map checkMap : checkInstList) {
                        String checkTableState = CommonTools.Obj2String(checkMap.get("C_CHECK_STATE_" + checkTempInstBM.getId()));
                        if (checkTableState.equals("已上传") || checkTableState.equals("异常")) {
                            ++uploadCount;
                        }
                    }
                    uploadPercent = (float) uploadCount / checkInstList.size();
                    uploadPercent = uploadPercent * 100;
                }
                destroyFlowBean.setPercentDone(String.valueOf(uploadPercent));
                taskBeanList.add(destroyFlowBean);
            }
        }

        return taskBeanList;
    }

    public List<DestroyFlowBean> saveOrUpdateDestroyFlow(String rootModelName, String rootDataId, List<DestroyFlowBean> destroyFlowBeanList, boolean isDestroyTask) {
        Collection<User> users = roleEngine.getRoleModel(false).getUsers().values();
        IBusinessModel flowBm = this.businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_FLOW, schemaId, Table);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<DestroyFlowBean> retV = UtilFactory.newArrayList();
        for (DestroyFlowBean destroyFlowBean : destroyFlowBeanList) {
            Map flowMap = new HashMap();
            flowMap.put("C_NAME_" + flowBm.getId(), destroyFlowBean.getName());
            flowMap.put("C_FLOW_START_TIME_" + flowBm.getId(), destroyFlowBean.getStartDate());
            flowMap.put("C_FLOW_END_TIME_" + flowBm.getId(), destroyFlowBean.getEndDate());
            if (isDestroyTask) {
                flowMap.put("T_DESTROY_TASK_" + schemaId + "_ID", rootDataId);
            } else {
                flowMap.put("T_DESTROY_TYPE_" + schemaId + "_ID", rootDataId);
            }
            if (CommonTools.isNullString(destroyFlowBean.getId())) {
                String flowId = orientSqlEngine.getBmService().insertModelData(flowBm, flowMap);
                if (!flowId.isEmpty()) {
                    destroyFlowBean.setId(flowId);
                }
            } else {
                this.orientSqlEngine.getBmService().updateModelData(flowBm, flowMap, destroyFlowBean.getId());
            }
            destroyFlowBean.setIconCls("icon-" + destroyFlowBean.getType());
            retV.add(destroyFlowBean);
        }
        return retV;
    }

    public CommonResponseData deleteDestroyFlow(List<DestroyFlowBean> destroyFlowBeanList) {

        CommonResponseData retV = new CommonResponseData(false, "");

        IBusinessModel flowBM = this.businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_FLOW, schemaId, Table);

        StringBuilder flowBuilder = new StringBuilder();
        for (DestroyFlowBean destroyFlowBean : destroyFlowBeanList) {
            flowBuilder.append(destroyFlowBean.getId());
            flowBuilder.append(",");
        }
        String flowIds = flowBuilder.toString();
        flowIds = flowIds.substring(0, flowIds.length() - 1);
        if (flowIds != null && !"".equals(flowIds)) {
            orientSqlEngine.getBmService().deleteCascade(flowBM, flowIds);
        }
        retV.setSuccess(true);
        return retV;
    }

    public AjaxResponseData saveDestroyTaskData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel destroyTaskModel = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel destroyFlowModel = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_FLOW, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel formTempInstModel = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel skillDocumentModel = businessModelService.getBusinessModelBySName(PropertyConstant.SKILL_DOCUMENT, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel refPostNodeModel=businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE,schemaId,EnumInter.BusinessModelEnum.Table);
        IBusinessModel headerInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.BusinessModelEnum);

        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            dataMap.put("C_STATE_" + destroyTaskModel.getId(), "未开始");
            dataMap.put("C_TASK_DUTOR_" + destroyTaskModel.getId(), dataMap.get("destroyName_Id"));
            String destroyTaskId = orientSqlEngine.getBmService().insertModelData(destroyTaskModel, dataMap);
            String destroyTempId = (String) dataMap.get("C_DESTROY_TEMP_" + modelId);
            destroyFlowModel.setReserve_filter("AND T_DESTROY_TYPE_" + schemaId + "_ID='" + destroyTempId + "'");
            List<Map<String, Object>> destroyFlowList = orientSqlEngine.getBmService().createModelQuery(destroyFlowModel).list();

            List<Map> destroyIdList = UtilFactory.newArrayList();
            if (destroyFlowList.size() > 0) {
                StringBuilder destroyFlowBuilder = new StringBuilder();
                for (Map destroyMap : destroyFlowList) {
                    Map destroyIdMap = UtilFactory.newHashMap();
                    String destroyFlowId = CommonTools.Obj2String(destroyMap.get("ID"));
                    destroyFlowBuilder.append(destroyFlowId);
                    destroyFlowBuilder.append(",");
                    destroyMap.remove("ID");
                    destroyMap.remove("T_DESTROY_TYPE_" + schemaId + "_ID");
                    destroyMap.put("T_DESTROY_TASK_" + schemaId + "_ID", destroyTaskId);
                    String destroyFlowInstId = orientSqlEngine.getBmService().insertModelData(destroyFlowModel, destroyMap);
                    destroyIdMap.put(destroyFlowId, destroyFlowInstId);
                    destroyIdList.add(destroyIdMap);
                }
                String destroyFlowIds = destroyFlowBuilder.toString();
                destroyFlowIds = destroyFlowIds.substring(0, destroyFlowIds.length() - 1);
                formTempInstModel.setReserve_filter("AND T_DESTROY_FLOW_" + schemaId + "_ID IN (" + destroyFlowIds + ")");
                skillDocumentModel.setReserve_filter("AND T_DESTROY_FLOW_" + schemaId + "_ID IN (" + destroyFlowIds + ")");
                refPostNodeModel.setReserve_filter("AND T_DESTROY_FLOW_" + schemaId + "_ID IN (" + destroyFlowIds + ")");

                List<Map<String, Object>> formTempInstList = orientSqlEngine.getBmService().createModelQuery(formTempInstModel).list();
                if (formTempInstList.size() > 0) {
                    List<String> oldCheckInstIdsList = UtilFactory.newArrayList();
                    List<String> newCheckInstIdsList = UtilFactory.newArrayList();
                    Map oldCheckInstTypeMap=UtilFactory.newHashMap();
                    for (Map formMap : formTempInstList) {
                        //原来的实例ID
                        String oldCheckInstId = (String) formMap.get("ID");
                        String oldCheckInstType=(String) formMap.get("C_INS_TYPE_"+formTempInstModel.getId());
                        String refFlowTempId = CommonTools.Obj2String(formMap.get("T_DESTROY_FLOW_" + schemaId + "_ID"));
                        String newFlowInstId = "";
                        for (Map destroyIdMap : destroyIdList) {
                            newFlowInstId = CommonTools.Obj2String(destroyIdMap.get(refFlowTempId));
                            if(!"".equals(newFlowInstId)){
                                break;
                            }
                        }
                        formMap.remove("T_DESTROY_FLOW_" + schemaId + "_ID");
                        formMap.remove("ID");
                        formMap.put("T_DESTROY_FLOW_" + schemaId + "_ID", newFlowInstId);
                        //新生成的实例ID
                        String newCheckInstId = orientSqlEngine.getBmService().insertModelData(formTempInstModel, formMap);
//                        StringBuilder headerSql = new StringBuilder();
//                        headerSql.append("select * from T_CHECK_HEADER_INST_" + schemaId).append(" where 1=1").append(" and T_CHECK_TEMP_INST_" + schemaId + "_ID =?").append("order by ID ASC");
//                        List<Map<String, Object>> headerList = metaDAOFactory.getJdbcTemplate().queryForList(headerSql.toString(), checkInstId);
//                        taskPrepareMgrBusiness.copyCheckInstList(headerList, checkTempInstId, checkInstId);
                        oldCheckInstIdsList.add(oldCheckInstId);
                        newCheckInstIdsList.add(newCheckInstId);
                        oldCheckInstTypeMap.put(oldCheckInstId,oldCheckInstType);
                    }
                    taskPrepareMgrBusiness.commonCopyCheckTable(oldCheckInstIdsList,newCheckInstIdsList,headerInstBM,rowInstBM,cellInstBM,formTempInstModel,false,oldCheckInstTypeMap);
                }
                //复制岗位数据
                List<Map> refPostList = orientSqlEngine.getBmService().createModelQuery(refPostNodeModel).list();
                if (refPostList.size()>0){
                    for (Map<String, String> refMap : refPostList) {
                        String refFlowTempId = CommonTools.Obj2String(refMap.get("T_DESTROY_FLOW_" + schemaId + "_ID"));
                        String newFlowInstId = "";
                        for (Map destroyIdMap : destroyIdList) {
                            newFlowInstId = CommonTools.Obj2String(destroyIdMap.get(refFlowTempId));
                            if(!"".equals(newFlowInstId)){
                                break;
                            }
                        }
                         refMap.remove("ID");
                        refMap.remove("T_DESTROY_FLOW_" + schemaId + "_ID");
                        refMap.put("T_DESTROY_FLOW_" + schemaId + "_ID", newFlowInstId);
                        orientSqlEngine.getBmService().insertModelData(refPostNodeModel, refMap);
                    }
                }

                List<Map<String, Object>> skillDocumentList = orientSqlEngine.getBmService().createModelQuery(skillDocumentModel).list();
                if (skillDocumentList.size() > 0) {
                    for (Map skillMap : skillDocumentList) {
                        String skillName = CommonTools.Obj2String(skillMap.get("C_FILE_NAME_" + skillDocumentModel.getId()));
                        String refFlowTempId = CommonTools.Obj2String(skillMap.get("T_DESTROY_FLOW_" + schemaId + "_ID"));
                        String newFlowInstId = "";
                        for (Map destroyIdMap : destroyIdList) {
                            newFlowInstId = CommonTools.Obj2String(destroyIdMap.get(refFlowTempId));
                            if(!"".equals(newFlowInstId)){
                                break;
                            }
                        }
                        skillMap.remove("ID");
                        skillMap.remove("T_DESTROY_FLOW_" + schemaId + "_ID");
                        skillMap.put("T_DESTROY_FLOW_" + schemaId + "_ID", newFlowInstId);
                        orientSqlEngine.getBmService().insertModelData(skillDocumentModel, skillMap);

//                        try {
//                            JSONArray jsonArray = new JSONArray(skillName);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String fileId = jsonObject.getString("id");
//                                String name = jsonObject.getString("name");
//                                String fileType = jsonObject.getString("fileType");
////                                String findFileSql = " select FILENAME,FILETYPE,FILELOCATION,FILESIZE,UPLOAD_USER_ID, UPLOAD_DATE,FINALNAME," +
////                                        "FILESECRECY,UPLOAD_STATUS,FILECATALOG from cwm_file where FILEID='" + fileId + "'";
//                                List<Map<String, Object>> fileList = jdbcTemplate.queryForList(findFileSql);
////                                if (fileList.size() > 0) {
////                                    StringBuilder insertSql = new StringBuilder();
////                                    insertSql.append("insert into cwm_file (FILENAME,FILETYPE,FILELOCATION,FILESIZE,UPLOAD_USER_ID, UPLOAD_DATE,FINALNAME," +
////                                            "FILESECRECY,UPLOAD_STATUS,FILECATALOG)").append(findFileSql);
//                                    //获取复制新生成的文件主键ID
////                                    int copyNewFileId = (int) metaDaoFactory.getJdbcTemplate().execute(insertSql.toString(), new PreparedStatementCallback() {
////                                        @Override
////                                        public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
////                                            preparedStatement.executeUpdate();
////                                            ResultSet rs = preparedStatement.getGeneratedKeys();
////                                            rs.next();
////                                            return rs.getInt(1);
////                                        }
////                                    });
////                                    List<Map> newSkillList=UtilFactory.newArrayList();
////                                    Map newSkillMap=UtilFactory.newHashMap();
////                                    newSkillMap.put("id",copyNewFileId);
////                                    newSkillMap.put("name",name);
////                                    newSkillMap.put("fileType",fileType);
////                                    newSkillList.add(newSkillMap);
////                                    String skillFileName=newSkillList.toString();
//                                    Map insertSkillMap=UtilFactory.newHashMap();
//                                    insertSkillMap.put("C_FILE_NAME_"+skillDocumentModel.getId(),skillFileName);
//                                    String newFlowInstId = "";
//                                    for (Map destroyIdMap : destroyIdList) {
//                                        newFlowInstId = CommonTools.Obj2String(destroyIdMap.get(refFlowTempId));
//                                    }
//                                    insertSkillMap.put("T_DESTROY_FLOW_"+skillName+"_ID",newFlowInstId);
//                                    orientSqlEngine.getBmService().insertModelData(skillDocumentModel,insertSkillMap);
////                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                    }
                }

                retVal.setMsg("保存成功");
                retVal.setSuccess(true);
            }
        }
        return retVal;
    }

    public ExtComboboxResponseData<ExtComboboxData> getEnumDestroyType() {
        ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();

        IBusinessModel destroyTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map> destroyTypeList = orientSqlEngine.getBmService().createModelQuery(destroyTypeBM).orderAsc("TO_NUMBER(ID)").list();
        if (destroyTypeList.size() > 0) {
            for (Map typeMap : destroyTypeList) {
                String destroyTypeId = (String) typeMap.get("ID");
                String destroyTypeName = (String) typeMap.get("C_NAME_" + destroyTypeBM.getId());
                ExtComboboxData cb = new ExtComboboxData();
                cb.setId(destroyTypeId);
                cb.setValue(destroyTypeName);
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

    public CommonResponseData destroyTaskBegin(String destroyTaskId) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel destroyTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        String taskModelId = destroyTaskBM.getId();
        List<Map<String, Object>> destroyTaskList = orientSqlEngine.getBmService().createModelQuery(destroyTaskBM).list();
        if (destroyTaskList.size() > 0) {
            for (int i = 0; i < destroyTaskList.size(); i++) {
                String taskID = CommonTools.Obj2String(destroyTaskList.get(i).get("ID"));
                if (destroyTaskId.equals(taskID)) {
                    destroyTaskList.remove(i);
                } else {
                    continue;
                }
            }
            for (Map taskMap : destroyTaskList) {
                String taskState = CommonTools.Obj2String(taskMap.get("c_state_" + destroyTaskBM.getId()));
                String destroyTaskName = CommonTools.Obj2String(taskMap.get("c_name_" + destroyTaskBM.getId()));
                if ("进行中".equals(taskState)) {
                    retVal.setSuccess(false);
                    retVal.setMsg(destroyTaskName + "还未结束!");
                    return retVal;
                } else {
                    continue;
                }
            }
            destroyTaskBM.clearCustomFilter();
            destroyTaskBM.setReserve_filter(" AND ID='" + destroyTaskId + "'");
            Map<String, String> destroyTaskMap = (Map<String, String>) orientSqlEngine.getBmService().createModelQuery(destroyTaskBM).list().get(0);
            String taskState = CommonTools.Obj2String(destroyTaskMap.get("c_state_" + destroyTaskBM.getId()));
            if (taskState.equals("未开始")) {
                destroyTaskMap.put("c_state_" + taskModelId, "进行中");
                orientSqlEngine.getBmService().updateModelData(destroyTaskBM, destroyTaskMap, destroyTaskId);
                retVal.setSuccess(true);
                retVal.setMsg("成功启动拆解任务！");
            } else if (taskState.equals("已结束")) {
                retVal.setSuccess(false);
                retVal.setMsg("当前任务已结束！");
            } else if (taskState.equals("进行中")) {
                destroyTaskMap.put("c_state_" + taskModelId, "已结束");
                orientSqlEngine.getBmService().updateModelData(destroyTaskBM, destroyTaskMap, destroyTaskId);
                retVal.setSuccess(true);
                retVal.setMsg("任务已经结束！");
            }
        }
        return retVal;
    }

    public void delDestroyFlowById(String destroyFlowId) {
        String tableName = PropertyConstant.DESTROY_FLOW;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        orientSqlEngine.getBmService().deleteCascade(bm, destroyFlowId);
    }

    public AjaxResponseData saveAttendPersonData(String id, String postId, String flowId, String selectPersonId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        refPostBM.setReserve_filter("AND ID='" + id + "'" + " AND C_POST_ID_" + refPostBM.getId() + "='" + postId + "'" +
                " AND T_DESTROY_FLOW_" + schemaId + "_ID='" + flowId + "'");
        List<Map<String, Object>> destroyPostList = orientSqlEngine.getBmService().createModelQuery(refPostBM).list();
        if (destroyPostList.size() > 0) {
            Map destroyPostMap = destroyPostList.get(0);
            String hasSelectPersonIds = CommonTools.Obj2String(destroyPostList.get(0).get("C_POST_PERSONNEL_" + refPostBM.getId()));
            if (selectPersonId != null && !"".equals(selectPersonId)) {
                String[] attendPersonIds = selectPersonId.split(",");
                List<String> attendPersonList = Arrays.asList(attendPersonIds);
                if (hasSelectPersonIds != null && !"".equals(hasSelectPersonIds)) {
                    String[] hasAttendPerson = hasSelectPersonIds.split(",");
                    List<String> hasAttendPersonList = new ArrayList<>(Arrays.asList(hasAttendPerson));
                    for (String attend : attendPersonList) {
                        if (hasAttendPersonList.contains(attend)) {
                            continue;
                        } else {
                            hasAttendPersonList.add(attend);
                        }
                    }
                    //前台删除的人员，因此数据库中的人员也应该删除
                    int delete=0;
                    for (int i=0; i<hasAttendPersonList.size();i++) {
                        for (String attend : attendPersonList) {
                            if (hasAttendPersonList.get(i).equals(attend)) {
                                delete++;
                                break;
                            }
                        }
                        if (i>=delete){
                            hasAttendPersonList.remove(i);
                            i--;
                        }
                    }
                    String newAttendPersonIds = Joiner.on(",").join(hasAttendPersonList);
                    destroyPostMap.put("C_POST_PERSONNEL_" + refPostBM.getId(), newAttendPersonIds);
                } else {
                    destroyPostMap.put("C_POST_PERSONNEL_" + refPostBM.getId(), selectPersonId);
                }
            } else {
                destroyPostMap.put("C_POST_PERSONNEL_" + refPostBM.getId(), "");
            }
            orientSqlEngine.getBmService().updateModelData(refPostBM, destroyPostMap, id);
        }
        retVal.setSuccess(true);
        retVal.setMsg("保存成功！");
        return retVal;
    }

    public ExtGridData<Map> queryDestroyPostData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort, String flowId) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);

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
        List<Map> dataList = businessModelQuery.list();
        for (Map postMap : dataList) {
            String postId = CommonTools.Obj2String(postMap.get("C_POST_ID_" + orientModelId));
            String postPersonerIds = CommonTools.Obj2String(postMap.get("C_POST_PERSONNEL_" + orientModelId));
            if (StringUtil.isNotEmpty(postId)) {
                StringBuilder sql = new StringBuilder();
                sql.append("select id,c_post_name_" + postBM.getId() + " from T_POST_MGR_" + schemaId + " where id in(").append(postId).append(")");
                List<Map<String, Object>> list = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                postMap.put("C_POST_ID_" + orientModelId, list.get(0).get("c_post_name_" + postBM.getId()));
                postMap.put("C_POST_ID_" + orientModelId + "_DISPLAY", postId);
                postMap.put("C_POST_PERSONNEL_" + orientModelId+"_ID", postPersonerIds);
                if (StringUtil.isNotEmpty(postPersonerIds)) {
                    StringBuilder userSql = new StringBuilder();

                    userSql.append("select id,all_name from cwm_sys_user where id in(").append(postPersonerIds).append(")");

                    List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());
                    String allNames = "";
                    for (Map userMap : userList) {
                        String allName = CommonTools.Obj2String(userMap.get("all_name"));
                        allNames += allName + ",";
                    }
                    allNames = allNames.substring(0, allNames.length() - 1);
                    postMap.put("C_POST_PERSONNEL_" + orientModelId, allNames);
                }
            }
        }
        retVal.setResults(dataList);
        retVal.setTotalProperty(count);
        return retVal;
    }

    public Map<String, Object> importDestroyListFromExcel(TableEntity excelEntity, String destroyTypeId,String destroyTypeName) {

        IBusinessModel destroyTypeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_TYPE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel destroyFlowBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_FLOW, schemaId, EnumInter.BusinessModelEnum.Table);

        Map<String, Object> retVal = new HashMap<>();
        List<ImportDestroyBean> destroyBeanList = UtilFactory.newArrayList();

        List<DataEntity> dataEntities = excelEntity.getDataEntityList();
        for (int j = 0; j < dataEntities.size(); j++) {
            ImportDestroyBean importDestroyBean = new ImportDestroyBean();
            //取出每一行的单元格数据遍历，插入到数据库
            List<FieldEntity> fieldEntities = dataEntities.get(j).getFieldEntityList();
            if (fieldEntities != null) {
                for (int i = 0; i < fieldEntities.size(); i++) {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    String name = fieldEntities.get(i).getName();
                    String value = fieldEntities.get(i).getValue();
                    if ("月份".equals(name)) {
                        if (value == null || "".equals(value)) {
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumer + "行第" + i + "列的月份不可为空，请修正后导入！");
                            return retVal;
                        }else if (value!=null&&!"".equals(value)){
                            int index=value.lastIndexOf("-");
                            value=value.substring(0,index);
                            if (!destroyTypeName.equals(value)){
                                continue;
                            }
                        }
                    }else if ("设备名称".equals(name)) {
                        if (value == null || "".equals(value)) {
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumer + "行第" + i + "列的设备名称不可为空，请修正后导入！");
                            return retVal;
                        }
                        importDestroyBean.setName(value);
                    } else if ("开始时间".equals(name)||"结束时间".equals(name)) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date storeDate = null;
                        try {
                            if (value != null && !"".equals(value)) {
                                storeDate = simpleDateFormat.parse(value);
                            } else if (value == null || "".equals(value)) {
                                storeDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                            }
                            if ("开始时间".equals(name)){
                                importDestroyBean.setStartDate(storeDate);
                            }else if ("结束时间".equals(name)){
                                importDestroyBean.setEndDate(storeDate);
                            }
                        } catch (ParseException e) {
//                            e.printStackTrace();
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumer + "行第" + i + "列的时间格式存在错误！");
                            return retVal;
                        }
                    }else {
                        retVal.put("success", false);
                        retVal.put("msg", "导入失败,表头格式存在错误！");
                        return retVal;
                    }
                }
                if (!"".equals(importDestroyBean.getName())) {
                    destroyBeanList.add(importDestroyBean);
                }
            }
        }
        if (destroyBeanList.size() > 0) {
            for (ImportDestroyBean importDestroyBean  : destroyBeanList) {
                String deviceName = importDestroyBean.getName();
                Date startDate=importDestroyBean.getStartDate();
                Date endDate=importDestroyBean.getEndDate();
                destroyFlowBM.clearAllFilter();
                    destroyFlowBM.setReserve_filter("AND C_NAME_" + destroyFlowBM.getId() + "='" + deviceName + "'" +
                            " AND T_DESTROY_TYPE_" + schemaId + "_ID='" + destroyTypeId + "'");
                List<Map> destroyList = orientSqlEngine.getBmService().createModelQuery(destroyFlowBM).list();
                if (destroyList.size() ==0) {
                    Map destroyMap = UtilFactory.newHashMap();
                    destroyMap.put("C_NAME_" + destroyFlowBM.getId(), deviceName);
                    destroyMap.put("C_FLOW_START_TIME_" + destroyFlowBM.getId(), CommonTools.util2Sql(startDate));
                    destroyMap.put("C_FLOW_END_TIME_" + destroyFlowBM.getId(),CommonTools.util2Sql(endDate));
                    destroyMap.put("T_DESTROY_TYPE_" + schemaId + "_ID", destroyTypeId);
                    orientSqlEngine.getBmService().insertModelData(destroyFlowBM, destroyMap);
                }
            }
        }
        retVal.put("success", true);
        retVal.put("msg", "导入成功！");
        return retVal;
    }

    public AjaxResponseData saveChooseProductTree(String treeId, String checkTableInstId) {
//        AjaxResponseData retVal = new AjaxResponseData();
//        IBusinessModel checkTempInstBM=businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST,schemaId,EnumInter.BusinessModelEnum.Table);
//        checkTempInstBM.setReserve_filter("AND ID='"+checkTableInstId+"'");
//        List<Map<String,Object>> checkInstList=orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).list();
//        if (checkInstList.size()>0){
//            Map checkInstMap=checkInstList.get(0);
//            if (StringUtil.isEmpty(treeId)){
//                checkInstMap.put("C_PRODUCT_ID_"+checkTempInstBM.getId(),"");
//            }else{
//                checkInstMap.put("C_PRODUCT_ID_"+checkTempInstBM.getId(),treeId);
//            }
//            orientSqlEngine.getBmService().updateModelData(checkTempInstBM,checkInstMap,checkTableInstId);
//        }
//        retVal.setSuccess(true);
//        retVal.setMsg("保存成功！");
//        return retVal;
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel checkTempInstBM=businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST,schemaId,EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkCellInstBM=businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST,schemaId,EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkRowInstBM=businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST,schemaId,EnumInter.BusinessModelEnum.Table);
        IBusinessModel spareInstBM=businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST,schemaId,EnumInter.BusinessModelEnum.Table);

        checkTempInstBM.setReserve_filter("AND ID='"+checkTableInstId+"'");
        List<Map<String,Object>> checkInstList=orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).list();

        if (checkInstList.size()>0){
            Map checkInstMap=checkInstList.get(0);
            if (StringUtil.isEmpty(treeId)){
                checkInstMap.put("C_PRODUCT_ID_"+checkTempInstBM.getId(),"");
            }else{
                checkInstMap.put("C_PRODUCT_ID_"+checkTempInstBM.getId(),treeId);
                checkCellInstBM.setReserve_filter(" AND C_CONTENT_"+checkCellInstBM.getId()+" like '%" + "结论" + "%'"+
                        " AND T_CHECK_TEMP_INST_"+schemaId+"_ID='"+checkTableInstId+"'");
                List<Map<String,Object>> checkCellList=orientSqlEngine.getBmService().createModelQuery(checkCellInstBM).list();
                if(checkCellList.size()>0){
                    String rowInstId=CommonTools.Obj2String(checkCellList.get(0).get("T_CHECK_ROW_INST_"+schemaId+"_ID"));
                    checkRowInstBM.setReserve_filter("AND ID='"+rowInstId+"'");
                    List<Map<String,Object>> checkRowList=orientSqlEngine.getBmService().createModelQuery(checkRowInstBM).list();
                    if (checkRowList.size()>0){
                        Map checkRowMap=checkRowList.get(0);
                        spareInstBM.setReserve_filter("AND C_PRODUCT_ID_"+spareInstBM.getId()+"='"+treeId+"'");
                        List<Map<String,Object>> spareInstList= orientSqlEngine.getBmService().createModelQuery(spareInstBM).list();
                        if (spareInstList.size()>0){
                            String deviceInstId=CommonTools.Obj2String(spareInstList.get(0).get("ID"));
                            checkRowMap.put("C_DEVICE_INST_ID_"+checkRowInstBM.getId(),deviceInstId);
                        }
                        checkRowMap.put("C_PRODUCT_ID_"+checkRowInstBM.getId(),treeId);
                        orientSqlEngine.getBmService().updateModelData(checkRowInstBM,checkRowMap,rowInstId);
                        checkCellInstBM.clearAllFilter();
                        checkCellInstBM.setReserve_filter(" AND T_CHECK_ROW_INST_"+schemaId+"_ID='"+rowInstId+"'"+
                                " AND T_CHECK_TEMP_INST_"+schemaId+"_ID='"+checkTableInstId+"'"+" AND C_CELL_TYPE_" + checkCellInstBM.getId()
                                + "='#2'");
                        List<Map<String,Object>> checkCellInstList=orientSqlEngine.getBmService().createModelQuery(checkCellInstBM).list();
                        if (checkCellInstList.size()>0){
                            for (Map checkCellMap:checkCellInstList){
                                checkCellMap.put("C_PRODUCT_ID_"+checkCellInstBM.getId(),treeId);
                                String cellInstId=CommonTools.Obj2String(checkCellMap.get("ID"));
                                orientSqlEngine.getBmService().updateModelData(checkCellInstBM,checkCellMap,cellInstId);
                            }
                        }
                    }
                }
            }
            orientSqlEngine.getBmService().updateModelData(checkTempInstBM,checkInstMap,checkTableInstId);
        }
        retVal.setSuccess(true);
        retVal.setMsg("保存成功！");
        return retVal;
    }
    public AjaxResponseData getChooseProductTree(String checkTableInstId) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        checkTempInstBM.setReserve_filter(" AND ID='" + checkTableInstId + "'");
        List<Map> checkInstList = orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).list();
        if (checkInstList.size() > 0) {
            String prodcutId = CommonTools.Obj2String(checkInstList.get(0).get("C_PRODUCT_ID_" +checkTempInstBM.getId()));
            String sql = "select c_name_" + productBM.getId() + " from T_PRODUCT_STRUCTURE_" + schemaId + " where id='" + prodcutId + "'";
            List<Map<String, Object>> productList = jdbcTemplate.queryForList(sql);
            if (StringUtil.isEmpty(prodcutId)) {
                retVal.setResults("未选择");
            } else {
                if (productList.size() > 0) {
                    retVal.setResults(productList.get(0).get("c_name_" + productBM.getId()));
                } else {
                    retVal.setResults("未选择");
                }
            }
        }
        return retVal;
    }

}
