/**
 * 任务看板
 */
Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Task.TaskViewDashboard', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.taskViewDashboard',
    config: {},
    initComponent: function () {
        var me = this;

        var baseInfoForm = Ext.create('OrientTdm.CollabDev.Processing.Common.NodeBaseInfoPanel', {
            region: 'north',
            modelName: TDM_SERVER_CONFIG.TASK,
            templateName: TDM_SERVER_CONFIG.TPL_VIEWBOARD_TASK,
            dataId: me.bmDataId,
            height: 200
        });

        var knowledgeCommendationForm = Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Task.TaskKnowledgeForm', {
            title: '知识推荐',
            region: 'center'
        });

        Ext.apply(me, {
            autoScroll: true,
            layout: 'border',
            items: [baseInfoForm, knowledgeCommendationForm]
        });

        me.callParent(arguments);
    },
    refresh: function () {
        var _this = this;
        //刷新基础信息面板
        var nodeBaseInfoPanel = _this.down('nodeBaseInfoPanel');
        nodeBaseInfoPanel.dataId = _this.bmDataId;
        nodeBaseInfoPanel.restructurePanel();
    }
});