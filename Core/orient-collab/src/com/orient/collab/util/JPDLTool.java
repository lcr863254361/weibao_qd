package com.orient.collab.util;

import com.orient.collab.config.CollabConstants;
import com.orient.flow.extend.activity.CounterSignActivity;
import com.orient.jpdl.model.*;
import com.orient.jpdl.model.Process;
import com.orient.utils.CommonTools;
import com.orient.workflow.bean.AssignUser;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * jpdl tool used to create or update jpdl content from collab task
 *
 * @author Seraph
 *         2016-08-09 下午5:00
 */
public class JPDLTool {

    public static final String MAIN_FLOW = "main";
    static public JPDLTool getInstance(){
        return new JPDLTool();
    }

    private JPDLTool(){

    }

    public String createJpdlContentFromCollabTasks(List<com.orient.collab.model.Task> collabTasks, String processName, String type) {

        //创建流程对象
        Process process = new Process();
        process.setName(processName);
        process.setCategory(type);

        ProcessChoice pc = new ProcessChoice();
        process.setProcessChoice(pc);
        ProcessChoiceItem[] pcItemArr = new ProcessChoiceItem[collabTasks.size()+2];
        ProcessChoiceItem pcItem = new ProcessChoiceItem();
        ActivityGroup group = new ActivityGroup();
        //开始节点
        int x = 100;
        int y = 100;
        Start start = new Start();
        start.setName("开始1");
        start.setG(x+25 + "," + y +",92,52");
        group.setStart(start);
        pcItem.setActivityGroup(group);
        pcItemArr[0]=pcItem;
        for(int i=0;i<collabTasks.size();i++){
            //循环遍历
            com.orient.collab.model.Task collabTask = collabTasks.get(i);
            y = y + 100;

            if(CollabConstants.TASK_TYPE_COUNTERSIGN.equals(collabTask.getType())){

                Custom custom = getCumstom(collabTask, x, y);
                group = new ActivityGroup();
                group.setCustom(custom);
            }else{
                //普通任务
                Task task = new Task();
                task.setG(x + "," +y + ",92,52");
                task.setName(collabTask.getName());
                String assignee = CommonTools.Obj2String(collabTask.getPrincipal());
                if(assignee.contains(AssignUser.DELIMITER)){
                    task.setCandidateUsers(assignee);
                }else{
                    task.setAssignee(assignee);
                }
                group = new ActivityGroup();
                group.setTask(task);
            }
            pcItem = new ProcessChoiceItem();
            pcItem.setActivityGroup(group);
            pcItemArr[1+i]=pcItem;

        }
        y = y + 100;
        //结束节点
        End end = new End();
        end.setName("结束1");
        end.setG(x+25 + "," + y +",92,52");
        pcItem = new ProcessChoiceItem();
        group = new ActivityGroup();
        group.setEnd(end);
        //组装
        pcItem.setActivityGroup(group);
        pcItemArr[1+collabTasks.size()]=pcItem;
        pc.setProcessChoiceItem(pcItemArr);
        StringWriter sw = new StringWriter();
        try {
            process.marshal(sw);
            sw.close();
        } catch (MarshalException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public String updateJpdlContentWithCollabTasks(Process process, List<com.orient.collab.model.Task> collabTasks){

        Process realProcess = new Process();
        realProcess.setName(process.getName());
        realProcess.setCategory(process.getCategory());

        //遍历数据库取出来的jpdl流程
        ProcessChoice oriProcessChoice = process.getProcessChoice();
        ProcessChoiceItem[] oriItems = oriProcessChoice.getProcessChoiceItem();
        //过滤出分支 以及 合并
        List<ProcessChoiceItem> otherItems = new ArrayList<ProcessChoiceItem>();
        for(ProcessChoiceItem item : oriItems){
            ActivityGroup activityGroup = item.getActivityGroup();
            Object choiceValue = activityGroup.getChoiceValue();
            if(choiceValue instanceof Fork
                    || choiceValue instanceof Join
                    || choiceValue instanceof Decision
                    || choiceValue instanceof EndCancel
                    || choiceValue instanceof EndError){
                otherItems.add(item);
            }
        }
        ProcessChoice pc = new ProcessChoice();
        ProcessChoiceItem[] pcItemArr = new ProcessChoiceItem[collabTasks.size()+2+otherItems.size()];
        //装配 开始 以及 结束节点
        for(ProcessChoiceItem item : oriItems){
            ActivityGroup activityGroup = item.getActivityGroup();
            Object choiceValue = activityGroup.getChoiceValue();
            if(choiceValue instanceof Start) {
                pcItemArr[0] = item;
            }else if(choiceValue instanceof End) {
                pcItemArr[1+collabTasks.size()] = item;
            }
        }
        //装配特殊节点
        for(int i=0;i<otherItems.size();i++){
            ProcessChoiceItem item = otherItems.get(i);
            pcItemArr[2+collabTasks.size()+i] = item;
        }
        syncNode(collabTasks,oriItems,pcItemArr);
        pc.setProcessChoiceItem(pcItemArr);
        realProcess.setProcessChoice(pc);

        StringWriter sw = new StringWriter();
        //返回结果
        String retStr = "";
        try {
            realProcess.marshal(sw);
            sw.close();
            retStr = sw.toString();
        } catch (MarshalException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    private void syncNode(List<com.orient.collab.model.Task> collabTasks, ProcessChoiceItem[] oriItems, ProcessChoiceItem[] pcItemArr) {

        //装配 任务
        int i = 0;
        for(com.orient.collab.model.Task collabTask : collabTasks) {
            //任务名称
            String taskName = collabTask.getName();
            boolean choosen = false;
            //遍历已经存在的节点信息
            for(ProcessChoiceItem item : oriItems) {
                ActivityGroup activityGroup = item.getActivityGroup();
                Object choiceValue = activityGroup.getChoiceValue();

                if(choiceValue instanceof Task) {
                    Task oriTask = (Task)choiceValue;
                    if(oriTask.getName().equals(taskName)) {
                        choosen = true;
                        ProcessChoiceItem tempPcItem = new ProcessChoiceItem();
                        ActivityGroup tempGroup = new ActivityGroup();
                        String assignee = CommonTools.Obj2String(collabTask.getPrincipal());
                        oriTask.setAssignee(assignee);
                        tempGroup.setTask(oriTask);

                        tempPcItem.setActivityGroup(tempGroup);
                        pcItemArr[1+i] = tempPcItem;
                        break;
                    }
                } else if(choiceValue instanceof Custom){
                    Custom custom = (Custom) choiceValue;
                    if(custom.getName().equals(taskName)) {
                        choosen = true;

                        ProcessChoiceItem tempPcItem = new ProcessChoiceItem();
                        ActivityGroup tempGroup = new ActivityGroup();
                        String assignee = CommonTools.Obj2String(collabTask.getPrincipal());
                        WireObjectTypeItem[] wireObjectTypeItems = custom.getWireObjectTypeItem();

                        for(WireObjectTypeItem wireObjectTypeItem : wireObjectTypeItems){
                            if(wireObjectTypeItem.getField().getName().equals("counterSignUsers")){
                                com.orient.jpdl.model.String counterSignUsersString = new com.orient.jpdl.model.String();
                                counterSignUsersString.setValue("1::::" + assignee);
                                wireObjectTypeItem.getField().getWireObjectGroup().setString(counterSignUsersString);
                                break;
                            }
                        }
                        tempGroup.setCustom(custom);
                        tempPcItem.setActivityGroup(tempGroup);
                        pcItemArr[1+i] = tempPcItem;
                        break;
                    }
                }
            }

            if(!choosen) {
                Random rand = new Random();
                int x = rand.nextInt(500);
                int y = rand.nextInt(500);
                //如果没有找到 则新增

                ProcessChoiceItem tempPcItem = new ProcessChoiceItem();
                ActivityGroup tempGroup = new ActivityGroup();
                tempPcItem.setActivityGroup(tempGroup);

                if(CollabConstants.TASK_TYPE_COUNTERSIGN.equals(collabTask.getType())){
                    Custom custom = getCumstom(collabTask, x, y);
                    tempGroup.setCustom(custom);
                }else{
                    //普通任务
                    Task task = new Task();
                    task.setG(x + "," +y + ",92,52");
                    task.setName(collabTask.getName());
                    String assignee = CommonTools.Obj2String(collabTask.getPrincipal());
                    if(assignee.contains(AssignUser.DELIMITER)){
                        task.setCandidateUsers(assignee);
                    }else{
                        task.setAssignee(assignee);
                    }
                    tempGroup.setTask(task);
                }

                pcItemArr[1+i]=tempPcItem;
            }
            i++;
        }
    }

    private Custom getCumstom(com.orient.collab.model.Task collabTask, int x, int y){
        Custom custom = new Custom();
        custom.setName(collabTask.getName());
        custom.setG(x + "," +y + ",92,52");
        custom.setClazz(CounterSignActivity.class.getName());

        WireObjectTypeItem counterSignUsersWire = new WireObjectTypeItem();
        Field counterSignUsersField = new Field();
        counterSignUsersField.setName("counterSignUsers");
        WireObjectGroup counterSignUsersWireObjectGroup = new WireObjectGroup();

        com.orient.jpdl.model.String counterSignUsersString = new com.orient.jpdl.model.String();
        counterSignUsersString.setValue("1::::" + collabTask.getPrincipal());
        counterSignUsersWireObjectGroup.setString(counterSignUsersString);
        counterSignUsersField.setWireObjectGroup(counterSignUsersWireObjectGroup);
        counterSignUsersWire.setField(counterSignUsersField);
        custom.addWireObjectTypeItem(counterSignUsersWire);

        WireObjectTypeItem strategyWire = new WireObjectTypeItem();
        Field strategyField = new Field();
        strategyField.setName("strategy");
        WireObjectGroup strategyWireObjectGroup = new WireObjectGroup();

        com.orient.jpdl.model.String strategyString = new com.orient.jpdl.model.String();
        strategyString.setValue("100%");
        strategyWireObjectGroup.setString(strategyString);
        strategyField.setWireObjectGroup(strategyWireObjectGroup);
        strategyWire.setField(strategyField);
        custom.addWireObjectTypeItem(strategyWire);

        WireObjectTypeItem passTrainWriter = new WireObjectTypeItem();
        Field passTrainField = new Field();
        passTrainField.setName("passTransiton");
        WireObjectGroup passTrainWireObjectGroup = new WireObjectGroup();
        com.orient.jpdl.model.String passTrainString = new com.orient.jpdl.model.String();
        passTrainString.setValue("");
        passTrainWireObjectGroup.setString(passTrainString);
        passTrainField.setWireObjectGroup(passTrainWireObjectGroup);
        passTrainWriter.setField(passTrainField);
        custom.addWireObjectTypeItem(passTrainWriter);

        WireObjectTypeItem noPassTransitonWriter = new WireObjectTypeItem();
        Field noPassTransitonField = new Field();
        noPassTransitonField.setName("noPassTransiton");
        WireObjectGroup noPassTransitonWireObjectGroup = new WireObjectGroup();
        com.orient.jpdl.model.String noPassTransitonString = new com.orient.jpdl.model.String();
        noPassTransitonString.setValue("");
        noPassTransitonWireObjectGroup.setString(noPassTransitonString);
        noPassTransitonField.setWireObjectGroup(noPassTransitonWireObjectGroup);
        noPassTransitonWriter.setField(noPassTransitonField);
        custom.addWireObjectTypeItem(noPassTransitonWriter);

        return custom;
    }

}