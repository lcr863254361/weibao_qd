Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.DevData.TempDevDataPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.tempDevDataPanel',
    config: {
        nodeId: null,
        nodeVersion: null,
        type: null,
        isGlobal: null,
        showOtherFunctionButtonType: null
    },
    requires: [
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.ResultSettingsDevDataPanel'
    ],
    initComponent: function () {

        var me = this;

        Ext.apply(me, {
            layout: 'fit',
            icon: null,
            dockedItems: [],
            items: [
                {
                    xtype: 'resultSettingsDevDataPanel',
                    nodeId: me.nodeId,
                    nodeVersion: me.nodeVersion,
                    isGlobal: 0,
                    showOtherFunctionButtonType: 2
                }]
        });

        me.callParent(arguments);
    }
});