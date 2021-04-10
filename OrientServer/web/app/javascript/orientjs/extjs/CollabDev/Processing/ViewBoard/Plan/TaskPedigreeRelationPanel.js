Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Plan.TaskPedigreeRelationPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.taskPedigreeRelationPanel',
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
         * 计划下的任务谱系面板
         */
        var pedigreeComponent = Ext.create('OrientTdm.CollabDev.Common.Content.Pedigree.PedigreeComponent', {
            region: 'center',
            startNodeId: me.nodeId,
            statusAble: false,
            hideButton: true,
            isAsync: false,
            isInit: false
        });

        /**
         * 状态提示面板
         */
        var statusPanel = Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Plan.TaskTechStatusTipPanel', {
            region: 'south',
            height: 40
        });

        Ext.apply(me, {
            layout: 'border',
            items: [pedigreeComponent, statusPanel]
        });

        me.callParent(arguments);
    },
    refresh: function (nodeId) {
        var me = this;
        var pedigreeComponent = me.down('pedigreeComponent');
        pedigreeComponent.nodeId = nodeId;
        pedigreeComponent.fireEvent('doRefresh', nodeId);
    }
});