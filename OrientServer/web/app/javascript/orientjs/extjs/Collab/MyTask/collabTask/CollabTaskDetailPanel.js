/**
 * Created by Seraph on 16/8/11.
 */
Ext.define('OrientTdm.Collab.MyTask.collabTask.CollabTaskDetailPanel', {
    extend: 'OrientTdm.Collab.MyTask.plan.PlanDetailPanel',
    taskType: TDM_SERVER_CONFIG.COLLAB_TASK,
    alias: 'widget.collabTaskDetailPanel',
    iconCls:'icon-collabTask',
    config: {
        rootModelName: null,
        rootDataId: null,
        rootData: null,
        rootModelId: null,
        //历史任务描述
        hisTaskDetail: null,
        isHistory: false
    },
    initComponent: function () {
        var me = this;
        this.callParent(arguments);
    },
    doInit: function () {
        var me = this;

        var centerPanel = Ext.create("OrientTdm.Collab.MyTask.collabTask.CollabTaskCenterPanel", {
            region: 'center',
            padding: '0 0 0 5',
            rootModelName: me.rootModelName,
            rootDataId: me.rootDataId,
            rootModelId: me.rootModelId,
            rootData: me.rootData,
            hisTaskDetail: me.hisTaskDetail,
            isHistory: me.isHistory
        });

        Ext.apply(this, {
            id: 'collabTaskDetailPanel',
            tbar: Ext.bind(me.getTbar, me)(),
            layout: 'border',
            items: [centerPanel],
            westPanel: null,
            centerPanel: centerPanel
        });
    },
    getTbar: function () {
        var me = this;

        var btns = [];
        if (me.isHistory) {
            return btns;
        }

        if (me.rootData.groupTask) {
            btns.push({
                text: '接受任务',
                iconCls:'icon-takeTask',
                disabled: !me.rootData.groupTask,
                handler: Ext.bind(me.onAcceptTask, me)
            });
        }
        btns.push({
            text: '工作包分解',
            iconCls:'icon-taskwbs',
            disabled: me.rootData.groupTask,
            handler: Ext.bind(me.onPlanTaskBreak, me)
        });
        btns.push({
            text: '提交任务',
            iconCls:'icon-submitproject',
            disabled: me.rootData.groupTask,
            handler: Ext.bind(me.checkCanSubmit, me, [me.onSubmit], false)
        });



        return btns;
    },
    onAcceptTask: function () {
        var me = this;

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/flow/task/take.rdm', {flowTaskId: me.rootData.flowTaskId}, false, function (response) {
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
                me.rootData.groupTask = false;
            }
        });
    },
    checkCanSubmit: function (successCallBack) {
        //校驗是否可以提交任務
        //Rule 1 組件校验
        var me = this;
        var componentPanel = me.down('baseComponent');
        if (componentPanel) {
            componentPanel.validateComponent(successCallBack, me);
        } else {
            successCallBack.call(me);
        }
    }
});