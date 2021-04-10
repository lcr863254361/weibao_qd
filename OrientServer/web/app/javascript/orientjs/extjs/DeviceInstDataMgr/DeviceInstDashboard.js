Ext.define('OrientTdm.DeviceInstDataMgr.DeviceInstDashboard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.deviceInstDashboard',
    requires: [
        "OrientTdm.DataMgr.TBom.TBomTree",
        "OrientTdm.ProductStructMgr.StructDataShowRegion"
    ],
    id:'deviceInstDashboard',
    initComponent: function () {
        var me = this;
        var functionId = me.itemId;
        if (functionId) {
            //截取ID
            functionId = functionId.substr(functionId.indexOf("-") + 1, functionId.length);
        }
        //创建中间面板
        var centerPanel = Ext.create("OrientTdm.ProductStructMgr.StructDataShowRegion", {
            region: 'center',
            padding: '0 0 0 5',
            isOnlyShow:true
        });
        //Tbom
        var tbomPanel = Ext.create("OrientTdm.ProductStructMgr.ProductStructTree", {
            width: 280,
            minWidth: 280,
            maxWidth: 400,
            belongFunctionId: functionId,
            region: 'west',
            isHideOperationButton:true
        });
        // tbomPanel.expandAll();
        Ext.apply(me, {
            layout: 'border',
            items: [centerPanel, tbomPanel],
            westPanel: tbomPanel,
            centerPanel: centerPanel
        });
        me.callParent(arguments);
    }
});