/**
 * 研发数据
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.resultSettingsDevDataPanel',
    config: {
        nodeId: '',
        nodeVersion: '',
        nodeType: null,//节点类型
        isGlobal: 1,
        queryUrl: null,
        isShowLeftBar: true,
        showOtherFunctionButtonType: null,
        canCelldblclick: true
    },
    requires: [
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.DevDataSettingGrid',
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.SimpleHisDevDataGroupGrid',
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.ComplexHisDevDataTreeGrid'
    ],
    initComponent: function () {
        var me = this;

        //card布局,展现当前以及历史面板信息
        var devDataPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.DevDataSettingGrid', {
            nodeId: me.nodeId,
            nodeVersion: me.nodeVersion,
            nodeType: me.nodeType,
            isGlobal: me.isGlobal,
            queryUrl: me.queryUrl,
            isShowLeftBar: me.isShowLeftBar,
            showOtherFunctionButtonType: me.showOtherFunctionButtonType,
            canCelldblclick: me.canCelldblclick
        });

        var simpleHistoryPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.SimpleHisDevDataGroupGrid', {});

        var complexHistoryPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.ComplexHisDevDataTreeGrid', {});

        Ext.apply(me, {
            //title: '研发数据',
            icon: null,
            layout: 'card',
            items: [devDataPanel, simpleHistoryPanel, complexHistoryPanel]
        });

        me.callParent(arguments);
    }
});