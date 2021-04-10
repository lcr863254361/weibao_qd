Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.DevData.RefData.RefDataCardPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.refDataCardPanel',
    config: {
        nodeId: '',
        nodeVersion: '',
        nodeType: null
    },
    requires: [
        'OrientTdm.CollabDev.Processing.ResultSettings.DevData.RefData.RefDevDataGrid',
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.SimpleHisDevDataGroupGrid',
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.ComplexHisDevDataTreeGrid'
    ],
    initComponent: function () {
        var me = this;

        //card布局,展现当前以及历史面板信息
        var devDataPanel = Ext.create('OrientTdm.CollabDev.Processing.ResultSettings.DevData.RefData.RefDevDataGrid', {
            nodeId: me.nodeId,
            nodeVersion: me.nodeVersion,
            nodeType: me.nodeType
        });

        var simpleHistoryPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.SimpleHisDevDataGroupGrid', {});

        var complexHistoryPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.ComplexHisDevDataTreeGrid', {});

        Ext.apply(me, {
            icon: null,
            layout: 'card',
            items: [devDataPanel, simpleHistoryPanel, complexHistoryPanel]
        });

        me.callParent(arguments);
    }
});