package com.orient.pvm.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.TeamBusiness;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collab.model.Task;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.pvm.bean.sync.*;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.pvm.CwmSysCheckmodelsetEntity;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.collab.ICollabPrjModelRelationService;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.sysmodel.service.pvm.IPVMMulCheckRelationService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckModelService;
import com.orient.sysmodel.service.pvm.impl.TaskCheckRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by mengbin on 16/8/1.
 * Purpose:与Android的同步
 * Detail:
 */
@Component
public class PVMSyncBusiness extends BaseBusiness {


    @Autowired
    private TaskCheckModelService taskCheckModelService;

    @Autowired
    private CheckModelBusiness checkModelBusiness;

    @Autowired
    private TaskCheckRelationService taskCheckRelationService;

    @Autowired
    private TeamBusiness teamBusiness;

    @Autowired
    private CheckModelManageBusiness checkModelManageBusiness;

    @Autowired
    ICollabPrjModelRelationService collabPrjModelRelationService;

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Autowired
    IPVMMulCheckRelationService pVMMulCheckRelationService;
    @Autowired
    private FileService fileService;

    /**
     * 根据用户Id, 获取其所有参与的Task,状态不是已完成的任务 绑定的校验模型(可下发状态)
     *
     * @param userId
     * @return
     */

    public List<String> getCheckTables(String userId, String taskModelId) {

        List<String> ret = new ArrayList<>();

        //todo 代码待修改
        List<Task> tasks = teamBusiness.getParticiptionJob(Task.class, CollabConstants.TASK, userId);
        /*for (Task task : tasks) {
            if (task.getStatus().equals(CollabConstants.STATUS_FINISHED)) continue;
            List<TaskCheckModel> checkModels = checkModelBusiness.getBindCheckModel(taskModelId, task.getId(), 2);
            for (TaskCheckModel checkModel : checkModels) {
                ret.add(String.valueOf(checkModel.getId()));
            }
        }*/
        return ret;
    }


    /**
     * 获取同步的用户信息
     *
     * @return
     */

    public PVMUsers getAllUsers() {

        PVMUsers pvmUsers = new PVMUsers();

        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
        Iterator iterator = users.values().iterator();
        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            if (Integer.valueOf(user.getId()) < 0) {
                continue;
            }
            PVMUser pvmUser = new PVMUser();
            pvmUser.setId(user.getId());
            pvmUser.setName(user.getUserName());
            pvmUser.setDisplayName(user.getAllName());
            pvmUser.setPassword(user.getPassword());
            pvmUsers.getUsers().add(pvmUser);
        }

        return pvmUsers;
    }


    /**
     * 根据 checkModelId 获取 输出XML的对象
     *
     * @param checkModelBindId task 与 Checkmodel 绑定的Id
     * @param taskModelId      task的模型Id
     * @return
     */

    public PVMTables getCheckModelInfo(String checkModelBindId, String taskModelId) {
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(Long.valueOf(checkModelBindId));

        Task orientTask = this.orientSqlEngine.getTypeMappingBmService().getById(Task.class, String.valueOf(taskCheckModel.getNodeId()));
        Plan plan = this.orientSqlEngine.getTypeMappingBmService().getById(Plan.class, String.valueOf(orientTask.getParPlanId()));
        Project project = this.orientSqlEngine.getTypeMappingBmService().getById(Project.class, String.valueOf(plan.getParProjectId()));
        PVMTables pvmTables = new PVMTables();
        pvmTables.setVersion("1.0");
        List<CheckModel> checkModels = pvmTables.getCheckModelList();
        CheckModel checkModel = new CheckModel();
        checkModels.add(checkModel);
        checkModel.setModelId(String.valueOf(taskCheckModel.getId()));
        IBusinessModel dsModel = null;
        if (taskCheckModel.getCheckmodelid() != null) {
            dsModel = businessModelService.getBusinessModelById(String.valueOf(taskCheckModel.getCheckmodelid()), EnumInter.BusinessModelEnum.Table);

        }
        checkModel.setModelName(taskCheckModel.getName());
        checkModel.setRemark(taskCheckModel.getRemark());
        checkModel.setModelStatus(String.valueOf(taskCheckModel.getChecktablestatus()));
        checkModel.setPlan_Start(orientTask.getActualStartDate());
        checkModel.setPlan_End(orientTask.getActualEndDate());
        checkModel.setTaskId(orientTask.getId());
        String taskPath = project.getName() + "[orient-mid]" + plan.getName() + "[orient-mid]" + orientTask.getName();
        checkModel.setTaskPath(taskPath);
        if (project != null) {
            checkModel.setProjectName(project.getName());
        } else if (plan != null) {
            checkModel.setProjectName(plan.getName());
        }

        Signers signers = new Signers();
        if (!StringUtil.isEmpty(taskCheckModel.getSignroles())) {
            String signRoles[] = taskCheckModel.getSignroles().split(",");
            for (String signRole : signRoles) {
                Signer signer = new Signer();
                signer.setRoleName(signRole);
                signers.getSigners().add(signer);
            }
        }

        checkModel.setSigners(signers);
        Team team = new Team();
        List<String> ids = teamBusiness.getJobUserIds(CollabConstants.TASK, orientTask.getId());
        for (String id : ids) {
            UserId userId = new UserId();
            userId.setId(id);
            team.getUserIds().add(userId);
        }
        checkModel.setTeam(team);

        if (dsModel != null) {
            Columns tableColumns = new Columns();
            List<IBusinessColumn> dbColumns = dsModel.getAllBcCols();
            for (IBusinessColumn dbColumn : dbColumns) {
                //dbColumn.getCol().getId();这是checkModelSet表中的column_id
                String columnId = dbColumn.getCol().getId();
                List<CwmSysCheckmodelsetEntity> entity = checkModelManageBusiness.checkModelManageService.list(Restrictions.eq("columnId", columnId));
                if (dbColumn.getCol().getCategory() == IColumn.CATEGORY_COMMON) {
                    Column col = new Column();
                    col.setHeadCode(dbColumn.getCol().getColumnName());
                    col.setHeadName(dbColumn.getDisplay_name());
                    col.setType(dbColumn.getColType().toString());
                    if (entity.size() == 1) {//如果存在检查字段设置格式 非空判断
                        //设置默认格式
                        col.setDefaultType(entity.get(0).getCheckType().toString());
                        //设置字段是否为必填
                        setColumnIsRequired(entity, col);
                        //设置字段是否绑定照片
                        setColumnIsBindPhoto(entity, col);
                    }
                    tableColumns.getColumns().add(col);
                }
            }
            checkModel.setColumns(tableColumns);
            setRows(checkModel, tableColumns, dsModel, taskModelId);
            checkModel.setModelType(CheckModel.TYPE_MODEL);
        }
        if (!StringUtil.isEmpty(taskCheckModel.getHtml())) {
            checkModel.setModelType(CheckModel.TYPE_HTML);
        }
        if (dsModel != null && !StringUtil.isEmpty(taskCheckModel.getHtml())) {
            checkModel.setModelType(CheckModel.TYPE_ALL);
        }
        return pvmTables;
    }

    private void setColumnIsBindPhoto(List<CwmSysCheckmodelsetEntity> entity, Column col) {
        if (entity.get(0).getIsBindPhoto() == null) {
            col.setIsBindPhoto("不绑定");
        } else if ("0".equals(entity.get(0).getIsBindPhoto().toString())) {
            col.setIsBindPhoto("绑定");
        } else {
            col.setIsBindPhoto("不绑定");
        }
    }

    private void setColumnIsRequired(List<CwmSysCheckmodelsetEntity> entity, Column col) {
        if (entity.get(0).getIsRequired() == null) {
            col.setIsRequired("非必填");
        } else if ("0".equals(entity.get(0).getIsRequired().toString())) {
            col.setIsRequired("必填");
        } else {
            col.setIsRequired("非必填");
        }
    }


    /**
     * 获取checkModel的HTML内容
     *
     * @param checkModelBindId
     * @return
     */

    public String getTaskCheckHtmlBy(String checkModelBindId) {
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(Long.valueOf(checkModelBindId));
        if (taskCheckModel != null) {
            return CommonTools.Obj2String(taskCheckModel.getHtml());
        }
        return "";
    }


    public boolean setTaskCheckHtmlBy(String checkModelBindId, String html) {
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(Long.valueOf(checkModelBindId));
        if (taskCheckModel != null) {
            taskCheckModel.setChecktablestatus(TaskCheckModel.STATUS_UPLOADED);
            taskCheckModel.setHtml(html);
            taskCheckModelService.save(taskCheckModel);
            return true;

        }
        return false;
    }

    /**
     * 根据PVMTable的内容,更新数据库
     *
     * @param pvmTables
     * @return
     */
    public boolean setCheckModelInfo(PVMTables pvmTables, StringBuffer errorInfo) {

        List<CheckModel> checkModelList = pvmTables.getCheckModelList();

        for (CheckModel checkModel : checkModelList) {

            if (false == this.setCheckModelInfo(checkModel, errorInfo)) {
                errorInfo.append(checkModel.getModelName() + " 上传失败!\n");
                return false;
            }

        }

        return true;
    }


    public boolean setCheckModelInfo(CheckModel checkModel, StringBuffer errorInfo) {

        String checkModelBindId = checkModel.getModelId();
        TaskCheckModel taskCheckModel = taskCheckModelService.getById(Long.valueOf(checkModelBindId));
        if (taskCheckModel == null) {
            errorInfo.append(checkModel.getModelName() + "不存在,有可能已被删除\n");
            return false;
        }
        taskCheckModel.setChecktablestatus(TaskCheckModel.STATUS_UPLOADED);
        taskCheckModel.setSignfileids(checkModel.getSignfileids());
        //taskCheckModel.setUploadtime(new Date());
        //需求变更，改变上传时间
        String time = checkModel.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(time);
            taskCheckModel.setUploadtime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Signer> signers = checkModel.getSigners().getSigners();
        String singerNames = "";
        for (Signer signer : signers) {
            singerNames = singerNames + "," + signer.getUserName();
        }
        if (singerNames.length() > 0) {
            taskCheckModel.setSignnames(singerNames.substring(1));
        } else {
            taskCheckModel.setSignnames(singerNames.substring(0));
        }
        taskCheckModel.setUploaduser(checkModel.getTeam().getUploadUserId());

        if (checkModel.getFiles() != null) {
            List<PVMFile> files = checkModel.getFiles().getFiles();
            if (files.size() > 0) {
                for (PVMFile file : files) {
                    CwmFile cwmFile = fileService.findFileById(file.getFileId());
                    cwmFile.setDataid(checkModelBindId);
                    cwmFile.setTableid("CWM_TASKCHECKMODEL");
                    fileService.updateFile(cwmFile);
                }
            }
        }

        if (taskCheckModel.getCheckmodelid() != null) {
            IBusinessModel dsModel = businessModelService.getBusinessModelById(String.valueOf(taskCheckModel.getCheckmodelid()), EnumInter.BusinessModelEnum.Table);
            for (Row row : checkModel.getRows()) {
                String dataId = row.getDataId();
                if (StringUtil.isEmpty(dataId)) {
                    //增加
                    createNewCheckData(row, dsModel, taskCheckModel);
                } else {
                    //更新
                    for (Check check : row.getChecks()) {
                        setCheckValue(check, dataId, dsModel);
                    }
                }
            }
        }

        return true;
    }


    /**
     * 增加记录,并且关联好checkModel.
     *
     * @param row
     * @param dsModel
     * @param taskCheckModel
     * @return
     */
    private boolean createNewCheckData(Row row, IBusinessModel dsModel, TaskCheckModel taskCheckModel) {
        Map<String, String> dataMap = new HashMap<>();
        for (Check check : row.getChecks()) {
            if (check.getCheckOps().size() != 0) {
                String checkOpsValue = getCheckOpsValue(check);
                dataMap.put(check.getHeadCode(), checkOpsValue);
            } else {
                dataMap.put(check.getHeadCode(), check.getDispalyValue());
            }
        }
        String dataIndex = orientSqlEngine.getBmService().insertModelData(dsModel, dataMap);
        if (StringUtil.isEmpty(dataIndex)) {
            return false;
        }

        return checkModelBusiness.createRelationDatas(taskCheckModel, dataIndex);


    }


    /**
     * 根据checkoperation 的返回值 拼接生成 CheckValue
     *
     * @param check
     * @param dataId
     * @param dsModel
     * @return
     */
    private boolean setCheckValue(Check check, String dataId, IBusinessModel dsModel) {
        if (check.getCheckOps().size() == 0) {
            return true;
        }

        String checkValue = getCheckOpsValue(check);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put(check.getHeadCode(), checkValue);
        orientSqlEngine.getBmService().updateModelData(dsModel, dataMap, dataId);
        return true;
    }

    /**
     * 如果是check字段,获取该字段的Json字符串
     *
     * @param check
     * @return
     */
    private String getCheckOpsValue(Check check) {
        if (check.getCheckOps().size() == 0) {
            return "";
        }
        String checkValue = "";
        // [{"labelName":"","inputValue":true},{"labelName":"xxa","inputValue":false},{"labelName":"2","inputValue":"2"},{"labelName":"1","inputValue":"1"}]
        List<Map<String, Object>> jsonList = new ArrayList<>();
        for (CheckOP checkOP : check.getCheckOps()) {


            Map<String, Object> data = new HashMap<>();
            data.put("labelName", checkOP.getLabel());

            if (checkOP.getOPTYPE().equals(CheckOP.TYPE_INPUT)) {
                //填值
                data.put("inputValue", checkOP.getValue());
            } else {
                //打勾
                if (checkOP.getValue().equalsIgnoreCase("true")) {
                    data.put("inputValue", true);
                } else {
                    data.put("inputValue", false);
                }
            }

            data.put("userId", checkOP.getOPUSERID());       //操作人员
            data.put("opTime", checkOP.getOPTIME());         //操作时间
            data.put("fileId", checkOP.getFileId());        //绑定的附件Id,用","分隔
            jsonList.add(data);
        }
        checkValue = JsonUtil.toJson(jsonList);
        return checkValue;
    }


    /**
     * 设置数据行
     *
     * @param checkModel
     * @param tableColumn
     * @param dsModel
     * @param taskModelId
     * @return
     */
    private boolean setRows(CheckModel checkModel, Columns tableColumn, IBusinessModel dsModel, String taskModelId) {

        List<Row> rows = checkModel.getRows();
        TaskCheckRelation example = new TaskCheckRelation();
        example.setTaskdataid(Long.valueOf(checkModel.getTaskId()));
        example.setTaskmodelid(Long.valueOf(taskModelId));
        example.setCheckmodelid(Long.valueOf(dsModel.getId()));
        List<TaskCheckRelation> relations = taskCheckRelationService.listBeansByExample(example);
        String ids = "";
        for (TaskCheckRelation relation : relations) {
            Long dataId = relation.getCheckdataid();
            ids = ids + "," + String.valueOf(dataId);
        }
        if (!StringUtil.isEmpty(ids)) {
            ids = ids.substring(1);
        }
        CustomerFilter customerFilter = new CustomerFilter("ID", EnumInter.SqlOperation.In, ids);
        dsModel.appendCustomerFilter(customerFilter);
        List<Map<String, String>> datas = orientSqlEngine.getBmService().createModelQuery(dsModel).list();

        for (Map<String, String> rowData : datas) {

            Row row = new Row();
            row.setDataId(rowData.get("ID"));
            List<Check> checks = row.getChecks();
            for (Column col : tableColumn.getColumns()) {
                Check check = new Check();
                check.setType(col.getType());
                check.setHeadName(col.getHeadName());
                check.setHeadCode(col.getHeadCode());
                if (!col.getType().equals("C_Check")) {
                    check.setDispalyValue(rowData.get(col.getHeadCode()));
                } else {
                    String checkOps = rowData.get(col.getHeadCode());
                    if (!StringUtil.isEmpty(checkOps)) {
                        parseCheckValue(checkOps, check.getCheckOps());
                    }
                }
                checks.add(check);
            }
            rows.add(row);
        }
        return true;

    }

    /**
     * 解析检查字段,生成CheckOp的对象
     *
     * @param chepOpsValue
     * @param checkOps
     * @return
     */
    private boolean parseCheckValue(String chepOpsValue, List<CheckOP> checkOps) {


        // [{"labelName":"","inputValue":true},{"labelName":"xxa","inputValue":false},{"labelName":"2","inputValue":"2"},{"labelName":"1","inputValue":"1"}]
        List jsonList = JsonUtil.json2List(chepOpsValue);
        for (Object obj : jsonList) {
            Map<String, Object> data = (Map<String, Object>) obj;
            CheckOP checkOP = new CheckOP();
            checkOP.setLabel(CommonTools.Obj2String(data.get("labelName")));
            if (data.get("inputValue") instanceof String) {
                checkOP.setValue(CommonTools.Obj2String(data.get("inputValue")));
                checkOP.setOPTYPE(CheckOP.TYPE_INPUT);
            } else {
                checkOP.setValue("fasle");
                checkOP.setOPTYPE(CheckOP.TYPE_CHECK);
            }
            checkOps.add(checkOP);
        }
        return true;
    }

}
