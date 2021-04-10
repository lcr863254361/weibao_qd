/**
 * Created by Seraph on 16/7/26.
 */
Ext.define('OrientTdm.Collab.MyTask.plan.PlanDetailPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.planDetailPanel',
    iconCls: 'icon-planTask',
    config: {
        rootModelName: null,
        rootDataId: null,
        rootData: null,
        rootModelId: null,
        isHistory: false,
        taskType: TDM_SERVER_CONFIG.PLAN_TASK,
        hisTaskDetail: null
    },
    requires: [
        'OrientTdm.Collab.MyTask.auditTask.SubmitAuditFlowTaskPanel'
    ],
    initComponent: function () {
        var me = this;
        if (null == me.hisTaskDetail) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/projectTree/nodeModelInfo.rdm', {modelName: me.rootModelName}, false, function (response) {
                me.rootModelId = response.decodedData.results.modelId;
            });
        }
        me.doInit();
        this.callParent(arguments);
    },
    _fetchData: function () {

    },
    doInit: function () {
        var me = this;
        var centerPanel = Ext.create("OrientTdm.Collab.MyTask.plan.PlanCenterPanel", {
            region: 'center',
            padding: '0 0 0 5',
            rootModelName: me.rootModelName,
            rootDataId: me.rootDataId,
            rootModelId: me.rootModelId,
            rootData: me.rootData,
            hisTaskDetail: me.hisTaskDetail
        });
        Ext.apply(this, {
            id: 'planDetailPanel',
            tbar: Ext.bind(me.getTbar, me)(),
            layout: 'border',
            items: [centerPanel],
            westPanel: null,
            centerPanel: centerPanel
        });
    },
    onSubmit: function () {
        var me = this;
        Ext.bind(me.doSubmit, me);
        if (me.rootModelName === 'CB_TASK') {//协同调用
            var params = {flowTaskId: me.rootData.flowTaskId};
            var items = [];
            OrientExtUtil.AjaxHelper.doRequest("flow/info/nextFlowNodes.rdm", params, false, function (response) {
                var retV = Ext.decode(response.responseText).results;
                for (var i = 0; i < retV.length; i++) {
                    var nextTaskInfo = retV[i];
                    items.push({
                        xtype: 'button',
                        text: retV[i].name,
                        name: me.rootData.flowTaskId,
                        nextTaskInfo: retV[i],
                        handler: Ext.bind(me.doSubmit, me, [nextTaskInfo.transition, true])
                    });

                }
            });
            if (items.length == 1) {
                me.doSubmit(items[0].nextTaskInfo.transition, true);
            } else {
                //弹出选择任务面板
                var submitPanel = Ext.create("OrientTdm.Collab.MyTask.auditTask.SubmitAuditFlowTaskPanel", {
                    piId: me.rootData.piId,
                    transitions: items
                });
                var buttons = [];
                Ext.each(items, function (transition) {
                    buttons.push({
                        text: '提交至-' + transition.text,
                        handler: Ext.bind(me.doSubmit, me, [transition.nextTaskInfo.transition, true], false),
                        scope: me,
                        transition: transition,
                        currentTaskName: me.rootData.name,
                        listeners: {
                            mouseover: me._highLightTransition,
                            mouseout: me._clearHighLightTransition,
                            scope: me
                        }
                    });
                });
                OrientExtUtil.WindowHelper.createWindow(submitPanel, {
                    title: '提交任务',
                    buttons: buttons
                });
            }
        } else {
            me.doSubmit();
        }
    },
    doSubmit: function (transition, isCollabTask) {//注意协同任务也调用这个方法,见onSubmit方法
        var me = this;
        var url = '/' + me.rootModelName.toLowerCase() + '/submit.rdm';

        var params = {
            dataId: me.rootDataId,
            transitionName: transition
        };
        me._makeSurePanelInited();

        ////临时确认参数用
        //var frontViewRequest = HisTaskHelper._prepareHisData(me, me.rootData.flowTaskId || me.rootDataId);
        ////计划用到的是me.rootDataId 参数名为taskID
        ////还用到me.taskType 参数名taskType
        ////对于计划frontViewRequest.piId为undefined
        //
        ////对于协同用到的是me.rootData.flowTaskId 参数为taskId
        ////me.taskType 参数taskType
        //frontViewRequest.piId = me.rootData.piId;

        OrientExtUtil.AjaxHelper.doRequest(serviceName + url, params, false, function (response) {
            var retV = Ext.decode(response.responseText);
            if (retV.success) {
                //保存历史任务信息
                //var frontViewRequest = HisTaskHelper._prepareHisData(me, me.rootData.flowTaskId || me.rootDataId);
                //frontViewRequest.piId = me.rootData.piId;//对于计划frontViewRequest.piId为undefined
                if (!isCollabTask) { //保存计划历史任务信息
                    var params = {
                        taskId: me.rootDataId,
                        taskType: me.taskType,
                        rootModelName: me.rootModelName,
                        rootDataId: me.rootDataId,
                        rootModelId: me.rootModelId
                    };
                    HisTaskHelper.saveHisAuditTaskInfo(serviceName + '/hisTask/saveHisPlanTaskInfo.rdm', params, null, me);
                } else {//保存协同历史任务信息
                    var params = {
                        taskId: me.rootData.flowTaskId,
                        taskType: me.taskType,
                        piId: me.rootData.piId,//这三个参数构造workFlowFrontViewRequest用
                        rootModelName: me.rootModelName,
                        rootDataId: me.rootDataId,
                        rootModelId: me.rootModelId
                    };
                    HisTaskHelper.saveHisAuditTaskInfo(serviceName + '/hisTask/saveHisCollabTaskInfo.rdm', params, null, me);
                }

                if (me.submitWin) {
                    me.submitWin.close();
                }
                var myTaskDashboard = Ext.getCmp("myTaskDashboard");
                var rootTab = myTaskDashboard.ownerCt;
                rootTab.remove(me);
                //刷新表格面板
                var taskMainPanel = myTaskDashboard.down('taskMainPanel');
                var taskListGridPanel = taskMainPanel.layout.activeItem;
                var gridPanel = taskListGridPanel.down('orientGrid');
                if (gridPanel) {
                    gridPanel.fireEvent('refreshGrid');
                }
            }
        });
        //判断是否所有面板都已经初始化
        //me._makeSurePanelInited();
        //var frontViewRequest = HisTaskHelper._prepareHisData(me, me.rootData.flowTaskId || me.rootDataId);
        ////补充流程信息
        //frontViewRequest.piId = me.rootData.piId;
        //HisTaskHelper.saveHisAuditTaskInfo(serviceName + '/hisTask/saveHisCollabTaskInfo.rdm', frontViewRequest, null, me);
    },
    _makeSurePanelInited: function () {
        //确保所有面板都已加载
        var me = this;
        OrientExtUtil.Common.mask('提交中，请稍后...');
        var contentPanel = me.items.getAt(0);
        contentPanel.items.each(function (item) {
            if (item.items.getCount() == 0 || !item.items.get(0).hasInited) {
                contentPanel.fireEvent('tabchange', contentPanel, item);
            }
        });
        OrientExtUtil.Common.unmask();
    },
    getTbar: function () {
        var me = this;

        if (me.isHistory) {
            return [];
        }
        var btns = [];
        if (TDM_SERVER_CONFIG['COLLAB_ENABLE_PLAN_BREAK']) {
            btns.push({
                text: '工作包分解',
                iconCls: 'icon-taskwbs',
                handler: Ext.bind(me.onPlanTaskBreak, me)
            });
        }
        btns.push({
            text: '提交任务',
            iconCls: 'icon-submitproject',
            handler: Ext.bind(me.onSubmit, me)
        });

        return btns;
    },
    onPlanTaskBreak: function () {
        var me = this;

        var myTaskDashboard = Ext.getCmp("myTaskDashboard");
        var rootTab = myTaskDashboard.ownerCt;

        var oldPanel = rootTab.queryById("planTaskBreak");
        if (!Ext.isEmpty(oldPanel)) {
            rootTab.remove(oldPanel);
        }

        var thePanel = Ext.create('OrientTdm.Collab.common.planTaskBreak.PlanTaskBreakMainPanel', {
            title: '工作包分解"' + me.rootData.name + '"',
            closable: true,
            rootModelName: me.rootModelName,
            rootDataId: me.rootDataId
        });
        rootTab.add(thePanel);
        rootTab.setActiveTab(thePanel);
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
