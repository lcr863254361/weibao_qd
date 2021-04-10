Ext.define('OrientTdm.DestroyRepairTemplateMgr.DestroyTemplateDashboard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.destroyTemplateDashboard',
    initComponent: function () {
        var me = this;

        var centerPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
            region: 'center',
            layout: 'fit',
            itemId:'destroyTypeRespRegion',
            activeTabName : '',
            padding: '0 0 0 5'
        });

        var leftPanel = Ext.create("OrientTdm.DestroyRepairTemplateMgr.DestroyTaskTree", {
            collapsible: true,
            width: 280,
            minWidth: 280,
            maxWidth: 400,
            region: 'west',
            id:'destroyTaskTreePanel'
        });
        Ext.apply(this, {
            layout: 'border',
            items: [leftPanel, centerPanel],
            westPanel: leftPanel,
            centerPanel: centerPanel
        });

        this.callParent(arguments);
    }
});