package com.orient.history.business;

import com.orient.background.bean.AuditFlowTaskSettingEntityWrapper;
import com.orient.background.bean.CwmComponentModelEntityWrapper;
import com.orient.background.business.AuditFlowTaskSettingBusiness;
import com.orient.background.business.ComponentBindBusiness;
import com.orient.background.business.ModelFormViewBusiness;
import com.orient.background.controller.ModelFormViewController;
import com.orient.background.event.PreviewModelViewEvent;
import com.orient.background.eventParam.PreviewModelViewEventParam;
import com.orient.collab.business.DataFlowBusiness;
import com.orient.collab.business.GanttBusiness;
import com.orient.collab.business.ProjectTreeBusiness;
import com.orient.collab.business.TeamBusiness;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.*;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.business.FlowInfoBusiness;
import com.orient.history.core.IHisTaskEngine;
import com.orient.history.core.binddata.model.HisTaskInfo;
import com.orient.history.core.engine.factory.IHisTaskEngineFactory;
import com.orient.history.core.request.*;
import com.orient.history.core.support.HisTaskSupport;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskHelper;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.GetGridModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.component.CwmComponentEntity;
import com.orient.sysmodel.domain.component.CwmComponentModelEntity;
import com.orient.sysmodel.domain.flow.AuditFlowTaskSettingEntity;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.domain.his.CwmSysHisTaskEntity;
import com.orient.sysmodel.service.flow.IHisTaskService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.modelDesc.model.OrientModelDesc;
import com.orient.utils.exception.OrientBaseAjaxException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class HisTaskBusiness extends BaseHibernateBusiness<CwmSysHisTaskEntity> {

    @Autowired
    IHisTaskService hisTaskService;

    @Autowired
    HisTaskSupport hisTaskSupport;

    @Autowired
    private FlowInfoBusiness flowInfoBusiness;

    @Autowired
    private GanttBusiness ganttBusiness;

    @Autowired
    private ModelDataBusiness modelDataBusiness;

    @Autowired
    private AuditFlowTaskSettingBusiness auditFlowTaskSettingBusiness;

    @Autowired
    private ProjectTreeBusiness projectTreeBusiness;

    @Autowired
    private DataFlowBusiness dataFlowBusiness;

    @Autowired
    private TeamBusiness teamBusiness;

    @Autowired
    private ComponentBindBusiness componentBindBusiness;

    @Autowired
    ModelFormViewBusiness modelFormViewBusiness;

    @Autowired
    FileServerConfig fileServerConfig;

    @Override
    public IHisTaskService getBaseService() {
        return hisTaskService;
    }

    public HisTaskInfo getHisTaskInfo(String taskId, String taskType) {
        HisTaskInfo retVal = null;
        List<CwmSysHisTaskEntity> hisTaskEntityList = hisTaskService.list(Restrictions.eq("taskId", taskId), Restrictions.eq("taskType", taskType));
        if (!CommonTools.isEmptyList(hisTaskEntityList)) {
            CwmSysHisTaskEntity hisTaskEntity = hisTaskEntityList.get(0);
            String savePath = hisTaskEntity.getSavePath();
            String ftpHome = fileServerConfig.getFtpHome();
            String filePath = ftpHome+savePath;
            File file = new File(filePath);
            byte[] bytes={};
            try(ByteArrayOutputStream bos = new ByteArrayOutputStream((int)file.length());
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                int buf_size = 1024;
                byte[] buffer = new byte[buf_size];
                int len = 0;
                while(-1 != (len=in.read(buffer,0,buf_size))) {
                    bos.write(buffer,0,len);
                }
                bytes = bos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //byte[] bytes = hisTaskEntity.getTaskBindData();
            if (null != bytes) {
                retVal = HisTaskHelper.getInstance().DerializeHisTask(bytes);
            }
        }
        return retVal;
    }

    public CwmSysHisTaskEntity saveHisTaskInfo(FrontViewRequest frontViewRequest) {
        IHisTaskEngineFactory hisTaskEngineFactory = hisTaskSupport.getHisTaskEngineFactory(frontViewRequest.getTaskType());
        if (null != hisTaskEngineFactory) {
            //保存临时数据
            HisTaskThreadLocalHolder.put(frontViewRequest.getTaskId() + HisTaskConstants.FRONT_REQUEST_KEY, frontViewRequest);
            IHisTaskEngine hisTaskEngine = hisTaskEngineFactory.createHisTaskEngine();
            //保存历史任务信息
            HisTaskInfo hisTaskInfo = hisTaskEngine.saveHisTaskInfo(frontViewRequest.getTaskId());
            if (null != hisTaskInfo) {
                CwmSysHisTaskEntity cwmSysHisTaskEntity = saveToDB(hisTaskInfo);
                return cwmSysHisTaskEntity;
            }
        } else
            throw new OrientBaseAjaxException("", "未找到相关历史处理引擎");
        return null;
    }

    private CwmSysHisTaskEntity saveToDB(HisTaskInfo hisTaskInfo) {
        CwmSysHisTaskEntity cwmSysHisTaskEntity = new CwmSysHisTaskEntity();
        BeanUtils.copyProperties(cwmSysHisTaskEntity, hisTaskInfo);
        //序列化对象
        byte[] bytes = HisTaskHelper.getInstance().SerializeHisTask(hisTaskInfo);
        String ftpHome = fileServerConfig.getFtpHome();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String savePath = File.separator+"HistoryTaskInfo"+File.separator+cwmSysHisTaskEntity.getTaskType()+File.separator+cwmSysHisTaskEntity.getTaskAssigner()+
                File.separator+sdf.format(date)+".txt";
        String filePath = ftpHome+savePath;
        File file = new File(filePath);
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while(-1!=(len=bin.read(buffer,0,buf_size))) {
                out.write(buffer,0,len);
            }
            cwmSysHisTaskEntity.setSavePath(savePath);//保存相对路径
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        hisTaskService.save(cwmSysHisTaskEntity);
        return cwmSysHisTaskEntity;
    }

    //构造frontViewRequest
    public AuditFrontViewRequest buildFrontViewRequest(AuditFrontViewRequest frontViewRequest,String auditType,String taskName) {
        Map<String, List<String>> modelFreemarkerHtml = new HashMap<>();
        List<ModelDataRequest> modelDataRequestList = new ArrayList<>();
        Map<String, String> extraData = new HashMap<>();

        List<FlowDataRelation> bindDatas = flowInfoBusiness.getFlowBindDatas(frontViewRequest.getPiId());
        if(auditType.equals("WbsBaseLineAudit")) {
            bindDatas.forEach(bindData->{
                if(bindData.getSubType().equals("gantt")) {
                    buildGanttInfo(modelDataRequestList,extraData,bindData);
                }
            });
        }else if(auditType.equals("ModelDataAudit")) {
            Map bindId = JsonUtil.json2Map(bindDatas.get(0).getExtramParams());
            if(bindId.isEmpty()) {
                Map<String,Object> modelCache = new HashMap<>();
                Map<String,List<Map>> modelDataCache = new HashMap<>();
                bindDatas.forEach(bindData->{
                    buildDetailFormInfo(modelCache,bindData,modelDataRequestList,modelDataCache);
                });
                for(String strId:modelDataCache.keySet()) {
                    ModelDataRequest request = new ModelDataRequest();
                    request.setModelId(strId);
                    request.setDataList(modelDataCache.get(strId));
                    modelDataRequestList.add(request);
                }
            }else{
                AuditFlowTaskSettingEntity example = new AuditFlowTaskSettingEntity();
                example.setBelongAuditBind(Long.parseLong(bindId.get("auditFlowModelBindId")+""));
                example.setTaskName(taskName);
                List<AuditFlowTaskSettingEntityWrapper> auditFlowTaskSettingEntities = auditFlowTaskSettingBusiness.listSpecial(null, null, example).getResults();
                if(auditFlowTaskSettingEntities.size()>0) {
                    AuditFlowTaskSettingEntityWrapper setting = auditFlowTaskSettingEntities.get(0);
                    if(setting.getFormId()!=null) {
                        Long formId = setting.getFormId();
                        bindDatas.forEach(bindData->{
                            PreviewModelViewEventParam eventParam = new PreviewModelViewEventParam();
                            eventParam.setFormValue(modelFormViewBusiness.findById(formId));
                            eventParam.setDataId(bindData.getDataId());
                            eventParam.setReGenTemplate(0);
                            OrientContextLoaderListener.Appwac.publishEvent(new PreviewModelViewEvent(ModelFormViewController.class, eventParam));
                            String html = eventParam.getOutHtml();
                            if(modelFreemarkerHtml.containsKey(bindData.getTableName())) {
                                modelFreemarkerHtml.get(bindData.getTableName()).add(html);
                            }else{
                                List<String> htmlList = new ArrayList<String>();
                                htmlList.add(html);
                                modelFreemarkerHtml.put(bindData.getTableName(),htmlList);
                            }
                        });
                    }else{
                        Map<String,Object> modelCache = new HashMap<>();
                        Map<String,List<Map>> modelDataCache = new HashMap<>();
                        bindDatas.forEach(bindData->{
                            buildDetailFormInfo(modelCache,bindData,modelDataRequestList,modelDataCache);
                        });
                        for(String strId:modelDataCache.keySet()) {
                            ModelDataRequest request = new ModelDataRequest();
                            request.setModelId(strId);
                            request.setDataList(modelDataCache.get(strId));
                            modelDataRequestList.add(request);
                        }
                    }
                }else{
                    Map<String,Object> modelCache = new HashMap<>();
                    Map<String,List<Map>> modelDataCache = new HashMap<>();
                    bindDatas.forEach(bindData->{
                        buildDetailFormInfo(modelCache,bindData,modelDataRequestList,modelDataCache);
                    });
                    for(String strId:modelDataCache.keySet()) {
                        ModelDataRequest request = new ModelDataRequest();
                        request.setModelId(strId);
                        request.setDataList(modelDataCache.get(strId));
                        modelDataRequestList.add(request);
                    }
                }

            }
        }

        frontViewRequest.setModelFreemarkerHtml(modelFreemarkerHtml);
        frontViewRequest.setExtraData(extraData);
        frontViewRequest.setModelDataRequestList(modelDataRequestList);
        return frontViewRequest;
    }

    //添加表单信息
    private void buildDetailFormInfo(Map<String, Object> modelCache, FlowDataRelation bindData, List<ModelDataRequest> modelDataRequestList, Map<String, List<Map>> modelDataCache) {
        OrientModelDesc modelDesc = new OrientModelDesc();
        Map modelData = new HashMap<>();
        if(modelCache.containsKey(bindData.getTableName())) {
            modelDesc = (OrientModelDesc)modelCache.get(bindData.getTableName());
            Map<String,String> filter = new HashMap<>();
            filter.put("filterName","ID");
            filter.put("operation","Equal");
            filter.put("expressionType","");
            filter.put("filterValue",bindData.getDataId());
            filter.put("connection","And");
            List<Map> filterList = new ArrayList<>();
            filterList.add(filter);
            String filterStr = JsonUtil.toJson(filterList);
            List<Map> results = modelDataBusiness.getModelDataByModelId(bindData.getTableName(),null,null,null,filterStr,true,null).getResults();
            modelData = results.get(0);
        }else{
            //获取模型描述
            GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(bindData.getTableName(), null, null);
            GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
            OrientContextLoaderListener.Appwac.publishEvent(getModelDescEvent);
            //获取模型数据
            Map result = modelDataBusiness.getModelDataByModelIdAndDataId(bindData.getTableName(),bindData.getDataId());
            modelDesc = getModelDescEventParam.getOrientModelDesc();
            modelCache.put(bindData.getTableName(),modelDesc);
            modelData = result;
        }

        String modelId = modelDesc.getModelId();
        if(!modelDataCache.containsKey(modelId)) {
            modelDataCache.put(modelId,new ArrayList<Map>());
        }
        modelDataCache.get(modelId).add(modelData);

//        for(String strId:modelDataCache.keySet()) {
//            ModelDataRequest request = new ModelDataRequest();
//            request.setModelId(strId);
//            request.setDataList(modelDataCache.get(strId));
//            modelDataRequestList.add(request);
//        }
    }

    //添加gantt有关信息
    private void buildGanttInfo(List<ModelDataRequest> modelDataRequestList, Map<String, String> extraData, FlowDataRelation bindData) {
        String[] modelNames = {CollabConstants.PLAN_DEPENDENCY};
        Map<String,String> modelNameId = ganttBusiness.getModelIdByName(modelNames);//key为modelName

        //planInfo任务信息
        List<GanttPlan> ganttPlans = ganttBusiness.getSubPlansCascade(bindData.getTableName(),bindData.getDataId());
        Map<String,Object> planInfo = new HashMap<>();
        planInfo.put("dataList", ganttPlans);//JSONArray.fromObject(ganttPlans).toString()
        extraData.put("planInfo",JSONObject.fromObject(planInfo).toString());

        //任务间关联信息   //连线暂时不考虑是否正确
        String dependencyModelId = modelNameId.get(CollabConstants.PLAN_DEPENDENCY);
        List<GanttPlanDependency> planDependencies = ganttBusiness.getGanttPlanDependencies(bindData.getTableName(),bindData.getDataId(),false);
        ModelDataRequest request = new ModelDataRequest();
        request.setModelId(dependencyModelId);
        List<Map> dataList = new ArrayList<>();
        planDependencies.forEach(planDependency->{
            Map<String,String> data = new HashMap();
            data.put("type",planDependency.getType());
            data.put("finishPlanId",planDependency.getFinishPlanId());
            data.put("startPlanId",planDependency.getStartPlanId());
            data.put("Id",planDependency.getId());
            //剩余的属性ignore?
            data.put("baseLineId",planDependency.getBaseLineId());
           // data.put("blngProjectId",planDependency.getBlngProjectId());
            dataList.add(data);
        });
        request.setDataList(dataList);
        modelDataRequestList.add(request);

        //人员分配信息
        GanttAssignmentData assign = ganttBusiness.getGanttAssignmentData(bindData.getTableName(),bindData.getDataId());
        Map<String,Object> resourceInfo = new HashMap<>();
        resourceInfo.put("dataList", assign.getResources());
        extraData.put("resourceInfo",JSONObject.fromObject(resourceInfo).toString());
    }

    /**
     * 重新构造数据任务的frontViewRequest
     * @param request
     * @param modelName
     * @param dataId
     * @return
     */
    public FrontViewRequest buildDataTaskFrontViewRequest(FrontViewRequest request, String modelName, String dataId) {
        ProjectTreeNode node = projectTreeBusiness.getParentTreeNodeInfo(modelName, dataId);
        String parentModelName = node.getModelName();
        String parentDataId = node.getDataId();

        Class cls = CollabConstants.PLAN.endsWith(parentModelName) ? Plan.class : Task.class;
        List<DataFlowActivity> activities = dataFlowBusiness.getDataFlowActivitys(parentDataId, cls);
        List<DataFlowTransition> dataFlowTransitions = dataFlowBusiness.getDataFlowTranstitions(parentDataId, cls);
        DataFlowInfo dataFlowInfo = new DataFlowInfo(dataFlowTransitions, activities);

        Map<String,Object> hisDataInfo = new HashMap<>();
        hisDataInfo.put("hisData",dataFlowInfo);
        Map<String,String> extraData = new HashMap<>();
        extraData.put("hisDataFlowInfo",JSONObject.fromObject(hisDataInfo).toString());

        request.setExtraData(extraData);
        return request;
    }

    /**
     * 重新构造计划历史信息的frontViewRequest
     * @param request
     * @param modelName
     * @param modelId
     * @param dataId
     * @return
     */
    public FrontViewRequest buildPlanTaskFrontViewRequest(FrontViewRequest request, String modelName, String modelId, String dataId) {
        //获取模型描述
        GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(modelId, null, null);
        GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getModelDescEvent);
        //获取模型数据
        Map modelData = modelDataBusiness.getModelDataByModelIdAndDataId(modelId, dataId);
        OrientModelDesc modelDesc = getModelDescEventParam.getOrientModelDesc();
        //添加模型相关信息
        List<ModelDataRequest> modelDataRequestList = new ArrayList<>();
        ModelDataRequest dataRequest = new ModelDataRequest();
        dataRequest.setModelId(modelDesc.getModelId());
        List<Map> dataList = new ArrayList<>();
        dataList.add(modelData);
        dataRequest.setDataList(dataList);
        modelDataRequestList.add(dataRequest);
        request.setModelDataRequestList(modelDataRequestList);

        //构造数据流程图信息
        buildDataFlowPanelInfo(request,modelName,modelId,dataId);

        return request;
    }

    private void buildDataFlowPanelInfo(FrontViewRequest request, String modelName, String modelId, String dataId) {
        boolean hasDataFlow = false;
        List<CollabFunction> functions = teamBusiness.getCurrentUserFunctions(modelName, dataId);
        if(functions.size()==0) {
            return;
        }
        for(CollabFunction function:functions) {
            if("数据流".equals(function.getName())){
                hasDataFlow = true;
                break;
            }
        }
        if(!hasDataFlow) {
            return;
        }

        Class cls = CollabConstants.PLAN.endsWith(modelName) ? Plan.class : Task.class;
        List<DataFlowActivity> activities = dataFlowBusiness.getDataFlowActivitys(dataId, cls);
        List<DataFlowTransition> dataFlowTransitions = dataFlowBusiness.getDataFlowTranstitions(dataId, cls);
        DataFlowInfo dataFlowInfo = new DataFlowInfo(dataFlowTransitions, activities);
        Map<String,Object> hisDataInfo = new HashMap<>();
        hisDataInfo.put("hisData",dataFlowInfo);
        Map<String,String> extraData = new HashMap<>();
        extraData.put("hisDataFlowInfo",JSONObject.fromObject(hisDataInfo).toString());
        request.setExtraData(extraData);
    }

    /**
     * 重新构造协同任务历史信息的frontViewRequest
     * @param request
     * @return
     */
    public WorkFlowFrontViewRequest buidCollabTaskFrontViewRequest(WorkFlowFrontViewRequest request, String modelName, String modelId, String dataId) {
        Map<String,String> extraData = new HashMap<>();

        List<CollabFunction> functions = teamBusiness.getCurrentUserFunctions(modelName, dataId);
        extraData.put("functionDescs",JSONArray.fromObject(functions).toString());

        CwmComponentModelEntity filter = new CwmComponentModelEntity();
        filter.setNodeId("");
       /* filter.setModelId(modelId);
        filter.setDataId(dataId);*/
        ExtGridData<CwmComponentModelEntityWrapper> retVal = componentBindBusiness.spcialList(null,null,filter);
        if(retVal.getResults().size()>0) {
            CwmComponentModelEntityWrapper component = retVal.getResults().get(0);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            extraData.put("componentBind",JSONObject.fromObject(component,jsonConfig).toString());
            //添加sysDataRequests
            request = buildSysDataRequests(request,component);
        }else{
            extraData.put("componentBind","null");
        }

        List<ModelDataRequest> modelDataRequestList = new ArrayList<>();
        ModelDataRequest modelDataRequest = new ModelDataRequest();
        modelDataRequest.setModelId(modelId);
        List<String> dataIds = new ArrayList<>();
        dataIds.add(dataId);
        modelDataRequest.setDataIds(dataIds);
        modelDataRequestList.add(modelDataRequest);

        request.setModelDataRequestList(modelDataRequestList);
        return request;
    }

    private WorkFlowFrontViewRequest buildSysDataRequests(WorkFlowFrontViewRequest request, CwmComponentModelEntityWrapper component) {
        CwmComponentEntity componentDesc = component.getBelongComponent();
        List<SysDataRequest> sysDataRequests = new ArrayList<>();

        SysDataRequest sysRequest = new SysDataRequest();
        List<Map<String, Object>> sysTableDataList = new ArrayList<>();
        sysRequest.setSysTableName("CWM_COMPONENT");
        Map<String,Object> map = new HashMap<>();
        map.put("id",componentDesc.getId());
        map.put("componentname",componentDesc.getComponentname());
        map.put("remark",componentDesc.getRemark());
        map.put("classname",componentDesc.getClassname());
        sysTableDataList.add(map);
        sysRequest.setSysTableDataList(sysTableDataList);

        sysDataRequests.add(sysRequest);
        request.setSysDataRequests(sysDataRequests);
        return request;
    }
}
