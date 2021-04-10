Ext.define('OrientTdm.HistoryTaskMgr.HistotyTaskDataGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.histotyTaskDataGrid',
    initComponent: function () {
        var me = this;
        var modelId = me.modelId;
        var templateId = me.templateId;
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId
        });

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});