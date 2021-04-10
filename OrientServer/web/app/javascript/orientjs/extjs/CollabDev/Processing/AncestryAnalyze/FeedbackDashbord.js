Ext.define('OrientTdm.CollabDev.Processing.AncestryAnalyze.FeedbackDashbord', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    alias: 'widget.feedbackDashbord',
    requires: [
        //'OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel'
    ],
    config: {
        nodeId: '',
        version: '',
        bmDataId: '',
        type: null,
        fromNodeId: '',
        fromVersion: '',
        fromType: null,
        fromBmDataId: '',
        taskName: '',
        fromTaskName: ''
    },
    initComponent: function () {
        var me = this;
        var selfTab = Ext.create('OrientTdm.CollabDev.Processing.ResultSettings.ResultSettingsTabPanel', {
            title: me.taskName + "任务自身数据",
            nodeId: me.nodeId,
            nodeVersion: me.version,
            bmDataId: me.bmDataId,
            type: me.type
        });
        selfTab.restructureTabs(me.nodeId, me.version, me.type, me.bmDataId);

        var fromTab = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel', {
            title: me.fromTaskName + "任务反馈数据",
            nodeId: me.fromNodeId,
            nodeVersion: me.fromVersion,
            nodeType: me.fromType,
            isGlobal: 1,
            isShowLeftBar: false,
            showOtherFunctionButtonType: 2,
            canCelldblclick: false
        });

        Ext.apply(me, {
            title: "下游任务反馈",
            closable: true,
            items: [selfTab, fromTab]
        });
        me.callParent(arguments);
    }
});