/**
 * Created by Seraph on 16/8/3.
 */
Ext.define('OrientTdm.Collab.MyTask.auditTask.AuditTaskDetailPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    config: {
        taskInfo: null,
        isHistory: false,
        taskType: TDM_SERVER_CONFIG.AUDIT_TASK,
        submitPanel: null
    },
    iconCls: 'icon-auditTask',
    tabPosition: 'bottom',
    requires: [
        'OrientTdm.Collab.MyTask.auditTask.ModelDataAuditDetailPanel',
        'OrientTdm.Collab.MyTask.auditTask.Opinion.AuditTaskOpinionPanel',
        'OrientTdm.Collab.MyTask.auditTask.SubmitAuditFlowTaskPanel'
    ],
    initComponent: function () {
        var me = this;
        var tBar = me.getTbar();
        Ext.apply(me, {
            id: "auditTaskDetailPanel",
            dockedItems: [tBar]
        });
        this.callParent(arguments);

        me.initDefaultComponent();
    },
    getTbar: function () {
        var me = this;
        var btns = [];
        if (me.isHistory) {
            return null;
        }
        //提交任务
        btns.push({
            text: '提交任务',
            iconCls: 'icon-submitproject',
            handler: me._submitTask,
            scope: me
        });

        if (me.taskInfo.groupTask) {
            btns.push({
                text: '接受任务',
                iconCls: 'icon-takeTask',
                disabled: !me.taskInfo.groupTask,
                handler: Ext.bind(me.onAcceptTask, me)
            });
        }
        return {
            xtype: 'toolbar',
            items: btns
        };
    },
    initDefaultComponent: function () {
        var me = this;
        var params = {piId: me.taskInfo.piId};
        var toolbar = me.getDockedItems('toolbar[dock="top"]')[0];
        OrientExtUtil.AjaxHelper.doRequest("flow/info/bindDatas.rdm", params, false, function (response) {
            var retV = response.decodedData.results;
            if (me.taskInfo.auditType === 'WbsBaseLineAudit') {
                for (var i = 0; i < retV.length; i++) {
                    var bindData = retV[i];
                    if (bindData.subType == 'gantt') {
                        me.add(
                            Ext.create("OrientTdm.Collab.common.gantt.GanttPanel", {
                                title: '计划分解',
                                readOnly: true,
                                enableControl: false,
                                modelName: bindData.tableName,
                                dataId: bindData.dataId
                            })
                        );
                    } else {

                    }
                }
            } else if (me.taskInfo.auditType === 'ModelDataAudit') {
                //合并modelId
                me.add(Ext.create('OrientTdm.Collab.MyTask.auditTask.ModelDataAuditDetailPanel', {
                    bindDatas: retV,
                    piId: me.taskInfo.piId,
                    taskName: me.taskInfo.name,
                    iconCls: 'icon-basicInfo',
                    title: '待审批数据'
                }));
            }
            var flowDiagPanel = Ext.create("OrientTdm.Collab.common.auditFlow.MonitAuditFlowPanel", Ext.apply({
                title: '流程图',
                iconCls: 'icon-flow'
            }, {
                piId: me.taskInfo.piId
            }));
            me.add(flowDiagPanel);
            //审批意见信息
            var opinionPanel = Ext.create('OrientTdm.Collab.MyTask.auditTask.Opinion.AuditTaskOpinionPanel', {
                title: '审批意见',
                isHistory: me.isHistory,
                taskId: me.taskInfo.flowTaskId,
                piId: me.taskInfo.piId,
                pdId: me.taskInfo.pdId,
                taskName: me.taskInfo.name
            });
            me.add(opinionPanel);
            me.setActiveTab(0);
        });
    },
    onAcceptTask: function () {
        var me = this;

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/flow/task/take.rdm', {flowTaskId: me.taskInfo.flowTaskId}, false, function (response) {
            var respJson = response.decodedData;

            if (respJson.success) {
                var toolbar = me.getDockedItems('toolbar[dock="top"]');
                var btns = toolbar[0].items.items;
                for (var i = 0; i < btns.length; i++) {
                    if (btns[i].getText() === '接受任务') {
                        btns[i].setDisabled(true);
                        continue;
                    }
                    btns[i].setDisabled(false);
                }
                me.taskInfo.groupTask = false;
            }
        });
    },
    _submitTask: function () {
        var me = this;
        //判断是否有未填的意见信息
        var validateFlag = true;
        if (me.down('auditTaskOpinionForm')) {
            var auditTaskOpinionForm = me.down('auditTaskOpinionForm');
            var form = auditTaskOpinionForm.getForm();
            if (form.isValid() == false) {
                validateFlag = false;
                OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.opinionnotnull, function () {
                    me.setActiveTab(2)
                });
            }
        }
        if (validateFlag == true) {
            //提交任务
            var transitions = me._getOutTransitions();
            //if (transitions.length == 1) {
            //    //直接提交
            //    me._submitTaskByTranstions(transitions[0]);
            //} else {
            //弹出选择任务面板
            var submitPanel = Ext.create("OrientTdm.Collab.MyTask.auditTask.SubmitAuditFlowTaskPanel", {
                piId: me.taskInfo.piId,
                transitions: transitions
            });
            me.submitPanel = submitPanel;
            var buttons = [];
            Ext.each(transitions, function (transition) {
                buttons.push({
                    //text: '提交至-' + transition.text,
                    text: transition.text,
                    iconCls: "icon-workGroup",
                    handler: Ext.bind(me._submitTaskByTranstions, me, [transition], false),
                    scope: me,
                    transition: transition,
                    currentTaskName: me.taskInfo.name,
                    listeners: {
                        mouseover: me._highLightTransition,
                        mouseout: me._clearHighLightTransition,
                        scope: me
                    }
                });
            });
            OrientExtUtil.WindowHelper.createWindow(submitPanel, {
                title: '提交任务',
                buttons: buttons,
                buttonAlign: "center"
            });
        }
    },
    _getOutTransitions: function () {
        var me = this;
        var params = {flowTaskId: me.taskInfo.flowTaskId};
        var items = [];
        OrientExtUtil.AjaxHelper.doRequest("flow/info/nextFlowNodes.rdm", params, false, function (response) {
            var retV = Ext.decode(response.responseText).results;
            for (var i = 0; i < retV.length; i++) {
                var nextTaskInfo = retV[i];
                items.push({
                    text: retV[i].name,
                    nextTaskInfo: retV[i],
                    type: retV[i].type//用于判断节点类型
                });

            }
        });
        return items;
    },
    _submitTaskByTranstions: function (transition) {
        var me = this;
        //准备历史信息
        var piId = me.taskInfo.piId;
        var taskName = me.taskInfo.name;
        var taskId = me.taskInfo.flowTaskId;
        var taskType = me.taskType;
        var auditType = me.taskInfo.auditType;
        var opinions = [];
        if (me.down('auditTaskOpinionForm')) {
            var auditTaskOpinionForm = me.down('auditTaskOpinionForm');
            var form = auditTaskOpinionForm.getForm();
            var formValues = form.getFieldValues();
            for (var opinionName in formValues) {
                opinions.push({
                    flowid: me.taskInfo.piId,
                    flowTaskId: me.taskInfo.flowTaskId,
                    activity: me.taskInfo.name,
                    label: opinionName,
                    handlestatus: transition.nextTaskInfo.transition,
                    value: formValues[opinionName]
                });
            }
        }

        var nextTasksUserAssign = {};//nextTasksUserAssign[transition.text]
        var gridPanel = me.submitPanel.southPanelComponent;
        var gridData = [];
        var store = gridPanel.getStore();
        for(var i=0; i<store.getCount(); i++) {
            var record = store.getAt(i);
            gridData.push(record.data);
        }
        var retData = {};
        for (var i in gridData) {
            if (gridData[i].nodeName === transition.text && "end" !== transition.type && "end-error" !== transition.type) {//节点类型不为end
                if (gridData[i].realNames.indexOf(',') != -1) {
                    //用户组
                    nextTasksUserAssign[transition.text] = {
                        candidateUsers: gridData[i].realNames
                    };
                } else {
                    //单用户
                    nextTasksUserAssign[transition.text] = {
                        currentUser: gridData[i].realNames
                    };
                }
            }
        }

        OrientExtUtil.Common.confirm(OrientLocal.prompt.confirm, "您确定要提交该任务吗？", function (btn) {
            if (btn == 'yes') {
        var params = {
            flowTaskId: me.taskInfo.flowTaskId,
            transitionName: transition.nextTaskInfo.transition,
            opinions: opinions,
            nextTasksUserAssign: nextTasksUserAssign
        };
        OrientExtUtil.AjaxHelper.doRequest("auditFlow/control/commitTask.rdm", params, true, function (response) {
            var retV = response.decodedData;
            var success = retV.success;
            var params = {piId:piId,taskName:taskName,taskId:taskId,taskType:taskType,auditType:auditType};
            if (success) {
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/hisTask/saveHisAuditTaskInfo.rdm', params, true, function (resp) {
                    //保存历史任务信息
                }, true);
                if (me.submitWin) {
                    me.submitWin.close();
                }
                var myTaskDashboard = Ext.getCmp("myTaskDashboard");
                if (myTaskDashboard) {
                    var rootTab = myTaskDashboard.ownerCt;
                    rootTab.remove(me);
                    //刷新表格面板
                    var taskMainPanel = myTaskDashboard.down('taskMainPanel');
                    var taskListGridPanel = taskMainPanel.layout.activeItem;
                    var gridPanel = taskListGridPanel.down('orientGrid');
                    if (gridPanel) {
                        gridPanel.fireEvent('refreshGrid');
                    }
                } else {
                    me.ownerCt.remove(me);
                }
            }
        }, true);
            }
        });
    },
    _highLightTransition: function (btn) {
        var me = this;
        if (!me.submitWin) {
            me.submitWin = btn.up('window');
        }
        var currentTaskName = btn.currentTaskName;
        var endTaskName = btn.transition.nextTaskInfo.name;
        btn.up('window').down('submitAuditFlowTaskPanel')._highLightTransition(currentTaskName, endTaskName);
    },
    _clearHighLightTransition: function (btn) {
        btn.up('window').down('submitAuditFlowTaskPanel')._clearHighLightTransition();
    }
});