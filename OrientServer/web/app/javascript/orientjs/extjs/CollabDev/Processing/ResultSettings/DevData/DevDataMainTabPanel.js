Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.DevData.DevDataMainTabPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    alias: 'widget.processingDevDataMainTabPanel',
    config: {
        nodeId: null,
        nodeVersion: null,
        bmDataId: null,
        type: null
    },
    initComponent: function () {
        var me = this;

        Ext.apply(me, {
                title: '研发数据',
                icon: null,
                ui: 'orientTab2'
            }
        );
        me.callParent(arguments);

        me.initDefaultPanels();

    },
    initDefaultPanels: function () {
        var me = this;
        var selfDataPanel = Ext.create('OrientTdm.CollabDev.Processing.ResultSettings.DevData.SelfDevDataPanel', {
            title: '自身数据',
            nodeId: me.nodeId,
            nodeVersion: me.nodeVersion,
            bmDataId: me.bmDataId,
            type: me.type
        });
        me.add(selfDataPanel);

        var tempDataPanel = Ext.create('OrientTdm.CollabDev.Processing.ResultSettings.DevData.TempDevDataPanel', {
            title: '临时数据',
            nodeId: me.nodeId,
            nodeVersion: me.nodeVersion
        });
        me.add(tempDataPanel);

        if (me.type != TDM_SERVER_CONFIG.NODE_TYPE_PRJ) {
            var referenceDataPanel = Ext.create('OrientTdm.CollabDev.Processing.ResultSettings.DevData.RefData.RefDataCardPanel', {
                title: '参考数据',
                nodeId: me.nodeId,
                nodeVersion: me.nodeVersion,
                nodeType: me.type
            });
            me.add(referenceDataPanel);
        }
        me.setActiveTab(0);

    }
});