Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.DevData.RefDevDataPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.refDevDataPanel',
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
                    nodeType: me.type,
                    isGlobal: 1,
                    queryUrl: serviceName + '/dataObj/getRefDataObj.rdm',
                    isShowLeftBar: false,
                    canCelldblclick: false
                }]
        });

        me.callParent(arguments);
    }
});