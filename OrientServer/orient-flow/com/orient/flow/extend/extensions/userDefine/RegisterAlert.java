package com.orient.flow.extend.extensions.userDefine;

import com.orient.alarm.model.*;
import com.orient.alarm.rule.NormalAlarmRule;
import com.orient.alarm.schedule.AlarmRule;
import com.orient.alarm.schedule.notice.NoticeUtil;
import com.orient.alarm.service.AlarmService;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.config.FlowType;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.orient.workflow.cmd.GetFormReaderCommand;
import com.orient.workflow.ext.identity.UserImpl;
import com.orient.workflow.form.model.AlertInfo;
import com.orient.workflow.form.model.XmlForm;
import com.orient.workflow.form.model.XmlFormReader;
import net.sf.json.JSONObject;
import org.hibernate.Hibernate;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.SwimlaneImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.BeansException;

import java.sql.SQLException;
import java.util.*;

@FlowTaskExecutionEventMarker(exceptTasks = {FlowTaskExecutionEventListener.FLOW_END, FlowTaskExecutionEventListener.FLOW_END_ERROR}, flowTypes = FlowType.Collab)
public class RegisterAlert implements FlowTaskExecutionEventListener {


    public static String TASK_ALERT = "TASK_ALERT_";
    public static String FLOW_ALERT = "FLOW_ALERT_";
    public static String ALARM_INFO_ID = "ALARM_INFO_ID";


    public static class Variable {
        private boolean isTask = true;
        private XmlFormReader xmlReader = null;
        private List<AlertInfo> alertInfos = UtilFactory.newArrayList();
        private ExecutionImpl execution;
        private TaskImpl task;
    }


    public void process() {
        initXmlFormReader();
        Variable v = tl.get();
        if (v.isTask) {
            v.alertInfos = getTaskAlertInfos();
        } else {
            v.alertInfos = getFlowAlertInfos();
        }
        List<String> alarmInfoIds = registerAlarmInfos();
        saveAlarmVariable(alarmInfoIds);
    }

    private void saveAlarmVariable(List<String> alarmInfoIds) {
        Map<String, List<String>> formData = UtilFactory.newHashMap();
        formData.put(ALARM_INFO_ID, alarmInfoIds);

        Variable v = tl.get();
        if (v.isTask) {
            v.execution.createVariable(TASK_ALERT + v.task.getName(), JSONObject.fromObject(formData).toString(), "string", true);
        } else {
            v.execution.createVariable(FLOW_ALERT + v.execution.getProcessInstance().getId(), JSONObject.fromObject(formData).toString(), "string", true);
        }
    }

    private List<String> registerAlarmInfos() {
        List<String> alarmInfoIds = UtilFactory.newArrayList();
        Variable v = tl.get();

        AlarmService alarmSvc = (AlarmService) OrientContextLoaderListener.Appwac.getBean("AlarmService");

        for (AlertInfo alertInfo : v.alertInfos) {

            try {
                AlarmInfo alarmInfo = alertInfo.getAlarmInfo().copyAlarm(); //修改缓存问题
                //add params
                String startTime = CommonTools.getSysdate();
                String initParam = alarmInfo.getParams();

                alarmInfo.setParams(initParam + NormalAlarmRule.ALARM_PARAM_SPLIT + startTime
                        + NormalAlarmRule.ALARM_PARAM_SPLIT + v.execution.getId() + NormalAlarmRule.ALARM_PARAM_SPLIT + v.task.getId());

                AlarmRule alarmRule = (AlarmRule) OrientContextLoaderListener.Appwac.getBean(alarmInfo.getClassname());
                AlarmContent alarmContent = alarmInfo.getAlarmContent();
                alarmContent.setContent(Hibernate.createClob(alarmRule.
                        getFormatContent(NoticeUtil.getContent(alarmContent.getContent().getCharacterStream()),
                                alarmInfo.getParams() + NormalAlarmRule.ALARM_PARAM_SPLIT + v.task.getName())));
                syncSender(alarmInfo);
                String alarmInfoId = alarmSvc.register(alarmInfo);
                alarmInfoIds.add(alarmInfoId);
            } catch (BeansException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return alarmInfoIds;
    }

    private List<AlertInfo> getTaskAlertInfos() {
        Variable v = tl.get();

        XmlForm taskForm = v.xmlReader.getFormByName(v.task.getName());
        // 派发子流程时，taskName是主流程的名称，taskForm可能为空，alertInfos在初始化时构造并加入判断（倪昱2015.5.4)
        if (taskForm != null) {
            v.alertInfos = taskForm.getAlertInfos();
        }
        return v.alertInfos;
    }

    private List<AlertInfo> getFlowAlertInfos() {
        Variable v = tl.get();
        v.alertInfos = v.xmlReader.getAlertInfos();
        return v.alertInfos;
    }

    private void initXmlFormReader() {
        Variable v = tl.get();
        String pdId = v.execution.getProcessDefinitionId();
        //初始化xmlReader
        ProcessEngine processEngine = (ProcessEngine) OrientContextLoaderListener.Appwac.getBean("processEngine");
        v.xmlReader = processEngine.execute(new GetFormReaderCommand(pdId));

    }

    private void syncSender(AlarmInfo alarmInfo) {
        Variable v = tl.get();

        String candidateUsers = "";
        String candidateGroups = "";

        if (v.task.getAssignee() == null) {

            Set<ParticipationImpl> participations = v.task.getAllParticipants();
            for (Iterator<ParticipationImpl> iter = participations.iterator(); iter.hasNext(); ) {

                ParticipationImpl participation = iter.next();

                if (participation.getSwimlane() != null) {
                    SwimlaneImpl swimlane = participation.getSwimlane();
                    if (swimlane.getAssignee() != null) {
                        candidateUsers = swimlane.getAssignee() + ",";
                        break;
                    }

                } else if (participation.getUserId() != null) {
                    candidateUsers += participation.getUserId() + ",";
                } else if (participation.getGroupId() != null) {
                    candidateGroups += participation.getGroupId() + ",";
                }

            }

            if (!"".equals(candidateUsers)) {
                modifySender(alarmInfo, CommonTools.Obj2String(candidateUsers.substring(0, candidateUsers.length() - 1)));
            }

            if (!"".equals(candidateGroups)) {

                IdentitySession identitySession = EnvironmentImpl.getCurrent().get(IdentitySession.class);

                StringTokenizer tokenizer = new StringTokenizer(candidateGroups, ",");
                while (tokenizer.hasMoreTokens()) {
                    String candidateGroup = tokenizer.nextToken().trim();
                    List<User> users = identitySession.findUsersByGroup(candidateGroup);
                    for (User user : users) {
                        candidateUsers += ((UserImpl) user).getId() + ",";
                    }
                }

                modifySender(alarmInfo, CommonTools.Obj2String(candidateUsers.substring(0, candidateUsers.length() - 1)));

            }

        } else {
            modifySender(alarmInfo, v.task.getAssignee());
        }

    }


    @SuppressWarnings("unchecked")
    private void modifySender(AlarmInfo alarmInfo, String users) {

        AlarmUserDAO alarmUserDAO = (AlarmUserDAO) OrientContextLoaderListener.Appwac.getBean("AlarmUserDAO");

        Map<AlarmUser, AlarmUserRelation> alarmUserMap = alarmInfo.getAlarmUserMap();

        for (Iterator<AlarmUser> iter = alarmUserMap.keySet().iterator(); iter.hasNext(); ) {

            AlarmUser alarmUser = iter.next();
            AlarmUserRelation alarmUserRelation = alarmUserMap.get(alarmUser);

            if (AlarmUserRelation.TO_SEND_USER.equals(alarmUserRelation.getType())) {
                iter.remove();
            }

        }

        List<AlarmUser> sendUsers = alarmUserDAO.getAlarmUserByNames(users);
        for (AlarmUser alarmUser : sendUsers) {
            alarmUserMap.put(alarmUser, new AlarmUserRelation(AlarmUserRelation.TO_SEND_USER));
        }

    }

    private ThreadLocal<Variable> tl = new ThreadLocal();

    @Override
    public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {
        Variable v = new Variable();
        v.execution = execution;
        v.task = task;
        if (task != null) {
            v.isTask = true;
        }

        tl.set(v);
//		process();
    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {

    }
}
