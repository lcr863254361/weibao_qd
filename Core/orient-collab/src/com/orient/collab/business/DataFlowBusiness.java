package com.orient.collab.business;

import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.*;
import com.orient.collab.util.JobCarvePosUtil;
import com.orient.sysmodel.domain.collab.CollabDataFlow;
import com.orient.sysmodel.domain.collab.CollabJobCarvePos;
import com.orient.sysmodel.service.collab.impl.CollabDataFlowService;
import com.orient.sysmodel.service.collab.impl.CollabJobCarvePosService;
import com.orient.web.base.BaseBusiness;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/22.
 * Purpose:
 * Detail:
 */
@Service
public class DataFlowBusiness extends BaseBusiness {


    @Autowired
    ProjectTreeBusiness projectTreeBusiness;
    @Autowired
    CollabDataFlowService collabDataFlowService;
    @Autowired
    CollabJobCarvePosService collabJobCarvePosService;

    /**
     * 获取该节点所管理的所有数据流的连线
     *
     * @param dataId 是父节点的ID
     * @param clz    是Plan 还是 Task
     * @return
     */
    public List<DataFlowTransition> getDataFlowTranstitions(String dataId, Class clz) {


        List<DataFlowTransition> ret = new ArrayList<>();

        List<ProjectTreeNode> nextLayerTaskNodes = null;
        if (clz.equals(Plan.class)) {
            nextLayerTaskNodes = projectTreeBusiness.getNextLayerNodes(CollabConstants.FUNC_MODULE_PLAN_MNG, CollabConstants.PLAN, dataId);
        } else {
            nextLayerTaskNodes = projectTreeBusiness.getNextLayerNodes(CollabConstants.FUNC_MODULE_PLAN_MNG, CollabConstants.TASK, dataId);
        }
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);


        for (ProjectTreeNode node : nextLayerTaskNodes) {

            StatefulModel statefulModel = orientSqlEngine.getTypeMappingBmService().getById(Task.class, node.getDataId());

            if (statefulModel == null || !taskModelId.equals(node.getModelId())) {
                continue;
            }
            CollabDataFlow flowVector = new CollabDataFlow();
            flowVector.setModelid(Long.valueOf(taskModelId));
            flowVector.setSrcid(Long.valueOf(node.getDataId()));
            List<CollabDataFlow>  vetorList = collabDataFlowService.listBeansByExample(flowVector);
            for (CollabDataFlow vetor: vetorList){
                DataFlowTransition dataFlowTransition = new DataFlowTransition();
                try {
                    PropertyUtils.copyProperties(dataFlowTransition,vetor);
                }
                catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
                ret.add(dataFlowTransition);
            }
        }

        return ret;


    }

    /**
     * 获取该节点所管理的所有子节点的位置,如果没有存储位置,则创建初始化位置,并保存数据库
     *
     * @param dataId
     * @param clz
     * @return
     */
    public List<DataFlowActivity> getDataFlowActivitys(String dataId, Class clz) {

        List<DataFlowActivity> retList = new ArrayList<>();
        List<DataFlowActivity> createDataFlowActivity = new ArrayList<>();

        List<ProjectTreeNode> nextLayerTaskNodes = null;
        if (clz.equals(Plan.class)) {
            nextLayerTaskNodes = projectTreeBusiness.getNextLayerNodes(CollabConstants.FUNC_MODULE_PLAN_MNG, CollabConstants.PLAN, dataId);
        } else {
            nextLayerTaskNodes = projectTreeBusiness.getNextLayerNodes(CollabConstants.FUNC_MODULE_PLAN_MNG, CollabConstants.TASK, dataId);
        }
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);

        for (ProjectTreeNode node : nextLayerTaskNodes) {


            StatefulModel statefulModel = orientSqlEngine.getTypeMappingBmService().getById(Task.class, node.getDataId());

            if (statefulModel == null || !taskModelId.equals(node.getModelId())) {
                continue;
            }
            CollabJobCarvePos jobPos = new CollabJobCarvePos();
            jobPos.setModelid(Long.valueOf(taskModelId));
            jobPos.setDataid(Long.valueOf(statefulModel.getId()));

            List<CollabJobCarvePos> posList = collabJobCarvePosService.listBeansByExample(jobPos);
            if (posList.size() == 0) {
                DataFlowActivity dataFlowActivity = new DataFlowActivity();
                dataFlowActivity.setDispalyName(node.getText());
                dataFlowActivity.setDataId(node.getDataId());
                dataFlowActivity.setModelId(taskModelId);
                createDataFlowActivity.add(dataFlowActivity);
            } else {
                jobPos = posList.get(0);
                DataFlowActivity dataFlowActivity = new DataFlowActivity();
                dataFlowActivity.setDispalyName(node.getText());
                dataFlowActivity.setPos(jobPos);
                dataFlowActivity.setId(String.valueOf(jobPos.getId()));
                retList.add(dataFlowActivity);
            }
        }

        //保存数据库
        JobCarvePosUtil.setDefaultCarvePostion(createDataFlowActivity);
        for (DataFlowActivity activity : createDataFlowActivity) {
            CollabJobCarvePos jobPos = activity.conStructJobCarvePos();
            collabJobCarvePosService.save(jobPos);
            activity.setId(String.valueOf(jobPos.getId()));
        }
        retList.addAll(createDataFlowActivity);
        return retList;

    }


    /**
     */
    public void saveDataFlow(DataFlowSyncBean dataFlowInfo) {
        dataFlowInfo.getToSaveData().getTransitions().forEach(dataFlowTransition -> {
            dataFlowTransition.setModelid(Long.valueOf(dataFlowInfo.getToSaveData().getActivities().get(0).getModelId()));
        });
        saveDataFlow(dataFlowInfo.getToSaveData().getActivities(), dataFlowInfo.getToSaveData().getTransitions());
        //删除移除的线
        if (null != dataFlowInfo.getToRemoveEdgeIds() && dataFlowInfo.getToRemoveEdgeIds().length > 0) {
            collabDataFlowService.delete(dataFlowInfo.getToRemoveEdgeIds());
        }
    }

    /**
     * 更新节点的位置,更新连线(没有id的是新增的,有id的是更新)
     *
     * @param activities
     * @param transitions
     * @return
     */
    public boolean saveDataFlow(List<DataFlowActivity> activities, List<DataFlowTransition> transitions) {

        // 更新节点的位置
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        for (DataFlowActivity activity : activities) {
            CollabJobCarvePos newPosInfo = activity.conStructJobCarvePos();
            collabJobCarvePosService.update(newPosInfo);
        }
        //更新节点的连线
        for (DataFlowTransition transition : transitions) {
            if (transition.getId() == null) {
                CollabDataFlow newDataFlow = new CollabDataFlow();
                try {
                    PropertyUtils.copyProperties(newDataFlow, transition);
                    collabDataFlowService.save(newDataFlow);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

            } else {
                CollabDataFlow newDataFlow = collabDataFlowService.getById(transition.getId());
                try {
                    PropertyUtils.copyProperties(newDataFlow, transition);
                    collabDataFlowService.update(newDataFlow);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }

        return true;

    }


    /**
     * 删除连线
     *
     * @param transitionId
     * @return
     */
    public boolean deleteDataFlowTransition(String transitionId) {

        CollabJobCarvePos posInfo = collabJobCarvePosService.getById(transitionId);
        if (posInfo != null) {
            collabJobCarvePosService.delete(posInfo);
        }
        return true;
    }

    /**
     * 删除任务节点的位置信息和其相关的连线
     *
     * @param dataId task表的DataId
     * @return
     */
    public boolean deleteDataFlowActivity(String dataId) {

        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        CollabJobCarvePos jobPos = new CollabJobCarvePos();
        jobPos.setModelid(Long.valueOf(taskModelId));
        jobPos.setDataid(Long.valueOf(dataId));
        List<CollabJobCarvePos> posList = collabJobCarvePosService.listBeansByExample(jobPos);
        for (CollabJobCarvePos posInfo : posList) {

            collabJobCarvePosService.delete(posInfo);

            CollabDataFlow fromFlow = new CollabDataFlow();
            fromFlow.setModelid(Long.valueOf(taskModelId));
            fromFlow.setSrcid(Long.valueOf(dataId));
            List<CollabDataFlow> fromList = collabDataFlowService.listBeansByExample(fromFlow);

            for (CollabDataFlow vetor : fromList) {
                collabDataFlowService.delete(vetor);
            }
            CollabDataFlow destFlow = new CollabDataFlow();
            destFlow.setModelid(Long.valueOf(taskModelId));
            destFlow.setDestnyid(Long.valueOf(dataId));
            List<CollabDataFlow> destList = collabDataFlowService.listBeansByExample(destFlow);
            for (CollabDataFlow vetor : destList) {
                collabDataFlowService.delete(vetor);
            }

        }

        return true;
    }


    /**
     * 获取当前任务的后继Task列表
     *
     * @param dataId
     * @return
     */
    public List<Task> getNextTasks(String dataId) {

        List<Task> retTasklist = new ArrayList<>();
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        CollabDataFlow fromFlow = new CollabDataFlow();
        fromFlow.setModelid(Long.valueOf(taskModelId));
        fromFlow.setSrcid(Long.valueOf(dataId));
        List<CollabDataFlow> fromList = collabDataFlowService.listBeansByExample(fromFlow);
        for (CollabDataFlow vetor : fromList) {
            Task task = orientSqlEngine.getTypeMappingBmService().getById(Task.class, String.valueOf(vetor.getDestnyid()));
            if (task != null) {
                retTasklist.add(task);
            }

        }

        return retTasklist;

    }

    /**
     * 获取当前任务的前驱Task列表
     *
     * @param dataId
     * @return
     */
    public List<Task> getPreTasks(String dataId) {

        List<Task> retTasklist = new ArrayList<>();
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        CollabDataFlow destFlow = new CollabDataFlow();
        destFlow.setModelid(Long.valueOf(taskModelId));
        destFlow.setDestnyid(Long.valueOf(dataId));
        List<CollabDataFlow> destList = collabDataFlowService.listBeansByExample(destFlow);
        for (CollabDataFlow vetor : destList) {
            Task task = orientSqlEngine.getTypeMappingBmService().getById(Task.class, String.valueOf(vetor.getSrcid()));
            if (task != null) {
                retTasklist.add(task);
            }

        }
        return retTasklist;
    }


}
