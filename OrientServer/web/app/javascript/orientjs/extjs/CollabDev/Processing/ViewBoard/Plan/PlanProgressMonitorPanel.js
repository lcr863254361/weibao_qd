Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Plan.PlanProgressMonitorPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.planProgressMonitorPanel',
    config: {
        nodeId: null
    },
    requires: [
        'OrientTdm.CollabDev.Common.Content.Pedigree.PedigreeComponent',
        'OrientTdm.CollabDev.Processing.ViewBoard.Plan.TaskTechStatusTipPanel'
    ],
    initComponent: function () {
        var me = this;

        /**
         * 计划版本滚动面板
         *
         */
        var leftPlanVersionPanel = Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Plan.LeftPlanVersionPanel', {
            region: 'west',
            width: 300,
            nodeId: me.nodeId
        });

        /**
         * 任务谱系面板
         */
        var taskPedigreeRelationPanel = Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Plan.TaskPedigreeRelationPanel', {
            region: 'center',
            nodeId: me.nodeId
        });

        Ext.apply(me, {
            layout: 'border',
            items: [leftPlanVersionPanel, taskPedigreeRelationPanel]
        });

        me.addEvents(
            'refresh'
        );
        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'refresh', me.refresh, me);
    },
    refresh: function (nodeVersion) {
        var me = this;
        //刷新谱系面板
        var pedigreeComponent = me.down('pedigreeComponent');
        pedigreeComponent.fireEvent('doRefresh', me.nodeId, nodeVersion);
    }
});